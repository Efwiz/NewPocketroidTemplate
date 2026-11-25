package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateBufferBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateBufferBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_buffer;
    }

    public CreateBufferBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_buffer_name);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_buffer_width);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_buffer_height);
    }

    public CreateBufferBrick(String n, String w, String h) {
        this(new Formula(n), new Formula(w), new Formula(h));
    }

    public CreateBufferBrick(Formula n, Formula w, Formula h) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, n);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, w);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, h);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBufferAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
