package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class ResourceInstance extends Instance {
    ResourceSpecification rspec;
    ResourceSet rs;
    SensorInstance si;
    String physicalAddress;

    public ResourceInstance(Operator parentOperator, String internalName, String externURN, boolean status, ResourceSpecification rspec, ResourceSet rs, SensorInstance si, String physicalAddress) {
        super(parentOperator, internalName, externURN, status);
        this.rspec = rspec;
        this.rs = rs;
        this.si = si;
        this.physicalAddress = physicalAddress;
    }

    @Override
    public Specification getSpecification() {
        return rspec;
    }
}
