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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateVideoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateVideoBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreateWebFileLayout;
    public final TextView createVideoControl;
    public final TextView createVideoFile;
    public final TextView createVideoHeight;
    public final TextView createVideoLoop;
    public final TextView createVideoName;
    public final TextView createVideoWidth;
    public final TextView createVideoX;
    public final TextView createVideoY;
    private final LinearLayout rootView;

    private BrickCreateVideoBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCreateWebFileLayout, TextView createVideoControl, TextView createVideoFile, TextView createVideoHeight, TextView createVideoLoop, TextView createVideoName, TextView createVideoWidth, TextView createVideoX, TextView createVideoY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateWebFileLayout = brickCreateWebFileLayout;
        this.createVideoControl = createVideoControl;
        this.createVideoFile = createVideoFile;
        this.createVideoHeight = createVideoHeight;
        this.createVideoLoop = createVideoLoop;
        this.createVideoName = createVideoName;
        this.createVideoWidth = createVideoWidth;
        this.createVideoX = createVideoX;
        this.createVideoY = createVideoY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateVideoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateVideoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_video, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateVideoBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_web_file_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_web_file_layout);
            if (brickLayout != null) {
                i = R.id.create_video_control;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_control);
                if (textView != null) {
                    i = R.id.create_video_file;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_file);
                    if (textView2 != null) {
                        i = R.id.create_video_height;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_height);
                        if (textView3 != null) {
                            i = R.id.create_video_loop;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_loop);
                            if (textView4 != null) {
                                i = R.id.create_video_name;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_name);
                                if (textView5 != null) {
                                    i = R.id.create_video_width;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_width);
                                    if (textView6 != null) {
                                        i = R.id.create_video_x;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_x);
                                        if (textView7 != null) {
                                            i = R.id.create_video_y;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.create_video_y);
                                            if (textView8 != null) {
                                                return new BrickCreateVideoBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8);
                                            }
                                        }
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
