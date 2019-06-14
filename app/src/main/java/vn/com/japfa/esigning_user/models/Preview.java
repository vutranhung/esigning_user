package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Preview {

    @SerializedName("pdfData")
    @Expose
    private byte[] pdfData = null;


    /**
     * No args constructor for use in serialization
     *
     */
    public Preview() {
    }

    /**
     *

     * @param pdfData
     */
    public Preview(byte[] pdfData) {
        super();
        this.pdfData = pdfData;

    }

    public byte[] getPdfData() {
        return pdfData;
    }

    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }


}