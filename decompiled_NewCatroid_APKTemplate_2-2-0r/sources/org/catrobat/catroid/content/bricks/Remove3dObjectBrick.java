package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Remove3dObjectBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Remove3dObjectBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_remove_3d_object;
    }

    public Remove3dObjectBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_remove_3d_object_id);
    }

    public Remove3dObjectBrick(String objectId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, new Formula(objectId));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRemove3dObjectAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}
