package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetShaderCodeAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetShaderCodeAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetShaderCodeAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "fragmentCode", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFragmentCode", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFragmentCode", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "vertexCode", "getVertexCode", "setVertexCode", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetShaderCodeAction extends TemporalAction {
    private Formula fragmentCode;
    private Scope scope;
    private Formula vertexCode;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getVertexCode() {
        return this.vertexCode;
    }

    public final void setVertexCode(Formula formula) {
        this.vertexCode = formula;
    }

    public final Formula getFragmentCode() {
        return this.fragmentCode;
    }

    public final void setFragmentCode(Formula formula) {
        this.fragmentCode = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener stageListener;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        ThreeDManager threeDManager = (stageActivity == null || (stageListener = stageActivity.stageListener) == null) ? null : stageListener.getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.vertexCode;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.fragmentCode;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (!Intrinsics.areEqual(strInterpretString, "") && !Intrinsics.areEqual(strInterpretString2, "")) {
            threeDManager.setShaderCode(strInterpretString, strInterpretString2);
        } else {
            threeDManager.resetSceneShader();
        }
    }
}
