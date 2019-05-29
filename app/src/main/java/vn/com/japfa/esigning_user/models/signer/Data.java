package vn.com.japfa.esigning_user.models.signer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.com.japfa.esigning_user.models.ResponseMeta;

public class Data {

    @SerializedName("documentsData")
    @Expose
    private List<DocumentsDatum> documentsData = null;
    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;

    /**
     * No args constructor for use in serialization
     *
     */
    public Data() {
    }

    /**
     *
     * @param responseMeta
     * @param documentsData
     */
    public Data(List<DocumentsDatum> documentsData, ResponseMeta responseMeta) {
        super();
        this.documentsData = documentsData;
        this.responseMeta = responseMeta;
    }

    public List<DocumentsDatum> getDocumentsData() {
        return documentsData;
    }

    public void setDocumentsData(List<DocumentsDatum> documentsData) {
        this.documentsData = documentsData;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

}
