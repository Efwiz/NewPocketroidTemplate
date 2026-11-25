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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoNxtMotorStopBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNxtMotorStopBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private String motor;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoNxtMotorStopBrick$Motor.class
      classes14.dex
     */
    public enum Motor {
        MOTOR_A,
        MOTOR_B,
        MOTOR_C,
        MOTOR_B_C,
        ALL_MOTORS
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_nxt_motor_stop;
    }

    public LegoNxtMotorStopBrick() {
        this.motor = Motor.MOTOR_A.name();
    }

    public LegoNxtMotorStopBrick(Motor motorEnum) {
        this.motor = motorEnum.name();
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.nxt_stop_motor_chooser, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.stop_motor_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4931x83df2313((Integer) obj);
            }
        }));
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-LegoNxtMotorStopBrick, reason: not valid java name */
    /* synthetic */ Unit m4931x83df2313(Integer num) {
        this.motor = Motor.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoNxtMotorStopAction(Motor.valueOf(this.motor)));
    }
}
