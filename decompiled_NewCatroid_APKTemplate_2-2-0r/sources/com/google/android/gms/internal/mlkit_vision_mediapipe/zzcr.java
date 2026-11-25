package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcr extends WeakReference<Throwable> {
    private final int zza;

    public zzcr(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, null);
        if (th == null) {
            throw new NullPointerException("The referent cannot be null");
        }
        this.zza = System.identityHashCode(th);
    }

    public final int hashCode() {
        return this.zza;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            zzcr zzcrVar = (zzcr) obj;
            if (this.zza == zzcrVar.zza && get() == zzcrVar.get()) {
                return true;
            }
        }
        return false;
    }
}
