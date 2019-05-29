package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignFollowDatum {

    @SerializedName("is_show")
    @Expose
    private String isShow;
    @SerializedName("signComment")
    @Expose
    private String signComment;
    @SerializedName("signedAt")
    @Expose
    private String signedAt;
    @SerializedName("signprocess_id")
    @Expose
    private Integer signprocessId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userPosition")
    @Expose
    private String userPosition;
    @SerializedName("user_level")
    @Expose
    private Integer userLevel;

    /**
     * No args constructor for use in serialization
     *
     */
    public SignFollowDatum() {
    }

    /**
     *
     * @param signedAt
     * @param userLevel
     * @param userName
     * @param signprocessId
     * @param signComment
     * @param userPosition
     * @param isShow
     */
    public SignFollowDatum(String isShow, String signComment, String signedAt, Integer signprocessId, String userName, String userPosition, Integer userLevel) {
        super();
        this.isShow = isShow;
        this.signComment = signComment;
        this.signedAt = signedAt;
        this.signprocessId = signprocessId;
        this.userName = userName;
        this.userPosition = userPosition;
        this.userLevel = userLevel;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getSignComment() {
        return signComment;
    }

    public void setSignComment(String signComment) {
        this.signComment = signComment;
    }

    public String getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(String signedAt) {
        this.signedAt = signedAt;
    }

    public Integer getSignprocessId() {
        return signprocessId;
    }

    public void setSignprocessId(Integer signprocessId) {
        this.signprocessId = signprocessId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

}