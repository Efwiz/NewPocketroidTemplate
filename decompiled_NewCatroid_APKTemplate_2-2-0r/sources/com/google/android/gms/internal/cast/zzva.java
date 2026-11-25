package com.google.android.gms.internal.cast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzva<T> implements zzvi<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzwj.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final zzux zze;
    private final boolean zzf;
    private final int[] zzg;
    private final int zzh;
    private final zzul zzi;
    private final zzvz zzj;
    private final zztf zzk;
    private final zzvc zzl;
    private final zzus zzm;

    private zzva(int[] iArr, Object[] objArr, int i, int i2, zzux zzuxVar, int i3, boolean z, int[] iArr2, int i4, int i5, zzvc zzvcVar, zzul zzulVar, zzvz zzvzVar, zztf zztfVar, zzus zzusVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        boolean z2 = false;
        if (zztfVar != null && zztfVar.zzc(zzuxVar)) {
            z2 = true;
        }
        this.zzf = z2;
        this.zzg = iArr2;
        this.zzh = i4;
        this.zzl = zzvcVar;
        this.zzi = zzulVar;
        this.zzj = zzvzVar;
        this.zzk = zztfVar;
        this.zze = zzuxVar;
        this.zzm = zzusVar;
    }

    private static boolean zzA(Object obj, int i, zzvi zzviVar) {
        return zzviVar.zzh(zzwj.zzf(obj, i & 1048575));
    }

    private static boolean zzB(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zztp) {
            return ((zztp) obj).zzK();
        }
        return true;
    }

    private final boolean zzC(Object obj, int i, int i2) {
        return zzwj.zzc(obj, (long) (zzm(i2) & 1048575)) == i;
    }

    private static boolean zzD(Object obj, long j) {
        return ((Boolean) zzwj.zzf(obj, j)).booleanValue();
    }

    private static final void zzE(int i, Object obj, zzwq zzwqVar) throws IOException {
        if (obj instanceof String) {
            zzwqVar.zzD(i, (String) obj);
        } else {
            zzwqVar.zzd(i, (zzsu) obj);
        }
    }

    /* JADX WARN: Code duplicated, block: B:125:0x0265  */
    /* JADX WARN: Code duplicated, block: B:126:0x0268  */
    /* JADX WARN: Code duplicated, block: B:129:0x027f  */
    /* JADX WARN: Code duplicated, block: B:130:0x0282  */
    static zzva zzi(Class cls, zzuu zzuuVar, zzvc zzvcVar, zzul zzulVar, zzvz zzvzVar, zztf zztfVar, zzus zzusVar) {
        int i;
        int iCharAt;
        int iCharAt2;
        int i2;
        int[] iArr;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        char cCharAt;
        int i8;
        char cCharAt2;
        int i9;
        char cCharAt3;
        int i10;
        char cCharAt4;
        int i11;
        char cCharAt5;
        int i12;
        char cCharAt6;
        int i13;
        char cCharAt7;
        int i14;
        char cCharAt8;
        int i15;
        int i16;
        int iObjectFieldOffset;
        int i17;
        int i18;
        int i19;
        int iObjectFieldOffset2;
        Field fieldZzs;
        char cCharAt9;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        Object obj;
        Field fieldZzs2;
        int i25;
        Object obj2;
        Field fieldZzs3;
        int i26;
        char cCharAt10;
        int i27;
        char cCharAt11;
        int i28;
        char cCharAt12;
        int i29;
        char cCharAt13;
        if (!(zzuuVar instanceof zzvh)) {
            throw null;
        }
        zzvh zzvhVar = (zzvh) zzuuVar;
        String strZzd = zzvhVar.zzd();
        int length = strZzd.length();
        char c = 55296;
        if (strZzd.charAt(0) >= 55296) {
            int i30 = 1;
            while (true) {
                i = i30 + 1;
                if (strZzd.charAt(i30) < 55296) {
                    break;
                }
                i30 = i;
            }
        } else {
            i = 1;
        }
        int i31 = i + 1;
        int iCharAt3 = strZzd.charAt(i);
        if (iCharAt3 >= 55296) {
            int i32 = iCharAt3 & 8191;
            int i33 = 13;
            while (true) {
                i29 = i31 + 1;
                cCharAt13 = strZzd.charAt(i31);
                if (cCharAt13 < 55296) {
                    break;
                }
                i32 |= (cCharAt13 & 8191) << i33;
                i33 += 13;
                i31 = i29;
            }
            iCharAt3 = i32 | (cCharAt13 << i33);
            i31 = i29;
        }
        if (iCharAt3 == 0) {
            iCharAt = 0;
            iCharAt2 = 0;
            i3 = 0;
            i6 = 0;
            i2 = 0;
            i4 = 0;
            iArr = zza;
            i5 = 0;
        } else {
            int i34 = i31 + 1;
            int iCharAt4 = strZzd.charAt(i31);
            if (iCharAt4 >= 55296) {
                int i35 = iCharAt4 & 8191;
                int i36 = 13;
                while (true) {
                    i14 = i34 + 1;
                    cCharAt8 = strZzd.charAt(i34);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i35 |= (cCharAt8 & 8191) << i36;
                    i36 += 13;
                    i34 = i14;
                }
                iCharAt4 = i35 | (cCharAt8 << i36);
                i34 = i14;
            }
            int i37 = i34 + 1;
            int iCharAt5 = strZzd.charAt(i34);
            if (iCharAt5 >= 55296) {
                int i38 = iCharAt5 & 8191;
                int i39 = 13;
                while (true) {
                    i13 = i37 + 1;
                    cCharAt7 = strZzd.charAt(i37);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i38 |= (cCharAt7 & 8191) << i39;
                    i39 += 13;
                    i37 = i13;
                }
                iCharAt5 = i38 | (cCharAt7 << i39);
                i37 = i13;
            }
            int i40 = i37 + 1;
            int iCharAt6 = strZzd.charAt(i37);
            if (iCharAt6 >= 55296) {
                int i41 = iCharAt6 & 8191;
                int i42 = 13;
                while (true) {
                    i12 = i40 + 1;
                    cCharAt6 = strZzd.charAt(i40);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i41 |= (cCharAt6 & 8191) << i42;
                    i42 += 13;
                    i40 = i12;
                }
                iCharAt6 = i41 | (cCharAt6 << i42);
                i40 = i12;
            }
            int i43 = i40 + 1;
            int iCharAt7 = strZzd.charAt(i40);
            if (iCharAt7 >= 55296) {
                int i44 = iCharAt7 & 8191;
                int i45 = 13;
                while (true) {
                    i11 = i43 + 1;
                    cCharAt5 = strZzd.charAt(i43);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i44 |= (cCharAt5 & 8191) << i45;
                    i45 += 13;
                    i43 = i11;
                }
                iCharAt7 = i44 | (cCharAt5 << i45);
                i43 = i11;
            }
            int i46 = i43 + 1;
            iCharAt = strZzd.charAt(i43);
            if (iCharAt >= 55296) {
                int i47 = iCharAt & 8191;
                int i48 = 13;
                while (true) {
                    i10 = i46 + 1;
                    cCharAt4 = strZzd.charAt(i46);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i47 |= (cCharAt4 & 8191) << i48;
                    i48 += 13;
                    i46 = i10;
                }
                iCharAt = i47 | (cCharAt4 << i48);
                i46 = i10;
            }
            int i49 = i46 + 1;
            iCharAt2 = strZzd.charAt(i46);
            if (iCharAt2 >= 55296) {
                int i50 = iCharAt2 & 8191;
                int i51 = 13;
                while (true) {
                    i9 = i49 + 1;
                    cCharAt3 = strZzd.charAt(i49);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i50 |= (cCharAt3 & 8191) << i51;
                    i51 += 13;
                    i49 = i9;
                }
                iCharAt2 = i50 | (cCharAt3 << i51);
                i49 = i9;
            }
            int i52 = i49 + 1;
            int iCharAt8 = strZzd.charAt(i49);
            if (iCharAt8 >= 55296) {
                int i53 = iCharAt8 & 8191;
                int i54 = 13;
                while (true) {
                    i8 = i52 + 1;
                    cCharAt2 = strZzd.charAt(i52);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i53 |= (cCharAt2 & 8191) << i54;
                    i54 += 13;
                    i52 = i8;
                }
                iCharAt8 = i53 | (cCharAt2 << i54);
                i52 = i8;
            }
            int i55 = i52 + 1;
            int iCharAt9 = strZzd.charAt(i52);
            if (iCharAt9 >= 55296) {
                int i56 = iCharAt9 & 8191;
                int i57 = 13;
                while (true) {
                    i7 = i55 + 1;
                    cCharAt = strZzd.charAt(i55);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i56 |= (cCharAt & 8191) << i57;
                    i57 += 13;
                    i55 = i7;
                }
                iCharAt9 = i56 | (cCharAt << i57);
                i55 = i7;
            }
            i2 = iCharAt4 + iCharAt4 + iCharAt5;
            iArr = new int[iCharAt9 + iCharAt2 + iCharAt8];
            i3 = iCharAt6;
            i4 = iCharAt9;
            i5 = iCharAt4;
            i6 = iCharAt7;
            i31 = i55;
        }
        Unsafe unsafe = zzb;
        Object[] objArrZze = zzvhVar.zze();
        Class<?> cls2 = zzvhVar.zza().getClass();
        int i58 = i4 + iCharAt2;
        int i59 = iCharAt + iCharAt;
        int[] iArr2 = new int[iCharAt * 3];
        Object[] objArr = new Object[i59];
        int i60 = 0;
        int i61 = 0;
        int i62 = i4;
        int i63 = i58;
        while (i31 < length) {
            int i64 = i31 + 1;
            int iCharAt10 = strZzd.charAt(i31);
            if (iCharAt10 >= c) {
                int i65 = iCharAt10 & 8191;
                int i66 = i64;
                int i67 = 13;
                while (true) {
                    i28 = i66 + 1;
                    cCharAt12 = strZzd.charAt(i66);
                    if (cCharAt12 < c) {
                        break;
                    }
                    i65 |= (cCharAt12 & 8191) << i67;
                    i67 += 13;
                    i66 = i28;
                }
                iCharAt10 = i65 | (cCharAt12 << i67);
                i15 = i28;
            } else {
                i15 = i64;
            }
            int i68 = i15 + 1;
            int iCharAt11 = strZzd.charAt(i15);
            if (iCharAt11 >= c) {
                int i69 = iCharAt11 & 8191;
                int i70 = i68;
                int i71 = 13;
                while (true) {
                    i27 = i70 + 1;
                    cCharAt11 = strZzd.charAt(i70);
                    if (cCharAt11 < c) {
                        break;
                    }
                    i69 |= (cCharAt11 & 8191) << i71;
                    i71 += 13;
                    i70 = i27;
                }
                iCharAt11 = i69 | (cCharAt11 << i71);
                i16 = i27;
            } else {
                i16 = i68;
            }
            if ((iCharAt11 & 1024) != 0) {
                iArr[i60] = i61;
                i60++;
            }
            int i72 = iCharAt11 & 255;
            int i73 = iCharAt11 & 2048;
            int i74 = length;
            if (i72 >= 51) {
                int i75 = i16 + 1;
                int iCharAt12 = strZzd.charAt(i16);
                char c2 = 55296;
                if (iCharAt12 >= 55296) {
                    int i76 = 13;
                    int i77 = iCharAt12 & 8191;
                    int i78 = i75;
                    while (true) {
                        i26 = i78 + 1;
                        cCharAt10 = strZzd.charAt(i78);
                        if (cCharAt10 < c2) {
                            break;
                        }
                        i77 |= (cCharAt10 & 8191) << i76;
                        i76 += 13;
                        i78 = i26;
                        c2 = 55296;
                    }
                    iCharAt12 = i77 | (cCharAt10 << i76);
                    i22 = i26;
                } else {
                    i22 = i75;
                }
                int i79 = i22;
                int i80 = i72 - 51;
                if (i80 == 9 || i80 == 17) {
                    i23 = i2 + 1;
                    int i81 = i61 / 3;
                    objArr[i81 + i81 + 1] = objArrZze[i2];
                } else {
                    if (i80 == 12) {
                        if (zzvhVar.zzc() == 1 || i73 != 0) {
                            i23 = i2 + 1;
                            int i82 = i61 / 3;
                            objArr[i82 + i82 + 1] = objArrZze[i2];
                        } else {
                            i73 = 0;
                        }
                    }
                    i24 = iCharAt12 + iCharAt12;
                    obj = objArrZze[i24];
                    if (obj instanceof Field) {
                        fieldZzs2 = (Field) obj;
                    } else {
                        fieldZzs2 = zzs(cls2, (String) obj);
                        objArrZze[i24] = fieldZzs2;
                    }
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzs2);
                    i25 = i24 + 1;
                    obj2 = objArrZze[i25];
                    if (obj2 instanceof Field) {
                        fieldZzs3 = (Field) obj2;
                    } else {
                        fieldZzs3 = zzs(cls2, (String) obj2);
                        objArrZze[i25] = fieldZzs3;
                    }
                    zzvhVar = zzvhVar;
                    strZzd = strZzd;
                    i19 = i2;
                    i17 = i79;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzs3);
                    i18 = 0;
                }
                i2 = i23;
                i24 = iCharAt12 + iCharAt12;
                obj = objArrZze[i24];
                if (obj instanceof Field) {
                    fieldZzs2 = (Field) obj;
                } else {
                    fieldZzs2 = zzs(cls2, (String) obj);
                    objArrZze[i24] = fieldZzs2;
                }
                iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZzs2);
                i25 = i24 + 1;
                obj2 = objArrZze[i25];
                if (obj2 instanceof Field) {
                    fieldZzs3 = (Field) obj2;
                } else {
                    fieldZzs3 = zzs(cls2, (String) obj2);
                    objArrZze[i25] = fieldZzs3;
                }
                zzvhVar = zzvhVar;
                strZzd = strZzd;
                i19 = i2;
                i17 = i79;
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzs3);
                i18 = 0;
            } else {
                int i83 = i2 + 1;
                Field fieldZzs4 = zzs(cls2, (String) objArrZze[i2]);
                if (i72 == 9 || i72 == 17) {
                    zzvhVar = zzvhVar;
                    int i84 = i61 / 3;
                    objArr[i84 + i84 + 1] = fieldZzs4.getType();
                } else {
                    if (i72 == 27) {
                        i20 = 1;
                        i21 = i2 + 2;
                    } else if (i72 == 49) {
                        i21 = i2 + 2;
                        i20 = 1;
                    } else if (i72 == 12 || i72 == 30 || i72 == 44) {
                        zzvhVar = zzvhVar;
                        if (zzvhVar.zzc() == 1 || i73 != 0) {
                            i21 = i2 + 2;
                            int i85 = i61 / 3;
                            objArr[i85 + i85 + 1] = objArrZze[i83];
                            i83 = i21;
                        } else {
                            i73 = 0;
                        }
                    } else {
                        if (i72 == 50) {
                            int i86 = i2 + 2;
                            int i87 = i62 + 1;
                            iArr[i62] = i61;
                            int i88 = i61 / 3;
                            int i89 = i88 + i88;
                            objArr[i89] = objArrZze[i83];
                            if (i73 != 0) {
                                i83 = i2 + 3;
                                objArr[i89 + 1] = objArrZze[i86];
                                i62 = i87;
                            } else {
                                i83 = i86;
                                i62 = i87;
                                i73 = 0;
                            }
                        }
                        zzvhVar = zzvhVar;
                    }
                    int i90 = i61 / 3;
                    objArr[i90 + i90 + i20] = objArrZze[i83];
                    i83 = i21;
                }
                int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZzs4);
                iObjectFieldOffset = 1048575;
                if ((iCharAt11 & 4096) == 0 || i72 > 17) {
                    i17 = i16;
                    i18 = 0;
                } else {
                    int i91 = i16 + 1;
                    int iCharAt13 = strZzd.charAt(i16);
                    if (iCharAt13 >= 55296) {
                        int i92 = iCharAt13 & 8191;
                        int i93 = 13;
                        while (true) {
                            i17 = i91 + 1;
                            cCharAt9 = strZzd.charAt(i91);
                            if (cCharAt9 < 55296) {
                                break;
                            }
                            i92 |= (cCharAt9 & 8191) << i93;
                            i93 += 13;
                            i91 = i17;
                        }
                        iCharAt13 = i92 | (cCharAt9 << i93);
                    } else {
                        i17 = i91;
                    }
                    int i94 = i5 + i5 + (iCharAt13 / 32);
                    Object obj3 = objArrZze[i94];
                    if (obj3 instanceof Field) {
                        fieldZzs = (Field) obj3;
                    } else {
                        fieldZzs = zzs(cls2, (String) obj3);
                        objArrZze[i94] = fieldZzs;
                    }
                    i18 = iCharAt13 % 32;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZzs);
                }
                if (i72 >= 18 && i72 <= 49) {
                    iArr[i63] = iObjectFieldOffset3;
                    i63++;
                }
                i19 = i83;
                iObjectFieldOffset2 = iObjectFieldOffset3;
            }
            int i95 = i61 + 1;
            iArr2[i61] = iCharAt10;
            int i96 = i61 + 2;
            iArr2[i95] = iObjectFieldOffset2 | ((iCharAt11 & 256) != 0 ? 268435456 : 0) | ((iCharAt11 & 512) != 0 ? 536870912 : 0) | (i73 != 0 ? Integer.MIN_VALUE : 0) | (i72 << 20);
            i61 += 3;
            iArr2[i96] = (i18 << 20) | iObjectFieldOffset;
            i2 = i19;
            i31 = i17;
            length = i74;
            zzvhVar = zzvhVar;
            strZzd = strZzd;
            i6 = i6;
            i3 = i3;
            c = 55296;
        }
        zzvh zzvhVar2 = zzvhVar;
        return new zzva(iArr2, objArr, i3, i6, zzvhVar2.zza(), zzvhVar2.zzc(), false, iArr, i4, i58, zzvcVar, zzulVar, zzvzVar, zztfVar, zzusVar);
    }

    private static double zzj(Object obj, long j) {
        return ((Double) zzwj.zzf(obj, j)).doubleValue();
    }

    private static float zzk(Object obj, long j) {
        return ((Float) zzwj.zzf(obj, j)).floatValue();
    }

    private static int zzl(Object obj, long j) {
        return ((Integer) zzwj.zzf(obj, j)).intValue();
    }

    private final int zzm(int i) {
        return this.zzc[i + 2];
    }

    private static int zzn(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzo(int i) {
        return this.zzc[i + 1];
    }

    private static long zzp(Object obj, long j) {
        return ((Long) zzwj.zzf(obj, j)).longValue();
    }

    private final zzvi zzq(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzvi zzviVar = (zzvi) objArr[i3];
        if (zzviVar != null) {
            return zzviVar;
        }
        zzvi zzviVarZzb = zzvf.zza().zzb((Class) objArr[i3 + 1]);
        this.zzd[i3] = zzviVarZzb;
        return zzviVarZzb;
    }

    private final Object zzr(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static Field zzs(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private final void zzt(Object obj, Object obj2, int i) {
        if (zzy(obj2, i)) {
            int iZzo = zzo(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzo;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzvi zzviVarZzq = zzq(i);
            if (!zzy(obj, i)) {
                if (zzB(object)) {
                    Object objZzc = zzviVarZzq.zzc();
                    zzviVarZzq.zze(objZzc, object);
                    unsafe.putObject(obj, j, objZzc);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzv(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzB(object2)) {
                Object objZzc2 = zzviVarZzq.zzc();
                zzviVarZzq.zze(objZzc2, object2);
                unsafe.putObject(obj, j, objZzc2);
                object2 = objZzc2;
            }
            zzviVarZzq.zze(object2, object);
        }
    }

    private final void zzu(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzC(obj2, i2, i)) {
            int iZzo = zzo(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzo;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzvi zzviVarZzq = zzq(i);
            if (!zzC(obj, i2, i)) {
                if (zzB(object)) {
                    Object objZzc = zzviVarZzq.zzc();
                    zzviVarZzq.zze(objZzc, object);
                    unsafe.putObject(obj, j, objZzc);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzw(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzB(object2)) {
                Object objZzc2 = zzviVarZzq.zzc();
                zzviVarZzq.zze(objZzc2, object2);
                unsafe.putObject(obj, j, objZzc2);
                object2 = objZzc2;
            }
            zzviVarZzq.zze(object2, object);
        }
    }

    private final void zzv(Object obj, int i) {
        int iZzm = zzm(i);
        long j = 1048575 & iZzm;
        if (j == 1048575) {
            return;
        }
        zzwj.zzq(obj, j, (1 << (iZzm >>> 20)) | zzwj.zzc(obj, j));
    }

    private final void zzw(Object obj, int i, int i2) {
        zzwj.zzq(obj, zzm(i2) & 1048575, i);
    }

    private final boolean zzx(Object obj, Object obj2, int i) {
        return zzy(obj, i) == zzy(obj2, i);
    }

    private final boolean zzy(Object obj, int i) {
        int iZzm = zzm(i);
        long j = iZzm & 1048575;
        if (j != 1048575) {
            return (zzwj.zzc(obj, j) & (1 << (iZzm >>> 20))) != 0;
        }
        int iZzo = zzo(i);
        long j2 = iZzo & 1048575;
        switch (zzn(iZzo)) {
            case 0:
                return Double.doubleToRawLongBits(zzwj.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzwj.zzb(obj, j2)) != 0;
            case 2:
                return zzwj.zzd(obj, j2) != 0;
            case 3:
                return zzwj.zzd(obj, j2) != 0;
            case 4:
                return zzwj.zzc(obj, j2) != 0;
            case 5:
                return zzwj.zzd(obj, j2) != 0;
            case 6:
                return zzwj.zzc(obj, j2) != 0;
            case 7:
                return zzwj.zzw(obj, j2);
            case 8:
                Object objZzf = zzwj.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzsu) {
                    return !zzsu.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzwj.zzf(obj, j2) != null;
            case 10:
                return !zzsu.zzb.equals(zzwj.zzf(obj, j2));
            case 11:
                return zzwj.zzc(obj, j2) != 0;
            case 12:
                return zzwj.zzc(obj, j2) != 0;
            case 13:
                return zzwj.zzc(obj, j2) != 0;
            case 14:
                return zzwj.zzd(obj, j2) != 0;
            case 15:
                return zzwj.zzc(obj, j2) != 0;
            case 16:
                return zzwj.zzd(obj, j2) != 0;
            case 17:
                return zzwj.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzz(Object obj, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzy(obj, i);
        }
        return (i3 & i4) != 0;
    }

    /* JADX WARN: Code duplicated, block: B:137:0x038f  */
    /* JADX WARN: Code duplicated, block: B:207:0x0551  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v108, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v109, types: [com.google.android.gms.internal.cast.zzuf] */
    /* JADX WARN: Type inference failed for: r0v111, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v113, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v130 */
    /* JADX WARN: Type inference failed for: r0v178, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v248, types: [int] */
    /* JADX WARN: Type inference failed for: r0v256 */
    /* JADX WARN: Type inference failed for: r0v258 */
    /* JADX WARN: Type inference failed for: r0v259 */
    /* JADX WARN: Type inference failed for: r0v260 */
    /* JADX WARN: Type inference failed for: r0v261 */
    /* JADX WARN: Type inference failed for: r0v262 */
    /* JADX WARN: Type inference failed for: r0v263 */
    /* JADX WARN: Type inference failed for: r0v264 */
    /* JADX WARN: Type inference failed for: r0v265 */
    /* JADX WARN: Type inference failed for: r0v266 */
    /* JADX WARN: Type inference failed for: r0v267 */
    /* JADX WARN: Type inference failed for: r0v268 */
    /* JADX WARN: Type inference failed for: r0v269 */
    /* JADX WARN: Type inference failed for: r0v270 */
    /* JADX WARN: Type inference failed for: r0v271 */
    /* JADX WARN: Type inference failed for: r0v272 */
    /* JADX WARN: Type inference failed for: r0v273 */
    /* JADX WARN: Type inference failed for: r0v274 */
    /* JADX WARN: Type inference failed for: r0v275 */
    /* JADX WARN: Type inference failed for: r12v3, types: [int] */
    /* JADX WARN: Type inference failed for: r12v4, types: [int] */
    /* JADX WARN: Type inference failed for: r12v5, types: [int] */
    /* JADX WARN: Type inference failed for: r12v6, types: [int] */
    /* JADX WARN: Type inference failed for: r12v8, types: [int] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v110, types: [int] */
    /* JADX WARN: Type inference failed for: r1v113, types: [int] */
    /* JADX WARN: Type inference failed for: r1v149 */
    /* JADX WARN: Type inference failed for: r1v152 */
    /* JADX WARN: Type inference failed for: r1v153 */
    /* JADX WARN: Type inference failed for: r1v155 */
    /* JADX WARN: Type inference failed for: r1v156 */
    /* JADX WARN: Type inference failed for: r1v157 */
    /* JADX WARN: Type inference failed for: r1v70, types: [int] */
    /* JADX WARN: Type inference failed for: r1v72 */
    /* JADX WARN: Type inference failed for: r2v30, types: [int] */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v36, types: [int] */
    /* JADX WARN: Type inference failed for: r2v40, types: [int] */
    /* JADX WARN: Type inference failed for: r2v44, types: [int] */
    /* JADX WARN: Type inference failed for: r2v52 */
    /* JADX WARN: Type inference failed for: r2v53, types: [int] */
    /* JADX WARN: Type inference failed for: r2v90 */
    /* JADX WARN: Type inference failed for: r2v91 */
    /* JADX WARN: Type inference failed for: r2v92 */
    /* JADX WARN: Type inference failed for: r2v93 */
    /* JADX WARN: Type inference failed for: r2v94 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23, types: [int] */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26, types: [int] */
    /* JADX WARN: Type inference failed for: r3v31 */
    /* JADX WARN: Type inference failed for: r3v35, types: [int] */
    /* JADX WARN: Type inference failed for: r3v36 */
    /* JADX WARN: Type inference failed for: r3v42, types: [int] */
    /* JADX WARN: Type inference failed for: r3v47 */
    /* JADX WARN: Type inference failed for: r3v48 */
    /* JADX WARN: Type inference failed for: r3v49 */
    /* JADX WARN: Type inference failed for: r3v50 */
    /* JADX WARN: Type inference failed for: r3v51 */
    /* JADX WARN: Type inference failed for: r3v52 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v30, types: [int] */
    /* JADX WARN: Type inference failed for: r4v34 */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r4v37, types: [int] */
    /* JADX WARN: Type inference failed for: r4v38 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v58 */
    /* JADX WARN: Type inference failed for: r4v59 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v3, types: [int] */
    @Override // com.google.android.gms.internal.cast.zzvi
    public final int zza(Object obj) {
        int i;
        ?? r15;
        ?? r5;
        int iZzx;
        int iZzx2;
        int iZzy;
        int iZzx3;
        int iZzx4;
        int iZzx5;
        int iZzx6;
        ?? Zzg;
        int size;
        int iZzx7;
        int iZzw;
        int iZzw2;
        ?? r3;
        int iZzv;
        ?? Zzx;
        ?? Zzh;
        int iZze;
        int iZzx8;
        int iZzx9;
        ?? r4;
        ?? r6;
        ?? r1;
        Unsafe unsafe = zzb;
        boolean z = false;
        int i2 = 1048575;
        ?? r2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1048575;
        while (i3 < this.zzc.length) {
            int iZzo = zzo(i3);
            int iZzn = zzn(iZzo);
            int[] iArr = this.zzc;
            int i6 = iArr[i3];
            int i7 = iArr[i3 + 2];
            int i8 = i7 & i2;
            if (iZzn <= 17) {
                if (i8 != i5) {
                    r1 = i8 == i2 ? z : unsafe.getInt(obj, i8);
                    i5 = i8;
                }
                i = i5;
                r15 = r1;
                r5 = 1 << (i7 >>> 20);
            } else {
                r1 = r2;
                i = i5;
                r15 = r2 == true ? 1 : 0;
                r5 = z;
            }
            int i9 = iZzo & i2;
            if (iZzn >= zztk.DOUBLE_LIST_PACKED.zza()) {
                zztk.SINT64_LIST_PACKED.zza();
            }
            long j = i9;
            switch (iZzn) {
                case 0:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzx = zztc.zzx(i6 << 3);
                        Zzh = iZzx + 8;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 1:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzx2 = zztc.zzx(i6 << 3);
                        Zzh = iZzx2 + 4;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 2:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzy = zztc.zzy(unsafe.getLong(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 3:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzy = zztc.zzy(unsafe.getLong(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 4:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzy = zztc.zzu(unsafe.getInt(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 5:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzx = zztc.zzx(i6 << 3);
                        Zzh = iZzx + 8;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 6:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzx2 = zztc.zzx(i6 << 3);
                        Zzh = iZzx2 + 4;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 7:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzx4 = zztc.zzx(i6 << 3);
                        Zzh = iZzx4 + 1;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 8:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        int i10 = i6 << 3;
                        Object object = unsafe.getObject(obj, j);
                        if (object instanceof zzsu) {
                            int i11 = zztc.zzb;
                            int iZzd = ((zzsu) object).zzd();
                            iZzx5 = zztc.zzx(iZzd) + iZzd;
                            iZzx6 = zztc.zzx(i10);
                            Zzh = iZzx6 + iZzx5;
                            i4 += Zzh;
                        } else {
                            iZzy = zztc.zzw((String) object);
                            iZzx3 = zztc.zzx(i10);
                            Zzh = iZzx3 + iZzy;
                            i4 += Zzh;
                        }
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 9:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        Zzh = zzvk.zzh(i6, unsafe.getObject(obj, j), zzq(i3));
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 10:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        zzsu zzsuVar = (zzsu) unsafe.getObject(obj, j);
                        int i12 = zztc.zzb;
                        int iZzd2 = zzsuVar.zzd();
                        iZzx5 = zztc.zzx(iZzd2) + iZzd2;
                        iZzx6 = zztc.zzx(i6 << 3);
                        Zzh = iZzx6 + iZzx5;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 11:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzy = zztc.zzx(unsafe.getInt(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 12:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzy = zztc.zzu(unsafe.getInt(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 13:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzx2 = zztc.zzx(i6 << 3);
                        Zzh = iZzx2 + 4;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 14:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        iZzx = zztc.zzx(i6 << 3);
                        Zzh = iZzx + 8;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 15:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        int i13 = unsafe.getInt(obj, j);
                        iZzx3 = zztc.zzx(i6 << 3);
                        iZzy = zztc.zzx((i13 >> 31) ^ (i13 + i13));
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 16:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        long j2 = unsafe.getLong(obj, j);
                        iZzx3 = zztc.zzx(i6 << 3);
                        iZzy = zztc.zzy((j2 >> 63) ^ (j2 + j2));
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 17:
                    if (zzz(obj, i3, i, r15 == true ? 1 : 0, r5)) {
                        Zzh = zztc.zzt(i6, (zzux) unsafe.getObject(obj, j), zzq(i3));
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 18:
                    Zzh = zzvk.zzd(i6, (List) unsafe.getObject(obj, j), z);
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 19:
                    Zzh = zzvk.zzb(i6, (List) unsafe.getObject(obj, j), z);
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 20:
                    List list = (List) unsafe.getObject(obj, j);
                    int i14 = zzvk.zza;
                    if (list.size() == 0) {
                        Zzg = z;
                    } else {
                        Zzg = zzvk.zzg(list) + (list.size() * zztc.zzx(i6 << 3));
                    }
                    i4 += Zzg;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 21:
                    List list2 = (List) unsafe.getObject(obj, j);
                    int i15 = zzvk.zza;
                    size = list2.size();
                    if (size == 0) {
                        Zzh = z;
                    } else {
                        iZzx3 = zzvk.zzl(list2);
                        iZzx7 = zztc.zzx(i6 << 3);
                        iZzy = size * iZzx7;
                        Zzh = iZzx3 + iZzy;
                    }
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 22:
                    List list3 = (List) unsafe.getObject(obj, j);
                    int i16 = zzvk.zza;
                    size = list3.size();
                    if (size == 0) {
                        Zzh = z;
                    } else {
                        iZzx3 = zzvk.zzf(list3);
                        iZzx7 = zztc.zzx(i6 << 3);
                        iZzy = size * iZzx7;
                        Zzh = iZzx3 + iZzy;
                    }
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 23:
                    Zzh = zzvk.zzd(i6, (List) unsafe.getObject(obj, j), z);
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 24:
                    Zzh = zzvk.zzb(i6, (List) unsafe.getObject(obj, j), z);
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 25:
                    List list4 = (List) unsafe.getObject(obj, j);
                    int i17 = zzvk.zza;
                    int size2 = list4.size();
                    if (size2 == 0) {
                        Zzh = z;
                    } else {
                        Zzh = size2 * (zztc.zzx(i6 << 3) + 1);
                    }
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 26:
                    ?? r0 = (List) unsafe.getObject(obj, j);
                    int i18 = zzvk.zza;
                    int size3 = r0.size();
                    if (size3 == 0) {
                        Zzg = z;
                    } else {
                        boolean z2 = r0 instanceof zzuf;
                        int iZzx10 = zztc.zzx(i6 << 3) * size3;
                        if (z2) {
                            ?? r7 = (zzuf) r0;
                            for (?? r8 = z; r8 < size3; r8++) {
                                Object objZze = r7.zze(r8);
                                if (objZze instanceof zzsu) {
                                    Zzg = iZzx10;
                                    int iZzd3 = ((zzsu) objZze).zzd();
                                    iZzw2 = Zzg + zztc.zzx(iZzd3) + iZzd3;
                                } else {
                                    Zzg = iZzx10;
                                    iZzw2 = Zzg + zztc.zzw((String) objZze);
                                }
                                Zzg = iZzw2;
                            }
                            Zzg = iZzx10;
                        } else {
                            for (?? r9 = z; r9 < size3; r9++) {
                                Object obj2 = r0.get(r9);
                                if (obj2 instanceof zzsu) {
                                    Zzg = iZzx10;
                                    int iZzd4 = ((zzsu) obj2).zzd();
                                    iZzw = Zzg + zztc.zzx(iZzd4) + iZzd4;
                                } else {
                                    Zzg = iZzx10;
                                    iZzw = Zzg + zztc.zzw((String) obj2);
                                }
                                Zzg = iZzw;
                            }
                            Zzg = iZzx10;
                        }
                    }
                    i4 += Zzg;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 27:
                    ?? r10 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq = zzq(i3);
                    int i19 = zzvk.zza;
                    int size4 = r10.size();
                    if (size4 == 0) {
                        r3 = z;
                    } else {
                        int iZzx11 = zztc.zzx(i6 << 3) * size4;
                        for (?? r11 = z; r11 < size4; r11++) {
                            Object obj3 = r10.get(r11);
                            if (obj3 instanceof zzud) {
                                r3 = iZzx11;
                                int iZza = ((zzud) obj3).zza();
                                iZzv = (r3 == true ? 1 : 0) + zztc.zzx(iZza) + iZza;
                            } else {
                                r3 = iZzx11;
                                iZzv = (r3 == true ? 1 : 0) + zztc.zzv((zzux) obj3, zzviVarZzq);
                            }
                            r3 = iZzv;
                        }
                        r3 = iZzx11;
                    }
                    i4 += r3;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 28:
                    ?? r12 = (List) unsafe.getObject(obj, j);
                    int i20 = zzvk.zza;
                    int size5 = r12.size();
                    if (size5 == 0) {
                        Zzx = z;
                    } else {
                        Zzx = size5 * zztc.zzx(i6 << 3);
                        for (?? r13 = z; r13 < r12.size(); r13++) {
                            int iZzd5 = ((zzsu) r12.get(r13)).zzd();
                            Zzx += zztc.zzx(iZzd5) + iZzd5;
                        }
                    }
                    i4 += Zzx;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 29:
                    List list5 = (List) unsafe.getObject(obj, j);
                    int i21 = zzvk.zza;
                    size = list5.size();
                    if (size == 0) {
                        Zzh = z;
                    } else {
                        iZzx3 = zzvk.zzk(list5);
                        iZzx7 = zztc.zzx(i6 << 3);
                        iZzy = size * iZzx7;
                        Zzh = iZzx3 + iZzy;
                    }
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 30:
                    List list6 = (List) unsafe.getObject(obj, j);
                    int i22 = zzvk.zza;
                    size = list6.size();
                    if (size == 0) {
                        Zzh = z;
                    } else {
                        iZzx3 = zzvk.zza(list6);
                        iZzx7 = zztc.zzx(i6 << 3);
                        iZzy = size * iZzx7;
                        Zzh = iZzx3 + iZzy;
                    }
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 31:
                    Zzh = zzvk.zzb(i6, (List) unsafe.getObject(obj, j), z);
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 32:
                    Zzh = zzvk.zzd(i6, (List) unsafe.getObject(obj, j), z);
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 33:
                    List list7 = (List) unsafe.getObject(obj, j);
                    int i23 = zzvk.zza;
                    size = list7.size();
                    if (size == 0) {
                        Zzh = z;
                    } else {
                        iZzx3 = zzvk.zzi(list7);
                        iZzx7 = zztc.zzx(i6 << 3);
                        iZzy = size * iZzx7;
                        Zzh = iZzx3 + iZzy;
                    }
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 34:
                    List list8 = (List) unsafe.getObject(obj, j);
                    int i24 = zzvk.zza;
                    size = list8.size();
                    if (size == 0) {
                        Zzh = z;
                    } else {
                        iZzx3 = zzvk.zzj(list8);
                        iZzx7 = zztc.zzx(i6 << 3);
                        iZzy = size * iZzx7;
                        Zzh = iZzx3 + iZzy;
                    }
                    i4 += Zzh;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 35:
                    iZze = zzvk.zze((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 36:
                    iZze = zzvk.zzc((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 37:
                    iZze = zzvk.zzg((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 38:
                    iZze = zzvk.zzl((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 39:
                    iZze = zzvk.zzf((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 40:
                    iZze = zzvk.zze((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 41:
                    iZze = zzvk.zzc((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 42:
                    List list9 = (List) unsafe.getObject(obj, j);
                    int i25 = zzvk.zza;
                    iZze = list9.size();
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 43:
                    iZze = zzvk.zzk((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 44:
                    iZze = zzvk.zza((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 45:
                    iZze = zzvk.zzc((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 46:
                    iZze = zzvk.zze((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 47:
                    iZze = zzvk.zzi((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 48:
                    iZze = zzvk.zzj((List) unsafe.getObject(obj, j));
                    if (iZze > 0) {
                        iZzx8 = zztc.zzx(iZze);
                        iZzx9 = zztc.zzx(i6 << 3);
                        Zzx = iZzx9 + iZzx8 + iZze;
                        i4 += Zzx;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 49:
                    ?? r14 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq2 = zzq(i3);
                    int i26 = zzvk.zza;
                    int size6 = r14.size();
                    if (size6 == 0) {
                        r4 = z;
                    } else {
                        boolean z3 = z;
                        r4 = z3;
                        while (r6 < size6) {
                            r6 = z3;
                            int iZzt = zztc.zzt(i6, (zzux) r14.get(r6), zzviVarZzq2);
                            r6++;
                            r4 = (r4 == true ? 1 : 0) + iZzt;
                        }
                        r6 = z3;
                    }
                    i4 += r4;
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 50:
                    zzur zzurVar = (zzur) unsafe.getObject(obj, j);
                    if (zzurVar.isEmpty()) {
                        continue;
                    } else {
                        Iterator it = zzurVar.entrySet().iterator();
                        if (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            entry.getKey();
                            entry.getValue();
                            throw null;
                        }
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                case 51:
                    if (zzC(obj, i6, i3)) {
                        iZzx = zztc.zzx(i6 << 3);
                        Zzh = iZzx + 8;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 52:
                    if (zzC(obj, i6, i3)) {
                        iZzx2 = zztc.zzx(i6 << 3);
                        Zzh = iZzx2 + 4;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 53:
                    if (zzC(obj, i6, i3)) {
                        iZzy = zztc.zzy(zzp(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 54:
                    if (zzC(obj, i6, i3)) {
                        iZzy = zztc.zzy(zzp(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 55:
                    if (zzC(obj, i6, i3)) {
                        iZzy = zztc.zzu(zzl(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 56:
                    if (zzC(obj, i6, i3)) {
                        iZzx = zztc.zzx(i6 << 3);
                        Zzh = iZzx + 8;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 57:
                    if (zzC(obj, i6, i3)) {
                        iZzx2 = zztc.zzx(i6 << 3);
                        Zzh = iZzx2 + 4;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 58:
                    if (zzC(obj, i6, i3)) {
                        iZzx4 = zztc.zzx(i6 << 3);
                        Zzh = iZzx4 + 1;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 59:
                    if (zzC(obj, i6, i3)) {
                        int i27 = i6 << 3;
                        Object object2 = unsafe.getObject(obj, j);
                        if (object2 instanceof zzsu) {
                            int i28 = zztc.zzb;
                            int iZzd6 = ((zzsu) object2).zzd();
                            iZzx5 = zztc.zzx(iZzd6) + iZzd6;
                            iZzx6 = zztc.zzx(i27);
                            Zzh = iZzx6 + iZzx5;
                            i4 += Zzh;
                        } else {
                            iZzy = zztc.zzw((String) object2);
                            iZzx3 = zztc.zzx(i27);
                            Zzh = iZzx3 + iZzy;
                            i4 += Zzh;
                        }
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 60:
                    if (zzC(obj, i6, i3)) {
                        Zzh = zzvk.zzh(i6, unsafe.getObject(obj, j), zzq(i3));
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 61:
                    if (zzC(obj, i6, i3)) {
                        zzsu zzsuVar2 = (zzsu) unsafe.getObject(obj, j);
                        int i29 = zztc.zzb;
                        int iZzd7 = zzsuVar2.zzd();
                        iZzx5 = zztc.zzx(iZzd7) + iZzd7;
                        iZzx6 = zztc.zzx(i6 << 3);
                        Zzh = iZzx6 + iZzx5;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 62:
                    if (zzC(obj, i6, i3)) {
                        iZzy = zztc.zzx(zzl(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 63:
                    if (zzC(obj, i6, i3)) {
                        iZzy = zztc.zzu(zzl(obj, j));
                        iZzx3 = zztc.zzx(i6 << 3);
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 64:
                    if (zzC(obj, i6, i3)) {
                        iZzx2 = zztc.zzx(i6 << 3);
                        Zzh = iZzx2 + 4;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 65:
                    if (zzC(obj, i6, i3)) {
                        iZzx = zztc.zzx(i6 << 3);
                        Zzh = iZzx + 8;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 66:
                    if (zzC(obj, i6, i3)) {
                        int iZzl = zzl(obj, j);
                        iZzx3 = zztc.zzx(i6 << 3);
                        iZzy = zztc.zzx((iZzl >> 31) ^ (iZzl + iZzl));
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 67:
                    if (zzC(obj, i6, i3)) {
                        long jZzp = zzp(obj, j);
                        iZzx3 = zztc.zzx(i6 << 3);
                        iZzy = zztc.zzy((jZzp >> 63) ^ (jZzp + jZzp));
                        Zzh = iZzx3 + iZzy;
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                case 68:
                    if (zzC(obj, i6, i3)) {
                        Zzh = zztc.zzt(i6, (zzux) unsafe.getObject(obj, j), zzq(i3));
                        i4 += Zzh;
                    }
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
                default:
                    i3 += 3;
                    i5 = i;
                    r2 = r15;
                    z = false;
                    i2 = 1048575;
                    break;
            }
        }
        zzvz zzvzVar = this.zzj;
        int iZza2 = i4 + zzvzVar.zza(zzvzVar.zzc(obj));
        if (!this.zzf) {
            return iZza2;
        }
        this.zzk.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final int zzb(Object obj) {
        int i;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int i2;
        int i3 = 0;
        for (int i4 = 0; i4 < this.zzc.length; i4 += 3) {
            int iZzo = zzo(i4);
            int[] iArr = this.zzc;
            int i5 = 1048575 & iZzo;
            int iZzn = zzn(iZzo);
            int i6 = iArr[i4];
            long j = i5;
            int iHashCode = 37;
            switch (iZzn) {
                case 0:
                    i = i3 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzwj.zza(obj, j));
                    byte[] bArr = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 1:
                    i = i3 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzwj.zzb(obj, j));
                    i3 = i + iFloatToIntBits;
                    break;
                case 2:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr2 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 3:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr3 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 4:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 5:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr4 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 6:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 7:
                    i = i3 * 53;
                    iFloatToIntBits = zzty.zza(zzwj.zzw(obj, j));
                    i3 = i + iFloatToIntBits;
                    break;
                case 8:
                    i = i3 * 53;
                    iFloatToIntBits = ((String) zzwj.zzf(obj, j)).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 9:
                    i2 = i3 * 53;
                    Object objZzf = zzwj.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i3 = i2 + iHashCode;
                    break;
                case 10:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 11:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 12:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 13:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 14:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr5 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 15:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 16:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr6 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 17:
                    i2 = i3 * 53;
                    Object objZzf2 = zzwj.zzf(obj, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i3 = i2 + iHashCode;
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
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 50:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 51:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzj(obj, j));
                        byte[] bArr7 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 52:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzk(obj, j));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 53:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr8 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 54:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr9 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 55:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 56:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr10 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 57:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 58:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzty.zza(zzD(obj, j));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 59:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = ((String) zzwj.zzf(obj, j)).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 60:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 61:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 62:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 63:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 64:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 65:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr11 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 66:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 67:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr12 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 68:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
            }
        }
        int iHashCode2 = (i3 * 53) + this.zzj.zzc(obj).hashCode();
        if (!this.zzf) {
            return iHashCode2;
        }
        this.zzk.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final Object zzc() {
        return ((zztp) this.zze).zzx();
    }

    /* JADX WARN: Code duplicated, block: B:26:0x006d  */
    /* JADX WARN: Code duplicated, block: B:28:0x0073  */
    /* JADX WARN: Code duplicated, block: B:41:0x0080 A[SYNTHETIC] */
    @Override // com.google.android.gms.internal.cast.zzvi
    public final void zzd(Object obj) {
        if (zzB(obj)) {
            if (obj instanceof zztp) {
                zztp zztpVar = (zztp) obj;
                zztpVar.zzI(Integer.MAX_VALUE);
                zztpVar.zza = 0;
                zztpVar.zzG();
            }
            int[] iArr = this.zzc;
            for (int i = 0; i < iArr.length; i += 3) {
                int iZzo = zzo(i);
                int i2 = 1048575 & iZzo;
                int iZzn = zzn(iZzo);
                long j = i2;
                if (iZzn != 9) {
                    if (iZzn != 60 && iZzn != 68) {
                        switch (iZzn) {
                            case 17:
                                if (zzy(obj, i)) {
                                    zzq(i).zzd(zzb.getObject(obj, j));
                                }
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
                                this.zzi.zza(obj, j);
                                break;
                            case 50:
                                Unsafe unsafe = zzb;
                                Object object = unsafe.getObject(obj, j);
                                if (object != null) {
                                    ((zzur) object).zzb();
                                    unsafe.putObject(obj, j, object);
                                }
                                break;
                        }
                    } else if (zzC(obj, this.zzc[i], i)) {
                        zzq(i).zzd(zzb.getObject(obj, j));
                    }
                } else if (zzy(obj, i)) {
                    zzq(i).zzd(zzb.getObject(obj, j));
                }
            }
            this.zzj.zze(obj);
            if (this.zzf) {
                this.zzk.zzb(obj);
            }
        }
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final void zze(Object obj, Object obj2) {
        if (!zzB(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(String.valueOf(obj))));
        }
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzo = zzo(i);
            int i2 = 1048575 & iZzo;
            int[] iArr = this.zzc;
            int iZzn = zzn(iZzo);
            int i3 = iArr[i];
            long j = i2;
            switch (iZzn) {
                case 0:
                    if (zzy(obj2, i)) {
                        zzwj.zzo(obj, j, zzwj.zza(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 1:
                    if (zzy(obj2, i)) {
                        zzwj.zzp(obj, j, zzwj.zzb(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 2:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 3:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 4:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 5:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 6:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 7:
                    if (zzy(obj2, i)) {
                        zzwj.zzm(obj, j, zzwj.zzw(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 8:
                    if (zzy(obj2, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 9:
                    zzt(obj, obj2, i);
                    break;
                case 10:
                    if (zzy(obj2, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 11:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 12:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 13:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 14:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 15:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 16:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 17:
                    zzt(obj, obj2, i);
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
                    this.zzi.zzb(obj, obj2, j);
                    break;
                case 50:
                    int i4 = zzvk.zza;
                    zzur zzurVarZza = (zzur) zzwj.zzf(obj, j);
                    zzur zzurVar = (zzur) zzwj.zzf(obj2, j);
                    if (!zzurVar.isEmpty()) {
                        if (!zzurVarZza.zzd()) {
                            zzurVarZza = zzurVarZza.zza();
                        }
                        zzurVarZza.zzc(zzurVar);
                    }
                    zzwj.zzs(obj, j, zzurVarZza);
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
                    if (zzC(obj2, i3, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzw(obj, i3, i);
                    }
                    break;
                case 60:
                    zzu(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzC(obj2, i3, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzw(obj, i3, i);
                    }
                    break;
                case 68:
                    zzu(obj, obj2, i);
                    break;
            }
        }
        zzvk.zzo(this.zzj, obj, obj2);
        if (this.zzf) {
            this.zzk.zza(obj2);
            throw null;
        }
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final void zzf(Object obj, zzwq zzwqVar) throws IOException {
        int i;
        int i2;
        int i3;
        if (this.zzf) {
            this.zzk.zza(obj);
            throw null;
        }
        int[] iArr = this.zzc;
        Unsafe unsafe = zzb;
        int i4 = 1048575;
        int i5 = 1048575;
        int i6 = 0;
        int i7 = 0;
        while (i7 < iArr.length) {
            int iZzo = zzo(i7);
            int[] iArr2 = this.zzc;
            int iZzn = zzn(iZzo);
            int i8 = iArr2[i7];
            if (iZzn <= 17) {
                int i9 = iArr2[i7 + 2];
                int i10 = i9 & i4;
                if (i10 != i5) {
                    i6 = i10 == i4 ? 0 : unsafe.getInt(obj, i10);
                    i5 = i10;
                }
                i = i5;
                i2 = i6;
                i3 = 1 << (i9 >>> 20);
            } else {
                i = i5;
                i2 = i6;
                i3 = 0;
            }
            long j = iZzo & i4;
            switch (iZzn) {
                case 0:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzf(i8, zzwj.zza(obj, j));
                    }
                    break;
                case 1:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzn(i8, zzwj.zzb(obj, j));
                    }
                    break;
                case 2:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzs(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 3:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzH(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 4:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzq(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 5:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzl(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 6:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzj(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 7:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzb(i8, zzwj.zzw(obj, j));
                    }
                    break;
                case 8:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzE(i8, unsafe.getObject(obj, j), zzwqVar);
                    }
                    break;
                case 9:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzu(i8, unsafe.getObject(obj, j), zzq(i7));
                    }
                    break;
                case 10:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzd(i8, (zzsu) unsafe.getObject(obj, j));
                    }
                    break;
                case 11:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzF(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 12:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzh(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 13:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzv(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 14:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzx(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 15:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzz(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 16:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzB(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 17:
                    if (zzz(obj, i7, i, i2, i3)) {
                        zzwqVar.zzp(i8, unsafe.getObject(obj, j), zzq(i7));
                    }
                    break;
                case 18:
                    zzvk.zzr(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 19:
                    zzvk.zzv(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 20:
                    zzvk.zzx(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 21:
                    zzvk.zzD(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 22:
                    zzvk.zzw(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 23:
                    zzvk.zzu(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 24:
                    zzvk.zzt(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 25:
                    zzvk.zzq(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 26:
                    int i11 = this.zzc[i7];
                    List list = (List) unsafe.getObject(obj, j);
                    int i12 = zzvk.zza;
                    if (list != null && !list.isEmpty()) {
                        zzwqVar.zzE(i11, list);
                    }
                    break;
                case 27:
                    int i13 = this.zzc[i7];
                    List list2 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq = zzq(i7);
                    int i14 = zzvk.zza;
                    if (list2 != null && !list2.isEmpty()) {
                        for (int i15 = 0; i15 < list2.size(); i15++) {
                            ((zztd) zzwqVar).zzu(i13, list2.get(i15), zzviVarZzq);
                        }
                    }
                    break;
                case 28:
                    int i16 = this.zzc[i7];
                    List list3 = (List) unsafe.getObject(obj, j);
                    int i17 = zzvk.zza;
                    if (list3 != null && !list3.isEmpty()) {
                        zzwqVar.zze(i16, list3);
                    }
                    break;
                case 29:
                    zzvk.zzC(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 30:
                    zzvk.zzs(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 31:
                    zzvk.zzy(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 32:
                    zzvk.zzz(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 33:
                    zzvk.zzA(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 34:
                    zzvk.zzB(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    break;
                case 35:
                    zzvk.zzr(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 36:
                    zzvk.zzv(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 37:
                    zzvk.zzx(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 38:
                    zzvk.zzD(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 39:
                    zzvk.zzw(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 40:
                    zzvk.zzu(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 41:
                    zzvk.zzt(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 42:
                    zzvk.zzq(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 43:
                    zzvk.zzC(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 44:
                    zzvk.zzs(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 45:
                    zzvk.zzy(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 46:
                    zzvk.zzz(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 47:
                    zzvk.zzA(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 48:
                    zzvk.zzB(this.zzc[i7], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    break;
                case 49:
                    int i18 = this.zzc[i7];
                    List list4 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq2 = zzq(i7);
                    int i19 = zzvk.zza;
                    if (list4 != null && !list4.isEmpty()) {
                        for (int i20 = 0; i20 < list4.size(); i20++) {
                            ((zztd) zzwqVar).zzp(i18, list4.get(i20), zzviVarZzq2);
                        }
                    }
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        throw null;
                    }
                    break;
                    break;
                case 51:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzf(i8, zzj(obj, j));
                    }
                    break;
                case 52:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzn(i8, zzk(obj, j));
                    }
                    break;
                case 53:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzs(i8, zzp(obj, j));
                    }
                    break;
                case 54:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzH(i8, zzp(obj, j));
                    }
                    break;
                case 55:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzq(i8, zzl(obj, j));
                    }
                    break;
                case 56:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzl(i8, zzp(obj, j));
                    }
                    break;
                case 57:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzj(i8, zzl(obj, j));
                    }
                    break;
                case 58:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzb(i8, zzD(obj, j));
                    }
                    break;
                case 59:
                    if (zzC(obj, i8, i7)) {
                        zzE(i8, unsafe.getObject(obj, j), zzwqVar);
                    }
                    break;
                case 60:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzu(i8, unsafe.getObject(obj, j), zzq(i7));
                    }
                    break;
                case 61:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzd(i8, (zzsu) unsafe.getObject(obj, j));
                    }
                    break;
                case 62:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzF(i8, zzl(obj, j));
                    }
                    break;
                case 63:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzh(i8, zzl(obj, j));
                    }
                    break;
                case 64:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzv(i8, zzl(obj, j));
                    }
                    break;
                case 65:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzx(i8, zzp(obj, j));
                    }
                    break;
                case 66:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzz(i8, zzl(obj, j));
                    }
                    break;
                case 67:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzB(i8, zzp(obj, j));
                    }
                    break;
                case 68:
                    if (zzC(obj, i8, i7)) {
                        zzwqVar.zzp(i8, unsafe.getObject(obj, j), zzq(i7));
                    }
                    break;
            }
            i7 += 3;
            i5 = i;
            i6 = i2;
            i4 = 1048575;
        }
        zzvz zzvzVar = this.zzj;
        zzvzVar.zzg(zzvzVar.zzc(obj), zzwqVar);
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final boolean zzg(Object obj, Object obj2) {
        boolean zZzE;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzo = zzo(i);
            long j = iZzo & 1048575;
            switch (zzn(iZzo)) {
                case 0:
                    if (!zzx(obj, obj2, i) || Double.doubleToLongBits(zzwj.zza(obj, j)) != Double.doubleToLongBits(zzwj.zza(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzx(obj, obj2, i) || Float.floatToIntBits(zzwj.zzb(obj, j)) != Float.floatToIntBits(zzwj.zzb(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzx(obj, obj2, i) || zzwj.zzw(obj, j) != zzwj.zzw(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
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
                    zZzE = zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j));
                    break;
                case 50:
                    zZzE = zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j));
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
                    long jZzm = zzm(i) & 1048575;
                    if (zzwj.zzc(obj, jZzm) != zzwj.zzc(obj2, jZzm) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    continue;
                    break;
            }
            if (!zZzE) {
                return false;
            }
        }
        if (!this.zzj.zzc(obj).equals(this.zzj.zzc(obj2))) {
            return false;
        }
        if (!this.zzf) {
            return true;
        }
        this.zzk.zza(obj);
        this.zzk.zza(obj2);
        throw null;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x009e  */
    /* JADX WARN: Code duplicated, block: B:44:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:50:0x00c3 A[LOOP:1: B:45:0x00b2->B:50:0x00c3, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:66:0x00c2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x00e1 A[SYNTHETIC] */
    @Override // com.google.android.gms.internal.cast.zzvi
    public final boolean zzh(Object obj) {
        int i;
        int i2;
        List list;
        zzvi zzviVarZzq;
        int i3;
        int i4 = 0;
        int i5 = 0;
        int i6 = 1048575;
        while (i5 < this.zzh) {
            int[] iArr = this.zzg;
            int[] iArr2 = this.zzc;
            int i7 = iArr[i5];
            int i8 = iArr2[i7];
            int iZzo = zzo(i7);
            int i9 = this.zzc[i7 + 2];
            int i10 = i9 & 1048575;
            int i11 = 1 << (i9 >>> 20);
            if (i10 != i6) {
                if (i10 != 1048575) {
                    i4 = zzb.getInt(obj, i10);
                }
                i2 = i4;
                i = i10;
            } else {
                i = i6;
                i2 = i4;
            }
            if ((268435456 & iZzo) != 0 && !zzz(obj, i7, i, i2, i11)) {
                return false;
            }
            int iZzn = zzn(iZzo);
            if (iZzn == 9 || iZzn == 17) {
                if (zzz(obj, i7, i, i2, i11) && !zzA(obj, iZzo, zzq(i7))) {
                    return false;
                }
            } else if (iZzn == 27) {
                list = (List) zzwj.zzf(obj, iZzo & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    zzviVarZzq = zzq(i7);
                    for (i3 = 0; i3 < list.size(); i3++) {
                        if (!zzviVarZzq.zzh(list.get(i3))) {
                            return false;
                        }
                    }
                }
            } else if (iZzn == 60 || iZzn == 68) {
                if (zzC(obj, i8, i7) && !zzA(obj, iZzo, zzq(i7))) {
                    return false;
                }
            } else if (iZzn == 49) {
                list = (List) zzwj.zzf(obj, iZzo & 1048575);
                if (list.isEmpty()) {
                    zzviVarZzq = zzq(i7);
                    while (i3 < list.size()) {
                        if (!zzviVarZzq.zzh(list.get(i3))) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            } else if (iZzn == 50 && !((zzur) zzwj.zzf(obj, iZzo & 1048575)).isEmpty()) {
                throw null;
            }
            i5++;
            i6 = i;
            i4 = i2;
        }
        if (!this.zzf) {
            return true;
        }
        this.zzk.zza(obj);
        throw null;
    }
}
