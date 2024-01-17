import java.util.ArrayList;

public class UtilitairePaireChaineEntier {

	public static int indicePourChaine(SortedArray listePaires, String chaine) {
		int i = -1;
		for (int j = 0; j < listePaires.size(); ++j) {
			if (listePaires.get(j).getChaine().compareTo(chaine) == 0) {
				i = j;
			}
		}
		return i;
	}

	public static int entierPourChaine(SortedArray listePaires, String chaine) {
		return listePaires.get(chaine).getEntier();
	}

	public static String chaineMax(SortedArray listePaires) {
		PaireChaineEntier max = listePaires.get(0);
		for (int i = 0; i < listePaires.size(); ++i) {
			if (listePaires.get(i).getEntier() > max.getEntier()) {
				max = listePaires.get(i);
			}
		}
		return max.getChaine();
	}

	public static float moyenne(SortedArray listePaires) {
		int somme = 0;
		if(listePaires.size() == 0) {
			return 0;
		}
		for (int i = 0; i < listePaires.size(); ++i) {
			somme += listePaires.get(i).getEntier();
		}
		return somme / listePaires.size();
	}

/**
 * The function `indexOf` takes an ArrayList of `PaireChaineEntier` objects and a String `chaine` as parameters, and
 * returns the index of the first occurrence of `chaine` in the ArrayList, or -1 if it is not found.
 * 
 * @param listePaires An ArrayList of objects of type PaireChaineEntier.
 * @param chaine The parameter "chaine" is a String that represents the value we are searching for in the ArrayList.
 * @return The method is returning the index of the first occurrence of the given string in the ArrayList of
 * PaireChaineEntier objects. If the string is found, the method returns the index. If the string is not found, the method
 * returns -1.
 */
	public static int indexOf(SortedArray listePaires, String chaine) {
		int i = 0;
		while (i < listePaires.size() && listePaires.get(i).getChaine().compareTo(chaine) != 0) {
			i++;
		}
		if (i < listePaires.size()) {
			return i;
		} else {
			return -1;
		}
	}

}
