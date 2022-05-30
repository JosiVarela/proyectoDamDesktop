package daos;

import java.io.IOException;

public interface IReportsDAO {
    Object[] getCollectionReport() throws IOException, ClassNotFoundException;
    Object[] getCollectionReportByName(String name) throws IOException, ClassNotFoundException;
}
