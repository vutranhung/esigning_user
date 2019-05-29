package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignActivityDatum {

    @SerializedName("action_type")
    @Expose
    private String actionType;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("signerComment")
    @Expose
    private String signerComment;

    /**
     * No args constructor for use in serialization
     *
     */
    public SignActivityDatum() {
    }

    /**
     *
     * @param createdAt
     * @param signerComment
     * @param actionType
     */
    public SignActivityDatum(String actionType, String createdAt, String signerComment) {
        super();
        this.actionType = actionType;
        this.createdAt = createdAt;
        this.signerComment = signerComment;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSignerComment() {
        return signerComment;
    }

    public void setSignerComment(String signerComment) {
        this.signerComment = signerComment;
    }

}