package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Set3DSoundPositionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Set3DSoundPositionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_3d_sound_position;
    }

    public Set3DSoundPositionBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_set_3d_sound_name);
        addAllowedBrickField(Brick.BrickField.VALUE_X, R.id.brick_set_3d_sound_x);
        addAllowedBrickField(Brick.BrickField.VALUE_Y, R.id.brick_set_3d_sound_y);
        addAllowedBrickField(Brick.BrickField.VALUE_Z, R.id.brick_set_3d_sound_z);
    }

    public Set3DSoundPositionBrick(String name2, Integer x, Integer y, Integer z) {
        this(new Formula(name2), new Formula(x), new Formula(y), new Formula(z));
    }

    public Set3DSoundPositionBrick(Formula name2, Formula x, Formula y, Formula z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.VALUE_X, x);
        setFormulaWithBrickField(Brick.BrickField.VALUE_Y, y);
        setFormulaWithBrickField(Brick.BrickField.VALUE_Z, z);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSet3DSoundPositionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.VALUE_X), getFormulaWithBrickField(Brick.BrickField.VALUE_Y), getFormulaWithBrickField(Brick.BrickField.VALUE_Z)));
    }
}
