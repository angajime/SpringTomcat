package curl.model;

import java.util.Set;

/**
 * Created by Juanlu on 31/10/14.
 */
public abstract class Instance implements Payloadable {

    public abstract Specification getSpecification();
    public abstract Set<Instance> getChildren();
}
