package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DroneMoveUpBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DroneMoveUpBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_drone_move_up;
    }

    public DroneMoveUpBrick() {
        addAllowedBrickField(Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS, R.id.brick_drone_move_up_edit_text_second);
        addAllowedBrickField(Brick.BrickField.DRONE_POWER_IN_PERCENT, R.id.brick_drone_move_up_edit_text_power);
    }

    public DroneMoveUpBrick(int durationInMilliseconds, int powerInPercent) {
        this(new Formula(Double.valueOf(((double) durationInMilliseconds) / 1000.0d)), new Formula(Integer.valueOf(powerInPercent)));
    }

    public DroneMoveUpBrick(Formula durationInSeconds, Formula powerInPercent) {
        this();
        setFormulaWithBrickField(Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS, durationInSeconds);
        setFormulaWithBrickField(Brick.BrickField.DRONE_POWER_IN_PERCENT, powerInPercent);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS;
    }
}
