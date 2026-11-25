package com.badlogic.gdx.backends.android;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

/* JADX INFO: loaded from: classes8.dex */
public class AndroidHaptics {
    private AudioAttributes audioAttributes;
    private boolean hapticsSupport;
    private final Vibrator vibrator;
    private boolean vibratorSupport;

    public AndroidHaptics(Context context) {
        this.vibratorSupport = false;
        this.hapticsSupport = false;
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        this.vibrator = vibrator;
        if (vibrator == null || !vibrator.hasVibrator()) {
            return;
        }
        this.vibratorSupport = true;
        if (Build.VERSION.SDK_INT >= 29) {
            if (vibrator.hasAmplitudeControl()) {
                this.hapticsSupport = true;
            }
            this.audioAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(14).build();
        }
    }

    public void vibrate(int i) {
        if (this.vibratorSupport) {
            this.vibrator.vibrate(VibrationEffect.createOneShot(i, -1));
        }
    }

    public void vibrate(Input.VibrationType vibrationType) {
        if (!this.hapticsSupport || Build.VERSION.SDK_INT < 29) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$Input$VibrationType[vibrationType.ordinal()];
        int i2 = 2;
        if (i != 1) {
            if (i == 2) {
                i2 = 0;
            } else {
                if (i != 3) {
                    throw new IllegalArgumentException("Unknown VibrationType " + vibrationType);
                }
                i2 = 5;
            }
        }
        this.vibrator.vibrate(VibrationEffect.createPredefined(i2), this.audioAttributes);
    }

    /* JADX INFO: renamed from: com.badlogic.gdx.backends.android.AndroidHaptics$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$Input$VibrationType;

        static {
            int[] iArr = new int[Input.VibrationType.values().length];
            $SwitchMap$com$badlogic$gdx$Input$VibrationType = iArr;
            try {
                iArr[Input.VibrationType.LIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$Input$VibrationType[Input.VibrationType.MEDIUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$Input$VibrationType[Input.VibrationType.HEAVY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void vibrate(int i, int i2, boolean z) {
        if (this.hapticsSupport) {
            this.vibrator.vibrate(VibrationEffect.createOneShot(i, MathUtils.clamp(i2, 0, 255)));
        } else if (z) {
            vibrate(i);
        }
    }

    public boolean hasVibratorAvailable() {
        return this.vibratorSupport;
    }

    public boolean hasHapticsSupport() {
        return this.hapticsSupport;
    }
}
