package curl.model;

import java.util.Set;

/**
 * Created by Juanlu on 31/10/14.
 */
public abstract class Instance extends Entity {

    private boolean status;

    protected Instance(Operator parentOperator, String internalName, String externURN, boolean status) {
        super(parentOperator, internalName, externURN);
        this.status = status;
    }

    public abstract Specification getSpecification();
}
