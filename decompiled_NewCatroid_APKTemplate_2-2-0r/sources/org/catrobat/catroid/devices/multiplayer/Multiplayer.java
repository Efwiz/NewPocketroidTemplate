package org.catrobat.catroid.devices.multiplayer;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/multiplayer/Multiplayer.class
  classes14.dex
 */
/* JADX INFO: compiled from: Multiplayer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 12\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0016\u001a\u00020\u0017J\b\u0010\u0018\u001a\u00020\u0017H\u0016J\b\u0010\u0019\u001a\u00020\u0017H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u00020\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010\u001f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010!0 H\u0016J\b\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\u0017H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020\u0017H\u0016J\u0012\u0010(\u001a\u00020\u00172\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u0014\u0010+\u001a\u00020\u00172\f\u0010\u0003\u001a\b\u0018\u00010\u0004R\u00020\u0005J\u0010\u0010,\u001a\u00020\u00172\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u0010\u0010-\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\u0007H\u0016J\u001c\u0010/\u001a\u00020\u00172\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u00100\u001a\u00020\u0017H\u0016R\u0014\u0010\u0003\u001a\b\u0018\u00010\u0004R\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\u00118FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013¨\u00062"}, d2 = {"Lorg/catrobat/catroid/devices/multiplayer/Multiplayer;", "Lorg/catrobat/catroid/devices/multiplayer/MultiplayerInterface;", "()V", "acceptThread", "Lorg/catrobat/catroid/bluetooth/ConnectBluetoothDeviceActivity$AcceptThread;", "Lorg/catrobat/catroid/bluetooth/ConnectBluetoothDeviceActivity;", "bluetoothConnection", "Lorg/catrobat/catroid/bluetooth/base/BluetoothConnection;", "bluetoothSocket", "Landroid/bluetooth/BluetoothSocket;", "connectedThread", "Ljava/lang/Thread;", "inputStream", "Ljava/io/InputStream;", "outputStream", "Ljava/io/OutputStream;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "closeStreams", "", "destroy", MqttDisconnectHandler.NAME, "getBluetoothDeviceUUID", "Ljava/util/UUID;", "getChangedMultiplayerVariables", "receivedData", "", "getDeviceType", "Ljava/lang/Class;", "Lorg/catrobat/catroid/bluetooth/base/BluetoothDevice;", "getName", "", "initialise", "isAlive", "", "pause", "sendChangedMultiplayerVariables", "multiplayerVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setAcceptThread", "setBluetoothSocket", "setConnection", "connection", "setStreams", "start", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Multiplayer implements MultiplayerInterface {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final UUID MULTIPLAYER_UUID;
    private static final int READ_BLOCKSIZE = 1024;
    private static final String TAG;
    private ConnectBluetoothDeviceActivity.AcceptThread acceptThread;
    private BluetoothConnection bluetoothConnection;
    private BluetoothSocket bluetoothSocket;
    private Thread connectedThread;
    private InputStream inputStream;
    private OutputStream outputStream;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager = KoinJavaComponent.inject$default(ProjectManager.class, null, null, 6, null);

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void destroy() {
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void initialise() {
    }

    public final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public String getName() {
        return "MULTIPLAYER";
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void setConnection(BluetoothConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        this.bluetoothConnection = connection;
    }

    public final void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
    }

    public final void setAcceptThread(ConnectBluetoothDeviceActivity.AcceptThread acceptThread) {
        this.acceptThread = acceptThread;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public Class<? extends BluetoothDevice> getDeviceType() {
        Class<MultiplayerInterface> MULTIPLAYER = BluetoothDevice.MULTIPLAYER;
        Intrinsics.checkNotNullExpressionValue(MULTIPLAYER, "MULTIPLAYER");
        return MULTIPLAYER;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void disconnect() {
        BluetoothConnection bluetoothConnection = this.bluetoothConnection;
        if (bluetoothConnection != null) {
            if (bluetoothConnection != null) {
                bluetoothConnection.disconnect();
            }
            this.bluetoothConnection = null;
        }
        BluetoothSocket bluetoothSocket = this.bluetoothSocket;
        if (bluetoothSocket != null) {
            if (bluetoothSocket != null) {
                try {
                    bluetoothSocket.close();
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage(), e);
                }
            }
            this.bluetoothSocket = null;
        }
        closeStreams();
        ConnectBluetoothDeviceActivity.AcceptThread acceptThread = this.acceptThread;
        if (acceptThread == null || !acceptThread.isAlive()) {
            return;
        }
        ConnectBluetoothDeviceActivity.AcceptThread acceptThread2 = this.acceptThread;
        if (acceptThread2 != null) {
            acceptThread2.cancel();
        }
        ConnectBluetoothDeviceActivity.AcceptThread acceptThread3 = this.acceptThread;
        if (acceptThread3 != null) {
            acceptThread3.interrupt();
        }
    }

    public final void closeStreams() {
        try {
            InputStream inputStream = this.inputStream;
            if (inputStream != null) {
                inputStream.close();
            }
            this.inputStream = null;
        } catch (IOException e) {
            Log.d(TAG, e.getMessage(), e);
        }
        try {
            OutputStream outputStream = this.outputStream;
            if (outputStream != null) {
                outputStream.close();
            }
            this.outputStream = null;
        } catch (IOException e2) {
            Log.d(TAG, e2.getMessage(), e2);
        }
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public boolean isAlive() {
        return (this.inputStream == null || this.outputStream == null) ? false : true;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public UUID getBluetoothDeviceUUID() {
        return MULTIPLAYER_UUID;
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void start() {
        Thread thread = new Thread() { // from class: org.catrobat.catroid.devices.multiplayer.Multiplayer.start.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                byte[] bArr = new byte[1024];
                while (!isInterrupted()) {
                    try {
                        InputStream inputStream = Multiplayer.this.inputStream;
                        if ((inputStream != null ? inputStream.read(bArr) : 0) > 0) {
                            Multiplayer.this.getChangedMultiplayerVariables(bArr);
                        }
                    } catch (IOException e) {
                        Log.d(Multiplayer.TAG, "Input stream was disconnected", e);
                        Multiplayer.this.disconnect();
                        return;
                    }
                }
            }
        };
        this.connectedThread = thread;
        thread.start();
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void pause() {
        Thread thread = this.connectedThread;
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override // org.catrobat.catroid.devices.multiplayer.MultiplayerInterface
    public void sendChangedMultiplayerVariables(UserVariable multiplayerVariable) {
        MultiplayerVariableMessage multiplayerVariableMessage;
        if (multiplayerVariable != null) {
            String name2 = multiplayerVariable.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            Object value = multiplayerVariable.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            multiplayerVariableMessage = new MultiplayerVariableMessage(name2, value);
        } else {
            multiplayerVariableMessage = null;
        }
        try {
            OutputStream outputStream = this.outputStream;
            if (outputStream != null) {
                outputStream.write(SerializationUtils.serialize(multiplayerVariableMessage));
            }
        } catch (IOException e) {
            Log.d(TAG, "Error occurred when sending data", e);
            disconnect();
        }
    }

    @Override // org.catrobat.catroid.devices.multiplayer.MultiplayerInterface
    public void getChangedMultiplayerVariables(byte[] receivedData) {
        try {
            MultiplayerVariableMessage multiplayerVariableMessage = (MultiplayerVariableMessage) SerializationUtils.deserialize(receivedData);
            UserVariable multiplayerVariable = getProjectManager().getCurrentProject().getMultiplayerVariable(multiplayerVariableMessage.getName());
            if (multiplayerVariable == null) {
                return;
            }
            multiplayerVariable.setValue(multiplayerVariableMessage.getValue());
        } catch (SerializationException unused) {
        }
    }

    @Override // org.catrobat.catroid.devices.multiplayer.MultiplayerInterface
    public void setStreams(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/multiplayer/Multiplayer$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: Multiplayer.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/devices/multiplayer/Multiplayer$Companion;", "", "()V", "MULTIPLAYER_UUID", "Ljava/util/UUID;", "getMULTIPLAYER_UUID", "()Ljava/util/UUID;", "READ_BLOCKSIZE", "", "TAG", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UUID getMULTIPLAYER_UUID() {
            return Multiplayer.MULTIPLAYER_UUID;
        }
    }

    static {
        UUID uuidFromString = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        Intrinsics.checkNotNullExpressionValue(uuidFromString, "fromString(...)");
        MULTIPLAYER_UUID = uuidFromString;
        TAG = Reflection.getOrCreateKotlinClass(Multiplayer.class).getSimpleName();
    }
}
