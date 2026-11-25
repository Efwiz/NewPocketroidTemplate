package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ThreedCreateFixedConstraintAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThreedCreateFixedConstraintAction extends TemporalAction {
    public Formula ax;
    public Formula ay;
    public Formula az;
    public Formula bx;
    public Formula by;
    public Formula bz;
    public Formula constraintId;
    public int mode;
    public Formula objA;
    public Formula objB;
    public Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            String strInterpretString = this.constraintId.interpretString(this.scope);
            String strInterpretString2 = this.objA.interpretString(this.scope);
            String strInterpretString3 = this.objB.interpretString(this.scope);
            if (!strInterpretString.isEmpty() && !strInterpretString2.isEmpty() && !strInterpretString3.isEmpty()) {
                if (this.mode == 0) {
                    threeDManager.createFixedConstraintWeld(strInterpretString, strInterpretString2, strInterpretString3);
                } else {
                    threeDManager.createFixedConstraintManual(strInterpretString, strInterpretString2, strInterpretString3, this.ax.interpretFloat(this.scope).floatValue(), this.ay.interpretFloat(this.scope).floatValue(), this.az.interpretFloat(this.scope).floatValue(), this.bx.interpretFloat(this.scope).floatValue(), this.by.interpretFloat(this.scope).floatValue(), this.bz.interpretFloat(this.scope).floatValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
