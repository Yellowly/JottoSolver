/**
 * Generates, keeps track, and gives hints for a hidden word
 */
public interface WordMonarch {
    /**
     * Returns a hint given a guess
     * PRECONDITION: guess ia a valid Jotto guess
     * @param validGuess - A valid Jotto guess from the guesser
     * @return - The number of letters in common between validGuess and hiddenWord
     */
    public int giveHint(String validGuess);

}
