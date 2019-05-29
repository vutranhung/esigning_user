package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMeta {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseMeta() {
    }

    /**
     *
     * @param statusCode
     * @param message
     */
    public ResponseMeta(String message, String statusCode) {
        super();
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
