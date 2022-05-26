package model.daos;

import controller.ServerConnection;
import model.entities.ComicNumber;

import java.io.*;
import java.net.Socket;

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

    @Override
    public String insertComicNumber(ComicNumber comicNumber) throws IOException {
        String response;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        DataInputStream dataInputStream;

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("insertComicNumber");

        objectOutputStream = new ObjectOutputStream(ServerConnection.getConnection().getOutputStream());
        objectOutputStream.writeObject(comicNumber);
        objectOutputStream.flush();

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response = dataInputStream.readUTF();

        return response;
    }

    @Override
    public String updateComicNumber(ComicNumber comicNumber) throws IOException {
        String response;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        DataInputStream dataInputStream;

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("updateComicNumber");

        objectOutputStream = new ObjectOutputStream(ServerConnection.getConnection().getOutputStream());
        objectOutputStream.writeObject(comicNumber);
        objectOutputStream.flush();

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response = dataInputStream.readUTF();

        return response;
    }

    @Override
    public String deleteComicNumber(String isbn) throws IOException {
        String response;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF("deleteComicNumber");

        dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
        dataOutputStream.writeUTF(isbn);

        dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
        response = dataInputStream.readUTF();

        return response;
    }

    @Override
    public Object[] getNumbers() throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;

        Socket socket = ServerConnection.getConnection();

        Object[] returnObject = new Object[2];

        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF("getComicNumberList");

        dataInputStream = new DataInputStream(socket.getInputStream());

        String message = dataInputStream.readUTF();

        returnObject[0] = message;

        if(message.equals("OK")){
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            returnObject[1] = objectInputStream.readObject();
        }

        return returnObject;
    }

    @Override
    public Object[] getNumbersByName(String name) throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;

        Socket socket = ServerConnection.getConnection();

        Object[] returnObject = new Object[2];

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("getNumbersByName");

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(name);

        dataInputStream = new DataInputStream(socket.getInputStream());
        String message = dataInputStream.readUTF();

        returnObject[0] = message;

        if(message.equals("OK")){
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            returnObject[1] = objectInputStream.readObject();
        }

        return returnObject;
    }

    @Override
    public Object[] getNumbersByNameCol(String name, int idCol) throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;

        Socket socket = ServerConnection.getConnection();

        Object[] returnObject = new Object[2];

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("getNumbersByNameCol");

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(name);

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeInt(idCol);

        dataInputStream = new DataInputStream(socket.getInputStream());
        String message = dataInputStream.readUTF();

        returnObject[0] = message;

        if(message.equals("OK")){
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            returnObject[1] = objectInputStream.readObject();
        }

        return returnObject;
    }

    @Override
    public Object[] getNumbersByColName(String name) throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ObjectInputStream objectInputStream;

        Socket socket = ServerConnection.getConnection();

        Object[] returnObject = new Object[2];

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("getNumbersByColName");

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(name);

        dataInputStream = new DataInputStream(socket.getInputStream());
        String message = dataInputStream.readUTF();

        returnObject[0] = message;

        if(message.equals("OK")){
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            returnObject[1] = objectInputStream.readObject();
        }

        return returnObject;
    }
}
