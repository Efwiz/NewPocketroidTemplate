package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorTransformBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorTransformBinding implements ViewBinding {
    public final ImageButton btnAlignPositionWithView;
    public final ImageButton btnAlignRotationWithView;
    public final EditText editPosX;
    public final EditText editPosY;
    public final EditText editPosZ;
    public final EditText editRotX;
    public final EditText editRotY;
    public final EditText editRotZ;
    public final EditText editScaleX;
    public final EditText editScaleY;
    public final EditText editScaleZ;
    private final LinearLayout rootView;

    private InspectorTransformBinding(LinearLayout rootView, ImageButton btnAlignPositionWithView, ImageButton btnAlignRotationWithView, EditText editPosX, EditText editPosY, EditText editPosZ, EditText editRotX, EditText editRotY, EditText editRotZ, EditText editScaleX, EditText editScaleY, EditText editScaleZ) {
        this.rootView = rootView;
        this.btnAlignPositionWithView = btnAlignPositionWithView;
        this.btnAlignRotationWithView = btnAlignRotationWithView;
        this.editPosX = editPosX;
        this.editPosY = editPosY;
        this.editPosZ = editPosZ;
        this.editRotX = editRotX;
        this.editRotY = editRotY;
        this.editRotZ = editRotZ;
        this.editScaleX = editScaleX;
        this.editScaleY = editScaleY;
        this.editScaleZ = editScaleZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorTransformBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorTransformBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_transform, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorTransformBinding bind(View rootView) {
        int i = R.id.btn_align_position_with_view;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_align_position_with_view);
        if (imageButton != null) {
            i = R.id.btn_align_rotation_with_view;
            ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_align_rotation_with_view);
            if (imageButton2 != null) {
                i = R.id.edit_pos_x;
                EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_pos_x);
                if (editText != null) {
                    i = R.id.edit_pos_y;
                    EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_pos_y);
                    if (editText2 != null) {
                        i = R.id.edit_pos_z;
                        EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_pos_z);
                        if (editText3 != null) {
                            i = R.id.edit_rot_x;
                            EditText editText4 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_rot_x);
                            if (editText4 != null) {
                                i = R.id.edit_rot_y;
                                EditText editText5 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_rot_y);
                                if (editText5 != null) {
                                    i = R.id.edit_rot_z;
                                    EditText editText6 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_rot_z);
                                    if (editText6 != null) {
                                        i = R.id.edit_scale_x;
                                        EditText editText7 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_scale_x);
                                        if (editText7 != null) {
                                            i = R.id.edit_scale_y;
                                            EditText editText8 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_scale_y);
                                            if (editText8 != null) {
                                                i = R.id.edit_scale_z;
                                                EditText editText9 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_scale_z);
                                                if (editText9 != null) {
                                                    return new InspectorTransformBinding((LinearLayout) rootView, imageButton, imageButton2, editText, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9);
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
