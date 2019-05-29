package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("loginUser")
    @Expose
    private LoginUser loginUser;
    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param responseMeta
     * @param loginUser
     */
    public User(LoginUser loginUser, ResponseMeta responseMeta) {
        super();
        this.loginUser = loginUser;
        this.responseMeta = responseMeta;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

}

