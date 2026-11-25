package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/JumpingSumoRotateLeftBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JumpingSumoRotateLeftBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_jumping_sumo_rotate_left;
    }

    public JumpingSumoRotateLeftBrick() {
        addAllowedBrickField(Brick.BrickField.JUMPING_SUMO_ROTATE, R.id.brick_jumping_sumo_change_left_variable_edit_text);
    }

    public JumpingSumoRotateLeftBrick(double degree) {
        this(new Formula(Double.valueOf(degree)));
    }

    public JumpingSumoRotateLeftBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JUMPING_SUMO_ROTATE, formula);
    }
}
