package Server;

import Objects.*;
import Query.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    public static final Object LOCK = new Object();

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
                    System.out.println("NEW CONNECTION: " + client.getPort()); // Implies a new query
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

        public Marketplace marketplace;

        public ClientHandler(Socket client, Marketplace marketplace) {
            this.client = client;
            this.marketplace = marketplace;
        }

        // All communication with client happens here
        @Override
        public void run() {
            synchronized (LOCK) {
                Helpers helpers = new Helpers(marketplace);

                try {
                    ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
                    ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());

                    Query query = (Query) reader.readObject();

                    if (query instanceof GetQuery) {
                        if (query instanceof ComputeQuery) {
                            Query q = helpers.compute((ComputeQuery) query);
                            writer.writeObject(q);
                        } else {
                            writer.writeObject(helpers.get((GetQuery) query));
                        }
                        System.out.println(marketplace);
                    } else if (query instanceof DeleteQuery) {
                        writer.writeObject(helpers.delete((DeleteQuery) query));
                        marketplace.saveMarketplace();
                        System.out.println(marketplace);
                    } else if (query instanceof UpdateQuery) {
                        writer.writeObject(helpers.update((UpdateQuery) query));
                        marketplace.saveMarketplace();
                        System.out.println(marketplace);
                    }

                    writer.flush();

                    writer.close();
                    reader.close();
                    client.close();
                } catch (IOException e) {
                    System.out.println("Client disconnected");
                    return;
                } catch (ClassNotFoundException e) {
                    // Not a valid object
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
