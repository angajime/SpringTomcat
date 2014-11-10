package hello;

import java.util.List;

/**
 * Created by tarazaky on 6/11/14.
 */
public class Widget {


    private String type;
    private Setting settings;

    public Widget(String type, Setting settings) {
        this.type = type;
        this.settings = settings;
    }

    public Setting getSettings() {
        return settings;
    }

    public void setSettings(Setting settings) {
        this.settings = settings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class Setting{
    private String title;
    private String size;
    private String value;
    private Boolean animate;

    Setting(String title, String size, String value, Boolean animate) {
        this.title = title;
        this.size = size;
        this.value = value;
        this.animate = animate;
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
