package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetMainRenderLoopsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetMainRenderLoopsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetMainRenderLoopsAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "render2D", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getRender2D", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setRender2D", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "render3D", "getRender3D", "setRender3D", "renderFast2D", "getRenderFast2D", "setRenderFast2D", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "act", "", "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetMainRenderLoopsAction extends Action {
    private Formula render2D;
    private Formula render3D;
    private Formula renderFast2D;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRender2D() {
        return this.render2D;
    }

    public final void setRender2D(Formula formula) {
        this.render2D = formula;
    }

    public final Formula getRenderFast2D() {
        return this.renderFast2D;
    }

    public final void setRenderFast2D(Formula formula) {
        this.renderFast2D = formula;
    }

    public final Formula getRender3D() {
        return this.render3D;
    }

    public final void setRender3D(Formula formula) {
        this.render3D = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Scope scope = this.scope;
        if (scope == null) {
            return true;
        }
        Formula formula = this.render2D;
        Float fInterpretFloat = formula != null ? formula.interpretFloat(scope) : null;
        float fFloatValue = fInterpretFloat == null ? 1.0f : fInterpretFloat.floatValue();
        Formula formula2 = this.renderFast2D;
        Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 1.0f : fInterpretFloat2.floatValue();
        Formula formula3 = this.render3D;
        Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(scope) : null;
        float fFloatValue3 = fInterpretFloat3 != null ? fInterpretFloat3.floatValue() : 1.0f;
        RenderTextureManager.setMain2DRenderEnabled(!(fFloatValue == 0.0f));
        RenderTextureManager.setMainFast2DRenderEnabled(!(fFloatValue2 == 0.0f));
        RenderTextureManager.setMain3DRenderEnabled(!(fFloatValue3 == 0.0f));
        return true;
    }
}
