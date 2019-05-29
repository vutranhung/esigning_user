package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowDocument {

    @SerializedName("rawInformation")
    @Expose
    private String rawInformation;
    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShowDocument() {
    }

    /**
     *
     * @param rawInformation
     * @param responseMeta
     */
    public ShowDocument(String rawInformation, ResponseMeta responseMeta) {
        super();
        this.rawInformation = rawInformation;
        this.responseMeta = responseMeta;
    }

    public String getRawInformation() {
        return rawInformation;
    }

    public void setRawInformation(String rawInformation) {
        this.rawInformation = rawInformation;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

}