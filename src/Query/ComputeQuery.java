package Query;

import java.lang.reflect.Array;

/**
 * Type of query that behaves as a get query, but returns a server computed value
 * For example: Computing if a username exists
 */
public class ComputeQuery extends GetQuery {

    public ComputeQuery(String[] input, String opt, String params) {
        super(input, opt, params);
    }

    public ComputeQuery(boolean[] input, String opt, String params) {
        super(input, opt, params);
    }

    public ComputeQuery(int[] input, String opt, String params) {
        super(input, opt, params);
    }

    public ComputeQuery(char[] input, String opt, String params) {
        super(input, opt, params);
    }

    public ComputeQuery(double[] input, String opt, String params) {
        super(input, opt, params);
    }
}
