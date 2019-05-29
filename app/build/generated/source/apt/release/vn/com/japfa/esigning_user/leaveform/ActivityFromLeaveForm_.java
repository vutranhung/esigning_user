//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.6.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package vn.com.japfa.esigning_user.leaveform;

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

public final class ActivityFromLeaveForm_
    extends ActivityFromLeaveForm
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

    public static ActivityFromLeaveForm_.IntentBuilder_ intent(Context context) {
        return new ActivityFromLeaveForm_.IntentBuilder_(context);
    }

    public static ActivityFromLeaveForm_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ActivityFromLeaveForm_.IntentBuilder_(fragment);
    }

    public static ActivityFromLeaveForm_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ActivityFromLeaveForm_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.recyclerListSigner = hasViews.internalFindViewById(R.id.recyclerListSigner);
        this.editTextMieuTa = hasViews.internalFindViewById(R.id.editTextMieuTa);
        this.textViewTon = hasViews.internalFindViewById(R.id.textViewTon);
        this.textViewNgayNghi = hasViews.internalFindViewById(R.id.textViewNgayNghi);
        this.textViewNamNay = hasViews.internalFindViewById(R.id.textViewNamNay);
        this.textViewDaNghi = hasViews.internalFindViewById(R.id.textViewDaNghi);
        this.textViewConLai = hasViews.internalFindViewById(R.id.textViewConLai);
        this.editTextFromDate1 = hasViews.internalFindViewById(R.id.editTextFromDate1);
        this.editTextToDate1 = hasViews.internalFindViewById(R.id.editTextToDate1);
        this.editTextFromDate2 = hasViews.internalFindViewById(R.id.editTextFromDate2);
        this.editTextToDate2 = hasViews.internalFindViewById(R.id.editTextToDate2);
        this.autoCompleteTextViewtQty1 = hasViews.internalFindViewById(R.id.autoCompleteTextViewtQty1);
        this.autoCompleteTextViewtQty2 = hasViews.internalFindViewById(R.id.autoCompleteTextViewtQty2);
        this.spinnerType = hasViews.internalFindViewById(R.id.spinnerType);
        if (this.editTextFromDate1 != null) {
            this.editTextFromDate1 .setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ActivityFromLeaveForm_.this.editTextFromDate1();
                }
            }
            );
        }
        if (this.editTextFromDate2 != null) {
            this.editTextFromDate2 .setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ActivityFromLeaveForm_.this.editTextFromDate2();
                }
            }
            );
        }
        if (this.spinnerType!= null) {
            ((AdapterView<?> ) this.spinnerType).setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    ActivityFromLeaveForm_.this.spinnerType(false);
                }

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ActivityFromLeaveForm_.this.spinnerType(true);
                }
            }
            );
        }
        checkStaticVariables();
        checkListSignFollow();
        createView();
        autoCompleteTextViewtQty();
        addDataSpinner();
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
        extends ActivityIntentBuilder<ActivityFromLeaveForm_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, ActivityFromLeaveForm_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), ActivityFromLeaveForm_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), ActivityFromLeaveForm_.class);
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
