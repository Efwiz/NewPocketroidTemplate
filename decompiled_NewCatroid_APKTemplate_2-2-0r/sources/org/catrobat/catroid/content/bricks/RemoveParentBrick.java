package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RemoveParentBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RemoveParentBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_remove_parent;
    }

    public RemoveParentBrick() {
        addAllowedBrickField(Brick.BrickField.CHILD_OBJECT, R.id.brick_remove_parent_object_edit);
    }

    public RemoveParentBrick(String childObjectName) {
        this(new Formula(childObjectName));
    }

    public RemoveParentBrick(Formula childObjectFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CHILD_OBJECT, childObjectFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRemoveParentAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CHILD_OBJECT)));
    }
}
