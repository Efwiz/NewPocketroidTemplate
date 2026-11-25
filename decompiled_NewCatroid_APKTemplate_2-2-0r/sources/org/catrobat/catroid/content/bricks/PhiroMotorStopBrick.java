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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroMotorStopBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroMotorStopBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private String motor;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroMotorStopBrick$Motor.class
      classes14.dex
     */
    public enum Motor {
        MOTOR_LEFT,
        MOTOR_RIGHT,
        MOTOR_BOTH
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_phiro_motor_stop;
    }

    public PhiroMotorStopBrick() {
        this.motor = Motor.MOTOR_BOTH.name();
    }

    public PhiroMotorStopBrick(Motor motorEnum) {
        this.motor = motorEnum.name();
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_phiro_stop_motor_spinner, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_stop_motor_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.PhiroMotorStopBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4948xda01f1b8((Integer) obj);
            }
        }));
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-PhiroMotorStopBrick, reason: not valid java name */
    /* synthetic */ Unit m4948xda01f1b8(Integer num) {
        this.motor = Motor.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 10);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPhiroMotorStopActionAction(Motor.valueOf(this.motor)));
    }
}
