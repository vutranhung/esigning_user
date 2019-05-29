package vn.com.japfa.esigning_user.Interface;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.japfa.esigning_user.models.ActionSigner;
import vn.com.japfa.esigning_user.models.AnnualLeaveResponse;
import vn.com.japfa.esigning_user.models.DeleteDocResponse;
import vn.com.japfa.esigning_user.models.Documents;
import vn.com.japfa.esigning_user.models.Preview;
import vn.com.japfa.esigning_user.models.ShowDocument;
import vn.com.japfa.esigning_user.models.SignFollow;
import vn.com.japfa.esigning_user.models.User;
import vn.com.japfa.esigning_user.models.signer.Data;
import vn.com.japfa.esigning_user.models.signer.UserName;

public interface Service {
    String base_URL = "http://apps.japfa.com.vn:62040/";

    //region login and update
    @POST("RestService.svc/loginWithAuthentication")
    Call<User> login(@Query("username") String user);

    @GET("Apps/esigninguser.apk")
    Call<ResponseBody> download();

    @GET("RestService.svc/version_esigninguser")
    Call<String> checkVersionAndUpdate();
    //endregion

    //region user
    @POST("RestService.svc/create_edocument")
    Call<Documents> saveData(@Header("UserID") int userid, @Body String s, @Query("type") String type);

    @GET("RestService.svc/documents/{documentID}/preview")
    Call<Preview> preview(@Header("UserID") int userid, @Path("documentID") String documenid);

    @GET("RestService.svc/owner_documents")
    Call<Documents> getListDocuments(@Header("UserID") int userid);

    @GET("RestService.svc/list_signfollow")
    Call<SignFollow> getListSignFollow(@Header("UserID") int userid, @Query("type") String type);

    @GET("RestService.svc/documents/{documentID}/sign_follow")
    Call<SignFollow> getListSignFollowDetail(@Header("UserID") int userid, @Path("documentID") String documenid);

    @GET("RestService.svc/documents/{documentID}")
    Call<ShowDocument> editDocument(@Header("UserID") int userid, @Path("documentID") String documenid);

    @POST("RestService.svc/documents/{documentID}/send")
    Call<Documents> sendData(@Header("UserID") int userid, @Path("documentID") String documenid);

    @GET("RestService.svc/signprocesses/{signprocess_id}/activities")
    Call<ActionSigner> getActionSigner(@Path("signprocess_id") String signprocess_id);

    @POST("RestService.svc/getAnnualLeaveByUserId")
    Call<AnnualLeaveResponse> getAnnualLeaveByUserId(@Header("UserID") int userid,
                                                     @Query("fromDate1") String fromDate1,
                                                     @Query("txtDayWillTake1") String DaytxtDayWillTake1,
                                                     @Query("fromDate2") String fromDate2,
                                                     @Query("txtDayWillTake2") String DaytxtDayWillTake2,
                                                     @Query("typeOfLeave") String typeOffLeaveIndex);

    @POST("RestService.svc/documents/{documentID}/delete")
    Call<DeleteDocResponse> delete(@Header("UserID") int userid, @Path("documentID") String documenid);
    //endregion

    //region signer

    @POST("RestService.svc/login")
    Call<User> login(@Query("username") String user, @Query("password") String password);

    @GET("RestService.svc/documents")
    Call<Data> getData(@Header("UserID") int userid);

    @POST("RestService.svc/documents/{documentID}/sendcode")
    Call<Data> sendCode(@Header("UserID") int userid, @Path("documentID") String documenid);

    @POST("RestService.svc/documents/{documentID}/sign")
    Call<Data> accept(@Header("UserID") int userid, @Path("documentID") String documenid
            , @Query("verifyCode") String verifyCode, @Query("comment") String comment);

    @GET("RestService.svc/documents/{documentID}/signedusers")
    Call<Data> listSigned(@Header("UserID") int userid, @Path("documentID") String documenid);

    @POST("RestService.svc/documents/{documentID}/pending")
    Call<Data> pending(@Header("UserID") int userid, @Path("documentID") String documenid
            , @Query("comment") String comment);

    @POST("RestService.svc/documents/{documentID}/reject")
    Call<Data> reject(@Header("UserID") int userid, @Path("documentID") String documenid
            , @Query("comment") String comment);

    @PUT("RestService.svc/documents/{documentID}/is_viewed")
    Call<Data> isView(@Header("UserID") int userid, @Path("documentID") String documenid);

    @GET("RestService.svc/all_users")
    Call<UserName> getAllUser(@Header("UserID") int userid);

    @POST("RestService.svc/documents/{documentID}/forward")
    Call<UserName> forward(@Header("UserID") int userid, @Path("documentID") String documenid, @Query("toUser") String username
            , @Query("comment") String comment);
    //endregion
}
