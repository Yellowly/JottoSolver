import java.util.List;

/** Guesser is responsible for guessing a valid guess in Jotto game */
public interface Guesser {
    /**
     * Returns a valid guess for a Jotto game given previous turns of guesses and hints
     * @param previousTurns - All of the previous turns of this Jotto game
     * @return - A valid Jotto guess under the conditions
     */
    public String guessValidGuess(List<Turn> previousTurns);
}
