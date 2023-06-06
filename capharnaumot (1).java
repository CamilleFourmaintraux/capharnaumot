import extensions.CSVFile;
import extensions.File;
import java.io.*;
class capharnaumot extends Program{ 

    /*
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ||||||||||||>>Initialisation et affichage<<||||||||||||||||||||||||||||||||||||||||||||||||||||
    ///////////////////////////////////////////////////////////////////////////////////////////////
    */
    
    // Trouve la plus grande chaîne de caractères d'un fichier CSV et renvoie sa taille.
    // Fonction intestable car le contenu du fichier CSV est changeant
    int max(CSVFile f){

        int max=0;
        String donnees;
        for (int l=0; l<rowCount(f); l+=1){

            for (int c=0; c<columnCount(f); c+=1){

                donnees=getCell(f,l,c);
                if (length(donnees)+2>max){

                    max = length(donnees)+2;

                }

            }

        }
        return max;

    }

    /////// Tests pour la fonction aléatoire pioche ///////
    void testEstVideInt(){

        assertTrue(estVideInt(new int[]{-1,-1,-1,-1}));
        assertFalse(estVideInt(new int[]{-1,-1,4,-1}));

    }
    
    // Renvoie true si un tableau d'entier ne contient que des -1.
    boolean estVideInt(int[] tab){

        for (int i=0; i<length(tab); i+=1){

            if (tab[i]!=-1){

                return false;

            }

        }
        return true;

    }

    // Vérifie que tous les tirages possibles de 0 à 9 se font au moins une fois en 100 000 tentatives.
    void testPioche(){

        int[] tab = new int[]{0,1,2,3,4,5,6,7,8,9};
        int tentatives=100000;
        while(!estVideInt(tab)||tentatives>0){

            tab[pioche(10)]=-1;
            tentatives-=1;

        }
        assertTrue(estVideInt(tab));

    }
    /////// Fin des Tests ///////

    // tire un entier au hasard entre 0 et max exlus
    int pioche(int max){

        return (int) (random()*max);

    }

    /////// FONCTIONS D'AFFICHAGE ///////
    // Affichage de la grille de jeu pour debug
    void afficheDebug(Mot[][]grille, int MAX){

        String chaine="";
        // Affichage des chiffres des coordonnées
        for (int c=0; c<length(grille,2); c+=1){

            chaine="     "+c;
            chaine=ajoutEspace(chaine,MAX);
            print(chaine);

        }
        println();
        for (int c=0; c<length(grille,2); c+=1){

            chaine="-";
            chaine=ajoutTiret(chaine,MAX);
            print(chaine);

        }
        println();
        // Affichage des lettres des coordonnées, de la barre verticale, et du contenu de la grille
        for (int l=0; l<length(grille,1); l+=1){//l=1 & length-1
        
            print((char)('A'+l)+"|");
            for (int c=0; c<length(grille,2); c+=1){//c=1 & length-1

                chaine="";
                if (grille[l][c] != null){

                    chaine = toStringCategorie(grille[l][c])+" ";

                } else {

                    chaine = " - - - "; // Cas d'une case vide de la grille

                }
                // Ajustement de la taille des mots par l'ajout d'espace afin qu'ils soient tous aligné et de la même taille.
                chaine=ajoutEspace(chaine,MAX);
                print(chaine);

            }
            // Saut entre chaque ligne pour améliorer la lisibilité de l'affichage.
            println("\n |");

        }
        
    }

    // Affichage de la grille de jeu
    void affiche(Mot[][]grille, int MAX){

        clearScreen();
        String chaine="";
        print("    ");
        for (int c=0; c<length(grille,2); c+=1){

            chaine="―";
            chaine=ajoutTiret(chaine,MAX);
            print(chaine);

        }
        println();
        print("    ");
        // Affichage des chiffres des coordonnées
        for (int c=0; c<length(grille,2); c+=1){

            chaine="|        "+c;
            chaine=ajoutEspace(chaine,MAX);
            print(chaine);

        }
        println("|");
        print("    ");
        for (int c=0; c<length(grille,2); c+=1){

            chaine="―";
            chaine=ajoutTiret(chaine,MAX);
            print(chaine);

        }
        //println();
        // Affichage des lettres des coordonnées, de la barre verticale, et du contenu de la grille
        println();
        println("    | ― |");
        for (int l=0; l<length(grille,1); l+=1){//l=1 & length-1
            print("    | ");
            print((char)('A'+l)+" |");
            for (int c=0; c<length(grille,2); c+=1){//c=1 & length-1

                chaine="";
                if (grille[l][c] != null){

                    chaine = " "+toStringMot(grille[l][c])+" ";
                    
                } else {

                    chaine = " - - - "; // Cas d'une case vide de la grille

                }
                // Ajustement de la taille des mots par l'ajout d'espace afin qu'ils soient tous aligné et de la même taille.
                chaine=ajoutEspace(chaine,MAX);
                print(chaine);

            }
            // Saut entre chaque ligne pour améliorer la lisibilité de l'affichage.
            println();
            println("    | ― |");

        }
        println();

    }
    /////// - - - ///////

    // Fonction intestable car dépend du fichier CSV qui est changeant
    Mot newMot(int colonne, int ligne, CSVFile f){

        Mot mot = new Mot();
        mot.chaine = getCell(f, ligne, colonne);
        mot.categorie = getCell(f, 0, colonne);
        return mot;

    }

    // Fonction intestable car aléatoire
    Mot newMotAlea(CSVFile f){

        Mot mot = newMot(pioche(columnCount(f)),pioche(rowCount(f)-1)+1,f);
        return mot;

    }

    void testToString(){

        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        assertEquals("[pomme]",toStringMot(mot1));
        assertEquals("[FRUITS]",toStringCategorie(mot1));

    }

    //Convertit le contenu d'un Mot en String.
    String toStringMot(Mot mot){

        return "["+mot.chaine+"]";

    }
    //Convertit la catégorie d'un Mot en String.
    String toStringCategorie(Mot mot){

        return "["+mot.categorie+"]";

    }

    void testCreerGrille(){

        assertTrue(length(new Mot[5][4],1)==length(creerGrille(5,4),1));
        assertTrue(length(new Mot[4][5],2)==length(creerGrille(4,5),2));

    }

    // Cree une grille de mot en fonction de deux entiers (la longueur et la largeur) passé en paramètre.
    Mot[][] creerGrille(int longueur, int largeur){

        return new Mot[longueur][largeur];

    }

    // Initialise la grille avec des mots aléatoire
    void initialiserGrille(Mot[][] grille, CSVFile f, int MAX){

        for (int l = 0; l<length(grille,1); l+=1){

            for (int c = 0; c<length(grille,2); c+=1){

                grille[l][c]=newMotAlea(f);

            }
        }
        remplirDebug(grille,f,MAX);

    }

    void testAjoutEspace(){

        assertEquals(ajoutEspace("lasagnes",11),"lasagnes   ");
        assertEquals(ajoutEspace("miam-miam",10),"miam-miam ");
        assertEquals(ajoutEspace("yumi-yumi",9),"yumi-yumi");
        assertEquals(ajoutEspace("",7),"       ");

    }

