package com.google.android.gms.internal.gtm;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzsv implements Comparator<zztd> {
    zzsv() {
    }

    @Override // java.util.Comparator
    public final /* bridge */ /* synthetic */ int compare(zztd zztdVar, zztd zztdVar2) {
        zztd zztdVar3 = zztdVar;
        zztd zztdVar4 = zztdVar2;
        zzst zzstVar = new zzst(zztdVar3);
        zzst zzstVar2 = new zzst(zztdVar4);
        while (zzstVar.hasNext() && zzstVar2.hasNext()) {
            int iZza = zzsu.zza(zzstVar.zza() & 255, zzstVar2.zza() & 255);
            if (iZza != 0) {
                return iZza;
            }
        }
        return zzsu.zza(zztdVar3.zzd(), zztdVar4.zzd());
    }
}
