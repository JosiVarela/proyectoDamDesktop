package model;


import daos.CollectionDAO;

public class CollectionManagement {
    public static Object[] getCollections(){
        return new CollectionDAO().getCollectionList();
    }
}
