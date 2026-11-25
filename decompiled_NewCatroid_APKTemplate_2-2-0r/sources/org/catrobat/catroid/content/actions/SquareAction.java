package org.catrobat.catroid.content.actions;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.SquareController;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SquareAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SquareAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\b¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/content/actions/SquareAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "bord", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBord", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBord", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", TypedValues.Custom.S_COLOR, "getColor", "setColor", "h", "getH", "setH", "nam", "getNam", "setNam", "rot", "getRot", "setRot", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "trans", "getTrans", "setTrans", "w", "getW", "setW", "x", "getX", "setX", "y", "getY", "setY", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SquareAction extends TemporalAction {
    private Formula bord;
    private Formula color;
    private Formula h;
    private Formula nam;
    private Formula rot;
    private Scope scope;
    private Formula trans;
    private Formula w;
    private Formula x;
    private Formula y;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getX() {
        return this.x;
    }

    public final void setX(Formula formula) {
        this.x = formula;
    }

    public final Formula getY() {
        return this.y;
    }

    public final void setY(Formula formula) {
        this.y = formula;
    }

    public final Formula getW() {
        return this.w;
    }

    public final void setW(Formula formula) {
        this.w = formula;
    }

    public final Formula getH() {
        return this.h;
    }

    public final void setH(Formula formula) {
        this.h = formula;
    }

    public final Formula getColor() {
        return this.color;
    }

    public final void setColor(Formula formula) {
        this.color = formula;
    }

    public final Formula getNam() {
        return this.nam;
    }

    public final void setNam(Formula formula) {
        this.nam = formula;
    }

    public final Formula getRot() {
        return this.rot;
    }

    public final void setRot(Formula formula) {
        this.rot = formula;
    }

    public final Formula getTrans() {
        return this.trans;
    }

    public final void setTrans(Formula formula) {
        this.trans = formula;
    }

    public final Formula getBord() {
        return this.bord;
    }

    public final void setBord(Formula formula) {
        this.bord = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.x;
        Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula2 = this.y;
        Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula3 = this.w;
        Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 100.0f : fInterpretFloat3.floatValue();
        Formula formula4 = this.h;
        Float fInterpretFloat4 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 100.0f : fInterpretFloat4.floatValue();
        Formula formula5 = this.color;
        String strInterpretString = formula5 != null ? formula5.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "#ff0000";
        }
        String str = strInterpretString;
        Formula formula6 = this.nam;
        String strInterpretString2 = formula6 != null ? formula6.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "square";
        }
        String str2 = strInterpretString2;
        Formula formula7 = this.rot;
        Float fInterpretFloat5 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula8 = this.trans;
        Float fInterpretFloat6 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        float fFloatValue6 = fInterpretFloat6 == null ? 0.0f : fInterpretFloat6.floatValue();
        Formula formula9 = this.bord;
        Float fInterpretFloat7 = formula9 != null ? formula9.interpretFloat(this.scope) : null;
        float fFloatValue7 = fInterpretFloat7 != null ? fInterpretFloat7.floatValue() : 0.0f;
        Stage stage = StageActivity.getActiveStageListener().getStage();
        if (stage != null) {
            SquareController.INSTANCE.getInstance().createOrUpdateSquare(str2, fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4, str, fFloatValue5, fFloatValue6, fFloatValue7, stage);
        }
    }
}
