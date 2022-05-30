package daos;

import java.io.IOException;

public interface IReportsDAO {
    Object[] getCollectionReport() throws IOException, ClassNotFoundException;
}
