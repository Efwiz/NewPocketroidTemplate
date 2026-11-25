package org.catrobat.catroid.devices.mindstorms.ev3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/EV3CommandType.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum EV3CommandType {
    DIRECT_COMMAND_REPLY(0),
    DIRECT_COMMAND_NO_REPLY(128),
    SYSTEM_COMMAND_REPLY(1),
    SYSTEM_COMMAND_NO_REPLY(129);

    private int commandTypeValue;

    EV3CommandType(int commandTypeValue) {
        this.commandTypeValue = commandTypeValue;
    }

    public byte getByte() {
        return (byte) this.commandTypeValue;
    }
}
