package model.daos;

import model.entities.ComicCopy;

import java.io.IOException;

public interface INumberCopiesDAO {
    String insertCopy(ComicCopy comicCopy) throws IOException;
    Object[] existsCopy(int idCopy) throws IOException;
}
