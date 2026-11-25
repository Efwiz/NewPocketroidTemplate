package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateVideoBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateVideoBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_video;
    }

    public CreateVideoBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.create_video_name);
        addAllowedBrickField(Brick.BrickField.FILE, R.id.create_video_file);
        addAllowedBrickField(Brick.BrickField.POSX, R.id.create_video_x);
        addAllowedBrickField(Brick.BrickField.POSY, R.id.create_video_y);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.create_video_width);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.create_video_height);
        addAllowedBrickField(Brick.BrickField.LOOPED, R.id.create_video_loop);
        addAllowedBrickField(Brick.BrickField.CONTROLS, R.id.create_video_control);
    }

    public CreateVideoBrick(String name2, String file, Integer x, Integer y, Integer width, Integer height, Integer looped, Integer controls) {
        this(new Formula(name2), new Formula(file), new Formula(x), new Formula(y), new Formula(width), new Formula(height), new Formula(looped), new Formula(controls));
    }

    public CreateVideoBrick(Formula name2, Formula file, Formula x, Formula y, Formula width, Formula height, Formula looped, Formula controls) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.FILE, file);
        setFormulaWithBrickField(Brick.BrickField.POSX, x);
        setFormulaWithBrickField(Brick.BrickField.POSY, y);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, width);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, height);
        setFormulaWithBrickField(Brick.BrickField.LOOPED, looped);
        setFormulaWithBrickField(Brick.BrickField.CONTROLS, controls);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().videoAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.FILE), getFormulaWithBrickField(Brick.BrickField.POSX), getFormulaWithBrickField(Brick.BrickField.POSY), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT), getFormulaWithBrickField(Brick.BrickField.LOOPED), getFormulaWithBrickField(Brick.BrickField.CONTROLS)));
    }
}
