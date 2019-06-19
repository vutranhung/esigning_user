package vn.com.japfa.esigning_user.login_local;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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
import vn.com.japfa.esigning_user.ActivityLogin_;
import vn.com.japfa.esigning_user.Interface.Service;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.documents.ActivityDocuments_;
import vn.com.japfa.esigning_user.models.MTRelease;
import vn.com.japfa.esigning_user.models.User;
import vn.com.japfa.esigning_user.util.Constant;
import vn.com.japfa.esigning_user.util.UtilHelper;

@EActivity
public class LoginLocalActivity extends AppCompatActivity {

    private String userName, password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditer;
    private String versionLocal = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_local);
        setTitle("Login");
            requestPermissions();
        UtilHelper.getValueProperty(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkVersionAndUpdate();
        loadAccount();
    }

    private boolean mBacktoExit = false;
    @Override
    public void onBackPressed() {
        if (mBacktoExit) {
            Intent main = new Intent(LoginLocalActivity.this, ActivityLogin_.class);
            startActivity(main);
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            finish();
        }
        mBacktoExit = true;
        Toast.makeText(this, "please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBacktoExit = false;
            }
        }, 2000);
    }

    //region login
    @ViewById
    CheckBox checkBoxUserName;
    @ViewById
    CheckBox checkBoxPassword;
    @ViewById
    EditText editTextUser;
    @ViewById
    EditText editTextPassword;

    @Click
    protected void button_Login() {
        saveAccount();
        BaseApp.version="1";
        if (BaseApp.version.equals("")) {
            checkVersionAndUpdate();
        } else {
            if (!BaseApp.version.equals(versionLocal)) {
                Toast.makeText(this, "Please update application and try again.", Toast.LENGTH_SHORT).show();
                downloadAndInstall();
            } else {
                login();
            }
        }
    }

    private void login(){

        Call<User> call = BaseApp.service().login(userName);

        call.enqueue(new CallBackCustom<User>(this) {
            @Override
            public void onResponseCustom(Call<User> call, Response<User> response) {

                 if(response.isSuccessful()){
                     if(response.body()!=null){
                         Intent intent=new Intent(LoginLocalActivity.this, ActivityDocuments_.class);
                         BaseApp.userID=response.body().getID();
                         startActivity(intent);
                     }else{
                         Toast.makeText(LoginLocalActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                     }
                 }else {
                     Toast.makeText(LoginLocalActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                 }

            }

            @Override
            public void onFailureCustom(Call<User> call, Throwable t) {
                Toast.makeText(LoginLocalActivity.this, "Check on your Internet connection and try again.(Login error)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAccount(){
        userName = editTextUser.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        if (checkBoxUserName.isChecked()) {
            byte[] bytesUsername = userName.getBytes();
            String base64Username = Base64.encodeToString(bytesUsername, Base64.DEFAULT);
            sharedPreferencesEditer.putString("username", base64Username);
            sharedPreferencesEditer.putBoolean("checkusername", true);
        } else {
            sharedPreferencesEditer.putString("username", "");
            sharedPreferencesEditer.putBoolean("checkusername", false);
        }

        if (checkBoxPassword.isChecked()) {
            byte[] bytesPassword = password.getBytes();
            String base64Password = Base64.encodeToString(bytesPassword, Base64.DEFAULT);
            sharedPreferencesEditer.putString("password", base64Password);
            sharedPreferencesEditer.putBoolean("checkpassword", true);
        } else {
            sharedPreferencesEditer.putString("password", "");
            sharedPreferencesEditer.putBoolean("checkpassword", false);
        }
        sharedPreferencesEditer.commit();
    }

    @SuppressLint("CommitPrefEdits")
    private void loadAccount(){
        sharedPreferences = getSharedPreferences("remember_account", MODE_PRIVATE);
        sharedPreferencesEditer = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("checkusername", false)) {
            checkBoxUserName.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkpassword", false)) {
            checkBoxPassword.setChecked(true);
        }
        String base64Username = sharedPreferences.getString("username", "");
        if (!base64Username.equals("")) {
            byte[] bytesUsername = Base64.decode(base64Username, Base64.DEFAULT);
            String usernameDecode = new String(bytesUsername);
            editTextUser.setText(usernameDecode);

        }
        String base64Password = sharedPreferences.getString("password", "");
        if(!base64Password.equals("")){
            byte[] bytesPassword = Base64.decode(base64Password, Base64.DEFAULT);
            String passwordDecode = new String(bytesPassword);
            editTextPassword.setText(passwordDecode);
        }
    }

    //endregion

    //region yeu cau quyen truy cap
    private int REQUEST_PERMISSION = 123456;

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                makerequest();
            }
        }
    }

    private void makerequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.INTERNET
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        }
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
                .baseUrl(Constant.DOWNLOAD_FILE_URL_VALUE)
                .build();
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.download();

        // Set up progress before call
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
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
                            Uri apkUri = FileProvider.getUriForFile(LoginLocalActivity.this, getPackageName() + ".fileprovider", toInstall);
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
                    Toast.makeText(LoginLocalActivity.this, "Update error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginLocalActivity.this, "OnFailure, Update error", Toast.LENGTH_SHORT).show();

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
                .baseUrl(Constant.SERVICE_URL_VALUE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<MTRelease> call = service.checkVersionAndUpdate(Constant.APP_NAME_VALUE);

        call.enqueue(new CallBackCustom<MTRelease>(this) {
            @Override
            public void onResponseCustom(Call<MTRelease> call, Response<MTRelease> response) {
                if(response.isSuccessful()){
                    MTRelease mtRelease=response.body();
                    if(mtRelease!=null ){
                        if(mtRelease.getCURRENTVERSION()!=null){
                            Integer curVersion= Math.round(mtRelease.getCURRENTVERSION());
                            if(!curVersion.toString().equals(versionLocal)){
                                downloadAndInstall();
                            }
                        }

                    }else {
                        Toast.makeText(LoginLocalActivity.this, "Check internet - Version_esigning error", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginLocalActivity.this, "Check internet - Version_esigning error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailureCustom(Call<MTRelease> call, Throwable t) {
                Toast.makeText(LoginLocalActivity.this, "Check internet - Version_esigning error", Toast.LENGTH_SHORT).show();
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
            Intent main = new Intent(LoginLocalActivity.this, ActivityLogin_.class);
            startActivity(main);
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            finish();
        }
        return true;
    }

}
