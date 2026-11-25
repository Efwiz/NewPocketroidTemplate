package org.catrobat.catroid.devices.mindstorms;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3ColorSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3InfraredSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3LightSensorNXT;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3SensorMode;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3SoundSensorNXT;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3TouchSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3UltrasonicSensorNXT;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.HiTechnicColorSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.TemperatureSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTI2CUltraSonicSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTLightSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTLightSensorActive;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSoundSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTTouchSensor;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/LegoSensorFactory.class
  classes14.dex
 */
/* JADX INFO: compiled from: LegoSensorFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/devices/mindstorms/LegoSensorFactory;", "", "connection", "Lorg/catrobat/catroid/devices/mindstorms/MindstormsConnection;", "(Lorg/catrobat/catroid/devices/mindstorms/MindstormsConnection;)V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/catrobat/catroid/devices/mindstorms/LegoSensor;", "sensorType", "", "port", "", "createEv3Sensor", "Lorg/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3Sensor$Sensor;", "createNxtSensor", "Lorg/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTSensor$Sensor;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LegoSensorFactory {
    private final MindstormsConnection connection;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/LegoSensorFactory$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LegoSensorFactory.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[EV3Sensor.Sensor.values().length];
            try {
                iArr[EV3Sensor.Sensor.INFRARED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EV3Sensor.Sensor.TOUCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EV3Sensor.Sensor.COLOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[EV3Sensor.Sensor.COLOR_AMBIENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[EV3Sensor.Sensor.COLOR_REFLECT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[EV3Sensor.Sensor.HT_NXT_COLOR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[EV3Sensor.Sensor.NXT_TEMPERATURE_C.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[EV3Sensor.Sensor.NXT_TEMPERATURE_F.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[EV3Sensor.Sensor.NXT_LIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[EV3Sensor.Sensor.NXT_LIGHT_ACTIVE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[EV3Sensor.Sensor.NXT_SOUND.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[EV3Sensor.Sensor.NXT_ULTRASONIC.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[NXTSensor.Sensor.values().length];
            try {
                iArr2[NXTSensor.Sensor.TOUCH.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[NXTSensor.Sensor.SOUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[NXTSensor.Sensor.LIGHT_INACTIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[NXTSensor.Sensor.LIGHT_ACTIVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[NXTSensor.Sensor.ULTRASONIC.ordinal()] = 5;
            } catch (NoSuchFieldError unused17) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public LegoSensorFactory(MindstormsConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        this.connection = connection;
    }

    public final LegoSensor create(Enum<?> sensorType, int port) {
        Intrinsics.checkNotNullParameter(sensorType, "sensorType");
        if (sensorType instanceof EV3Sensor.Sensor) {
            return createEv3Sensor((EV3Sensor.Sensor) sensorType, port);
        }
        if (sensorType instanceof NXTSensor.Sensor) {
            return createNxtSensor((NXTSensor.Sensor) sensorType, port);
        }
        throw new IllegalArgumentException("Trying to create LegoSensor with invalid sensorType: " + sensorType);
    }

    private final LegoSensor createEv3Sensor(EV3Sensor.Sensor sensorType, int port) {
        switch (WhenMappings.$EnumSwitchMapping$0[sensorType.ordinal()]) {
            case 1:
                return new EV3InfraredSensor(port, this.connection);
            case 2:
                return new EV3TouchSensor(port, this.connection);
            case 3:
                return new EV3ColorSensor(port, this.connection, EV3SensorMode.MODE2);
            case 4:
                return new EV3ColorSensor(port, this.connection, EV3SensorMode.MODE0);
            case 5:
                return new EV3ColorSensor(port, this.connection, EV3SensorMode.MODE1);
            case 6:
                return new HiTechnicColorSensor(port, this.connection, EV3SensorMode.MODE1);
            case 7:
                return new TemperatureSensor(port, this.connection, EV3SensorMode.MODE0);
            case 8:
                return new TemperatureSensor(port, this.connection, EV3SensorMode.MODE1);
            case 9:
                return new EV3LightSensorNXT(port, this.connection, EV3SensorMode.MODE1);
            case 10:
                return new EV3LightSensorNXT(port, this.connection, EV3SensorMode.MODE0);
            case 11:
                return new EV3SoundSensorNXT(port, this.connection, EV3SensorMode.MODE1);
            case 12:
                return new EV3UltrasonicSensorNXT(port, this.connection, EV3SensorMode.MODE0);
            default:
                throw new IllegalArgumentException("Trying to create Ev3Sensor with invalid sensorType: " + sensorType);
        }
    }

    private final LegoSensor createNxtSensor(NXTSensor.Sensor sensorType, int port) {
        int i = WhenMappings.$EnumSwitchMapping$1[sensorType.ordinal()];
        if (i == 1) {
            return new NXTTouchSensor(port, this.connection);
        }
        if (i == 2) {
            return new NXTSoundSensor(port, this.connection);
        }
        if (i == 3) {
            return new NXTLightSensor(port, this.connection);
        }
        if (i == 4) {
            return new NXTLightSensorActive(port, this.connection);
        }
        if (i == 5) {
            return new NXTI2CUltraSonicSensor(this.connection);
        }
        throw new IllegalArgumentException("Trying to create NxtSensor with invalid sensorType: " + sensorType);
    }
}
