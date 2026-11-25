package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorColliderItemBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorColliderItemBinding implements ViewBinding {
    public final ImageButton btnDeleteCollider;
    public final ImageButton btnDuplicateCollider;
    public final LinearLayout colliderContentLayout;
    public final TextView colliderTitle;
    public final EditText editColliderCx;
    public final EditText editColliderCy;
    public final EditText editColliderCz;
    public final EditText editColliderRadius;
    public final EditText editColliderSx;
    public final EditText editColliderSy;
    public final EditText editColliderSz;
    public final TextView labelColliderSx;
    public final TextView labelColliderSy;
    public final TextView labelColliderSz;
    public final LinearLayout layoutColliderRadius;
    public final LinearLayout layoutColliderSize;
    private final LinearLayout rootView;

    private InspectorColliderItemBinding(LinearLayout rootView, ImageButton btnDeleteCollider, ImageButton btnDuplicateCollider, LinearLayout colliderContentLayout, TextView colliderTitle, EditText editColliderCx, EditText editColliderCy, EditText editColliderCz, EditText editColliderRadius, EditText editColliderSx, EditText editColliderSy, EditText editColliderSz, TextView labelColliderSx, TextView labelColliderSy, TextView labelColliderSz, LinearLayout layoutColliderRadius, LinearLayout layoutColliderSize) {
        this.rootView = rootView;
        this.btnDeleteCollider = btnDeleteCollider;
        this.btnDuplicateCollider = btnDuplicateCollider;
        this.colliderContentLayout = colliderContentLayout;
        this.colliderTitle = colliderTitle;
        this.editColliderCx = editColliderCx;
        this.editColliderCy = editColliderCy;
        this.editColliderCz = editColliderCz;
        this.editColliderRadius = editColliderRadius;
        this.editColliderSx = editColliderSx;
        this.editColliderSy = editColliderSy;
        this.editColliderSz = editColliderSz;
        this.labelColliderSx = labelColliderSx;
        this.labelColliderSy = labelColliderSy;
        this.labelColliderSz = labelColliderSz;
        this.layoutColliderRadius = layoutColliderRadius;
        this.layoutColliderSize = layoutColliderSize;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorColliderItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorColliderItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_collider_item, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorColliderItemBinding bind(View rootView) {
        int i = R.id.btn_delete_collider;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_delete_collider);
        if (imageButton != null) {
            i = R.id.btn_duplicate_collider;
            ImageButton imageButton2 = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_duplicate_collider);
            if (imageButton2 != null) {
                i = R.id.collider_content_layout;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.collider_content_layout);
                if (linearLayout != null) {
                    i = R.id.collider_title;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.collider_title);
                    if (textView != null) {
                        i = R.id.edit_collider_cx;
                        EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_collider_cx);
                        if (editText != null) {
                            i = R.id.edit_collider_cy;
                            EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_collider_cy);
                            if (editText2 != null) {
                                i = R.id.edit_collider_cz;
                                EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_collider_cz);
                                if (editText3 != null) {
                                    i = R.id.edit_collider_radius;
                                    EditText editText4 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_collider_radius);
                                    if (editText4 != null) {
                                        i = R.id.edit_collider_sx;
                                        EditText editText5 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_collider_sx);
                                        if (editText5 != null) {
                                            i = R.id.edit_collider_sy;
                                            EditText editText6 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_collider_sy);
                                            if (editText6 != null) {
                                                i = R.id.edit_collider_sz;
                                                EditText editText7 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_collider_sz);
                                                if (editText7 != null) {
                                                    i = R.id.label_collider_sx;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.label_collider_sx);
                                                    if (textView2 != null) {
                                                        i = R.id.label_collider_sy;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.label_collider_sy);
                                                        if (textView3 != null) {
                                                            i = R.id.label_collider_sz;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.label_collider_sz);
                                                            if (textView4 != null) {
                                                                i = R.id.layout_collider_radius;
                                                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_collider_radius);
                                                                if (linearLayout2 != null) {
                                                                    i = R.id.layout_collider_size;
                                                                    LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_collider_size);
                                                                    if (linearLayout3 != null) {
                                                                        return new InspectorColliderItemBinding((LinearLayout) rootView, imageButton, imageButton2, linearLayout, textView, editText, editText2, editText3, editText4, editText5, editText6, editText7, textView2, textView3, textView4, linearLayout2, linearLayout3);
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
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
