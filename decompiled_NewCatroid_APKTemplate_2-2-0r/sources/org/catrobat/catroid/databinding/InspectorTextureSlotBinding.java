package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorTextureSlotBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorTextureSlotBinding implements ViewBinding {
    public final ImageButton btnClearTexture;
    public final Button btnSelectTexture;
    private final LinearLayout rootView;
    public final TextView textTexturePath;

    private InspectorTextureSlotBinding(LinearLayout rootView, ImageButton btnClearTexture, Button btnSelectTexture, TextView textTexturePath) {
        this.rootView = rootView;
        this.btnClearTexture = btnClearTexture;
        this.btnSelectTexture = btnSelectTexture;
        this.textTexturePath = textTexturePath;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorTextureSlotBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorTextureSlotBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_texture_slot, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorTextureSlotBinding bind(View rootView) {
        int i = R.id.btn_clear_texture;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_clear_texture);
        if (imageButton != null) {
            i = R.id.btn_select_texture;
            Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_select_texture);
            if (button != null) {
                i = R.id.text_texture_path;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_texture_path);
                if (textView != null) {
                    return new InspectorTextureSlotBinding((LinearLayout) rootView, imageButton, button, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
