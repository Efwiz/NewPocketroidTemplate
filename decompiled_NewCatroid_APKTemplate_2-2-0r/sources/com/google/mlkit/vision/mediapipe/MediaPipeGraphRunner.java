package com.google.mlkit.vision.mediapipe;

import com.google.mlkit.common.MlKitException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class MediaPipeGraphRunner {
    private static final String zza = "MediaPipeGraphRunner";
    private final MediaPipeGraphRunnerConfig zzb;
    private zzb zzc;
    private final AtomicBoolean zzd = new AtomicBoolean(false);

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public interface Callback {
        void zza(String str);

        void zza(ByteBuffer byteBuffer);
    }

    public MediaPipeGraphRunner(MediaPipeGraphRunnerConfig mediaPipeGraphRunnerConfig) {
        this.zzb = mediaPipeGraphRunnerConfig;
    }

    public void load() throws MlKitException {
        if (this.zzd.get()) {
            throw new MlKitException("close() already called, can't call load().", 13);
        }
        if (this.zzc == null) {
            zzb zzbVar = new zzb(this.zzb);
            this.zzc = zzbVar;
            zzbVar.zza();
            this.zzc.zzb();
        }
    }

    public boolean sendInputFrame(MediaPipeInput mediaPipeInput, Callback callback) throws MlKitException {
        zza();
        return this.zzc.zza(mediaPipeInput, callback);
    }

    public <ResultT> ResultT run(MediaPipeInput mediaPipeInput, Converter<ResultT> converter) throws MlKitException {
        zza();
        return (ResultT) this.zzc.zza(mediaPipeInput, converter);
    }

    private final void zza() throws MlKitException {
        if (this.zzd.get()) {
            throw new MlKitException("close() already called, can't send any more inputs.", 13);
        }
        if (this.zzc == null) {
            load();
        }
    }

    public void close() {
        this.zzd.set(true);
        zzb zzbVar = this.zzc;
        if (zzbVar != null) {
            zzbVar.zzc();
            this.zzc = null;
        }
    }

    public void sendToInputStream(String str, int i, long j) throws MlKitException {
        zza();
        this.zzc.zza(str, i, j);
    }
}
