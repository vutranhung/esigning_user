package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AnnualLeaveResponse {

    @SerializedName("AnnualLeave")
    @Expose
    private AnnualLeave annualLeave;
    @SerializedName("responseMeta")
    @Expose
    private ResponseMeta responseMeta;

    /**
     * No args constructor for use in serialization
     *
     */
    public AnnualLeaveResponse() {
    }

    /**
     *
     * @param annualLeave
     * @param responseMeta
     */
    public AnnualLeaveResponse(AnnualLeave annualLeave, ResponseMeta responseMeta) {
        super();
        this.annualLeave = annualLeave;
        this.responseMeta = responseMeta;
    }

    public AnnualLeave getAnnualLeave() {
        return annualLeave;
    }

    public void setAnnualLeave(AnnualLeave annualLeave) {
        this.annualLeave = annualLeave;
    }

    public ResponseMeta getResponseMeta() {
        return responseMeta;
    }

    public void setResponseMeta(ResponseMeta responseMeta) {
        this.responseMeta = responseMeta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("annualLeave", annualLeave).append("responseMeta", responseMeta).toString();
    }

}