package vn.com.japfa.esigning_user.base.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.ActivityLogin_;
import vn.com.japfa.esigning_user.ActivityPdfView_;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.adapter.AdapterListSigners;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.base.MaHoaAES;
import vn.com.japfa.esigning_user.documents.ActivityDocuments_;
import vn.com.japfa.esigning_user.models.DocumentDatum;
import vn.com.japfa.esigning_user.models.Documents;
import vn.com.japfa.esigning_user.models.SignFollow;
import vn.com.japfa.esigning_user.models.SignFollowDatum;

@EActivity
@OptionsMenu(R.menu.menu_form)
public abstract class BaseActivityFrom extends BaseActivity {
    protected NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setLayout(int layout) {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layout, null, false);
        nestedScrollView.addView(contentView, 0);
    }

    @InjectMenu
    protected void createOptionsMenuItem(Menu menu1) {
        MenuItem itemSave = menu1.findItem(R.id.itemSave);
        MenuItem itemSend = menu1.findItem(R.id.itemSend);
        MenuItem itemPreview = menu1.findItem(R.id.itemPreview);
        if (BaseApp.documentID != null) {
            if (BaseApp.status.equals("InSigning") || BaseApp.status.equals("Completed") || BaseApp.status.equals("Pending")) {
                itemSave.setEnabled(false);
                itemSave.getIcon().setAlpha(100);
                itemSend.setEnabled(false);
                itemSend.getIcon().setAlpha(100);
                itemPreview.setEnabled(true);
                itemPreview.getIcon().setAlpha(255);
            } else {
                itemSave.setEnabled(true);
                itemSave.getIcon().setAlpha(255);
                itemSend.setEnabled(true);
                itemSend.getIcon().setAlpha(255);
                itemPreview.setEnabled(true);
                itemPreview.getIcon().setAlpha(255);
            }
        } else {
            itemPreview.setEnabled(false);
            itemPreview.getIcon().setAlpha(100);
            itemSend.setEnabled(false);
            itemSend.getIcon().setAlpha(100);
            itemSave.setEnabled(true);
            itemSave.getIcon().setAlpha(255);
        }
    }

    @OptionsItem(android.R.id.home)
    protected void home() {
        onBackPressed();
    }

    @OptionsItem
    protected void itemSend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivityFrom.this);
        builder.setMessage("Are you sure want to send this document?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<Void> call = BaseApp.service().sendData(BaseApp.userID, BaseApp.documentID);
                call.enqueue(new CallBackCustom<Void>(BaseActivityFrom.this) {
                    @Override
                    public void onResponseCustom(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Send document successful", Toast.LENGTH_SHORT).show();
                            Intent intentMainActivity = new Intent(getApplicationContext(), ActivityDocuments_.class);
                            startActivity(intentMainActivity);
                        }else {
                            Toast.makeText(getApplicationContext(),"Send document error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailureCustom(Call<Void> call, Throwable t) {
                       Toast.makeText(getApplicationContext(), "sendDocument error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @OptionsItem
    protected void itemPreview() {
        Intent intentPreview = new Intent(getApplicationContext(), ActivityPdfView_.class);
        startActivity(intentPreview);
    }

    @OptionsItem
    protected void itemSave() {
        save();
    }

    public abstract void save();

    protected void saveData(HashMap<String, String> data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to save this document?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Gson gsonOcject = new Gson();
                String jsonData = gsonOcject.toJson(data);
                MaHoaAES maHoaAES = new MaHoaAES();
                String enCodeData = maHoaAES.encryptAndEncode(jsonData);
//            Log.e("++++", enCodeData);
                Call<Documents> call = BaseApp.service().saveData(BaseApp.userID, enCodeData, BaseApp.type);

                call.enqueue(new CallBackCustom<Documents>(BaseActivityFrom.this) {
                    @Override
                    public void onResponseCustom(Call<Documents> call, Response<Documents> response) {

                        if(response.isSuccessful()){
                            Documents documents = response.body();
                            if (documents != null){
                                if (BaseApp.documentID == null) {
                                    BaseApp.documentID = documents.getId() + "";
                                    BaseApp.status = documents.getStatus();
                                } else BaseApp.status = documents.getStatus();
                                checkListSignFollow();  //refresh list sign follow
                                supportInvalidateOptionsMenu();   //refresh menu
                                Toast.makeText(getApplicationContext(), "Create document successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Create document error", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "Create document error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailureCustom(Call<Documents> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "saveData error", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public abstract void showDocument();

    @ViewById
    protected RecyclerView recyclerListSigner;

    @AfterViews
    protected void checkListSignFollow() {
        Call<List<SignFollow>> call;
        if (BaseApp.documentID!=null && !BaseApp.documentID.isEmpty()) {
            call = BaseApp.service().getListSignFollowDetail(BaseApp.userID, BaseApp.documentID);
        } else {
            call = BaseApp.service().getListSignFollowByType(BaseApp.userID, BaseApp.type);
        }

        recyclerListSigner = BaseApp.createRecycler(getApplicationContext(), recyclerListSigner);

        call.enqueue(new CallBackCustom<List<SignFollow>>(this) {
            @Override
            public void onResponseCustom(Call<List<SignFollow>> call, Response<List<SignFollow>> response) {

                if(response.isSuccessful()){
                    if(response.body()!=null){
                        List<SignFollow> list = response.body();
                        AdapterListSigners adapterListSigners = new AdapterListSigners(BaseActivityFrom.this, list);
                        recyclerListSigner.setAdapter(adapterListSigners);

                    }else{
                        Toast.makeText(getApplicationContext(),"Get list signer error",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Get list signer error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailureCustom(Call<List<SignFollow>> call, Throwable t) {
               Toast.makeText(getApplicationContext(), "getListSignFollow error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
