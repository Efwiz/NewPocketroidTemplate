package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.databinding.DialogTermsOfUseBinding;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/TermsOfUseDialogFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: TermsOfUseDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/dialogs/TermsOfUseDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/DialogTermsOfUseBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/DialogTermsOfUseBinding;", "onCreateDialog", "Landroid/app/Dialog;", "bundle", "Landroid/os/Bundle;", "onDestroyView", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TermsOfUseDialogFragment extends DialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TermsOfUseDialogFragment";
    private DialogTermsOfUseBinding _binding;

    private final DialogTermsOfUseBinding getBinding() {
        DialogTermsOfUseBinding dialogTermsOfUseBinding = this._binding;
        Intrinsics.checkNotNull(dialogTermsOfUseBinding);
        return dialogTermsOfUseBinding;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        this._binding = DialogTermsOfUseBinding.inflate(LayoutInflater.from(getActivity()));
        TextView dialogTermsOfUseTextViewInfo = getBinding().dialogTermsOfUseTextViewInfo;
        Intrinsics.checkNotNullExpressionValue(dialogTermsOfUseTextViewInfo, "dialogTermsOfUseTextViewInfo");
        TextView dialogTermsOfUseTextViewUrl = getBinding().dialogTermsOfUseTextViewUrl;
        Intrinsics.checkNotNullExpressionValue(dialogTermsOfUseTextViewUrl, "dialogTermsOfUseTextViewUrl");
        dialogTermsOfUseTextViewUrl.setMovementMethod(LinkMovementMethod.getInstance());
        String string = getString(R.string.dialog_terms_of_use_link_text);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        AlertDialog.Builder title = new AlertDialog.Builder(requireActivity()).setView(getBinding().getRoot()).setTitle(R.string.dialog_terms_of_use_title);
        title.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.TermsOfUseDialogFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogTermsOfUseTextViewInfo.setText(R.string.dialog_terms_of_use_info);
        String string2 = getString(R.string.terms_of_use_link_template, Constants.CATROBAT_TERMS_OF_USE_URL, string);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        dialogTermsOfUseTextViewUrl.setText(Html.fromHtml(string2, 0));
        AlertDialog alertDialogCreate = title.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        alertDialogCreate.setCanceledOnTouchOutside(true);
        return alertDialogCreate;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/TermsOfUseDialogFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: TermsOfUseDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/ui/dialogs/TermsOfUseDialogFragment$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return TermsOfUseDialogFragment.TAG;
        }
    }
}
