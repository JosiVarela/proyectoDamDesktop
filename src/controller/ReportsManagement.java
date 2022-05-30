package controller;

import daos.ReportsDAO;

import java.io.IOException;

public class ReportsManagement {
    public static Object[] getCollectionReport() throws IOException, ClassNotFoundException {
        return new ReportsDAO().getCollectionReport();
    }

    public static Object[] getCollectionReportByName(String name) throws IOException, ClassNotFoundException {
        return new ReportsDAO().getCollectionReportByName(name);
    }
}
