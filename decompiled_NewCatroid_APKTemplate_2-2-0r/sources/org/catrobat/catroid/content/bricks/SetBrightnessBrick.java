package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBrightnessBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBrightnessBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_brightness;
    }

    public SetBrightnessBrick() {
        addAllowedBrickField(Brick.BrickField.BRIGHTNESS, R.id.brick_set_brightness_edit_text);
    }

    public SetBrightnessBrick(double brightness) {
        this(new Formula(Double.valueOf(brightness)));
    }

    public SetBrightnessBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.BRIGHTNESS, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBrightnessAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.BRIGHTNESS)));
    }
}
