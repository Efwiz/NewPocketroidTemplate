package org.catrobat.catroid.libraryeditor.data;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/FormulasEditorFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: FormulasEditorFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u001a\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\u0018\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/FormulasEditorFragment;", "Landroidx/fragment/app/Fragment;", "()V", "adapter", "Lorg/catrobat/catroid/libraryeditor/data/FormulaAdapter;", "viewModel", "Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "getViewModel", "()Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", Promotion.ACTION_VIEW, "setupAdapter", "showDeleteConfirmationDialog", "formulaId", "", "name", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FormulasEditorFragment extends Fragment {
    private FormulaAdapter adapter;

    /* JADX INFO: renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    public FormulasEditorFragment() {
        final FormulasEditorFragment formulasEditorFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(formulasEditorFragment, Reflection.getOrCreateKotlinClass(LibraryEditorViewModel.class), new Function0<ViewModelStore>() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = formulasEditorFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment$special$$inlined$activityViewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                if (function1 != null && (creationExtras = (CreationExtras) function1.invoke()) != null) {
                    return creationExtras;
                }
                CreationExtras defaultViewModelCreationExtras = formulasEditorFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = formulasEditorFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    private final LibraryEditorViewModel getViewModel() {
        return (LibraryEditorViewModel) this.viewModel.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_formulas_editor, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        View viewFindViewById = view.findViewById(R.id.formulas_recycler_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = view.findViewById(R.id.fab_add_formula);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        FloatingActionButton floatingActionButton = (FloatingActionButton) viewFindViewById2;
        setupAdapter();
        FormulaAdapter formulaAdapter = this.adapter;
        if (formulaAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            formulaAdapter = null;
        }
        recyclerView.setAdapter(formulaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        getViewModel().getLibraryDraft().observe(getViewLifecycleOwner(), new FormulasEditorFragment$sam$androidx_lifecycle_Observer$0(new Function1<LibraryDraft, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment.onViewCreated.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LibraryDraft libraryDraft) {
                invoke2(libraryDraft);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LibraryDraft libraryDraft) {
                FormulaAdapter formulaAdapter2 = FormulasEditorFragment.this.adapter;
                if (formulaAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    formulaAdapter2 = null;
                }
                formulaAdapter2.submitList(CollectionsKt.toList(libraryDraft.getFormulas()));
            }
        }));
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FormulasEditorFragment.onViewCreated$lambda$0(this.f$0, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(FormulasEditorFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FormulaEditDialogFragment.INSTANCE.newInstance(null).show(this$0.getChildFragmentManager(), "EDIT_FORMULA_DIALOG");
    }

    private final void setupAdapter() {
        this.adapter = new FormulaAdapter(new Function1<EditableFormula, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment.setupAdapter.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(EditableFormula editableFormula) {
                invoke2(editableFormula);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(EditableFormula formula) {
                Intrinsics.checkNotNullParameter(formula, "formula");
                FormulaEditDialogFragment.INSTANCE.newInstance(formula.getId()).show(FormulasEditorFragment.this.getChildFragmentManager(), "EDIT_FORMULA_DIALOG");
            }
        }, new Function1<EditableFormula, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment.setupAdapter.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(EditableFormula editableFormula) {
                invoke2(editableFormula);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(EditableFormula formula) {
                Intrinsics.checkNotNullParameter(formula, "formula");
                FormulasEditorFragment.this.showDeleteConfirmationDialog(formula.getId(), formula.getDisplayName());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDeleteConfirmationDialog(final String formulaId, String name2) {
        new AlertDialog.Builder(requireContext()).setTitle(getString(R.string.libs_delete_wf)).setMessage(getString(R.string.libs_delete_w2) + " '" + name2 + "'?").setPositiveButton(getString(R.string.libs_delete), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.data.FormulasEditorFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FormulasEditorFragment.showDeleteConfirmationDialog$lambda$1(this.f$0, formulaId, dialogInterface, i);
            }
        }).setNegativeButton(getString(R.string.libs_cancel), (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteConfirmationDialog$lambda$1(FormulasEditorFragment this$0, String formulaId, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(formulaId, "$formulaId");
        this$0.getViewModel().deleteFormula(formulaId);
    }
}
