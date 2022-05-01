package model;


import daos.CollectionDAO;
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

    public static String updateCollection(Collection collection){
        return new CollectionDAO().updateCollection(collection);
    }
}
