package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetFrictionAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetFrictionAction extends TemporalAction {
    private Formula friction;
    private PhysicsObject physicsObject;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        try {
            Formula formula = this.friction;
            this.physicsObject.setFriction((formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope)).floatValue() / 100.0f);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setFriction(Formula friction) {
        this.friction = friction;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
