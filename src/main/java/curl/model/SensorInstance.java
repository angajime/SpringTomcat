package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class SensorInstance extends Instance {
    SensorSpecification ss;
    DeviceGatewayInstance dgi;
    String serialNumber;

    public SensorInstance(Operator parentOperator, String internalName, String externURN, boolean status, SensorSpecification ss, DeviceGatewayInstance dgi) {
        super(parentOperator, internalName, externURN, status);
        this.ss = ss;
        this.dgi = dgi;
    }

    @Override
    public Specification getSpecification() {
        return ss;
    }
}
