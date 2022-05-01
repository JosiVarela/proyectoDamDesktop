package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Collection implements Serializable {
    private int id;
    private String title;
    private LocalDate publishDate;
    private String argument;
    private int comicQuantity;
    private List<ComicNumber> numberList;


    public Collection() {
    }

    public Collection(int id, String title, LocalDate publishDate, String argument) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.argument = argument;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public int getComicQuantity() {
        return comicQuantity;
    }

    public void setComicQuantity(int comicQuantity) {
        this.comicQuantity = comicQuantity;
    }

    public List<ComicNumber> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<ComicNumber> numberList) {
        this.numberList = numberList;
    }
}
