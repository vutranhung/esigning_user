package vn.com.japfa.esigning_user.Interface;


import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.japfa.esigning_user.models.ActionSigner;
import vn.com.japfa.esigning_user.models.AnnualLeave;
import vn.com.japfa.esigning_user.models.AnnualLeaveResponse;
import vn.com.japfa.esigning_user.models.DeleteDocResponse;
import vn.com.japfa.esigning_user.models.Documents;
import vn.com.japfa.esigning_user.models.MTRelease;
import vn.com.japfa.esigning_user.models.Preview;
import vn.com.japfa.esigning_user.models.ShowDocument;
import vn.com.japfa.esigning_user.models.SignActivityDatum;
import vn.com.japfa.esigning_user.models.SignFollow;
import vn.com.japfa.esigning_user.models.User;
import vn.com.japfa.esigning_user.models.signer.Data;
import vn.com.japfa.esigning_user.models.signer.DocumentsDatum;
import vn.com.japfa.esigning_user.models.signer.UserName;
import vn.com.japfa.esigning_user.util.Constant;

public interface Service {
    //String base_URL = "http://apps.japfa.com.vn:62040/";

    //region login and update
   /* @POST("RestService.svc/loginWithAuthentication")
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
    Call<Preview> preview(@Header("UserID") int userid, @Path("documentID") String documentid);

    @GET("RestService.svc/owner_documents")
    Call<Documents> getListDocuments(@Header("UserID") int userid);

    @GET("RestService.svc/list_signfollow")
    Call<SignFollow> getListSignFollow(@Header("UserID") int userid, @Query("type") String type);

    @GET("RestService.svc/documents/{documentID}/sign_follow")
    Call<SignFollow> getListSignFollowDetail(@Header("UserID") int userid, @Path("documentID") String documentid);

    @GET("RestService.svc/documents/{documentID}")
    Call<ShowDocument> editDocument(@Header("UserID") int userid, @Path("documentID") String documentid);

    @POST("RestService.svc/documents/{documentID}/send")
    Call<Documents> sendData(@Header("UserID") int userid, @Path("documentID") String documentid);

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
    Call<DeleteDocResponse> delete(@Header("UserID") int userid, @Path("documentID") String documentid);
    //endregion

    //region signer

    @POST("RestService.svc/login")
    Call<User> login(@Query("username") String user, @Query("password") String password);

    @GET("RestService.svc/documents")
    Call<Data> getData(@Header("UserID") int userid);

    @POST("RestService.svc/documents/{documentID}/sendcode")
    Call<Data> sendCode(@Header("UserID") int userid, @Path("documentID") String documentid);

    @POST("RestService.svc/documents/{documentID}/sign")
    Call<Data> accept(@Header("UserID") int userid, @Path("documentID") String documentid
            , @Query("verifyCode") String verifyCode, @Query("comment") String comment);

    @GET("RestService.svc/documents/{documentID}/signedusers")
    Call<Data> listSigned(@Header("UserID") int userid, @Path("documentID") String documentid);

    @POST("RestService.svc/documents/{documentID}/pending")
    Call<Data> pending(@Header("UserID") int userid, @Path("documentID") String documentid
            , @Query("comment") String comment);

    @POST("RestService.svc/documents/{documentID}/reject")
    Call<Data> reject(@Header("UserID") int userid, @Path("documentID") String documentid
            , @Query("comment") String comment);

    @PUT("RestService.svc/documents/{documentID}/is_viewed")
    Call<Data> isView(@Header("UserID") int userid, @Path("documentID") String documentid);

    @GET("RestService.svc/all_users")
    Call<UserName> getAllUser(@Header("UserID") int userid);

    @POST("RestService.svc/documents/{documentID}/forward")
    Call<UserName> forward(@Header("UserID") int userid, @Path("documentID") String documentid, @Query("toUser") String username
            , @Query("comment") String comment);
    //endregion
*/


   //OK
    @GET("Permission/LoginWithAuthentication")
    Call<User> login(@Query("email") String email);


    @GET("Apps/esigninguser.apk")
    Call<ResponseBody> download();

