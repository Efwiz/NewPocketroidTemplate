package com.google.android.gms.tagmanager;

import android.util.Base64;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbq extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.ENCODE.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.NO_PADDING.toString();
    private static final String zzd = com.google.android.gms.internal.gtm.zzb.INPUT_FORMAT.toString();
    private static final String zze = com.google.android.gms.internal.gtm.zzb.OUTPUT_FORMAT.toString();

    public zzbq() {
        super(zza, zzb);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        byte[] bArrDecode;
        String strEncodeToString;
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzb);
        if (zzakVar == null || zzakVar == zzfv.zzb()) {
            return zzfv.zzb();
        }
        String strZzn = zzfv.zzn(zzfv.zzl(zzakVar));
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzd);
        String strZzn2 = zzakVar2 == null ? "text" : zzfv.zzn(zzfv.zzl(zzakVar2));
        com.google.android.gms.internal.gtm.zzak zzakVar3 = map.get(zze);
        String strZzn3 = zzakVar3 == null ? "base16" : zzfv.zzn(zzfv.zzl(zzakVar3));
        com.google.android.gms.internal.gtm.zzak zzakVar4 = map.get(zzc);
        int i = 2;
        if (zzakVar4 != null && zzfv.zzg(zzfv.zzl(zzakVar4)).booleanValue()) {
            i = 3;
        }
        try {
            if ("text".equals(strZzn2)) {
                bArrDecode = strZzn.getBytes();
            } else if ("base16".equals(strZzn2)) {
                bArrDecode = zzp.zzb(strZzn);
            } else if ("base64".equals(strZzn2)) {
                bArrDecode = Base64.decode(strZzn, i);
            } else {
                if (!"base64url".equals(strZzn2)) {
                    String strValueOf = String.valueOf(strZzn2);
                    zzdh.zza(strValueOf.length() != 0 ? "Encode: unknown input format: ".concat(strValueOf) : new String("Encode: unknown input format: "));
                    return zzfv.zzb();
                }
                bArrDecode = Base64.decode(strZzn, i | 8);
            }
            if ("base16".equals(strZzn3)) {
                strEncodeToString = zzp.zza(bArrDecode);
            } else if ("base64".equals(strZzn3)) {
                strEncodeToString = Base64.encodeToString(bArrDecode, i);
            } else {
                if (!"base64url".equals(strZzn3)) {
                    String strValueOf2 = String.valueOf(strZzn3);
                    zzdh.zza(strValueOf2.length() != 0 ? "Encode: unknown output format: ".concat(strValueOf2) : new String("Encode: unknown output format: "));
                    return zzfv.zzb();
                }
                strEncodeToString = Base64.encodeToString(bArrDecode, i | 8);
            }
            return zzfv.zzc(strEncodeToString);
        } catch (IllegalArgumentException unused) {
            zzdh.zza("Encode: invalid input:");
            return zzfv.zzb();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
