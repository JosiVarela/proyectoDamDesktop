package model.daos;

import controller.ServerConnection;
import model.entities.ComicNumber;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ComicNumberDAO implements IComicNumberDAO{
    /**
     * This method checks if the isbn exists.
     * @param isbn The comic number isbn
     * @return Object[2]. Object[0] is a check for know if the operation was successful and Object[1] is a boolean
     * that indicates if the number exists
     * @throws IOException
     */
    @Override
    public Object[] existsNumber(String isbn) throws IOException {
        Object[] response = new Object[2];
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("existsComicNumber");

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF(isbn);

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response[0] = dataInputStream.readUTF();

        if(response[0].equals("OK")){
            dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
            response[1] = dataInputStream.readBoolean();
        }

        return response;
    }

    @Override
    public Object[] getComicNumber(String isbn) throws IOException, ClassNotFoundException {
        Object[] response = new Object[2];
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("getComicNumber");

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF(isbn);

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response[0] = dataInputStream.readUTF();

        if(response[0].equals("OK")){
            objectInputStream = new ObjectInputStream(ServerConnection.getConnection().getInputStream());
            response[1] = objectInputStream.readObject();
        }

        return response;
    }
}
