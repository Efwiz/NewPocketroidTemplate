package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlaceAtBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlaceAtBrick extends VisualPlacementBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_place_at;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getXEditTextId() {
        return R.id.brick_place_at_edit_text_x;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getYEditTextId() {
        return R.id.brick_place_at_edit_text_y;
    }

    public PlaceAtBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_place_at_edit_text_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_place_at_edit_text_y);
    }

    public PlaceAtBrick(int xPositionValue, int yPositionValue) {
        this(new Formula(Integer.valueOf(xPositionValue)), new Formula(Integer.valueOf(yPositionValue)));
    }

    public PlaceAtBrick(Formula xPosition, Formula yPosition) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, xPosition);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, yPosition);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.X_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlaceAtAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
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
