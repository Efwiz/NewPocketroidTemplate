package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/NormalizeImgBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NormalizeImgBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_normalize_img;
    }

    public NormalizeImgBrick() {
        addAllowedBrickField(Brick.BrickField.FILE, R.id.brick_normalize_img_file);
        addAllowedBrickField(Brick.BrickField.RED, R.id.brick_normalize_img_r);
        addAllowedBrickField(Brick.BrickField.GREEN, R.id.brick_normalize_img_g);
        addAllowedBrickField(Brick.BrickField.BLUE, R.id.brick_normalize_img_b);
    }

    public NormalizeImgBrick(String val1, String val2, String val3, String val4) {
        this(new Formula(val1), new Formula(val2), new Formula(val3), new Formula(val4));
    }

    public NormalizeImgBrick(Formula code, Formula r, Formula g, Formula b) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE, code);
        setFormulaWithBrickField(Brick.BrickField.RED, r);
        setFormulaWithBrickField(Brick.BrickField.GREEN, g);
        setFormulaWithBrickField(Brick.BrickField.BLUE, b);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createNormalizeImg(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE), getFormulaWithBrickField(Brick.BrickField.RED), getFormulaWithBrickField(Brick.BrickField.GREEN), getFormulaWithBrickField(Brick.BrickField.BLUE)));
    }
}
