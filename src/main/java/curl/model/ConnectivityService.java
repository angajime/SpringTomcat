package curl.model;

/**
 * Created by marcos on 09/02/15.
 */
public class ConnectivityService {
    String connectivityServiceInformation;
    ConnectivityProvider cp;
    String externURN;

    public ConnectivityService(String connectivityServiceInformation, ConnectivityProvider cp, String externURN) {
        this.connectivityServiceInformation = connectivityServiceInformation;
        this.cp = cp;
        this.externURN = externURN;
    }
}
