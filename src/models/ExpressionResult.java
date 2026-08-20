package models;
 
public class ExpressionResult {
    private boolean success;
    private double value;
    private String postfix;
    private String errorMessage;
    private long elapsedTimeNanos;
    private OperationCounter counter;
 
    public static ExpressionResult success(double value, String postfix, long elapsedTimeNanos, OperationCounter counter) {
        ExpressionResult r = new ExpressionResult();
        r.success = true;
        r.value = value;
        r.postfix = postfix;
        r.elapsedTimeNanos = elapsedTimeNanos;
        r.counter = counter;
        return r;
    }
 
    public static ExpressionResult error(String message, long elapsedTimeNanos, OperationCounter counter) {
        ExpressionResult r = new ExpressionResult();
        r.success = false;
        r.errorMessage = message;
        r.elapsedTimeNanos = elapsedTimeNanos;
        r.counter = counter;
        return r;
    }
 
    public boolean isSuccess() { return success; }
    public double getValue() { return value; }
    public String getPostfix() { return postfix; }
    public String getErrorMessage() { return errorMessage; }
    public long getElapsedTimeNanos() { return elapsedTimeNanos; }
    public OperationCounter getCounter() { return counter; }
}
