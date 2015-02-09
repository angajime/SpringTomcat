package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class DeviceGatewayGroupSpecification extends Specification {
    DeviceGatewayGroupType dggt;

    public DeviceGatewayGroupSpecification(Operator parentOperator, String internalName, String externURN, Type associatedType, DeviceGatewayGroupType dggt) {
        super(parentOperator, internalName, externURN, associatedType);
        this.dggt = dggt;
    }

    @Override
    public Type getType() {
        return dggt;
    }
}
