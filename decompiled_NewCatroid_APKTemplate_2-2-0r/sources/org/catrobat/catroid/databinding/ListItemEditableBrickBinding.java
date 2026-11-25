package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ListItemEditableBrickBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ListItemEditableBrickBinding implements ViewBinding {
    public final TextView brickFunctionName;
    public final TextView brickHeaderText;
    public final TextView brickId;
    private final MaterialCardView rootView;

    private ListItemEditableBrickBinding(MaterialCardView rootView, TextView brickFunctionName, TextView brickHeaderText, TextView brickId) {
        this.rootView = rootView;
        this.brickFunctionName = brickFunctionName;
        this.brickHeaderText = brickHeaderText;
        this.brickId = brickId;
    }

    @Override // androidx.viewbinding.ViewBinding
    public MaterialCardView getRoot() {
        return this.rootView;
    }

    public static ListItemEditableBrickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ListItemEditableBrickBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.list_item_editable_brick, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ListItemEditableBrickBinding bind(View rootView) {
        int i = R.id.brick_function_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_function_name);
        if (textView != null) {
            i = R.id.brick_header_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_header_text);
            if (textView2 != null) {
                i = R.id.brick_id;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_id);
                if (textView3 != null) {
                    return new ListItemEditableBrickBinding((MaterialCardView) rootView, textView, textView2, textView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
