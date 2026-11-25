package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ThreedAttachObjectToBoneBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThreedAttachObjectToBoneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_threed_attach_object_to_bone;
    }

    public ThreedAttachObjectToBoneBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_attach_child_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_attach_parent_id);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_attach_bone_name);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_attach_ox);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_attach_oy);
        addAllowedBrickField(Brick.BrickField.Z, R.id.brick_attach_oz);
    }

    public ThreedAttachObjectToBoneBrick(String childId, String parentId, String boneName, double ox, double oy, double oz) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(childId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(parentId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(boneName));
        setFormulaWithBrickField(Brick.BrickField.X, new Formula(Double.valueOf(ox)));
        setFormulaWithBrickField(Brick.BrickField.Y, new Formula(Double.valueOf(oy)));
        setFormulaWithBrickField(Brick.BrickField.Z, new Formula(Double.valueOf(oz)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThreedAttachObjectToBoneAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y), getFormulaWithBrickField(Brick.BrickField.Z)));
    }
}
