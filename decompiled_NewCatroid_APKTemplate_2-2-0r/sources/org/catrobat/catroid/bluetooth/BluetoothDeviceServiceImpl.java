package org.catrobat.catroid.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/BluetoothDeviceServiceImpl.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class BluetoothDeviceServiceImpl implements BluetoothDeviceService {
    private static final String TAG = "BluetoothDeviceServiceImpl";
    private Map<Class<? extends BluetoothDevice>, BluetoothDevice> connectedDevices = new HashMap();

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDeviceService
    public BluetoothDeviceService.ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> deviceToConnect, Activity activity, int requestCode) {
        if (isDeviceConnectedAndAlive(deviceToConnect)) {
            return BluetoothDeviceService.ConnectDeviceResult.ALREADY_CONNECTED;
        }
        activity.startActivityForResult(createStartIntent(deviceToConnect, activity), requestCode);
        return BluetoothDeviceService.ConnectDeviceResult.CONNECTION_REQUESTED;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDeviceService
    public BluetoothDeviceService.ConnectDeviceResult connectDevice(Class<? extends BluetoothDevice> deviceToConnect, Context context) {
        if (isDeviceConnectedAndAlive(deviceToConnect)) {
            return BluetoothDeviceService.ConnectDeviceResult.ALREADY_CONNECTED;
        }
        context.startActivity(createStartIntent(deviceToConnect, context));
        return BluetoothDeviceService.ConnectDeviceResult.CONNECTION_REQUESTED;
    }

    private synchronized boolean isDeviceConnectedAndAlive(Class<? extends BluetoothDevice> deviceToConnect) {
        BluetoothDevice bluetoothDevice = this.connectedDevices.get(deviceToConnect);
        if (bluetoothDevice != null) {
            try {
                if (bluetoothDevice.isAlive()) {
                    bluetoothDevice.start();
                    return true;
                }
                bluetoothDevice.disconnect();
                this.connectedDevices.remove(deviceToConnect);
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
            }
            throw th;
        }
        return false;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDeviceService
    public synchronized void deviceConnected(BluetoothDevice device) throws MindstormsException {
        this.connectedDevices.put(device.getDeviceType(), device);
        device.start();
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDeviceService
    public synchronized void disconnectDevices() {
        Iterator<BluetoothDevice> it = this.connectedDevices.values().iterator();
        while (it.hasNext()) {
            it.next().disconnect();
        }
        this.connectedDevices.clear();
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDeviceService
    public synchronized <T extends BluetoothDevice> T getDevice(Class<T> btDevice) {
        T t = (T) this.connectedDevices.get(btDevice);
        if (t != null) {
            return t;
        }
        return null;
    }

    protected Intent createStartIntent(Class<? extends BluetoothDevice> deviceToConnect, Context context) {
        Intent intent = new Intent(context, (Class<?>) ConnectBluetoothDeviceActivity.class);
        intent.putExtra(ConnectBluetoothDeviceActivity.DEVICE_TO_CONNECT, deviceToConnect);
        return intent;
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public synchronized void initialise() throws MindstormsException {
        Iterator<BluetoothDevice> it = this.connectedDevices.values().iterator();
        while (it.hasNext()) {
            it.next().initialise();
        }
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public synchronized void start() throws MindstormsException {
        Iterator<BluetoothDevice> it = this.connectedDevices.values().iterator();
        while (it.hasNext()) {
            it.next().start();
        }
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public synchronized void pause() {
        Iterator<BluetoothDevice> it = this.connectedDevices.values().iterator();
        while (it.hasNext()) {
            it.next().pause();
        }
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public synchronized void destroy() {
        Iterator<BluetoothDevice> it = this.connectedDevices.values().iterator();
        while (it.hasNext()) {
            it.next().destroy();
        }
    }
}
