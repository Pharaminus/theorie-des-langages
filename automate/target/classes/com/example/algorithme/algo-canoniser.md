La **canonisation** d'un AFD consiste à renommer ses états de manière systématique, souvent en utilisant des indices ou des nombres, pour obtenir une représentation unique de l'automate. Cela aide à comparer deux automates pour vérifier s'ils sont équivalents.

### Entrées
- `alphabet` : Ensemble des symboles de l'alphabet (`Σ`)
- `states` : Ensemble des états (`Q`)
- `transitions` : Fonction de transition (`δ`), représentée comme un dictionnaire où `transitions[q][a]` donne l'état atteint depuis `q` avec le symbole `a`
- `initial_state` : État initial (`q0`)
- `final_states` : Ensemble des états finaux (`F`)

### Sorties
- `canonized_states` : Ensemble des états canonisés (`Q'`)
- `canonized_transitions` : Fonction de transition canonisée (`δ'`)
- `canonized_initial_state` : État initial canonisé (`q0'`)
- `canonized_final_states` : Ensemble des états finaux canonisés (`F'`)

### Algorithme

```pseudo
Fonction canoniser_AFD(alphabet, states, transitions, initial_state, final_states)
    //========= Étape 1: Initialisation
    //========= Utilisation d'une file pour parcourir les états

    file = []
    file.ajouter(initial_state)
    
    //=========== Dictionnaire pour mapper les anciens états aux nouveaux

    mapping = dictionnaire vide
    mapping[initial_state] = "q0"
    
    //=========== Compteur pour les nouveaux noms d'états
    compteur = 1
    
    //========== Ensembles pour les nouveaux états et transitions
    canonized_states = {"q0"}
    canonized_transitions = dictionnaire vide
    canonized_final_states = ∅
    
    //========== Étape 2: Parcourir les états de l'AFD
    Tant que file n'est pas vide
        etat_courant = file.defiler()
        etat_canonique = mapping[etat_courant]
        
        //======= Initialiser les transitions pour l'état canonique actuel
        canonized_transitions[etat_canonique] = dictionnaire vide
        
        //======= Parcourir les transitions de l'état courant
        Pour chaque symbole ∈ alphabet
            Si symbole ∈ transitions[etat_courant]
                prochain_etat = transitions[etat_courant][symbole]
                
                Si prochain_etat ∉ mapping
                    nouveau_nom = "q" + compteur.toString()
                    mapping[prochain_etat] = nouveau_nom
                    canonized_states.ajouter(nouveau_nom)
                    file.ajouter(prochain_etat)
                    compteur = compteur + 1
                
                //========= Ajouter la transition canonisée
                canonized_transitions[etat_canonique][symbole] = mapping[prochain_etat]
        
        //====== Ajouter à l'ensemble des états finaux canonisés si applicable
        Si etat_courant ∈ final_states
            canonized_final_states.ajouter(etat_canonique)
    
    //========== État initial canonisé
    canonized_initial_state = "q0"
    
    Retourner (canonized_states, alphabet, canonized_transitions, canonized_initial_state, canonized_final_states)
```

### Description

1. **Initialisation** :
   - Utilisation d'une file pour parcourir les états à partir de l'état initial.
   - Utilisation d'un dictionnaire (`mapping`) pour mapper les anciens états aux nouveaux noms d'états canonisés.
   - Initialisation des ensembles pour les nouveaux états (`canonized_states`), transitions (`canonized_transitions`), et états finaux (`canonized_final_states`).

2. **Parcours des états** :
   - Tant que la file n'est pas vide, on traite l'état courant.
   - On initialise les transitions pour l'état canonique actuel.
   - Pour chaque symbole de l'alphabet, on vérifie les transitions de l'état courant.
   - Si l'état atteint par la transition n'a pas encore été mappé, on lui attribue un nouveau nom, l'ajoute aux états canonisés et à la file pour traitement ultérieur.
   - On ajoute la transition canonisée à l'ensemble des transitions canonisées.

3. **Gestion des états finaux** :
   - Si l'état courant est un état final, on l'ajoute à l'ensemble des états finaux canonisés.

4. **Retour des résultats** :
   - On retourne l'ensemble des états canonisés, les transitions canonisées, l'état initial canonisé, et les états finaux canonisés.

### Exemple d'utilisation

Prenons un exemple simple pour illustrer cet algorithme :

```pseudo
alphabet = {'a', 'b'}
states = {'q0', 'q1', 'q2'}
transitions = {
    '