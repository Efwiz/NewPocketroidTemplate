package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ZigZagStitchBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ZigZagStitchBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_zigzag_stitch;
    }

    public ZigZagStitchBrick() {
        addAllowedBrickField(Brick.BrickField.ZIGZAG_EMBROIDERY_LENGTH, R.id.brick_zigzag_stitch_edit_text_length);
        addAllowedBrickField(Brick.BrickField.ZIGZAG_EMBROIDERY_WIDTH, R.id.brick_zigzag_stitch_edit_text_width);
    }

    public ZigZagStitchBrick(Formula length, Formula width) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ZIGZAG_EMBROIDERY_LENGTH, length);
        setFormulaWithBrickField(Brick.BrickField.ZIGZAG_EMBROIDERY_WIDTH, width);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.ZIGZAG_EMBROIDERY_LENGTH;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createZigZagStitchAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ZIGZAG_EMBROIDERY_LENGTH), getFormulaWithBrickField(Brick.BrickField.ZIGZAG_EMBROIDERY_WIDTH)));
    }
}
