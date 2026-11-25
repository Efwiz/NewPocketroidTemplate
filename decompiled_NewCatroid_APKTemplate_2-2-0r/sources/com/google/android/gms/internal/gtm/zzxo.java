package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzxo<T, B> {
    zzxo() {
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

    abstract void zzk(B b, int i, zztd zztdVar);

    abstract void zzl(B b, int i, long j);

    abstract void zzm(Object obj);

    abstract void zzn(Object obj, B b);

    abstract void zzo(Object obj, T t);

    final boolean zzp(B b, zzww zzwwVar) throws IOException {
        int iZzd = zzwwVar.zzd();
        int i = iZzd >>> 3;
        int i2 = iZzd & 7;
        if (i2 == 0) {
            zzl(b, i, zzwwVar.zzl());
            return true;
        }
        if (i2 == 1) {
            zzi(b, i, zzwwVar.zzk());
            return true;
        }
        if (i2 == 2) {
            zzk(b, i, zzwwVar.zzq());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzvk.zza();
            }
            zzh(b, i, zzwwVar.zzf());
            return true;
        }
        B bZzf = zzf();
        int i3 = 4 | (i << 3);
        while (zzwwVar.zzc() != Integer.MAX_VALUE && zzp(bZzf, zzwwVar)) {
        }
        if (i3 != zzwwVar.zzd()) {
            throw zzvk.zzb();
        }
        zzg(bZzf);
        zzj(b, i, bZzf);
        return true;
    }

    abstract boolean zzq(zzww zzwwVar);

    abstract void zzr(T t, zztp zztpVar) throws IOException;

    abstract void zzs(T t, zztp zztpVar) throws IOException;
}
