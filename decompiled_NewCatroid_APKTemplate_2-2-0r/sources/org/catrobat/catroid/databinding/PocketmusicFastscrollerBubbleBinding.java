package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/PocketmusicFastscrollerBubbleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PocketmusicFastscrollerBubbleBinding implements ViewBinding {
    private final TextView rootView;

    private PocketmusicFastscrollerBubbleBinding(TextView rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public TextView getRoot() {
        return this.rootView;
    }

    public static PocketmusicFastscrollerBubbleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static PocketmusicFastscrollerBubbleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.pocketmusic_fastscroller_bubble, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static PocketmusicFastscrollerBubbleBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        return new PocketmusicFastscrollerBubbleBinding((TextView) rootView);
    }
}
