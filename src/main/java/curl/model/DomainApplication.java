package curl.model;

/**
 * Created by marcos on 06/02/15.
 */
public class DomainApplication extends Entity {

    boolean isServiceCustomer;
    boolean isGatewayApplicationProviderPassword;
    String serviceConsumerPassword;
    String gatewayApplicationPassword;

    public boolean isServiceCustomer() {
        return isServiceCustomer;
    }

    public boolean isGatewayApplicationProviderPassword() {
        return isGatewayApplicationProviderPassword;
    }

    public String getServiceConsumerPassword() {
        return serviceConsumerPassword;
    }

    public String getGatewayApplicationPassword() {
        return gatewayApplicationPassword;
    }

    public DomainApplication(Operator parentOperator, boolean isServiceCustomer, String externURN, String internalName, String gatewayApplicationPassword, String serviceConsumerPassword, boolean isGatewayApplicationProviderPassword) {
        this.parentOperator = parentOperator;
        this.isServiceCustomer = isServiceCustomer;
        this.externURN = externURN;
        this.internalName = internalName;
        this.gatewayApplicationPassword = gatewayApplicationPassword;
        this.serviceConsumerPassword = serviceConsumerPassword;
        this.isGatewayApplicationProviderPassword = isGatewayApplicationProviderPassword;
    }


}
