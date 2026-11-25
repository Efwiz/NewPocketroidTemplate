package org.catrobat.catroid.bluetooth.base;

import android.content.Context;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/base/BluetoothDeviceFactory.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public interface BluetoothDeviceFactory {
    <T extends BluetoothDevice> BluetoothDevice createDevice(Class<T> service, Context applicationContext);
}
