package controller;


import model.daos.CollectionDAO;
import model.entities.Collection;

import java.io.IOException;

public class CollectionManagement {
    public static Object[] getCollections() throws IOException, ClassNotFoundException {
        return new CollectionDAO().getCollectionList();
    }

    public static Object[] getCollectionInfoById(int id) throws IOException, ClassNotFoundException {
        return new CollectionDAO().getCollectionInfoById(id);
    }

    public static Object[] getCollectionsByName(String name) throws IOException, ClassNotFoundException {
        return new CollectionDAO().getCollectionsByName(name);
    }

    public static Object[] existsCollectionWithName(int id, String name) throws IOException {
        return new CollectionDAO().existsCollectionWithName(id, name);
    }

    public static Object[] existsCollectionWithId(int id) throws IOException {
        return new CollectionDAO().existsCollectionWithId(id);
    }

    public static String updateCollection(Collection collection) throws IOException {
        return new CollectionDAO().updateCollection(collection);
    }

    public static Object[] existsCollectionWithName(String name) throws IOException {
        return new CollectionDAO().existsCollectionWithName(name);
    }

    public static String insertCollection(Collection collection) throws IOException {
        return new CollectionDAO().insertCollection(collection);
    }

    public static String deleteCollection(int colId) throws IOException {
        return new CollectionDAO().deleteCollection(colId);
    }

    public static Object[] getCollectionName(int idCol) throws IOException {
        return new CollectionDAO().getCollectionName(idCol);
    }
}
