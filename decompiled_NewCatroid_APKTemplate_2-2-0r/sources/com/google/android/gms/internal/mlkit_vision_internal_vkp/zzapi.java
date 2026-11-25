package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapi<T> implements zzapz<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzard.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzapf zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final int[] zzk;
    private final int zzl;
    private final int zzm;
    private final zzaos zzn;
    private final zzaqt<?, ?> zzo;
    private final zzand<?> zzp;
    private final zzapl zzq;
    private final zzapa zzr;

    /* JADX WARN: Multi-variable type inference failed */
    private zzapi(int[] iArr, int[] iArr2, Object[] objArr, int i, int i2, zzapf zzapfVar, boolean z, boolean z2, int[] iArr3, int i3, int i4, zzapl zzaplVar, zzaos zzaosVar, zzaqt<?, ?> zzaqtVar, zzand<?> zzandVar, zzapa zzapaVar) {
        this.zzc = iArr;
        this.zzd = iArr2;
        this.zze = objArr;
        this.zzf = i;
        this.zzi = i2 instanceof zzans;
        this.zzj = zzapfVar;
        boolean z3 = false;
        if (zzaqtVar != 0 && zzaqtVar.zzj(i2)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzk = z2;
        this.zzl = iArr3;
        this.zzm = i3;
        this.zzq = i4;
        this.zzn = zzaplVar;
        this.zzo = zzaosVar;
        this.zzp = zzaqtVar;
        this.zzg = i2;
        this.zzr = zzandVar;
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
        return ((Long) zzard.zzf(t, j)).longValue();
    }

    private final zzanw zzE(int i) {
        int i2 = i / 3;
        return (zzanw) this.zzd[i2 + i2 + 1];
    }

    private final zzapz zzF(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzapz zzapzVar = (zzapz) this.zzd[i3];
        if (zzapzVar != null) {
            return zzapzVar;
        }
        zzapz<T> zzapzVarZzb = zzapo.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzapzVarZzb;
        return zzapzVarZzb;
    }

    private final <UT, UB> UB zzG(Object obj, int i, UB ub, zzaqt<UT, UB> zzaqtVar) {
        zzanw zzanwVarZzE;
        int i2 = this.zzc[i];
        Object objZzf = zzard.zzf(obj, zzC(i) & 1048575);
        if (objZzf == null || (zzanwVarZzE = zzE(i)) == null) {
            return ub;
        }
        zzaox zzaoxVarZzc = ((zzaoy) zzH(i)).zzc();
        Iterator it = ((zzaoz) objZzf).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!zzanwVarZzE.zza(((Integer) entry.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzaqtVar.zzf();
                }
                int iZzb = zzaoy.zzb(zzaoxVarZzc, entry.getKey(), entry.getValue());
                zzamk zzamkVar = zzamk.zzb;
                byte[] bArr = new byte[iZzb];
                zzamx zzamxVarZzO = zzamx.zzO(bArr);
                try {
                    zzaoy.zze(zzamxVarZzO, zzaoxVarZzc, entry.getKey(), entry.getValue());
                    zzaqtVar.zzk(ub, i2, zzamf.zza(zzamxVarZzO, bArr));
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
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
        if (zzS(t2, i)) {
            Object objZzf = zzard.zzf(t, jZzC);
            Object objZzf2 = zzard.zzf(t2, jZzC);
            if (objZzf != null && objZzf2 != null) {
                zzard.zzs(t, jZzC, zzaoc.zzg(objZzf, objZzf2));
                zzM(t, i);
            } else if (objZzf2 != null) {
                zzard.zzs(t, jZzC, objZzf2);
                zzM(t, i);
            }
        }
    }

    private final void zzK(T t, T t2, int i) {
        int iZzC = zzC(i);
        int i2 = this.zzc[i];
        long j = iZzC & 1048575;
        if (zzV(t2, i2, i)) {
            Object objZzf = zzV(t, i2, i) ? zzard.zzf(t, j) : null;
            Object objZzf2 = zzard.zzf(t2, j);
            if (objZzf != null && objZzf2 != null) {
                zzard.zzs(t, j, zzaoc.zzg(objZzf, objZzf2));
                zzN(t, i2, i);
            } else if (objZzf2 != null) {
                zzard.zzs(t, j, objZzf2);
                zzN(t, i2, i);
            }
        }
    }

    private final void zzL(Object obj, int i, zzapr zzaprVar) throws IOException {
        if (zzR(i)) {
            zzard.zzs(obj, i & 1048575, zzaprVar.zzu());
        } else if (this.zzi) {
            zzard.zzs(obj, i & 1048575, zzaprVar.zzt());
        } else {
            zzard.zzs(obj, i & 1048575, zzaprVar.zzp());
        }
    }

    private final void zzM(T t, int i) {
        int iZzz = zzz(i);
        long j = 1048575 & iZzz;
        if (j == 1048575) {
            return;
        }
        zzard.zzq(t, j, (1 << (iZzz >>> 20)) | zzard.zzc(t, j));
    }

    private final void zzN(T t, int i, int i2) {
        zzard.zzq(t, zzz(i2) & 1048575, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0023  */
    private final void zzO(T t, zzarl zzarlVar) throws IOException {
        Iterator itZzh;
        Map.Entry<?, ?> entry;
        int i;
        boolean z;
        if (this.zzh) {
            zzanh<T> zzanhVarZzb = this.zzp.zzb(t);
            if (zzanhVarZzb.zza.isEmpty()) {
                itZzh = null;
                entry = null;
            } else {
                itZzh = zzanhVarZzb.zzh();
                entry = (Map.Entry) itZzh.next();
            }
        } else {
            itZzh = null;
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
                this.zzp.zzi(zzarlVar, entry);
                entry = itZzh.hasNext() ? (Map.Entry) itZzh.next() : null;
            }
            long j = iZzC & i2;
            switch (iZzB) {
                case 0:
                    if ((i & i5) != 0) {
                        zzarlVar.zzf(i6, zzard.zza(t, j));
                    }
                    break;
                case 1:
                    if ((i & i5) != 0) {
                        zzarlVar.zzo(i6, zzard.zzb(t, j));
                    }
                    break;
                case 2:
                    if ((i & i5) != 0) {
                        zzarlVar.zzt(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 3:
                    if ((i & i5) != 0) {
                        zzarlVar.zzL(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 4:
                    if ((i & i5) != 0) {
                        zzarlVar.zzr(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 5:
                    if ((i & i5) != 0) {
                        zzarlVar.zzm(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 6:
                    if ((i & i5) != 0) {
                        zzarlVar.zzk(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 7:
                    if ((i & i5) != 0) {
                        zzarlVar.zzb(i6, zzard.zzw(t, j));
                    }
                    break;
                case 8:
                    if ((i & i5) != 0) {
                        zzY(i6, unsafe.getObject(t, j), zzarlVar);
                    }
                    break;
                case 9:
                    if ((i & i5) != 0) {
                        zzarlVar.zzw(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                case 10:
                    if ((i & i5) != 0) {
                        zzarlVar.zzd(i6, (zzamk) unsafe.getObject(t, j));
                    }
                    break;
                case 11:
                    if ((i & i5) != 0) {
                        zzarlVar.zzJ(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 12:
                    if ((i & i5) != 0) {
                        zzarlVar.zzi(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 13:
                    if ((i & i5) != 0) {
                        zzarlVar.zzy(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 14:
                    if ((i & i5) != 0) {
                        zzarlVar.zzA(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 15:
                    if ((i & i5) != 0) {
                        zzarlVar.zzC(i6, unsafe.getInt(t, j));
                    }
                    break;
                case 16:
                    if ((i & i5) != 0) {
                        zzarlVar.zzE(i6, unsafe.getLong(t, j));
                    }
                    break;
                case 17:
                    if ((i & i5) != 0) {
                        zzarlVar.zzq(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                case 18:
                    zzaqb.zzJ(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 19:
                    zzaqb.zzN(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 20:
                    zzaqb.zzQ(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 21:
                    zzaqb.zzY(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 22:
                    zzaqb.zzP(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 23:
                    zzaqb.zzM(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 24:
                    zzaqb.zzL(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 25:
                    zzaqb.zzH(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 26:
                    zzaqb.zzW(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar);
                    break;
                case 27:
                    zzaqb.zzR(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, zzF(i4));
                    break;
                case 28:
                    zzaqb.zzI(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar);
                    break;
                case 29:
                    z = false;
                    zzaqb.zzX(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 30:
                    z = false;
                    zzaqb.zzK(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 31:
                    z = false;
                    zzaqb.zzS(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 32:
                    z = false;
                    zzaqb.zzT(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 33:
                    z = false;
                    zzaqb.zzU(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 34:
                    z = false;
                    zzaqb.zzV(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, false);
                    break;
                case 35:
                    zzaqb.zzJ(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 36:
                    zzaqb.zzN(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 37:
                    zzaqb.zzQ(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 38:
                    zzaqb.zzY(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 39:
                    zzaqb.zzP(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 40:
                    zzaqb.zzM(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 41:
                    zzaqb.zzL(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 42:
                    zzaqb.zzH(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 43:
                    zzaqb.zzX(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 44:
                    zzaqb.zzK(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 45:
                    zzaqb.zzS(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 46:
                    zzaqb.zzT(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 47:
                    zzaqb.zzU(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 48:
                    zzaqb.zzV(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, true);
                    break;
                case 49:
                    zzaqb.zzO(this.zzc[i4], (List) unsafe.getObject(t, j), zzarlVar, zzF(i4));
                    break;
                case 50:
                    zzP(zzarlVar, i6, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzf(i6, zzo(t, j));
                    }
                    break;
                case 52:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzo(i6, zzp(t, j));
                    }
                    break;
                case 53:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzt(i6, zzD(t, j));
                    }
                    break;
                case 54:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzL(i6, zzD(t, j));
                    }
                    break;
                case 55:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzr(i6, zzs(t, j));
                    }
                    break;
                case 56:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzm(i6, zzD(t, j));
                    }
                    break;
                case 57:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzk(i6, zzs(t, j));
                    }
                    break;
                case 58:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzb(i6, zzW(t, j));
                    }
                    break;
                case 59:
                    if (zzV(t, i6, i4)) {
                        zzY(i6, unsafe.getObject(t, j), zzarlVar);
                    }
                    break;
                case 60:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzw(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                case 61:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzd(i6, (zzamk) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzJ(i6, zzs(t, j));
                    }
                    break;
                case 63:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzi(i6, zzs(t, j));
                    }
                    break;
                case 64:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzy(i6, zzs(t, j));
                    }
                    break;
                case 65:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzA(i6, zzD(t, j));
                    }
                    break;
                case 66:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzC(i6, zzs(t, j));
                    }
                    break;
                case 67:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzE(i6, zzD(t, j));
                    }
                    break;
                case 68:
                    if (zzV(t, i6, i4)) {
                        zzarlVar.zzq(i6, unsafe.getObject(t, j), zzF(i4));
                    }
                    break;
                default:
                    break;
            }
            i4 += 3;
            i2 = 1048575;
        }
        while (entry != null) {
            this.zzp.zzi(zzarlVar, entry);
            entry = itZzh.hasNext() ? (Map.Entry) itZzh.next() : null;
        }
        zzaqt<?, ?> zzaqtVar = this.zzo;
        zzaqtVar.zzq(zzaqtVar.zzd(t), zzarlVar);
    }

    private final <K, V> void zzP(zzarl zzarlVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzarlVar.zzv(i, ((zzaoy) zzH(i2)).zzc(), (zzaoz) obj);
        }
    }

    private final boolean zzQ(T t, T t2, int i) {
        return zzS(t, i) == zzS(t2, i);
    }

    private static boolean zzR(int i) {
        return (i & 536870912) != 0;
    }

    private final boolean zzS(T t, int i) {
        int iZzz = zzz(i);
        long j = iZzz & 1048575;
        if (j != 1048575) {
            return (zzard.zzc(t, j) & (1 << (iZzz >>> 20))) != 0;
        }
        int iZzC = zzC(i);
        long j2 = iZzC & 1048575;
        switch (zzB(iZzC)) {
            case 0:
                return zzard.zza(t, j2) != 0.0d;
            case 1:
                return zzard.zzb(t, j2) != 0.0f;
            case 2:
                return zzard.zzd(t, j2) != 0;
            case 3:
                return zzard.zzd(t, j2) != 0;
            case 4:
                return zzard.zzc(t, j2) != 0;
            case 5:
                return zzard.zzd(t, j2) != 0;
            case 6:
                return zzard.zzc(t, j2) != 0;
            case 7:
                return zzard.zzw(t, j2);
            case 8:
                Object objZzf = zzard.zzf(t, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzamk) {
                    return !zzamk.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzard.zzf(t, j2) != null;
            case 10:
                return !zzamk.zzb.equals(zzard.zzf(t, j2));
            case 11:
                return zzard.zzc(t, j2) != 0;
            case 12:
                return zzard.zzc(t, j2) != 0;
            case 13:
                return zzard.zzc(t, j2) != 0;
            case 14:
                return zzard.zzd(t, j2) != 0;
            case 15:
                return zzard.zzc(t, j2) != 0;
            case 16:
                return zzard.zzd(t, j2) != 0;
            case 17:
                return zzard.zzf(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzT(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzS(t, i);
        }
        return (i3 & i4) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zzU(Object obj, int i, zzapz zzapzVar) {
        return zzapzVar.zzl(zzard.zzf(obj, i & 1048575));
    }

    private final boolean zzV(T t, int i, int i2) {
        return zzard.zzc(t, (long) (zzz(i2) & 1048575)) == i;
    }

    private static <T> boolean zzW(T t, long j) {
        return ((Boolean) zzard.zzf(t, j)).booleanValue();
    }

    private static final int zzX(byte[] bArr, int i, int i2, zzarj zzarjVar, Class<?> cls, zzalv zzalvVar) throws IOException {
        zzarj zzarjVar2 = zzarj.DOUBLE;
        switch (zzarjVar) {
            case DOUBLE:
                zzalvVar.zzc = Double.valueOf(Double.longBitsToDouble(zzalw.zzo(bArr, i)));
                return i + 8;
            case FLOAT:
                zzalvVar.zzc = Float.valueOf(Float.intBitsToFloat(zzalw.zzb(bArr, i)));
                return i + 4;
            case INT64:
            case UINT64:
                int iZzm = zzalw.zzm(bArr, i, zzalvVar);
                zzalvVar.zzc = Long.valueOf(zzalvVar.zzb);
                return iZzm;
            case INT32:
            case UINT32:
            case ENUM:
                int iZzj = zzalw.zzj(bArr, i, zzalvVar);
                zzalvVar.zzc = Integer.valueOf(zzalvVar.zza);
                return iZzj;
            case FIXED64:
            case SFIXED64:
                zzalvVar.zzc = Long.valueOf(zzalw.zzo(bArr, i));
                return i + 8;
            case FIXED32:
            case SFIXED32:
                zzalvVar.zzc = Integer.valueOf(zzalw.zzb(bArr, i));
                return i + 4;
            case BOOL:
                int iZzm2 = zzalw.zzm(bArr, i, zzalvVar);
                zzalvVar.zzc = Boolean.valueOf(zzalvVar.zzb != 0);
                return iZzm2;
            case STRING:
                return zzalw.zzh(bArr, i, zzalvVar);
            case GROUP:
            default:
                throw new RuntimeException("unsupported field type.");
            case MESSAGE:
                return zzalw.zzd(zzapo.zza().zzb(cls), bArr, i, i2, zzalvVar);
            case BYTES:
                return zzalw.zza(bArr, i, zzalvVar);
            case SINT32:
                int iZzj2 = zzalw.zzj(bArr, i, zzalvVar);
                zzalvVar.zzc = Integer.valueOf(zzamq.zzF(zzalvVar.zza));
                return iZzj2;
            case SINT64:
                int iZzm3 = zzalw.zzm(bArr, i, zzalvVar);
                zzalvVar.zzc = Long.valueOf(zzamq.zzG(zzalvVar.zzb));
                return iZzm3;
        }
    }

    private static final void zzY(int i, Object obj, zzarl zzarlVar) throws IOException {
        if (obj instanceof String) {
            zzarlVar.zzH(i, (String) obj);
        } else {
            zzarlVar.zzd(i, (zzamk) obj);
        }
    }

    static zzaqu zzd(Object obj) {
        zzans zzansVar = (zzans) obj;
        zzaqu zzaquVar = zzansVar.zzc;
        if (zzaquVar != zzaqu.zzc()) {
            return zzaquVar;
        }
        zzaqu zzaquVarZze = zzaqu.zze();
        zzansVar.zzc = zzaquVarZze;
        return zzaquVarZze;
    }

    static <T> zzapi<T> zzm(Class<T> cls, zzapc zzapcVar, zzapl zzaplVar, zzaos zzaosVar, zzaqt<?, ?> zzaqtVar, zzand<?> zzandVar, zzapa zzapaVar) {
        if (zzapcVar instanceof zzapq) {
            return zzn((zzapq) zzapcVar, zzaplVar, zzaosVar, zzaqtVar, zzandVar, zzapaVar);
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
    static <T> zzapi<T> zzn(zzapq zzapqVar, zzapl zzaplVar, zzaos zzaosVar, zzaqt<?, ?> zzaqtVar, zzand<?> zzandVar, zzapa zzapaVar) {
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
        boolean z = zzapqVar.zzc() == 2;
        String strZzd = zzapqVar.zzd();
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
        Object[] objArrZze = zzapqVar.zze();
        Class<?> cls = zzapqVar.zza().getClass();
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
        return new zzapi<>(iArr3, objArr2, iCharAt, i4, zzapqVar.zza(), z, false, iArr, iCharAt3, i60, zzaplVar, zzaosVar, zzaqtVar, zzandVar, zzapaVar, null);
    }

    private static <T> double zzo(T t, long j) {
        return ((Double) zzard.zzf(t, j)).doubleValue();
    }

    private static <T> float zzp(T t, long j) {
        return ((Float) zzard.zzf(t, j)).floatValue();
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
        int iZzM;
        int iZzM2;
        int iZzM3;
        int iZzN;
        int iZzM4;
        int iZzF;
        int iZzM5;
        int iZzM6;
        int iZzd;
        int iZzM7;
        int iZzo;
        int iZzL;
        int iZzM8;
        int i2;
        Unsafe unsafe = zzb;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1048575;
        for (int i6 = 0; i6 < this.zzc.length; i6 += 3) {
            int iZzC = zzC(i6);
            int i7 = this.zzc[i6];
            int iZzB = zzB(iZzC);
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
            long j = iZzC & 1048575;
            switch (iZzB) {
                case 0:
                    if ((i4 & i) != 0) {
                        iZzM = zzamx.zzM(i7 << 3);
                        iZzo = iZzM + 8;
                        i3 += iZzo;
                    }
                    break;
                case 1:
                    if ((i4 & i) != 0) {
                        iZzM2 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 2:
                    if ((i4 & i) != 0) {
                        long j2 = unsafe.getLong(t, j);
                        iZzM3 = zzamx.zzM(i7 << 3);
                        iZzN = zzamx.zzN(j2);
                        iZzo = iZzM3 + iZzN;
                        i3 += iZzo;
                    }
                    break;
                case 3:
                    if ((i4 & i) != 0) {
                        long j3 = unsafe.getLong(t, j);
                        iZzM3 = zzamx.zzM(i7 << 3);
                        iZzN = zzamx.zzN(j3);
                        iZzo = iZzM3 + iZzN;
                        i3 += iZzo;
                    }
                    break;
                case 4:
                    if ((i4 & i) != 0) {
                        int i10 = unsafe.getInt(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzF(i10);
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 5:
                    if ((i4 & i) != 0) {
                        iZzM = zzamx.zzM(i7 << 3);
                        iZzo = iZzM + 8;
                        i3 += iZzo;
                    }
                    break;
                case 6:
                    if ((i4 & i) != 0) {
                        iZzM2 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 7:
                    if ((i4 & i) != 0) {
                        iZzM5 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM5 + 1;
                        i3 += iZzo;
                    }
                    break;
                case 8:
                    if ((i4 & i) != 0) {
                        Object object = unsafe.getObject(t, j);
                        if (object instanceof zzamk) {
                            iZzM6 = zzamx.zzM(i7 << 3);
                            iZzd = ((zzamk) object).zzd();
                            iZzM7 = zzamx.zzM(iZzd);
                            i2 = iZzM6 + iZzM7 + iZzd;
                            i3 += i2;
                        } else {
                            iZzM4 = zzamx.zzM(i7 << 3);
                            iZzF = zzamx.zzK((String) object);
                            i2 = iZzM4 + iZzF;
                            i3 += i2;
                        }
                    }
                    break;
                case 9:
                    if ((i4 & i) != 0) {
                        iZzo = zzaqb.zzo(i7, unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
                case 10:
                    if ((i4 & i) != 0) {
                        zzamk zzamkVar = (zzamk) unsafe.getObject(t, j);
                        iZzM6 = zzamx.zzM(i7 << 3);
                        iZzd = zzamkVar.zzd();
                        iZzM7 = zzamx.zzM(iZzd);
                        i2 = iZzM6 + iZzM7 + iZzd;
                        i3 += i2;
                    }
                    break;
                case 11:
                    if ((i4 & i) != 0) {
                        int i11 = unsafe.getInt(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzM(i11);
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 12:
                    if ((i4 & i) != 0) {
                        int i12 = unsafe.getInt(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzF(i12);
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 13:
                    if ((i4 & i) != 0) {
                        iZzM2 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 14:
                    if ((i4 & i) != 0) {
                        iZzM = zzamx.zzM(i7 << 3);
                        iZzo = iZzM + 8;
                        i3 += iZzo;
                    }
                    break;
                case 15:
                    if ((i4 & i) != 0) {
                        int i13 = unsafe.getInt(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzM((i13 >> 31) ^ (i13 + i13));
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 16:
                    if ((i4 & i) != 0) {
                        long j4 = unsafe.getLong(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzN((j4 >> 63) ^ (j4 + j4));
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 17:
                    if ((i4 & i) != 0) {
                        iZzo = zzamx.zzD(i7, (zzapf) unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
                case 18:
                    iZzo = zzaqb.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 19:
                    iZzo = zzaqb.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 20:
                    iZzo = zzaqb.zzm(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 21:
                    iZzo = zzaqb.zzx(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 22:
                    iZzo = zzaqb.zzk(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 23:
                    iZzo = zzaqb.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 24:
                    iZzo = zzaqb.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 25:
                    iZzo = zzaqb.zza(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 26:
                    iZzo = zzaqb.zzu(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzo;
                    break;
                case 27:
                    iZzo = zzaqb.zzp(i7, (List) unsafe.getObject(t, j), zzF(i6));
                    i3 += iZzo;
                    break;
                case 28:
                    iZzo = zzaqb.zzc(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzo;
                    break;
                case 29:
                    iZzo = zzaqb.zzv(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 30:
                    iZzo = zzaqb.zzd(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 31:
                    iZzo = zzaqb.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 32:
                    iZzo = zzaqb.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 33:
                    iZzo = zzaqb.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 34:
                    iZzo = zzaqb.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzo;
                    break;
                case 35:
                    iZzF = zzaqb.zzi((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 36:
                    iZzF = zzaqb.zzg((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 37:
                    iZzF = zzaqb.zzn((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 38:
                    iZzF = zzaqb.zzy((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 39:
                    iZzF = zzaqb.zzl((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 40:
                    iZzF = zzaqb.zzi((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 41:
                    iZzF = zzaqb.zzg((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 42:
                    iZzF = zzaqb.zzb((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 43:
                    iZzF = zzaqb.zzw((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 44:
                    iZzF = zzaqb.zze((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 45:
                    iZzF = zzaqb.zzg((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 46:
                    iZzF = zzaqb.zzi((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 47:
                    iZzF = zzaqb.zzr((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 48:
                    iZzF = zzaqb.zzt((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i7);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 49:
                    iZzo = zzaqb.zzj(i7, (List) unsafe.getObject(t, j), zzF(i6));
                    i3 += iZzo;
                    break;
                case 50:
                    iZzo = zzapa.zza(i7, unsafe.getObject(t, j), zzH(i6));
                    i3 += iZzo;
                    break;
                case 51:
                    if (zzV(t, i7, i6)) {
                        iZzM = zzamx.zzM(i7 << 3);
                        iZzo = iZzM + 8;
                        i3 += iZzo;
                    }
                    break;
                case 52:
                    if (zzV(t, i7, i6)) {
                        iZzM2 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 53:
                    if (zzV(t, i7, i6)) {
                        long jZzD = zzD(t, j);
                        iZzM3 = zzamx.zzM(i7 << 3);
                        iZzN = zzamx.zzN(jZzD);
                        iZzo = iZzM3 + iZzN;
                        i3 += iZzo;
                    }
                    break;
                case 54:
                    if (zzV(t, i7, i6)) {
                        long jZzD2 = zzD(t, j);
                        iZzM3 = zzamx.zzM(i7 << 3);
                        iZzN = zzamx.zzN(jZzD2);
                        iZzo = iZzM3 + iZzN;
                        i3 += iZzo;
                    }
                    break;
                case 55:
                    if (zzV(t, i7, i6)) {
                        int iZzs = zzs(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzF(iZzs);
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 56:
                    if (zzV(t, i7, i6)) {
                        iZzM = zzamx.zzM(i7 << 3);
                        iZzo = iZzM + 8;
                        i3 += iZzo;
                    }
                    break;
                case 57:
                    if (zzV(t, i7, i6)) {
                        iZzM2 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 58:
                    if (zzV(t, i7, i6)) {
                        iZzM5 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM5 + 1;
                        i3 += iZzo;
                    }
                    break;
                case 59:
                    if (zzV(t, i7, i6)) {
                        Object object2 = unsafe.getObject(t, j);
                        if (object2 instanceof zzamk) {
                            iZzM6 = zzamx.zzM(i7 << 3);
                            iZzd = ((zzamk) object2).zzd();
                            iZzM7 = zzamx.zzM(iZzd);
                            i2 = iZzM6 + iZzM7 + iZzd;
                            i3 += i2;
                        } else {
                            iZzM4 = zzamx.zzM(i7 << 3);
                            iZzF = zzamx.zzK((String) object2);
                            i2 = iZzM4 + iZzF;
                            i3 += i2;
                        }
                    }
                    break;
                case 60:
                    if (zzV(t, i7, i6)) {
                        iZzo = zzaqb.zzo(i7, unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
                case 61:
                    if (zzV(t, i7, i6)) {
                        zzamk zzamkVar2 = (zzamk) unsafe.getObject(t, j);
                        iZzM6 = zzamx.zzM(i7 << 3);
                        iZzd = zzamkVar2.zzd();
                        iZzM7 = zzamx.zzM(iZzd);
                        i2 = iZzM6 + iZzM7 + iZzd;
                        i3 += i2;
                    }
                    break;
                case 62:
                    if (zzV(t, i7, i6)) {
                        int iZzs2 = zzs(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzM(iZzs2);
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 63:
                    if (zzV(t, i7, i6)) {
                        int iZzs3 = zzs(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzF(iZzs3);
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 64:
                    if (zzV(t, i7, i6)) {
                        iZzM2 = zzamx.zzM(i7 << 3);
                        iZzo = iZzM2 + 4;
                        i3 += iZzo;
                    }
                    break;
                case 65:
                    if (zzV(t, i7, i6)) {
                        iZzM = zzamx.zzM(i7 << 3);
                        iZzo = iZzM + 8;
                        i3 += iZzo;
                    }
                    break;
                case 66:
                    if (zzV(t, i7, i6)) {
                        int iZzs4 = zzs(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzM((iZzs4 >> 31) ^ (iZzs4 + iZzs4));
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 67:
                    if (zzV(t, i7, i6)) {
                        long jZzD3 = zzD(t, j);
                        iZzM4 = zzamx.zzM(i7 << 3);
                        iZzF = zzamx.zzN((jZzD3 >> 63) ^ (jZzD3 + jZzD3));
                        i2 = iZzM4 + iZzF;
                        i3 += i2;
                    }
                    break;
                case 68:
                    if (zzV(t, i7, i6)) {
                        iZzo = zzamx.zzD(i7, (zzapf) unsafe.getObject(t, j), zzF(i6));
                        i3 += iZzo;
                    }
                    break;
            }
        }
        zzaqt<?, ?> zzaqtVar = this.zzo;
        int iZza = i3 + zzaqtVar.zza(zzaqtVar.zzd(t));
        if (!this.zzh) {
            return iZza;
        }
        zzanh<T> zzanhVarZzb = this.zzp.zzb(t);
        int iZzc = 0;
        for (int i14 = 0; i14 < zzanhVarZzb.zza.zzb(); i14++) {
            Map.Entry entryZzg = zzanhVarZzb.zza.zzg(i14);
            iZzc += zzanh.zzc((zzang) entryZzg.getKey(), entryZzg.getValue());
        }
        for (Map.Entry entry : zzanhVarZzb.zza.zzc()) {
            iZzc += zzanh.zzc((zzang) entry.getKey(), entry.getValue());
        }
        return iZza + iZzc;
    }

    private final int zzr(T t) {
        int iZzM;
        int iZzM2;
        int iZzM3;
        int iZzN;
        int iZzM4;
        int iZzF;
        int iZzM5;
        int iZzM6;
        int iZzd;
        int iZzM7;
        int iZzo;
        int iZzL;
        int iZzM8;
        int i;
        Unsafe unsafe = zzb;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzc.length; i3 += 3) {
            int iZzC = zzC(i3);
            int iZzB = zzB(iZzC);
            int i4 = this.zzc[i3];
            long j = iZzC & 1048575;
            if (iZzB >= zzani.DOUBLE_LIST_PACKED.zza() && iZzB <= zzani.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i3 + 2];
            }
            switch (iZzB) {
                case 0:
                    if (zzS(t, i3)) {
                        iZzM = zzamx.zzM(i4 << 3);
                        iZzo = iZzM + 8;
                        i2 += iZzo;
                    }
                    break;
                case 1:
                    if (zzS(t, i3)) {
                        iZzM2 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 2:
                    if (zzS(t, i3)) {
                        long jZzd = zzard.zzd(t, j);
                        iZzM3 = zzamx.zzM(i4 << 3);
                        iZzN = zzamx.zzN(jZzd);
                        i2 += iZzM3 + iZzN;
                    }
                    break;
                case 3:
                    if (zzS(t, i3)) {
                        long jZzd2 = zzard.zzd(t, j);
                        iZzM3 = zzamx.zzM(i4 << 3);
                        iZzN = zzamx.zzN(jZzd2);
                        i2 += iZzM3 + iZzN;
                    }
                    break;
                case 4:
                    if (zzS(t, i3)) {
                        int iZzc = zzard.zzc(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzF(iZzc);
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 5:
                    if (zzS(t, i3)) {
                        iZzM = zzamx.zzM(i4 << 3);
                        iZzo = iZzM + 8;
                        i2 += iZzo;
                    }
                    break;
                case 6:
                    if (zzS(t, i3)) {
                        iZzM2 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 7:
                    if (zzS(t, i3)) {
                        iZzM5 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM5 + 1;
                        i2 += iZzo;
                    }
                    break;
                case 8:
                    if (zzS(t, i3)) {
                        Object objZzf = zzard.zzf(t, j);
                        if (objZzf instanceof zzamk) {
                            iZzM6 = zzamx.zzM(i4 << 3);
                            iZzd = ((zzamk) objZzf).zzd();
                            iZzM7 = zzamx.zzM(iZzd);
                            i = iZzM6 + iZzM7 + iZzd;
                            i2 += i;
                        } else {
                            iZzM4 = zzamx.zzM(i4 << 3);
                            iZzF = zzamx.zzK((String) objZzf);
                            i = iZzM4 + iZzF;
                            i2 += i;
                        }
                    }
                    break;
                case 9:
                    if (zzS(t, i3)) {
                        iZzo = zzaqb.zzo(i4, zzard.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
                case 10:
                    if (zzS(t, i3)) {
                        zzamk zzamkVar = (zzamk) zzard.zzf(t, j);
                        iZzM6 = zzamx.zzM(i4 << 3);
                        iZzd = zzamkVar.zzd();
                        iZzM7 = zzamx.zzM(iZzd);
                        i = iZzM6 + iZzM7 + iZzd;
                        i2 += i;
                    }
                    break;
                case 11:
                    if (zzS(t, i3)) {
                        int iZzc2 = zzard.zzc(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzM(iZzc2);
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 12:
                    if (zzS(t, i3)) {
                        int iZzc3 = zzard.zzc(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzF(iZzc3);
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 13:
                    if (zzS(t, i3)) {
                        iZzM2 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 14:
                    if (zzS(t, i3)) {
                        iZzM = zzamx.zzM(i4 << 3);
                        iZzo = iZzM + 8;
                        i2 += iZzo;
                    }
                    break;
                case 15:
                    if (zzS(t, i3)) {
                        int iZzc4 = zzard.zzc(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzM((iZzc4 >> 31) ^ (iZzc4 + iZzc4));
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 16:
                    if (zzS(t, i3)) {
                        long jZzd3 = zzard.zzd(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzN((jZzd3 >> 63) ^ (jZzd3 + jZzd3));
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 17:
                    if (zzS(t, i3)) {
                        iZzo = zzamx.zzD(i4, (zzapf) zzard.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
                case 18:
                    iZzo = zzaqb.zzh(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 19:
                    iZzo = zzaqb.zzf(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 20:
                    iZzo = zzaqb.zzm(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 21:
                    iZzo = zzaqb.zzx(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 22:
                    iZzo = zzaqb.zzk(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 23:
                    iZzo = zzaqb.zzh(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 24:
                    iZzo = zzaqb.zzf(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 25:
                    iZzo = zzaqb.zza(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 26:
                    iZzo = zzaqb.zzu(i4, (List) zzard.zzf(t, j));
                    i2 += iZzo;
                    break;
                case 27:
                    iZzo = zzaqb.zzp(i4, (List) zzard.zzf(t, j), zzF(i3));
                    i2 += iZzo;
                    break;
                case 28:
                    iZzo = zzaqb.zzc(i4, (List) zzard.zzf(t, j));
                    i2 += iZzo;
                    break;
                case 29:
                    iZzo = zzaqb.zzv(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 30:
                    iZzo = zzaqb.zzd(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 31:
                    iZzo = zzaqb.zzf(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 32:
                    iZzo = zzaqb.zzh(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 33:
                    iZzo = zzaqb.zzq(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 34:
                    iZzo = zzaqb.zzs(i4, (List) zzard.zzf(t, j), false);
                    i2 += iZzo;
                    break;
                case 35:
                    iZzF = zzaqb.zzi((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 36:
                    iZzF = zzaqb.zzg((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 37:
                    iZzF = zzaqb.zzn((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 38:
                    iZzF = zzaqb.zzy((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 39:
                    iZzF = zzaqb.zzl((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 40:
                    iZzF = zzaqb.zzi((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 41:
                    iZzF = zzaqb.zzg((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 42:
                    iZzF = zzaqb.zzb((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 43:
                    iZzF = zzaqb.zzw((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 44:
                    iZzF = zzaqb.zze((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 45:
                    iZzF = zzaqb.zzg((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 46:
                    iZzF = zzaqb.zzi((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 47:
                    iZzF = zzaqb.zzr((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 48:
                    iZzF = zzaqb.zzt((List) unsafe.getObject(t, j));
                    if (iZzF > 0) {
                        iZzL = zzamx.zzL(i4);
                        iZzM8 = zzamx.zzM(iZzF);
                        iZzM4 = iZzL + iZzM8;
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 49:
                    iZzo = zzaqb.zzj(i4, (List) zzard.zzf(t, j), zzF(i3));
                    i2 += iZzo;
                    break;
                case 50:
                    iZzo = zzapa.zza(i4, zzard.zzf(t, j), zzH(i3));
                    i2 += iZzo;
                    break;
                case 51:
                    if (zzV(t, i4, i3)) {
                        iZzM = zzamx.zzM(i4 << 3);
                        iZzo = iZzM + 8;
                        i2 += iZzo;
                    }
                    break;
                case 52:
                    if (zzV(t, i4, i3)) {
                        iZzM2 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 53:
                    if (zzV(t, i4, i3)) {
                        long jZzD = zzD(t, j);
                        iZzM3 = zzamx.zzM(i4 << 3);
                        iZzN = zzamx.zzN(jZzD);
                        i2 += iZzM3 + iZzN;
                    }
                    break;
                case 54:
                    if (zzV(t, i4, i3)) {
                        long jZzD2 = zzD(t, j);
                        iZzM3 = zzamx.zzM(i4 << 3);
                        iZzN = zzamx.zzN(jZzD2);
                        i2 += iZzM3 + iZzN;
                    }
                    break;
                case 55:
                    if (zzV(t, i4, i3)) {
                        int iZzs = zzs(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzF(iZzs);
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 56:
                    if (zzV(t, i4, i3)) {
                        iZzM = zzamx.zzM(i4 << 3);
                        iZzo = iZzM + 8;
                        i2 += iZzo;
                    }
                    break;
                case 57:
                    if (zzV(t, i4, i3)) {
                        iZzM2 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 58:
                    if (zzV(t, i4, i3)) {
                        iZzM5 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM5 + 1;
                        i2 += iZzo;
                    }
                    break;
                case 59:
                    if (zzV(t, i4, i3)) {
                        Object objZzf2 = zzard.zzf(t, j);
                        if (objZzf2 instanceof zzamk) {
                            iZzM6 = zzamx.zzM(i4 << 3);
                            iZzd = ((zzamk) objZzf2).zzd();
                            iZzM7 = zzamx.zzM(iZzd);
                            i = iZzM6 + iZzM7 + iZzd;
                            i2 += i;
                        } else {
                            iZzM4 = zzamx.zzM(i4 << 3);
                            iZzF = zzamx.zzK((String) objZzf2);
                            i = iZzM4 + iZzF;
                            i2 += i;
                        }
                    }
                    break;
                case 60:
                    if (zzV(t, i4, i3)) {
                        iZzo = zzaqb.zzo(i4, zzard.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
                case 61:
                    if (zzV(t, i4, i3)) {
                        zzamk zzamkVar2 = (zzamk) zzard.zzf(t, j);
                        iZzM6 = zzamx.zzM(i4 << 3);
                        iZzd = zzamkVar2.zzd();
                        iZzM7 = zzamx.zzM(iZzd);
                        i = iZzM6 + iZzM7 + iZzd;
                        i2 += i;
                    }
                    break;
                case 62:
                    if (zzV(t, i4, i3)) {
                        int iZzs2 = zzs(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzM(iZzs2);
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 63:
                    if (zzV(t, i4, i3)) {
                        int iZzs3 = zzs(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzF(iZzs3);
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 64:
                    if (zzV(t, i4, i3)) {
                        iZzM2 = zzamx.zzM(i4 << 3);
                        iZzo = iZzM2 + 4;
                        i2 += iZzo;
                    }
                    break;
                case 65:
                    if (zzV(t, i4, i3)) {
                        iZzM = zzamx.zzM(i4 << 3);
                        iZzo = iZzM + 8;
                        i2 += iZzo;
                    }
                    break;
                case 66:
                    if (zzV(t, i4, i3)) {
                        int iZzs4 = zzs(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzM((iZzs4 >> 31) ^ (iZzs4 + iZzs4));
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 67:
                    if (zzV(t, i4, i3)) {
                        long jZzD3 = zzD(t, j);
                        iZzM4 = zzamx.zzM(i4 << 3);
                        iZzF = zzamx.zzN((jZzD3 >> 63) ^ (jZzD3 + jZzD3));
                        i = iZzM4 + iZzF;
                        i2 += i;
                    }
                    break;
                case 68:
                    if (zzV(t, i4, i3)) {
                        iZzo = zzamx.zzD(i4, (zzapf) zzard.zzf(t, j), zzF(i3));
                        i2 += iZzo;
                    }
                    break;
            }
        }
        zzaqt<?, ?> zzaqtVar = this.zzo;
        return i2 + zzaqtVar.zza(zzaqtVar.zzd(t));
    }

    private static <T> int zzs(T t, long j) {
        return ((Integer) zzard.zzf(t, j)).intValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <K, V> int zzt(T t, byte[] bArr, int i, int i2, int i3, long j, zzalv zzalvVar) throws IOException {
        Unsafe unsafe = zzb;
        Object objZzH = zzH(i3);
        Object object = unsafe.getObject(t, j);
        if (zzapa.zzb(object)) {
            zzaoz<K, V> zzaozVarZzb = zzaoz.zza().zzb();
            zzapa.zzc(zzaozVarZzb, object);
            unsafe.putObject(t, j, zzaozVarZzb);
            object = zzaozVarZzb;
        }
        zzaox<K, V> zzaoxVarZzc = ((zzaoy) objZzH).zzc();
        zzaoz zzaozVar = (zzaoz) object;
        int iZzj = zzalw.zzj(bArr, i, zzalvVar);
        int i4 = zzalvVar.zza;
        if (i4 < 0 || i4 > i2 - iZzj) {
            throw zzaoe.zzj();
        }
        int i5 = iZzj + i4;
        Object obj = zzaoxVarZzc.zzb;
        Object obj2 = zzaoxVarZzc.zzd;
        while (iZzj < i5) {
            int iZzk = iZzj + 1;
            int i6 = bArr[iZzj];
            if (i6 < 0) {
                iZzk = zzalw.zzk(i6, bArr, iZzk, zzalvVar);
                i6 = zzalvVar.zza;
            }
            int i7 = i6 & 7;
            int i8 = i6 >>> 3;
            if (i8 != 1) {
                if (i8 == 2 && i7 == zzaoxVarZzc.zzc.zza()) {
                    iZzj = zzX(bArr, iZzk, i2, zzaoxVarZzc.zzc, zzaoxVarZzc.zzd.getClass(), zzalvVar);
                    obj2 = zzalvVar.zzc;
                } else {
                    iZzj = zzalw.zzn(i6, bArr, iZzk, i2, zzalvVar);
                }
            } else if (i7 == zzaoxVarZzc.zza.zza()) {
                iZzj = zzX(bArr, iZzk, i2, zzaoxVarZzc.zza, null, zzalvVar);
                obj = zzalvVar.zzc;
            } else {
                iZzj = zzalw.zzn(i6, bArr, iZzk, i2, zzalvVar);
            }
        }
        if (iZzj != i5) {
            throw zzaoe.zzg();
        }
        zzaozVar.put(obj, obj2);
        return i5;
    }

    private final int zzu(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzalv zzalvVar) throws IOException {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Double.valueOf(Double.longBitsToDouble(zzalw.zzo(bArr, i))));
                unsafe.putInt(t, j2, i4);
                return i + 8;
            case 52:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Float.valueOf(Float.intBitsToFloat(zzalw.zzb(bArr, i))));
                unsafe.putInt(t, j2, i4);
                return i + 4;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                int iZzm = zzalw.zzm(bArr, i, zzalvVar);
                unsafe.putObject(t, j, Long.valueOf(zzalvVar.zzb));
                unsafe.putInt(t, j2, i4);
                return iZzm;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                int iZzj = zzalw.zzj(bArr, i, zzalvVar);
                unsafe.putObject(t, j, Integer.valueOf(zzalvVar.zza));
                unsafe.putInt(t, j2, i4);
                return iZzj;
            case 56:
            case 65:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Long.valueOf(zzalw.zzo(bArr, i)));
                unsafe.putInt(t, j2, i4);
                return i + 8;
            case 57:
            case 64:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Integer.valueOf(zzalw.zzb(bArr, i)));
                unsafe.putInt(t, j2, i4);
                return i + 4;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                int iZzm2 = zzalw.zzm(bArr, i, zzalvVar);
                unsafe.putObject(t, j, Boolean.valueOf(zzalvVar.zzb != 0));
                unsafe.putInt(t, j2, i4);
                return iZzm2;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZzj2 = zzalw.zzj(bArr, i, zzalvVar);
                int i9 = zzalvVar.zza;
                if (i9 == 0) {
                    unsafe.putObject(t, j, "");
                } else {
                    if ((i6 & 536870912) != 0 && !zzari.zzi(bArr, iZzj2, iZzj2 + i9)) {
                        throw zzaoe.zzd();
                    }
                    unsafe.putObject(t, j, new String(bArr, iZzj2, i9, zzaoc.zza));
                    iZzj2 += i9;
                }
                unsafe.putInt(t, j2, i4);
                return iZzj2;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZzd = zzalw.zzd(zzF(i8), bArr, i, i2, zzalvVar);
                Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object == null) {
                    unsafe.putObject(t, j, zzalvVar.zzc);
                } else {
                    unsafe.putObject(t, j, zzaoc.zzg(object, zzalvVar.zzc));
                }
                unsafe.putInt(t, j2, i4);
                return iZzd;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzalw.zza(bArr, i, zzalvVar);
                unsafe.putObject(t, j, zzalvVar.zzc);
                unsafe.putInt(t, j2, i4);
                return iZza;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZzj3 = zzalw.zzj(bArr, i, zzalvVar);
                int i10 = zzalvVar.zza;
                zzanw zzanwVarZzE = zzE(i8);
                if (zzanwVarZzE == null || zzanwVarZzE.zza(i10)) {
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
                int iZzj4 = zzalw.zzj(bArr, i, zzalvVar);
                unsafe.putObject(t, j, Integer.valueOf(zzamq.zzF(zzalvVar.zza)));
                unsafe.putInt(t, j2, i4);
                return iZzj4;
            case 67:
                if (i5 != 0) {
                    return i;
                }
                int iZzm3 = zzalw.zzm(bArr, i, zzalvVar);
                unsafe.putObject(t, j, Long.valueOf(zzamq.zzG(zzalvVar.zzb)));
                unsafe.putInt(t, j2, i4);
                return iZzm3;
            case 68:
                if (i5 != 3) {
                    return i;
                }
                int iZzc = zzalw.zzc(zzF(i8), bArr, i, i2, (i3 & (-8)) | 4, zzalvVar);
                Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object2 == null) {
                    unsafe.putObject(t, j, zzalvVar.zzc);
                } else {
                    unsafe.putObject(t, j, zzaoc.zzg(object2, zzalvVar.zzc));
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
    private final int zzv(T t, byte[] bArr, int i, int i2, zzalv zzalvVar) throws IOException {
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
        zzalvVar = zzalvVar;
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
                iZzk = zzalw.zzk(b, bArr, i19, zzalvVar);
                i3 = zzalvVar.zza;
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
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 1) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                zzard.zzo(t, j, Double.longBitsToDouble(zzalw.zzo(bArr, iZzk)));
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
                                zzard.zzp(t, j, Float.intBitsToFloat(zzalw.zzb(bArr, iZzk)));
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
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 0) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzm = zzalw.zzm(bArr, iZzk, zzalvVar);
                                unsafe2.putLong(t, j, zzalvVar.zzb);
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
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 0) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzi = zzalw.zzj(bArr, iZzk, zzalvVar);
                                unsafe2.putInt(t, j, zzalvVar.zza);
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 5:
                        case 14:
                            i5 = i20;
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 1) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                unsafe2.putLong(t, j, zzalw.zzo(bArr, iZzk));
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
                                unsafe2.putInt(t, j, zzalw.zzb(bArr, iZzk));
                                iZzi = iZzk + 4;
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 7:
                            i5 = i20;
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 0) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                int iZzm2 = zzalw.zzm(bArr, iZzk, zzalvVar);
                                zzard.zzm(t, j, zzalvVar.zzb != 0);
                                i18 = i9 | i24;
                                iZzi = iZzm2;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 8:
                            i5 = i20;
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            i9 = i18;
                            if (i21 != 2) {
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzi = (536870912 & i22) == 0 ? zzalw.zzg(bArr, iZzk, zzalvVar) : zzalw.zzh(bArr, iZzk, zzalvVar);
                                unsafe2.putObject(t, j, zzalvVar.zzc);
                                i18 = i9 | i24;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 9:
                            i5 = i20;
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            if (i21 != 2) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzd = zzalw.zzd(this.zzF(i8), bArr, iZzk, i2, zzalvVar);
                                Object object = unsafe2.getObject(t, j);
                                if (object == null) {
                                    unsafe2.putObject(t, j, zzalvVar.zzc);
                                } else {
                                    unsafe2.putObject(t, j, zzaoc.zzg(object, zzalvVar.zzc));
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
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            if (i21 != 2) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzd = zzalw.zza(bArr, iZzk, zzalvVar);
                                unsafe2.putObject(t, j, zzalvVar.zzc);
                                i18 |= i24;
                                iZzi = iZzd;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 12:
                            i5 = i20;
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            if (i21 != 0) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzd = zzalw.zzj(bArr, iZzk, zzalvVar);
                                unsafe2.putInt(t, j, zzalvVar.zza);
                                i18 |= i24;
                                iZzi = iZzd;
                                i17 = i8;
                                i15 = i5;
                                i14 = -1;
                            }
                            break;
                        case 15:
                            i5 = i20;
                            zzalvVar = zzalvVar;
                            i8 = iZzy;
                            if (i21 != 0) {
                                i9 = i18;
                                i18 = i9;
                                i4 = iZzk;
                                i7 = i8;
                                unsafe = unsafe2;
                                i6 = -1;
                            } else {
                                iZzi = zzalw.zzj(bArr, iZzk, zzalvVar);
                                unsafe2.putInt(t, j, zzamq.zzF(zzalvVar.zza));
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
                                zzalvVar = zzalvVar;
                                iZzm = zzalw.zzm(bArr, iZzk, zzalvVar);
                                i8 = iZzy;
                                i5 = i20;
                                unsafe2.putLong(t, j, zzamq.zzG(zzalvVar.zzb));
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
                    zzalvVar = zzalvVar;
                    if (iZzB != 27) {
                        if (iZzB <= 49) {
                            i11 = i26;
                            int i27 = iZzk;
                            i10 = i16;
                            i7 = iZzy;
                            i6 = -1;
                            unsafe = unsafe2;
                            iZzi = zzw(t, bArr, iZzk, i2, i3, i5, i21, iZzy, i22, iZzB, j, zzalvVar);
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
                                    iZzi = zzt(t, bArr, i12, i2, i7, j, zzalvVar);
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
                                iZzi = zzu(t, bArr, i12, i2, i3, i5, i21, i22, iZzB, j, i7, zzalvVar);
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
                        zzaob zzaobVarZzd = (zzaob) unsafe2.getObject(t, j);
                        if (!zzaobVarZzd.zzc()) {
                            int size = zzaobVarZzd.size();
                            zzaobVarZzd = zzaobVarZzd.zzd(size == 0 ? 10 : size + size);
                            unsafe2.putObject(t, j, zzaobVarZzd);
                        }
                        iZzi = zzalw.zze(this.zzF(iZzy), i3, bArr, iZzk, i2, zzaobVarZzd, zzalvVar);
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
            iZzi = zzalw.zzi(i3, bArr, i4, i2, zzd(t), zzalvVar);
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
        throw zzaoe.zzg();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int zzw(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzalv zzalvVar) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        int iZzj;
        int iZzj2 = i;
        Unsafe unsafe = zzb;
        zzaob zzaobVarZzd = (zzaob) unsafe.getObject(t, j2);
        if (!zzaobVarZzd.zzc()) {
            int size = zzaobVarZzd.size();
            zzaobVarZzd = zzaobVarZzd.zzd(size == 0 ? 10 : size + size);
            unsafe.putObject(t, j2, zzaobVarZzd);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzamz zzamzVar = (zzamz) zzaobVarZzd;
                    int iZzj3 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i12 = zzalvVar.zza + iZzj3;
                    while (iZzj3 < i12) {
                        zzamzVar.zze(Double.longBitsToDouble(zzalw.zzo(bArr, iZzj3)));
                        iZzj3 += 8;
                    }
                    if (iZzj3 == i12) {
                        return iZzj3;
                    }
                    throw zzaoe.zzj();
                }
                if (i5 == 1) {
                    zzamz zzamzVar2 = (zzamz) zzaobVarZzd;
                    zzamzVar2.zze(Double.longBitsToDouble(zzalw.zzo(bArr, i)));
                    while (true) {
                        i8 = iZzj2 + 8;
                        if (i8 < i2) {
                            iZzj2 = zzalw.zzj(bArr, i8, zzalvVar);
                            if (i3 == zzalvVar.zza) {
                                zzamzVar2.zze(Double.longBitsToDouble(zzalw.zzo(bArr, iZzj2)));
                            }
                        }
                    }
                    return i8;
                }
                return iZzj2;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzanj zzanjVar = (zzanj) zzaobVarZzd;
                    int iZzj4 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i13 = zzalvVar.zza + iZzj4;
                    while (iZzj4 < i13) {
                        zzanjVar.zzf(Float.intBitsToFloat(zzalw.zzb(bArr, iZzj4)));
                        iZzj4 += 4;
                    }
                    if (iZzj4 == i13) {
                        return iZzj4;
                    }
                    throw zzaoe.zzj();
                }
                if (i5 == 5) {
                    zzanj zzanjVar2 = (zzanj) zzaobVarZzd;
                    zzanjVar2.zzf(Float.intBitsToFloat(zzalw.zzb(bArr, i)));
                    while (true) {
                        i9 = iZzj2 + 4;
                        if (i9 < i2) {
                            iZzj2 = zzalw.zzj(bArr, i9, zzalvVar);
                            if (i3 == zzalvVar.zza) {
                                zzanjVar2.zzf(Float.intBitsToFloat(zzalw.zzb(bArr, iZzj2)));
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
                    zzaot zzaotVar = (zzaot) zzaobVarZzd;
                    int iZzj5 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i14 = zzalvVar.zza + iZzj5;
                    while (iZzj5 < i14) {
                        iZzj5 = zzalw.zzm(bArr, iZzj5, zzalvVar);
                        zzaotVar.zzg(zzalvVar.zzb);
                    }
                    if (iZzj5 == i14) {
                        return iZzj5;
                    }
                    throw zzaoe.zzj();
                }
                if (i5 == 0) {
                    zzaot zzaotVar2 = (zzaot) zzaobVarZzd;
                    int iZzm = zzalw.zzm(bArr, iZzj2, zzalvVar);
                    zzaotVar2.zzg(zzalvVar.zzb);
                    while (iZzm < i2) {
                        int iZzj6 = zzalw.zzj(bArr, iZzm, zzalvVar);
                        if (i3 != zzalvVar.zza) {
                            return iZzm;
                        }
                        iZzm = zzalw.zzm(bArr, iZzj6, zzalvVar);
                        zzaotVar2.zzg(zzalvVar.zzb);
                    }
                    return iZzm;
                }
                return iZzj2;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzalw.zzf(bArr, iZzj2, zzaobVarZzd, zzalvVar);
                }
                if (i5 == 0) {
                    return zzalw.zzl(i3, bArr, i, i2, zzaobVarZzd, zzalvVar);
                }
                return iZzj2;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzaot zzaotVar3 = (zzaot) zzaobVarZzd;
                    int iZzj7 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i15 = zzalvVar.zza + iZzj7;
                    while (iZzj7 < i15) {
                        zzaotVar3.zzg(zzalw.zzo(bArr, iZzj7));
                        iZzj7 += 8;
                    }
                    if (iZzj7 == i15) {
                        return iZzj7;
                    }
                    throw zzaoe.zzj();
                }
                if (i5 == 1) {
                    zzaot zzaotVar4 = (zzaot) zzaobVarZzd;
                    zzaotVar4.zzg(zzalw.zzo(bArr, i));
                    while (true) {
                        i10 = iZzj2 + 8;
                        if (i10 < i2) {
                            iZzj2 = zzalw.zzj(bArr, i10, zzalvVar);
                            if (i3 == zzalvVar.zza) {
                                zzaotVar4.zzg(zzalw.zzo(bArr, iZzj2));
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
                    zzant zzantVar = (zzant) zzaobVarZzd;
                    int iZzj8 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i16 = zzalvVar.zza + iZzj8;
                    while (iZzj8 < i16) {
                        zzantVar.zzg(zzalw.zzb(bArr, iZzj8));
                        iZzj8 += 4;
                    }
                    if (iZzj8 == i16) {
                        return iZzj8;
                    }
                    throw zzaoe.zzj();
                }
                if (i5 == 5) {
                    zzant zzantVar2 = (zzant) zzaobVarZzd;
                    zzantVar2.zzg(zzalw.zzb(bArr, i));
                    while (true) {
                        i11 = iZzj2 + 4;
                        if (i11 < i2) {
                            iZzj2 = zzalw.zzj(bArr, i11, zzalvVar);
                            if (i3 == zzalvVar.zza) {
                                zzantVar2.zzg(zzalw.zzb(bArr, iZzj2));
                            }
                        }
                    }
                    return i11;
                }
                return iZzj2;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzalx zzalxVar = (zzalx) zzaobVarZzd;
                    iZzj = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i17 = zzalvVar.zza + iZzj;
                    while (iZzj < i17) {
                        iZzj = zzalw.zzm(bArr, iZzj, zzalvVar);
                        zzalxVar.zze(zzalvVar.zzb != 0);
                    }
                    if (iZzj != i17) {
                        throw zzaoe.zzj();
                    }
                    return iZzj;
                }
                if (i5 == 0) {
                    zzalx zzalxVar2 = (zzalx) zzaobVarZzd;
                    int iZzm2 = zzalw.zzm(bArr, iZzj2, zzalvVar);
                    zzalxVar2.zze(zzalvVar.zzb != 0);
                    while (iZzm2 < i2) {
                        int iZzj9 = zzalw.zzj(bArr, iZzm2, zzalvVar);
                        if (i3 != zzalvVar.zza) {
                            return iZzm2;
                        }
                        iZzm2 = zzalw.zzm(bArr, iZzj9, zzalvVar);
                        zzalxVar2.zze(zzalvVar.zzb != 0);
                    }
                    return iZzm2;
                }
                return iZzj2;
            case 26:
                if (i5 == 2) {
                    if ((j & TagBits.HasTypeVariable) == 0) {
                        int iZzj10 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                        int i18 = zzalvVar.zza;
                        if (i18 < 0) {
                            throw zzaoe.zzf();
                        }
                        if (i18 == 0) {
                            zzaobVarZzd.add("");
                        } else {
                            zzaobVarZzd.add(new String(bArr, iZzj10, i18, zzaoc.zza));
                            iZzj10 += i18;
                        }
                        while (iZzj10 < i2) {
                            int iZzj11 = zzalw.zzj(bArr, iZzj10, zzalvVar);
                            if (i3 != zzalvVar.zza) {
                                return iZzj10;
                            }
                            iZzj10 = zzalw.zzj(bArr, iZzj11, zzalvVar);
                            int i19 = zzalvVar.zza;
                            if (i19 < 0) {
                                throw zzaoe.zzf();
                            }
                            if (i19 == 0) {
                                zzaobVarZzd.add("");
                            } else {
                                zzaobVarZzd.add(new String(bArr, iZzj10, i19, zzaoc.zza));
                                iZzj10 += i19;
                            }
                        }
                        return iZzj10;
                    }
                    int iZzj12 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i20 = zzalvVar.zza;
                    if (i20 < 0) {
                        throw zzaoe.zzf();
                    }
                    if (i20 == 0) {
                        zzaobVarZzd.add("");
                    } else {
                        int i21 = iZzj12 + i20;
                        if (!zzari.zzi(bArr, iZzj12, i21)) {
                            throw zzaoe.zzd();
                        }
                        zzaobVarZzd.add(new String(bArr, iZzj12, i20, zzaoc.zza));
                        iZzj12 = i21;
                    }
                    while (iZzj12 < i2) {
                        int iZzj13 = zzalw.zzj(bArr, iZzj12, zzalvVar);
                        if (i3 != zzalvVar.zza) {
                            return iZzj12;
                        }
                        iZzj12 = zzalw.zzj(bArr, iZzj13, zzalvVar);
                        int i22 = zzalvVar.zza;
                        if (i22 < 0) {
                            throw zzaoe.zzf();
                        }
                        if (i22 == 0) {
                            zzaobVarZzd.add("");
                        } else {
                            int i23 = iZzj12 + i22;
                            if (!zzari.zzi(bArr, iZzj12, i23)) {
                                throw zzaoe.zzd();
                            }
                            zzaobVarZzd.add(new String(bArr, iZzj12, i22, zzaoc.zza));
                            iZzj12 = i23;
                        }
                    }
                    return iZzj12;
                }
                return iZzj2;
            case 27:
                if (i5 == 2) {
                    return zzalw.zze(zzF(i6), i3, bArr, i, i2, zzaobVarZzd, zzalvVar);
                }
                return iZzj2;
            case 28:
                if (i5 == 2) {
                    int iZzj14 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i24 = zzalvVar.zza;
                    if (i24 < 0) {
                        throw zzaoe.zzf();
                    }
                    if (i24 > bArr.length - iZzj14) {
                        throw zzaoe.zzj();
                    }
                    if (i24 == 0) {
                        zzaobVarZzd.add(zzamk.zzb);
                    } else {
                        zzaobVarZzd.add(zzamk.zzs(bArr, iZzj14, i24));
                        iZzj14 += i24;
                    }
                    while (iZzj14 < i2) {
                        int iZzj15 = zzalw.zzj(bArr, iZzj14, zzalvVar);
                        if (i3 != zzalvVar.zza) {
                            return iZzj14;
                        }
                        iZzj14 = zzalw.zzj(bArr, iZzj15, zzalvVar);
                        int i25 = zzalvVar.zza;
                        if (i25 < 0) {
                            throw zzaoe.zzf();
                        }
                        if (i25 > bArr.length - iZzj14) {
                            throw zzaoe.zzj();
                        }
                        if (i25 == 0) {
                            zzaobVarZzd.add(zzamk.zzb);
                        } else {
                            zzaobVarZzd.add(zzamk.zzs(bArr, iZzj14, i25));
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
                        iZzj = zzalw.zzl(i3, bArr, i, i2, zzaobVarZzd, zzalvVar);
                    }
                    return iZzj2;
                }
                iZzj = zzalw.zzf(bArr, iZzj2, zzaobVarZzd, zzalvVar);
                zzans zzansVar = (zzans) t;
                zzaqu zzaquVar = zzansVar.zzc;
                if (zzaquVar == zzaqu.zzc()) {
                    zzaquVar = null;
                }
                Object objZzC = zzaqb.zzC(i4, zzaobVarZzd, zzE(i6), zzaquVar, this.zzo);
                if (objZzC != null) {
                    zzansVar.zzc = (zzaqu) objZzC;
                    return iZzj;
                }
                return iZzj;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzant zzantVar3 = (zzant) zzaobVarZzd;
                    int iZzj16 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i26 = zzalvVar.zza + iZzj16;
                    while (iZzj16 < i26) {
                        iZzj16 = zzalw.zzj(bArr, iZzj16, zzalvVar);
                        zzantVar3.zzg(zzamq.zzF(zzalvVar.zza));
                    }
                    if (iZzj16 == i26) {
                        return iZzj16;
                    }
                    throw zzaoe.zzj();
                }
                if (i5 == 0) {
                    zzant zzantVar4 = (zzant) zzaobVarZzd;
                    int iZzj17 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    zzantVar4.zzg(zzamq.zzF(zzalvVar.zza));
                    while (iZzj17 < i2) {
                        int iZzj18 = zzalw.zzj(bArr, iZzj17, zzalvVar);
                        if (i3 != zzalvVar.zza) {
                            return iZzj17;
                        }
                        iZzj17 = zzalw.zzj(bArr, iZzj18, zzalvVar);
                        zzantVar4.zzg(zzamq.zzF(zzalvVar.zza));
                    }
                    return iZzj17;
                }
                return iZzj2;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzaot zzaotVar5 = (zzaot) zzaobVarZzd;
                    int iZzj19 = zzalw.zzj(bArr, iZzj2, zzalvVar);
                    int i27 = zzalvVar.zza + iZzj19;
                    while (iZzj19 < i27) {
                        iZzj19 = zzalw.zzm(bArr, iZzj19, zzalvVar);
                        zzaotVar5.zzg(zzamq.zzG(zzalvVar.zzb));
                    }
                    if (iZzj19 == i27) {
                        return iZzj19;
                    }
                    throw zzaoe.zzj();
                }
                if (i5 == 0) {
                    zzaot zzaotVar6 = (zzaot) zzaobVarZzd;
                    int iZzm3 = zzalw.zzm(bArr, iZzj2, zzalvVar);
                    zzaotVar6.zzg(zzamq.zzG(zzalvVar.zzb));
                    while (iZzm3 < i2) {
                        int iZzj20 = zzalw.zzj(bArr, iZzm3, zzalvVar);
                        if (i3 != zzalvVar.zza) {
                            return iZzm3;
                        }
                        iZzm3 = zzalw.zzm(bArr, iZzj20, zzalvVar);
                        zzaotVar6.zzg(zzamq.zzG(zzalvVar.zzb));
                    }
                    return iZzm3;
                }
                return iZzj2;
            default:
                if (i5 == 3) {
                    zzapz zzapzVarZzF = zzF(i6);
                    int i28 = (i3 & (-8)) | 4;
                    int iZzc = zzalw.zzc(zzapzVarZzF, bArr, i, i2, i28, zzalvVar);
                    zzaobVarZzd.add(zzalvVar.zzc);
                    while (iZzc < i2) {
                        int iZzj21 = zzalw.zzj(bArr, iZzc, zzalvVar);
                        if (i3 != zzalvVar.zza) {
                            return iZzc;
                        }
                        iZzc = zzalw.zzc(zzapzVarZzF, bArr, iZzj21, i2, i28, zzalvVar);
                        zzaobVarZzd.add(zzalvVar.zzc);
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

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final int zza(T t) {
        return this.zzj ? zzr(t) : zzq(t);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
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
                    iZzc = zzaoc.zzc(Double.doubleToLongBits(zzard.zza(t, j)));
                    i2 = i + iZzc;
                    break;
                case 1:
                    i = i2 * 53;
                    iZzc = Float.floatToIntBits(zzard.zzb(t, j));
                    i2 = i + iZzc;
                    break;
                case 2:
                    i = i2 * 53;
                    iZzc = zzaoc.zzc(zzard.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 3:
                    i = i2 * 53;
                    iZzc = zzaoc.zzc(zzard.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 4:
                    i = i2 * 53;
                    iZzc = zzard.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 5:
                    i = i2 * 53;
                    iZzc = zzaoc.zzc(zzard.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 6:
                    i = i2 * 53;
                    iZzc = zzard.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 7:
                    i = i2 * 53;
                    iZzc = zzaoc.zza(zzard.zzw(t, j));
                    i2 = i + iZzc;
                    break;
                case 8:
                    i = i2 * 53;
                    iZzc = ((String) zzard.zzf(t, j)).hashCode();
                    i2 = i + iZzc;
                    break;
                case 9:
                    Object objZzf = zzard.zzf(t, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZzc = zzard.zzf(t, j).hashCode();
                    i2 = i + iZzc;
                    break;
                case 11:
                    i = i2 * 53;
                    iZzc = zzard.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 12:
                    i = i2 * 53;
                    iZzc = zzard.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 13:
                    i = i2 * 53;
                    iZzc = zzard.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 14:
                    i = i2 * 53;
                    iZzc = zzaoc.zzc(zzard.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 15:
                    i = i2 * 53;
                    iZzc = zzard.zzc(t, j);
                    i2 = i + iZzc;
                    break;
                case 16:
                    i = i2 * 53;
                    iZzc = zzaoc.zzc(zzard.zzd(t, j));
                    i2 = i + iZzc;
                    break;
                case 17:
                    Object objZzf2 = zzard.zzf(t, j);
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
                    iZzc = zzard.zzf(t, j).hashCode();
                    i2 = i + iZzc;
                    break;
                case 50:
                    i = i2 * 53;
                    iZzc = zzard.zzf(t, j).hashCode();
                    i2 = i + iZzc;
                    break;
                case 51:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzaoc.zzc(Double.doubleToLongBits(zzo(t, j)));
                        i2 = i + iZzc;
                    }
                    break;
                case 52:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = Float.floatToIntBits(zzp(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 53:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzaoc.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 54:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzaoc.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 55:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 56:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzaoc.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 57:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 58:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzaoc.zza(zzW(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 59:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = ((String) zzard.zzf(t, j)).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
                case 60:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzard.zzf(t, j).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
                case 61:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzard.zzf(t, j).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
                case 62:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 63:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 64:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 65:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzaoc.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 66:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzs(t, j);
                        i2 = i + iZzc;
                    }
                    break;
                case 67:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzaoc.zzc(zzD(t, j));
                        i2 = i + iZzc;
                    }
                    break;
                case 68:
                    if (zzV(t, i4, i3)) {
                        i = i2 * 53;
                        iZzc = zzard.zzf(t, j).hashCode();
                        i2 = i + iZzc;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzo.zzd(t).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzp.zzb(t).zza.hashCode() : iHashCode2;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 12041. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    final int zzc(T r29, byte[] r30, int r31, int r32, int r33, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalv r34) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1204
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapi.zzc(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalv):int");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final T zze() {
        return (T) ((zzans) this.zzg).zzb(4, null, null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzf(T t) {
        int i;
        int i2 = this.zzl;
        while (true) {
            i = this.zzm;
            if (i2 >= i) {
                break;
            }
            long jZzC = zzC(this.zzk[i2]) & 1048575;
            Object objZzf = zzard.zzf(t, jZzC);
            if (objZzf != null) {
                ((zzaoz) objZzf).zzc();
                zzard.zzs(t, jZzC, objZzf);
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
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzh(T t, zzapr zzaprVar, zzanc zzancVar) throws IOException {
        zzancVar.getClass();
        zzaqt<?, ?> zzaqtVar = this.zzo;
        zzand<?> zzandVar = this.zzp;
        zzanh zzanhVarZzc = null;
        Object objZzG = null;
        while (true) {
            try {
                int iZzc = zzaprVar.zzc();
                int iZzx = zzx(iZzc);
                if (iZzx >= 0) {
                    int iZzC = zzC(iZzx);
                    try {
                        switch (zzB(iZzC)) {
                            case 0:
                                zzard.zzo(t, iZzC & 1048575, zzaprVar.zza());
                                zzM(t, iZzx);
                                break;
                            case 1:
                                zzard.zzp(t, iZzC & 1048575, zzaprVar.zzb());
                                zzM(t, iZzx);
                                break;
                            case 2:
                                zzard.zzr(t, iZzC & 1048575, zzaprVar.zzl());
                                zzM(t, iZzx);
                                break;
                            case 3:
                                zzard.zzr(t, iZzC & 1048575, zzaprVar.zzo());
                                zzM(t, iZzx);
                                break;
                            case 4:
                                zzard.zzq(t, iZzC & 1048575, zzaprVar.zzg());
                                zzM(t, iZzx);
                                break;
                            case 5:
                                zzard.zzr(t, iZzC & 1048575, zzaprVar.zzk());
                                zzM(t, iZzx);
                                break;
                            case 6:
                                zzard.zzq(t, iZzC & 1048575, zzaprVar.zzf());
                                zzM(t, iZzx);
                                break;
                            case 7:
                                zzard.zzm(t, iZzC & 1048575, zzaprVar.zzO());
                                zzM(t, iZzx);
                                break;
                            case 8:
                                zzL(t, iZzC, zzaprVar);
                                zzM(t, iZzx);
                                break;
                            case 9:
                                if (zzS(t, iZzx)) {
                                    long j = iZzC & 1048575;
                                    zzard.zzs(t, j, zzaoc.zzg(zzard.zzf(t, j), zzaprVar.zzs(zzF(iZzx), zzancVar)));
                                } else {
                                    zzard.zzs(t, iZzC & 1048575, zzaprVar.zzs(zzF(iZzx), zzancVar));
                                    zzM(t, iZzx);
                                }
                                break;
                            case 10:
                                zzard.zzs(t, iZzC & 1048575, zzaprVar.zzp());
                                zzM(t, iZzx);
                                break;
                            case 11:
                                zzard.zzq(t, iZzC & 1048575, zzaprVar.zzj());
                                zzM(t, iZzx);
                                break;
                            case 12:
                                int iZze = zzaprVar.zze();
                                zzanw zzanwVarZzE = zzE(iZzx);
                                if (zzanwVarZzE == null || zzanwVarZzE.zza(iZze)) {
                                    zzard.zzq(t, iZzC & 1048575, iZze);
                                    zzM(t, iZzx);
                                } else {
                                    objZzG = zzaqb.zzD(iZzc, iZze, objZzG, zzaqtVar);
                                }
                                break;
                            case 13:
                                zzard.zzq(t, iZzC & 1048575, zzaprVar.zzh());
                                zzM(t, iZzx);
                                break;
                            case 14:
                                zzard.zzr(t, iZzC & 1048575, zzaprVar.zzm());
                                zzM(t, iZzx);
                                break;
                            case 15:
                                zzard.zzq(t, iZzC & 1048575, zzaprVar.zzi());
                                zzM(t, iZzx);
                                break;
                            case 16:
                                zzard.zzr(t, iZzC & 1048575, zzaprVar.zzn());
                                zzM(t, iZzx);
                                break;
                            case 17:
                                if (zzS(t, iZzx)) {
                                    long j2 = iZzC & 1048575;
                                    zzard.zzs(t, j2, zzaoc.zzg(zzard.zzf(t, j2), zzaprVar.zzr(zzF(iZzx), zzancVar)));
                                } else {
                                    zzard.zzs(t, iZzC & 1048575, zzaprVar.zzr(zzF(iZzx), zzancVar));
                                    zzM(t, iZzx);
                                }
                                break;
                            case 18:
                                zzaprVar.zzx(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 19:
                                zzaprVar.zzB(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 20:
                                zzaprVar.zzE(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 21:
                                zzaprVar.zzN(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 22:
                                zzaprVar.zzD(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 23:
                                zzaprVar.zzA(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 24:
                                zzaprVar.zzz(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 25:
                                zzaprVar.zzv(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 26:
                                if (zzR(iZzC)) {
                                    ((zzamr) zzaprVar).zzL(this.zzn.zza(t, iZzC & 1048575), true);
                                } else {
                                    ((zzamr) zzaprVar).zzL(this.zzn.zza(t, iZzC & 1048575), false);
                                }
                                break;
                            case 27:
                                zzaprVar.zzG(this.zzn.zza(t, iZzC & 1048575), zzF(iZzx), zzancVar);
                                break;
                            case 28:
                                zzaprVar.zzw(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 29:
                                zzaprVar.zzM(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 30:
                                List<Integer> listZza = this.zzn.zza(t, iZzC & 1048575);
                                zzaprVar.zzy(listZza);
                                objZzG = zzaqb.zzC(iZzc, listZza, zzE(iZzx), objZzG, zzaqtVar);
                                break;
                            case 31:
                                zzaprVar.zzH(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 32:
                                zzaprVar.zzI(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 33:
                                zzaprVar.zzJ(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 34:
                                zzaprVar.zzK(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 35:
                                zzaprVar.zzx(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 36:
                                zzaprVar.zzB(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 37:
                                zzaprVar.zzE(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 38:
                                zzaprVar.zzN(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 39:
                                zzaprVar.zzD(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 40:
                                zzaprVar.zzA(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 41:
                                zzaprVar.zzz(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 42:
                                zzaprVar.zzv(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 43:
                                zzaprVar.zzM(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 44:
                                List<Integer> listZza2 = this.zzn.zza(t, iZzC & 1048575);
                                zzaprVar.zzy(listZza2);
                                objZzG = zzaqb.zzC(iZzc, listZza2, zzE(iZzx), objZzG, zzaqtVar);
                                break;
                            case 45:
                                zzaprVar.zzH(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 46:
                                zzaprVar.zzI(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 47:
                                zzaprVar.zzJ(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 48:
                                zzaprVar.zzK(this.zzn.zza(t, iZzC & 1048575));
                                break;
                            case 49:
                                zzaprVar.zzC(this.zzn.zza(t, iZzC & 1048575), zzF(iZzx), zzancVar);
                                break;
                            case 50:
                                Object objZzH = zzH(iZzx);
                                long jZzC = zzC(iZzx) & 1048575;
                                Object objZzf = zzard.zzf(t, jZzC);
                                if (objZzf == null) {
                                    objZzf = zzaoz.zza().zzb();
                                    zzard.zzs(t, jZzC, objZzf);
                                } else if (zzapa.zzb(objZzf)) {
                                    Object objZzb = zzaoz.zza().zzb();
                                    zzapa.zzc(objZzb, objZzf);
                                    zzard.zzs(t, jZzC, objZzb);
                                    objZzf = objZzb;
                                }
                                zzaprVar.zzF((zzaoz) objZzf, ((zzaoy) objZzH).zzc(), zzancVar);
                                break;
                            case 51:
                                zzard.zzs(t, iZzC & 1048575, Double.valueOf(zzaprVar.zza()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 52:
                                zzard.zzs(t, iZzC & 1048575, Float.valueOf(zzaprVar.zzb()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 53:
                                zzard.zzs(t, iZzC & 1048575, Long.valueOf(zzaprVar.zzl()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 54:
                                zzard.zzs(t, iZzC & 1048575, Long.valueOf(zzaprVar.zzo()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 55:
                                zzard.zzs(t, iZzC & 1048575, Integer.valueOf(zzaprVar.zzg()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 56:
                                zzard.zzs(t, iZzC & 1048575, Long.valueOf(zzaprVar.zzk()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 57:
                                zzard.zzs(t, iZzC & 1048575, Integer.valueOf(zzaprVar.zzf()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 58:
                                zzard.zzs(t, iZzC & 1048575, Boolean.valueOf(zzaprVar.zzO()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 59:
                                zzL(t, iZzC, zzaprVar);
                                zzN(t, iZzc, iZzx);
                                break;
                            case 60:
                                if (zzV(t, iZzc, iZzx)) {
                                    long j3 = iZzC & 1048575;
                                    zzard.zzs(t, j3, zzaoc.zzg(zzard.zzf(t, j3), zzaprVar.zzs(zzF(iZzx), zzancVar)));
                                } else {
                                    zzard.zzs(t, iZzC & 1048575, zzaprVar.zzs(zzF(iZzx), zzancVar));
                                    zzM(t, iZzx);
                                }
                                zzN(t, iZzc, iZzx);
                                break;
                            case 61:
                                zzard.zzs(t, iZzC & 1048575, zzaprVar.zzp());
                                zzN(t, iZzc, iZzx);
                                break;
                            case 62:
                                zzard.zzs(t, iZzC & 1048575, Integer.valueOf(zzaprVar.zzj()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 63:
                                int iZze2 = zzaprVar.zze();
                                zzanw zzanwVarZzE2 = zzE(iZzx);
                                if (zzanwVarZzE2 == null || zzanwVarZzE2.zza(iZze2)) {
                                    zzard.zzs(t, iZzC & 1048575, Integer.valueOf(iZze2));
                                    zzN(t, iZzc, iZzx);
                                } else {
                                    objZzG = zzaqb.zzD(iZzc, iZze2, objZzG, zzaqtVar);
                                }
                                break;
                            case 64:
                                zzard.zzs(t, iZzC & 1048575, Integer.valueOf(zzaprVar.zzh()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 65:
                                zzard.zzs(t, iZzC & 1048575, Long.valueOf(zzaprVar.zzm()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 66:
                                zzard.zzs(t, iZzC & 1048575, Integer.valueOf(zzaprVar.zzi()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 67:
                                zzard.zzs(t, iZzC & 1048575, Long.valueOf(zzaprVar.zzn()));
                                zzN(t, iZzc, iZzx);
                                break;
                            case 68:
                                zzard.zzs(t, iZzC & 1048575, zzaprVar.zzr(zzF(iZzx), zzancVar));
                                zzN(t, iZzc, iZzx);
                                break;
                            default:
                                if (objZzG == null) {
                                    objZzG = zzaqtVar.zzf();
                                }
                                if (!zzaqtVar.zzr((Object) objZzG, zzaprVar)) {
                                    for (int i = this.zzl; i < this.zzm; i++) {
                                        objZzG = zzG(t, this.zzk[i], objZzG, zzaqtVar);
                                    }
                                    if (objZzG != null) {
                                        zzaqtVar.zzn(t, (Object) objZzG);
                                        return;
                                    }
                                    return;
                                }
                                break;
                                break;
                        }
                    } catch (zzaod unused) {
                        zzaqtVar.zzs(zzaprVar);
                        if (objZzG == null) {
                            objZzG = zzaqtVar.zzc(t);
                        }
                        if (!zzaqtVar.zzr((Object) objZzG, zzaprVar)) {
                            for (int i2 = this.zzl; i2 < this.zzm; i2++) {
                                objZzG = zzG(t, this.zzk[i2], objZzG, zzaqtVar);
                            }
                            if (objZzG != null) {
                                zzaqtVar.zzn(t, (Object) objZzG);
                                return;
                            }
                            return;
                        }
                    }
                } else {
                    if (iZzc == Integer.MAX_VALUE) {
                        for (int i3 = this.zzl; i3 < this.zzm; i3++) {
                            objZzG = zzG(t, this.zzk[i3], objZzG, zzaqtVar);
                        }
                        if (objZzG != null) {
                            zzaqtVar.zzn(t, (Object) objZzG);
                            return;
                        }
                        return;
                    }
                    Object objZzd = !this.zzh ? null : zzandVar.zzd(zzancVar, this.zzg, iZzc);
                    if (objZzd != null) {
                        if (zzanhVarZzc == null) {
                            zzanhVarZzc = zzandVar.zzc(t);
                        }
                        zzanh zzanhVar = zzanhVarZzc;
                        objZzG = zzandVar.zze(zzaprVar, objZzd, zzancVar, zzanhVar, objZzG, zzaqtVar);
                        zzanhVarZzc = zzanhVar;
                    } else {
                        zzaqtVar.zzs(zzaprVar);
                        if (objZzG == null) {
                            objZzG = zzaqtVar.zzc(t);
                        }
                        if (!zzaqtVar.zzr((Object) objZzG, zzaprVar)) {
                            for (int i4 = this.zzl; i4 < this.zzm; i4++) {
                                objZzG = zzG(t, this.zzk[i4], objZzG, zzaqtVar);
                            }
                            if (objZzG != null) {
                                zzaqtVar.zzn(t, (Object) objZzG);
                                return;
                            }
                            return;
                        }
                    }
                }
            } catch (Throwable th) {
                for (int i5 = this.zzl; i5 < this.zzm; i5++) {
                    objZzG = zzG(t, this.zzk[i5], objZzG, zzaqtVar);
                }
                if (objZzG != null) {
                    zzaqtVar.zzn(t, (Object) objZzG);
                }
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzi(T t, byte[] bArr, int i, int i2, zzalv zzalvVar) throws IOException {
        if (this.zzj) {
            zzv(t, bArr, i, i2, zzalvVar);
        } else {
            zzc(t, bArr, i, i2, 0, zzalvVar);
        }
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0022  */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzj(T t, zzarl zzarlVar) throws IOException {
        Iterator itZzh;
        Map.Entry<?, ?> entry;
        if (!this.zzj) {
            zzO(t, zzarlVar);
            return;
        }
        if (this.zzh) {
            zzanh<T> zzanhVarZzb = this.zzp.zzb(t);
            if (zzanhVarZzb.zza.isEmpty()) {
                itZzh = null;
                entry = null;
            } else {
                itZzh = zzanhVarZzb.zzh();
                entry = (Map.Entry) itZzh.next();
            }
        } else {
            itZzh = null;
            entry = null;
        }
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzC = zzC(i);
            int i2 = this.zzc[i];
            while (entry != null && this.zzp.zza(entry) <= i2) {
                this.zzp.zzi(zzarlVar, entry);
                entry = itZzh.hasNext() ? (Map.Entry) itZzh.next() : null;
            }
            switch (zzB(iZzC)) {
                case 0:
                    if (zzS(t, i)) {
                        zzarlVar.zzf(i2, zzard.zza(t, iZzC & 1048575));
                    }
                    break;
                case 1:
                    if (zzS(t, i)) {
                        zzarlVar.zzo(i2, zzard.zzb(t, iZzC & 1048575));
                    }
                    break;
                case 2:
                    if (zzS(t, i)) {
                        zzarlVar.zzt(i2, zzard.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 3:
                    if (zzS(t, i)) {
                        zzarlVar.zzL(i2, zzard.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 4:
                    if (zzS(t, i)) {
                        zzarlVar.zzr(i2, zzard.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 5:
                    if (zzS(t, i)) {
                        zzarlVar.zzm(i2, zzard.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 6:
                    if (zzS(t, i)) {
                        zzarlVar.zzk(i2, zzard.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 7:
                    if (zzS(t, i)) {
                        zzarlVar.zzb(i2, zzard.zzw(t, iZzC & 1048575));
                    }
                    break;
                case 8:
                    if (zzS(t, i)) {
                        zzY(i2, zzard.zzf(t, iZzC & 1048575), zzarlVar);
                    }
                    break;
                case 9:
                    if (zzS(t, i)) {
                        zzarlVar.zzw(i2, zzard.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
                case 10:
                    if (zzS(t, i)) {
                        zzarlVar.zzd(i2, (zzamk) zzard.zzf(t, iZzC & 1048575));
                    }
                    break;
                case 11:
                    if (zzS(t, i)) {
                        zzarlVar.zzJ(i2, zzard.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 12:
                    if (zzS(t, i)) {
                        zzarlVar.zzi(i2, zzard.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 13:
                    if (zzS(t, i)) {
                        zzarlVar.zzy(i2, zzard.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 14:
                    if (zzS(t, i)) {
                        zzarlVar.zzA(i2, zzard.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 15:
                    if (zzS(t, i)) {
                        zzarlVar.zzC(i2, zzard.zzc(t, iZzC & 1048575));
                    }
                    break;
                case 16:
                    if (zzS(t, i)) {
                        zzarlVar.zzE(i2, zzard.zzd(t, iZzC & 1048575));
                    }
                    break;
                case 17:
                    if (zzS(t, i)) {
                        zzarlVar.zzq(i2, zzard.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
                case 18:
                    zzaqb.zzJ(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 19:
                    zzaqb.zzN(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 20:
                    zzaqb.zzQ(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 21:
                    zzaqb.zzY(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 22:
                    zzaqb.zzP(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 23:
                    zzaqb.zzM(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 24:
                    zzaqb.zzL(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 25:
                    zzaqb.zzH(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 26:
                    zzaqb.zzW(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar);
                    break;
                case 27:
                    zzaqb.zzR(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, zzF(i));
                    break;
                case 28:
                    zzaqb.zzI(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar);
                    break;
                case 29:
                    zzaqb.zzX(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 30:
                    zzaqb.zzK(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 31:
                    zzaqb.zzS(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 32:
                    zzaqb.zzT(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 33:
                    zzaqb.zzU(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 34:
                    zzaqb.zzV(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, false);
                    break;
                case 35:
                    zzaqb.zzJ(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 36:
                    zzaqb.zzN(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 37:
                    zzaqb.zzQ(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 38:
                    zzaqb.zzY(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 39:
                    zzaqb.zzP(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 40:
                    zzaqb.zzM(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 41:
                    zzaqb.zzL(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 42:
                    zzaqb.zzH(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 43:
                    zzaqb.zzX(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 44:
                    zzaqb.zzK(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 45:
                    zzaqb.zzS(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 46:
                    zzaqb.zzT(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 47:
                    zzaqb.zzU(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 48:
                    zzaqb.zzV(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, true);
                    break;
                case 49:
                    zzaqb.zzO(this.zzc[i], (List) zzard.zzf(t, iZzC & 1048575), zzarlVar, zzF(i));
                    break;
                case 50:
                    zzP(zzarlVar, i2, zzard.zzf(t, iZzC & 1048575), i);
                    break;
                case 51:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzf(i2, zzo(t, iZzC & 1048575));
                    }
                    break;
                case 52:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzo(i2, zzp(t, iZzC & 1048575));
                    }
                    break;
                case 53:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzt(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 54:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzL(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 55:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzr(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 56:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzm(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 57:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzk(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 58:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzb(i2, zzW(t, iZzC & 1048575));
                    }
                    break;
                case 59:
                    if (zzV(t, i2, i)) {
                        zzY(i2, zzard.zzf(t, iZzC & 1048575), zzarlVar);
                    }
                    break;
                case 60:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzw(i2, zzard.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
                case 61:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzd(i2, (zzamk) zzard.zzf(t, iZzC & 1048575));
                    }
                    break;
                case 62:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzJ(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 63:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzi(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 64:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzy(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 65:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzA(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 66:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzC(i2, zzs(t, iZzC & 1048575));
                    }
                    break;
                case 67:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzE(i2, zzD(t, iZzC & 1048575));
                    }
                    break;
                case 68:
                    if (zzV(t, i2, i)) {
                        zzarlVar.zzq(i2, zzard.zzf(t, iZzC & 1048575), zzF(i));
                    }
                    break;
            }
        }
        while (entry != null) {
            this.zzp.zzi(zzarlVar, entry);
            entry = itZzh.hasNext() ? (Map.Entry) itZzh.next() : null;
        }
        zzaqt<?, ?> zzaqtVar = this.zzo;
        zzaqtVar.zzq(zzaqtVar.zzd(t), zzarlVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final boolean zzk(T t, T t2) {
        boolean zZzZ;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzC = zzC(i);
            long j = iZzC & 1048575;
            switch (zzB(iZzC)) {
                case 0:
                    if (!zzQ(t, t2, i) || Double.doubleToLongBits(zzard.zza(t, j)) != Double.doubleToLongBits(zzard.zza(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzQ(t, t2, i) || Float.floatToIntBits(zzard.zzb(t, j)) != Float.floatToIntBits(zzard.zzb(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzQ(t, t2, i) || zzard.zzd(t, j) != zzard.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzQ(t, t2, i) || zzard.zzd(t, j) != zzard.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzQ(t, t2, i) || zzard.zzc(t, j) != zzard.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzQ(t, t2, i) || zzard.zzd(t, j) != zzard.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzQ(t, t2, i) || zzard.zzc(t, j) != zzard.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzQ(t, t2, i) || zzard.zzw(t, j) != zzard.zzw(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzQ(t, t2, i) || !zzaqb.zzZ(zzard.zzf(t, j), zzard.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzQ(t, t2, i) || !zzaqb.zzZ(zzard.zzf(t, j), zzard.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzQ(t, t2, i) || !zzaqb.zzZ(zzard.zzf(t, j), zzard.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzQ(t, t2, i) || zzard.zzc(t, j) != zzard.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzQ(t, t2, i) || zzard.zzc(t, j) != zzard.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzQ(t, t2, i) || zzard.zzc(t, j) != zzard.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzQ(t, t2, i) || zzard.zzd(t, j) != zzard.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzQ(t, t2, i) || zzard.zzc(t, j) != zzard.zzc(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzQ(t, t2, i) || zzard.zzd(t, j) != zzard.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzQ(t, t2, i) || !zzaqb.zzZ(zzard.zzf(t, j), zzard.zzf(t2, j))) {
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
                    zZzZ = zzaqb.zzZ(zzard.zzf(t, j), zzard.zzf(t2, j));
                    break;
                case 50:
                    zZzZ = zzaqb.zzZ(zzard.zzf(t, j), zzard.zzf(t2, j));
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
                    if (zzard.zzc(t, jZzz) != zzard.zzc(t2, jZzz) || !zzaqb.zzZ(zzard.zzf(t, j), zzard.zzf(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    continue;
                    break;
            }
            if (!zZzZ) {
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

    /* JADX WARN: Code duplicated, block: B:50:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:52:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:55:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:58:0x00f3 A[LOOP:2: B:53:0x00e2->B:58:0x00f3, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:75:0x00f2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:0x0110 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21, types: [com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz] */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz] */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final boolean zzl(T t) {
        int i;
        int i2;
        List list;
        ?? ZzF;
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
            if ((268435456 & iZzC) != 0 && !zzT(t, i7, i, i2, i11)) {
                return false;
            }
            int iZzB = zzB(iZzC);
            if (iZzB == 9 || iZzB == 17) {
                if (zzT(t, i7, i, i2, i11) && !zzU(t, iZzC, zzF(i7))) {
                    return false;
                }
            } else if (iZzB == 27) {
                list = (List) zzard.zzf(t, iZzC & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    ZzF = zzF(i7);
                    for (i3 = 0; i3 < list.size(); i3++) {
                        if (!ZzF.zzl(list.get(i3))) {
                            return false;
                        }
                    }
                }
            } else if (iZzB == 60 || iZzB == 68) {
                if (zzV(t, i8, i7) && !zzU(t, iZzC, zzF(i7))) {
                    return false;
                }
            } else if (iZzB == 49) {
                list = (List) zzard.zzf(t, iZzC & 1048575);
                if (list.isEmpty()) {
                    ZzF = zzF(i7);
                    while (i3 < list.size()) {
                        if (!ZzF.zzl(list.get(i3))) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            } else if (iZzB != 50) {
                continue;
            } else {
                zzaoz zzaozVar = (zzaoz) zzard.zzf(t, iZzC & 1048575);
                if (!zzaozVar.isEmpty() && ((zzaoy) zzH(i7)).zzc().zzc.zzb() == zzark.MESSAGE) {
                    ?? Zzb = 0;
                    for (Object obj : zzaozVar.values()) {
                        if (Zzb == 0) {
                            Zzb = Zzb;
                            Zzb = zzapo.zza().zzb(obj.getClass());
                        }
                        Zzb = Zzb;
                        if (!Zzb.zzl(obj)) {
                            return false;
                        }
                    }
                }
            }
            i6++;
            i4 = i;
            i5 = i2;
        }
        return !this.zzh || this.zzp.zzb(t).zzn();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzg(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzC = zzC(i);
            long j = 1048575 & iZzC;
            int i2 = this.zzc[i];
            switch (zzB(iZzC)) {
                case 0:
                    if (zzS(t2, i)) {
                        zzard.zzo(t, j, zzard.zza(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 1:
                    if (zzS(t2, i)) {
                        zzard.zzp(t, j, zzard.zzb(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 2:
                    if (zzS(t2, i)) {
                        zzard.zzr(t, j, zzard.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 3:
                    if (zzS(t2, i)) {
                        zzard.zzr(t, j, zzard.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 4:
                    if (zzS(t2, i)) {
                        zzard.zzq(t, j, zzard.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 5:
                    if (zzS(t2, i)) {
                        zzard.zzr(t, j, zzard.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 6:
                    if (zzS(t2, i)) {
                        zzard.zzq(t, j, zzard.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 7:
                    if (zzS(t2, i)) {
                        zzard.zzm(t, j, zzard.zzw(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 8:
                    if (zzS(t2, i)) {
                        zzard.zzs(t, j, zzard.zzf(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 9:
                    zzJ(t, t2, i);
                    break;
                case 10:
                    if (zzS(t2, i)) {
                        zzard.zzs(t, j, zzard.zzf(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 11:
                    if (zzS(t2, i)) {
                        zzard.zzq(t, j, zzard.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 12:
                    if (zzS(t2, i)) {
                        zzard.zzq(t, j, zzard.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 13:
                    if (zzS(t2, i)) {
                        zzard.zzq(t, j, zzard.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 14:
                    if (zzS(t2, i)) {
                        zzard.zzr(t, j, zzard.zzd(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 15:
                    if (zzS(t2, i)) {
                        zzard.zzq(t, j, zzard.zzc(t2, j));
                        zzM(t, i);
                    }
                    break;
                case 16:
                    if (zzS(t2, i)) {
                        zzard.zzr(t, j, zzard.zzd(t2, j));
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
                    zzaqb.zzaa(this.zzr, t, t2, j);
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
                    if (zzV(t2, i2, i)) {
                        zzard.zzs(t, j, zzard.zzf(t2, j));
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
                    if (zzV(t2, i2, i)) {
                        zzard.zzs(t, j, zzard.zzf(t2, j));
                        zzN(t, i2, i);
                    }
                    break;
                case 68:
                    zzK(t, t2, i);
                    break;
            }
        }
        zzaqb.zzF(this.zzo, t, t2);
        if (this.zzh) {
            zzaqb.zzE(this.zzp, t, t2);
        }
    }
}
