package org.catrobat.catroid.content.bricks;

import android.content.Context;
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
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.fragment.SingleSeekBar;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroMotorMoveBackwardBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroMotorMoveBackwardBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String motor;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroMotorMoveBackwardBrick$Motor.class
      classes14.dex
     */
    public enum Motor {
        MOTOR_LEFT,
        MOTOR_RIGHT,
        MOTOR_BOTH
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_phiro_motor_backward;
    }

    public PhiroMotorMoveBackwardBrick() {
        this.motor = Motor.MOTOR_LEFT.name();
        addAllowedBrickField(Brick.BrickField.PHIRO_SPEED, R.id.brick_phiro_motor_backward_action_speed_edit_text);
    }

    public PhiroMotorMoveBackwardBrick(Motor motorEnum, int speed) {
        this(motorEnum, new Formula(Integer.valueOf(speed)));
    }

    public PhiroMotorMoveBackwardBrick(Motor motorEnum, Formula formula) {
        this();
        this.motor = motorEnum.name();
        setFormulaWithBrickField(Brick.BrickField.PHIRO_SPEED, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public View getCustomView(Context context) {
        return new SingleSeekBar(this, Brick.BrickField.PHIRO_SPEED, R.string.phiro_motor_speed).getView(context);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_phiro_select_motor_spinner, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_motor_backward_action_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4946xea017926((Integer) obj);
            }
        }));
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-PhiroMotorMoveBackwardBrick, reason: not valid java name */
    /* synthetic */ Unit m4946xea017926(Integer num) {
        this.motor = Motor.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        if (isSpeedOnlyANumber()) {
            FormulaEditorFragment.showCustomFragment(view.getContext(), this, Brick.BrickField.PHIRO_SPEED);
        } else {
            super.showFormulaEditorToEditFormula(view);
        }
    }

    private boolean isSpeedOnlyANumber() {
        return getFormulaWithBrickField(Brick.BrickField.PHIRO_SPEED).getRoot().getElementType() == FormulaElement.ElementType.NUMBER;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 10);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPhiroMotorMoveBackwardActionAction(sprite, sequence, Motor.valueOf(this.motor), getFormulaWithBrickField(Brick.BrickField.PHIRO_SPEED)));
    }
}
