package org.catrobat.catroid.devices.mindstorms.ev3;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/LegoEV3Impl.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEV3Impl implements LegoEV3, LegoSensorService.OnSensorChangedListener {
    private static final int FREQ_MAX_VAL = 10000;
    private static final int FREQ_MIN_VAL = 250;
    private static final int KEEP_ALIVE_TIME = 5;
    private static final int NUMBER_VOLUME_LEVELS = 13;
    private static final int VOLUME_LEVEL_INCR = 8;
    protected Context context;
    private boolean isInitialized = false;
    protected MindstormsConnection mindstormsConnection;
    private EV3Motor motorA;
    private EV3Motor motorB;
    private EV3Motor motorC;
    private EV3Motor motorD;
    private LegoSensor sensor1;
    private LegoSensor sensor2;
    private LegoSensor sensor3;
    private LegoSensor sensor4;
    private LegoSensorService sensorService;
    private static final UUID LEGO_EV3_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "LegoEV3Impl";

    public LegoEV3Impl(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public String getName() {
        return "Lego EV3";
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public Class<? extends BluetoothDevice> getDeviceType() {
        return BluetoothDevice.LEGO_EV3;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x001c A[PHI: r1
      0x001c: PHI (r1v7 int) = (r1v4 int), (r1v5 int) binds: [B:18:0x001a, B:21:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public void playTone(int frequencyInHz, int durationInMs, int volumeInPercent) {
        if (volumeInPercent > 100) {
            volumeInPercent = 100;
        } else if (volumeInPercent < 0) {
            volumeInPercent = 0;
        }
        if ((volumeInPercent == 0) || (durationInMs <= 0)) {
            return;
        }
        int i = 10000;
        if (frequencyInHz > 10000) {
            frequencyInHz = i;
        } else {
            i = 250;
            if (frequencyInHz < 250) {
                frequencyInHz = i;
            }
        }
        int i2 = 13;
        for (int i3 = 0; i3 < 13; i3++) {
            if (volumeInPercent > i3 * 8) {
                i2 = i3 + 1;
            }
        }
        EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_SOUND);
        this.mindstormsConnection.incCommandCounter();
        eV3Command.append(EV3CommandByte.EV3CommandByteCode.SOUND_PLAY_TONE);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, i2);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, frequencyInHz);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, durationInMs);
        try {
            this.mindstormsConnection.send(eV3Command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public EV3Motor getMotorA() {
        return this.motorA;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public EV3Motor getMotorB() {
        return this.motorB;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public EV3Motor getMotorC() {
        return this.motorC;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public EV3Motor getMotorD() {
        return this.motorD;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensorService.OnSensorChangedListener
    public void onSensorChanged() {
        assignSensorsToPorts();
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void setConnection(BluetoothConnection btConnection) {
        this.mindstormsConnection = new MindstormsConnectionImpl(btConnection);
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3, org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public boolean isAlive() {
        try {
            sendKeepAlive();
            return true;
        } catch (MindstormsException unused) {
            return false;
        }
    }

    private void sendKeepAlive() throws MindstormsException {
        EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_KEEP_ALIVE);
        this.mindstormsConnection.incCommandCounter();
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, 5);
        try {
            this.mindstormsConnection.send(eV3Command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public void moveMotorStepsSpeed(byte b, int i, int i2, int i3, int i4, int i5, boolean z) {
        EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_OUTPUT_STEP_SPEED);
        this.mindstormsConnection.incCommandCounter();
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, i);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, b);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, i2);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, i3);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, i4);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, i5);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, z ? 1 : 0);
        try {
            this.mindstormsConnection.send(eV3Command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public void moveMotorSpeed(byte outputField, int chainLayer, int speed) {
        EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_OUTPUT_SPEED);
        this.mindstormsConnection.incCommandCounter();
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, chainLayer);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, outputField);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, speed);
        EV3Command eV3Command2 = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_OUTPUT_START);
        this.mindstormsConnection.incCommandCounter();
        eV3Command2.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, chainLayer);
        eV3Command2.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, outputField);
        try {
            this.mindstormsConnection.send(eV3Command);
            this.mindstormsConnection.send(eV3Command2);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public void stopMotor(byte b, int i, boolean z) {
        EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_OUTPUT_STOP);
        this.mindstormsConnection.incCommandCounter();
        eV3Command.append((byte) i);
        eV3Command.append(b);
        eV3Command.append(z ? (byte) 1 : (byte) 0);
        try {
            this.mindstormsConnection.send(eV3Command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public void setLed(int ledStatus) {
        EV3Command eV3Command = new EV3Command(this.mindstormsConnection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandByte.EV3CommandOpCode.OP_UI_WRITE);
        this.mindstormsConnection.incCommandCounter();
        eV3Command.append(EV3CommandByte.EV3CommandByteCode.UI_WRITE_LED);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, ledStatus);
        try {
            this.mindstormsConnection.send(eV3Command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/LegoEV3Impl$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3Impl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors;

        static {
            int[] iArr = new int[Sensors.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors = iArr;
            try {
                iArr[Sensors.EV3_SENSOR_1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.EV3_SENSOR_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.EV3_SENSOR_3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.EV3_SENSOR_4.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public float getSensorValue(Sensors sensor) {
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[sensor.ordinal()];
        if (i == 1) {
            LegoSensor legoSensor = this.sensor1;
            if (legoSensor != null) {
                return legoSensor.getLastSensorValue();
            }
            return 0.0f;
        }
        if (i == 2) {
            LegoSensor legoSensor2 = this.sensor2;
            if (legoSensor2 != null) {
                return legoSensor2.getLastSensorValue();
            }
            return 0.0f;
        }
        if (i == 3) {
            LegoSensor legoSensor3 = this.sensor3;
            if (legoSensor3 != null) {
                return legoSensor3.getLastSensorValue();
            }
            return 0.0f;
        }
        if (i != 4) {
            return -1.0f;
        }
        LegoSensor legoSensor4 = this.sensor4;
        if (legoSensor4 != null) {
            return legoSensor4.getLastSensorValue();
        }
        return 0.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public LegoSensor getSensor1() {
        return this.sensor1;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public LegoSensor getSensor2() {
        return this.sensor2;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public LegoSensor getSensor3() {
        return this.sensor3;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public LegoSensor getSensor4() {
        return this.sensor4;
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public synchronized void initialise() throws MindstormsException {
        if (this.isInitialized) {
            return;
        }
        this.mindstormsConnection.init();
        this.motorA = new EV3Motor(0);
        this.motorB = new EV3Motor(1);
        this.motorC = new EV3Motor(2);
        this.motorD = new EV3Motor(3);
        assignSensorsToPorts();
        this.isInitialized = true;
    }

    private synchronized void assignSensorsToPorts() {
        if (this.sensorService == null) {
            LegoSensorService legoSensorService = new LegoSensorService(1, this.mindstormsConnection, PreferenceManager.getDefaultSharedPreferences(this.context));
            this.sensorService = legoSensorService;
            legoSensorService.registerOnSensorChangedListener(this);
        }
        this.sensor1 = this.sensorService.createSensor(0);
        this.sensor2 = this.sensorService.createSensor(1);
        this.sensor3 = this.sensorService.createSensor(2);
        this.sensor4 = this.sensorService.createSensor(3);
    }

    @Override // org.catrobat.catroid.stage.StageResourceInterface
    public void start() throws MindstormsException {
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

    @Override // org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3
    public void stopAllMovements() {
        stopMotor((byte) 15, 0, true);
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public void disconnect() {
        if (this.mindstormsConnection.isConnected()) {
            stopAllMovements();
            this.mindstormsConnection.disconnect();
        }
    }

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    public UUID getBluetoothDeviceUUID() {
        return LEGO_EV3_UUID;
    }
}
