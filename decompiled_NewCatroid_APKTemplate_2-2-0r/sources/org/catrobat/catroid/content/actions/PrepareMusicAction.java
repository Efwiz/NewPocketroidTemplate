package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PrepareMusicAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PrepareMusicAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/PrepareMusicAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", PsiTreeChangeEvent.PROP_FILE_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "soundName", "getSoundName", "setSoundName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrepareMusicAction extends TemporalAction {
    private Formula fileName;
    private Scope scope;
    private Formula soundName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFileName() {
        return this.fileName;
    }

    public final void setFileName(Formula formula) {
        this.fileName = formula;
    }

    public final Formula getSoundName() {
        return this.soundName;
    }

    public final void setSoundName(Formula formula) {
        this.soundName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String str;
        StageListener activeStageListener;
        ThreeDManager threeDManager;
        Formula formula = this.fileName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.soundName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str2 = strInterpretString;
        if (str2 == null || str2.length() == 0 || (str = strInterpretString2) == null || str.length() == 0 || (activeStageListener = StageActivity.getActiveStageListener()) == null || (threeDManager = activeStageListener.getThreeDManager()) == null) {
            return;
        }
        threeDManager.prepareAudio(strInterpretString2, strInterpretString, true);
    }
}
