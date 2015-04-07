package rockapps.com.fastrestdao.dao.framework.mymodels;

import java.util.ArrayList;
import java.util.List;

public class JsonMessageList<E> extends JsonMessage {

    public JsonMessageList() {

    }

    private List<E> object = new ArrayList<E>();


    public List<E> getObject() {
        return object;
    }

    public void setObject(List<E> object) {
        this.object = object;
    }
}
