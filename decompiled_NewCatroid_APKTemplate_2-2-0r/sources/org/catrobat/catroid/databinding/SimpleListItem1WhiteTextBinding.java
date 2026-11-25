package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/SimpleListItem1WhiteTextBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class SimpleListItem1WhiteTextBinding implements ViewBinding {
    private final TextView rootView;
    public final TextView text1;

    private SimpleListItem1WhiteTextBinding(TextView rootView, TextView text1) {
        this.rootView = rootView;
        this.text1 = text1;
    }

    @Override // androidx.viewbinding.ViewBinding
    public TextView getRoot() {
        return this.rootView;
    }

    public static SimpleListItem1WhiteTextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static SimpleListItem1WhiteTextBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.simple_list_item_1_white_text, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static SimpleListItem1WhiteTextBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        TextView textView = (TextView) rootView;
        return new SimpleListItem1WhiteTextBinding(textView, textView);
    }
}
