package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Set3dRotationAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Set3dRotationAction extends TemporalAction {
    public Formula objectId;
    public Formula pitch;
    public Formula roll;
    public Scope scope;
    public Formula yaw;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            String strInterpretString = this.objectId.interpretString(this.scope);
            float fFloatValue = this.yaw.interpretFloat(this.scope).floatValue();
            float fFloatValue2 = this.pitch.interpretFloat(this.scope).floatValue();
            float fFloatValue3 = this.roll.interpretFloat(this.scope).floatValue();
            if (threeDManager.hasActiveAI(strInterpretString)) {
                threeDManager.setModelRotationOffset(strInterpretString, fFloatValue, fFloatValue2, fFloatValue3);
            } else {
                threeDManager.setRotation(strInterpretString, fFloatValue, fFloatValue2, fFloatValue3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
