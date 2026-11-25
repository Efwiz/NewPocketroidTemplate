package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.Constants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: AppStoreDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment$Companion$Extension;", "getCorrectUrl", "", "onCreateDialog", "Landroid/app/Dialog;", "bundle", "Landroid/os/Bundle;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AppStoreDialogFragment extends DialogFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private Companion.Extension extension;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AppStoreDialogFragment.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Companion.Extension.values().length];
            try {
                iArr[Companion.Extension.LEGO_NXT_EV3.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Companion.Extension.EMBROIDERY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Companion.Extension.PHIRO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @JvmStatic
    public static final AppStoreDialogFragment newInstance(Companion.Extension extension) {
        return INSTANCE.newInstance(extension);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AppStoreDialogFragment.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment$Companion$Extension;", "Extension", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment$Companion$Extension.class
          classes14.dex
         */
        /* JADX INFO: compiled from: AppStoreDialogFragment.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/dialog/AppStoreDialogFragment$Companion$Extension;", "", "(Ljava/lang/String;I)V", "LEGO_NXT_EV3", "PHIRO", "EMBROIDERY", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum Extension {
            LEGO_NXT_EV3,
            PHIRO,
            EMBROIDERY;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<Extension> getEntries() {
                return $ENTRIES;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return AppStoreDialogFragment.TAG;
        }

        @JvmStatic
        public final AppStoreDialogFragment newInstance(Extension extension) {
            Intrinsics.checkNotNullParameter(extension, "extension");
            AppStoreDialogFragment appStoreDialogFragment = new AppStoreDialogFragment();
            appStoreDialogFragment.extension = extension;
            return appStoreDialogFragment;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("AppStoreDialogFragment", "getSimpleName(...)");
        TAG = "AppStoreDialogFragment";
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        String string;
        String string2;
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_google_play, null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.dialog_google_play_text);
        Companion.Extension extension = this.extension;
        if (extension == null) {
            Intrinsics.throwUninitializedPropertyAccessException(SchemaSymbols.ATTVAL_EXTENSION);
            extension = null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[extension.ordinal()];
        if (i == 1) {
            string = getString(R.string.preference_lego_dialog_text);
        } else if (i == 2) {
            string = getString(R.string.preference_embroidery_dialog_text);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            string = getString(R.string.preference_phiro_dialog_text);
        }
        textView.setText(string);
        AlertDialog.Builder negativeButton = new AlertDialog.Builder(requireActivity()).setTitle(getString(R.string.preference_dialog_use_full_features)).setView(viewInflate).setNegativeButton(R.string.cancel_button_text, (DialogInterface.OnClickListener) null);
        Intrinsics.checkNotNullExpressionValue(negativeButton, "setNegativeButton(...)");
        if (!Intrinsics.areEqual(Build.BRAND, Constants.DEVICE_BRAND_HUAWEI)) {
            string2 = getString(R.string.preference_dialog_google_play);
        } else {
            string2 = getString(R.string.preference_dialog_appgallery);
        }
        Intrinsics.checkNotNull(string2);
        negativeButton.setPositiveButton(string2, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.AppStoreDialogFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                AppStoreDialogFragment.onCreateDialog$lambda$0(this.f$0, dialogInterface, i2);
            }
        });
        AlertDialog alertDialogCreate = negativeButton.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$0(AppStoreDialogFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this$0.getCorrectUrl()));
        this$0.startActivity(intent);
    }

    private final String getCorrectUrl() {
        Companion.Extension extension = null;
        if (!Intrinsics.areEqual(Build.BRAND, Constants.DEVICE_BRAND_HUAWEI)) {
            Companion.Extension extension2 = this.extension;
            if (extension2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(SchemaSymbols.ATTVAL_EXTENSION);
            } else {
                extension = extension2;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[extension.ordinal()];
            if (i == 1) {
                return Constants.PREFRENCE_PLAYSTORE_MINDSTORMS_URL;
            }
            if (i == 2) {
                return Constants.PREFRENCE_PLAYSTORE_EMBROIDERY_URL;
            }
            if (i == 3) {
                return Constants.PREFRENCE_PLAYSTORE_PHIRO_URL;
            }
            throw new NoWhenBranchMatchedException();
        }
        Companion.Extension extension3 = this.extension;
        if (extension3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(SchemaSymbols.ATTVAL_EXTENSION);
        } else {
            extension = extension3;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[extension.ordinal()];
        if (i2 == 1 || i2 == 2 || i2 == 3) {
            return "https://appgallery.huawei.com/app/C100085769";
        }
        throw new NoWhenBranchMatchedException();
    }
}
