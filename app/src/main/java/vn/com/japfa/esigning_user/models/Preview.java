package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Preview {

    @SerializedName("pdfData")
    @Expose
    private byte[] pdfData = null;
    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;

    /**
     * No args constructor for use in serialization
     *
     */
    public Preview() {
    }

    /**
     *
     * @param responseMeta
     * @param pdfData
     */
    public Preview(byte[] pdfData, ResponseMeta responseMeta) {
        super();
        this.pdfData = pdfData;
        this.responseMeta = responseMeta;
    }

    public byte[] getPdfData() {
        return pdfData;
    }

    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

}