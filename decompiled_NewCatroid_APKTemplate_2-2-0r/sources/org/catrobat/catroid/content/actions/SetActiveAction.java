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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetActiveAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetActiveAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetActiveAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "activeState", "", "getActiveState", "()Z", "setActiveState", "(Z)V", "objectName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getObjectName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setObjectName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetActiveAction extends TemporalAction {
    private boolean activeState = true;
    private Formula objectName;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectName() {
        return this.objectName;
    }

    public final void setObjectName(Formula formula) {
        this.objectName = formula;
    }

    public final boolean getActiveState() {
        return this.activeState;
    }

    public final void setActiveState(boolean z) {
        this.activeState = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.objectName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        SceneManager sceneManager = activeStageListener != null ? activeStageListener.sceneManager : null;
        if (sceneManager == null) {
            return;
        }
        GameObject gameObjectFindObjectByName = sceneManager.findObjectByName(strInterpretString);
        if (gameObjectFindObjectByName != null) {
            sceneManager.setObjectActive(gameObjectFindObjectByName, this.activeState);
        } else {
            StageActivity.getActiveStageListener().getThreeDManager().setObjectVisibility(strInterpretString, this.activeState);
        }
    }
}
