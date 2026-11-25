package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/GrayscaleImgBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GrayscaleImgBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_grayscale_img;
    }

    public GrayscaleImgBrick() {
        addAllowedBrickField(Brick.BrickField.FILE, R.id.brick_grayscale_img_file);
    }

    public GrayscaleImgBrick(String val1) {
        this(new Formula(val1));
    }

    public GrayscaleImgBrick(Formula code) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE, code);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGrayscaleImg(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE)));
    }
}
