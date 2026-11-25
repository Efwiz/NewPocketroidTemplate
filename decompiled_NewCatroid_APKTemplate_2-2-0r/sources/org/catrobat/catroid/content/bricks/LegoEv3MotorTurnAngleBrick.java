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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoEv3MotorTurnAngleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3MotorTurnAngleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String motor;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoEv3MotorTurnAngleBrick$Motor.class
      classes14.dex
     */
    public enum Motor {
        MOTOR_A,
        MOTOR_B,
        MOTOR_C,
        MOTOR_D,
        MOTOR_B_C,
        MOTOR_ALL
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ev3_motor_turn_angle;
    }

    public LegoEv3MotorTurnAngleBrick() {
        this.motor = Motor.MOTOR_A.name();
        addAllowedBrickField(Brick.BrickField.LEGO_EV3_DEGREES, R.id.ev3_motor_turn_angle_edit_text);
    }

    public LegoEv3MotorTurnAngleBrick(Motor motorEnum, int degrees) {
        this(motorEnum, new Formula(Integer.valueOf(degrees)));
    }

    public LegoEv3MotorTurnAngleBrick(Motor motorEnum, Formula degreesFormula) {
        this();
        this.motor = motorEnum.name();
        setFormulaWithBrickField(Brick.BrickField.LEGO_EV3_DEGREES, degreesFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 20);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(final Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.ev3_motor_chooser, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.lego_ev3_motor_turn_angle_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.LegoEv3MotorTurnAngleBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4928xc3cf359f((Integer) obj);
            }
        }));
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-LegoEv3MotorTurnAngleBrick, reason: not valid java name */
    /* synthetic */ Unit m4928xc3cf359f(Integer num) {
        this.motor = Motor.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoEv3MotorTurnAngleAction(sprite, sequence, Motor.valueOf(this.motor), getFormulaWithBrickField(Brick.BrickField.LEGO_EV3_DEGREES)));
    }
}
