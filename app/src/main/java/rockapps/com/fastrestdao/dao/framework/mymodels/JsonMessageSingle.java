package rockapps.com.fastrestdao.dao.framework.mymodels;

public class JsonMessageSingle<E> extends JsonMessage{
private E object;

    public JsonMessageSingle() {

    }

    public E getObject() {
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }

}
