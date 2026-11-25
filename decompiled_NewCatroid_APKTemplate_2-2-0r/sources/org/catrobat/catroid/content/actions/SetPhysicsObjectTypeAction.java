package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.physics.PhysicsObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetPhysicsObjectTypeAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPhysicsObjectTypeAction extends TemporalAction {
    private PhysicsObject physicsObject;
    private PhysicsObject.Type type = PhysicsObject.Type.NONE;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        this.physicsObject.setType(this.type);
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setType(PhysicsObject.Type type) {
        this.type = type;
    }
}
