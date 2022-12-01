package Query;

import java.io.Serializable;

public class DeleteQuery extends Query implements Serializable {

    public DeleteQuery(Object o, String opt) {
        super(o, opt);
    }
}
