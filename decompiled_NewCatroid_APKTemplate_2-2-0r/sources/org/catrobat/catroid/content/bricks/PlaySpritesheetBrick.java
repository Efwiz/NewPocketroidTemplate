package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlaySpritesheetBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlaySpritesheetBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_play_spritesheet;
    }

    public PlaySpritesheetBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_play_spritesheet_rows);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_play_spritesheet_cols);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_play_spritesheet_selected_row);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_play_spritesheet_frames_count);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_play_spritesheet_speed_val);
    }

    public PlaySpritesheetBrick(Formula rows, Formula cols, Formula selectedRow, Formula framesCount, Formula speed) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, rows);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, cols);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, selectedRow);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, framesCount);
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, speed);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlaySpritesheetAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5)));
    }
}
