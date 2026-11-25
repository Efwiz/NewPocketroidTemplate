package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.lunoscript.LunoScriptEngine;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LunoscriptAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoscriptAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0015R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/actions/LunoscriptAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCode", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCode", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LunoscriptAction extends TemporalAction {
    private Formula code;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getCode() {
        return this.code;
    }

    public final void setCode(Formula formula) {
        this.code = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.code;
        final String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        final LunoScriptEngine lunoScriptEngine = new LunoScriptEngine(CatroidApplication.getAppContext(), this.scope);
        new Thread(new Runnable() { // from class: org.catrobat.catroid.content.actions.LunoscriptAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LunoscriptAction.update$lambda$0(lunoScriptEngine, strInterpretString);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(LunoScriptEngine lunoEngine, String codeStr) {
        Intrinsics.checkNotNullParameter(lunoEngine, "$lunoEngine");
        Intrinsics.checkNotNullParameter(codeStr, "$codeStr");
        lunoEngine.execute(codeStr);
    }
}
