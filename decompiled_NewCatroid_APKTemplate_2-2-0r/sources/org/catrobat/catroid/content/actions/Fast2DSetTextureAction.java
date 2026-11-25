package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.fast2d.FastTwoDManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Fast2DSetTextureAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: Fast2DSetTextureAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/Fast2DSetTextureAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "entityId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getEntityId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setEntityId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", PsiTreeChangeEvent.PROP_FILE_NAME, "getFileName", "setFileName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fast2DSetTextureAction extends TemporalAction {
    private Formula entityId;
    private Formula fileName;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getEntityId() {
        return this.entityId;
    }

    public final void setEntityId(Formula formula) {
        this.entityId = formula;
    }

    public final Formula getFileName() {
        return this.fileName;
    }

    public final void setFileName(Formula formula) {
        this.fileName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageActivity stageActivity;
        StageListener stageListener;
        FastTwoDManager fastTwoDManager;
        Project project;
        Formula formula = this.entityId;
        File file = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.fileName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            return;
        }
        Scope scope = this.scope;
        if (scope != null && (project = scope.getProject()) != null) {
            file = project.getFile(strInterpretString2);
        }
        if (file == null || !file.exists() || (stageActivity = StageActivity.activeStageActivity.get()) == null || (stageListener = stageActivity.stageListener) == null || (fastTwoDManager = stageListener.fastTwoDManager) == null) {
            return;
        }
        fastTwoDManager.setTexture(strInterpretString, file.getAbsolutePath());
    }
}
