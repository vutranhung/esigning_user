package vn.com.japfa.esigning_user.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import vn.com.japfa.esigning_user.R;


@EActivity
@OptionsMenu(R.menu.base_popup)
public class BaseActivityPopup extends BaseActivity {
    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_base_activity);
    }

    @AfterViews
    protected void createToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void setLayout(int layout) {
        drawerLayout = findViewById(R.id.drawerLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layout, null, false);
        drawerLayout.addView(contentView, 0);
    }

    @OptionsItem
    protected void itemOnBackPressed() {
        onBackPressed();
    }


}
