import Objects.Marketplace;

import javax.management.openmbean.OpenMBeanConstructorInfo;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }

    @Override
    public void run() {
        // When the server starts up, a marketplace is loaded
        Marketplace marketplace = new Marketplace();

        // Spawn a new thread whenever client connects
        try (ServerSocket serverSocket = new ServerSocket(1111)) {
            System.out.println("Awaiting client connection...");

            // Always await connection until server is stopped:
            while (true) {
                Socket client;
                try {
                    client = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("Server Stopped.");
                    e.printStackTrace(); // TODO: may remove
                    return;
                }
                new Thread(new ClientHandler(client, marketplace)).start();
            }
        } catch (IOException e) {
            System.out.println("Error creating client connection");
        }
    }

    public static class ClientHandler implements Runnable {
        private final Socket client;

        private Marketplace marketplace;

        public ClientHandler(Socket client, Marketplace marketplace) {
            this.client = client;
            this.marketplace = marketplace;
        }

        // All communication with client happens here
        @Override
        public void run() {
            try {
                ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());

                Object o = reader.readObject();
                System.out.println(o.toString());

            } catch (IOException e) {
                System.out.println("Client disconnected");
                e.printStackTrace(); // TODO: Maybe remove
                return;
            } catch (ClassNotFoundException e) {
                // Not a valid object
                throw new RuntimeException(e);
            }
        }
    }
}
