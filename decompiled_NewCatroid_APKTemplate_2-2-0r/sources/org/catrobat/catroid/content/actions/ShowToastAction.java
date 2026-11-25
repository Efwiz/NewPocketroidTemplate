package org.catrobat.catroid.content.actions;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowToastAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ShowToastAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/content/actions/ShowToastAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "toast", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getToast", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setToast", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "act", "", "delta", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShowToastAction extends Action {
    private static Toast currentToast;
    public Scope scope;
    public Formula toast;
    private static final Handler uiHandler = new Handler(Looper.getMainLooper());

    public final Scope getScope() {
        Scope scope = this.scope;
        if (scope != null) {
            return scope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scope");
        return null;
    }

    public final void setScope(Scope scope) {
        Intrinsics.checkNotNullParameter(scope, "<set-?>");
        this.scope = scope;
    }

    public final Formula getToast() {
        Formula formula = this.toast;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("toast");
        return null;
    }

    public final void setToast(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.toast = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        final String str = "";
        try {
            String strInterpretString = getToast().interpretString(getScope());
            if (strInterpretString != null) {
                str = strInterpretString;
            }
        } catch (InterpretationException unused) {
        }
        uiHandler.post(new Runnable() { // from class: org.catrobat.catroid.content.actions.ShowToastAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ShowToastAction.act$lambda$0(str);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void act$lambda$0(String message) {
        Intrinsics.checkNotNullParameter(message, "$message");
        Toast toast = currentToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast toastMakeText = Toast.makeText(CatroidApplication.getAppContext(), message, 0);
        toastMakeText.show();
        currentToast = toastMakeText;
    }
}
