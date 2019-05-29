package vn.com.japfa.esigning_user.documents;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.ActivityLogin_;
import vn.com.japfa.esigning_user.Interface.Refresh;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.base.setting.ActivitySetting_;
import vn.com.japfa.esigning_user.bringout.ActivityFromBringOut_;
import vn.com.japfa.esigning_user.exitform.ActivityFromExit_;
import vn.com.japfa.esigning_user.leaveform.ActivityFromLeaveForm_;
import vn.com.japfa.esigning_user.models.DocumentDatum;
import vn.com.japfa.esigning_user.models.Documents;
import vn.com.japfa.esigning_user.signer.ActivitySignerDocuments_;
import vn.com.japfa.esigning_user.vehiclerequest.ActivityFromVehicleRequest_;
import vn.com.japfa.esigning_user.worktravel.ActivityFromWorkTravel_;


@EActivity
public class ActivityDocuments extends AppCompatActivity implements Refresh {

    private AdapterDocuments adapter;
    private final int REQUEST_SETUP = 100;


    @ViewById
    Toolbar toolbar;

    @ViewById
    RecyclerView recyclerView;
    @ViewById
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents_activity);
        setTitle("Documents");
        setSupportActionBar(toolbar);
        createNavigation();
        swipeRefreshLayoutMain();
    }

    @Override
    protected void onResume() {
        getListDocuments();
        super.onResume();
    }

    boolean mBacktoExit = false;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (mBacktoExit) {
                exit();
            }
            mBacktoExit = true;
            Toast.makeText(this, "please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBacktoExit = false;
                }
            }, 2000);
        }
    }

    private void exit() {
        Intent main = new Intent(ActivityDocuments.this, ActivityLogin_.class);
        startActivity(main);
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
    }

    private void createNavigation() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                setTitle("Menu");
                expandableListView.expandGroup(0);
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                setTitle("Documents");
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationMain = (NavigationView) findViewById(R.id.navigationMain);
//        navigationMain.setNavigationItemSelectedListener(this);

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
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemRefresh:
                getListDocuments();
                break;
            default:
                break;
        }
        return true;
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.itemLeave:
//                Intent intentLeave = new Intent(ActivitySignerDocuments.this, ActivityFromLeaveForm_.class);
//                BaseApp.documentID = null;
//                startActivity(intentLeave);
//                break;
//            case R.id.itemWorkTravel:
//                Intent intentTravel = new Intent(ActivitySignerDocuments.this, ActivityFromWorkTravel_.class);
//                BaseApp.documentID = null;
//                startActivity(intentTravel);
//                break;
//            case R.id.itemGiayRaCong:
//                Intent intentExit = new Intent(ActivitySignerDocuments.this, ActivityFromExit_.class);
//                BaseApp.documentID = null;
//
//                startActivity(intentExit);
//                break;
//            case R.id.itemYeucCauXe:
//                Intent intentVehicle = new Intent(ActivitySignerDocuments.this, ActivityFromVehicleRequest_.class);
//                BaseApp.documentID = null;
//
//                startActivity(intentVehicle);
//                break;
//            case R.id.itemMangHangHoaRangoai:
//                Intent intentBringOut = new Intent(ActivitySignerDocuments.this, ActivityFromBringOut_.class);
//                BaseApp.documentID = null;
//
//                startActivity(intentBringOut);
//                break;
//            case R.id.itemSignOut:
//                clearCookies();
//                AuthenticationContext authenticationContext = new AuthenticationContext(getApplicationContext(), AUTHORITY, false);
//                authenticationContext.getCache().removeAll();
//                startActivity(new Intent(ActivitySignerDocuments.this, ActivityLogin_.class));
//                break;
//            case R.id.itemExit:
//                exit();
//                break;
//            default:
//                break;
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void clearCookies() {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    @AfterViews
    protected void recyclerView() {
        recyclerView = BaseApp.createRecycler(getApplicationContext(), recyclerView);
    }

    private void getListDocuments() {
        Call<Documents> call = BaseApp.service().getListDocuments(BaseApp.userID);
        call.enqueue(new CallBackCustom<Documents>(this) {
            @Override
            public void onResponseCustom(Call<Documents> call, Response<Documents> response) {
                Documents documents = response.body();
                if (documents != null) {
                    String statusCode = documents.getResponseMeta().getStatusCode();
                    String message = documents.getResponseMeta().getMessage();
                    if (statusCode.equals("200")) {
                        List<DocumentDatum> listDocuments = documents.getDocumentData();
                        adapter = new AdapterDocuments(listDocuments, ActivityDocuments.this);
                        recyclerView.setAdapter(adapter);
                        adapter.setRefresh(ActivityDocuments.this);
                    } else {
                        Toast.makeText(ActivityDocuments.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<Documents> call, Throwable t) {
                Toast.makeText(ActivityDocuments.this, "Check on your Internet connection and try again.(getListDocuments error)", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //region SwireRefreshLayout
    @ViewById
    SwipeRefreshLayout swipeRefreshMain;

    private void swipeRefreshLayoutMain() {
        swipeRefreshMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListDocuments();
                swipeRefreshMain.setRefreshing(false);
            }
        });
    }

    @Override
    public void refreshSuccess(List<DocumentDatum> listDocuments) {
        getListDocuments();
    }
    //endregion

    @Click
    protected void buttonShare() {
        shareTextUrl();
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://apps.japfa.com.vn:62040/Apps/esigninguser.apk");

        startActivity(Intent.createChooser(share, "Share application link!"));
    }

    @Click
    protected void buttonSetting() {
        startActivityForResult(new Intent(this, ActivitySetting_.class), REQUEST_SETUP);
    }

    //region expanlistview


    @ViewById
    protected ExpandableListView expandableListView;

    @AfterViews
    protected void expandableListView() {
        List<String> DOCUMENTS = new ArrayList<>();
        DOCUMENTS.add("Leave form");
        DOCUMENTS.add("Work travel form");
        DOCUMENTS.add("Exit form");
        DOCUMENTS.add("Vehicle request form");
        DOCUMENTS.add("Bring out form");


        final HashMap<String, List<String>> items = new HashMap<>();
        items.put("DOCUMENTS", DOCUMENTS);
        items.put("SIGNER", new ArrayList<>());
        items.put("EXIT", new ArrayList<>());

        final List<String> groups = new ArrayList<>();
        groups.add("DOCUMENTS");
        groups.add("SIGNER");
        groups.add("EXIT");

        AdapterExpandableListMenu adapter = new AdapterExpandableListMenu(this, groups, items);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groups.get(groupPosition).equals("EXIT")) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    exit();
                }
                if (groups.get(groupPosition).equals("SIGNER")) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(ActivityDocuments.this, ActivitySignerDocuments_.class));
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                BaseApp.documentID = null;
                switch (items.get(groups.get(i)).get(i1)) {

                    //region Transaction
                    case "Leave form":
                        Intent intent1 = new Intent(ActivityDocuments.this, ActivityFromLeaveForm_.class);
                        startActivity(intent1);
                        break;
                    case "Work travel form":
                        Intent intent2 = new Intent(ActivityDocuments.this, ActivityFromWorkTravel_.class);
                        startActivity(intent2);
                        break;
                    case "Exit form":
                        Intent intent302 = new Intent(ActivityDocuments.this, ActivityFromExit_.class);
                        startActivity(intent302);
                        break;
                    case "Vehicle request form":
                        Intent intent3 = new Intent(ActivityDocuments.this, ActivityFromVehicleRequest_.class);
                        startActivity(intent3);
                        break;
                    case "Bring out form":
                        Intent intent4 = new Intent(ActivityDocuments.this, ActivityFromBringOut_.class);
                        startActivity(intent4);
                        break;
                    //endregion
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }
    //endregion
}
