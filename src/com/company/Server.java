package com.company;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) {
        ArrayList<Socket> clients = new ArrayList<>();
        HashMap<Socket, String> clientNameList = new HashMap<Socket, String>();

        try (ServerSocket serversocket = new ServerSocket(8080)) {
            System.out.println("Server.Server is started...");

            while (true) {
                Socket socket = serversocket.accept();
                clients.add(socket);
                ServerThread serverThread = new ServerThread(socket, clients, clientNameList);
                serverThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
