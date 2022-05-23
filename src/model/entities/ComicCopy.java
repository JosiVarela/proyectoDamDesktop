package model.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class ComicCopy implements Serializable {
    private int idCopy;
    private LocalDate purchaseDate;
    private int state;
    private String observations;
    private String isbn;

    public ComicCopy() {
    }

    public ComicCopy(int idCopy, LocalDate purchaseDate, int state, String observations) {
        this.idCopy = idCopy;
        this.purchaseDate = purchaseDate;
        this.state = state;
        this.observations = observations;
    }

    public ComicCopy(int idCopy, LocalDate purchaseDate, int state, String observations, String isbn) {
        this.idCopy = idCopy;
        this.purchaseDate = purchaseDate;
        this.state = state;
        this.observations = observations;
        this.isbn = isbn;
    }

    public ComicCopy(LocalDate purchaseDate, int state, String observations, String isbn) {
        this.purchaseDate = purchaseDate;
        this.state = state;
        this.observations = observations;
        this.isbn = isbn;
    }

    public int getIdCopy() {
        return idCopy;
    }

    public void setIdCopy(int idCopy) {
        this.idCopy = idCopy;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
