package com.google.android.gms.internal.gtm;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzvw extends zzvy {
    private zzvw() {
        super(null);
    }

    /* synthetic */ zzvw(zzvv zzvvVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.gtm.zzvy
    final <L> List<L> zza(Object obj, long j) {
        zzvh zzvhVar = (zzvh) zzxy.zzf(obj, j);
        if (zzvhVar.zzc()) {
            return zzvhVar;
        }
        int size = zzvhVar.size();
        zzvh zzvhVarZzd = zzvhVar.zzd(size == 0 ? 10 : size + size);
        zzxy.zzs(obj, j, zzvhVarZzd);
        return zzvhVarZzd;
    }

    @Override // com.google.android.gms.internal.gtm.zzvy
    final void zzb(Object obj, long j) {
        ((zzvh) zzxy.zzf(obj, j)).zzb();
    }

    @Override // com.google.android.gms.internal.gtm.zzvy
    final <E> void zzc(Object obj, Object obj2, long j) {
        zzvh zzvhVarZzd = (zzvh) zzxy.zzf(obj, j);
        zzvh zzvhVar = (zzvh) zzxy.zzf(obj2, j);
        int size = zzvhVarZzd.size();
        int size2 = zzvhVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzvhVarZzd.zzc()) {
                zzvhVarZzd = zzvhVarZzd.zzd(size2 + size);
            }
            zzvhVarZzd.addAll(zzvhVar);
        }
        if (size > 0) {
            zzvhVar = zzvhVarZzd;
        }
        zzxy.zzs(obj, j, zzvhVar);
    }
}
