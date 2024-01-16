import java.util.ArrayList;

public class UtilitairePaireChaineEntier {

	public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
		int i = -1;
		for (int j = 0; j < listePaires.size(); ++j) {
			if (listePaires.get(j).getChaine().compareTo(chaine) == 0) {
				i = j;
			}
		}
		return i;
	}

	public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
		int i = 0;
		int entier = 0;
		while (i < listePaires.size()) {
			if (listePaires.get(i).getChaine().compareTo(chaine) == 0) {
				entier = listePaires.get(i).getEntier();
			}
			i++;
		}
		return entier;
	}

	public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
		PaireChaineEntier max = listePaires.get(0);
		for (int i = 0; i < listePaires.size(); ++i) {
			if (listePaires.get(i).getEntier() > max.getEntier()) {
				max = listePaires.get(i);
			}
		}
		return max.getChaine();
	}

	public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
		int somme = 0;
		for (int i = 0; i < listePaires.size(); ++i) {
			somme += listePaires.get(i).getEntier();
		}
		return somme / listePaires.size();
	}

	public static int indexOf(ArrayList<PaireChaineEntier> listePaires, String chaine) {
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
