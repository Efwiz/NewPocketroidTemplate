package com.huawei.hms.mlsdk.asr.engine;

import android.os.Bundle;
import android.os.SystemClock;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import com.huawei.hms.mlsdk.asr.energy.NativeVadDetector;
import com.huawei.hms.mlsdk.asr.energy.vo.DetectResult;
import com.huawei.hms.mlsdk.asr.energy.vo.SampleBuffer;
import com.huawei.hms.mlsdk.asr.energy.vo.VoiceDetectParams;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: compiled from: AsrVadDetector.java */
/* JADX INFO: loaded from: classes10.dex */
public class d {
    private AsrEngine a;
    private com.huawei.hms.mlsdk.asr.engine.utils.a c;
    private NativeVadDetector h;
    private c i;
    private List<a> b = new ArrayList();
    private int d = 1;
    private boolean e = false;
    private Long f = null;
    private Long g = null;

    /* JADX INFO: compiled from: AsrVadDetector.java */
    public interface a {
    }

    /* JADX INFO: compiled from: AsrVadDetector.java */
    public static class b {
        private boolean a;
        private double b;
        private byte[] c;

        public double b() {
            return this.b;
        }

        public boolean c() {
            return this.a;
        }

        public void a(boolean z) {
            this.a = z;
        }

        public void a(double d) {
            this.b = d;
        }

        public byte[] a() {
            byte[] bArr = this.c;
            if (bArr == null) {
                return null;
            }
            return (byte[]) bArr.clone();
        }

        public void a(byte[] bArr) {
            this.c = bArr == null ? null : (byte[]) bArr.clone();
        }
    }

    public d(AsrEngine asrEngine) {
        this.c = null;
        this.a = asrEngine;
        asrEngine.getEngineConfig().setVadDetectDuration(AsrConstants.bufferSize / 64);
        this.c = new com.huawei.hms.mlsdk.asr.engine.utils.a(asrEngine.getEngineConfig().getVadDetectDuration() * 64);
        VoiceDetectParams voiceDetectParamsA = new VoiceDetectParams.b().a(13500).a();
        NativeVadDetector nativeVadDetector = new NativeVadDetector();
        this.h = nativeVadDetector;
        nativeVadDetector.init(voiceDetectParamsA);
        this.i = new c(asrEngine.getEngineConfig().getVadEndMuteDuration());
    }

    private b b(byte[] bArr) throws com.huawei.hms.mlsdk.asr.engine.a {
        DetectResult detectResultDetect = this.h.detect(new SampleBuffer.b().a(bArr).a(bArr.length).a());
        if (detectResultDetect == null) {
            detectResultDetect = new DetectResult(1, 0.0f);
        }
        b bVar = new b();
        bVar.a(bArr);
        bVar.a(detectResultDetect.getEnergy());
        bVar.a(detectResultDetect.getVoiceType() == 1);
        return bVar;
    }

    public void a(a aVar) {
        this.b.add(aVar);
    }

    public void a(byte[] bArr) throws com.huawei.hms.mlsdk.asr.engine.a {
        this.c.a(bArr);
        byte[] bArrB = this.c.b();
        if (bArrB == null || bArrB.length == 0) {
            return;
        }
        try {
            b bVarB = b(bArrB);
            Iterator<a> it = this.b.iterator();
            while (it.hasNext()) {
                ((AsrEngine.d) it.next()).a(bVarB);
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (!bVarB.c() && !this.e) {
                this.e = true;
                this.g = Long.valueOf(jElapsedRealtime);
                SmartLogger.i("AsrVadDetector", "start send to cloud, energy===>" + bVarB.b());
                Iterator<a> it2 = this.b.iterator();
                while (it2.hasNext()) {
                    ((AsrEngine.d) it2.next()).e(this.g.longValue());
                }
                return;
            }
            this.i.a(bVarB.b);
            if (this.a.hasResult()) {
                this.i.a();
                if (this.i.b()) {
                    Iterator<a> it3 = this.b.iterator();
                    while (it3.hasNext()) {
                        ((AsrEngine.d) it3.next()).d(jElapsedRealtime);
                    }
                    return;
                } else {
                    if (jElapsedRealtime - this.g.longValue() > this.a.getEngineConfig().getMaxAudioDuration()) {
                        Iterator<a> it4 = this.b.iterator();
                        while (it4.hasNext()) {
                            ((AsrEngine.d) it4.next()).a(jElapsedRealtime);
                        }
                        return;
                    }
                    return;
                }
            }
            if (this.f == null) {
                this.f = this.a.getRecordStartTime();
            }
            if (this.f == null) {
                this.f = Long.valueOf(jElapsedRealtime);
            }
            long jLongValue = jElapsedRealtime - this.f.longValue();
            int vadStartMuteDuration = this.a.getEngineConfig().getVadStartMuteDuration();
            int i = this.d;
            if (jLongValue >= vadStartMuteDuration * i) {
                if (i != this.a.getEngineConfig().getVadStartMuteDetectTimes()) {
                    SmartLogger.i("AsrProcessor", "onNoSound duration===>" + jLongValue);
                    Iterator<a> it5 = this.b.iterator();
                    while (it5.hasNext()) {
                        ((AsrEngine.d) it5.next()).b(jElapsedRealtime);
                    }
                } else {
                    SmartLogger.i("AsrProcessor", "onNoSoundTimesExceed duration===>" + jLongValue);
                    Iterator<a> it6 = this.b.iterator();
                    while (it6.hasNext()) {
                        ((AsrEngine.d) it6.next()).c(jElapsedRealtime);
                    }
                }
                this.d++;
            }
        } catch (RuntimeException e) {
            SmartLogger.e("AsrVadDetector", com.huawei.hms.mlsdk.asr.o.a.a("RuntimeException e = ").append(e.getMessage()).toString());
            Bundle bundle = new Bundle();
            bundle.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, 1002);
            Iterator<a> it7 = this.b.iterator();
            while (it7.hasNext()) {
                ((AsrEngine.d) it7.next()).a(new AsrError(40, "Service unavailable", bundle));
            }
        }
    }

