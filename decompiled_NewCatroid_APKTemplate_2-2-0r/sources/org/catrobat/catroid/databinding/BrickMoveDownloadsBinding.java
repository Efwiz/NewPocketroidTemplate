package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickMoveDownloadsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickMoveDownloadsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMoveDownloadsEditText;
    public final BrickLayout brickMoveDownloadsLayout;
    private final LinearLayout rootView;

    private BrickMoveDownloadsBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMoveDownloadsEditText, BrickLayout brickMoveDownloadsLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMoveDownloadsEditText = brickMoveDownloadsEditText;
        this.brickMoveDownloadsLayout = brickMoveDownloadsLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickMoveDownloadsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickMoveDownloadsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_move_downloads, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickMoveDownloadsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_move_downloads_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_move_downloads_edit_text);
            if (textView != null) {
                i = R.id.brick_move_downloads_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_move_downloads_layout);
                if (brickLayout != null) {
                    return new BrickMoveDownloadsBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
