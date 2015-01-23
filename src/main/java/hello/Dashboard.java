package hello;

import java.util.List;

/**
 * Created by tarazaky on 6/11/14.
 */
public class Dashboard {
    private Integer version;
    private Integer columns;
    private Boolean allow_edit;
    private List<Pane> panes;
    private List<Datasource> datasources;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Boolean getAllow_edit() {
        return allow_edit;
    }

    public void setAllow_edit(Boolean allow_edit) {
        this.allow_edit = allow_edit;
    }

    public List<Pane> getPanes() {
        return panes;
    }

    public void setPanes(List<Pane> panes) {
        this.panes = panes;
    }

    public List<Datasource> getDatasources() {
        return datasources;
    }

    public void setDatasources(List<Datasource> datasources) {
        this.datasources = datasources;
    }

    public Dashboard(Integer version, Integer columns, Boolean allow_edit, List<Pane> panes, List<Datasource> datasources) {
        this.version = version;
        this.columns = columns;
        this.allow_edit = allow_edit;
        this.panes = panes;
        this.datasources = datasources;
    }
}

class Datasource{
    private String name;
    private String type;
    private Settings settings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    Datasource(String name, String type, Settings settings) {
        this.name = name;
        this.type = type;
        this.settings = settings;
    }
}

class Settings{
    private String url;
    private Boolean use_thingproxy;
    private Integer refresh;
    private String method;

    Settings(String url, Boolean use_thingproxy, Integer refresh, String method) {
        this.url = url;
        this.use_thingproxy = use_thingproxy;
        this.refresh = refresh;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUse_thingproxy() {
        return use_thingproxy;
    }

    public void setUse_thingproxy(Boolean use_thingproxy) {
        this.use_thingproxy = use_thingproxy;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}