package curl.model;

import java.util.Set;

/**
 * Created by Juanlu on 31/10/14.
 */
public abstract class Specification {

    public abstract Set<Instance> getInstances();
    public abstract Type getType();
}
