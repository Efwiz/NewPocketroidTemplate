package com.google.android.gms.internal.mlkit_vision_face;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzks {
    private final zzis zza;
    private zzjz zzb = new zzjz();

    private zzks(zzis zzisVar) {
        this.zza = zzisVar;
    }

    public static zzks zzc(zzis zzisVar) {
        return new zzks(zzisVar);
    }

    public final byte[] zza(int i, boolean z) {
        this.zzb.zzi(Boolean.valueOf(i == 0));
        this.zzb.zzg(Boolean.valueOf(z));
        this.zza.zza(this.zzb.zzk());
        try {
            zzle.zza();
            if (i == 0) {
                return new JsonDataEncoderBuilder().configureWith(zzhc.zza).ignoreNullValues(true).build().encode(this.zza.zzf()).getBytes("utf-8");
            }
            zzit zzitVarZzf = this.zza.zzf();
            zzda zzdaVar = new zzda();
            zzhc.zza.configure(zzdaVar);
            return zzdaVar.zza().zza(zzitVarZzf);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("Failed to covert logging to UTF-8 byte array", e);
        }
    }

    public final String zzb() {
        zzka zzkaVarZza = this.zza.zzf().zza();
        return (zzkaVarZza == null || zzab.zza(zzkaVarZza.zzd())) ? "NA" : (String) Preconditions.checkNotNull(zzkaVarZza.zzd());
    }

    public final zzks zzd(zzjz zzjzVar) {
        this.zzb = zzjzVar;
        return this;
    }

    public final zzks zze(zziq zziqVar) {
        this.zza.zzb(zziqVar);
        return this;
    }
}
