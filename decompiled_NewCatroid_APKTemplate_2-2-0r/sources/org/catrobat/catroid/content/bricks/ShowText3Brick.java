package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.strategy.ShowColorPickerFormulaEditorStrategy;
import org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.common.Conversions;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ShowText3Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowText3Brick extends UserVariableBrickWithVisualPlacement {
    private static final long serialVersionUID = 1;
    public int alignmentSelection;
    private final transient ShowFormulaEditorStrategy showFormulaEditorStrategy;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithVisualPlacement
    protected int getSpinnerId() {
        return R.id.brick_show_text_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_show_text3;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getXEditTextId() {
        return R.id.brick_show_text_edit_x;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getYEditTextId() {
        return R.id.brick_show_text_edit_y;
    }

    public ShowText3Brick() {
        this.alignmentSelection = 1;
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_show_text_edit_text);
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_show_text_edit_name);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_show_text_edit_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_show_text_edit_y);
        addAllowedBrickField(Brick.BrickField.SIZE, R.id.brick_show_text_edit_size);
        addAllowedBrickField(Brick.BrickField.COLOR, R.id.brick_show_text_edit_color);
        this.showFormulaEditorStrategy = new ShowColorPickerFormulaEditorStrategy();
    }

    public ShowText3Brick(String name2, String text, int xPosition, int yPosition, double size, String color) {
        this(new Formula(name2), new Formula(text), new Formula(Integer.valueOf(xPosition)), new Formula(Integer.valueOf(yPosition)), new Formula(Double.valueOf(size)), new Formula(color));
    }

    private ShowText3Brick(Formula name2, Formula text, Formula xPosition, Formula yPosition, Formula size, Formula color) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT, text);
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, xPosition);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, yPosition);
        setFormulaWithBrickField(Brick.BrickField.SIZE, size);
        setFormulaWithBrickField(Brick.BrickField.COLOR, color);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.NAME;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick, org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        if (view.getId() == R.id.brick_show_text_edit_color && ShowTextUtils.isValidColorString(getColor())) {
            this.showFormulaEditorStrategy.showFormulaEditorToEditFormula(view, new ShowTextColorSizeAlignmentBrickCallback(view));
        } else {
            superShowFormulaEditor(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void superShowFormulaEditor(View view) {
        super.showFormulaEditorToEditFormula(view);
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithVisualPlacement, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AlignmentStyle(context.getString(R.string.brick_show_variable_aligned_left), 0));
        arrayList.add(new AlignmentStyle(context.getString(R.string.brick_show_variable_aligned_centered), 1));
        arrayList.add(new AlignmentStyle(context.getString(R.string.brick_show_variable_aligned_right), 2));
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.brick_show_text_spinner), this.view, arrayList);
        brickSpinner.setSelection(this.alignmentSelection);
        brickSpinner.setOnItemSelectedListener(new BrickSpinner.OnItemSelectedListener<AlignmentStyle>() { // from class: org.catrobat.catroid.content.bricks.ShowText3Brick.1
            @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
            public void onEditOptionSelected(Integer spinnerId) {
            }

            @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
            public void onNewOptionSelected(Integer spinnerId) {
            }

            @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
            public void onStringOptionSelected(Integer spinnerId, String string) {
            }

            @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
            public void onItemSelected(Integer spinnerId, AlignmentStyle item) {
                if (item != null) {
                    ShowText3Brick.this.alignmentSelection = item.alignmentStyle;
                }
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createShowtext3Action(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.SIZE), getFormulaWithBrickField(Brick.BrickField.COLOR), this.alignmentSelection, new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext())));
    }

    private String getColor() {
        return getFormulaWithBrickField(Brick.BrickField.COLOR).getRoot().getValue();
    }

    private float sanitizeTextSize() {
        Formula formulaWithBrickField = getFormulaWithBrickField(Brick.BrickField.SIZE);
        if (formulaWithBrickField.getRoot().getElementType() == FormulaElement.ElementType.NUMBER) {
            return Float.parseFloat(formulaWithBrickField.getRoot().getValue()) / 100.0f;
        }
        return 1.0f;
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithVisualPlacement, org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Intent generateIntentForVisualPlacement(Brick.BrickField brickFieldX, Brick.BrickField brickFieldY) {
        Intent intentGenerateIntentForVisualPlacement = super.generateIntentForVisualPlacement(brickFieldX, brickFieldY);
        intentGenerateIntentForVisualPlacement.putExtra(SpriteActivity.EXTRA_TEXT_COLOR, getColor());
        intentGenerateIntentForVisualPlacement.putExtra(SpriteActivity.EXTRA_TEXT_SIZE, sanitizeTextSize());
        intentGenerateIntentForVisualPlacement.putExtra(SpriteActivity.EXTRA_TEXT_ALIGNMENT, this.alignmentSelection);
        return intentGenerateIntentForVisualPlacement;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ShowText3Brick$AlignmentStyle.class
      classes14.dex
     */
    private static class AlignmentStyle implements Nameable {
        private int alignmentStyle;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private String f562name;

        AlignmentStyle(String name2, int alignmentStyle) {
            this.f562name = name2;
            this.alignmentStyle = alignmentStyle;
        }

        @Override // org.catrobat.catroid.common.Nameable
        public String getName() {
            return this.f562name;
        }

        @Override // org.catrobat.catroid.common.Nameable
        public void setName(String name2) {
            this.f562name = name2;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ShowText3Brick$ShowTextColorSizeAlignmentBrickCallback.class
      classes14.dex
     */
    private final class ShowTextColorSizeAlignmentBrickCallback implements ShowFormulaEditorStrategy.Callback {
        private View view;

        private ShowTextColorSizeAlignmentBrickCallback(View view) {
            this.view = view;
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void showFormulaEditor(View view) {
            ShowText3Brick.this.superShowFormulaEditor(view);
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void setValue(int value) {
            ShowText3Brick.this.setFormulaWithBrickField(Brick.BrickField.COLOR, new Formula(ShowTextUtils.convertColorToString(value)));
            AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
            Fragment fragmentFindFragmentById = activityFromView.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            ShowText3Brick.this.notifyDataSetChanged(activityFromView);
            if (fragmentFindFragmentById instanceof FormulaEditorFragment) {
                ((FormulaEditorFragment) fragmentFindFragmentById).updateFragmentAfterColorPicker();
            }
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public int getValue() {
            return !isColorBrickFieldAString() ? ViewCompat.MEASURED_STATE_MASK : Conversions.tryParseColor(getColorBrickFieldStringValue());
        }

        private boolean isColorBrickFieldAString() {
            return getColorFormulaElement().getElementType() == FormulaElement.ElementType.STRING;
        }

        private String getColorBrickFieldStringValue() {
            return getColorFormulaElement().getValue();
        }

        private FormulaElement getColorFormulaElement() {
            return ShowText3Brick.this.getFormulaWithBrickField(Brick.BrickField.COLOR).getRoot();
        }
    }
}
