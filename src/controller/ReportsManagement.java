package controller;

import daos.ReportsDAO;

import java.io.IOException;

public class ReportsManagement {
    public static Object[] getCollectionReport() throws IOException, ClassNotFoundException {
        return new ReportsDAO().getCollectionReport();
    }
}