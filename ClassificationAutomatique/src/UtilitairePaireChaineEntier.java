import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;
        while(i < listePaires.size() && listePaires.get(i).getChaine().compareTo(chaine) != 0){
            i++;
        }
        return 0;
    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;
        int entier = 0;
        System.out.println(listePaires.size());
        while(i < listePaires.size()){
            if(listePaires.get(i).getChaine().compareTo(chaine) == 0){
                entier = listePaires.get(i).getEntier();
            }
            i++;
        }
        return entier;
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        int entier_max = 0;
        String chaine_max = "";
        for(int i = 0; i < listePaires.size(); ++i){
            if(listePaires.get(i).getEntier() > entier_max){
                entier_max = listePaires.get(i).getEntier();
                chaine_max = listePaires.get(i).getChaine();
            }
        }
        return chaine_max;
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        return 0;
    }

}
