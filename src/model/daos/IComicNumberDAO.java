package model.daos;

import model.entities.ComicNumber;

import java.io.IOException;

public interface IComicNumberDAO {
    Object[] existsNumber(String isbn) throws IOException;
    Object[] getComicNumber(String isbn) throws IOException, ClassNotFoundException;
}
