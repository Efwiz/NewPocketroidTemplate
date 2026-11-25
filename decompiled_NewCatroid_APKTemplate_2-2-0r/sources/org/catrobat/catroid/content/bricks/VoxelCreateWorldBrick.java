package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VoxelCreateWorldBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VoxelCreateWorldBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_voxel_create_world;
    }

    public VoxelCreateWorldBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_voxel_world_id);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_voxel_size_x);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_voxel_size_y);
        addAllowedBrickField(Brick.BrickField.Z, R.id.brick_voxel_size_z);
        addAllowedBrickField(Brick.BrickField.X_1, R.id.brick_voxel_world_x);
        addAllowedBrickField(Brick.BrickField.Y_1, R.id.brick_voxel_world_y);
        addAllowedBrickField(Brick.BrickField.Z_1, R.id.brick_voxel_world_z);
    }

    public VoxelCreateWorldBrick(Formula id2, Formula sx, Formula sy, Formula sz, Formula wx, Formula wy, Formula wz) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X, sx);
        setFormulaWithBrickField(Brick.BrickField.Y, sy);
        setFormulaWithBrickField(Brick.BrickField.Z, sz);
        setFormulaWithBrickField(Brick.BrickField.X_1, wx);
        setFormulaWithBrickField(Brick.BrickField.Y_1, wy);
        setFormulaWithBrickField(Brick.BrickField.Z_1, wz);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVoxelCreateWorldAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y), getFormulaWithBrickField(Brick.BrickField.Z), getFormulaWithBrickField(Brick.BrickField.X_1), getFormulaWithBrickField(Brick.BrickField.Y_1), getFormulaWithBrickField(Brick.BrickField.Z_1)));
    }
}
