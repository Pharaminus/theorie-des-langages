package com.example.model;



import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Map;

public class AutomatonVisualizer {

    private static final double RADIUS = 20;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    
    

    public  void drawTransitions(Pane pane, Map<String, Map<String, String>> transitions) {
        Map<String, Circle> stateNodes = new HashMap<>();
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;
        double angleStep = 2 * Math.PI / transitions.size();
        int index = 0;

        // Dessiner les états
        for (String state : transitions.keySet()) {
            double x = centerX + 150 * Math.cos(index * angleStep);
            double y = centerY + 200 * Math.sin(index * angleStep);
            Circle circle = new Circle(x, y, RADIUS, Color.LIGHTBLUE);
            Text text = new Text(x - 5, y + 5, state);
            stateNodes.put(state, circle);
            pane.getChildren().addAll(circle, text);
            index++;
        }

        // Dessiner les transitions
        for (Map.Entry<String, Map<String, String>> entry : transitions.entrySet()) {
            String fromState = entry.getKey();
            Circle fromCircle = stateNodes.get(fromState);
            Map<String, String> transitionMap = entry.getValue();

            for (Map.Entry<String, String> transition : transitionMap.entrySet()) {
                String input = transition.getKey();
                String toStates = transition.getValue();

                // int toIndex = 0;
                // for (String toState : toStates) {
                    Circle toCircle = stateNodes.get(toStates);

                    if (fromCircle == toCircle) {
                        // Transition sur soi-même
                        drawSelfTransition(pane, fromCircle, input);
                    } else {
                        // Transition vers un autre état
                        drawTransition(pane, fromCircle, toCircle, input, 0);
                    }

                    // toIndex++;
                // }
            }
        }
    }

    private void drawSelfTransition(Pane pane, Circle circle, String input) {
        double centerX = circle.getCenterX();
        double centerY = circle.getCenterY();
        double controlOffset = 50;

        CubicCurve curve = new CubicCurve(
                centerX, centerY - RADIUS,
                centerX - controlOffset, centerY - controlOffset - RADIUS,
                centerX + controlOffset, centerY - controlOffset - RADIUS,
                centerX, centerY - RADIUS);
        curve.setFill(Color.TRANSPARENT);
        curve.setStroke(Color.BLACK);

        Text text = new Text(centerX + 10, centerY - controlOffset - RADIUS - 10, input);
        text.setFill(Color.RED);

        pane.getChildren().addAll(curve, text);
    }

    public void drawTransition(Pane pane, Circle fromCircle, Circle toCircle, String input, int toIndex) {
        double startX = fromCircle.getCenterX();
        double startY = fromCircle.getCenterY();
        double endX = toCircle.getCenterX();
        double endY = toCircle.getCenterY();

        double deltaX = endX - startX;
        double deltaY = endY - startY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Calculate the new start and end points
        double adjustedStartX = startX + (deltaX * RADIUS / distance);
        double adjustedStartY = startY + (deltaY * RADIUS / distance);
        double adjustedEndX = endX - (deltaX * RADIUS / distance);
        double adjustedEndY = endY - (deltaY * RADIUS / distance);

        // Calculate control points for the curved line
        double controlOffsetX = deltaY * 30 / distance; // 30 is the curve offset, can be adjusted
        double controlOffsetY = -deltaX * 30 / distance;

        double controlX1 = (startX + endX) / 2 + controlOffsetX;
        double controlY1 = (startY + endY) / 2 + controlOffsetY;

        CubicCurve curve = new CubicCurve(adjustedStartX, adjustedStartY, controlX1, controlY1, controlX1, controlY1, adjustedEndX, adjustedEndY);
        curve.setFill(Color.TRANSPARENT);
        curve.setStroke(Color.BLACK);

        // Draw the arrowhead
        double arrowLength = 10;
        double arrowAngle = Math.toRadians(45);

        double angle = Math.atan2(adjustedEndY - controlY1, adjustedEndX - controlX1);  // Use control point to end point angle

        double arrowX1 = adjustedEndX - arrowLength * Math.cos(angle - arrowAngle);
        double arrowY1 = adjustedEndY - arrowLength * Math.sin(angle - arrowAngle);
        double arrowX2 = adjustedEndX - arrowLength * Math.cos(angle + arrowAngle);
        double arrowY2 = adjustedEndY - arrowLength * Math.sin(angle + arrowAngle);

        Line arrowLine1 = new Line(adjustedEndX, adjustedEndY, arrowX1, arrowY1);
        Line arrowLine2 = new Line(adjustedEndX, adjustedEndY, arrowX2, arrowY2);

        // Adjust text position to avoid overlap
        double midX = (adjustedStartX + adjustedEndX) / 2 + controlOffsetX / 2;
        double midY = (adjustedStartY + adjustedEndY) / 2 + controlOffsetY / 2;
        Text text = new Text(midX, midY - 8 - toIndex * 2, input);  
        text.setFill(Color.RED);

        pane.getChildren().addAll(curve, arrowLine1, arrowLine2, text);
    }

}