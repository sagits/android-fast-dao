package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.app.ProgressDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

public class RestDao<E> extends GenericAbstractDao<E>{

	public RestDao(Activity activity) {
        super(activity);
	}
	
	protected void addRequest(Request request) {
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 5, 0.1f));
        SmarterLogMAKER.w("Making request "+ getRequestType(request.getMethod()) +" to: "+request.getUrl());

        RequestController.getInstance(activity).addToRequestQueue(request);}

    public void getAll(CallListListener callListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, serverUrl + modelUrl, null, callListener, callListener);
        addRequest(request);
    };

    public void getById(CallSingleListener callListener, int id) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, serverUrl + modelUrl + "/" + id, null, callListener, callListener);
        addRequest(request);
    };

    public void add(CallSingleListener callListener,E model) {
        try {
            String gsonUser = new JSONObject(gson.toJson(model)).toString();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, serverUrl + modelUrl, new JSONObject(gson.toJson(model)), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }


    public void edit(CallSingleListener callListener,E model) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, serverUrl + modelUrl, new JSONObject(gson.toJson(model)), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }

    public void delete(CallSingleListener callListener, int id) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, serverUrl + modelUrl + "/" + id, null, callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {
        }
    }


    @Override
    public void getAll(CallListListener callListener, String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, callListener, callListener);
        addRequest(request);
    }

    @Override
    public void getById(CallSingleListener callListener, int id, String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + "/" + id, null, callListener, callListener);
        addRequest(request);
    }

    @Override
    public void add(CallSingleListener callListener, E model, String url) {
        try {
            String gsonUser = new JSONObject(gson.toJson(model)).toString();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(gson.toJson(model)), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }

    @Override
    public void edit(CallSingleListener callListener, E model, String url) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(gson.toJson(model)), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }

    @Override
    public void delete(CallSingleListener callListener, int id, String url) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url + "/" + id, null, callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {
        }
    }

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
}
