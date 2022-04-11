package controller;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private static Socket socket;

    public static void startConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public static void closeConnection() throws IOException {
        socket.close();
    }

    public static Socket getConnection(){
        return socket;
    }
}
