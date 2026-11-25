package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import android.util.Log;
import android.util.SparseArray;
import java.util.Locale;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3Command;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandType;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3Reply;
import org.catrobat.catroid.utils.EnumUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3Sensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class EV3Sensor implements LegoSensor {
    public static final String TAG = "EV3Sensor";
    protected final MindstormsConnection connection;
    protected boolean hasInit;
    protected final int port;
    protected final EV3SensorMode sensorMode;
    protected final EV3SensorType sensorType;
    protected final int updateInterval = 250;
    protected float lastValidValue = 0.0f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3Sensor$CpuCommandFlag.class
      classes14.dex
     */
    public enum CpuCommandFlag {
        INCLUDE_TYPE_AND_MODE,
        NO_TYPE_AND_MODE,
        INCLUDE_RETURN_VALUE,
        NO_RETURN_VALUE
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public int getUpdateInterval() {
        return 250;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3Sensor$Sensor.class
      classes14.dex
     */
    public enum Sensor {
        NO_SENSOR,
        TOUCH,
        COLOR,
        COLOR_AMBIENT,
        COLOR_REFLECT,
        INFRARED,
        HT_NXT_COLOR,
        NXT_TEMPERATURE_C,
        NXT_TEMPERATURE_F,
        NXT_LIGHT,
        NXT_LIGHT_ACTIVE,
        NXT_SOUND,
        NXT_ULTRASONIC;

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

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3Sensor$SensorConnectionType.class
      classes14.dex
     */
    public enum SensorConnectionType {
        CONN_UNKNOWN(111),
        CONN_DAISYCHAIN(117),
        CONN_NXT_COLOR(118),
        CONN_NXT_ANALOG(119),
        CONN_NXT_IIC(120),
        CONN_EV3_IN_DUMB(121),
        CONN_EV3_IN_UART(122),
        CONN_EV3_OUT_DUMB(123),
        CONN_EV3_OUT_INTELLIGENT(124),
        CONN_EV3_OUT_TACHO(125),
        CONN_NONE(126),
        CONN_ERROR(127);

        private static final SparseArray<SensorConnectionType> LOOKUP = new SparseArray<>();
        private int sensorConnectionByteCode;

        static {
            for (SensorConnectionType sensorConnectionType : values()) {
                LOOKUP.put(sensorConnectionType.sensorConnectionByteCode, sensorConnectionType);
            }
        }

        public static SensorConnectionType getSensorConnectionTypeByValue(byte value) {
            return LOOKUP.get(value & 255);
        }

        public static boolean isMember(byte memberToTest) {
            return LOOKUP.get(memberToTest & 255) != null;
        }

        SensorConnectionType(int sensorConnectionType) {
            this.sensorConnectionByteCode = sensorConnectionType;
        }

        public byte getByte() {
            return (byte) this.sensorConnectionByteCode;
        }
    }

    public EV3Sensor(int port, EV3SensorType sensorType, EV3SensorMode sensorMode, MindstormsConnection connection) {
        this.port = port;
        this.sensorType = sensorType;
        this.sensorMode = sensorMode;
        this.connection = connection;
    }

    public SensorConnectionType getConnectionType(int chainLayer) {
        short commandCounter = this.connection.getCommandCounter();
        EV3Command eV3Command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, 1, 0, EV3CommandByte.EV3CommandOpCode.OP_INPUT_DEVICE);
        this.connection.incCommandCounter();
        eV3Command.append(EV3CommandByte.EV3CommandByteCode.INPUT_DEVICE_GET_CONNECTION.getByte());
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, chainLayer);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
        byte b = 0;
        eV3Command.append(EV3CommandByte.EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
        try {
            b = sendCommandAndGetReply(eV3Command, commandCounter).getByte(3);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
        return SensorConnectionType.getSensorConnectionTypeByValue(b);
    }

    protected void initialize() {
        MindstormsConnection mindstormsConnection = this.connection;
        if (mindstormsConnection != null && mindstormsConnection.isConnected()) {
            short commandCounter = this.connection.getCommandCounter();
            this.hasInit = true;
            try {
                sendCommandAndGetReply(buildCommand(1, EV3CommandByte.EV3CommandByteCode.INPUT_DEVICE_READY_RAW, EV3CommandByte.EV3CommandOpCode.OP_INPUT_DEVICE, CpuCommandFlag.INCLUDE_TYPE_AND_MODE, CpuCommandFlag.INCLUDE_RETURN_VALUE), commandCounter);
                return;
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
                return;
            }
        }
        this.hasInit = false;
    }

    public int getPercentValue() {
        if (!this.hasInit) {
            initialize();
        } else {
            try {
                return sendCommandAndGetReply(buildCommand(1, null, EV3CommandByte.EV3CommandOpCode.OP_INPUT_READ, CpuCommandFlag.INCLUDE_TYPE_AND_MODE, CpuCommandFlag.NO_RETURN_VALUE), this.connection.getCommandCounter()).getByte(3);
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return 0;
    }

    public byte[] getRawValue(int numBytes) {
        byte[] bArr = new byte[numBytes];
        if (!this.hasInit) {
            initialize();
            return bArr;
        }
        try {
            EV3Reply eV3ReplySendCommandAndGetReply = sendCommandAndGetReply(buildCommand(numBytes, EV3CommandByte.EV3CommandByteCode.INPUT_DEVICE_GET_RAW, EV3CommandByte.EV3CommandOpCode.OP_INPUT_DEVICE, CpuCommandFlag.NO_TYPE_AND_MODE, CpuCommandFlag.NO_RETURN_VALUE), this.connection.getCommandCounter());
            return eV3ReplySendCommandAndGetReply.getData(3, eV3ReplySendCommandAndGetReply.getLength() - 3);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
            return bArr;
        }
    }

    public byte[] getSiValue(int numBytes) {
        byte[] bArr = new byte[numBytes];
        if (!this.hasInit) {
            initialize();
            return bArr;
        }
        try {
            EV3Reply eV3ReplySendCommandAndGetReply = sendCommandAndGetReply(buildCommand(numBytes, null, EV3CommandByte.EV3CommandOpCode.OP_INPUT_READ_SI, CpuCommandFlag.INCLUDE_TYPE_AND_MODE, CpuCommandFlag.NO_RETURN_VALUE), this.connection.getCommandCounter());
            return eV3ReplySendCommandAndGetReply.getData(3, eV3ReplySendCommandAndGetReply.getLength() - 3);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
            return bArr;
        }
    }

    private EV3Command buildCommand(int globalVars, EV3CommandByte.EV3CommandByteCode commandCode, EV3CommandByte.EV3CommandOpCode opCode, CpuCommandFlag addTypeAndMode, CpuCommandFlag addReturnValue) {
        EV3Command eV3Command = new EV3Command(this.connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_REPLY, globalVars, 0, opCode);
        this.connection.incCommandCounter();
        byte b = this.sensorType.getByte();
        byte b2 = this.sensorMode.getByte();
        if (commandCode != null) {
            eV3Command.append(commandCode);
        }
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, 0);
        eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, this.port);
        if (addTypeAndMode == CpuCommandFlag.INCLUDE_TYPE_AND_MODE) {
            eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_LONG, b);
            eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, b2);
        }
        if (addReturnValue == CpuCommandFlag.INCLUDE_RETURN_VALUE) {
            eV3Command.append(EV3CommandByte.EV3CommandParamFormat.PARAM_FORMAT_SHORT, 1);
        }
        eV3Command.append(EV3CommandByte.EV3CommandVariableScope.PARAM_VARIABLE_SCOPE_GLOBAL, 0);
        return eV3Command;
    }

    private EV3Reply sendCommandAndGetReply(EV3Command command, int commandCounter) throws MindstormsException {
        EV3Reply eV3Reply = new EV3Reply(this.connection.sendAndReceive(command));
        if (eV3Reply.isValid(commandCounter)) {
            return eV3Reply;
        }
        throw new MindstormsException("Reply not valid!");
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
