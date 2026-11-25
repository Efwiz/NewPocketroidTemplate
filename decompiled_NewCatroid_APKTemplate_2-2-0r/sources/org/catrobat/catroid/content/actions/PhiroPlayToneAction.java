package org.catrobat.catroid.content.actions;

import android.util.Log;
import androidx.mediarouter.media.GlobalMediaRouter;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroPlayToneAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroPlayToneAction extends TemporalAction {
    private BluetoothDeviceService btService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
    private Formula durationInSeconds;
    private Scope scope;
    private PhiroPlayToneBrick.Tone toneEnum;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        try {
            iIntValue = this.durationInSeconds.interpretInteger(this.scope).intValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            iIntValue = 0;
        }
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (phiro == null) {
        }
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone[this.toneEnum.ordinal()]) {
            case 1:
                phiro.playTone(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED, iIntValue);
                break;
            case 2:
                phiro.playTone(294, iIntValue);
                break;
            case 3:
                phiro.playTone(330, iIntValue);
                break;
            case 4:
                phiro.playTone(349, iIntValue);
                break;
            case 5:
                phiro.playTone(392, iIntValue);
                break;
            case 6:
                phiro.playTone(440, iIntValue);
                break;
            case 7:
                phiro.playTone(494, iIntValue);
                break;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroPlayToneAction$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.PhiroPlayToneAction$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone;

        static {
            int[] iArr = new int[PhiroPlayToneBrick.Tone.values().length];
            $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone = iArr;
            try {
                iArr[PhiroPlayToneBrick.Tone.DO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone[PhiroPlayToneBrick.Tone.RE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone[PhiroPlayToneBrick.Tone.MI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone[PhiroPlayToneBrick.Tone.FA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone[PhiroPlayToneBrick.Tone.SO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone[PhiroPlayToneBrick.Tone.LA.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$bricks$PhiroPlayToneBrick$Tone[PhiroPlayToneBrick.Tone.TI.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public void setSelectedTone(PhiroPlayToneBrick.Tone toneEnum) {
        this.toneEnum = toneEnum;
    }

    public void setDurationInSeconds(Formula durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
