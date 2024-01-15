import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private ArrayList<PaireChaineEntier> lexique = new ArrayList<>(); //le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }


    public String getNom() {
        return nom;
    }


    public  ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }


    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {
        try{
            //lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                String ligne = scanner.nextLine();
                int index = ligne.indexOf(":");
                String chaine = ligne.substring(0, index);
                int note = Integer.parseInt(ligne.substring(index + 1).trim());
                PaireChaineEntier paireChaineEntier = new PaireChaineEntier();
                PaireChaineEntier paire = new PaireChaineEntier();
                paire.setChaine(chaine);
                paire.setEntier(note);
                lexique.add(paire);
            }
            scanner.close();
            System.out.println();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        return 0;
    }


}
