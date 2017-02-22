package com.kamilszuba.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kamil Szuba on 2017-02-21.
 */
public class LibraryTest {
    Library library;

    @Before
    public void setUp() throws Exception {
        System.out.println("\nStarting new test!");
        library = new Library();
        library.addBookToLibrary("Pan Tadeusz", "Adam Mickiewicz", 1991);
        library.addBookToLibrary("Pan Tadeusz", "Adam Mickiewicz", 1991);
        library.addBookToLibrary("Balladyna", "Juliusz Slowacki", 1991);
        library.addBookToLibrary("Zlodziej czasu", "Terry Pratchett", 1991);
        library.addBookToLibrary("Kolory magii", "Terry Pratchett", 1991);
        library.addBookToLibrary("Fight Club", "Chuck Palahniuk", 1991);

    }

    @After
    public void tearDown() throws Exception {
        library.cleanLibrary();
    }

    @Test
    public void lendABookAvailableInLibrary() throws Exception {
       assertTrue(library.lendABook(2, "Kamil Szuba"));
    }
    @Test
    public void lendABookUnavailableInLibrary() throws Exception {
        assertTrue(!(library.lendABook(8, "Kamil Szuba")));

    }



}