    /* JADX INFO: compiled from: AsrVadDetector.java */
    private static class c {
        private static final double f = Math.log10(1000.0d) * 10.0d;
        private double a = -1.0d;
        private double b = -1.0d;
        private long c = -1;
        private long d;
        private List<Double[]> e;

        public c(long j) {
            ArrayList arrayList = new ArrayList();
            this.e = arrayList;
            this.d = j;
            Double dValueOf = Double.valueOf(40.0d);
            arrayList.add(new Double[]{Double.valueOf(0.0d), dValueOf, Double.valueOf(0.15d)});
            List<Double[]> list = this.e;
            Double dValueOf2 = Double.valueOf(60.0d);
            list.add(new Double[]{dValueOf, dValueOf2, Double.valueOf(0.1d)});
            List<Double[]> list2 = this.e;
            Double dValueOf3 = Double.valueOf(70.0d);
            list2.add(new Double[]{dValueOf2, dValueOf3, Double.valueOf(0.075d)});
            this.e.add(new Double[]{dValueOf3, Double.valueOf(Double.MAX_VALUE), Double.valueOf(0.05d)});
        }

        public void a(double d) {
            double dLog10 = Double.compare(d, 0.0d) > 0 ? Math.log10(d) * 10.0d : 0.0d;
            if (Double.compare(dLog10, f) <= 0) {
                return;
            }
            if (Double.compare(this.a, 0.0d) <= 0) {
                SmartLogger.i("AsrVadDetector", String.format(Locale.ENGLISH, "first energy = %.2f", Double.valueOf(dLog10)));
                this.a = dLog10;
                return;
            }
            if (Double.compare(this.b, 0.0d) <= 0) {
                this.c = -1L;
                if (Double.compare(this.a, dLog10) > 0) {
                    this.a = dLog10;
                    return;
                }
                return;
            }
            double dAbs = Math.abs(dLog10 - this.b) / this.b;
            for (Double[] dArr : this.e) {
                if (Double.compare(this.b, dArr[0].doubleValue()) > 0 && Double.compare(this.b, dArr[1].doubleValue()) <= 0) {
                    if (Double.compare(dAbs, dArr[2].doubleValue()) <= 0) {
                        if (this.c > 0) {
                            break;
                        }
                        this.c = SystemClock.elapsedRealtime();
                        break;
                    }
                    this.c = -1L;
                    break;
                }
            }
            SmartLogger.i("AsrVadDetector", String.format(Locale.ENGLISH, "lowerEnergy = %.2f, energy = %.2f, diff = %.2f", Double.valueOf(this.b), Double.valueOf(dLog10), Double.valueOf(dAbs)));
        }

        public boolean b() {
            if (this.c <= 0) {
                return false;
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime() - this.c;
            SmartLogger.i("AsrVadDetector", String.format(Locale.ENGLISH, "duration = %d, timingDuration = %d, lowerEnergy = %.2f", Long.valueOf(jElapsedRealtime), Long.valueOf(this.d), Double.valueOf(this.b)));
            return jElapsedRealtime >= this.d;
        }

        public void a() {
            if (Double.compare(this.b, 0.0d) > 0) {
                return;
            }
            this.b = this.a;
            SmartLogger.i("AsrVadDetector", String.format(Locale.ENGLISH, "stop calc lower energy, lowerEnergy = %.2f", Double.valueOf(this.b)));
        }
    }

    public synchronized void a() {
        com.huawei.hms.mlsdk.asr.engine.utils.a aVar = this.c;
        if (aVar != null) {
            aVar.a();
        }
        NativeVadDetector nativeVadDetector = this.h;
        if (nativeVadDetector != null) {
            nativeVadDetector.release();
            this.h = null;
        }
    }
}
