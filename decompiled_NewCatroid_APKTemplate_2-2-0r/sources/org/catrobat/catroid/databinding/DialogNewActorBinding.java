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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNewActorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNewActorBinding implements ViewBinding {
    public final TextView dialogNewLookBackpack;
    public final TextView dialogNewLookCamera;
    public final TextView dialogNewLookEmptyObject;
    public final TextView dialogNewLookFromActorsAndObjects;
    public final TextView dialogNewLookFromLocal;
    public final TextView dialogNewLookGallery;
    public final TextView dialogNewLookMediaLibrary;
    public final TextView dialogNewLookObjectLibrary;
    public final TextView dialogNewLookPaintroid;
    private final ScrollView rootView;

    private DialogNewActorBinding(ScrollView rootView, TextView dialogNewLookBackpack, TextView dialogNewLookCamera, TextView dialogNewLookEmptyObject, TextView dialogNewLookFromActorsAndObjects, TextView dialogNewLookFromLocal, TextView dialogNewLookGallery, TextView dialogNewLookMediaLibrary, TextView dialogNewLookObjectLibrary, TextView dialogNewLookPaintroid) {
        this.rootView = rootView;
        this.dialogNewLookBackpack = dialogNewLookBackpack;
        this.dialogNewLookCamera = dialogNewLookCamera;
        this.dialogNewLookEmptyObject = dialogNewLookEmptyObject;
        this.dialogNewLookFromActorsAndObjects = dialogNewLookFromActorsAndObjects;
        this.dialogNewLookFromLocal = dialogNewLookFromLocal;
        this.dialogNewLookGallery = dialogNewLookGallery;
        this.dialogNewLookMediaLibrary = dialogNewLookMediaLibrary;
        this.dialogNewLookObjectLibrary = dialogNewLookObjectLibrary;
        this.dialogNewLookPaintroid = dialogNewLookPaintroid;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogNewActorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNewActorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_new_actor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNewActorBinding bind(View rootView) {
        int i = R.id.dialog_new_look_backpack;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_backpack);
        if (textView != null) {
            i = R.id.dialog_new_look_camera;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_camera);
            if (textView2 != null) {
                i = R.id.dialog_new_look_empty_object;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_empty_object);
                if (textView3 != null) {
                    i = R.id.dialog_new_look_from_actors_and_objects;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_from_actors_and_objects);
                    if (textView4 != null) {
                        i = R.id.dialog_new_look_from_local;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_from_local);
                        if (textView5 != null) {
                            i = R.id.dialog_new_look_gallery;
                            TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_gallery);
                            if (textView6 != null) {
                                i = R.id.dialog_new_look_media_library;
                                TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_media_library);
                                if (textView7 != null) {
                                    i = R.id.dialog_new_look_object_library;
                                    TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_object_library);
                                    if (textView8 != null) {
                                        i = R.id.dialog_new_look_paintroid;
                                        TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_new_look_paintroid);
                                        if (textView9 != null) {
                                            return new DialogNewActorBinding((ScrollView) rootView, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9);
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
