package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/GlideToBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GlideToBrick extends VisualPlacementBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_glide_to;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getXEditTextId() {
        return R.id.brick_glide_to_edit_text_x;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getYEditTextId() {
        return R.id.brick_glide_to_edit_text_y;
    }

    public GlideToBrick() {
        addAllowedBrickField(Brick.BrickField.X_DESTINATION, R.id.brick_glide_to_edit_text_x);
        addAllowedBrickField(Brick.BrickField.Y_DESTINATION, R.id.brick_glide_to_edit_text_y);
        addAllowedBrickField(Brick.BrickField.DURATION_IN_SECONDS, R.id.brick_glide_to_edit_text_duration);
    }

    public GlideToBrick(int xDestinationValue, int yDestinationValue, int durationInMilliSecondsValue) {
        this(new Formula(Integer.valueOf(xDestinationValue)), new Formula(Integer.valueOf(yDestinationValue)), new Formula(Double.valueOf(((double) durationInMilliSecondsValue) / 1000.0d)));
    }

    public GlideToBrick(Formula xDestination, Formula yDestination, Formula durationInSeconds) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_DESTINATION, xDestination);
        setFormulaWithBrickField(Brick.BrickField.Y_DESTINATION, yDestination);
        setFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS, durationInSeconds);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.DURATION_IN_SECONDS);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.DURATION_IN_SECONDS;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGlideToAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_DESTINATION), getFormulaWithBrickField(Brick.BrickField.Y_DESTINATION), getFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS)));
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getXBrickField() {
        return Brick.BrickField.X_DESTINATION;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getYBrickField() {
        return Brick.BrickField.Y_DESTINATION;
    }
}
