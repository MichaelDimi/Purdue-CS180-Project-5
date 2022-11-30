package Query;

/**
 * Type of query that behaves as a get query, but returns a server computed value
 * For example: Computing if a username exists
 */
public class ComputeQuery extends GetQuery {

    public ComputeQuery(Object o, String opt, String params) {
        super(o, opt, params);
    }
}
