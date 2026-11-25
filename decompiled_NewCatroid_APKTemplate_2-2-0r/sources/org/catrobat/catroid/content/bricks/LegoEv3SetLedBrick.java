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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoEv3SetLedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3SetLedBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private String ledStatus;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LegoEv3SetLedBrick$LedStatus.class
      classes14.dex
     */
    public enum LedStatus {
        LED_OFF,
        LED_GREEN,
        LED_RED,
        LED_ORANGE,
        LED_GREEN_FLASHING,
        LED_RED_FLASHING,
        LED_ORANGE_FLASHING,
        LED_GREEN_PULSE,
        LED_RED_PULSE,
        LED_ORANGE_PULSE
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ev3_set_led;
    }

    public LegoEv3SetLedBrick() {
        this.ledStatus = LedStatus.LED_GREEN.name();
    }

    public LegoEv3SetLedBrick(LedStatus ledStatusEnum) {
        this.ledStatus = ledStatusEnum.name();
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.ev3_led_status_chooser, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_ev3_set_led_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4929xded124b7((Integer) obj);
            }
        }));
        spinner.setSelection(LedStatus.valueOf(this.ledStatus).ordinal());
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-LegoEv3SetLedBrick, reason: not valid java name */
    /* synthetic */ Unit m4929xded124b7(Integer num) {
        this.ledStatus = LedStatus.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 20);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoEv3SetLedAction(LedStatus.valueOf(this.ledStatus)));
    }
}
