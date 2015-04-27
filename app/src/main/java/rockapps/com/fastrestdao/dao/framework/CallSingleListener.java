package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import rockapps.com.fastrestdao.dao.framework.mymodels.JsonMessageSingle;


public class CallSingleListener<E> extends CallListener<E> {
    public JsonMessageSingle<E> jsonMessageSingle;
    private String objectName = "records";

    public CallSingleListener() {

    }

    public CallSingleListener(Class<E> type) {
        super(type);
    }



    // LAYOUT LOADING
    public CallSingleListener(Activity activity, Class<E> type, RelativeLayout parentLayout) {
        super(activity, type, parentLayout);
    }


    // LAYOUT LOADING WITH ON ERROR DIALOG AND SAVE
    public CallSingleListener(Activity activity, Class<E> type, RelativeLayout parentLayout, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        super(activity, type, parentLayout, onDialogButtonClick, saveLocal, saveLocalName);
    }


    // NO LOADING DIALOG WITH ON ERROR DIALOG
    public CallSingleListener(Activity activity, Class<E> type, OnDialogButtonClick onDialogButtonClick) {
        super(activity, type, onDialogButtonClick);
    }

    // ON ERROR DIALOG
    public CallSingleListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick) {
        super(activity, type, message, onDialogButtonClick);
    }

    // SAVE LOCAL AND ON ERROR DIALOG
    public CallSingleListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        super(activity, type, message, onDialogButtonClick, saveLocal, saveLocalName);
    }

    // FROM DIALOG
    public CallSingleListener(Activity activity, Class<E> type, String message) {
        super(activity, type, message);
    }

    // CHANGE OBJECT NAME AND ON ERROR DIALOG
    public CallSingleListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, String objectName) {
        super(activity, type, message, onDialogButtonClick);
        this.objectName = objectName;
    }

    // CHANGE OBJECT NAME AND SAVE LOCAL
    public CallSingleListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName, String objectName) {
        super(activity, type, message, onDialogButtonClick, saveLocal, saveLocalName);
        this.objectName = objectName;
    }


    // NO DIALOG
    public CallSingleListener(Activity activity, Class<E> type) {
        super(activity, type);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);

    }

    @Override
    public void onResponse(JSONObject json) {
        super.onResponse(json);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onParse(JSONObject json) {
        jsonMessageSingle = gson.fromJson(json.toString(), JsonMessageSingle.class);
        try {
            jsonMessageSingle.setObject(getObject(type, json.has(objectName) ? json.getJSONObject(objectName) : json));
            if (saveLocal) {
                saveLocal(jsonMessageSingle.getObject(), saveLocalName);
            }
        } catch (JSONException e) {
        }
    }

    public E getObject(Class<E> type, JSONObject json) {
        Gson gsonB = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gsonB.fromJson(json.toString(), type);
    }

    public String getErrorMessage() {
        try {
            return jsonMessageSingle.getError();
        } catch (Exception e) {
            return defaultErrorMessage;
        }
    }

    public boolean success() {
        return jsonMessageSingle.success();
    }

}
