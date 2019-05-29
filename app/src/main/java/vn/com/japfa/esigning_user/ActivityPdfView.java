package vn.com.japfa.esigning_user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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

        Call<Preview> call = BaseApp.service().preview(BaseApp.userID, BaseApp.documentID);

        call.enqueue(new CallBackCustom<Preview>(this) {
            @Override
            public void onResponseCustom(Call<Preview> call, Response<Preview> response) {
                Preview preview = response.body();
                if (preview != null) {
                    String statusCode = preview.getResponseMeta().getStatusCode();
                    String message = preview.getResponseMeta().getMessage();
                    if (statusCode.equals("200")) {
                        byte[] bytesdata = preview.getPdfData();
                        PDFViewPreview.fromBytes(bytesdata)
                                .load();
                    } else {
                        Toast.makeText(ActivityPdfView.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<Preview> call, Throwable t) {
                Toast.makeText(ActivityPdfView.this, "Check on your Internet connection and try again.(Preview error)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
