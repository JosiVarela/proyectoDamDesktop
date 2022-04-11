package daos;

import java.io.IOException;

public interface ICollectionDAO {
    Object[] getCollectionList() throws IOException, ClassNotFoundException;

}
