package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetBufferShaderAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetBufferShaderAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetBufferShaderAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "bufferName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBufferName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBufferName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "fragmentCode", "getFragmentCode", "setFragmentCode", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "vertexCode", "getVertexCode", "setVertexCode", "act", "", "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetBufferShaderAction extends Action {
    private Formula bufferName;
    private Formula fragmentCode;
    private Scope scope;
    private Formula vertexCode;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getBufferName() {
        return this.bufferName;
    }

    public final void setBufferName(Formula formula) {
        this.bufferName = formula;
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

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Scope scope = this.scope;
        if (scope == null) {
            return true;
        }
        Formula formula = this.bufferName;
        String strInterpretString = formula != null ? formula.interpretString(scope) : null;
        if (strInterpretString == null) {
            return true;
        }
        Formula formula2 = this.vertexCode;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(scope) : null;
        Formula formula3 = this.fragmentCode;
        RenderTextureManager.INSTANCE.setBufferShader(strInterpretString, strInterpretString2, formula3 != null ? formula3.interpretString(scope) : null);
        return true;
    }
}
