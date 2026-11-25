package org.catrobat.catroid.devices.mindstorms.ev3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/EV3MotorOutputByteCode.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum EV3MotorOutputByteCode {
    MOTOR_A_OUT(1),
    MOTOR_B_OUT(2),
    MOTOR_C_OUT(4),
    MOTOR_D_OUT(8);

    private int ev3MotorOutputValue;

    EV3MotorOutputByteCode(int ev3MotorOutputValue) {
        this.ev3MotorOutputValue = ev3MotorOutputValue;
    }

    public byte getByte() {
        return (byte) this.ev3MotorOutputValue;
    }
}
