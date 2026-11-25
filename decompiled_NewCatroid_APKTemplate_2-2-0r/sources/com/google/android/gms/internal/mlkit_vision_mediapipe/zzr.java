package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzr {
    private static final zzt zza = new zzt();
    private final AtomicLong zzb = new AtomicLong();
    private final AtomicLong zzc = new AtomicLong();
    private final AtomicInteger zzd = new AtomicInteger();

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    static final class zza {
        private static long zza() {
            throw new NoSuchMethodError();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void zza(int i) {
            throw new NoSuchMethodError();
        }

        public final String toString() {
            throw new NoSuchMethodError();
        }

        public final int hashCode() {
            throw new NoSuchMethodError();
        }

        public final boolean equals(Object obj) {
            throw new NoSuchMethodError();
        }

        static /* synthetic */ long zza(zza zzaVar) {
            return zza();
        }
    }

    zzr() {
    }

    public static zzr zza(Object obj) {
        return zza.zza(obj);
    }

    final boolean zza(int i) {
        return this.zzb.getAndIncrement() % ((long) i) == 0;
    }

    final boolean zza(long j, zza zzaVar) {
        long j2 = this.zzc.get();
        long jZza = zza.zza(zzaVar) + j2;
        if (jZza >= 0 && ((j >= jZza || j2 == 0) && this.zzc.compareAndSet(j2, j))) {
            zza.zza(this.zzd.getAndSet(0));
            return true;
        }
        this.zzd.incrementAndGet();
        return false;
    }
}
