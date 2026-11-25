package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RaspiPwmAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiPwmAction extends TemporalAction {
    private static final String TAG = "RaspiPwmAction";
    private double frequencyInterpretation;
    private double percentageInterpretation;
    private int pinInterpretation;
    private Formula pinNumberFormula;
    private Formula pwmFrequencyFormula;
    private Formula pwmPercentageFormula;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        try {
            Formula formula = this.pinNumberFormula;
            this.pinInterpretation = (formula == null ? 0 : formula.interpretInteger(this.scope)).intValue();
        } catch (InterpretationException e) {
            this.pinInterpretation = 0;
            Log.d(TAG, "Formula interpretation for this specific Brick failed. (pin)", e);
        }
        try {
            Formula formula2 = this.pwmFrequencyFormula;
            this.frequencyInterpretation = (formula2 == null ? Double.valueOf(0.0d) : formula2.interpretDouble(this.scope)).doubleValue();
        } catch (InterpretationException e2) {
            this.frequencyInterpretation = 0.0d;
            Log.d(TAG, "Formula interpretation for this specific Brick failed. (frequency)", e2);
        }
        try {
            Formula formula3 = this.pwmPercentageFormula;
            this.percentageInterpretation = (formula3 == null ? Double.valueOf(0.0d) : formula3.interpretDouble(this.scope)).doubleValue();
        } catch (InterpretationException e3) {
            this.percentageInterpretation = 0.0d;
            Log.d(TAG, "Formula interpretation for this specific Brick failed. (percentage)", e3);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        RPiSocketConnection rPiSocketConnection = RaspberryPiService.getInstance().connection;
        try {
            Log.d(TAG, "RPi pwm pin=" + this.pinInterpretation + ", " + this.percentageInterpretation + "%, " + this.frequencyInterpretation + "Hz");
            rPiSocketConnection.setPWM(this.pinInterpretation, this.frequencyInterpretation, this.percentageInterpretation);
        } catch (Exception e) {
            Log.e(TAG, "RPi: exception during setPwm: " + e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPinNumberFormula(Formula pinNumberFormula) {
        this.pinNumberFormula = pinNumberFormula;
    }

    public void setPwmFrequencyFormula(Formula pwmFrequencyFormula) {
        this.pwmFrequencyFormula = pwmFrequencyFormula;
    }

    public void setPwmPercentageFormula(Formula pwmPercentageFormula) {
        this.pwmPercentageFormula = pwmPercentageFormula;
    }
}
