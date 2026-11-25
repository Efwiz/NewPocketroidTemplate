package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetMassAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetMassAction extends TemporalAction {
    private Formula mass;
    private PhysicsObject physicsObject;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        try {
            Formula formula = this.mass;
            this.physicsObject.setMass((formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope)).floatValue());
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setMass(Formula mass) {
        this.mass = mass;
    }
}
