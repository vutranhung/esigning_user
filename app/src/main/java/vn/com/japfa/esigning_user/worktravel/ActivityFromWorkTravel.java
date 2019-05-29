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
    @ViewById
    EditText editTextDateFrom;
    @ViewById
    EditText editTextDateTo;

    @Click
    void editTextDateFrom() {
        BaseApp.setDateTime(this, editTextDateFrom);
    }

    @Click
    void editTextDateTo() {
        BaseApp.setDateTime(this, editTextDateTo);
    }
    //endregion

    //region add,get data spinner
    @ViewById
    Spinner spinnerType;
    private String textSpinnerType;
    private ArrayAdapter adapterType;

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
            editTextDateFrom.setText(BaseApp.getDateTimeCurrent());
            editTextDateTo.setText(BaseApp.getDateTimeCurrent());
        }
    }

    @Override
    public void showDocument() {

        Call<ShowDocument> call = BaseApp.service().editDocument(BaseApp.userID, BaseApp.documentID);

        call.enqueue(new CallBackCustom<ShowDocument>(this) {
            @Override
            public void onResponseCustom(Call<ShowDocument> call, Response<ShowDocument> response) {
                ShowDocument showDocument = response.body();
                if (showDocument != null) {
                    String status = showDocument.getResponseMeta().getStatusCode();
                    String message = showDocument.getResponseMeta().getMessage();
                    if (status.equals("200")) {
                        String stringData = showDocument.getRawInformation();
                        Gson gson = new Gson();
                        Type type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        HashMap<String, String> hashMapData = gson.fromJson(stringData, type);
                        editTextNoiDen.setText(hashMapData.get("Destination"));
                        editTextMucDich.setText(hashMapData.get("Purpose"));
                        editTextNguoiDiKem.setText(hashMapData.get("AccompaniedPerson"));
                        editTextDateFrom.setText(hashMapData.get("FromDate"));
                        editTextDateTo.setText(hashMapData.get("ToDate"));
                        editTextMieuTa.setText(hashMapData.get("documentDesc"));

                        int i = adapterType.getPosition(hashMapData.get("ModeOfTransport"));
                        spinnerType.setSelection(i);
                    } else
                        Toast.makeText(ActivityFromWorkTravel.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailureCustom(Call<ShowDocument> call, Throwable t) {
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

    @Override
    public void save() {
        HashMap<String, String> data = new HashMap<>();
        data.put("Destination", editTextNoiDen.getText().toString());
        data.put("Purpose", editTextMucDich.getText().toString());
        data.put("ModeOfTransport", textSpinnerType);
        data.put("AccompaniedPerson", editTextNguoiDiKem.getText().toString());
        data.put("FromDate", editTextDateFrom.getText().toString());
        data.put("ToDate", editTextDateTo.getText().toString());
        data.put("documentDesc", editTextMieuTa.getText().toString());
        data.put("priority", "Normal");
        data.put("documentID", BaseApp.documentID);

        saveData(data);
    }


}
