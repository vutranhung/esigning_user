package vn.com.japfa.esigning_user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.microsoft.aad.adal.ADALError;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

import org.androidannotations.annotations.EActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.japfa.esigning_user.Interface.Service;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.documents.ActivityDocuments_;
import vn.com.japfa.esigning_user.models.User;

@EActivity
public class ActivityLogin extends AppCompatActivity {
    private String mUserName;
    private String versionLocal = "3.6";
    /* Azure AD Variables */
    private AuthenticationContext mAuthContext;
    private AuthenticationResult mAuthResult;

//    private String user = "test2";

    private static final String AUTHORITY = "https://login.microsoftonline.com/japfa.com";
    private static final String CLIENT_ID = "d14ca098-eb8e-4cbb-a748-09ff6035c55f";
    private static final String RESOURCE_ID = "https://graph.microsoft.com/";
    private static final String REDIRECT_URI = "http://localhost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        requestPermissions();
        mAuthContext = new AuthenticationContext(getApplicationContext(), AUTHORITY, false);
        checkVersionAndUpdate();
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        Intent main = new Intent(ActivityLogin.this, ActivityLogin_.class);
        startActivity(main);
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
    }

    //region login

    private void loginMicrosoft() {
        mAuthContext.acquireToken(ActivityLogin.this, RESOURCE_ID, CLIENT_ID, REDIRECT_URI, PromptBehavior.Always, getAuthInteractiveCallback());
    }

    private void login() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        Call<User> call = service.login(mUserName);
//        Call<User> call = service.login(user);

        call.enqueue(new CallBackCustom<User>(this) {
            @Override
            public void onResponseCustom(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    String statusCode = response.body().getResponseMeta().getStatusCode();
                    String message = response.body().getResponseMeta().getMessage();
                    if (statusCode.equals("200")) {
                        Intent intent = new Intent(ActivityLogin.this, ActivityDocuments_.class);
                        BaseApp.userID = response.body().getLoginUser().getId();
                        startActivity(intent);
                    } else {
                        Toast.makeText(ActivityLogin.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<User> call, Throwable t) {
                Toast.makeText(ActivityLogin.this, "Check on your Internet connection and try again.(Login error)", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //endregion

    //region yeu cau quyen truy cap
    private int REQUEST_PERMISSION = 123456;

    private void requestPermissions() {
        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            makerequest();
        }
    }

    private void makerequest() {
        requestPermissions(new String[]{
                Manifest.permission.INTERNET
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
        }, REQUEST_PERMISSION);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length == 0
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED
                    || grantResults[1] != PackageManager.PERMISSION_GRANTED
                    || grantResults[2] != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions();
            }
        }
    }
    //endregion

    //region auto update
    private void downloadAndInstall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.base_URL)
                .build();
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.download();

        // Set up progress before call
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    boolean ketqua = writeResponseBodyToDisk(response.body());
                    if (ketqua) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                            if (!haveInstallPermission) {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse(String.format("package:%s", getPackageName())));
                                startActivity(intent);
                                return;
                            }
                        }
                        String path = Environment.getExternalStorageDirectory() + "/download/" + "app.apk";
                        File toInstall = new File(path);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Uri apkUri = FileProvider.getUriForFile(ActivityLogin.this, getPackageName() + ".fileprovider", toInstall);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(intent);
                        } else {
                            Uri apkUri = Uri.fromFile(toInstall);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }
                } else
                    Toast.makeText(ActivityLogin.this, "Update error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityLogin.this, "OnFailure, Update error", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File path = new File(Environment.getExternalStorageDirectory() + "/download/" + "app.apk");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(path);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("++", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void checkVersionAndUpdate() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<String> call = service.checkVersionAndUpdate();

        call.enqueue(new CallBackCustom<String>(this) {
            @Override
            public void onResponseCustom(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    BaseApp.version = response.body();
                    if (!BaseApp.version.equals(versionLocal)) {
                        downloadAndInstall();
                    } else {
                        loginMicrosoft();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<String> call, Throwable t) {
                Toast.makeText(ActivityLogin.this, "Check internet - Version_esigning error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemExitLogin) {
            exit();
        }
        return true;
    }


    private AuthenticationCallback<AuthenticationResult> getAuthInteractiveCallback() {
        Log.e("++", "AuthenticationCallback");
        return new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onSuccess(AuthenticationResult result) {
                if (result == null || TextUtils.isEmpty(result.getAccessToken())
                        || result.getStatus() != AuthenticationResult.AuthenticationStatus.Succeeded) {
                    Log.e("++", "Authentication Result is invalid");
                    return;
                }
                mAuthResult = result;
                mUserName = mAuthResult.getUserInfo().getDisplayableId();
                login();
            }

            @Override
            public void onError(Exception exc) {
                if (exc instanceof AuthenticationException) {
                    ADALError error = ((AuthenticationException) exc).getCode();
                    if (error == ADALError.AUTH_FAILED_CANCELLED) {
                        Log.e("++", "The user cancelled the authorization request");
                        exit();
                    } else if (error == ADALError.AUTH_FAILED_NO_TOKEN) {
                        loginMicrosoft();
                    } else if (error == ADALError.NO_NETWORK_CONNECTION_POWER_OPTIMIZATION) {
                        Log.e("++", "Device is in doze mode or the app is in standby mode");
                    }
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("++", "onActivityResult");
        mAuthContext.onActivityResult(requestCode, resultCode, data);
    }
}
