package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Response;
import org.catrobat.catroid.TrustedDomainManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.BrickDialogManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.web.WebConnection;
import org.catrobat.catroid.web.WebConnectionHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WebAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WebAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u00012\u00020\u0002:\u00029:B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020)H\u0002J\b\u0010*\u001a\u00020)H\u0002J\u0006\u0010+\u001a\u00020)J\u0006\u0010,\u001a\u00020)J\u0010\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020\u0019H&J\b\u0010/\u001a\u00020)H&J\b\u00100\u001a\u00020%H\u0002J\b\u00101\u001a\u00020)H\u0017J\u0010\u00102\u001a\u00020)2\u0006\u00103\u001a\u00020\u0019H\u0017J\u0010\u00104\u001a\u00020)2\u0006\u00105\u001a\u000206H\u0017J\b\u00107\u001a\u00020)H\u0017J\b\u00108\u001a\u00020%H\u0002R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u0006;"}, d2 = {"Lorg/catrobat/catroid/content/actions/WebAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lorg/catrobat/catroid/web/WebConnection$WebRequestListener;", "()V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "permissionStatus", "Lorg/catrobat/catroid/content/actions/WebAction$PermissionStatus;", "requestStatus", "Lorg/catrobat/catroid/content/actions/WebAction$RequestStatus;", "getRequestStatus", "()Lorg/catrobat/catroid/content/actions/WebAction$RequestStatus;", "setRequestStatus", "(Lorg/catrobat/catroid/content/actions/WebAction$RequestStatus;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "url", "", "getUrl", "()Ljava/lang/String;", "setUrl", "(Ljava/lang/String;)V", "webConnection", "Lorg/catrobat/catroid/web/WebConnection;", "getWebConnection", "()Lorg/catrobat/catroid/web/WebConnection;", "setWebConnection", "(Lorg/catrobat/catroid/web/WebConnection;)V", "act", "", "delta", "", "askForPermission", "", "checkPermission", "denyPermission", "grantPermission", "handleError", "error", "handleResponse", "interpretUrl", "onCancelledCall", "onRequestError", "httpError", "onRequestSuccess", "httpResponse", "Lokhttp3/Response;", "restart", "sendRequest", "PermissionStatus", "RequestStatus", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WebAction extends Action implements WebConnection.WebRequestListener {
    private Formula formula;
    private Scope scope;
    private String url;
    private WebConnection webConnection;
    private RequestStatus requestStatus = RequestStatus.NOT_SENT;
    private PermissionStatus permissionStatus = PermissionStatus.UNKNOWN;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WebAction$PermissionStatus.class
      classes14.dex
     */
    /* JADX INFO: compiled from: WebAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/content/actions/WebAction$PermissionStatus;", "", "(Ljava/lang/String;I)V", GrsBaseInfo.CountryCodeSource.UNKNOWN, "PENDING", "DENIED", "GRANTED", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WebAction$RequestStatus.class
      classes14.dex
     */
    /* JADX INFO: compiled from: WebAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/content/actions/WebAction$RequestStatus;", "", "(Ljava/lang/String;I)V", "NOT_SENT", "WAITING", "FINISHED", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WebAction$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: WebAction.kt */
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

    public final Formula getFormula() {
        return this.formula;
    }

    public final void setFormula(Formula formula) {
        this.formula = formula;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
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
        try {
            Formula formula = this.formula;
            Intrinsics.checkNotNull(formula);
            String strInterpretString = formula.interpretString(this.scope);
            Intrinsics.checkNotNull(strInterpretString);
            String strValueOf = null;
            if (!StringsKt.startsWith$default(strInterpretString, "http://", false, 2, (Object) null) && !StringsKt.startsWith$default(strInterpretString, "https://", false, 2, (Object) null)) {
                strInterpretString = "https://" + strInterpretString;
            }
            this.url = strInterpretString;
            Integer numValueOf = strInterpretString != null ? Integer.valueOf(StringsKt.indexOf$default((CharSequence) strInterpretString, "\n", 0, false, 6, (Object) null)) : null;
            if (numValueOf == null || numValueOf.intValue() != -1) {
                if (numValueOf != null) {
                    int iIntValue = numValueOf.intValue();
                    String str = this.url;
                    strValueOf = String.valueOf(str != null ? str.subSequence(0, iIntValue) : null);
                }
                this.url = strValueOf;
            }
            return true;
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Couldn't interpret formula", e);
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
        StageListener stageListener;
        WebConnectionHolder webConnectionHolder;
        this.requestStatus = RequestStatus.WAITING;
        String str = this.url;
        Intrinsics.checkNotNull(str);
        this.webConnection = new WebConnection(this, str);
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null || (stageListener = stageActivity.stageListener) == null || (webConnectionHolder = stageListener.webConnectionHolder) == null) {
            return false;
        }
        WebConnection webConnection = this.webConnection;
        Intrinsics.checkNotNull(webConnection);
        if (!webConnectionHolder.addConnection(webConnection)) {
            return false;
        }
        WebConnection webConnection2 = this.webConnection;
        Intrinsics.checkNotNull(webConnection2);
        webConnection2.sendWebRequest();
        return true;
    }

    public void onRequestSuccess(Response httpResponse) {
        Intrinsics.checkNotNullParameter(httpResponse, "httpResponse");
        this.requestStatus = RequestStatus.FINISHED;
    }

    public void onRequestError(String httpError) {
        Intrinsics.checkNotNullParameter(httpError, "httpError");
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
