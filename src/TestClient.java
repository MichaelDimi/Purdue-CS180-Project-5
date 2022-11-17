import Objects.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestClient {

    public static void main(String[] args) {
        Book book = new Book("Book 1", "Store 1", "Scary", "A really scary book", 12.50);

        try {
            Socket socket = new Socket("localhost", 1111);

            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());

            writer.writeObject(book);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
