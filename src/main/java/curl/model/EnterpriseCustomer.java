package curl.model;

/**
 * Created by marcos on 06/02/15.
 */
public class EnterpriseCustomer extends Entity {

    DomainApplication da;

    public EnterpriseCustomer(Operator parentOperator, String internalName, String externURN, DomainApplication da) {
        super(parentOperator, internalName, externURN);
        this.da = da;
    }

    public int attachToDomain() {
        //TODO Call Curl with the parameters
        return 0;
    }
}
