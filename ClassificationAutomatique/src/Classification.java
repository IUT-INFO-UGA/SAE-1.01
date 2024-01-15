import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Classification {


    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }


    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        ArrayList<PaireChaineEntier> result = new ArrayList<>();
        for(int i = 0; i < categories.size(); ++i){
            for(int j = 0; j < depeches.size(); ++i){
                    result.add(new PaireChaineEntier(categories.get(i).getNom(), 0));                    
                }
            }
        }
    }


    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        return resultat;

    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
    }

    public static int poidsPourScore(int score) {
        return 0;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

    }

    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }

        Categorie culture = new Categorie("Culture");
        culture.initLexique("./lexiques/CULTURE");
        Categorie economie = new Categorie("Economie");
        economie.initLexique("./lexiques/ECONOMIE");
        Categorie environnement = new Categorie("Environnement");
        environnement.initLexique("./lexiques/ENVIRONNEMENT-SCIENCES");
        Categorie polithique = new Categorie("Polithique");
        polithique.initLexique("./lexiques/POLITHIQUE");
        Categorie sport = new Categorie("Sport");
        sport.initLexique("./lexiques/SPORTS");

        Categorie categorie = new Categorie("Environement-sciences");
        categorie.initLexique("./lexiques/ENVIRONNEMENT-SCIENCES");
        System.out.println("Score de la dépêche 0 pour la catégorie Sports : " + categorie.score(depeches.get(0)));
        
        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(sport.getLexique(), "amuser"));
        
        int i = 0;
        List<PaireChaineEntier> resultats = new ArrayList<>();

        while (i < depeches.size()) {
            String categorie = depeches.get(i).getCategorie();
            int score = 0;

            while (i < depeches.size() && depeches.get(i).getCategorie().equals(categorie)) {
                switch (categorie) {
                    case "ENVIRONNEMENT-SCIENCES":
                        score += environnement.score(depeches.get(i));
                        break;
                    case "CULTURE":
                        score += culture.score(depeches.get(i));
                        break;
                    case "ECONOMIE":
                        score += economie.score(depeches.get(i));
                        break;
                    case "POLITIQUE":
                        score += polithique.score(depeches.get(i));
                        break;
                    case "SPORTS":
                        score += sport.score(depeches.get(i));
                        break;
                    // Ajoutez d'autres catégories au besoin
                }
                ++i;
            }

            PaireChaineEntier paire = new PaireChaineEntier();
            paire.setChaine(categorie);
            paire.setEntier(score);
            resultats.add(paire);
        }

        System.out.println(resultats.get(0).getChaine());

        
    }


}

