package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhp<T> implements zzib<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zziz.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzhl zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzhu zzo;
    private final zzgv zzp;
    private final zzit<?, ?> zzq;
    private final zzfu<?> zzr;
    private final zzhi zzs;

    private zzhp(int[] iArr, Object[] objArr, int i, int i2, zzhl zzhlVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzhu zzhuVar, zzgv zzgvVar, zzit<?, ?> zzitVar, zzfu<?> zzfuVar, zzhi zzhiVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzhlVar instanceof zzgd;
        this.zzj = z;
        this.zzh = zzfuVar != null && zzfuVar.zza(zzhlVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzhuVar;
        this.zzp = zzgvVar;
        this.zzq = zzitVar;
        this.zzr = zzfuVar;
        this.zzg = zzhlVar;
        this.zzs = zzhiVar;
    }

    /* JADX WARN: Code duplicated, block: B:163:0x033a  */
    /* JADX WARN: Code duplicated, block: B:178:0x0388  */
    /* JADX WARN: Code duplicated, block: B:181:0x0395  */
    static <T> zzhp<T> zza(Class<T> cls, zzhj zzhjVar, zzhu zzhuVar, zzgv zzgvVar, zzit<?, ?> zzitVar, zzfu<?> zzfuVar, zzhi zzhiVar) {
        int i;
        int iCharAt;
        int iCharAt2;
        int iCharAt3;
        int iCharAt4;
        int iCharAt5;
        int i2;
        int[] iArr;
        int i3;
        char cCharAt;
        int i4;
        char cCharAt2;
        int i5;
        char cCharAt3;
        int i6;
        char cCharAt4;
        int i7;
        char cCharAt5;
        int i8;
        char cCharAt6;
        int i9;
        char cCharAt7;
        int i10;
        char cCharAt8;
        int i11;
        int i12;
        int i13;
        int i14;
        zzic zzicVar;
        int i15;
        int iObjectFieldOffset;
        int iObjectFieldOffset2;
        int i16;
        int i17;
        Field fieldZza;
        char cCharAt9;
        int i18;
        int i19;
        Field fieldZza2;
        Field fieldZza3;
        int i20;
        char cCharAt10;
        int i21;
        char cCharAt11;
        int i22;
        char cCharAt12;
        int i23;
        char cCharAt13;
        if (zzhjVar instanceof zzic) {
            zzic zzicVar2 = (zzic) zzhjVar;
            int i24 = 0;
            boolean z = zzicVar2.zza() == zzhx.zzb;
            String strZzd = zzicVar2.zzd();
            int length = strZzd.length();
            if (strZzd.charAt(0) >= 55296) {
                int i25 = 1;
                while (true) {
                    i = i25 + 1;
                    if (strZzd.charAt(i25) < 55296) {
                        break;
                    }
                    i25 = i;
                }
            } else {
                i = 1;
            }
            int i26 = i + 1;
            int iCharAt6 = strZzd.charAt(i);
            if (iCharAt6 >= 55296) {
                int i27 = iCharAt6 & 8191;
                int i28 = 13;
                while (true) {
                    i23 = i26 + 1;
                    cCharAt13 = strZzd.charAt(i26);
                    if (cCharAt13 < 55296) {
                        break;
                    }
                    i27 |= (cCharAt13 & 8191) << i28;
                    i28 += 13;
                    i26 = i23;
                }
                iCharAt6 = i27 | (cCharAt13 << i28);
                i26 = i23;
            }
            if (iCharAt6 == 0) {
                iCharAt = 0;
                iCharAt2 = 0;
                iCharAt3 = 0;
                iCharAt4 = 0;
                iCharAt5 = 0;
                iArr = zza;
                i2 = 0;
            } else {
                int i29 = i26 + 1;
                int iCharAt7 = strZzd.charAt(i26);
                if (iCharAt7 >= 55296) {
                    int i30 = iCharAt7 & 8191;
                    int i31 = 13;
                    while (true) {
                        i10 = i29 + 1;
                        cCharAt8 = strZzd.charAt(i29);
                        if (cCharAt8 < 55296) {
                            break;
                        }
                        i30 |= (cCharAt8 & 8191) << i31;
                        i31 += 13;
                        i29 = i10;
                    }
                    iCharAt7 = i30 | (cCharAt8 << i31);
                    i29 = i10;
                }
                int i32 = i29 + 1;
                int iCharAt8 = strZzd.charAt(i29);
                if (iCharAt8 >= 55296) {
                    int i33 = iCharAt8 & 8191;
                    int i34 = 13;
                    while (true) {
                        i9 = i32 + 1;
                        cCharAt7 = strZzd.charAt(i32);
                        if (cCharAt7 < 55296) {
                            break;
                        }
                        i33 |= (cCharAt7 & 8191) << i34;
                        i34 += 13;
                        i32 = i9;
                    }
                    iCharAt8 = i33 | (cCharAt7 << i34);
                    i32 = i9;
                }
                int i35 = i32 + 1;
                iCharAt = strZzd.charAt(i32);
                if (iCharAt >= 55296) {
                    int i36 = iCharAt & 8191;
                    int i37 = 13;
                    while (true) {
                        i8 = i35 + 1;
                        cCharAt6 = strZzd.charAt(i35);
                        if (cCharAt6 < 55296) {
                            break;
                        }
                        i36 |= (cCharAt6 & 8191) << i37;
                        i37 += 13;
                        i35 = i8;
                    }
                    iCharAt = i36 | (cCharAt6 << i37);
                    i35 = i8;
                }
                int i38 = i35 + 1;
                iCharAt2 = strZzd.charAt(i35);
                if (iCharAt2 >= 55296) {
                    int i39 = iCharAt2 & 8191;
                    int i40 = 13;
                    while (true) {
                        i7 = i38 + 1;
                        cCharAt5 = strZzd.charAt(i38);
                        if (cCharAt5 < 55296) {
                            break;
                        }
                        i39 |= (cCharAt5 & 8191) << i40;
                        i40 += 13;
                        i38 = i7;
                    }
                    iCharAt2 = i39 | (cCharAt5 << i40);
                    i38 = i7;
                }
                int i41 = i38 + 1;
                iCharAt3 = strZzd.charAt(i38);
                if (iCharAt3 >= 55296) {
                    int i42 = iCharAt3 & 8191;
                    int i43 = 13;
                    while (true) {
                        i6 = i41 + 1;
                        cCharAt4 = strZzd.charAt(i41);
                        if (cCharAt4 < 55296) {
                            break;
                        }
                        i42 |= (cCharAt4 & 8191) << i43;
                        i43 += 13;
                        i41 = i6;
                    }
                    iCharAt3 = i42 | (cCharAt4 << i43);
                    i41 = i6;
                }
                int i44 = i41 + 1;
                iCharAt4 = strZzd.charAt(i41);
                if (iCharAt4 >= 55296) {
                    int i45 = iCharAt4 & 8191;
                    int i46 = 13;
                    while (true) {
                        i5 = i44 + 1;
                        cCharAt3 = strZzd.charAt(i44);
                        if (cCharAt3 < 55296) {
                            break;
                        }
                        i45 |= (cCharAt3 & 8191) << i46;
                        i46 += 13;
                        i44 = i5;
                    }
                    iCharAt4 = i45 | (cCharAt3 << i46);
                    i44 = i5;
                }
                int i47 = i44 + 1;
                int iCharAt9 = strZzd.charAt(i44);
                if (iCharAt9 >= 55296) {
                    int i48 = iCharAt9 & 8191;
                    int i49 = 13;
                    while (true) {
                        i4 = i47 + 1;
                        cCharAt2 = strZzd.charAt(i47);
                        if (cCharAt2 < 55296) {
                            break;
                        }
                        i48 |= (cCharAt2 & 8191) << i49;
                        i49 += 13;
                        i47 = i4;
                    }
                    iCharAt9 = i48 | (cCharAt2 << i49);
                    i47 = i4;
                }
                int i50 = i47 + 1;
                iCharAt5 = strZzd.charAt(i47);
                if (iCharAt5 >= 55296) {
                    int i51 = iCharAt5 & 8191;
                    int i52 = i50;
                    int i53 = 13;
                    while (true) {
                        i3 = i52 + 1;
                        cCharAt = strZzd.charAt(i52);
                        if (cCharAt < 55296) {
                            break;
                        }
                        i51 |= (cCharAt & 8191) << i53;
                        i53 += 13;
                        i52 = i3;
                    }
                    iCharAt5 = i51 | (cCharAt << i53);
                    i50 = i3;
                }
                i2 = (iCharAt7 << 1) + iCharAt8;
                iArr = new int[iCharAt5 + iCharAt4 + iCharAt9];
                i24 = iCharAt7;
                i26 = i50;
            }
            Unsafe unsafe = zzb;
            Object[] objArrZze = zzicVar2.zze();
            Class<?> cls2 = zzicVar2.zzc().getClass();
            int i54 = i26;
            int[] iArr2 = new int[iCharAt3 * 3];
            Object[] objArr = new Object[iCharAt3 << 1];
            int i55 = iCharAt5 + iCharAt4;
            int i56 = i2;
            int i57 = iCharAt5;
            int i58 = i54;
            int i59 = i55;
            int i60 = 0;
            int i61 = 0;
            while (i58 < length) {
                int i62 = i58 + 1;
                int iCharAt10 = strZzd.charAt(i58);
                if (iCharAt10 >= 55296) {
                    int i63 = iCharAt10 & 8191;
                    int i64 = i62;
                    int i65 = 13;
                    while (true) {
                        i22 = i64 + 1;
                        cCharAt12 = strZzd.charAt(i64);
                        i11 = length;
                        if (cCharAt12 < 55296) {
                            break;
                        }
                        i63 |= (cCharAt12 & 8191) << i65;
                        i65 += 13;
                        i64 = i22;
                        length = i11;
                    }
                    iCharAt10 = i63 | (cCharAt12 << i65);
                    i12 = i22;
                } else {
                    i11 = length;
                    i12 = i62;
                }
                int i66 = i12 + 1;
                int iCharAt11 = strZzd.charAt(i12);
                if (iCharAt11 >= 55296) {
                    int i67 = iCharAt11 & 8191;
                    int i68 = i66;
                    int i69 = 13;
                    while (true) {
                        i21 = i68 + 1;
                        cCharAt11 = strZzd.charAt(i68);
                        i13 = iCharAt5;
                        if (cCharAt11 < 55296) {
                            break;
                        }
                        i67 |= (cCharAt11 & 8191) << i69;
                        i69 += 13;
                        i68 = i21;
                        iCharAt5 = i13;
                    }
                    iCharAt11 = i67 | (cCharAt11 << i69);
                    i14 = i21;
                } else {
                    i13 = iCharAt5;
                    i14 = i66;
                }
                int i70 = iCharAt11 & 255;
                int i71 = iCharAt2;
                if ((iCharAt11 & 1024) != 0) {
                    iArr[i60] = i61;
                    i60++;
                }
                int i72 = iCharAt;
                if (i70 >= 51) {
                    int i73 = i14 + 1;
                    int iCharAt12 = strZzd.charAt(i14);
                    char c = 55296;
                    if (iCharAt12 >= 55296) {
                        int i74 = iCharAt12 & 8191;
                        int i75 = 13;
                        while (true) {
                            i20 = i73 + 1;
                            cCharAt10 = strZzd.charAt(i73);
                            if (cCharAt10 < c) {
                                break;
                            }
                            i74 |= (cCharAt10 & 8191) << i75;
                            i75 += 13;
                            i73 = i20;
                            c = 55296;
                        }
                        iCharAt12 = i74 | (cCharAt10 << i75);
                        i73 = i20;
                    }
                    int i76 = i70 - 51;
                    int i77 = i73;
                    if (i76 == 9 || i76 == 17) {
                        i19 = 1;
                        objArr[((i61 / 3) << 1) + 1] = objArrZze[i56];
                        i56++;
                    } else {
                        if (i76 == 12 && !z) {
                            objArr[((i61 / 3) << 1) + 1] = objArrZze[i56];
                            i56++;
                        }
                        i19 = 1;
                    }
                    int i78 = iCharAt12 << i19;
                    Object obj = objArrZze[i78];
                    if (obj instanceof Field) {
                        fieldZza2 = (Field) obj;
                    } else {
                        fieldZza2 = zza(cls2, (String) obj);
                        objArrZze[i78] = fieldZza2;
                    }
                    int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZza2);
                    int i79 = i78 + 1;
                    Object obj2 = objArrZze[i79];
                    if (obj2 instanceof Field) {
                        fieldZza3 = (Field) obj2;
                    } else {
                        fieldZza3 = zza(cls2, (String) obj2);
                        objArrZze[i79] = fieldZza3;
                    }
                    i15 = i56;
                    iObjectFieldOffset = iObjectFieldOffset3;
                    i16 = i77;
                    zzicVar = zzicVar2;
                    strZzd = strZzd;
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza3);
                    i17 = 0;
                } else {
                    int i80 = i56 + 1;
                    Field fieldZza4 = zza(cls2, (String) objArrZze[i56]);
                    zzicVar = zzicVar2;
                    if (i70 == 9 || i70 == 17) {
                        objArr[((i61 / 3) << 1) + 1] = fieldZza4.getType();
                    } else {
                        if (i70 == 27 || i70 == 49) {
                            i18 = i56 + 2;
                            objArr[((i61 / 3) << 1) + 1] = objArrZze[i80];
                        } else if (i70 != 12 && i70 != 30 && i70 != 44) {
                            if (i70 == 50) {
                                int i81 = i57 + 1;
                                iArr[i57] = i61;
                                int i82 = (i61 / 3) << 1;
                                int i83 = i56 + 2;
                                objArr[i82] = objArrZze[i80];
                                if ((iCharAt11 & 2048) != 0) {
                                    i80 = i56 + 3;
                                    objArr[i82 + 1] = objArrZze[i83];
                                    i57 = i81;
                                } else {
                                    i57 = i81;
                                    i15 = i83;
                                }
                            }
                            iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                            if ((iCharAt11 & 4096) == 4096 || i70 > 17) {
                                iObjectFieldOffset2 = 1048575;
                                i16 = i14;
                                i17 = 0;
                            } else {
                                int i84 = i14 + 1;
                                int iCharAt13 = strZzd.charAt(i14);
                                if (iCharAt13 >= 55296) {
                                    int i85 = iCharAt13 & 8191;
                                    int i86 = 13;
                                    while (true) {
                                        i16 = i84 + 1;
                                        cCharAt9 = strZzd.charAt(i84);
                                        if (cCharAt9 < 55296) {
                                            break;
                                        }
                                        i85 |= (cCharAt9 & 8191) << i86;
                                        i86 += 13;
                                        i84 = i16;
                                    }
                                    iCharAt13 = i85 | (cCharAt9 << i86);
                                } else {
                                    i16 = i84;
                                }
                                int i87 = (i24 << 1) + (iCharAt13 / 32);
                                Object obj3 = objArrZze[i87];
                                if (obj3 instanceof Field) {
                                    fieldZza = (Field) obj3;
                                } else {
                                    fieldZza = zza(cls2, (String) obj3);
                                    objArrZze[i87] = fieldZza;
                                }
                                iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza);
                                i17 = iCharAt13 % 32;
                            }
                            if (i70 >= 18 && i70 <= 49) {
                                iArr[i59] = iObjectFieldOffset;
                                i59++;
                            }
                        } else if (!z) {
                            i18 = i56 + 2;
                            objArr[((i61 / 3) << 1) + 1] = objArrZze[i80];
                        }
                        i15 = i18;
                        iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                        if ((iCharAt11 & 4096) == 4096) {
                            iObjectFieldOffset2 = 1048575;
                            i16 = i14;
                            i17 = 0;
                        } else {
                            iObjectFieldOffset2 = 1048575;
                            i16 = i14;
                            i17 = 0;
                        }
                        if (i70 >= 18) {
                            iArr[i59] = iObjectFieldOffset;
                            i59++;
                        }
                    }
                    i15 = i80;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                    if ((iCharAt11 & 4096) == 4096) {
                        iObjectFieldOffset2 = 1048575;
                        i16 = i14;
                        i17 = 0;
                    } else {
                        iObjectFieldOffset2 = 1048575;
                        i16 = i14;
                        i17 = 0;
                    }
                    if (i70 >= 18) {
                        iArr[i59] = iObjectFieldOffset;
                        i59++;
                    }
                }
                int i88 = i61 + 1;
                iArr2[i61] = iCharAt10;
                int i89 = i61 + 2;
                iArr2[i88] = ((iCharAt11 & 256) != 0 ? 268435456 : 0) | ((iCharAt11 & 512) != 0 ? 536870912 : 0) | (i70 << 20) | iObjectFieldOffset;
                i61 += 3;
                iArr2[i89] = iObjectFieldOffset2 | (i17 << 20);
                strZzd = strZzd;
                i56 = i15;
                iCharAt2 = i71;
                i58 = i16;
                length = i11;
                iCharAt5 = i13;
                iCharAt = i72;
                zzicVar2 = zzicVar;
            }
            return new zzhp<>(iArr2, objArr, iCharAt, iCharAt2, zzicVar2.zzc(), z, false, iArr, iCharAt5, i55, zzhuVar, zzgvVar, zzitVar, zzfuVar, zzhiVar);
        }
        ((zziq) zzhjVar).zza();
        int i90 = zzhx.zzb;
        throw new NoSuchMethodError();
    }

    private static Field zza(Class<?> cls, String str) {
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
            throw new RuntimeException(new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name2).length() + String.valueOf(string).length()).append("Field ").append(str).append(" for ").append(name2).append(" not found. Known fields are ").append(string).toString());
        }
    }

    /* JADX WARN: Code duplicated, block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final boolean zza(T t, T t2) {
        int length = this.zzc.length;
        int i = 0;
        while (true) {
            boolean zZza = true;
            if (i < length) {
                int iZzc = zzc(i);
                long j = iZzc & 1048575;
                switch ((iZzc & 267386880) >>> 20) {
                    case 0:
                        if (!zzc(t, t2, i) || Double.doubleToLongBits(zziz.zze(t, j)) != Double.doubleToLongBits(zziz.zze(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 1:
                        if (!zzc(t, t2, i) || Float.floatToIntBits(zziz.zzd(t, j)) != Float.floatToIntBits(zziz.zzd(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 2:
                        if (!zzc(t, t2, i) || zziz.zzb(t, j) != zziz.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 3:
                        if (!zzc(t, t2, i) || zziz.zzb(t, j) != zziz.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 4:
                        if (!zzc(t, t2, i) || zziz.zza(t, j) != zziz.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 5:
                        if (!zzc(t, t2, i) || zziz.zzb(t, j) != zziz.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 6:
                        if (!zzc(t, t2, i) || zziz.zza(t, j) != zziz.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 7:
                        if (!zzc(t, t2, i) || zziz.zzc(t, j) != zziz.zzc(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 8:
                        if (!zzc(t, t2, i) || !zzid.zza(zziz.zzf(t, j), zziz.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 9:
                        if (!zzc(t, t2, i) || !zzid.zza(zziz.zzf(t, j), zziz.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 10:
                        if (!zzc(t, t2, i) || !zzid.zza(zziz.zzf(t, j), zziz.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 11:
                        if (!zzc(t, t2, i) || zziz.zza(t, j) != zziz.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 12:
                        if (!zzc(t, t2, i) || zziz.zza(t, j) != zziz.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 13:
                        if (!zzc(t, t2, i) || zziz.zza(t, j) != zziz.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 14:
                        if (!zzc(t, t2, i) || zziz.zzb(t, j) != zziz.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 15:
                        if (!zzc(t, t2, i) || zziz.zza(t, j) != zziz.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 16:
                        if (!zzc(t, t2, i) || zziz.zzb(t, j) != zziz.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 17:
                        if (!zzc(t, t2, i) || !zzid.zza(zziz.zzf(t, j), zziz.zzf(t2, j))) {
                            zZza = false;
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
                        zZza = zzid.zza(zziz.zzf(t, j), zziz.zzf(t2, j));
                        break;
                    case 50:
                        zZza = zzid.zza(zziz.zzf(t, j), zziz.zzf(t2, j));
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
                        long jZzd = zzd(i) & 1048575;
                        if (zziz.zza(t, jZzd) != zziz.zza(t2, jZzd) || !zzid.zza(zziz.zzf(t, j), zziz.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                }
                if (!zZza) {
                    return false;
                }
                i += 3;
            } else {
                if (!this.zzq.zza(t).equals(this.zzq.zza(t2))) {
                    return false;
                }
                if (this.zzh) {
                    return this.zzr.zza(t).equals(this.zzr.zza(t2));
                }
                return true;
            }
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final int zza(T t) {
        int i;
        int iZza;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzc = zzc(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzc;
            int iHashCode = 37;
            switch ((iZzc & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZza = zzgg.zza(Double.doubleToLongBits(zziz.zze(t, j)));
                    i2 = i + iZza;
                    break;
                case 1:
                    i = i2 * 53;
                    iZza = Float.floatToIntBits(zziz.zzd(t, j));
                    i2 = i + iZza;
                    break;
                case 2:
                    i = i2 * 53;
                    iZza = zzgg.zza(zziz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 3:
                    i = i2 * 53;
                    iZza = zzgg.zza(zziz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 4:
                    i = i2 * 53;
                    iZza = zziz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 5:
                    i = i2 * 53;
                    iZza = zzgg.zza(zziz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 6:
                    i = i2 * 53;
                    iZza = zziz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 7:
                    i = i2 * 53;
                    iZza = zzgg.zza(zziz.zzc(t, j));
                    i2 = i + iZza;
                    break;
                case 8:
                    i = i2 * 53;
                    iZza = ((String) zziz.zzf(t, j)).hashCode();
                    i2 = i + iZza;
                    break;
                case 9:
                    Object objZzf = zziz.zzf(t, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZza = zziz.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 11:
                    i = i2 * 53;
                    iZza = zziz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 12:
                    i = i2 * 53;
                    iZza = zziz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 13:
                    i = i2 * 53;
                    iZza = zziz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 14:
                    i = i2 * 53;
                    iZza = zzgg.zza(zziz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 15:
                    i = i2 * 53;
                    iZza = zziz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 16:
                    i = i2 * 53;
                    iZza = zzgg.zza(zziz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 17:
                    Object objZzf2 = zziz.zzf(t, j);
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
                    iZza = zziz.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 50:
                    i = i2 * 53;
                    iZza = zziz.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 51:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzgg.zza(Double.doubleToLongBits(zzb(t, j)));
                        i2 = i + iZza;
                    }
                    break;
                case 52:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = Float.floatToIntBits(zzc(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 53:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzgg.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 54:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzgg.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 55:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 56:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzgg.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 57:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 58:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzgg.zza(zzf(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 59:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = ((String) zziz.zzf(t, j)).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 60:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zziz.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 61:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zziz.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 62:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 63:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 64:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 65:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzgg.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 66:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 67:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzgg.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 68:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zziz.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzq.zza(t).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzr.zza(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final void zzb(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzc = zzc(i);
            long j = 1048575 & iZzc;
            int i2 = this.zzc[i];
            switch ((iZzc & 267386880) >>> 20) {
                case 0:
                    if (zza((Object) t2, i)) {
                        zziz.zza(t, j, zziz.zze(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 1:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zzd(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 2:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 3:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 4:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 5:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 6:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 7:
                    if (zza((Object) t2, i)) {
                        zziz.zza(t, j, zziz.zzc(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 8:
                    if (zza((Object) t2, i)) {
                        zziz.zza(t, j, zziz.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((Object) t2, i)) {
                        zziz.zza(t, j, zziz.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 11:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 12:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 13:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 14:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 15:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 16:
                    if (zza((Object) t2, i)) {
                        zziz.zza((Object) t, j, zziz.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 17:
                    zza(t, t2, i);
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
                    this.zzp.zza(t, t2, j);
                    break;
                case 50:
                    zzid.zza(this.zzs, t, t2, j);
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
                    if (zza(t2, i2, i)) {
                        zziz.zza(t, j, zziz.zzf(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza(t2, i2, i)) {
                        zziz.zza(t, j, zziz.zzf(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzid.zza(this.zzq, t, t2);
        if (this.zzh) {
            zzid.zza(this.zzr, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzc = zzc(i) & 1048575;
        if (zza((Object) t2, i)) {
            Object objZzf = zziz.zzf(t, jZzc);
            Object objZzf2 = zziz.zzf(t2, jZzc);
            if (objZzf != null && objZzf2 != null) {
                zziz.zza(t, jZzc, zzgg.zza(objZzf, objZzf2));
                zzb((Object) t, i);
            } else if (objZzf2 != null) {
                zziz.zza(t, jZzc, objZzf2);
                zzb((Object) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzc = zzc(i);
        int i2 = this.zzc[i];
        long j = iZzc & 1048575;
        if (zza(t2, i2, i)) {
            Object objZzf = zza(t, i2, i) ? zziz.zzf(t, j) : null;
            Object objZzf2 = zziz.zzf(t2, j);
            if (objZzf != null && objZzf2 != null) {
                zziz.zza(t, j, zzgg.zza(objZzf, objZzf2));
                zzb(t, i2, i);
            } else if (objZzf2 != null) {
                zziz.zza(t, j, objZzf2);
                zzb(t, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:328:0x0809 A[PHI: r5
      0x0809: PHI (r5v4 int) = 
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v16 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v17 int)
      (r5v1 int)
     binds: [B:204:0x04b2, B:366:0x08ad, B:360:0x0891, B:357:0x087f, B:354:0x0870, B:351:0x0863, B:348:0x0856, B:344:0x084b, B:341:0x0840, B:338:0x0833, B:335:0x0826, B:332:0x0813, B:305:0x0722, B:302:0x070d, B:299:0x06f8, B:296:0x06e3, B:293:0x06ce, B:290:0x06b9, B:287:0x06a3, B:284:0x068d, B:281:0x0677, B:278:0x0661, B:275:0x064b, B:272:0x0635, B:269:0x061f, B:266:0x0609, B:261:0x05d5, B:258:0x05c8, B:255:0x05b8, B:252:0x05a8, B:249:0x0598, B:246:0x058a, B:243:0x057d, B:240:0x0570, B:234:0x0552, B:231:0x053e, B:228:0x052c, B:225:0x051c, B:222:0x050c, B:346:0x0852, B:219:0x04ff, B:216:0x04f1, B:213:0x04e1, B:210:0x04d1, B:327:0x0808, B:207:0x04bb] A[DONT_GENERATE, DONT_INLINE]] */
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
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final int zzb(T t) {
        int i;
        int i2;
        int i3;
        long j;
        int iZzd;
        int iZzb;
        int iZzj;
        int iZzh;
        int iZzi;
        int iZze;
        int iZzg;
        int iZzb2;
        int iZzi2;
        int iZze2;
        int iZzg2;
        int i4 = 267386880;
        int i5 = 1048575;
        int i6 = 1;
        int i7 = 0;
        if (this.zzj) {
            Unsafe unsafe = zzb;
            int i8 = 0;
            int i9 = 0;
            while (i8 < this.zzc.length) {
                int iZzc = zzc(i8);
                int i10 = (iZzc & i4) >>> 20;
                int i11 = this.zzc[i8];
                long j2 = iZzc & 1048575;
                if (i10 >= zzga.DOUBLE_LIST_PACKED.zza() && i10 <= zzga.SINT64_LIST_PACKED.zza()) {
                    int i12 = this.zzc[i8 + 2];
                }
                switch (i10) {
                    case 0:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzb(i11, 0.0d);
                            i9 += iZzb2;
                        }
                        break;
                    case 1:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzb(i11, 0.0f);
                            i9 += iZzb2;
                        }
                        break;
                    case 2:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzd(i11, zziz.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 3:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zze(i11, zziz.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 4:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzf(i11, zziz.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 5:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzg(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 6:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzi(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 7:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzb(i11, true);
                            i9 += iZzb2;
                        }
                        break;
                    case 8:
                        if (zza((Object) t, i8)) {
                            Object objZzf = zziz.zzf(t, j2);
                            if (objZzf instanceof zzez) {
                                iZzb2 = zzfq.zzc(i11, (zzez) objZzf);
                            } else {
                                iZzb2 = zzfq.zzb(i11, (String) objZzf);
                            }
                            i9 += iZzb2;
                        }
                        break;
                    case 9:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzid.zza(i11, zziz.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 10:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzc(i11, (zzez) zziz.zzf(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 11:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzg(i11, zziz.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 12:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzk(i11, zziz.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 13:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzj(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 14:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzh(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 15:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzh(i11, zziz.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 16:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzf(i11, zziz.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 17:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzfq.zzc(i11, (zzhl) zziz.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 18:
                        iZzb2 = zzid.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 19:
                        iZzb2 = zzid.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 20:
                        iZzb2 = zzid.zza(i11, (List<Long>) zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 21:
                        iZzb2 = zzid.zzb(i11, (List<Long>) zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 22:
                        iZzb2 = zzid.zze(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 23:
                        iZzb2 = zzid.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 24:
                        iZzb2 = zzid.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 25:
                        iZzb2 = zzid.zzj(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 26:
                        iZzb2 = zzid.zza(i11, zza(t, j2));
                        i9 += iZzb2;
                        break;
                    case 27:
                        iZzb2 = zzid.zza(i11, zza(t, j2), zza(i8));
                        i9 += iZzb2;
                        break;
                    case 28:
                        iZzb2 = zzid.zzb(i11, zza(t, j2));
                        i9 += iZzb2;
                        break;
                    case 29:
                        iZzb2 = zzid.zzf(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 30:
                        iZzb2 = zzid.zzd(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 31:
                        iZzb2 = zzid.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 32:
                        iZzb2 = zzid.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 33:
                        iZzb2 = zzid.zzg(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 34:
                        iZzb2 = zzid.zzc(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 35:
                        iZzi2 = zzid.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 36:
                        iZzi2 = zzid.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 37:
                        iZzi2 = zzid.zza((List<Long>) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 38:
                        iZzi2 = zzid.zzb((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 39:
                        iZzi2 = zzid.zze((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 40:
                        iZzi2 = zzid.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 41:
                        iZzi2 = zzid.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 42:
                        iZzi2 = zzid.zzj((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 43:
                        iZzi2 = zzid.zzf((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 44:
                        iZzi2 = zzid.zzd((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 45:
                        iZzi2 = zzid.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 46:
                        iZzi2 = zzid.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 47:
                        iZzi2 = zzid.zzg((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 48:
                        iZzi2 = zzid.zzc((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzfq.zze(i11);
                            iZzg2 = zzfq.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 49:
                        iZzb2 = zzid.zzb(i11, (List<zzhl>) zza(t, j2), zza(i8));
                        i9 += iZzb2;
                        break;
                    case 50:
                        iZzb2 = this.zzs.zza(i11, zziz.zzf(t, j2), zzb(i8));
                        i9 += iZzb2;
                        break;
                    case 51:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzb(i11, 0.0d);
                            i9 += iZzb2;
                        }
                        break;
                    case 52:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzb(i11, 0.0f);
                            i9 += iZzb2;
                        }
                        break;
                    case 53:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzd(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 54:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zze(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 55:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzf(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 56:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzg(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 57:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzi(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 58:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzb(i11, true);
                            i9 += iZzb2;
                        }
                        break;
                    case 59:
                        if (zza(t, i11, i8)) {
                            Object objZzf2 = zziz.zzf(t, j2);
                            if (objZzf2 instanceof zzez) {
                                iZzb2 = zzfq.zzc(i11, (zzez) objZzf2);
                            } else {
                                iZzb2 = zzfq.zzb(i11, (String) objZzf2);
                            }
                            i9 += iZzb2;
                        }
                        break;
                    case 60:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzid.zza(i11, zziz.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 61:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzc(i11, (zzez) zziz.zzf(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 62:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzg(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 63:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzk(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 64:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzj(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 65:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzh(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 66:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzh(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 67:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzf(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 68:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzfq.zzc(i11, (zzhl) zziz.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                }
                i8 += 3;
                i4 = 267386880;
            }
            return i9 + zza((zzit) this.zzq, (Object) t);
        }
        Unsafe unsafe2 = zzb;
        int i13 = 1048575;
        int i14 = 0;
        int iZzb3 = 0;
        int i15 = 0;
        while (i14 < this.zzc.length) {
            int iZzc2 = zzc(i14);
            int[] iArr = this.zzc;
            int i16 = iArr[i14];
            int i17 = (iZzc2 & 267386880) >>> 20;
            if (i17 <= 17) {
                int i18 = iArr[i14 + 2];
                int i19 = i18 & i5;
                i = i6 << (i18 >>> 20);
                if (i19 != i13) {
                    i15 = unsafe2.getInt(t, i19);
                    i13 = i19;
                }
            } else {
                i = 0;
            }
            long j3 = iZzc2 & i5;
            switch (i17) {
                case 0:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzfq.zzb(i16, 0.0d);
                    }
                    break;
                case 1:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzfq.zzb(i16, 0.0f);
                    }
                    break;
                case 2:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzfq.zzd(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 3:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzfq.zze(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 4:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzfq.zzf(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 5:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzd = zzfq.zzg(i16, 0L);
                        iZzb3 += iZzd;
                    }
                    break;
                case 6:
                    i2 = 1;
                    i3 = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzfq.zzi(i16, 0);
                    }
                    j = 0;
                    break;
                case 7:
                    if ((i15 & i) != 0) {
                        i2 = 1;
                        iZzb3 += zzfq.zzb(i16, true);
                    } else {
                        i2 = 1;
                    }
                    i3 = 0;
                    j = 0;
                    break;
                case 8:
                    if ((i15 & i) != 0) {
                        Object object = unsafe2.getObject(t, j3);
                        if (object instanceof zzez) {
                            iZzb = zzfq.zzc(i16, (zzez) object);
                        } else {
                            iZzb = zzfq.zzb(i16, (String) object);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 9:
                    if ((i15 & i) != 0) {
                        iZzb = zzid.zza(i16, unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 10:
                    if ((i15 & i) != 0) {
                        iZzb = zzfq.zzc(i16, (zzez) unsafe2.getObject(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 11:
                    if ((i15 & i) != 0) {
                        iZzb = zzfq.zzg(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 12:
                    if ((i15 & i) != 0) {
                        iZzb = zzfq.zzk(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 13:
                    if ((i15 & i) != 0) {
                        iZzj = zzfq.zzj(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 14:
                    if ((i15 & i) != 0) {
                        iZzb = zzfq.zzh(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 15:
                    if ((i15 & i) != 0) {
                        iZzb = zzfq.zzh(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 16:
                    if ((i15 & i) != 0) {
                        iZzb = zzfq.zzf(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 17:
                    if ((i15 & i) != 0) {
                        iZzb = zzfq.zzc(i16, (zzhl) unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 18:
                    iZzb = zzid.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 19:
                    i3 = 0;
                    iZzh = zzid.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 20:
                    i3 = 0;
                    iZzh = zzid.zza(i16, (List<Long>) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 21:
                    i3 = 0;
                    iZzh = zzid.zzb(i16, (List<Long>) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 22:
                    i3 = 0;
                    iZzh = zzid.zze(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 23:
                    i3 = 0;
                    iZzh = zzid.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 24:
                    i3 = 0;
                    iZzh = zzid.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 25:
                    i3 = 0;
                    iZzh = zzid.zzj(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 26:
                    iZzb = zzid.zza(i16, (List<?>) unsafe2.getObject(t, j3));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 27:
                    iZzb = zzid.zza(i16, (List<?>) unsafe2.getObject(t, j3), zza(i14));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 28:
                    iZzb = zzid.zzb(i16, (List) unsafe2.getObject(t, j3));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 29:
                    iZzb = zzid.zzf(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 30:
                    i3 = 0;
                    iZzh = zzid.zzd(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 31:
                    i3 = 0;
                    iZzh = zzid.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 32:
                    i3 = 0;
                    iZzh = zzid.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 33:
                    i3 = 0;
                    iZzh = zzid.zzg(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 34:
                    i3 = 0;
                    iZzh = zzid.zzc(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 35:
                    iZzi = zzid.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 36:
                    iZzi = zzid.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 37:
                    iZzi = zzid.zza((List<Long>) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 38:
                    iZzi = zzid.zzb((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 39:
                    iZzi = zzid.zze((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 40:
                    iZzi = zzid.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 41:
                    iZzi = zzid.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 42:
                    iZzi = zzid.zzj((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 43:
                    iZzi = zzid.zzf((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 44:
                    iZzi = zzid.zzd((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 45:
                    iZzi = zzid.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 46:
                    iZzi = zzid.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 47:
                    iZzi = zzid.zzg((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 48:
                    iZzi = zzid.zzc((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzfq.zze(i16);
                        iZzg = zzfq.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 49:
                    iZzb = zzid.zzb(i16, (List<zzhl>) unsafe2.getObject(t, j3), zza(i14));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 50:
                    iZzb = this.zzs.zza(i16, unsafe2.getObject(t, j3), zzb(i14));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 51:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzb(i16, 0.0d);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 52:
                    if (zza(t, i16, i14)) {
                        iZzj = zzfq.zzb(i16, 0.0f);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 53:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzd(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 54:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zze(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 55:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzf(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 56:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzg(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 57:
                    if (zza(t, i16, i14)) {
                        iZzj = zzfq.zzi(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 58:
                    if (zza(t, i16, i14)) {
                        iZzj = zzfq.zzb(i16, true);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 59:
                    if (zza(t, i16, i14)) {
                        Object object2 = unsafe2.getObject(t, j3);
                        if (object2 instanceof zzez) {
                            iZzb = zzfq.zzc(i16, (zzez) object2);
                        } else {
                            iZzb = zzfq.zzb(i16, (String) object2);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 60:
                    if (zza(t, i16, i14)) {
                        iZzb = zzid.zza(i16, unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 61:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzc(i16, (zzez) unsafe2.getObject(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 62:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzg(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 63:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzk(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 64:
                    if (zza(t, i16, i14)) {
                        iZzj = zzfq.zzj(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 65:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzh(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 66:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzh(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 67:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzf(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 68:
                    if (zza(t, i16, i14)) {
                        iZzb = zzfq.zzc(i16, (zzhl) unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                default:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
            }
            i14 += 3;
            i6 = i2;
            i7 = i3;
            i5 = 1048575;
        }
        int iZza = i7;
        int iZza2 = iZzb3 + zza((zzit) this.zzq, (Object) t);
        if (!this.zzh) {
            return iZza2;
        }
        zzfv<T> zzfvVarZza = this.zzr.zza(t);
        for (int i20 = iZza; i20 < zzfvVarZza.zza.zzc(); i20++) {
            Map.Entry entryZzb = zzfvVarZza.zza.zzb(i20);
            iZza += zzfv.zza((zzfx<?>) entryZzb.getKey(), entryZzb.getValue());
        }
        for (Map.Entry entry : zzfvVarZza.zza.zzd()) {
            iZza += zzfv.zza((zzfx<?>) entry.getKey(), entry.getValue());
        }
        return iZza2 + iZza;
    }

    private static <UT, UB> int zza(zzit<UT, UB> zzitVar, T t) {
        return zzitVar.zzd(zzitVar.zza(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zziz.zzf(obj, j);
    }

    /* JADX WARN: Code duplicated, block: B:178:0x054a  */
    /* JADX WARN: Code duplicated, block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final void zza(T t, zzjn zzjnVar) throws IOException {
        Iterator itZzd;
        Map.Entry<?, ?> entry;
        Iterator itZze;
        Map.Entry<?, ?> entry2;
        if (zzjnVar.zza() == zzjq.zzb) {
            zza(this.zzq, t, zzjnVar);
            if (this.zzh) {
                zzfv<T> zzfvVarZza = this.zzr.zza(t);
                if (zzfvVarZza.zza.isEmpty()) {
                    itZze = null;
                    entry2 = null;
                } else {
                    itZze = zzfvVarZza.zze();
                    entry2 = (Map.Entry) itZze.next();
                }
            } else {
                itZze = null;
                entry2 = null;
            }
            for (int length = this.zzc.length - 3; length >= 0; length -= 3) {
                int iZzc = zzc(length);
                int i = this.zzc[length];
                while (entry2 != null && this.zzr.zza(entry2) > i) {
                    this.zzr.zza(zzjnVar, entry2);
                    entry2 = itZze.hasNext() ? (Map.Entry) itZze.next() : null;
                }
                switch ((iZzc & 267386880) >>> 20) {
                    case 0:
                        if (zza((Object) t, length)) {
                            zzjnVar.zza(i, zziz.zze(t, iZzc & 1048575));
                        }
                        break;
                    case 1:
                        if (zza((Object) t, length)) {
                            zzjnVar.zza(i, zziz.zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 2:
                        if (zza((Object) t, length)) {
                            zzjnVar.zza(i, zziz.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 3:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzc(i, zziz.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 4:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzc(i, zziz.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 5:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzd(i, zziz.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 6:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzd(i, zziz.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 7:
                        if (zza((Object) t, length)) {
                            zzjnVar.zza(i, zziz.zzc(t, iZzc & 1048575));
                        }
                        break;
                    case 8:
                        if (zza((Object) t, length)) {
                            zza(i, zziz.zzf(t, iZzc & 1048575), zzjnVar);
                        }
                        break;
                    case 9:
                        if (zza((Object) t, length)) {
                            zzjnVar.zza(i, zziz.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                    case 10:
                        if (zza((Object) t, length)) {
                            zzjnVar.zza(i, (zzez) zziz.zzf(t, iZzc & 1048575));
                        }
                        break;
                    case 11:
                        if (zza((Object) t, length)) {
                            zzjnVar.zze(i, zziz.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 12:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzb(i, zziz.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 13:
                        if (zza((Object) t, length)) {
                            zzjnVar.zza(i, zziz.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 14:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzb(i, zziz.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 15:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzf(i, zziz.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 16:
                        if (zza((Object) t, length)) {
                            zzjnVar.zze(i, zziz.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 17:
                        if (zza((Object) t, length)) {
                            zzjnVar.zzb(i, zziz.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                    case 18:
                        zzid.zza(this.zzc[length], (List<Double>) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 19:
                        zzid.zzb(this.zzc[length], (List<Float>) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 20:
                        zzid.zzc(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 21:
                        zzid.zzd(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 22:
                        zzid.zzh(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 23:
                        zzid.zzf(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 24:
                        zzid.zzk(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 25:
                        zzid.zzn(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 26:
                        zzid.zza(this.zzc[length], (List<String>) zziz.zzf(t, iZzc & 1048575), zzjnVar);
                        break;
                    case 27:
                        zzid.zza(this.zzc[length], (List<?>) zziz.zzf(t, iZzc & 1048575), zzjnVar, zza(length));
                        break;
                    case 28:
                        zzid.zzb(this.zzc[length], (List<zzez>) zziz.zzf(t, iZzc & 1048575), zzjnVar);
                        break;
                    case 29:
                        zzid.zzi(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 30:
                        zzid.zzm(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 31:
                        zzid.zzl(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 32:
                        zzid.zzg(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 33:
                        zzid.zzj(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 34:
                        zzid.zze(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, false);
                        break;
                    case 35:
                        zzid.zza(this.zzc[length], (List<Double>) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 36:
                        zzid.zzb(this.zzc[length], (List<Float>) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 37:
                        zzid.zzc(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 38:
                        zzid.zzd(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 39:
                        zzid.zzh(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 40:
                        zzid.zzf(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 41:
                        zzid.zzk(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 42:
                        zzid.zzn(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 43:
                        zzid.zzi(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 44:
                        zzid.zzm(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 45:
                        zzid.zzl(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 46:
                        zzid.zzg(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 47:
                        zzid.zzj(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 48:
                        zzid.zze(this.zzc[length], (List) zziz.zzf(t, iZzc & 1048575), zzjnVar, true);
                        break;
                    case 49:
                        zzid.zzb(this.zzc[length], (List<?>) zziz.zzf(t, iZzc & 1048575), zzjnVar, zza(length));
                        break;
                    case 50:
                        zza(zzjnVar, i, zziz.zzf(t, iZzc & 1048575), length);
                        break;
                    case 51:
                        if (zza(t, i, length)) {
                            zzjnVar.zza(i, zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 52:
                        if (zza(t, i, length)) {
                            zzjnVar.zza(i, zzc(t, iZzc & 1048575));
                        }
                        break;
                    case 53:
                        if (zza(t, i, length)) {
                            zzjnVar.zza(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 54:
                        if (zza(t, i, length)) {
                            zzjnVar.zzc(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 55:
                        if (zza(t, i, length)) {
                            zzjnVar.zzc(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 56:
                        if (zza(t, i, length)) {
                            zzjnVar.zzd(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 57:
                        if (zza(t, i, length)) {
                            zzjnVar.zzd(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 58:
                        if (zza(t, i, length)) {
                            zzjnVar.zza(i, zzf(t, iZzc & 1048575));
                        }
                        break;
                    case 59:
                        if (zza(t, i, length)) {
                            zza(i, zziz.zzf(t, iZzc & 1048575), zzjnVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i, length)) {
                            zzjnVar.zza(i, zziz.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                    case 61:
                        if (zza(t, i, length)) {
                            zzjnVar.zza(i, (zzez) zziz.zzf(t, iZzc & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i, length)) {
                            zzjnVar.zze(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 63:
                        if (zza(t, i, length)) {
                            zzjnVar.zzb(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 64:
                        if (zza(t, i, length)) {
                            zzjnVar.zza(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 65:
                        if (zza(t, i, length)) {
                            zzjnVar.zzb(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 66:
                        if (zza(t, i, length)) {
                            zzjnVar.zzf(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 67:
                        if (zza(t, i, length)) {
                            zzjnVar.zze(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 68:
                        if (zza(t, i, length)) {
                            zzjnVar.zzb(i, zziz.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                }
            }
            while (entry2 != null) {
                this.zzr.zza(zzjnVar, entry2);
                entry2 = itZze.hasNext() ? (Map.Entry) itZze.next() : null;
            }
            return;
        }
        if (this.zzj) {
            if (this.zzh) {
                zzfv<T> zzfvVarZza2 = this.zzr.zza(t);
                if (zzfvVarZza2.zza.isEmpty()) {
                    itZzd = null;
                    entry = null;
                } else {
                    itZzd = zzfvVarZza2.zzd();
                    entry = (Map.Entry) itZzd.next();
                }
            } else {
                itZzd = null;
                entry = null;
            }
            int length2 = this.zzc.length;
            for (int i2 = 0; i2 < length2; i2 += 3) {
                int iZzc2 = zzc(i2);
                int i3 = this.zzc[i2];
                while (entry != null && this.zzr.zza(entry) <= i3) {
                    this.zzr.zza(zzjnVar, entry);
                    entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
                }
                switch ((iZzc2 & 267386880) >>> 20) {
                    case 0:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zza(i3, zziz.zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 1:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zza(i3, zziz.zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 2:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zza(i3, zziz.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 3:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzc(i3, zziz.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 4:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzc(i3, zziz.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 5:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzd(i3, zziz.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 6:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzd(i3, zziz.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 7:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zza(i3, zziz.zzc(t, iZzc2 & 1048575));
                        }
                        break;
                    case 8:
                        if (zza((Object) t, i2)) {
                            zza(i3, zziz.zzf(t, iZzc2 & 1048575), zzjnVar);
                        }
                        break;
                    case 9:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zza(i3, zziz.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                    case 10:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zza(i3, (zzez) zziz.zzf(t, iZzc2 & 1048575));
                        }
                        break;
                    case 11:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zze(i3, zziz.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 12:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzb(i3, zziz.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 13:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zza(i3, zziz.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 14:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzb(i3, zziz.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 15:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzf(i3, zziz.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 16:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zze(i3, zziz.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 17:
                        if (zza((Object) t, i2)) {
                            zzjnVar.zzb(i3, zziz.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                    case 18:
                        zzid.zza(this.zzc[i2], (List<Double>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 19:
                        zzid.zzb(this.zzc[i2], (List<Float>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 20:
                        zzid.zzc(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 21:
                        zzid.zzd(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 22:
                        zzid.zzh(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 23:
                        zzid.zzf(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 24:
                        zzid.zzk(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 25:
                        zzid.zzn(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 26:
                        zzid.zza(this.zzc[i2], (List<String>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar);
                        break;
                    case 27:
                        zzid.zza(this.zzc[i2], (List<?>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, zza(i2));
                        break;
                    case 28:
                        zzid.zzb(this.zzc[i2], (List<zzez>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar);
                        break;
                    case 29:
                        zzid.zzi(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 30:
                        zzid.zzm(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 31:
                        zzid.zzl(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 32:
                        zzid.zzg(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 33:
                        zzid.zzj(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 34:
                        zzid.zze(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, false);
                        break;
                    case 35:
                        zzid.zza(this.zzc[i2], (List<Double>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 36:
                        zzid.zzb(this.zzc[i2], (List<Float>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 37:
                        zzid.zzc(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 38:
                        zzid.zzd(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 39:
                        zzid.zzh(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 40:
                        zzid.zzf(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 41:
                        zzid.zzk(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 42:
                        zzid.zzn(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 43:
                        zzid.zzi(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 44:
                        zzid.zzm(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 45:
                        zzid.zzl(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 46:
                        zzid.zzg(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 47:
                        zzid.zzj(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 48:
                        zzid.zze(this.zzc[i2], (List) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, true);
                        break;
                    case 49:
                        zzid.zzb(this.zzc[i2], (List<?>) zziz.zzf(t, iZzc2 & 1048575), zzjnVar, zza(i2));
                        break;
                    case 50:
                        zza(zzjnVar, i3, zziz.zzf(t, iZzc2 & 1048575), i2);
                        break;
                    case 51:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zza(i3, zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 52:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zza(i3, zzc(t, iZzc2 & 1048575));
                        }
                        break;
                    case 53:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zza(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 54:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzc(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 55:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzc(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 56:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzd(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 57:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzd(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 58:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zza(i3, zzf(t, iZzc2 & 1048575));
                        }
                        break;
                    case 59:
                        if (zza(t, i3, i2)) {
                            zza(i3, zziz.zzf(t, iZzc2 & 1048575), zzjnVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zza(i3, zziz.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                    case 61:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zza(i3, (zzez) zziz.zzf(t, iZzc2 & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zze(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 63:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzb(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 64:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zza(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 65:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzb(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 66:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzf(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 67:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zze(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 68:
                        if (zza(t, i3, i2)) {
                            zzjnVar.zzb(i3, zziz.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                }
            }
            while (entry != null) {
                this.zzr.zza(zzjnVar, entry);
                entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
            }
            zza(this.zzq, t, zzjnVar);
            return;
        }
        zzb((Object) t, zzjnVar);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0023  */
    private final void zzb(T t, zzjn zzjnVar) throws IOException {
        Iterator itZzd;
        Map.Entry<?, ?> entry;
        int i;
        boolean z;
        if (this.zzh) {
            zzfv<T> zzfvVarZza = this.zzr.zza(t);
            if (zzfvVarZza.zza.isEmpty()) {
                itZzd = null;
                entry = null;
            } else {
                itZzd = zzfvVarZza.zzd();
                entry = (Map.Entry) itZzd.next();
            }
        } else {
            itZzd = null;
            entry = null;
        }
        int length = this.zzc.length;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iZzc = zzc(i4);
            int[] iArr = this.zzc;
            int i5 = iArr[i4];
            int i6 = (iZzc & 267386880) >>> 20;
            if (i6 <= 17) {
                int i7 = iArr[i4 + 2];
                int i8 = i7 & 1048575;
                if (i8 != i2) {
                    i3 = unsafe.getInt(t, i8);
                    i2 = i8;
                }
                i = 1 << (i7 >>> 20);
            } else {
                i = 0;
            }
            while (entry != null && this.zzr.zza(entry) <= i5) {
                this.zzr.zza(zzjnVar, entry);
                entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
            }
            long j = iZzc & 1048575;
            switch (i6) {
                case 0:
                    if ((i & i3) != 0) {
                        zzjnVar.zza(i5, zziz.zze(t, j));
                    }
                    break;
                case 1:
                    if ((i & i3) != 0) {
                        zzjnVar.zza(i5, zziz.zzd(t, j));
                    }
                    break;
                case 2:
                    if ((i & i3) != 0) {
                        zzjnVar.zza(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 3:
                    if ((i & i3) != 0) {
                        zzjnVar.zzc(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 4:
                    if ((i & i3) != 0) {
                        zzjnVar.zzc(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 5:
                    if ((i & i3) != 0) {
                        zzjnVar.zzd(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 6:
                    if ((i & i3) != 0) {
                        zzjnVar.zzd(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 7:
                    if ((i & i3) != 0) {
                        zzjnVar.zza(i5, zziz.zzc(t, j));
                    }
                    break;
                case 8:
                    if ((i & i3) != 0) {
                        zza(i5, unsafe.getObject(t, j), zzjnVar);
                    }
                    break;
                case 9:
                    if ((i & i3) != 0) {
                        zzjnVar.zza(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 10:
                    if ((i & i3) != 0) {
                        zzjnVar.zza(i5, (zzez) unsafe.getObject(t, j));
                    }
                    break;
                case 11:
                    if ((i & i3) != 0) {
                        zzjnVar.zze(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 12:
                    if ((i & i3) != 0) {
                        zzjnVar.zzb(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 13:
                    if ((i & i3) != 0) {
                        zzjnVar.zza(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 14:
                    if ((i & i3) != 0) {
                        zzjnVar.zzb(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 15:
                    if ((i & i3) != 0) {
                        zzjnVar.zzf(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 16:
                    if ((i & i3) != 0) {
                        zzjnVar.zze(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 17:
                    if ((i & i3) != 0) {
                        zzjnVar.zzb(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 18:
                    z = false;
                    zzid.zza(this.zzc[i4], (List<Double>) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 19:
                    z = false;
                    zzid.zzb(this.zzc[i4], (List<Float>) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 20:
                    z = false;
                    zzid.zzc(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 21:
                    z = false;
                    zzid.zzd(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 22:
                    z = false;
                    zzid.zzh(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 23:
                    z = false;
                    zzid.zzf(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 24:
                    z = false;
                    zzid.zzk(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 25:
                    z = false;
                    zzid.zzn(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 26:
                    zzid.zza(this.zzc[i4], (List<String>) unsafe.getObject(t, j), zzjnVar);
                    break;
                case 27:
                    zzid.zza(this.zzc[i4], (List<?>) unsafe.getObject(t, j), zzjnVar, zza(i4));
                    break;
                case 28:
                    zzid.zzb(this.zzc[i4], (List<zzez>) unsafe.getObject(t, j), zzjnVar);
                    break;
                case 29:
                    z = false;
                    zzid.zzi(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 30:
                    z = false;
                    zzid.zzm(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 31:
                    z = false;
                    zzid.zzl(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 32:
                    z = false;
                    zzid.zzg(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 33:
                    z = false;
                    zzid.zzj(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 34:
                    z = false;
                    zzid.zze(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, false);
                    break;
                case 35:
                    zzid.zza(this.zzc[i4], (List<Double>) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 36:
                    zzid.zzb(this.zzc[i4], (List<Float>) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 37:
                    zzid.zzc(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 38:
                    zzid.zzd(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 39:
                    zzid.zzh(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 40:
                    zzid.zzf(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 41:
                    zzid.zzk(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 42:
                    zzid.zzn(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 43:
                    zzid.zzi(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 44:
                    zzid.zzm(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 45:
                    zzid.zzl(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 46:
                    zzid.zzg(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 47:
                    zzid.zzj(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 48:
                    zzid.zze(this.zzc[i4], (List) unsafe.getObject(t, j), zzjnVar, true);
                    break;
                case 49:
                    zzid.zzb(this.zzc[i4], (List<?>) unsafe.getObject(t, j), zzjnVar, zza(i4));
                    break;
                case 50:
                    zza(zzjnVar, i5, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zza(i5, zzb(t, j));
                    }
                    break;
                case 52:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zza(i5, zzc(t, j));
                    }
                    break;
                case 53:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zza(i5, zze(t, j));
                    }
                    break;
                case 54:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzc(i5, zze(t, j));
                    }
                    break;
                case 55:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzc(i5, zzd(t, j));
                    }
                    break;
                case 56:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzd(i5, zze(t, j));
                    }
                    break;
                case 57:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzd(i5, zzd(t, j));
                    }
                    break;
                case 58:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zza(i5, zzf(t, j));
                    }
                    break;
                case 59:
                    if (zza(t, i5, i4)) {
                        zza(i5, unsafe.getObject(t, j), zzjnVar);
                    }
                    break;
                case 60:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zza(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 61:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zza(i5, (zzez) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zze(i5, zzd(t, j));
                    }
                    break;
                case 63:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzb(i5, zzd(t, j));
                    }
                    break;
                case 64:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zza(i5, zzd(t, j));
                    }
                    break;
                case 65:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzb(i5, zze(t, j));
                    }
                    break;
                case 66:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzf(i5, zzd(t, j));
                    }
                    break;
                case 67:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zze(i5, zze(t, j));
                    }
                    break;
                case 68:
                    if (zza(t, i5, i4)) {
                        zzjnVar.zzb(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                default:
                    break;
            }
        }
        while (entry != null) {
            this.zzr.zza(zzjnVar, entry);
            entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
        }
        zza(this.zzq, t, zzjnVar);
    }

    private final <K, V> void zza(zzjn zzjnVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzjnVar.zza(i, this.zzs.zza(zzb(i2)), this.zzs.zzb(obj));
        }
    }

    private static <UT, UB> void zza(zzit<UT, UB> zzitVar, T t, zzjn zzjnVar) throws IOException {
        zzitVar.zza(zzitVar.zza(t), zzjnVar);
    }

    private final zzib zza(int i) {
        int i2 = (i / 3) << 1;
        zzib zzibVar = (zzib) this.zzd[i2];
        if (zzibVar != null) {
            return zzibVar;
        }
        zzib<T> zzibVarZza = zzia.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zzibVarZza;
        return zzibVarZza;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final void zzc(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long jZzc = zzc(this.zzl[i2]) & 1048575;
            Object objZzf = zziz.zzf(t, jZzc);
            if (objZzf != null) {
                zziz.zza(t, jZzc, this.zzs.zzc(objZzf));
            }
            i2++;
        }
        int length = this.zzl.length;
        while (i < length) {
            this.zzp.zza(t, this.zzl[i]);
            i++;
        }
        this.zzq.zzb(t);
        if (this.zzh) {
            this.zzr.zzc(t);
        }
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:51:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:54:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:57:0x00f7 A[LOOP:2: B:52:0x00e6->B:57:0x00f7, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:74:0x00f6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x0114 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzib] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzib] */
    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final boolean zzd(T t) {
        int i;
        int i2;
        List list;
        ?? Zza;
        int i3;
        int i4 = 1048575;
        int i5 = 0;
        int i6 = 0;
        while (i6 < this.zzm) {
            int i7 = this.zzl[i6];
            int i8 = this.zzc[i7];
            int iZzc = zzc(i7);
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
            if ((268435456 & iZzc) != 0 && !zza(t, i7, i, i2, i11)) {
                return false;
            }
            int i12 = (267386880 & iZzc) >>> 20;
            if (i12 == 9 || i12 == 17) {
                if (zza(t, i7, i, i2, i11) && !zza(t, iZzc, zza(i7))) {
                    return false;
                }
            } else if (i12 == 27) {
                list = (List) zziz.zzf(t, iZzc & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    Zza = zza(i7);
                    for (i3 = 0; i3 < list.size(); i3++) {
                        if (!Zza.zzd(list.get(i3))) {
                            return false;
                        }
                    }
                }
            } else if (i12 == 60 || i12 == 68) {
                if (zza(t, i8, i7) && !zza(t, iZzc, zza(i7))) {
                    return false;
                }
            } else if (i12 == 49) {
                list = (List) zziz.zzf(t, iZzc & 1048575);
                if (list.isEmpty()) {
                    Zza = zza(i7);
                    while (i3 < list.size()) {
                        if (!Zza.zzd(list.get(i3))) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            } else if (i12 != 50) {
                continue;
            } else {
                Map<?, ?> mapZzb = this.zzs.zzb(zziz.zzf(t, iZzc & 1048575));
                if (mapZzb.isEmpty()) {
                    continue;
                } else if (this.zzs.zza(zzb(i7)).zzb.zza() == zzjo.MESSAGE) {
                    ?? Zza2 = 0;
                    for (Object obj : mapZzb.values()) {
                        if (Zza2 == 0) {
                            Zza2 = Zza2;
                            Zza2 = zzia.zza().zza((Class) obj.getClass());
                        }
                        Zza2 = Zza2;
                        if (!Zza2.zzd(obj)) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            }
            i6++;
            i4 = i;
            i5 = i2;
        }
        return !this.zzh || this.zzr.zza(t).zzf();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzib zzibVar) {
        return zzibVar.zzd(zziz.zzf(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzjn zzjnVar) throws IOException {
        if (obj instanceof String) {
            zzjnVar.zza(i, (String) obj);
        } else {
            zzjnVar.zza(i, (zzez) obj);
        }
    }

    private final int zzc(int i) {
        return this.zzc[i + 1];
    }

    private final int zzd(int i) {
        return this.zzc[i + 2];
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zziz.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zziz.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zziz.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zziz.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zziz.zzf(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((Object) t, i) == zza((Object) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza((Object) t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t, int i) {
        int iZzd = zzd(i);
        long j = iZzd & 1048575;
        if (j != 1048575) {
            return (zziz.zza(t, j) & (1 << (iZzd >>> 20))) != 0;
        }
        int iZzc = zzc(i);
        long j2 = iZzc & 1048575;
        switch ((iZzc & 267386880) >>> 20) {
            case 0:
                return zziz.zze(t, j2) != 0.0d;
            case 1:
                return zziz.zzd(t, j2) != 0.0f;
            case 2:
                return zziz.zzb(t, j2) != 0;
            case 3:
                return zziz.zzb(t, j2) != 0;
            case 4:
                return zziz.zza(t, j2) != 0;
            case 5:
                return zziz.zzb(t, j2) != 0;
            case 6:
                return zziz.zza(t, j2) != 0;
            case 7:
                return zziz.zzc(t, j2);
            case 8:
                Object objZzf = zziz.zzf(t, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzez) {
                    return !zzez.zza.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zziz.zzf(t, j2) != null;
            case 10:
                return !zzez.zza.equals(zziz.zzf(t, j2));
            case 11:
                return zziz.zza(t, j2) != 0;
            case 12:
                return zziz.zza(t, j2) != 0;
            case 13:
                return zziz.zza(t, j2) != 0;
            case 14:
                return zziz.zzb(t, j2) != 0;
            case 15:
                return zziz.zza(t, j2) != 0;
            case 16:
                return zziz.zzb(t, j2) != 0;
            case 17:
                return zziz.zzf(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzb(T t, int i) {
        int iZzd = zzd(i);
        long j = 1048575 & iZzd;
        if (j == 1048575) {
            return;
        }
        zziz.zza((Object) t, j, (1 << (iZzd >>> 20)) | zziz.zza(t, j));
    }

    private final boolean zza(T t, int i, int i2) {
        return zziz.zza(t, (long) (zzd(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zziz.zza((Object) t, zzd(i2) & 1048575, i);
    }
}
