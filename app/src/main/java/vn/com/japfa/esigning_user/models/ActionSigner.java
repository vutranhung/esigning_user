package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActionSigner {

    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;
    @SerializedName("signActivityData")
    @Expose
    private List<SignActivityDatum> signActivityData = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ActionSigner() {
    }

    /**
     *
     * @param signActivityData
     * @param responseMeta
     */
    public ActionSigner(ResponseMeta responseMeta, List<SignActivityDatum> signActivityData) {
        super();
        this.responseMeta = responseMeta;
        this.signActivityData = signActivityData;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

    public List<SignActivityDatum> getSignActivityData() {
        return signActivityData;
    }

    public void setSignActivityData(List<SignActivityDatum> signActivityData) {
        this.signActivityData = signActivityData;
    }

}