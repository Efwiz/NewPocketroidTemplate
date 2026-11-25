package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoNxtMotorStopAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNxtMotorStopAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private LegoNxtMotorStopBrick.Motor motorEnum;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        LegoNXT legoNXT = (LegoNXT) this.btService.getDevice(BluetoothDevice.LEGO_NXT);
        if (legoNXT == null) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorStopBrick$Motor[this.motorEnum.ordinal()];
        if (i == 1) {
            legoNXT.getMotorA().stop();
            return;
        }
        if (i == 2) {
            legoNXT.getMotorB().stop();
            return;
        }
        if (i == 3) {
            legoNXT.getMotorC().stop();
            return;
        }
        if (i == 4) {
            legoNXT.getMotorB().stop();
            legoNXT.getMotorC().stop();
        } else {
            if (i != 5) {
                return;
            }
            legoNXT.stopAllMovements();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoNxtMotorStopAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.LegoNxtMotorStopAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorStopBrick$Motor;

        static {
            int[] iArr = new int[LegoNxtMotorStopBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorStopBrick$Motor = iArr;
            try {
                iArr[LegoNxtMotorStopBrick.Motor.MOTOR_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorStopBrick$Motor[LegoNxtMotorStopBrick.Motor.MOTOR_B.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorStopBrick$Motor[LegoNxtMotorStopBrick.Motor.MOTOR_C.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorStopBrick$Motor[LegoNxtMotorStopBrick.Motor.MOTOR_B_C.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorStopBrick$Motor[LegoNxtMotorStopBrick.Motor.ALL_MOTORS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public void setMotorEnum(LegoNxtMotorStopBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }
}
