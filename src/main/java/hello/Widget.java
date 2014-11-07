package hello;

import java.util.List;

/**
 * Created by tarazaky on 6/11/14.
 */
public class Widget {
    private List<Setting> settings;

    public Widget(List<Setting> settings) {
        this.settings = settings;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }
}

class Setting{
    private String type;
    private String title;
    private String size;
    private String value;
    private Boolean animate;

    Setting(String type, String title, String size, String value, Boolean animate) {
        this.type = type;
        this.title = title;
        this.size = size;
        this.value = value;
        this.animate = animate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getAnimate() {
        return animate;
    }

    public void setAnimate(Boolean animate) {
        this.animate = animate;
    }
}
