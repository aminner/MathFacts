package amamin.com.mathfacts;

/**
 * Created by Amanda on 10/22/2016.
 */

public enum MathFactsType {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("X"),
    DIVISION("*/*");

    private String operator;

    MathFactsType(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
