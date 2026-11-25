package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetDirectionalLightAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetDirectionalLightAction extends TemporalAction {
    public Formula blue;
    public Formula dirX;
    public Formula dirY;
    public Formula dirZ;
    public Formula green;
    public Formula lightId;
    public Formula red;
    public Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            String strInterpretString = this.lightId.interpretString(this.scope);
            if (strInterpretString.isEmpty()) {
                return;
            }
            threeDManager.setDirectionalLight(strInterpretString, this.red.interpretFloat(this.scope).floatValue(), this.green.interpretFloat(this.scope).floatValue(), this.blue.interpretFloat(this.scope).floatValue(), this.dirX.interpretFloat(this.scope).floatValue(), this.dirY.interpretFloat(this.scope).floatValue(), this.dirZ.interpretFloat(this.scope).floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
