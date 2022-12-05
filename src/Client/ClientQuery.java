package Client;

import Query.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientQuery {
    public ObjectOutputStream writer;
    public ObjectInputStream reader;

    public Query getQuery(Object o, String opt, String params) {
        try {
            Socket socket = new Socket("localhost", 1111);

            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            GetQuery get = new GetQuery(o, opt, params);
            writer.writeObject(get);

            Query q = (Query) reader.readObject();

            if (q.getObject() == null) return new Query(false, "err");

            writer.close();
            reader.close();
            socket.close();

            return q;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection refused: Please make sure the server has been started");
        }

        return new Query(false, "err");
    }

    public Query computeQuery(Object[] input, String opt, String params) {

        try {
            Socket socket = new Socket("localhost", 1111);

            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            ComputeQuery get = new ComputeQuery(input, opt, params);
            writer.writeObject(get);
            Query q = (Query) reader.readObject();

            writer.close();
            reader.close();
            socket.close();

            return q;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection refused: Please make sure the server has been started");
        }

        return new Query(false, "err");
    }

    public Query updateQuery(Object o, String opt, String params, Object newVal) {
        try {
            Socket socket = new Socket("localhost", 1111);

            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            UpdateQuery update = new UpdateQuery(o, opt, params, newVal);
            writer.writeObject(update);
            Query q = (Query) reader.readObject();

            writer.close();
            reader.close();
            socket.close();

            return q;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection refused: Please make sure the server has been started");
        }

        return new Query(false, "err");
    }

    public Query deleteQuery(Object o, String opt) {
        try {
            Socket socket = new Socket("localhost", 1111);

            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            DeleteQuery delete = new DeleteQuery(o, opt);
            writer.writeObject(delete);
            Query q = (Query) reader.readObject();

            writer.close();
            reader.close();
            socket.close();

            return q;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection refused: Please make sure the server has been started");
        }

        return new Query(false, "err");
    }
    public Query deleteQuery(Object o, String opt, Object params) {
        try {
            Socket socket = new Socket("localhost", 1111);

            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            DeleteQuery delete = new DeleteQuery(o, opt, params);
            writer.writeObject(delete);
            Query q = (Query) reader.readObject();

            writer.close();
            reader.close();
            socket.close();

            return q;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection refused: Please make sure the server has been started");
        }

        return new Query(false, "err");
    }
}
