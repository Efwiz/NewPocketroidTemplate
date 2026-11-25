package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewScriptFinderBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewScriptFinderBinding implements ViewBinding {
    public final ImageView close;
    public final ImageView find;
    public final ImageView findNext;
    public final ImageView findPrevious;
    public final ProgressBar progressBar;
    private final View rootView;
    public final TextView sceneAndSpriteName;
    public final EditText searchBar;
    public final TextView searchPositionIndicator;

    private ViewScriptFinderBinding(View rootView, ImageView close, ImageView find, ImageView findNext, ImageView findPrevious, ProgressBar progressBar, TextView sceneAndSpriteName, EditText searchBar, TextView searchPositionIndicator) {
        this.rootView = rootView;
        this.close = close;
        this.find = find;
        this.findNext = findNext;
        this.findPrevious = findPrevious;
        this.progressBar = progressBar;
        this.sceneAndSpriteName = sceneAndSpriteName;
        this.searchBar = searchBar;
        this.searchPositionIndicator = searchPositionIndicator;
    }

    @Override // androidx.viewbinding.ViewBinding
    public View getRoot() {
        return this.rootView;
    }

    public static ViewScriptFinderBinding inflate(LayoutInflater inflater, ViewGroup parent) {
        if (parent == null) {
            throw new NullPointerException("parent");
        }
        inflater.inflate(R.layout.view_script_finder, parent);
        return bind(parent);
    }

    public static ViewScriptFinderBinding bind(View rootView) {
        int i = R.id.close;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.close);
        if (imageView != null) {
            i = R.id.find;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.find);
            if (imageView2 != null) {
                i = R.id.find_next;
                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.find_next);
                if (imageView3 != null) {
                    i = R.id.find_previous;
                    ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.find_previous);
                    if (imageView4 != null) {
                        i = R.id.progress_bar;
                        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.progress_bar);
                        if (progressBar != null) {
                            i = R.id.scene_and_sprite_name;
                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.scene_and_sprite_name);
                            if (textView != null) {
                                i = R.id.search_bar;
                                EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.search_bar);
                                if (editText != null) {
                                    i = R.id.search_position_indicator;
                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.search_position_indicator);
                                    if (textView2 != null) {
                                        return new ViewScriptFinderBinding(rootView, imageView, imageView2, imageView3, imageView4, progressBar, textView, editText, textView2);
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
