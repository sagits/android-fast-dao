package rockapps.com.fastrestdao.dao.framework.mymodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Renato on 09/11/2014.
 */
public class JsonMessage {
    @SerializedName("_meta")
    protected MetaModel meta = new MetaModel();


    public boolean success() {

        return meta != null && meta.getStatus() != null && meta.getStatus().equals("SUCCESS");
    }

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    public String getMessage() {
        return meta.getStatus();
    }
}
