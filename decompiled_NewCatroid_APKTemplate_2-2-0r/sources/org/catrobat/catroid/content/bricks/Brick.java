package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface Brick extends Serializable, Cloneable {
    public static final int AUDIO = 30;
    public static final int BLUETOOTH_LEGO_EV3 = 20;
    public static final int BLUETOOTH_LEGO_NXT = 2;
    public static final int BLUETOOTH_PHIRO = 10;
    public static final int BLUETOOTH_SENSORS_ARDUINO = 6;
    public static final int CAMERA_BACK = 11;
    public static final int CAMERA_FLASH = 8;
    public static final int CAMERA_FRONT = 12;
    public static final int CAST_REQUIRED = 22;
    public static final int COLLISION = 19;
    public static final int DOCUMENT = 31;
    public static final int FACE_DETECTION = 4;
    public static final int IMAGE = 29;
    public static final int MICROPHONE = 24;
    public static final int NETWORK_CONNECTION = 21;
    public static final int NFC_ADAPTER = 16;
    public static final int OBJECT_DETECTION = 28;
    public static final int OTHER = 32;
    public static final int PHYSICS = 3;
    public static final int POSE_DETECTION = 27;
    public static final int SENSOR_ACCELERATION = 13;
    public static final int SENSOR_COMPASS = 15;
    public static final int SENSOR_GPS = 18;
    public static final int SENSOR_INCLINATION = 14;
    public static final int SOCKET_RASPI = 7;
    public static final int SPEECH_RECOGNITION = 23;
    public static final int STORAGE_READ = 25;
    public static final int STORAGE_WRITE = 26;
    public static final int TEXT_DETECTION = 5;
    public static final int TEXT_TO_SPEECH = 1;
    public static final int VIBRATION = 9;
    public static final int VIDEO = 17;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Brick$FormulaField.class
      classes14.dex
     */
    public interface FormulaField extends Serializable {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Brick$Resources.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Resources {
    }

    void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence);

    boolean addBrickInNestedBrick(UUID parentBrickId, int subStackIndex, List<Brick> bricksToAdd);

    void addRequiredResources(ResourcesSet requiredResourcesSet);

    void addToFlatList(List<Brick> bricks);

    /* JADX INFO: renamed from: clone */
    Brick mo4893clone() throws CloneNotSupportedException;

    boolean consistsOfMultipleParts();

    void disableSpinners();

    List<Brick> findBricksInNestedBricks(List<UUID> brickIds);

    List<Brick> getAllParts();

    UUID getBrickID();

    CheckBox getCheckBox();

    List<Brick> getDragAndDropTargetList();

    String getHelpUrl(String category);

    Brick getParent();

    int getPositionInDragAndDropTargetList();

    int getPositionInScript();

    View getPrototypeView(Context context);

    Script getScript();

    View getView(Context context);

    boolean hasHelpPage();

    boolean isCommentedOut();

    default boolean isPhantom() {
        return false;
    }

    boolean removeChild(Brick brick);

    void setCommentedOut(boolean commentedOut);

    void setParent(Brick parent);

    default void setPhantom(boolean isPhantom) {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Brick$BrickField.class
      classes14.dex
     */
    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r3v0 org.catrobat.catroid.content.bricks.Brick$BrickField, still in use, count: 1, list:
      (r3v0 org.catrobat.catroid.content.bricks.Brick$BrickField) from 0x103c: FILLED_NEW_ARRAY 
      (r3v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r4v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r5v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r6v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r7v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r8v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r9v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r10v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r11v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
      (r12v0 org.catrobat.catroid.content.bricks.Brick$BrickField)
     A[WRAPPED] (LINE:108) elemType: org.catrobat.catroid.content.bricks.Brick$BrickField
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
    public static final class BrickField implements FormulaField {
        COLOR,
        COLOR_CHANGE,
        BRIGHTNESS,
        BRIGHTNESS_CHANGE,
        X_POSITION,
        Y_POSITION,
        X_POSITION_CHANGE,
        Y_POSITION_CHANGE,
        TRANSPARENCY,
        TRANSPARENCY_CHANGE,
        SIZE,
        SIZE_CHANGE,
        VOLUME,
        VOLUME_CHANGE,
        X_DESTINATION,
        Y_DESTINATION,
        STEPS,
        DURATION_IN_SECONDS,
        DEGREES,
        TURN_RIGHT_DEGREES,
        TURN_LEFT_DEGREES,
        TIME_TO_WAIT_IN_SECONDS,
        VARIABLE,
        TOAST,
        HEADER,
        BODY,
        URL,
        TEXT,
        KEY,
        QUESTION,
        TIME,
        VARNAME,
        VALUE,
        CODE,
        HTML,
        POSX,
        POSY,
        WIDTH,
        HEIGHT,
        NAME,
        IP,
        PORT,
        FILE_URL,
        SCENE_ID,
        FILE,
        TABLE_NAME,
        SIZE_X,
        SIZE_Y,
        FLOAT_ARRAY,
        VERTEX,
        FRAGMENT,
        X1,
        X2,
        Y1,
        Y2,
        LOOPED,
        CONTROLS,
        TEXTSIZE,
        TEXTCOLOR,
        BGCOLOR,
        HINT_TEXT,
        HINT_COLOR,
        OK,
        CANEL,
        DEFAULT,
        FIREBASE_ID,
        FIREBASE_KEY,
        FIREBASE_VALUE,
        TEST_FORMULA,
        VALUE_2,
        VALUE_3,
        VALUE_4,
        VALUE_5,
        VALUE_1,
        VALUE_6,
        VALUE_7,
        VALUE_9,
        VALUE_10,
        VALUE_11,
        RED,
        GREEN,
        BLUE,
        ALPHA,
        REGEX,
        TITLE,
        MESSAGE,
        MODEL,
        ROTATION,
        BORDERS,
        HIDE_STATUS_BAR,
        USER_DEFINED,
        VARIABLE_CHANGE,
        WEB_REQUEST,
        LOOK_REQUEST,
        LOOK_NEW,
        LOOK_COPY,
        BACKGROUND_REQUEST,
        WRITE_FILENAME,
        READ_FILENAME,
        TEMPO,
        HORIZONTAL_FLEXIBILITY,
        VERTICAL_FLEXIBILITY,
        ALIGNMENT,
        PASSWORD,
        MAX_LEN,
        CORNER,
        TYPE,
        TEMPO_CHANGE,
        BEATS_TO_PAUSE,
        NOTE_TO_PLAY,
        BEATS_TO_PLAY_NOTE,
        OPEN_URL,
        PLAY_DRUM,
        PLAY_SOUND_AT,
        FORMULA_1,
        FORMULA_2,
        FORMULA_3,
        FORMULA_4,
        FORMULA_5,
        FORMULA_6,
        FORMULA_7,
        PEN_SIZE,
        PEN_COLOR_RED,
        PEN_COLOR_GREEN,
        PEN_COLOR_BLUE,
        CUSTOM_PARAM_1,
        CUSTOM_PARAM_2,
        CUSTOM_PARAM_3,
        CUSTOM_PARAM_4,
        CUSTOM_PARAM_5,
        CUSTOM_PARAM_6,
        CUSTOM_PARAM_7,
        CUSTOM_PARAM_8,
        CUSTOM_PARAM_9,
        CUSTOM_PARAM_10,
        IF_CONDITION,
        TIMES_TO_REPEAT,
        FOR_LOOP_FROM,
        FOR_LOOP_TO,
        VIBRATE_DURATION_IN_SECONDS,
        USER_BRICK,
        NOTE,
        SPEAK,
        SHOWTEXT,
        STRING,
        ROTATION_STYLE,
        REPEAT_UNTIL_CONDITION,
        ASK_QUESTION,
        NFC_NDEF_MESSAGE,
        ASK_SPEECH_QUESTION,
        LOOK_INDEX,
        BACKGROUND_INDEX,
        BACKGROUND_WAIT_INDEX,
        REPORT_BRICK,
        LEGO_NXT_SPEED,
        LEGO_NXT_DEGREES,
        LEGO_NXT_FREQUENCY,
        LEGO_NXT_DURATION_IN_SECONDS,
        LEGO_EV3_FREQUENCY,
        LEGO_EV3_DURATION_IN_SECONDS,
        LEGO_EV3_VOLUME,
        LEGO_EV3_SPEED,
        LEGO_EV3_POWER,
        LEGO_EV3_PERIOD_IN_SECONDS,
        LEGO_EV3_DEGREES,
        DRONE_TIME_TO_FLY_IN_SECONDS,
        LIST_ADD_ITEM,
        LIST_DELETE_ITEM,
        INSERT_ITEM_INTO_USERLIST_VALUE,
        INSERT_ITEM_INTO_USERLIST_INDEX,
        REPLACE_ITEM_IN_USERLIST_VALUE,
        REPLACE_ITEM_IN_USERLIST_INDEX,
        DRONE_POWER_IN_PERCENT,
        DRONE_ALTITUDE_LIMIT,
        DRONE_VERTICAL_SPEED_MAX,
        DRONE_ROTATION_MAX,
        DRONE_TILT_ANGLE,
        JUMPING_SUMO_SPEED,
        JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS,
        JUMPING_SUMO_VOLUME,
        JUMPING_SUMO_ROTATE,
        PHIRO_SPEED,
        PHIRO_DURATION_IN_SECONDS,
        PHIRO_LIGHT_RED,
        PHIRO_LIGHT_GREEN,
        PHIRO_LIGHT_BLUE,
        PHYSICS_BOUNCE_FACTOR,
        PHYSICS_FRICTION,
        PHYSICS_GRAVITY_X,
        PHYSICS_GRAVITY_Y,
        PHYSICS_MASS,
        PHYSICS_VELOCITY_X,
        PHYSICS_VELOCITY_Y,
        PHYSICS_TURN_LEFT_SPEED,
        PHYSICS_TURN_RIGHT_SPEED,
        ARDUINO_ANALOG_PIN_VALUE,
        ARDUINO_ANALOG_PIN_NUMBER,
        ARDUINO_DIGITAL_PIN_VALUE,
        ARDUINO_DIGITAL_PIN_NUMBER,
        RASPI_DIGITAL_PIN_VALUE,
        RASPI_DIGITAL_PIN_NUMBER,
        RASPI_PWM_PERCENTAGE,
        RASPI_PWM_FREQUENCY,
        EMBROIDERY_LENGTH,
        ZIGZAG_EMBROIDERY_LENGTH,
        ZIGZAG_EMBROIDERY_WIDTH,
        THREAD_COLOR,
        STORE_CSV_INTO_USERLIST_COLUMN,
        STORE_CSV_INTO_USERLIST_CSV,
        ASSERT_EQUALS_EXPECTED,
        ASSERT_EQUALS_ACTUAL,
        ASSERT_LOOP_ACTUAL,
        THREED_OBJECT_ID,
        THREED_ENABLED,
        THREED_VERTEX_SHADER_CODE,
        THREED_FRAGMENT_SHADER_CODE,
        THREED_UNIFORM_NAME,
        VALUE_X,
        VALUE_Y,
        VALUE_Z,
        SCRIPT,
        FILE_NAME,
        VARIABLE_NAME,
        GL_VIEW_NAME,
        VM_DISK_SIZE,
        VM_DISK_NAME,
        VM_HDA,
        VM_CDROM,
        VM_CPU,
        VM_MEMORY,
        VM_KEY_DOWN,
        VALUE_8,
        VALUE_12,
        VALUE_13,
        VALUE_14,
        FILENAME,
        INTERVAL,
        CACHE_NAME,
        VIEW_ID,
        PROJECT_NAME,
        JOINT_ID,
        SPRITE,
        JOINT_DAMPING,
        JOINT_FREQUENCY,
        JOINT_LENGTH,
        AXIS_X,
        AXIS_Y,
        FORCE_Y,
        FORCE_X,
        IMPULSE_X,
        IMPULSE_Y,
        TORQUE,
        ANGULAR_IMPULSE,
        LINEAR_DAMPING,
        ANGULAR_DAMPING,
        RAY_ID,
        X_START,
        Y_START,
        X_END,
        Y_END,
        SPRITE_A,
        GROUND_ANCHOR_A_X,
        SPRITE_B,
        GROUND_ANCHOR_A_Y,
        GROUND_ANCHOR_B_X,
        GROUND_ANCHOR_B_Y,
        RATIO,
        JOINT_A_ID,
        JOINT_B_ID,
        OBJECT_ID,
        DISTANCE,
        PITCH,
        PROJECT_FILE_NAME,
        CLONE_NAME,
        VM_NAME,
        INPUT_TEXT,
        VM_ARGUMENTS,
        CHILD_OBJECT,
        PARENT_OBJECT,
        JOINT_NAME,
        OBJECT_B,
        OBJECT_A,
        PIVOT_A_X,
        PIVOT_A_Y,
        PIVOT_A_Z,
        PIVOT_B_X,
        PIVOT_B_Y,
        PIVOT_B_Z,
        OBJECT_NAME,
        MATERIAL_R,
        MATERIAL_G,
        MATERIAL_B,
        MATERIAL_METALLIC,
        MATERIAL_ROUGHNESS,
        MATERIAL_TEXTURE_COLOR,
        MATERIAL_TEXTURE_NORMAL,
        MATERIAL_TEXTURE_MR,
        MATERIAL_A,
        SOUND_NAME,
        INSTANCE_NAME,
        POS_X,
        POS_Y,
        POS_Z,
        MASK,
        PARTICLE_ID,
        PARTICLE_LOOPING,
        PARTICLE_DURATION,
        PARTICLE_LIFETIME,
        PARTICLE_SPEED,
        PARTICLE_SIZE,
        PARTICLE_GRAVITY,
        PARTICLE_MAX_COUNT,
        PARTICLE_RATE,
        PARTICLE_ANGLE,
        PARTICLE_RADIUS,
        PARTICLE_START_COLOR,
        PARTICLE_END_COLOR,
        PARTICLE_END_SIZE,
        PARTICLE_TEXTURE,
        PARTICLE_ADDITIVE,
        PARTICLE_ROTATION,
        PARTICLE_ROTATION_SPEED,
        PARTICLE_POS_X,
        PARTICLE_POS_Y,
        PARTICLE_POS_Z,
        PARTICLE_ROT_PITCH,
        PARTICLE_ROT_YAW,
        PARTICLE_ROT_ROLL,
        TEXT_1,
        TEXT_2,
        TEXT_3,
        TEXT_4,
        TEXT_5,
        TEXT_6,
        TEXT_7,
        TEXT_8,
        TEXT_9,
        TEXT_10,
        X,
        Y,
        Z,
        MODE,
        TARGET,
        SPEED,
        FOV,
        INTENSITY,
        DURATION,
        RANGE,
        STEP_HEIGHT,
        AVOID_OBSTACLES,
        ENABLED,
        SENSITIVITY,
        YAW,
        ROLL,
        EMISSION_RATE,
        SHADOW_RESOLUTION,
        SHADOW_SIZE,
        CONSTRAINT_ID,
        MOTOR_TARGET,
        MOTOR_MAX_FORCE,
        AXIS_A_X,
        AXIS_B_X,
        AXIS_A_Y,
        AXIS_B_Y,
        AXIS_B_Z,
        AXIS_A_Z,
        Y_SCALE,
        X_SCALE,
        VIBRATE_DURATION,
        ZOOM,
        STRING_VALUE,
        Z_POSITION,
        DEGREES_BY,
        DATA,
        X_1,
        Y_1,
        Z_1,
        MAX_LIGHTS;

        public static final BrickField[] EXPECTS_STRING_VALUE = {new BrickField(), new BrickField(), new BrickField(), new BrickField(), new BrickField(), new BrickField(), new BrickField(), new BrickField(), new BrickField(), new BrickField()};

        private BrickField() {
            super($enum$name, $enum$ordinal);
        }

        public static BrickField valueOf(String name2) {
            return (BrickField) Enum.valueOf(BrickField.class, name2);
        }

        public static BrickField[] values() {
            return (BrickField[]) $VALUES.clone();
        }

        static {
        }

        public static boolean isExpectingStringValue(BrickField field) {
            for (BrickField brickField : EXPECTS_STRING_VALUE) {
                if (brickField.equals(field)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Brick$BrickData.class
      classes14.dex
     */
    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 org.catrobat.catroid.content.bricks.Brick$BrickData, still in use, count: 1, list:
      (r0v0 org.catrobat.catroid.content.bricks.Brick$BrickData) from 0x002e: FILLED_NEW_ARRAY 
      (r0v0 org.catrobat.catroid.content.bricks.Brick$BrickData)
      (r1v1 org.catrobat.catroid.content.bricks.Brick$BrickData)
      (r2v2 org.catrobat.catroid.content.bricks.Brick$BrickData)
     A[WRAPPED] (LINE:126) elemType: org.catrobat.catroid.content.bricks.Brick$BrickData
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
    public static final class BrickData {
        ASSERT_LISTS_EXPECTED,
        ASSERT_LISTS_ACTUAL,
        FOR_ITEM_IN_USERLIST_LIST,
        FOR_ITEM_IN_USERLIST_VARIABLE;

        public static final BrickData[] EXPECTS_USERLIST = {new BrickData(), new BrickData(), new BrickData()};

        private BrickData() {
            super($enum$name, $enum$ordinal);
        }

        public static BrickData valueOf(String name2) {
            return (BrickData) Enum.valueOf(BrickData.class, name2);
        }

        public static BrickData[] values() {
            return (BrickData[]) $VALUES.clone();
        }

        static {
        }

        public static boolean isUserList(BrickData field) {
            for (BrickData brickData : EXPECTS_USERLIST) {
                if (brickData.equals(field)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Brick$ResourcesSet.class
      classes14.dex
     */
    public static class ResourcesSet extends HashSet<Integer> {
        @Override // java.util.HashSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean add(Integer integer) {
            return super.add(integer);
        }
    }
}
