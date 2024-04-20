public class Dictionary {
    private final int method; // if 1 --> linear, 2 --> quadratic

    public Dictionary(int method) {
        this.method = method;
    }
    /**
     * Inserts a word into the dictionary.
     *
     * @param word The word to be inserted.
     * @return {@code true} if the word was successfully inserted, {@code false} if it already exists.
     */
    public boolean insert(String word) {
        return true;
    }

    /**
     * Deletes a word from the dictionary.
     *
     * @param word The word to be deleted.
     * @return {@code true} if the word was successfully deleted, {@code false} if it doesn't
     * exist in the dictionary.
     */
    public boolean delete(String word) {
        return true;
    }

    /**
     * Searches for a word in the dictionary.
     *
     * @param word The word to be searched.
     * @return {@code true} if the word is found in the dictionary, {@code false} if it doesn't
     * exist in the dictionary.
     */
    public boolean search(String word) {
        return true;
    }

    /**
     * Inserts an array of words read from file into the dictionary.
     *
     * @param words The array of words to be inserted.
     */
    public void batchInsert(String[] words) {

    }

    /**
     * Deletes an array of words read from file from the dictionary.
     *
     * @param words The array of words to be deleted.
     */
    public void batchDelete(String[] words) {

    }
}
