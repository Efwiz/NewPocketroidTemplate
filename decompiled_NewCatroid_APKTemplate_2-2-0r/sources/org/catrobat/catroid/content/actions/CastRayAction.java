package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CastRayAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CastRayAction extends TemporalAction {
    public Formula dirX;
    public Formula dirY;
    public Formula dirZ;
    public Formula fromX;
    public Formula fromY;
    public Formula fromZ;
    public Formula rayName;
    public Scope scope;
    private static final Vector3 tmpFrom = new Vector3();
    private static final Vector3 tmpDir = new Vector3();

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            String strInterpretString = this.rayName.interpretString(this.scope);
            if (strInterpretString.isEmpty()) {
                return;
            }
            Vector3 vector3 = tmpFrom;
            vector3.set(this.fromX.interpretFloat(this.scope).floatValue(), this.fromY.interpretFloat(this.scope).floatValue(), this.fromZ.interpretFloat(this.scope).floatValue());
            Vector3 vector4 = tmpDir;
            vector4.set(this.dirX.interpretFloat(this.scope).floatValue(), this.dirY.interpretFloat(this.scope).floatValue(), this.dirZ.interpretFloat(this.scope).floatValue()).nor();
            threeDManager.castRay(strInterpretString, vector3, vector4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
