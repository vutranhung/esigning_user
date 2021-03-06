package vn.com.japfa.esigning_user.base.setting;

import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.activity.BaseActivityPopup;
import vn.com.japfa.esigning_user.util.Constant;


@EActivity
public class ActivitySetting extends BaseActivityPopup {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setLayout(R.layout.setting_activity);
        setTitle("Info");
    }

    @ViewById
    TextView textViewVersion;

    @AfterViews
    protected void createView() {
        textViewVersion.setText(Constant.VERSION_VALUE);
    }


}
