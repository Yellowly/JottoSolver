import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dictionary implements Iterable<String> {
    /** PRECONDITION: always sorted
     * List of valid Jotto words */
    protected List<String> words;


    /**
     * Default constructor for dictionary
     * loads 5letterwords.txt to the dictionary
     */
    public Dictionary(){
        words=new ArrayList<String>();
        try {
            fileToDictionary("5letterwords.txt");
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

    }

    /**
     * PRECONDITION : the text file is sorted alphabetically
     * Consturctor that creates a dictionary of words from a text file
     * @param path - filepath of txt file
     */
    public Dictionary(String path){
        words=new ArrayList<String>();
        try {
            fileToDictionary(path);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * PRECONDITION : words list is sorted
     * constructor that creates a dictionary which uses a list of words
     * Changes to this dictionary will result in changes to the list of words given
     * @param words - list of words to use in the dictionary
     */
    public Dictionary(List<String> words){
        this.words=words;
    }


    /**
     * Loads a txt file line by line to the dictionary
     * @param path - filepath of txt file
     */
    private void fileToDictionary(String path) throws Exception {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            if (isValidWord(line)) {
                words.add(line.toUpperCase());
            }
            line = br.readLine();
        }
        fr.close();
    }


    /**
     * Find the number of times a string is found in another string
     * helper method for isValidWord
     * @param s    - string that is being searched
     * @param find - string to search for
     * @return integer number of how many times "find" is in "s"
     */
    public static int numOfOccurences(String s, String find) {
        if (s.contains(find))
            return 1 + numOfOccurences(s.substring(s.indexOf(find) + 1), find);
        return 0;
    }

    /**
     * Returns if this word has no repeating letters and has 5 letters
     * @param word - word to check is valid
     * @return - if the word has no repeating letters and has 5 letters
     */
    public static boolean isValidWord(String word){
        if(word.length()!=5) return false;
        for (int i = 0; i < word.length(); i++) {
            if (numOfOccurences(word, word.substring(i, i + 1)) > 1)
                return false;
        }
        return true;
    }

    /**
     * PRECONDITION - words array is sorted
     * @param word - word to check if in dictionary
     * @return -
     */
    public boolean contains(String word){
        return indexOf(word)>-1;
    }

    /**
     * PRECONDITION - words array is sorted
     * Binary search to find the index of a string in words dictionary
     * @return - index of an element using binary search
     */
    public int indexOf(String word){
        return indexOf(word,0,words.size()-1);
    }

    /**
     * recursive helper method for indexOf
     * @param word - word to search for
     * @param left - left bound for subarray
     * @param right - right bound for subarray
     * @return - index of the word in words dictionary, -1 if not found
     */
    private int indexOf(String word, int left, int right){
        if(right<left) return -1;
        int idx = left+(right-left)/2; //midpoint of left and right, idx of word we're checking
        if(words.get(idx).equals(word)) return idx;
        if(words.get(idx).compareTo(word)<0) return indexOf(word,idx+1,right);
        return indexOf(word,left,idx-1);
    }

    /**
     * Accessor method for a word in this dictionary
     * @param idx - index that the word is at
     * @return - the word at this index of the dictionray
     */
    public String getWord(int idx){
        return words.get(idx);
    }

    /**
     * gets a random word out of this dictionary
     * @return - a random word from this dictionray
     */
    public String getRandomWord(){
        return words.get((int)(Math.random()*words.size()));
    }

    /**
     * returns the number of words in this dictionary
     * @return - size of this dictionary
     */
    public int size() { return words.size(); }

    /**
     * gets this dictionary as a string
     * @return - a string with all the words in this dictionary
     */
    public String toString(){
        return words.toString();
    }

    /**
     * instantiates an iterator that iterates through this dictionary
     * @return
     */
    public Iterator<String> iterator(){
        return new DictionaryIterator(this);
    }

    /**
     * defines Dictionary's iterator
     */
    private class DictionaryIterator implements Iterator<String>{
        /** dictionary which this iterator is associated with */
        private Dictionary obj;
        /** current index this iterator is at */
        private int idx;
        /** constructor that creates a dictionary iterator for a given dictionary */
        DictionaryIterator(Dictionary obj){
            this.obj=obj;
            this.idx=0;
        }
        /** are there still words in this dictionary that we haven't iterated through yet? */
        public boolean hasNext() {
            if(idx<obj.size()) return true;
            return false;
        }
        /** gets the word at index and then iterates the index */
        public String next() {
            return obj.getWord(idx++);
        }
    }
}
