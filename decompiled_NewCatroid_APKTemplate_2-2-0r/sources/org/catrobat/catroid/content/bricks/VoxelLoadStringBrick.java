package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VoxelLoadStringBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VoxelLoadStringBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_voxel_load_string;
    }

    public VoxelLoadStringBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_voxel_load_id);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_voxel_load_data);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_voxel_load_dx);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_voxel_load_dy);
        addAllowedBrickField(Brick.BrickField.Z, R.id.brick_voxel_load_dz);
    }

    public VoxelLoadStringBrick(Formula id2, Formula data, Formula dx, Formula dy, Formula dz) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.TEXT, data);
        setFormulaWithBrickField(Brick.BrickField.X, dx);
        setFormulaWithBrickField(Brick.BrickField.Y, dy);
        setFormulaWithBrickField(Brick.BrickField.Z, dz);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVoxelLoadStringAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y), getFormulaWithBrickField(Brick.BrickField.Z)));
    }
}
