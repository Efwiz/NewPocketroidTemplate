package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ItemFileBrowserBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ItemFileBrowserBinding implements ViewBinding {
    public final ImageView fileIcon;
    public final TextView fileName;
    private final LinearLayout rootView;

    private ItemFileBrowserBinding(LinearLayout rootView, ImageView fileIcon, TextView fileName) {
        this.rootView = rootView;
        this.fileIcon = fileIcon;
        this.fileName = fileName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemFileBrowserBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemFileBrowserBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_file_browser, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemFileBrowserBinding bind(View rootView) {
        int i = R.id.file_icon;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.file_icon);
        if (imageView != null) {
            i = R.id.file_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.file_name);
            if (textView != null) {
                return new ItemFileBrowserBinding((LinearLayout) rootView, imageView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
