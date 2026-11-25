package com.google.android.gms.tagmanager;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzar extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.FUNCTION_CALL.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.FUNCTION_CALL_NAME.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.ADDITIONAL_PARAMS.toString();
    private final zzaq zzd;

    public zzar(zzaq zzaqVar) {
        super(zza, zzb);
        this.zzd = zzaqVar;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String strZzn = zzfv.zzn(zzfv.zzl(map.get(zzb)));
        HashMap map2 = new HashMap();
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzc);
        if (zzakVar != null) {
            Object objZzl = zzfv.zzl(zzakVar);
            if (!(objZzl instanceof Map)) {
                zzdh.zzc("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return zzfv.zzb();
            }
            for (Map.Entry entry : ((Map) objZzl).entrySet()) {
                map2.put(entry.getKey().toString(), entry.getValue());
            }
        }
        try {
            return zzfv.zzc(this.zzd.zza(strZzn, map2));
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(strZzn).length() + 34 + String.valueOf(message).length());
            sb.append("Custom macro/tag ");
            sb.append(strZzn);
            sb.append(" threw exception ");
            sb.append(message);
            zzdh.zzc(sb.toString());
            return zzfv.zzb();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
