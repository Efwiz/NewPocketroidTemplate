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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoNxtMotorMoveBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNxtMotorMoveBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String motor;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoNxtMotorMoveBrick$Motor.class
      classes14.dex
     */
    public enum Motor {
        MOTOR_A,
        MOTOR_B,
        MOTOR_C,
        MOTOR_B_C
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_nxt_motor_action;
    }

    public LegoNxtMotorMoveBrick() {
        this.motor = Motor.MOTOR_A.name();
        addAllowedBrickField(Brick.BrickField.LEGO_NXT_SPEED, R.id.motor_action_speed_edit_text);
    }

    public LegoNxtMotorMoveBrick(Motor motorEnum, int speedValue) {
        this(motorEnum, new Formula(Integer.valueOf(speedValue)));
    }

    public LegoNxtMotorMoveBrick(Motor motorEnum, Formula formula) {
        this();
        this.motor = motorEnum.name();
        setFormulaWithBrickField(Brick.BrickField.LEGO_NXT_SPEED, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.nxt_motor_chooser, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.lego_motor_action_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4930x6264f8a4((Integer) obj);
            }
        }));
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-LegoNxtMotorMoveBrick, reason: not valid java name */
    /* synthetic */ Unit m4930x6264f8a4(Integer num) {
        this.motor = Motor.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 2);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoNxtMotorMoveAction(sprite, sequence, Motor.valueOf(this.motor), getFormulaWithBrickField(Brick.BrickField.LEGO_NXT_SPEED)));
    }
}
