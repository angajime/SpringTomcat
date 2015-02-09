package curl.model;

import java.util.Set;

/**
 * Created by Juanlu on 31/10/14.
 */
public abstract class Type extends Entity {

    protected Type(Operator parentOperator, String internalName, String externURN) {
        super(parentOperator, internalName, externURN);
    }

}
