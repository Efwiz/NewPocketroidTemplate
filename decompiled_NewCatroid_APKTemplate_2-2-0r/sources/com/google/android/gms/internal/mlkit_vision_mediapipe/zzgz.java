package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgz {
    private static final Class<?> zza = zzd();
    private static final zzhp<?, ?> zzb = zza(false);
    private static final zzhp<?, ?> zzc = zza(true);
    private static final zzhp<?, ?> zzd = new zzhr();

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzfb.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzg(i, list, z);
    }

    public static void zzb(int i, List<Float> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzf(i, list, z);
    }

    public static void zzc(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzl(i, list, z);
    }

    public static void zzh(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzim zzimVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzi(i, list, z);
    }

    public static void zza(int i, List<String> list, zzim zzimVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zza(i, list);
    }

    public static void zzb(int i, List<zzdu> list, zzim zzimVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzb(i, list);
    }

    public static void zza(int i, List<?> list, zzim zzimVar, zzgx zzgxVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zza(i, list, zzgxVar);
    }

    public static void zzb(int i, List<?> list, zzim zzimVar, zzgx zzgxVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzimVar.zzb(i, list, zzgxVar);
    }

    static int zza(List<Long> list) {
        int iZzd;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfw) {
            zzfw zzfwVar = (zzfw) list;
            iZzd = 0;
            while (i < size) {
                iZzd += zzel.zzd(zzfwVar.zzb(i));
                i++;
            }
        } else {
            iZzd = 0;
            while (i < size) {
                iZzd += zzel.zzd(list.get(i).longValue());
                i++;
            }
        }
        return iZzd;
    }

    static int zza(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzel.zze(i));
    }

    static int zzb(List<Long> list) {
        int iZze;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfw) {
            zzfw zzfwVar = (zzfw) list;
            iZze = 0;
            while (i < size) {
                iZze += zzel.zze(zzfwVar.zzb(i));
                i++;
            }
        } else {
            iZze = 0;
            while (i < size) {
                iZze += zzel.zze(list.get(i).longValue());
                i++;
            }
        }
        return iZze;
    }

    static int zzb(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzb(list) + (size * zzel.zze(i));
    }

    static int zzc(List<Long> list) {
        int iZzf;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfw) {
            zzfw zzfwVar = (zzfw) list;
            iZzf = 0;
            while (i < size) {
                iZzf += zzel.zzf(zzfwVar.zzb(i));
                i++;
            }
        } else {
            iZzf = 0;
            while (i < size) {
                iZzf += zzel.zzf(list.get(i).longValue());
                i++;
            }
        }
        return iZzf;
    }

    static int zzc(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzc(list) + (size * zzel.zze(i));
    }

    static int zzd(List<Integer> list) {
        int iZzk;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfc) {
            zzfc zzfcVar = (zzfc) list;
            iZzk = 0;
            while (i < size) {
                iZzk += zzel.zzk(zzfcVar.zzb(i));
                i++;
            }
        } else {
            iZzk = 0;
            while (i < size) {
                iZzk += zzel.zzk(list.get(i).intValue());
                i++;
            }
        }
        return iZzk;
    }

    static int zzd(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzel.zze(i));
    }

    static int zze(List<Integer> list) {
        int iZzf;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfc) {
            zzfc zzfcVar = (zzfc) list;
            iZzf = 0;
            while (i < size) {
                iZzf += zzel.zzf(zzfcVar.zzb(i));
                i++;
            }
        } else {
            iZzf = 0;
            while (i < size) {
                iZzf += zzel.zzf(list.get(i).intValue());
                i++;
            }
        }
        return iZzf;
    }

    static int zze(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzel.zze(i));
    }

    static int zzf(List<Integer> list) {
        int iZzg;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfc) {
            zzfc zzfcVar = (zzfc) list;
            iZzg = 0;
            while (i < size) {
                iZzg += zzel.zzg(zzfcVar.zzb(i));
                i++;
            }
        } else {
            iZzg = 0;
            while (i < size) {
                iZzg += zzel.zzg(list.get(i).intValue());
                i++;
            }
        }
        return iZzg;
    }

    static int zzf(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzf(list) + (size * zzel.zze(i));
    }

    static int zzg(List<Integer> list) {
        int iZzh;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfc) {
            zzfc zzfcVar = (zzfc) list;
            iZzh = 0;
            while (i < size) {
                iZzh += zzel.zzh(zzfcVar.zzb(i));
                i++;
            }
        } else {
            iZzh = 0;
            while (i < size) {
                iZzh += zzel.zzh(list.get(i).intValue());
                i++;
            }
        }
        return iZzh;
    }

    static int zzg(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzg(list) + (size * zzel.zze(i));
    }

    static int zzh(List<?> list) {
        return list.size() << 2;
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzel.zzi(i, 0);
    }

    static int zzi(List<?> list) {
        return list.size() << 3;
    }

    static int zzi(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzel.zzg(i, 0L);
    }

    static int zzj(List<?> list) {
        return list.size();
    }

    static int zzj(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzel.zzb(i, true);
    }

    static int zza(int i, List<?> list) {
        int iZzb;
        int iZzb2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZze = zzel.zze(i) * size;
        if (list instanceof zzfp) {
            zzfp zzfpVar = (zzfp) list;
            while (i2 < size) {
                Object objZzb = zzfpVar.zzb(i2);
                if (objZzb instanceof zzdu) {
                    iZzb2 = zzel.zzb((zzdu) objZzb);
                } else {
                    iZzb2 = zzel.zzb((String) objZzb);
                }
                iZze += iZzb2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzdu) {
                    iZzb = zzel.zzb((zzdu) obj);
                } else {
                    iZzb = zzel.zzb((String) obj);
                }
                iZze += iZzb;
                i2++;
            }
        }
        return iZze;
    }

    static int zza(int i, Object obj, zzgx zzgxVar) {
        if (obj instanceof zzfn) {
            return zzel.zza(i, (zzfn) obj);
        }
        return zzel.zzb(i, (zzgi) obj, zzgxVar);
    }

    static int zza(int i, List<?> list, zzgx zzgxVar) {
        int iZza;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZze = zzel.zze(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzfn) {
                iZza = zzel.zza((zzfn) obj);
            } else {
                iZza = zzel.zza((zzgi) obj, zzgxVar);
            }
            iZze += iZza;
        }
        return iZze;
    }

    static int zzb(int i, List<zzdu> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZze = size * zzel.zze(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZze += zzel.zzb(list.get(i2));
        }
        return iZze;
    }

    static int zzb(int i, List<zzgi> list, zzgx zzgxVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzc += zzel.zzc(i, list.get(i2), zzgxVar);
        }
        return iZzc;
    }

    public static zzhp<?, ?> zza() {
        return zzb;
    }

    public static zzhp<?, ?> zzb() {
        return zzc;
    }

    public static zzhp<?, ?> zzc() {
        return zzd;
    }

    private static zzhp<?, ?> zza(boolean z) {
        try {
            Class<?> clsZze = zze();
            if (clsZze == null) {
                return null;
            }
            return (zzhp) clsZze.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzd() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zze() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zza(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zza(zzgb zzgbVar, T t, T t2, long j) {
        zzhv.zza(t, j, zzgbVar.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j)));
    }

    static <T, FT extends zzew<FT>> void zza(zzeq<FT> zzeqVar, T t, T t2) {
        zzeu<T> zzeuVarZza = zzeqVar.zza(t2);
        if (zzeuVarZza.zza.isEmpty()) {
            return;
        }
        zzeqVar.zzb(t).zza((zzeu) zzeuVarZza);
    }

    static <T, UT, UB> void zza(zzhp<UT, UB> zzhpVar, T t, T t2) {
        zzhpVar.zza(t, zzhpVar.zzc(zzhpVar.zza(t), zzhpVar.zza(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzfg zzfgVar, UB ub, zzhp<UT, UB> zzhpVar) {
        if (zzfgVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int iIntValue = list.get(i3).intValue();
                if (zzfgVar.zza(iIntValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(iIntValue));
                    }
                    i2++;
                } else {
                    ub = (UB) zza(i, iIntValue, ub, zzhpVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int iIntValue2 = it.next().intValue();
                if (!zzfgVar.zza(iIntValue2)) {
                    ub = (UB) zza(i, iIntValue2, ub, zzhpVar);
                    it.remove();
                }
            }
        }
        return ub;
    }

    private static <UT, UB> UB zza(int i, int i2, UB ub, zzhp<UT, UB> zzhpVar) {
        if (ub == null) {
            ub = zzhpVar.zza();
        }
        zzhpVar.zza(ub, i, i2);
        return ub;
    }
}
