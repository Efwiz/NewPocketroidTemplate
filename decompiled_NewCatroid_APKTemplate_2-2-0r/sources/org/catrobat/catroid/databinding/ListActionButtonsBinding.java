package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ListActionButtonsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ListActionButtonsBinding implements ViewBinding {
    public final RelativeLayout bottomBar;
    public final FloatingActionButton buttonAdd;
    public final FloatingActionButton buttonPlay;
    private final RelativeLayout rootView;

    private ListActionButtonsBinding(RelativeLayout rootView, RelativeLayout bottomBar, FloatingActionButton buttonAdd, FloatingActionButton buttonPlay) {
        this.rootView = rootView;
        this.bottomBar = bottomBar;
        this.buttonAdd = buttonAdd;
        this.buttonPlay = buttonPlay;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ListActionButtonsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ListActionButtonsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.list_action_buttons, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ListActionButtonsBinding bind(View rootView) {
        RelativeLayout relativeLayout = (RelativeLayout) rootView;
        int i = R.id.button_add;
        FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.button_add);
        if (floatingActionButton != null) {
            i = R.id.button_play;
            FloatingActionButton floatingActionButton2 = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.button_play);
            if (floatingActionButton2 != null) {
                return new ListActionButtonsBinding(relativeLayout, relativeLayout, floatingActionButton, floatingActionButton2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
