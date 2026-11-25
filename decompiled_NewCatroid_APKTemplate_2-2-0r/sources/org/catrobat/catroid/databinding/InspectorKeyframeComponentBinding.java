package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorKeyframeComponentBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorKeyframeComponentBinding implements ViewBinding {
    public final Button btnAddKeyframe;
    public final ImageButton btnKeyframePlay;
    public final ImageButton btnKeyframeStop;
    public final CheckBox keyframeAutostart;
    public final LinearLayout keyframeListContainer;
    public final CheckBox keyframeLoop;
    public final SeekBar keyframeTimelineSlider;
    private final LinearLayout rootView;

    private InspectorKeyframeComponentBinding(LinearLayout rootView, Button btnAddKeyframe, ImageButton btnKeyframePlay, ImageButton btnKeyframeStop, CheckBox keyframeAutostart, LinearLayout keyframeListContainer, CheckBox keyframeLoop, SeekBar keyframeTimelineSlider) {
        this.rootView = rootView;
        this.btnAddKeyframe = btnAddKeyframe;
        this.btnKeyframePlay = btnKeyframePlay;
        this.btnKeyframeStop = btnKeyframeStop;
        this.keyframeAutostart = keyframeAutostart;
        this.keyframeListContainer = keyframeListContainer;
        this.keyframeLoop = keyframeLoop;
        this.keyframeTimelineSlider = keyframeTimelineSlider;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorKeyframeComponentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorKeyframeComponentBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_keyframe_component, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorKeyframeComponentBinding bind(View rootView) {
        int i = R.id.btn_add_keyframe;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_keyframe);
        if (button != null) {
            i = R.id.btn_keyframe_play;
            ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_keyframe_play);
            if (imageButton != null) {
                i = R.id.btn_keyframe_stop;
                ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_keyframe_stop);
                if (imageButton2 != null) {
                    i = R.id.keyframe_autostart;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.keyframe_autostart);
                    if (checkBox != null) {
                        i = R.id.keyframe_list_container;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.keyframe_list_container);
                        if (linearLayout != null) {
                            i = R.id.keyframe_loop;
                            CheckBox checkBox2 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.keyframe_loop);
                            if (checkBox2 != null) {
                                i = R.id.keyframe_timeline_slider;
                                SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(rootView, R.id.keyframe_timeline_slider);
                                if (seekBar != null) {
                                    return new InspectorKeyframeComponentBinding((LinearLayout) rootView, button, imageButton, imageButton2, checkBox, linearLayout, checkBox2, seekBar);
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
