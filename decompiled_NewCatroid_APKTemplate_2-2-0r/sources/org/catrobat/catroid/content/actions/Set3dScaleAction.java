package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Set3dScaleAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Set3dScaleAction extends TemporalAction {
    public Formula objectId;
    public Formula scaleX;
    public Formula scaleY;
    public Formula scaleZ;
    public Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            String strInterpretString = this.objectId.interpretString(this.scope);
            if (strInterpretString.isEmpty()) {
                return;
            }
            threeDManager.setScale(strInterpretString, this.scaleX.interpretFloat(this.scope).floatValue(), this.scaleY.interpretFloat(this.scope).floatValue(), this.scaleZ.interpretFloat(this.scope).floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
