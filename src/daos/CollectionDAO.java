package daos;

import controller.ServerConnection;
import data.MockData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CollectionDAO implements ICollectionDAO{
    /**
     * This method returns the collection list;
     * @return Object[]. Object[0] is a String which is a server message that indicates if the request has been
     * processed successfully and Object[1] is a list of Collection.
     */
    @Override
    public Object[] getCollectionList() throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;

        Socket socket = ServerConnection.getConnection();

        Object[] returnObject = new Object[2];

        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF("getCollectionList");

        dataInputStream = new DataInputStream(socket.getInputStream());

        String message = dataInputStream.readUTF();

        returnObject[0] = message;

        objectInputStream = new ObjectInputStream(socket.getInputStream());

        returnObject[1] = objectInputStream.readObject();

        return returnObject;
    }
}
