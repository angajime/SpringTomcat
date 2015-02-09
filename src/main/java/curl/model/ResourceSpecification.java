package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class ResourceSpecification extends Specification {
    String payloadDataType;

    public ResourceSpecification(Operator parentOperator, String internalName, String externURN, Type associatedType, String payloadDataType) {
        super(parentOperator, internalName, externURN, associatedType);
        this.payloadDataType = payloadDataType;
    }

    @Override
    public Type getType() {
        return null;
    }
}
