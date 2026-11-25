package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzamb implements Comparator<zzamk> {
    zzamb() {
    }

    @Override // java.util.Comparator
    public final /* bridge */ /* synthetic */ int compare(zzamk zzamkVar, zzamk zzamkVar2) {
        zzamk zzamkVar3 = zzamkVar;
        zzamk zzamkVar4 = zzamkVar2;
        zzame it = zzamkVar3.iterator();
        zzame it2 = zzamkVar4.iterator();
        while (it.hasNext() && it2.hasNext()) {
            int iZza = zzama.zza(it.zza() & 255, it2.zza() & 255);
            if (iZza != 0) {
                return iZza;
            }
        }
        return zzama.zza(zzamkVar3.zzd(), zzamkVar4.zzd());
    }
}
