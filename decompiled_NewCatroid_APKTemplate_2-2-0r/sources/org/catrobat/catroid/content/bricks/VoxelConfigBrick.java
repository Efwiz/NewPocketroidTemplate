package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VoxelConfigBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VoxelConfigBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_voxel_config;
    }

    public VoxelConfigBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_voxel_config_edit_id);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_voxel_config_edit_shape);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_voxel_config_edit_tx);
        addAllowedBrickField(Brick.BrickField.Z, R.id.brick_voxel_config_edit_ty);
    }

    public VoxelConfigBrick(Formula id2, Formula shape, Formula tx, Formula ty) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X, shape);
        setFormulaWithBrickField(Brick.BrickField.Y, tx);
        setFormulaWithBrickField(Brick.BrickField.Z, ty);
    }

    public VoxelConfigBrick(String id2, int shape, int tx, int ty) {
        this(new Formula(id2), new Formula(Integer.valueOf(shape)), new Formula(Integer.valueOf(tx)), new Formula(Integer.valueOf(ty)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVoxelConfigAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y), getFormulaWithBrickField(Brick.BrickField.Z)));
    }
}
