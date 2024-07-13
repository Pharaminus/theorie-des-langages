package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TableController {

    @FXML
    private GridPane tableGrid;

    private int rows = 2;
    private int cols = 2;

    public void addRow() {
        rows++;
        updateTable();
    }

    public void addColumn() {
        cols++;
        updateTable();
    }

    public void removeRow() {
        if (rows > 1) {
            rows--;
            updateTable();
        }
    }

    public void removeColumn() {
        if (cols > 1) {
            cols--;
            updateTable();
        }
    }

    private void updateTable() {
        tableGrid.getChildren().clear();
        tableGrid.getColumnConstraints().clear();
        tableGrid.getRowConstraints().clear();

        for (int i = 0; i < cols; i++) {
            tableGrid.getColumnConstraints().add(new ColumnConstraints(100, 100, Double.MAX_VALUE));
        }

        for (int i = 0; i < rows; i++) {
            tableGrid.getRowConstraints().add(new RowConstraints(30, 30, Double.MAX_VALUE));
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Label label = new Label(String.format("(%d, %d)", row, col));
                TextField textField = new TextField();
                tableGrid.add(label, col, row);
                tableGrid.add(textField, col, row + 1);
            }
        }
    }

    @FXML
    public void initialize() {
        updateTable();
    }
}