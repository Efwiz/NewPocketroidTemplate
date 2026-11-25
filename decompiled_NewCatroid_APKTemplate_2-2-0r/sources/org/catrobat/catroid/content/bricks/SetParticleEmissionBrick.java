package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetParticleEmissionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetParticleEmissionBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_particle_emission;
    }

    public SetParticleEmissionBrick() {
        addAllowedBrickField(Brick.BrickField.OBJECT_ID, R.id.particle_obj_id);
        addAllowedBrickField(Brick.BrickField.EMISSION_RATE, R.id.particle_rate2);
    }

    public SetParticleEmissionBrick(String id2, float rate) {
        this(new Formula(id2), new Formula(Float.valueOf(rate)));
    }

    public SetParticleEmissionBrick(Formula id2, Formula rate) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_ID, id2);
        setFormulaWithBrickField(Brick.BrickField.EMISSION_RATE, rate);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetParticleEmissionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_ID), getFormulaWithBrickField(Brick.BrickField.EMISSION_RATE)));
    }
}
