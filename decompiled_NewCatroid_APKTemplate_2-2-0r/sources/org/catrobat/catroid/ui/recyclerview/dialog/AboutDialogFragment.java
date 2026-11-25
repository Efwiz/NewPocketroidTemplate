package org.catrobat.catroid.ui.recyclerview.dialog;

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
import org.catrobat.catroid.databinding.DialogAboutBinding;
import org.catrobat.catroid.utils.Utils;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/AboutDialogFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: AboutDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/dialog/AboutDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/DialogAboutBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/DialogAboutBinding;", "onCreateDialog", "Landroid/app/Dialog;", "bundle", "Landroid/os/Bundle;", "onDestroyView", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AboutDialogFragment extends DialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "AboutDialogFragment";
    private DialogAboutBinding _binding;

    private final DialogAboutBinding getBinding() {
        DialogAboutBinding dialogAboutBinding = this._binding;
        Intrinsics.checkNotNull(dialogAboutBinding);
        return dialogAboutBinding;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        this._binding = DialogAboutBinding.inflate(LayoutInflater.from(getActivity()));
        TextView dialogAboutTextViewUrl = getBinding().dialogAboutTextViewUrl;
        Intrinsics.checkNotNullExpressionValue(dialogAboutTextViewUrl, "dialogAboutTextViewUrl");
        dialogAboutTextViewUrl.setMovementMethod(LinkMovementMethod.getInstance());
        String string = getString(R.string.about_link_template, Constants.ABOUT_POCKETCODE_LICENSE_URL, getString(R.string.dialog_about_license_link_text));
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        dialogAboutTextViewUrl.setText(Html.fromHtml(string, 0));
        TextView dialogAboutTextCatrobatUrl = getBinding().dialogAboutTextCatrobatUrl;
        Intrinsics.checkNotNullExpressionValue(dialogAboutTextCatrobatUrl, "dialogAboutTextCatrobatUrl");
        dialogAboutTextCatrobatUrl.setMovementMethod(LinkMovementMethod.getInstance());
        String string2 = getString(R.string.about_link_template, "https://t.me/NewCatroidDevs", getString(R.string.dialog_about_catrobat_link_text));
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        dialogAboutTextCatrobatUrl.setText(Html.fromHtml(string2, 0));
        TextView dialogAboutTextViewCatrobatVersionName = getBinding().dialogAboutTextViewCatrobatVersionName;
        Intrinsics.checkNotNullExpressionValue(dialogAboutTextViewCatrobatVersionName, "dialogAboutTextViewCatrobatVersionName");
        dialogAboutTextViewCatrobatVersionName.setText(getString(R.string.app_name) + " " + getString(R.string.dialog_about_version) + " " + getString(R.string.android_version_prefix) + Utils.getVersionName(getActivity()));
        TextView dialogAboutTextViewCatrobatVersionName2 = getBinding().dialogAboutTextViewCatrobatVersionName;
        Intrinsics.checkNotNullExpressionValue(dialogAboutTextViewCatrobatVersionName2, "dialogAboutTextViewCatrobatVersionName");
        dialogAboutTextViewCatrobatVersionName2.setText(getString(R.string.dialog_about_catrobat_language_version) + ": 1.18");
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireActivity()).setTitle(R.string.dialog_about_title).setView(getBinding().getRoot()).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/AboutDialogFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AboutDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/dialog/AboutDialogFragment$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return AboutDialogFragment.TAG;
        }
    }
}
