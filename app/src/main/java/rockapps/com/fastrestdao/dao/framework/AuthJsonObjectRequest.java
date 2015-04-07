package rockapps.com.fastrestdao.dao.framework;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Renato on 16/11/2014.
 */
public class AuthJsonObjectRequest extends JsonObjectRequest {
    public AuthJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public AuthJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

       // UserModel user = ApplicationController.getInstance().getUser();

        HashMap<String, String> params = new HashMap<String, String>();
     //   String auth = "Bearer "  + user.getAuthToken();
    //    params.put("Authorization", auth);

     //   SmarterLogMAKER.w("auth credentials: " + auth);

        return params;
    }

}
