package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/CastPauseScreenBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class CastPauseScreenBinding implements ViewBinding {
    public final RelativeLayout castPauseScreenLayout;
    private final RelativeLayout rootView;

    private CastPauseScreenBinding(RelativeLayout rootView, RelativeLayout castPauseScreenLayout) {
        this.rootView = rootView;
        this.castPauseScreenLayout = castPauseScreenLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static CastPauseScreenBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static CastPauseScreenBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.cast_pause_screen, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static CastPauseScreenBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        RelativeLayout relativeLayout = (RelativeLayout) rootView;
        return new CastPauseScreenBinding(relativeLayout, relativeLayout);
    }
}
