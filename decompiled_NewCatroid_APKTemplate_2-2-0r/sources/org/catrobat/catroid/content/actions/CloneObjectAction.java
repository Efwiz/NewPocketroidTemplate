package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CloneObjectAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CloneObjectAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/CloneObjectAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "cloneObjectName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCloneObjectName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCloneObjectName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sourceObjectName", "getSourceObjectName", "setSourceObjectName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CloneObjectAction extends TemporalAction {
    private Formula cloneObjectName;
    private Scope scope;
    private Formula sourceObjectName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getSourceObjectName() {
        return this.sourceObjectName;
    }

    public final void setSourceObjectName(Formula formula) {
        this.sourceObjectName = formula;
    }

    public final Formula getCloneObjectName() {
        return this.cloneObjectName;
    }

    public final void setCloneObjectName(Formula formula) {
        this.cloneObjectName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String str;
        GameObject gameObjectFindObjectByName;
        Formula formula = this.sourceObjectName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.cloneObjectName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str2 = strInterpretString;
        if (str2 == null || str2.length() == 0 || (str = strInterpretString2) == null || str.length() == 0) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        SceneManager sceneManager = activeStageListener != null ? activeStageListener.sceneManager : null;
        if (sceneManager == null || (gameObjectFindObjectByName = sceneManager.findObjectByName(strInterpretString)) == null) {
            return;
        }
        sceneManager.cloneGameObject(gameObjectFindObjectByName, strInterpretString2);
    }
}
