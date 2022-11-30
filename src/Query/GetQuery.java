package Query;

public class GetQuery extends Query {

    private String params;

    public GetQuery(Object o, String opt, String params) {
        super(o, opt);
        this.params = params;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
