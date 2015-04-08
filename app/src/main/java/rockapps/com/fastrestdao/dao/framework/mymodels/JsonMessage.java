package rockapps.com.fastrestdao.dao.framework.mymodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Renato on 09/11/2014.
 */
public class JsonMessage {
    protected String status;
    protected String msg;
    protected String error;
    protected String code;


    public boolean success() {

        return msg != null && msg.toLowerCase().equals("sucesso");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
