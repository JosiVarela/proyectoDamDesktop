package controller;

import model.daos.NumberCopiesDAO;
import model.entities.ComicCopy;

import java.io.IOException;

public class NumberCopiesManagement {
    public static String insertCopy(ComicCopy comicCopy) throws IOException {
        return new NumberCopiesDAO().insertCopy(comicCopy);
    }

    public static Object[] existsCopy(int id) throws IOException {
        return new NumberCopiesDAO().existsCopy(id);
    }
}
