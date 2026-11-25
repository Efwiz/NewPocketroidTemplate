package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzaqt<T, B> {
    zzaqt() {
    }

    abstract int zza(T t);

    abstract int zzb(T t);

    abstract B zzc(Object obj);

    abstract T zzd(Object obj);

    abstract T zze(T t, T t2);

    abstract B zzf();

    abstract T zzg(B b);

    abstract void zzh(B b, int i, int i2);

    abstract void zzi(B b, int i, long j);

    abstract void zzj(B b, int i, T t);

    abstract void zzk(B b, int i, zzamk zzamkVar);

    abstract void zzl(B b, int i, long j);

    abstract void zzm(Object obj);

    abstract void zzn(Object obj, B b);

    abstract void zzo(Object obj, T t);

    abstract void zzp(T t, zzarl zzarlVar) throws IOException;

    abstract void zzq(T t, zzarl zzarlVar) throws IOException;

    final boolean zzr(B b, zzapr zzaprVar) throws IOException {
        int iZzd = zzaprVar.zzd();
        int i = iZzd >>> 3;
        int i2 = iZzd & 7;
        if (i2 == 0) {
            zzl(b, i, zzaprVar.zzl());
            return true;
        }
        if (i2 == 1) {
            zzi(b, i, zzaprVar.zzk());
            return true;
        }
        if (i2 == 2) {
            zzk(b, i, zzaprVar.zzp());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzaoe.zza();
            }
            zzh(b, i, zzaprVar.zzf());
            return true;
        }
        B bZzf = zzf();
        int i3 = 4 | (i << 3);
        while (zzaprVar.zzc() != Integer.MAX_VALUE && zzr(bZzf, zzaprVar)) {
        }
        if (i3 != zzaprVar.zzd()) {
            throw zzaoe.zzb();
        }
        zzg(bZzf);
        zzj(b, i, bZzf);
        return true;
    }

    abstract boolean zzs(zzapr zzaprVar);
}
