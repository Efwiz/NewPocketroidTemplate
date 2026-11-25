package com.google.android.gms.internal.mlkit_vision_text;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkp {
    private final zziq zza;
    private zzjx zzb = new zzjx();

    private zzkp(zziq zziqVar) {
        this.zza = zziqVar;
    }

    public static zzkp zzc(zziq zziqVar) {
        return new zzkp(zziqVar);
    }

    public final byte[] zza(int i, boolean z) {
        this.zzb.zzi(Boolean.valueOf(i == 0));
        this.zzb.zzg(Boolean.valueOf(z));
        this.zza.zza(this.zzb.zzk());
        try {
            zzlb.zza();
            if (i == 0) {
                return new JsonDataEncoderBuilder().configureWith(zzhh.zza).ignoreNullValues(true).build().encode(this.zza.zzf()).getBytes("utf-8");
            }
            zzir zzirVarZzf = this.zza.zzf();
            zzdg zzdgVar = new zzdg();
            zzhh.zza.configure(zzdgVar);
            return zzdgVar.zza().zza(zzirVarZzf);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("Failed to covert logging to UTF-8 byte array", e);
        }
    }

    public final String zzb() {
        zzjy zzjyVarZza = this.zza.zzf().zza();
        return (zzjyVarZza == null || zzab.zza(zzjyVarZza.zzd())) ? "NA" : (String) Preconditions.checkNotNull(zzjyVarZza.zzd());
    }

    public final zzkp zzd(zzjx zzjxVar) {
        this.zzb = zzjxVar;
        return this;
    }

    public final zzkp zze(zzio zzioVar) {
        this.zza.zzb(zzioVar);
        return this;
    }
}
