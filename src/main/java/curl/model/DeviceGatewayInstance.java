package curl.model;


/**
 * Created by marcos on 06/02/15.
 */
public class DeviceGatewayInstance extends Instance {
    DeviceGatewayGroupInstance dggi;
    DeviceGatewaySpecification dgs;
    ConnectivityService cs;
    String serialNumber;
    String vendorNumber;

    public DeviceGatewayInstance(Operator parentOperator, String internalName, String externURN, boolean status, DeviceGatewayGroupInstance dggi, DeviceGatewaySpecification dgs, ConnectivityService cs) {
        super(parentOperator, internalName, externURN, status);
        this.dggi = dggi;
        this.dgs = dgs;
        this.cs = cs;
    }

    @Override
    public Specification getSpecification() {
        return dgs;
    }
}
