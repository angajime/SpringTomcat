package hello;

import java.util.List;

/**
 * Created by tarazaky on 28/10/14.
 */
public class Client {
    private final String id;

    private int c;

    public Client(String id, int c){
        this.id = id;
        this.c = c;
    }


    public String getId() {
        return id;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
