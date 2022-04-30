package model.entities;

import java.io.Serializable;

public class ComicNumber implements Serializable {
    String isbn;
    int comicNumber;
    String name;
    String cover;
    int copies;
    int colId;

    public ComicNumber() {
    }

    public ComicNumber(String isbn, int comicNumber, String name, String cover, int copies, int colId) {
        this.isbn = isbn;
        this.comicNumber = comicNumber;
        this.cover = cover;
        this.copies = copies;
        this.colId = colId;
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getComicNumber() {
        return comicNumber;
    }

    public void setComicNumber(int comicNumber) {
        this.comicNumber = comicNumber;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
