package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VoxelSetBlockBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VoxelSetBlockBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_voxel_set_block;
    }

    public VoxelSetBlockBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_voxel_set_edit_world);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_voxel_set_edit_x);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_voxel_set_edit_y);
        addAllowedBrickField(Brick.BrickField.Z, R.id.brick_voxel_set_edit_z);
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_voxel_set_edit_type);
        addAllowedBrickField(Brick.BrickField.DATA, R.id.brick_voxel_set_edit_data);
    }

    public VoxelSetBlockBrick(Formula worldId, Formula x, Formula y, Formula z, Formula type, Formula data) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, worldId);
        setFormulaWithBrickField(Brick.BrickField.X, x);
        setFormulaWithBrickField(Brick.BrickField.Y, y);
        setFormulaWithBrickField(Brick.BrickField.Z, z);
        setFormulaWithBrickField(Brick.BrickField.VALUE, type);
        setFormulaWithBrickField(Brick.BrickField.DATA, data);
    }

    public VoxelSetBlockBrick(String worldId, double x, double y, double z, int type, int data) {
        this(new Formula(worldId), new Formula(Double.valueOf(x)), new Formula(Double.valueOf(y)), new Formula(Double.valueOf(z)), new Formula(Integer.valueOf(type)), new Formula(Integer.valueOf(data)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVoxelSetBlockAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y), getFormulaWithBrickField(Brick.BrickField.Z), getFormulaWithBrickField(Brick.BrickField.VALUE), getFormulaWithBrickField(Brick.BrickField.DATA)));
    }
}
