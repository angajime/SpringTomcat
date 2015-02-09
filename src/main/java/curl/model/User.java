package curl.model;

import java.util.Set;

/**
 * Created by marcos on 09/02/15.
 */
public class User extends Entity {
    Set<EnterpriseCustomer> parentEnterpriseCustomer;

    public User(Operator parentOperator, String internalName, String externURN, Set<EnterpriseCustomer> parentEnterpriseCustomer) {
        super(parentOperator, internalName, externURN);
        this.parentEnterpriseCustomer = parentEnterpriseCustomer;
    }
}
