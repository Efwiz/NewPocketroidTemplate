package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/GenerateKeyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GenerateKeyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_generate_key;
    }

    public GenerateKeyBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT_1, R.id.brick_gen_key_edit_filename);
        addAllowedBrickField(Brick.BrickField.TEXT_2, R.id.brick_gen_key_edit_pass);
        addAllowedBrickField(Brick.BrickField.TEXT_3, R.id.brick_gen_key_edit_alias);
        addAllowedBrickField(Brick.BrickField.TEXT_4, R.id.brick_gen_key_edit_name);
    }

    public GenerateKeyBrick(String filename, String pass, String alias, String name2) {
        this(new Formula(filename), new Formula(pass), new Formula(alias), new Formula(name2));
    }

    public GenerateKeyBrick(Formula filename, Formula pass, Formula alias, Formula name2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT_1, filename);
        setFormulaWithBrickField(Brick.BrickField.TEXT_2, pass);
        setFormulaWithBrickField(Brick.BrickField.TEXT_3, alias);
        setFormulaWithBrickField(Brick.BrickField.TEXT_4, name2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGenerateKeyAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT_1), getFormulaWithBrickField(Brick.BrickField.TEXT_2), getFormulaWithBrickField(Brick.BrickField.TEXT_3), getFormulaWithBrickField(Brick.BrickField.TEXT_4)));
    }
}
