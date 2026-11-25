package com.google.android.gms.tagmanager;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzed extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.REGEX_GROUP.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.ARG1.toString();
    private static final String zzd = com.google.android.gms.internal.gtm.zzb.IGNORE_CASE.toString();
    private static final String zze = com.google.android.gms.internal.gtm.zzb.GROUP.toString();

    public zzed() {
        super(zza, zzb, zzc);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzb);
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzc);
        if (zzakVar == null || zzakVar == zzfv.zzb() || zzakVar2 == null || zzakVar2 == zzfv.zzb()) {
            return zzfv.zzb();
        }
        int iIntValue = 1;
        int i = true != zzfv.zzg(zzfv.zzl(map.get(zzd))).booleanValue() ? 64 : 66;
        com.google.android.gms.internal.gtm.zzak zzakVar3 = map.get(zze);
        if (zzakVar3 != null) {
            Long lZzk = zzfv.zzk(zzfv.zzl(zzakVar3));
            if (lZzk == zzfv.zzj()) {
                return zzfv.zzb();
            }
            iIntValue = lZzk.intValue();
            if (iIntValue < 0) {
                return zzfv.zzb();
            }
        }
        try {
            Matcher matcher = Pattern.compile(zzfv.zzn(zzfv.zzl(zzakVar2)), i).matcher(zzfv.zzn(zzfv.zzl(zzakVar)));
            String strGroup = null;
            if (matcher.find() && matcher.groupCount() >= iIntValue) {
                strGroup = matcher.group(iIntValue);
            }
            return strGroup == null ? zzfv.zzb() : zzfv.zzc(strGroup);
        } catch (PatternSyntaxException unused) {
            return zzfv.zzb();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
