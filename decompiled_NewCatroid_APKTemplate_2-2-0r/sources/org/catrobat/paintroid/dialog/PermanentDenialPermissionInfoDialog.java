package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.os.BundleKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: PermanentDenialPermissionInfoDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/dialog/PermanentDenialPermissionInfoDialog;", "Landroidx/appcompat/app/AppCompatDialogFragment;", "()V", "context", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PermanentDenialPermissionInfoDialog extends AppCompatDialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String context;

    @JvmStatic
    public static final PermanentDenialPermissionInfoDialog newInstance(String str) {
        return INSTANCE.newInstance(str);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireArguments().getString("context");
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog).setMessage(R.string.permission_info_permanent_denial_text).setPositiveButton(R.string.dialog_settings, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.PermanentDenialPermissionInfoDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PermanentDenialPermissionInfoDialog.m5947onCreateDialog$lambda0(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireContext()…ll)\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-0, reason: not valid java name */
    public static final void m5947onCreateDialog$lambda0(PermanentDenialPermissionInfoDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse(Intrinsics.stringPlus("package:", this$0.context))));
    }

    /* JADX INFO: compiled from: PermanentDenialPermissionInfoDialog.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/dialog/PermanentDenialPermissionInfoDialog$Companion;", "", "()V", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/paintroid/dialog/PermanentDenialPermissionInfoDialog;", "context", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final PermanentDenialPermissionInfoDialog newInstance(String context) {
            Intrinsics.checkNotNullParameter(context, "context");
            PermanentDenialPermissionInfoDialog permanentDenialPermissionInfoDialog = new PermanentDenialPermissionInfoDialog();
            permanentDenialPermissionInfoDialog.setArguments(BundleKt.bundleOf(TuplesKt.to("context", context)));
            return permanentDenialPermissionInfoDialog;
        }
    }
}
