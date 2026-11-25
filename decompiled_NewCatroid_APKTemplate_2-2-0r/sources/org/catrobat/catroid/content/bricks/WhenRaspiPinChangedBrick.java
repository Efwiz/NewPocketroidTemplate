package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenRaspiPinChangedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenRaspiPinChangedBrick extends ScriptBrickBaseType {
    private static final long serialVersionUID = 1;
    private RaspiInterruptScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_raspi_pin_changed;
    }

    public WhenRaspiPinChangedBrick() {
        this.script = new RaspiInterruptScript();
    }

    public WhenRaspiPinChangedBrick(RaspiInterruptScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenRaspiPinChangedBrick whenRaspiPinChangedBrick = (WhenRaspiPinChangedBrick) super.clone();
        RaspiInterruptScript raspiInterruptScript = (RaspiInterruptScript) this.script.clone();
        whenRaspiPinChangedBrick.script = raspiInterruptScript;
        raspiInterruptScript.setScriptBrick(whenRaspiPinChangedBrick);
        return whenRaspiPinChangedBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        setupValueSpinner(context);
        setupPinSpinner(context);
        return this.view;
    }

    private void setupPinSpinner(Context context) {
        final Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_raspi_when_pinspinner);
        ArrayList<Integer> gpioList = RaspberryPiService.getInstance().getGpioList(SettingsFragment.getRaspiRevision(context));
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Iterator<Integer> it = gpioList.iterator();
        while (it.hasNext()) {
            arrayAdapter.add(it.next().toString());
        }
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.WhenRaspiPinChangedBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4998x97aa599(spinner, (Integer) obj);
            }
        }));
        spinner.setSelection(arrayAdapter.getPosition(this.script.getPin()), true);
    }

    /* JADX INFO: renamed from: lambda$setupPinSpinner$0$org-catrobat-catroid-content-bricks-WhenRaspiPinChangedBrick, reason: not valid java name */
    /* synthetic */ Unit m4998x97aa599(Spinner spinner, Integer num) {
        this.script.setPin(spinner.getSelectedItem().toString());
        return Unit.INSTANCE;
    }

    private void setupValueSpinner(final Context context) {
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_raspi_when_valuespinner);
        spinner.setAdapter((SpinnerAdapter) getValueSpinnerArrayAdapter(context));
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.WhenRaspiPinChangedBrick$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4999xff2a014((Integer) obj);
            }
        }));
        for (int i = 0; i < BrickValues.RASPI_EVENTS.length; i++) {
            if (BrickValues.RASPI_EVENTS[i].equals(this.script.getEventValue())) {
                spinner.setSelection(i, true);
                return;
            }
        }
    }

    /* JADX INFO: renamed from: lambda$setupValueSpinner$1$org-catrobat-catroid-content-bricks-WhenRaspiPinChangedBrick, reason: not valid java name */
    /* synthetic */ Unit m4999xff2a014(Integer num) {
        if (num.intValue() < BrickValues.RASPI_EVENTS.length) {
            this.script.setEventValue(BrickValues.RASPI_EVENTS[num.intValue()]);
        }
        return Unit.INSTANCE;
    }

    private ArrayAdapter<String> getValueSpinnerArrayAdapter(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.add(context.getString(R.string.brick_raspi_pressed_text));
        arrayAdapter.add(context.getString(R.string.brick_raspi_released_text));
        return arrayAdapter;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public RaspiInterruptScript getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 7);
    }
}
