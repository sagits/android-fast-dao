package rockapps.com.fastrestdao.dao.framework;

/**
 * Created by Renato on 06/11/2014.
 */
public interface GenericInterfaceDao<E> {
    public void getAll(CallListListener callListener);
    public void getById(CallSingleListener callListener, int id);
    public void add(CallSingleListener callListener, E model);
    public void edit(CallSingleListener callListener, E model);
    public void delete(CallSingleListener callListener, int id);
}
