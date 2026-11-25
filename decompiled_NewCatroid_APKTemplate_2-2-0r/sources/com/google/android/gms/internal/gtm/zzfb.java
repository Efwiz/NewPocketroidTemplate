package com.google.android.gms.internal.gtm;

import com.beust.jcommander.Parameters;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfb extends zzbs {
    private static zzfb zza;

    public zzfb(zzbv zzbvVar) {
        super(zzbvVar);
    }

    public static zzfb zza() {
        return zza;
    }

    protected static final String zzf(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf(((Integer) obj).intValue());
        }
        boolean z = obj instanceof Long;
        String str = Parameters.DEFAULT_OPTION_PREFIXES;
        if (!z) {
            if (obj instanceof Boolean) {
                return String.valueOf(obj);
            }
            return obj instanceof Throwable ? obj.getClass().getCanonicalName() : Parameters.DEFAULT_OPTION_PREFIXES;
        }
        Long l = (Long) obj;
        if (Math.abs(l.longValue()) < 100) {
            return String.valueOf(obj);
        }
        if (String.valueOf(obj).charAt(0) != '-') {
            str = "";
        }
        String strValueOf = String.valueOf(Math.abs(l.longValue()));
        return str + Math.round(Math.pow(10.0d, strValueOf.length() - 1)) + "..." + str + Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d);
    }

    public final void zzb(zzex zzexVar, String str) {
        zzS(str.length() != 0 ? "Discarding hit. ".concat(str) : new String("Discarding hit. "), zzexVar != null ? zzexVar.toString() : "no hit data");
    }

    public final void zzc(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
        }
        zzS(str.length() != 0 ? "Discarding hit. ".concat(str) : new String("Discarding hit. "), sb.toString());
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
        synchronized (zzfb.class) {
            zza = this;
        }
    }

    public final synchronized void zze(int i, String str, Object obj, Object obj2, Object obj3) {
        char c;
        Preconditions.checkNotNull(str);
        if (zzw().zzb()) {
            zzw();
            c = Util.C_CHAR;
        } else {
            zzw();
            c = 'c';
        }
        char cCharAt = "01VDIWEA?".charAt(i);
        String str2 = zzbt.zza;
        String strZzD = zzD(str, zzf(obj), zzf(obj2), zzf(obj3));
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 4 + String.valueOf(strZzD).length());
        sb.append("3");
        sb.append(cCharAt);
        sb.append(c);
        sb.append(str2);
        sb.append(":");
        sb.append(strZzD);
        String string = sb.toString();
        if (string.length() > 1024) {
            string = string.substring(0, 1024);
        }
        zzfh zzfhVarZzp = zzt().zzp();
        if (zzfhVarZzp != null) {
            zzfhVarZzp.zze().zzc(string);
        }
    }
}
