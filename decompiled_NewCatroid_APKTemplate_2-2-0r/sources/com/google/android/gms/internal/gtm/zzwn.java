package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwn<T> implements zzwx<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzxy.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzwk zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final int[] zzk;
    private final int zzl;
    private final int zzm;
    private final zzvy zzn;
    private final zzxo<?, ?> zzo;
    private final zzuk<?> zzp;
    private final zzwq zzq;
    private final zzwf zzr;

    /* JADX WARN: Multi-variable type inference failed */
    private zzwn(int[] iArr, int[] iArr2, Object[] objArr, int i, int i2, zzwk zzwkVar, boolean z, boolean z2, int[] iArr3, int i3, int i4, zzwq zzwqVar, zzvy zzvyVar, zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwf zzwfVar) {
        this.zzc = iArr;
        this.zzd = iArr2;
        this.zze = objArr;
        this.zzf = i;
        this.zzi = i2 instanceof zzuz;
        this.zzj = zzwkVar;
        boolean z3 = false;
        if (zzxoVar != 0 && zzxoVar.zzi(i2)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzk = z2;
        this.zzl = iArr3;
        this.zzm = i3;
        this.zzq = i4;
        this.zzn = zzwqVar;
        this.zzo = zzvyVar;
        this.zzp = zzxoVar;
        this.zzg = i2;
        this.zzr = zzukVar;
    }

    private final int zzA(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private static int zzB(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzC(int i) {
        return this.zzc[i + 1];
    }

    private static <T> long zzD(T t, long j) {
        return ((Long) zzxy.zzf(t, j)).longValue();
    }

    private final zzvd zzE(int i) {
        int i2 = i / 3;
        return (zzvd) this.zzd[i2 + i2 + 1];
    }

    private final zzwx zzF(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzwx zzwxVar = (zzwx) this.zzd[i3];
        if (zzwxVar != null) {
            return zzwxVar;
        }
        zzwx<T> zzwxVarZzb = zzwt.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzwxVarZzb;
        return zzwxVarZzb;
    }

    private final <UT, UB> UB zzG(Object obj, int i, UB ub, zzxo<UT, UB> zzxoVar) {
        int i2 = this.zzc[i];
        Object objZzf = zzxy.zzf(obj, zzC(i) & 1048575);
        if (objZzf == null || zzE(i) == null) {
            return ub;
        }
        throw null;
    }

    private final Object zzH(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static Field zzI(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name2 = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name2).length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name2);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    private final void zzJ(T t, T t2, int i) {
        long jZzC = zzC(i) & 1048575;
        if (zzQ(t2, i)) {
            Object objZzf = zzxy.zzf(t, jZzC);
            Object objZzf2 = zzxy.zzf(t2, jZzC);
            if (objZzf != null && objZzf2 != null) {
                zzxy.zzs(t, jZzC, zzvi.zzg(objZzf, objZzf2));
                zzM(t, i);
            } else if (objZzf2 != null) {
                zzxy.zzs(t, jZzC, objZzf2);
                zzM(t, i);
            }
        }
    }

    private final void zzK(T t, T t2, int i) {
        int iZzC = zzC(i);
        int i2 = this.zzc[i];
        long j = iZzC & 1048575;
        if (zzT(t2, i2, i)) {
            Object objZzf = zzT(t, i2, i) ? zzxy.zzf(t, j) : null;
            Object objZzf2 = zzxy.zzf(t2, j);
            if (objZzf != null && objZzf2 != null) {
                zzxy.zzs(t, j, zzvi.zzg(objZzf, objZzf2));
                zzN(t, i2, i);
            } else if (objZzf2 != null) {
                zzxy.zzs(t, j, objZzf2);
                zzN(t, i2, i);
            }
        }
    }

    private final void zzL(Object obj, int i, zzww zzwwVar) throws IOException {
        if (zzP(i)) {
            zzxy.zzs(obj, i & 1048575, zzwwVar.zzx());
        } else if (this.zzi) {
            zzxy.zzs(obj, i & 1048575, zzwwVar.zzv());
        } else {
            zzxy.zzs(obj, i & 1048575, zzwwVar.zzq());
        }
    }

    private final void zzM(T t, int i) {
        int iZzz = zzz(i);
        long j = 1048575 & iZzz;
        if (j == 1048575) {
            return;
        }
        zzxy.zzq(t, j, (1 << (iZzz >>> 20)) | zzxy.zzc(t, j));
    }

    private final void zzN(T t, int i, int i2) {
        zzxy.zzq(t, zzz(i2) & 1048575, i);
    }

    private final boolean zzO(T t, T t2, int i) {
        return zzQ(t, i) == zzQ(t2, i);
    }

    private static boolean zzP(int i) {
        return (i & 536870912) != 0;
    }

    private final boolean zzQ(T t, int i) {
        int iZzz = zzz(i);
        long j = iZzz & 1048575;
        if (j != 1048575) {
            return (zzxy.zzc(t, j) & (1 << (iZzz >>> 20))) != 0;
        }
        int iZzC = zzC(i);
        long j2 = iZzC & 1048575;
        switch (zzB(iZzC)) {
            case 0:
                return zzxy.zza(t, j2) != 0.0d;
            case 1:
                return zzxy.zzb(t, j2) != 0.0f;
            case 2:
                return zzxy.zzd(t, j2) != 0;
            case 3:
                return zzxy.zzd(t, j2) != 0;
            case 4:
                return zzxy.zzc(t, j2) != 0;
            case 5:
                return zzxy.zzd(t, j2) != 0;
            case 6:
                return zzxy.zzc(t, j2) != 0;
            case 7:
                return zzxy.zzw(t, j2);
            case 8:
                Object objZzf = zzxy.zzf(t, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zztd) {
                    return !zztd.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzxy.zzf(t, j2) != null;
            case 10:
                return !zztd.zzb.equals(zzxy.zzf(t, j2));
            case 11:
                return zzxy.zzc(t, j2) != 0;
            case 12:
                return zzxy.zzc(t, j2) != 0;
            case 13:
                return zzxy.zzc(t, j2) != 0;
            case 14:
                return zzxy.zzd(t, j2) != 0;
            case 15:
                return zzxy.zzc(t, j2) != 0;
            case 16:
                return zzxy.zzd(t, j2) != 0;
            case 17:
                return zzxy.zzf(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzR(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzQ(t, i);
        }
        return (i3 & i4) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zzS(Object obj, int i, zzwx zzwxVar) {
        return zzwxVar.zzk(zzxy.zzf(obj, i & 1048575));
    }

    private final boolean zzT(T t, int i, int i2) {
        return zzxy.zzc(t, (long) (zzz(i2) & 1048575)) == i;
    }

    private static <T> boolean zzU(T t, long j) {
        return ((Boolean) zzxy.zzf(t, j)).booleanValue();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0023  */
    private final void zzV(T t, zztp zztpVar) throws IOException {
        Iterator itZzf;
        Map.Entry<?, ?> entry;
        int i;
        boolean z;
        if (this.zzh) {
            zzuo<T> zzuoVarZzb = this.zzp.zzb(t);
            if (zzuoVarZzb.zza.isEmpty()) {
                itZzf = null;
                entry = null;
            } else {
                itZzf = zzuoVarZzb.zzf();
                entry = (Map.Entry) itZzf.next();
            }
        } else {
            itZzf = null;
            entry = null;
        }
        int length = this.zzc.length;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i4 < length) {
            int iZzC = zzC(i4);
            int i6 = this.zzc[i4];
            int iZzB = zzB(iZzC);
            if (iZzB <= 17) {
                int i7 = this.zzc[i4 + 2];
                int i8 = i7 & i2;
                if (i8 != i3) {
                    i5 = unsafe.getInt(t, i8);
                    i3 = i8;
                }
                i = 1 << (i7 >>> 20);
            } else {
                i = 0;
            }
            while (entry != null && this.zzp.zza(entry) <= i6) {
                this.zzp.zzj(zztpVar, entry);
                entry = itZzf.hasNext() ? (Map.Entry) itZzf.next() : null;
            }
            long j = iZzC & i2;
            switch (iZzB) {
                case 0:
                    if ((i & i5) != 0) {
                        zztpVar.zzf(i6, zzxy.zza(t, j));
                    }
                    break;
                case 1:
                    if ((i & i5) != 0) {
                        zztpVar.zzo(i6, zzxy.zzb(t, j));
                    }
                    break;
                case 2:
                    if ((i & i5) != 0) {
                        zztpVar.zzt(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 3:
                    if ((i & i5) != 0) {
                        zztpVar.zzK(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 4:
                    if ((i & i5) != 0) {
                        zztpVar.zzr(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 5:
                    if ((i & i5) != 0) {
                        zztpVar.zzm(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 6:
                    if ((i & i5) != 0) {
                        zztpVar.zzk(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 7:
                    if ((i & i5) != 0) {
                        zztpVar.zzb(i6, zzxy.zzw(t, j));
                    }
                    break;
                case 8:
                    if ((i & i5) != 0) {
                        zzX(i6, unsafe.getObject(t, j), zztpVar);
                    }
                    break;
                case 9:
                    if ((i & i5) != 0) {
                        zztpVar.zzv(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                case 10:
                    if ((i & i5) != 0) {
                        zztpVar.zzd(i6, (zztd) unsafe.getObject(t, j));
                    }
                    break;
                case 11:
                    if ((i & i5) != 0) {
                        zztpVar.zzI(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 12:
                    if ((i & i5) != 0) {
                        zztpVar.zzi(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 13:
                    if ((i & i5) != 0) {
                        zztpVar.zzx(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 14:
                    if ((i & i5) != 0) {
                        zztpVar.zzz(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 15:
                    if ((i & i5) != 0) {
                        zztpVar.zzB(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 16:
                    if ((i & i5) != 0) {
                        zztpVar.zzD(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 17:
                    if ((i & i5) != 0) {
                        zztpVar.zzq(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                case 18:
                    zzwz.zzL(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 19:
                    zzwz.zzP(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 20:
                    zzwz.zzS(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 21:
                    zzwz.zzaa(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 22:
                    zzwz.zzR(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 23:
                    zzwz.zzO(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 24:
                    zzwz.zzN(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 25:
                    zzwz.zzJ(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 26:
                    zzwz.zzY(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar);
                    break;
                case 27:
                    zzwz.zzT(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, zzF(i4));
                    break;
                case 28:
                    zzwz.zzK(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar);
                    break;
                case 29:
                    z = false;
                    zzwz.zzZ(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 30:
                    z = false;
                    zzwz.zzM(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 31:
                    z = false;
                    zzwz.zzU(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 32:
                    z = false;
                    zzwz.zzV(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 33:
                    z = false;
                    zzwz.zzW(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 34:
                    z = false;
                    zzwz.zzX(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, false);
                    break;
                case 35:
                    zzwz.zzL(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 36:
                    zzwz.zzP(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 37:
                    zzwz.zzS(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 38:
                    zzwz.zzaa(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 39:
                    zzwz.zzR(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 40:
                    zzwz.zzO(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 41:
                    zzwz.zzN(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 42:
                    zzwz.zzJ(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 43:
                    zzwz.zzZ(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 44:
                    zzwz.zzM(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 45:
                    zzwz.zzU(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 46:
                    zzwz.zzV(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 47:
                    zzwz.zzW(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 48:
                    zzwz.zzX(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, true);
                    break;
                case 49:
                    zzwz.zzQ(this.zzc[i4], (List) unsafe.getObject(t, j), zztpVar, zzF(i4));
                    break;
                case 50:
                    zzW(zztpVar, i6, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzf(i6, zzo(t, j));
                    }
                    break;
                case 52:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzo(i6, zzp(t, j));
                    }
                    break;
                case 53:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzt(i6, zzD(t, j));
                    }
                    break;
                case 54:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzK(i6, zzD(t, j));
                    }
                    break;
                case 55:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzr(i6, zzs(t, j));
                    }
                    break;
                case 56:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzm(i6, zzD(t, j));
                    }
                    break;
                case 57:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzk(i6, zzs(t, j));
                    }
                    break;
                case 58:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzb(i6, zzU(t, j));
                    }
                    break;
                case 59:
                    if (zzT(t, i6, i4)) {
                        zzX(i6, unsafe.getObject(t, j), zztpVar);
                    }
                    break;
                case 60:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzv(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                case 61:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzd(i6, (zztd) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzI(i6, zzs(t, j));
                    }
                    break;
                case 63:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzi(i6, zzs(t, j));
                    }
                    break;
                case 64:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzx(i6, zzs(t, j));
                    }
                    break;
                case 65:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzz(i6, zzD(t, j));
                    }
                    break;
                case 66:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzB(i6, zzs(t, j));
                    }
                    break;
                case 67:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzD(i6, zzD(t, j));
                    }
                    break;
                case 68:
                    if (zzT(t, i6, i4)) {
                        zztpVar.zzq(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                default:
                    break;
            }
            i4 += 3;
            i2 = 1048575;
        }
        while (entry != null) {
            this.zzp.zzj(zztpVar, entry);
            entry = itZzf.hasNext() ? (Map.Entry) itZzf.next() : null;
        }
        zzxo<?, ?> zzxoVar = this.zzo;
        zzxoVar.zzs(zzxoVar.zzd(t), zztpVar);
    }

    private final <K, V> void zzW(zztp zztpVar, int i, Object obj, int i2) throws IOException {
        if (obj == null) {
            return;
        }
        throw null;
    }

    private static final void zzX(int i, Object obj, zztp zztpVar) throws IOException {
        if (obj instanceof String) {
            zztpVar.zzG(i, (String) obj);
        } else {
            zztpVar.zzd(i, (zztd) obj);
        }
    }

    static zzxp zzd(Object obj) {
        zzuz zzuzVar = (zzuz) obj;
        zzxp zzxpVar = zzuzVar.zzc;
        if (zzxpVar != zzxp.zzc()) {
            return zzxpVar;
        }
        zzxp zzxpVarZze = zzxp.zze();
        zzuzVar.zzc = zzxpVarZze;
        return zzxpVarZze;
    }

    static <T> zzwn<T> zzl(Class<T> cls, zzwh zzwhVar, zzwq zzwqVar, zzvy zzvyVar, zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwf zzwfVar) {
        if (zzwhVar instanceof zzwv) {
            return zzm((zzwv) zzwhVar, zzwqVar, zzvyVar, zzxoVar, zzukVar, zzwfVar);
        }
        throw null;
    }

    /* JADX WARN: Code duplicated, block: B:123:0x025e  */
    /* JADX WARN: Code duplicated, block: B:124:0x0261  */
    /* JADX WARN: Code duplicated, block: B:127:0x0279  */
    /* JADX WARN: Code duplicated, block: B:128:0x027c  */
    /* JADX WARN: Code duplicated, block: B:162:0x0330  */
    /* JADX WARN: Code duplicated, block: B:177:0x037f  */
    /* JADX WARN: Code duplicated, block: B:180:0x0389  */
    static <T> zzwn<T> zzm(zzwv zzwvVar, zzwq zzwqVar, zzvy zzvyVar, zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwf zzwfVar) {
        int i;
        int iCharAt;
        int iCharAt2;
        int iCharAt3;
        int[] iArr;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        char cCharAt;
        int i7;
        char cCharAt2;
        int i8;
        char cCharAt3;
        int i9;
        char cCharAt4;
        int i10;
        char cCharAt5;
        int i11;
        char cCharAt6;
        int i12;
        char cCharAt7;
        int i13;
        char cCharAt8;
        int i14;
        int i15;
        int i16;
        int[] iArr2;
        int i17;
        int i18;
        int i19;
        int iObjectFieldOffset;
        int iObjectFieldOffset2;
        Object[] objArr;
        int i20;
        int i21;
        Field fieldZzI;
        char cCharAt9;
        int i22;
        int i23;
        int i24;
        int i25;
        Object obj;
        Field fieldZzI2;
        int i26;
        Object obj2;
        Field fieldZzI3;
        int i27;
        char cCharAt10;
        int i28;
        char cCharAt11;
        int i29;
        char cCharAt12;
        int i30;
        char cCharAt13;
        boolean z = zzwvVar.zzc() == 2;
        String strZzd = zzwvVar.zzd();
        int length = strZzd.length();
        char c = 55296;
        if (strZzd.charAt(0) >= 55296) {
            int i31 = 1;
            while (true) {
                i = i31 + 1;
                if (strZzd.charAt(i31) < 55296) {
                    break;
                }
                i31 = i;
            }
        } else {
            i = 1;
        }
        int i32 = i + 1;
        int iCharAt4 = strZzd.charAt(i);
        if (iCharAt4 >= 55296) {
            int i33 = iCharAt4 & 8191;
            int i34 = 13;
            while (true) {
                i30 = i32 + 1;
                cCharAt13 = strZzd.charAt(i32);
                if (cCharAt13 < 55296) {
                    break;
                }
                i33 |= (cCharAt13 & 8191) << i34;
                i34 += 13;
                i32 = i30;
            }
            iCharAt4 = i33 | (cCharAt13 << i34);
            i32 = i30;
        }
        if (iCharAt4 == 0) {
            iCharAt = 0;
            i5 = 0;
            iCharAt2 = 0;
            i4 = 0;
            iCharAt3 = 0;
            i2 = 0;
            iArr = zza;
            i3 = 0;
        } else {
            int i35 = i32 + 1;
            int iCharAt5 = strZzd.charAt(i32);
            if (iCharAt5 >= 55296) {
                int i36 = iCharAt5 & 8191;
                int i37 = 13;
                while (true) {
                    i13 = i35 + 1;
                    cCharAt8 = strZzd.charAt(i35);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i36 |= (cCharAt8 & 8191) << i37;
                    i37 += 13;
                    i35 = i13;
                }
                iCharAt5 = i36 | (cCharAt8 << i37);
                i35 = i13;
            }
            int i38 = i35 + 1;
            int iCharAt6 = strZzd.charAt(i35);
            if (iCharAt6 >= 55296) {
                int i39 = iCharAt6 & 8191;
                int i40 = 13;
                while (true) {
                    i12 = i38 + 1;
                    cCharAt7 = strZzd.charAt(i38);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i39 |= (cCharAt7 & 8191) << i40;
                    i40 += 13;
                    i38 = i12;
                }
                iCharAt6 = i39 | (cCharAt7 << i40);
                i38 = i12;
            }
            int i41 = i38 + 1;
            iCharAt = strZzd.charAt(i38);
            if (iCharAt >= 55296) {
                int i42 = iCharAt & 8191;
                int i43 = 13;
                while (true) {
                    i11 = i41 + 1;
                    cCharAt6 = strZzd.charAt(i41);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i42 |= (cCharAt6 & 8191) << i43;
                    i43 += 13;
                    i41 = i11;
                }
                iCharAt = i42 | (cCharAt6 << i43);
                i41 = i11;
            }
            int i44 = i41 + 1;
            int iCharAt7 = strZzd.charAt(i41);
            if (iCharAt7 >= 55296) {
                int i45 = iCharAt7 & 8191;
                int i46 = 13;
                while (true) {
                    i10 = i44 + 1;
                    cCharAt5 = strZzd.charAt(i44);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i45 |= (cCharAt5 & 8191) << i46;
                    i46 += 13;
                    i44 = i10;
                }
                iCharAt7 = i45 | (cCharAt5 << i46);
                i44 = i10;
            }
            int i47 = i44 + 1;
            iCharAt2 = strZzd.charAt(i44);
            if (iCharAt2 >= 55296) {
                int i48 = iCharAt2 & 8191;
                int i49 = 13;
                while (true) {
                    i9 = i47 + 1;
                    cCharAt4 = strZzd.charAt(i47);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i48 |= (cCharAt4 & 8191) << i49;
                    i49 += 13;
                    i47 = i9;
                }
                iCharAt2 = i48 | (cCharAt4 << i49);
                i47 = i9;
            }
            int i50 = i47 + 1;
            int iCharAt8 = strZzd.charAt(i47);
            if (iCharAt8 >= 55296) {
                int i51 = iCharAt8 & 8191;
                int i52 = 13;
                while (true) {
                    i8 = i50 + 1;
                    cCharAt3 = strZzd.charAt(i50);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i51 |= (cCharAt3 & 8191) << i52;
                    i52 += 13;
                    i50 = i8;
                }
                iCharAt8 = i51 | (cCharAt3 << i52);
                i50 = i8;
            }
            int i53 = i50 + 1;
            int iCharAt9 = strZzd.charAt(i50);
            if (iCharAt9 >= 55296) {
                int i54 = iCharAt9 & 8191;
                int i55 = 13;
                while (true) {
                    i7 = i53 + 1;
                    cCharAt2 = strZzd.charAt(i53);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i54 |= (cCharAt2 & 8191) << i55;
                    i55 += 13;
                    i53 = i7;
                }
                iCharAt9 = i54 | (cCharAt2 << i55);
                i53 = i7;
            }
            int i56 = i53 + 1;
            iCharAt3 = strZzd.charAt(i53);
            if (iCharAt3 >= 55296) {
                int i57 = iCharAt3 & 8191;
                int i58 = 13;
                while (true) {
                    i6 = i56 + 1;
                    cCharAt = strZzd.charAt(i56);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i57 |= (cCharAt & 8191) << i58;
                    i58 += 13;
                    i56 = i6;
                }
                iCharAt3 = i57 | (cCharAt << i58);
                i56 = i6;
            }
            iArr = new int[iCharAt3 + iCharAt8 + iCharAt9];
            i2 = iCharAt5 + iCharAt5 + iCharAt6;
            i3 = iCharAt5;
            i32 = i56;
            int i59 = iCharAt8;
            i4 = iCharAt7;
            i5 = i59;
        }
        Unsafe unsafe = zzb;
        Object[] objArrZze = zzwvVar.zze();
        Class<?> cls = zzwvVar.zza().getClass();
        int[] iArr3 = new int[iCharAt2 * 3];
        Object[] objArr2 = new Object[iCharAt2 + iCharAt2];
        int i60 = iCharAt3 + i5;
        int i61 = iCharAt3;
        int i62 = i60;
        int i63 = 0;
        int i64 = 0;
        while (i32 < length) {
            int i65 = i32 + 1;
            int iCharAt10 = strZzd.charAt(i32);
            if (iCharAt10 >= c) {
                int i66 = iCharAt10 & 8191;
                int i67 = i65;
                int i68 = 13;
                while (true) {
                    i29 = i67 + 1;
                    cCharAt12 = strZzd.charAt(i67);
                    if (cCharAt12 < c) {
                        break;
                    }
                    i66 |= (cCharAt12 & 8191) << i68;
                    i68 += 13;
                    i67 = i29;
                }
                iCharAt10 = i66 | (cCharAt12 << i68);
                i14 = i29;
            } else {
                i14 = i65;
            }
            int i69 = i14 + 1;
            int iCharAt11 = strZzd.charAt(i14);
            if (iCharAt11 >= c) {
                int i70 = iCharAt11 & 8191;
                int i71 = i69;
                int i72 = 13;
                while (true) {
                    i28 = i71 + 1;
                    cCharAt11 = strZzd.charAt(i71);
                    i15 = length;
                    if (cCharAt11 < 55296) {
                        break;
                    }
                    i70 |= (cCharAt11 & 8191) << i72;
                    i72 += 13;
                    i71 = i28;
                    length = i15;
                }
                iCharAt11 = i70 | (cCharAt11 << i72);
                i16 = i28;
            } else {
                i15 = length;
                i16 = i69;
            }
            int i73 = iCharAt11 & 255;
            int i74 = iCharAt3;
            if ((iCharAt11 & 1024) != 0) {
                iArr[i64] = i63;
                i64++;
            }
            if (i73 >= 51) {
                int i75 = i16 + 1;
                int iCharAt12 = strZzd.charAt(i16);
                if (iCharAt12 >= 55296) {
                    int i76 = iCharAt12 & 8191;
                    int i77 = i75;
                    int i78 = 13;
                    while (true) {
                        i27 = i77 + 1;
                        cCharAt10 = strZzd.charAt(i77);
                        i18 = i4;
                        if (cCharAt10 < 55296) {
                            break;
                        }
                        i76 |= (cCharAt10 & 8191) << i78;
                        i78 += 13;
                        i77 = i27;
                        i4 = i18;
                    }
                    iCharAt12 = i76 | (cCharAt10 << i78);
                    i23 = i27;
                } else {
                    i18 = i4;
                    i23 = i75;
                }
                int i79 = i73 - 51;
                int i80 = i23;
                if (i79 == 9 || i79 == 17) {
                    int i81 = i63 / 3;
                    i24 = i2 + 1;
                    objArr2[i81 + i81 + 1] = objArrZze[i2];
                } else {
                    if (i79 == 12 && !z) {
                        int i82 = i63 / 3;
                        i24 = i2 + 1;
                        objArr2[i82 + i82 + 1] = objArrZze[i2];
                    }
                    i25 = iCharAt12 + iCharAt12;
                    obj = objArrZze[i25];
                    if (obj instanceof Field) {
                        fieldZzI2 = (Field) obj;
                    } else {
                        fieldZzI2 = zzI(cls, (String) obj);
                        objArrZze[i25] = fieldZzI2;
                    }
                    iArr2 = iArr3;
                    i17 = iCharAt;
                    int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZzI2);
                    i26 = i25 + 1;
                    obj2 = objArrZze[i26];
                    if (obj2 instanceof Field) {
                        fieldZzI3 = (Field) obj2;
                    } else {
                        fieldZzI3 = zzI(cls, (String) obj2);
                        objArrZze[i26] = fieldZzI3;
                    }
                    strZzd = strZzd;
                    objArr = objArr2;
                    i19 = i2;
                    i20 = i80;
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzI3);
                    iObjectFieldOffset = iObjectFieldOffset3;
                    i21 = 0;
                }
                i2 = i24;
                i25 = iCharAt12 + iCharAt12;
                obj = objArrZze[i25];
                if (obj instanceof Field) {
                    fieldZzI2 = (Field) obj;
                } else {
                    fieldZzI2 = zzI(cls, (String) obj);
                    objArrZze[i25] = fieldZzI2;
                }
                iArr2 = iArr3;
                i17 = iCharAt;
                int iObjectFieldOffset4 = (int) unsafe.objectFieldOffset(fieldZzI2);
                i26 = i25 + 1;
                obj2 = objArrZze[i26];
                if (obj2 instanceof Field) {
                    fieldZzI3 = (Field) obj2;
                } else {
                    fieldZzI3 = zzI(cls, (String) obj2);
                    objArrZze[i26] = fieldZzI3;
                }
                strZzd = strZzd;
                objArr = objArr2;
                i19 = i2;
                i20 = i80;
                iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzI3);
                iObjectFieldOffset = iObjectFieldOffset4;
                i21 = 0;
            } else {
                iArr2 = iArr3;
                i17 = iCharAt;
                i18 = i4;
                int i83 = i2 + 1;
                Field fieldZzI4 = zzI(cls, (String) objArrZze[i2]);
                if (i73 == 9 || i73 == 17) {
                    int i84 = i63 / 3;
                    objArr2[i84 + i84 + 1] = fieldZzI4.getType();
                } else {
                    if (i73 == 27 || i73 == 49) {
                        int i85 = i63 / 3;
                        i22 = i2 + 2;
                        objArr2[i85 + i85 + 1] = objArrZze[i83];
                    } else if (i73 == 12 || i73 == 30 || i73 == 44) {
                        if (!z) {
                            int i86 = i63 / 3;
                            i22 = i2 + 2;
                            objArr2[i86 + i86 + 1] = objArrZze[i83];
                        }
                    } else if (i73 == 50) {
                        int i87 = i61 + 1;
                        iArr[i61] = i63;
                        int i88 = i63 / 3;
                        int i89 = i88 + i88;
                        int i90 = i2 + 2;
                        objArr2[i89] = objArrZze[i83];
                        if ((iCharAt11 & 2048) != 0) {
                            i83 = i2 + 3;
                            objArr2[i89 + 1] = objArrZze[i90];
                            i61 = i87;
                        } else {
                            i61 = i87;
                            i19 = i90;
                        }
                        iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzI4);
                        iObjectFieldOffset2 = 1048575;
                        objArr = objArr2;
                        if ((iCharAt11 & 4096) == 4096 || i73 > 17) {
                            i20 = i16;
                            i21 = 0;
                        } else {
                            int i91 = i16 + 1;
                            int iCharAt13 = strZzd.charAt(i16);
                            if (iCharAt13 >= 55296) {
                                int i92 = iCharAt13 & 8191;
                                int i93 = 13;
                                while (true) {
                                    i20 = i91 + 1;
                                    cCharAt9 = strZzd.charAt(i91);
                                    if (cCharAt9 < 55296) {
                                        break;
                                    }
                                    i92 |= (cCharAt9 & 8191) << i93;
                                    i93 += 13;
                                    i91 = i20;
                                }
                                iCharAt13 = i92 | (cCharAt9 << i93);
                            } else {
                                i20 = i91;
                            }
                            int i94 = i3 + i3 + (iCharAt13 / 32);
                            Object obj3 = objArrZze[i94];
                            if (obj3 instanceof Field) {
                                fieldZzI = (Field) obj3;
                            } else {
                                fieldZzI = zzI(cls, (String) obj3);
                                objArrZze[i94] = fieldZzI;
                            }
                            i21 = iCharAt13 % 32;
                            iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzI);
                        }
                        if (i73 >= 18 && i73 <= 49) {
                            iArr[i62] = iObjectFieldOffset;
                            i62++;
                        }
                    }
                    i19 = i22;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzI4);
                    iObjectFieldOffset2 = 1048575;
                    objArr = objArr2;
                    if ((iCharAt11 & 4096) == 4096) {
                        i20 = i16;
                        i21 = 0;
                    } else {
                        i20 = i16;
                        i21 = 0;
                    }
                    if (i73 >= 18) {
                        iArr[i62] = iObjectFieldOffset;
                        i62++;
                    }
                }
                i19 = i83;
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzI4);
                iObjectFieldOffset2 = 1048575;
                objArr = objArr2;
                if ((iCharAt11 & 4096) == 4096) {
                    i20 = i16;
                    i21 = 0;
                } else {
                    i20 = i16;
                    i21 = 0;
                }
                if (i73 >= 18) {
                    iArr[i62] = iObjectFieldOffset;
                    i62++;
                }
            }
            int i95 = i63 + 1;
            iArr2[i63] = iCharAt10;
            int i96 = i63 + 2;
            iArr2[i95] = ((iCharAt11 & 256) != 0 ? 268435456 : 0) | ((iCharAt11 & 512) != 0 ? 536870912 : 0) | (i73 << 20) | iObjectFieldOffset;
            i63 += 3;
            iArr2[i96] = (i21 << 20) | iObjectFieldOffset2;
            i2 = i19;
            iCharAt = i17;
            iCharAt3 = i74;
            i32 = i20;
            length = i15;
            objArr2 = objArr;
            strZzd = strZzd;
            iArr3 = iArr2;
            i4 = i18;
            c = 55296;
        }
        return new zzwn<>(iArr3, objArr2, iCharAt, i4, zzwvVar.zza(), z, false, iArr, iCharAt3, i60, zzwqVar, zzvyVar, zzxoVar, zzukVar, zzwfVar, null);
    }

    private static <T> double zzo(T t, long j) {
        return ((Double) zzxy.zzf(t, j)).doubleValue();
    }

    private static <T> float zzp(T t, long j) {
        return ((Float) zzxy.zzf(t, j)).floatValue();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final int zzq(T t) {
        int i;
        int iZzD;
        int iZzD2;
        int iZzD3;
        int iZzE;
        int iZzD4;
        int iZzx;
        int iZzD5;
        int iZzD6;
        int iZzd;
        int iZzD7;
        int iZzo;
        int iZzC;
        int iZzD8;
        int i2;
        Unsafe unsafe = zzb;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1048575;
        for (int i6 = 0; i6 < this.zzc.length; i6 += 3) {
            int iZzC2 = zzC(i6);
            int i7 = this.zzc[i6];
            int iZzB = zzB(iZzC2);
            if (iZzB <= 17) {
                int i8 = this.zzc[i6 + 2];
                int i9 = i8 & 1048575;
                i = 1 << (i8 >>> 20);
                if (i9 != i5) {
                    i4 = unsafe.getInt(t, i9);
                    i5 = i9;
                }
            } else {
                i = 0;
            }
            long j = iZzC2 & 1048575;
            switch (iZzB) {
                case 0:
                    if ((i4 & i) != 0) {
                        iZzD = zzto.zzD(i7 << 3);
                        iZzo = iZzD + 8;
                        i3 += iZzo;
                    }
                    break;
                case 1:
                    if ((i4 & i) != 0) {
                        iZzD2 = zzto.zzD(i7 << 3);
                        iZzo = iZzD2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 2:
                    if ((i4 & i) != 0) {
                        long j2 = unsafe.getLong(t, j);
                        iZzD3 = zzto.zzD(i7 << 3);
                        iZzE = zzto.zzE(j2);
                        iZzo = iZzD3 + iZzE;
                        i3 += iZzo;
                    }
                    break;
                case 3:
                    if ((i4 & i) != 0) {
                        long j3 = unsafe.getLong(t, j);
                        iZzD3 = zzto.zzD(i7 << 3);
                        iZzE = zzto.zzE(j3);
                        iZzo = iZzD3 + iZzE;
                        i3 += iZzo;
                    }
                    break;
                case 4:
                    if ((i4 & i) != 0) {
                        int i10 = unsafe.getInt(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzx(i10);
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 5:
                    if ((i4 & i) != 0) {
                        iZzD = zzto.zzD(i7 << 3);
                        iZzo = iZzD + 8;
                        i3 += iZzo;
                    }
                    break;
                case 6:
                    if ((i4 & i) != 0) {
                        iZzD2 = zzto.zzD(i7 << 3);
                        iZzo = iZzD2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 7:
                    if ((i4 & i) != 0) {
                        iZzD5 = zzto.zzD(i7 << 3);
                        iZzo = iZzD5 + 1;
                        i3 += iZzo;
                    }
                    break;
                case 8:
                    if ((i4 & i) != 0) {
                        Object object = unsafe.getObject(t, j);
                        if (object instanceof zztd) {
                            iZzD6 = zzto.zzD(i7 << 3);
                            iZzd = ((zztd) object).zzd();
                            iZzD7 = zzto.zzD(iZzd);
                            i2 = iZzD6 + iZzD7 + iZzd;
                            i3 += i2;
                        } else {
                            iZzD4 = zzto.zzD(i7 << 3);
                            iZzx = zzto.zzB((String) object);
                            i2 = iZzD4 + iZzx;
                            i3 += i2;
                        }
                    }
                    break;
                case 9:
                    if ((i4 & i) != 0) {
                        iZzo = zzwz.zzo(i7, unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
                case 10:
                    if ((i4 & i) != 0) {
                        zztd zztdVar = (zztd) unsafe.getObject(t, j);
                        iZzD6 = zzto.zzD(i7 << 3);
                        iZzd = zztdVar.zzd();
                        iZzD7 = zzto.zzD(iZzd);
                        i2 = iZzD6 + iZzD7 + iZzd;
                        i3 += i2;
                    }
                    break;
                case 11:
                    if ((i4 & i) != 0) {
                        int i11 = unsafe.getInt(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzD(i11);
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 12:
                    if ((i4 & i) != 0) {
                        int i12 = unsafe.getInt(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzx(i12);
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 13:
                    if ((i4 & i) != 0) {
                        iZzD2 = zzto.zzD(i7 << 3);
                        iZzo = iZzD2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 14:
                    if ((i4 & i) != 0) {
                        iZzD = zzto.zzD(i7 << 3);
                        iZzo = iZzD + 8;
                        i3 += iZzo;
                    }
                    break;
                case 15:
                    if ((i4 & i) != 0) {
                        int i13 = unsafe.getInt(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzD((i13 >> 31) ^ (i13 + i13));
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 16:
                    if ((i4 & i) != 0) {
                        long j4 = unsafe.getLong(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzE((j4 >> 63) ^ (j4 + j4));
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 17:
                    if ((i4 & i) != 0) {
                        iZzo = zzto.zzv(i7, (zzwk) unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
                case 18:
                    iZzo = zzwz.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 19:
                    iZzo = zzwz.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 20:
                    iZzo = zzwz.zzm(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 21:
                    iZzo = zzwz.zzx(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 22:
                    iZzo = zzwz.zzk(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 23:
                    iZzo = zzwz.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 24:
                    iZzo = zzwz.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 25:
                    iZzo = zzwz.zza(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 26:
                    iZzo = zzwz.zzu(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzo;
                    break;
                case 27:
                    iZzo = zzwz.zzp(i7, (List) unsafe.getObject(t, j), zzF(i6));
                    i3 += iZzo;
                    break;
                case 28:
                    iZzo = zzwz.zzc(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzo;
                    break;
                case 29:
                    iZzo = zzwz.zzv(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 30:
                    iZzo = zzwz.zzd(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 31:
                    iZzo = zzwz.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 32:
                    iZzo = zzwz.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 33:
                    iZzo = zzwz.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 34:
                    iZzo = zzwz.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 35:
                    iZzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 36:
                    iZzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 37:
                    iZzx = zzwz.zzn((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 38:
                    iZzx = zzwz.zzy((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 39:
                    iZzx = zzwz.zzl((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 40:
                    iZzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 41:
                    iZzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 42:
                    iZzx = zzwz.zzb((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 43:
                    iZzx = zzwz.zzw((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 44:
                    iZzx = zzwz.zze((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 45:
                    iZzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 46:
                    iZzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 47:
                    iZzx = zzwz.zzr((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 48:
                    iZzx = zzwz.zzt((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i7);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 49:
                    iZzo = zzwz.zzj(i7, (List) unsafe.getObject(t, j), zzF(i6));
                    i3 += iZzo;
                    break;
                case 50:
                    zzwf.zza(i7, unsafe.getObject(t, j), zzH(i6));
                    break;
                case 51:
                    if (zzT(t, i7, i6)) {
                        iZzD = zzto.zzD(i7 << 3);
                        iZzo = iZzD + 8;
                        i3 += iZzo;
                    }
                    break;
                case 52:
                    if (zzT(t, i7, i6)) {
                        iZzD2 = zzto.zzD(i7 << 3);
                        iZzo = iZzD2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 53:
                    if (zzT(t, i7, i6)) {
                        long jZzD = zzD(t, j);
                        iZzD3 = zzto.zzD(i7 << 3);
                        iZzE = zzto.zzE(jZzD);
                        iZzo = iZzD3 + iZzE;
                        i3 += iZzo;
                    }
                    break;
                case 54:
                    if (zzT(t, i7, i6)) {
                        long jZzD2 = zzD(t, j);
                        iZzD3 = zzto.zzD(i7 << 3);
                        iZzE = zzto.zzE(jZzD2);
                        iZzo = iZzD3 + iZzE;
                        i3 += iZzo;
                    }
                    break;
                case 55:
                    if (zzT(t, i7, i6)) {
                        int iZzs = zzs(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzx(iZzs);
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 56:
                    if (zzT(t, i7, i6)) {
                        iZzD = zzto.zzD(i7 << 3);
                        iZzo = iZzD + 8;
                        i3 += iZzo;
                    }
                    break;
                case 57:
                    if (zzT(t, i7, i6)) {
                        iZzD2 = zzto.zzD(i7 << 3);
                        iZzo = iZzD2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 58:
                    if (zzT(t, i7, i6)) {
                        iZzD5 = zzto.zzD(i7 << 3);
                        iZzo = iZzD5 + 1;
                        i3 += iZzo;
                    }
                    break;
                case 59:
                    if (zzT(t, i7, i6)) {
                        Object object2 = unsafe.getObject(t, j);
                        if (object2 instanceof zztd) {
                            iZzD6 = zzto.zzD(i7 << 3);
                            iZzd = ((zztd) object2).zzd();
                            iZzD7 = zzto.zzD(iZzd);
                            i2 = iZzD6 + iZzD7 + iZzd;
                            i3 += i2;
                        } else {
                            iZzD4 = zzto.zzD(i7 << 3);
                            iZzx = zzto.zzB((String) object2);
                            i2 = iZzD4 + iZzx;
                            i3 += i2;
                        }
                    }
                    break;
                case 60:
                    if (zzT(t, i7, i6)) {
                        iZzo = zzwz.zzo(i7, unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
                case 61:
                    if (zzT(t, i7, i6)) {
                        zztd zztdVar2 = (zztd) unsafe.getObject(t, j);
                        iZzD6 = zzto.zzD(i7 << 3);
                        iZzd = zztdVar2.zzd();
                        iZzD7 = zzto.zzD(iZzd);
                        i2 = iZzD6 + iZzD7 + iZzd;
                        i3 += i2;
                    }
                    break;
                case 62:
                    if (zzT(t, i7, i6)) {
                        int iZzs2 = zzs(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzD(iZzs2);
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 63:
                    if (zzT(t, i7, i6)) {
                        int iZzs3 = zzs(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzx(iZzs3);
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 64:
                    if (zzT(t, i7, i6)) {
                        iZzD2 = zzto.zzD(i7 << 3);
                        iZzo = iZzD2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 65:
                    if (zzT(t, i7, i6)) {
                        iZzD = zzto.zzD(i7 << 3);
                        iZzo = iZzD + 8;
                        i3 += iZzo;
                    }
                    break;
                case 66:
                    if (zzT(t, i7, i6)) {
                        int iZzs4 = zzs(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzD((iZzs4 >> 31) ^ (iZzs4 + iZzs4));
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 67:
                    if (zzT(t, i7, i6)) {
                        long jZzD3 = zzD(t, j);
                        iZzD4 = zzto.zzD(i7 << 3);
                        iZzx = zzto.zzE((jZzD3 >> 63) ^ (jZzD3 + jZzD3));
                        i2 = iZzD4 + iZzx;
                        i3 += i2;
                    }
                    break;
                case 68:
                    if (zzT(t, i7, i6)) {
                        iZzo = zzto.zzv(i7, (zzwk) unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
            }
        }
        zzxo<?, ?> zzxoVar = this.zzo;
        int iZza = i3 + zzxoVar.zza(zzxoVar.zzd(t));
        if (!this.zzh) {
            return iZza;
        }
        zzuo<T> zzuoVarZzb = this.zzp.zzb(t);
        int iZza2 = 0;
        for (int i14 = 0; i14 < zzuoVarZzb.zza.zzb(); i14++) {
            Map.Entry entryZzg = zzuoVarZzb.zza.zzg(i14);
            iZza2 += zzuo.zza((zzun) entryZzg.getKey(), entryZzg.getValue());
        }
        for (Map.Entry entry : zzuoVarZzb.zza.zzc()) {
            iZza2 += zzuo.zza((zzun) entry.getKey(), entry.getValue());
        }
        return iZza + iZza2;
    }

    private final int zzr(T t) {
        int iZzD;
        int iZzD2;
        int iZzD3;
        int iZzE;
        int iZzD4;
        int iZzx;
        int iZzD5;
        int iZzD6;
        int iZzd;
        int iZzD7;
        int iZzo;
        int iZzC;
        int iZzD8;
        int i;
        Unsafe unsafe = zzb;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzc.length; i3 += 3) {
            int iZzC2 = zzC(i3);
            int iZzB = zzB(iZzC2);
            int i4 = this.zzc[i3];
            long j = iZzC2 & 1048575;
            if (iZzB >= zzup.DOUBLE_LIST_PACKED.zza() && iZzB <= zzup.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i3 + 2];
            }
            switch (iZzB) {
                case 0:
                    if (zzQ(t, i3)) {
                        iZzD = zzto.zzD(i4 << 3);
                        iZzo = iZzD + 8;
                        i2 += iZzo;
                    }
                    break;
                case 1:
                    if (zzQ(t, i3)) {
                        iZzD2 = zzto.zzD(i4 << 3);
                        iZzo = iZzD2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 2:
                    if (zzQ(t, i3)) {
                        long jZzd = zzxy.zzd(t, j);
                        iZzD3 = zzto.zzD(i4 << 3);
                        iZzE = zzto.zzE(jZzd);
                        i2 += iZzD3 + iZzE;
                    }
                    break;
                case 3:
                    if (zzQ(t, i3)) {
                        long jZzd2 = zzxy.zzd(t, j);
                        iZzD3 = zzto.zzD(i4 << 3);
                        iZzE = zzto.zzE(jZzd2);
                        i2 += iZzD3 + iZzE;
                    }
                    break;
                case 4:
                    if (zzQ(t, i3)) {
                        int iZzc = zzxy.zzc(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzx(iZzc);
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 5:
                    if (zzQ(t, i3)) {
                        iZzD = zzto.zzD(i4 << 3);
                        iZzo = iZzD + 8;
                        i2 += iZzo;
                    }
                    break;
                case 6:
                    if (zzQ(t, i3)) {
                        iZzD2 = zzto.zzD(i4 << 3);
                        iZzo = iZzD2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 7:
                    if (zzQ(t, i3)) {
                        iZzD5 = zzto.zzD(i4 << 3);
                        iZzo = iZzD5 + 1;
                        i2 += iZzo;
                    }
                    break;
                case 8:
                    if (zzQ(t, i3)) {
                        Object objZzf = zzxy.zzf(t, j);
                        if (objZzf instanceof zztd) {
                            iZzD6 = zzto.zzD(i4 << 3);
                            iZzd = ((zztd) objZzf).zzd();
                            iZzD7 = zzto.zzD(iZzd);
                            i = iZzD6 + iZzD7 + iZzd;
                            i2 += i;
                        } else {
                            iZzD4 = zzto.zzD(i4 << 3);
                            iZzx = zzto.zzB((String) objZzf);
                            i = iZzD4 + iZzx;
                            i2 += i;
                        }
                    }
                    break;
                case 9:
                    if (zzQ(t, i3)) {
                        iZzo = zzwz.zzo(i4, zzxy.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
                case 10:
                    if (zzQ(t, i3)) {
                        zztd zztdVar = (zztd) zzxy.zzf(t, j);
                        iZzD6 = zzto.zzD(i4 << 3);
                        iZzd = zztdVar.zzd();
                        iZzD7 = zzto.zzD(iZzd);
                        i = iZzD6 + iZzD7 + iZzd;
                        i2 += i;
                    }
                    break;
                case 11:
                    if (zzQ(t, i3)) {
                        int iZzc2 = zzxy.zzc(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzD(iZzc2);
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 12:
                    if (zzQ(t, i3)) {
                        int iZzc3 = zzxy.zzc(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzx(iZzc3);
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 13:
                    if (zzQ(t, i3)) {
                        iZzD2 = zzto.zzD(i4 << 3);
                        iZzo = iZzD2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 14:
                    if (zzQ(t, i3)) {
                        iZzD = zzto.zzD(i4 << 3);
                        iZzo = iZzD + 8;
                        i2 += iZzo;
                    }
                    break;
                case 15:
                    if (zzQ(t, i3)) {
                        int iZzc4 = zzxy.zzc(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzD((iZzc4 >> 31) ^ (iZzc4 + iZzc4));
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 16:
                    if (zzQ(t, i3)) {
                        long jZzd3 = zzxy.zzd(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzE((jZzd3 >> 63) ^ (jZzd3 + jZzd3));
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 17:
                    if (zzQ(t, i3)) {
                        iZzo = zzto.zzv(i4, (zzwk) zzxy.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
                case 18:
                    iZzo = zzwz.zzh(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 19:
                    iZzo = zzwz.zzf(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 20:
                    iZzo = zzwz.zzm(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 21:
                    iZzo = zzwz.zzx(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 22:
                    iZzo = zzwz.zzk(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 23:
                    iZzo = zzwz.zzh(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 24:
                    iZzo = zzwz.zzf(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 25:
                    iZzo = zzwz.zza(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 26:
                    iZzo = zzwz.zzu(i4, (List) zzxy.zzf(t, j));
                    i2 += iZzo;
                    break;
                case 27:
                    iZzo = zzwz.zzp(i4, (List) zzxy.zzf(t, j), zzF(i3));
                    i2 += iZzo;
                    break;
                case 28:
                    iZzo = zzwz.zzc(i4, (List) zzxy.zzf(t, j));
                    i2 += iZzo;
                    break;
                case 29:
                    iZzo = zzwz.zzv(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 30:
                    iZzo = zzwz.zzd(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 31:
                    iZzo = zzwz.zzf(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 32:
                    iZzo = zzwz.zzh(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 33:
                    iZzo = zzwz.zzq(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 34:
                    iZzo = zzwz.zzs(i4, (List) zzxy.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 35:
                    iZzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 36:
                    iZzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 37:
                    iZzx = zzwz.zzn((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 38:
                    iZzx = zzwz.zzy((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 39:
                    iZzx = zzwz.zzl((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 40:
                    iZzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 41:
                    iZzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 42:
                    iZzx = zzwz.zzb((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 43:
                    iZzx = zzwz.zzw((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 44:
                    iZzx = zzwz.zze((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 45:
                    iZzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 46:
                    iZzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 47:
                    iZzx = zzwz.zzr((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 48:
                    iZzx = zzwz.zzt((List) unsafe.getObject(t, j));
                    if (iZzx > 0) {
                        iZzC = zzto.zzC(i4);
                        iZzD8 = zzto.zzD(iZzx);
                        iZzD4 = iZzC + iZzD8;
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 49:
                    iZzo = zzwz.zzj(i4, (List) zzxy.zzf(t, j), zzF(i3));
                    i2 += iZzo;
                    break;
                case 50:
                    zzwf.zza(i4, zzxy.zzf(t, j), zzH(i3));
                    break;
                case 51:
                    if (zzT(t, i4, i3)) {
                        iZzD = zzto.zzD(i4 << 3);
                        iZzo = iZzD + 8;
                        i2 += iZzo;
                    }
                    break;
                case 52:
                    if (zzT(t, i4, i3)) {
                        iZzD2 = zzto.zzD(i4 << 3);
                        iZzo = iZzD2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 53:
                    if (zzT(t, i4, i3)) {
                        long jZzD = zzD(t, j);
                        iZzD3 = zzto.zzD(i4 << 3);
                        iZzE = zzto.zzE(jZzD);
                        i2 += iZzD3 + iZzE;
                    }
                    break;
                case 54:
                    if (zzT(t, i4, i3)) {
                        long jZzD2 = zzD(t, j);
                        iZzD3 = zzto.zzD(i4 << 3);
                        iZzE = zzto.zzE(jZzD2);
                        i2 += iZzD3 + iZzE;
                    }
                    break;
                case 55:
                    if (zzT(t, i4, i3)) {
                        int iZzs = zzs(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzx(iZzs);
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 56:
                    if (zzT(t, i4, i3)) {
                        iZzD = zzto.zzD(i4 << 3);
                        iZzo = iZzD + 8;
                        i2 += iZzo;
                    }
                    break;
                case 57:
                    if (zzT(t, i4, i3)) {
                        iZzD2 = zzto.zzD(i4 << 3);
                        iZzo = iZzD2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 58:
                    if (zzT(t, i4, i3)) {
                        iZzD5 = zzto.zzD(i4 << 3);
                        iZzo = iZzD5 + 1;
                        i2 += iZzo;
                    }
                    break;
                case 59:
                    if (zzT(t, i4, i3)) {
                        Object objZzf2 = zzxy.zzf(t, j);
                        if (objZzf2 instanceof zztd) {
                            iZzD6 = zzto.zzD(i4 << 3);
                            iZzd = ((zztd) objZzf2).zzd();
                            iZzD7 = zzto.zzD(iZzd);
                            i = iZzD6 + iZzD7 + iZzd;
                            i2 += i;
                        } else {
                            iZzD4 = zzto.zzD(i4 << 3);
                            iZzx = zzto.zzB((String) objZzf2);
                            i = iZzD4 + iZzx;
                            i2 += i;
                        }
                    }
                    break;
                case 60:
                    if (zzT(t, i4, i3)) {
                        iZzo = zzwz.zzo(i4, zzxy.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
                case 61:
                    if (zzT(t, i4, i3)) {
                        zztd zztdVar2 = (zztd) zzxy.zzf(t, j);
                        iZzD6 = zzto.zzD(i4 << 3);
                        iZzd = zztdVar2.zzd();
                        iZzD7 = zzto.zzD(iZzd);
                        i = iZzD6 + iZzD7 + iZzd;
                        i2 += i;
                    }
                    break;
                case 62:
                    if (zzT(t, i4, i3)) {
                        int iZzs2 = zzs(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzD(iZzs2);
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 63:
                    if (zzT(t, i4, i3)) {
                        int iZzs3 = zzs(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzx(iZzs3);
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 64:
                    if (zzT(t, i4, i3)) {
                        iZzD2 = zzto.zzD(i4 << 3);
                        iZzo = iZzD2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 65:
                    if (zzT(t, i4, i3)) {
                        iZzD = zzto.zzD(i4 << 3);
                        iZzo = iZzD + 8;
                        i2 += iZzo;
                    }
                    break;
                case 66:
                    if (zzT(t, i4, i3)) {
                        int iZzs4 = zzs(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzD((iZzs4 >> 31) ^ (iZzs4 + iZzs4));
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 67:
                    if (zzT(t, i4, i3)) {
                        long jZzD3 = zzD(t, j);
                        iZzD4 = zzto.zzD(i4 << 3);
                        iZzx = zzto.zzE((jZzD3 >> 63) ^ (jZzD3 + jZzD3));
                        i = iZzD4 + iZzx;
                        i2 += i;
                    }
                    break;
                case 68:
                    if (zzT(t, i4, i3)) {
                        iZzo = zzto.zzv(i4, (zzwk) zzxy.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
            }
        }
        zzxo<?, ?> zzxoVar = this.zzo;
        return i2 + zzxoVar.zza(zzxoVar.zzd(t));
    }

    private static <T> int zzs(T t, long j) {
        return ((Integer) zzxy.zzf(t, j)).intValue();
    }

    private final <K, V> int zzt(T t, byte[] bArr, int i, int i2, int i3, long j, zzsl zzslVar) throws IOException {
        Unsafe unsafe = zzb;
        Object objZzH = zzH(i3);
        Object object = unsafe.getObject(t, j);
        if (zzwf.zzb(object)) {
            zzwe<K, V> zzweVarZzb = zzwe.zza().zzb();
            zzwf.zzc(zzweVarZzb, object);
            unsafe.putObject(t, j, zzweVarZzb);
        }
        throw null;
    }

    private final int zzu(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzsl zzslVar) throws IOException {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Double.valueOf(Double.longBitsToDouble(zzsm.zzo(bArr, i))));
                unsafe.putInt(t, j2, i4);
                return i + 8;
            case 52:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Float.valueOf(Float.intBitsToFloat(zzsm.zzb(bArr, i))));
                unsafe.putInt(t, j2, i4);
                return i + 4;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                int iZzm = zzsm.zzm(bArr, i, zzslVar);
                unsafe.putObject(t, j, Long.valueOf(zzslVar.zzb));
                unsafe.putInt(t, j2, i4);
                return iZzm;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                int iZzj = zzsm.zzj(bArr, i, zzslVar);
                unsafe.putObject(t, j, Integer.valueOf(zzslVar.zza));
                unsafe.putInt(t, j2, i4);
                return iZzj;
            case 56:
            case 65:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Long.valueOf(zzsm.zzo(bArr, i)));
                unsafe.putInt(t, j2, i4);
                return i + 8;
            case 57:
            case 64:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Integer.valueOf(zzsm.zzb(bArr, i)));
                unsafe.putInt(t, j2, i4);
                return i + 4;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                int iZzm2 = zzsm.zzm(bArr, i, zzslVar);
                unsafe.putObject(t, j, Boolean.valueOf(zzslVar.zzb != 0));
                unsafe.putInt(t, j2, i4);
                return iZzm2;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZzj2 = zzsm.zzj(bArr, i, zzslVar);
                int i9 = zzslVar.zza;
                if (i9 == 0) {
                    unsafe.putObject(t, j, "");
                } else {
                    if ((i6 & 536870912) != 0 && !zzyd.zzf(bArr, iZzj2, iZzj2 + i9)) {
                        throw zzvk.zzd();
                    }
                    unsafe.putObject(t, j, new String(bArr, iZzj2, i9, zzvi.zza));
                    iZzj2 += i9;
                }
                unsafe.putInt(t, j2, i4);
                return iZzj2;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZzd = zzsm.zzd(zzF(i8), bArr, i, i2, zzslVar);
                Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object == null) {
                    unsafe.putObject(t, j, zzslVar.zzc);
                } else {
                    unsafe.putObject(t, j, zzvi.zzg(object, zzslVar.zzc));
                }
                unsafe.putInt(t, j2, i4);
                return iZzd;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzsm.zza(bArr, i, zzslVar);
                unsafe.putObject(t, j, zzslVar.zzc);
                unsafe.putInt(t, j2, i4);
                return iZza;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZzj3 = zzsm.zzj(bArr, i, zzslVar);
                int i10 = zzslVar.zza;
                zzvd zzvdVarZzE = zzE(i8);
                if (zzvdVarZzE == null || zzvdVarZzE.zza(i10)) {
                    unsafe.putObject(t, j, Integer.valueOf(i10));
                    unsafe.putInt(t, j2, i4);
                } else {
                    zzd(t).zzh(i3, Long.valueOf(i10));
                }
                return iZzj3;
            case 66:
                if (i5 != 0) {
                    return i;
                }
                int iZzj4 = zzsm.zzj(bArr, i, zzslVar);
                unsafe.putObject(t, j, Integer.valueOf(zztj.zzs(zzslVar.zza)));
                unsafe.putInt(t, j2, i4);
                return iZzj4;
            case 67:
                if (i5 != 0) {
                    return i;
                }
                int iZzm3 = zzsm.zzm(bArr, i, zzslVar);
                unsafe.putObject(t, j, Long.valueOf(zztj.zzt(zzslVar.zzb)));
                unsafe.putInt(t, j2, i4);
                return iZzm3;
            case 68:
                if (i5 != 3) {
                    return i;
                }
                int iZzc = zzsm.zzc(zzF(i8), bArr, i, i2, (i3 & (-8)) | 4, zzslVar);
                Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object2 == null) {
                    unsafe.putObject(t, j, zzslVar.zzc);
                } else {
                    unsafe.putObject(t, j, zzvi.zzg(object2, zzslVar.zzc));
                }
                unsafe.putInt(t, j2, i4);
                return iZzc;
            default:
                return i;
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x02a3 A[PHI: r0 r18 r23 r26 r27 r28
      0x02a3: PHI (r0v55 int) = (r0v50 int), (r0v53 int), (r0v57 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02a3: PHI (r18v5 int) = (r18v3 int), (r18v3 int), (r18v6 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02a3: PHI (r23v3 int) = (r23v1 int), (r23v1 int), (r23v4 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02a3: PHI (r26v2 int) = (r26v0 int), (r26v0 int), (r26v3 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02a3: PHI (r27v6 int) = (r27v4 int), (r27v4 int), (r27v7 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02a3: PHI (r28v7 sun.misc.Unsafe) = (r28v5 sun.misc.Unsafe), (r28v5 sun.misc.Unsafe), (r28v8 sun.misc.Unsafe) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:102:0x02b7 A[PHI: r0 r18 r23 r26 r27 r28
      0x02b7: PHI (r0v54 int) = (r0v50 int), (r0v53 int), (r0v57 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02b7: PHI (r18v4 int) = (r18v3 int), (r18v3 int), (r18v6 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02b7: PHI (r23v2 int) = (r23v1 int), (r23v1 int), (r23v4 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02b7: PHI (r26v1 int) = (r26v0 int), (r26v0 int), (r26v3 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02b7: PHI (r27v5 int) = (r27v4 int), (r27v4 int), (r27v7 int) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]
      0x02b7: PHI (r28v6 sun.misc.Unsafe) = (r28v5 sun.misc.Unsafe), (r28v5 sun.misc.Unsafe), (r28v8 sun.misc.Unsafe) binds: [B:113:0x030b, B:108:0x02e8, B:100:0x02a1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0085. Please report as an issue. */
    private final int zzv(T t, byte[] bArr, int i, int i2, zzsl zzslVar) throws IOException {
        int i3;
        int iZzk;
        int i4;
        int i5;
        int i6;
        Unsafe unsafe;
        int i7;
        int i8;
        int i9;
        int iZzm;
        int iZzd;
        int i10;
        int i11;
        int i12;
        this = this;
        t = t;
        bArr = bArr;
        i2 = i2;
        zzslVar = zzslVar;
        Unsafe unsafe2 = zzb;
        int i13 = 1048575;
        int i14 = -1;
        int iZzi = i;
        int i15 = -1;
        int i16 = 1048575;
        int i17 = 0;
        int i18 = 0;
        while (iZzi < i2) {
            int i19 = iZzi + 1;
            byte b = bArr[iZzi];
            if (b < 0) {
                iZzk = zzsm.zzk(b, bArr, i19, zzslVar);
                i3 = zzslVar.zza;
            } else {
                i3 = b;
                iZzk = i19;
            }
            int i20 = i3 >>> 3;
            int i21 = i3 & 7;
            int iZzy = i20 > i15 ? this.zzy(i20, i17 / 3) : this.zzx(i20);
            if (iZzy == i14) {
                i4 = iZzk;
                i5 = i20;
                i6 = i14;
                unsafe = unsafe2;
                i7 = 0;
            } else {
                int i22 = this.zzc[iZzy + 1];
                int iZzB = zzB(i22);
                long j = i22 & i13;
                if (iZzB <= 17) {
                    int i23 = this.zzc[iZzy + 2];
                    int i24 = 1 << (i23 >>> 20);
                    i13 = 1048575;
                    int i25 = i23 & 1048575;
                    if (i25 != i16) {
                        if (i16 != 1048575) {
                            unsafe2.putInt(t, i16, i18);
                            i13 = 1048575;
                        }
                        if (i25 != i13) {
                            i18 = unsafe2.getInt(t, i25);
                        }
                        i16 = i25;
                    }
                    switch (iZzB) {
                        case 0:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 1) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                zzxy.zzo(t, j, Double.longBitsToDouble(zzsm.zzo(bArr, iZzk)));
                                iZzi = iZzk + 8;
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 1:
                            i5 = i20;
                            i9 = i18;
                            i8 = iZzy;
                            if (i21 != 5) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                zzxy.zzp(t, j, Float.intBitsToFloat(zzsm.zzb(bArr, iZzk)));
                                iZzi = iZzk + 4;
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 2:
                        case 3:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 0) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzm = zzsm.zzm(bArr, iZzk, zzslVar);
                                unsafe2.putLong(t, j, zzslVar.zzb);
                                i18 = i9 | i24;
                                i17 = i8;
                                iZzi = iZzm;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 4:
                        case 11:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 0) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzi = zzsm.zzj(bArr, iZzk, zzslVar);
                                unsafe2.putInt(t, j, zzslVar.zza);
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 5:
                        case 14:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 1) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                unsafe2.putLong(t, j, zzsm.zzo(bArr, iZzk));
                                iZzi = iZzk + 8;
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 6:
                        case 13:
                            i5 = i20;
                            i9 = i18;
                            i8 = iZzy;
                            if (i21 != 5) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                unsafe2.putInt(t, j, zzsm.zzb(bArr, iZzk));
                                iZzi = iZzk + 4;
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 7:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 0) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                int iZzm2 = zzsm.zzm(bArr, iZzk, zzslVar);
                                zzxy.zzm(t, j, zzslVar.zzb != 0);
                                i18 = i9 | i24;
                                iZzi = iZzm2;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 8:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 2) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzi = (536870912 & i22) == 0 ? zzsm.zzg(bArr, iZzk, zzslVar) : zzsm.zzh(bArr, iZzk, zzslVar);
                                unsafe2.putObject(t, j, zzslVar.zzc);
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 9:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            if (i21 != 2) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzd = zzsm.zzd(this.zzF(i8), bArr, iZzk, i2, zzslVar);
                                Object object = unsafe2.getObject(t, j);
                                if (object == null) {
                                    unsafe2.putObject(t, j, zzslVar.zzc);
                                } else {
                                    unsafe2.putObject(t, j, zzvi.zzg(object, zzslVar.zzc));
                                }
                                i18 |= i24;
                                iZzi = iZzd;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 10:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            if (i21 != 2) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzd = zzsm.zza(bArr, iZzk, zzslVar);
                                unsafe2.putObject(t, j, zzslVar.zzc);
                                i18 |= i24;
                                iZzi = iZzd;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 12:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            if (i21 != 0) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzd = zzsm.zzj(bArr, iZzk, zzslVar);
                                unsafe2.putInt(t, j, zzslVar.zza);
                                i18 |= i24;
                                iZzi = iZzd;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 15:
                            i5 = i20;
                            zzslVar = zzslVar;
                            i8 = iZzy;
                            if (i21 != 0) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzi = zzsm.zzj(bArr, iZzk, zzslVar);
                                unsafe2.putInt(t, j, zztj.zzs(zzslVar.zza));
                                i18 |= i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 16:
                            if (i21 != 0) {
                                i5 = i20;
                                i8 = iZzy;
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                zzslVar = zzslVar;
                                iZzm = zzsm.zzm(bArr, iZzk, zzslVar);
                                i8 = iZzy;
                                i5 = i20;
                                unsafe2.putLong(t, j, zztj.zzt(zzslVar.zzb));
                                i18 |= i24;
                                i17 = i8;
                                iZzi = iZzm;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        default:
                            i5 = i20;
                            i8 = iZzy;
                            i9 = i18;
                            i18 = i9;
                            i4 = iZzk;
                            i7 = i8;
                            unsafe = unsafe2;
                            i6 = -1;
                            break;
                    }
                } else {
                    i5 = i20;
                    int i26 = i18;
                    i13 = 1048575;
                    zzslVar = zzslVar;
                    if (iZzB != 27) {
                        if (iZzB <= 49) {
                            i11 = i26;
                            int i27 = iZzk;
                            i10 = i16;
                            i7 = iZzy;
                            i6 = -1;
                            unsafe = unsafe2;
                            iZzi = zzw(t, bArr, iZzk, i2, i3, i5, i21, iZzy, i22, iZzB, j, zzslVar);
                            if (iZzi != i27) {
                                i17 = i7;
                                i15 = i5;
                                i18 = i11;
                                i16 = i10;
                            } else {
                                i4 = iZzi;
                                i18 = i11;
                                i16 = i10;
                            }
                        } else {
                            i10 = i16;
                            i7 = iZzy;
                            unsafe = unsafe2;
                            i11 = i26;
                            i6 = -1;
                            i12 = iZzk;
                            if (iZzB == 50) {
                                if (i21 == 2) {
                                    iZzi = zzt(t, bArr, i12, i2, i7, j, zzslVar);
                                    if (iZzi != i12) {
                                        i17 = i7;
                                        i15 = i5;
                                        i18 = i11;
                                        i16 = i10;
                                    } else {
                                        i4 = iZzi;
                                    }
                                }
                                i18 = i11;
                                i16 = i10;
                            } else {
                                iZzi = zzu(t, bArr, i12, i2, i3, i5, i21, i22, iZzB, j, i7, zzslVar);
                                if (iZzi != i12) {
                                    i17 = i7;
                                    i15 = i5;
                                    i18 = i11;
                                    i16 = i10;
                                } else {
                                    i4 = iZzi;
                                    i18 = i11;
                                    i16 = i10;
                                }
                            }
                        }
                        i14 = i6;
                        unsafe2 = unsafe;
                        i13 = 1048575;
                    } else if (i21 == 2) {
                        zzvh zzvhVarZzd = (zzvh) unsafe2.getObject(t, j);
                        if (!zzvhVarZzd.zzc()) {
                            int size = zzvhVarZzd.size();
                            zzvhVarZzd = zzvhVarZzd.zzd(size == 0 ? 10 : size + size);
                            unsafe2.putObject(t, j, zzvhVarZzd);
                        }
                        iZzi = zzsm.zze(this.zzF(iZzy), i3, bArr, iZzk, i2, zzvhVarZzd, zzslVar);
                        i17 = iZzy;
                        i18 = i26;
                        i15 = i5;
                        i14 = -1;
                        this = this;
                    } else {
                        i12 = iZzk;
                        i10 = i16;
                        i7 = iZzy;
                        unsafe = unsafe2;
                        i11 = i26;
                        i6 = -1;
                    }
                    i4 = i12;
                    i18 = i11;
                    i16 = i10;
                }
            }
            iZzi = zzsm.zzi(i3, bArr, i4, i2, zzd(t), zzslVar);
            i17 = i7;
            i15 = i5;
            i14 = i6;
            unsafe2 = unsafe;
            i13 = 1048575;
        }
        int i28 = i18;
        Unsafe unsafe3 = unsafe2;
        if (i16 != i13) {
            unsafe3.putInt(t, i16, i28);
        }
        if (iZzi == i2) {
            return iZzi;
        }
        throw zzvk.zzg();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int zzw(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzsl zzslVar) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        int iZzj;
        int iZzj2 = i;
        Unsafe unsafe = zzb;
        zzvh zzvhVarZzd = (zzvh) unsafe.getObject(t, j2);
        if (!zzvhVarZzd.zzc()) {
            int size = zzvhVarZzd.size();
            zzvhVarZzd = zzvhVarZzd.zzd(size == 0 ? 10 : size + size);
            unsafe.putObject(t, j2, zzvhVarZzd);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzug zzugVar = (zzug) zzvhVarZzd;
                    int iZzj3 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i12 = zzslVar.zza + iZzj3;
                    while (iZzj3 < i12) {
                        zzugVar.zze(Double.longBitsToDouble(zzsm.zzo(bArr, iZzj3)));
                        iZzj3 += 8;
                    }
                    if (iZzj3 == i12) {
                        return iZzj3;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 1) {
                    zzug zzugVar2 = (zzug) zzvhVarZzd;
                    zzugVar2.zze(Double.longBitsToDouble(zzsm.zzo(bArr, i)));
                    while (true) {
                        i8 = iZzj2 + 8;
                        if (i8 < i2) {
                            iZzj2 = zzsm.zzj(bArr, i8, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzugVar2.zze(Double.longBitsToDouble(zzsm.zzo(bArr, iZzj2)));
                            }
                        }
                    }
                    return i8;
                }
                return iZzj2;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzuq zzuqVar = (zzuq) zzvhVarZzd;
                    int iZzj4 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i13 = zzslVar.zza + iZzj4;
                    while (iZzj4 < i13) {
                        zzuqVar.zze(Float.intBitsToFloat(zzsm.zzb(bArr, iZzj4)));
                        iZzj4 += 4;
                    }
                    if (iZzj4 == i13) {
                        return iZzj4;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 5) {
                    zzuq zzuqVar2 = (zzuq) zzvhVarZzd;
                    zzuqVar2.zze(Float.intBitsToFloat(zzsm.zzb(bArr, i)));
                    while (true) {
                        i9 = iZzj2 + 4;
                        if (i9 < i2) {
                            iZzj2 = zzsm.zzj(bArr, i9, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzuqVar2.zze(Float.intBitsToFloat(zzsm.zzb(bArr, iZzj2)));
                            }
                        }
                    }
                    return i9;
                }
                return iZzj2;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzvz zzvzVar = (zzvz) zzvhVarZzd;
                    int iZzj5 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i14 = zzslVar.zza + iZzj5;
                    while (iZzj5 < i14) {
                        iZzj5 = zzsm.zzm(bArr, iZzj5, zzslVar);
                        zzvzVar.zzf(zzslVar.zzb);
                    }
                    if (iZzj5 == i14) {
                        return iZzj5;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 0) {
                    zzvz zzvzVar2 = (zzvz) zzvhVarZzd;
                    int iZzm = zzsm.zzm(bArr, iZzj2, zzslVar);
                    zzvzVar2.zzf(zzslVar.zzb);
                    while (iZzm < i2) {
                        int iZzj6 = zzsm.zzj(bArr, iZzm, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return iZzm;
                        }
                        iZzm = zzsm.zzm(bArr, iZzj6, zzslVar);
                        zzvzVar2.zzf(zzslVar.zzb);
                    }
                    return iZzm;
                }
                return iZzj2;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzsm.zzf(bArr, iZzj2, zzvhVarZzd, zzslVar);
                }
                if (i5 == 0) {
                    return zzsm.zzl(i3, bArr, i, i2, zzvhVarZzd, zzslVar);
                }
                return iZzj2;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzvz zzvzVar3 = (zzvz) zzvhVarZzd;
                    int iZzj7 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i15 = zzslVar.zza + iZzj7;
                    while (iZzj7 < i15) {
                        zzvzVar3.zzf(zzsm.zzo(bArr, iZzj7));
                        iZzj7 += 8;
                    }
                    if (iZzj7 == i15) {
                        return iZzj7;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 1) {
                    zzvz zzvzVar4 = (zzvz) zzvhVarZzd;
                    zzvzVar4.zzf(zzsm.zzo(bArr, i));
                    while (true) {
                        i10 = iZzj2 + 8;
                        if (i10 < i2) {
                            iZzj2 = zzsm.zzj(bArr, i10, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzvzVar4.zzf(zzsm.zzo(bArr, iZzj2));
                            }
                        }
                    }
                    return i10;
                }
                return iZzj2;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzva zzvaVar = (zzva) zzvhVarZzd;
                    int iZzj8 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i16 = zzslVar.zza + iZzj8;
                    while (iZzj8 < i16) {
                        zzvaVar.zzh(zzsm.zzb(bArr, iZzj8));
                        iZzj8 += 4;
                    }
                    if (iZzj8 == i16) {
                        return iZzj8;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 5) {
                    zzva zzvaVar2 = (zzva) zzvhVarZzd;
                    zzvaVar2.zzh(zzsm.zzb(bArr, i));
                    while (true) {
                        i11 = iZzj2 + 4;
                        if (i11 < i2) {
                            iZzj2 = zzsm.zzj(bArr, i11, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzvaVar2.zzh(zzsm.zzb(bArr, iZzj2));
                            }
                        }
                    }
                    return i11;
                }
                return iZzj2;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzsr zzsrVar = (zzsr) zzvhVarZzd;
                    iZzj = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i17 = zzslVar.zza + iZzj;
                    while (iZzj < i17) {
                        iZzj = zzsm.zzm(bArr, iZzj, zzslVar);
                        zzsrVar.zze(zzslVar.zzb != 0);
                    }
                    if (iZzj != i17) {
                        throw zzvk.zzj();
                    }
                    return iZzj;
                }
                if (i5 == 0) {
                    zzsr zzsrVar2 = (zzsr) zzvhVarZzd;
                    int iZzm2 = zzsm.zzm(bArr, iZzj2, zzslVar);
                    zzsrVar2.zze(zzslVar.zzb != 0);
                    while (iZzm2 < i2) {
                        int iZzj9 = zzsm.zzj(bArr, iZzm2, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return iZzm2;
                        }
                        iZzm2 = zzsm.zzm(bArr, iZzj9, zzslVar);
                        zzsrVar2.zze(zzslVar.zzb != 0);
                    }
                    return iZzm2;
                }
                return iZzj2;
            case 26:
                if (i5 == 2) {
                    if ((j & TagBits.HasTypeVariable) == 0) {
                        int iZzj10 = zzsm.zzj(bArr, iZzj2, zzslVar);
                        int i18 = zzslVar.zza;
                        if (i18 < 0) {
                            throw zzvk.zzf();
                        }
                        if (i18 == 0) {
                            zzvhVarZzd.add("");
                        } else {
                            zzvhVarZzd.add(new String(bArr, iZzj10, i18, zzvi.zza));
                            iZzj10 += i18;
                        }
                        while (iZzj10 < i2) {
                            int iZzj11 = zzsm.zzj(bArr, iZzj10, zzslVar);
                            if (i3 != zzslVar.zza) {
                                return iZzj10;
                            }
                            iZzj10 = zzsm.zzj(bArr, iZzj11, zzslVar);
                            int i19 = zzslVar.zza;
                            if (i19 < 0) {
                                throw zzvk.zzf();
                            }
                            if (i19 == 0) {
                                zzvhVarZzd.add("");
                            } else {
                                zzvhVarZzd.add(new String(bArr, iZzj10, i19, zzvi.zza));
                                iZzj10 += i19;
                            }
                        }
                        return iZzj10;
                    }
                    int iZzj12 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i20 = zzslVar.zza;
                    if (i20 < 0) {
                        throw zzvk.zzf();
                    }
                    if (i20 == 0) {
                        zzvhVarZzd.add("");
                    } else {
                        int i21 = iZzj12 + i20;
                        if (!zzyd.zzf(bArr, iZzj12, i21)) {
                            throw zzvk.zzd();
                        }
                        zzvhVarZzd.add(new String(bArr, iZzj12, i20, zzvi.zza));
                        iZzj12 = i21;
                    }
                    while (iZzj12 < i2) {
                        int iZzj13 = zzsm.zzj(bArr, iZzj12, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return iZzj12;
                        }
                        iZzj12 = zzsm.zzj(bArr, iZzj13, zzslVar);
                        int i22 = zzslVar.zza;
                        if (i22 < 0) {
                            throw zzvk.zzf();
                        }
                        if (i22 == 0) {
                            zzvhVarZzd.add("");
                        } else {
                            int i23 = iZzj12 + i22;
                            if (!zzyd.zzf(bArr, iZzj12, i23)) {
                                throw zzvk.zzd();
                            }
                            zzvhVarZzd.add(new String(bArr, iZzj12, i22, zzvi.zza));
                            iZzj12 = i23;
                        }
                    }
                    return iZzj12;
                }
                return iZzj2;
            case 27:
                if (i5 == 2) {
                    return zzsm.zze(zzF(i6), i3, bArr, i, i2, zzvhVarZzd, zzslVar);
                }
                return iZzj2;
            case 28:
                if (i5 == 2) {
                    int iZzj14 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i24 = zzslVar.zza;
                    if (i24 < 0) {
                        throw zzvk.zzf();
                    }
                    if (i24 > bArr.length - iZzj14) {
                        throw zzvk.zzj();
                    }
                    if (i24 == 0) {
                        zzvhVarZzd.add(zztd.zzb);
                    } else {
                        zzvhVarZzd.add(zztd.zzn(bArr, iZzj14, i24));
                        iZzj14 += i24;
                    }
                    while (iZzj14 < i2) {
                        int iZzj15 = zzsm.zzj(bArr, iZzj14, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return iZzj14;
                        }
                        iZzj14 = zzsm.zzj(bArr, iZzj15, zzslVar);
                        int i25 = zzslVar.zza;
                        if (i25 < 0) {
                            throw zzvk.zzf();
                        }
                        if (i25 > bArr.length - iZzj14) {
                            throw zzvk.zzj();
                        }
                        if (i25 == 0) {
                            zzvhVarZzd.add(zztd.zzb);
                        } else {
                            zzvhVarZzd.add(zztd.zzn(bArr, iZzj14, i25));
                            iZzj14 += i25;
                        }
                    }
                    return iZzj14;
                }
                return iZzj2;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZzj = zzsm.zzl(i3, bArr, i, i2, zzvhVarZzd, zzslVar);
                    }
                    return iZzj2;
                }
                iZzj = zzsm.zzf(bArr, iZzj2, zzvhVarZzd, zzslVar);
                zzuz zzuzVar = (zzuz) t;
                zzxp zzxpVar = zzuzVar.zzc;
                if (zzxpVar == zzxp.zzc()) {
                    zzxpVar = null;
                }
                Object objZzC = zzwz.zzC(i4, zzvhVarZzd, zzE(i6), zzxpVar, this.zzo);
                if (objZzC != null) {
                    zzuzVar.zzc = (zzxp) objZzC;
                    return iZzj;
                }
                return iZzj;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzva zzvaVar3 = (zzva) zzvhVarZzd;
                    int iZzj16 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i26 = zzslVar.zza + iZzj16;
                    while (iZzj16 < i26) {
                        iZzj16 = zzsm.zzj(bArr, iZzj16, zzslVar);
                        zzvaVar3.zzh(zztj.zzs(zzslVar.zza));
                    }
                    if (iZzj16 == i26) {
                        return iZzj16;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 0) {
                    zzva zzvaVar4 = (zzva) zzvhVarZzd;
                    int iZzj17 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    zzvaVar4.zzh(zztj.zzs(zzslVar.zza));
                    while (iZzj17 < i2) {
                        int iZzj18 = zzsm.zzj(bArr, iZzj17, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return iZzj17;
                        }
                        iZzj17 = zzsm.zzj(bArr, iZzj18, zzslVar);
                        zzvaVar4.zzh(zztj.zzs(zzslVar.zza));
                    }
                    return iZzj17;
                }
                return iZzj2;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzvz zzvzVar5 = (zzvz) zzvhVarZzd;
                    int iZzj19 = zzsm.zzj(bArr, iZzj2, zzslVar);
                    int i27 = zzslVar.zza + iZzj19;
                    while (iZzj19 < i27) {
                        iZzj19 = zzsm.zzm(bArr, iZzj19, zzslVar);
                        zzvzVar5.zzf(zztj.zzt(zzslVar.zzb));
                    }
                    if (iZzj19 == i27) {
                        return iZzj19;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 0) {
                    zzvz zzvzVar6 = (zzvz) zzvhVarZzd;
                    int iZzm3 = zzsm.zzm(bArr, iZzj2, zzslVar);
                    zzvzVar6.zzf(zztj.zzt(zzslVar.zzb));
                    while (iZzm3 < i2) {
                        int iZzj20 = zzsm.zzj(bArr, iZzm3, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return iZzm3;
                        }
                        iZzm3 = zzsm.zzm(bArr, iZzj20, zzslVar);
                        zzvzVar6.zzf(zztj.zzt(zzslVar.zzb));
                    }
                    return iZzm3;
                }
                return iZzj2;
            default:
                if (i5 == 3) {
                    zzwx zzwxVarZzF = zzF(i6);
                    int i28 = (i3 & (-8)) | 4;
                    int iZzc = zzsm.zzc(zzwxVarZzF, bArr, i, i2, i28, zzslVar);
                    zzvhVarZzd.add(zzslVar.zzc);
                    while (iZzc < i2) {
                        int iZzj21 = zzsm.zzj(bArr, iZzc, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return iZzc;
                        }
                        iZzc = zzsm.zzc(zzwxVarZzF, bArr, iZzj21, i2, i28, zzslVar);
                        zzvhVarZzd.add(zzslVar.zzc);
                    }
                    return iZzc;
                }
                return iZzj2;
        }
    }

    private final int zzx(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzA(i, 0);
    }

    private final int zzy(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzA(i, i2);
    }

    private final int zzz(int i) {
        return this.zzc[i + 2];
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zza(T t) {
        return this.zzj ? zzr(t) : zzq(t);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zzb(T t) {
        int i;
        int iZzc;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzC = zzC(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzC;
            int iHashCode = 37;
            switch (zzB(iZzC)) {
                case 0:
                    i = i2 * 53;
                    iZzc = zzvi.zzc(Double.doubleToLongBits(zzxy.zza(t, j)));
                    i2 = i + iZzc;
                    break;
                case 1:
                    i = i2 * 53;
                    iZzc = Float.floatToIntBits(zzxy.zzb(t, j));
                    i2 = i + iZzc;
                    break;
                case 2:
                    i = i2 * 53;
                    iZzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 3:
                    i = i2 * 53;
                    iZzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 4:
                    i = i2 * 53;
                    iZzc = zzxy.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 5:
                    i = i2 * 53;
                    iZzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 6:
                    i = i2 * 53;
                    iZzc = zzxy.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 7:
                    i = i2 * 53;
                    iZzc = zzvi.zza(zzxy.zzw(t, j));
                    i2 = i + iZzc;
                    break;
                case 8:
                    i = i2 * 53;
                    iZzc = ((String) zzxy.zzf(t, j)).hashCode();
                    i2 = i + iZzc;
                    break;
                case 9:
                    Object objZzf = zzxy.zzf(t, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZzc = zzxy.zzf(t, j).hashCode();
                    i2 = i + iZzc;
                    break;
                case 11:
                    i = i2 * 53;
                    iZzc = zzxy.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 12:
                    i = i2 * 53;
                    iZzc = zzxy.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 13:
                    i = i2 * 53;
                    iZzc = zzxy.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 14:
                    i = i2 * 53;
                    iZzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 15:
                    i = i2 * 53;
                    iZzc = zzxy.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 16:
                    i = i2 * 53;
                    iZzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 17:
                    Object objZzf2 = zzxy.zzf(t, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    iZzc = zzxy.zzf(t, j).hashCode();
                    i2 = i + iZzc;
                    break;
                case 50:
                    i = i2 * 53;
                    iZzc = zzxy.zzf(t, j).hashCode();
                    i2 = i + iZzc;
                    break;
                case 51:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzvi.zzc(Double.doubleToLongBits(zzo(t, j)));
                        i2 = i + iZzc;
                    }
                    break;
                case 52:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = Float.floatToIntBits(zzp(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 53:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzvi.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 54:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzvi.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 55:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 56:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzvi.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 57:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 58:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzvi.zza(zzU(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 59:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = ((String) zzxy.zzf(t, j)).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
                case 60:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzxy.zzf(t, j).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
                case 61:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzxy.zzf(t, j).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
                case 62:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 63:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 64:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 65:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzvi.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 66:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 67:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzvi.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 68:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzxy.zzf(t, j).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzo.zzd(t).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzp.zzb(t).zza.hashCode() : iHashCode2;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 15581. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    final int zzc(T r32, byte[] r33, int r34, int r35, int r36, com.google.android.gms.internal.gtm.zzsl r37) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1558
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzwn.zzc(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.gtm.zzsl):int");
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final T zze() {
        return (T) ((zzuz) this.zzg).zzb(4, null, null);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzf(T t) {
        int i;
        int i2 = this.zzl;
        while (true) {
            i = this.zzm;
            if (i2 >= i) {
                break;
            }
            long jZzC = zzC(this.zzk[i2]) & 1048575;
            Object objZzf = zzxy.zzf(t, jZzC);
            if (objZzf != null) {
                ((zzwe) objZzf).zzc();
                zzxy.zzs(t, jZzC, objZzf);
            }
            i2++;
        }
        int length = this.zzk.length;
        while (i < length) {
            this.zzn.zzb(t, this.zzk[i]);
            i++;
        }
        this.zzo.zzm(t);
        if (this.zzh) {
            this.zzp.zzf(t);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzh(T t, zzww zzwwVar, zzuj zzujVar) throws IOException {
        zzujVar.getClass();
        zzxo<?, ?> zzxoVar = this.zzo;
        zzuk<?> zzukVar = this.zzp;
        zzuo zzuoVarZzc = null;
        Object objZze = null;
        while (true) {
            try {
                int iZzc = zzwwVar.zzc();
                int iZzx = zzx(iZzc);
                if (iZzx >= 0) {
                    int iZzC = zzC(iZzx);
                    try {
                        switch (zzB(iZzC)) {
                            case 0:
                                zzxy.zzo(t, iZzC & 1048575, zzwwVar.zza());
                                zzM(t, iZzx);
                                break;
                            case 1:
                                zzxy.zzp(t, iZzC & 1048575, zzwwVar.zzb());
                                zzM(t, iZzx);
                                break;
                            case 2:
                                zzxy.zzr(t, iZzC & 1048575, zzwwVar.zzl());
                                zzM(t, iZzx);
                                break;
                            case 3:
                                zzxy.zzr(t, iZzC & 1048575, zzwwVar.zzo());
                                zzM(t, iZzx);
                                break;
                            case 4:
                                zzxy.zzq(t, iZzC & 1048575, zzwwVar.zzg());
                                zzM(t, iZzx);
                                break;
                            case 5:
                                zzxy.zzr(t, iZzC & 1048575, zzwwVar.zzk());
                                zzM(t, iZzx);
                                break;
                            case 6:
                                zzxy.zzq(t, iZzC & 1048575, zzwwVar.zzf());
                                zzM(t, iZzx);
                                break;
                            case 7:
                                zzxy.zzm(t, iZzC & 1048575, zzwwVar.zzS());
                                zzM(t, iZzx);
                                break;
                            case 8:
                                zzL(t, iZzC, zzwwVar);
                                zzM(t, iZzx);
                                break;
                            case 9:
                                if (zzQ(t, iZzx)) {
                                    long j = iZzC & 1048575;
                                    zzxy.zzs(t, j, zzvi.zzg(zzxy.zzf(t, j), zzwwVar.zzu(zzF(iZzx), zzujVar)));
                                } else {
                                    zzxy.zzs(t, iZzC & 1048575, zzwwVar.zzu(zzF(iZzx), zzujVar));
                                    zzM(t, iZzx);
                                }
                                break;
                            case 10:
                                zzxy.zzs(t, iZzC & 1048575, zzwwVar.zzq());
                                zzM(t, iZzx);
                                break;
                            case 11:
                                zzxy.zzq(t, iZzC & 1048575, zzwwVar.zzj());
                                zzM(t, iZzx);
                                break;
                            case 12:
                                int iZze = zzwwVar.zze();
                                zzvd zzvdVarZzE = zzE(iZzx);
                                if (zzvdVarZzE == null || zzvdVarZzE.zza(iZze)) {
                                    zzxy.zzq(t, iZzC & 1048575, iZze);
                                    zzM(t, iZzx);
                                } else {
                                    objZze = zzwz.zzD(iZzc, iZze, objZze, zzxoVar);
                                }
                                break;
                            case 13:
                                zzxy.zzq(t, iZzC & 1048575, zzwwVar.zzh());
                                zzM(t, iZzx);
                                break;
                            case 14:
                                zzxy.zzr(t, iZzC & 1048575, zzwwVar.zzm());
                                zzM(t, iZzx);
                                break;
                            case 15:
                                zzxy.zzq(t, iZzC & 1048575, zzwwVar.zzi());
                                zzM(t, iZzx);
                                break;
                            case 16:
                                zzxy.zzr(t, iZzC & 1048575, zzwwVar.zzn());
                                zzM(t, iZzx);
                                break;
                            case 17:
                                if (zzQ(t, iZzx)) {
                                    long j2 = iZzC & 1048575;
                                    zzxy.zzs(t, j2, zzvi.zzg(zzxy.zzf(t, j2), zzwwVar.zzs(zzF(iZzx), zzujVar)));
                                } else {
                                    zzxy.zzs(t, iZzC & 1048575, zzwwVar.zzs(zzF(iZzx), zzujVar));
                                    zzM(t, iZzx);
                                }
                                break;
                            case 18:
                                zzwwVar.zzA(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 19:
                                zzwwVar.zzE(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 20:
                                zzwwVar.zzH(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 21:
                                zzwwVar.zzR(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 22:
                                zzwwVar.zzG(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 23:
                                zzwwVar.zzD(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 24:
                                zzwwVar.zzC(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 25:
                                zzwwVar.zzy(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 26:
                                if (zzP(iZzC)) {
                                    zzwwVar.zzP(this.zzn.zza(t, iZzC & 1048575));
                                } else {
                                    zzwwVar.zzN(this.zzn.zza(t, iZzC & 1048575));
                                }
                                break;
                            case 27:
                                zzwwVar.zzI(this.zzn.zza(t, iZzC & 1048575), zzF(iZzx), zzujVar);
                                break;
                            case 28:
                                zzwwVar.zzz(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 29:
                                zzwwVar.zzQ(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 30:
                                List<Integer> listZza = this.zzn.zza(t, iZzC & 1048575);
                                zzwwVar.zzB(listZza);
                                objZze = zzwz.zzC(iZzc, listZza, zzE(iZzx), objZze, zzxoVar);
                                break;
                            case 31:
                                zzwwVar.zzJ(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 32:
                                zzwwVar.zzK(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 33:
                                zzwwVar.zzL(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 34:
                                zzwwVar.zzM(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 35:
                                zzwwVar.zzA(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 36:
                                zzwwVar.zzE(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 37:
                                zzwwVar.zzH(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 38:
                                zzwwVar.zzR(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 39:
                                zzwwVar.zzG(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 40:
                                zzwwVar.zzD(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 41:
                                zzwwVar.zzC(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 42:
                                zzwwVar.zzy(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 43:
                                zzwwVar.zzQ(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 44:
                                List<Integer> listZza2 = this.zzn.zza(t, iZzC & 1048575);
                                zzwwVar.zzB(listZza2);
                                objZze = zzwz.zzC(iZzc, listZza2, zzE(iZzx), objZze, zzxoVar);
                                break;
                            case 45:
                                zzwwVar.zzJ(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 46:
                                zzwwVar.zzK(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 47:
                                zzwwVar.zzL(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 48:
                                zzwwVar.zzM(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 49:
                                zzwwVar.zzF(this.zzn.zza(t, iZzC & 1048575), zzF(iZzx), zzujVar);
                                break;
                            case 50:
                                Object objZzH = zzH(iZzx);
                                long jZzC = zzC(iZzx) & 1048575;
                                Object objZzf = zzxy.zzf(t, jZzC);
                                if (objZzf == null) {
                                    objZzf = zzwe.zza().zzb();
                                    zzxy.zzs(t, jZzC, objZzf);
                                } else if (zzwf.zzb(objZzf)) {
                                    Object objZzb = zzwe.zza().zzb();
                                    zzwf.zzc(objZzb, objZzf);
                                    zzxy.zzs(t, jZzC, objZzb);
                                    objZzf = objZzb;
                                }
                                throw null;
                            case 51:
                                zzxy.zzs(t, iZzC & 1048575, Double.valueOf(zzwwVar.zza()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 52:
                                zzxy.zzs(t, iZzC & 1048575, Float.valueOf(zzwwVar.zzb()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 53:
                                zzxy.zzs(t, iZzC & 1048575, Long.valueOf(zzwwVar.zzl()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 54:
                                zzxy.zzs(t, iZzC & 1048575, Long.valueOf(zzwwVar.zzo()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 55:
                                zzxy.zzs(t, iZzC & 1048575, Integer.valueOf(zzwwVar.zzg()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 56:
                                zzxy.zzs(t, iZzC & 1048575, Long.valueOf(zzwwVar.zzk()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 57:
                                zzxy.zzs(t, iZzC & 1048575, Integer.valueOf(zzwwVar.zzf()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 58:
                                zzxy.zzs(t, iZzC & 1048575, Boolean.valueOf(zzwwVar.zzS()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 59:
                                zzL(t, iZzC, zzwwVar);
                                zzN(t, iZzc, iZzx);
                                break;
                            case 60:
                                if (zzT(t, iZzc, iZzx)) {
                                    long j3 = iZzC & 1048575;
                                    zzxy.zzs(t, j3, zzvi.zzg(zzxy.zzf(t, j3), zzwwVar.zzu(zzF(iZzx), zzujVar)));
                                } else {
                                    zzxy.zzs(t, iZzC & 1048575, zzwwVar.zzu(zzF(iZzx), zzujVar));
                                    zzM(t, iZzx);
                                }
                                zzN(t, iZzc, iZzx);
                                break;
                            case 61:
                                zzxy.zzs(t, iZzC & 1048575, zzwwVar.zzq());
                                zzN(t, iZzc, iZzx);
                                break;
                            case 62:
                                zzxy.zzs(t, iZzC & 1048575, Integer.valueOf(zzwwVar.zzj()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 63:
                                int iZze2 = zzwwVar.zze();
                                zzvd zzvdVarZzE2 = zzE(iZzx);
                                if (zzvdVarZzE2 == null || zzvdVarZzE2.zza(iZze2)) {
                                    zzxy.zzs(t, iZzC & 1048575, Integer.valueOf(iZze2));
                                    zzN(t, iZzc, iZzx);
                                } else {
                                    objZze = zzwz.zzD(iZzc, iZze2, objZze, zzxoVar);
                                }
                                break;
                            case 64:
                                zzxy.zzs(t, iZzC & 1048575, Integer.valueOf(zzwwVar.zzh()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 65:
                                zzxy.zzs(t, iZzC & 1048575, Long.valueOf(zzwwVar.zzm()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 66:
                                zzxy.zzs(t, iZzC & 1048575, Integer.valueOf(zzwwVar.zzi()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 67:
                                zzxy.zzs(t, iZzC & 1048575, Long.valueOf(zzwwVar.zzn()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 68:
                                zzxy.zzs(t, iZzC & 1048575, zzwwVar.zzs(zzF(iZzx), zzujVar));
                                zzN(t, iZzc, iZzx);
                                break;
                            default:
                                if (objZze == null) {
                                    objZze = zzxoVar.zzf();
                                }
                                if (!zzxoVar.zzp((Object) objZze, zzwwVar)) {
                                    for (int i = this.zzl; i < this.zzm; i++) {
                                        zzG(t, this.zzk[i], objZze, zzxoVar);
                                    }
                                    zzxoVar.zzn(t, (Object) objZze);
                                    return;
                                }
                                break;
                                break;
                        }
                    } catch (zzvj unused) {
                        zzxoVar.zzq(zzwwVar);
                        if (objZze == null) {
                            objZze = zzxoVar.zzc(t);
                        }
                        if (!zzxoVar.zzp((Object) objZze, zzwwVar)) {
                            for (int i2 = this.zzl; i2 < this.zzm; i2++) {
                                zzG(t, this.zzk[i2], objZze, zzxoVar);
                            }
                            if (objZze != null) {
                                zzxoVar.zzn(t, (Object) objZze);
                                return;
                            }
                            return;
                        }
                    }
                } else {
                    if (iZzc == Integer.MAX_VALUE) {
                        for (int i3 = this.zzl; i3 < this.zzm; i3++) {
                            zzG(t, this.zzk[i3], objZze, zzxoVar);
                        }
                        if (objZze != null) {
                            zzxoVar.zzn(t, (Object) objZze);
                            return;
                        }
                        return;
                    }
                    Object objZzd = !this.zzh ? null : zzukVar.zzd(zzujVar, this.zzg, iZzc);
                    if (objZzd != null) {
                        if (zzuoVarZzc == null) {
                            zzuoVarZzc = zzukVar.zzc(t);
                        }
                        zzuo zzuoVar = zzuoVarZzc;
                        objZze = zzukVar.zze(zzwwVar, objZzd, zzujVar, zzuoVar, objZze, zzxoVar);
                        zzuoVarZzc = zzuoVar;
                    } else {
                        zzxoVar.zzq(zzwwVar);
                        if (objZze == null) {
                            objZze = zzxoVar.zzc(t);
                        }
                        if (!zzxoVar.zzp((Object) objZze, zzwwVar)) {
                            for (int i4 = this.zzl; i4 < this.zzm; i4++) {
                                zzG(t, this.zzk[i4], objZze, zzxoVar);
                            }
                            if (objZze != null) {
                                zzxoVar.zzn(t, (Object) objZze);
                                return;
                            }
                            return;
                        }
                    }
                }
            } catch (Throwable th) {
                for (int i5 = this.zzl; i5 < this.zzm; i5++) {
                    zzG(t, this.zzk[i5], objZze, zzxoVar);
                }
                if (objZze != null) {
                    zzxoVar.zzn(t, (Object) objZze);
                }
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzi(T t, byte[] bArr, int i, int i2, zzsl zzslVar) throws IOException {
        if (this.zzj) {
            zzv(t, bArr, i, i2, zzslVar);
        } else {
            zzc(t, bArr, i, i2, 0, zzslVar);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final boolean zzj(T t, T t2) {
        boolean zZzH;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzC = zzC(i);
            long j = iZzC & 1048575;
            switch (zzB(iZzC)) {
                case 0:
                    if (!zzO(t, t2, i) || Double.doubleToLongBits(zzxy.zza(t, j)) != Double.doubleToLongBits(zzxy.zza(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzO(t, t2, i) || Float.floatToIntBits(zzxy.zzb(t, j)) != Float.floatToIntBits(zzxy.zzb(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzO(t, t2, i) || zzxy.zzd(t, j) != zzxy.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzO(t, t2, i) || zzxy.zzd(t, j) != zzxy.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzO(t, t2, i) || zzxy.zzc(t, j) != zzxy.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzO(t, t2, i) || zzxy.zzd(t, j) != zzxy.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzO(t, t2, i) || zzxy.zzc(t, j) != zzxy.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzO(t, t2, i) || zzxy.zzw(t, j) != zzxy.zzw(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzO(t, t2, i) || !zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzO(t, t2, i) || !zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzO(t, t2, i) || !zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzO(t, t2, i) || zzxy.zzc(t, j) != zzxy.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzO(t, t2, i) || zzxy.zzc(t, j) != zzxy.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzO(t, t2, i) || zzxy.zzc(t, j) != zzxy.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzO(t, t2, i) || zzxy.zzd(t, j) != zzxy.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzO(t, t2, i) || zzxy.zzc(t, j) != zzxy.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzO(t, t2, i) || zzxy.zzd(t, j) != zzxy.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzO(t, t2, i) || !zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zZzH = zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j));
                    break;
                case 50:
                    zZzH = zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzz = zzz(i) & 1048575;
                    if (zzxy.zzc(t, jZzz) != zzxy.zzc(t2, jZzz) || !zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    continue;
                    break;
            }
            if (!zZzH) {
                return false;
            }
        }
        if (!this.zzo.zzd(t).equals(this.zzo.zzd(t2))) {
            return false;
        }
        if (this.zzh) {
            return this.zzp.zzb(t).equals(this.zzp.zzb(t2));
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x009b  */
    /* JADX WARN: Code duplicated, block: B:44:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:50:0x00c0 A[LOOP:1: B:45:0x00af->B:50:0x00c0, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:67:0x00bf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:0x00dd A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzwx
    public final boolean zzk(T t) {
        int i;
        int i2;
        List list;
        zzwx zzwxVarZzF;
        int i3;
        int i4 = 1048575;
        int i5 = 0;
        int i6 = 0;
        while (i6 < this.zzl) {
            int i7 = this.zzk[i6];
            int i8 = this.zzc[i7];
            int iZzC = zzC(i7);
            int i9 = this.zzc[i7 + 2];
            int i10 = i9 & 1048575;
            int i11 = 1 << (i9 >>> 20);
            if (i10 != i4) {
                if (i10 != 1048575) {
                    i5 = zzb.getInt(t, i10);
                }
                i2 = i5;
                i = i10;
            } else {
                i = i4;
                i2 = i5;
            }
            if ((268435456 & iZzC) != 0 && !zzR(t, i7, i, i2, i11)) {
                return false;
            }
            int iZzB = zzB(iZzC);
            if (iZzB == 9 || iZzB == 17) {
                if (zzR(t, i7, i, i2, i11) && !zzS(t, iZzC, zzF(i7))) {
                    return false;
                }
            } else if (iZzB == 27) {
                list = (List) zzxy.zzf(t, iZzC & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    zzwxVarZzF = zzF(i7);
                    for (i3 = 0; i3 < list.size(); i3++) {
                        if (!zzwxVarZzF.zzk(list.get(i3))) {
                            return false;
                        }
                    }
                }
            } else if (iZzB == 60 || iZzB == 68) {
                if (zzT(t, i8, i7) && !zzS(t, iZzC, zzF(i7))) {
                    return false;
                }
            } else if (iZzB == 49) {
                list = (List) zzxy.zzf(t, iZzC & 1048575);
                if (list.isEmpty()) {
                    zzwxVarZzF = zzF(i7);
                    while (i3 < list.size()) {
                        if (!zzwxVarZzF.zzk(list.get(i3))) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            } else if (iZzB == 50 && !((zzwe) zzxy.zzf(t, iZzC & 1048575)).isEmpty()) {
                throw null;
            }
            i6++;
            i4 = i;
            i5 = i2;
        }
        return !this.zzh || this.zzp.zzb(t).zzk();
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0022  */
    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzn(T t, zztp zztpVar) throws IOException {
        Iterator itZzf;
        Map.Entry<?, ?> entry;
        if (!this.zzj) {
            zzV(t, zztpVar);
            return;
        }
        if (this.zzh) {
            zzuo<T> zzuoVarZzb = this.zzp.zzb(t);
            if (zzuoVarZzb.zza.isEmpty()) {
                itZzf = null;
                entry = null;
            } else {
                itZzf = zzuoVarZzb.zzf();
                entry = (Map.Entry) itZzf.next();
            }
        } else {
            itZzf = null;
            entry = null;
        }
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzC = zzC(i);
            int i2 = this.zzc[i];
            while (entry != null && this.zzp.zza(entry) <= i2) {
                this.zzp.zzj(zztpVar, entry);
                entry = itZzf.hasNext() ? (Map.Entry) itZzf.next() : null;
            }
            switch (zzB(iZzC)) {
                case 0:
                    if (zzQ(t, i)) {
                        zztpVar.zzf(i2, zzxy.zza(t, iZzC & 1048575));
                    }
                    break;
                case 1:
                    if (zzQ(t, i)) {
                        zztpVar.zzo(i2, zzxy.zzb(t, iZzC & 1048575));
                    }
                    break;
                case 2:
                    if (zzQ(t, i)) {
                        zztpVar.zzt(i2, zzxy.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 3:
                    if (zzQ(t, i)) {
                        zztpVar.zzK(i2, zzxy.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 4:
                    if (zzQ(t, i)) {
                        zztpVar.zzr(i2, zzxy.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 5:
                    if (zzQ(t, i)) {
                        zztpVar.zzm(i2, zzxy.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 6:
                    if (zzQ(t, i)) {
                        zztpVar.zzk(i2, zzxy.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 7:
                    if (zzQ(t, i)) {
                        zztpVar.zzb(i2, zzxy.zzw(t, iZzC & 1048575));
                    }
                    break;
                case 8:
                    if (zzQ(t, i)) {
                        zzX(i2, zzxy.zzf(t, iZzC & 1048575), zztpVar);
                    }
                    break;
                case 9:
                    if (zzQ(t, i)) {
                        zztpVar.zzv(i2, zzxy.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
                case 10:
                    if (zzQ(t, i)) {
                        zztpVar.zzd(i2, (zztd) zzxy.zzf(t, iZzC & 1048575));
                    }
                    break;
                case 11:
                    if (zzQ(t, i)) {
                        zztpVar.zzI(i2, zzxy.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 12:
                    if (zzQ(t, i)) {
                        zztpVar.zzi(i2, zzxy.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 13:
                    if (zzQ(t, i)) {
                        zztpVar.zzx(i2, zzxy.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 14:
                    if (zzQ(t, i)) {
                        zztpVar.zzz(i2, zzxy.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 15:
                    if (zzQ(t, i)) {
                        zztpVar.zzB(i2, zzxy.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 16:
                    if (zzQ(t, i)) {
                        zztpVar.zzD(i2, zzxy.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 17:
                    if (zzQ(t, i)) {
                        zztpVar.zzq(i2, zzxy.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
                case 18:
                    zzwz.zzL(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 19:
                    zzwz.zzP(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 20:
                    zzwz.zzS(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 21:
                    zzwz.zzaa(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 22:
                    zzwz.zzR(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 23:
                    zzwz.zzO(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 24:
                    zzwz.zzN(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 25:
                    zzwz.zzJ(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 26:
                    zzwz.zzY(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar);
                    break;
                case 27:
                    zzwz.zzT(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, zzF(i));
                    break;
                case 28:
                    zzwz.zzK(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar);
                    break;
                case 29:
                    zzwz.zzZ(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 30:
                    zzwz.zzM(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 31:
                    zzwz.zzU(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 32:
                    zzwz.zzV(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 33:
                    zzwz.zzW(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 34:
                    zzwz.zzX(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, false);
                    break;
                case 35:
                    zzwz.zzL(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 36:
                    zzwz.zzP(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 37:
                    zzwz.zzS(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 38:
                    zzwz.zzaa(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 39:
                    zzwz.zzR(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 40:
                    zzwz.zzO(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 41:
                    zzwz.zzN(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 42:
                    zzwz.zzJ(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 43:
                    zzwz.zzZ(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 44:
                    zzwz.zzM(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 45:
                    zzwz.zzU(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 46:
                    zzwz.zzV(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 47:
                    zzwz.zzW(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 48:
                    zzwz.zzX(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, true);
                    break;
                case 49:
                    zzwz.zzQ(this.zzc[i], (List) zzxy.zzf(t, iZzC & 1048575), zztpVar, zzF(i));
                    break;
                case 50:
                    zzW(zztpVar, i2, zzxy.zzf(t, iZzC & 1048575), i);
                    break;
                case 51:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzf(i2, zzo(t, iZzC & 1048575));
                    }
                    break;
                case 52:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzo(i2, zzp(t, iZzC & 1048575));
                    }
                    break;
                case 53:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzt(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 54:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzK(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 55:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzr(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 56:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzm(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 57:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzk(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 58:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzb(i2, zzU(t, iZzC & 1048575));
                    }
                    break;
                case 59:
                    if (zzT(t, i2, i)) {
                        zzX(i2, zzxy.zzf(t, iZzC & 1048575), zztpVar);
                    }
                    break;
                case 60:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzv(i2, zzxy.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
                case 61:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzd(i2, (zztd) zzxy.zzf(t, iZzC & 1048575));
                    }
                    break;
                case 62:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzI(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 63:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzi(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 64:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzx(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 65:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzz(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 66:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzB(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 67:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzD(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 68:
                    if (zzT(t, i2, i)) {
                        zztpVar.zzq(i2, zzxy.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
            }
        }
        while (entry != null) {
            this.zzp.zzj(zztpVar, entry);
            entry = itZzf.hasNext() ? (Map.Entry) itZzf.next() : null;
        }
        zzxo<?, ?> zzxoVar = this.zzo;
        zzxoVar.zzs(zzxoVar.zzd(t), zztpVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzg(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzC = zzC(i);
            long j = 1048575 & iZzC;
            int i2 = this.zzc[i];
            switch (zzB(iZzC)) {
                case 0:
                    if (zzQ(t2, i)) {
                        zzxy.zzo(t, j, zzxy.zza(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 1:
                    if (zzQ(t2, i)) {
                        zzxy.zzp(t, j, zzxy.zzb(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 2:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 3:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 4:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 5:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 6:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 7:
                    if (zzQ(t2, i)) {
                        zzxy.zzm(t, j, zzxy.zzw(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 8:
                    if (zzQ(t2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 9:
                    zzJ(t, t2, i);
                    break;
                case 10:
                    if (zzQ(t2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 11:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 12:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 13:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 14:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 15:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 16:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 17:
                    zzJ(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzn.zzc(t, t2, j);
                    break;
                case 50:
                    zzwz.zzI(this.zzr, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzT(t2, i2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzN(t, i2, i);
                    }
                    break;
                case 60:
                    zzK(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzT(t2, i2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzN(t, i2, i);
                    }
                    break;
                case 68:
                    zzK(t, t2, i);
                    break;
            }
        }
        zzwz.zzF(this.zzo, t, t2);
        if (this.zzh) {
            zzwz.zzE(this.zzp, t, t2);
        }
    }
}
