package daos;

import java.io.IOException;
import java.util.Map;

public interface IReportsDAO {
    Object[] getCollectionReport() throws IOException, ClassNotFoundException;
    Object[] getCollectionReportByName(String name) throws IOException, ClassNotFoundException;

    Object[] getNumbersReport() throws IOException, ClassNotFoundException;

    Object[] getNumbersReportByName(String numberName, String colName) throws IOException, ClassNotFoundException;

    Object[] getCopiesReport() throws IOException, ClassNotFoundException;
    Object[] getCopiesReportFiltered(Map<String, Object> parameters) throws IOException, ClassNotFoundException;
}
