package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowDocument {

    @SerializedName("rawInformation")
    @Expose
    private String rawInformation;


    /**
     * No args constructor for use in serialization
     *
     */
    public ShowDocument() {
    }

    /**
     *
     * @param rawInformation

     */
    public ShowDocument(String rawInformation ) {
        super();
        this.rawInformation = rawInformation;

    }

    public String getRawInformation() {
        return rawInformation;
    }

    public void setRawInformation(String rawInformation) {
        this.rawInformation = rawInformation;
    }



}