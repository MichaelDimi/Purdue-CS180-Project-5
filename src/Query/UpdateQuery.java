package Query;

public class UpdateQuery extends Query {

    private String params;

    private Object newVal;

    public UpdateQuery(Object o, String opt, String params, Object newVal) {
        super(o, opt);
        this.params = params;
        this.newVal = newVal;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Object getNewVal() {
        return newVal;
    }

    public void setNewVal(Object newVal) {
        this.newVal = newVal;
    }
}
