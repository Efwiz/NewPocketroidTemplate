package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.os.BundleKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: ScaleImageOnLoadDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/dialog/ScaleImageOnLoadDialog;", "Lorg/catrobat/paintroid/dialog/MainActivityDialogFragment;", "()V", "requestCode", "", "uri", "Landroid/net/Uri;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ScaleImageOnLoadDialog extends MainActivityDialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int requestCode;
    private Uri uri;

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleRequireArguments = requireArguments();
        Intrinsics.checkNotNullExpressionValue(bundleRequireArguments, "requireArguments()");
        this.uri = Uri.parse(bundleRequireArguments.getString("Uri"));
        this.requestCode = bundleRequireArguments.getInt("requestCode");
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireActivity(), R.style.PocketPaintAlertDialog).setTitle(R.string.dialog_scale_title).setMessage(R.string.dialog_scale_message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.ScaleImageOnLoadDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ScaleImageOnLoadDialog.m5965onCreateDialog$lambda0(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireActivity(…ll)\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-0, reason: not valid java name */
    public static final void m5965onCreateDialog$lambda0(ScaleImageOnLoadDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getPresenter().loadScaledImage(this$0.uri, this$0.requestCode);
    }

    /* JADX INFO: compiled from: ScaleImageOnLoadDialog.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/dialog/ScaleImageOnLoadDialog$Companion;", "", "()V", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/paintroid/dialog/ScaleImageOnLoadDialog;", "uri", "Landroid/net/Uri;", "requestCode", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ScaleImageOnLoadDialog newInstance(Uri uri, int requestCode) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            ScaleImageOnLoadDialog scaleImageOnLoadDialog = new ScaleImageOnLoadDialog();
            scaleImageOnLoadDialog.setArguments(BundleKt.bundleOf(TuplesKt.to("Uri", uri.toString()), TuplesKt.to("requestCode", Integer.valueOf(requestCode))));
            return scaleImageOnLoadDialog;
        }
    }
}
