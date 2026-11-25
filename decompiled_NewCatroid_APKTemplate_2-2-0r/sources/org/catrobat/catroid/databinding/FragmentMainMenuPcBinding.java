package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentMainMenuPcBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentMainMenuPcBinding implements ViewBinding {
    public final MaterialCardView btnCommunity;
    public final FloatingActionButton btnNewProjectFab;
    public final MaterialCardView btnWeb;
    public final CardView currentProjectLayout;
    public final ImageView editProject;
    public final FragmentContainerView pcProjectListContainer;
    public final ImageView playProject;
    public final ImageView projectImageView;
    private final LinearLayout rootView;

    private FragmentMainMenuPcBinding(LinearLayout rootView, MaterialCardView btnCommunity, FloatingActionButton btnNewProjectFab, MaterialCardView btnWeb, CardView currentProjectLayout, ImageView editProject, FragmentContainerView pcProjectListContainer, ImageView playProject, ImageView projectImageView) {
        this.rootView = rootView;
        this.btnCommunity = btnCommunity;
        this.btnNewProjectFab = btnNewProjectFab;
        this.btnWeb = btnWeb;
        this.currentProjectLayout = currentProjectLayout;
        this.editProject = editProject;
        this.pcProjectListContainer = pcProjectListContainer;
        this.playProject = playProject;
        this.projectImageView = projectImageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentMainMenuPcBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentMainMenuPcBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_main_menu_pc, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentMainMenuPcBinding bind(View rootView) {
        int i = R.id.btnCommunity;
        MaterialCardView materialCardView = (MaterialCardView) ViewBindings.findChildViewById(rootView, R.id.btnCommunity);
        if (materialCardView != null) {
            i = R.id.btnNewProjectFab;
            FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.btnNewProjectFab);
            if (floatingActionButton != null) {
                i = R.id.btnWeb;
                MaterialCardView materialCardView2 = (MaterialCardView) ViewBindings.findChildViewById(rootView, R.id.btnWeb);
                if (materialCardView2 != null) {
                    i = R.id.currentProjectLayout;
                    CardView cardView = (CardView) ViewBindings.findChildViewById(rootView, R.id.currentProjectLayout);
                    if (cardView != null) {
                        i = R.id.editProject;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.editProject);
                        if (imageView != null) {
                            i = R.id.pc_project_list_container;
                            FragmentContainerView fragmentContainerView = (FragmentContainerView) ViewBindings.findChildViewById(rootView, R.id.pc_project_list_container);
                            if (fragmentContainerView != null) {
                                i = R.id.playProject;
                                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.playProject);
                                if (imageView2 != null) {
                                    i = R.id.projectImageView;
                                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.projectImageView);
                                    if (imageView3 != null) {
                                        return new FragmentMainMenuPcBinding((LinearLayout) rootView, materialCardView, floatingActionButton, materialCardView2, cardView, imageView, fragmentContainerView, imageView2, imageView3);
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
