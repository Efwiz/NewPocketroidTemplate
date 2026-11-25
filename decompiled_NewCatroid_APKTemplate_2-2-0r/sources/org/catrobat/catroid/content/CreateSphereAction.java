package org.catrobat.catroid.content;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/CreateSphereAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateSphereAction extends TemporalAction {
    public Formula objectId;
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
            threeDManager.createSphere(strInterpretString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
