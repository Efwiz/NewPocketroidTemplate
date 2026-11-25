package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.strategy.ShowColorPickerFormulaEditorStrategy;
import org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.ui.UiUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroRGBLightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroRGBLightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String eye;
    private final transient ShowFormulaEditorStrategy showFormulaEditorStrategy;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroRGBLightBrick$Eye.class
      classes14.dex
     */
    public enum Eye {
        LEFT,
        RIGHT,
        BOTH
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_phiro_rgb_light;
    }

    public PhiroRGBLightBrick() {
        addAllowedBrickField(Brick.BrickField.PHIRO_LIGHT_RED, R.id.brick_phiro_rgb_led_action_red_edit_text);
        addAllowedBrickField(Brick.BrickField.PHIRO_LIGHT_GREEN, R.id.brick_phiro_rgb_led_action_green_edit_text);
        addAllowedBrickField(Brick.BrickField.PHIRO_LIGHT_BLUE, R.id.brick_phiro_rgb_led_action_blue_edit_text);
        this.eye = Eye.BOTH.name();
        this.showFormulaEditorStrategy = new ShowColorPickerFormulaEditorStrategy();
    }

    public PhiroRGBLightBrick(Eye eyeEnum, int red, int green, int blue) {
        this(eyeEnum, new Formula(Integer.valueOf(red)), new Formula(Integer.valueOf(green)), new Formula(Integer.valueOf(blue)));
    }

    public PhiroRGBLightBrick(Eye eyeEnum, Formula redFormula, Formula greenFormula, Formula blueFormula) {
        this();
        this.eye = eyeEnum.name();
        setFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_RED, redFormula);
        setFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_GREEN, greenFormula);
        setFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_BLUE, blueFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_phiro_select_light_spinner, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_rgb_light_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.PhiroRGBLightBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4950x63169e6a((Integer) obj);
            }
        }));
        spinner.setSelection(Eye.valueOf(this.eye).ordinal());
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-PhiroRGBLightBrick, reason: not valid java name */
    /* synthetic */ Unit m4950x63169e6a(Integer num) {
        this.eye = Eye.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        if (areAllBrickFieldsNumbers()) {
            this.showFormulaEditorStrategy.showFormulaEditorToEditFormula(view, new SetPhiroRGBLightBrickCallback(view));
        } else {
            super.showFormulaEditorToEditFormula(view);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.PHIRO_LIGHT_RED;
    }

    private boolean areAllBrickFieldsNumbers() {
        return isBrickFieldANumber(Brick.BrickField.PHIRO_LIGHT_RED) && isBrickFieldANumber(Brick.BrickField.PHIRO_LIGHT_GREEN) && isBrickFieldANumber(Brick.BrickField.PHIRO_LIGHT_BLUE);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 10);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPhiroRgbLedEyeActionAction(sprite, sequence, Eye.valueOf(this.eye), getFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_RED), getFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_GREEN), getFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_BLUE)));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroRGBLightBrick$SetPhiroRGBLightBrickCallback.class
      classes14.dex
     */
    private final class SetPhiroRGBLightBrickCallback implements ShowFormulaEditorStrategy.Callback {
        private final View view;

        private SetPhiroRGBLightBrickCallback(View view) {
            this.view = view;
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void showFormulaEditor(View view) {
            PhiroRGBLightBrick.super.showFormulaEditorToEditFormula(view);
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public void setValue(int value) {
            PhiroRGBLightBrick.this.setFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_RED, new Formula(Integer.valueOf(Color.red(value))));
            PhiroRGBLightBrick.this.setFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_GREEN, new Formula(Integer.valueOf(Color.green(value))));
            PhiroRGBLightBrick.this.setFormulaWithBrickField(Brick.BrickField.PHIRO_LIGHT_BLUE, new Formula(Integer.valueOf(Color.blue(value))));
            PhiroRGBLightBrick.this.notifyDataSetChanged(UiUtils.getActivityFromView(this.view));
        }

        @Override // org.catrobat.catroid.content.strategy.ShowFormulaEditorStrategy.Callback
        public int getValue() {
            return Color.rgb(getColorValueFromBrickField(Brick.BrickField.PHIRO_LIGHT_RED), getColorValueFromBrickField(Brick.BrickField.PHIRO_LIGHT_GREEN), getColorValueFromBrickField(Brick.BrickField.PHIRO_LIGHT_BLUE));
        }

        private int getColorValueFromBrickField(Brick.BrickField brickField) {
            try {
                return Math.max(0, Math.min(255, PhiroRGBLightBrick.this.getFormulaWithBrickField(brickField).interpretInteger(null).intValue()));
            } catch (InterpretationException unused) {
                return 0;
            }
        }
    }
}
