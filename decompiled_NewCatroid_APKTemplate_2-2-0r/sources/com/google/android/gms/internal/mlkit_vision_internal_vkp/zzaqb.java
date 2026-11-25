package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaqb {
    private static final Class<?> zza;
    private static final zzaqt<?, ?> zzb;
    private static final zzaqt<?, ?> zzc;
    private static final zzaqt<?, ?> zzd;

    static {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        zza = cls;
        zzb = zzab(false);
        zzc = zzab(true);
        zzd = new zzaqv();
    }

    public static zzaqt<?, ?> zzA() {
        return zzc;
    }

    public static zzaqt<?, ?> zzB() {
        return zzd;
    }

    static <UT, UB> UB zzC(int i, List<Integer> list, zzanw zzanwVar, UB ub, zzaqt<UT, UB> zzaqtVar) {
        if (zzanwVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int iIntValue = list.get(i3).intValue();
                if (zzanwVar.zza(iIntValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(iIntValue));
                    }
                    i2++;
                } else {
                    ub = (UB) zzD(i, iIntValue, ub, zzaqtVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
                return ub;
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int iIntValue2 = it.next().intValue();
                if (!zzanwVar.zza(iIntValue2)) {
                    ub = (UB) zzD(i, iIntValue2, ub, zzaqtVar);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zzD(int i, int i2, UB ub, zzaqt<UT, UB> zzaqtVar) {
        if (ub == null) {
            ub = zzaqtVar.zzf();
        }
        zzaqtVar.zzl(ub, i, i2);
        return ub;
    }

    static <T, FT extends zzang<FT>> void zzE(zzand<FT> zzandVar, T t, T t2) {
        zzanh<T> zzanhVarZzb = zzandVar.zzb(t2);
        if (zzanhVarZzb.zza.isEmpty()) {
            return;
        }
        zzandVar.zzc(t).zzj(zzanhVarZzb);
    }

    static <T, UT, UB> void zzF(zzaqt<UT, UB> zzaqtVar, T t, T t2) {
        zzaqtVar.zzo(t, zzaqtVar.zze(zzaqtVar.zzd(t), zzaqtVar.zzd(t2)));
    }

    public static void zzG(Class<?> cls) {
        Class<?> cls2;
        if (!zzans.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzH(int i, List<Boolean> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzc(i, list, z);
    }

    public static void zzI(int i, List<zzamk> list, zzarl zzarlVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zze(i, list);
    }

    public static void zzJ(int i, List<Double> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzg(i, list, z);
    }

    public static void zzK(int i, List<Integer> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzj(i, list, z);
    }

    public static void zzL(int i, List<Integer> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzl(i, list, z);
    }

    public static void zzM(int i, List<Long> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzn(i, list, z);
    }

    public static void zzN(int i, List<Float> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzp(i, list, z);
    }

    public static void zzO(int i, List<?> list, zzarl zzarlVar, zzapz zzapzVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzamy) zzarlVar).zzq(i, list.get(i2), zzapzVar);
        }
    }

    public static void zzP(int i, List<Integer> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzs(i, list, z);
    }

    public static void zzQ(int i, List<Long> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzu(i, list, z);
    }

    public static void zzR(int i, List<?> list, zzarl zzarlVar, zzapz zzapzVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzamy) zzarlVar).zzw(i, list.get(i2), zzapzVar);
        }
    }

    public static void zzS(int i, List<Integer> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzz(i, list, z);
    }

    public static void zzT(int i, List<Long> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzB(i, list, z);
    }

    public static void zzU(int i, List<Integer> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzD(i, list, z);
    }

    public static void zzV(int i, List<Long> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzF(i, list, z);
    }

    public static void zzW(int i, List<String> list, zzarl zzarlVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzI(i, list);
    }

    public static void zzX(int i, List<Integer> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzK(i, list, z);
    }

    public static void zzY(int i, List<Long> list, zzarl zzarlVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzarlVar.zzM(i, list, z);
    }

    static boolean zzZ(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static int zza(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzamx.zzM(i << 3) + 1);
    }

    static <T> void zzaa(zzapa zzapaVar, T t, T t2, long j) {
        zzard.zzs(t, j, zzapa.zzc(zzard.zzf(t, j), zzard.zzf(t2, j)));
    }

    private static zzaqt<?, ?> zzab(boolean z) {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            cls = null;
        }
        if (cls == null) {
            return null;
        }
        try {
            return (zzaqt) cls.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused2) {
            return null;
        }
    }

    static int zzb(List<?> list) {
        return list.size();
    }

    static int zzc(int i, List<zzamk> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzL = size * zzamx.zzL(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZzL += zzamx.zzC(list.get(i2));
        }
        return iZzL;
    }

    static int zzd(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzamx.zzL(i));
    }

    static int zze(List<Integer> list) {
        int iZzF;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzant) {
            zzant zzantVar = (zzant) list;
            iZzF = 0;
            while (i < size) {
                iZzF += zzamx.zzF(zzantVar.zze(i));
                i++;
            }
        } else {
            iZzF = 0;
            while (i < size) {
                iZzF += zzamx.zzF(list.get(i).intValue());
                i++;
            }
        }
        return iZzF;
    }

    static int zzf(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzamx.zzM(i << 3) + 4);
    }

    static int zzg(List<?> list) {
        return list.size() * 4;
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzamx.zzM(i << 3) + 8);
    }

    static int zzi(List<?> list) {
        return list.size() * 8;
    }

    static int zzj(int i, List<zzapf> list, zzapz zzapzVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzD = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzD += zzamx.zzD(i, list.get(i2), zzapzVar);
        }
        return iZzD;
    }

    static int zzk(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzl(list) + (size * zzamx.zzL(i));
    }

    static int zzl(List<Integer> list) {
        int iZzF;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzant) {
            zzant zzantVar = (zzant) list;
            iZzF = 0;
            while (i < size) {
                iZzF += zzamx.zzF(zzantVar.zze(i));
                i++;
            }
        } else {
            iZzF = 0;
            while (i < size) {
                iZzF += zzamx.zzF(list.get(i).intValue());
                i++;
            }
        }
        return iZzF;
    }

    static int zzm(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzn(list) + (list.size() * zzamx.zzL(i));
    }

    static int zzn(List<Long> list) {
        int iZzN;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzaot) {
            zzaot zzaotVar = (zzaot) list;
            iZzN = 0;
            while (i < size) {
                iZzN += zzamx.zzN(zzaotVar.zze(i));
                i++;
            }
        } else {
            iZzN = 0;
            while (i < size) {
                iZzN += zzamx.zzN(list.get(i).longValue());
                i++;
            }
        }
        return iZzN;
    }

    static int zzo(int i, Object obj, zzapz zzapzVar) {
        if (!(obj instanceof zzaok)) {
            return zzamx.zzM(i << 3) + zzamx.zzI((zzapf) obj, zzapzVar);
        }
        int iZzM = zzamx.zzM(i << 3);
        int iZza = ((zzaok) obj).zza();
        return iZzM + zzamx.zzM(iZza) + iZza;
    }

    static int zzp(int i, List<?> list, zzapz zzapzVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzL = zzamx.zzL(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            iZzL += obj instanceof zzaok ? zzamx.zzG((zzaok) obj) : zzamx.zzI((zzapf) obj, zzapzVar);
        }
        return iZzL;
    }

    static int zzq(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzr(list) + (size * zzamx.zzL(i));
    }

    static int zzr(List<Integer> list) {
        int iZzM;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzant) {
            zzant zzantVar = (zzant) list;
            iZzM = 0;
            while (i < size) {
                int iZze = zzantVar.zze(i);
                iZzM += zzamx.zzM((iZze >> 31) ^ (iZze + iZze));
                i++;
            }
        } else {
            iZzM = 0;
            while (i < size) {
                int iIntValue = list.get(i).intValue();
                iZzM += zzamx.zzM((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
        }
        return iZzM;
    }

    static int zzs(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzt(list) + (size * zzamx.zzL(i));
    }

    static int zzt(List<Long> list) {
        int iZzN;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzaot) {
            zzaot zzaotVar = (zzaot) list;
            iZzN = 0;
            while (i < size) {
                long jZze = zzaotVar.zze(i);
                iZzN += zzamx.zzN((jZze >> 63) ^ (jZze + jZze));
                i++;
            }
        } else {
            iZzN = 0;
            while (i < size) {
                long jLongValue = list.get(i).longValue();
                iZzN += zzamx.zzN((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
        }
        return iZzN;
    }

    static int zzu(int i, List<?> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzL = zzamx.zzL(i) * size;
        if (list instanceof zzaom) {
            zzaom zzaomVar = (zzaom) list;
            while (i2 < size) {
                Object objZzf = zzaomVar.zzf(i2);
                iZzL += objZzf instanceof zzamk ? zzamx.zzC((zzamk) objZzf) : zzamx.zzK((String) objZzf);
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                iZzL += obj instanceof zzamk ? zzamx.zzC((zzamk) obj) : zzamx.zzK((String) obj);
                i2++;
            }
        }
        return iZzL;
    }

    static int zzv(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzw(list) + (size * zzamx.zzL(i));
    }

    static int zzw(List<Integer> list) {
        int iZzM;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzant) {
            zzant zzantVar = (zzant) list;
            iZzM = 0;
            while (i < size) {
                iZzM += zzamx.zzM(zzantVar.zze(i));
                i++;
            }
        } else {
            iZzM = 0;
            while (i < size) {
                iZzM += zzamx.zzM(list.get(i).intValue());
                i++;
            }
        }
        return iZzM;
    }

    static int zzx(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzy(list) + (size * zzamx.zzL(i));
    }

    static int zzy(List<Long> list) {
        int iZzN;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzaot) {
            zzaot zzaotVar = (zzaot) list;
            iZzN = 0;
            while (i < size) {
                iZzN += zzamx.zzN(zzaotVar.zze(i));
                i++;
            }
        } else {
            iZzN = 0;
            while (i < size) {
                iZzN += zzamx.zzN(list.get(i).longValue());
                i++;
            }
        }
        return iZzN;
    }

    public static zzaqt<?, ?> zzz() {
        return zzb;
    }
}
