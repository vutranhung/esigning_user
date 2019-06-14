package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

   /* @SerializedName("loginUser")
    @Expose
    private LoginUser loginUser;
    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;

    *//**
     * No args constructor for use in serialization
     *
     *//*
    public User() {
    }

    */
    /*
        public User(LoginUser loginUser, ResponseMeta responseMeta) {
            super();
            this.loginUser = loginUser;
            this.responseMeta = responseMeta;
        }

        public LoginUser getLoginUser() {
            return loginUser;
        }

        public void setLoginUser(LoginUser loginUser) {
            this.loginUser = loginUser;
        }

        public ResponseMeta getResponseMeta() {
            return responseMeta;
        }

        public void setResponseMeta(ResponseMeta responseMeta) {
            this.responseMeta = responseMeta;
        }*/

    private int ID;
    private String USERNAME;
    private String PASSWORD;
    private String FULLNAME;
    private String EMAIL;
    private String ROLE;
    private String ACTIVE ;
    private Integer ID_LOCATION;
    private String POSITION_ID;
    private String PHONE ;
    private byte[] SIGNATURE_IMG ;
    private String CODE ;
    private String LOGIN_TYPE ;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public Integer getID_LOCATION() {
        return ID_LOCATION;
    }

    public void setID_LOCATION(Integer ID_LOCATION) {
        this.ID_LOCATION = ID_LOCATION;
    }

    public String getPOSITION_ID() {
        return POSITION_ID;
    }

    public void setPOSITION_ID(String POSITION_ID) {
        this.POSITION_ID = POSITION_ID;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public byte[] getSIGNATURE_IMG() {
        return SIGNATURE_IMG;
    }

    public void setSIGNATURE_IMG(byte[] SIGNATURE_IMG) {
        this.SIGNATURE_IMG = SIGNATURE_IMG;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getLOGIN_TYPE() {
        return LOGIN_TYPE;
    }

    public void setLOGIN_TYPE(String LOGIN_TYPE) {
        this.LOGIN_TYPE = LOGIN_TYPE;
    }
}

