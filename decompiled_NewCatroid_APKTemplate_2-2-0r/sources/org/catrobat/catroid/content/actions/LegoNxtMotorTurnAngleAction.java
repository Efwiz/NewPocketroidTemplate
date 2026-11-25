package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoNxtMotorTurnAngleAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNxtMotorTurnAngleAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private Formula degrees;
    private LegoNxtMotorTurnAngleBrick.Motor motorEnum;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        int i;
        try {
            iIntValue = this.degrees.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            iIntValue = 0;
        }
        if (iIntValue < 0) {
            iIntValue += iIntValue * (-2);
            i = -1;
        } else {
            i = 1;
        }
        LegoNXT legoNXT = (LegoNXT) this.btService.getDevice(BluetoothDevice.LEGO_NXT);
        if (legoNXT == null) {
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorTurnAngleBrick$Motor[this.motorEnum.ordinal()];
        if (i2 == 1) {
            legoNXT.getMotorA().move(i * 30, iIntValue);
            return;
        }
        if (i2 == 2) {
            legoNXT.getMotorB().move(i * 30, iIntValue);
            return;
        }
        if (i2 == 3) {
            legoNXT.getMotorC().move(i * 30, iIntValue);
        } else {
            if (i2 != 4) {
                return;
            }
            int i3 = i * 30;
            legoNXT.getMotorB().move(i3, iIntValue);
            legoNXT.getMotorC().move(i3, iIntValue);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoNxtMotorTurnAngleAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.LegoNxtMotorTurnAngleAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorTurnAngleBrick$Motor;

        static {
            int[] iArr = new int[LegoNxtMotorTurnAngleBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorTurnAngleBrick$Motor = iArr;
            try {
                iArr[LegoNxtMotorTurnAngleBrick.Motor.MOTOR_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorTurnAngleBrick$Motor[LegoNxtMotorTurnAngleBrick.Motor.MOTOR_B.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorTurnAngleBrick$Motor[LegoNxtMotorTurnAngleBrick.Motor.MOTOR_C.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorTurnAngleBrick$Motor[LegoNxtMotorTurnAngleBrick.Motor.MOTOR_B_C.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void setMotorEnum(LegoNxtMotorTurnAngleBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }

    public void setDegrees(Formula degrees) {
        this.degrees = degrees;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
