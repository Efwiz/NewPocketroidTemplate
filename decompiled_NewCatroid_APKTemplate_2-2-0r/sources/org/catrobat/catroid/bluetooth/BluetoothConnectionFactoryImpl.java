package org.catrobat.catroid.bluetooth;

import android.content.Context;
import java.util.UUID;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothConnectionFactory;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/BluetoothConnectionFactoryImpl.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class BluetoothConnectionFactoryImpl implements BluetoothConnectionFactory {
    @Override // org.catrobat.catroid.bluetooth.base.BluetoothConnectionFactory
    public <T extends BluetoothDevice> BluetoothConnection createBTConnectionForDevice(Class<T> bluetoothDeviceType, String address, UUID deviceUUID, Context applicationContext) {
        return new BluetoothConnectionImpl(address, deviceUUID);
    }
}
