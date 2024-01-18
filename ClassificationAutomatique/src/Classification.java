import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Classification {
    /**
     * The function "lectureDepeches" reads a file containing news articles and returns an ArrayList of Depeche objects,
     * where each object represents a news article with an ID, date, category, and content.
     * 
     * @param nomFichier String that represents the name of the file from which the method will read the data.
     * @return The method is returning an ArrayList of Depeche objects.
     */
    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        final ArrayList<Depeche> depeches = new ArrayList<>();
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
                final Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("error during reading");
        }
        return depeches;
    }

    /**
     * The function "classementDepeches" takes in a list of news articles, a list of categories, and a file name, and
     * writes the classification results and statistics to the specified file.
     * 
     * @param depeches An ArrayList of Depeche objects, which represents a collection of news dispatches.
     * @param categories An ArrayList of objects of type Categorie. Each Categorie object represents a category for the
     * Depeche objects.
     * @param nomFichier String that represents the name of the file where the results will be written.
     */
    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {

        ArrayList<PaireChaineEntier> catResults = new ArrayList<>();

        for (Categorie category : categories) {
            catResults.add(new PaireChaineEntier(category.getNom(), 0));
        }

        try {
            FileWriter file = new FileWriter(nomFichier);
            for (Depeche depeche : depeches) {
                final ArrayList<PaireChaineEntier> result = new ArrayList<>();
                for (Categorie category : categories) {
                    result.add(new PaireChaineEntier(category.getNom(), category.score(depeche)));
                }

                if (UtilitairePaireChaineEntier.chaineMax(result).equals(depeche.getCategorie())) {
                    catResults.set(UtilitairePaireChaineEntier.indicePourChaine(catResults, depeche.getCategorie()),
                            new PaireChaineEntier(depeche.getCategorie(),
                            catResults.get(UtilitairePaireChaineEntier.indicePourChaine(catResults, depeche.getCategorie())).getEntier() + 1));
                }

                file.write(depeche.getId() + ":" + UtilitairePaireChaineEntier.chaineMax(result) + "\n");
            }
            file.write("\n\n");
            int moyenne = 0;
            for (PaireChaineEntier catResult : catResults) {
                file.write(catResult.getChaine() + ":" + catResult.getEntier() + "%\n");
                moyenne += catResult.getEntier();
            }
            file.write("MOYENNE:" + (moyenne / catResults.size()) + "%\n");
            file.close();
        } catch (IOException e) {
            System.out.println("error during writing");
        }
    }


    /**
     * The function `initDico` takes in a list of `Depeche` objects and a category, and returns a sorted array containing
     * the unique words from the `Depeche` objects in the specified category along with their frequency of occurrence.
     * 
     * @param depeches An ArrayList of Depeche objects.
     * @param categorie String that represents the category of the "Depeche" objects. It is used to filter the "depeches" 
     * ArrayList and only consider the ones that have the specified category.
     * @return The method is returning a SortedArray object.
     */
    public static SortedArray initDico(ArrayList<Depeche> depeches, String categorie) {
        SortedArray resultat = new SortedArray();

        for (Depeche depeche : depeches) {
            if (depeche.getCategorie().equals(categorie)) {
                for (String mot : depeche.getMots()) {
                    if (resultat.indexOf(mot) == -1) {
                        resultat.addSorted(new PaireChaineEntier(mot, 1));
                    } else {
                        resultat.set(resultat.indexOf(mot), resultat.get(resultat.indexOf(mot)).getEntier() + 1);
                    }
                }
            }
        }

        return resultat;
    }

/**
 * The function calculates scores for each word in a list of news articles based on their category and updates a dictionary
 * accordingly.
 * 
 * @param depeches An ArrayList of Depeche objects.
 * @param categorie String that represents the category of the Depeche objects.
 * @param dictionnaire SortedArray object, which is a data structure
 * stores words and their corresponding scores. Each word in the dictionnaire has an associated score represented by an
 * integer value.
 */
    public static void calculScores(ArrayList<Depeche> depeches, String categorie, SortedArray dictionnaire) {
        for (Depeche depeche : depeches) {
            for (String mot : depeche.getMots()) {
                if (dictionnaire.indexOf(mot) != -1) {
                    if (depeche.getCategorie().equals(categorie)) {
                        dictionnaire.set(dictionnaire.indexOf(mot), dictionnaire.get(mot).getEntier() + 1);
                    } else {
                        dictionnaire.set(dictionnaire.indexOf(mot), dictionnaire.get(mot).getEntier() - 1);
                    }
                }
            }
        }
    }

