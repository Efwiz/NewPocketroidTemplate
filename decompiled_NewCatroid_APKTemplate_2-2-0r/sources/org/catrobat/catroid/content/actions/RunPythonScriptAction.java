package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.UserVarsManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.python.PythonEngine;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunPythonScriptAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RunPythonScriptAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunPythonScriptAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "script", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getScript", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setScript", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "variableName", "getVariableName", "setVariableName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RunPythonScriptAction extends TemporalAction {
    private Scope scope;
    private Formula script;
    private Formula variableName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getScript() {
        return this.script;
    }

    public final void setScript(Formula formula) {
        this.script = formula;
    }

    public final Formula getVariableName() {
        return this.variableName;
    }

    public final void setVariableName(Formula formula) {
        this.variableName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.script;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.variableName;
        final String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "myVar";
        }
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            return;
        }
        PythonEngine pythonEngine = MainMenuActivity.pythonEngine;
        if (pythonEngine == null) {
            Log.e("RunPythonScript", "PythonEngine not available.");
        } else {
            pythonEngine.runScriptAsync(strInterpretString, new Function1<String, Unit>() { // from class: org.catrobat.catroid.content.actions.RunPythonScriptAction.update.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                    invoke2(str2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String output) {
                    Intrinsics.checkNotNullParameter(output, "output");
                    UserVarsManager.INSTANCE.setVar(strInterpretString2, output);
                }
            });
        }
    }
}
