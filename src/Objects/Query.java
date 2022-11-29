package Objects;

public class Query {

    private Action action;

    private Object object;

    private String options;

    public Query(Action a, Object o, String opt) {
        this.action = a;
        this.object = o;
        this.options = opt;
    }

    public Query() {
        this.action = null;
        this.object = null;
        this.options = null;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Query{" + "action=" + action + ", object=" + object + ", options='" + options + '\'' + '}';
    }

    public enum Action {
        GET,
        DELETE,
        UPDATE
    }
}
