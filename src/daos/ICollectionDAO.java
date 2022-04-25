package daos;

import java.io.IOException;

public interface ICollectionDAO {
    Object[] getCollectionList() throws IOException, ClassNotFoundException;
    Object[] getCollectionInfoById(int id) throws IOException, ClassNotFoundException;

}
