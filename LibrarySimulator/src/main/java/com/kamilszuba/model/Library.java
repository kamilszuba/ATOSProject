package com.kamilszuba.model;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

/**
 * Created by Kamil Szuba on 2017-02-20.
 */
public class Library {
    private static AtomicLong idCounter = new AtomicLong();
    private Map<Long, Book> availableBooks;
    private Map<Long, Book> lentBooks;

    private static Long createID() {

        return idCounter.getAndIncrement();
    }


    public Library() {
        availableBooks = new HashMap<>();
        lentBooks = new HashMap<>();
    }

    public void cleanLibrary() {
        availableBooks.clear();
        lentBooks.clear();
        idCounter = new AtomicLong();

    }

    public void addBookToLibrary(String title, String author, int year) {
        if (title == null || author == null) {
            System.out.println("Could not add this book.");
            return;
        }
        String titleOfTheBook = title.trim();
        String authorOfTheBook = author.trim();

        if (titleOfTheBook.isEmpty() || authorOfTheBook.isEmpty()) {
            System.out.println("Could not add this book.");
            return;
        }

        Book book = new Book(titleOfTheBook, authorOfTheBook, year);
        availableBooks.put(createID(), book);
    }

    public void removeBookFromLibrary(long bookId) {
        Long id = Long.valueOf(bookId);
        if (availableBooks.containsKey(id)) {
            availableBooks.remove(id);
        } else {
            System.out.println("Could not remove this book.");
        }
    }

    public boolean lendABook(long bookId, String lenderName) {
        Long id = Long.valueOf(bookId);
        if (lenderName == null || lenderName.isEmpty()) {
            System.out.println("You must specify lenders name!");
            return false;
        }
        if (availableBooks.containsKey(id)) {
            Book bookToLend = availableBooks.get(id);
            bookToLend.setLenderName(lenderName);
            lentBooks.put(id, bookToLend);
            availableBooks.remove(id);
            return true;

        } else {
            System.out.println("Sorry. You can't lend this book.");
            return false;
        }
    }

    public void showBookDeteails(long bookId) {
        Long id = Long.valueOf(bookId);
        if (availableBooks.containsKey(id)) {
            System.out.println(availableBooks.get(id) + " it is available");
        } else if (lentBooks.containsKey(id)) {
            Book lentBook = lentBooks.get(id);
            System.out.println(lentBook + "book is lent by " + lentBook.getLenderName());
        } else {
            System.out.println("Can't print book details. Couldn't find book You requested.");
        }

    }

    public void searchForBook(LibraryQuery query) {

        boolean isAuthorFieldUsed = query.getAuthor() != null;
        if (isAuthorFieldUsed){
            isAuthorFieldUsed =  !query.getAuthor().trim().isEmpty();
        }
        boolean isTitleFieldUsed = query.getTitle() != null;
        if (isTitleFieldUsed){
            isTitleFieldUsed = !query.getTitle().trim().isEmpty();
        }
        boolean isYearFieldUsed = query.getYear() != null;

        if (!isAuthorFieldUsed && !isTitleFieldUsed && !isYearFieldUsed) {
            System.out.println("There are no search criteria!");
            return;
        }
        List<Predicate<Map.Entry<Long, Book>>> searchPredicates = new ArrayList<>();

        if (isAuthorFieldUsed) {
            String author = query.getAuthor();
            searchPredicates.add((entry) -> entry.getValue().getAuthor().equals(author));
        }
        if (isTitleFieldUsed) {
            String title = query.getTitle();
            searchPredicates.add((entry) -> entry.getValue().getTitle().equals(title));
        }
        if (isYearFieldUsed) {
            Integer year = query.getYear();
            searchPredicates.add((entry) -> entry.getValue().getYear() == year.intValue());
        }
        Predicate<Map.Entry<Long, Book>> fullPredicate = (b) -> true;
        for (Predicate<Map.Entry<Long, Book>> predicate : searchPredicates) {
            fullPredicate = fullPredicate.and(predicate);
        }
        final Predicate<Map.Entry<Long, Book>> searchCriteriaPredicate = fullPredicate;
        availableBooks.entrySet().stream().filter(searchCriteriaPredicate).forEach(entry -> System.out.println("ID: " + entry.getKey() + ", " + entry.getValue()));

    }

    public void showBookList() {
        List<Book> allbooks = new ArrayList();
        allbooks.addAll(availableBooks.values());
        allbooks.addAll(lentBooks.values());
        Set<Book> distinctBooks = new HashSet<>();
        distinctBooks.addAll(allbooks);
        for (Book distinctBook : distinctBooks) {
            int bookCount = Collections.frequency(allbooks, distinctBook);
            int availableBookCount = Collections.frequency(availableBooks.values(), distinctBook);
            System.out.println(distinctBook + ", available " + availableBookCount + " out of " + bookCount);
        }
    }


}
