package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3PlayToneAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3PlayToneAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private Formula durationInSeconds;
    private Formula hertz;
    private Scope scope;
    private Formula volumeInPercent;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        float fFloatValue;
        int iIntValue2 = 0;
        try {
            iIntValue = this.hertz.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            iIntValue = 0;
        }
        try {
            fFloatValue = this.durationInSeconds.interpretFloat(this.scope).floatValue();
        } catch (InterpretationException e2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e2);
            fFloatValue = 0.0f;
        }
        try {
            iIntValue2 = this.volumeInPercent.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e3) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e3);
        }
        LegoEV3 legoEV3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (legoEV3 == null) {
            return;
        }
        legoEV3.playTone(iIntValue * 100, (int) (fFloatValue * 1000.0f), iIntValue2);
    }

    public void setHertz(Formula hertz) {
        this.hertz = hertz;
    }

    public void setDurationInSeconds(Formula durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setVolumeInPercent(Formula volumeInPercent) {
        this.volumeInPercent = volumeInPercent;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
