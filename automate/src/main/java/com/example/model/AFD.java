package com.example.model;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.LinkedList;





public class AFD extends Automate {
    private String etatInitiale;
    private Map<String, Map<String, String>> transitions;


    public AFD(){
        super();
        this.etatInitiale = new String();
        this.transitions = new HashMap<>();
    }

    public Map<String, Map<String, String>> getTransitionsAFD() {
        return this.transitions;
    }


    public String getEtatInitiale() {
        return etatInitiale;
    }


    public void setEtatInitiale(String etatInitiale) {
        this.etatInitiale = etatInitiale;
    }

    public void ajouterEtatInitiale(String etatInitiale) {
        this.etatInitiale = etatInitiale;
    }

    public void ajouterTransition(String etatDebut, String symbole, String etatFin) {

        // Map<String, List<String> > sousTransition = new HashMap();

        if (this.transitions.containsKey(etatDebut)) {
            if (this.transitions.get(etatDebut).containsKey(symbole)) {

                this.transitions.get(etatDebut).put(symbole, etatFin);

            } else {

                this.transitions.get(etatDebut).put(symbole, etatFin);
                
            }

        } else {

            this.transitions.put(etatDebut, null);
            Map<String, String> sousTransition = new HashMap();
            sousTransition.put(symbole, etatFin);
            this.transitions.put(etatDebut, sousTransition);

        }

    }

    

