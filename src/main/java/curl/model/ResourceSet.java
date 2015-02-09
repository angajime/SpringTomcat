package curl.model;

import java.util.Set;

/**
 * Created by marcos on 09/02/15.
 */
public class ResourceSet extends Entity {
    Set<ResourceInstance> resourceList;

    public ResourceSet(Operator parentOperator, String internalName, String externURN) {
        super(parentOperator, internalName, externURN);
    }

    public int addResource(ResourceInstance ri) {
        resourceList.add(ri);
        //TODO Effect into DM
        return 1; //return status
    }
}
