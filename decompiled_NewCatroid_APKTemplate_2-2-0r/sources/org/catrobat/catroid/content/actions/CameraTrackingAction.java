package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CameraTrackingAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CameraTrackingAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\f\"\u0004\b\u001d\u0010\u000eR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000eR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/content/actions/CameraTrackingAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "mode", "", "getMode", "()I", "setMode", "(I)V", "objectId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getObjectId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setObjectId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "px", "getPx", "setPx", "py", "getPy", "setPy", "pz", "getPz", "setPz", "rp", "getRp", "setRp", "rr", "getRr", "setRr", "ry", "getRy", "setRy", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CameraTrackingAction extends TemporalAction {
    private int mode;
    private Formula objectId;
    private Formula px;
    private Formula py;
    private Formula pz;
    private Formula rp;
    private Formula rr;
    private Formula ry;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectId() {
        return this.objectId;
    }

    public final void setObjectId(Formula formula) {
        this.objectId = formula;
    }

    public final int getMode() {
        return this.mode;
    }

    public final void setMode(int i) {
        this.mode = i;
    }

    public final Formula getPx() {
        return this.px;
    }

    public final void setPx(Formula formula) {
        this.px = formula;
    }

    public final Formula getPy() {
        return this.py;
    }

    public final void setPy(Formula formula) {
        this.py = formula;
    }

    public final Formula getPz() {
        return this.pz;
    }

    public final void setPz(Formula formula) {
        this.pz = formula;
    }

    public final Formula getRy() {
        return this.ry;
    }

    public final void setRy(Formula formula) {
        this.ry = formula;
    }

    public final Formula getRp() {
        return this.rp;
    }

    public final void setRp(Formula formula) {
        this.rp = formula;
    }

    public final Formula getRr() {
        return this.rr;
    }

    public final void setRr(Formula formula) {
        this.rr = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.objectId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.px;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula3 = this.py;
        Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula4 = this.pz;
        Float fInterpretFloat3 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula5 = this.ry;
        Float fInterpretFloat4 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
        Formula formula6 = this.rp;
        Float fInterpretFloat5 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula7 = this.rr;
        Float fInterpretFloat6 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue6 = fInterpretFloat6 == null ? 0.0f : fInterpretFloat6.floatValue();
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        SceneManager sceneManager = activeStageListener != null ? activeStageListener.sceneManager : null;
        if (sceneManager == null) {
            return;
        }
        sceneManager.setCameraTracking(strInterpretString, this.mode, fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4, fFloatValue5, fFloatValue6);
    }
}