    // Ajoute des espaces à un mot jusqu'à qu'il soit de la bonne taille
    String ajoutEspace(String mot, int taille){

        while(length(mot)<taille){

            mot=mot+" ";

        }
        return mot;

    }

    void testAjoutTiret(){

        assertEquals(ajoutTiret("_",10),"_―――――――――");
        assertEquals(ajoutTiret("pizza_miam-miam",15),"pizza_miam-miam");
        assertEquals(ajoutTiret("",7),"―――――――");

    }

    // Ajoute des tirets à un mot jusqu'à qu'il soit de la bonne taille
    String ajoutTiret(String mot, int taille){
        while(length(mot)<taille){
            mot=mot+"―";
        }
        return mot;
    }
    
    // Affiche le contenu d'un fichier.
    void afficherFichier(String chemin){

		File unTexte = newFile(chemin);

		//Stockage dans une variable de la ligne suivante dans le fichier

		while(ready(unTexte)){
			//affichage du contenu de la ligne suivante
			println(readLine(unTexte));
		}
    }


    /*
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ||||||||||||>>saisie<<|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    ///////////////////////////////////////////////////////////////////////////////////////////////
    */

    

    /// Crédit pour ce test et cette fonction à Mr Delecroix ///
    void testCasesAdjacentes(){

        assertFalse(casesAdjacentes("A1A1"));
        assertFalse(casesAdjacentes("A1E7"));
        assertFalse(casesAdjacentes("A1B2"));
        assertTrue(casesAdjacentes("A1A2"));
        assertTrue(casesAdjacentes("A1B1"));

    }

    boolean casesAdjacentes(String coor){

	    //distance de 1 sur les lignes et colonnes cumulées
	    return abs(charAt(coor,0) - charAt(coor,2)) + abs(charAt(coor,1) - charAt(coor,3)) == 1;

    }
    /// Merci pour l'optimisation =) ///

    // Pas de test possible
    String saisie(int LONGUEUR, int LARGEUR){

        String entree;
        do{

            entree = readString();

        } while(!saisieValideMSG(entree,LONGUEUR,LARGEUR));
        return entree;
    }

    void testSaisieValide(){

        assertFalse(saisieValide("B1",5,5));
        assertFalse(saisieValide(" H3IIO_w0Rld",5,5));
        assertFalse(saisieValide("6641",5,5));
        assertFalse(saisieValide("A6B1",5,5));
        assertFalse(saisieValide("A0G1",5,5));
        assertFalse(saisieValide("A5E1",5,5));
        assertTrue(saisieValide("A0A1",5,5));
        assertTrue(saisieValide("A2B2",5,5));

    }

    // Renvoie true si la saisie est valide, sinon renvoie false et print un msg d'erreur.
    boolean saisieValideMSG(String saisie, int LONGUEUR, int LARGEUR){
        if(!equals(saisie,"SAVE")){

            if(length(saisie)!=4){

                println("Saisie invalide : Format incorrect. Doit être: LCLC avec L->lettre et C->chiffre, Reçu: "+saisie);
                return false;

            }else if ((charAt(saisie,0)<'A' || charAt(saisie,0)-'A'>LONGUEUR) || (charAt(saisie,2)<'A' || charAt(saisie,2)-'A'>LONGUEUR)){

                println("Saisie invalide : Lignes incorrectes. Doivent êtres entre [A,"+(char)('A'+LONGUEUR)+"]. Reçues: "+charAt(saisie,0)+" & "+charAt(saisie,2));
                return false;

            }else if ((charAt(saisie,1)<'0' || charAt(saisie,1)-'0'>LARGEUR) || (charAt(saisie,3)<'0' || charAt(saisie,3)-'0'>LARGEUR)){

                println("Saisie invalide : Colonnes incorrectes. Doivent êtres entre [0,"+LARGEUR+"]. Reçues: "+(charAt(saisie,1)-'0')+" & "+(charAt(saisie,3)-'0'));
                return false;

            }else if (!casesAdjacentes(saisie)){

                println("Saisie invalide : Les cases sélectionnés ne sont pas adjacentes. :/");
                return false;

            }else{

                println("Saisie valide");
                return true;

            }

        }else{

            return true;

        }
        
    }

    // Fonction parfaitement identique à saisieValideMSG mais sans le print de messages d'erreur.
    boolean saisieValide(String saisie, int LONGUEUR, int LARGEUR){

        if(!equals(saisie,"SAVE")){

            if(length(saisie)!=4){

                return false;

            }else if ((charAt(saisie,0)<'A' || charAt(saisie,0)-'A'>LONGUEUR) || (charAt(saisie,2)<'A' || charAt(saisie,2)-'A'>LONGUEUR)){

                return false;

            }else if ((charAt(saisie,1)<'0' || charAt(saisie,1)-'0'>LARGEUR) || (charAt(saisie,3)<'0' || charAt(saisie,3)-'0'>LARGEUR)){

                return false;

            }else if (!casesAdjacentes(saisie)){

                return false;

            }else{

                return true;

            }

        }else{

            return true;

        }
        
    }

    // Demande a l'utilisateur d'entrer ses initials en 3 caractères majuscules.
    String saisieNomJoueur(){

        String saisie;
        boolean valide =true;
        do{

            if(!valide){

                println();
                println("Pseudo invalide, entrez vos initials (ex : ADM)");
                print("=====> ");

            }
            saisie = readString();
            valide = saisieNomJoueurValide(saisie);

        }while(!valide);
        return saisie;
    }

    boolean saisieNomJoueurValide(String saisie){
        boolean longueurValide = length(saisie) == 3;
        if(longueurValide){
            
            return charAt(saisie,0) >='A' && charAt(saisie,0) <='Z' && charAt(saisie,1) >='A' && charAt(saisie,1) <='Z' && charAt(saisie,2) >='A' && charAt(saisie,2) <='Z';

        }else{

            return longueurValide;

        }

    }

    void testSaisieNomJoueurValide(){
        
        assertTrue(saisieNomJoueurValide("CAM"));
        assertTrue(saisieNomJoueurValide("AAA"));
        assertTrue(saisieNomJoueurValide("ZZZ"));
        assertTrue(saisieNomJoueurValide("CAM"));
        assertFalse(saisieNomJoueurValide(""));
        assertFalse(saisieNomJoueurValide("CAMI"));
        assertFalse(saisieNomJoueurValide("cam"));
        assertFalse(saisieNomJoueurValide("CAM "));
        assertFalse(saisieNomJoueurValide("c AM"));
        assertFalse(saisieNomJoueurValide("CM"));

    }


    void testStringToInt(){

        assertEquals(400,StringToInt("400"));
        assertEquals(542,StringToInt("542"));
        assertEquals(0,StringToInt(""));
        assertEquals(0,StringToInt("0"));
        assertEquals(0,StringToInt("000"));
        assertEquals(7,StringToInt("007"));
        assertEquals(100000,StringToInt("100000"));
        assertNotEquals(10,StringToInt("11"));

    }

