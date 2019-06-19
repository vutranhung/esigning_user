package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.com.japfa.esigning_user.base.BaseApp;

public class Documents {

    @SerializedName("CREATED_AT")
    @Expose
    private String createdAt;
    @SerializedName("Define_Type")
    @Expose
    private String defineType;
    @SerializedName("DESCRIPTION")
    @Expose
    private String description;
    @SerializedName("DOCUMENT_NO")
    @Expose
    private String documentNo;
    @SerializedName("Document_Type")
    @Expose
    private String documentType;
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("PRIORITY")
    @Expose
    private String priority;
    @SerializedName("STATUS")
    @Expose
    private String status;

    public String getCreatedAt() {
       // return BaseApp.formatViewDateString(createdAt);
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDefineType() {
        return defineType;
    }

    public void setDefineType(String defineType) {
        this.defineType = defineType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
