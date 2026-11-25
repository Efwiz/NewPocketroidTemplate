package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsWorld;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetGravityAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetGravityAction extends TemporalAction {
    private Formula gravityX;
    private Formula gravityY;
    private PhysicsWorld physicsWorld;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        try {
            Formula formula = this.gravityX;
            Float fValueOf = formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope);
            try {
                Formula formula2 = this.gravityY;
                this.physicsWorld.setGravity(fValueOf.floatValue(), (formula2 == null ? Float.valueOf(0.0f) : formula2.interpretFloat(this.scope)).floatValue());
            } catch (InterpretationException e) {
                Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            }
        } catch (InterpretationException e2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e2);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPhysicsWorld(PhysicsWorld physicsWorld) {
        this.physicsWorld = physicsWorld;
    }

    public void setGravity(Formula gravityX, Formula gravityY) {
        this.gravityX = gravityX;
        this.gravityY = gravityY;
    }
}
