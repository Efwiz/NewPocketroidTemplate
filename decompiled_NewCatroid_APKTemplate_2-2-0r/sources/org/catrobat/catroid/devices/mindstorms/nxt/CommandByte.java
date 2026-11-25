package org.catrobat.catroid.devices.mindstorms.nxt;

import android.util.SparseArray;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/CommandByte.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum CommandByte {
    PLAY_TONE(3),
    SET_OUTPUT_STATE(4),
    SET_INPUT_MODE(5),
    GET_INPUT_VALUES(7),
    RESET_INPUT_SCALED_VALUE(8),
    LS_WRITE(15),
    LS_GET_STATUS(14),
    LS_READ(16),
    GET_BATTERY_LEVEL(11),
    KEEP_ALIVE(13);

    private static final SparseArray<CommandByte> LOOKUP = new SparseArray<>();
    private int commandByteValue;

    static {
        for (CommandByte commandByte : values()) {
            LOOKUP.put(commandByte.commandByteValue, commandByte);
        }
    }

    CommandByte(int commandByteValue) {
        this.commandByteValue = commandByteValue;
    }

    public byte getByte() {
        return (byte) this.commandByteValue;
    }

    public static boolean isMember(byte memberToTest) {
        return LOOKUP.get(memberToTest & 255) != null;
    }

    public static CommandByte getTypeByValue(byte value) {
        return LOOKUP.get(value & 255);
    }
}
