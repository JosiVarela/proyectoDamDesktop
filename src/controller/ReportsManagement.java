package controller;

import daos.ReportsDAO;

import java.io.IOException;
import java.util.Map;

public class ReportsManagement {
    public static Object[] getCollectionReport() throws IOException, ClassNotFoundException {
        return new ReportsDAO().getCollectionReport();
    }

    public static Object[] getCopiesReport() throws IOException, ClassNotFoundException {
        return new ReportsDAO().getCopiesReport();
    }

    public static Object[] getNumbersReport() throws IOException, ClassNotFoundException {
        return new ReportsDAO().getNumbersReport();
    }

    public static Object[] getCollectionReportByName(String name) throws IOException, ClassNotFoundException {
        return new ReportsDAO().getCollectionReportByName(name);
    }

    public static Object[] getNumbersReportByName(String numberName, String colName) throws IOException, ClassNotFoundException {
        return new ReportsDAO().getNumbersReportByName(numberName, colName);
    }

    public static Object[] getCopiesReportFiltered(Map<String, Object> parameters) throws IOException, ClassNotFoundException {
        return new ReportsDAO().getCopiesReportFiltered(parameters);
    }
}
