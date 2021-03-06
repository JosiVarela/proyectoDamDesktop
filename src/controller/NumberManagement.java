package controller;

import daos.ComicNumberDAO;
import model.entities.ComicNumber;

import java.io.IOException;

public class NumberManagement {
    public static Object[] existsNumber(String isbn) throws IOException {
        return new ComicNumberDAO().existsNumber(isbn);
    }
    public static Object[] getComicNumber(String isbn) throws IOException, ClassNotFoundException {
        return new ComicNumberDAO().getComicNumber(isbn);
    }

    public static String insertComicNumber(ComicNumber comicNumber) throws IOException {
        return new ComicNumberDAO().insertComicNumber(comicNumber);
    }
    public static String updateComicNumber(ComicNumber comicNumber) throws IOException {
        return new ComicNumberDAO().updateComicNumber(comicNumber);
    }

    public static String deleteComicNumber(String isbn) throws IOException {
        return new ComicNumberDAO().deleteComicNumber(isbn);
    }

    public static Object[] getComicNumbers() throws IOException, ClassNotFoundException {
        return new ComicNumberDAO().getNumbers();
    }

    public static Object[] getNumbersByName(String name) throws IOException, ClassNotFoundException {
        return new ComicNumberDAO().getNumbersByName(name);
    }

    public static Object[] getNumbersByNameCol(String name, int idCol) throws IOException, ClassNotFoundException {
        return new ComicNumberDAO().getNumbersByNameCol(name, idCol);
    }

    public static Object[] getNumbersByColName(String name) throws IOException, ClassNotFoundException {
        return new ComicNumberDAO().getNumbersByColName(name);
    }
}
