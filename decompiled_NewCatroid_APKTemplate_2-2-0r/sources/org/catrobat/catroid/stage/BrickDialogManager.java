package org.catrobat.catroid.stage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.method.LinkMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.text.HtmlCompat;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.TrustedDomainManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.actions.AskAction;
import org.catrobat.catroid.content.actions.BigAskAction;
import org.catrobat.catroid.content.actions.WebAction;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/BrickDialogManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickDialogManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002:\u00012B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J8\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0011H\u0002J\u0018\u0010\u0016\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J \u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\bH\u0002J\u0018\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0011H\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020!J\u0010\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020$H\u0016J \u0010%\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020!2\u0006\u0010#\u001a\u00020\bH\u0002J>\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u00101\u001a\u00020\u0011R2\u0010\u0006\u001a&\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b \t*\u0012\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b\u0018\u00010\n0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u00063"}, d2 = {"Lorg/catrobat/catroid/stage/BrickDialogManager;", "Landroid/content/DialogInterface$OnKeyListener;", "Landroid/content/DialogInterface$OnDismissListener;", "stageActivity", "Lorg/catrobat/catroid/stage/StageActivity;", "(Lorg/catrobat/catroid/stage/StageActivity;)V", "openDialogs", "", "Landroid/app/Dialog;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "getStageActivity", "()Lorg/catrobat/catroid/stage/StageActivity;", "createAsk2Dialog", "askAction", "Lorg/catrobat/catroid/content/actions/BigAskAction;", "question", "", NotificationCompat.CATEGORY_MESSAGE, "sumb", "canel", "baseText", "createAskDialog", "Lorg/catrobat/catroid/content/actions/AskAction;", "createTrustDomainDialog", "webAction", "Lorg/catrobat/catroid/content/actions/WebAction;", "url", "webAccessDialog", "createWebAccessDialog", "dialogIsShowing", "", "dismissAllDialogs", "", "onDismiss", "dialog", "Landroid/content/DialogInterface;", "onKey", "keyCode", "", "event", "Landroid/view/KeyEvent;", "openDialog", "showDialog", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/stage/BrickDialogManager$DialogType;", "action", "Lcom/badlogic/gdx/scenes/scene2d/Action;", FirebaseAnalytics.Param.CONTENT, "defaultValue", "DialogType", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickDialogManager implements DialogInterface.OnKeyListener, DialogInterface.OnDismissListener {
    private final List<Dialog> openDialogs;
    private final StageActivity stageActivity;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/BrickDialogManager$DialogType.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickDialogManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/stage/BrickDialogManager$DialogType;", "", "(Ljava/lang/String;I)V", "ASK_DIALOG", "WEB_ACCESS_DIALOG", "ASK_2_DIALOG", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum DialogType {
        ASK_DIALOG,
        WEB_ACCESS_DIALOG,
        ASK_2_DIALOG;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<DialogType> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/BrickDialogManager$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickDialogManager.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DialogType.values().length];
            try {
                iArr[DialogType.ASK_2_DIALOG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DialogType.ASK_DIALOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DialogType.WEB_ACCESS_DIALOG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BrickDialogManager(StageActivity stageActivity) {
        Intrinsics.checkNotNullParameter(stageActivity, "stageActivity");
        this.stageActivity = stageActivity;
        this.openDialogs = Collections.synchronizedList(new ArrayList());
    }

    public final StageActivity getStageActivity() {
        return this.stageActivity;
    }

    public final boolean dialogIsShowing() {
        List<Dialog> openDialogs = this.openDialogs;
        Intrinsics.checkNotNullExpressionValue(openDialogs, "openDialogs");
        return !openDialogs.isEmpty();
    }

    public final void dismissAllDialogs() {
        List<Dialog> openDialogs = this.openDialogs;
        Intrinsics.checkNotNullExpressionValue(openDialogs, "openDialogs");
        Iterator<T> it = openDialogs.iterator();
        while (it.hasNext()) {
            ((Dialog) it.next()).dismiss();
        }
        this.openDialogs.clear();
    }

    public final void showDialog(DialogType type, Action action, String content, String msg, String sumb, String canel, String defaultValue) {
        Dialog dialogCreateAsk2Dialog;
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Intrinsics.checkNotNullParameter(sumb, "sumb");
        Intrinsics.checkNotNullParameter(canel, "canel");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            dialogCreateAsk2Dialog = createAsk2Dialog((BigAskAction) action, content, msg, sumb, canel, defaultValue);
        } else if (i == 2) {
            dialogCreateAsk2Dialog = createAskDialog((AskAction) action, content);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            dialogCreateAsk2Dialog = createWebAccessDialog((WebAction) action, content);
        }
        openDialog(dialogCreateAsk2Dialog);
    }

    private final void openDialog(Dialog dialog) {
        StageLifeCycleController.stagePause(this.stageActivity);
        this.openDialogs.add(dialog);
        dialog.show();
    }

    private final Dialog createAsk2Dialog(final BigAskAction askAction, String question, String msg, String sumb, String canel, String baseText) {
        final EditText editText = new EditText(this.stageActivity);
        editText.setText(baseText);
        AlertDialog alertDialogCreate = new MaterialAlertDialogBuilder(this.stageActivity, 2131952355).setView((View) editText).setMessage((CharSequence) msg).setTitle((CharSequence) question).setCancelable(false).setOnKeyListener((DialogInterface.OnKeyListener) this).setOnDismissListener((DialogInterface.OnDismissListener) this).setPositiveButton((CharSequence) sumb, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createAsk2Dialog$lambda$1(askAction, editText, dialogInterface, i);
            }
        }).setNegativeButton((CharSequence) canel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createAsk2Dialog$lambda$2(askAction, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        editText.requestFocus();
        Window window = alertDialogCreate.getWindow();
        if (window != null) {
            window.setSoftInputMode(4);
        }
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAsk2Dialog$lambda$1(BigAskAction askAction, EditText editText, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(askAction, "$askAction");
        Intrinsics.checkNotNullParameter(editText, "$editText");
        askAction.setAnswerText(editText.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAsk2Dialog$lambda$2(BigAskAction askAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(askAction, "$askAction");
        askAction.setAnswerText("0");
    }

    private final Dialog createAskDialog(final AskAction askAction, String question) {
        final EditText editText = new EditText(this.stageActivity);
        AlertDialog alertDialogCreate = new MaterialAlertDialogBuilder(this.stageActivity, 2131952355).setView((View) editText).setMessage((CharSequence) this.stageActivity.getString(R.string.brick_ask_dialog_hint)).setTitle((CharSequence) question).setCancelable(false).setOnKeyListener((DialogInterface.OnKeyListener) this).setOnDismissListener((DialogInterface.OnDismissListener) this).setPositiveButton((CharSequence) this.stageActivity.getString(R.string.brick_ask_dialog_submit), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createAskDialog$lambda$3(askAction, editText, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        editText.requestFocus();
        Window window = alertDialogCreate.getWindow();
        if (window != null) {
            window.setSoftInputMode(4);
        }
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAskDialog$lambda$3(AskAction askAction, EditText editText, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(askAction, "$askAction");
        Intrinsics.checkNotNullParameter(editText, "$editText");
        askAction.setAnswerText(editText.getText().toString());
    }

    private final Dialog createWebAccessDialog(final WebAction webAction, final String url) {
        View viewInflate = LayoutInflater.from(this.stageActivity).inflate(R.layout.dialog_web_access, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.request_url)).setText(url);
        TextView textView = (TextView) viewInflate.findViewById(R.id.request_warning);
        textView.setText(HtmlCompat.fromHtml(this.stageActivity.getString(R.string.web_request_warning_message, new Object[]{Constants.WEB_REQUEST_WIKI_URL}), 0));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        AlertDialog alertDialogCreate = new MaterialAlertDialogBuilder(new ContextThemeWrapper(this.stageActivity, 2131952355)).setTitle((CharSequence) this.stageActivity.getString(R.string.web_request_warning_title)).setCancelable(false).setView(viewInflate).setOnKeyListener((DialogInterface.OnKeyListener) this).setOnDismissListener((DialogInterface.OnDismissListener) this).setPositiveButton((CharSequence) this.stageActivity.getString(R.string.once), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createWebAccessDialog$lambda$5(webAction, dialogInterface, i);
            }
        }).setNeutralButton((CharSequence) this.stageActivity.getString(R.string.always), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createWebAccessDialog$lambda$6(this.f$0, webAction, url, dialogInterface, i);
            }
        }).setNegativeButton((CharSequence) this.stageActivity.getString(R.string.deny), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createWebAccessDialog$lambda$7(webAction, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createWebAccessDialog$lambda$5(WebAction webAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(webAction, "$webAction");
        webAction.grantPermission();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createWebAccessDialog$lambda$6(BrickDialogManager this$0, WebAction webAction, String url, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(webAction, "$webAction");
        Intrinsics.checkNotNullParameter(url, "$url");
        Intrinsics.checkNotNull(dialogInterface, "null cannot be cast to non-null type android.app.Dialog");
        this$0.openDialog(this$0.createTrustDomainDialog(webAction, url, (Dialog) dialogInterface));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createWebAccessDialog$lambda$7(WebAction webAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(webAction, "$webAction");
        webAction.denyPermission();
    }

    private final Dialog createTrustDomainDialog(final WebAction webAction, String url, final Dialog webAccessDialog) {
        String host = new URI(url).getHost();
        Intrinsics.checkNotNullExpressionValue(host, "getHost(...)");
        final String strRemovePrefix = StringsKt.removePrefix(host, (CharSequence) "www.");
        View viewInflate = LayoutInflater.from(this.stageActivity).inflate(R.layout.dialog_web_access, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.request_url)).setText(strRemovePrefix);
        StringBuilder sbAppend = new StringBuilder().append(this.stageActivity.getString(R.string.web_request_warning_message, new Object[]{Constants.WEB_REQUEST_WIKI_URL})).append("<br><br>").append(this.stageActivity.getString(R.string.web_request_trust_domain_warning_message));
        TextView textView = (TextView) viewInflate.findViewById(R.id.request_warning);
        textView.setText(HtmlCompat.fromHtml(sbAppend.toString(), 0));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        AlertDialog alertDialogCreate = new MaterialAlertDialogBuilder(new ContextThemeWrapper(this.stageActivity, 2131952355)).setTitle((CharSequence) this.stageActivity.getString(R.string.web_request_trust_domain_warning_title)).setCancelable(false).setView(viewInflate).setOnKeyListener((DialogInterface.OnKeyListener) this).setOnDismissListener((DialogInterface.OnDismissListener) this).setPositiveButton((CharSequence) this.stageActivity.getString(R.string.always), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createTrustDomainDialog$lambda$9(strRemovePrefix, webAction, dialogInterface, i);
            }
        }).setNeutralButton((CharSequence) this.stageActivity.getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.BrickDialogManager$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BrickDialogManager.createTrustDomainDialog$lambda$10(this.f$0, webAccessDialog, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createTrustDomainDialog$lambda$9(String domain, WebAction webAction, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(domain, "$domain");
        Intrinsics.checkNotNullParameter(webAction, "$webAction");
        TrustedDomainManager.INSTANCE.addToUserTrustList(domain);
        webAction.grantPermission();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createTrustDomainDialog$lambda$10(BrickDialogManager this$0, Dialog webAccessDialog, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(webAccessDialog, "$webAccessDialog");
        this$0.openDialog(webAccessDialog);
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(event, "event");
        boolean z = keyCode == 4;
        if (z) {
            this.stageActivity.onBackPressed();
        }
        return z;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        this.openDialogs.remove((Dialog) dialog);
        StageLifeCycleController.stageResume(this.stageActivity);
    }
}
