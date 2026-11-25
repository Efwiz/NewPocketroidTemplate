package com.huawei.hms.mlplugin.asr;

import android.os.Bundle;
import com.huawei.hms.mlplugin.asr.view.VoiceWaveView;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;

/* JADX INFO: compiled from: AsrDefaultWavePainter.java */
/* JADX INFO: loaded from: classes10.dex */
public class a implements b {
    private VoiceWaveView a;

    public a(VoiceWaveView voiceWaveView) {
        this.a = voiceWaveView;
    }

    public void a(double d, byte[] bArr, Bundle bundle) {
        double dSqrt = Math.sqrt(Math.sqrt(d)) / (Math.sqrt(34000.0d) / ((double) this.a.getMaxVolume()));
        if (dSqrt < 2.0d) {
            dSqrt = 2.0d;
        }
        if (dSqrt > 120.0d) {
            dSqrt = 120.0d;
        }
        double dPow = ((Math.pow(2.718281828459045d, (dSqrt / 120.0d) * (-0.925000011920929d)) * 5.5d) * dSqrt) / 120.0d;
        VoiceWaveView voiceWaveView = this.a;
        if (voiceWaveView != null) {
            int maxVolume = (int) (((double) voiceWaveView.getMaxVolume()) * dPow);
            SmartLogger.d("AsrDefaultWavePainter", "buffer = " + dPow + ", volume = " + maxVolume);
            this.a.setVolume(maxVolume);
        }
    }
}
