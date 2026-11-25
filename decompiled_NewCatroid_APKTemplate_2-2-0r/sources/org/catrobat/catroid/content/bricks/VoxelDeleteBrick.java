package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VoxelDeleteBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VoxelDeleteBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_voxel_delete;
    }

    public VoxelDeleteBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_voxel_delete_edit_id);
    }

    public VoxelDeleteBrick(Formula id2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVoxelDeleteAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME)));
    }
}
