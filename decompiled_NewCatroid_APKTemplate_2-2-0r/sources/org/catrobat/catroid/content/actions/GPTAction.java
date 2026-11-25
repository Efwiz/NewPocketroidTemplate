package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import okhttp3.Response;
import org.catrobat.catroid.TrustedDomainManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.BrickDialogManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.ErrorLog;
import org.catrobat.catroid.web.WebConnection;
import org.catrobat.catroid.web.WebConnectionHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GPTAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: GPTAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u00012\u00020\u0002:\u0002BCB\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J\b\u00101\u001a\u000202H\u0002J\b\u00103\u001a\u000202H\u0002J\u0006\u00104\u001a\u000202J\u0006\u00105\u001a\u000202J\u0010\u00106\u001a\u0002022\u0006\u00107\u001a\u00020\rH&J\b\u00108\u001a\u000202H&J\b\u00109\u001a\u00020.H\u0002J\b\u0010:\u001a\u000202H\u0017J\u0010\u0010;\u001a\u0002022\u0006\u0010<\u001a\u00020\rH\u0017J\u0010\u0010=\u001a\u0002022\u0006\u0010>\u001a\u00020?H\u0017J\b\u0010@\u001a\u000202H\u0017J\b\u0010A\u001a\u00020.H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\t\"\u0004\b \u0010\u000bR\u001c\u0010!\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u000f\"\u0004\b#\u0010\u0011R\u001c\u0010$\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u000f\"\u0004\b&\u0010\u0011R\u001c\u0010'\u001a\u0004\u0018\u00010(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,¨\u0006D"}, d2 = {"Lorg/catrobat/catroid/content/actions/GPTAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lorg/catrobat/catroid/web/WebConnection$WebRequestListener;", "()V", "permissionStatus", "Lorg/catrobat/catroid/content/actions/GPTAction$PermissionStatus;", "pformula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getPformula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setPformula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "prompt", "", "getPrompt", "()Ljava/lang/String;", "setPrompt", "(Ljava/lang/String;)V", "requestStatus", "Lorg/catrobat/catroid/content/actions/GPTAction$RequestStatus;", "getRequestStatus", "()Lorg/catrobat/catroid/content/actions/GPTAction$RequestStatus;", "setRequestStatus", "(Lorg/catrobat/catroid/content/actions/GPTAction$RequestStatus;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sformula", "getSformula", "setSformula", "system", "getSystem", "setSystem", "url", "getUrl", "setUrl", "webConnection", "Lorg/catrobat/catroid/web/WebConnection;", "getWebConnection", "()Lorg/catrobat/catroid/web/WebConnection;", "setWebConnection", "(Lorg/catrobat/catroid/web/WebConnection;)V", "act", "", "delta", "", "askForPermission", "", "checkPermission", "denyPermission", "grantPermission", "handleError", "error", "handleResponse", "interpretUrl", "onCancelledCall", "onRequestError", "httpError", "onRequestSuccess", "httpResponse", "Lokhttp3/Response;", "restart", "sendRequest", "PermissionStatus", "RequestStatus", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class GPTAction extends Action implements WebConnection.WebRequestListener {
    private Formula pformula;
    private String prompt;
    private Scope scope;
    private Formula sformula;
    private String system;
    private WebConnection webConnection;
    private String url = "https://text.pollinations.ai";
    private RequestStatus requestStatus = RequestStatus.NOT_SENT;
    private PermissionStatus permissionStatus = PermissionStatus.UNKNOWN;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GPTAction$PermissionStatus.class
      classes14.dex
     */
    /* JADX INFO: compiled from: GPTAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/content/actions/GPTAction$PermissionStatus;", "", "(Ljava/lang/String;I)V", GrsBaseInfo.CountryCodeSource.UNKNOWN, "PENDING", "DENIED", "GRANTED", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum PermissionStatus {
        UNKNOWN,
        PENDING,
        DENIED,
        GRANTED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<PermissionStatus> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GPTAction$RequestStatus.class
      classes14.dex
     */
    /* JADX INFO: compiled from: GPTAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/content/actions/GPTAction$RequestStatus;", "", "(Ljava/lang/String;I)V", "NOT_SENT", "WAITING", "FINISHED", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum RequestStatus {
        NOT_SENT,
        WAITING,
        FINISHED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<RequestStatus> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GPTAction$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: GPTAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PermissionStatus.values().length];
            try {
                iArr[PermissionStatus.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PermissionStatus.DENIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public abstract void handleError(String error);

    public abstract void handleResponse();

    public final WebConnection getWebConnection() {
        return this.webConnection;
    }

    public final void setWebConnection(WebConnection webConnection) {
        this.webConnection = webConnection;
    }

    public final Formula getPformula() {
        return this.pformula;
    }

    public final void setPformula(Formula formula) {
        this.pformula = formula;
    }

    public final Formula getSformula() {
        return this.sformula;
    }

    public final void setSformula(Formula formula) {
        this.sformula = formula;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final String getPrompt() {
        return this.prompt;
    }

    public final void setPrompt(String str) {
        this.prompt = str;
    }

    public final String getSystem() {
        return this.system;
    }

    public final void setSystem(String str) {
        this.system = str;
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String str) {
        this.url = str;
    }

    public final RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public final void setRequestStatus(RequestStatus requestStatus) {
        Intrinsics.checkNotNullParameter(requestStatus, "<set-?>");
        this.requestStatus = requestStatus;
    }

    private final boolean interpretUrl() {
        String strInterpretString;
        String strInterpretString2;
        try {
            Formula formula = this.pformula;
            String str = "";
            if (formula == null || (strInterpretString = formula.interpretString(this.scope)) == null) {
                strInterpretString = "";
            }
            this.prompt = strInterpretString;
            Formula formula2 = this.sformula;
            if (formula2 != null && (strInterpretString2 = formula2.interpretString(this.scope)) != null) {
                str = strInterpretString2;
            }
            this.system = str;
            String str2 = "https://text.pollinations.ai/" + URLEncoder.encode(this.prompt, StandardCharsets.UTF_8.toString()) + "?private=true&system=" + URLEncoder.encode(this.system, StandardCharsets.UTF_8.toString()) + "&seed=" + Random.INSTANCE.nextInt(0, 999999999);
            this.url = str2;
            Log.d("GPTAction", "url: " + str2);
            return true;
        } catch (Exception e) {
            ErrorLog errorLog = ErrorLog.INSTANCE;
            String message = e.getMessage();
            if (message == null) {
                message = "**message not provided :(**";
            }
            errorLog.log(message);
            Log.e("GPTAction_Interpret", "Error in interpretUrl", e);
            return false;
        }
    }

    private final void askForPermission() {
        if (StageActivity.messageHandler == null) {
            denyPermission();
            return;
        }
        this.permissionStatus = PermissionStatus.PENDING;
        BrickDialogManager.DialogType dialogType = BrickDialogManager.DialogType.WEB_ACCESS_DIALOG;
        String str = this.url;
        Intrinsics.checkNotNull(str);
        StageActivity.messageHandler.obtainMessage(2, CollectionsKt.arrayListOf(dialogType, this, str)).sendToTarget();
    }

    public final void grantPermission() {
        this.permissionStatus = PermissionStatus.GRANTED;
    }

    public final void denyPermission() {
        this.permissionStatus = PermissionStatus.DENIED;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        StageListener stageListener;
        WebConnectionHolder webConnectionHolder;
        if (this.url == null && !interpretUrl()) {
            return true;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.permissionStatus.ordinal()];
        if (i == 1) {
            checkPermission();
        } else if (i == 2) {
            handleError("511");
            return true;
        }
        if (this.permissionStatus == PermissionStatus.PENDING) {
            return false;
        }
        if (this.requestStatus == RequestStatus.NOT_SENT && !sendRequest()) {
            handleError("429");
            return true;
        }
        if (this.requestStatus == RequestStatus.WAITING) {
            return false;
        }
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null && (stageListener = stageActivity.stageListener) != null && (webConnectionHolder = stageListener.webConnectionHolder) != null) {
            webConnectionHolder.removeConnection(this.webConnection);
        }
        handleResponse();
        return true;
    }

    private final void checkPermission() {
        TrustedDomainManager trustedDomainManager = TrustedDomainManager.INSTANCE;
        String str = this.url;
        Intrinsics.checkNotNull(str);
        if (trustedDomainManager.isURLTrusted(str)) {
            grantPermission();
        } else {
            grantPermission();
        }
    }

    private final boolean sendRequest() {
        boolean zAddConnection;
        StageListener stageListener;
        WebConnectionHolder webConnectionHolder;
        this.requestStatus = RequestStatus.WAITING;
        String str = this.url;
        Intrinsics.checkNotNull(str);
        this.webConnection = new WebConnection(this, str);
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null || (stageListener = stageActivity.stageListener) == null || (webConnectionHolder = stageListener.webConnectionHolder) == null) {
            zAddConnection = false;
        } else {
            WebConnection webConnection = this.webConnection;
            Intrinsics.checkNotNull(webConnection);
            zAddConnection = webConnectionHolder.addConnection(webConnection);
        }
        if (!zAddConnection) {
            return false;
        }
        WebConnection webConnection2 = this.webConnection;
        Intrinsics.checkNotNull(webConnection2);
        webConnection2.sendWebRequest();
        return true;
    }

    public void onRequestSuccess(Response httpResponse) {
        Intrinsics.checkNotNullParameter(httpResponse, "httpResponse");
        Log.i("GPTAction_Callback", "[" + System.identityHashCode(this) + "] onRequestSuccess CALLED! URL: '" + this.url + "', HTTP Code: " + httpResponse.code());
        this.requestStatus = RequestStatus.FINISHED;
    }

    public void onRequestError(String httpError) {
        Intrinsics.checkNotNullParameter(httpError, "httpError");
        Log.e("GPTAction_Callback", "[" + System.identityHashCode(this) + "] onRequestError CALLED! URL: '" + this.url + "', Error: " + httpError);
        this.requestStatus = RequestStatus.FINISHED;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        StageListener stageListener;
        WebConnectionHolder webConnectionHolder;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null && (stageListener = stageActivity.stageListener) != null && (webConnectionHolder = stageListener.webConnectionHolder) != null) {
            webConnectionHolder.removeConnection(this.webConnection);
        }
        this.webConnection = null;
        this.url = null;
        this.requestStatus = RequestStatus.NOT_SENT;
        this.permissionStatus = PermissionStatus.UNKNOWN;
    }

    public void onCancelledCall() {
        this.webConnection = null;
        this.url = null;
        this.requestStatus = RequestStatus.NOT_SENT;
    }
}
