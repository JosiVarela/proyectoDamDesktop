package model.daos;

import controller.ServerConnection;
import model.entities.ComicCopy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NumberCopiesDAO implements INumberCopiesDAO{
    /**
     * This method insert a Comic copy
     * @param comicCopy The comic copy to insert
     * @return String. The string returned is a message that indicates if the comic copy was successfully inserted
     * @throws IOException
     */
    @Override
    public String insertCopy(ComicCopy comicCopy) throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        String response;

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("insertCopy");

        objectOutputStream = new ObjectOutputStream(ServerConnection.getConnection().getOutputStream());
        objectOutputStream.writeObject(comicCopy);
        objectOutputStream.flush();

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response = dataInputStream.readUTF();

        return response;
    }

    /**
     * Checks if a Comic copy exists
     * @param idCopy The copy ID
     * @return Object[2]. Object[0] is a check message and object[2] is a boolean
     * @throws IOException
     */
    @Override
    public Object[] existsCopy(int idCopy) throws IOException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        Object[] response = new Object[2];

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("existsCopy");

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeInt(idCopy);

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response[0] = dataInputStream.readUTF();

        if(response[0].equals("OK")){
            dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
            response[1] = dataInputStream.readBoolean();
        }

        return response;
    }
}
