package Query;

public class DeleteQuery extends Query{

    private String params;

    private Object newVal;

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
