//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.6.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package vn.com.japfa.esigning_user.worktravel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;
import vn.com.japfa.esigning_user.R;

public final class ActivityFromWorkTravel_
    extends ActivityFromWorkTravel
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public<T extends View> T internalFindViewById(int id) {
        return ((T) this.findViewById(id));
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static ActivityFromWorkTravel_.IntentBuilder_ intent(Context context) {
        return new ActivityFromWorkTravel_.IntentBuilder_(context);
    }

    public static ActivityFromWorkTravel_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ActivityFromWorkTravel_.IntentBuilder_(fragment);
    }

    public static ActivityFromWorkTravel_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ActivityFromWorkTravel_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.recyclerListSigner = hasViews.internalFindViewById(R.id.recyclerListSigner);
        this.editTextDateFrom = hasViews.internalFindViewById(R.id.editTextDateFrom);
        this.editTextDateTo = hasViews.internalFindViewById(R.id.editTextDateTo);
        this.spinnerType = hasViews.internalFindViewById(R.id.spinnerType);
        this.editTextNoiDen = hasViews.internalFindViewById(R.id.editTextNoiDen);
        this.editTextMucDich = hasViews.internalFindViewById(R.id.editTextMucDich);
        this.editTextNguoiDiKem = hasViews.internalFindViewById(R.id.editTextNguoiDiKem);
        this.editTextMieuTa = hasViews.internalFindViewById(R.id.editTextMieuTa);
        if (this.editTextDateFrom!= null) {
            this.editTextDateFrom.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ActivityFromWorkTravel_.this.editTextDateFrom();
                }
            }
            );
        }
        if (this.editTextDateTo!= null) {
            this.editTextDateTo.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ActivityFromWorkTravel_.this.editTextDateTo();
                }
            }
            );
        }
        if (this.spinnerType!= null) {
            ((AdapterView<?> ) this.spinnerType).setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    ActivityFromWorkTravel_.this.spinnerType(false);
                }

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ActivityFromWorkTravel_.this.spinnerType(true);
                }
            }
            );
        }
        checkStaticVariables();
        checkListSignFollow();
        addDataSpinner();
        createView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_form, menu);
        {
            Menu menu1 = null;
            menu1 = menu;
            createOptionsMenuItem(menu1);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId_ = item.getItemId();
        if (itemId_ == android.R.id.home) {
            home();
            return true;
        }
        if (itemId_ == R.id.itemSend) {
            itemSend();
            return true;
        }
        if (itemId_ == R.id.itemPreview) {
            itemPreview();
            return true;
        }
        if (itemId_ == R.id.itemSave) {
            itemSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<ActivityFromWorkTravel_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, ActivityFromWorkTravel_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), ActivityFromWorkTravel_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), ActivityFromWorkTravel_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public PostActivityStarter startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent, requestCode, lastOptions);
                } else {
                    if (context instanceof Activity) {
                        Activity activity = ((Activity) context);
                        ActivityCompat.startActivityForResult(activity, intent, requestCode, lastOptions);
                    } else {
                        context.startActivity(intent, lastOptions);
                    }
                }
            }
            return new PostActivityStarter(context);
        }
    }
}
