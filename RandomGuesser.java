import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** Guesser is responsible for guessing a valid guess in Jotto game */
public class RandomGuesser implements Guesser {

    /** the remaining possible guesses in this game of jotto */
    private Dictionary possibleGuesses;

    /**
     * Constructor for this guesser that
     * @param dictionary
     */
    public RandomGuesser(Dictionary dictionary){
        this.possibleGuesses=dictionary;
    }


    //8.043 turns avg
    /**
     * Returns a valid guess for a Jotto game given previous turns of guesses and hints
     * @param previousTurns - All of the previous turns of this Jotto game
     * @return - A valid Jotto guess under the conditions
     */
    public String guessValidGuess(List<Turn> previousTurns){
        if(previousTurns.size()>0) updatePossibleGuesses(previousTurns);
        return possibleGuesses.getRandomWord();
    }

    /**
     * updates the remaining possible guesses
     * this method should be called every time a new guess is made
     * @param previousTurns - all the previous turns
     */
    private void updatePossibleGuesses(List<Turn> previousTurns){
        Turn previousTurn = previousTurns.get(previousTurns.size()-1);
        List<String> validWords = new ArrayList<>();
        int idx = 0;
        for(String word : possibleGuesses){
            if(Jotto.numOfSharedLetters(word,previousTurn.getGuess())==previousTurn.getHint()) validWords.add(word);
        }
        possibleGuesses = new Dictionary(validWords);
    }
}
