package vn.com.japfa.esigning_user.bringout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
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
import vn.com.japfa.esigning_user.models.Goods;
import vn.com.japfa.esigning_user.models.ShowDocument;

@EActivity
public class ActivityFromBringOut extends BaseActivityFrom {
    @ViewById
    EditText editTextMieuTa;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setLayout(R.layout.bring_out_activity);
        BaseApp.type = "BRING_OUT";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getResources().getString(R.string.giaymanghanghoarangoai));

        if (BaseApp.documentID != null) {
            showDocument();
        }

    }

    @Override
    public void save() {
        Gson gson = new Gson();
        HashMap<String, String> data = new HashMap<>();
        if (listGoods.size() > 0) {
            data.put("documentDesc", editTextMieuTa.getText().toString());
            data.put("priority", "Normal");
            data.put("documentID", BaseApp.documentID);
            data.put("ListProduct", gson.toJson(listGoods));

            saveData(data);
        } else
            Toast.makeText(this, "Pls input Goods", Toast.LENGTH_SHORT).show();
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
                        editTextMieuTa.setText(hashMapData.get("documentDesc"));
                        type = new TypeToken<List<Goods>>() {
                        }.getType();
                        listGoods = gson.fromJson(hashMapData.get("ListProduct"), type);
                        AdapterBringOut adapterBringOut = new AdapterBringOut(ActivityFromBringOut.this, listGoods);
                        recyclerBringOut.setAdapter(adapterBringOut);
                    }else {
                        Toast.makeText(ActivityFromBringOut.this, "View document error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ActivityFromBringOut.this, "View document error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailureCustom(Call<String> call, Throwable t) {
                Toast.makeText(ActivityFromBringOut.this, "showDocument error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //region dialog add Goods
    private EditText editTextTenhangHoa, editTextSoLuong, editTextMoTa, editTextLyDo;
    private List<Goods> listGoods = new ArrayList<>();
    private AdapterBringOut adapterBringOut;

    @ViewById
    RecyclerView recyclerBringOut;

    @AfterViews
    protected void recyclerBringOut() {
        recyclerBringOut = BaseApp.createRecycler(getApplicationContext(), recyclerBringOut);
    }

    @Click
    void buttonAdd() {
        keyboard(true);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_bring_out);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        editTextMoTa = (EditText) dialog.findViewById(R.id.editTextMoTa);
        editTextTenhangHoa = (EditText) dialog.findViewById(R.id.editTextTenhangHoa);
        editTextSoLuong = (EditText) dialog.findViewById(R.id.editTextSoLuong);
        editTextLyDo = (EditText) dialog.findViewById(R.id.editTextLyDo);
        Button buttonSaveDialogBring = (Button) dialog.findViewById(R.id.buttonSaveDialogBring);


        buttonSaveDialogBring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = editTextSoLuong.getText().toString();
                if (!qty.equals("")) {
                    listGoods.add(new Goods(editTextTenhangHoa.getText().toString(), editTextMoTa.getText().toString(), Integer.parseInt(qty),
                            editTextLyDo.getText().toString()));
                    adapterBringOut = new AdapterBringOut(ActivityFromBringOut.this, listGoods);
                    recyclerBringOut.setAdapter(adapterBringOut);
                    keyboard(false);
                    dialog.dismiss();
                } else
                    Toast.makeText(ActivityFromBringOut.this, "pls input Q.ty", Toast.LENGTH_SHORT).show();

            }
        });
        dialog.show();
    }

    //endregion

    private boolean checkKeyboard() {
        final View activityRootView = findViewById(R.id.ScrollView);
        Rect r = new Rect();
        activityRootView.getWindowVisibleDisplayFrame(r);

        int screenHeight = activityRootView.getRootView().getHeight();
//        Log.e("++","screenHeight: "+ String.valueOf(screenHeight));
        int heightDiff = screenHeight - (r.bottom - r.top);
//        Log.e("++","heightDiff: "+ String.valueOf(heightDiff));
        boolean visible = heightDiff > screenHeight / 3;
//        Log.e("++","visible: "+ String.valueOf(visible));
        return visible;
//        if (visible) {
//            Log.d("++","ban phim dang mo");
//            return true;
//        } else {
//            Log.d("++","ban phim dang dong");
//            return false;
//        }

    }

    private void keyboard(boolean check) {
        if (!checkKeyboard() & check) {
            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else if (checkKeyboard() & !check) {
            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }


}
