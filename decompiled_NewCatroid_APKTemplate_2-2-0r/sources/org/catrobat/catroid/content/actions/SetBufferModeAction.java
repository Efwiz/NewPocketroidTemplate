package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetBufferModeAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetBufferModeAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetBufferModeAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "nameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "render2DFormula", "getRender2DFormula", "setRender2DFormula", "render3DFormula", "getRender3DFormula", "setRender3DFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetBufferModeAction extends TemporalAction {
    private Formula nameFormula;
    private Formula render2DFormula;
    private Formula render3DFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNameFormula() {
        return this.nameFormula;
    }

    public final void setNameFormula(Formula formula) {
        this.nameFormula = formula;
    }

    public final Formula getRender2DFormula() {
        return this.render2DFormula;
    }

    public final void setRender2DFormula(Formula formula) {
        this.render2DFormula = formula;
    }

    public final Formula getRender3DFormula() {
        return this.render3DFormula;
    }

    public final void setRender3DFormula(Formula formula) {
        this.render3DFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.nameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.render2DFormula;
        Integer numInterpretInteger = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        int iIntValue = numInterpretInteger == null ? 1 : numInterpretInteger.intValue();
        Formula formula3 = this.render3DFormula;
        Integer numInterpretInteger2 = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        RenderTextureManager.INSTANCE.setBufferMode(strInterpretString, iIntValue > 0, (numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue()) > 0);
    }
}
