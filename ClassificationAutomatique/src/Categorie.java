import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Categorie {

    final private String nom; // le nom de la catégorie p.ex : sport, politique,...
    final private SortedArray lexique = new SortedArray(); // le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }

    // constructeur fonctionnel
    public Categorie(String nom, String nomFichier) {
        this.nom = nom;
        initLexique(nomFichier);
    }

    public String getNom() {
        return nom;
    }

    public SortedArray getLexique() {
        return lexique;
    }

    // initialisation du lexique de la catégorie à partir du contenu d'un fichier
    // texte
    public void initLexique(String nomFichier) {
        try {
            // lecture du fichier d'entrée
            final FileInputStream file = new FileInputStream(nomFichier);
            final Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                final String ligne = scanner.nextLine();
                final int index = ligne.indexOf(":");
                final String chaine = ligne.substring(0, index);
                final int note = Integer.parseInt(ligne.substring(index + 1).trim());
                final PaireChaineEntier paire = new PaireChaineEntier();
                paire.setChaine(chaine.toLowerCase());
                paire.setEntier(note);
                lexique.addSorted(paire);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier " + nomFichier);

        }
    }

    // calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        int score = 0;
        for (final String mot : d.getMots()) {
            try {
                score += lexique.get(mot).getEntier();
            } catch (ArrayIndexOutOfBoundsException ignored){}
        }
        return score;
    }
}
