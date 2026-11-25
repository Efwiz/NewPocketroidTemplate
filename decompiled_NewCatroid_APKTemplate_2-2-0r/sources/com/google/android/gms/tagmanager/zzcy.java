package com.google.android.gms.tagmanager;

import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcy extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.JOINER.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.ITEM_SEPARATOR.toString();
    private static final String zzd = com.google.android.gms.internal.gtm.zzb.KEY_VALUE_SEPARATOR.toString();
    private static final String zze = com.google.android.gms.internal.gtm.zzb.ESCAPE.toString();

    public zzcy() {
        super(zza, zzb);
    }

    private static final void zzc(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    private static final String zzd(String str, int i, Set<Character> set) {
        int i2 = i - 1;
        if (i2 == 1) {
            try {
                return zzfy.zza(str);
            } catch (UnsupportedEncodingException e) {
                zzdh.zzb("Joiner: unsupported encoding", e);
                return str;
            }
        }
        if (i2 != 2) {
            return str;
        }
        String strReplace = str.replace("\\", "\\\\");
        Iterator<Character> it = set.iterator();
        while (it.hasNext()) {
            String string = it.next().toString();
            String strValueOf = String.valueOf(string);
            strReplace = strReplace.replace(string, strValueOf.length() != 0 ? "\\".concat(strValueOf) : new String("\\"));
        }
        return strReplace;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        int i;
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzb);
        if (zzakVar == null) {
            return zzfv.zzb();
        }
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzc);
        String strZzn = zzakVar2 != null ? zzfv.zzn(zzfv.zzl(zzakVar2)) : "";
        com.google.android.gms.internal.gtm.zzak zzakVar3 = map.get(zzd);
        String strZzn2 = zzakVar3 != null ? zzfv.zzn(zzfv.zzl(zzakVar3)) : ContainerUtils.KEY_VALUE_DELIMITER;
        com.google.android.gms.internal.gtm.zzak zzakVar4 = map.get(zze);
        HashSet hashSet = null;
        boolean z = true;
        if (zzakVar4 != null) {
            String strZzn3 = zzfv.zzn(zzfv.zzl(zzakVar4));
            if ("url".equals(strZzn3)) {
                i = 2;
            } else {
                if (!"backslash".equals(strZzn3)) {
                    String strValueOf = String.valueOf(strZzn3);
                    zzdh.zza(strValueOf.length() != 0 ? "Joiner: unsupported escape type: ".concat(strValueOf) : new String("Joiner: unsupported escape type: "));
                    return zzfv.zzb();
                }
                hashSet = new HashSet();
                zzc(hashSet, strZzn);
                zzc(hashSet, strZzn2);
                hashSet.remove('\\');
                i = 3;
            }
        } else {
            i = 1;
        }
        StringBuilder sb = new StringBuilder();
        int iZzO = zzakVar.zzO();
        if (iZzO == 2) {
            for (com.google.android.gms.internal.gtm.zzak zzakVar5 : zzakVar.zzr()) {
                if (!z) {
                    sb.append(strZzn);
                }
                sb.append(zzd(zzfv.zzn(zzfv.zzl(zzakVar5)), i, hashSet));
                z = false;
            }
        } else if (iZzO != 3) {
            sb.append(zzd(zzfv.zzn(zzfv.zzl(zzakVar)), i, hashSet));
        } else {
            for (int i2 = 0; i2 < zzakVar.zzc(); i2++) {
                if (i2 > 0) {
                    sb.append(strZzn);
                }
                String strZzn4 = zzfv.zzn(zzfv.zzl(zzakVar.zzk(i2)));
                String strZzn5 = zzfv.zzn(zzfv.zzl(zzakVar.zzl(i2)));
                sb.append(zzd(strZzn4, i, hashSet));
                sb.append(strZzn2);
                sb.append(zzd(strZzn5, i, hashSet));
            }
        }
        return zzfv.zzc(sb.toString());
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
