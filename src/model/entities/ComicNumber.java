package model.entities;

import java.io.Serializable;
import java.util.List;

public class ComicNumber implements Serializable {
    private String isbn;
    private int comicNumber;
    private String name;
    private String cover;
    private String argument;
    private int copies;
    private int colId;
    private byte[] image;
    private List<ComicCopy> comicCopyList;

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

    public ComicNumber(String name) {
        this.name = name;
    }

    public ComicNumber(String isbn, int comicNumber, String cover, int colId, String name, String argument) {
        this.isbn = isbn;
        this.comicNumber = comicNumber;
        this.cover = cover;
        this.colId = colId;
        this.name = name;
        this.argument = argument;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public List<ComicCopy> getComicCopyList() {
        return comicCopyList;
    }

    public void setComicCopyList(List<ComicCopy> comicCopyList) {
        this.comicCopyList = comicCopyList;
    }
}
