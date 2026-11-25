package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.PhiroMotorStopBrick;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroMotorStopAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroMotorStopAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private PhiroMotorStopBrick.Motor motorEnum;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (phiro == null) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorStopBrick$Motor[this.motorEnum.ordinal()];
        if (i == 1) {
            phiro.stopLeftMotor();
        } else if (i == 2) {
            phiro.stopRightMotor();
        } else {
            if (i != 3) {
                return;
            }
            phiro.stopAllMovements();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroMotorStopAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.PhiroMotorStopAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorStopBrick$Motor;

        static {
            int[] iArr = new int[PhiroMotorStopBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorStopBrick$Motor = iArr;
            try {
                iArr[PhiroMotorStopBrick.Motor.MOTOR_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorStopBrick$Motor[PhiroMotorStopBrick.Motor.MOTOR_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorStopBrick$Motor[PhiroMotorStopBrick.Motor.MOTOR_BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setMotorEnum(PhiroMotorStopBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }
}
