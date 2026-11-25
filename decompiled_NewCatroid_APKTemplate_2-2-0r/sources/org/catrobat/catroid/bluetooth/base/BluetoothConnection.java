package org.catrobat.catroid.bluetooth.base;

import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/base/BluetoothConnection.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public interface BluetoothConnection {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/base/BluetoothConnection$State.class
      classes12.dex
     */
    public enum State {
        CONNECTED,
        NOT_CONNECTED,
        ERROR_BLUETOOTH_NOT_SUPPORTED,
        ERROR_BLUETOOTH_NOT_ON,
        ERROR_ADAPTER,
        ERROR_DEVICE,
        ERROR_SOCKET,
        ERROR_STILL_BONDING,
        ERROR_NOT_BONDED,
        ERROR_CLOSING
    }

    State connect();

    State connectSocket(BluetoothSocket socket);

    void disconnect();

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    State getState();
}
