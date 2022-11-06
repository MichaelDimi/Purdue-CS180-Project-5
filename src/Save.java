import Objects.Marketplace;

import java.io.*;

public class Save {
    private Marketplace serializableObject;
    private String filename;

    public Save(Marketplace serializableObject, String filename) {
        this.serializableObject = serializableObject;
        this.filename = filename;
    }

    public void saveMarketplace() {
        // serializes data
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(serializableObject);

            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Marketplace readMarketplace() {
        // deserializes data
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            Marketplace marketplace = (Marketplace) in.readObject();

            in.close();
            file.close();

            return marketplace;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ignored) {

        }

        return null;
    }
}
