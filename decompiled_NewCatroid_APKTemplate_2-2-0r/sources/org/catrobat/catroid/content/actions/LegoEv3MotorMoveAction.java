package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3MotorMoveAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3MotorMoveAction extends TemporalAction {
    private static final int MAX_SPEED = 100;
    private static final int MIN_SPEED = -100;
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private LegoEv3MotorMoveBrick.Motor motorEnum;
    private Scope scope;
    private Formula speed;

    /* JADX WARN: Code duplicated, block: B:9:0x0021 A[PHI: r1
      0x0021: PHI (r1v7 int) = (r1v2 int), (r1v3 int) binds: [B:8:0x001f, B:11:0x0025] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        try {
            iIntValue = this.speed.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            iIntValue = 0;
        }
        int i = -100;
        if (iIntValue < -100) {
            iIntValue = i;
        } else {
            i = 100;
            if (iIntValue > 100) {
                iIntValue = i;
            }
        }
        LegoEV3 legoEV3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (legoEV3 == null) {
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorMoveBrick$Motor[this.motorEnum.ordinal()];
        byte b = 1;
        if (i2 != 1) {
            b = 2;
            if (i2 != 2) {
                if (i2 == 3) {
                    b = 4;
                } else if (i2 != 4) {
                    b = i2 != 5 ? (byte) 0 : (byte) 6;
                } else {
                    b = 8;
                }
            }
        }
        legoEV3.moveMotorSpeed(b, 0, iIntValue);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3MotorMoveAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.LegoEv3MotorMoveAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorMoveBrick$Motor;

        static {
            int[] iArr = new int[LegoEv3MotorMoveBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorMoveBrick$Motor = iArr;
            try {
                iArr[LegoEv3MotorMoveBrick.Motor.MOTOR_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorMoveBrick$Motor[LegoEv3MotorMoveBrick.Motor.MOTOR_B.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorMoveBrick$Motor[LegoEv3MotorMoveBrick.Motor.MOTOR_C.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorMoveBrick$Motor[LegoEv3MotorMoveBrick.Motor.MOTOR_D.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3MotorMoveBrick$Motor[LegoEv3MotorMoveBrick.Motor.MOTOR_B_C.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public void setMotorEnum(LegoEv3MotorMoveBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }

    public void setSpeed(Formula speed) {
        this.speed = speed;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
