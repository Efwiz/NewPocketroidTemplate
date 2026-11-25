package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ToggleDisplayBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ToggleDisplayBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_toggle_display;
    }

    public ToggleDisplayBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_visible_edit);
    }

    public ToggleDisplayBrick(int visible) {
        this(new Formula(Integer.valueOf(visible)));
    }

    public ToggleDisplayBrick(Formula visible) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, visible);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createToggleDisplayAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}
