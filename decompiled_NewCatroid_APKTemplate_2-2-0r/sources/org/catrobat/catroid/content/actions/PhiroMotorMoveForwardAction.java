package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveForwardBrick;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroMotorMoveForwardAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroMotorMoveForwardAction extends TemporalAction {
    private static final int MAX_SPEED = 100;
    private static final int MIN_SPEED = 0;
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private PhiroMotorMoveForwardBrick.Motor motorEnum;
    private Scope scope;
    private Formula speed;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        int i = 0;
        try {
            iIntValue = this.speed.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            iIntValue = 0;
        }
        if (iIntValue >= 0) {
            i = 100;
            if (iIntValue <= 100) {
                i = iIntValue;
            }
        }
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (phiro == null) {
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorMoveForwardBrick$Motor[this.motorEnum.ordinal()];
        if (i2 == 1) {
            phiro.moveLeftMotorForward(i);
            return;
        }
        if (i2 == 2) {
            phiro.moveRightMotorForward(i);
        } else {
            if (i2 != 3) {
                return;
            }
            phiro.moveRightMotorForward(i);
            phiro.moveLeftMotorForward(i);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroMotorMoveForwardAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.PhiroMotorMoveForwardAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorMoveForwardBrick$Motor;

        static {
            int[] iArr = new int[PhiroMotorMoveForwardBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorMoveForwardBrick$Motor = iArr;
            try {
                iArr[PhiroMotorMoveForwardBrick.Motor.MOTOR_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorMoveForwardBrick$Motor[PhiroMotorMoveForwardBrick.Motor.MOTOR_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroMotorMoveForwardBrick$Motor[PhiroMotorMoveForwardBrick.Motor.MOTOR_BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setMotorEnum(PhiroMotorMoveForwardBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }

    public void setSpeed(Formula speed) {
        this.speed = speed;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