    // Convertit un nombre en String en Int. /!\ Les caractères doivent donc être compris entre 0 et 9.
    int StringToInt(String nombre){

        int total=0;
        for(int i=0; i<length(nombre);i+=1){

            total=total+(int)((charAt(nombre,i)-'0')*(pow(10.0,length(nombre)-(i+1))));

        }
        if(length(nombre) > 0){

            if(charAt(nombre,0) == '-'){

                return -total;

            }else{

                return total;

            }

        }else{

            return total;

        }
        
        
    }


    // Deconstruit une saisie valide en String et la sauvegarde dans un tableau d'entier passé en paramètre.
    void saisieInTabInt(String saisie, int[] tab){
        tab[0]=charAt(saisie,0)-'A';
        tab[1]=charAt(saisie,1)-'0';
        tab[2]=charAt(saisie,2)-'A';
        tab[3]=charAt(saisie,3)-'0';
    }


    /*
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ||||||||||||>>comportement grille<<||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    ///////////////////////////////////////////////////////////////////////////////////////////////
    */
    

    void testVertical(){
        Mot[][] grilleTest = new Mot[6][6];
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        for (int l=0; l<length(grilleTest,1);l+=1){
            for (int c=0; c<length(grilleTest,2);c+=1){
                grilleTest[l][c]=mot1;
            }
        }
        grilleTest[0][1]=mot2;
        assertEquals(6,vertical(grilleTest,0,0));
        assertEquals(1,vertical(grilleTest,0,1));
    }

    int vertical(Mot[][] grille, int l, int c){
        int portee = 1;
        while(l<length(grille,1)-1){
            if (grille[l][c]==null || grille[l+1][c]==null){
                break;
            }else if(equals(grille[l][c].categorie,grille[l+1][c].categorie)){
                portee+=1;
                l+=1;
            } else {
                break;
            }
        }
        return portee;
    }

    void testHorizontal(){
        Mot[][] grilleTest = new Mot[6][6];
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        for (int l=0; l<length(grilleTest,1);l+=1){
            for (int c=0; c<length(grilleTest,2);c+=1){
                grilleTest[l][c]=mot1;
            }
        }
        grilleTest[0][1]=mot2;
        assertEquals(4,horizontal(grilleTest,0,2));
        assertEquals(1,horizontal(grilleTest,0,0));
    }

    int horizontal(Mot[][] grille, int l, int c){
        int portee = 1;
        while(c<length(grille,2)-1){
            if (grille[l][c]==null || grille[l][c+1]==null){
                break;
            }else if(equals(grille[l][c].categorie,grille[l][c+1].categorie)){
                portee+=1;
                c+=1;
            } else {
                break;
            }
        }
        return portee;
    }

    void testAlignement(){
        Mot[][] grilleTest = new Mot[6][6];
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        for (int l=0; l<length(grilleTest,1);l+=1){
            for (int c=0; c<length(grilleTest,2);c+=1){
                grilleTest[l][c]=mot1;
            }
        }
        grilleTest[0][1]=mot2;
        grilleTest[3][0]=mot2;
        alignement(grilleTest,0,0);
        alignement(grilleTest,0,1);
        alignement(grilleTest,4,4);
        ///////////////////////////////////////////////////////////////A CORRIGER - TEST INVALIDE/////////////////////////////////////////
    }

    //Vérifie si une case entrée en paramètre est aligné.
    //Cette case doit faire partie de la grille, et ne doit pas être adjacente au bord droit ou au bord bas de la grille.
    void alignement(Mot[][]grille, int l, int c){
        int portee;
        portee = horizontal(grille,l,c);
        if(portee>2){
            for(int i=0; i<portee; i+=1){
                grille[l][c+i]=null;
            }
        }
        portee = vertical(grille,l,c);
        if(portee>2){
            for(int i=0; i<portee; i+=1){
                grille[l+i][c]=null;
            }
        }
    }

    // Même fonction que alignement mais avec la gestion des points en plus.
    int alignementAvecScore(Mot[][]grille, int l, int c, int score){
        int portee;
        portee = horizontal(grille,l,c);
        if(portee>2){
            for(int i=0; i<portee; i+=1){
                grille[l][c+i]=null;
            }
            return score+=portee*100;
        }
        portee = vertical(grille,l,c);
        if(portee>2){
            for(int i=0; i<portee; i+=1){
                grille[l+i][c]=null;
            }
            return score+=portee*100;
        }
        return score;
    }

    void testStable(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        Mot[][] grilleTest = new Mot[][]{{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot1},{mot2,mot1,mot2,mot1}};
        assertTrue(stable(grilleTest,1,1));
        assertFalse(stable(grilleTest,1,3));
    }

    // Renvoie true si la case aux coordonnées l, c de la grille passé en paramètre est instable (si un alignement est détecté), sinon renvoie false.
    boolean stable(Mot[][] grille, int l, int c){
        if (horizontal(grille, l, c)>2 || vertical(grille, l, c)>2){
            return false;
        } else{
            return true;
        }
    }

    void testStables(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        Mot[][] grilleTest = new Mot[][]{{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1}};
        assertTrue(stables(grilleTest));
        grilleTest[2][3]=mot1;
        assertFalse(stables(grilleTest));
    }

    // Renvoie true si la grille passé en paramètre est instable (un alignement y est détecté), sinon renvoie false.
    boolean stables(Mot[][] grille){
        for (int l = 0; l<length(grille,1); l+=1){
            for (int c=0; c<length(grille,2); c+=1){
                if (!stable(grille,l,c)){
                    return false;
                }
            }
        }
        return true;
    }

    void testStabiliser(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        Mot[][] grilleTest = new Mot[][]{{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1}};
        assertArrayEquals(new Mot[][]{{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1}},grilleTest);
        grilleTest[2][3]=mot1;
        assertArrayEquals(new Mot[][]{{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot1},{mot2,mot1,mot2,mot1}},grilleTest);
        stabiliser(grilleTest);
        assertTrue(grilleTest[0][3]==mot2);
        assertTrue(grilleTest[1][3]==null);
        assertTrue(grilleTest[2][3]==null);
        assertTrue(grilleTest[3][3]==null);
        assertFalse(grilleTest[2][1]==mot1);
        assertFalse(grilleTest[1][2]==null);
        assertFalse(grilleTest[2][3]==mot1);
    }

    //Tant que la grille n'est pas stable(sans alignements), alors cette fonction parcoure la grille et fait tous les alignements possibles.
    void stabiliser(Mot[][] grille){
        while(!stables(grille)){
            for (int l = 0; l<length(grille,1); l+=1){
                for (int c=0; c<length(grille,2); c+=1){
                    alignement(grille,l,c);
                }
            }
        }
    }

    // Même fonction que la précédente mais avec la gestion du score en plus.
    int stabiliserAvecScore(Mot[][] grille, int score){
        while(!stables(grille)){
            for (int l = 0; l<length(grille,1); l+=1){
                for (int c=0; c<length(grille,2); c+=1){
                    score=alignementAvecScore(grille,l,c,score);
                }
            }
        }
        return score;
    }

