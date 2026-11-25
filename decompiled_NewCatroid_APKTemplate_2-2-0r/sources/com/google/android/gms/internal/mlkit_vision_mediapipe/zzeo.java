package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzeo {
    private static volatile boolean zza = false;
    private static boolean zzb = true;
    private static volatile zzeo zzc;
    private static final zzeo zzd = new zzeo(true);
    private final Map<zza, zzfb.zzd<?, ?>> zze;

    public static zzeo zza() {
        zzeo zzeoVar = zzc;
        if (zzeoVar == null) {
            synchronized (zzeo.class) {
                zzeoVar = zzc;
                if (zzeoVar == null) {
                    zzeoVar = zzd;
                    zzc = zzeoVar;
                }
            }
        }
        return zzeoVar;
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    private static final class zza {
        private final Object zza;
        private final int zzb;

        zza(Object obj, int i) {
            this.zza = obj;
            this.zzb = i;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zza) * 65535) + this.zzb;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) obj;
            return this.zza == zzaVar.zza && this.zzb == zzaVar.zzb;
        }
    }

    public final <ContainingType extends zzgi> zzfb.zzd<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zzfb.zzd) this.zze.get(new zza(containingtype, i));
    }

    zzeo() {
        this.zze = new HashMap();
    }

    private zzeo(boolean z) {
        this.zze = Collections.emptyMap();
    }
}
