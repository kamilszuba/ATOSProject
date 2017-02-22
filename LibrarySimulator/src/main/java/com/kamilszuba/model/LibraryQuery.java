package com.kamilszuba.model;

/**
 * Created by Kamil Szuba on 2017-02-20.
 */
public class LibraryQuery {
    private String title;
    private String author;
    private Integer year;

    private LibraryQuery(QueryBuilder query) {
        this.title = query.title;
        this.author = query.author;
        this.year = query.year;
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getYear() {
        return year;
    }
    public static class QueryBuilder {
        private String title;
        private String author;
        private Integer year;

        public QueryBuilder(){

        }

        public QueryBuilder title(String title) {
            this.title = title;
            return this;
        }

        public QueryBuilder author(String author) {
            this.author = author;
            return this;
        }

        public QueryBuilder year(int year) {
            this.year = Integer.valueOf(year);
            return this;
        }

        public LibraryQuery buildQuery() {
            return new LibraryQuery(this);
        }
    }
}
