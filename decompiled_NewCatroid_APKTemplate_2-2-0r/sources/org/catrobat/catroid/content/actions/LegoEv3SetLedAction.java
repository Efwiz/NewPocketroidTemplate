package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3SetLedAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegoEv3SetLedAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private LegoEv3SetLedBrick.LedStatus ledStatusEnum;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        LegoEV3 legoEV3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (legoEV3 == null) {
            return;
        }
        int i = 0;
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[this.ledStatusEnum.ordinal()]) {
            case 2:
                i = 1;
                break;
            case 3:
                i = 2;
                break;
            case 4:
                i = 3;
                break;
            case 5:
                i = 4;
                break;
            case 6:
                i = 5;
                break;
            case 7:
                i = 6;
                break;
            case 8:
                i = 7;
                break;
            case 9:
                i = 8;
                break;
            case 10:
                i = 9;
                break;
        }
        legoEV3.setLed(i);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LegoEv3SetLedAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.LegoEv3SetLedAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus;

        static {
            int[] iArr = new int[LegoEv3SetLedBrick.LedStatus.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus = iArr;
            try {
                iArr[LegoEv3SetLedBrick.LedStatus.LED_OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_GREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_RED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_ORANGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_GREEN_FLASHING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_RED_FLASHING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_ORANGE_FLASHING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_GREEN_PULSE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_RED_PULSE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$LegoEv3SetLedBrick$LedStatus[LegoEv3SetLedBrick.LedStatus.LED_ORANGE_PULSE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public void setLedStatusEnum(LegoEv3SetLedBrick.LedStatus ledStatusEnum) {
        this.ledStatusEnum = ledStatusEnum;
    }
}
