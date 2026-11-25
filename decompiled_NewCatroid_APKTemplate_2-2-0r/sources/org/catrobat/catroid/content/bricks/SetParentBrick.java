package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetParentBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetParentBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_parent;
    }

    public SetParentBrick() {
        addAllowedBrickField(Brick.BrickField.CHILD_OBJECT, R.id.brick_set_parent_child_edit);
        addAllowedBrickField(Brick.BrickField.PARENT_OBJECT, R.id.brick_set_parent_parent_edit);
    }

    public SetParentBrick(String child, String parent) {
        this(new Formula(child), new Formula(parent));
    }

    public SetParentBrick(Formula child, Formula parent) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CHILD_OBJECT, child);
        setFormulaWithBrickField(Brick.BrickField.PARENT_OBJECT, parent);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetParentAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CHILD_OBJECT), getFormulaWithBrickField(Brick.BrickField.PARENT_OBJECT)));
    }
}
