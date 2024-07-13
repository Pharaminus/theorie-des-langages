package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(loadFXML("acceuil"), 1300, 600);  
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        // stage.setWidth();
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        App.scene = scene;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void openNewWindow(Pane pane) {
        // Création de la nouvelle fenêtre
        Stage newStage = new Stage();

        VBox newRoot = new VBox();
        Label newLabel = new Label("This is a new window");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> newStage.close());
        newRoot.getChildren().addAll(newLabel, pane, closeButton);

        Scene newScene = new Scene(newRoot, 800, 600);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");
        newStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}