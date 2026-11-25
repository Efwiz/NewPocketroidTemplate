package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityStageGamepadBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityStageGamepadBinding implements ViewBinding {
    public final ImageView gamepadBackground;
    public final ImageButton gamepadButtonA;
    public final ImageButton gamepadButtonB;
    public final ImageButton gamepadButtonDown;
    public final ImageButton gamepadButtonLeft;
    public final ImageButton gamepadButtonRight;
    public final ImageButton gamepadButtonUp;
    public final ImageButton gamepadPauseButton;
    private final RelativeLayout rootView;

    private ActivityStageGamepadBinding(RelativeLayout rootView, ImageView gamepadBackground, ImageButton gamepadButtonA, ImageButton gamepadButtonB, ImageButton gamepadButtonDown, ImageButton gamepadButtonLeft, ImageButton gamepadButtonRight, ImageButton gamepadButtonUp, ImageButton gamepadPauseButton) {
        this.rootView = rootView;
        this.gamepadBackground = gamepadBackground;
        this.gamepadButtonA = gamepadButtonA;
        this.gamepadButtonB = gamepadButtonB;
        this.gamepadButtonDown = gamepadButtonDown;
        this.gamepadButtonLeft = gamepadButtonLeft;
        this.gamepadButtonRight = gamepadButtonRight;
        this.gamepadButtonUp = gamepadButtonUp;
        this.gamepadPauseButton = gamepadPauseButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityStageGamepadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityStageGamepadBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_stage_gamepad, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityStageGamepadBinding bind(View rootView) {
        int i = R.id.gamepadBackground;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.gamepadBackground);
        if (imageView != null) {
            i = R.id.gamepadButtonA;
            ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.gamepadButtonA);
            if (imageButton != null) {
                i = R.id.gamepadButtonB;
                ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.gamepadButtonB);
                if (imageButton2 != null) {
                    i = R.id.gamepadButtonDown;
                    ImageButton imageButton3 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.gamepadButtonDown);
                    if (imageButton3 != null) {
                        i = R.id.gamepadButtonLeft;
                        ImageButton imageButton4 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.gamepadButtonLeft);
                        if (imageButton4 != null) {
                            i = R.id.gamepadButtonRight;
                            ImageButton imageButton5 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.gamepadButtonRight);
                            if (imageButton5 != null) {
                                i = R.id.gamepadButtonUp;
                                ImageButton imageButton6 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.gamepadButtonUp);
                                if (imageButton6 != null) {
                                    i = R.id.gamepadPauseButton;
                                    ImageButton imageButton7 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.gamepadPauseButton);
                                    if (imageButton7 != null) {
                                        return new ActivityStageGamepadBinding((RelativeLayout) rootView, imageView, imageButton, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
