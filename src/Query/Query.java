package Query;

import java.io.Serializable;

public class Query implements Serializable {

    private Object object;

    private String option;

    public Query(Object o, String opt) {
        this.object = o;
        this.option = opt;
    }

    public Query() {
        this.object = null;
        this.option = null;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getOptions() {
        return option;
    }

    public void setOptions(String options) {
        this.option = options;
    }

    @Override
    public String toString() {
        return "Query{object=" + object + ", options='" + option + '\'' + '}';
    }

    public enum Action {
        GET,
        DELETE,
        UPDATE
    }
}
