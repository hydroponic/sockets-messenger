package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable {

    private Socket socket;
    private BufferedReader in;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                System.out.println(message);
            }
        } catch (SocketException ignored) {

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
