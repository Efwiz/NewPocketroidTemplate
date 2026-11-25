package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwz {
    private static final Class<?> zza;
    private static final zzxo<?, ?> zzb;
    private static final zzxo<?, ?> zzc;
    private static final zzxo<?, ?> zzd;

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
        zzd = new zzxq();
    }

    public static zzxo<?, ?> zzA() {
        return zzc;
    }

    public static zzxo<?, ?> zzB() {
        return zzd;
    }

    static <UT, UB> UB zzC(int i, List<Integer> list, zzvd zzvdVar, UB ub, zzxo<UT, UB> zzxoVar) {
        if (zzvdVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int iIntValue = list.get(i3).intValue();
                if (zzvdVar.zza(iIntValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(iIntValue));
                    }
                    i2++;
                } else {
                    ub = (UB) zzD(i, iIntValue, ub, zzxoVar);
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
                if (!zzvdVar.zza(iIntValue2)) {
                    ub = (UB) zzD(i, iIntValue2, ub, zzxoVar);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zzD(int i, int i2, UB ub, zzxo<UT, UB> zzxoVar) {
        if (ub == null) {
            ub = zzxoVar.zzf();
        }
        zzxoVar.zzl(ub, i, i2);
        return ub;
    }

    static <T, FT extends zzun<FT>> void zzE(zzuk<FT> zzukVar, T t, T t2) {
        zzuo<T> zzuoVarZzb = zzukVar.zzb(t2);
        if (zzuoVarZzb.zza.isEmpty()) {
            return;
        }
        zzukVar.zzc(t).zzh(zzuoVarZzb);
    }

    static <T, UT, UB> void zzF(zzxo<UT, UB> zzxoVar, T t, T t2) {
        zzxoVar.zzo(t, zzxoVar.zze(zzxoVar.zzd(t), zzxoVar.zzd(t2)));
    }

    public static void zzG(Class<?> cls) {
        Class<?> cls2;
        if (!zzuz.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    static boolean zzH(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zzI(zzwf zzwfVar, T t, T t2, long j) {
        zzxy.zzs(t, j, zzwf.zzc(zzxy.zzf(t, j), zzxy.zzf(t2, j)));
    }

    public static void zzJ(int i, List<Boolean> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzc(i, list, z);
    }

    public static void zzK(int i, List<zztd> list, zztp zztpVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zze(i, list);
    }

    public static void zzL(int i, List<Double> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzg(i, list, z);
    }

    public static void zzM(int i, List<Integer> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzj(i, list, z);
    }

    public static void zzN(int i, List<Integer> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzl(i, list, z);
    }

    public static void zzO(int i, List<Long> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzn(i, list, z);
    }

    public static void zzP(int i, List<Float> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzp(i, list, z);
    }

    public static void zzQ(int i, List<?> list, zztp zztpVar, zzwx zzwxVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            zztpVar.zzq(i, list.get(i2), zzwxVar);
        }
    }

    public static void zzR(int i, List<Integer> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzs(i, list, z);
    }

    public static void zzS(int i, List<Long> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzu(i, list, z);
    }

    public static void zzT(int i, List<?> list, zztp zztpVar, zzwx zzwxVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            zztpVar.zzv(i, list.get(i2), zzwxVar);
        }
    }

    public static void zzU(int i, List<Integer> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzy(i, list, z);
    }

    public static void zzV(int i, List<Long> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzA(i, list, z);
    }

    public static void zzW(int i, List<Integer> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzC(i, list, z);
    }

    public static void zzX(int i, List<Long> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzE(i, list, z);
    }

    public static void zzY(int i, List<String> list, zztp zztpVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzH(i, list);
    }

    public static void zzZ(int i, List<Integer> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzJ(i, list, z);
    }

    static int zza(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzto.zzD(i << 3) + 1);
    }

    public static void zzaa(int i, List<Long> list, zztp zztpVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zztpVar.zzL(i, list, z);
    }

    private static zzxo<?, ?> zzab(boolean z) {
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
            return (zzxo) cls.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused2) {
            return null;
        }
    }

    static int zzb(List<?> list) {
        return list.size();
    }

    static int zzc(int i, List<zztd> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzC = size * zzto.zzC(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZzC += zzto.zzu(list.get(i2));
        }
        return iZzC;
    }

    static int zzd(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzto.zzC(i));
    }

    static int zze(List<Integer> list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzva) {
            zzva zzvaVar = (zzva) list;
            iZzx = 0;
            while (i < size) {
                iZzx += zzto.zzx(zzvaVar.zze(i));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                iZzx += zzto.zzx(list.get(i).intValue());
                i++;
            }
        }
        return iZzx;
    }

    static int zzf(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzto.zzD(i << 3) + 4);
    }

    static int zzg(List<?> list) {
        return list.size() * 4;
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzto.zzD(i << 3) + 8);
    }

    static int zzi(List<?> list) {
        return list.size() * 8;
    }

    static int zzj(int i, List<zzwk> list, zzwx zzwxVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzv = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzv += zzto.zzv(i, list.get(i2), zzwxVar);
        }
        return iZzv;
    }

    static int zzk(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzl(list) + (size * zzto.zzC(i));
    }

    static int zzl(List<Integer> list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzva) {
            zzva zzvaVar = (zzva) list;
            iZzx = 0;
            while (i < size) {
                iZzx += zzto.zzx(zzvaVar.zze(i));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                iZzx += zzto.zzx(list.get(i).intValue());
                i++;
            }
        }
        return iZzx;
    }

    static int zzm(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzn(list) + (list.size() * zzto.zzC(i));
    }

    static int zzn(List<Long> list) {
        int iZzE;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzvz) {
            zzvz zzvzVar = (zzvz) list;
            iZzE = 0;
            while (i < size) {
                iZzE += zzto.zzE(zzvzVar.zze(i));
                i++;
            }
        } else {
            iZzE = 0;
            while (i < size) {
                iZzE += zzto.zzE(list.get(i).longValue());
                i++;
            }
        }
        return iZzE;
    }

    static int zzo(int i, Object obj, zzwx zzwxVar) {
        if (!(obj instanceof zzvq)) {
            return zzto.zzD(i << 3) + zzto.zzA((zzwk) obj, zzwxVar);
        }
        int iZzD = zzto.zzD(i << 3);
        int iZza = ((zzvq) obj).zza();
        return iZzD + zzto.zzD(iZza) + iZza;
    }

    static int zzp(int i, List<?> list, zzwx zzwxVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzC = zzto.zzC(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            iZzC += obj instanceof zzvq ? zzto.zzy((zzvq) obj) : zzto.zzA((zzwk) obj, zzwxVar);
        }
        return iZzC;
    }

    static int zzq(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzr(list) + (size * zzto.zzC(i));
    }

    static int zzr(List<Integer> list) {
        int iZzD;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzva) {
            zzva zzvaVar = (zzva) list;
            iZzD = 0;
            while (i < size) {
                int iZze = zzvaVar.zze(i);
                iZzD += zzto.zzD((iZze >> 31) ^ (iZze + iZze));
                i++;
            }
        } else {
            iZzD = 0;
            while (i < size) {
                int iIntValue = list.get(i).intValue();
                iZzD += zzto.zzD((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
        }
        return iZzD;
    }

    static int zzs(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzt(list) + (size * zzto.zzC(i));
    }

    static int zzt(List<Long> list) {
        int iZzE;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzvz) {
            zzvz zzvzVar = (zzvz) list;
            iZzE = 0;
            while (i < size) {
                long jZze = zzvzVar.zze(i);
                iZzE += zzto.zzE((jZze >> 63) ^ (jZze + jZze));
                i++;
            }
        } else {
            iZzE = 0;
            while (i < size) {
                long jLongValue = list.get(i).longValue();
                iZzE += zzto.zzE((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
        }
        return iZzE;
    }

    static int zzu(int i, List<?> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzC = zzto.zzC(i) * size;
        if (list instanceof zzvs) {
            zzvs zzvsVar = (zzvs) list;
            while (i2 < size) {
                Object objZzf = zzvsVar.zzf(i2);
                iZzC += objZzf instanceof zztd ? zzto.zzu((zztd) objZzf) : zzto.zzB((String) objZzf);
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                iZzC += obj instanceof zztd ? zzto.zzu((zztd) obj) : zzto.zzB((String) obj);
                i2++;
            }
        }
        return iZzC;
    }

    static int zzv(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzw(list) + (size * zzto.zzC(i));
    }

    static int zzw(List<Integer> list) {
        int iZzD;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzva) {
            zzva zzvaVar = (zzva) list;
            iZzD = 0;
            while (i < size) {
                iZzD += zzto.zzD(zzvaVar.zze(i));
                i++;
            }
        } else {
            iZzD = 0;
            while (i < size) {
                iZzD += zzto.zzD(list.get(i).intValue());
                i++;
            }
        }
        return iZzD;
    }

    static int zzx(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzy(list) + (size * zzto.zzC(i));
    }

    static int zzy(List<Long> list) {
        int iZzE;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzvz) {
            zzvz zzvzVar = (zzvz) list;
            iZzE = 0;
            while (i < size) {
                iZzE += zzto.zzE(zzvzVar.zze(i));
                i++;
            }
        } else {
            iZzE = 0;
            while (i < size) {
                iZzE += zzto.zzE(list.get(i).longValue());
                i++;
            }
        }
        return iZzE;
    }

    public static zzxo<?, ?> zzz() {
        return zzb;
    }
}
