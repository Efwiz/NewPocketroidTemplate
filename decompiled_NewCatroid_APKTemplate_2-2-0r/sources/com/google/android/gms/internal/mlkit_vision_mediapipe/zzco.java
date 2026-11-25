package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzco {
    private final ConcurrentHashMap<zzcr, List<Throwable>> zza = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzb = new ReferenceQueue<>();

    zzco() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Reference<? extends Throwable> referencePoll = this.zzb.poll();
        while (referencePoll != null) {
            this.zza.remove(referencePoll);
            referencePoll = this.zzb.poll();
        }
        return this.zza.get(new zzcr(th, null));
    }
}
