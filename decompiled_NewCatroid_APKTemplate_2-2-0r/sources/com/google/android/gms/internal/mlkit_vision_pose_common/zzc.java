package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzc {
    private final String zza;
    private final zzf zzb;
    private zzf zzc;
    private boolean zzd;

    private zzc(String str) {
        zzf zzfVar = new zzf();
        this.zzb = zzfVar;
        this.zzc = zzfVar;
        this.zzd = false;
        this.zza = (String) zzh.zza(str);
    }

    public final zzc zza(String str, @NullableDecl Object obj) {
        return zzb(str, obj);
    }

    public final zzc zza(String str, boolean z) {
        return zzb(str, String.valueOf(z));
    }

    public final zzc zza(String str, int i) {
        return zzb(str, String.valueOf(i));
    }

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder(32).append(this.zza).append('{');
        zzf zzfVar = this.zzb.zzc;
        String str = "";
        while (zzfVar != null) {
            Object obj = zzfVar.zzb;
            sbAppend.append(str);
            if (zzfVar.zza != null) {
                sbAppend.append(zzfVar.zza).append('=');
            }
            if (obj != null && obj.getClass().isArray()) {
                String strDeepToString = Arrays.deepToString(new Object[]{obj});
                sbAppend.append((CharSequence) strDeepToString, 1, strDeepToString.length() - 1);
            } else {
                sbAppend.append(obj);
            }
            zzfVar = zzfVar.zzc;
            str = ", ";
        }
        return sbAppend.append('}').toString();
    }

    private final zzc zzb(String str, @NullableDecl Object obj) {
        zzf zzfVar = new zzf();
        this.zzc.zzc = zzfVar;
        this.zzc = zzfVar;
        zzfVar.zzb = obj;
        zzfVar.zza = (String) zzh.zza(str);
        return this;
    }
}
