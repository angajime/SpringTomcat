package curl.model;

import java.util.Set;

/**
 * Created by Juanlu on 31/10/14.
 */
public abstract class Specification extends Entity {

    Type associatedType;

    protected Specification(Operator parentOperator, String internalName, String externURN, Type associatedType) {
        super(parentOperator, internalName, externURN);
        this.associatedType = associatedType;
    }

    public abstract Type getType();
}
