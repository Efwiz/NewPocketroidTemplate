package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PlaySpritesheetAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PlaySpritesheetAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/actions/PlaySpritesheetAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "cols", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCols", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCols", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "framesCount", "getFramesCount", "setFramesCount", "rows", "getRows", "setRows", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "selectedRow", "getSelectedRow", "setSelectedRow", "speed", "getSpeed", "setSpeed", "reset", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlaySpritesheetAction extends TemporalAction {
    private Formula cols;
    private Formula framesCount;
    private Formula rows;
    private Scope scope;
    private Formula selectedRow;
    private Formula speed;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRows() {
        return this.rows;
    }

    public final void setRows(Formula formula) {
        this.rows = formula;
    }

    public final Formula getCols() {
        return this.cols;
    }

    public final void setCols(Formula formula) {
        this.cols = formula;
    }

    public final Formula getSelectedRow() {
        return this.selectedRow;
    }

    public final void setSelectedRow(Formula formula) {
        this.selectedRow = formula;
    }

    public final Formula getFramesCount() {
        return this.framesCount;
    }

    public final void setFramesCount(Formula formula) {
        this.framesCount = formula;
    }

    public final Formula getSpeed() {
        return this.speed;
    }

    public final void setSpeed(Formula formula) {
        this.speed = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Sprite sprite;
        Look look;
        Scope scope = this.scope;
        if (scope == null || (sprite = scope.getSprite()) == null || (look = sprite.look) == null) {
            return;
        }
        Formula formula = this.rows;
        Integer numInterpretInteger = formula != null ? formula.interpretInteger(this.scope) : null;
        int iIntValue = numInterpretInteger == null ? 1 : numInterpretInteger.intValue();
        Formula formula2 = this.cols;
        Integer numInterpretInteger2 = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        int iIntValue2 = numInterpretInteger2 == null ? 1 : numInterpretInteger2.intValue();
        Formula formula3 = this.selectedRow;
        Integer numInterpretInteger3 = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula4 = this.framesCount;
        Integer numInterpretInteger4 = formula4 != null ? formula4.interpretInteger(this.scope) : null;
        int iIntValue4 = numInterpretInteger4 != null ? numInterpretInteger4.intValue() : 1;
        Formula formula5 = this.speed;
        Float fInterpretFloat = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        look.playSpritesheet(iIntValue, iIntValue2, iIntValue3, iIntValue4, fInterpretFloat == null ? 0.1f : fInterpretFloat.floatValue());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.scope = null;
        this.rows = null;
        this.cols = null;
        this.selectedRow = null;
        this.framesCount = null;
        this.speed = null;
    }
}
