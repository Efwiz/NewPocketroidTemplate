package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetPhysicsStateAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPhysicsStateAction extends TemporalAction {
    public Formula mass;
    public Formula objectId;
    public Scope scope;
    public int shapeSelection;
    public int stateSelection;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager.PhysicsState physicsState;
        ThreeDManager.PhysicsShape physicsShape;
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            String strInterpretString = this.objectId.interpretString(this.scope);
            if (strInterpretString.isEmpty()) {
                return;
            }
            float fFloatValue = this.mass.interpretFloat(this.scope).floatValue();
            int i = this.stateSelection;
            if (i == 0) {
                physicsState = ThreeDManager.PhysicsState.NONE;
            } else if (i == 1) {
                physicsState = ThreeDManager.PhysicsState.STATIC;
            } else if (i == 2) {
                physicsState = ThreeDManager.PhysicsState.DYNAMIC;
            } else if (i == 3) {
                physicsState = ThreeDManager.PhysicsState.MESH_STATIC;
            } else if (i != 4) {
                return;
            } else {
                physicsState = ThreeDManager.PhysicsState.KINEMATIC;
            }
            int i2 = this.shapeSelection;
            if (i2 == 1) {
                physicsShape = ThreeDManager.PhysicsShape.SPHERE;
            } else if (i2 == 2) {
                physicsShape = ThreeDManager.PhysicsShape.CAPSULE;
            } else {
                physicsShape = ThreeDManager.PhysicsShape.BOX;
            }
            threeDManager.setPhysicsState(strInterpretString, physicsState, physicsShape, fFloatValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
