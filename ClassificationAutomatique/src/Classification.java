import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
            System.out.println("Erreur lors de l'écriture du fichier " + nomFichier);

        }
        return depeches;
    }

    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
		ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
		for (int i = 0; i < depeches.size(); ++i) {
			if (depeches.get(i).getCategorie().equals(categorie)) {
				for (int j = 0; j < depeches.get(i).getMots().size(); ++j) {
					if (UtilitairePaireChaineEntier.indicePourChaine(resultat, depeches.get(i).getMots().get(j)) == -1)
						resultat.add(new PaireChaineEntier(depeches.get(i).getMots().get(j), 0));
				}
			}
		}
		return resultat;
	}

    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories,
            String nomFichier) {
        String out = "";
        ArrayList<PaireChaineEntier> detected = new ArrayList<>();
        for (int i = 0; i < depeches.size(); i++) {
            PaireChaineEntier max = new PaireChaineEntier("no data", -1);
            for (int j = 0; j < categories.size(); j++) {
                final PaireChaineEntier tmp = new PaireChaineEntier(categories.get(j).getNom(),
                        categories.get(j).score(depeches.get(i)));
                if (tmp.getEntier() > max.getEntier()) {
                    max = tmp;
                }
            }
            final PaireChaineEntier selectCat = max;
            out += "Depeche " + i + " : " + selectCat.getChaine() + "\n";
            if (depeches.get(i).getCategorie().equals(selectCat.getChaine())) {
                int h = 0;
                while (h < detected.size() && detected.get(h).getChaine() != selectCat.getChaine()) {
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

    public static void calculScores(ArrayList<Depeche> depeches, String categorie,
            ArrayList<PaireChaineEntier> dictionnaire) {
        for (int i = 0; i < dictionnaire.size(); ++i) {
            for (int j = 0; j < depeches.size(); ++j) {
                if (depeches.get(j).getContenu().indexOf(dictionnaire.get(i).getChaine()) == 0) {
                    dictionnaire.get(i).setEntier(dictionnaire.get(i).getEntier() + 1);
                } else {
                    dictionnaire.get(i).setEntier(dictionnaire.get(i).getEntier() - 1);
                }
            }
        }
    }

    public static int poidsPourScore(int score) {
        if (score < 0)
            return 0;
        else if (score < 20)
            return 1;
        else if (score < 30)
            return 2;
        else
            return 3;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorieName, String nomFichier) {
        final ArrayList<PaireChaineEntier> dico = initDico(depeches, categorieName);
        calculScores(depeches, categorieName, dico);
        for (int i = 0; i < dico.size(); ++i) {
            dico.get(i).setEntier(poidsPourScore(dico.get(i).getEntier()));
        }
        // Ecriture du fichier
        try {
            FileOutputStream file = new FileOutputStream(nomFichier);
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < dico.size(); ++i) {
                pw.println(dico.get(i).getChaine() + " : " + dico.get(i).getEntier());
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier " + nomFichier);
        }
    }

    public static void main(String[] args) {

        // Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }

        ArrayList<Categorie> categories = new ArrayList<>();
        categories.add(new Categorie("CULTURE", "./lexiques/CULTURE"));
        categories.add(new Categorie("ECONOMIE", "./lexiques/ECONOMIE"));
        categories.add(new Categorie("ENVIRONNEMENT-SCIENCES", "./lexiques/ENVIRONNEMENT-SCIENCES"));
        categories.add(new Categorie("POLITIQUE", "./lexiques/POLITIQUE"));
        categories.add(new Categorie("SPORTS", "./lexiques/SPORTS"));

        ArrayList<PaireChaineEntier> catt = new ArrayList<>();
        for (int i = 0; i < depeches.size(); i++) {
            catt.add(new PaireChaineEntier(categories.get(2).getNom(), categories.get(2).score(depeches.get(i))));
        }
        System.out.println("categorie de la depeche 2: ");
        System.out.println(UtilitairePaireChaineEntier.chaineMax(catt));

        classementDepeches(depeches, categories, "testClassment.txt");
    }

}
