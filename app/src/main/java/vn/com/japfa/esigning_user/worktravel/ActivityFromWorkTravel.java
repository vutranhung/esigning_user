package vn.com.japfa.esigning_user.worktravel;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.base.activity.BaseActivityFrom;
import vn.com.japfa.esigning_user.models.ShowDocument;

@EActivity
public class ActivityFromWorkTravel extends BaseActivityFrom {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setLayout(R.layout.work_travel_activity);
        BaseApp.type = "WORK_TRAVEL";
        setTitle(getResources().getString(R.string.giayyeucaucongtac));
    }

    //region add times
//    @ViewById
//    EditText editTextDateFrom;
//    @ViewById
//    EditText editTextDateTo;
    @ViewById
    EditText editTextDateFromWorkTravel;
    @ViewById
    EditText editTextDateToWorkTravel;
    @ViewById
    EditText editTextFromTimeWorkTravel;
    @ViewById
    EditText editTextToTimeWorkTravel;

    @Click
    void editTextDateFromWorkTravel() {
        BaseApp.setDate(this, editTextDateFromWorkTravel);
    }
    @Click
    void  editTextDateToWorkTravel(){BaseApp.setDate(this,editTextDateToWorkTravel);}
    @Click
    void editTextFromTimeWorkTravel(){BaseApp.setTime(this,editTextFromTimeWorkTravel);}
    @Click
    void  editTextToTimeWorkTravel(){BaseApp.setTime(this,editTextToTimeWorkTravel);}


//    @Click
//    void editTextDateTo() {
//        BaseApp.setDateTime(this, editTextDateTo);
//    }
    //endregion

    //region add,get data spinner
    @ViewById
    Spinner spinnerType;
    @ViewById
    Spinner spinnerAccommodation;
    private String textSpinnerType;
    private ArrayAdapter adapterType;
    private ArrayAdapter adapterAccommodation;
    private  String txtSpinnerAcomodation;

    @AfterViews
    protected void addDataSpinner() {
        List<String> listType = new ArrayList<>();
        listType.add(getResources().getString(R.string.xemayrieng));
        listType.add(getResources().getString(R.string.xekhach));
        listType.add(getResources().getString(R.string.maybay));
        listType.add(getResources().getString(R.string.otocongty));
        listType.add(getResources().getString(R.string.tauhoa));
        adapterType = new ArrayAdapter(this, R.layout.spinner_item, listType);
        spinnerType.setAdapter(adapterType);

        List<String> lstAccommodation=new ArrayList<>();
        lstAccommodation.add(getResources().getString(R.string.khachsan));
        lstAccommodation.add(getResources().getString(R.string.nhakhach));
        lstAccommodation.add(getResources().getString(R.string.chookhac));
        adapterAccommodation=new ArrayAdapter(this,R.layout.spinner_item,lstAccommodation);
        spinnerAccommodation.setAdapter(adapterAccommodation);
    }

    @ItemSelect
    protected void spinnerAccommodation(boolean selected){
        if(selected)
            txtSpinnerAcomodation=spinnerAccommodation.getSelectedItem().toString();
    }

    @ItemSelect
    protected void spinnerType(boolean selected) {
        if (selected) {
            textSpinnerType = spinnerType.getSelectedItem().toString();
        }
    }
    //endregion

    @AfterViews
    protected void createView() {
        if (BaseApp.documentID != null) {
            showDocument();
        } else {

//            editTextDateFrom.setText(BaseApp.getDateTimeCurrent());
//            editTextDateTo.setText(BaseApp.getDateTimeCurrent());
            editTextDateFromWorkTravel.setText(BaseApp.getDateCurrent());
            editTextDateToWorkTravel.setText(BaseApp.getDateCurrent());
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
                        editTextNoiDen.setText(hashMapData.get("Destination"));
                        editTextMucDich.setText(hashMapData.get("Purpose"));
                        editTextNguoiDiKem.setText(hashMapData.get("AccompaniedPerson"));
                        editTextDateFromWorkTravel.setText(hashMapData.get("FromDate"));
                        editTextDateToWorkTravel.setText(hashMapData.get("ToDate"));
                        editTextFromTimeWorkTravel.setText(hashMapData.get("FromTime"));
                        editTextToTimeWorkTravel.setText(hashMapData.get("ToTime"));

//                        editTextDateFrom.setText(hashMapData.get("FromDate"));
//                        editTextDateTo.setText(hashMapData.get("ToDate"));
                        editTextMieuTa.setText(hashMapData.get("documentDesc"));

                        int i = adapterType.getPosition(hashMapData.get("ModeOfTransport"));
                        spinnerType.setSelection(i);

                        editTextKhac.setText(hashMapData.get("Other"));
                        i=adapterAccommodation.getPosition(hashMapData.get("ModeOfAccommodation"));
                        spinnerAccommodation.setSelection(i);

                    }else {
                        Toast.makeText(ActivityFromWorkTravel.this, "View document error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ActivityFromWorkTravel.this, "View document error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailureCustom(Call<String> call, Throwable t) {
                Toast.makeText(ActivityFromWorkTravel.this, "showDocument onFailureCustom", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @ViewById
    EditText editTextNoiDen;
    @ViewById
    EditText editTextMucDich;
    @ViewById
    EditText editTextNguoiDiKem;
    @ViewById
    EditText editTextMieuTa;
    @ViewById
    EditText editTextKhac;

    @Override
    public void save() {
        HashMap<String, String> data = new HashMap<>();
        data.put("Destination", editTextNoiDen.getText().toString());
        data.put("Purpose", editTextMucDich.getText().toString());
        data.put("ModeOfTransport", textSpinnerType);
        data.put("AccompaniedPerson", editTextNguoiDiKem.getText().toString());
        data.put("FromDate",editTextDateFromWorkTravel.getText().toString());
        data.put("ToDate",editTextDateToWorkTravel.getText().toString()) ;
        data.put("FromTime",editTextFromTimeWorkTravel.getText().toString());
        data.put("ToTime",editTextToTimeWorkTravel.getText().toString());
        data.put("documentDesc", editTextMieuTa.getText().toString());
        data.put("priority", "Normal");
        data.put("documentID", BaseApp.documentID);
        data.put("ModeOfAccommodation",txtSpinnerAcomodation);
        data.put("Other",editTextKhac.getText().toString());
        saveData(data);
    }


}
