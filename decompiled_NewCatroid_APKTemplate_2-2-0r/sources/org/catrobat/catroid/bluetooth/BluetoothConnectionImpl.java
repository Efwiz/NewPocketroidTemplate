package org.catrobat.catroid.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/BluetoothConnectionImpl.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class BluetoothConnectionImpl implements BluetoothConnection {
    private static final String REFLECTION_METHOD_NAME = "createRfcommSocket";
    private static final String TAG = "BluetoothConnectionImpl";
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
    private final String macAddress;
    private BluetoothConnection.State state = BluetoothConnection.State.NOT_CONNECTED;
    private final UUID uuid;

    public BluetoothConnectionImpl(String macAddress, UUID uuid) {
        this.macAddress = macAddress;
        this.uuid = uuid;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothConnection
    public BluetoothConnection.State connect() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.bluetoothAdapter = defaultAdapter;
        if (defaultAdapter == null) {
            BluetoothConnection.State state = BluetoothConnection.State.ERROR_BLUETOOTH_NOT_SUPPORTED;
            this.state = state;
            return state;
        }
        if (defaultAdapter.getState() != 12) {
            BluetoothConnection.State state2 = BluetoothConnection.State.ERROR_ADAPTER;
            this.state = state2;
            return state2;
        }
        String str = TAG;
        Log.d(str, "Got Adapter and Adapter was on");
        BluetoothDevice remoteDevice = this.bluetoothAdapter.getRemoteDevice(this.macAddress);
        this.bluetoothDevice = remoteDevice;
        if (remoteDevice == null) {
            BluetoothConnection.State state3 = BluetoothConnection.State.ERROR_DEVICE;
            this.state = state3;
            return state3;
        }
        Log.d(str, "Got remote device");
        try {
            this.bluetoothSocket = this.bluetoothDevice.createRfcommSocketToServiceRecord(this.uuid);
            Log.d(str, "Socket was created");
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$bluetooth$base$BluetoothConnection$State[connectSocket(this.bluetoothSocket).ordinal()];
            if (i == 1) {
                Log.d(str, "connected");
                return BluetoothConnection.State.CONNECTED;
            }
            if (i == 2) {
                Log.d(str, "error connecting");
                return BluetoothConnection.State.ERROR_SOCKET;
            }
            Log.wtf(str, "This should never happen!");
            return BluetoothConnection.State.NOT_CONNECTED;
        } catch (IOException unused) {
            if (this.bluetoothDevice.getBondState() == 10) {
                BluetoothConnection.State state4 = BluetoothConnection.State.ERROR_NOT_BONDED;
                this.state = state4;
                return state4;
            }
            if (this.bluetoothDevice.getBondState() == 11) {
                BluetoothConnection.State state5 = BluetoothConnection.State.ERROR_STILL_BONDING;
                this.state = state5;
                return state5;
            }
            BluetoothConnection.State state6 = BluetoothConnection.State.ERROR_SOCKET;
            this.state = state6;
            return state6;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/BluetoothConnectionImpl$1.class
      classes12.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.bluetooth.BluetoothConnectionImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$bluetooth$base$BluetoothConnection$State;

        static {
            int[] iArr = new int[BluetoothConnection.State.values().length];
            $SwitchMap$org$catrobat$catroid$bluetooth$base$BluetoothConnection$State = iArr;
            try {
                iArr[BluetoothConnection.State.CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$bluetooth$base$BluetoothConnection$State[BluetoothConnection.State.ERROR_SOCKET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothConnection
    public BluetoothConnection.State connectSocket(BluetoothSocket socket) {
        if (socket == null) {
            BluetoothConnection.State state = BluetoothConnection.State.NOT_CONNECTED;
            this.state = state;
            return state;
        }
        Log.d(TAG, "Connecting");
        this.bluetoothSocket = socket;
        try {
            socket.connect();
            BluetoothConnection.State state2 = BluetoothConnection.State.CONNECTED;
            this.state = state2;
            return state2;
        } catch (IOException e) {
            try {
                String str = TAG;
                Log.e(str, Log.getStackTraceString(e));
                Log.d(str, "Try connecting again");
                BluetoothSocket bluetoothSocket = (BluetoothSocket) this.bluetoothDevice.getClass().getMethod(REFLECTION_METHOD_NAME, Integer.TYPE).invoke(this.bluetoothDevice, 1);
                this.bluetoothSocket = bluetoothSocket;
                bluetoothSocket.connect();
                BluetoothConnection.State state3 = BluetoothConnection.State.CONNECTED;
                this.state = state3;
                return state3;
            } catch (IOException e2) {
                Log.e(TAG, Log.getStackTraceString(e2));
                BluetoothConnection.State state4 = BluetoothConnection.State.ERROR_SOCKET;
                this.state = state4;
                return state4;
            } catch (IllegalAccessException e3) {
                Log.e(TAG, Log.getStackTraceString(e3));
                BluetoothConnection.State state5 = BluetoothConnection.State.ERROR_SOCKET;
                this.state = state5;
                return state5;
            } catch (NoSuchMethodException e4) {
                Log.e(TAG, Log.getStackTraceString(e4));
                BluetoothConnection.State state6 = BluetoothConnection.State.ERROR_SOCKET;
                this.state = state6;
                return state6;
            } catch (InvocationTargetException e5) {
                Log.e(TAG, Log.getStackTraceString(e5));
                BluetoothConnection.State state7 = BluetoothConnection.State.ERROR_SOCKET;
                this.state = state7;
                return state7;
            }
        }
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothConnection
    public void disconnect() {
        Log.d(TAG, "disconnecting");
        try {
            this.state = BluetoothConnection.State.NOT_CONNECTED;
            BluetoothSocket bluetoothSocket = this.bluetoothSocket;
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                this.bluetoothSocket = null;
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothConnection
    public InputStream getInputStream() throws IOException {
        return this.bluetoothSocket.getInputStream();
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothConnection
    public OutputStream getOutputStream() throws IOException {
        return this.bluetoothSocket.getOutputStream();
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothConnection
    public BluetoothConnection.State getState() {
        return this.state;
    }
}
