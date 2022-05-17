package model.daos;

import model.entities.Collection;

import java.io.IOException;

public interface ICollectionDAO {
    Object[] getCollectionList() throws IOException, ClassNotFoundException;
    Object[] getCollectionInfoById(int id) throws IOException, ClassNotFoundException;
    Object[] getCollectionsByName(String name) throws IOException, ClassNotFoundException;
    Object[] existsCollectionWithName(int id, String name) throws IOException;
    Object[] existsCollectionWithName(String name) throws IOException;
    Object[] existsCollectionWithId(int id) throws IOException;
    String updateCollection(Collection collection) throws IOException;
    String insertCollection(Collection collection) throws IOException;
    String deleteCollection(int idCol) throws IOException;

    Object[] getCollectionName(int idCol) throws IOException;

}
