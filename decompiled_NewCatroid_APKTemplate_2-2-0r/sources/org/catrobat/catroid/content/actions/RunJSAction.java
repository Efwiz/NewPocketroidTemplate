package org.catrobat.catroid.content.actions;

import android.os.Handler;
import android.os.Looper;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunJSAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RunJSAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunJSAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "runScript", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getRunScript", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setRunScript", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RunJSAction extends TemporalAction {
    private static boolean isWebViewCreated;
    private Formula runScript;
    private Scope scope;
    private UserVariable userVariable;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Handler uiHandler = new Handler(Looper.getMainLooper());
    private static final Lazy<WebView> sharedWebView$delegate = LazyKt.lazy(new Function0<WebView>() { // from class: org.catrobat.catroid.content.actions.RunJSAction$Companion$sharedWebView$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final WebView invoke() {
            WebView webView = new WebView(CatroidApplication.getAppContext());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(new JsInterface(), "Android");
            webView.setWebViewClient(new WebViewClient() { // from class: org.catrobat.catroid.content.actions.RunJSAction$Companion$sharedWebView$2.1
            });
            RunJSAction.Companion companion = RunJSAction.INSTANCE;
            RunJSAction.isWebViewCreated = true;
            return webView;
        }
    });

    public RunJSAction() {
        setDuration(0.0f);
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRunScript() {
        return this.runScript;
    }

    public final void setRunScript(Formula formula) {
        this.runScript = formula;
    }

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (percent < 1.0f) {
            return;
        }
        Formula formula = this.runScript;
        Object objInterpretObject = formula != null ? formula.interpretObject(this.scope) : null;
        String str = objInterpretObject instanceof String ? (String) objInterpretObject : null;
        if (str == null) {
            str = "";
        }
        INSTANCE.evaluateJS(str, this.userVariable);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunJSAction$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: RunJSAction.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunJSAction$Companion;", "", "()V", "isWebViewCreated", "", "sharedWebView", "Landroid/webkit/WebView;", "getSharedWebView", "()Landroid/webkit/WebView;", "sharedWebView$delegate", "Lkotlin/Lazy;", "uiHandler", "Landroid/os/Handler;", "destroyWebView", "", "evaluateJS", "script", "", "variable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final WebView getSharedWebView() {
            return (WebView) RunJSAction.sharedWebView$delegate.getValue();
        }

        public final void evaluateJS(final String script, final UserVariable variable) {
            Intrinsics.checkNotNullParameter(script, "script");
            RunJSAction.uiHandler.post(new Runnable() { // from class: org.catrobat.catroid.content.actions.RunJSAction$Companion$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RunJSAction.Companion.evaluateJS$lambda$1(script, variable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void evaluateJS$lambda$1(String script, final UserVariable userVariable) {
            Intrinsics.checkNotNullParameter(script, "$script");
            RunJSAction.INSTANCE.getSharedWebView().loadData("", "text/html", null);
            RunJSAction.INSTANCE.getSharedWebView().evaluateJavascript(script, new ValueCallback() { // from class: org.catrobat.catroid.content.actions.RunJSAction$Companion$$ExternalSyntheticLambda0
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    RunJSAction.Companion.evaluateJS$lambda$1$lambda$0(userVariable, (String) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void evaluateJS$lambda$1$lambda$0(UserVariable userVariable, String str) {
            if (str == null) {
                str = "";
            }
            if (userVariable == null) {
                return;
            }
            userVariable.setValue(str);
        }

        public final void destroyWebView() {
            if (RunJSAction.isWebViewCreated) {
                RunJSAction.uiHandler.post(new Runnable() { // from class: org.catrobat.catroid.content.actions.RunJSAction$Companion$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        RunJSAction.Companion.destroyWebView$lambda$2();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void destroyWebView$lambda$2() {
            RunJSAction.INSTANCE.getSharedWebView().stopLoading();
        }
    }
}
