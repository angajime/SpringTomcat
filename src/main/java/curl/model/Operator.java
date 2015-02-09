package curl.model;

/**
 * Created by marcos on 06/02/15.
 */
public class Operator {
    private String operatorURN;

    public Operator (String operatorURN) {
        this.operatorURN = operatorURN;
    }

    public void setOperatorURN(String operatorURN) {
        this.operatorURN = operatorURN;
    }

    public String getOperatorURN() {
        return operatorURN;
    }
}
