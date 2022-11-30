import Objects.*;
import Query.*;

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

                if (query instanceof GetQuery) {
                    if (query instanceof ComputeQuery) {
                        writer.writeObject(compute((ComputeQuery) query));
                    } else {
                        writer.writeObject(get((GetQuery) query));
                    }
                } else if (query instanceof DeleteQuery) {
                    writer.writeObject(delete((DeleteQuery) query));
                    marketplace.saveMarketplace();
                } else if (query instanceof UpdateQuery) {
                    writer.writeObject(update((UpdateQuery) query));
                    marketplace.saveMarketplace();
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

        public GetQuery get(GetQuery get) {
            String opt = get.getOptions();
            String params = get.getParams();
            switch (opt) {
                case "currentUser":
                    get.setObject(this.marketplace.getCurrentUser());
                    break;
            }
            return get;
        }

        public Query compute(ComputeQuery compute) {
            String opt = compute.getOptions();
            String params = compute.getParams();
            switch (opt) {

            }
            return new Query(false, "err: Couldn't find opt/params");
        }

        public Query update(UpdateQuery update) {
            String opt = update.getOptions();
            String params = update.getParams();
            switch (opt) {

            }
            return new Query(false, "err: Couldn't find opt/params");
        }

        public Query delete(DeleteQuery delete) {
            String opt = delete.getOptions();
            String params = delete.getParams();
            switch (opt) {

            }
            return new Query(false, "err: Couldn't find opt/params");
        }






    }
}
