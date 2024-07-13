package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.example.App;
import com.example.model.AFD;
import com.example.model.AutomatonVisualizer;

import javafx.fxml.FXML;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AcceuilController {

    AFD automate = new AFD();

    @FXML
    private TextField inputTextFieldSymboles;
    @FXML
    private TextField inputTextFieldEtats;
    @FXML
    private TextField inputTextFieldEtatInitiale;
    @FXML
    private TextField inputTextFieldEtatFinale;
    @FXML
    private TextField inputTextFieldEtatDepart;
    @FXML
    private TextField inputTextFieldEtiquette;
    @FXML
    private TextField inputTextFieldEtatArriver;
    @FXML
    private TextField inputTextFieldChaine;

    
    @FXML
    private Label ZoneMessage;

    @FXML
    private GridPane tableGrid;
    @FXML
    private GridPane tableGridSymbole;
    @FXML
    private GridPane tableGridEtat;
    @FXML
    private GridPane tableGridEtatInit;
    @FXML
    private GridPane tableGridEtatFinaux;

    @FXML
    private GridPane tableGridEtatDepart;
    @FXML
    private GridPane tableGridEtiquette;
    @FXML
    private GridPane tableGridEtatArriver;


    private int alphabetRows = 1;
    private int etatRows = 1;
    private int etatFinauxRows = 1;
    private int nombreTransition = 1;
    

    
    @FXML
    private void switchToAFD() throws IOException {
        App.setRoot("acceuil");
    }

    @FXML
    private void switchToMinimisation() throws IOException {
        App.setRoot("minimisation");
    }

    

    

    public GridPane supprimerDerniereLigne(GridPane gridPane, int nbreLigne) {
        if (nbreLigne >= 2) {
            gridPane.getChildren().remove(nbreLigne - 1);
            // nbreLigne--;
        }

        return gridPane;
    }

    // ================( methode pour supprimer une transition )============
    public void supprimerTransition() {

        tableGridEtatDepart = supprimerDerniereLigne(tableGridEtatDepart, nombreTransition);
        tableGridEtiquette = supprimerDerniereLigne(tableGridEtiquette, nombreTransition);
        tableGridEtatArriver = supprimerDerniereLigne(tableGridEtatArriver, nombreTransition);
        
        
        nombreTransition--;
        System.out.println("Transition supprimer avec sucess !!!");
    }

    
    // ================( methode pour ajuter un symbole de l'alphabet )============
    @FXML
    private void ajouterSymbole() {
        String inputText = this.inputTextFieldSymboles.getText();
        inputText = inputText.trim();
        String inputText1[] = inputText.split(",");

        for(int i = 0; i < inputText1.length; i++){
            if(!inputText1[i].isEmpty()){
                if(!automate.getSymboles().contains(inputText1[i])){
                    alphabetRows++;

                    automate.ajouterSymbole(inputText1[i]+"");
                    System.out.println("Etat : " + inputText1[i]);
            
                    tableGridSymbole = ajouterLigne(alphabetRows, inputText1[i], tableGridSymbole);

                }
                else{
                    ZoneMessage.setText("C'est deja un symbole de l'alphabet !!!");
                }
                
            }
        }
        this.inputTextFieldSymboles.setText("");
    }
    // ================( methode pour ajouter un etat )============

    @FXML
    private void ajouterEtat() {
        // inputTextFieldEtats = new TextField();
        String inputText = this.inputTextFieldEtats.getText();
        String inputText1[] = inputText.split(",");

        for(int i = 0; i < inputText1.length; i++){
            if(inputText1[i] != " "){
                if(!automate.getEtats().contains(inputText1[i])){
                    etatRows++;
                    automate.ajouterEtat(inputText1[i]+"");
                    System.out.println("Etat : " + inputText1[i]);

                    tableGridEtat = ajouterLigne(etatRows, inputText1[i], tableGridEtat);
                }
                else{
                    ZoneMessage.setText("C'est deja un etat !!!");
                }
                
            }
        }
        inputTextFieldEtats.setText("");
    }

    // ================( methode pour ajouter un etat finale )============

    @FXML
    private void ajouterEtatFinale(){

        String inputText = this.inputTextFieldEtatFinale.getText();
        String inputText1[] = inputText.split(",");
        for(int i = 0; i < inputText1.length; i++){
            if(automate.getEtats().contains(inputText1[i]+"")){
                if(!automate.getFinalEtat().contains(inputText1[i])){
                    etatFinauxRows++;

                    automate.ajouterEtatFinale(inputText1[i]+"");
                    System.out.println("Etat finale : " + inputText1[i]);
                    tableGridEtatFinaux = ajouterLigne(etatFinauxRows, inputText1[i], tableGridEtatFinaux);
                }
                else{
                    ZoneMessage.setText("C'est deja un etat finale !!!");
                }
                
            }
            else{
                System.out.println("l'etat : " + inputText1[i]+ " ne fait pas partir des etat!!");
                ZoneMessage.setText("l'etat : " + inputText1[i]+ " ne fait pas partir des etat!!");
            }
            
        }
        this.inputTextFieldEtatFinale.setText("");
    }

    

    // ================( methode pour ajouter un etat initiale )============
    @FXML
    private void ajouterEtatInitiale(){
        String inputText = this.inputTextFieldEtatInitiale.getText();
            if(automate.getEtats().contains(inputText)){
                if(!automate.getEtatInitiale().isEmpty()){
                    System.err.println("Vous ne pouvez pas avoir plusieurs etat initiaux !!!!");
                    ZoneMessage.setText("Vous ne pouvez pas avoir plusieurs etat initiaux !!!!");
                }
                else{
                    automate.ajouterEtatInitiale(inputText);
                    System.out.println("Etat initiale : " + inputText);

                    tableGridEtatInit = ajouterLigne(2, inputText, tableGridEtatInit);
                    
                }   
            }
            else{
                System.err.printf("L'etat %s ne fait pas partir des etats!!!!\n",inputText);
                ZoneMessage.setText("L'etat "+ inputText +" ne fait pas partir des etats !!!!");
            }
            
            this.inputTextFieldEtatInitiale.setText("");
    }

    // ================( methode pour ajouter une transition )============
    @FXML
    private void ajouterTransition(){
        String debutVal = this.inputTextFieldEtatDepart.getText();
            String etiquetteVal = this.inputTextFieldEtiquette.getText();
            String finVal = this.inputTextFieldEtatArriver.getText();
            
            if(automate.getEtats().contains(debutVal) == false || automate.getSymboles().contains(etiquetteVal) == false || automate.getEtats().contains(finVal) == false){
                if(automate.getEtats().contains(debutVal) == false ){
                    ZoneMessage.setText("L'etat depart "+debutVal+" ne fait pas partir des etats");
                    System.err.println("L'etat depart "+debutVal+" ne fait pas partir des etats");
                }
                else if(automate.getSymboles ().contains(etiquetteVal) == false ){
                    ZoneMessage.setText("Le symbole "+etiquetteVal+" ne fait pas partir de l'alphabet");
                    System.err.println("Le symbole "+etiquetteVal+" ne fait pas partir de l'alphabet");
                }
                else if(automate.getSymboles ().contains(finVal) == false ){
                    ZoneMessage.setText("L'etat arrive "+finVal+" ne fait pas partir des etats");
                    System.out.println("L'etat arrive "+finVal+" ne fait pas partir des etats");
                }
                
            }
            else{
                System.out.printf("Transition : (%s)==%s==>(%s)\n",debutVal, etiquetteVal, finVal);
                if(automate.getTransitionsAFD().containsKey(debutVal)){
                    if(automate.getTransitionsAFD().get(debutVal).containsKey(etiquetteVal)){
                        ZoneMessage.setText("Vous devez entrer un AFD !!!!");
                        System.err.println("Vous devez entrer un AFD !!!!");
                    }
                    else{
                        System.err.println("1-Transitions ajouter avec sucess!");

                        automate.ajouterTransition(debutVal+"", etiquetteVal+"", finVal+"");
                        nombreTransition++;
                        // =======( ajouter donnees dans table de transition )==========
                        tableGridEtatDepart = ajouterLigne(nombreTransition, debutVal, tableGridEtatDepart);
                        tableGridEtiquette = ajouterLigne(nombreTransition, etiquetteVal, tableGridEtiquette);
                        tableGridEtatArriver = ajouterLigne(nombreTransition, finVal, tableGridEtatArriver);

                        

                    }
                }
                else{
                    automate.ajouterTransition(debutVal+"", etiquetteVal+"", finVal+"");
                    nombreTransition++;
                    System.err.println("2-Transitions ajouter avec sucess!");

                    // =======( ajouter donnees dans table de transition )==========
                    tableGridEtatDepart = ajouterLigne(nombreTransition, debutVal, tableGridEtatDepart);
                    tableGridEtiquette = ajouterLigne(nombreTransition, etiquetteVal, tableGridEtiquette);
                    tableGridEtatArriver = ajouterLigne(nombreTransition, finVal, tableGridEtatArriver);
                    
                }
                
            }

            this.inputTextFieldEtatDepart.setText("");
            this.inputTextFieldEtiquette.setText("");
            this.inputTextFieldEtatArriver.setText("");
    }

    // ==========( methode pour ajouter une ligne dans l'affichage )==========
    private GridPane ajouterLigne(int nombreTransition, String etiquette, GridPane tabeGride){
        tabeGride.getColumnConstraints().clear();
        tabeGride.getRowConstraints().clear();

        // for (int j = 0; j < alphabetCols; j++) {
        if(nombreTransition == 0)
            tableGrid.getColumnConstraints().add(new ColumnConstraints(30, 30, Double.MAX_VALUE));
        // }

        for (int j = 0; j < nombreTransition; j++) {
            tabeGride.getRowConstraints().add(new RowConstraints(30, 30, Double.MAX_VALUE));
        }   

        for (int row = 0; row < nombreTransition; row++) {
            // for (int col = 0; col < nombreTransition; col++) {
                if(row == nombreTransition -1){
                    TextField textField = new TextField();
                    // if(row == row - 1)
                    textField.setText(etiquette);
                    textField.setEditable(false);;
                    textField.setStyle("-fx-pref-width: 3px;");
                    // tableGrid.add(label, col, row);
                    tabeGride.add(textField, 0, row + 1);
                // }
            }
        }

        return tabeGride;
    }

    // ===========( methode de mise ajour des etats )================
    public GridPane supprimerLigne(GridPane tabeGride){
        tabeGride.getColumnConstraints().clear();
        tabeGride.getRowConstraints().clear();
        tabeGride.getChildren().clear();

        return tabeGride;
    }

    // =========( methode pour verifier si la chaine est reconnu par l'automate )============
    @FXML
    private void verifierChaine(){
        String inputText = this.inputTextFieldChaine.getText();
        automate.afficherMatriceTransition();
        String resultat = automate.lireChaine(inputText);
        ZoneMessage.setText(resultat);
        System.out.println("Resultat : " + resultat);
    }

    @FXML
    // =======( methode pour minimiser un afd )===========
    private void minimiser(){

        automate = automate.minimize();
        miseAjourAutomate();
        
    }

    // =======( methode pour mettre a jour l'affichage de element de l'automate )==========
    private void miseAjourEtatGridPane(){
        supprimerDerniereLigne(tableGridEtat, etatRows);
        etatRows = 1;
        supprimerDerniereLigne(tableGridEtatFinaux, etatFinauxRows);
        etatFinauxRows = 1;
        supprimerDerniereLigne(tableGridSymbole, alphabetRows);
        alphabetRows = 1;

        // if (nbreLigne >= 2) {
        //     gridPane.getChildren().remove(nbreLigne - 1);
        //     // nbreLigne--;
        // }
        

        // gridPane.getChildren().remove(nbreLigne - 1);
        // supprimerDerniereLigne(tableGridEtatInit, 2);
        tableGridEtatInit = ajouterLigne(2, automate.getEtatInitiale(), tableGridEtatInit);
        
        for( String etat : automate.getEtats()){
            etatRows++;
            tableGridEtat = ajouterLigne(etatRows, etat, tableGridEtat);
        }

        for( String etat : automate.getFinalEtat()){
            etatFinauxRows++;
            tableGridEtatFinaux = ajouterLigne(etatFinauxRows, etat, tableGridEtatFinaux);
        }

        for( String etat : automate.getSymboles()){
            alphabetRows++;
            tableGridSymbole = ajouterLigne(alphabetRows, etat, tableGridSymbole);
        }

        

    }

    // ==========( methode pour mettre a jour l'automate )==========
    private void miseAjourAutomate(){

        miseAjourEtatGridPane();

        int iter = nombreTransition;
        for(int i = 1; i < iter; i++){
            supprimerTransition();
            System.out.println(iter);
            
        }

        nombreTransition = 1;   

        for (Map.Entry<String, Map<String, String>> transitionsParEtat : automate.getTransitionsAFD().entrySet()) {
            String etatDepart = transitionsParEtat.getKey();
            Map<String, String> transitionsDepuisEtat = transitionsParEtat.getValue();
    
            for (Map.Entry<String, String> transition : transitionsDepuisEtat.entrySet()) {
                nombreTransition++;

                String symbole = transition.getKey();
                String etatArrivee = transition.getValue();
                tableGridEtatDepart = ajouterLigne(nombreTransition, etatDepart, tableGridEtatDepart);
                tableGridEtiquette = ajouterLigne(nombreTransition, symbole, tableGridEtiquette);
                tableGridEtatArriver = ajouterLigne(nombreTransition, etatArrivee, tableGridEtatArriver);

                System.out.println("Transition depuis l'état " + etatDepart + " avec le symbole " + symbole + " vers l'état " + etatArrivee);
            }
        }
        
        // automate.toString();
        // automate.transitionsToString();
        ZoneMessage.setText(automate.getEtats()+"");
    }


    // =========( methode pour importer l'automate )==========
    public void importAutomate(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir fichier Automate");
        File file = fileChooser.showOpenDialog(App.getStage());
        if (file != null) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
                // automate.toString2();
                automate.fromString(content);
                miseAjourAutomate();
                ZoneMessage.setText("Automate importé avec succès:\n" + automate.getEtats());
            } catch (IOException ex) {
                ZoneMessage.setText("Erreur lors de l'importation de l'automate.");
            }
        }

        // System.out.println(automate.getTransitionsAFD());

    }
    // =======( methode pour exporter l'automate )=========
    public void exportAutomate(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer fichier Automate");
        File file = fileChooser.showSaveDialog(App.getStage());
        if (file != null) {
            try {
                String content = automate.toString();
                Files.write(Paths.get(file.toURI()), content.getBytes());
                ZoneMessage.setText("Automate exporté avec succès:\n" + content);
            } catch (IOException ex) {
                ZoneMessage.setText("Erreur lors de l'exportation de l'automate.");
            }
        }
    }



    @FXML
    private void grapheAutomate(){
        AutomatonVisualizer automatonVisualizer = new AutomatonVisualizer();
        
         Pane pane = new Pane();
        Stage primaryStage = new Stage();
      

        // drawTransitions(pane, transitions);
        // pane.setStyle("-fx-background-color: #605b70;");
        automatonVisualizer.drawTransitions(pane, automate.getTransitionsAFD());

        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("Graphe de l'automate");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    

}
