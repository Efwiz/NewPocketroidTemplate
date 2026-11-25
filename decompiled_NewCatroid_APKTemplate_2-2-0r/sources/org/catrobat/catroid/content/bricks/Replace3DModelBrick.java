package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Replace3DModelBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Replace3DModelBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_replace_3d_model;
    }

    public Replace3DModelBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_replace_3d_model_edit_object_id);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_replace_3d_model_edit_model_path);
    }

    public Replace3DModelBrick(String objectId, String modelPath) {
        this(new Formula(objectId), new Formula(modelPath));
    }

    public Replace3DModelBrick(Formula objectId, Formula modelPath) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, objectId);
        setFormulaWithBrickField(Brick.BrickField.TEXT, modelPath);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createReplace3DModelAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT)));
    }
}
