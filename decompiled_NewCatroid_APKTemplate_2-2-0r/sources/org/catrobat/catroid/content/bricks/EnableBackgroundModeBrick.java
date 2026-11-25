package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/EnableBackgroundModeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EnableBackgroundModeBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_enable_background_mode;
    }

    public EnableBackgroundModeBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_enable_background_mode_edit_title);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_enable_background_mode_edit_text);
    }

    public EnableBackgroundModeBrick(String title, String text) {
        this(new Formula(title), new Formula(text));
    }

    public EnableBackgroundModeBrick(Formula title, Formula text) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, title);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, text);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createEnableBackgroundModeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
