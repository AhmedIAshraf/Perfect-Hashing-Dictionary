package org.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionaryTest {
    @Test
    public void testInsertion() {
        Dictionary<String> dictionary = new Dictionary<>(1);
        dictionary.insert("apple");
    }
}