/**
 * The function "poidsPourScore" returns a weight value based on the input score, with higher scores resulting in higher
 * weights.
 * 
 * @param score The parameter "score" represents a numerical value that is used to determine the weight or importance of
 * the data. The function "poidsPourScore" calculates and returns a weight value based on the given score.
 * @return The method `poidsPourScore` returns an integer value. The value returned depends on the input `score`. If
 * `score` is less than 5, the method returns 1. If `score` is between 5 and 15 (exclusive), the method returns 2.
 * Otherwise, if `score` is greater than or equal to 15, the method returns 3.
 */
    public static int poidsPourScore(int score) {
        // This defines the disparity of the model data
        // More the two values are far from each other, more the data will be generalized
        if (score < 5) {
            return 1;
        } else if (score < 15 ) {
            return 2;
        }
        return 3;
    }

/**
 * The function `cleanString` takes a string as input and removes certain characters from it, returning an empty string if
 * the resulting string is either "-" or "'", or if the length of the resulting string is less than or equal to 2.
 * 
 * @param string The parameter "string" is a string that needs to be cleaned.
 * @return The method returns the cleaned string.
 */
    private static String cleanString(String string) {
        string = string.replaceAll(":", "");
        if (string.equals("-")) {
            return "";
        } else if (string.equals("'")) {
            return "";
        } else if (string.equals("%")) {
            return "";
        } else if (string.length() <= 2) {
            return "";
        }
        return string;
    }

    /**
     * The function `generationLexique` takes in a list of `Depeche` objects, a category, and a filename, and generates a
     * lexicon by calculating scores for each word in the dataset and writing them to the specified file.
     * 
     * @param depeches An ArrayList of objects of type "Depeche". It contains the depeches (news articles) that will be
     * used to generate the lexique (dictionary).
     * @param categorie The parameter "categorie" is a string that represents the category of the data. It is used to
     * filter the data and perform calculations specific to that category.
     * @param nomFichier The parameter "nomFichier" is a String that represents the name of the file where the generated
     * lexicon will be saved.
     */
    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        final SortedArray dictionnaire = initDico(depeches, categorie);

        // The following is the model searching depth. The higher it is, better the result will be
        // In our dataset, it starts to crunch the data at 5
       final int depth = 5;
        for (int i = 0; i < depth; i++) {
            calculScores(depeches, categorie, dictionnaire);
        }
        try {
            final FileWriter file = new FileWriter(nomFichier);
            for (PaireChaineEntier paireChaineEntier : dictionnaire.getArray()) {
                if (!cleanString(paireChaineEntier.getChaine()).isEmpty()) {
                    file.write(cleanString(paireChaineEntier.getChaine()) + ":" + poidsPourScore(paireChaineEntier.getEntier()) + "\n");
                }
            }
            file.close();
        } catch (IOException e) {
            System.out.println("error during writing");
        }
    }

    public static void main(String[] args) {
        final long totalStartTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        final ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");
        final ArrayList<Categorie> categories = new ArrayList<>();
        System.out.println("Depeches loaded in " + (System.currentTimeMillis()-startTime) + "ms");

        startTime = System.currentTimeMillis();
        generationLexique(depeches, "ECONOMIE", "economie_knn.txt");
        generationLexique(depeches, "POLITIQUE", "politique_knn.txt");
        generationLexique(depeches, "CULTURE", "culture_knn.txt");
        generationLexique(depeches, "ENVIRONNEMENT-SCIENCES", "environnement-sciences_knn.txt");
        generationLexique(depeches, "SPORTS", "sports_knn.txt");
        System.out.println("Lexiques generated in " + (System.currentTimeMillis()-startTime) + "ms");

        startTime = System.currentTimeMillis();
        categories.add(new Categorie("ECONOMIE", "economie_knn.txt"));
        categories.add(new Categorie("POLITIQUE", "politique_knn.txt"));
        categories.add(new Categorie("CULTURE", "culture_knn.txt"));
        categories.add(new Categorie("ENVIRONNEMENT-SCIENCES", "environnement-sciences_knn.txt"));
        categories.add(new Categorie("SPORTS", "sports_knn.txt"));
        System.out.println("Lexiques loaded in " + (System.currentTimeMillis()-startTime) + "ms");

        startTime = System.currentTimeMillis();
        classementDepeches(depeches, categories, "output_depeche.txt");
        final ArrayList<Depeche> depeches2 = lectureDepeches("./test.txt");
        classementDepeches(depeches2, categories, "output_test.txt");
        System.out.println("Depeches processed in " + (System.currentTimeMillis()-startTime) + "ms");

        long totalEndTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time: " + (totalEndTime-totalStartTime) + "ms");
    }
}

