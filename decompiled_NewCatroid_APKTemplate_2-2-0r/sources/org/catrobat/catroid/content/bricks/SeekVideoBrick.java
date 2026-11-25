package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SeekVideoBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SeekVideoBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_seek_video;
    }

    public SeekVideoBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_seek_video_name);
        addAllowedBrickField(Brick.BrickField.TIME, R.id.brick_seek_video_sec);
    }

    public SeekVideoBrick(String name2, Integer time) {
        this(new Formula(name2), new Formula(time));
    }

    public SeekVideoBrick(Formula formula, Formula time) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, formula);
        setFormulaWithBrickField(Brick.BrickField.TIME, time);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().seekVideoAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TIME)));
    }
}
