package org.catrobat.catroid.devices.mindstorms.nxt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/CommandType.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum CommandType {
    DIRECT_COMMAND(0),
    REPLY_COMMAND(2);

    private int commandTypeValue;

    CommandType(int commandTypeValue) {
        this.commandTypeValue = commandTypeValue;
    }

    public byte getByte() {
        return (byte) this.commandTypeValue;
    }

    public static boolean isMember(byte memberToTest) {
        for (CommandType commandType : values()) {
            if (commandType.getByte() == memberToTest) {
                return true;
            }
        }
        return false;
    }
}
