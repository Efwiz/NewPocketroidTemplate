package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentFormulasEditorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentFormulasEditorBinding implements ViewBinding {
    public final FloatingActionButton fabAddFormula;
    public final RecyclerView formulasRecyclerView;
    private final CoordinatorLayout rootView;

    private FragmentFormulasEditorBinding(CoordinatorLayout rootView, FloatingActionButton fabAddFormula, RecyclerView formulasRecyclerView) {
        this.rootView = rootView;
        this.fabAddFormula = fabAddFormula;
        this.formulasRecyclerView = formulasRecyclerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CoordinatorLayout getRoot() {
        return this.rootView;
    }

    public static FragmentFormulasEditorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentFormulasEditorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_formulas_editor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentFormulasEditorBinding bind(View rootView) {
        int i = R.id.fab_add_formula;
        FloatingActionButton floatingActionButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, R.id.fab_add_formula);
        if (floatingActionButton != null) {
            i = R.id.formulas_recycler_view;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.formulas_recycler_view);
            if (recyclerView != null) {
                return new FragmentFormulasEditorBinding((CoordinatorLayout) rootView, floatingActionButton, recyclerView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
