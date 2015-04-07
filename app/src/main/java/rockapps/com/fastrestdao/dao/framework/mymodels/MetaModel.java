package rockapps.com.fastrestdao.dao.framework.mymodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Renato on 07/11/2014.
 */
public class MetaModel {
    private String status;
    private int count;
    @SerializedName("_errors")
    private ErrorModel errorModel;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ErrorModel getErrorModel() {
        return errorModel;
    }

    public void setErrorModel(ErrorModel errorModel) {
        this.errorModel = errorModel;
    }
}
