package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class DeviceGatewayType extends Type {
    DeviceGatewayGroupType dggt;

    public DeviceGatewayType(Operator parentOperator, String internalName, String externURN, DeviceGatewayGroupType dggt) {
        super(parentOperator, internalName, externURN);
        this.dggt = dggt;
    }
}
