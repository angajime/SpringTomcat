package hello;

import java.util.List;

/**
 * Created by tarazaky on 31/10/14.
 */
public class Alert {
    private long id;
    private String urn, resource, comparator;
    private Double value;
    private List<String> recipients;

    public Alert(long id, String urn, String resource, String comparator, Double value, List<String> recipients) {
        this.id = id;
        this.urn = urn;
        this.resource = resource;
        this.comparator = comparator;
        this.value = value;
        this.recipients = recipients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", urn='" + urn + '\'' +
                ", resource='" + resource + '\'' +
                ", comparator='" + comparator + '\'' +
                ", value=" + value +
                ", recipients=" + recipients +
                '}';
    }
}
