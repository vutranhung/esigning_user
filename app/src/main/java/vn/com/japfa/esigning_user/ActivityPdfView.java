package vn.com.japfa.esigning_user;

import android.os.Bundle;
import android.renderscript.Element;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.models.Preview;

@EActivity
public class ActivityPdfView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view_activity);
        setTitle("Preview");
        //enable backup actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        preview();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @ViewById
    PDFView PDFViewPreview;

    private void preview() {

        Call<String> call = BaseApp.service().preview(BaseApp.userID, BaseApp.documentID);

        call.enqueue(new CallBackCustom<String>(this) {
            @Override
            public void onResponseCustom(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    try {

                        String result =response.body();
                        if(result!=null && !result.isEmpty()){
                            byte[] data = Base64.decode(result, Base64.DEFAULT);

                            if (data != null) {
                                PDFViewPreview.fromBytes(data)
                                        .load();
                              }
                        } else {
                            Toast.makeText(ActivityPdfView.this, "Error preview document", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {

                    }

                } else {
                    Toast.makeText(ActivityPdfView.this, "Error preview document", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailureCustom(Call<String> call, Throwable t) {
                Toast.makeText(ActivityPdfView.this, "Check on your Internet connection and try again.(Preview error)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
