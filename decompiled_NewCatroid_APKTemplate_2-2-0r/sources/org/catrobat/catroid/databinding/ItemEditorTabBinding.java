package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ItemEditorTabBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ItemEditorTabBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final ImageButton tabCloseBtn;
    public final LinearLayout tabContainer;
    public final TextView tabFileName;

    private ItemEditorTabBinding(LinearLayout rootView, ImageButton tabCloseBtn, LinearLayout tabContainer, TextView tabFileName) {
        this.rootView = rootView;
        this.tabCloseBtn = tabCloseBtn;
        this.tabContainer = tabContainer;
        this.tabFileName = tabFileName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemEditorTabBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemEditorTabBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_editor_tab, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemEditorTabBinding bind(View rootView) {
        int i = R.id.tab_close_btn;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.tab_close_btn);
        if (imageButton != null) {
            LinearLayout linearLayout = (LinearLayout) rootView;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tab_file_name);
            if (textView != null) {
                return new ItemEditorTabBinding(linearLayout, imageButton, linearLayout, textView);
            }
            i = R.id.tab_file_name;
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
