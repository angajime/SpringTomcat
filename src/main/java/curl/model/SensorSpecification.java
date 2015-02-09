package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class SensorSpecification extends Specification {
    SensorType st;

    public SensorSpecification(Operator parentOperator, String internalName, String externURN, Type associatedType, SensorType st) {
        super(parentOperator, internalName, externURN, associatedType);
        this.st = st;
    }

    @Override
    public Type getType() {
        return st;
    }
}
