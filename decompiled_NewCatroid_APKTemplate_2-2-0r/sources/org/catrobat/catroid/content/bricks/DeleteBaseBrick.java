package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteBaseBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteBaseBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_base;
    }

    public DeleteBaseBrick() {
        addAllowedBrickField(Brick.BrickField.FIREBASE_ID, R.id.brick_delete_base_edit_base);
        addAllowedBrickField(Brick.BrickField.FIREBASE_KEY, R.id.brick_delete_base_edit_key);
    }

    public DeleteBaseBrick(String base, String key) {
        this(new Formula(base), new Formula(key));
    }

    public DeleteBaseBrick(Formula base, Formula key) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FIREBASE_ID, base);
        setFormulaWithBrickField(Brick.BrickField.FIREBASE_KEY, key);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteBaseAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FIREBASE_ID), getFormulaWithBrickField(Brick.BrickField.FIREBASE_KEY)));
    }
}
