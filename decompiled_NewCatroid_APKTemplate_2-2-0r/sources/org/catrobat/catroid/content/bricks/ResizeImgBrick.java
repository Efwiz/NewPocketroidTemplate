package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ResizeImgBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ResizeImgBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_resize_img;
    }

    public ResizeImgBrick() {
        addAllowedBrickField(Brick.BrickField.FILE, R.id.brick_resize_img_file);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_resize_img_x);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_resize_img_y);
    }

    public ResizeImgBrick(String val1, Integer val2, Integer val3) {
        this(new Formula(val1), new Formula(val2), new Formula(val3));
    }

    public ResizeImgBrick(Formula code, Formula x, Formula y) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE, code);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, x);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, y);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createResizeImg(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
