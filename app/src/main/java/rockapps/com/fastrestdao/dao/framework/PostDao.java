package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.app.ProgressDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

public class PostDao<E> extends GenericAbstractDao<E>{

	public PostDao(Activity activity) {
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
            CustomRequest request = new CustomRequest(Request.Method.POST, serverUrl + modelUrl, mapFromObject(model), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }


    public void edit(CallSingleListener callListener,E model) {
        try {
            CustomRequest request = new CustomRequest(Request.Method.POST, serverUrl + modelUrl, mapFromObject(model), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }

    public void delete(CallSingleListener callListener, int id) {
        try {
            CustomRequest request = new CustomRequest(Request.Method.GET, serverUrl + modelUrl + "/" + id, null, callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {
        }
    }


    @Override
    public void getAll(CallListListener callListener, String url) {
        CustomRequest request = new CustomRequest(Request.Method.GET, url, null, callListener, callListener);
        addRequest(request);
    }

    @Override
    public void getById(CallSingleListener callListener, int id, String url) {
        CustomRequest request = new CustomRequest(Request.Method.GET, url + "/" + id, null, callListener, callListener);
        addRequest(request);
    }

    @Override
    public void add(CallSingleListener callListener, E model, String url) {
        try {
            String gsonUser = new JSONObject(gson.toJson(model)).toString();
            CustomRequest request = new CustomRequest(Request.Method.POST, url, mapFromObject(model), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }

    @Override
    public void edit(CallSingleListener callListener, E model, String url) {
        try {
            CustomRequest request = new CustomRequest(Request.Method.POST, url, mapFromObject(model), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }

    @Override
    public void delete(CallSingleListener callListener, int id, String url) {
        try {
            CustomRequest request = new CustomRequest(Request.Method.GET, url + "/" + id, null, callListener, callListener);
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
