package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.app.ProgressDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;

public abstract class GenericAbstractDao<E> {

    protected Activity activity;
    protected Gson gson;
    protected ProgressDialog dialog;
    protected String serverUrl = "serverurl";
    protected String modelUrl;

    public GenericAbstractDao(Activity activity) {
        this.activity = activity;
        gson = new Gson();
    }

    protected void addRequest(Request request) {
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 5, 0.1f));
        SmarterLogMAKER.w("Making request " + getRequestType(request.getMethod()) + " to: " + request.getUrl());

        RequestController.getInstance(activity).addToRequestQueue(request);
    }

    public abstract void getAll(CallListListener callListener, String url);

    public abstract void getById(CallSingleListener callListener, int id, String url);

    public abstract void add(CallSingleListener callListener, E model, String url);

    public abstract void edit(CallSingleListener callListener, E model, String url);

    public abstract void delete(CallSingleListener callListener, int id, String url);

    public abstract void getAll(CallListListener callListener);

    public abstract void getById(CallSingleListener callListener, int id);

    public abstract void add(CallSingleListener callListener, E model);

    public abstract void edit(CallSingleListener callListener, E model);

    public abstract void delete(CallSingleListener callListener, int id);

    private String getRequestType(int id) {
        String type = new String();
        switch (id) {
            case 0:
                type = "GET";
                break;
            case 1:
                type = "POST";
                break;
            case 2:
                type = "PUT";
                break;
            case 3:
                type = "DELETE";
                break;
            default:
                type = "UNKNOWN";
        }
        return type;
    }


    public HashMap mapFromObject(E model) {
        try {
            String gsonUser = new JSONObject(gson.toJson(model)).toString();
            HashMap<String,String> objectAsMap = new Gson().fromJson(gsonUser, new TypeToken<HashMap<String, String>>(){}.getType());

            return objectAsMap;
        } catch (Exception e) {
            return null;
        }

    }

    public HashMap mapFromAnotherObject(Object model) {
        try {
            String gsonUser = new JSONObject(gson.toJson(model)).toString();
            HashMap<String,String> objectAsMap = new Gson().fromJson(gsonUser, new TypeToken<HashMap<String, String>>(){}.getType());

            return objectAsMap;
        } catch (Exception e) {
            return null;
        }

    }

}
