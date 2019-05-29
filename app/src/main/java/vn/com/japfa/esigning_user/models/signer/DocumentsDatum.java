package vn.com.japfa.esigning_user.models.signer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentsDatum {

    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("docDescription")
    @Expose
    private String docDescription;
    @SerializedName("documentNo")
    @Expose
    private String documentNo;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("document_id")
    @Expose
    private String documentId;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("is_viewed")
    @Expose
    private Integer isViewed;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("need_verify_code")
    @Expose
    private Integer needVerifyCode;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("recievedAt")
    @Expose
    private String recievedAt;
    @SerializedName("signedAtDisplay")
    @Expose
    private Object signedAtDisplay;
    @SerializedName("statusApproval")
    @Expose
    private String statusApproval;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("verifycode")
    @Expose
    private Object verifycode;

    /**
     * No args constructor for use in serialization
     *
     */
    public DocumentsDatum() {
    }

    /**
     *
     * @param needVerifyCode
     * @param statusApproval
     * @param documentType
     * @param locationName
     * @param recievedAt
     * @param documentNo
     * @param email
     * @param description
     * @param isViewed
     * @param priority
     * @param verifycode
     * @param userName
     * @param docDescription
     * @param signedAtDisplay
     * @param documentId
     */
    public DocumentsDatum(Object description, String docDescription, String documentNo, String documentType, String documentId, Object email, Integer isViewed, String locationName, Integer needVerifyCode, String priority, String recievedAt, Object signedAtDisplay, String statusApproval, String userName, Object verifycode) {
        super();
        this.description = description;
        this.docDescription = docDescription;
        this.documentNo = documentNo;
        this.documentType = documentType;
        this.documentId = documentId;
        this.email = email;
        this.isViewed = isViewed;
        this.locationName = locationName;
        this.needVerifyCode = needVerifyCode;
        this.priority = priority;
        this.recievedAt = recievedAt;
        this.signedAtDisplay = signedAtDisplay;
        this.statusApproval = statusApproval;
        this.userName = userName;
        this.verifycode = verifycode;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Integer getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Integer isViewed) {
        this.isViewed = isViewed;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getNeedVerifyCode() {
        return needVerifyCode;
    }

    public void setNeedVerifyCode(Integer needVerifyCode) {
        this.needVerifyCode = needVerifyCode;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRecievedAt() {
        return recievedAt;
    }

    public void setRecievedAt(String recievedAt) {
        this.recievedAt = recievedAt;
    }

    public Object getSignedAtDisplay() {
        return signedAtDisplay;
    }

    public void setSignedAtDisplay(Object signedAtDisplay) {
        this.signedAtDisplay = signedAtDisplay;
    }

    public String getStatusApproval() {
        return statusApproval;
    }

    public void setStatusApproval(String statusApproval) {
        this.statusApproval = statusApproval;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(Object verifycode) {
        this.verifycode = verifycode;
    }

}