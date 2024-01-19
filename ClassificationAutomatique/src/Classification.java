import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Classification {

/**
 * The function `lectureDepeches` reads a file containing news articles and returns an ArrayList of Depeche objects, where
 * each Depeche object represents a news article with an ID, date, category, and content.
 * 
 * @param nomFichier The parameter "nomFichier" is a String that represents the name of the file from which the method will
 * read the data.
 * @return The method is returning an ArrayList of Depeche objects.
 */
    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        // creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            final FileInputStream file = new FileInputStream(nomFichier);
            final Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                final String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.isEmpty()) {
                    ligne = scanner.nextLine();
                    if (!ligne.isEmpty()) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                depeches.add(new Depeche(id, date, categorie, lignes));
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier " + nomFichier);

        }
        return depeches;
    }

/**
 * The function `initDico` initializes a dictionary by iterating through a list of news articles and adding unique words
 * from articles of a specific category to the dictionary.
 * 
 * @param depeches An ArrayList of objects of type Depeche.
 * @param categorie String that represents the category of the news articles.
 * @return The method is returning an ArrayList of PaireChaineEntier objects.
 */
    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        final ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        for (Depeche depech : depeches) {
            if (depech.getCategorie().equals(categorie)) {
                for (int j = 0; j < depech.getMots().size(); ++j) {
                    if (depech.getMots().get(j).length() > 2) {
                        if (UtilitairePaireChaineEntier.indicePourChaine(resultat, depech.getMots().get(j)) == -1)
                            resultat.add(new PaireChaineEntier(depech.getMots().get(j), 0));
                    }
                }
            }
        }
        return resultat;
    }

/**
 * The function `classementDepeches` calculates the category with the highest score for each article, keeps track of the
 * number of articles detected in each category, calculates the total number of articles, and writes the results to a file.
 * 
 * @param depeches An ArrayList of Depeche objects, representing a collection of news dispatches.
 * @param categories An ArrayList of objects of type Categorie. Each Categorie object represents a category and has a name
 * and a score method.
 * @param nomFichier String that represents the name of the file where the output will be
 * written.
 */
    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        String out = "";
        final ArrayList<PaireChaineEntier> detected = new ArrayList<>();
        for (int i = 0; i < depeches.size(); i++) {
            PaireChaineEntier max = new PaireChaineEntier("no data", -1);
            for (Categorie category : categories) {
                final PaireChaineEntier tmp = new PaireChaineEntier(category.getNom(), category.score(depeches.get(i)));
                if (tmp.getEntier() > max.getEntier()) {
                    max = tmp;
                }
            }
            final PaireChaineEntier selectCat = max;
            out += "Depeche " + i + " : " + selectCat.getChaine() + "\n";
            if (depeches.get(i).getCategorie().equals(selectCat.getChaine())) {
                int h = 0;
                while (h < detected.size() && !Objects.equals(detected.get(h).getChaine(), selectCat.getChaine())) {
                    h++;
                }
                if (h < detected.size()) {
                    detected.get(h).setEntier(detected.get(h).getEntier() + 1);
                } else {
                    detected.add(new PaireChaineEntier(selectCat.getChaine(), 1));
                }
            }
        }
        out += "\n\n";
        int total = 0;
        int nbCatt = 0;
        while (nbCatt < detected.size()) {
            out += detected.get(nbCatt).getChaine() + " : " + detected.get(nbCatt).getEntier() + "\n";
            total += detected.get(nbCatt).getEntier();
            nbCatt++;
        }
        out += "Total : " + total / nbCatt + "\n";
        try {
            FileOutputStream file = new FileOutputStream(nomFichier);
            PrintWriter pw = new PrintWriter(file);
            pw.println(out);
            pw.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier " + nomFichier);
        }
    }

