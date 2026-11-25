package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ItemFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ItemFileBinding implements ViewBinding {
    public final ImageButton deleteButton;
    public final ImageView fileIcon;
    public final TextView fileName;
    public final TextView fileSize;
    private final MaterialCardView rootView;

    private ItemFileBinding(MaterialCardView rootView, ImageButton deleteButton, ImageView fileIcon, TextView fileName, TextView fileSize) {
        this.rootView = rootView;
        this.deleteButton = deleteButton;
        this.fileIcon = fileIcon;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override // androidx.viewbinding.ViewBinding
    public MaterialCardView getRoot() {
        return this.rootView;
    }

    public static ItemFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemFileBinding bind(View rootView) {
        int i = R.id.deleteButton;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.deleteButton);
        if (imageButton != null) {
            i = R.id.file_icon;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.file_icon);
            if (imageView != null) {
                i = R.id.file_name;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.file_name);
                if (textView != null) {
                    i = R.id.file_size;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.file_size);
                    if (textView2 != null) {
                        return new ItemFileBinding((MaterialCardView) rootView, imageButton, imageView, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
