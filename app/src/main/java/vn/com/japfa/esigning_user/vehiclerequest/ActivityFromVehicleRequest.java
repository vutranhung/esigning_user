package vn.com.japfa.esigning_user.vehiclerequest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.activity.BaseActivityFrom;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.models.ShowDocument;

@EActivity
public class ActivityFromVehicleRequest extends BaseActivityFrom {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setLayout(R.layout.vehicle_request_activity);
        BaseApp.type = "VEHICLE_REQUEST";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.giayyeucauxe));

        addDataSpinner();
        if (BaseApp.documentID != null) {
            showDocument();
        } else {
            editTextDateFromVehicle.setText(BaseApp.getDateCurrent());
            editTextDateToVehicle.setText(BaseApp.getDateCurrent());
        }
    }

    @Override
    public void showDocument() {
        Call<String> call = BaseApp.service().editDocument(BaseApp.userID, BaseApp.documentID);

        call.enqueue(new CallBackCustom<String>(this) {
            @Override
            public void onResponseCustom(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String strData= response.body();
                    if(strData!=null && !strData.isEmpty()){
                        Gson gson = new Gson();
                        Type type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        HashMap<String, String> hashMapData = gson.fromJson(strData, type);
                        editTextNoiBatDauVehicle.setText(hashMapData.get("Destination"));
                        editTextNoiDenVehicle.setText(hashMapData.get("Arrival"));
                        editTextMucDichVehicle.setText(hashMapData.get("Purpose"));
                        editTextDateFromVehicle.setText(hashMapData.get("FromDate"));
                        editTextDateToVehicle.setText(hashMapData.get("ToDate"));
                        editTextFromTimeVehicle.setText(hashMapData.get("FromTime"));
                        editTextToTimeVehicle.setText(hashMapData.get("ToTime"));
                        editTextMieuTaVehicle.setText(hashMapData.get("documentDesc"));

                        int i = adapterXeYeuCauVehicle.getPosition(hashMapData.get("Purpose"));
                        spinnerXeYeuCauVehicle.setSelection(i);
                    }else {
                        Toast.makeText(ActivityFromVehicleRequest.this, "View document error", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(ActivityFromVehicleRequest.this, "View document error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailureCustom(Call<String> call, Throwable t) {
                Toast.makeText(ActivityFromVehicleRequest.this, "showDocument error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //region add times
    @ViewById
    EditText editTextDateFromVehicle;
    @ViewById
    EditText editTextDateToVehicle;
    @ViewById
    EditText editTextFromTimeVehicle;
    @ViewById
    EditText editTextToTimeVehicle;

    @Click
    void editTextDateFromVehicle() {
        BaseApp.setDate(this, editTextDateFromVehicle);
    }

    @Click
    void editTextDateToVehicle() {
        BaseApp.setDate(this, editTextDateToVehicle);
    }

    @Click
    protected void editTextFromTimeVehicle() {
        BaseApp.setTime(this, editTextFromTimeVehicle);
    }

    @Click
    protected void editTextToTimeVehicle() {
        BaseApp.setTime(this, editTextToTimeVehicle);
    }
    //endregions

    //region add,get data spinner
    @ViewById()
    Spinner spinnerXeYeuCauVehicle;
    String textspinnerXeYeuCauVehicle;
    ArrayAdapter adapterXeYeuCauVehicle;

    void addDataSpinner() {
        List<String> listMucDich = new ArrayList<>();
        listMucDich.add(getResources().getString(R.string.cho7));
        listMucDich.add(getResources().getString(R.string.cho16));
        adapterXeYeuCauVehicle = new ArrayAdapter(this, R.layout.spinner_item, listMucDich);
        spinnerXeYeuCauVehicle.setAdapter(adapterXeYeuCauVehicle);

    }

    @ItemSelect
    void spinnerXeYeuCauVehicle(boolean selected) {
        if (selected) {
            textspinnerXeYeuCauVehicle = spinnerXeYeuCauVehicle.getSelectedItem().toString();
        }
    }
    //endregion


    @ViewById
    EditText editTextNoiBatDauVehicle;
    @ViewById
    EditText editTextNoiDenVehicle;
    @ViewById
    EditText editTextMieuTaVehicle;
    @ViewById
    EditText editTextMucDichVehicle;

    @Override
    public void save() {
        HashMap<String, String> data = new HashMap<>();
        data.put("Destination", editTextNoiBatDauVehicle.getText().toString());
        data.put("ModeOfTransport", textspinnerXeYeuCauVehicle);
        data.put("Arrival", editTextNoiDenVehicle.getText().toString());
        data.put("FromDate", editTextDateFromVehicle.getText().toString());
        data.put("ToDate", editTextDateToVehicle.getText().toString());
        data.put("FromTime", editTextFromTimeVehicle.getText().toString());
        data.put("ToTime", editTextToTimeVehicle.getText().toString());
        data.put("documentDesc", editTextMieuTaVehicle.getText().toString());
        data.put("Purpose", editTextMucDichVehicle.getText().toString());
        data.put("priority", "Normal");
        data.put("documentID", BaseApp.documentID);

        saveData(data);
    }


}
