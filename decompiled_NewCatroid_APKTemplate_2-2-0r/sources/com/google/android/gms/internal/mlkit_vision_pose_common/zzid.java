package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzid {
    private static final Class<?> zza = zzd();
    private static final zzit<?, ?> zzb = zza(false);
    private static final zzit<?, ?> zzc = zza(true);
    private static final zzit<?, ?> zzd = new zziv();

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzgd.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzg(i, list, z);
    }

    public static void zzb(int i, List<Float> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzf(i, list, z);
    }

    public static void zzc(int i, List<Long> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Long> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzl(i, list, z);
    }

    public static void zzh(int i, List<Integer> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzjn zzjnVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzi(i, list, z);
    }

    public static void zza(int i, List<String> list, zzjn zzjnVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zza(i, list);
    }

    public static void zzb(int i, List<zzez> list, zzjn zzjnVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzb(i, list);
    }

    public static void zza(int i, List<?> list, zzjn zzjnVar, zzib zzibVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zza(i, list, zzibVar);
    }

    public static void zzb(int i, List<?> list, zzjn zzjnVar, zzib zzibVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzjnVar.zzb(i, list, zzibVar);
    }

    static int zza(List<Long> list) {
        int iZzd;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgz) {
            zzgz zzgzVar = (zzgz) list;
            iZzd = 0;
            while (i < size) {
                iZzd += zzfq.zzd(zzgzVar.zza(i));
                i++;
            }
        } else {
            iZzd = 0;
            while (i < size) {
                iZzd += zzfq.zzd(list.get(i).longValue());
                i++;
            }
        }
        return iZzd;
    }

    static int zza(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzfq.zze(i));
    }

    static int zzb(List<Long> list) {
        int iZze;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgz) {
            zzgz zzgzVar = (zzgz) list;
            iZze = 0;
            while (i < size) {
                iZze += zzfq.zze(zzgzVar.zza(i));
                i++;
            }
        } else {
            iZze = 0;
            while (i < size) {
                iZze += zzfq.zze(list.get(i).longValue());
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
        return zzb(list) + (size * zzfq.zze(i));
    }

    static int zzc(List<Long> list) {
        int iZzf;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgz) {
            zzgz zzgzVar = (zzgz) list;
            iZzf = 0;
            while (i < size) {
                iZzf += zzfq.zzf(zzgzVar.zza(i));
                i++;
            }
        } else {
            iZzf = 0;
            while (i < size) {
                iZzf += zzfq.zzf(list.get(i).longValue());
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
        return zzc(list) + (size * zzfq.zze(i));
    }

    static int zzd(List<Integer> list) {
        int iZzk;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzge) {
            zzge zzgeVar = (zzge) list;
            iZzk = 0;
            while (i < size) {
                iZzk += zzfq.zzk(zzgeVar.zza(i));
                i++;
            }
        } else {
            iZzk = 0;
            while (i < size) {
                iZzk += zzfq.zzk(list.get(i).intValue());
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
        return zzd(list) + (size * zzfq.zze(i));
    }

    static int zze(List<Integer> list) {
        int iZzf;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzge) {
            zzge zzgeVar = (zzge) list;
            iZzf = 0;
            while (i < size) {
                iZzf += zzfq.zzf(zzgeVar.zza(i));
                i++;
            }
        } else {
            iZzf = 0;
            while (i < size) {
                iZzf += zzfq.zzf(list.get(i).intValue());
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
        return zze(list) + (size * zzfq.zze(i));
    }

    static int zzf(List<Integer> list) {
        int iZzg;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzge) {
            zzge zzgeVar = (zzge) list;
            iZzg = 0;
            while (i < size) {
                iZzg += zzfq.zzg(zzgeVar.zza(i));
                i++;
            }
        } else {
            iZzg = 0;
            while (i < size) {
                iZzg += zzfq.zzg(list.get(i).intValue());
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
        return zzf(list) + (size * zzfq.zze(i));
    }

    static int zzg(List<Integer> list) {
        int iZzh;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzge) {
            zzge zzgeVar = (zzge) list;
            iZzh = 0;
            while (i < size) {
                iZzh += zzfq.zzh(zzgeVar.zza(i));
                i++;
            }
        } else {
            iZzh = 0;
            while (i < size) {
                iZzh += zzfq.zzh(list.get(i).intValue());
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
        return zzg(list) + (size * zzfq.zze(i));
    }

    static int zzh(List<?> list) {
        return list.size() << 2;
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzfq.zzi(i, 0);
    }

    static int zzi(List<?> list) {
        return list.size() << 3;
    }

    static int zzi(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzfq.zzg(i, 0L);
    }

    static int zzj(List<?> list) {
        return list.size();
    }

    static int zzj(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzfq.zzb(i, true);
    }

    static int zza(int i, List<?> list) {
        int iZzb;
        int iZzb2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZze = zzfq.zze(i) * size;
        if (list instanceof zzgw) {
            zzgw zzgwVar = (zzgw) list;
            while (i2 < size) {
                Object objZza = zzgwVar.zza(i2);
                if (objZza instanceof zzez) {
                    iZzb2 = zzfq.zzb((zzez) objZza);
                } else {
                    iZzb2 = zzfq.zzb((String) objZza);
                }
                iZze += iZzb2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzez) {
                    iZzb = zzfq.zzb((zzez) obj);
                } else {
                    iZzb = zzfq.zzb((String) obj);
                }
                iZze += iZzb;
                i2++;
            }
        }
        return iZze;
    }

    static int zza(int i, Object obj, zzib zzibVar) {
        if (obj instanceof zzgu) {
            return zzfq.zza(i, (zzgu) obj);
        }
        return zzfq.zzb(i, (zzhl) obj, zzibVar);
    }

    static int zza(int i, List<?> list, zzib zzibVar) {
        int iZza;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZze = zzfq.zze(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzgu) {
                iZza = zzfq.zza((zzgu) obj);
            } else {
                iZza = zzfq.zza((zzhl) obj, zzibVar);
            }
            iZze += iZza;
        }
        return iZze;
    }

    static int zzb(int i, List<zzez> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZze = size * zzfq.zze(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZze += zzfq.zzb(list.get(i2));
        }
        return iZze;
    }

    static int zzb(int i, List<zzhl> list, zzib zzibVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzc += zzfq.zzc(i, list.get(i2), zzibVar);
        }
        return iZzc;
    }

    public static zzit<?, ?> zza() {
        return zzb;
    }

    public static zzit<?, ?> zzb() {
        return zzc;
    }

    public static zzit<?, ?> zzc() {
        return zzd;
    }

    private static zzit<?, ?> zza(boolean z) {
        try {
            Class<?> clsZze = zze();
            if (clsZze == null) {
                return null;
            }
            return (zzit) clsZze.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
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

    static <T> void zza(zzhi zzhiVar, T t, T t2, long j) {
        zziz.zza(t, j, zzhiVar.zza(zziz.zzf(t, j), zziz.zzf(t2, j)));
    }

    static <T, FT extends zzfx<FT>> void zza(zzfu<FT> zzfuVar, T t, T t2) {
        zzfv<T> zzfvVarZza = zzfuVar.zza(t2);
        if (zzfvVarZza.zza.isEmpty()) {
            return;
        }
        zzfuVar.zzb(t).zza((zzfv) zzfvVarZza);
    }

    static <T, UT, UB> void zza(zzit<UT, UB> zzitVar, T t, T t2) {
        zzitVar.zza(t, zzitVar.zzb(zzitVar.zza(t), zzitVar.zza(t2)));
    }
}
