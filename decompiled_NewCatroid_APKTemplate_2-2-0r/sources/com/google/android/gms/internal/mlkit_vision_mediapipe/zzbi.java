package com.google.android.gms.internal.mlkit_vision_mediapipe;

import android.os.Build;
import android.util.Log;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbi extends zzay {
    private static final AtomicReference<zzbe> zza = new AtomicReference<>();
    private static final AtomicLong zzc = new AtomicLong();
    private static final ConcurrentLinkedQueue<zzbk> zzd = new ConcurrentLinkedQueue<>();
    private volatile zzal zzb;

    public static zzal zza(String str) {
        AtomicReference<zzbe> atomicReference = zza;
        if (atomicReference.get() != null) {
            return atomicReference.get().zza(str);
        }
        zzbi zzbiVar = new zzbi(str.replace('$', '.'));
        zzbh.zza.offer(zzbiVar);
        if (atomicReference.get() != null) {
            while (true) {
                zzbi zzbiVarPoll = zzbh.zza.poll();
                if (zzbiVarPoll == null) {
                    break;
                }
                zzbiVarPoll.zzb = zza.get().zza(zzbiVarPoll.zza());
            }
            zzb();
        }
        return zzbiVar;
    }

    private zzbi(String str) {
        super(str);
        boolean z = true;
        boolean z2 = Build.FINGERPRINT == null || "robolectric".equals(Build.FINGERPRINT);
        boolean z3 = "goldfish".equals(Build.HARDWARE) || "ranchu".equals(Build.HARDWARE);
        if (!"eng".equals(Build.TYPE) && !"userdebug".equals(Build.TYPE)) {
            z = false;
        }
        if (z2 || z3) {
            this.zzb = new zzbb().zza(zza());
        } else if (z) {
            this.zzb = new zzbo().zza(false).zza(zza());
        } else {
            this.zzb = null;
        }
    }

    private static void zzb() {
        while (true) {
            zzbk zzbkVarPoll = zzd.poll();
            if (zzbkVarPoll == null) {
                return;
            }
            zzc.getAndDecrement();
            zzal zzalVarZza = zzbkVarPoll.zza();
            zzai zzaiVarZzb = zzbkVarPoll.zzb();
            if (zzaiVarZzb.zzj() || zzalVarZza.zza(zzaiVarZzb.zzd())) {
                zzalVarZza.zza(zzaiVarZzb);
            }
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public final boolean zza(Level level) {
        if (this.zzb != null) {
            return this.zzb.zza(level);
        }
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public final void zza(zzai zzaiVar) {
        if (this.zzb != null) {
            this.zzb.zza(zzaiVar);
            return;
        }
        if (zzc.incrementAndGet() > 20) {
            zzd.poll();
            Log.w("ProxyAndroidLoggerBackend", "Too many Flogger logs received before configuration. Dropping old logs.");
        }
        zzd.offer(new zzbk(this, zzaiVar));
        if (this.zzb != null) {
            zzb();
        }
    }
}
