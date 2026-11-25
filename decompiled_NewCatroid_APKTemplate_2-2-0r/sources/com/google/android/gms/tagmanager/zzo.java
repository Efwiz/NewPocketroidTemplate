package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzo extends zzft {
    static final String zza;
    private static final String zzb;
    private static final String zzc;
    private static final String zzd;
    private static final String zze;
    private static final Set<String> zzf;
    private final zzn zzg;
    private final Context zzh;

    static {
        String string = com.google.android.gms.internal.gtm.zza.ARBITRARY_PIXEL.toString();
        zzb = string;
        zzc = com.google.android.gms.internal.gtm.zzb.URL.toString();
        zzd = com.google.android.gms.internal.gtm.zzb.ADDITIONAL_PARAMS.toString();
        zze = com.google.android.gms.internal.gtm.zzb.UNREPEATABLE.toString();
        StringBuilder sb = new StringBuilder(String.valueOf(string).length() + 17);
        sb.append("gtm_");
        sb.append(string);
        sb.append("_unrepeatable");
        zza = sb.toString();
        zzf = new HashSet();
    }

    public zzo(Context context) {
        zzm zzmVar = new zzm(context);
        super(zzb, zzc);
        this.zzg = zzmVar;
        this.zzh = context;
    }

    private final synchronized boolean zzd(String str) {
        Set<String> set = zzf;
        if (set.contains(str)) {
            return true;
        }
        if (!this.zzh.getSharedPreferences(zza, 0).contains(str)) {
            return false;
        }
        set.add(str);
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzft
    public final void zzc(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String str = zze;
        String strZzn = map.get(str) != null ? zzfv.zzn(zzfv.zzl(map.get(str))) : null;
        if (strZzn == null || !zzd(strZzn)) {
            Uri.Builder builderBuildUpon = Uri.parse(zzfv.zzn(zzfv.zzl(map.get(zzc)))).buildUpon();
            com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzd);
            if (zzakVar != null) {
                Object objZzl = zzfv.zzl(zzakVar);
                if (!(objZzl instanceof List)) {
                    String strValueOf = String.valueOf(builderBuildUpon.build().toString());
                    zzdh.zza(strValueOf.length() != 0 ? "ArbitraryPixel: additional params not a list: not sending partial hit: ".concat(strValueOf) : new String("ArbitraryPixel: additional params not a list: not sending partial hit: "));
                    return;
                }
                for (Object obj : (List) objZzl) {
                    if (!(obj instanceof Map)) {
                        String strValueOf2 = String.valueOf(builderBuildUpon.build().toString());
                        zzdh.zza(strValueOf2.length() != 0 ? "ArbitraryPixel: additional params contains non-map: not sending partial hit: ".concat(strValueOf2) : new String("ArbitraryPixel: additional params contains non-map: not sending partial hit: "));
                        return;
                    } else {
                        for (Map.Entry entry : ((Map) obj).entrySet()) {
                            builderBuildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                        }
                    }
                }
            }
            String string = builderBuildUpon.build().toString();
            zzbh.zzb(((zzm) this.zzg).zza).zza(string);
            String strValueOf3 = String.valueOf(string);
            zzdh.zzb.zzd(strValueOf3.length() != 0 ? "ArbitraryPixel: url = ".concat(strValueOf3) : new String("ArbitraryPixel: url = "));
            if (strZzn != null) {
                synchronized (zzo.class) {
                    zzf.add(strZzn);
                    zzfg.zza(this.zzh, zza, strZzn, "true");
                }
            }
        }
    }
}
