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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNewSoundBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNewSoundBinding implements ViewBinding {
    public final TextView dialogNewSoundGallery;
    public final TextView dialogNewSoundMediaLibrary;
    public final TextView dialogNewSoundPocketmusic;
    public final TextView dialogNewSoundRecorder;
    private final ScrollView rootView;

    private DialogNewSoundBinding(ScrollView rootView, TextView dialogNewSoundGallery, TextView dialogNewSoundMediaLibrary, TextView dialogNewSoundPocketmusic, TextView dialogNewSoundRecorder) {
        this.rootView = rootView;
        this.dialogNewSoundGallery = dialogNewSoundGallery;
        this.dialogNewSoundMediaLibrary = dialogNewSoundMediaLibrary;
        this.dialogNewSoundPocketmusic = dialogNewSoundPocketmusic;
        this.dialogNewSoundRecorder = dialogNewSoundRecorder;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogNewSoundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNewSoundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_new_sound, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNewSoundBinding bind(View rootView) {
        int i = R.id.dialog_new_sound_gallery;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_sound_gallery);
        if (textView != null) {
            i = R.id.dialog_new_sound_media_library;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_sound_media_library);
            if (textView2 != null) {
                i = R.id.dialog_new_sound_pocketmusic;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_sound_pocketmusic);
                if (textView3 != null) {
                    i = R.id.dialog_new_sound_recorder;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_sound_recorder);
                    if (textView4 != null) {
                        return new DialogNewSoundBinding((ScrollView) rootView, textView, textView2, textView3, textView4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
