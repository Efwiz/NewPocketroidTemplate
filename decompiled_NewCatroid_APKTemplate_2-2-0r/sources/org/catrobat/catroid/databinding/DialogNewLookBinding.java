package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNewLookBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNewLookBinding implements ViewBinding {
    public final TextView dialogNewLookCamera;
    public final TextView dialogNewLookGallery;
    public final TextView dialogNewLookMediaLibrary;
    public final TextView dialogNewLookPaintroid;
    private final ScrollView rootView;

    private DialogNewLookBinding(ScrollView rootView, TextView dialogNewLookCamera, TextView dialogNewLookGallery, TextView dialogNewLookMediaLibrary, TextView dialogNewLookPaintroid) {
        this.rootView = rootView;
        this.dialogNewLookCamera = dialogNewLookCamera;
        this.dialogNewLookGallery = dialogNewLookGallery;
        this.dialogNewLookMediaLibrary = dialogNewLookMediaLibrary;
        this.dialogNewLookPaintroid = dialogNewLookPaintroid;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogNewLookBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNewLookBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_new_look, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNewLookBinding bind(View rootView) {
        int i = R.id.dialog_new_look_camera;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_camera);
        if (textView != null) {
            i = R.id.dialog_new_look_gallery;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_gallery);
            if (textView2 != null) {
                i = R.id.dialog_new_look_media_library;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_media_library);
                if (textView3 != null) {
                    i = R.id.dialog_new_look_paintroid;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_paintroid);
                    if (textView4 != null) {
                        return new DialogNewLookBinding((ScrollView) rootView, textView, textView2, textView3, textView4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
