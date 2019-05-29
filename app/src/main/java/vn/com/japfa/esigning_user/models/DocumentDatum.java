package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentDatum {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("define_type")
    @Expose
    private String defineType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("document_no")
    @Expose
    private String documentNo;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public DocumentDatum() {
    }

    /**
     *
     * @param id
     * @param documentType
     * @param status
     * @param priority
     * @param description
     * @param createdAt
     * @param defineType
     * @param documentNo
     */
    public DocumentDatum(String createdAt, String defineType, String description, String documentNo, String documentType, Integer id, String priority, String status) {
        super();
        this.createdAt = createdAt;
        this.defineType = defineType;
        this.description = description;
        this.documentNo = documentNo;
        this.documentType = documentType;
        this.id = id;
        this.priority = priority;
        this.status = status;
    }

    public String getCreatedAt() {
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

