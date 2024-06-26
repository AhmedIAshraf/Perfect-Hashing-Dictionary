package org.dictionary;

public class Dictionary <T> {
    private final int methodOfHash;
    private final HashTable<String> hashTable;

    public Dictionary(int method) {
        methodOfHash = method;
        if (method == 1)
            hashTable = new LinearSpace<>();
        else
            hashTable = new QuadraticSpace<>(false);
    }
    /**
     * Inserts a word into the dictionary.
     *
     * @param word The word to be inserted.
     * @return {@code true} if the word was successfully inserted, {@code false} if it already exists.
     */
    public boolean insert(String word) {
        boolean linear = methodOfHash == 1;
        return hashTable.insert(word,linear);
    }

    /**
     * Deletes a word from the dictionary.
     *
     * @param word The word to be deleted.
     * @return {@code true} if the word was successfully deleted, {@code false} if it doesn't
     * exist in the dictionary.
     */
    public boolean delete(String word) {
        return hashTable.delete(word);
    }

    /**
     * Searches for a word in the dictionary.
     *
     * @param word The word to be searched.
     * @return {@code true} if the word is found in the dictionary, {@code false} if it doesn't
     * exist in the dictionary.
     */
    public boolean search(String word) {
        return hashTable.search(word);
    }

    /**
     * Inserts an array of words read from file into the dictionary.
     *
     * @param words The array of words to be inserted.
     */
    public void batchInsert(String[] words) {
//        Integer [] word_hashcode=new Integer[words.length];
//        for(int i =0;i<words.length;i++) word_hashcode[i]=words[i].hashCode();
        hashTable.batchInsert(words);
    }

    /**
     * Deletes an array of words read from file from the dictionary.
     *
     * @param words The array of words to be deleted.
     */
    public void batchDelete(String[] words) {
//        Integer [] word_hashcode=new Integer[words.length];
//        for(int i =0;i<words.length;i++) word_hashcode[i]=words[i].hashCode();
        hashTable.batchDelete(words);
    }
}