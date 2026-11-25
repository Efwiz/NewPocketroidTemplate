package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PointInDirectionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PointInDirectionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_point_in_direction;
    }

    public PointInDirectionBrick() {
        addAllowedBrickField(Brick.BrickField.DEGREES, R.id.brick_point_in_direction_edit_text);
    }

    public PointInDirectionBrick(double direction) {
        this(new Formula(Double.valueOf(direction)));
    }

    public PointInDirectionBrick(Formula direction) {
        this();
        setFormulaWithBrickField(Brick.BrickField.DEGREES, direction);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPointInDirectionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.DEGREES)));
    }
}
