package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomRequest extends Request<JSONObject> {

	private Listener<JSONObject> listener;
	private Map<String, String> params;
	private Activity activity;

	public CustomRequest(String url, Map<String, String> params,
			Listener<JSONObject> reponseListener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.listener = reponseListener;
		this.params = params;
		setUserToken();
	}

	public CustomRequest(Activity activity, String url,
			Map<String, String> params, Listener<JSONObject> reponseListener,
			ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.activity = activity;
		this.listener = reponseListener;
		this.params = params;
		setUserToken();
	}

	private void setUserToken() {
//		if (params == null)
//			params = new HashMap<String, String>();
//
//		String api_key = new CustomerDao(activity).getInfo().getApi_key();
//		if (api_key != null) {
//			params.put("api_key", api_key);
//		}

	}

	public CustomRequest(int method, String url, Map<String, String> params,
			Listener<JSONObject> reponseListener, ErrorListener errorListener) {
		super(method, url, errorListener);
		
		this.listener = reponseListener;
		this.params = params;	
	}
	

	public CustomRequest(Activity activity, int method, String url, Map<String, String> params,
			Listener<JSONObject> reponseListener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.activity = activity;
		this.listener = reponseListener;
		this.params = params;
		setUserToken();
	}

	protected Map<String, String> getParams()
			throws com.android.volley.AuthFailureError {
		return params;
	};

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		Log.w(" response", " NetworkResponse response");
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new JSONObject(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		Log.w(" response", " deliverResponse");
		listener.onResponse(response);
	}

}
