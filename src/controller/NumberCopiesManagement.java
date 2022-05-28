package controller;

import daos.NumberCopiesDAO;
import model.entities.ComicCopy;

import java.io.IOException;

public class NumberCopiesManagement {
    public static String insertCopy(ComicCopy comicCopy) throws IOException {
        return new NumberCopiesDAO().insertCopy(comicCopy);
    }

    public static String updateCopy(ComicCopy comicCopy) throws IOException {
        return new NumberCopiesDAO().updateCopy(comicCopy);
    }

    public static String deleteCopy(int comicId) throws IOException {
        return new NumberCopiesDAO().deleteCopy(comicId);
    }

    public static Object[] existsCopy(int id) throws IOException {
        return new NumberCopiesDAO().existsCopy(id);
    }

    public static Object[] getComicCopy(int id) throws IOException, ClassNotFoundException {
        return new NumberCopiesDAO().getComicCopy(id);
    }
}
