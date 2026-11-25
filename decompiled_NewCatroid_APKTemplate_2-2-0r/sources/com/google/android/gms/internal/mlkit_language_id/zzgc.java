package com.google.android.gms.internal.mlkit_language_id;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgc<T> implements zzgp<T> {
    private final zzfz zza;
    private final zzhh<?, ?> zzb;
    private final boolean zzc;
    private final zzee<?> zzd;

    private zzgc(zzhh<?, ?> zzhhVar, zzee<?> zzeeVar, zzfz zzfzVar) {
        this.zzb = zzhhVar;
        this.zzc = zzeeVar.zza(zzfzVar);
        this.zzd = zzeeVar;
        this.zza = zzfzVar;
    }

    static <T> zzgc<T> zza(zzhh<?, ?> zzhhVar, zzee<?> zzeeVar, zzfz zzfzVar) {
        return new zzgc<>(zzhhVar, zzeeVar, zzfzVar);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final boolean zza(T t, T t2) {
        if (!this.zzb.zza(t).equals(this.zzb.zza(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza(t).equals(this.zzd.zza(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final int zza(T t) {
        int iHashCode = this.zzb.zza(t).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zza(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zzb(T t, T t2) {
        zzgr.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzgr.zza(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zza(T t, zzib zzibVar) throws IOException {
        Iterator itZzd = this.zzd.zza(t).zzd();
        while (itZzd.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzd.next();
            zzel zzelVar = (zzel) entry.getKey();
            if (zzelVar.zzc() != zzhy.MESSAGE || zzelVar.zzd() || zzelVar.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzfc) {
                zzibVar.zza(zzelVar.zza(), (Object) ((zzfc) entry).zza().zzc());
            } else {
                zzibVar.zza(zzelVar.zza(), entry.getValue());
            }
        }
        zzhh<?, ?> zzhhVar = this.zzb;
        zzhhVar.zzb(zzhhVar.zza(t), zzibVar);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zzb(T t) {
        this.zzb.zzb(t);
        this.zzd.zzc(t);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final boolean zzc(T t) {
        return this.zzd.zza(t).zzf();
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final int zzd(T t) {
        zzhh<?, ?> zzhhVar = this.zzb;
        int iZzc = zzhhVar.zzc(zzhhVar.zza(t));
        return this.zzc ? iZzc + this.zzd.zza(t).zzg() : iZzc;
    }
}
