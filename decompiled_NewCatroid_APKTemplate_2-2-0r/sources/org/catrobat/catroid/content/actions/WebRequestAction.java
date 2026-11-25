package org.catrobat.catroid.content.actions;

import android.util.Log;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WebRequestAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WebRequestAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004H\u0016J\b\u0010\u0016\u001a\u00020\u0014H\u0016J\b\u0010\u0017\u001a\u00020\u0014H\u0016J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0004H\u0016J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0014H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/actions/WebRequestAction;", "Lorg/catrobat/catroid/content/actions/WebAction;", "()V", "response", "", "getResponse", "()Ljava/lang/String;", "setResponse", "(Ljava/lang/String;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "act", "", "delta", "", "handleError", "", "error", "handleResponse", "onCancelledCall", "onRequestError", "httpError", "onRequestSuccess", "httpResponse", "Lokhttp3/Response;", "restart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WebRequestAction extends WebAction {
    private String response;
    private UserVariable userVariable;

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    public final String getResponse() {
        return this.response;
    }

    public final void setResponse(String str) {
        this.response = str;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x001e  */
    @Override // org.catrobat.catroid.content.actions.WebAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        boolean z;
        Scope scope = getScope();
        if ((scope != null ? scope.getSprite() : null) == null) {
            z = false;
        } else {
            Scope scope2 = getScope();
            if ((scope2 != null ? scope2.getSequence() : null) != null) {
                z = true;
            } else {
                z = false;
            }
        }
        if (this.userVariable == null || getFormula() == null || !z) {
            return true;
        }
        return super.act(delta);
    }

    @Override // org.catrobat.catroid.content.actions.WebAction
    public void handleResponse() {
        UserVariable userVariable = this.userVariable;
        Intrinsics.checkNotNull(userVariable);
        userVariable.setValue(this.response);
    }

    @Override // org.catrobat.catroid.content.actions.WebAction
    public void handleError(String error) {
        Intrinsics.checkNotNullParameter(error, "error");
        UserVariable userVariable = this.userVariable;
        Intrinsics.checkNotNull(userVariable);
        userVariable.setValue(error);
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.response = null;
        super.restart();
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, org.catrobat.catroid.web.WebConnection.WebRequestListener
    public void onRequestSuccess(Response httpResponse) {
        String strString;
        String str = "";
        Intrinsics.checkNotNullParameter(httpResponse, "httpResponse");
        try {
            ResponseBody responseBodyBody = httpResponse.body();
            if (responseBodyBody != null && (strString = responseBodyBody.string()) != null) {
                str = strString;
            }
        } catch (IOException e) {
            Log.d(getClass().getSimpleName(), "HTTP reponse body is empty", e);
        }
        this.response = str;
        super.onRequestSuccess(httpResponse);
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, org.catrobat.catroid.web.WebConnection.WebRequestListener
    public void onRequestError(String httpError) {
        Intrinsics.checkNotNullParameter(httpError, "httpError");
        this.response = httpError;
        super.onRequestError(httpError);
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, org.catrobat.catroid.web.WebConnection.WebRequestListener
    public void onCancelledCall() {
        this.response = null;
        super.onCancelledCall();
    }
}
