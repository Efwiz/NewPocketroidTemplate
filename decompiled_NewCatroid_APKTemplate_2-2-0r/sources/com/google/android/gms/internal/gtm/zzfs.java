package com.google.android.gms.internal.gtm;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.beust.jcommander.Parameters;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.HttpUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.huawei.hms.framework.common.ContainerUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.bytebuddy.description.type.TypeDescription;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfs {
    public static long zza(String str) {
        if (str == null) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    public static zzaw zzb(zzfb zzfbVar, String str) {
        Preconditions.checkNotNull(zzfbVar);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        new HashMap();
        try {
            String strValueOf = String.valueOf(str);
            Map<String, String> map = HttpUtils.parse(new URI(strValueOf.length() != 0 ? TypeDescription.Generic.OfWildcardType.SYMBOL.concat(strValueOf) : new String(TypeDescription.Generic.OfWildcardType.SYMBOL)), "UTF-8");
            zzaw zzawVar = new zzaw();
            zzawVar.zzp(map.get("utm_content"));
            zzawVar.zzu(map.get("utm_medium"));
            zzawVar.zzv(map.get("utm_campaign"));
            zzawVar.zzw(map.get("utm_source"));
            zzawVar.zzt(map.get("utm_term"));
            zzawVar.zzs(map.get("utm_id"));
            zzawVar.zzo(map.get("anid"));
            zzawVar.zzr(map.get("gclid"));
            zzawVar.zzq(map.get("dclid"));
            zzawVar.zzn(map.get(FirebaseAnalytics.Param.ACLID));
            return zzawVar;
        } catch (URISyntaxException e) {
            zzfbVar.zzS("No valid campaign data found", e);
            return null;
        }
    }

    public static String zzc(boolean z) {
        return true != z ? "0" : SchemaSymbols.ATTVAL_TRUE_1;
    }

    public static String zzd(Locale locale) {
        if (locale == null) {
            return null;
        }
        String language = locale.getLanguage();
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(language.toLowerCase(locale));
        if (!TextUtils.isEmpty(locale.getCountry())) {
            sb.append(Parameters.DEFAULT_OPTION_PREFIXES);
            sb.append(locale.getCountry().toLowerCase(locale));
        }
        return sb.toString();
    }

    public static MessageDigest zze(String str) {
        for (int i = 0; i < 2; i++) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                if (messageDigest != null) {
                    return messageDigest;
                }
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        return null;
    }

    public static Map<String, String> zzf(String str) {
        HashMap map = new HashMap();
        for (String str2 : str.split(ContainerUtils.FIELD_DELIMITER)) {
            String[] strArrSplit = str2.split(ContainerUtils.KEY_VALUE_DELIMITER, 3);
            int length = strArrSplit.length;
            if (length > 1) {
                map.put(strArrSplit[0], TextUtils.isEmpty(strArrSplit[1]) ? null : strArrSplit[1]);
                if (length == 3 && !TextUtils.isEmpty(strArrSplit[1]) && !map.containsKey(strArrSplit[1])) {
                    map.put(strArrSplit[1], TextUtils.isEmpty(strArrSplit[2]) ? null : strArrSplit[2]);
                }
            } else if (length == 1 && strArrSplit[0].length() != 0) {
                map.put(strArrSplit[0], null);
            }
        }
        return map;
    }

    public static void zzg(Map<String, String> map, String str, String str2) {
        if (str2 == null || map.containsKey(str)) {
            return;
        }
        map.put(str, str2);
    }

    public static void zzh(Map<String, String> map, String str, Map<String, String> map2) {
        zzg(map, str, map2.get(str));
    }

    public static boolean zzi(Context context, String str, boolean z) {
        try {
            ActivityInfo receiverInfo = context.getPackageManager().getReceiverInfo(new ComponentName(context, str), 0);
            return receiverInfo != null && receiverInfo.enabled && (!z || receiverInfo.exported);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public static boolean zzj(double d, String str) {
        int i;
        if (d > 0.0d && d < 100.0d) {
            if (TextUtils.isEmpty(str)) {
                i = 1;
            } else {
                i = 0;
                for (int length = str.length() - 1; length >= 0; length--) {
                    char cCharAt = str.charAt(length);
                    i = ((i << 6) & MqttVariableByteInteger.FOUR_BYTES_MAX_VALUE) + cCharAt + (cCharAt << 14);
                    int i2 = i >> 21;
                    if ((266338304 & i) != 0) {
                        i ^= i2 & 127;
                    }
                }
            }
            if (i % 10000 >= d * 100.0d) {
                return true;
            }
        }
        return false;
    }
}
