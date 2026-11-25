package com.huawei.hms.mlsdk.asr.energy;

import com.huawei.hms.mlsdk.asr.energy.vo.DetectResult;
import com.huawei.hms.mlsdk.asr.energy.vo.SampleBuffer;
import com.huawei.hms.mlsdk.asr.energy.vo.VoiceDetectParams;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import com.huawei.hms.mlsdk.asr.o.a;

/* JADX INFO: loaded from: classes10.dex */
public class NativeVadDetector {
    private int a = new Object().hashCode();

    public NativeVadDetector() {
        try {
            System.loadLibrary("ml-vadenergy");
        } catch (UnsatisfiedLinkError e) {
            SmartLogger.e("NativeVadDetector", a.a("loadLibrary e = ").append(e.getMessage()).toString());
        }
    }

    public native DetectResult detect(SampleBuffer sampleBuffer);

    public int getTag() {
        return this.a;
    }

    public native boolean init(VoiceDetectParams voiceDetectParams);

    public native void release();
}
