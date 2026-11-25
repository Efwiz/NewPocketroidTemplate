package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateGLViewBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateGLViewBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_gl_create_view;
    }

    public CreateGLViewBrick() {
        addAllowedBrickField(Brick.BrickField.GL_VIEW_NAME, R.id.brick_gl_name);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_gl_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_gl_y);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_gl_width);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_gl_height);
    }

    public CreateGLViewBrick(String name2, int x, int y, int width, int height) {
        this(new Formula(name2), new Formula(Integer.valueOf(x)), new Formula(Integer.valueOf(y)), new Formula(Integer.valueOf(width)), new Formula(Integer.valueOf(height)));
    }

    public CreateGLViewBrick(Formula name2, Formula x, Formula y, Formula width, Formula height) {
        this();
        setFormulaWithBrickField(Brick.BrickField.GL_VIEW_NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, width);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, height);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGLViewAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.GL_VIEW_NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
