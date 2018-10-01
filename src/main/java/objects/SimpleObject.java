package objects;

import java.util.ArrayList;
import java.util.List;

public class SimpleObject {

    private String name;
    private List<String> list;

    public String getName() {
        return name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setName(String name) {
        this.name = name;
        List l = new ArrayList();
    }
}
