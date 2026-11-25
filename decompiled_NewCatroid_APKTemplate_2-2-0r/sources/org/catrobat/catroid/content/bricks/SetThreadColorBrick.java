package org.catrobat.catroid.content.bricks;

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetThreadColorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetThreadColorBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private final transient ShowFormulaEditorStrategy showFormulaEditorStrategy;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_thread_color;
    }

    public SetThreadColorBrick() {
        addAllowedBrickField(Brick.BrickField.THREAD_COLOR, R.id.brick_set_thread_color_action_edit_text);
        this.showFormulaEditorStrategy = new ShowColorPickerFormulaEditorStrategy();
    }

    public SetThreadColorBrick(Formula color) {
        this();
        setFormulaWithBrickField(Brick.BrickField.THREAD_COLOR, color);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        this.showFormulaEditorStrategy.showFormulaEditorToEditFormula(view, new SetThreadColorBrickCallback(view));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void superShowFormulaEditor(View view) {
        super.showFormulaEditorToEditFormula(view);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.THREAD_COLOR;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetThreadColorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.THREAD_COLOR)));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetThreadColorBrick$SetThreadColorBrickCallback.class
      classes14.dex
     */
    private final class SetThreadColorBrickCallback implements ShowFormulaEditorStrategy.Callback {
        private final View view;

        private SetThreadColorBrickCallback(View view) {
            this.view = view;
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void showFormulaEditor(View view) {
            SetThreadColorBrick.this.superShowFormulaEditor(view);
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void setValue(int value) {
            SetThreadColorBrick.this.setFormulaWithBrickField(Brick.BrickField.THREAD_COLOR, new Formula(convertToHexString(value)));
            SetThreadColorBrick.this.notifyDataSetChanged(UiUtils.getActivityFromView(this.view));
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public int getValue() {
            return getColorValueFromBrickField(Brick.BrickField.THREAD_COLOR);
        }

        private int getColorValueFromBrickField(Brick.BrickField brickField) {
            try {
                return Integer.decode(SetThreadColorBrick.this.getFormulaWithBrickField(brickField).interpretString(null)).intValue();
            } catch (InterpretationException unused) {
                return 0;
            }
        }

        private String convertToHexString(int value) {
            return "#" + Integer.toHexString(value).substring(2);
        }
    }
}
