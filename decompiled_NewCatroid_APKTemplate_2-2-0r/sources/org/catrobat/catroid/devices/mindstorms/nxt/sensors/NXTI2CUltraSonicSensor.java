package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import java.util.Locale;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTI2CUltraSonicSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NXTI2CUltraSonicSensor extends NXTI2CSensor {
    private static final int DEFAULT_VALUE = 255;
    private static final String TAG = "NXTI2CUltraSonicSensor";
    public static final byte ULTRASONIC_ADDRESS = 2;
    private DistanceUnit distanceUnit;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTI2CUltraSonicSensor$DistanceUnit.class
      classes14.dex
     */
    private enum DistanceUnit {
        DUMMY,
        CENTIMETER,
        INCH
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTI2CUltraSonicSensor$SensorRegister.class
      classes14.dex
     */
    private enum SensorRegister {
        Version(0),
        ProductId(8),
        SensorType(16),
        FactoryZeroValue(17),
        FactoryScaleFactor(18),
        FactoryScaleDivisor(19),
        MeasurementUnits(20),
        Interval(64),
        Command(65),
        Result1(66),
        Result2(67),
        Result3(68),
        Result4(69),
        Result5(70),
        Result6(71),
        Result7(72),
        Result8(73),
        ZeroValue(80),
        ScaleFactor(81),
        ScaleDivisor(82);

        private int register;

        SensorRegister(int register) {
            this.register = register;
        }

        public byte getByte() {
            return (byte) this.register;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTI2CUltraSonicSensor$UltrasonicCommand.class
      classes14.dex
     */
    private enum UltrasonicCommand {
        Off(0),
        SingleShot(1),
        Continuous(2),
        EventCapture(3),
        RequestWarmReset(4);

        private int command;

        UltrasonicCommand(int command) {
            this.command = command;
        }

        public byte getByte() {
            return (byte) this.command;
        }
    }

    public NXTI2CUltraSonicSensor(MindstormsConnection connection) {
        super((byte) 2, NXTSensorType.LOW_SPEED_9V, connection);
        this.distanceUnit = DistanceUnit.CENTIMETER;
        this.lastValidValue = 255.0f;
    }

    public NXTI2CUltraSonicSensor(DistanceUnit distanceUnit, MindstormsConnection connection) {
        super((byte) 2, NXTSensorType.LOW_SPEED_9V, connection);
        this.distanceUnit = distanceUnit;
        this.lastValidValue = 255.0f;
    }

    public void singleShot(boolean reply) throws MindstormsException {
        setMode(UltrasonicCommand.SingleShot, reply);
    }

    public void turnOffSonar() throws MindstormsException {
        setMode(UltrasonicCommand.Off, false);
    }

    public void continuous() throws MindstormsException {
        setMode(UltrasonicCommand.Continuous, false);
    }

    public boolean isSensorOff() throws MindstormsException {
        return getMode() == UltrasonicCommand.Off;
    }

    public void reset() throws MindstormsException {
        setMode(UltrasonicCommand.RequestWarmReset, false);
    }

    public byte getContinuousInterval() throws MindstormsException {
        return readRegister(SensorRegister.Interval.getByte(), 1)[0];
    }

    public void setContinuousInterval(byte interval) throws MindstormsException {
        writeRegister(SensorRegister.Interval.getByte(), interval, false);
        super.wait(60);
    }

    private UltrasonicCommand getMode() throws MindstormsException {
        UltrasonicCommand ultrasonicCommand = UltrasonicCommand.Continuous;
        return UltrasonicCommand.values()[readRegister(SensorRegister.Command.getByte(), 1)[0]];
    }

    private void setMode(UltrasonicCommand command, boolean reply) throws MindstormsException {
        writeRegister(SensorRegister.Command.getByte(), command.getByte(), reply);
        super.wait(60);
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() throws MindstormsException {
        return getValueInDefinedUnitSystem(readRegister(SensorRegister.Result1.getByte(), 1)[0] & 255);
    }

    private int getValueInDefinedUnitSystem(int value) {
        return this.distanceUnit == DistanceUnit.INCH ? (value * 39370) / 1000 : value;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor, org.catrobat.catroid.devices.mindstorms.LegoSensor
    public String getName() {
        return String.format(Locale.getDefault(), "%s_%s_%d", TAG, "ULTRASONIC", Integer.valueOf(this.port));
    }
}
