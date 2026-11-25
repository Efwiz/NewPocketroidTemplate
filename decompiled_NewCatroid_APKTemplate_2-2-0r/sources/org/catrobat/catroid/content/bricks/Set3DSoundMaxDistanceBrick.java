package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Set3DSoundMaxDistanceBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Set3DSoundMaxDistanceBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_3d_sound_max_distance;
    }

    public Set3DSoundMaxDistanceBrick() {
        addAllowedBrickField(Brick.BrickField.INSTANCE_NAME, R.id.brick_set_3d_sound_max_distance_instance);
        addAllowedBrickField(Brick.BrickField.DISTANCE, R.id.brick_set_3d_sound_max_distance_val);
    }

    public Set3DSoundMaxDistanceBrick(String instanceName, float distance) {
        this(new Formula(instanceName), new Formula(Float.valueOf(distance)));
    }

    public Set3DSoundMaxDistanceBrick(Formula instanceName, Formula distance) {
        this();
        setFormulaWithBrickField(Brick.BrickField.INSTANCE_NAME, instanceName);
        setFormulaWithBrickField(Brick.BrickField.DISTANCE, distance);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSet3DSoundMaxDistanceAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.INSTANCE_NAME), getFormulaWithBrickField(Brick.BrickField.DISTANCE)));
    }
}
