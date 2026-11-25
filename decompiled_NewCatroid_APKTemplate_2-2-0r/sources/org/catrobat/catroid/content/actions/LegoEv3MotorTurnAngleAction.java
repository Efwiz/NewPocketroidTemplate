package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.LegoEv3MotorTurnAngleBrick;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3MotorTurnAngleAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3MotorTurnAngleAction extends TemporalAction {
    private static final int MAX_SPEED = 100;
    private static final int POWER_DOWN_RAMP_DEGREES = 20;
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private Formula degrees;
    private LegoEv3MotorTurnAngleBrick.Motor motorEnum;
    private Scope scope;

    /* JADX WARN: Code duplicated, block: B:31:0x0060 A[PHI: r1
      0x0060: PHI (r1v3 byte) = (r1v2 byte), (r1v4 byte) binds: [B:19:0x0049, B:21:0x004c] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        int i;
        int i2;
        int i3;
        byte b;
        byte b2 = 0;
        try {
            iIntValue = this.degrees.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            iIntValue = 0;
        }
        byte b3 = 1;
        if (iIntValue < 0) {
            iIntValue += iIntValue * (-2);
            i = -1;
        } else {
            i = 1;
        }
        if (iIntValue > 20) {
            i3 = iIntValue - 20;
            i2 = 20;
        } else {
            i2 = 0;
            i3 = iIntValue;
        }
        LegoEV3 legoEV3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (legoEV3 == null) {
            return;
        }
        int i4 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorTurnAngleBrick$Motor[this.motorEnum.ordinal()];
        if (i4 != 1) {
            b3 = 2;
            if (i4 == 2) {
                b = b3;
            } else if (i4 != 3) {
                if (i4 == 4) {
                    b2 = 8;
                } else if (i4 == 5) {
                    b2 = 6;
                }
                b = b2;
            } else {
                b = 4;
            }
        } else {
            b = b3;
        }
        legoEV3.moveMotorStepsSpeed(b, 0, i * 100, 0, i3, i2, true);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3MotorTurnAngleAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.LegoEv3MotorTurnAngleAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorTurnAngleBrick$Motor;

        static {
            int[] iArr = new int[LegoEv3MotorTurnAngleBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorTurnAngleBrick$Motor = iArr;
            try {
                iArr[LegoEv3MotorTurnAngleBrick.Motor.MOTOR_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorTurnAngleBrick$Motor[LegoEv3MotorTurnAngleBrick.Motor.MOTOR_B.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorTurnAngleBrick$Motor[LegoEv3MotorTurnAngleBrick.Motor.MOTOR_C.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorTurnAngleBrick$Motor[LegoEv3MotorTurnAngleBrick.Motor.MOTOR_D.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorTurnAngleBrick$Motor[LegoEv3MotorTurnAngleBrick.Motor.MOTOR_B_C.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public void setMotorEnum(LegoEv3MotorTurnAngleBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }

    public void setDegrees(Formula degrees) {
        this.degrees = degrees;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
