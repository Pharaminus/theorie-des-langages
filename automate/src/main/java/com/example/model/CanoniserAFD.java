package com.example.model;

import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

public class CanoniserAFD {
    private Map<String, Map<Character, String>> transition_table;
    private Set<String> final_states;
    private Set<String> states;
    private Character[] alphabet;

    public CanoniserAFD(Map<String, Map<Character, String>> transition_table, Set<String> final_states, Set<String> states, Character[] alphabet) {
        this.transition_table = transition_table;
        this.final_states = final_states;
        this.states = states;
        this.alphabet = alphabet;
    }

    public Map<String, Map<Character, String>> minimizeDFA() {
        Map<String, Map<Character, String>> minimized_table = new HashMap<>();

        // Étape 1 : Partition initiale
        Map<Boolean, Set<String>> partition = partitionStates();

        // Étape 2 : Raffiner la partition
        partition = refinePartition(partition);

        // Étape 3 : Construire la table de transition minimisée
        for (Set<String> block : partition.values()) {
            String representative = block.iterator().next();
            Map<Character, String> new_transitions = new HashMap<>();
            for (Character c : alphabet) {
                String next_state = transition_table.get(representative).get(c);
                for (String state : block) {
                    if (!transition_table.get(state).get(c).equals(next_state)) {
                        next_state = null;
                        break;
                    }
                }
                if (next_state != null) {
                    new_transitions.put(c, next_state);
                }
            }
            minimized_table.put(representative, new_transitions);
        }

        return minimized_table;
    }

    private Map<Boolean, Set<String>> partitionStates() {
        Map<Boolean, Set<String>> partition = new HashMap<>();
        partition.put(true, new HashSet<>(final_states));
        partition.put(false, new HashSet<>(states));
        partition.get(true).removeAll(partition.get(false));
        return partition;
    }

    private Map<Boolean, Set<String>> refinePartition(Map<Boolean, Set<String>> partition) {
        boolean changed = true;
        while (changed) {
            changed = false;
            Map<Boolean, Set<String>> new_partition = new HashMap<>();
            for (boolean is_final : partition.keySet()) {
                new_partition.put(is_final, new HashSet<>());
            }
            for (String state : states) {
                boolean found = false;
                for (boolean is_final : partition.keySet()) {
                    for (String other_state : partition.get(is_final)) {
                        if (areEquivalent(state, other_state, partition)) {
                            new_partition.get(is_final).add(state);
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
                if (!found) {
                    changed = true;
                    new_partition.get(partition.containsKey(state) && partition.get(state).contains(state)).add(state);
                }
            }
            partition = new_partition;
        }
        return partition;
    }

    private boolean areEquivalent(String s1, String s2, Map<Boolean, Set<String>> partition) {
        for (Character c : alphabet) {
            String t1 = transition_table.get(s1).get(c);
            String t2 = transition_table.get(s2).get(c);
            boolean t1_final = partition.containsKey(true) && partition.get(true).contains(t1);
            boolean t2_final = partition.containsKey(true) && partition.get(true).contains(t2);
            if (t1_final != t2_final || !partition.get(t1_final).contains(t1) || !partition.get(t2_final).contains(t2)) {
                return false;
            }
        }
        return true;
    }
    
}