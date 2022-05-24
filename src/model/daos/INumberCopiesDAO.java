package model.daos;

import model.entities.ComicCopy;

import java.io.IOException;

public interface INumberCopiesDAO {
    String insertCopy(ComicCopy comicCopy) throws IOException;
    String updateCopy(ComicCopy comicCopy) throws IOException;
    String deleteCopy(int comicId) throws IOException;
    Object[] existsCopy(int idCopy) throws IOException;
    Object[] getComicCopy(int idCopy) throws IOException, ClassNotFoundException;
}
