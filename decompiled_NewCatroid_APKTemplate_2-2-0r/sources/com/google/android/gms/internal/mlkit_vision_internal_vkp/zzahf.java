package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzahf implements zzagv {
    private final zzads zza;
    private zzafg zzb = new zzafg();

    private zzahf(zzads zzadsVar, int i) {
        this.zza = zzadsVar;
        zzahs.zza();
    }

    public static zzagv zzf(zzads zzadsVar) {
        return new zzahf(zzadsVar, 0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final int zza() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final zzagv zzb(zzadr zzadrVar) {
        this.zza.zzd(zzadrVar);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final zzagv zzc(zzafg zzafgVar) {
        this.zzb = zzafgVar;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final String zzd() {
        zzafi zzafiVarZzd = this.zza.zzg().zzd();
        return (zzafiVarZzd == null || zzkq.zzc(zzafiVarZzd.zzj())) ? "NA" : (String) Preconditions.checkNotNull(zzafiVarZzd.zzj());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagv
    public final byte[] zze(int i, boolean z) {
        this.zzb.zzf(Boolean.valueOf(i == 0));
        this.zzb.zze(false);
        this.zza.zzf(this.zzb.zzl());
        try {
            zzahs.zza();
            if (i == 0) {
                return new JsonDataEncoderBuilder().configureWith(zzaco.zza).ignoreNullValues(true).build().encode(this.zza.zzg()).getBytes("utf-8");
            }
            zzadu zzaduVarZzg = this.zza.zzg();
            zzmh zzmhVar = new zzmh();
            zzaco.zza.configure(zzmhVar);
            return zzmhVar.zza().zza(zzaduVarZzg);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("Failed to covert logging to UTF-8 byte array", e);
        }
    }
}
