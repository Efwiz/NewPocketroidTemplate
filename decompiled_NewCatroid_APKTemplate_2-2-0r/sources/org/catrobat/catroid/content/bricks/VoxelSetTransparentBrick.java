package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/VoxelSetTransparentBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VoxelSetTransparentBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_voxel_set_transparent;
    }

    public VoxelSetTransparentBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_voxel_transp_edit_id);
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_voxel_transp_edit_value);
    }

    public VoxelSetTransparentBrick(Formula blockId, Formula isTransparent) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, blockId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, isTransparent);
    }

    public VoxelSetTransparentBrick(int blockId, int isTransparent) {
        this(new Formula(Integer.valueOf(blockId)), new Formula(Integer.valueOf(isTransparent)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVoxelSetTransparentAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE), getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}