    void testEstFlottante(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        Mot[][] grilleTest = new Mot[][]{{null,mot2,mot1,mot2},{mot2,mot1,mot2,null},{null,mot2,mot1,mot1},{null,null,mot2,mot1}};
        assertFalse(estFlottante(grilleTest,0,0));
        assertFalse(estFlottante(grilleTest,2,0));
        assertFalse(estFlottante(grilleTest,0,1));
        assertFalse(estFlottante(grilleTest,3,2));
        assertFalse(estFlottante(grilleTest,3,3));
        assertTrue(estFlottante(grilleTest,0,3));
        assertTrue(estFlottante(grilleTest,1,0));
        assertTrue(estFlottante(grilleTest,2,1));
    }

    // Return true si la case en dessous de celle passé en paramètre est égal à null.
    // Renvoie directement false si la case passé en paramètre est égal à null ou si elle est situé tout en bas de la grille.
    boolean estFlottante(Mot[][]grille,int l, int c){
        if (grille[l][c]==null || l>=length(grille,1)-1){
            return false;
        }else{
            return (grille[l+1][c]==null);
        }
    }

    void testEchange(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        Mot[][] grilleTest = new Mot[][]{{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1}};
        echange(grilleTest,0,1,1,1);
        assertArrayEquals(new Mot[][]{{mot1,mot1,mot1,mot2},{mot2,mot2,mot2,mot1},{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1}},grilleTest);
        echange(grilleTest,3,1,3,2);
        echange(grilleTest,1,1,3,3);
        assertArrayEquals(new Mot[][]{{mot1,mot1,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot2},{mot2,mot2,mot1,mot2}},grilleTest);
    }

    void echange(Mot[][] grille, int l1, int c1, int l2, int c2){
        Mot temp = grille[l1][c1];
        grille[l1][c1]=grille[l2][c2];
        grille[l2][c2]=temp;
    }

    void testChute(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        Mot[][] grilleTest = new Mot[][]{{mot1,mot2,mot1,mot2},{mot2,mot1,mot2,mot1},{mot1,mot2,mot1,mot2},{mot2,mot1,null,mot1}};
        chute(grilleTest,2,2);
        assertTrue(grilleTest[3][2]==mot1);
        assertTrue(grilleTest[2][2]==null);
        assertTrue(grilleTest[3][3]==mot1);
        assertTrue(grilleTest[1][2]==mot2);
        assertFalse(grilleTest[3][1]==null);
        assertFalse(grilleTest[3][2]==null);
    }

    void chute(Mot[][] grille, int l, int c){
        if (estFlottante(grille,l,c)){
            echange(grille,l,c,l+1,c);
        }
    }

    void testGravite(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        Mot mot2 = new Mot();
        mot2.chaine = "violon";
        mot2.categorie = "INSTRUMENTS";
        Mot mot3 = new Mot();
        mot3.chaine = "marteau";
        mot3.categorie = "OUTILS";
        Mot[][] grilleTest = new Mot[][]{{mot1,mot2,mot1,null},{mot2,mot1,null,mot1},{null,null,mot1,mot2},{mot3,mot1,null,null}};
        graviteDebug(grilleTest,mot3);
        assertTrue(grilleTest[0][0]==null);
        assertTrue(grilleTest[1][0]==mot1);
        assertTrue(grilleTest[2][0]==mot2);
        assertTrue(grilleTest[3][0]==mot3);
        assertTrue(grilleTest[0][3]==mot3);
        assertTrue(grilleTest[1][3]==null);
        assertTrue(grilleTest[2][3]==mot1);
        assertTrue(grilleTest[3][3]==mot2);
        assertFalse(grilleTest[0][2]==mot2);
        assertFalse(grilleTest[0][3]==mot1);
        assertFalse(grilleTest[1][2]==null);
        assertFalse(grilleTest[2][3]==mot2);
    }


    // Cette fonction applique chute à tous les éléments d'ne grille, en partant de la fin et en remontant.
    // Une fois en haut de la grille, si la case == null, génère un nouveau mot aléatoire.
    void gravite(Mot[][] grille, CSVFile f){
        for (int l = length(grille,1)-2; l>-1; l-=1){
            for (int c=length(grille,2)-1; c>-1; c-=1){
                if (l==0 && grille[l][c]==null){
                    grille[l][c]=newMotAlea(f);
                }else{
                    chute(grille,l,c);
                }
            }
        }
    }

    // Fonction parfaitement similaire à graviter() mais génère avec un mot prédéfini plutot que aléatoirement avec un fichier CSV.
    void graviteDebug(Mot[][] grille, Mot mot){
        for (int l = length(grille,1)-2; l>-1; l-=1){
            for (int c=length(grille,2)-1; c>-1; c-=1){
                if (l==0 && grille[l][c]==null){
                    grille[l][c]=mot;
                }else{
                    chute(grille,l,c);
                }
            }
        }
    }

    void testEstVide(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        assertTrue(estVide(new Mot[][]{{null,null,null},{null,null,null},{null,null,null},{null,null,null}}));
        assertFalse(estVide(new Mot[][]{{null,null,null},{mot1,null,null},{null,null,null},{null,null,null}}));
    }

    // Retourne true si la tableau de ot passé en paramètre est vide (ne contient que des null)
    boolean estVide(Mot[][]grille){
        for (int l = 0; l<length(grille,1); l+=1){
            for (int c=0; c<length(grille,2); c+=1){
                if (grille[l][c]!=null){
                    return false;
                }
            }
        }
        return true;
    }
    
    void testEstPlein(){
        Mot mot1 = new Mot();
        mot1.chaine = "pomme";
        mot1.categorie = "FRUITS";
        assertTrue(estPlein(new Mot[][]{{mot1,mot1,mot1},{mot1,mot1,mot1},{mot1,mot1,mot1},{mot1,mot1,mot1}}));
        assertFalse(estPlein(new Mot[][]{{null,null,null},{mot1,null,null},{null,null,null},{null,null,null}}));
    }

    // Retourne true si le tableau de Mot passé en paramètre est plein (contient aucun null).
    boolean estPlein(Mot[][]grille){
        for (int l = 0; l<length(grille,1); l+=1){
            for (int c=0; c<length(grille,2); c+=1){
                if (grille[l][c]==null){
                    return false;
                }
            }
        }
        return true;
    }

    int remplir(Mot[][] grille, CSVFile fichier, int MAX, int score){
        while(!stables(grille)){
            score=stabiliserAvecScore(grille,score);
            affiche(grille,MAX);
            println('\n');
            delay(650);
            while(!estPlein(grille)){
                gravite(grille,fichier);
                affiche(grille,MAX);
                println('\n');
                delay(650);
            }
        }
        return score;
    }

    // Même fonction que la précédente mais sans affichage et sans ajout de points.
    void remplirDebug(Mot[][] grille, CSVFile fichier, int MAX){
        while(!stables(grille)){
            stabiliser(grille);
            while(!estPlein(grille)){
                gravite(grille,fichier);
            }
        }
    }

    
    /*
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ||||||||||||>>Intéraction fichiers CSV<<|||||||||||||||||||||||||||||||||||||||||||||||||||||||
    ///////////////////////////////////////////////////////////////////////////////////////////////
    */



