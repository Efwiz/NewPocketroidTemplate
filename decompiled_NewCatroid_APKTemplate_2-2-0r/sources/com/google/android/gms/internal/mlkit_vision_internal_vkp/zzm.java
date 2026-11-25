package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzm {
    public static final zzm zza;
    public static final zzm zzb;
    private final boolean zzc;
    private final boolean zzd = false;
    private final zzkz<zzw> zze;

    static {
        zzj zzjVar = null;
        zzk zzkVar = new zzk(zzjVar);
        zzkVar.zza();
        zza = zzkVar.zzc();
        zzk zzkVar2 = new zzk(zzjVar);
        zzkVar2.zzb();
        zzb = zzkVar2.zzc();
    }

    /* synthetic */ zzm(boolean z, boolean z2, zzkz zzkzVar, zzl zzlVar) {
        this.zzc = z;
        this.zze = zzkzVar;
    }

    static /* bridge */ /* synthetic */ boolean zza(zzm zzmVar) {
        boolean z = zzmVar.zzd;
        return false;
    }

    static /* bridge */ /* synthetic */ int zzc(zzm zzmVar, Context context, zzv zzvVar) {
        zzkz<zzw> zzkzVar = zzmVar.zze;
        int size = zzkzVar.size();
        int i = 0;
        while (i < size) {
            zzw zzwVar = zzkzVar.get(i);
            boolean z = zzmVar.zzc;
            int iZza = zzwVar.zza();
            int i2 = iZza - 1;
            if (iZza == 0) {
                throw null;
            }
            i++;
            if (i2 == 0) {
                return 1;
            }
            if (i2 == 1) {
                return 2;
            }
        }
        return 3;
    }
}
