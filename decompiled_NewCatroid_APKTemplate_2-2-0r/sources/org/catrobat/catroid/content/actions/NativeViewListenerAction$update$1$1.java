package org.catrobat.catroid.content.actions;

import android.webkit.JavascriptInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewListenerAction$update$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewListenerAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"org/catrobat/catroid/content/actions/NativeViewListenerAction$update$1$1", "", "postMessage", "", CrashHianalyticsData.MESSAGE, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewListenerAction$update$1$1 {
    final /* synthetic */ StageActivity $stage;
    final /* synthetic */ UserVariable $variable;

    NativeViewListenerAction$update$1$1(StageActivity stageActivity, UserVariable userVariable) {
        this.$stage = stageActivity;
        this.$variable = userVariable;
    }

    @JavascriptInterface
    public final void postMessage(final String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        StageActivity stageActivity = this.$stage;
        final UserVariable userVariable = this.$variable;
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$update$1$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewListenerAction$update$1$1.postMessage$lambda$0(userVariable, message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void postMessage$lambda$0(UserVariable variable, String message) {
        Intrinsics.checkNotNullParameter(variable, "$variable");
        Intrinsics.checkNotNullParameter(message, "$message");
        variable.setValue(message);
    }
}
