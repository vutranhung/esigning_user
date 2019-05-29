package vn.com.japfa.esigning_user.exitform;

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
public class ActivityFromExit extends BaseActivityFrom {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setLayout(R.layout.exit_activity);
        BaseApp.type = "EXIT";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.giayracong));

        addDataSpinner();
        if (BaseApp.documentID != null) {
            showDocument();
        } else {
            editTextDate.setText(BaseApp.getDateCurrent());
        }
    }


    //region add times
    @ViewById
    EditText editTextDate;
    @ViewById
    EditText editTextFromTimeEdit;
    @ViewById
    EditText editTextToTimeEdit;

    @Click
    protected void editTextDate() {
        BaseApp.setDate(this, editTextDate);
    }

    @Click
    protected void editTextFromTimeEdit() {
        BaseApp.setTime(this, editTextFromTimeEdit);
    }

    @Click
    protected void editTextToTimeEdit() {
        BaseApp.setTime(this, editTextToTimeEdit);
    }

    //endregion

    //region add data spinner
    @ViewById()
    Spinner spinnerMucDichExit;
    String textspinnerMucDichExit;
    private ArrayAdapter adapterMucDichExit;

    private void addDataSpinner() {
        List<String> listMucDichExit = new ArrayList<>();
        listMucDichExit.add(getResources().getString(R.string.congviec));
        listMucDichExit.add(getResources().getString(R.string.canhan));
        adapterMucDichExit = new ArrayAdapter(this, R.layout.spinner_item, listMucDichExit);
        spinnerMucDichExit.setAdapter(adapterMucDichExit);
    }

    @ItemSelect
    protected void spinnerMucDichExit(boolean selected) {
        if (selected) {
            textspinnerMucDichExit = spinnerMucDichExit.getSelectedItem().toString();
        }
    }
    //endregion

    @ViewById
    EditText editTextNoiDenExit;
    @ViewById
    EditText editTextLyDoEdit;
    @ViewById
    EditText editTextMieuTaEdit;

    @Override
    public void save() {
        HashMap<String, String> data = new HashMap<>();
        data.put("Destination", editTextNoiDenExit.getText().toString());
        data.put("Reason", editTextLyDoEdit.getText().toString());
        data.put("FromDate", editTextDate.getText().toString());
        data.put("ToDate", editTextDate.getText().toString());
        data.put("FromTime", editTextFromTimeEdit.getText().toString());
        data.put("ToTime", editTextToTimeEdit.getText().toString());
        data.put("documentDesc", editTextMieuTaEdit.getText().toString());
        data.put("Purpose", textspinnerMucDichExit);
        data.put("priority", "Normal");
        data.put("documentID", BaseApp.documentID);

        saveData(data);
    }

    @Override
    public void showDocument() {
        Call<ShowDocument> call = BaseApp.service().editDocument(BaseApp.userID, BaseApp.documentID);

        call.enqueue(new CallBackCustom<ShowDocument>(this) {
            @Override
            public void onResponseCustom(Call<ShowDocument> call, Response<ShowDocument> response) {
                ShowDocument showDocument = response.body();
                String status = showDocument.getResponseMeta().getStatusCode();
                String message = showDocument.getResponseMeta().getMessage();
                if (status.equals("200")) {
                    String stringData = showDocument.getRawInformation();
                    Gson gson = new Gson();
                    Type type = new TypeToken<HashMap<String, String>>() {
                    }.getType();
                    HashMap<String, String> hashMapData = gson.fromJson(stringData, type);
                    editTextNoiDenExit.setText(hashMapData.get("Destination"));
                    editTextLyDoEdit.setText(hashMapData.get("Reason"));
                    editTextDate.setText(hashMapData.get("FromDate"));
                    editTextFromTimeEdit.setText(hashMapData.get("FromTime"));
                    editTextToTimeEdit.setText(hashMapData.get("ToTime"));
                    editTextMieuTaEdit.setText(hashMapData.get("documentDesc"));

                    int i = adapterMucDichExit.getPosition(hashMapData.get("Purpose"));
                    spinnerMucDichExit.setSelection(i);

                } else Toast.makeText(ActivityFromExit.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailureCustom(Call<ShowDocument> call, Throwable t) {
                Toast.makeText(ActivityFromExit.this, "showDocument error", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
