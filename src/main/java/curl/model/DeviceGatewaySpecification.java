package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class DeviceGatewaySpecification extends Specification {
    DeviceGatewayType dgt;
    DeviceGatewayGroupSpecification dggs;

    public DeviceGatewaySpecification(Operator parentOperator, String internalName, String externURN, Type associatedType, DeviceGatewayType dgt, DeviceGatewayGroupSpecification dggs) {
        super(parentOperator, internalName, externURN, associatedType);
        this.dgt = dgt;
        this.dggs = dggs;
    }

    @Override
    public Type getType() {
        return dgt;
    }
}
