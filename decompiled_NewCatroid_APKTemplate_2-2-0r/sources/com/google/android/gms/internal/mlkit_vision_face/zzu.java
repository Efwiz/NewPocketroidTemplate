package com.google.android.gms.internal.mlkit_vision_face;

import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzu {
    private static boolean zza;
    private final String zzb;
    private final zzt zzc;
    private zzt zzd;

    /* synthetic */ zzu(String str, zzs zzsVar) {
        zzt zztVar = new zzt(null);
        this.zzc = zztVar;
        this.zzd = zztVar;
        if (!zza) {
            synchronized (zzu.class) {
                if (!zza) {
                    zza = true;
                }
            }
        }
        this.zzb = str;
    }

    private final zzu zze(String str, @NullableDecl Object obj) {
        zzt zztVar = new zzt(null);
        this.zzd.zzc = zztVar;
        this.zzd = zztVar;
        zztVar.zzb = obj;
        str.getClass();
        zztVar.zza = str;
        return this;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.zzb);
        sb.append('{');
        zzt zztVar = this.zzc.zzc;
        String str = "";
        while (zztVar != null) {
            Object obj = zztVar.zzb;
            sb.append(str);
            String str2 = zztVar.zza;
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
            zztVar = zztVar.zzc;
            str = ", ";
        }
        sb.append('}');
        return sb.toString();
    }

    public final zzu zza(String str, @NullableDecl Object obj) {
        zze(str, obj);
        return this;
    }

    public final zzu zzb(String str, boolean z) {
        zze("trackingEnabled", String.valueOf(z));
        return this;
    }

    public final zzu zzc(String str, float f) {
        zze(str, String.valueOf(f));
        return this;
    }

    public final zzu zzd(String str, int i) {
        zze(str, String.valueOf(i));
        return this;
    }
}
