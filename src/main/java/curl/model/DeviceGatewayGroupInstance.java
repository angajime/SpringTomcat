package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class DeviceGatewayGroupInstance extends Instance {
    DeviceGatewayGroupSpecification dggs;
    User customerOrUserAssigneeURN;
    User customerOrUserOwnerURN;

    public DeviceGatewayGroupInstance(Operator parentOperator, String internalName, String externURN, boolean status, DeviceGatewayGroupSpecification dggs, User customerOrUserAssigneeURN, User customerOrUserOwnerURN) {
        super(parentOperator, internalName, externURN, status);
        this.dggs = dggs;
        this.customerOrUserAssigneeURN = customerOrUserAssigneeURN;
        this.customerOrUserOwnerURN = customerOrUserOwnerURN;
    }

    @Override
    public Specification getSpecification() {
        return dggs;
    }
}
