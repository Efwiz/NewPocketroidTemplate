package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetNativeParentBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetNativeParentBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_native_parent;
    }

    public SetNativeParentBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_native_parent_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_native_parent_target_field);
    }

    public SetNativeParentBrick(String viewId, String parentId) {
        this(new Formula(viewId), new Formula(parentId));
    }

    public SetNativeParentBrick(Formula viewId, Formula parentId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, viewId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, parentId);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetNativeParentAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
