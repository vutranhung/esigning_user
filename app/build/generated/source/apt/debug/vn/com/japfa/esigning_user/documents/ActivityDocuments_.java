//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.6.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package vn.com.japfa.esigning_user.documents;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;
import vn.com.japfa.esigning_user.R;

public final class ActivityDocuments_
    extends ActivityDocuments
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

    public static ActivityDocuments_.IntentBuilder_ intent(Context context) {
        return new ActivityDocuments_.IntentBuilder_(context);
    }

    public static ActivityDocuments_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ActivityDocuments_.IntentBuilder_(fragment);
    }

    public static ActivityDocuments_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ActivityDocuments_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.toolbar = hasViews.internalFindViewById(R.id.toolbar);
        this.recyclerView = hasViews.internalFindViewById(R.id.recyclerView);
        this.drawerLayout = hasViews.internalFindViewById(R.id.drawerLayout);
        this.swipeRefreshMain = hasViews.internalFindViewById(R.id.swipeRefreshMain);
        this.expandableListView = hasViews.internalFindViewById(R.id.expandableListView);
        View view_buttonShare = hasViews.internalFindViewById(R.id.buttonShare);
        View view_buttonSetting = hasViews.internalFindViewById(R.id.buttonSetting);

        if (view_buttonShare!= null) {
            view_buttonShare.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ActivityDocuments_.this.buttonShare();
                }
            }
            );
        }
        if (view_buttonSetting!= null) {
            view_buttonSetting.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    ActivityDocuments_.this.buttonSetting();
                }
            }
            );
        }
        recyclerView();
        expandableListView();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<ActivityDocuments_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, ActivityDocuments_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), ActivityDocuments_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), ActivityDocuments_.class);
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
