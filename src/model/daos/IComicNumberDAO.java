package model.daos;

import model.entities.ComicNumber;

import java.io.IOException;

public interface IComicNumberDAO {
    Object[] existsNumber(String isbn) throws IOException;
    Object[] getComicNumber(String isbn) throws IOException, ClassNotFoundException;
    String insertComicNumber(ComicNumber comicNumber) throws IOException;
    String updateComicNumber(ComicNumber comicNumber) throws IOException;
    String deleteComicNumber(String isbn) throws IOException;
    Object[] getNumbers() throws IOException, ClassNotFoundException;
    Object[] getNumbersByName(String name) throws IOException, ClassNotFoundException;
    Object[] getNumbersByNameCol(String name, int idCol) throws IOException, ClassNotFoundException;
    Object[] getNumbersByColName(String name) throws IOException, ClassNotFoundException;
}