    // Transforme un type Mot en chaine de caractères.
    String motToString(Mot mot){
        return mot.chaine + " " + mot.categorie;
    }
    void testMotToString(){
        Mot m = new Mot();
        m.chaine = "acacia";
        m.categorie = "ARBRE";
        assertEquals("acacia ARBRE",motToString(m));
    }


    // Transforme une chaine de caractères en type Mot.
    Mot stringToMot(String cell){
        int i = 0;
        while(charAt(cell,i)!=' '){
            i++;
        }
        Mot m = new Mot();
        m.chaine = substring(cell,0,i);
        m.categorie = substring(cell,i+1,length(cell));
        return m;
    }
    void testStringToMot(){
        Mot m = stringToMot("bleu COULEUR");
        assertEquals("bleu",m.chaine);
        assertEquals("COULEUR",m.categorie);
        
    }

    /*
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ||||||||||||>>Gestion joueur<<|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    ///////////////////////////////////////////////////////////////////////////////////////////////
    */

    Joueur newJoueur(int idJ, String nom){
        Joueur j = new Joueur();
        j.idJ = idJ;
        j.nom = nom;
        return j;
    }


    // Transforme un type Joueur en chaine de caractères.
    String joueurToString(Joueur j){
        return j.idJ + " " + j.nom;
    }
    void testJoueurToString(){
        Joueur j1 = newJoueur(1,"Arthur");
        Joueur j2 = newJoueur(29,"Bô");
        assertEquals("1 Arthur", joueurToString(j1));
        assertEquals("29 Bô", joueurToString(j2));
    }


    // Transforme une chaine de caractères en type Joueur.
    Joueur stringToJoueur(String chaine){
        int space = 0;
        while(charAt(chaine,space)!=' '){
            space++;
        }
        Joueur j = new Joueur();
        j.idJ = stringToInt(substring(chaine,0,space));
        j.nom = substring(chaine, space+1, length(chaine));
        return j;
    }
    void testStringToJoueur(){
        String chaine = "2 Shell";
        Joueur j = stringToJoueur(chaine);
        assertEquals(2,j.idJ);
        assertEquals("Shell",j.nom);
    }


    // sauvegarde l'historique des joueurs dans un fichier CSV.
    void saveJoueursHistoriqueCSV(Joueur[] joueurs){
        String[][] j = new String[length(joueurs)][1];
        for(int i = 0; i < length(joueurs); i++){
            j[i][0] = joueurToString(joueurs[i]);
        }
        saveCSV(j,"../ressources/infosJoueurs/historique_joueurs.csv");
    }
    
    // Charge dans un tableau l'historique des joueurs.
    Joueur[] loadJoueurHistoriqueCSV(){
        CSVFile j = loadCSV("../ressources/infosJoueurs/historique_joueurs.csv");
        Joueur[] joueurs = new Joueur[rowCount(j)];
        for(int i = 0; i < length(joueurs); i++){
            joueurs[i] = stringToJoueur(getCell(j,i,0));
        }
        return joueurs;
    }

    Joueur creerNouveauJoueur(int longueur_historique_joueurs){

        clearScreen();
        delay(500);
        print("    --Entrez un pseudo en 3 lettre majuscules (Ex : \"ADM\")--\n\n  =====> ");
        String nom = saisieNomJoueur(); // à changer pour faire la vérification ...
        Joueur j = newJoueur(longueur_historique_joueurs,nom);
        clearScreen();
        return j;
        
    }

    Joueur[] ajouterJoueurListe(Joueur j, Joueur[] liste){

        Joueur[] nouvelleListe = new Joueur[length(liste)+1];
        for(int i = 0; i < length(liste); i++){
            nouvelleListe[i] = liste[i];
        }
        nouvelleListe[length(nouvelleListe)-1] = j;
        return nouvelleListe;

    }
    
    void affichageListeJoueurs(Joueur[] joueursListe){

        println("    Historique des joueurs");
        println("    -----------");
        println("    |");

        for(int i = 0; i < length(joueursListe); i++){

            println("    |  " + joueursListe[i].nom);
            println("    |");

        }
        
        println("    -----------");
    }



    /*
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ||||||||||||>>Menus / Modes de jeu<<|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    ///////////////////////////////////////////////////////////////////////////////////////////////
    */



    // Verifie si un dossier ciblé est vide ou non.
    boolean estDossierVide(String dossier){

        String[] contenu = getAllFilesFromDirectory(dossier);
        return length(contenu) == 0;

    }
    void testEstDossierVide(){

        String dossier1 = "../ressources/grilles_jeux_sauvegardes/Plus_de_points_possible";
        assertTrue(estDossierVide(dossier1));

        String dossier2 = "..";
        assertFalse(estDossierVide(dossier2));

    }

    String dimensionToString(Mot[][] grille){

        return length(grille,1) + " " + length(grille,2);

    }
    int[] stringToDimension(String dimString){

        int[] dimInt = new int[2];
        int space = 0;
        while(charAt(dimString,space) != ' '){

            space++;
            
        }
        dimInt[0] = stringToInt(substring(dimString,0,space));
        dimInt[1] = stringToInt(substring(dimString,space+1,length(dimString)));
        return dimInt;
        
    }

    // Sauvegarde une partie en cours [Mode 1 : plus de points possible].
    void saveGameMode1(Mot[][] grille, Joueur joueurCourant,int score, int coupsRestants){
        String[][] sauvegarde = new String[length(grille,1)][length(grille,2)+1];
        for(int i = 0; i < length(grille,1); i++){
            for(int j = 0; j < length(grille,2); j++){
                sauvegarde[i][j] = motToString(grille[i][j]);
            }
        }
        sauvegarde[0][length(sauvegarde,2)-1] = dimensionToString(grille);
        sauvegarde[1][length(sauvegarde,2)-1] = joueurToString(joueurCourant);
        sauvegarde[2][length(sauvegarde,2)-1] = "" + score;
        sauvegarde[3][length(sauvegarde,2)-1] = "" + coupsRestants;
        
        String nomFichierCSV = "../ressources/grilles_jeux_sauvegardes/Plus_de_points_possible/" + joueurCourant.nom + ".csv";

        saveCSV(sauvegarde, nomFichierCSV);
    }

    // Charge le contenu d'un fichier SCV dans une grille de chaines de caractères.
    String[][] loadGame(String nomFichierCSV){
        CSVFile f = loadCSV(nomFichierCSV);
        String[][] partie = new String[rowCount(f)][columnCount(f)];
        for(int i = 0; i < length(partie,1); i++){
            for(int j = 0; j < length(partie,2); j++){
                partie[i][j] = getCell(f,i,j);
            }
        }
        return partie;
    }

    // Cherche dans un dossier si il existe une partie en cours portant le nom du joueur. Rnvoie une chaine vide si rien n'a été trouvé.
    String chercherPartieEnCours(String nomJoueur, String nomDossier){

        String[] contenuDossier = getAllFilesFromDirectory(nomDossier);
        String nomFichierPartie = "";
        int cpt = 0;
        boolean trouve = false;
        while(!trouve && cpt < length(contenuDossier)){

            if(equals(nomJoueur,substring(contenuDossier[cpt],0,3))){

                nomFichierPartie = contenuDossier[cpt];
                trouve = true;

            }
            cpt ++;

        }
        if(!trouve){

            return nomFichierPartie;

        }else{
            
            return nomDossier + "/" + nomFichierPartie;
            
        }

    }

