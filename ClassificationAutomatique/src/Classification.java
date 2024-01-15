import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Classification {

	private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
		// creation d'un tableau de dépêches
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

	public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories,
			String nomFichier) {
	}

	public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
		ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
		return resultat;

	}

	public static void calculScores(ArrayList<Depeche> depeches, String categorie,
			ArrayList<PaireChaineEntier> dictionnaire) {
	}

	public static int poidsPourScore(int score) {
		return 0;
	}

	public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

	}

	public static void main(String[] args) {

		// Chargement des dépêches en mémoire
		System.out.println("chargement des dépêches");
		ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

		for (int i = 0; i < depeches.size(); i++) {
			depeches.get(i).afficher();
		}

		Categorie culture = new Categorie("Culture");
		culture.initLexique("./lexiques/CULTURE");
		Categorie economie = new Categorie("Economie");
		economie.initLexique("./lexiques/ECONOMIE");
		Categorie environnement = new Categorie("Environnement-Sciences");
		environnement.initLexique("./lexiques/ENVIRONNEMENT-SCIENCES");
		Categorie polithique = new Categorie("Polithique");
		polithique.initLexique("./lexiques/POLITHIQUE");
		Categorie sport = new Categorie("Sport");
		sport.initLexique("./lexiques/SPORTS");

		Categorie categorie = new Categorie("Environement-sciences");
		categorie.initLexique("./lexiques/ENVIRONNEMENT-SCIENCES");
		System.out.println("Score de la dépêche 0 pour la catégorie Sports : " + categorie.score(depeches.get(0)));

		ArrayList<Categorie> categories = new ArrayList<>();
		categories.add(culture);
		categories.add(economie);
		categories.add(environnement);
		categories.add(polithique);
		categories.add(sport);

		ArrayList<PaireChaineEntier> catt = new ArrayList<>();
		for (int i = 0; i < categories.size(); i++) {
			catt.add(new PaireChaineEntier(categories.get(2).getNom(), categories.get(2).score(depeches.get(i))));
		}
		System.out.println("categorie de la depeche 2: ");
		System.out.println(UtilitairePaireChaineEntier.chaineMax(catt));

	}

}
