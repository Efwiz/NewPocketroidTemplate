package android.preference;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import androidx.core.content.ContextCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.TrustedDomainManager;
import org.catrobat.catroid.common.Constants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:android/preference/TrustListEditorPreference.class
  classes.dex
 */
/* JADX INFO: compiled from: TrustListEditorPreference.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0012\u0010\r\u001a\u00020\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroid/preference/TrustListEditorPreference;", "Landroid/preference/EditTextPreference;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "neutralButtonText", "", "onBindDialogView", "", Promotion.ACTION_VIEW, "Landroid/view/View;", "showDialog", "state", "Landroid/os/Bundle;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrustListEditorPreference extends EditTextPreference {
    private final String neutralButtonText;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrustListEditorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        String string = context.getString(R.string.brick_context_dialog_help);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.neutralButtonText = string;
        setDialogTitle(R.string.preference_screen_web_access_title);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0066  */
    @Override // android.preference.EditTextPreference, android.preference.DialogPreference
    protected void showDialog(Bundle state) {
        AlertDialog.Builder onDismissListener = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), 2131952355)).setTitle(getDialogTitle()).setIcon(getDialogIcon()).setPositiveButton(getPositiveButtonText(), new DialogInterface.OnClickListener() { // from class: android.preference.TrustListEditorPreference$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TrustListEditorPreference.showDialog$lambda$0(this.f$0, dialogInterface, i);
            }
        }).setNeutralButton(this.neutralButtonText, (DialogInterface.OnClickListener) null).setNegativeButton(getNegativeButtonText(), this).setOnDismissListener(this);
        View viewInflate = LayoutInflater.from(onDismissListener.getContext()).inflate(getDialogLayoutResource(), (ViewGroup) null);
        if (viewInflate != null) {
            onBindDialogView(viewInflate);
            if (onDismissListener.setView(viewInflate) == null) {
                onDismissListener.setMessage(getDialogMessage());
            }
        } else {
            onDismissListener.setMessage(getDialogMessage());
        }
        onPrepareDialogBuilder(onDismissListener);
        final AlertDialog alertDialogCreate = onDismissListener.create();
        if (state != null) {
            alertDialogCreate.onRestoreInstanceState(state);
        }
        Window window = alertDialogCreate.getWindow();
        if (window != null) {
            window.setSoftInputMode(5);
        }
        getEditText().requestFocus();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: android.preference.TrustListEditorPreference$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                TrustListEditorPreference.showDialog$lambda$6$lambda$5(alertDialogCreate, dialogInterface);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialog$lambda$0(TrustListEditorPreference this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setText(this$0.getEditText().getText().toString());
        TrustedDomainManager trustedDomainManager = TrustedDomainManager.INSTANCE;
        String text = this$0.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        trustedDomainManager.setUserTrustList(text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialog$lambda$6$lambda$5(final AlertDialog alertDialog, DialogInterface dialogInterface) {
        alertDialog.getButton(-3).setOnClickListener(new View.OnClickListener() { // from class: android.preference.TrustListEditorPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TrustListEditorPreference.showDialog$lambda$6$lambda$5$lambda$4(alertDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialog$lambda$6$lambda$5$lambda$4(AlertDialog alertDialog, View view) {
        ContextCompat.startActivity(alertDialog.getContext(), new Intent("android.intent.action.VIEW", Uri.parse(Constants.WEB_REQUEST_WIKI_URL)), null);
    }

    @Override // android.preference.EditTextPreference, android.preference.DialogPreference
    protected void onBindDialogView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onBindDialogView(view);
        setText(TrustedDomainManager.INSTANCE.getUserTrustList());
        getEditText().setText(getText());
        getEditText().setSelection(getText().length());
        ViewParent parent = getEditText().getParent();
        if (parent != view) {
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(getEditText());
            }
            onAddEditTextToDialogView(view, getEditText());
        }
    }
}
