package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetBufferShaderUniformAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetBufferShaderUniformAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\b¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetBufferShaderUniformAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "bufferName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBufferName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBufferName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "paramCount", "", "getParamCount", "()I", "setParamCount", "(I)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "uniformName", "getUniformName", "setUniformName", "val1", "getVal1", "setVal1", "val2", "getVal2", "setVal2", "val3", "getVal3", "setVal3", "act", "", "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetBufferShaderUniformAction extends Action {
    private Formula bufferName;
    private int paramCount = 1;
    private Scope scope;
    private Formula uniformName;
    private Formula val1;
    private Formula val2;
    private Formula val3;

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

    public final Formula getUniformName() {
        return this.uniformName;
    }

    public final void setUniformName(Formula formula) {
        this.uniformName = formula;
    }

    public final Formula getVal1() {
        return this.val1;
    }

    public final void setVal1(Formula formula) {
        this.val1 = formula;
    }

    public final Formula getVal2() {
        return this.val2;
    }

    public final void setVal2(Formula formula) {
        this.val2 = formula;
    }

    public final Formula getVal3() {
        return this.val3;
    }

    public final void setVal3(Formula formula) {
        this.val3 = formula;
    }

    public final int getParamCount() {
        return this.paramCount;
    }

    public final void setParamCount(int i) {
        this.paramCount = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Float fValueOf;
        Scope scope = this.scope;
        if (scope == null) {
            return true;
        }
        Formula formula = this.bufferName;
        String strInterpretString = formula != null ? formula.interpretString(scope) : null;
        if (strInterpretString == null) {
            return true;
        }
        Formula formula2 = this.uniformName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(scope) : null;
        if (strInterpretString2 == null) {
            return true;
        }
        Formula formula3 = this.val1;
        Float fInterpretFloat = formula3 != null ? formula3.interpretFloat(scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula4 = this.val2;
        Float fInterpretFloat2 = formula4 != null ? formula4.interpretFloat(scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula5 = this.val3;
        Float fInterpretFloat3 = formula5 != null ? formula5.interpretFloat(scope) : null;
        float fFloatValue3 = fInterpretFloat3 != null ? fInterpretFloat3.floatValue() : 0.0f;
        int i = this.paramCount;
        if (i == 1) {
            fValueOf = Float.valueOf(fFloatValue);
        } else if (i == 2) {
            fValueOf = new Vector2(fFloatValue, fFloatValue2);
        } else {
            fValueOf = new Vector3(fFloatValue, fFloatValue2, fFloatValue3);
        }
        RenderTextureManager.setBufferShaderUniform(strInterpretString, strInterpretString2, fValueOf);
        return true;
    }
}
