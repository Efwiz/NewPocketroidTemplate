package com.google.android.gms.internal.mlkit_vision_mediapipe;

import io.netty.util.internal.StringUtil;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzca extends zzch {
    private static final zzch zza = new zzca();

    public static zzch zza() {
        return zza;
    }

    private zzca() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzch
    public final int zza(zzcc<?> zzccVar, int i, String str, int i2, int i3, int i4) throws zzce {
        zzbz zzbzVarZza;
        int i5 = i4 + 1;
        char cCharAt = str.charAt(i4);
        zzab zzabVarZza = zzab.zza(str, i3, i4, (cCharAt & StringUtil.SPACE) == 0);
        zzy zzyVarZza = zzy.zza(cCharAt);
        if (zzyVarZza != null) {
            if (!zzabVarZza.zza(zzyVarZza)) {
                throw zzce.zza("invalid format specifier", str, i2, i5);
            }
            zzbzVarZza = zzcb.zza(i, zzyVarZza, zzabVarZza);
        } else if (cCharAt == 't' || cCharAt == 'T') {
            if (!zzabVarZza.zza(160, false)) {
                throw zzce.zza("invalid format specification", str, i2, i5);
            }
            int i6 = i4 + 2;
            if (i6 > str.length()) {
                throw zzce.zza("truncated format specifier", str, i2);
            }
            zzbx zzbxVarZza = zzbx.zza(str.charAt(i5));
            if (zzbxVarZza == null) {
                throw zzce.zza("illegal date/time conversion", str, i5);
            }
            zzbzVarZza = zzbw.zza(zzbxVarZza, zzabVarZza, i);
            i5 = i6;
        } else if (cCharAt == 'h' || cCharAt == 'H') {
            if (!zzabVarZza.zza(160, false)) {
                throw zzce.zza("invalid format specification", str, i2, i5);
            }
            zzbzVarZza = new zzcd(zzabVarZza, i, zzabVarZza);
        } else {
            throw zzce.zza("invalid format specification", str, i2, i5);
        }
        zzccVar.zzb(i2, i5, zzbzVarZza);
        return i5;
    }
}
