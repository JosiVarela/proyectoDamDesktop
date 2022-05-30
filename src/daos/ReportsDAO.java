package daos;

import controller.ServerConnection;

import java.io.*;

public class ReportsDAO implements IReportsDAO{

    @Override
    public Object[] getCollectionReport() throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;
        Object[] response = new Object[2];

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("getCollectionReport");

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response[0] = dataInputStream.readUTF();

        if(response[0].equals("OK")){
            objectInputStream = new ObjectInputStream(ServerConnection.getConnection().getInputStream());
            response[1] = objectInputStream.readObject();
        }

        return response;
    }

    @Override
    public Object[] getCollectionReportByName(String name) throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;
        Object[] response = new Object[2];

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("getCollectionReportByName");

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF(name);

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response[0] = dataInputStream.readUTF();

        if(response[0].equals("OK")){
            objectInputStream = new ObjectInputStream(ServerConnection.getConnection().getInputStream());
            response[1] = objectInputStream.readObject();
        }

        return response;
    }
}
