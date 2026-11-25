package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfv {
    private static final Long zza = new Long(0);
    private static final Double zzb = new Double(0.0d);
    private static final zzfu zzc = zzfu.zzd(0);
    private static final String zzd;
    private static final Boolean zze;
    private static final List<Object> zzf;
    private static final Map<Object, Object> zzg;
    private static final com.google.android.gms.internal.gtm.zzak zzh;

    static {
        String str = new String("");
        zzd = str;
        zze = new Boolean(false);
        zzf = new ArrayList(0);
        zzg = new HashMap();
        zzh = zzc(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static com.google.android.gms.internal.gtm.zzak zza(String str) {
        com.google.android.gms.internal.gtm.zzal zzalVarZzg = com.google.android.gms.internal.gtm.zzak.zzg();
        zzalVarZzg.zzt(1);
        zzalVarZzg.zzt(5);
        zzalVarZzg.zzp(str);
        zzalVarZzg.zzo(false);
        return (com.google.android.gms.internal.gtm.zzak) zzalVarZzg.zzC();
    }

    public static com.google.android.gms.internal.gtm.zzak zzb() {
        return zzh;
    }

    public static com.google.android.gms.internal.gtm.zzak zzc(Object obj) {
        com.google.android.gms.internal.gtm.zzal zzalVarZzg = com.google.android.gms.internal.gtm.zzak.zzg();
        zzalVarZzg.zzt(1);
        if (obj instanceof com.google.android.gms.internal.gtm.zzak) {
            return (com.google.android.gms.internal.gtm.zzak) obj;
        }
        boolean z = false;
        if (obj instanceof String) {
            zzalVarZzg.zzt(1);
            zzalVarZzg.zzs((String) obj);
        } else if (obj instanceof List) {
            zzalVarZzg.zzt(2);
            List list = (List) obj;
            ArrayList arrayList = new ArrayList(list.size());
            Iterator it = list.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                com.google.android.gms.internal.gtm.zzak zzakVarZzc = zzc(it.next());
                com.google.android.gms.internal.gtm.zzak zzakVar = zzh;
                if (zzakVarZzc == zzakVar) {
                    return zzakVar;
                }
                z2 = z2 || zzakVarZzc.zzN();
                arrayList.add(zzakVarZzc);
            }
            zzalVarZzg.zzj();
            zzalVarZzg.zzb(arrayList);
            z = z2;
        } else if (obj instanceof Map) {
            zzalVarZzg.zzt(3);
            Set<Map.Entry> setEntrySet = ((Map) obj).entrySet();
            ArrayList arrayList2 = new ArrayList(setEntrySet.size());
            ArrayList arrayList3 = new ArrayList(setEntrySet.size());
            boolean z3 = false;
            for (Map.Entry entry : setEntrySet) {
                com.google.android.gms.internal.gtm.zzak zzakVarZzc2 = zzc(entry.getKey());
                com.google.android.gms.internal.gtm.zzak zzakVarZzc3 = zzc(entry.getValue());
                com.google.android.gms.internal.gtm.zzak zzakVar2 = zzh;
                if (zzakVarZzc2 == zzakVar2 || zzakVarZzc3 == zzakVar2) {
                    return zzakVar2;
                }
                z3 = z3 || zzakVarZzc2.zzN() || zzakVarZzc3.zzN();
                arrayList2.add(zzakVarZzc2);
                arrayList3.add(zzakVarZzc3);
            }
            zzalVarZzg.zzk();
            zzalVarZzg.zzc(arrayList2);
            zzalVarZzg.zzl();
            zzalVarZzg.zzd(arrayList3);
            z = z3;
        } else if (zzr(obj)) {
            zzalVarZzg.zzt(1);
            zzalVarZzg.zzs(obj.toString());
        } else if (zzs(obj)) {
            zzalVarZzg.zzt(6);
            zzalVarZzg.zzq(zzp(obj));
        } else {
            if (!(obj instanceof Boolean)) {
                String strValueOf = String.valueOf(obj == null ? "null" : obj.getClass().toString());
                zzdh.zza(strValueOf.length() != 0 ? "Converting to Value from unknown object type: ".concat(strValueOf) : new String("Converting to Value from unknown object type: "));
                return zzh;
            }
            zzalVarZzg.zzt(8);
            zzalVarZzg.zzn(((Boolean) obj).booleanValue());
        }
        zzalVarZzg.zzo(z);
        return (com.google.android.gms.internal.gtm.zzak) zzalVarZzg.zzC();
    }

    public static zzfu zzd() {
        return zzc;
    }

    public static zzfu zze(Object obj) {
        if (obj instanceof zzfu) {
            return (zzfu) obj;
        }
        if (zzs(obj)) {
            return zzfu.zzd(zzp(obj));
        }
        return zzr(obj) ? zzfu.zzc(Double.valueOf(zzo(obj))) : zzq(zzn(obj));
    }

    public static Boolean zzf() {
        return zze;
    }

    public static Boolean zzg(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        String strZzn = zzn(obj);
        if ("true".equalsIgnoreCase(strZzn)) {
            return Boolean.TRUE;
        }
        return "false".equalsIgnoreCase(strZzn) ? Boolean.FALSE : zze;
    }

    public static Double zzh() {
        return zzb;
    }

    public static Double zzi(Object obj) {
        if (zzr(obj)) {
            return Double.valueOf(zzo(obj));
        }
        zzfu zzfuVarZzq = zzq(zzn(obj));
        return zzfuVarZzq == zzc ? zzb : Double.valueOf(zzfuVarZzq.doubleValue());
    }

    public static Long zzj() {
        return zza;
    }

    public static Long zzk(Object obj) {
        if (zzs(obj)) {
            return Long.valueOf(zzp(obj));
        }
        zzfu zzfuVarZzq = zzq(zzn(obj));
        return zzfuVarZzq == zzc ? zza : Long.valueOf(zzfuVarZzq.zzb());
    }

    public static Object zzl(com.google.android.gms.internal.gtm.zzak zzakVar) {
        if (zzakVar == null) {
            return null;
        }
        switch (zzakVar.zzO()) {
            case 1:
                return zzakVar.zzp();
            case 2:
                ArrayList arrayList = new ArrayList(zzakVar.zza());
                Iterator<com.google.android.gms.internal.gtm.zzak> it = zzakVar.zzr().iterator();
                while (it.hasNext()) {
                    Object objZzl = zzl(it.next());
                    if (objZzl == null) {
                        return null;
                    }
                    arrayList.add(objZzl);
                }
                return arrayList;
            case 3:
                if (zzakVar.zzc() != zzakVar.zzd()) {
                    String strValueOf = String.valueOf(zzakVar.toString());
                    zzdh.zza(strValueOf.length() != 0 ? "Converting an invalid value to object: ".concat(strValueOf) : new String("Converting an invalid value to object: "));
                    return null;
                }
                HashMap map = new HashMap(zzakVar.zzd());
                for (int i = 0; i < zzakVar.zzc(); i++) {
                    Object objZzl2 = zzl(zzakVar.zzk(i));
                    Object objZzl3 = zzl(zzakVar.zzl(i));
                    if (objZzl2 == null || objZzl3 == null) {
                        return null;
                    }
                    map.put(objZzl2, objZzl3);
                }
                return map;
            case 4:
                zzdh.zza("Trying to convert a macro reference to object");
                return null;
            case 5:
                zzdh.zza("Trying to convert a function id to object");
                return null;
            case 6:
                return Long.valueOf(zzakVar.zzf());
            case 7:
                StringBuilder sb = new StringBuilder();
                Iterator<com.google.android.gms.internal.gtm.zzak> it2 = zzakVar.zzs().iterator();
                while (it2.hasNext()) {
                    String strZzn = zzn(zzl(it2.next()));
                    if (strZzn == zzd) {
                        return null;
                    }
                    sb.append(strZzn);
                }
                return sb.toString();
            default:
                return Boolean.valueOf(zzakVar.zzM());
        }
    }

    public static String zzm() {
        return zzd;
    }

    public static String zzn(Object obj) {
        return obj == null ? zzd : obj.toString();
    }

    private static double zzo(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        zzdh.zza("getDouble received non-Number");
        return 0.0d;
    }

    private static long zzp(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        zzdh.zza("getInt64 received non-Number");
        return 0L;
    }

    private static zzfu zzq(String str) {
        try {
            return zzfu.zze(str);
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33);
            sb.append("Failed to convert '");
            sb.append(str);
            sb.append("' to a number.");
            zzdh.zza(sb.toString());
            return zzc;
        }
    }

    private static boolean zzr(Object obj) {
        if ((obj instanceof Double) || (obj instanceof Float)) {
            return true;
        }
        return (obj instanceof zzfu) && ((zzfu) obj).zzf();
    }

    private static boolean zzs(Object obj) {
        if ((obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long)) {
            return true;
        }
        return (obj instanceof zzfu) && ((zzfu) obj).zzg();
    }
}
