package org.catrobat.catroid.content.actions;

import android.app.Activity;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.catrobat.catroid.content.MyActivityManager;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PostWebRequestAction$update$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: PostWebRequestAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"org/catrobat/catroid/content/actions/PostWebRequestAction$update$1$1", "Lokhttp3/Callback;", "onFailure", "", "call", "Lokhttp3/Call;", "e", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PostWebRequestAction$update$1$1 implements Callback {
    final /* synthetic */ PostWebRequestAction this$0;

    PostWebRequestAction$update$1$1(PostWebRequestAction postWebRequestAction) {
        this.this$0 = postWebRequestAction;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, final IOException e) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(e, "e");
        Activity stage_activity = MyActivityManager.INSTANCE.getStage_activity();
        if (stage_activity != null) {
            final PostWebRequestAction postWebRequestAction = this.this$0;
            stage_activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.PostWebRequestAction$update$1$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PostWebRequestAction$update$1$1.onFailure$lambda$0(postWebRequestAction, e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFailure$lambda$0(PostWebRequestAction this$0, IOException e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e, "$e");
        UserVariable userVariable = this$0.getUserVariable();
        if (userVariable == null) {
            return;
        }
        userVariable.setValue("Response error: " + e.getMessage());
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        final String strString;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        if (response.isSuccessful()) {
            ResponseBody responseBodyBody = response.body();
            if (responseBodyBody == null || (strString = responseBodyBody.string()) == null) {
                strString = "Empty response";
            }
            Activity stage_activity = MyActivityManager.INSTANCE.getStage_activity();
            if (stage_activity != null) {
                final PostWebRequestAction postWebRequestAction = this.this$0;
                stage_activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.PostWebRequestAction$update$1$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PostWebRequestAction$update$1$1.onResponse$lambda$1(postWebRequestAction, strString);
                    }
                });
                return;
            }
            return;
        }
        final String str = "Error " + response.code() + ": " + response.message();
        Activity stage_activity2 = MyActivityManager.INSTANCE.getStage_activity();
        if (stage_activity2 != null) {
            final PostWebRequestAction postWebRequestAction2 = this.this$0;
            stage_activity2.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.PostWebRequestAction$update$1$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    PostWebRequestAction$update$1$1.onResponse$lambda$2(postWebRequestAction2, str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onResponse$lambda$1(PostWebRequestAction this$0, String bodyStr) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bodyStr, "$bodyStr");
        UserVariable userVariable = this$0.getUserVariable();
        if (userVariable == null) {
            return;
        }
        userVariable.setValue(bodyStr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onResponse$lambda$2(PostWebRequestAction this$0, String errorMessage) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(errorMessage, "$errorMessage");
        UserVariable userVariable = this$0.getUserVariable();
        if (userVariable == null) {
            return;
        }
        userVariable.setValue(errorMessage);
    }
}
