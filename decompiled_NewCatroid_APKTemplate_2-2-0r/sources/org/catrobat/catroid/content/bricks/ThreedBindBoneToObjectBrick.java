package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ThreedBindBoneToObjectBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThreedBindBoneToObjectBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_threed_bind_bone_to_object;
    }

    public ThreedBindBoneToObjectBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_bind_bone_name);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_bind_model_id);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_bind_target_id);
    }

    public ThreedBindBoneToObjectBrick(String boneName, String modelId, String targetId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(boneName));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(modelId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(targetId));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThreedBindBoneToObjectAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
