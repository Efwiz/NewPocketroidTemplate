package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.gson.Gson;
import defpackage.CustomDns;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.catrobat.catroid.content.GeminiManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AskGeminiAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AskGeminiAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/actions/AskGeminiAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "ask", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAsk", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAsk", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "setVariable", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AskGeminiAction extends TemporalAction {
    private Formula ask;
    private Scope scope;
    private UserVariable userVariable;

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$0(String str, SSLSession sSLSession) {
        return true;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getAsk() {
        return this.ask;
    }

    public final void setAsk(Formula formula) {
        this.ask = formula;
    }

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    public final void setVariable(UserVariable userVariable) {
        if (userVariable == null) {
            return;
        }
        this.userVariable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final OkHttpClient okHttpClientBuild = new OkHttpClient.Builder().dns(new CustomDns()).connectTimeout(0L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.SECONDS).writeTimeout(0L, TimeUnit.SECONDS).hostnameVerifier(new HostnameVerifier() { // from class: org.catrobat.catroid.content.actions.AskGeminiAction$$ExternalSyntheticLambda0
            @Override // javax.net.ssl.HostnameVerifier
            public final boolean verify(String str, SSLSession sSLSession) {
                return AskGeminiAction.update$lambda$0(str, sSLSession);
            }
        }).build();
        Formula formula = this.ask;
        Object objInterpretObject = formula != null ? formula.interpretObject(this.scope) : null;
        if (objInterpretObject == null) {
            objInterpretObject = "";
        }
        String strReplace$default = StringsKt.replace$default(objInterpretObject.toString(), "\"", "\\\"", false, 4, (Object) null);
        String api_key = GeminiManager.INSTANCE.getApi_key();
        String str = api_key;
        if (str == null || StringsKt.isBlank(str)) {
            return;
        }
        String strTrimIndent = StringsKt.trimIndent("\n    {\n    \"contents\": [\n      {\n        \"parts\": [\n          {\n            \"text\": \"" + strReplace$default + "\"\n          }\n        ]\n      }\n    ]\n  }\n    ");
        if (this.userVariable == null) {
            return;
        }
        final Request requestBuild = new Request.Builder().url("https://generativelanguage.googleapis.com/v1beta/models/models/gemini-flash-lite-latest:generateContent").post(RequestBody.INSTANCE.create(MediaType.INSTANCE.get("application/json; charset=utf-8"), strTrimIndent)).header("x-goog-api-key", api_key).build();
        Log.d("GeminiAPI", "URL: https://generativelanguage.googleapis.com/v1beta/models/models/gemini-flash-lite-latest:generateContent");
        Log.d("GeminiAPI", "Request Body: " + strTrimIndent);
        new Thread(new Runnable() { // from class: org.catrobat.catroid.content.actions.AskGeminiAction$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AskGeminiAction.update$lambda$1(okHttpClientBuild, requestBuild, this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$1(OkHttpClient client, Request request, final AskGeminiAction this$0) {
        Intrinsics.checkNotNullParameter(client, "$client");
        Intrinsics.checkNotNullParameter(request, "$request");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        client.newCall(request).enqueue(new Callback() { // from class: org.catrobat.catroid.content.actions.AskGeminiAction$update$1$1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(e, "e");
                UserVariable userVariable = this.this$0.getUserVariable();
                if (userVariable == null) {
                    return;
                }
                userVariable.setValue("Response error: " + e.getMessage());
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                String strString;
                String strString2;
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                if (response.isSuccessful()) {
                    ResponseBody responseBodyBody = response.body();
                    if (responseBodyBody == null || (strString2 = responseBodyBody.string()) == null) {
                        strString2 = "Empty response";
                    }
                    try {
                        String text = ((Part) CollectionsKt.first((List) ((Candidate) CollectionsKt.first((List) ((ResponseData) new Gson().fromJson(strString2, ResponseData.class)).getCandidates())).getContent().getParts())).getText();
                        UserVariable userVariable = this.this$0.getUserVariable();
                        if (userVariable == null) {
                            return;
                        }
                        userVariable.setValue(text);
                        return;
                    } catch (Exception e) {
                        UserVariable userVariable2 = this.this$0.getUserVariable();
                        if (userVariable2 != null) {
                            userVariable2.setValue("Error parsing JSON: " + e.getMessage());
                        }
                        Log.e("GeminiAPI", "JSON Parsing Error: " + e.getMessage());
                        return;
                    }
                }
                UserVariable userVariable3 = this.this$0.getUserVariable();
                if (userVariable3 != null) {
                    userVariable3.setValue("Error " + response.code() + ": " + response.message());
                }
                ResponseBody responseBodyBody2 = response.body();
                if (responseBodyBody2 == null || (strString = responseBodyBody2.string()) == null) {
                    strString = "Unknown error";
                }
                Log.e("GeminiAPI", "Error body: " + strString);
            }
        });
    }
}
