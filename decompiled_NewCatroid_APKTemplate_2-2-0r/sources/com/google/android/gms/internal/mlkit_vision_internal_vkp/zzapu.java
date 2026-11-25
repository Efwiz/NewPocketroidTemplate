package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.ArrayDeque;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapu {
    private final ArrayDeque<zzamk> zza = new ArrayDeque<>();

    private zzapu() {
    }

    static /* bridge */ /* synthetic */ zzamk zza(zzapu zzapuVar, zzamk zzamkVar, zzamk zzamkVar2) {
        zzapuVar.zzb(zzamkVar);
        zzapuVar.zzb(zzamkVar2);
        zzamk zzamkVarPop = zzapuVar.zza.pop();
        while (!zzapuVar.zza.isEmpty()) {
            zzamkVarPop = new zzapy(zzapuVar.zza.pop(), zzamkVarPop);
        }
        return zzamkVarPop;
    }

    private final void zzb(zzamk zzamkVar) {
        zzapx zzapxVar;
        if (!zzamkVar.zzh()) {
            if (zzamkVar instanceof zzapy) {
                zzapy zzapyVar = (zzapy) zzamkVar;
                zzb(zzapyVar.zzd);
                zzb(zzapyVar.zze);
                return;
            } else {
                String strValueOf = String.valueOf(zzamkVar.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 49);
                sb.append("Has a new type of ByteString been created? Found ");
                sb.append(strValueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        int iZzc = zzc(zzamkVar.zzd());
        int iZzc2 = zzapy.zzc(iZzc + 1);
        if (this.zza.isEmpty() || this.zza.peek().zzd() >= iZzc2) {
            this.zza.push(zzamkVar);
            return;
        }
        int iZzc3 = zzapy.zzc(iZzc);
        zzamk zzamkVarPop = this.zza.pop();
        while (true) {
            zzapxVar = null;
            if (this.zza.isEmpty() || this.zza.peek().zzd() >= iZzc3) {
                break;
            } else {
                zzamkVarPop = new zzapy(this.zza.pop(), zzamkVarPop);
            }
        }
        zzapy zzapyVar2 = new zzapy(zzamkVarPop, zzamkVar);
        while (!this.zza.isEmpty()) {
            if (this.zza.peek().zzd() >= zzapy.zzc(zzc(zzapyVar2.zzd()) + 1)) {
                break;
            } else {
                zzapyVar2 = new zzapy(this.zza.pop(), zzapyVar2);
            }
        }
        this.zza.push(zzapyVar2);
    }

    private static final int zzc(int i) {
        int iBinarySearch = Arrays.binarySearch(zzapy.zza, i);
        return iBinarySearch < 0 ? (-(iBinarySearch + 1)) - 1 : iBinarySearch;
    }

    /* synthetic */ zzapu(zzapt zzaptVar) {
    }
}
