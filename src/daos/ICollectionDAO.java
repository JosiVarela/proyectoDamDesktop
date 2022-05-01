package daos;

import model.entities.Collection;

import java.io.IOException;

public interface ICollectionDAO {
    Object[] getCollectionList() throws IOException, ClassNotFoundException;
    Object[] getCollectionInfoById(int id) throws IOException, ClassNotFoundException;
    Object[] getCollectionsByName(String name) throws IOException, ClassNotFoundException;
    Object[] existsCollectionWithName(int id, String name) throws IOException;
    String updateCollection(Collection collection);

}