    // Supprime un fichier à partir d'un chemin relatif.
    void supprimerFichier(String cheminFichier){

        java.io.File fichierASupprimer = new java.io.File(cheminFichier);
        fichierASupprimer.delete();

    }
    
    // Sauvegarde dans l'ordre décroissant les scores. (La fonction prend uniquement le joueur et le score pour modifier le ficher CSV)
    void sauvegarderScoreDansTableau_Mode1(Joueur joueur, int score){
        
        CSVFile f = loadCSV("../ressources/infosJoueurs/scores_plus_de_points_possible.csv");
        String[][] tabScores = new String[rowCount(f)][columnCount(f)];
        for(int i = 0; i < length(tabScores,1); i++){

            tabScores[i][0] = getCell(f,i,0);
            tabScores[i][1] = getCell(f,i,1);

        }
        int indice = 0;
        while(score < stringToInt(tabScores[indice][1]) && indice < length(tabScores,1)-1){
            
            indice++;

        }
        String[][] nouvelleTable = new String[length(tabScores,1)+1][2];
        for(int i = 0; i < indice; i++){
            
            nouvelleTable[i][0] = tabScores[i][0];
            nouvelleTable[i][1] = tabScores[i][1];
            
        }
        nouvelleTable[indice][0] = joueur.nom;
        nouvelleTable[indice][1] = "" + score;
        for(int i = indice+1; i < length(nouvelleTable); i++){
            
            nouvelleTable[i][0] = tabScores[i-1][0];
            nouvelleTable[i][1] = tabScores[i-1][1];
            
        }
        tabScores = nouvelleTable;
        saveCSV(tabScores, "../ressources/infosJoueurs/scores_plus_de_points_possible.csv");
        
    }

    // Affiche le tableau des scores du mode de jeu 1.
    void afficherTopScoresMode1(){

        CSVFile f = loadCSV("../ressources/infosJoueurs/scores_plus_de_points_possible.csv");
        String[][] tabScores = new String[rowCount(f)][columnCount(f)];
        for(int i = 0; i < length(tabScores,1); i++){

            tabScores[i][0] = getCell(f,i,0);
            tabScores[i][1] = getCell(f,i,1);

        }
        clearScreen();
        delay(500);
        println("    Tableau des scores");
        println("    Mode : Plus de points possible");
        println("    -----------");
        println("    |");
        for(int i = 0; i < length(tabScores,1)-1; i++){

            println("    |  " + (i+1) + " - " + tabScores[i][0] + " " + tabScores[i][1] + "pts");
            println("    |");

        }
        println("    -----------");
        println();
        print("Faites [entrer] pour revenir au menu");
        String attente = readString();
        clearScreen();
        delay(500);
    }

    

    void aide(int score, int SCORE_MAX){

        println("Bienvenue dans Capharnaumot !");
        delay(1500);
        println("Vous allez devoir échanger des mots pour former des groupes de 3 ou + et les faire disparaître.");
        delay(3000);
        println("Faire disparaître des mots vous fait gagner des points, pour sélectionner des mots, vous devez entrer leurs coordonnées.");
        delay(3000);
        println("Les coordonnées doivent être au format LCLC avec L:Lettre et C:chiffre.");
        delay(3000);
        println("Les coordonnées doivent être adjacente, leur position seront alors échangés.");
         delay(3000);
        println("Votre score est de "+score+" pts, atteignez "+SCORE_MAX+" pts pour gagner. Bonne chance !");
        delay(2500);

    }

    void menu_tableaux_des_scores(){

        clearScreen();
        delay(500);
        boolean quitter = false;
        int choix;
        do{
            afficher_Menu_Des_Scores();
            print("  Selectionnez un tableau : ");
            choix = stringToInt(readString());

            switch(choix){
                case 1:
                    afficherTopScoresMode1();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 0:
                    quitter = true;
                    break;
            }
        }while(!quitter);
        clearScreen();
        delay(500);
    }

    void afficher_Menu_Des_Scores(){

        println("      --Tableaux des scores--");
        println("    ―――――――――――――――――――――――――――\n    |");
        println("    |  #1 High Score Runner\n    |");
        println("    |  #2 Lingo Clash\n    |");
        println("    |  #3 Objective word\n    |");
        println("    ―――――――――――――――――――――――――――");
        println("       #0 Quitter\n");

    }

    void recupererGrille(Mot[][] grille, String[][] grilleInfosPartie){
        for(int i = 0; i < length(grille,1); i++){
                
            for(int j = 0; j < length(grille,2); j++){

                grille[i][j] = stringToMot(grilleInfosPartie[i][j]);

            }

        }
    }

    void highScoreRunner(Joueur joueurCourant,CSVFile mots, String fichierDePartie,boolean partieDejaExistante){
        final int MAX = max(mots)+2;
         //Initialisation des variables de la grille.//Création de la grille de jeu
        int longueur,largeur;
        String[][] grilleInfosPartie = new String[6][6];/*if partie existe*/
        Mot[][] grille;
        if(partieDejaExistante){

            // Charger une partie déjà existante.
            grilleInfosPartie = loadGame(fichierDePartie);
            int[] dimensions = stringToDimension(grilleInfosPartie[0][length(grilleInfosPartie,2)-1]);
            longueur = dimensions[0];
            largeur = dimensions[1];
            grille=creerGrille(longueur,largeur);
            recupererGrille(grille,grilleInfosPartie);

        }else{

            // Créer une nouvelle partie.
            longueur = 6;
            largeur = 6;
            grille=creerGrille(longueur,largeur);
            initialiserGrille(grille,mots,MAX);
        }
        
        final int NBCOUPS = 10;// <-- Changer ici le nombre de coups de départ.
        int score;
        int scoreSauv=0;
        int nbCoupsRestants;
        if(partieDejaExistante){

            // Charger le score sauvegardé.
            score = stringToInt(grilleInfosPartie[2][length(grilleInfosPartie,2)-1]);
            // Charge le nombre de coups sauvegardé.
            nbCoupsRestants = stringToInt(grilleInfosPartie[3][length(grilleInfosPartie,2)-1]);

        }else{

            // Nouveau score.
            score = 0;
            // Met le nombre de coups au max.
            nbCoupsRestants = NBCOUPS;

        }
        //Initialisation des variables de saisies.
        String saisie;
        int[] coordonnees=new int[4];


        // Introduction du jeu.
        //aide(score,SCORE_MAX);

        // Commencement du jeu.
        boolean enjeu=true;

        clearScreen();

        // Affichage des règles du mode de jeu.
        delay(1000);
        println("    Règles du jeu :");
        println("    Faire le plus de points possibles en " + NBCOUPS + " coups.");
        println("    Les erreurs sont compabilisée, réfléchissez bien à vos coups !");
        println();
        print("  Faites [entrer] pour lancer la partie");
        readString();

        while(enjeu){
            
            // Affichage de la grille de jeu.
            affiche(grille,MAX);

            // Affichage scores et coups restants.
            print("    SCORE:"+score+"pts (+"+(score-scoreSauv)+"pts).");
            if (score-scoreSauv>600){
                print("Bien joué ! =D");
            }
            println();
            println("    Il te reste " + nbCoupsRestants + " coups");
            println();
            print("  Veuillez saisir des coordonnées :");
            
            saisie=saisie(length(grille,1),length(grille,2));


            // Verification de la volonté du joueur a continuer ou sauvegarder et quitter la partie.
            if(!equals(saisie,"SAVE")){

                // Le jeu continue.
                saisieInTabInt(saisie,coordonnees);
                echange(grille,coordonnees[0],coordonnees[1],coordonnees[2],coordonnees[3]);

                affiche(grille,MAX);
                println("\n\n");
                
                delay(1500);

                scoreSauv=score;
                
                // Verification de la validité du coup.
                if(!stables(grille)){
                    
                    // Coup réussi.
                    score=remplir(grille,mots,MAX,score);

                }else{

                    // Coup raté.
                    echange(grille,coordonnees[0],coordonnees[1],coordonnees[2],coordonnees[3]);

                }
                nbCoupsRestants--;
                enjeu = nbCoupsRestants > 0;

            }else{
                
                // Le joueur décide de quitter la partie.
                enjeu = false;

            }
            delay(500);

        }

        // Fin du jeu ou mise en pause.

        if(nbCoupsRestants > 0){
            
            // Jeu non terminé -> sauvegarde de la partie.
            println("Sauvegarde ...");
            saveGameMode1(grille, joueurCourant, score, nbCoupsRestants);
            print("Revenir au menu principal [entrer]");
            readString();

        }else{

            // Jeu terminé -> sauvegarde du nombre de points.
            sauvegarderScoreDansTableau_Mode1(joueurCourant,score);
            if(partieDejaExistante){

                // Suppression du fichier de sauvegarde.
                supprimerFichier(fichierDePartie);

            }
            println("TERMINÉ ! Tu as épuisé les " + NBCOUPS + " coups à ta disposition.\nTon score est de " + score + " pts.");
            println();
            print("Revenir au menu principal [entrer]");
            readString();

        }
    }

