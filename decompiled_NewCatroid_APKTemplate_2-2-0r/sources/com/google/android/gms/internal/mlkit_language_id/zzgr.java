package com.google.android.gms.internal.mlkit_language_id;

import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgr {
    private static final Class<?> zza = zzd();
    private static final zzhh<?, ?> zzb = zza(false);
    private static final zzhh<?, ?> zzc = zza(true);
    private static final zzhh<?, ?> zzd = new zzhj();

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzeo.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzg(i, list, z);
    }

    public static void zzb(int i, List<Float> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzf(i, list, z);
    }

    public static void zzc(int i, List<Long> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Long> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzl(i, list, z);
    }

    public static void zzh(int i, List<Integer> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzib zzibVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzi(i, list, z);
    }

    public static void zza(int i, List<String> list, zzib zzibVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zza(i, list);
    }

    public static void zzb(int i, List<zzdn> list, zzib zzibVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzb(i, list);
    }

    public static void zza(int i, List<?> list, zzib zzibVar, zzgp zzgpVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zza(i, list, zzgpVar);
    }

    public static void zzb(int i, List<?> list, zzib zzibVar, zzgp zzgpVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzibVar.zzb(i, list, zzgpVar);
    }

    static int zza(List<Long> list) {
        int iZzd;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfn) {
            zzfn zzfnVar = (zzfn) list;
            iZzd = 0;
            while (i < size) {
                iZzd += zzea.zzd(zzfnVar.zza(i));
                i++;
            }
        } else {
            iZzd = 0;
            while (i < size) {
                iZzd += zzea.zzd(list.get(i).longValue());
                i++;
            }
        }
        return iZzd;
    }

    static int zza(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzea.zze(i));
    }

    static int zzb(List<Long> list) {
        int iZze;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfn) {
            zzfn zzfnVar = (zzfn) list;
            iZze = 0;
            while (i < size) {
                iZze += zzea.zze(zzfnVar.zza(i));
                i++;
            }
        } else {
            iZze = 0;
            while (i < size) {
                iZze += zzea.zze(list.get(i).longValue());
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
        return zzb(list) + (size * zzea.zze(i));
    }

    static int zzc(List<Long> list) {
        int iZzf;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfn) {
            zzfn zzfnVar = (zzfn) list;
            iZzf = 0;
            while (i < size) {
                iZzf += zzea.zzf(zzfnVar.zza(i));
                i++;
            }
        } else {
            iZzf = 0;
            while (i < size) {
                iZzf += zzea.zzf(list.get(i).longValue());
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
        return zzc(list) + (size * zzea.zze(i));
    }

    static int zzd(List<Integer> list) {
        int iZzk;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzer) {
            zzer zzerVar = (zzer) list;
            iZzk = 0;
            while (i < size) {
                iZzk += zzea.zzk(zzerVar.zza(i));
                i++;
            }
        } else {
            iZzk = 0;
            while (i < size) {
                iZzk += zzea.zzk(list.get(i).intValue());
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
        return zzd(list) + (size * zzea.zze(i));
    }

    static int zze(List<Integer> list) {
        int iZzf;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzer) {
            zzer zzerVar = (zzer) list;
            iZzf = 0;
            while (i < size) {
                iZzf += zzea.zzf(zzerVar.zza(i));
                i++;
            }
        } else {
            iZzf = 0;
            while (i < size) {
                iZzf += zzea.zzf(list.get(i).intValue());
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
        return zze(list) + (size * zzea.zze(i));
    }

    static int zzf(List<Integer> list) {
        int iZzg;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzer) {
            zzer zzerVar = (zzer) list;
            iZzg = 0;
            while (i < size) {
                iZzg += zzea.zzg(zzerVar.zza(i));
                i++;
            }
        } else {
            iZzg = 0;
            while (i < size) {
                iZzg += zzea.zzg(list.get(i).intValue());
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
        return zzf(list) + (size * zzea.zze(i));
    }

    static int zzg(List<Integer> list) {
        int iZzh;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzer) {
            zzer zzerVar = (zzer) list;
            iZzh = 0;
            while (i < size) {
                iZzh += zzea.zzh(zzerVar.zza(i));
                i++;
            }
        } else {
            iZzh = 0;
            while (i < size) {
                iZzh += zzea.zzh(list.get(i).intValue());
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
        return zzg(list) + (size * zzea.zze(i));
    }

    static int zzh(List<?> list) {
        return list.size() << 2;
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzea.zzi(i, 0);
    }

    static int zzi(List<?> list) {
        return list.size() << 3;
    }

    static int zzi(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzea.zzg(i, 0L);
    }

    static int zzj(List<?> list) {
        return list.size();
    }

    static int zzj(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzea.zzb(i, true);
    }

    static int zza(int i, List<?> list) {
        int iZzb;
        int iZzb2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZze = zzea.zze(i) * size;
        if (list instanceof zzfg) {
            zzfg zzfgVar = (zzfg) list;
            while (i2 < size) {
                Object objZza = zzfgVar.zza(i2);
                if (objZza instanceof zzdn) {
                    iZzb2 = zzea.zzb((zzdn) objZza);
                } else {
                    iZzb2 = zzea.zzb((String) objZza);
                }
                iZze += iZzb2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzdn) {
                    iZzb = zzea.zzb((zzdn) obj);
                } else {
                    iZzb = zzea.zzb((String) obj);
                }
                iZze += iZzb;
                i2++;
            }
        }
        return iZze;
    }

    static int zza(int i, Object obj, zzgp zzgpVar) {
        if (obj instanceof zzfe) {
            return zzea.zza(i, (zzfe) obj);
        }
        return zzea.zzb(i, (zzfz) obj, zzgpVar);
    }

    static int zza(int i, List<?> list, zzgp zzgpVar) {
        int iZza;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZze = zzea.zze(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzfe) {
                iZza = zzea.zza((zzfe) obj);
            } else {
                iZza = zzea.zza((zzfz) obj, zzgpVar);
            }
            iZze += iZza;
        }
        return iZze;
    }

    static int zzb(int i, List<zzdn> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZze = size * zzea.zze(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZze += zzea.zzb(list.get(i2));
        }
        return iZze;
    }

    static int zzb(int i, List<zzfz> list, zzgp zzgpVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzc += zzea.zzc(i, list.get(i2), zzgpVar);
        }
        return iZzc;
    }

    public static zzhh<?, ?> zza() {
        return zzb;
    }

    public static zzhh<?, ?> zzb() {
        return zzc;
    }

    public static zzhh<?, ?> zzc() {
        return zzd;
    }

    private static zzhh<?, ?> zza(boolean z) {
        try {
            Class<?> clsZze = zze();
            if (clsZze == null) {
                return null;
            }
            return (zzhh) clsZze.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
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

    static <T> void zza(zzfs zzfsVar, T t, T t2, long j) {
        zzhn.zza(t, j, zzfsVar.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j)));
    }

    static <T, FT extends zzel<FT>> void zza(zzee<FT> zzeeVar, T t, T t2) {
        zzej<T> zzejVarZza = zzeeVar.zza(t2);
        if (zzejVarZza.zza.isEmpty()) {
            return;
        }
        zzeeVar.zzb(t).zza((zzej) zzejVarZza);
    }

    static <T, UT, UB> void zza(zzhh<UT, UB> zzhhVar, T t, T t2) {
        zzhhVar.zza(t, zzhhVar.zzb(zzhhVar.zza(t), zzhhVar.zza(t2)));
    }
}
