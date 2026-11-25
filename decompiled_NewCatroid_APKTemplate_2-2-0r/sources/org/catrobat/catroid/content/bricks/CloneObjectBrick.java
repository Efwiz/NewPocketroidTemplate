package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CloneObjectBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CloneObjectBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_clone_object;
    }

    public CloneObjectBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_clone_source_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_clone_new_name_edit);
    }

    public CloneObjectBrick(String sourceName, String newName) {
        this(new Formula(sourceName), new Formula(newName));
    }

    public CloneObjectBrick(Formula source, Formula newName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, source);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, newName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCloneObjectAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
