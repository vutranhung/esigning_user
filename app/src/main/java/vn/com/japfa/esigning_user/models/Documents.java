package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Documents {

    @SerializedName("documentData")
    @Expose
    private List<DocumentDatum> documentData = null;
    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;

    /**
     * No args constructor for use in serialization
     *
     */
    public Documents() {
    }

    /**
     *
     * @param responseMeta
     * @param documentData
     */
    public Documents(List<DocumentDatum> documentData, ResponseMeta responseMeta) {
        super();
        this.documentData = documentData;
        this.responseMeta = responseMeta;
    }

    public List<DocumentDatum> getDocumentData() {
        return documentData;
    }

    public void setDocumentData(List<DocumentDatum> documentData) {
        this.documentData = documentData;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

}
