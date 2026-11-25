package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AttachSOAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AttachSOAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/AttachSOAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "soFileName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getSoFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setSoFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "viewName", "getViewName", "setViewName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AttachSOAction extends TemporalAction {
    private Scope scope;
    private Formula soFileName;
    private Formula viewName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewName() {
        return this.viewName;
    }

    public final void setViewName(Formula formula) {
        this.viewName = formula;
    }

    public final Formula getSoFileName() {
        return this.soFileName;
    }

    public final void setSoFileName(Formula formula) {
        this.soFileName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        WeakReference<StageActivity> weakReference;
        StageActivity stageActivity;
        Project project;
        Formula formula = this.viewName;
        File file = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.soFileName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str = strInterpretString2 != null ? strInterpretString2 : "";
        if (strInterpretString.length() <= 0 || str.length() <= 0) {
            return;
        }
        Scope scope = this.scope;
        if (scope != null && (project = scope.getProject()) != null) {
            file = project.getFile(str);
        }
        if (file == null || !file.exists() || (weakReference = StageActivity.activeStageActivity) == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.attachSoToView(strInterpretString, file.getAbsolutePath());
    }
}
