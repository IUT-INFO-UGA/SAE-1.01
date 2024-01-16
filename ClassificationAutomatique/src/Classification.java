import java.io.FileInputStream;
import java.io.FileWriter;
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
		/*ArrayList<Categorie> Categories = new ArrayList<>();
		Categories.add(new Categorie("Culture", "./lexiques/CULTURE"));
		Categories.add(new Categorie("Economie", "./lexiques/ECONOMIE"));
		Categories.add(new Categorie("Environnement-Sciences", "./lexiques/ENVIRONNEMENT-SCIENCES"));
		Categories.add(new Categorie("Polithique", "./lexiques/POLITHIQUE"));
		Categories.add(new Categorie("Sport", "./lexiques/SPORTS"));

        ArrayList<PaireChaineEntier> catt = new ArrayList<>();
		for (int i = 0; i < Categories.size(); i++) {
			catt.add(new PaireChaineEntier(Categories.get(2).getNom(), Categories.get(2).score(depeches.get(i))));
		}
		for (int i = 0; i < Categories.size(); i++) {
			System.out.println(catt.get(i).getEntier());
		}*/
		try{
			final FileWriter file = new FileWriter("nomFichier.txt");
		
			for(int i = 0; i < depeches.size(); ++i){
				
				file.write(depeches.get(i).getId() + ":" + depeches.get(i).getCategorie() + "\n");
				
				
			}
			
			file.close();
		}catch(IOException e){
			e.printStackTrace();
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
