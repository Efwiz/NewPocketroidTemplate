package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentMainMenuBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentMainMenuBinding implements ViewBinding {
    public final CardView currentProjectLayout;
    public final ImageView editProject;
    public final TextView featuredProjectsTextView;
    public final TextView featuredProjectsTextView2;
    public final RecyclerView myProjectsRecyclerView;
    public final TextView myProjectsTextView;
    public final FloatingActionButton newProjectFloatingActionButton;
    public final ImageView playProject;
    public final ImageView projectImageView;
    private final LinearLayout rootView;
    public final ImageView shadow;

    private FragmentMainMenuBinding(LinearLayout rootView, CardView currentProjectLayout, ImageView editProject, TextView featuredProjectsTextView, TextView featuredProjectsTextView2, RecyclerView myProjectsRecyclerView, TextView myProjectsTextView, FloatingActionButton newProjectFloatingActionButton, ImageView playProject, ImageView projectImageView, ImageView shadow) {
        this.rootView = rootView;
        this.currentProjectLayout = currentProjectLayout;
        this.editProject = editProject;
        this.featuredProjectsTextView = featuredProjectsTextView;
        this.featuredProjectsTextView2 = featuredProjectsTextView2;
        this.myProjectsRecyclerView = myProjectsRecyclerView;
        this.myProjectsTextView = myProjectsTextView;
        this.newProjectFloatingActionButton = newProjectFloatingActionButton;
        this.playProject = playProject;
        this.projectImageView = projectImageView;
        this.shadow = shadow;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentMainMenuBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentMainMenuBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_main_menu, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentMainMenuBinding bind(View rootView) {
        int i = R.id.currentProjectLayout;
        CardView cardView = (CardView) ViewBindings.findChildViewById(rootView, R.id.currentProjectLayout);
        if (cardView != null) {
            i = R.id.editProject;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.editProject);
            if (imageView != null) {
                i = R.id.featuredProjectsTextView;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.featuredProjectsTextView);
                if (textView != null) {
                    i = R.id.featuredProjectsTextView2;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.featuredProjectsTextView2);
                    if (textView2 != null) {
                        i = R.id.myProjectsRecyclerView;
                        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.myProjectsRecyclerView);
                        if (recyclerView != null) {
                            i = R.id.myProjectsTextView;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.myProjectsTextView);
                            if (textView3 != null) {
                                i = R.id.newProjectFloatingActionButton;
                                FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.newProjectFloatingActionButton);
                                if (floatingActionButton != null) {
                                    i = R.id.playProject;
                                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.playProject);
                                    if (imageView2 != null) {
                                        i = R.id.projectImageView;
                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.projectImageView);
                                        if (imageView3 != null) {
                                            i = R.id.shadow;
                                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.shadow);
                                            if (imageView4 != null) {
                                                return new FragmentMainMenuBinding((LinearLayout) rootView, cardView, imageView, textView, textView2, recyclerView, textView3, floatingActionButton, imageView2, imageView3, imageView4);
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
