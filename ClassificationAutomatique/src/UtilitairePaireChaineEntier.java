import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


/**
 * The function "indicePourChaine" returns the index of a given string in an ArrayList of objects containing a string and
 * an integer.
 * 
 * @param listePaires An ArrayList of objects of type PaireChaineEntier.
 * @param chaine The parameter "chaine" is a String that represents the value we are searching for in the list of
 * "listePaires".
 * @return The method `indicePourChaine` returns an integer value. If the given `chaine` is found in the `listePaires`, it
 * returns the index of the pair that contains the `chaine`. If the `chaine` is not found, it returns -1.
 */
    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        for (int i = 0; i < listePaires.size(); i++) {
            if (listePaires.get(i).getChaine().equals(chaine)) {
                return i;
            }
        }
        return -1;
    }

/**
 * The function "entierPourChaine" takes a sorted array of pairs and a string as input, and returns the corresponding
 * integer value from the pair whose string matches the input string.
 * 
 * @param listePaires A SortedArray object that contains pairs of strings and integers.
 * @param chaine The parameter "chaine" is a String that represents the value we are searching for in the "listePaires"
 * SortedArray.
 * @return The method is returning an integer value.
 */
    public static int entierPourChaine(SortedArray listePaires, String chaine) {

        boolean found = false;
        int i = 0;
        while (i < listePaires.size() && !found) {
            if (listePaires.get(i).getChaine().equals(chaine)) {
                found = true;
            }
            i++;
        }
        if (found) {
            return listePaires.get(i - 1).getEntier();
        }
        return 0;
    }

/**
 * The function "chaineMax" takes in an ArrayList of objects of type "PaireChaineEntier" and returns the string value of
 * the object with the highest integer value.
 * 
 * @param listePaires An ArrayList of objects of type PaireChaineEntier.
 * @return The method is returning a String.
 */
    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        PaireChaineEntier max = listePaires.get(0);
        for (PaireChaineEntier listePaire : listePaires) {
            if (max.getEntier() < listePaire.getEntier()) {
                max = listePaire;
            }
        }
        return max.getChaine();
    }


/**
 * The function calculates the average of the integer values in a list of PaireChaineEntier objects.
 * 
 * @param listePaires An ArrayList of objects of type PaireChaineEntier.
 * @return The method is returning the average (mean) of the integers in the ArrayList.
 */
    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        int moyenne = 0;
        for (PaireChaineEntier listePaire : listePaires) {
            moyenne += listePaire.getEntier();
        }
        return (float) moyenne / listePaires.size();
    }

}
