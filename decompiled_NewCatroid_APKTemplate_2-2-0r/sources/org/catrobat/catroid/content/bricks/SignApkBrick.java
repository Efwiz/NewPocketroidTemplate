package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SignApkBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SignApkBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_sign_apk;
    }

    public SignApkBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT_1, R.id.brick_sign_apk_edit_in);
        addAllowedBrickField(Brick.BrickField.TEXT_2, R.id.brick_sign_apk_edit_out);
        addAllowedBrickField(Brick.BrickField.TEXT_3, R.id.brick_sign_apk_edit_key);
        addAllowedBrickField(Brick.BrickField.TEXT_4, R.id.brick_sign_apk_edit_pass);
        addAllowedBrickField(Brick.BrickField.TEXT_5, R.id.brick_sign_apk_edit_alias);
    }

    public SignApkBrick(String input, String output, String key, String pass, String alias) {
        this(new Formula(input), new Formula(output), new Formula(key), new Formula(pass), new Formula(alias));
    }

    public SignApkBrick(Formula input, Formula output, Formula key, Formula pass, Formula alias) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT_1, input);
        setFormulaWithBrickField(Brick.BrickField.TEXT_2, output);
        setFormulaWithBrickField(Brick.BrickField.TEXT_3, key);
        setFormulaWithBrickField(Brick.BrickField.TEXT_4, pass);
        setFormulaWithBrickField(Brick.BrickField.TEXT_5, alias);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSignApkAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT_1), getFormulaWithBrickField(Brick.BrickField.TEXT_2), getFormulaWithBrickField(Brick.BrickField.TEXT_3), getFormulaWithBrickField(Brick.BrickField.TEXT_4), getFormulaWithBrickField(Brick.BrickField.TEXT_5)));
    }
}
