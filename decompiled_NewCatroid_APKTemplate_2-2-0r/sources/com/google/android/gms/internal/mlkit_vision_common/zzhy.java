package com.google.android.gms.internal.mlkit_vision_common;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhy {
    private final zzft zza;
    private zzhe zzb = new zzhe();

    private zzhy(zzft zzftVar, int i) {
        this.zza = zzftVar;
        zzik.zza();
    }

    public static zzhy zzc(zzft zzftVar) {
        return new zzhy(zzftVar, 0);
    }

    public final String zza() {
        zzhg zzhgVarZzc = this.zza.zzf().zzc();
        return (zzhgVarZzc == null || zzg.zzb(zzhgVarZzc.zzj())) ? "NA" : (String) Preconditions.checkNotNull(zzhgVarZzc.zzj());
    }

    public final byte[] zzb(int i, boolean z) {
        this.zzb.zzf(Boolean.valueOf(i == 0));
        this.zzb.zze(false);
        this.zza.zze(this.zzb.zzl());
        try {
            zzik.zza();
            if (i == 0) {
                return new JsonDataEncoderBuilder().configureWith(zzel.zza).ignoreNullValues(true).build().encode(this.zza.zzf()).getBytes("utf-8");
            }
            zzfv zzfvVarZzf = this.zza.zzf();
            zzab zzabVar = new zzab();
            zzel.zza.configure(zzabVar);
            return zzabVar.zza().zza(zzfvVarZzf);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("Failed to covert logging to UTF-8 byte array", e);
        }
    }

    public final zzhy zzd(zzfs zzfsVar) {
        this.zza.zzc(zzfsVar);
        return this;
    }

    public final zzhy zze(zzhe zzheVar) {
        this.zzb = zzheVar;
        return this;
    }
}
