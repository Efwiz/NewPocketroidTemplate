package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoNxtMotorMoveAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoNxtMotorMoveAction extends TemporalAction {
    private static final int MAX_SPEED = 100;
    private static final int MIN_SPEED = -100;
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private LegoNxtMotorMoveBrick.Motor motorEnum;
    private Scope scope;
    private Formula speed;

    /* JADX WARN: Code duplicated, block: B:8:0x0020 A[PHI: r0
      0x0020: PHI (r0v11 int) = (r0v2 int), (r0v3 int) binds: [B:7:0x001e, B:10:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
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
        LegoNXT legoNXT = (LegoNXT) this.btService.getDevice(BluetoothDevice.LEGO_NXT);
        if (legoNXT == null) {
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorMoveBrick$Motor[this.motorEnum.ordinal()];
        if (i2 == 1) {
            legoNXT.getMotorA().move(iIntValue);
            return;
        }
        if (i2 == 2) {
            legoNXT.getMotorB().move(iIntValue);
            return;
        }
        if (i2 == 3) {
            legoNXT.getMotorC().move(iIntValue);
        } else {
            if (i2 != 4) {
                return;
            }
            legoNXT.getMotorB().move(iIntValue);
            legoNXT.getMotorC().move(iIntValue);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoNxtMotorMoveAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.LegoNxtMotorMoveAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorMoveBrick$Motor;

        static {
            int[] iArr = new int[LegoNxtMotorMoveBrick.Motor.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorMoveBrick$Motor = iArr;
            try {
                iArr[LegoNxtMotorMoveBrick.Motor.MOTOR_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorMoveBrick$Motor[LegoNxtMotorMoveBrick.Motor.MOTOR_B.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorMoveBrick$Motor[LegoNxtMotorMoveBrick.Motor.MOTOR_C.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoNxtMotorMoveBrick$Motor[LegoNxtMotorMoveBrick.Motor.MOTOR_B_C.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void setMotorEnum(LegoNxtMotorMoveBrick.Motor motorEnum) {
        this.motorEnum = motorEnum;
    }

    public void setSpeed(Formula speed) {
        this.speed = speed;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