    @GET("Release/GetRelease_MaxVersion")
    Call<MTRelease> checkVersionAndUpdate(@Query("appName") String appName);
    //endregion

   //OK
    //region user
    @POST("CreateEDocument/CreateEDocument")
    Call<Documents> saveData(@Header("UserID") int userid, @Body String rawInformation, @Query("type") String type);

    //OK
    @GET("ViewDocument/PreviewForMobile")
    Call<String> preview(@Header("UserID") int userid, @Query("documentID") String documentid);
    //OK
    @GET("GetListOwnerDocument/GetListOwnerDocument")
    Call<List<Documents>> getListDocuments(@Header("UserID") int userid);

    //OK
    @GET("SignUser/GetListSignFollowByType")
    Call<List<SignFollow>> getListSignFollowByType(@Header("UserID") int userid, @Query("type") String type);
   //OK
    @GET("SignUser/GetListSignFollowForDocument")
    Call<List<SignFollow>> getListSignFollowDetail(@Header("UserID") int userid, @Query("docID") String documentid);

   //OK
    @GET("ViewDocument/ViewDocument")
    Call<String> editDocument(@Header("UserID") int userid, @Query("documentID") String documentid);
    //OK
    @POST("SendDocument/SendDocument")
    Call<Void> sendData(@Header("UserID") int userid, @Query("documentID") String documentid);

    @GET("GetListSignActivitiesOfSigner/GetListSignActivitiesOfSigner")
    Call<List<SignActivityDatum>> getActionSigner(@Query("signprocessID") String signprocess_id);

    //OK
    @GET("GetListAnnualLeaveByCondition/GetListAnnualLeaveByCondition")
    Call<AnnualLeave> getAnnualLeaveByUserId(@Header("UserID") int userid,
                                             @Query("fromDate1") String fromDate1, @Query("txtDayWillTake1") String DaytxtDayWillTake1,
                                             @Query("fromDate2") String fromDate2,
                                             @Query("txtDayWillTake2") String DaytxtDayWillTake2,
                                             @Query("typeOfLeave") String typeOffLeaveIndex);

    //OK
    @DELETE("DeleteDocument/DeleteDocument")
    Call<Void> delete(@Header("UserID") int userid, @Query("documentID") String documentid);
    //endregion

    //region signer
   //TODO hien tai khong su dung
    @FormUrlEncoded
    @GET("Permission/login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

   //OK
    @GET("SignProcess/GetListDocumentSignerByUserId")
    Call<List<DocumentsDatum>> getData(@Header("UserID") int userid);

    //TODO hien tai khong su dung
    @POST("/RestService.svc/documents/{documentID}/sendcode")
    Call<Data> sendCode(@Header("UserID") int userid, @Path("documentID") String documentid);


    //OK
    @POST("SignDocument/SignDocument")
    Call<Void> accept(@Header("UserID") int userid, @Query("documentID") String  documentid
            , @Query("verifyCode") String verifyCode, @Query("comment") String comment);

    //OK
    @GET("SignUser/GetListSignedUser")
    Call<List<DocumentsDatum>> listSigned(@Header("UserID") int userid, @Query("documentID") String documentid);

   //OK
    @POST("SignDocument/PendingDocument")
    Call<Void> pending(@Header("UserID") int userid, @Query("documentID") String documentid
            , @Query("comment") String comment);

   //OK
    @POST("RejectDocument/RejectDocument")
    Call<Void> reject(@Header("UserID") int userid, @Query("documentID") String documentid
            , @Query("comment") String comment);

    //OK
    @PUT("SignProcess/IsViewed")
    Call<Void> isView(@Header("UserID") int userid, @Query("documentID") String documentid);

    //OK
    @GET("SignProcess/GetListUserNameForForwardSign")
    Call<List<String>> getAllUser(@Header("UserID") int userid);

    //OK
    @POST("SignDocument/ForwardDocument")
    Call<Void> forward(@Header("UserID") int userid, @Query("documentID") String documentid, @Query("toUsername") String username
            , @Query("comment") String comment);
}
