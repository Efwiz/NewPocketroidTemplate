package org.catrobat.catroid.bluetooth.base;

import android.app.Activity;
import android.content.Context;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.stage.StageResourceInterface;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/base/BluetoothDeviceService.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public interface BluetoothDeviceService extends CatroidService, StageResourceInterface {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/base/BluetoothDeviceService$ConnectDeviceResult.class
      classes12.dex
     */
    public enum ConnectDeviceResult {
        ALREADY_CONNECTED,
        CONNECTION_REQUESTED
    }

    ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> deviceType, Activity activity, int requestCode);

    ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> deviceToConnect, Context context);

    void deviceConnected(BluetoothDevice device) throws MindstormsException;

    void disconnectDevices();

    <T extends BluetoothDevice> T getDevice(Class<T> btDevice);
}
