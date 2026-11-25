package org.catrobat.catroid.formulaeditor;

import java.util.EnumSet;
import org.catrobat.catroid.utils.EnumUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/Functions.class
  classes14.dex
 */
/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v22 org.catrobat.catroid.formulaeditor.Functions, still in use, count: 1, list:
  (r0v22 org.catrobat.catroid.formulaeditor.Functions) from 0x0905: INVOKE 
  (r0v22 org.catrobat.catroid.formulaeditor.Functions)
  (wrap org.catrobat.catroid.formulaeditor.Functions[]:0x08ff: FILLED_NEW_ARRAY 
  (r3v0 org.catrobat.catroid.formulaeditor.Functions)
  (r2v52 org.catrobat.catroid.formulaeditor.Functions)
  (r3v17 org.catrobat.catroid.formulaeditor.Functions)
  (r3v19 org.catrobat.catroid.formulaeditor.Functions)
  (r4v50 org.catrobat.catroid.formulaeditor.Functions)
  (r3v22 org.catrobat.catroid.formulaeditor.Functions)
  (r1v90 org.catrobat.catroid.formulaeditor.Functions)
  (r4v91 org.catrobat.catroid.formulaeditor.Functions)
  (r1v82 org.catrobat.catroid.formulaeditor.Functions)
  (r3v11 org.catrobat.catroid.formulaeditor.Functions)
 A[WRAPPED] (LINE:98) elemType: org.catrobat.catroid.formulaeditor.Functions)
 STATIC call: java.util.EnumSet.of(java.lang.Enum, java.lang.Enum[]):java.util.EnumSet A[MD:<E extends java.lang.Enum<E>>:(E extends java.lang.Enum<E>, E extends java.lang.Enum<E>[]):java.util.EnumSet<E extends java.lang.Enum<E>> VARARG (c), VARARG_CALL, WRAPPED] (LINE:98)
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
public final class Functions {
    SIN,
    COS,
    TAN,
    LN,
    LOG,
    SQRT,
    RAND,
    ROUND,
    ROUNDTO,
    ABS,
    PI,
    MOD,
    ARCSIN,
    ARCCOS,
    ARCTAN,
    ARCTAN2,
    EXP,
    POWER,
    FLOOR,
    CEIL,
    MAX,
    MIN,
    TRUE,
    FALSE,
    LENGTH,
    LETTER,
    SUBTEXT,
    FILE,
    TO_HEX,
    TO_DEC,
    CLAMP,
    DISTAN,
    UPPER,
    LOWER,
    REVERSE,
    VAR,
    VARNAME,
    VARVALUE,
    RANDOM_STR,
    REPLACE,
    CONTAINS_STR,
    REPEAT,
    TABLE_X,
    TABLE_Y,
    TABLE_ELEMENT,
    TABLE_JOIN,
    JOIN,
    JOIN3,
    DISTANCE,
    JOINNUMBER,
    REGEX,
    LIST_ITEM,
    CONTAINS,
    INDEX_OF_ITEM,
    NUMBER_OF_ITEMS,
    LUA,
    FLOATARRAY,
    VIEW_X,
    VIEW_Y,
    VIEW_WIDTH,
    VIEW_HEIGHT,
    VIDEO_PLAYING,
    VIDEO_TIME,
    JSON_GET,
    JSON_SET,
    JSON_IS_VALID,
    ARDUINOANALOG,
    ARDUINODIGITAL,
    RASPIDIGITAL,
    MULTI_FINGER_X,
    MULTI_FINGER_Y,
    MULTI_FINGER_TOUCHED,
    INDEX_CURRENT_TOUCH,
    COLLIDES_WITH_COLOR,
    COLOR_TOUCHES_COLOR,
    COLOR_AT_XY,
    COLOR_EQUALS_COLOR,
    TEXT_BLOCK_X,
    TEXT_BLOCK_Y,
    TEXT_BLOCK_SIZE,
    TEXT_BLOCK_FROM_CAMERA,
    TEXT_BLOCK_LANGUAGE_FROM_CAMERA,
    IF_THEN_ELSE,
    FLATTEN,
    CONNECT,
    FIND,
    TOUCHES_OBJECT_BY_NAME,
    GET_3D_POSITION_X,
    GET_3D_POSITION_Y,
    GET_3D_POSITION_Z,
    GET_3D_ROTATION_YAW,
    GET_3D_ROTATION_PITCH,
    GET_3D_ROTATION_ROLL,
    GET_3D_SCALE_X,
    GET_3D_SCALE_Y,
    GET_3D_SCALE_Z,
    GET_3D_DISTANCE,
    GET_DIRECTION_X,
    GET_DIRECTION_Y,
    GET_ANGLE,
    GET_RAY_DISTANCE,
    GET_RAY_HIT_OBJECT,
    GET_CAMERA_POS_X,
    GET_CAMERA_POS_Y,
    GET_CAMERA_POS_Z,
    GET_CAMERA_DIR_X,
    GET_CAMERA_DIR_Y,
    GET_CAMERA_DIR_Z,
    GET_CAMERA_ROTATION_YAW,
    GET_CAMERA_ROTATION_PITCH,
    GET_CAMERA_ROTATION_ROLL,
    GET_3D_VELOCITY_X,
    GET_3D_VELOCITY_Y,
    GET_3D_VELOCITY_Z,
    OBJECT_TOUCHES_OBJECT,
    OBJECT_INTERSECTS_OBJECT,
    GET_RAY_HIT_X,
    GET_RAY_HIT_Y,
    GET_RAY_HIT_Z,
    GET_RAY_HIT_NORMAL_X,
    GET_RAY_HIT_NORMAL_Y,
    GET_RAY_HIT_NORMAL_Z,
    RAY_DID_HIT,
    ID_OF_DETECTED_OBJECT,
    OBJECT_WITH_ID_VISIBLE,
    IS_MOUSE_BUTTON_DOWN,
    RAY_DID_HIT2,
    RAY_HIT_SPRITE_NAME,
    RAY_HIT_X,
    RAY_HIT_Y,
    RAY_HIT_DISTANCE,
    FILES_PATH,
    ALL_FILES,
    FILE_SIZE,
    DELTA,
    COLLISION_LIST,
    INTERSECT_LIST,
    PT_ARGMAX,
    PT_VALUE,
    PT_VALUEND,
    PT_SHAPE,
    PT_DUMP,
    PT_TOTALSIZE,
    F2D_X,
    F2D_Y,
    F2D_ROTATION,
    F2D_SCALE_X,
    F2D_SCALE_Y,
    F2D_COLOR_R,
    F2D_COLOR_G,
    F2D_COLOR_B,
    F2D_ALPHA,
    F2D_TEXTURE,
    F2D_CAM_X,
    F2D_CAM_Y,
    F2D_CAM_ZOOM,
    F2D_IS_TOUCHED,
    F2D_IS_TOUCHED_INDEX,
    VOXEL_GET_ID,
    VOXEL_GET_DATA,
    HTTP_RESPONSE_TEXT,
    HTTP_RESPONSE_CODE,
    HTTP_RESPONSE_HEADER,
    NOTIFICATION_REPLY,
    READ_FILE,
    MEDIA_DURATION,
    IMAGE_WIDTH,
    IMAGE_HEIGHT,
    FILE_LAST_MODIFIED,
    CAMERA_X,
    CAMERA_Y,
    CAMERA_ROTATION,
    CAMERA_ZOOM,
    CLIPBOARD_TEXT,
    MQTT_MESSAGE,
    FILE_TO_BASE64,
    MD5,
    SHA256,
    FILE_MD5,
    FILE_SHA256;