    void objectiveWord(Joueur joueurCourant,CSVFile mots, String fichierDePartie, boolean partieDejaExistante){
        final int MAX = max(mots)+2;
         //Initialisation des variables de la grille.
         // et Création de la grille de jeu
        int longueur,largeur;
        String[][] grilleInfosPartie = new String[6][6];
        Mot[][] grille;
        if(partieDejaExistante){

            // Charger une partie déjà existante.
            grilleInfosPartie = loadGame(fichierDePartie);
            int[] dimensions = stringToDimension(grilleInfosPartie[0][length(grilleInfosPartie,2)-1]);
            longueur = dimensions[0];
            largeur = dimensions[1];
            grille = creerGrille(longueur,largeur);
            recupererGrille(grille,grilleInfosPartie);

        }else{

            // Créer une nouvelle partie.
            longueur = 6;
            largeur = 6;
            grille = creerGrille(longueur,largeur);
            initialiserGrille(grille,mots,MAX);
        }
        final int OBJECTIF_SCORE=2000;//Changer ici le nombre de points à atteindre.
        int score;
        int scoreSauv=0;
        int nbCoupsRestants=0;
        if(partieDejaExistante){

            // Charger le score sauvegardé.
            score = stringToInt(grilleInfosPartie[2][length(grilleInfosPartie,2)-1]);

        }else{
            // Nouveau score.
            score = 0;

        }
        //Initialisation des variables de saisies.
        String saisie;
        int[] coordonnees=new int[4];


        // Introduction du jeu.
        //aide(score,SCORE_MAX);

        // Commencement du jeu.
        boolean enjeu=true;

        clearScreen();

        // Affichage des règles du mode de jeu.
        delay(1000);
        println("    Règles du jeu :");
        println("    Atteignez "+ OBJECTIF_SCORE +" avec le moins de coups possible.");
        println("    Chaque erreurs vous retire 100pts, réfléchissez bien à vos coups !");
        println();
        print("  Faites [entrer] pour lancer la partie");
        readString();
        while(enjeu){
            
            // Affichage de la grille de jeu.
            affiche(grille,MAX);

            // Affichage scores et coups restants.
            print("    SCORE:"+score+"pts (+"+(score-scoreSauv)+"pts).");
            if (score-scoreSauv>600){
                print("Bien joué ! =D");
            }
            println();
            println("    Il te reste " + (OBJECTIF_SCORE-score) + " points à atteindre.");
            println();
            print("  Veuillez saisir des coordonnées :");
            
            saisie=saisie(length(grille,1),length(grille,2));


            // Verification de la volonté du joueur a continuer ou sauvegarder et quitter la partie.
            if(!equals(saisie,"SAVE")){

                // Le jeu continue.
                saisieInTabInt(saisie,coordonnees);
                echange(grille,coordonnees[0],coordonnees[1],coordonnees[2],coordonnees[3]);

                affiche(grille,MAX);
                println("\n\n");
                
                delay(1500);

                scoreSauv=score;
                
                // Verification de la validité du coup.
                if(!stables(grille)){
                    
                    // Coup réussi.
                    score=remplir(grille,mots,MAX,score);

                }else{

                    // Coup raté.
                    echange(grille,coordonnees[0],coordonnees[1],coordonnees[2],coordonnees[3]);
                    score-=100;//On retire 100 poitns aux joueurs qui se trompent

                }
                nbCoupsRestants++;
                enjeu = OBJECTIF_SCORE > score;

            }else{
                
                // Le joueur décide de quitter la partie.
                enjeu = false;

            }
            delay(500);

        }

        // Fin du jeu ou mise en pause.

        if(OBJECTIF_SCORE > score){
            
            // Jeu non terminé -> sauvegarde de la partie.
            println("Sauvegarde ...");
            saveGameMode1(grille, joueurCourant, score, nbCoupsRestants);
            print("Revenir au menu principal [entrer]");
            readString();

        }else{

            // Jeu terminé -> sauvegarde du nombre de points.
            sauvegarderScoreDansTableau_Mode1(joueurCourant,score);
            if(partieDejaExistante){

                // Suppression du fichier de sauvegarde.
                supprimerFichier(fichierDePartie);

            }
            println("TERMINÉ ! Tu as atteint l'objectif des " + OBJECTIF_SCORE + " points en "+nbCoupsRestants+" coups.\nTon score est de " + score + " pts.");
            println();
            print("Revenir au menu principal [entrer]");
            readString();

        }

    }

