package curl.model;

/**
 * Created by marcos on 06/02/15.
 */
public abstract class Entity {

    Operator parentOperator;
    String internalName;
    String externURN;

    protected Entity() {
    }

    public Operator getParentOperator() {
        return parentOperator;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getExternURN() {
        return externURN;
    }

    public void setParentOperator(Operator parentOperator) {
        this.parentOperator = parentOperator;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public void setExternURN(String externURN) {
        this.externURN = externURN;
    }

    protected Entity(Operator parentOperator, String internalName, String externURN) {
        this.parentOperator = parentOperator;
        this.internalName = internalName;
        this.externURN = externURN;
    }

}
