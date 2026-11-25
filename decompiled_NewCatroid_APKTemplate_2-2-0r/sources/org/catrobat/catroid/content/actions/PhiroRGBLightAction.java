package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroRGBLightAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroRGBLightAction extends TemporalAction {
    private static final int MAX_VALUE = 255;
    private static final int MIN_VALUE = 0;
    private static final String TAG = "PhiroRGBLightAction";
    private Formula blue;
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private PhiroRGBLightBrick.Eye eyeEnum;
    private Formula green;
    private Formula red;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iUpdateFormulaValue = updateFormulaValue(this.red);
        int iUpdateFormulaValue2 = updateFormulaValue(this.green);
        int iUpdateFormulaValue3 = updateFormulaValue(this.blue);
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (this.eyeEnum.equals(PhiroRGBLightBrick.Eye.LEFT)) {
            phiro.setLeftRGBLightColor(iUpdateFormulaValue, iUpdateFormulaValue2, iUpdateFormulaValue3);
            return;
        }
        if (this.eyeEnum.equals(PhiroRGBLightBrick.Eye.RIGHT)) {
            phiro.setRightRGBLightColor(iUpdateFormulaValue, iUpdateFormulaValue2, iUpdateFormulaValue3);
        } else if (this.eyeEnum.equals(PhiroRGBLightBrick.Eye.BOTH)) {
            phiro.setLeftRGBLightColor(iUpdateFormulaValue, iUpdateFormulaValue2, iUpdateFormulaValue3);
            phiro.setRightRGBLightColor(iUpdateFormulaValue, iUpdateFormulaValue2, iUpdateFormulaValue3);
        } else {
            Log.d(TAG, "Error: EyeEnum:" + this.eyeEnum);
        }
    }

    private int updateFormulaValue(Formula rgbFormula) {
        int iIntValue;
        try {
            iIntValue = rgbFormula.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            iIntValue = 0;
        }
        if (iIntValue < 0) {
            return 0;
        }
        if (iIntValue > 255) {
            return 255;
        }
        return iIntValue;
    }

    public void setEyeEnum(PhiroRGBLightBrick.Eye eyeEnum) {
        this.eyeEnum = eyeEnum;
    }

    public void setRed(Formula red) {
        this.red = red;
    }

    public void setGreen(Formula green) {
        this.green = green;
    }

    public void setBlue(Formula blue) {
        this.blue = blue;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
