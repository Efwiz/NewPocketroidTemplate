package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.os.BundleKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: InfoDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000e2\u00020\u00012\u00020\u0002:\u0002\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/dialog/InfoDialog;", "Landroidx/appcompat/app/AppCompatDialogFragment;", "Landroid/content/DialogInterface$OnClickListener;", "()V", "onClick", "", "dialog", "Landroid/content/DialogInterface;", "which", "", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "DialogType", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class InfoDialog extends AppCompatDialogFragment implements DialogInterface.OnClickListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog);
        Bundle arguments = getArguments();
        if (arguments != null) {
            builder.setTitle(arguments.getInt("titleResource")).setIcon(arguments.getInt("drawableResource")).setMessage(arguments.getInt("messageResource"));
        }
        builder.setPositiveButton(android.R.string.ok, this);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "builder.create()");
        return alertDialogCreate;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        dialog.cancel();
    }

    /* JADX INFO: compiled from: InfoDialog.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/dialog/InfoDialog$DialogType;", "", "imageResource", "", "(Ljava/lang/String;II)V", "getImageResource", "()I", "INFO", "WARNING", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum DialogType {
        INFO(R.drawable.ic_pocketpaint_dialog_info),
        WARNING(R.drawable.ic_pocketpaint_dialog_warning);

        private final int imageResource;

        DialogType(int i) {
            this.imageResource = i;
        }

        public final int getImageResource() {
            return this.imageResource;
        }
    }

    /* JADX INFO: compiled from: InfoDialog.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\b¨\u0006\n"}, d2 = {"Lorg/catrobat/paintroid/dialog/InfoDialog$Companion;", "", "()V", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/paintroid/dialog/InfoDialog;", "dialogType", "Lorg/catrobat/paintroid/dialog/InfoDialog$DialogType;", "messageResource", "", "titleResource", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final InfoDialog newInstance(DialogType dialogType, int messageResource, int titleResource) {
            Intrinsics.checkNotNullParameter(dialogType, "dialogType");
            InfoDialog infoDialog = new InfoDialog();
            infoDialog.setArguments(BundleKt.bundleOf(TuplesKt.to("drawableResource", Integer.valueOf(dialogType.getImageResource())), TuplesKt.to("messageResource", Integer.valueOf(messageResource)), TuplesKt.to("titleResource", Integer.valueOf(titleResource))));
            return infoDialog;
        }
    }
}
