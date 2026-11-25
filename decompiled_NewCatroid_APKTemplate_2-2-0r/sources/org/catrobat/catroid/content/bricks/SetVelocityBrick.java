package org.catrobat.catroid.content.bricks;

import com.badlogic.gdx.math.Vector2;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetVelocityBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetVelocityBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_physics_set_velocity;
    }

    public SetVelocityBrick() {
        addAllowedBrickField(Brick.BrickField.PHYSICS_VELOCITY_X, R.id.brick_set_velocity_edit_text_x);
        addAllowedBrickField(Brick.BrickField.PHYSICS_VELOCITY_Y, R.id.brick_set_velocity_edit_text_y);
    }

    public SetVelocityBrick(Vector2 velocity) {
        this(new Formula(Float.valueOf(velocity.x)), new Formula(Float.valueOf(velocity.y)));
    }

    public SetVelocityBrick(Formula velocityX, Formula velocityY) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PHYSICS_VELOCITY_X, velocityX);
        setFormulaWithBrickField(Brick.BrickField.PHYSICS_VELOCITY_Y, velocityY);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 3);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.PHYSICS_VELOCITY_X;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetVelocityAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PHYSICS_VELOCITY_X), getFormulaWithBrickField(Brick.BrickField.PHYSICS_VELOCITY_Y)));
    }
}
