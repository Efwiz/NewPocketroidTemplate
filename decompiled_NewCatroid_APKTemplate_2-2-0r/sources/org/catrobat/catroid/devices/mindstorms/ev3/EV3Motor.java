package org.catrobat.catroid.devices.mindstorms.ev3;

import org.catrobat.catroid.devices.mindstorms.MindstormsMotor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/EV3Motor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EV3Motor implements MindstormsMotor {
    private byte outputField;

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void move(int speed, int degrees) {
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void move(int speed, int degrees, boolean reply) {
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void stop() {
    }

    public EV3Motor(int port) {
        if (port == 0) {
            this.outputField = EV3MotorOutputByteCode.MOTOR_A_OUT.getByte();
            return;
        }
        if (port == 1) {
            this.outputField = EV3MotorOutputByteCode.MOTOR_B_OUT.getByte();
        } else if (port == 2) {
            this.outputField = EV3MotorOutputByteCode.MOTOR_C_OUT.getByte();
        } else {
            if (port != 3) {
                return;
            }
            this.outputField = EV3MotorOutputByteCode.MOTOR_D_OUT.getByte();
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void move(int speed) {
        move(speed, 0, false);
    }

    public byte getOutputField() {
        return this.outputField;
    }
}
