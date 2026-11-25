package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/JumpingSumoRotateRightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JumpingSumoRotateRightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_jumping_sumo_rotate_right;
    }

    public JumpingSumoRotateRightBrick() {
        addAllowedBrickField(Brick.BrickField.JUMPING_SUMO_ROTATE, R.id.brick_jumping_sumo_change_right_variable_edit_text);
    }

    public JumpingSumoRotateRightBrick(double degree) {
        this(new Formula(Double.valueOf(degree)));
    }

    public JumpingSumoRotateRightBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JUMPING_SUMO_ROTATE, formula);
    }
}
