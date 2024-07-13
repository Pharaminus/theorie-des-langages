package com.example.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.ArrayList;

public class Automate {
    protected List<String> etats;
    protected List<String> symboles;
    protected List<String> initEtats;
    protected List<String> finalEtat;
    protected Map<String, Map<String, List<String>>> transitions;

    public Automate() {
        this.etats = new ArrayList<>();
        this.symboles = new ArrayList<>();
        this.finalEtat = new ArrayList<>();
        this.initEtats = new ArrayList<>();
        this.transitions = new HashMap<>();

        

    }

    public List<String> getEtats() {
        return etats;
    }

    public void setEtats(List<String> etats) {
        this.etats = etats;
    }

    public List<String> getSymboles() {
        return symboles;
    }

    public void setSymboles(List<String> symboles) {
        this.symboles = symboles;
    }

    public List<String> getInitEtats() {
        return initEtats;
    }

    public void setInitEtats(List<String> initEtats) {
        this.initEtats = initEtats;
    }

    public List<String> getFinalEtat() {
        return finalEtat;
    }

    public void setFinalEtat(List<String> finalEtat) {
        this.finalEtat = finalEtat;
    }

    public Map<String, Map<String, List<String>>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, Map<String, List<String>>> transitions) {
        this.transitions = transitions;
    }

    public void ajouterEtat(String etat) {
        this.etats.add(etat);
    }

    public void ajouterSymbole(String symbole) {
        this.symboles.add(symbole);
    }

    public void ajouterEtatInitiale(String etatInitiale) {
        this.initEtats.add(etatInitiale);
    }

    public void ajouterEtatFinale(String etatFinale) {
        this.finalEtat.add(etatFinale);
    }

    public void ajouterTransition(String etatDebut, String symbole, String etatFin) {

        // Map<String, List<String> > sousTransition = new HashMap();

        if (this.transitions.containsKey(etatDebut)) {
            if (this.transitions.get(etatDebut).containsKey(symbole)) {

                this.transitions.get(etatDebut).get(symbole).add(etatFin);

            } else {

                List<String> etats = new ArrayList<>();
                etats.add(etatFin);
                this.transitions.get(etatDebut).put(symbole, etats);
            }

        } else {

            this.transitions.put(etatDebut, null);
            Map<String, List<String>> sousTransition = new HashMap();
            sousTransition.put(symbole, null);
            List<String> etats = new ArrayList<>();
            etats.add(etatFin);
            sousTransition.put(symbole, etats);
            this.transitions.put(etatDebut, sousTransition);

        }

    }

    // =========(fonction permettant de lire la chaine  )============
    public String lireChaine(String chaineALire) {

        int nombreDeSymbole = chaineALire.length();
        String chemin_suivie = new String();
        String etat = this.initEtats.get(0);
        String etatSuivant = new String();
        chemin_suivie = "("+etat+")";

        for (int i = 0; i < nombreDeSymbole; i++) {
            // on verifie si le symbole appartien a l'alphabet
            if(this.symboles.contains(chaineALire.charAt(i)+"")){ // si oui
                // on verifie que l'etat est dans le dictionnaire des etats
                if (this.transitions.containsKey(etat)) { // si oui
                    // on verifie si l'etat est liee au symbole lu dans la chaine par une transition
                    if (this.transitions.get(etat).containsKey(chaineALire.charAt(i) + "")) { // si oui
                        etatSuivant = this.transitions.get(etat).get(chaineALire.charAt(i)+"").get(0);
                        chemin_suivie += "=="+chaineALire.charAt(i)+"=>("+etatSuivant+")";
                        // on verifie si on a fini de lire les symboles de la chaine
                        if (i == nombreDeSymbole - 1) { // si oui

                            // on verifie si nous somme a un etat finale
                            if (this.finalEtat.contains(etatSuivant)) {// si oui
                                // chemin_suivie += "=="+chaineALire.charAt(i)+"=>(\033[93m"+etatSuivant+"\033[0m)";
                                return "Chaine reconnu par l'automate \n Chemin :"+chemin_suivie +"\nOn a finit de lire la chaine et nous sommes a un etat finale";
                            }   
                            else { // si non
                                // chemin_suivie += "=="+chaineALire.charAt(i)+"=>("+etatSuivant+")";
                                return "Chaine non reconnu par l'automate \n Chemin :"+chemin_suivie +"\nOn a finit de lire la chaine et nous ne sommes pas a un etat finale";
                            }
                        } 
                        else {// si non (on n'a pas finit de lire les symbole de la chaine )
                            etat = etatSuivant;
                        }
                    }
                    else{// non (l'etat n'est pas liee au symbole lu dans la chaine par une transition )
                        return "Chaine non reconnu par l'automate \n Chemin :"+chemin_suivie +"\nIl n'y a pas de chemin pour lire la chaine "+chaineALire.charAt(i);
                    }
                } 
                else 
                { // sinon (il n'y a aucun chemin pour cet etat)
                    
                    return "Chaine non reconnu par l'automate \n Chemin :"+chemin_suivie +"\nOn a pas finir de lire la chaine et nous sommes a un etat sans transition pour lire le symbole "+chaineALire.charAt(i);
                }
            }
            else{// si non
                return "Chaine non reconnu par l'automate \n Chemin :"+chemin_suivie +"\n le symbole "+chaineALire.charAt(i) +" ne fait pas partir de l'alphabet ";
            }
        }
        return "Chaine reconnu par l'automate "+chemin_suivie;
    }

    // ===========( fonction permettant d'afficher la table des transition
    // )=========
    public void afficherMatriceTransition() {
        for (Map.Entry<String, Map<String, List<String>>> entry : this.transitions.entrySet()) {

            // for (int i = 0; i < entry.getValue().size(); i++) {
            Map<String, List<String>> s_t = entry.getValue();
            for (Map.Entry<String, List<String>> entry2 : s_t.entrySet()) {
                String nom = entry.getKey();
                String valeur = entry2.getKey();
                String nom2 = entry2.getValue().get(0);
                System.out.println(nom + " ===(" + valeur + ")==> " + nom2);
            }
            // }

        }
    }

    // ==========( methode permettant de detruir un objet )===========
    public void destructeur(){
        this.etats = new ArrayList<>();
        this.symboles = new ArrayList<>();
        this.finalEtat = new ArrayList<>();
        this.initEtats = new ArrayList<>();
        this.transitions = new HashMap<>();
    }

    // ==========( methode permettant de canoniser un automate )============
    public Tuple canoniser_AFD(Set<String> alphabet, Set<String> states, Map<String, Map<String, String>> transitions,
                                      String initial_state, Set<String> final_states) {
        // ========= Étape 1: Initialisation
        // ========= Utilisation d'une file pour parcourir les états
        Queue<String> queue = new LinkedList<>();
        queue.offer(initial_state);

        // =========== Dictionnaire pour mapper les anciens états aux nouveaux
        Map<String, String> mapping = new HashMap<>();
        mapping.put(initial_state, this.initEtats.get(0));

        // =========== Compteur pour les nouveaux noms d'états
        int counter = 1;

        // ========== Ensembles pour les nouveaux états et transitions
        Set<String> canonizedStates = new HashSet<>();
        canonizedStates.add(this.initEtats.get(0));
        Map<String, Map<String, String>> canonizedTransitions = new HashMap<>();
        Set<String> canonizedFinalStates = new HashSet<>();

        // ========== Étape 2: Parcourir les états de l'AFD
        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            String canonicalState = mapping.get(currentState);

            // ======= Initialiser les transitions pour l'état canonique actuel
            canonizedTransitions.put(canonicalState, new HashMap<>());

            // ======= Parcourir les transitions de l'état courant
            for (String symbol : alphabet) {
                if (transitions.get(currentState).containsKey(symbol)) {
                    String nextState = transitions.get(currentState).get(symbol);

                    if (!mapping.containsKey(nextState)) {
                        String newName = "q" + counter++;
                        mapping.put(nextState, newName);
                        canonizedStates.add(newName);
                        queue.offer(nextState);
                    }

                    // ========= Ajouter la transition canonisée
                    canonizedTransitions.get(canonicalState).put(symbol, mapping.get(nextState));
                }
            }

            // ====== Ajouter à l'ensemble des états finaux canonisés si applicable
            if (final_states.contains(currentState)) {
                canonizedFinalStates.add(canonicalState);
            }
        }

        // ========== État initial canonisé
        String canonizedInitialState = this.initEtats.get(0);

        return new Tuple(canonizedStates, alphabet, canonizedTransitions, canonizedInitialState, canonizedFinalStates);
    }

    public static class Tuple {
        public final Set<String> states;
        public final Set<String> alphabet;
        public final Map<String, Map<String, String>> transitions;
        public final String initialState;
        public final Set<String> finalStates;

        public Tuple(Set<String> states, Set<String> alphabet, Map<String, Map<String, String>> transitions,
                     String initialState, Set<String> finalStates) {
            this.states = states;
            this.alphabet = alphabet;
            this.transitions = transitions;
            this.initialState = initialState;
            this.finalStates = finalStates;
        }
    }
}
