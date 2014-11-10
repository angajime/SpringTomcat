package hello;

import java.util.List;

/**
 * Created by tarazaky on 6/11/14.
 */
public class Pane {
    private Integer width;
    private Integer row;
    private Integer col_width;
    private Integer col;
    private List<Widget> widgets;

    public Pane(Integer width, Integer row, Integer col_width, Integer col, List<Widget> widgets) {
        this.width = width;
        this.row = row;
        this.col_width = col_width;
        this.widgets = widgets;
        this.col = col;
    }

    public Integer getCol() {

        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol_width() {
        return col_width;
    }

    public void setCol_width(Integer col_width) {
        this.col_width = col_width;
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }
}
