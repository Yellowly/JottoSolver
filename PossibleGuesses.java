import java.util.LinkedList;
import java.util.List;

/**
 * Dictionray used to keep track of all remaining possible guesses in a game of
 * Jotto
 */
public class PossibleGuesses extends Dictionary {
    /** removes a word from possible guesses at the index */
    public String remove(int idx) {
        return words.remove(idx);
    }

    /** finds and removes a word from possible guesses */
    public String remove(String word) {
        return words.remove(indexOf(word));
    }

    /**
     * updates possible guesses when a new turn is played by removing invalid words
     */
    public List<String> update(Turn newTurn) {
        // i hate this
        List<Turn> temp = new LinkedList<>();
        temp.add(newTurn);
        int idx = 0;
        while(idx<words.size()) {
            if (!Jotto.isValidGuess(this.words.get(idx), temp))
                this.remove(idx);
            else idx++;
        }
        return this.words;
    }
}
