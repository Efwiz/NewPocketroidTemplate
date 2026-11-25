package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RaspiSendDigitalValueAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiSendDigitalValueAction extends TemporalAction {
    private static final String TAG = "RaspiSendDigitalValueAction";
    private int pin;
    private Formula pinNumber;
    private Formula pinValue;
    private Scope scope;
    private boolean value;

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0014 -> B:19:0x001e). Please report as a decompilation issue!!! */
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        Integer numInterpretInteger;
        boolean z = false;
        try {
            Formula formula = this.pinNumber;
            numInterpretInteger = formula == null ? 0 : formula.interpretInteger(this.scope);
        } catch (InterpretationException e) {
            Log.d(TAG, "Formula interpretation for this specific Brick failed.", e);
            numInterpretInteger = 0;
        }
        try {
            Formula formula2 = this.pinValue;
            if (formula2 != null && formula2.interpretBoolean(this.scope)) {
                z = true;
            }
        } catch (InterpretationException e2) {
            Log.d(TAG, "Formula interpretation for this specific Brick failed.", e2);
        }
        this.pin = numInterpretInteger.intValue();
        this.value = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        RPiSocketConnection rPiSocketConnection = RaspberryPiService.getInstance().connection;
        try {
            Log.d(TAG, "RPi set " + this.pin + " to " + this.value);
            rPiSocketConnection.setPin(this.pin, this.value);
        } catch (Exception e) {
            Log.e(TAG, "RPi: exception during setPin: " + e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPinNumber(Formula newPinNumber) {
        this.pinNumber = newPinNumber;
    }

    public void setPinValue(Formula newpinValue) {
        this.pinValue = newpinValue;
    }
}
