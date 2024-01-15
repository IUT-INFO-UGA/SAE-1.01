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
            final FileInputStream file = new FileInputStream(nomFichier);
            final Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                final String ligne = scanner.nextLine();
                final int index = ligne.indexOf(":");
                final String chaine = ligne.substring(0, index);
                final int note = Integer.parseInt(ligne.substring(index + 1).trim());
                final PaireChaineEntier paire = new PaireChaineEntier();
                paire.setChaine(chaine.toLowerCase());
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
        int score = 0;
        for(int i=0 ; i < lexique.size() ; i++){
            for (int j = 0; j < d.getMots().size(); j++) {
                System.out.println(lexique.get(i).getChaine()+'='+d.getMots().get(j));
                if(lexique.get(i).getChaine().equals(d.getMots().get(j)))
                    score+= lexique.get(i).getEntier();
            }
        }
        return score;
    }
}
