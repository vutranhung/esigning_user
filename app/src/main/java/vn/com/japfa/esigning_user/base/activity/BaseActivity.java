package vn.com.japfa.esigning_user.base.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import vn.com.japfa.esigning_user.ActivityLogin_;
import vn.com.japfa.esigning_user.base.BaseApp;

@EActivity
public class BaseActivity extends AppCompatActivity {
    @AfterViews
    protected void checkStaticVariables() {
        if (BaseApp.userID == -1) {
            startActivity(new Intent(getApplicationContext(), ActivityLogin_.class));
            System.exit(0);
        }
    }

}
