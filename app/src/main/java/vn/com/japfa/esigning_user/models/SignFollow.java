package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignFollow {

    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;
    @SerializedName("signFollowData")
    @Expose
    private List<SignFollowDatum> signFollowData = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SignFollow() {
    }

    /**
     *
     * @param responseMeta
     * @param signFollowData
     */
    public SignFollow(ResponseMeta responseMeta, List<SignFollowDatum> signFollowData) {
        super();
        this.responseMeta = responseMeta;
        this.signFollowData = signFollowData;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

    public List<SignFollowDatum> getSignFollowData() {
        return signFollowData;
    }

    public void setSignFollowData(List<SignFollowDatum> signFollowData) {
        this.signFollowData = signFollowData;
    }

}