    /*void mode_1_High_Score_Runner(Joueur joueurCourant){

        // Importation du fichier CSV
        final String MOTS = "../ressources/bibliotechMots/lexiques.csv";
        CSVFile mots = loadCSV(MOTS);
        
        // Recherche d'une partie déjà existante.
        String fichierDePartie = chercherPartieEnCours(joueurCourant.nom, "../ressources/grilles_jeux_sauvegardes/Plus_de_points_possible");
        println();
        println(fichierDePartie);
        println();

        String[][] grilleInfosPartie = new String[6][6];
        boolean partieDejaExistante = !equals(fichierDePartie,"");
        if(partieDejaExistante){

            grilleInfosPartie = loadGame(fichierDePartie);

        }
        // Constante de la taille du mot le plus grand du fichier CSV, permet de gérer l'affichage.
        final int MAX = max(mots)+2;

        //Initialisation des variables de la grille.
        int longueur,largeur;
        if(partieDejaExistante){

            // Charger une partie déjà existante.
            int[] dimensions = stringToDimension(grilleInfosPartie[0][length(grilleInfosPartie,2)-1]);
            longueur = dimensions[0];
            largeur = dimensions[1];

        }else{

            // Créer une nouvelle partie.
            longueur = 6;
            largeur = 6;
        }

        Mot[][] grille = creerGrille(longueur,largeur);
        if(partieDejaExistante){

            for(int i = 0; i < length(grille,1); i++){
                
                for(int j = 0; j < length(grille,2); j++){

                    grille[i][j] = stringToMot(grilleInfosPartie[i][j]);

                }

            }
            
        }else{

            initialiserGrille(grille,mots,MAX);

        }
        //Initialisation des variables de scores.
        final int NBCOUPS = 5;// <-- Changer ici le nombre de coups de départ.
        int score;
        if(partieDejaExistante){

            // Charger le score sauvegardé.
            score = stringToInt(grilleInfosPartie[2][length(grilleInfosPartie,2)-1]);

        }else{

            // Nouveau score.
            score = 0;

        }
        int scoreSauv=0;
        int nbCoupsRestants;
        if(partieDejaExistante){

            // Charge le nombre de coups sauvegardé.
            nbCoupsRestants = stringToInt(grilleInfosPartie[3][length(grilleInfosPartie,2)-1]);
            
        }else{
            
            // Met le nombre de coups au max.
            nbCoupsRestants = NBCOUPS;

        }
        //Initialisation des variables de saisies.
        String saisie;
        int[] coordonnees=new int[4];


        // Introduction du jeu.
        //aide(score,SCORE_MAX);

        // Commencement du jeu.
        boolean enjeu=true;

        clearScreen();

        // Affichage des règles du mode de jeu.
        delay(1000);
        println("    Règles du jeu :");
        println("    Faire le plus de points possibles en " + NBCOUPS + " coups.");
        println("    Les erreurs sont compabilisée, réfléchissez bien à vos coups !");
        println();
        print("  Faites [entrer] pour lancer la partie");
        readString();

        while(enjeu){
            
            // Affichage de la grille de jeu.
            affiche(grille,MAX);

            // Affichage scores et coups restants.
            println("    SCORE:"+score+"pts (+"+(score-scoreSauv)+"pts). Bien joué ! =D");
            println("    Il te reste " + nbCoupsRestants + " coups");
            println();
            print("  Veuillez saisir des coordonnées :");
            
            saisie=saisie(length(grille,1),length(grille,2));


            // Verification de la volonté du joueur a continuer ou sauvegarder et quitter la partie.
            if(!equals(saisie,"SAVE")){

                // Le jeu continue.
                saisieInTabInt(saisie,coordonnees);
                echange(grille,coordonnees[0],coordonnees[1],coordonnees[2],coordonnees[3]);

                affiche(grille,MAX);
                println("\n\n");
                
                delay(1500);

                scoreSauv=score;
                
                // Verification de la validité du coup.
                if(!stables(grille)){
                    
                    // Coup réussi.
                    score=remplir(grille,mots,MAX,score);

                }else{

                    // Coup raté.
                    echange(grille,coordonnees[0],coordonnees[1],coordonnees[2],coordonnees[3]);

                }
                nbCoupsRestants--;
                
                enjeu = nbCoupsRestants > 0;

            }else{
                
                // Le joueur décide de quitter la partie.
                enjeu = false;

            }
            delay(500);

        }

        // Fin du jeu ou mise en pause.

        if(nbCoupsRestants > 0){
            
            // Jeu non terminé -> sauvegarde de la partie.
            println("Sauvegarde ...");
            saveGameMode1(grille, joueurCourant, score, nbCoupsRestants);
            print("Revenir au menu principal [entrer]");
            readString();

        }else{

            // Jeu terminé -> sauvegarde du nombre de points.
            sauvegarderScoreDansTableau_Mode1(joueurCourant,score);
            if(partieDejaExistante){

                // Suppression du fichier de sauvegarde.
                supprimerFichier(fichierDePartie);

            }
            println("TERMINÉ ! Tu as épuisé les " + NBCOUPS + " coups à ta disposition.\nTon score est de " + score + " pts.");
            println();
            print("Revenir au menu principal [entrer]");
            readString();

        }

    }*/

    /*
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ||||||||||||>>Programme principal<<||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    ///////////////////////////////////////////////////////////////////////////////////////////////
    */

    void algorithm(){

        // Création du joueur.
        Joueur[] historique_des_joueurs = loadJoueurHistoriqueCSV();
        affichageListeJoueurs(historique_des_joueurs);

        Joueur currentPlayer = creerNouveauJoueur(length(historique_des_joueurs));

        historique_des_joueurs = ajouterJoueurListe(currentPlayer,historique_des_joueurs);
        saveJoueursHistoriqueCSV(historique_des_joueurs);

        println("    Bonjour " + currentPlayer.nom + ", bienvenu dans le ...");

        delay(2000);


        // menu principal
        boolean quitter = false;
        String choix;

        //Importation du fichier CSV
        final String MOTS = "../ressources/bibliotechMots/lexiques.csv";
        CSVFile mots = loadCSV(MOTS);
        
        String fichierDePartie;
        boolean partieDejaExistante;

        int longueur=6;
        int largeur=6;

        do{
            
            
            // Recherche d'une partie déjà existante.
            fichierDePartie = chercherPartieEnCours(currentPlayer.nom, "../ressources/grilles_jeux_sauvegardes/Plus_de_points_possible");
            println();
            println(fichierDePartie);
            println();
            partieDejaExistante = !equals(fichierDePartie,"");
            
            clearScreen();
            afficherFichier("../ressources/dessinsMenu/menu_principal");
            println("    Joueur : " + currentPlayer.nom);
            println();
            print("    Choisissez une option : ");
            choix = readString();
            switch(choix){

                case "1":

                    highScoreRunner(currentPlayer,mots,fichierDePartie,partieDejaExistante);
                    break;

                case "2":
                    println("Contenu indisponible.");
                    delay(1000);
                    break;

                case "3":
                    objectiveWord(currentPlayer,mots,fichierDePartie,partieDejaExistante);
                    break;

                case "4":

                    menu_tableaux_des_scores();
                    break;
                    
                case "5":

                    clearScreen();
                    println("    Souhaites-tu quitter ? [o/n]");
                    print("    =====>");
                    String choixQuitter = readString();//     \   
                    if(equals(choixQuitter,"o")){//           |
//                                                            |> à changer à l'avenir ...
                        quitter = true;//                     |
//                                                            /
                    }
                    break;
            }
            

        }while(!quitter);

    }
    
}