package org.catrobat.catroid.content.bricks;

import android.graphics.Color;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.strategy.ShowColorPickerFormulaEditorStrategy;
import org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.ui.UiUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetPenColorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPenColorBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private final transient ShowFormulaEditorStrategy showFormulaEditorStrategy;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_pen_color;
    }

    public SetPenColorBrick() {
        addAllowedBrickField(Brick.BrickField.PEN_COLOR_RED, R.id.brick_set_pen_color_action_red_edit_text);
        addAllowedBrickField(Brick.BrickField.PEN_COLOR_GREEN, R.id.brick_set_pen_color_action_green_edit_text);
        addAllowedBrickField(Brick.BrickField.PEN_COLOR_BLUE, R.id.brick_set_pen_color_action_blue_edit_text);
        this.showFormulaEditorStrategy = new ShowColorPickerFormulaEditorStrategy();
    }

    public SetPenColorBrick(int red, int green, int blue) {
        this(new Formula(Integer.valueOf(red)), new Formula(Integer.valueOf(green)), new Formula(Integer.valueOf(blue)));
    }

    public SetPenColorBrick(Formula red, Formula green, Formula blue) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PEN_COLOR_RED, red);
        setFormulaWithBrickField(Brick.BrickField.PEN_COLOR_GREEN, green);
        setFormulaWithBrickField(Brick.BrickField.PEN_COLOR_BLUE, blue);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        if (areAllBrickFieldsNumbers()) {
            this.showFormulaEditorStrategy.showFormulaEditorToEditFormula(view, new SetPenColorBrickCallback(view));
        } else {
            superShowFormulaEditor(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void superShowFormulaEditor(View view) {
        super.showFormulaEditorToEditFormula(view);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.PEN_COLOR_RED;
    }

    private boolean areAllBrickFieldsNumbers() {
        return isBrickFieldANumber(Brick.BrickField.PEN_COLOR_RED) && isBrickFieldANumber(Brick.BrickField.PEN_COLOR_GREEN) && isBrickFieldANumber(Brick.BrickField.PEN_COLOR_BLUE);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPenColorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PEN_COLOR_RED), getFormulaWithBrickField(Brick.BrickField.PEN_COLOR_GREEN), getFormulaWithBrickField(Brick.BrickField.PEN_COLOR_BLUE)));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetPenColorBrick$SetPenColorBrickCallback.class
      classes14.dex
     */
    private final class SetPenColorBrickCallback implements ShowFormulaEditorStrategy.Callback {
        private final View view;

        private SetPenColorBrickCallback(View view) {
            this.view = view;
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void showFormulaEditor(View view) {
            SetPenColorBrick.this.superShowFormulaEditor(view);
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void setValue(int value) {
            SetPenColorBrick.this.setFormulaWithBrickField(Brick.BrickField.PEN_COLOR_RED, new Formula(Integer.valueOf(Color.red(value))));
            SetPenColorBrick.this.setFormulaWithBrickField(Brick.BrickField.PEN_COLOR_GREEN, new Formula(Integer.valueOf(Color.green(value))));
            SetPenColorBrick.this.setFormulaWithBrickField(Brick.BrickField.PEN_COLOR_BLUE, new Formula(Integer.valueOf(Color.blue(value))));
            SetPenColorBrick.this.notifyDataSetChanged(UiUtils.getActivityFromView(this.view));
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public int getValue() {
            return Color.rgb(getColorValueFromBrickField(Brick.BrickField.PEN_COLOR_RED), getColorValueFromBrickField(Brick.BrickField.PEN_COLOR_GREEN), getColorValueFromBrickField(Brick.BrickField.PEN_COLOR_BLUE));
        }

        private int getColorValueFromBrickField(Brick.BrickField brickField) {
            try {
                return Math.max(0, Math.min(255, SetPenColorBrick.this.getFormulaWithBrickField(brickField).interpretInteger(null).intValue()));
            } catch (InterpretationException unused) {
                return 0;
            }
        }
    }
}
