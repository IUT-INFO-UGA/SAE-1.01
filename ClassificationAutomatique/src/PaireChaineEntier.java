public class PaireChaineEntier {
    private String chaine;
    private int entier; 

    public PaireChaineEntier(String chaine, int entier){
        this.chaine = chaine;
        this.entier = entier;
    }
    
    public void setChaine(String chaine){
        this.chaine = chaine;
    }
    public String getChaine(){
        return this.chaine;
    }
    public void setEntier(int entier){
        this.entier = entier;
    }
    public int getEntier(){
        return this.entier;
    }
}