/**
 * give a score to a Depeche object based on the words it contains and the lexicon of the category it belongs to.
 * 
 * @param depeches An ArrayList of objects of type Depeche. Each Depeche object represents a news article and contains
 * information such as the category and the words in the article.
 * @param categorie String that represents the category of the Depeche objects.
 * @param dictionnaire ArrayList of type PaireChaineEntier. It is used to store pairs of strings and integers. 
 * Each pair represents a word and its corresponding count.
 */
    public static void calculScores(ArrayList<Depeche> depeches, String categorie,
            ArrayList<PaireChaineEntier> dictionnaire) {
        for (final Depeche depeche : depeches) {
            for (final String mot : depeche.getMots()) {
                final int index = UtilitairePaireChaineEntier.indexOf(dictionnaire, mot);
                if (index != -1) {
                    if (depeche.getCategorie().equals(categorie)) {
                        dictionnaire.set(index, new PaireChaineEntier(dictionnaire.get(index).getChaine(),dictionnaire.get(index).getEntier() + 1));
                    } else {
                        dictionnaire.set(index, new PaireChaineEntier(dictionnaire.get(index).getChaine(),dictionnaire.get(index).getEntier() + 1));
                    }
                }
            }
        }
    }

/**
 * The function "poidsPourScore" returns a weight based on the input score, with different weights assigned to different
 * score ranges.
 * 
 * @param score The parameter "score" represents the score for which we want to calculate the weight.
 * @return The method `poidsPourScore` returns an integer value.
 */
    public static int poidsPourScore(int score) {
        if (score < 0)
            return 0;
        else if (score < 5)
            return 1;
        else if (score < 30)
            return 2;
        else
            return 3;
    }

/**
 * The function `generationLexique` takes in a list of `Depeche` objects, a category name, and a file name, and generates a
 * lexicon by calculating scores for each `Depeche` object and writing the lexicon to a file.
 * 
 * @param depeches An ArrayList of objects of type "Depeche".
 * @param categorieName String that represents the category name for which the lexicon is being generated.
 * @param nomFichier String that represents the name of the file where the generated lexicon will be written.
 */
    public static void generationLexique(ArrayList<Depeche> depeches, String categorieName, String nomFichier) {
        final ArrayList<PaireChaineEntier> dico = initDico(depeches, categorieName);
        calculScores(depeches, categorieName, dico);
        for (PaireChaineEntier paireChaineEntier : dico) {
            final int weight = poidsPourScore(paireChaineEntier.getEntier());
            if (weight > 0) {
                paireChaineEntier.setEntier(weight);
            }
        }
        // Ecriture du fichier
        try {
            final FileOutputStream file = new FileOutputStream(nomFichier);
            final PrintWriter pw = new PrintWriter(file);
            for (PaireChaineEntier paireChaineEntier : dico) {
                pw.println(paireChaineEntier.getChaine() + ":" + paireChaineEntier.getEntier());
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier " + nomFichier);
        }
    }

    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        final long startTime = System.currentTimeMillis();
        final ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        // Affichage des dépêches
        // for (int i = 0; i < depeches.size(); i++) {
        //     depeches.get(i).afficher();
        // }

        // v1 less optimized
        // ArrayList<Categorie> categories = new ArrayList<>();
        // final Categorie culture = new Categorie("CULTURE");
        // culture.initDico("./lexiques/CULTURE");
        // categories.add(culture);
        // final Categorie economie = new Categorie("ECONOMIE");
        // economie.initDico("./lexiques/ECONOMIE");
        // categories.add(economie);
        // final Categorie environnement = new Categorie("ENVIRONNEMENT-SCIENCES");
        // environnement.initDico("./lexiques/ENVIRONNEMENT-SCIENCES");
        // categories.add(environnement);
        // final Categorie politique = new Categorie("POLITIQUE");
        // politique.initDico("./lexiques/POLITIQUE");
        // categories.add(politique);
        // final Categorie sports = new Categorie("SPORTS");
        // sports.initDico("./lexiques/SPORTS");
        // categories.add(sports);
        //v2
         final ArrayList<Categorie> categories = new ArrayList<>();
         categories.add(new Categorie("CULTURE", "./lexiques/CULTURE"));
         categories.add(new Categorie("ECONOMIE", "./lexiques/ECONOMIE"));
         categories.add(new Categorie("ENVIRONNEMENT-SCIENCES", "./lexiques/ENVIRONNEMENT-SCIENCES"));
         categories.add(new Categorie("POLITIQUE", "./lexiques/POLITIQUE"));
         categories.add(new Categorie("SPORTS", "./lexiques/SPORTS"));

        //5.6
         classementDepeches(depeches, categories, "testClassment.txt");
        System.out.println("executé en : " + (System.currentTimeMillis() -startTime) + "ms");
    }

}
