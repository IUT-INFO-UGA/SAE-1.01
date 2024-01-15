import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return 0;
    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;
        int entier = 0;
        System.out.println(listePaires.size());
        while(i < listePaires.size()){
            System.out.println(listePaires.get(i).getChaine());
            if(listePaires.get(i).getChaine().compareTo(chaine) == 0){
                entier = listePaires.get(i).getEntier();
            }
            i++;
        }
        return entier;
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        return "SPORTS";
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        return 0;
    }

}
