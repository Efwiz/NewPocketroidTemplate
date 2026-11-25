package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.os.BundleKt;
import androidx.fragment.app.FragmentActivity;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: PermissionInfoDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0002\u0011\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.¢\u0006\u0004\n\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/dialog/PermissionInfoDialog;", "Landroidx/appcompat/app/AppCompatDialogFragment;", "()V", "permissionType", "Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;", "permissions", "", "", "[Ljava/lang/String;", "requestCode", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "Companion", "PermissionType", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PermissionInfoDialog extends AppCompatDialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private PermissionType permissionType;
    private String[] permissions;
    private int requestCode;

    @JvmStatic
    public static final PermissionInfoDialog newInstance(PermissionType permissionType, String[] strArr, int i) {
        return INSTANCE.newInstance(permissionType, strArr, i);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleRequireArguments = requireArguments();
        this.requestCode = bundleRequireArguments.getInt("requestCodeKey");
        String[] stringArray = bundleRequireArguments.getStringArray("permissionsKey");
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
        }
        this.permissions = stringArray;
        Serializable serializable = bundleRequireArguments.getSerializable("permissionTypeKey");
        if (serializable == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.dialog.PermissionInfoDialog.PermissionType");
        }
        this.permissionType = (PermissionType) serializable;
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog);
        PermissionType permissionType = this.permissionType;
        if (permissionType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("permissionType");
            permissionType = null;
        }
        AlertDialog.Builder icon = builder.setIcon(permissionType.getIconResource());
        PermissionType permissionType2 = this.permissionType;
        if (permissionType2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("permissionType");
            permissionType2 = null;
        }
        AlertDialog alertDialogCreate = icon.setMessage(permissionType2.getMessageResource()).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.PermissionInfoDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PermissionInfoDialog.m5948onCreateDialog$lambda1(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireContext()…ll)\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-1, reason: not valid java name */
    public static final void m5948onCreateDialog$lambda1(PermissionInfoDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity fragmentActivityRequireActivity = this$0.requireActivity();
        String[] strArr = this$0.permissions;
        if (strArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("permissions");
            strArr = null;
        }
        ActivityCompat.requestPermissions(fragmentActivityRequireActivity, strArr, this$0.requestCode);
    }

    /* JADX INFO: compiled from: PermissionInfoDialog.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001b\b\u0002\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;", "", "iconResource", "", "messageResource", "(Ljava/lang/String;III)V", "getIconResource", "()I", "getMessageResource", "EXTERNAL_STORAGE", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum PermissionType {
        EXTERNAL_STORAGE(R.drawable.ic_pocketpaint_dialog_info, R.string.permission_info_external_storage_text);

        private final int iconResource;
        private final int messageResource;

        PermissionType(int i, int i2) {
            this.iconResource = i;
            this.messageResource = i2;
        }

        public final int getIconResource() {
            return this.iconResource;
        }

        public final int getMessageResource() {
            return this.messageResource;
        }
    }

    /* JADX INFO: compiled from: PermissionInfoDialog.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$Companion;", "", "()V", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/paintroid/dialog/PermissionInfoDialog;", "permissionType", "Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;", "permissions", "", "", "requestCode", "", "(Lorg/catrobat/paintroid/dialog/PermissionInfoDialog$PermissionType;[Ljava/lang/String;I)Lorg/catrobat/paintroid/dialog/PermissionInfoDialog;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final PermissionInfoDialog newInstance(PermissionType permissionType, String[] permissions, int requestCode) {
            Intrinsics.checkNotNullParameter(permissionType, "permissionType");
            Intrinsics.checkNotNullParameter(permissions, "permissions");
            PermissionInfoDialog permissionInfoDialog = new PermissionInfoDialog();
            permissionInfoDialog.setArguments(BundleKt.bundleOf(TuplesKt.to("permissionTypeKey", permissionType), TuplesKt.to("permissionsKey", permissions), TuplesKt.to("requestCodeKey", Integer.valueOf(requestCode))));
            return permissionInfoDialog;
        }
    }
}
