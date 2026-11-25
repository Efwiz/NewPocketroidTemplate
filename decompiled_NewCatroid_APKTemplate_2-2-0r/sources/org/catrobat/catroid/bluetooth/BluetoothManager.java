package org.catrobat.catroid.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/BluetoothManager.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class BluetoothManager {
    public static final int BLUETOOTH_ACTIVATING = 0;
    public static final int BLUETOOTH_ALREADY_ON = 1;
    public static final int BLUETOOTH_NOT_SUPPORTED = -1;
    public static final int REQUEST_BT_PERMISSIONS = 2001;
    public static final int REQUEST_ENABLE_BT = 2000;
    private final Activity activity;
    private BluetoothAdapter bluetoothAdapter;

    public BluetoothManager(Activity activity) {
        this.activity = activity;
    }

    public int activateBluetooth() {
        if (this.bluetoothAdapter == null) {
            this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (this.bluetoothAdapter == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 31 && ContextCompat.checkSelfPermission(this.activity, "android.permission.BLUETOOTH_CONNECT") != 0) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_SCAN"}, 2001);
            return 0;
        }
        if (this.bluetoothAdapter.isEnabled()) {
            return 1;
        }
        this.activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2000);
        return 0;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.bluetoothAdapter;
    }
}
