package vn.com.japfa.esigning_user.base;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import okhttp3.OkHttpClient;
import vn.com.japfa.esigning_user.Interface.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApp extends Application {

    //region leak memory
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
        // Normal app init code...
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApp application = (BaseApp) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;
    //endregion

    public static int userID=-1;
    public static String documentID;
    public static String status;
    public static String type;
    public static String version;

    public static String convertDate(String dateString, String check) {
        Date date;
        String result = "null";
        if (check.equals("send")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy h:mm a",Locale.ENGLISH);  //co the thay Z ban X trong android 7 tro len
            try {
                date = simpleDateFormat.parse(dateString);
                result = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH).format(date.getTime());
            } catch (ParseException e) {
                return result;
            }
            return result;
        } else if (check.equals("view")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);  //co the thay Z ban X trong android 7 tro len
            try {
                date = simpleDateFormat.parse(dateString);
                result = new SimpleDateFormat("dd-MMM-yyyy h:mm a",Locale.ENGLISH).format(date.getTime());
            } catch (ParseException e) {
                return result;
            }

        }
        return result;
    }

    public static String getDateCurrent() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public static String getDateTimeCurrent() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy h:mm a",Locale.ENGLISH);
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public static void setDate(Context context, EditText editText) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                editText.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public static void setDateTime(Context context, EditText editText) {
        final Calendar currentDate = Calendar.getInstance(Locale.ENGLISH);
        Calendar date = Calendar.getInstance(Locale.ENGLISH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy h:mm a",Locale.ENGLISH);
                        editText.setText(simpleDateFormat.format(date.getTime()));

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
//        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }

    public static void setTime(Context context, EditText editText) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a",Locale.ENGLISH);
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                editText.setText(simpleDateFormat.format(calendar.getTime()));

            }
        },gio,phut,false);
        timePickerDialog.show();
    }

    public static Service service() {
        String baseURL = "http://apps.japfa.com.vn:62040/";

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Service service = retrofit.create(Service.class);
        return service;
    }

    public static RecyclerView createRecycler(Context context, RecyclerView recyclerView) {
//        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        return recyclerView;
    }

}
