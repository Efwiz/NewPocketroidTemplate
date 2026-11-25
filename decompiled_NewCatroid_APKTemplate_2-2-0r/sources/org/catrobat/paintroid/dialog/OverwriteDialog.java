package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.os.BundleKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: OverwriteDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/dialog/OverwriteDialog;", "Lorg/catrobat/paintroid/dialog/MainActivityDialogFragment;", "()V", OverwriteDialog.IS_EXPORT, "", OverwriteDialog.PERMISSION, "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class OverwriteDialog extends MainActivityDialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String IS_EXPORT = "isExport";
    private static final String PERMISSION = "permission";
    private boolean isExport;
    private int permission;

    /* JADX INFO: compiled from: OverwriteDialog.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FileIO.FileType.values().length];
            iArr[FileIO.FileType.JPG.ordinal()] = 1;
            iArr[FileIO.FileType.PNG.ordinal()] = 2;
            iArr[FileIO.FileType.CATROBAT.ordinal()] = 3;
            iArr[FileIO.FileType.ORA.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleRequireArguments = requireArguments();
        Intrinsics.checkNotNullExpressionValue(bundleRequireArguments, "requireArguments()");
        this.isExport = bundleRequireArguments.getBoolean(IS_EXPORT);
        this.permission = bundleRequireArguments.getInt(PERMISSION);
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog).setTitle(R.string.pocketpaint_overwrite_title).setMessage(getResources().getString(R.string.pocketpaint_overwrite, getString(R.string.menu_save_copy))).setPositiveButton(R.string.overwrite_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.OverwriteDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                OverwriteDialog.m5944onCreateDialog$lambda0(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.OverwriteDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                OverwriteDialog.m5945onCreateDialog$lambda1(this.f$0, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireContext()…) }\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-0, reason: not valid java name */
    public static final void m5944onCreateDialog$lambda0(OverwriteDialog this$0, DialogInterface dialogInterface, int i) {
        Uri uriForFilenameInPicturesFolder;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ContentResolver resolver = this$0.requireContext().getContentResolver();
        int i2 = WhenMappings.$EnumSwitchMapping$0[FileIO.fileType.ordinal()];
        if (i2 == 1 || i2 == 2) {
            FileIO fileIO = FileIO.INSTANCE;
            String defaultFileName = FileIO.INSTANCE.getDefaultFileName();
            Intrinsics.checkNotNullExpressionValue(resolver, "resolver");
            uriForFilenameInPicturesFolder = fileIO.getUriForFilenameInPicturesFolder(defaultFileName, resolver);
        } else {
            if (i2 != 3 && i2 != 4) {
                throw new NoWhenBranchMatchedException();
            }
            FileIO fileIO2 = FileIO.INSTANCE;
            String defaultFileName2 = FileIO.INSTANCE.getDefaultFileName();
            Intrinsics.checkNotNullExpressionValue(resolver, "resolver");
            uriForFilenameInPicturesFolder = fileIO2.getUriForFilenameInDownloadsFolder(defaultFileName2, resolver);
        }
        FileIO fileIO3 = FileIO.INSTANCE;
        FileIO.storeImageUri = uriForFilenameInPicturesFolder;
        this$0.getPresenter().switchBetweenVersions(this$0.permission, this$0.isExport);
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-1, reason: not valid java name */
    public static final void m5945onCreateDialog$lambda1(OverwriteDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: compiled from: OverwriteDialog.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/dialog/OverwriteDialog$Companion;", "", "()V", "IS_EXPORT", "", "PERMISSION", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/paintroid/dialog/OverwriteDialog;", "permissionCode", "", OverwriteDialog.IS_EXPORT, "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final OverwriteDialog newInstance(int permissionCode, boolean isExport) {
            OverwriteDialog overwriteDialog = new OverwriteDialog();
            overwriteDialog.setArguments(BundleKt.bundleOf(TuplesKt.to(OverwriteDialog.PERMISSION, Integer.valueOf(permissionCode)), TuplesKt.to(OverwriteDialog.IS_EXPORT, Boolean.valueOf(isExport))));
            return overwriteDialog;
        }
    }
}
