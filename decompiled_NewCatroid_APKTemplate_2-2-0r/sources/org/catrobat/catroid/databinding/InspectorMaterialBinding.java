package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorMaterialBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorMaterialBinding implements ViewBinding {
    public final Button btnEmissiveColor;
    public final Button btnMaterialColor;
    public final LinearLayout materialParamsContainer;
    private final LinearLayout rootView;
    public final InspectorSliderBinding sliderMetallic;
    public final InspectorSliderBinding sliderRoughness;
    public final InspectorTextureSlotBinding slotBaseColorTexture;
    public final InspectorTextureSlotBinding slotEmissiveTexture;
    public final InspectorTextureSlotBinding slotMrTexture;
    public final InspectorTextureSlotBinding slotNormalTexture;
    public final InspectorTextureSlotBinding slotOcclusionTexture;

    private InspectorMaterialBinding(LinearLayout rootView, Button btnEmissiveColor, Button btnMaterialColor, LinearLayout materialParamsContainer, InspectorSliderBinding sliderMetallic, InspectorSliderBinding sliderRoughness, InspectorTextureSlotBinding slotBaseColorTexture, InspectorTextureSlotBinding slotEmissiveTexture, InspectorTextureSlotBinding slotMrTexture, InspectorTextureSlotBinding slotNormalTexture, InspectorTextureSlotBinding slotOcclusionTexture) {
        this.rootView = rootView;
        this.btnEmissiveColor = btnEmissiveColor;
        this.btnMaterialColor = btnMaterialColor;
        this.materialParamsContainer = materialParamsContainer;
        this.sliderMetallic = sliderMetallic;
        this.sliderRoughness = sliderRoughness;
        this.slotBaseColorTexture = slotBaseColorTexture;
        this.slotEmissiveTexture = slotEmissiveTexture;
        this.slotMrTexture = slotMrTexture;
        this.slotNormalTexture = slotNormalTexture;
        this.slotOcclusionTexture = slotOcclusionTexture;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorMaterialBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorMaterialBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_material, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorMaterialBinding bind(View rootView) {
        int i = R.id.btn_emissive_color;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_emissive_color);
        if (button != null) {
            i = R.id.btn_material_color;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_material_color);
            if (button2 != null) {
                i = R.id.material_params_container;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.material_params_container);
                if (linearLayout != null) {
                    i = R.id.slider_metallic;
                    View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.slider_metallic);
                    if (viewFindChildViewById != null) {
                        InspectorSliderBinding inspectorSliderBindingBind = InspectorSliderBinding.bind(viewFindChildViewById);
                        i = R.id.slider_roughness;
                        View viewFindChildViewById2 = ViewBindings.findChildViewById(rootView, R.id.slider_roughness);
                        if (viewFindChildViewById2 != null) {
                            InspectorSliderBinding inspectorSliderBindingBind2 = InspectorSliderBinding.bind(viewFindChildViewById2);
                            i = R.id.slot_base_color_texture;
                            View viewFindChildViewById3 = ViewBindings.findChildViewById(rootView, R.id.slot_base_color_texture);
                            if (viewFindChildViewById3 != null) {
                                InspectorTextureSlotBinding inspectorTextureSlotBindingBind = InspectorTextureSlotBinding.bind(viewFindChildViewById3);
                                i = R.id.slot_emissive_texture;
                                View viewFindChildViewById4 = ViewBindings.findChildViewById(rootView, R.id.slot_emissive_texture);
                                if (viewFindChildViewById4 != null) {
                                    InspectorTextureSlotBinding inspectorTextureSlotBindingBind2 = InspectorTextureSlotBinding.bind(viewFindChildViewById4);
                                    i = R.id.slot_mr_texture;
                                    View viewFindChildViewById5 = ViewBindings.findChildViewById(rootView, R.id.slot_mr_texture);
                                    if (viewFindChildViewById5 != null) {
                                        InspectorTextureSlotBinding inspectorTextureSlotBindingBind3 = InspectorTextureSlotBinding.bind(viewFindChildViewById5);
                                        i = R.id.slot_normal_texture;
                                        View viewFindChildViewById6 = ViewBindings.findChildViewById(rootView, R.id.slot_normal_texture);
                                        if (viewFindChildViewById6 != null) {
                                            InspectorTextureSlotBinding inspectorTextureSlotBindingBind4 = InspectorTextureSlotBinding.bind(viewFindChildViewById6);
                                            i = R.id.slot_occlusion_texture;
                                            View viewFindChildViewById7 = ViewBindings.findChildViewById(rootView, R.id.slot_occlusion_texture);
                                            if (viewFindChildViewById7 != null) {
                                                return new InspectorMaterialBinding((LinearLayout) rootView, button, button2, linearLayout, inspectorSliderBindingBind, inspectorSliderBindingBind2, inspectorTextureSlotBindingBind, inspectorTextureSlotBindingBind2, inspectorTextureSlotBindingBind3, inspectorTextureSlotBindingBind4, InspectorTextureSlotBinding.bind(viewFindChildViewById7));
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
