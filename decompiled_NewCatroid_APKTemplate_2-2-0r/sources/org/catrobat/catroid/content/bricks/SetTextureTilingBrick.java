package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetTextureTilingBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetTextureTilingBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_texture_tiling;
    }

    public SetTextureTilingBrick() {
        addAllowedBrickField(Brick.BrickField.OBJECT_ID, R.id.tiling_obj_id);
        addAllowedBrickField(Brick.BrickField.X, R.id.tiling_u);
        addAllowedBrickField(Brick.BrickField.Y, R.id.tiling_v);
    }

    public SetTextureTilingBrick(String objId, float u, float v) {
        this(new Formula(objId), new Formula(Float.valueOf(u)), new Formula(Float.valueOf(v)));
    }

    public SetTextureTilingBrick(Formula objId, Formula u, Formula v) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_ID, objId);
        setFormulaWithBrickField(Brick.BrickField.X, u);
        setFormulaWithBrickField(Brick.BrickField.Y, v);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetTextureTilingAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_ID), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y)));
    }
}
