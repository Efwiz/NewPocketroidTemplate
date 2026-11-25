package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoNxtPlayToneAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNxtPlayToneAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private Formula durationInSeconds;
    private Formula hertz;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        float fFloatValue;
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
        LegoNXT legoNXT = (LegoNXT) this.btService.getDevice(BluetoothDevice.LEGO_NXT);
        if (legoNXT == null) {
            return;
        }
        legoNXT.playTone(iIntValue * 100, (int) (fFloatValue * 1000.0f));
    }

    public void setHertz(Formula hertz) {
        this.hertz = hertz;
    }

    public void setDurationInSeconds(Formula durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
