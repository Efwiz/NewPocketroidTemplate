package org.catrobat.catroid.content.actions;

import android.content.Intent;
import android.net.Uri;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/OpenUrlAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: OpenUrlAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\f\u001a\u00020\nH\u0016J\b\u0010\r\u001a\u00020\nH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/content/actions/OpenUrlAction;", "Lorg/catrobat/catroid/content/actions/WebAction;", "()V", "response", "", "getResponse", "()Ljava/lang/String;", "setResponse", "(Ljava/lang/String;)V", "handleError", "", "error", "handleResponse", "openUrl", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OpenUrlAction extends WebAction {
    private String response;

    @Override // org.catrobat.catroid.content.actions.WebAction
    public void handleError(String error) {
        Intrinsics.checkNotNullParameter(error, "error");
    }

    public final String getResponse() {
        return this.response;
    }

    public final void setResponse(String str) {
        this.response = str;
    }

    private final void openUrl() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(getUrl()));
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.startActivity(intent);
        }
    }

    @Override // org.catrobat.catroid.content.actions.WebAction
    public void handleResponse() {
        openUrl();
    }
}
