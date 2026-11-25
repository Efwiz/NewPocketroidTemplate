package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.python.PythonCommandManager;
import org.catrobat.catroid.python.PythonEngine;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunShellAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RunShellAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunShellAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "command", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCommand", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCommand", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RunShellAction extends TemporalAction {
    private Formula command;
    private Scope scope;
    private UserVariable userVariable;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getCommand() {
        return this.command;
    }

    public final void setCommand(Formula formula) {
        this.command = formula;
    }

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws IOException {
        String str;
        PythonEngine pythonEngine;
        Formula formula = this.command;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Scope scope = this.scope;
        if ((scope != null ? scope.getProject() : null) == null || (str = strInterpretString) == null || str.length() == 0 || (pythonEngine = MainMenuActivity.pythonEngine) == null) {
            return;
        }
        Scope scope2 = this.scope;
        Intrinsics.checkNotNull(scope2);
        Project project = scope2.getProject();
        Intrinsics.checkNotNull(project);
        new PythonCommandManager(pythonEngine, project).executeCommandForResult(strInterpretString, new Function1<String, Unit>() { // from class: org.catrobat.catroid.content.actions.RunShellAction.update.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                invoke2(str2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String result) {
                Intrinsics.checkNotNullParameter(result, "result");
                UserVariable userVariable = RunShellAction.this.getUserVariable();
                if (userVariable == null) {
                    return;
                }
                userVariable.setValue(result);
            }
        });
    }
}
