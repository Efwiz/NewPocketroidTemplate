package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AddFileToApkBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AddFileToApkBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_add_file_apk;
    }

    public AddFileToApkBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT_1, R.id.brick_add_file_apk_edit_apk);
        addAllowedBrickField(Brick.BrickField.TEXT_2, R.id.brick_add_file_apk_edit_src);
        addAllowedBrickField(Brick.BrickField.TEXT_3, R.id.brick_add_file_apk_edit_dest);
    }

    public AddFileToApkBrick(String apk, String src, String dest) {
        this(new Formula(apk), new Formula(src), new Formula(dest));
    }

    public AddFileToApkBrick(Formula apk, Formula src, Formula dest) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT_1, apk);
        setFormulaWithBrickField(Brick.BrickField.TEXT_2, src);
        setFormulaWithBrickField(Brick.BrickField.TEXT_3, dest);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAddFileToApkAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT_1), getFormulaWithBrickField(Brick.BrickField.TEXT_2), getFormulaWithBrickField(Brick.BrickField.TEXT_3)));
    }
}
