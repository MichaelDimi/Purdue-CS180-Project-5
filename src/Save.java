import Objects.Marketplace;

import java.io.*;

public class Save {
    private Marketplace serializableObject;
    private String filename;

    public Save(Marketplace serializableObject, String filename) {
        this.serializableObject = serializableObject;
        this.filename = filename;
    }


}
