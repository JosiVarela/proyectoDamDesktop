package daos;

import controller.ServerConnection;
import model.entities.Collection;

import java.io.*;
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

        if(message.equals("OK")){
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            returnObject[1] = objectInputStream.readObject();
        }

        return returnObject;
    }
    /**
     * This method returns collection info by id;
     * @return Object[]. Object[0] is a String which is a server message that indicates if the request has been
     * processed successfully and Object[1] is a Collection.
     */
    @Override
    public Object[] getCollectionInfoById(int id) throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;
        String message;

        Socket socket = ServerConnection.getConnection();

        Object[] returnObject = new Object[2];

        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF("getCollectionInfoById");

        dataOutputStream.writeInt(id);

        dataInputStream = new DataInputStream(socket.getInputStream());

        message = dataInputStream.readUTF();

        returnObject[0] = message;

        if(message.equals("OK")){
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            returnObject[1] = objectInputStream.readObject();
        }

        return returnObject;
    }
    /**
     * This method returns collections by name;
     * @return Object[]. Object[0] is a String which is a server message that indicates if the request has been
     * processed successfully and Object[1] is a Collection.
     */
    @Override
    public Object[] getCollectionsByName(String name) throws IOException, ClassNotFoundException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        ObjectInputStream objectInputStream;
        Object[] serverData = new Object[2];


        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("getCollectionsByName");

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF(name);

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        serverData[0] = dataInputStream.readUTF();

        if(serverData[0].equals("OK")){
            objectInputStream = new ObjectInputStream(ServerConnection.getConnection().getInputStream());
            serverData[1] = objectInputStream.readObject();
        }

        return serverData;
    }
    /**
     * This method returns true if exists another collection with distinct id and same name;
     * @return Object[]. Object[0] is a String which is a server message that indicates if the request has been
     * processed successfully and Object[1] is a boolean.
     */
    @Override
    public Object[] existsCollectionWithName(int id, String name) throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        Object[] serverResponse = new Object[2];

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("existCollectionWithName");


        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF(name);


        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeInt(id);


        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        serverResponse[0] = dataInputStream.readUTF();

        if(serverResponse[0].equals("OK")){
            dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
            serverResponse[1] = dataInputStream.readBoolean();
        }

        return serverResponse;

    }

    @Override
    public String updateCollection(Collection collection) {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        String serverResponse;

        try{
            dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
            dataOutputStream.writeUTF("updateCollection");

            objectOutputStream = new ObjectOutputStream(ServerConnection.getConnection().getOutputStream());
            objectOutputStream.writeObject(collection);
            objectOutputStream.flush();

            dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
            serverResponse = dataInputStream.readUTF();

            return serverResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
