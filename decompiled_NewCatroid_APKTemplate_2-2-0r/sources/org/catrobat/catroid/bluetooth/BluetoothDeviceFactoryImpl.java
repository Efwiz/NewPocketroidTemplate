package org.catrobat.catroid.bluetooth;

import android.content.Context;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceFactory;
import org.catrobat.catroid.devices.arduino.ArduinoImpl;
import org.catrobat.catroid.devices.arduino.phiro.PhiroImpl;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3Impl;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXTImpl;
import org.catrobat.catroid.devices.multiplayer.Multiplayer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/BluetoothDeviceFactoryImpl.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class BluetoothDeviceFactoryImpl implements BluetoothDeviceFactory {
    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDeviceFactory
    public <T extends BluetoothDevice> BluetoothDevice createDevice(Class<T> service, Context applicationContext) {
        if (service == BluetoothDevice.LEGO_NXT) {
            return new LegoNXTImpl(applicationContext);
        }
        if (service == BluetoothDevice.LEGO_EV3) {
            return new LegoEV3Impl(applicationContext);
        }
        if (service == BluetoothDevice.PHIRO) {
            return new PhiroImpl();
        }
        if (service == BluetoothDevice.ARDUINO) {
            return new ArduinoImpl();
        }
        if (service == BluetoothDevice.MULTIPLAYER) {
            return new Multiplayer();
        }
        return null;
    }
}
