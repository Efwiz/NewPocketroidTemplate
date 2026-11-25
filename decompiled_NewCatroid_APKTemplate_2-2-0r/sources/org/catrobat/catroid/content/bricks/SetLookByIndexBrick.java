package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetLookByIndexBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetLookByIndexBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_look_by_index;
    }

    public SetLookByIndexBrick() {
        addAllowedBrickField(Brick.BrickField.LOOK_INDEX, R.id.brick_set_look_by_index_edit_text);
    }

    public SetLookByIndexBrick(int index) {
        this(new Formula(Integer.valueOf(index)));
    }

    public SetLookByIndexBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.LOOK_INDEX, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookByIndexAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.LOOK_INDEX)));
    }
}
