package model;


import daos.CollectionDAO;

import java.io.IOException;

public class CollectionManagement {
    public static Object[] getCollections() throws IOException, ClassNotFoundException {
        return new CollectionDAO().getCollectionList();
    }

    public static Object[] getCollectionInfoById(int id) throws IOException, ClassNotFoundException {
        return new CollectionDAO().getCollectionInfoById(id);
    }
}
