package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CameraLookAtAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CameraLookAtAction extends TemporalAction {
    public Scope scope;
    public Formula xValue;
    public Formula yValue;
    public Formula zValue;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            threeDManager.cameraLookAt(this.xValue.interpretFloat(this.scope).floatValue(), this.yValue.interpretFloat(this.scope).floatValue(), this.zValue.interpretFloat(this.scope).floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
