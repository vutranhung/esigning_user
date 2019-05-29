package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Goods {

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("productQuantity")
    @Expose
    private int productQuantity;
    @SerializedName("productReason")
    @Expose
    private String productReason;

    /**
     * No args constructor for use in serialization
     *
     */
    public Goods() {
    }

    /**
     *
     * @param productReason
     * @param productDescription
     * @param productQuantity
     * @param productName
     */
    public Goods(String productName, String productDescription, int productQuantity, String productReason) {
        super();
        this.productName = productName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productReason = productReason;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductReason() {
        return productReason;
    }

    public void setProductReason(String productReason) {
        this.productReason = productReason;
    }

}
