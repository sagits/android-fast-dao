package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.List;

import rockapps.com.fastrestdao.dao.framework.mymodels.JsonMessageList;

public class CallListListener<E> extends CallListener<E> {
    public JsonMessageList<E> jsonMessageList;
    private String objectName = "records";

    // NO DIALOG
    public CallListListener(Activity activity, Class<E> type) {
        super(activity, type);
    }


    // LAYOUT DIALOG
    public CallListListener(Activity activity, Class<E> type, RelativeLayout parentLayout) {
        super(activity, type, parentLayout);
    }

    // LAYOUT DIALOG, ERROR DIALOG, SAVE LIST
    public CallListListener(Activity activity, Class<E> type, RelativeLayout parentLayout, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        super(activity, type, parentLayout, onDialogButtonClick, saveLocal, saveLocalName);
    }

    // DIALOG, ERROR DIALOG
    public CallListListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick) {
        super(activity, type, message, onDialogButtonClick);
    }


    // JUST ERROR DIALOG
    public CallListListener(Activity activity, Class<E> type, OnDialogButtonClick onDialogButtonClick) {
        super(activity, type, onDialogButtonClick);
    }


    // JUST DIALOG
    public CallListListener(Activity activity, Class<E> type, String message) {
        super(activity, type, message);
    }


    // SAVE LOCAL LIST, WITH DIALOG, WITH ERROR DIALOG
    public CallListListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        super(activity, type, message, onDialogButtonClick, saveLocal, saveLocalName);
    }


    // CHANGE OBJECT ARRAY NAME, WITH DIALOG, WITH ERROR DIALOG
    public CallListListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, String objectName) {
        super(activity, type, message, onDialogButtonClick);
        this.objectName = objectName;
    }

    // CHANGE OBJECT ARRAY NAME, WITH DIALOG, WITH ERROR DIALOG, SAVE LOCAL LIST
    public CallListListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName, String objectName) {
        super(activity, type, message, onDialogButtonClick, saveLocal, saveLocalName);
        this.objectName = objectName;
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


        try {
            jsonMessageList = gson.fromJson(json.toString(), JsonMessageList.class);
        } catch (Exception e) {
            SmarterLogMAKER.w("error trying to parse json object list");
        }


        try {
            List<E> list = getList(type, json.getJSONArray(objectName));
            if (saveLocal) {
                saveLocal(list, saveLocalName);
            }
            jsonMessageList.setObject(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<E> getList(Class<E> type, JSONArray json) throws Exception {
        Gson gsonB = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gsonB.fromJson(json.toString(), new JsonListHelper<E>(type));
    }

    public String removeAcentos(String str) {

        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;

    }

    public String getErrorMessage() {
        try {
            return jsonMessageList.getError();
        } catch (Exception e) {
            return defaultErrorMessage;
        }
    }


    public boolean success() {
        return jsonMessageList.success();
    }
}
