## Fonction MinimiserAFD(AFD):
    # Étape 1 : Construire les classes d'équivalence
    Classes = CreerClassesInitiales(AFD)
    ChangementEffectue = True
    Tant que ChangementEffectue:
        ChangementEffectue = False
        NouvellesClasses = []
        Pour chaque Classe dans Classes:
            SousClasses = SubdiviserClasse(Classe, AFD)
            NouvellesClasses.extend(SousClasses)
            Si le nombre de SousClasses > 1:
                ChangementEffectue = True
        Classes = NouvellesClasses
    
    # Étape 2 : Construire l'AFD minimal
    AFDMinimal = CreerAFDMinimal(AFD, Classes)
    Retourner AFDMinimal

## Fonction CreerClassesInitiales(AFD):
    ClassesInitiales = []
    ClassesInitiales.append({q pour q dans AFD.États si q est final})
    ClassesInitiales.append({q pour q dans AFD.États si q n'est pas final})
    Retourner ClassesInitiales

## Fonction SubdiviserClasse(Classe, AFD):
    SousClasses = []
    Pour chaque Symbole dans AFD.Alphabet:
        NouvelleClasse = {q pour q dans Classe si AFD.Delta(q, Symbole) dans une même sous-classe}
        Si NouvelleClasse non vide:
            SousClasses.append(NouvelleClasse)
    Retourner SousClasses

## Fonction CreerAFDMinimal(AFD, Classes):
    AFDMinimal = Créer un nouvel AFD
    AFDMinimal.États = Classes
    AFDMinimal.ÉtatInitial = Classe contenant l'état initial de l'AFD original
    AFDMinimal.ÉtatsFinals = {Classe pour Classe dans Classes si Classe contient un état final de l'AFD original}
    Pour chaque Classe dans Classes:
        Pour chaque Symbole dans AFD.Alphabet:
            AFDMinimal.Delta(Classe, Symbole) = Classe contenant AFD.Delta(q, Symbole) pour un q dans Classe
    Retourner AFDMinimal