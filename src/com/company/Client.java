package com.company;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String name = "empty";
        String reply = "empty";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        reply = sc.nextLine();
        name = reply;

        try (Socket socket = new Socket("localhost", 8080)) {
            System.out.println("You have successfully connected, start typing messages");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            ClientThread threadClient = new ClientThread(socket);
            new Thread(threadClient).start();

            do {
                String message = (name + ": ");
                reply = sc.nextLine();
                out.println(message + reply);
            } while (true);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}

