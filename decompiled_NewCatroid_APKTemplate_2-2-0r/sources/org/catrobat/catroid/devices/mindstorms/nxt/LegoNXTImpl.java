package org.catrobat.catroid.devices.mindstorms.nxt;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.LegoSensorService;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnectionImpl;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.formulaeditor.Sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/LegoNXTImpl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNXTImpl implements LegoNXT, LegoSensorService.OnSensorChangedListener {
    private static final UUID LEGO_NXT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "LegoNXTImpl";
    protected Context context;
    private boolean isInitialized = false;
    protected MindstormsConnection mindstormsConnection;
    private NXTMotor motorA;
    private NXTMotor motorB;
    private NXTMotor motorC;
    private LegoSensor sensor1;
    private LegoSensor sensor2;
    private LegoSensor sensor3;
    private LegoSensor sensor4;
    private LegoSensorService sensorService;

    public LegoNXTImpl(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public String getName() {
        return "Lego NXT";
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public Class<? extends BluetoothDevice> getDeviceType() {
        return BluetoothDevice.LEGO_NXT;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void setConnection(BluetoothConnection btConnection) {
        this.mindstormsConnection = new MindstormsConnectionImpl(btConnection);
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public UUID getBluetoothDeviceUUID() {
        return LEGO_NXT_UUID;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void disconnect() {
        if (this.mindstormsConnection.isConnected()) {
            stopAllMovements();
            LegoSensorService legoSensorService = this.sensorService;
            if (legoSensorService != null) {
                legoSensorService.deactivateAllSensors(this.mindstormsConnection);
                this.sensorService.destroy();
            }
            this.mindstormsConnection.disconnect();
        }
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public boolean isAlive() {
        try {
            tryGetKeepAliveTime();
            return true;
        } catch (MindstormsException unused) {
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0007 A[PHI: r0
      0x0007: PHI (r0v3 int) = (r0v0 int), (r0v1 int) binds: [B:5:0x0005, B:8:0x000b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public void playTone(int frequencyInHz, int durationInMs) {
        if (durationInMs <= 0) {
            return;
        }
        int i = 14000;
        if (frequencyInHz > 14000) {
            frequencyInHz = i;
        } else {
            i = 200;
            if (frequencyInHz < 200) {
                frequencyInHz = i;
            }
        }
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.PLAY_TONE, false);
        command.append((byte) (frequencyInHz & 255));
        command.append((byte) ((frequencyInHz & 65280) >> 8));
        command.append((byte) (durationInMs & 255));
        command.append((byte) ((durationInMs & 65280) >> 8));
        try {
            this.mindstormsConnection.send(command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public int getKeepAliveTime() {
        try {
            return tryGetKeepAliveTime();
        } catch (NXTException | MindstormsException unused) {
            return -1;
        }
    }

    private int tryGetKeepAliveTime() throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.KEEP_ALIVE, true);
        byte[] bArrSendAndReceive = this.mindstormsConnection.sendAndReceive(command);
        NXTError.checkForError(new NXTReply(this.mindstormsConnection.sendAndReceive(command)), 7);
        return ByteBuffer.wrap(new byte[]{bArrSendAndReceive[3], bArrSendAndReceive[4], bArrSendAndReceive[5], bArrSendAndReceive[6]}).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public int getBatteryLevel() {
        try {
            return tryGetBatteryLevel();
        } catch (NXTException | MindstormsException unused) {
            return -1;
        }
    }

    private int tryGetBatteryLevel() throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.GET_BATTERY_LEVEL, true);
        NXTError.checkForError(new NXTReply(this.mindstormsConnection.sendAndReceive(command)), 5);
        byte[] bArrSendAndReceive = this.mindstormsConnection.sendAndReceive(command);
        return ByteBuffer.wrap(new byte[]{bArrSendAndReceive[3], bArrSendAndReceive[4]}).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public NXTMotor getMotorA() {
        return this.motorA;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public NXTMotor getMotorB() {
        return this.motorB;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public NXTMotor getMotorC() {
        return this.motorC;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public void stopAllMovements() {
        this.motorA.stop();
        this.motorB.stop();
        this.motorC.stop();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/LegoNXTImpl$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.devices.mindstorms.nxt.LegoNXTImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors;

        static {
            int[] iArr = new int[Sensors.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors = iArr;
            try {
                iArr[Sensors.NXT_SENSOR_1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NXT_SENSOR_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NXT_SENSOR_3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.NXT_SENSOR_4.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public synchronized float getSensorValue(Sensors sensor) {
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[sensor.ordinal()];
        if (i == 1) {
            LegoSensor legoSensor = this.sensor1;
            return legoSensor != null ? legoSensor.getLastSensorValue() : 0.0f;
        }
        if (i == 2) {
            LegoSensor legoSensor2 = this.sensor2;
            return legoSensor2 != null ? legoSensor2.getLastSensorValue() : 0.0f;
        }
        if (i == 3) {
            LegoSensor legoSensor3 = this.sensor3;
            return legoSensor3 != null ? legoSensor3.getLastSensorValue() : 0.0f;
        }
        if (i != 4) {
            return -1.0f;
        }
        LegoSensor legoSensor4 = this.sensor4;
        return legoSensor4 != null ? legoSensor4.getLastSensorValue() : 0.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public LegoSensor getSensor1() {
        return this.sensor1;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public LegoSensor getSensor2() {
        return this.sensor2;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public LegoSensor getSensor3() {
        return this.sensor3;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT
    public LegoSensor getSensor4() {
        return this.sensor4;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensorService.OnSensorChangedListener
    public void onSensorChanged() {
        assignSensorsToPorts();
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public synchronized void initialise() {
        if (this.isInitialized) {
            return;
        }
        try {
            this.mindstormsConnection.init();
            this.motorA = new NXTMotor(0, this.mindstormsConnection);
            this.motorB = new NXTMotor(1, this.mindstormsConnection);
            this.motorC = new NXTMotor(2, this.mindstormsConnection);
            assignSensorsToPorts();
            this.isInitialized = true;
        } catch (MindstormsException unused) {
            this.isInitialized = false;
        }
    }

    private synchronized void assignSensorsToPorts() {
        if (this.sensorService == null) {
            LegoSensorService legoSensorService = new LegoSensorService(0, this.mindstormsConnection, PreferenceManager.getDefaultSharedPreferences(this.context));
            this.sensorService = legoSensorService;
            legoSensorService.registerOnSensorChangedListener(this);
        }
        this.sensor1 = this.sensorService.createSensor(0);
        this.sensor2 = this.sensorService.createSensor(1);
        this.sensor3 = this.sensorService.createSensor(2);
        this.sensor4 = this.sensorService.createSensor(3);
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void start() {
        initialise();
        assignSensorsToPorts();
        this.sensorService.resumeSensorUpdate();
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void pause() {
        stopAllMovements();
        this.sensorService.pauseSensorUpdate();
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void destroy() {
        this.sensorService.deactivateAllSensors(this.mindstormsConnection);
    }
}
