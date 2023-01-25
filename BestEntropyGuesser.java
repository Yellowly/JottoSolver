import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Type of guesser that uses the entropy equation SUM(probability*logBase2(1/probability)) to make guesses which maximize information
 */
public class BestEntropyGuesser implements Guesser {

    /** first best guesses for a game of jotto with 5 letters in each word */
    private static final List<String> firstBestGuesses = firstBestGuesses();

    /** remaining possible guesses in this game of Jotto */
    private Dictionary possibleGuesses;

    /** stores the log of 2 */
    private static final double logOf2 = Math.log(2);
    /** stores an epsilon to prevent divide by 0 errors when calculating information */
    private static final double epsilon = 0.0000000000000001;

    /** the size of the original full dictionary of possible words given by Jotto */
    private int originalDictionarySize;

    /** used to optimize the first possible guess depending on if we are only using the hidden words rather than full dictionary when guessing */
    private boolean cheat;

    /**
     * Constructor for this guesser
     * @param dictionary - all the possible words which could be guessed
     */
    public BestEntropyGuesser(Dictionary dictionary){
        originalDictionarySize = dictionary.size();
        this.possibleGuesses=dictionary;
    }

    /**
     * Constructor for this guesser that defines if we should optimize the first guess based on the hidden words dictionary
     * @param dictionary - Possible words which could be guessed
     * @param cheat - do we optimize first guess for hidden words or not?
     */
    public BestEntropyGuesser(Dictionary dictionary, boolean cheat){
        originalDictionarySize = dictionary.size();
        this.possibleGuesses=dictionary;
        this.cheat=cheat;
    }


    /**
     * pre-calculated first best guesses to make guessing faster
     * @return - the first best guesses for the full 8k possible words dictionary
     */
    private static List<String> firstBestGuesses(){
        List<String> res = new ArrayList<>();
        res.add("ARETS");
        res.add("ASTER");
        res.add("EARST");
        res.add("RATES");
        res.add("REAST");
        res.add("RESAT");
        res.add("STARE");
        res.add("STEAR");
        res.add("STRAE");
        res.add("TARES");
        res.add("TASER");
        res.add("TEARS");
        res.add("TERAS");
        return res;
    }

    /**
     * gives one of the best possible valid guesses baesd on the entropy equation
     * previousTurns - previous turns in this game of jotto
     * @return - mathematically optimal word using entropy equation when looking ahead by 1 turn
     */
    //7.621 turns avg
    public String guessValidGuess(List<Turn> previousTurns) {
        if(previousTurns.size()>0){
            Turn previousTurn = previousTurns.get(previousTurns.size()-1);
            possibleGuesses = new Dictionary(testWord(previousTurn.getGuess(),possibleGuesses)[previousTurn.getHint()]);
            System.out.println(possibleGuesses.size());
        }
        else if(cheat) return "BARED";
        else
            return firstBestGuesses.get((int)Math.random()*firstBestGuesses.size());
        List<String> bestWords = new ArrayList<>();
        double bestEval = -1;
        for(String word : possibleGuesses) {
            double eval = evalWord(word, possibleGuesses);
            if(eval>bestEval) {
                bestWords=new ArrayList<>();
                bestEval=eval;
                bestWords.add(word);
            }
            else if(eval==bestEval) bestWords.add(word);
        }
        System.out.println(bestWords);
        return bestWords.get((int)(Math.random()*(bestWords.size())));
    }


    /**
     * evaluates how good a guess is, higher the evaluation the better the guess
     * SUM(probability * logBase2(1/probability))
     * @param word - word/guess to be evaluated
     * @param dictionary - remaining possible words
     * @return - a double representing the sum of the probabilities of an outcome of a guess * the amount of information this outcome would give
     */
    private static double evalWord(String word, Dictionary dictionary) {
        double sum = 0.0;
        List<String>[] possibilities = testWord(word,dictionary);
        for(int i=0;i<possibilities.length;i++) {
            double probability = (double)possibilities[i].size()/dictionary.size();
            if(probability==0) probability= epsilon;
            sum+=(probability*(Math.log(1/probability)/logOf2));
        }
        return sum;
    }

    /**
     * finds the remaining possible words for every possible hint of a guess
     * @param word - guess to be tested
     * @param dictionary - dictionary of remaining possible words
     * @return - list of possible guesses in an array where the index is what hint would be required for the tested guess to give those possible guesses
     */
    private static List<String>[] testWord(String word, Dictionary dictionary) {
        List<String>[] res = new List[6];
        for(int i=0;i<6;i++) {
            res[i]=new LinkedList<>();
        }
        for(String test : dictionary) {
            res[Jotto.numOfSharedLetters(word, test)].add(test);
        }
        return res;
    }
}
