package util;

import java.io.Serializable;

public class LogOutDTO implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
