package daos;

import java.io.IOException;

public interface IReportsDAO {
    Object[] getCollectionReport() throws IOException, ClassNotFoundException;
    Object[] getCollectionReportByName(String name) throws IOException, ClassNotFoundException;

    Object[] getNumbersReport() throws IOException, ClassNotFoundException;

    Object[] getNumbersReportByName(String numberName, String colName) throws IOException, ClassNotFoundException;
}
