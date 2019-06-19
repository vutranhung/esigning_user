package vn.com.japfa.esigning_user.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilHelper {
    public static boolean checkInternetConnection(Activity activity) {

        ConnectivityManager connManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(activity, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(activity, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(activity, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static Properties loadPropertyFile(Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);
        return properties;
    }

    public static void getValueProperty(Context context)
    {
        try{
            Properties properties = loadPropertyFile(context);
            Constant.SERVICE_URL_VALUE = properties.getProperty(Constant.SERVICE_URL_PROPERTY);
            Constant.APP_NAME_VALUE=properties.getProperty(Constant.APP_NAME_PROPERTY);
            Constant.DOWNLOAD_FILE_URL_VALUE=properties.getProperty(Constant.DOWNLOAD_FILE_URL_PROPERTY);
            Constant.VERSION_VALUE=properties.getProperty(Constant.VERSION_PROPERTY);
        }catch (Exception ex){
            Toast.makeText(context, "Không đọc được file cấu hình config.properties", Toast.LENGTH_LONG);
        }
    }


}
