package org.catrobat.catroid.libraryeditor.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.os.BundleKt;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.libraryeditor.data.EditableFormula;
import org.catrobat.catroid.libraryeditor.data.EditableParam;
import org.catrobat.catroid.libraryeditor.data.LibraryDraft;
import org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/ui/FormulaEditDialogFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: FormulaEditDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J \u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0019H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/ui/FormulaEditDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "formulaToEdit", "Lorg/catrobat/catroid/libraryeditor/data/EditableFormula;", "inflater", "Landroid/view/LayoutInflater;", "paramsContainer", "Landroid/widget/LinearLayout;", "viewModel", "Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "getViewModel", "()Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "addParamView", "", "param", "Lorg/catrobat/catroid/libraryeditor/data/EditableParam;", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onSaveClicked", "idEdit", "Landroid/widget/EditText;", "displayNameEdit", "functionNameEdit", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FormulaEditDialogFragment extends DialogFragment {
    private static final String ARG_FORMULA_ID = "formula_id";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private EditableFormula formulaToEdit;
    private LayoutInflater inflater;
    private LinearLayout paramsContainer;

    /* JADX INFO: renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    public FormulaEditDialogFragment() {
        final FormulaEditDialogFragment formulaEditDialogFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(formulaEditDialogFragment, Reflection.getOrCreateKotlinClass(LibraryEditorViewModel.class), new Function0<ViewModelStore>() { // from class: org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = formulaEditDialogFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment$special$$inlined$activityViewModels$default$2
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
                CreationExtras defaultViewModelCreationExtras = formulaEditDialogFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = formulaEditDialogFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    private final LibraryEditorViewModel getViewModel() {
        return (LibraryEditorViewModel) this.viewModel.getValue();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/ui/FormulaEditDialogFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: FormulaEditDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/ui/FormulaEditDialogFragment$Companion;", "", "()V", "ARG_FORMULA_ID", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/catroid/libraryeditor/ui/FormulaEditDialogFragment;", "formulaId", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FormulaEditDialogFragment newInstance(String formulaId) {
            FormulaEditDialogFragment formulaEditDialogFragment = new FormulaEditDialogFragment();
            formulaEditDialogFragment.setArguments(BundleKt.bundleOf(TuplesKt.to(FormulaEditDialogFragment.ARG_FORMULA_ID, formulaId)));
            return formulaEditDialogFragment;
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EditableFormula editableFormula;
        List<EditableFormula> formulas;
        Object next;
        Bundle arguments = getArguments();
        String string = arguments != null ? arguments.getString(ARG_FORMULA_ID) : null;
        LibraryDraft value = getViewModel().getLibraryDraft().getValue();
        if (value == null || (formulas = value.getFormulas()) == null) {
            editableFormula = null;
        } else {
            Iterator<T> it = formulas.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((EditableFormula) next).getId(), string));
            editableFormula = (EditableFormula) next;
        }
        this.formulaToEdit = editableFormula;
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "getLayoutInflater(...)");
        this.inflater = layoutInflater;
        if (layoutInflater == null) {
            Intrinsics.throwUninitializedPropertyAccessException("inflater");
            layoutInflater = null;
        }
        View viewInflate = layoutInflater.inflate(R.layout.dialog_edit_formula, (ViewGroup) null);
        View viewFindViewById = viewInflate.findViewById(R.id.formula_id_edit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        final EditText editText = (EditText) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.formula_display_name_edit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        final EditText editText2 = (EditText) viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.formula_function_name_edit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        final EditText editText3 = (EditText) viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.add_param_button);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        Button button = (Button) viewFindViewById4;
        View viewFindViewById5 = viewInflate.findViewById(R.id.params_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        this.paramsContainer = (LinearLayout) viewFindViewById5;
        EditableFormula editableFormula2 = this.formulaToEdit;
        if (editableFormula2 != null) {
            editText.setText(editableFormula2.getId());
            editText2.setText(editableFormula2.getDisplayName());
            editText3.setText(editableFormula2.getFunctionName());
            Iterator<T> it2 = editableFormula2.getParams().iterator();
            while (it2.hasNext()) {
                addParamView((EditableParam) it2.next());
            }
        }
        editText.setEnabled(this.formulaToEdit == null);
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FormulaEditDialogFragment.onCreateDialog$lambda$3(this.f$0, view);
            }
        });
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext()).setTitle(getString(this.formulaToEdit == null ? R.string.libs_new_form : R.string.libs_edit_form)).setView(viewInflate).setPositiveButton(getString(R.string.libs_save), (DialogInterface.OnClickListener) null).setNegativeButton(getString(R.string.libs_cancel), (DialogInterface.OnClickListener) null).create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                FormulaEditDialogFragment.onCreateDialog$lambda$6$lambda$5(alertDialogCreate, this, editText, editText2, editText3, dialogInterface);
            }
        });
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "apply(...)");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$3(FormulaEditDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        addParamView$default(this$0, null, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$6$lambda$5(AlertDialog this_apply, final FormulaEditDialogFragment this$0, final EditText idEdit, final EditText displayNameEdit, final EditText functionNameEdit, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(idEdit, "$idEdit");
        Intrinsics.checkNotNullParameter(displayNameEdit, "$displayNameEdit");
        Intrinsics.checkNotNullParameter(functionNameEdit, "$functionNameEdit");
        this_apply.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FormulaEditDialogFragment.onCreateDialog$lambda$6$lambda$5$lambda$4(this.f$0, idEdit, displayNameEdit, functionNameEdit, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$6$lambda$5$lambda$4(FormulaEditDialogFragment this$0, EditText idEdit, EditText displayNameEdit, EditText functionNameEdit, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(idEdit, "$idEdit");
        Intrinsics.checkNotNullParameter(displayNameEdit, "$displayNameEdit");
        Intrinsics.checkNotNullParameter(functionNameEdit, "$functionNameEdit");
        this$0.onSaveClicked(idEdit, displayNameEdit, functionNameEdit);
    }

    static /* synthetic */ void addParamView$default(FormulaEditDialogFragment formulaEditDialogFragment, EditableParam editableParam, int i, Object obj) {
        if ((i & 1) != 0) {
            editableParam = null;
        }
        formulaEditDialogFragment.addParamView(editableParam);
    }

    private final void addParamView(EditableParam param) {
        LayoutInflater layoutInflater = this.inflater;
        LinearLayout linearLayout = null;
        if (layoutInflater == null) {
            Intrinsics.throwUninitializedPropertyAccessException("inflater");
            layoutInflater = null;
        }
        LinearLayout linearLayout2 = this.paramsContainer;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paramsContainer");
            linearLayout2 = null;
        }
        final View viewInflate = layoutInflater.inflate(R.layout.list_item_editable_param, (ViewGroup) linearLayout2, false);
        View viewFindViewById = viewInflate.findViewById(R.id.param_name_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        View viewFindViewById2 = viewInflate.findViewById(R.id.param_value_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        View viewFindViewById3 = viewInflate.findViewById(R.id.param_name_edit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        EditText editText = (EditText) viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.param_value_edit);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        EditText editText2 = (EditText) viewFindViewById4;
        View viewFindViewById5 = viewInflate.findViewById(R.id.delete_param_button);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        ImageButton imageButton = (ImageButton) viewFindViewById5;
        ((TextInputLayout) viewFindViewById).setHint(getString(R.string.libs_type2));
        ((TextInputLayout) viewFindViewById2).setHint(getString(R.string.libs_def));
        if (param != null) {
            editText.setText(param.getType());
            editText2.setText(param.getDefaultValue());
        }
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.ui.FormulaEditDialogFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FormulaEditDialogFragment.addParamView$lambda$8(this.f$0, viewInflate, view);
            }
        });
        LinearLayout linearLayout3 = this.paramsContainer;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paramsContainer");
        } else {
            linearLayout = linearLayout3;
        }
        linearLayout.addView(viewInflate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addParamView$lambda$8(FormulaEditDialogFragment this$0, View view, View view2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LinearLayout linearLayout = this$0.paramsContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paramsContainer");
            linearLayout = null;
        }
        linearLayout.removeView(view);
    }

    private final void onSaveClicked(EditText idEdit, EditText displayNameEdit, EditText functionNameEdit) {
        EditableFormula editableFormula;
        String string = StringsKt.trim((CharSequence) idEdit.getText().toString()).toString();
        String string2 = StringsKt.trim((CharSequence) displayNameEdit.getText().toString()).toString();
        String string3 = StringsKt.trim((CharSequence) functionNameEdit.getText().toString()).toString();
        if (string.length() == 0 || string2.length() == 0 || string3.length() == 0) {
            Toast.makeText(getContext(), getString(R.string.libs_error_n), 0).show();
            return;
        }
        if (!getViewModel().isFunctionDefined(string3)) {
            functionNameEdit.setError(getString(R.string.libs_error_f));
            return;
        }
        ArrayList arrayList = new ArrayList();
        LinearLayout linearLayout = this.paramsContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paramsContainer");
            linearLayout = null;
        }
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout linearLayout2 = this.paramsContainer;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("paramsContainer");
                linearLayout2 = null;
            }
            View childAt = linearLayout2.getChildAt(i);
            String upperCase = ((EditText) childAt.findViewById(R.id.param_name_edit)).getText().toString().toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            String string4 = ((EditText) childAt.findViewById(R.id.param_value_edit)).getText().toString();
            if (upperCase.length() > 0) {
                arrayList.add(new EditableParam(upperCase, string4, null, 4, null));
            }
        }
        EditableFormula editableFormula2 = this.formulaToEdit;
        if (editableFormula2 == null || (editableFormula = EditableFormula.copy$default(editableFormula2, null, string3, string2, arrayList, 1, null)) == null) {
            editableFormula = new EditableFormula(string, string3, string2, arrayList);
        }
        getViewModel().saveFormula(editableFormula);
        dismiss();
    }
}
