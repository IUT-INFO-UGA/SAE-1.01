import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    final private String nom;
    private ArrayList<PaireChaineEntier> lexique;

    public Categorie(String nom) {
        this.nom = nom;
    }

    public Categorie(String nom, String nomFichier) {
        this.nom = nom;
        this.initLexique(nomFichier);
    }


    public String getNom() {
        return nom;
    }


/**
 * The function "initLexique" initializes a SortedArray object by reading data from a file and adding it to the array in
 * sorted order.
 * 
 * @param nomFichier The parameter "nomFichier" is a String that represents the name of the file from which the lexique
 * (vocabulary) will be initialized.
 */
    public void initLexique(String nomFichier) {
        this.lexique = new ArrayList<>();
        try {
            final FileInputStream file = new FileInputStream(nomFichier);
            final Scanner scanner = new Scanner(file);
            String ligne = scanner.nextLine();
            this.lexique.add(new PaireChaineEntier(ligne.split(":")[0], Integer.parseInt(ligne.split(":")[1])));
            do {
                ligne = scanner.nextLine();
                this.lexique.add(new PaireChaineEntier(ligne.split(":")[0], Integer.parseInt(ligne.split(":")[1])));
            } while (scanner.hasNextLine());
            scanner.close();
        } catch (IOException e) {
            System.out.println("error during reading");

        }
    }

// The `score` method calculates the score of a given `Depeche` object based on the entier value of each word in the
// `Depeche` object. 
    public int score(Depeche d) {
        int score = 0;
        for (int i =0; i<d.getMots().size(); i++) {
            try {
                score += this.lexique.get(i).getEntier();
            } catch (ArrayIndexOutOfBoundsException ignored) { }
        }
        return score;
    }
}
