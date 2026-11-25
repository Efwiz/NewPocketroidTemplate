package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogSelectCastBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogSelectCastBinding implements ViewBinding {
    public final ListView castDeviceListView;
    public final TextView castSearchingForCastTextView;
    public final LinearLayout emptyViewItem;
    private final LinearLayout rootView;

    private DialogSelectCastBinding(LinearLayout rootView, ListView castDeviceListView, TextView castSearchingForCastTextView, LinearLayout emptyViewItem) {
        this.rootView = rootView;
        this.castDeviceListView = castDeviceListView;
        this.castSearchingForCastTextView = castSearchingForCastTextView;
        this.emptyViewItem = emptyViewItem;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogSelectCastBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogSelectCastBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_select_cast, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogSelectCastBinding bind(View rootView) {
        int i = R.id.cast_device_list_view;
        ListView listView = (ListView) ViewBindings.findChildViewById(rootView, R.id.cast_device_list_view);
        if (listView != null) {
            i = R.id.cast_searching_for_cast_text_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.cast_searching_for_cast_text_view);
            if (textView != null) {
                i = R.id.empty_view_item;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.empty_view_item);
                if (linearLayout != null) {
                    return new DialogSelectCastBinding((LinearLayout) rootView, listView, textView, linearLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