    // =========(fonction permettant de lire la chaine  )============
    public String lireChaine(String chaineALire) {

        int nombreDeSymbole = chaineALire.length();
        String chemin_suivie = new String();
        String etat = this.etatInitiale;
        String etatSuivant = new String();
        chemin_suivie = "("+etat+")";

        for (int i = 0; i < nombreDeSymbole; i++) {
            // on verifie si le symbole appartien a l'alphabet
            if(this.symboles.contains(chaineALire.charAt(i)+"")){ // si oui
                // on verifie que l'etat est dans le dictionnaire des etats
                if (this.transitions.containsKey(etat)) { // si oui
                    // on verifie si l'etat est liee au symbole lu dans la chaine par une transition
                    if (this.transitions.get(etat).containsKey(chaineALire.charAt(i) + "")) { // si oui
                        etatSuivant = this.transitions.get(etat).get(chaineALire.charAt(i)+"");
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

    //=========( fonction permettant de canoniser un automate )===================
    public AFD canoniser_AFD(){
        List<String> alphabet = new ArrayList<>();
        List<String> states = new ArrayList<>();
        Map<String, Map<String, String>> transitions = new HashMap();
        String initial_state = new String();
        List<String> final_states = new ArrayList<>();
        alphabet = this.symboles;
        states = this.etats;
        transitions = this.transitions;
        initial_state = this.etatInitiale;
        final_states = this.finalEtat;
        // ========= Étape 1: Initialisation
        // ========= Utilisation d'une file pour parcourir les états
        Queue<String> queue = new LinkedList<>();
        queue.offer(initial_state);

        // =========== Dictionnaire pour mapper les anciens états aux nouveaux
        Map<String, String> mapping = new HashMap<>();
        mapping.put(initial_state, this.etatInitiale);

        // =========== Compteur pour les nouveaux noms d'états
        int counter = 1;

        // ========== Ensembles pour les nouveaux états et transitions
        List<String> canonizedStates = new ArrayList<>();
        canonizedStates.add(this.etatInitiale);
        Map<String, Map<String, String>> canonizedTransitions = new HashMap<>();
        List<String> canonizedFinalStates = new ArrayList<>();

        // ========== Étape 2: Parcourir les états de l'AFD
        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            String canonicalState = mapping.get(currentState);

            // ======= Initialiser les transitions pour l'état canonique actuel
            canonizedTransitions.put(canonicalState, new HashMap<>());

            // ======= Parcourir les transitions de l'état courant
            for (String symbol : alphabet) {
                if(transitions.containsKey(currentState)){
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
            }

            // ====== Ajouter à l'ensemble des états finaux canonisés si applicable
            if (final_states.contains(currentState)) {
                canonizedFinalStates.add(canonicalState);
            }
        }   

        // ========== État initial canonisé
        String canonizedInitialState = this.etatInitiale;

        AFD automateAfd = new AFD();
        automateAfd.setEtatInitiale(canonizedInitialState);
        automateAfd.setSymboles(alphabet);
        automateAfd.setFinalEtat(canonizedFinalStates);
        automateAfd.setEtatInitiale(canonizedInitialState);
        automateAfd.setTransitionsAFD(canonizedTransitions);

        return automateAfd;
    }
    
    public void setTransitionsAFD(Map<String, Map<String, String>> transitions) {
        this.transitions = transitions;
    }

    public AFD minimize() {
        
        Set<String> alphabet = new  HashSet<>(this.symboles);

        // Étape 1 : Construire les classes d'équivalence initiales
        List<Set<String>> classes = createInitialClasses();

        boolean changed = true;
        while (changed) {
            changed = false;
            List<Set<String>> newClasses = new ArrayList<>();

            for (Set<String> c : classes) {
                Map<Map<String, String>, Set<String>> partition = new HashMap<>();
                for (String state : c) {
                    Map<String, String> transitionsMap = new HashMap<>();
                    for (String symbol : alphabet) {
                        String target = transitions.getOrDefault(state, Collections.emptyMap()).get(symbol);
                        transitionsMap.put(symbol, findClass(target, classes));
                    }
                    partition.putIfAbsent(transitionsMap, new HashSet<>());
                    partition.get(transitionsMap).add(state);
                }
                newClasses.addAll(partition.values());
                if (partition.size() > 1) {
                    changed = true;
                }
            }
            classes = newClasses;
        }

        // Étape 2 : Construire l'AFD minimal à partir des classes d'équivalence
        return createMinimizedAFD(classes);
    }

    private List<Set<String>> createInitialClasses() {

        Set<String> states = new HashSet<>(this.etats);
        Set<String> acceptStates = new HashSet<>(this.finalEtat);

        Set<String> finalStates = new HashSet<>(acceptStates);
        Set<String> nonFinalStates = states.stream().filter(s -> !acceptStates.contains(s)).collect(Collectors.toSet());

        List<Set<String>> initialClasses = new ArrayList<>();
        if (!finalStates.isEmpty()) {
            initialClasses.add(finalStates);
        }
        if (!nonFinalStates.isEmpty()) {
            initialClasses.add(nonFinalStates);
        }
        return initialClasses;
    }

    private String findClass(String state, List<Set<String>> classes) {
        
        if (state == null) {
            return null;
        }
        for (Set<String> c : classes) {
            if (c.contains(state)) {
                return c.iterator().next(); // Return a representative element of the class
            }
        }
        return null;
    }

    private AFD createMinimizedAFD(List<Set<String>> classes) {
        String startState = this.etatInitiale;
        Set<String> acceptStates = new HashSet<>(this.finalEtat);
        Set<String> alphabet = new HashSet<>(this.symboles);

        Set<String> newStates = classes.stream().map(c -> c.iterator().next()).collect(Collectors.toSet());
        String newStartState = findClass(startState, classes);
        Set<String> newAcceptStates = classes.stream()
                .filter(c -> c.stream().anyMatch(acceptStates::contains))
                .map(c -> c.iterator().next())
                .collect(Collectors.toSet());

        Map<String, Map<String, String>> newTransitions = new HashMap<>();
        for (Set<String> c : classes) {
            String representative = c.iterator().next();
            newTransitions.putIfAbsent(representative, new HashMap<>());
            for (String symbol : alphabet) {
                String target = transitions.getOrDefault(representative, Collections.emptyMap()).get(symbol);
                String newTarget = findClass(target, classes);
                if (newTarget != null) {
                    newTransitions.get(representative).put(symbol, newTarget);
                }
            }
        }

        AFD automateAfd = new AFD();

        List<String> etats = new ArrayList<>(newStates);
        List<String> symbole = new ArrayList<>(alphabet);
        List<String> etatfinale = new ArrayList<>(newAcceptStates);

        System.out.println(etatfinale);
        
        automateAfd.setEtats(etats);
        automateAfd.setSymboles(symbole);
        automateAfd.setFinalEtat(etatfinale);
        automateAfd.setEtatInitiale(newStartState);
        automateAfd.setTransitionsAFD(newTransitions);
        return automateAfd;
    }

    
    // ==========( methode permettant de ramener les transitions en chaine de character )==========
    // public String transitionsToString() {
        
    //     StringBuilder sb = new StringBuilder();
    //     for (Map.Entry<String, Map<String, String>> entry : this.transitions.entrySet()) {
    //         String fromState = entry.getKey();
    //         for (Map.Entry<String, String> trans : entry.getValue().entrySet()) {
    //             sb.append(fromState)
    //                     .append("-( ")
    //                     .append(trans.getKey())
    //                     .append(" )->")
    //                     .append(trans.getValue())
    //                     .append("\n");
    //         }
    //     }
    //     return sb.toString().trim();
    // }

    @Override
    public String toString() {
        return "États: " + this.etats + "\n" +
                "Alphabet: " + this.symboles + "\n" +
                "Transitions: " + this.getTransitionsAFD() + "\n" +
                "État initial: " + this.etatInitiale + "\n" +
                "États finales: " + this.etatInitiale;
    }

    

    public static Map<String, Map<String, String>> parseTransitions(String transitionsString) {
        Map<String, Map<String, String>> transitions = new HashMap<>();

        String[] transitionParts = transitionsString.split("Transitions:\\{");
        if (transitionParts.length > 1) {
            String transitionsBody = transitionParts[1].replaceAll("\\}", "");
            String[] transitionBlocks = transitionsBody.split(", ");

            for (String transitionBlock : transitionBlocks) {
                String[] transitionParts2 = transitionBlock.split("=");
                if (transitionParts2.length > 1) {
                    String state = transitionParts2[0].trim();
                    String transitionsString2 = transitionParts2[1].trim();

                    Map<String, String> stateTransitions = parseStateTransitions(transitionsString2);
                    transitions.put(state, stateTransitions);
                }
            }
        }

        return transitions;
    }


    // ===========( methode permettant de recuperer la transition de la chaine )===========
    private static Map<String, String> parseStateTransitions(String transitionsString) {
        Map<String, String> stateTransitions = new HashMap<>();

        String[] transitionPairs = transitionsString.split(", ");
        for (String transitionPair : transitionPairs) {
            String[] parts = transitionPair.split("=");
            if (parts.length > 1) {
                String event = parts[0].trim();
                String targetState = parts[1].trim();
                stateTransitions.put(event, targetState);
            }
        }

        return stateTransitions;
    }

    public String toString2() {
        StringBuilder sb = new StringBuilder();
    
        // États
        sb.append(String.join(",", this.etats));
        sb.append("\n");
    
        // Alphabet (non utilisé directement ici, donc ignoré)
        sb.append(String.join(",", this.symboles));
        sb.append("\n");
    
        // Transitions
        for (Map.Entry<String, Map<String, String>> entry : this.transitions.entrySet()) {
            String fromState = entry.getKey();
            for (Map.Entry<String, String> transition : entry.getValue().entrySet()) {
                String input = transition.getKey();
                String toState = transition.getValue();
                sb.append(fromState).append(",").append(input).append("->").append(toState).append("\n");
            }
        }
    
        // État initial
        sb.append(this.etatInitiale).append("\n");
    
        // États finaux
        sb.append(String.join(",", this.finalEtat));
    
        return sb.toString();
    }

    // ==========( fonction permettant de ramener un automate ecrit en string en un objet )===========
    public void fromString(String data) {
        // Split the input data into sections
        String[] sections = data.split("\n");

        // Parse the states (first section)
        this.etats = new ArrayList<>(Arrays.asList(sections[0].split(",")));

        // Parse the alphabet (second section) - not directly used, so it's commented out
        this.symboles = new ArrayList<>(Arrays.asList(sections[1].split(",")));
       
        for (int i = 2; i < sections.length - 2; i++) {
            String[] parts = sections[i].split("->");
            String[] left = parts[0].split(",");
            String fromState = left[0];
            String input = left[1];
            String toState = parts[1];
            this.transitions.putIfAbsent(fromState, new HashMap<>());
            this.transitions.get(fromState).put(input, toState);
        }
        
        // Parse the initial state (fourth section)
        this.etatInitiale = sections[sections.length - 2];

        // Parse the final states (fifth section)
        this.finalEtat = new ArrayList<>(Arrays.asList(sections[sections.length - 1].split(",")));

    }
}
    