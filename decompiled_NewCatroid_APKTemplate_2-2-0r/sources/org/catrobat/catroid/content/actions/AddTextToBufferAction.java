package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AddTextToBufferAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddTextToBufferAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/AddTextToBufferAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "bufferName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBufferName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBufferName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "textName", "getTextName", "setTextName", "act", "", "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddTextToBufferAction extends Action {
    private Formula bufferName;
    private Scope scope;
    private Formula textName;

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

    public final Formula getTextName() {
        return this.textName;
    }

    public final void setTextName(Formula formula) {
        this.textName = formula;
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
        Formula formula2 = this.textName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(scope) : null;
        if (strInterpretString2 == null) {
            return true;
        }
        RenderTextureManager.addVariableTextToTarget(strInterpretString, strInterpretString2);
        return true;
    }
}
