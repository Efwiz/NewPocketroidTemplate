package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import android.util.Log;
import java.util.Locale;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.nxt.Command;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandByte;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandType;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTError;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTReply;
import org.catrobat.catroid.utils.EnumUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class NXTSensor implements LegoSensor {
    public static final String TAG = "NXTSensor";
    protected final MindstormsConnection connection;
    public boolean hasInit;
    protected final int port;
    protected final NXTSensorMode sensorMode;
    protected final NXTSensorType sensorType;
    protected final int updateInterval = 250;
    protected float lastValidValue = 0.0f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTSensor$SensorReadings.class
      classes14.dex
     */
    public static class SensorReadings {
        public int normalized;
        public int raw;
        public int scaled;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public int getUpdateInterval() {
        return 250;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTSensor$Sensor.class
      classes14.dex
     */
    public enum Sensor {
        NO_SENSOR,
        TOUCH,
        SOUND,
        LIGHT_INACTIVE,
        LIGHT_ACTIVE,
        ULTRASONIC;

        public static String[] getSensorCodes() {
            String[] strArr = new String[values().length];
            for (int i = 0; i < values().length; i++) {
                strArr[i] = values()[i].name();
            }
            return strArr;
        }

        public String getSensorCode() {
            return name();
        }

        public static Sensor getSensorFromSensorCode(String sensorCode) {
            Sensor sensor = (Sensor) EnumUtils.getEnum(Sensor.class, sensorCode);
            return sensor != null ? sensor : NO_SENSOR;
        }
    }

    public NXTSensor(int port, NXTSensorType sensorType, NXTSensorMode sensorMode, MindstormsConnection connection) {
        this.port = port;
        this.sensorType = sensorType;
        this.sensorMode = sensorMode;
        this.connection = connection;
    }

    public void updateTypeAndMode() throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.SET_INPUT_MODE, true);
        command.append((byte) this.port);
        command.append(this.sensorType.getByte());
        command.append(this.sensorMode.getByte());
        NXTError.checkForError(new NXTReply(this.connection.sendAndReceive(command)), 3);
    }

    public int getScaledValue() throws MindstormsException {
        return getSensorReadings().scaled;
    }

    protected int getRawValue() throws MindstormsException {
        return getSensorReadings().raw;
    }

    protected int getNormalizedValue() throws MindstormsException {
        return getSensorReadings().normalized;
    }

    public SensorReadings getSensorReadings() throws MindstormsException {
        if (!this.hasInit) {
            initialize();
        }
        SensorReadings sensorReadings = new SensorReadings();
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.GET_INPUT_VALUES, true);
        command.append((byte) this.port);
        NXTReply nXTReply = new NXTReply(this.connection.sendAndReceive(command));
        NXTError.checkForError(nXTReply, 16);
        sensorReadings.raw = nXTReply.getShort(8);
        sensorReadings.normalized = nXTReply.getShort(10);
        sensorReadings.scaled = nXTReply.getShort(12);
        return sensorReadings;
    }

    protected void initialize() throws MindstormsException {
        MindstormsConnection mindstormsConnection = this.connection;
        if (mindstormsConnection != null && mindstormsConnection.isConnected()) {
            try {
                updateTypeAndMode();
                Thread.sleep(100L);
                resetScaledValue();
                Thread.sleep(100L);
                updateTypeAndMode();
                this.hasInit = true;
                return;
            } catch (InterruptedException unused) {
                this.hasInit = false;
                return;
            }
        }
        this.hasInit = false;
    }

    public void resetScaledValue() throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.RESET_INPUT_SCALED_VALUE, false);
        command.append((byte) this.port);
        this.connection.send(command);
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public void updateLastSensorValue() {
        try {
            this.lastValidValue = getValue();
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getLastSensorValue() {
        return this.lastValidValue;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public String getName() {
        return String.format(Locale.getDefault(), "%s_%s_%d", TAG, this.sensorType.name(), Integer.valueOf(this.port));
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public int getConnectedPort() {
        return this.port;
    }
}
