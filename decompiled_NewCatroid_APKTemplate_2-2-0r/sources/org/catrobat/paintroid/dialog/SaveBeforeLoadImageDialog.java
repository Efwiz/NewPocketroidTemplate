package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: SaveBeforeLoadImageDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/dialog/SaveBeforeLoadImageDialog;", "Lorg/catrobat/paintroid/dialog/MainActivityDialogFragment;", "()V", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SaveBeforeLoadImageDialog extends MainActivityDialogFragment {
    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireActivity(), R.style.PocketPaintAlertDialog).setTitle(R.string.menu_load_image).setMessage(R.string.dialog_warning_new_image).setPositiveButton(R.string.save_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.SaveBeforeLoadImageDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SaveBeforeLoadImageDialog.m5956onCreateDialog$lambda0(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.discard_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.SaveBeforeLoadImageDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SaveBeforeLoadImageDialog.m5957onCreateDialog$lambda1(this.f$0, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireActivity(…) }\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-0, reason: not valid java name */
    public static final void m5956onCreateDialog$lambda0(SaveBeforeLoadImageDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getPresenter().saveBeforeLoadImage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-1, reason: not valid java name */
    public static final void m5957onCreateDialog$lambda1(SaveBeforeLoadImageDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getPresenter().loadNewImage();
    }
}
