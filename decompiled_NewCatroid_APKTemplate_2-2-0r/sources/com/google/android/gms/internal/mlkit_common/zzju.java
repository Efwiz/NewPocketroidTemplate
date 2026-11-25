package com.google.android.gms.internal.mlkit_common;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzju implements zzji {
    private final zzha zza;
    private zziu zzb = new zziu();

    private zzju(zzha zzhaVar, int i) {
        this.zza = zzhaVar;
        zzkf.zza();
    }

    public static zzji zzf(zzha zzhaVar) {
        return new zzju(zzhaVar, 0);
    }

    public static zzji zzg() {
        return new zzju(new zzha(), 0);
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzji
    public final zzji zza(zzgz zzgzVar) {
        this.zza.zzf(zzgzVar);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzji
    public final zzji zzb(zzhg zzhgVar) {
        this.zza.zzi(zzhgVar);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzji
    public final zzji zzc(zziu zziuVar) {
        this.zzb = zziuVar;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzji
    public final String zzd() {
        zziw zziwVarZzf = this.zza.zzk().zzf();
        return (zziwVarZzf == null || zzae.zzc(zziwVarZzf.zzj())) ? "NA" : (String) Preconditions.checkNotNull(zziwVarZzf.zzj());
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzji
    public final byte[] zze(int i, boolean z) {
        this.zzb.zzf(Boolean.valueOf(i == 0));
        this.zzb.zze(false);
        this.zza.zzj(this.zzb.zzl());
        try {
            zzkf.zza();
            if (i == 0) {
                return new JsonDataEncoderBuilder().configureWith(zzfo.zza).ignoreNullValues(true).build().encode(this.zza.zzk()).getBytes("utf-8");
            }
            zzhc zzhcVarZzk = this.zza.zzk();
            zzbe zzbeVar = new zzbe();
            zzfo.zza.configure(zzbeVar);
            return zzbeVar.zza().zza(zzhcVarZzk);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("Failed to covert logging to UTF-8 byte array", e);
        }
    }
}
