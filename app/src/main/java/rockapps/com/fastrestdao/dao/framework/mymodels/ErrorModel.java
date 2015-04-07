package rockapps.com.fastrestdao.dao.framework.mymodels;

/**
 * Created by Renato on 09/11/2014.
 */
public class ErrorModel {
    private int errorCode;
    private String errorCodeDesc;
    private String userMessage;
    private String devMessage;
    private String more;
    private String applicationCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCodeDesc() {
        return errorCodeDesc;
    }

    public void setErrorCodeDesc(String errorCodeDesc) {
        this.errorCodeDesc = errorCodeDesc;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }
}
