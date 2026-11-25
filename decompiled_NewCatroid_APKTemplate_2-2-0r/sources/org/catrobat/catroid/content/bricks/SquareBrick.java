package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SquareBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SquareBrick extends VisualPlacementBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_square;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getXEditTextId() {
        return R.id.brick_square_edit_x;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getYEditTextId() {
        return R.id.brick_square_edit_y;
    }

    public SquareBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_square_edit_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_square_edit_y);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_square_edit_w);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_square_edit_h);
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_square_edit_name);
        addAllowedBrickField(Brick.BrickField.COLOR, R.id.brick_square_edit_color);
        addAllowedBrickField(Brick.BrickField.TRANSPARENCY, R.id.brick_square_edit_trans);
        addAllowedBrickField(Brick.BrickField.ROTATION, R.id.brick_square_edit_rot);
        addAllowedBrickField(Brick.BrickField.BORDERS, R.id.brick_square_edit_borders);
    }

    public SquareBrick(String name2, String color, float x, float y, float w, float h, float trans, float rot, float borders) {
        this(new Formula(name2), new Formula(color), new Formula(Float.valueOf(x)), new Formula(Float.valueOf(y)), new Formula(Float.valueOf(w)), new Formula(Float.valueOf(h)), new Formula(Float.valueOf(trans)), new Formula(Float.valueOf(rot)), new Formula(Float.valueOf(borders)));
    }

    public SquareBrick(Formula name2, Formula color, Formula x, Formula y, Formula w, Formula h, Formula trans, Formula rot, Formula borders) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, w);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, h);
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.COLOR, color);
        setFormulaWithBrickField(Brick.BrickField.TRANSPARENCY, trans);
        setFormulaWithBrickField(Brick.BrickField.ROTATION, rot);
        setFormulaWithBrickField(Brick.BrickField.BORDERS, borders);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.X_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSquareAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.COLOR), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT), getFormulaWithBrickField(Brick.BrickField.TRANSPARENCY), getFormulaWithBrickField(Brick.BrickField.ROTATION), getFormulaWithBrickField(Brick.BrickField.BORDERS)));
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getXBrickField() {
        return Brick.BrickField.X_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getYBrickField() {
        return Brick.BrickField.Y_POSITION;
    }
}
