package org.dictionary;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {
    Dictionary<String> dictionary = new Dictionary<>(2);
    String[] files = {"list.txt",
                        };
    String path = "C:\\Users\\WORK UEFI\\OneDrive - Alexandria University\\Desktop\\Perfect-Hashing-Dictionary\\Dictionary\\src\\test\\resources\\";

    @Test
    public void testInsertion() {
        assertTrue(dictionary.insert("apple"));
    }

    @Test
    public void testDeletion() {
        assertTrue(dictionary.delete("apple"));
//        assertFalse(dictionary.search("apple"));
    }

    @Test
    public void testSearch() {
        assertFalse(dictionary.search("apple"));
    }
}
