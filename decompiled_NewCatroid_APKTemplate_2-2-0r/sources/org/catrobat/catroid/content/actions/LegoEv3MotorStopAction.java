package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3MotorStopAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3MotorStopAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private LegoEv3MotorStopBrick.Motor motorEnum;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        byte b;
        LegoEV3 legoEV3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (legoEV3 == null) {
            return;
        }
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor[this.motorEnum.ordinal()]) {
            case 1:
                b = 1;
                break;
            case 2:
                b = 2;
                break;
            case 3:
                b = 4;
                break;
            case 4:
                b = 8;
                break;
            case 5:
                b = 6;
                break;
            case 6:
                b = 15;
                break;
            default:
                b = 0;
                break;
        }
        legoEV3.stopMotor(b, 0, true);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3MotorStopAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.LegoEv3MotorStopAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor;

        static {
            int[] iArr = new int[LegoEv3MotorStopBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor = iArr;
            try {
                iArr[LegoEv3MotorStopBrick.Motor.MOTOR_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor[LegoEv3MotorStopBrick.Motor.MOTOR_B.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor[LegoEv3MotorStopBrick.Motor.MOTOR_C.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor[LegoEv3MotorStopBrick.Motor.MOTOR_D.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor[LegoEv3MotorStopBrick.Motor.MOTOR_B_C.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorStopBrick$Motor[LegoEv3MotorStopBrick.Motor.ALL_MOTORS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void setMotorEnum(LegoEv3MotorStopBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }
}
