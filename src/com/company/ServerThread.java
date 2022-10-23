package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerThread extends Thread {
    private Socket socket;
    private ArrayList<Socket> clients;
    private HashMap<Socket, String> clientNameList;
    private ConcurrentHashMap<Socket, ArrayList<String>> msgStorage = new ConcurrentHashMap<>();

    public ServerThread(Socket socket, ArrayList<Socket> clients, HashMap<Socket, String> clientNameList) {
        this.socket = socket;
        this.clients = clients;
        this.clientNameList = clientNameList;
    }

    @Override
    public void run() {
        class MessageRunnable implements Runnable {
            private ConcurrentHashMap<Socket, ArrayList<String>> msgStorage;

            public MessageRunnable(ConcurrentHashMap<Socket, ArrayList<String>> msgStorage) {
                this.msgStorage = msgStorage;
            }

            @Override
            public void run() {
                try {
                    for(Map.Entry<Socket, ArrayList<String>> entry : msgStorage.entrySet()) {
                        for(String i : entry.getValue()) {
                            Socket socket = entry.getKey();
                            showMessageToAllClients(socket, i);
                        }
                        msgStorage.remove(socket);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Empty!");
                    e.printStackTrace();
                }
            }
        }

        Runnable showMsg = new MessageRunnable(msgStorage);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(showMsg, 0, 10, TimeUnit.SECONDS);

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String outputString = input.readLine();
                if (!clientNameList.containsKey(socket)) {
                    String[] messageString = outputString.split(":", 2);
                    clientNameList.put(socket, messageString[0]);
                    if (!msgStorage.containsKey(socket)) {
                        msgStorage.put(socket, new ArrayList<>());
                    }
                    msgStorage.get(socket).add(messageString[0] + messageString[1]);
                } else {
                    if (!msgStorage.containsKey(socket)) {
                        msgStorage.put(socket, new ArrayList<>());
                    }
                    msgStorage.get(socket).add(outputString);
                }
                System.out.println(msgStorage);
            }
        } catch (SocketException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessageToAllClients(Socket sender, String outputString) {
        Socket socket;
        PrintWriter printWriter;
        int i = 0;
        while (i < clients.size()) {
            socket = clients.get(i);
            i++;
            try {
                if (socket != sender) {
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(outputString);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