    public static final EnumSet<Functions> BOOLEAN;
    public static final EnumSet<Functions> LIST;
    private static final String TAG;
    public static EnumSet<Functions> TEXT;

    public static Functions valueOf(String name2) {
        return (Functions) Enum.valueOf(Functions.class, name2);
    }

    public static Functions[] values() {
        return (Functions[]) $VALUES.clone();
    }

    private Functions() {
        super($enum$name, $enum$ordinal);
    }

    static {
        Functions functions = CONTAINS;
        Functions functions2 = OBJECT_TOUCHES_OBJECT;
        Functions functions3 = OBJECT_INTERSECTS_OBJECT;
        TAG = "Functions";
        TEXT = EnumSet.of(functions, functions, functions, new Functions(), new Functions(), new Functions(), functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, new Functions(), functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions, functions2, new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), functions3, new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions(), new Functions());
        LIST = EnumSet.of(new Functions(), functions, new Functions(), new Functions(), new Functions(), new Functions(), new Functions());
        BOOLEAN = EnumSet.of(new Functions(), new Functions(), functions, functions, functions, functions, functions, functions, functions3, functions2, new Functions());
    }

    public static boolean isFunction(String value) {
        return EnumUtils.isValidEnum(Functions.class, value);
    }

    public static boolean isBoolean(Functions function) {
        return BOOLEAN.contains(function);
    }

    public static Functions getFunctionByValue(String value) {
        return (Functions) EnumUtils.getEnum(Functions.class, value);
    }

    public static void addText(Functions func) {
        TEXT.add(func);
    }
}
