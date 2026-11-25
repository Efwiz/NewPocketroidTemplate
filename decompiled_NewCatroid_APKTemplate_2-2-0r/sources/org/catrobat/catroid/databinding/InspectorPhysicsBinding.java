package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorPhysicsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorPhysicsBinding implements ViewBinding {
    public final Button btnAddBoxCollider;
    public final Button btnAddCapsuleCollider;
    public final LinearLayout collidersContainer;
    public final EditText editPhysicsFriction;
    public final EditText editPhysicsMass;
    public final EditText editPhysicsRestitution;
    public final LinearLayout layoutPhysicsMass;
    public final LinearLayout layoutPhysicsProperties;
    private final LinearLayout rootView;
    public final Spinner spinnerPhysicsState;

    private InspectorPhysicsBinding(LinearLayout rootView, Button btnAddBoxCollider, Button btnAddCapsuleCollider, LinearLayout collidersContainer, EditText editPhysicsFriction, EditText editPhysicsMass, EditText editPhysicsRestitution, LinearLayout layoutPhysicsMass, LinearLayout layoutPhysicsProperties, Spinner spinnerPhysicsState) {
        this.rootView = rootView;
        this.btnAddBoxCollider = btnAddBoxCollider;
        this.btnAddCapsuleCollider = btnAddCapsuleCollider;
        this.collidersContainer = collidersContainer;
        this.editPhysicsFriction = editPhysicsFriction;
        this.editPhysicsMass = editPhysicsMass;
        this.editPhysicsRestitution = editPhysicsRestitution;
        this.layoutPhysicsMass = layoutPhysicsMass;
        this.layoutPhysicsProperties = layoutPhysicsProperties;
        this.spinnerPhysicsState = spinnerPhysicsState;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorPhysicsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorPhysicsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_physics, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorPhysicsBinding bind(View rootView) {
        int i = R.id.btn_add_box_collider;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_box_collider);
        if (button != null) {
            i = R.id.btn_add_capsule_collider;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_capsule_collider);
            if (button2 != null) {
                i = R.id.colliders_container;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.colliders_container);
                if (linearLayout != null) {
                    i = R.id.edit_physics_friction;
                    EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_physics_friction);
                    if (editText != null) {
                        i = R.id.edit_physics_mass;
                        EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_physics_mass);
                        if (editText2 != null) {
                            i = R.id.edit_physics_restitution;
                            EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_physics_restitution);
                            if (editText3 != null) {
                                i = R.id.layout_physics_mass;
                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_physics_mass);
                                if (linearLayout2 != null) {
                                    i = R.id.layout_physics_properties;
                                    LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_physics_properties);
                                    if (linearLayout3 != null) {
                                        i = R.id.spinner_physics_state;
                                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner_physics_state);
                                        if (spinner != null) {
                                            return new InspectorPhysicsBinding((LinearLayout) rootView, button, button2, linearLayout, editText, editText2, editText3, linearLayout2, linearLayout3, spinner);
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
