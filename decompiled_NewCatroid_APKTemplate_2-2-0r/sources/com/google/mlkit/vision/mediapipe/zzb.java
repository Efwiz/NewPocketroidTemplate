package com.google.mlkit.vision.mediapipe;

import android.util.Log;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzcv;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzcw;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzcx;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdd;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzde;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdg;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.mlkit.common.MlKitException;
import com.google.research.xeno.effect.AssetRegistry;
import com.google.research.xeno.effect.AssetRegistryService;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class zzb {
    private static final String zza = "zzb";
    private final MediaPipeGraphRunnerConfig zzb;
    private zzcx zzc;
    private zzcv zzd;
    private final AtomicBoolean zze = new AtomicBoolean(false);
    private long zzf = -1;
    private ConcurrentHashMap<Long, zza> zzg;

    /* JADX INFO: renamed from: com.google.mlkit.vision.mediapipe.zzb$zzb, reason: collision with other inner class name */
    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    class C0105zzb implements zzde {
        C0105zzb() {
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzde
        public final void zza(zzdf zzdfVar) {
            ByteBuffer byteBufferWrap;
            zza zzaVar = (zza) zzb.this.zzg.remove(Long.valueOf(zzdfVar.zzb()));
            MediaPipeGraphRunner.Callback callbackZza = zzaVar.zza();
            if (callbackZza != null) {
                if (zzb.this.zzb.zzf() == MediaPipeGraphRunnerConfig.ResultType.IMAGE_FRAME) {
                    byteBufferWrap = ByteBuffer.allocateDirect((zzaVar.zzb() * zzaVar.zzc()) << 2).order(ByteOrder.nativeOrder());
                    zzdg.zza(zzdfVar, byteBufferWrap);
                } else {
                    if (zzb.this.zzb.zzf() != MediaPipeGraphRunnerConfig.ResultType.PROTO) {
                        String strValueOf = String.valueOf(zzb.this.zzb.zzf());
                        callbackZza.zza(new StringBuilder(String.valueOf(strValueOf).length() + 26).append("ResultType not supported: ").append(strValueOf).toString());
                        return;
                    }
                    byteBufferWrap = ByteBuffer.wrap(zzdg.zza(zzdfVar));
                }
                callbackZza.zza(byteBufferWrap);
            }
        }
    }

    zzb(MediaPipeGraphRunnerConfig mediaPipeGraphRunnerConfig) {
        this.zzb = mediaPipeGraphRunnerConfig;
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    private static class zza {
        private final MediaPipeGraphRunner.Callback zza;
        private final int zzb;
        private final int zzc;

        zza(MediaPipeGraphRunner.Callback callback, int i, int i2) {
            this.zza = callback;
            this.zzb = i;
            this.zzc = i2;
        }

        final MediaPipeGraphRunner.Callback zza() {
            return this.zza;
        }

        final int zzb() {
            return this.zzb;
        }

        final int zzc() {
            return this.zzc;
        }
    }

    final void zza() {
        this.zzc = zzd();
        this.zzg = new ConcurrentHashMap<>();
        this.zzd = new zzcv(this.zzc);
        this.zzc.zza(this.zzb.zzd(), new C0105zzb());
    }

    private final zzcx zzd() {
        try {
            zzcw.zza(this.zzb.zza().getApplicationContext());
            zzcx zzcxVar = new zzcx();
            zzcxVar.zza(zzcw.zza(this.zzb.zza().getApplicationContext().getAssets(), this.zzb.zzb()));
            if (this.zzb.zze() != null) {
                zzcxVar.zza(new AssetRegistryService(), new AssetRegistry(this.zzb.zze()));
            }
            return zzcxVar;
        } catch (zzdd e) {
            String str = zza;
            String strValueOf = String.valueOf(e);
            Log.e(str, new StringBuilder(String.valueOf(strValueOf).length() + 31).append("Error loading MediaPipe graph. ").append(strValueOf).toString());
            return null;
        }
    }

    final void zzb() {
        if (this.zze.getAndSet(true)) {
            return;
        }
        this.zzc.zzb();
    }

    final boolean zza(MediaPipeInput mediaPipeInput, MediaPipeGraphRunner.Callback callback) throws MlKitException {
        if (this.zzc == null) {
            return false;
        }
        if (!this.zze.getAndSet(true)) {
            this.zzc.zzb();
        }
        zzdf zzdfVarZza = mediaPipeInput.zza(this.zzd);
        long jZzc = mediaPipeInput.zzc();
        if (jZzc <= this.zzf) {
            throw new MlKitException(new StringBuilder(113).append("Timestamp must be monotonically increasing. Last timestampUs: ").append(this.zzf).append(", Current: ").append(jZzc).toString(), 13);
        }
        try {
            this.zzg.put(Long.valueOf(jZzc), new zza(callback, mediaPipeInput.zza(), mediaPipeInput.zzb()));
            this.zzc.zza(this.zzb.zzc(), zzdfVarZza, jZzc);
            this.zzf = jZzc;
            return true;
        } catch (zzdd e) {
            zzdfVarZza.zzd();
            Log.e(zza, "Mediapipe error: ", e);
            this.zzg.remove(Long.valueOf(jZzc));
            String strValueOf = String.valueOf(e.getMessage());
            String strConcat = strValueOf.length() != 0 ? "Mediapipe failed with message: ".concat(strValueOf) : new String("Mediapipe failed with message: ");
            callback.zza(strConcat);
            throw new MlKitException(strConcat, 13);
        }
    }

    public final <ResultT> ResultT zza(MediaPipeInput mediaPipeInput, Converter<ResultT> converter) throws MlKitException {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!zza(mediaPipeInput, new zzc(this, taskCompletionSource))) {
            throw new MlKitException("Couldn't send input frame. Possibe reason: load() hasn't been called yet.", 13);
        }
        Task task = taskCompletionSource.getTask();
        try {
            ResultT resulttZza = converter.zza((ByteBuffer) Tasks.await(taskCompletionSource.getTask()));
            if (task.getException() == null) {
                return resulttZza;
            }
            throw new MlKitException(task.getException().getMessage(), 13);
        } catch (InterruptedException | ExecutionException e) {
            throw new MlKitException(e.getMessage(), 13);
        }
    }

    final void zzc() {
        if (this.zze.get()) {
            this.zze.set(false);
            try {
                this.zzc.zzc();
                this.zzc.zzd();
            } catch (zzdd e) {
                Log.e(zza, "Mediapipe error: ", e);
            }
            try {
                this.zzc.zze();
            } catch (zzdd e2) {
                Log.e(zza, "Mediapipe error: ", e2);
            }
        }
    }

    final void zza(String str, int i, long j) throws MlKitException {
        zzdf zzdfVarZza = this.zzd.zza(i);
        try {
            this.zzc.zza(str, zzdfVarZza, j);
        } catch (zzdd e) {
            zzdfVarZza.zzd();
            Log.e(zza, "Mediapipe error: ", e);
            String strValueOf = String.valueOf(e.getMessage());
            throw new MlKitException(strValueOf.length() != 0 ? "Mediapipe failed with message: ".concat(strValueOf) : new String("Mediapipe failed with message: "), 13);
        }
    }

    static {
        try {
            System.loadLibrary("xeno_native");
        } catch (UnsatisfiedLinkError e) {
            Log.e(zza, "Failed to load JNI: ", e);
        }
    }
}
