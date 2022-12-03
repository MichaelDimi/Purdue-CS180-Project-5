package Query;

import java.io.Serializable;

public class DeleteQuery extends Query implements Serializable {

    private Object params;

    public DeleteQuery(Object o, String opt) {
        super(o, opt);
    }

    public DeleteQuery(Object o, String opt, Object params) {
        super(o, opt);
        this.params = params;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
