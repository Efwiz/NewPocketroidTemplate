package org.catrobat.catroid.content.actions;

import org.catrobat.catroid.content.actions.conditional.GlideToAction;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsLook;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GlideToPhysicsAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GlideToPhysicsAction extends GlideToAction {
    private PhysicsLook physicsLook;

    @Override // org.catrobat.catroid.content.actions.conditional.GlideToAction, com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() throws InterpretationException {
        this.physicsLook.startGlide();
        super.begin();
    }

    @Override // org.catrobat.catroid.content.actions.conditional.GlideToAction, com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void end() {
        super.end();
        this.physicsLook.stopGlide();
    }

    public void setPhysicsLook(PhysicsLook physicsLook) {
        this.physicsLook = physicsLook;
    }
}
