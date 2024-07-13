package com.example.model;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {

    private TextField list_etat_init;
    private TextField list_etat_fini;
    private TextField symbolsAutomateField;
    private TextField statesAutomateField;
    private Button validateSymbolsButton;
    private Button validateStatesButton;
    private Button validateEtatInitialeButton;
    private Button validateEtatFinaleButton;
    private TextField resultatVerif;
    private Button btnVerifierChaine;
    private TableView<ObservableList<String>> tableView;
    private Label resultLabel;
    private final ObservableList<Data> dataList = FXCollections.observableArrayList();



    // =========( methode pour le lancement de l'application )==============
    @Override
    public void start(Stage primaryStage) {
        this.tableView = new TableView<>();
        this.tableView.setId("transitionTable");

        //==========( premiere objet de l'interface )===============
        primaryStage.setTitle("Automate");
        
        //======( labelle pour afficher le message )=============
        this.resultLabel = new Label("Entrer votre automate");
        this.resultLabel.setId("resultLabel");
        this.resultLabel.setMaxWidth(Region.USE_PREF_SIZE);
        
        // =========( instattiation de l'automate )========
        Automate automate = new Automate();

        //======( barre de menu )===========
        MenuBar menuBar = new MenuBar();

        // =====( composant de la barre de menu )==========
        Menu tabAFD = new Menu("AFD");
        Menu tabAFN = new Menu("Gluskov");
        // Menu tabSimulateur = new Menu("simulateur");
        // Menu tabAFN2 = new Menu("AFN");

        // tabPane.getTabs().addAll(tabAFD, tabAFN, tabSimulateur, tabAFN2);
        menuBar.getMenus().addAll(tabAFD, tabAFN);
        
        // Panel for AFN Tab
        GridPane afnPane = new GridPane();
        afnPane.setPadding(new Insets(10));
        afnPane.setHgap(10);
        afnPane.setVgap(10);


        // Left Panel
        VBox leftPanel = new VBox(10);
        leftPanel.setMinWidth(Region.USE_PREF_SIZE);
        leftPanel.setMaxWidth(Region.USE_PREF_SIZE);
        leftPanel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-background-color: #333333; -fx-padding: 10; -fx-border-radius: 60;");

        this.symbolsAutomateField = new TextField();
        this.symbolsAutomateField.setPromptText("symboles automate (a,b,c)");
        this.symbolsAutomateField.setMaxWidth(Region.USE_PREF_SIZE);
        this.validateSymbolsButton = new Button("Ajouter les symboles de l'alphabet");
        this.validateSymbolsButton.setMaxWidth(Region.USE_PREF_SIZE);
        // ======( bloc pour ajouter les symboles )=========
        this.validateSymbolsButton.setOnAction(event -> {
            // Code à exécuter lorsque le bouton est cliqué
            String inputText = this.symbolsAutomateField.getText();
            String inputText1[] = inputText.split(",");
            for(int i = 0; i < inputText1.length; i++){
                if(inputText1[i] != " "){
                    automate.ajouterSymbole(inputText1[i]+"");
                    System.out.println("Etat : " + inputText1[i]);

                    boolean found = false;
                    // Ajoute l'état à une ligne vide ou crée une nouvelle ligne
                    for (Data data : dataList) {
                        if (data.getSymbole().isEmpty()) {
                            data.setSymbole(inputText1[i]);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        dataList.add(new Data("", inputText1[i],"","",""));
                    }
                }
            }

            

            this.symbolsAutomateField.setText("");
            
        });
        HBox hBoxSymbole = new HBox();
        hBoxSymbole.setMaxWidth(Region.USE_PREF_SIZE);
        hBoxSymbole.getChildren().addAll(this.symbolsAutomateField, this.validateSymbolsButton);



    

        this.statesAutomateField = new TextField();
        statesAutomateField.setPromptText("Entrer les etats de l'automate(1,2,3)");
        this.statesAutomateField.setMaxWidth(Region.USE_PREF_SIZE);
        this.validateStatesButton = new Button("Ajouter etat");
        // validateStatesButton.setStyle("-fx-background-color: #00cc00;");

        // ======( bloc pour ajouter les etats )===========
        this.validateStatesButton.setOnAction(event -> {
            // Code à exécuter lorsque le bouton est cliqué
            String inputText = this.statesAutomateField.getText();
            String inputText1[] = inputText.split(",");
            for(int i = 0; i < inputText1.length; i++){
                if(inputText1[i] != " "){
                    automate.ajouterEtat(inputText1[i]+"");
                    System.out.println("Etat : " + inputText1[i]);

                    boolean found = false;
                    // Ajoute l'état à une ligne vide ou crée une nouvelle ligne
                    for (Data data : dataList) {
                        if (data.getEtat().isEmpty()) {
                            data.setEtat(inputText1[i]);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        dataList.add(new Data(inputText1[i], "","","",""));
                    }
                }
            }
            this.statesAutomateField.setText("");
            // System.out.println("Bouton cliqué !");
            
        });
        HBox hBoxEtat = new HBox();
        hBoxEtat.getChildren().addAll(this.statesAutomateField, this.validateStatesButton);

        // ========( block pour inserer les etats finaux )=========
        this.list_etat_fini = new TextField();
        list_etat_fini.setPromptText("Entrer les etats finaux");
        this.validateEtatFinaleButton = new Button("Ajouter etat finale(1,2,3)");
        this.validateEtatFinaleButton.setOnAction(event -> {
            // Code à exécuter lorsque le bouton est cliqué
            String inputText = this.list_etat_fini.getText();
            String inputText1[] = inputText.split(",");
            for(int i = 0; i < inputText1.length; i++){
                if(automate.getEtats().contains(inputText1[i]+"")){
                    automate.ajouterEtatFinale(inputText1[i]+"");
                    System.out.println("Etat finale : " + inputText1[i]);
                }
                else{
                    System.out.println("l'etat : " + inputText1[i]+ " ne fait pas partir des etat!!");
                    resultLabel.setText("l'etat : " + inputText1[i]+ " ne fait pas partir des etat!!");
                }
                
            }
            this.list_etat_fini.setText("");
            
        });

        HBox hBoxEtatFinaux = new HBox();
        hBoxEtatFinaux.getChildren().addAll(this.list_etat_fini, this.validateEtatFinaleButton);

        // ========( block pour inserer les etat initiaux )=========
        this.list_etat_init = new TextField();
        this.list_etat_init.setPromptText("Entrer l'etat initiale");
        this.validateEtatInitialeButton = new Button("Ajouter etat initiale");

        this.validateEtatInitialeButton.setOnAction(event -> {
            // Code à exécuter lorsque le bouton est cliqué
            String inputText = this.list_etat_init.getText();
            if(automate.getEtats().contains(inputText)){
                if(automate.getInitEtats().size() > 0){
                    System.err.println("Vous ne pouvez pas avoir plusieurs etat initiaux !!!!");
                    resultLabel.setText("Vous ne pouvez pas avoir plusieurs etat initiaux !!!!");
                }
                else{
                    automate.ajouterEtatInitiale(inputText);
                    System.out.println("Etat : " + inputText);

                }   
            }
            else{
                System.err.printf("L'etat %s ne fait pas partir des etats!!!!\n",inputText);
                resultLabel.setText("L'etat %s ne fait pas partir des etats!!!! !!!!");
            }
            
            this.list_etat_init.setText("");
            
        });
        HBox hBoxEtatInitiaux = new HBox();
        hBoxEtatInitiaux.getChildren().addAll(this.list_etat_init, this.validateEtatInitialeButton);

        
        
        resultLabel.setStyle("-fx-background-color: #red;");

        this.resultatVerif = new TextField();
        this.resultatVerif.setPromptText("Entrer la chaine a verifier");
        this.resultatVerif.setMaxWidth(Region.USE_PREF_SIZE);

        this.btnVerifierChaine = new Button("Verifier la chaine");
        // validateInputButton.setStyle("-fx-background-color: #00cc00;");
        this.btnVerifierChaine.setMaxWidth(Region.USE_PREF_SIZE);
        this.btnVerifierChaine.setOnAction(event -> {
            String inputText = this.resultatVerif.getText();
            automate.afficherMatriceTransition();
            String resultat = automate.lireChaine(inputText);
            resultLabel.setText(resultat);
            System.out.println("Resultat : " + resultat);
        });


        // ========( block pour definir les transitions )=========
        TextField etatDebut = new TextField();
        etatDebut.setPromptText("Etat de depart");
        etatDebut.setStyle("-fx-margin: 20px; -fx-pref-width: 130;");

        TextField etiquette = new TextField();
        etiquette.setPromptText("Etiquette");
        etiquette.setStyle("-fx-margin: 20px; -fx-pref-width: 130;");

        TextField etatFin = new TextField();
        etatFin.setPromptText("Etat de fin");
        etatFin.setStyle("-fx-margin: 80px; -fx-pref-width: 130;");

        Button boutonAjouterTransition = new Button();

        HBox hBoxTableTransition = new HBox();
        hBoxTableTransition.getChildren().addAll(etatDebut, etiquette, etatFin, boutonAjouterTransition);
        boutonAjouterTransition.setId("boutonAjouterTransition");
        boutonAjouterTransition.setText("Ajouter une transition");

        // ====(bouton pour ajouter une transition )======
        boutonAjouterTransition.setOnAction(event ->{
            String debutVal = etatDebut.getText();
            String etiquetteVal = etiquette.getText();
            String finVal = etatFin.getText();
            
            if(automate.getEtats().contains(debutVal) == false || automate.getSymboles().contains(etiquetteVal) == false || automate.getEtats().contains(finVal) == false){
                if(automate.getEtats().contains(debutVal) == false ){
                    resultLabel.setText("L'etat "+debutVal+" ne fait pas partir des etats");
                    System.err.println("L'etat "+debutVal+" ne fait pas partir des etats");
                }
                else if(automate.getSymboles ().contains(etiquetteVal) == false ){
                    resultLabel.setText("Le symbole "+etiquetteVal+" ne fait pas partir de l'alphabet");
                    System.err.println("Le symbole "+etiquetteVal+" ne fait pas partir de l'alphabet");
                }
                else{
                    resultLabel.setText("L'etat "+finVal+" ne fait pas partir des etats");
                    System.out.println("L'etat "+finVal+" ne fait pas partir des etats");
                }
                
            }
            else{
                System.out.printf("Transition : (%s)==%s==>(%s)\n",debutVal, etiquetteVal, finVal);
                if(automate.getTransitions().containsKey(debutVal)){
                    if(automate.getTransitions().get(debutVal).containsKey(etiquetteVal)){
                        resultLabel.setText("Vous devez entrer un AFD !!!!");
                        System.err.println("Vous devez entrer un AFD !!!!");
                    }
                    else{
                        

                        boolean found = false;
                        // Ajoute l'état à une ligne vide ou crée une nouvelle ligne
                        for (Data data : dataList) {
                            if (data.getEtatDepart().isEmpty() && data.getEtiquette().isEmpty() && data.getEtatArriver().isEmpty()) {
                                
                                data.setetatDepart(debutVal);
                                data.setetiquette(etiquetteVal);
                                data.setetatArriver(finVal);
                                automate.ajouterTransition(debutVal+"", etiquetteVal+"", finVal+"");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            dataList.add(new Data("", "",debutVal,etiquetteVal,finVal));
                            automate.ajouterTransition(debutVal+"", etiquetteVal+"", finVal+"");
                        }

                    }
                }
                else{

                    boolean found = false;
                        // Ajoute l'état à une ligne vide ou crée une nouvelle ligne
                        for (Data data : dataList) {
                            if (data.getEtatDepart().isEmpty() && data.getEtiquette().isEmpty() && data.getEtatArriver().isEmpty()) {
                                
                                data.setetatDepart(debutVal);
                                data.setetiquette(etiquetteVal);
                                data.setetatArriver(finVal);
                                automate.ajouterTransition(debutVal+"", etiquetteVal+"", finVal+"");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            dataList.add(new Data("", "",debutVal,etiquetteVal,finVal));
                            automate.ajouterTransition(debutVal+"", etiquetteVal+"", finVal+"");
                        }
                        
                }
                
            }

            etatDebut.setText("");
            etiquette.setText("");
            etatFin.setText("");
            

        });

        leftPanel.getChildren().addAll(hBoxSymbole, hBoxEtat,hBoxEtatInitiaux ,hBoxEtatFinaux,hBoxTableTransition, 
                                        resultLabel, this.resultatVerif, this.btnVerifierChaine);

        // ==========( construction du tableau )==================
        
        // Right Panel - Transition Table
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setStyle("-fx-background-color: #333333; -fx-padding: 10; -fx-border-radius: 10;");
       
        Label transitionTableLabel = new Label("table transition");
        transitionTableLabel.setStyle("-fx-text-fill: #ffffff;");

        GridPane transitionTable = new GridPane();
        transitionTable.setPadding(new Insets(10));
        transitionTable.setHgap(10);
        transitionTable.setVgap(10);

        // First row of the transition table (headers)
        // TableView et ses colonnes
        TableView<Data> tableView = new TableView<>();
        tableView.setId("transitionTable");
        TableColumn<Data, String> etatColumn = new TableColumn<>("États");
        etatColumn.setStyle("background-color: #242b38");
        etatColumn.setCellValueFactory(cellData -> cellData.getValue().etatProperty());
        TableColumn<Data, String> symboleColumn = new TableColumn<>("Symboles");
        symboleColumn.setCellValueFactory(cellData -> cellData.getValue().symboleProperty());

        TableColumn<Data, String> etatDepartColumn = new TableColumn<>("Depart");
        etatDepartColumn.setCellValueFactory(cellData -> cellData.getValue().etatDepartProperty());
        TableColumn<Data, String> etiquetteColumn = new TableColumn<>("Etiquette");
        etiquetteColumn.setCellValueFactory(cellData -> cellData.getValue().etiquetteProperty());
        TableColumn<Data, String> etatArriverColumn = new TableColumn<>("Arrivee");
        etatArriverColumn.setCellValueFactory(cellData -> cellData.getValue().etatArriverProperty());

        
        tableView.setItems(dataList);
        tableView.getColumns().add(etatColumn);
        tableView.getColumns().add(symboleColumn);
        tableView.getColumns().add(etatDepartColumn);
        tableView.getColumns().add(etiquetteColumn);
        tableView.getColumns().add(etatArriverColumn);

        //========( code pour reinitialiser l'automate )=================
        Button nouvelleAutomateBtn = new Button("Nouvelle automate");
        nouvelleAutomateBtn.setOnAction(event -> {
            automate.destructeur();
            System.err.println("Automate reinitialiser !!!");
            resultLabel.setText("Automate reinitialiser !!!");
        });



        rightPanel.getChildren().addAll(transitionTableLabel, tableView, nouvelleAutomateBtn);
        
        // Main Layout
        HBox mainLayout = new HBox(10);
        mainLayout.setStyle("-fx-background-color: rgb(17, 17, 37);");
        mainLayout.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);


        VBox rootLayout = new VBox(10);
        rootLayout.setStyle("-fx-background-color: rgb(17, 17, 37);");
        rootLayout.getChildren().addAll(menuBar, mainLayout);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);


        

        

        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ===========( class pour la representation de l'alphabet, des symboles et des transitions )=======
    
    /**
     * DATA
     */
    // Classe pour les données de la TableView
    public static class Data {
        private final SimpleStringProperty etat;
        private final SimpleStringProperty symbole;
        private final SimpleStringProperty etatDepart;
        private final SimpleStringProperty etiquette;
        private final SimpleStringProperty etatArriver;

        public Data(String etat, String symbole, String etatDepart, String etiquette, String etatArriver) {
            this.etat = new SimpleStringProperty(etat);
            this.symbole = new SimpleStringProperty(symbole);
            this.etatDepart = new SimpleStringProperty(etatDepart);
            this.etiquette = new SimpleStringProperty(etiquette);
            this.etatArriver = new SimpleStringProperty(etatArriver);
        }

        // ------------------------------
        public String getEtat() {
            return etat.get();
        }

        public SimpleStringProperty etatProperty() {
            return etat;
        }

        public void setEtat(String etat) {
            this.etat.set(etat);
        }

        // --------------------------------------
        public String getSymbole() {
            return symbole.get();
        }

        public SimpleStringProperty symboleProperty() {
            return symbole;
        }

        public void setSymbole(String symbole) {
            this.symbole.set(symbole);
        }

        // -----------------------------------------
        public String getEtatDepart() {
            return etatDepart.get();
        }

        public SimpleStringProperty etatDepartProperty() {
            return etatDepart;
        }


        public void setetatDepart(String etatDepart) {
            this.etatDepart.set(etatDepart);
        }


        
        // --------------------------------------
        public String getEtatArriver() {
            return etatArriver.get();
        }

        public SimpleStringProperty etatArriverProperty() {
            return etatArriver;
        }

        public void setetatArriver(String etatArriver) {
            this.etatArriver.set(etatArriver);
        }



        // ----------------------------
        public String getEtiquette() {
            return etiquette.get();
        }

        public void setetiquette(String etiquette) {
            this.etiquette.set(etiquette);
        }

        public SimpleStringProperty etiquetteProperty() {
            return etiquette;
        }
        
    }


    
}