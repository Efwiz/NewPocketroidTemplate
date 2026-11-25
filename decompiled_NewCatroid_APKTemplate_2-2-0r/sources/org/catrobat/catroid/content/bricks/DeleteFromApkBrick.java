package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteFromApkBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteFromApkBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_from_apk;
    }

    public DeleteFromApkBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT_1, R.id.brick_del_apk_edit_path);
        addAllowedBrickField(Brick.BrickField.TEXT_2, R.id.brick_del_apk_edit_pattern);
    }

    public DeleteFromApkBrick(String apk, String pattern) {
        this(new Formula(apk), new Formula(pattern));
    }

    public DeleteFromApkBrick(Formula apk, Formula pattern) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT_1, apk);
        setFormulaWithBrickField(Brick.BrickField.TEXT_2, pattern);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteFromApkAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT_1), getFormulaWithBrickField(Brick.BrickField.TEXT_2)));
    }
}
