package vn.com.japfa.esigning_user.signer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.base.activity.BaseActivity;
import vn.com.japfa.esigning_user.models.signer.Data;
import vn.com.japfa.esigning_user.models.signer.DocumentsDatum;

@EActivity
public class ActivitySignerDocuments extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signer);
        setTitle("Documents-Signer");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createRecycler();
        refreshRecyclerApproval();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OptionsItem(android.R.id.home)
    protected void home() {
        onBackPressed();
    }

    @ViewById
    RecyclerView recyclerView;

    AdapterSignerDocuments adapter;

    private void createRecycler() {
        recyclerView = BaseApp.createRecycler(getApplicationContext(), recyclerView);
    }

    int isviewed = 0;//hien thi tren refresh tren menu

    private void getData() {

        Call<List<DocumentsDatum>> call = BaseApp.service().getData(BaseApp.userID);

        call.enqueue(new CallBackCustom<List<DocumentsDatum>>(this) {
            @Override
            public void onResponseCustom(Call<List<DocumentsDatum>> call, Response<List<DocumentsDatum>> response) {

                if(response.isSuccessful()){
                    List<DocumentsDatum> lstData=response.body();
                    if(lstData!=null){
                        adapter = new AdapterSignerDocuments(lstData, ActivitySignerDocuments.this);
                        recyclerView.setAdapter(adapter);
                        isviewed = 0;
                        for (DocumentsDatum document : lstData) {
                            if (document.getStatusApproval().equals("InSigning")
                                    || document.getStatusApproval().equals("Pending")) {
                                isviewed++;
                            }
                        }
                        supportInvalidateOptionsMenu();
                    }else {
                        Toast.makeText(ActivitySignerDocuments.this, "Get data error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ActivitySignerDocuments.this, "Get data error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailureCustom(Call<List<DocumentsDatum>> call, Throwable t) {
                Toast.makeText(ActivitySignerDocuments.this, "Check on your Internet connection and try again.(getData error)", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OptionsItem
    protected void  itemRefresh() {
        getData();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem itemRefresh = menu.findItem(R.id.itemRefresh);
        FrameLayout frameLayoutRefresh = (FrameLayout) itemRefresh.getActionView();
        FrameLayout redCircle = (FrameLayout) frameLayoutRefresh.findViewById(R.id.red_circle);
        TextView countTextView = (TextView) frameLayoutRefresh.findViewById(R.id.textviewIsView);

        countTextView.setText(isviewed + "");
        redCircle.setVisibility((isviewed > 0) ? View.VISIBLE : View.GONE);

        frameLayoutRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(itemRefresh);
            }
        });
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //tao menu
        getMenuInflater().inflate(R.menu.menu_documents, menu);

        //tao searchview
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null) {
                    adapter.filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    // region swipeRefreshlayoutMain
    @ViewById
    SwipeRefreshLayout swipeRefreshMain;

    private void refreshRecyclerApproval() {
        swipeRefreshMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshMain.setRefreshing(false);
            }
        });
    }
    //endregion

}
