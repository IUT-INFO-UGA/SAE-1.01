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


	public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
		ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
		return resultat;

    }
    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        ArrayList<PaireChaineEntier> result = new ArrayList<>();
        for(int i = 0; i < categories.size(); ++i){
            result.add(new PaireChaineEntier());
            result.get(result.size()-1).setChaine(categories.get(i).getNom());
        }
        for (int i = 0; i < depeches.size(); i++) {
            for (int j = 0; j < result.size()-1; j++) {
                // Comparer les noms des catégories
                if (result.get(j).getChaine().equals(categories.get(i).getNom())) {
                    // Mettre à jour le nombre associé à la catégorie
                    result.get(j).setEntier(result.get(j).getEntier() + 1);
                }
            }
        }
        for(int i = 0; i < 5; ++i){
            System.out.println(result.get(i).getChaine());
            System.out.println(result.get(i).getEntier());
        }
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

        ArrayList<Categorie> categories = new ArrayList<>();
		categories.add(new Categorie("Culture", "./lexiques/CULTURE"));
		categories.add(new Categorie("Economie", "./lexiques/ECONOMIE"));
		categories.add(new Categorie("Environnement-Sciences", "./lexiques/ENVIRONNEMENT-SCIENCES"));
		categories.add(new Categorie("Polithique", "./lexiques/POLITHIQUE"));
		categories.add(new Categorie("Sport", "./lexiques/SPORTS"));

        ArrayList<PaireChaineEntier> catt = new ArrayList<>();
		for (int i = 0; i < categories.size(); i++) {
			catt.add(new PaireChaineEntier(categories.get(2).getNom(), categories.get(2).score(depeches.get(i))));
		}
		System.out.println("categorie de la depeche 2: ");
		System.out.println(UtilitairePaireChaineEntier.chaineMax(catt));
        classementDepeches(depeches, categories, null);
	}

}
