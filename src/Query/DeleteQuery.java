package Query;

import java.io.Serializable;

public class DeleteQuery extends Query implements Serializable {

    private String params;

    public DeleteQuery(String opt, String params) {
        super(null, opt);
        this.params = params;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
