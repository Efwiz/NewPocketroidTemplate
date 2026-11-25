package org.catrobat.catroid.bluetooth.base;

import android.content.Context;
import java.util.UUID;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/base/BluetoothConnectionFactory.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public interface BluetoothConnectionFactory {
    <T extends BluetoothDevice> BluetoothConnection createBTConnectionForDevice(Class<T> bluetoothDeviceType, String address, UUID deviceUUID, Context applicationContext);
}
