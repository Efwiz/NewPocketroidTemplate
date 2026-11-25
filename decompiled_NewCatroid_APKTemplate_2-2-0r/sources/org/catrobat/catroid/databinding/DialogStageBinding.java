package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogStageBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogStageBinding implements ViewBinding {
    private final RelativeLayout rootView;
    public final Button stageDialogButtonBack;
    public final Button stageDialogButtonContinue;
    public final Button stageDialogButtonDebug;
    public final ImageButton stageDialogButtonMaximize;
    public final Button stageDialogButtonRestart;
    public final Button stageDialogButtonScreenshot;
    public final FloatingActionButton stageDialogButtonShare;
    public final Button stageDialogButtonToggleAxes;
    public final ImageView stageDialogMenu;
    public final ImageView stageDialogMenuSideLeft;
    public final ImageView stageDialogMenuSideRight;
    public final LinearLayout stageLayoutLinearShare;
    public final RelativeLayout stageLayoutRelative;

    private DialogStageBinding(RelativeLayout rootView, Button stageDialogButtonBack, Button stageDialogButtonContinue, Button stageDialogButtonDebug, ImageButton stageDialogButtonMaximize, Button stageDialogButtonRestart, Button stageDialogButtonScreenshot, FloatingActionButton stageDialogButtonShare, Button stageDialogButtonToggleAxes, ImageView stageDialogMenu, ImageView stageDialogMenuSideLeft, ImageView stageDialogMenuSideRight, LinearLayout stageLayoutLinearShare, RelativeLayout stageLayoutRelative) {
        this.rootView = rootView;
        this.stageDialogButtonBack = stageDialogButtonBack;
        this.stageDialogButtonContinue = stageDialogButtonContinue;
        this.stageDialogButtonDebug = stageDialogButtonDebug;
        this.stageDialogButtonMaximize = stageDialogButtonMaximize;
        this.stageDialogButtonRestart = stageDialogButtonRestart;
        this.stageDialogButtonScreenshot = stageDialogButtonScreenshot;
        this.stageDialogButtonShare = stageDialogButtonShare;
        this.stageDialogButtonToggleAxes = stageDialogButtonToggleAxes;
        this.stageDialogMenu = stageDialogMenu;
        this.stageDialogMenuSideLeft = stageDialogMenuSideLeft;
        this.stageDialogMenuSideRight = stageDialogMenuSideRight;
        this.stageLayoutLinearShare = stageLayoutLinearShare;
        this.stageLayoutRelative = stageLayoutRelative;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static DialogStageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogStageBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_stage, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogStageBinding bind(View rootView) {
        int i = R.id.stage_dialog_button_back;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_back);
        if (button != null) {
            i = R.id.stage_dialog_button_continue;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_continue);
            if (button2 != null) {
                i = R.id.stage_dialog_button_debug;
                Button button3 = (Button) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_debug);
                if (button3 != null) {
                    i = R.id.stage_dialog_button_maximize;
                    ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_maximize);
                    if (imageButton != null) {
                        i = R.id.stage_dialog_button_restart;
                        Button button4 = (Button) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_restart);
                        if (button4 != null) {
                            i = R.id.stage_dialog_button_screenshot;
                            Button button5 = (Button) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_screenshot);
                            if (button5 != null) {
                                i = R.id.stage_dialog_button_share;
                                FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_share);
                                if (floatingActionButton != null) {
                                    i = R.id.stage_dialog_button_toggle_axes;
                                    Button button6 = (Button) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_button_toggle_axes);
                                    if (button6 != null) {
                                        i = R.id.stage_dialog_menu;
                                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_menu);
                                        if (imageView != null) {
                                            i = R.id.stage_dialog_menu_side_left;
                                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_menu_side_left);
                                            if (imageView2 != null) {
                                                i = R.id.stage_dialog_menu_side_right;
                                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.stage_dialog_menu_side_right);
                                                if (imageView3 != null) {
                                                    i = R.id.stage_layout_linear_share;
                                                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.stage_layout_linear_share);
                                                    if (linearLayout != null) {
                                                        RelativeLayout relativeLayout = (RelativeLayout) rootView;
                                                        return new DialogStageBinding(relativeLayout, button, button2, button3, imageButton, button4, button5, floatingActionButton, button6, imageView, imageView2, imageView3, linearLayout, relativeLayout);
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
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
