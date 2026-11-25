package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteParticlesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteParticlesBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_particles;
    }

    public DeleteParticlesBrick() {
        addAllowedBrickField(Brick.BrickField.PARTICLE_ID, R.id.brick_particle_id_edit);
    }

    public DeleteParticlesBrick(String particleId) {
        this(new Formula(particleId));
    }

    public DeleteParticlesBrick(Formula particleId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ID, particleId);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteParticlesAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PARTICLE_ID)));
    }
}
