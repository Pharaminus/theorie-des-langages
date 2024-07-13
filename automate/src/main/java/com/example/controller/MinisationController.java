package com.example.controller;

import java.io.IOException;

import com.example.App;

import javafx.fxml.FXML;

public class MinisationController extends AcceuilController {
    
    @FXML
    private void switchToAFD() throws IOException {
        App.setRoot("acceuil");
    }

    @FXML
    private void switchToMinimisation() throws IOException {
        App.setRoot("minimisation");
    }

    
}
