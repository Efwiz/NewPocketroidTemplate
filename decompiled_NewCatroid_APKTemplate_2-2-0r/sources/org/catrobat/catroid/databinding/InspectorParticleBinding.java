package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorParticleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorParticleBinding implements ViewBinding {
    public final ImageButton btnClearTexture;
    public final Button btnSelectTexture;
    public final InspectorParamFloatBinding pConeAngle;
    public final InspectorParamFloatBinding pConeRadius;
    public final InspectorParamFloatBinding pDuration;
    public final InspectorParamFloatBinding pEmissionRate;
    public final Button pEndColor;
    public final InspectorParamFloatBinding pEndSize;
    public final InspectorParamFloatBinding pGravity;
    public final CheckBox pIsAdditive;
    public final CheckBox pLooping;
    public final InspectorParamFloatBinding pMaxParticles;
    public final InspectorParamFloatBinding pRotationOverLifetime;
    public final Button pStartColor;
    public final InspectorParamFloatBinding pStartLifetime;
    public final InspectorParamFloatBinding pStartRotation;
    public final InspectorParamFloatBinding pStartSize;
    public final InspectorParamFloatBinding pStartSpeed;
    private final LinearLayout rootView;
    public final LinearLayout slotParticleTexture;
    public final TextView textTexturePath;

    private InspectorParticleBinding(LinearLayout rootView, ImageButton btnClearTexture, Button btnSelectTexture, InspectorParamFloatBinding pConeAngle, InspectorParamFloatBinding pConeRadius, InspectorParamFloatBinding pDuration, InspectorParamFloatBinding pEmissionRate, Button pEndColor, InspectorParamFloatBinding pEndSize, InspectorParamFloatBinding pGravity, CheckBox pIsAdditive, CheckBox pLooping, InspectorParamFloatBinding pMaxParticles, InspectorParamFloatBinding pRotationOverLifetime, Button pStartColor, InspectorParamFloatBinding pStartLifetime, InspectorParamFloatBinding pStartRotation, InspectorParamFloatBinding pStartSize, InspectorParamFloatBinding pStartSpeed, LinearLayout slotParticleTexture, TextView textTexturePath) {
        this.rootView = rootView;
        this.btnClearTexture = btnClearTexture;
        this.btnSelectTexture = btnSelectTexture;
        this.pConeAngle = pConeAngle;
        this.pConeRadius = pConeRadius;
        this.pDuration = pDuration;
        this.pEmissionRate = pEmissionRate;
        this.pEndColor = pEndColor;
        this.pEndSize = pEndSize;
        this.pGravity = pGravity;
        this.pIsAdditive = pIsAdditive;
        this.pLooping = pLooping;
        this.pMaxParticles = pMaxParticles;
        this.pRotationOverLifetime = pRotationOverLifetime;
        this.pStartColor = pStartColor;
        this.pStartLifetime = pStartLifetime;
        this.pStartRotation = pStartRotation;
        this.pStartSize = pStartSize;
        this.pStartSpeed = pStartSpeed;
        this.slotParticleTexture = slotParticleTexture;
        this.textTexturePath = textTexturePath;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorParticleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorParticleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_particle, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorParticleBinding bind(View rootView) {
        int i = R.id.btn_clear_texture;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_clear_texture);
        if (imageButton != null) {
            i = R.id.btn_select_texture;
            Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_select_texture);
            if (button != null) {
                i = R.id.p_cone_angle;
                View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.p_cone_angle);
                if (viewFindChildViewById != null) {
                    InspectorParamFloatBinding inspectorParamFloatBindingBind = InspectorParamFloatBinding.bind(viewFindChildViewById);
                    i = R.id.p_cone_radius;
                    View viewFindChildViewById2 = ViewBindings.findChildViewById(rootView, R.id.p_cone_radius);
                    if (viewFindChildViewById2 != null) {
                        InspectorParamFloatBinding inspectorParamFloatBindingBind2 = InspectorParamFloatBinding.bind(viewFindChildViewById2);
                        i = R.id.p_duration;
                        View viewFindChildViewById3 = ViewBindings.findChildViewById(rootView, R.id.p_duration);
                        if (viewFindChildViewById3 != null) {
                            InspectorParamFloatBinding inspectorParamFloatBindingBind3 = InspectorParamFloatBinding.bind(viewFindChildViewById3);
                            i = R.id.p_emission_rate;
                            View viewFindChildViewById4 = ViewBindings.findChildViewById(rootView, R.id.p_emission_rate);
                            if (viewFindChildViewById4 != null) {
                                InspectorParamFloatBinding inspectorParamFloatBindingBind4 = InspectorParamFloatBinding.bind(viewFindChildViewById4);
                                i = R.id.p_end_color;
                                Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.p_end_color);
                                if (button2 != null) {
                                    i = R.id.p_end_size;
                                    View viewFindChildViewById5 = ViewBindings.findChildViewById(rootView, R.id.p_end_size);
                                    if (viewFindChildViewById5 != null) {
                                        InspectorParamFloatBinding inspectorParamFloatBindingBind5 = InspectorParamFloatBinding.bind(viewFindChildViewById5);
                                        i = R.id.p_gravity;
                                        View viewFindChildViewById6 = ViewBindings.findChildViewById(rootView, R.id.p_gravity);
                                        if (viewFindChildViewById6 != null) {
                                            InspectorParamFloatBinding inspectorParamFloatBindingBind6 = InspectorParamFloatBinding.bind(viewFindChildViewById6);
                                            i = R.id.p_is_additive;
                                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.p_is_additive);
                                            if (checkBox != null) {
                                                i = R.id.p_looping;
                                                CheckBox checkBox2 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.p_looping);
                                                if (checkBox2 != null) {
                                                    i = R.id.p_max_particles;
                                                    View viewFindChildViewById7 = ViewBindings.findChildViewById(rootView, R.id.p_max_particles);
                                                    if (viewFindChildViewById7 != null) {
                                                        InspectorParamFloatBinding inspectorParamFloatBindingBind7 = InspectorParamFloatBinding.bind(viewFindChildViewById7);
                                                        i = R.id.p_rotation_over_lifetime;
                                                        View viewFindChildViewById8 = ViewBindings.findChildViewById(rootView, R.id.p_rotation_over_lifetime);
                                                        if (viewFindChildViewById8 != null) {
                                                            InspectorParamFloatBinding inspectorParamFloatBindingBind8 = InspectorParamFloatBinding.bind(viewFindChildViewById8);
                                                            i = R.id.p_start_color;
                                                            Button button3 = (Button) ViewBindings.findChildViewById(rootView, R.id.p_start_color);
                                                            if (button3 != null) {
                                                                i = R.id.p_start_lifetime;
                                                                View viewFindChildViewById9 = ViewBindings.findChildViewById(rootView, R.id.p_start_lifetime);
                                                                if (viewFindChildViewById9 != null) {
                                                                    InspectorParamFloatBinding inspectorParamFloatBindingBind9 = InspectorParamFloatBinding.bind(viewFindChildViewById9);
                                                                    i = R.id.p_start_rotation;
                                                                    View viewFindChildViewById10 = ViewBindings.findChildViewById(rootView, R.id.p_start_rotation);
                                                                    if (viewFindChildViewById10 != null) {
                                                                        InspectorParamFloatBinding inspectorParamFloatBindingBind10 = InspectorParamFloatBinding.bind(viewFindChildViewById10);
                                                                        i = R.id.p_start_size;
                                                                        View viewFindChildViewById11 = ViewBindings.findChildViewById(rootView, R.id.p_start_size);
                                                                        if (viewFindChildViewById11 != null) {
                                                                            InspectorParamFloatBinding inspectorParamFloatBindingBind11 = InspectorParamFloatBinding.bind(viewFindChildViewById11);
                                                                            i = R.id.p_start_speed;
                                                                            View viewFindChildViewById12 = ViewBindings.findChildViewById(rootView, R.id.p_start_speed);
                                                                            if (viewFindChildViewById12 != null) {
                                                                                InspectorParamFloatBinding inspectorParamFloatBindingBind12 = InspectorParamFloatBinding.bind(viewFindChildViewById12);
                                                                                i = R.id.slot_particle_texture;
                                                                                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.slot_particle_texture);
                                                                                if (linearLayout != null) {
                                                                                    i = R.id.text_texture_path;
                                                                                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_texture_path);
                                                                                    if (textView != null) {
                                                                                        return new InspectorParticleBinding((LinearLayout) rootView, imageButton, button, inspectorParamFloatBindingBind, inspectorParamFloatBindingBind2, inspectorParamFloatBindingBind3, inspectorParamFloatBindingBind4, button2, inspectorParamFloatBindingBind5, inspectorParamFloatBindingBind6, checkBox, checkBox2, inspectorParamFloatBindingBind7, inspectorParamFloatBindingBind8, button3, inspectorParamFloatBindingBind9, inspectorParamFloatBindingBind10, inspectorParamFloatBindingBind11, inspectorParamFloatBindingBind12, linearLayout, textView);
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
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
