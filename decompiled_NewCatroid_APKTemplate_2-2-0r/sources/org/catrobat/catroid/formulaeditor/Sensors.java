package org.catrobat.catroid.formulaeditor;

import java.util.EnumSet;
import org.catrobat.catroid.utils.EnumUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/Sensors.class
  classes14.dex
 */
/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v20 org.catrobat.catroid.formulaeditor.Sensors, still in use, count: 1, list:
  (r0v20 org.catrobat.catroid.formulaeditor.Sensors) from 0x0852: INVOKE 
  (r0v20 org.catrobat.catroid.formulaeditor.Sensors)
  (wrap org.catrobat.catroid.formulaeditor.Sensors[]:0x084e: FILLED_NEW_ARRAY 
  (r1v24 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v128 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v129 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v130 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v132 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v160 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v159 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v158 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v157 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v156 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v155 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v144 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v137 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v136 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v134 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v133 org.catrobat.catroid.formulaeditor.Sensors)
  (r1v181 org.catrobat.catroid.formulaeditor.Sensors)
 A[WRAPPED] (LINE:73) elemType: org.catrobat.catroid.formulaeditor.Sensors)
 STATIC call: java.util.EnumSet.of(java.lang.Enum, java.lang.Enum[]):java.util.EnumSet A[MD:<E extends java.lang.Enum<E>>:(E extends java.lang.Enum<E>, E extends java.lang.Enum<E>[]):java.util.EnumSet<E extends java.lang.Enum<E>> VARARG (c), VARARG_CALL, WRAPPED] (LINE:73)
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: loaded from: classes14.dex */
public final class Sensors {
    X_ACCELERATION,
    Y_ACCELERATION,
    Z_ACCELERATION,
    COMPASS_DIRECTION,
    X_INCLINATION,
    Y_INCLINATION,
    LOUDNESS,
    LATITUDE,
    LONGITUDE,
    LOCATION_ACCURACY,
    ALTITUDE,
    USER_LANGUAGE,
    TIMER,
    DATE_YEAR,
    DATE_MONTH,
    DATE_DAY,
    DATE_WEEKDAY,
    TIME_HOUR,
    TIME_MINUTE,
    TIME_SECOND,
    FACE_DETECTED,
    FACE_SIZE,
    FACE_X,
    FACE_Y,
    SECOND_FACE_DETECTED,
    SECOND_FACE_SIZE,
    SECOND_FACE_X,
    SECOND_FACE_Y,
    HEAD_TOP_X,
    HEAD_TOP_Y,
    NECK_X,
    NECK_Y,
    NOSE_X,
    NOSE_Y,
    LEFT_EYE_INNER_X,
    LEFT_EYE_INNER_Y,
    LEFT_EYE_CENTER_X,
    LEFT_EYE_CENTER_Y,
    LEFT_EYE_OUTER_X,
    LEFT_EYE_OUTER_Y,
    RIGHT_EYE_INNER_X,
    RIGHT_EYE_INNER_Y,
    RIGHT_EYE_CENTER_X,
    RIGHT_EYE_CENTER_Y,
    RIGHT_EYE_OUTER_X,
    RIGHT_EYE_OUTER_Y,
    LEFT_EAR_X,
    LEFT_EAR_Y,
    RIGHT_EAR_X,
    RIGHT_EAR_Y,
    MOUTH_LEFT_CORNER_X,
    MOUTH_LEFT_CORNER_Y,
    MOUTH_RIGHT_CORNER_X,
    MOUTH_RIGHT_CORNER_Y,
    LEFT_SHOULDER_X,
    LEFT_SHOULDER_Y,
    RIGHT_SHOULDER_X,
    RIGHT_SHOULDER_Y,
    LEFT_ELBOW_X,
    LEFT_ELBOW_Y,
    RIGHT_ELBOW_X,
    RIGHT_ELBOW_Y,
    LEFT_WRIST_X,
    LEFT_WRIST_Y,
    RIGHT_WRIST_X,
    RIGHT_WRIST_Y,
    LEFT_PINKY_X,
    LEFT_PINKY_Y,
    RIGHT_PINKY_X,
    RIGHT_PINKY_Y,
    LEFT_INDEX_X,
    LEFT_INDEX_Y,
    RIGHT_INDEX_X,
    RIGHT_INDEX_Y,
    LEFT_THUMB_X,
    LEFT_THUMB_Y,
    RIGHT_THUMB_X,
    RIGHT_THUMB_Y,
    LEFT_HIP_X,
    LEFT_HIP_Y,
    RIGHT_HIP_X,
    RIGHT_HIP_Y,
    LEFT_KNEE_X,
    LEFT_KNEE_Y,
    RIGHT_KNEE_X,
    RIGHT_KNEE_Y,
    LEFT_ANKLE_X,
    LEFT_ANKLE_Y,
    RIGHT_ANKLE_X,
    RIGHT_ANKLE_Y,
    LEFT_HEEL_X,
    LEFT_HEEL_Y,
    RIGHT_HEEL_X,
    RIGHT_HEEL_Y,
    LEFT_FOOT_INDEX_X,
    LEFT_FOOT_INDEX_Y,
    RIGHT_FOOT_INDEX_X,
    RIGHT_FOOT_INDEX_Y,
    OBJECT_X(true),
    OBJECT_Y(true),
    OBJECT_TRANSPARENCY(true),
    OBJECT_BRIGHTNESS(true),
    OBJECT_COLOR(true),
    OBJECT_SIZE(true),
    OBJECT_WIDTH(true),
    OBJECT_HEIGHT(true),
    WIDTH(true),
    HEIGHT(true),
    MOTION_DIRECTION(true),
    LOOK_DIRECTION(true),
    OBJECT_LAYER(true),
    OBJECT_DISTANCE_TO(true),
    NXT_SENSOR_1,
    NXT_SENSOR_2,
    NXT_SENSOR_3,
    NXT_SENSOR_4,
    EV3_SENSOR_1,
    EV3_SENSOR_2,
    EV3_SENSOR_3,
    EV3_SENSOR_4,
    PHIRO_FRONT_LEFT,
    PHIRO_FRONT_RIGHT,
    PHIRO_SIDE_LEFT,
    PHIRO_SIDE_RIGHT,
    PHIRO_BOTTOM_LEFT,
    PHIRO_BOTTOM_RIGHT,
    DRONE_BATTERY_STATUS,
    DRONE_EMERGENCY_STATE,
    DRONE_FLYING,
    DRONE_INITIALIZED,
    DRONE_USB_ACTIVE,
    DRONE_USB_REMAINING_TIME,
    DRONE_CAMERA_READY,
    DRONE_RECORD_READY,
    DRONE_RECORDING,
    DRONE_NUM_FRAMES,
    COLLIDES_WITH_EDGE(true),
    COLLIDES_WITH_FINGER(true),
    OBJECT_X_VELOCITY(true),
    OBJECT_Y_VELOCITY(true),
    OBJECT_ANGULAR_VELOCITY(true),
    LAST_FINGER_INDEX,
    FINGER_X,
    FINGER_Y,
    FINGER_TOUCHED,
    NUMBER_CURRENT_TOUCHES,
    OBJECT_LOOK_NUMBER(true),
    OBJECT_LOOK_WIDTH(true),
    OBJECT_LOOK_HEIGHT(true),
    OBJECT_LOOK_NAME(true),
    OBJECT_NUMBER_OF_LOOKS(true),
    OBJECT_BACKGROUND_NUMBER(true),
    OBJECT_BACKGROUND_NAME(true),
    NFC_TAG_ID(true),
    NFC_TAG_MESSAGE(true),
    GAMEPAD_A_PRESSED,
    GAMEPAD_B_PRESSED,
    GAMEPAD_UP_PRESSED,
    GAMEPAD_DOWN_PRESSED,
    GAMEPAD_LEFT_PRESSED,
    GAMEPAD_RIGHT_PRESSED,
    TEXT_FROM_CAMERA,
    TEXT_BLOCKS_NUMBER,
    TEXT_BLOCK_X,
    TEXT_BLOCK_Y,
    TEXT_BLOCK_SIZE,
    TEXT_BLOCK_FROM_CAMERA,
    TEXT_BLOCK_LANGUAGE_FROM_CAMERA,
    SPEECH_RECOGNITION_LANGUAGE,
    STAGE_WIDTH,
    STAGE_HEIGHT,
    MICRO,
    IP,
    PORT,
    FREQ,
    INTERNET,
    BATTARY,
    ARCH,
    FPS,
    NFC_TECH_LIST,
    NFC_MEMORY_SIZE,
    NFC_IS_WRITABLE,
    NFC_TAG_TYPE,
    MOUSE_X,
    MOUSE_Y,
    MOUSE_DELTA_X,
    MOUSE_DELTA_Y,
    MOUSE_SCROLL,
    CLIPBOARD_TEXT,
    KEYBOARD_HEIGHT,
    OBJECT_NAME(true);

    public static final EnumSet<Sensors> BOOLEAN;
    public final boolean isObjectSensor;
    public static final String TAG = "Sensors";

    public static Sensors valueOf(String name2) {
        return (Sensors) Enum.valueOf(Sensors.class, name2);
    }

    public static Sensors[] values() {
        return (Sensors[]) $VALUES.clone();
    }

    static {
        BOOLEAN = EnumSet.of(new Sensors(), new Sensors(), new Sensors(), new Sensors(), new Sensors(), new Sensors(), sensors, sensors, sensors, sensors, sensors, sensors, sensors, sensors, sensors, sensors, new Sensors(), new Sensors());
    }

    private Sensors(boolean isObjectSensor) {
        super($enum$name, $enum$ordinal);
        this.isObjectSensor = isObjectSensor;
    }

    private Sensors() {
        super($enum$name, $enum$ordinal);
        this.isObjectSensor = false;
    }

    public static boolean isSensor(String value) {
        return EnumUtils.isValidEnum(Sensors.class, value);
    }

    public static boolean isBoolean(Sensors sensor) {
        return BOOLEAN.contains(sensor);
    }

    public static Sensors getSensorByValue(String value) {
        return (Sensors) EnumUtils.getEnum(Sensors.class, value);
    }
}
