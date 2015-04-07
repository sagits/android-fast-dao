package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.app.ProgressDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONObject;

public abstract class GenericAbstractDao<E> {

	protected Activity activity;
	protected Gson gson;
	protected ProgressDialog dialog;
	protected String serverUrl = "http://yoururl/";
    protected String modelUrl;

	public GenericAbstractDao(Activity activity) {
		this.activity = activity;
		gson = new Gson();
	}
	
	protected void addRequest(Request request) {
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 5, 0.1f));
        SmarterLogMAKER.w("Making request "+ getRequestType(request.getMethod()) +" to: "+request.getUrl());

        RequestController.getInstance(activity).addToRequestQueue(request);}

    public void getAll(CallListListener callListener){
        AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.GET, serverUrl + modelUrl, null, callListener, callListener);
        addRequest(request);
    };

    public void getById(CallSingleListener callListener, int id) {
        AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.GET, serverUrl + modelUrl + "/" + id, null, callListener, callListener);
        addRequest(request);
    };

    public void add(CallSingleListener callListener,E model) {
        try {
            String gsonUser = new JSONObject(gson.toJson(model)).toString();
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.POST, serverUrl + modelUrl, new JSONObject(gson.toJson(model)), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }


    public void edit(CallSingleListener callListener,E model) {
        try {
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.PUT, serverUrl + modelUrl, new JSONObject(gson.toJson(model)), callListener, callListener);
            addRequest(request);
        }
        catch (Exception e) {

        }
    }

    public void delete(CallSingleListener callListener, int id) {
        try {
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.DELETE, serverUrl + modelUrl + "/" + id, null, callListener, callListener);
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
