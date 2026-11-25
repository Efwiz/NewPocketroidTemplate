package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznz {
    private final zzkq zza;
    private zzmt zzb = new zzmt();
    private final int zzc;

    private zznz(zzkq zzkqVar, int i) {
        this.zza = zzkqVar;
        zzpd.zza();
        this.zzc = i;
    }

    public static zznz zzd(zzkq zzkqVar) {
        return new zznz(zzkqVar, 0);
    }

    public static zznz zze(zzkq zzkqVar, int i) {
        return new zznz(zzkqVar, 1);
    }

    public final int zza() {
        return this.zzc;
    }

    public final String zzb() {
        zzmv zzmvVarZzh = this.zza.zzl().zzh();
        return (zzmvVarZzh == null || zzi.zzb(zzmvVarZzh.zzj())) ? "NA" : (String) Preconditions.checkNotNull(zzmvVarZzh.zzj());
    }

    public final byte[] zzc(int i, boolean z) {
        this.zzb.zzf(Boolean.valueOf(i == 0));
        this.zzb.zze(false);
        this.zza.zzk(this.zzb.zzl());
        try {
            zzpd.zza();
            if (i == 0) {
                return new JsonDataEncoderBuilder().configureWith(zzjd.zza).ignoreNullValues(true).build().encode(this.zza.zzl()).getBytes("utf-8");
            }
            zzks zzksVarZzl = this.zza.zzl();
            zzcf zzcfVar = new zzcf();
            zzjd.zza.configure(zzcfVar);
            return zzcfVar.zza().zza(zzksVarZzl);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("Failed to covert logging to UTF-8 byte array", e);
        }
    }

    public final zznz zzf(zzkp zzkpVar) {
        this.zza.zzf(zzkpVar);
        return this;
    }

    public final zznz zzg(zzmt zzmtVar) {
        this.zzb = zzmtVar;
        return this;
    }
}
