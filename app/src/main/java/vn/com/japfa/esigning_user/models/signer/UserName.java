package vn.com.japfa.esigning_user.models.signer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.com.japfa.esigning_user.models.ResponseMeta;

public class UserName {

    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;
    @SerializedName("usernameData")
    @Expose
    private List<String> usernameData = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserName() {
    }

    /**
     *
     * @param responseMeta
     * @param usernameData
     */
    public UserName(ResponseMeta responseMeta, List<String> usernameData) {
        super();
        this.responseMeta = responseMeta;
        this.usernameData = usernameData;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

    public List<String> getUsernameData() {
        return usernameData;
    }

    public void setUsernameData(List<String> usernameData) {
        this.usernameData = usernameData;
    }

}