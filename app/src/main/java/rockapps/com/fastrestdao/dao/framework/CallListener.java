package rockapps.com.fastrestdao.dao.framework;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import com.android.volley.Response.ErrorListener;

import rockapps.com.fastrestdao.dao.local.LocalDbImplement;

public abstract class CallListener<E> implements ErrorListener, Response.Listener<JSONObject>, VolleyOnPreExecute {
    public Activity activity;
    public RelativeLayout parentLayout;
    protected Gson gson = new Gson();
    protected Class<E> type;
    protected ProgressDialog dialog;
    protected final String defaultErrorMessage = "Houve um erro, por favor tente novamente.";
    protected final String defaultErrorTitle = "Problema de conexão";
    protected final String defaultSuccessButtonMessage = "Tentar novamente";
    protected final String defaultErrorButtonMessage = "Cancelar";
    protected OnDialogButtonClick onDialogButtonClick;
    protected String saveLocalName;
    protected Boolean saveLocal = false;


    private View loadingView;
    private View errorView;
    public DefaultDialog errorDialog;

    public CallListener() {
        onPreExecute();
    }

    public CallListener(Class<E> type) {
        this();
        this.type = type;
    }

    public CallListener(Activity activity, Class<E> type) {
        this();
        this.activity = activity;
        this.type = type;
    }

    public CallListener(Activity activity, Class<E> type, OnDialogButtonClick onDialogButtonClick) {
        this();
        this.onDialogButtonClick = onDialogButtonClick;
        this.activity = activity;
        this.type = type;
    }

    public CallListener(Activity activity, Class<E> type, RelativeLayout parentLayout) {
        this();
        this.activity = activity;
        this.parentLayout = parentLayout;
        this.type = type;
        onPreExecute();
    }

    public CallListener(Activity activity, Class<E> type, String message) {
        this();
        this.activity = activity;
        if (message != null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(message);
            dialog.setCancelable(false);
        }
        this.type = type;
        onPreExecute();
    }

    public CallListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick) {
        this();
        this.onDialogButtonClick = onDialogButtonClick;
        this.activity = activity;
        if (message != null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(message);
            dialog.setCancelable(false);
        }
        this.type = type;
        onPreExecute();
    }

    public CallListener(Activity activity, Class<E> type, RelativeLayout parentLayout, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        this();
        this.activity = activity;
        this.onDialogButtonClick = onDialogButtonClick;
        this.parentLayout = parentLayout;
        this.type = type;
        this.saveLocal = saveLocal;
        this.saveLocalName = saveLocalName;
        onPreExecute();
    }


    public CallListener(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        this();
        this.onDialogButtonClick = onDialogButtonClick;
        this.activity = activity;
        if (message != null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(message);
            dialog.setCancelable(false);
        }
        this.type = type;
        this.saveLocal = saveLocal;
        this.saveLocalName = saveLocalName;
        onPreExecute();
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        onPostExecute("error");

        Log.w(" erro volley", " erro");

        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            SmarterLogMAKER.w("Error: " + new JSONObject(responseBody).toString(4));
            onParse(new JSONObject(responseBody));
        } catch (Exception e) {

        }

        String errorMsg = "";
        if (error instanceof TimeoutError) {
            errorMsg = "TimeoutError";
        } else if (error instanceof ServerError) {
            errorMsg = "isServerProblem";
        } else if (error instanceof AuthFailureError) {
            errorMsg = "Authentication error";
        } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
            errorMsg = "isNetworkProblem";
        }
        Log.w("Parent activity", activity.getClass().getSimpleName() + "");
        Log.w("Error volley:", errorMsg);

        try {
            SmarterLogMAKER.w("http error code " + error.networkResponse.statusCode);
        } catch (Exception e) {
        }

    }

    public void onPreExecute() {
        if (parentLayout != null) {
            inflateLoadingScreen(parentLayout);
        } else if (dialog != null) {
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    protected void onPostExecute(String status) {
        removeDialog();
        if (parentLayout != null) {
            removeLoadingScreen(parentLayout);
            if (status.equals("error")) {
                inflateErrorScreen(parentLayout);
            }
        } else if (status.equals("error") && onDialogButtonClick != null) {
            createDefaultDialog();
        }
    }

    @Override
    public void onResponse(JSONObject json) {
        try {
            SmarterLogMAKER.w("Sucess: " + json.toString(4));
        } catch (Exception e) {
        }
        onPostExecute("sucess");
        onParse(json);

    }

    public void onParse(JSONObject json) {
    }

    public void inflateLoadingScreen(RelativeLayout fragmentLayout) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View loadingView = (View) inflater.inflate(R.layout.loading, fragmentLayout, false);
        fragmentLayout.addView(loadingView);
    }

    public void removeLoadingScreen(View fragmentLayout) {
        //LayoutInflater inflater = getLayoutInflater();
        View loadingView = (View) fragmentLayout.findViewById(R.id.loadingLayout);
        ((RelativeLayout) fragmentLayout).removeView(loadingView);
    }

    public void inflateErrorScreen(RelativeLayout fragmentLayout) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View loadingView = (View) inflater.inflate(R.layout.loading_error, fragmentLayout, false);
        fragmentLayout.addView(loadingView);

        if (onDialogButtonClick != null) {
            setTryAgainButton(loadingView);
        }
    }

    public void removeErrorScreen(View fragmentLayout) {
        removeLoadingScreen(fragmentLayout);
    }

    public void setTryAgainButton(View loadingView) {
        Button tryAgainButton = (Button) loadingView.findViewById(R.id.tryAgainButton);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeErrorScreen(parentLayout);
                onDialogButtonClick.onPositiveClick();
            }
        });
    }

    public void removeDialog() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }
    }


    public static interface VolleyOnPreExecute {
        void onPreExecute();
    }

    public void createDefaultDialog() {
        try {
            DefaultDialog errorDialog = DefaultDialog.newInstance(defaultErrorTitle, defaultErrorMessage, defaultSuccessButtonMessage, defaultErrorButtonMessage, onDialogButtonClick);
            errorDialog.show(activity.getFragmentManager(), null);
        } catch (Exception e) {
            SmarterLogMAKER.w("Error trying to create dialog. Please check your activity");
        }
    }

    public void saveLocal(E object, String name) {
        new LocalDbImplement<E>(activity.getApplicationContext()).save(object, type, name == null ? type.getSimpleName() : name);
    }

    public void saveLocal(List<E> objects, String name) {
        name = name == null ? type.getSimpleName() : name;
        new LocalDbImplement<E>(activity.getApplicationContext()).saveList(objects, type, name);
    }

 /*   public E getLocalObjectFromList(int id) {
        List<E> localList = getLocalList();

        try {
            for (Object o : localList) {
                if (((MyModel) o).getId() == id) {
                    return (E) o;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    } */

    public E getLocalObject() {
        return new LocalDbImplement<E>(activity.getApplicationContext()).get(type, saveLocalName != null ? saveLocalName : type.getSimpleName());
    }

    public List<E> getLocalList() {
        String name = saveLocalName != null ? saveLocalName : type.getSimpleName();
        return new LocalDbImplement<E>(activity.getApplicationContext()).getList(type, name);
    }


}
