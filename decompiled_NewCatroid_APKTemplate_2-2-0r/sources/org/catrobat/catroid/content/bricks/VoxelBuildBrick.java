package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VoxelBuildBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VoxelBuildBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_voxel_build;
    }

    public VoxelBuildBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_voxel_world_id);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_voxel_atlas_text);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_voxel_atlas_width);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_voxel_atlas_height);
    }

    public VoxelBuildBrick(Formula id2, Formula atlas, Formula w, Formula h) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.TEXT, atlas);
        setFormulaWithBrickField(Brick.BrickField.X, w);
        setFormulaWithBrickField(Brick.BrickField.Y, h);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVoxelBuildAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y)));
    }
}
