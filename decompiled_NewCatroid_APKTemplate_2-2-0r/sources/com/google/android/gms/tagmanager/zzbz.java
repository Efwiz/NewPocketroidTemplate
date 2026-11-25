package com.google.android.gms.tagmanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbz extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.HASH.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.ALGORITHM.toString();
    private static final String zzd = com.google.android.gms.internal.gtm.zzb.INPUT_FORMAT.toString();

    public zzbz() {
        super(zza, zzb);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        byte[] bArrZzb;
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzb);
        if (zzakVar == null || zzakVar == zzfv.zzb()) {
            return zzfv.zzb();
        }
        String strZzn = zzfv.zzn(zzfv.zzl(zzakVar));
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzc);
        String strZzn2 = zzakVar2 == null ? "MD5" : zzfv.zzn(zzfv.zzl(zzakVar2));
        com.google.android.gms.internal.gtm.zzak zzakVar3 = map.get(zzd);
        String strZzn3 = zzakVar3 == null ? "text" : zzfv.zzn(zzfv.zzl(zzakVar3));
        if ("text".equals(strZzn3)) {
            bArrZzb = strZzn.getBytes();
        } else {
            if (!"base16".equals(strZzn3)) {
                String strValueOf = String.valueOf(strZzn3);
                zzdh.zza(strValueOf.length() != 0 ? "Hash: unknown input format: ".concat(strValueOf) : new String("Hash: unknown input format: "));
                return zzfv.zzb();
            }
            bArrZzb = zzp.zzb(strZzn);
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(strZzn2);
            messageDigest.update(bArrZzb);
            return zzfv.zzc(zzp.zza(messageDigest.digest()));
        } catch (NoSuchAlgorithmException unused) {
            String strValueOf2 = String.valueOf(strZzn2);
            zzdh.zza(strValueOf2.length() != 0 ? "Hash: unknown algorithm: ".concat(strValueOf2) : new String("Hash: unknown algorithm: "));
            return zzfv.zzb();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
