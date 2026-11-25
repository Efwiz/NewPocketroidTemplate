package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PostWebRequestAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PostWebRequestAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/actions/PostWebRequestAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "body", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBody", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBody", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "header", "getHeader", "setHeader", "rurl", "getRurl", "setRurl", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "setVariable", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PostWebRequestAction extends TemporalAction {
    private Formula body;
    private Formula header;
    private Formula rurl;
    private Scope scope;
    private UserVariable userVariable;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRurl() {
        return this.rurl;
    }

    public final void setRurl(Formula formula) {
        this.rurl = formula;
    }

    public final Formula getHeader() {
        return this.header;
    }

    public final void setHeader(Formula formula) {
        this.header = formula;
    }

    public final Formula getBody() {
        return this.body;
    }

    public final void setBody(Formula formula) {
        this.body = formula;
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
        final OkHttpClient okHttpClientBuild = new OkHttpClient.Builder().connectTimeout(0L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.SECONDS).writeTimeout(0L, TimeUnit.SECONDS).build();
        Formula formula = this.rurl;
        Object objInterpretObject = formula != null ? formula.interpretObject(this.scope) : null;
        if (objInterpretObject == null) {
            objInterpretObject = "";
        }
        String string = objInterpretObject.toString();
        Formula formula2 = this.header;
        Object objInterpretObject2 = formula2 != null ? formula2.interpretObject(this.scope) : null;
        if (objInterpretObject2 == null) {
            objInterpretObject2 = "";
        }
        Formula formula3 = this.body;
        Object objInterpretObject3 = formula3 != null ? formula3.interpretObject(this.scope) : null;
        Object obj = objInterpretObject3 != null ? objInterpretObject3 : "";
        String string2 = StringsKt.trim((CharSequence) StringsKt.removePrefix(StringsKt.trim((CharSequence) objInterpretObject2.toString()).toString(), (CharSequence) "Content-Type:")).toString();
        String string3 = obj.toString();
        if (this.userVariable == null) {
            return;
        }
        MediaType mediaType = MediaType.INSTANCE.parse(string2);
        if (mediaType == null) {
            mediaType = MediaType.INSTANCE.get("application/json");
        }
        final Request requestBuild = new Request.Builder().url(string).post(RequestBody.INSTANCE.create(mediaType, string3)).build();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.content.actions.PostWebRequestAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PostWebRequestAction.update$lambda$0(okHttpClientBuild, requestBuild, this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(OkHttpClient client, Request request, PostWebRequestAction this$0) {
        Intrinsics.checkNotNullParameter(client, "$client");
        Intrinsics.checkNotNullParameter(request, "$request");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        client.newCall(request).enqueue(new PostWebRequestAction$update$1$1(this$0));
    }
}
