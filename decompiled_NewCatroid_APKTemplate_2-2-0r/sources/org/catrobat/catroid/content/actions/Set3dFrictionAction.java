package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Set3dFrictionAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Set3dFrictionAction extends TemporalAction {
    public Formula friction;
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
            float fFloatValue = this.friction.interpretFloat(this.scope).floatValue();
            if (strInterpretString.isEmpty()) {
                return;
            }
            Log.d("Friction", "Set friction: " + strInterpretString + ", " + fFloatValue);
            threeDManager.setFriction(strInterpretString, fFloatValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
