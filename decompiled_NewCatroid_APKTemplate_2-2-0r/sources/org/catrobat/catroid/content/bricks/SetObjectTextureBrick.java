package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetObjectTextureBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetObjectTextureBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_object_texture;
    }

    public SetObjectTextureBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_object_texture_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_object_texture_file);
    }

    public SetObjectTextureBrick(String objectId, String modelPath) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, new Formula(objectId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(modelPath));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetObjectTextureAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
