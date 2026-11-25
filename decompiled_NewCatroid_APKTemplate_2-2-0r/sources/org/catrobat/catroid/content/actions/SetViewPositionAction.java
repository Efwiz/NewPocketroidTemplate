package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetViewPositionAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetViewPositionAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetViewPositionAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "viewIdFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getViewIdFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setViewIdFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "xFormula", "getXFormula", "setXFormula", "yFormula", "getYFormula", "setYFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetViewPositionAction extends TemporalAction {
    private Scope scope;
    private Formula viewIdFormula;
    private Formula xFormula;
    private Formula yFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewIdFormula() {
        return this.viewIdFormula;
    }

    public final void setViewIdFormula(Formula formula) {
        this.viewIdFormula = formula;
    }

    public final Formula getXFormula() {
        return this.xFormula;
    }

    public final void setXFormula(Formula formula) {
        this.xFormula = formula;
    }

    public final Formula getYFormula() {
        return this.yFormula;
    }

    public final void setYFormula(Formula formula) {
        this.yFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        if (stageActivity == null) {
            Log.e("SetViewPositionAction", "StageActivity is not active.");
            return;
        }
        Formula formula = this.viewIdFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.xFormula;
        Integer numInterpretInteger = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        Formula formula3 = this.yFormula;
        Integer numInterpretInteger2 = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0 || numInterpretInteger == null || numInterpretInteger2 == null) {
            Log.e("SetViewPositionAction", "View ID or coordinates are null.");
        } else {
            stageActivity.setViewPosition(strInterpretString, numInterpretInteger.intValue(), numInterpretInteger2.intValue());
        }
    }
}
