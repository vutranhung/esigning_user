package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MTRelease {

    @SerializedName("TID")
    @Expose
    private Integer TID;
    @SerializedName("APPID")
    @Expose
    private String APPID;
    @SerializedName("CURRENTVERSION")
    @Expose
    private Float CURRENTVERSION;
    @SerializedName("NEWVERSION")
    @Expose
    private Float NEWVERSION;
    @SerializedName("RELEASENOTE")
    @Expose
    private String RELEASENOTE;
    @SerializedName("FROMIP")
    @Expose
    private String FROMIP ;
    @SerializedName("UPDATESTATUS")
    @Expose
    private String UPDATESTATUS ;
    @SerializedName("ISSERVER")
    @Expose
    private Float ISSERVER;

    public Integer getTID() {
        return TID;
    }

    public void setTID(Integer TID) {
        this.TID = TID;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public Float getCURRENTVERSION() {
        return CURRENTVERSION;
    }

    public void setCURRENTVERSION(Float CURRENTVERSION) {
        this.CURRENTVERSION = CURRENTVERSION;
    }

    public Float getNEWVERSION() {
        return NEWVERSION;
    }

    public void setNEWVERSION(Float NEWVERSION) {
        this.NEWVERSION = NEWVERSION;
    }

    public String getRELEASENOTE() {
        return RELEASENOTE;
    }

    public void setRELEASENOTE(String RELEASENOTE) {
        this.RELEASENOTE = RELEASENOTE;
    }

    public String getFROMIP() {
        return FROMIP;
    }

    public void setFROMIP(String FROMIP) {
        this.FROMIP = FROMIP;
    }

    public String getUPDATESTATUS() {
        return UPDATESTATUS;
    }

    public void setUPDATESTATUS(String UPDATESTATUS) {
        this.UPDATESTATUS = UPDATESTATUS;
    }

    public Float getISSERVER() {
        return ISSERVER;
    }

    public void setISSERVER(Float ISSERVER) {
        this.ISSERVER = ISSERVER;
    }
}
