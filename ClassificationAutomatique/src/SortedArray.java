import java.util.ArrayList;

public class SortedArray {
    private ArrayList<PaireChaineEntier> array;

    public SortedArray() {
        this.array = new ArrayList<>();
    }

    public int size() {
        return this.array.size();
    }

    /**
     * The function returns the index of a given string in an array, using binary search.
     * 
     * @param chaine The parameter "chaine" is a String that represents the value we are searching for in the array.
     * @return The method is returning an integer value. If the given string "chaine" is found in the array, it returns the
     * index of the string in the array. If the string is not found, it returns -1.
     */
    public int indexOf(String chaine) {
        if (this.array.isEmpty()) return -1;
        if (this.array.get(this.array.size() - 1).getChaine().compareTo(chaine) < 0) return -1;
        int inf = 0;
        int sup = this.array.size() - 1;
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


/**
 * The function `get` returns the `PaireChaineEntier` object from the `array` list that matches the given `chaine` string.
 * 
 * @param chaine The parameter "chaine" is a string that represents the value we are searching for in the array.
 * @return The method is returning an object of type `PaireChaineEntier`.
 */
    public PaireChaineEntier get(String chaine) {
        if (this.array.get(this.array.size() - 1).getChaine().compareTo(chaine) < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int inf = 0;
        int sup = this.array.size() - 1;
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

/**
 * The function `insereAt` inserts a `PaireChaineEntier` object at a specified index in an ArrayList.
 * 
 * @param index The index parameter represents the position at which the PaireChaineEntier object should be inserted into
 * the array.
 * @param paireChaineEntier The parameter `paireChaineEntier` is of type `PaireChaineEntier`, which is a custom class or
 * data type. It represents an object that contains a pair of a string and an integer value.
 */
    private void insereAt(int index, PaireChaineEntier paireChaineEntier) {
        if (this.array.isEmpty()) {
            this.array.add(paireChaineEntier);
            return;
        }
        ArrayList<PaireChaineEntier> newArray = new ArrayList<>();
        for (int j = 0; j < index; j++) {
            newArray.add(this.array.get(j));
        }
        newArray.add(paireChaineEntier);
        for (int j = index; j < this.array.size(); j++) {
            newArray.add(this.array.get(j));
        }
        this.array = newArray;
    }

    /**
     * The addSorted function adds a PaireChaineEntier object to an ArrayList in a sorted manner based on the value of the
     * "chaine" attribute.
     * 
     * @param paireChaineEntier The parameter `paireChaineEntier` is of type `PaireChaineEntier`, which is a class that
     * represents a pair of a string (`chaine`) and an integer (`entier`).
     */
    public void addSorted(PaireChaineEntier paireChaineEntier) {
        if (this.array.isEmpty()) {
            this.array.add(paireChaineEntier);
            return;
        } else if (this.array.get(0).getChaine().compareTo(paireChaineEntier.getChaine()) > 0) {
            ArrayList<PaireChaineEntier> array = new ArrayList<>();
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
