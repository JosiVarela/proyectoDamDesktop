package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Collection implements Serializable {
    private int id;
    private String title;
    private Date publishDate;
    private String argument;
    private int comicQuantity;


    public Collection() {
    }

    public Collection(int id, String title, Date publishDate, String argument) {
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
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
}
