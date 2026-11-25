package com.google.android.gms.internal.mlkit_common;

import java.util.Arrays;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzx {
    private final String zza;
    private final zzv zzb;
    private zzv zzc;

    /* synthetic */ zzx(String str, zzw zzwVar) {
        zzv zzvVar = new zzv(null);
        this.zzb = zzvVar;
        this.zzc = zzvVar;
        str.getClass();
        this.zza = str;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.zza);
        sb.append('{');
        zzv zzvVar = this.zzb.zzc;
        String str = "";
        while (zzvVar != null) {
            Object obj = zzvVar.zzb;
            sb.append(str);
            String str2 = zzvVar.zza;
            if (str2 != null) {
                sb.append(str2);
                sb.append('=');
            }
            if (obj == null || !obj.getClass().isArray()) {
                sb.append(obj);
            } else {
                String strDeepToString = Arrays.deepToString(new Object[]{obj});
                sb.append((CharSequence) strDeepToString, 1, strDeepToString.length() - 1);
            }
            zzvVar = zzvVar.zzc;
            str = ", ";
        }
        sb.append('}');
        return sb.toString();
    }

    public final zzx zza(String str, @CheckForNull Object obj) {
        zzv zzvVar = new zzv(null);
        this.zzc.zzc = zzvVar;
        this.zzc = zzvVar;
        zzvVar.zzb = obj;
        zzvVar.zza = str;
        return this;
    }

    public final zzx zzb(String str, boolean z) {
        String strValueOf = String.valueOf(z);
        zzt zztVar = new zzt(null);
        this.zzc.zzc = zztVar;
        this.zzc = zztVar;
        zztVar.zzb = strValueOf;
        zztVar.zza = "isManifestFile";
        return this;
    }
}
