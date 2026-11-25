package com.google.android.gms.internal.mlkit_language_id;

import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzf {
    private final String zza;
    private final zze zzb;
    private zze zzc;
    private boolean zzd;

    private zzf(String str) {
        zze zzeVar = new zze();
        this.zzb = zzeVar;
        this.zzc = zzeVar;
        this.zzd = false;
        this.zza = (String) zzg.zza(str);
    }

    public final zzf zza(String str, @NullableDecl Object obj) {
        return zzb(str, obj);
    }

    public final zzf zza(String str, float f) {
        return zzb(str, String.valueOf(f));
    }

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder(32).append(this.zza).append('{');
        zze zzeVar = this.zzb.zzc;
        String str = "";
        while (zzeVar != null) {
            Object obj = zzeVar.zzb;
            sbAppend.append(str);
            if (zzeVar.zza != null) {
                sbAppend.append(zzeVar.zza).append('=');
            }
            if (obj != null && obj.getClass().isArray()) {
                String strDeepToString = Arrays.deepToString(new Object[]{obj});
                sbAppend.append((CharSequence) strDeepToString, 1, strDeepToString.length() - 1);
            } else {
                sbAppend.append(obj);
            }
            zzeVar = zzeVar.zzc;
            str = ", ";
        }
        return sbAppend.append('}').toString();
    }

    private final zzf zzb(String str, @NullableDecl Object obj) {
        zze zzeVar = new zze();
        this.zzc.zzc = zzeVar;
        this.zzc = zzeVar;
        zzeVar.zzb = obj;
        zzeVar.zza = (String) zzg.zza(str);
        return this;
    }
}
