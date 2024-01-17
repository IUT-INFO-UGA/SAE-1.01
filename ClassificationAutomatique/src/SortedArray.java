import java.util.ArrayList;

public class SortedArray {
    private ArrayList<PaireChaineEntier> array;

    public SortedArray() {
        this.array = new ArrayList<>();
    }

    public int size() {
        return this.array.size();
    }

    public int indexOf(String chaine) {
        if (this.array.isEmpty()) return -1;
        if (this.array.get(size() - 1).getChaine().compareTo(chaine) < 0) return -1;
        int inf = 0;
        int sup = size() - 1;
        while (inf < sup) {
            int m = (inf + sup) / 2;
            if (this.array.get(m).getChaine().compareTo(chaine) >= 0) {
                sup = m;
            } else {
                inf = m + 1;
            }
        }
        if (this.array.get(sup).getChaine().equals(chaine)) {
            return sup;
        } else {
            return -1;
        }
    }


    public PaireChaineEntier get(String chaine) throws ArrayIndexOutOfBoundsException {
        if (this.array.get(size() - 1).getChaine().compareTo(chaine) < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int inf = 0;
        int sup = size() - 1;
        while (inf < sup) {
            int m = (inf + sup) / 2;
            if (this.array.get(m).getChaine().compareTo(chaine) >= 0) {
                sup = m;
            } else {
                inf = m + 1;
            }
        }
        if (this.array.get(sup).getChaine().equals(chaine)) {
            return this.array.get(sup);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public PaireChaineEntier get(int index) {
        return this.array.get(index);
    }

    public void set(int index, int entier) {
        this.array.set(index, new PaireChaineEntier(this.array.get(index).getChaine(), entier));
    }

    private void insereAt(int index, PaireChaineEntier paireChaineEntier) {
        if (this.array.isEmpty()) {
            this.array.add(paireChaineEntier);
            return;
        }
        final ArrayList<PaireChaineEntier> newArray = new ArrayList<>();
        for (int j = 0; j < index; j++) {
            newArray.add(this.array.get(j));
        }
        newArray.add(paireChaineEntier);
        for (int j = index; j < this.array.size(); j++) {
            newArray.add(this.array.get(j));
        }
        this.array = newArray;
    }

    public void addSorted(PaireChaineEntier paireChaineEntier) {
        if (this.array.isEmpty()) {
            this.array.add(paireChaineEntier);
            return;
        } else if (this.array.get(0).getChaine().compareTo(paireChaineEntier.getChaine()) > 0) {
            final ArrayList<PaireChaineEntier> array = new ArrayList<>();
            array.add(paireChaineEntier);
            array.addAll(this.array);
            this.array = array;
            return;
        } else if (this.array.get(this.array.size() - 1).getChaine().compareTo(paireChaineEntier.getChaine()) < 0) {
            this.array.add(paireChaineEntier);
            return;
        }

        int i = 0;
        while (i < this.array.size()) {
            if (this.array.get(i).getChaine().compareTo(paireChaineEntier.getChaine()) > 0) {
                insereAt(i, paireChaineEntier);
                return;
            }
            i++;
        }
    }

    public ArrayList<PaireChaineEntier> getArray() {
        return array;
    }
}