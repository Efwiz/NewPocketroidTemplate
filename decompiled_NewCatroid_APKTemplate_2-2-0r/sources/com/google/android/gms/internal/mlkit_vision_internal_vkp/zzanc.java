package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzanc {
    static final zzanc zza = new zzanc(true);
    private static volatile boolean zzb = false;
    private static volatile zzanc zzc;
    private static volatile zzanc zzd;
    private final Map<zzanb, zzanq<?, ?>> zze;

    zzanc() {
        this.zze = new HashMap();
    }

    public static zzanc zza() {
        zzanc zzancVar = zzc;
        if (zzancVar == null) {
            synchronized (zzanc.class) {
                zzancVar = zzc;
                if (zzancVar == null) {
                    zzancVar = zza;
                    zzc = zzancVar;
                }
            }
        }
        return zzancVar;
    }

    public final <ContainingType extends zzapf> zzanq<ContainingType, ?> zzc(ContainingType containingtype, int i) {
        return (zzanq) this.zze.get(new zzanb(containingtype, i));
    }

    zzanc(boolean z) {
        this.zze = Collections.emptyMap();
    }

    public static zzanc zzb() {
        zzanc zzancVar = zzd;
        if (zzancVar != null) {
            return zzancVar;
        }
        synchronized (zzanc.class) {
            zzanc zzancVar2 = zzd;
            if (zzancVar2 != null) {
                return zzancVar2;
            }
            zzanc zzancVarZzb = zzank.zzb(zzanc.class);
            zzd = zzancVarZzb;
            return zzancVarZzb;
        }
    }
}
