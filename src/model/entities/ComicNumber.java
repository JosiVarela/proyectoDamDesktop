package model.entities;

import java.io.Serializable;
import java.util.Date;

public class ComicNumber implements Serializable {
    String isbn;
    int comicNumber;
    String cover;
    int copies;

    public ComicNumber() {
    }

    public ComicNumber(String isbn, int comicNumber, String cover, int copies) {
        this.isbn = isbn;
        this.comicNumber = comicNumber;
        this.cover = cover;
        this.copies = copies;
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
}
