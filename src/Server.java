import Objects.*;

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

        public Marketplace marketplace;

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

                Query query = (Query) reader.readObject();

                switch (query.getAction()) {
                    case GET:
                        get(query);
                        break;
                    case DELETE:
                        delete(query);
                        marketplace.saveMarketplace();
                        break;
                    case UPDATE:
                        update(query);
                         marketplace.saveMarketplace();
                        break;
                }

                writer.writeObject(marketplace);

                reader.close();
                writer.close();
            } catch (IOException e) {
                System.out.println("Client disconnected");
                return;
            } catch (ClassNotFoundException e) {
                // Not a valid object
                throw new RuntimeException(e);
            }
        }

        public Query get(Query query) {
            String opt = query.getOptions();
            switch (opt) {
                case "currentUser":
                    query.setObject(this.marketplace.getCurrentUser());
                    break;
            }

            return new Query();
        }




    }
}
