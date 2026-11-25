package com.google.android.gms.internal.mlkit_language_id;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgd<T> implements zzgp<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzhn.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzfz zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzge zzo;
    private final zzfj zzp;
    private final zzhh<?, ?> zzq;
    private final zzee<?> zzr;
    private final zzfs zzs;

    private zzgd(int[] iArr, Object[] objArr, int i, int i2, zzfz zzfzVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzge zzgeVar, zzfj zzfjVar, zzhh<?, ?> zzhhVar, zzee<?> zzeeVar, zzfs zzfsVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzfzVar instanceof zzeo;
        this.zzj = z;
        this.zzh = zzeeVar != null && zzeeVar.zza(zzfzVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzgeVar;
        this.zzp = zzfjVar;
        this.zzq = zzhhVar;
        this.zzr = zzeeVar;
        this.zzg = zzfzVar;
        this.zzs = zzfsVar;
    }

    /* JADX WARN: Code duplicated, block: B:163:0x033a  */
    /* JADX WARN: Code duplicated, block: B:178:0x0388  */
    /* JADX WARN: Code duplicated, block: B:181:0x0395  */
    static <T> zzgd<T> zza(Class<T> cls, zzfx zzfxVar, zzge zzgeVar, zzfj zzfjVar, zzhh<?, ?> zzhhVar, zzee<?> zzeeVar, zzfs zzfsVar) {
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
        zzgm zzgmVar;
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
        if (zzfxVar instanceof zzgm) {
            zzgm zzgmVar2 = (zzgm) zzfxVar;
            int i24 = 0;
            boolean z = zzgmVar2.zza() == zzgl.zzb;
            String strZzd = zzgmVar2.zzd();
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
            Object[] objArrZze = zzgmVar2.zze();
            Class<?> cls2 = zzgmVar2.zzc().getClass();
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
                    zzgmVar = zzgmVar2;
                    strZzd = strZzd;
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza3);
                    i17 = 0;
                } else {
                    int i80 = i56 + 1;
                    Field fieldZza4 = zza(cls2, (String) objArrZze[i56]);
                    zzgmVar = zzgmVar2;
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
                zzgmVar2 = zzgmVar;
            }
            return new zzgd<>(iArr2, objArr, iCharAt, iCharAt2, zzgmVar2.zzc(), z, false, iArr, iCharAt5, i55, zzgeVar, zzfjVar, zzhhVar, zzeeVar, zzfsVar);
        }
        ((zzha) zzfxVar).zza();
        int i90 = zzgl.zzb;
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
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
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
                        if (!zzc(t, t2, i) || Double.doubleToLongBits(zzhn.zze(t, j)) != Double.doubleToLongBits(zzhn.zze(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 1:
                        if (!zzc(t, t2, i) || Float.floatToIntBits(zzhn.zzd(t, j)) != Float.floatToIntBits(zzhn.zzd(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 2:
                        if (!zzc(t, t2, i) || zzhn.zzb(t, j) != zzhn.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 3:
                        if (!zzc(t, t2, i) || zzhn.zzb(t, j) != zzhn.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 4:
                        if (!zzc(t, t2, i) || zzhn.zza(t, j) != zzhn.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 5:
                        if (!zzc(t, t2, i) || zzhn.zzb(t, j) != zzhn.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 6:
                        if (!zzc(t, t2, i) || zzhn.zza(t, j) != zzhn.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 7:
                        if (!zzc(t, t2, i) || zzhn.zzc(t, j) != zzhn.zzc(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 8:
                        if (!zzc(t, t2, i) || !zzgr.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 9:
                        if (!zzc(t, t2, i) || !zzgr.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 10:
                        if (!zzc(t, t2, i) || !zzgr.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 11:
                        if (!zzc(t, t2, i) || zzhn.zza(t, j) != zzhn.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 12:
                        if (!zzc(t, t2, i) || zzhn.zza(t, j) != zzhn.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 13:
                        if (!zzc(t, t2, i) || zzhn.zza(t, j) != zzhn.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 14:
                        if (!zzc(t, t2, i) || zzhn.zzb(t, j) != zzhn.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 15:
                        if (!zzc(t, t2, i) || zzhn.zza(t, j) != zzhn.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 16:
                        if (!zzc(t, t2, i) || zzhn.zzb(t, j) != zzhn.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 17:
                        if (!zzc(t, t2, i) || !zzgr.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j))) {
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
                        zZza = zzgr.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j));
                        break;
                    case 50:
                        zZza = zzgr.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j));
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
                        if (zzhn.zza(t, jZzd) != zzhn.zza(t2, jZzd) || !zzgr.zza(zzhn.zzf(t, j), zzhn.zzf(t2, j))) {
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

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
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
                    iZza = zzeq.zza(Double.doubleToLongBits(zzhn.zze(t, j)));
                    i2 = i + iZza;
                    break;
                case 1:
                    i = i2 * 53;
                    iZza = Float.floatToIntBits(zzhn.zzd(t, j));
                    i2 = i + iZza;
                    break;
                case 2:
                    i = i2 * 53;
                    iZza = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 3:
                    i = i2 * 53;
                    iZza = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 4:
                    i = i2 * 53;
                    iZza = zzhn.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 5:
                    i = i2 * 53;
                    iZza = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 6:
                    i = i2 * 53;
                    iZza = zzhn.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 7:
                    i = i2 * 53;
                    iZza = zzeq.zza(zzhn.zzc(t, j));
                    i2 = i + iZza;
                    break;
                case 8:
                    i = i2 * 53;
                    iZza = ((String) zzhn.zzf(t, j)).hashCode();
                    i2 = i + iZza;
                    break;
                case 9:
                    Object objZzf = zzhn.zzf(t, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZza = zzhn.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 11:
                    i = i2 * 53;
                    iZza = zzhn.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 12:
                    i = i2 * 53;
                    iZza = zzhn.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 13:
                    i = i2 * 53;
                    iZza = zzhn.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 14:
                    i = i2 * 53;
                    iZza = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 15:
                    i = i2 * 53;
                    iZza = zzhn.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 16:
                    i = i2 * 53;
                    iZza = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 17:
                    Object objZzf2 = zzhn.zzf(t, j);
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
                    iZza = zzhn.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 50:
                    i = i2 * 53;
                    iZza = zzhn.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 51:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzeq.zza(Double.doubleToLongBits(zzb(t, j)));
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
                        iZza = zzeq.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 54:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzeq.zza(zze(t, j));
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
                        iZza = zzeq.zza(zze(t, j));
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
                        iZza = zzeq.zza(zzf(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 59:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = ((String) zzhn.zzf(t, j)).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 60:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzhn.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 61:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzhn.zzf(t, j).hashCode();
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
                        iZza = zzeq.zza(zze(t, j));
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
                        iZza = zzeq.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 68:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzhn.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzq.zza(t).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzr.zza(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zzb(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzc = zzc(i);
            long j = 1048575 & iZzc;
            int i2 = this.zzc[i];
            switch ((iZzc & 267386880) >>> 20) {
                case 0:
                    if (zza((Object) t2, i)) {
                        zzhn.zza(t, j, zzhn.zze(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 1:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzd(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 2:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 3:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 4:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 5:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 6:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 7:
                    if (zza((Object) t2, i)) {
                        zzhn.zza(t, j, zzhn.zzc(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 8:
                    if (zza((Object) t2, i)) {
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((Object) t2, i)) {
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 11:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 12:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 13:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 14:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 15:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 16:
                    if (zza((Object) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
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
                    zzgr.zza(this.zzs, t, t2, j);
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
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
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
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzgr.zza(this.zzq, t, t2);
        if (this.zzh) {
            zzgr.zza(this.zzr, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzc = zzc(i) & 1048575;
        if (zza((Object) t2, i)) {
            Object objZzf = zzhn.zzf(t, jZzc);
            Object objZzf2 = zzhn.zzf(t2, jZzc);
            if (objZzf != null && objZzf2 != null) {
                zzhn.zza(t, jZzc, zzeq.zza(objZzf, objZzf2));
                zzb((Object) t, i);
            } else if (objZzf2 != null) {
                zzhn.zza(t, jZzc, objZzf2);
                zzb((Object) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzc = zzc(i);
        int i2 = this.zzc[i];
        long j = iZzc & 1048575;
        if (zza(t2, i2, i)) {
            Object objZzf = zzhn.zzf(t, j);
            Object objZzf2 = zzhn.zzf(t2, j);
            if (objZzf != null && objZzf2 != null) {
                zzhn.zza(t, j, zzeq.zza(objZzf, objZzf2));
                zzb(t, i2, i);
            } else if (objZzf2 != null) {
                zzhn.zza(t, j, objZzf2);
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
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final int zzd(T t) {
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
                if (i10 >= zzek.DOUBLE_LIST_PACKED.zza() && i10 <= zzek.SINT64_LIST_PACKED.zza()) {
                    int i12 = this.zzc[i8 + 2];
                }
                switch (i10) {
                    case 0:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzb(i11, 0.0d);
                            i9 += iZzb2;
                        }
                        break;
                    case 1:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzb(i11, 0.0f);
                            i9 += iZzb2;
                        }
                        break;
                    case 2:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzd(i11, zzhn.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 3:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zze(i11, zzhn.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 4:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzf(i11, zzhn.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 5:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzg(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 6:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzi(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 7:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzb(i11, true);
                            i9 += iZzb2;
                        }
                        break;
                    case 8:
                        if (zza((Object) t, i8)) {
                            Object objZzf = zzhn.zzf(t, j2);
                            if (objZzf instanceof zzdn) {
                                iZzb2 = zzea.zzc(i11, (zzdn) objZzf);
                            } else {
                                iZzb2 = zzea.zzb(i11, (String) objZzf);
                            }
                            i9 += iZzb2;
                        }
                        break;
                    case 9:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzgr.zza(i11, zzhn.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 10:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzc(i11, (zzdn) zzhn.zzf(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 11:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzg(i11, zzhn.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 12:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzk(i11, zzhn.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 13:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzj(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 14:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzh(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 15:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzh(i11, zzhn.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 16:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzf(i11, zzhn.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 17:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzea.zzc(i11, (zzfz) zzhn.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 18:
                        iZzb2 = zzgr.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 19:
                        iZzb2 = zzgr.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 20:
                        iZzb2 = zzgr.zza(i11, (List<Long>) zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 21:
                        iZzb2 = zzgr.zzb(i11, (List<Long>) zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 22:
                        iZzb2 = zzgr.zze(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 23:
                        iZzb2 = zzgr.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 24:
                        iZzb2 = zzgr.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 25:
                        iZzb2 = zzgr.zzj(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 26:
                        iZzb2 = zzgr.zza(i11, zza(t, j2));
                        i9 += iZzb2;
                        break;
                    case 27:
                        iZzb2 = zzgr.zza(i11, zza(t, j2), zza(i8));
                        i9 += iZzb2;
                        break;
                    case 28:
                        iZzb2 = zzgr.zzb(i11, zza(t, j2));
                        i9 += iZzb2;
                        break;
                    case 29:
                        iZzb2 = zzgr.zzf(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 30:
                        iZzb2 = zzgr.zzd(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 31:
                        iZzb2 = zzgr.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 32:
                        iZzb2 = zzgr.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 33:
                        iZzb2 = zzgr.zzg(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 34:
                        iZzb2 = zzgr.zzc(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 35:
                        iZzi2 = zzgr.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 36:
                        iZzi2 = zzgr.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 37:
                        iZzi2 = zzgr.zza((List<Long>) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 38:
                        iZzi2 = zzgr.zzb((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 39:
                        iZzi2 = zzgr.zze((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 40:
                        iZzi2 = zzgr.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 41:
                        iZzi2 = zzgr.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 42:
                        iZzi2 = zzgr.zzj((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 43:
                        iZzi2 = zzgr.zzf((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 44:
                        iZzi2 = zzgr.zzd((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 45:
                        iZzi2 = zzgr.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 46:
                        iZzi2 = zzgr.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 47:
                        iZzi2 = zzgr.zzg((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 48:
                        iZzi2 = zzgr.zzc((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzea.zze(i11);
                            iZzg2 = zzea.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 49:
                        iZzb2 = zzgr.zzb(i11, (List<zzfz>) zza(t, j2), zza(i8));
                        i9 += iZzb2;
                        break;
                    case 50:
                        iZzb2 = this.zzs.zza(i11, zzhn.zzf(t, j2), zzb(i8));
                        i9 += iZzb2;
                        break;
                    case 51:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzb(i11, 0.0d);
                            i9 += iZzb2;
                        }
                        break;
                    case 52:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzb(i11, 0.0f);
                            i9 += iZzb2;
                        }
                        break;
                    case 53:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzd(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 54:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zze(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 55:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzf(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 56:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzg(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 57:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzi(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 58:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzb(i11, true);
                            i9 += iZzb2;
                        }
                        break;
                    case 59:
                        if (zza(t, i11, i8)) {
                            Object objZzf2 = zzhn.zzf(t, j2);
                            if (objZzf2 instanceof zzdn) {
                                iZzb2 = zzea.zzc(i11, (zzdn) objZzf2);
                            } else {
                                iZzb2 = zzea.zzb(i11, (String) objZzf2);
                            }
                            i9 += iZzb2;
                        }
                        break;
                    case 60:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzgr.zza(i11, zzhn.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 61:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzc(i11, (zzdn) zzhn.zzf(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 62:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzg(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 63:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzk(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 64:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzj(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 65:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzh(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 66:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzh(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 67:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzf(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 68:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzea.zzc(i11, (zzfz) zzhn.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                }
                i8 += 3;
                i4 = 267386880;
            }
            return i9 + zza((zzhh) this.zzq, (Object) t);
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
                        iZzb3 += zzea.zzb(i16, 0.0d);
                    }
                    break;
                case 1:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzea.zzb(i16, 0.0f);
                    }
                    break;
                case 2:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzea.zzd(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 3:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzea.zze(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 4:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzea.zzf(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 5:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzd = zzea.zzg(i16, 0L);
                        iZzb3 += iZzd;
                    }
                    break;
                case 6:
                    i2 = 1;
                    i3 = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzea.zzi(i16, 0);
                    }
                    j = 0;
                    break;
                case 7:
                    if ((i15 & i) != 0) {
                        i2 = 1;
                        iZzb3 += zzea.zzb(i16, true);
                    } else {
                        i2 = 1;
                    }
                    i3 = 0;
                    j = 0;
                    break;
                case 8:
                    if ((i15 & i) != 0) {
                        Object object = unsafe2.getObject(t, j3);
                        if (object instanceof zzdn) {
                            iZzb = zzea.zzc(i16, (zzdn) object);
                        } else {
                            iZzb = zzea.zzb(i16, (String) object);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 9:
                    if ((i15 & i) != 0) {
                        iZzb = zzgr.zza(i16, unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 10:
                    if ((i15 & i) != 0) {
                        iZzb = zzea.zzc(i16, (zzdn) unsafe2.getObject(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 11:
                    if ((i15 & i) != 0) {
                        iZzb = zzea.zzg(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 12:
                    if ((i15 & i) != 0) {
                        iZzb = zzea.zzk(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 13:
                    if ((i15 & i) != 0) {
                        iZzj = zzea.zzj(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 14:
                    if ((i15 & i) != 0) {
                        iZzb = zzea.zzh(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 15:
                    if ((i15 & i) != 0) {
                        iZzb = zzea.zzh(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 16:
                    if ((i15 & i) != 0) {
                        iZzb = zzea.zzf(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 17:
                    if ((i15 & i) != 0) {
                        iZzb = zzea.zzc(i16, (zzfz) unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 18:
                    iZzb = zzgr.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 19:
                    i3 = 0;
                    iZzh = zzgr.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 20:
                    i3 = 0;
                    iZzh = zzgr.zza(i16, (List<Long>) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 21:
                    i3 = 0;
                    iZzh = zzgr.zzb(i16, (List<Long>) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 22:
                    i3 = 0;
                    iZzh = zzgr.zze(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 23:
                    i3 = 0;
                    iZzh = zzgr.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 24:
                    i3 = 0;
                    iZzh = zzgr.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 25:
                    i3 = 0;
                    iZzh = zzgr.zzj(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 26:
                    iZzb = zzgr.zza(i16, (List<?>) unsafe2.getObject(t, j3));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 27:
                    iZzb = zzgr.zza(i16, (List<?>) unsafe2.getObject(t, j3), zza(i14));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 28:
                    iZzb = zzgr.zzb(i16, (List) unsafe2.getObject(t, j3));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 29:
                    iZzb = zzgr.zzf(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 30:
                    i3 = 0;
                    iZzh = zzgr.zzd(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 31:
                    i3 = 0;
                    iZzh = zzgr.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 32:
                    i3 = 0;
                    iZzh = zzgr.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 33:
                    i3 = 0;
                    iZzh = zzgr.zzg(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 34:
                    i3 = 0;
                    iZzh = zzgr.zzc(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 35:
                    iZzi = zzgr.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 36:
                    iZzi = zzgr.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 37:
                    iZzi = zzgr.zza((List<Long>) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 38:
                    iZzi = zzgr.zzb((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 39:
                    iZzi = zzgr.zze((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 40:
                    iZzi = zzgr.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 41:
                    iZzi = zzgr.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 42:
                    iZzi = zzgr.zzj((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 43:
                    iZzi = zzgr.zzf((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 44:
                    iZzi = zzgr.zzd((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 45:
                    iZzi = zzgr.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 46:
                    iZzi = zzgr.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 47:
                    iZzi = zzgr.zzg((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 48:
                    iZzi = zzgr.zzc((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzea.zze(i16);
                        iZzg = zzea.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 49:
                    iZzb = zzgr.zzb(i16, (List<zzfz>) unsafe2.getObject(t, j3), zza(i14));
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
                        iZzb = zzea.zzb(i16, 0.0d);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 52:
                    if (zza(t, i16, i14)) {
                        iZzj = zzea.zzb(i16, 0.0f);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 53:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzd(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 54:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zze(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 55:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzf(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 56:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzg(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 57:
                    if (zza(t, i16, i14)) {
                        iZzj = zzea.zzi(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 58:
                    if (zza(t, i16, i14)) {
                        iZzj = zzea.zzb(i16, true);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 59:
                    if (zza(t, i16, i14)) {
                        Object object2 = unsafe2.getObject(t, j3);
                        if (object2 instanceof zzdn) {
                            iZzb = zzea.zzc(i16, (zzdn) object2);
                        } else {
                            iZzb = zzea.zzb(i16, (String) object2);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 60:
                    if (zza(t, i16, i14)) {
                        iZzb = zzgr.zza(i16, unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 61:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzc(i16, (zzdn) unsafe2.getObject(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 62:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzg(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 63:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzk(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 64:
                    if (zza(t, i16, i14)) {
                        iZzj = zzea.zzj(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 65:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzh(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 66:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzh(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 67:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzf(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 68:
                    if (zza(t, i16, i14)) {
                        iZzb = zzea.zzc(i16, (zzfz) unsafe2.getObject(t, j3), zza(i14));
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
        int iZza2 = iZzb3 + zza((zzhh) this.zzq, (Object) t);
        if (!this.zzh) {
            return iZza2;
        }
        zzej<T> zzejVarZza = this.zzr.zza(t);
        for (int i20 = iZza; i20 < zzejVarZza.zza.zzc(); i20++) {
            Map.Entry entryZzb = zzejVarZza.zza.zzb(i20);
            iZza += zzej.zza((zzel<?>) entryZzb.getKey(), entryZzb.getValue());
        }
        for (Map.Entry entry : zzejVarZza.zza.zzd()) {
            iZza += zzej.zza((zzel<?>) entry.getKey(), entry.getValue());
        }
        return iZza2 + iZza;
    }

    private static <UT, UB> int zza(zzhh<UT, UB> zzhhVar, T t) {
        return zzhhVar.zzd(zzhhVar.zza(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzhn.zzf(obj, j);
    }

    /* JADX WARN: Code duplicated, block: B:178:0x054a  */
    /* JADX WARN: Code duplicated, block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zza(T t, zzib zzibVar) throws IOException {
        Iterator itZzd;
        Map.Entry<?, ?> entry;
        Iterator itZze;
        Map.Entry<?, ?> entry2;
        if (zzibVar.zza() == zzia.zzb) {
            zza(this.zzq, t, zzibVar);
            if (this.zzh) {
                zzej<T> zzejVarZza = this.zzr.zza(t);
                if (zzejVarZza.zza.isEmpty()) {
                    itZze = null;
                    entry2 = null;
                } else {
                    itZze = zzejVarZza.zze();
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
                    this.zzr.zza(zzibVar, entry2);
                    entry2 = itZze.hasNext() ? (Map.Entry) itZze.next() : null;
                }
                switch ((iZzc & 267386880) >>> 20) {
                    case 0:
                        if (zza((Object) t, length)) {
                            zzibVar.zza(i, zzhn.zze(t, iZzc & 1048575));
                        }
                        break;
                    case 1:
                        if (zza((Object) t, length)) {
                            zzibVar.zza(i, zzhn.zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 2:
                        if (zza((Object) t, length)) {
                            zzibVar.zza(i, zzhn.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 3:
                        if (zza((Object) t, length)) {
                            zzibVar.zzc(i, zzhn.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 4:
                        if (zza((Object) t, length)) {
                            zzibVar.zzc(i, zzhn.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 5:
                        if (zza((Object) t, length)) {
                            zzibVar.zzd(i, zzhn.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 6:
                        if (zza((Object) t, length)) {
                            zzibVar.zzd(i, zzhn.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 7:
                        if (zza((Object) t, length)) {
                            zzibVar.zza(i, zzhn.zzc(t, iZzc & 1048575));
                        }
                        break;
                    case 8:
                        if (zza((Object) t, length)) {
                            zza(i, zzhn.zzf(t, iZzc & 1048575), zzibVar);
                        }
                        break;
                    case 9:
                        if (zza((Object) t, length)) {
                            zzibVar.zza(i, zzhn.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                    case 10:
                        if (zza((Object) t, length)) {
                            zzibVar.zza(i, (zzdn) zzhn.zzf(t, iZzc & 1048575));
                        }
                        break;
                    case 11:
                        if (zza((Object) t, length)) {
                            zzibVar.zze(i, zzhn.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 12:
                        if (zza((Object) t, length)) {
                            zzibVar.zzb(i, zzhn.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 13:
                        if (zza((Object) t, length)) {
                            zzibVar.zza(i, zzhn.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 14:
                        if (zza((Object) t, length)) {
                            zzibVar.zzb(i, zzhn.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 15:
                        if (zza((Object) t, length)) {
                            zzibVar.zzf(i, zzhn.zza(t, iZzc & 1048575));
                        }
                        break;
                    case 16:
                        if (zza((Object) t, length)) {
                            zzibVar.zze(i, zzhn.zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 17:
                        if (zza((Object) t, length)) {
                            zzibVar.zzb(i, zzhn.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                    case 18:
                        zzgr.zza(this.zzc[length], (List<Double>) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 19:
                        zzgr.zzb(this.zzc[length], (List<Float>) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 20:
                        zzgr.zzc(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 21:
                        zzgr.zzd(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 22:
                        zzgr.zzh(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 23:
                        zzgr.zzf(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 24:
                        zzgr.zzk(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 25:
                        zzgr.zzn(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 26:
                        zzgr.zza(this.zzc[length], (List<String>) zzhn.zzf(t, iZzc & 1048575), zzibVar);
                        break;
                    case 27:
                        zzgr.zza(this.zzc[length], (List<?>) zzhn.zzf(t, iZzc & 1048575), zzibVar, zza(length));
                        break;
                    case 28:
                        zzgr.zzb(this.zzc[length], (List<zzdn>) zzhn.zzf(t, iZzc & 1048575), zzibVar);
                        break;
                    case 29:
                        zzgr.zzi(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 30:
                        zzgr.zzm(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 31:
                        zzgr.zzl(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 32:
                        zzgr.zzg(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 33:
                        zzgr.zzj(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 34:
                        zzgr.zze(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, false);
                        break;
                    case 35:
                        zzgr.zza(this.zzc[length], (List<Double>) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 36:
                        zzgr.zzb(this.zzc[length], (List<Float>) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 37:
                        zzgr.zzc(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 38:
                        zzgr.zzd(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 39:
                        zzgr.zzh(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 40:
                        zzgr.zzf(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 41:
                        zzgr.zzk(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 42:
                        zzgr.zzn(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 43:
                        zzgr.zzi(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 44:
                        zzgr.zzm(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 45:
                        zzgr.zzl(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 46:
                        zzgr.zzg(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 47:
                        zzgr.zzj(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 48:
                        zzgr.zze(this.zzc[length], (List) zzhn.zzf(t, iZzc & 1048575), zzibVar, true);
                        break;
                    case 49:
                        zzgr.zzb(this.zzc[length], (List<?>) zzhn.zzf(t, iZzc & 1048575), zzibVar, zza(length));
                        break;
                    case 50:
                        zza(zzibVar, i, zzhn.zzf(t, iZzc & 1048575), length);
                        break;
                    case 51:
                        if (zza(t, i, length)) {
                            zzibVar.zza(i, zzb(t, iZzc & 1048575));
                        }
                        break;
                    case 52:
                        if (zza(t, i, length)) {
                            zzibVar.zza(i, zzc(t, iZzc & 1048575));
                        }
                        break;
                    case 53:
                        if (zza(t, i, length)) {
                            zzibVar.zza(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 54:
                        if (zza(t, i, length)) {
                            zzibVar.zzc(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 55:
                        if (zza(t, i, length)) {
                            zzibVar.zzc(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 56:
                        if (zza(t, i, length)) {
                            zzibVar.zzd(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 57:
                        if (zza(t, i, length)) {
                            zzibVar.zzd(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 58:
                        if (zza(t, i, length)) {
                            zzibVar.zza(i, zzf(t, iZzc & 1048575));
                        }
                        break;
                    case 59:
                        if (zza(t, i, length)) {
                            zza(i, zzhn.zzf(t, iZzc & 1048575), zzibVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i, length)) {
                            zzibVar.zza(i, zzhn.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                    case 61:
                        if (zza(t, i, length)) {
                            zzibVar.zza(i, (zzdn) zzhn.zzf(t, iZzc & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i, length)) {
                            zzibVar.zze(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 63:
                        if (zza(t, i, length)) {
                            zzibVar.zzb(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 64:
                        if (zza(t, i, length)) {
                            zzibVar.zza(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 65:
                        if (zza(t, i, length)) {
                            zzibVar.zzb(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 66:
                        if (zza(t, i, length)) {
                            zzibVar.zzf(i, zzd(t, iZzc & 1048575));
                        }
                        break;
                    case 67:
                        if (zza(t, i, length)) {
                            zzibVar.zze(i, zze(t, iZzc & 1048575));
                        }
                        break;
                    case 68:
                        if (zza(t, i, length)) {
                            zzibVar.zzb(i, zzhn.zzf(t, iZzc & 1048575), zza(length));
                        }
                        break;
                }
            }
            while (entry2 != null) {
                this.zzr.zza(zzibVar, entry2);
                entry2 = itZze.hasNext() ? (Map.Entry) itZze.next() : null;
            }
            return;
        }
        if (this.zzj) {
            if (this.zzh) {
                zzej<T> zzejVarZza2 = this.zzr.zza(t);
                if (zzejVarZza2.zza.isEmpty()) {
                    itZzd = null;
                    entry = null;
                } else {
                    itZzd = zzejVarZza2.zzd();
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
                    this.zzr.zza(zzibVar, entry);
                    entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
                }
                switch ((iZzc2 & 267386880) >>> 20) {
                    case 0:
                        if (zza((Object) t, i2)) {
                            zzibVar.zza(i3, zzhn.zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 1:
                        if (zza((Object) t, i2)) {
                            zzibVar.zza(i3, zzhn.zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 2:
                        if (zza((Object) t, i2)) {
                            zzibVar.zza(i3, zzhn.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 3:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzc(i3, zzhn.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 4:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzc(i3, zzhn.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 5:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzd(i3, zzhn.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 6:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzd(i3, zzhn.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 7:
                        if (zza((Object) t, i2)) {
                            zzibVar.zza(i3, zzhn.zzc(t, iZzc2 & 1048575));
                        }
                        break;
                    case 8:
                        if (zza((Object) t, i2)) {
                            zza(i3, zzhn.zzf(t, iZzc2 & 1048575), zzibVar);
                        }
                        break;
                    case 9:
                        if (zza((Object) t, i2)) {
                            zzibVar.zza(i3, zzhn.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                    case 10:
                        if (zza((Object) t, i2)) {
                            zzibVar.zza(i3, (zzdn) zzhn.zzf(t, iZzc2 & 1048575));
                        }
                        break;
                    case 11:
                        if (zza((Object) t, i2)) {
                            zzibVar.zze(i3, zzhn.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 12:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzb(i3, zzhn.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 13:
                        if (zza((Object) t, i2)) {
                            zzibVar.zza(i3, zzhn.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 14:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzb(i3, zzhn.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 15:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzf(i3, zzhn.zza(t, iZzc2 & 1048575));
                        }
                        break;
                    case 16:
                        if (zza((Object) t, i2)) {
                            zzibVar.zze(i3, zzhn.zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 17:
                        if (zza((Object) t, i2)) {
                            zzibVar.zzb(i3, zzhn.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                    case 18:
                        zzgr.zza(this.zzc[i2], (List<Double>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 19:
                        zzgr.zzb(this.zzc[i2], (List<Float>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 20:
                        zzgr.zzc(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 21:
                        zzgr.zzd(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 22:
                        zzgr.zzh(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 23:
                        zzgr.zzf(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 24:
                        zzgr.zzk(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 25:
                        zzgr.zzn(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 26:
                        zzgr.zza(this.zzc[i2], (List<String>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar);
                        break;
                    case 27:
                        zzgr.zza(this.zzc[i2], (List<?>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, zza(i2));
                        break;
                    case 28:
                        zzgr.zzb(this.zzc[i2], (List<zzdn>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar);
                        break;
                    case 29:
                        zzgr.zzi(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 30:
                        zzgr.zzm(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 31:
                        zzgr.zzl(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 32:
                        zzgr.zzg(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 33:
                        zzgr.zzj(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 34:
                        zzgr.zze(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, false);
                        break;
                    case 35:
                        zzgr.zza(this.zzc[i2], (List<Double>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 36:
                        zzgr.zzb(this.zzc[i2], (List<Float>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 37:
                        zzgr.zzc(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 38:
                        zzgr.zzd(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 39:
                        zzgr.zzh(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 40:
                        zzgr.zzf(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 41:
                        zzgr.zzk(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 42:
                        zzgr.zzn(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 43:
                        zzgr.zzi(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 44:
                        zzgr.zzm(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 45:
                        zzgr.zzl(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 46:
                        zzgr.zzg(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 47:
                        zzgr.zzj(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 48:
                        zzgr.zze(this.zzc[i2], (List) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, true);
                        break;
                    case 49:
                        zzgr.zzb(this.zzc[i2], (List<?>) zzhn.zzf(t, iZzc2 & 1048575), zzibVar, zza(i2));
                        break;
                    case 50:
                        zza(zzibVar, i3, zzhn.zzf(t, iZzc2 & 1048575), i2);
                        break;
                    case 51:
                        if (zza(t, i3, i2)) {
                            zzibVar.zza(i3, zzb(t, iZzc2 & 1048575));
                        }
                        break;
                    case 52:
                        if (zza(t, i3, i2)) {
                            zzibVar.zza(i3, zzc(t, iZzc2 & 1048575));
                        }
                        break;
                    case 53:
                        if (zza(t, i3, i2)) {
                            zzibVar.zza(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 54:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzc(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 55:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzc(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 56:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzd(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 57:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzd(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 58:
                        if (zza(t, i3, i2)) {
                            zzibVar.zza(i3, zzf(t, iZzc2 & 1048575));
                        }
                        break;
                    case 59:
                        if (zza(t, i3, i2)) {
                            zza(i3, zzhn.zzf(t, iZzc2 & 1048575), zzibVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i3, i2)) {
                            zzibVar.zza(i3, zzhn.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                    case 61:
                        if (zza(t, i3, i2)) {
                            zzibVar.zza(i3, (zzdn) zzhn.zzf(t, iZzc2 & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i3, i2)) {
                            zzibVar.zze(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 63:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzb(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 64:
                        if (zza(t, i3, i2)) {
                            zzibVar.zza(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 65:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzb(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 66:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzf(i3, zzd(t, iZzc2 & 1048575));
                        }
                        break;
                    case 67:
                        if (zza(t, i3, i2)) {
                            zzibVar.zze(i3, zze(t, iZzc2 & 1048575));
                        }
                        break;
                    case 68:
                        if (zza(t, i3, i2)) {
                            zzibVar.zzb(i3, zzhn.zzf(t, iZzc2 & 1048575), zza(i2));
                        }
                        break;
                }
            }
            while (entry != null) {
                this.zzr.zza(zzibVar, entry);
                entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
            }
            zza(this.zzq, t, zzibVar);
            return;
        }
        zzb((Object) t, zzibVar);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0023  */
    private final void zzb(T t, zzib zzibVar) throws IOException {
        Iterator itZzd;
        Map.Entry<?, ?> entry;
        int i;
        boolean z;
        if (this.zzh) {
            zzej<T> zzejVarZza = this.zzr.zza(t);
            if (zzejVarZza.zza.isEmpty()) {
                itZzd = null;
                entry = null;
            } else {
                itZzd = zzejVarZza.zzd();
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
            if (this.zzj || i6 > 17) {
                i = 0;
            } else {
                int i7 = iArr[i4 + 2];
                int i8 = i7 & 1048575;
                if (i8 != i2) {
                    i3 = unsafe.getInt(t, i8);
                    i2 = i8;
                }
                i = 1 << (i7 >>> 20);
            }
            while (entry != null && this.zzr.zza(entry) <= i5) {
                this.zzr.zza(zzibVar, entry);
                entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
            }
            long j = iZzc & 1048575;
            switch (i6) {
                case 0:
                    if ((i & i3) != 0) {
                        zzibVar.zza(i5, zzhn.zze(t, j));
                    }
                    break;
                case 1:
                    if ((i & i3) != 0) {
                        zzibVar.zza(i5, zzhn.zzd(t, j));
                    }
                    break;
                case 2:
                    if ((i & i3) != 0) {
                        zzibVar.zza(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 3:
                    if ((i & i3) != 0) {
                        zzibVar.zzc(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 4:
                    if ((i & i3) != 0) {
                        zzibVar.zzc(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 5:
                    if ((i & i3) != 0) {
                        zzibVar.zzd(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 6:
                    if ((i & i3) != 0) {
                        zzibVar.zzd(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 7:
                    if ((i & i3) != 0) {
                        zzibVar.zza(i5, zzhn.zzc(t, j));
                    }
                    break;
                case 8:
                    if ((i & i3) != 0) {
                        zza(i5, unsafe.getObject(t, j), zzibVar);
                    }
                    break;
                case 9:
                    if ((i & i3) != 0) {
                        zzibVar.zza(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 10:
                    if ((i & i3) != 0) {
                        zzibVar.zza(i5, (zzdn) unsafe.getObject(t, j));
                    }
                    break;
                case 11:
                    if ((i & i3) != 0) {
                        zzibVar.zze(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 12:
                    if ((i & i3) != 0) {
                        zzibVar.zzb(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 13:
                    if ((i & i3) != 0) {
                        zzibVar.zza(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 14:
                    if ((i & i3) != 0) {
                        zzibVar.zzb(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 15:
                    if ((i & i3) != 0) {
                        zzibVar.zzf(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 16:
                    if ((i & i3) != 0) {
                        zzibVar.zze(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 17:
                    if ((i & i3) != 0) {
                        zzibVar.zzb(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 18:
                    z = false;
                    zzgr.zza(this.zzc[i4], (List<Double>) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 19:
                    z = false;
                    zzgr.zzb(this.zzc[i4], (List<Float>) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 20:
                    z = false;
                    zzgr.zzc(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 21:
                    z = false;
                    zzgr.zzd(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 22:
                    z = false;
                    zzgr.zzh(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 23:
                    z = false;
                    zzgr.zzf(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 24:
                    z = false;
                    zzgr.zzk(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 25:
                    z = false;
                    zzgr.zzn(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 26:
                    zzgr.zza(this.zzc[i4], (List<String>) unsafe.getObject(t, j), zzibVar);
                    break;
                case 27:
                    zzgr.zza(this.zzc[i4], (List<?>) unsafe.getObject(t, j), zzibVar, zza(i4));
                    break;
                case 28:
                    zzgr.zzb(this.zzc[i4], (List<zzdn>) unsafe.getObject(t, j), zzibVar);
                    break;
                case 29:
                    z = false;
                    zzgr.zzi(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 30:
                    z = false;
                    zzgr.zzm(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 31:
                    z = false;
                    zzgr.zzl(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 32:
                    z = false;
                    zzgr.zzg(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 33:
                    z = false;
                    zzgr.zzj(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 34:
                    z = false;
                    zzgr.zze(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, false);
                    break;
                case 35:
                    zzgr.zza(this.zzc[i4], (List<Double>) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 36:
                    zzgr.zzb(this.zzc[i4], (List<Float>) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 37:
                    zzgr.zzc(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 38:
                    zzgr.zzd(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 39:
                    zzgr.zzh(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 40:
                    zzgr.zzf(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 41:
                    zzgr.zzk(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 42:
                    zzgr.zzn(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 43:
                    zzgr.zzi(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 44:
                    zzgr.zzm(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 45:
                    zzgr.zzl(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 46:
                    zzgr.zzg(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 47:
                    zzgr.zzj(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 48:
                    zzgr.zze(this.zzc[i4], (List) unsafe.getObject(t, j), zzibVar, true);
                    break;
                case 49:
                    zzgr.zzb(this.zzc[i4], (List<?>) unsafe.getObject(t, j), zzibVar, zza(i4));
                    break;
                case 50:
                    zza(zzibVar, i5, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zza(t, i5, i4)) {
                        zzibVar.zza(i5, zzb(t, j));
                    }
                    break;
                case 52:
                    if (zza(t, i5, i4)) {
                        zzibVar.zza(i5, zzc(t, j));
                    }
                    break;
                case 53:
                    if (zza(t, i5, i4)) {
                        zzibVar.zza(i5, zze(t, j));
                    }
                    break;
                case 54:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzc(i5, zze(t, j));
                    }
                    break;
                case 55:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzc(i5, zzd(t, j));
                    }
                    break;
                case 56:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzd(i5, zze(t, j));
                    }
                    break;
                case 57:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzd(i5, zzd(t, j));
                    }
                    break;
                case 58:
                    if (zza(t, i5, i4)) {
                        zzibVar.zza(i5, zzf(t, j));
                    }
                    break;
                case 59:
                    if (zza(t, i5, i4)) {
                        zza(i5, unsafe.getObject(t, j), zzibVar);
                    }
                    break;
                case 60:
                    if (zza(t, i5, i4)) {
                        zzibVar.zza(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 61:
                    if (zza(t, i5, i4)) {
                        zzibVar.zza(i5, (zzdn) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zza(t, i5, i4)) {
                        zzibVar.zze(i5, zzd(t, j));
                    }
                    break;
                case 63:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzb(i5, zzd(t, j));
                    }
                    break;
                case 64:
                    if (zza(t, i5, i4)) {
                        zzibVar.zza(i5, zzd(t, j));
                    }
                    break;
                case 65:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzb(i5, zze(t, j));
                    }
                    break;
                case 66:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzf(i5, zzd(t, j));
                    }
                    break;
                case 67:
                    if (zza(t, i5, i4)) {
                        zzibVar.zze(i5, zze(t, j));
                    }
                    break;
                case 68:
                    if (zza(t, i5, i4)) {
                        zzibVar.zzb(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                default:
                    break;
            }
        }
        while (entry != null) {
            this.zzr.zza(zzibVar, entry);
            entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
        }
        zza(this.zzq, t, zzibVar);
    }

    private final <K, V> void zza(zzib zzibVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzibVar.zza(i, this.zzs.zzc(zzb(i2)), this.zzs.zza(obj));
        }
    }

    private static <UT, UB> void zza(zzhh<UT, UB> zzhhVar, T t, zzib zzibVar) throws IOException {
        zzhhVar.zza(zzhhVar.zza(t), zzibVar);
    }

    private final zzgp zza(int i) {
        int i2 = (i / 3) << 1;
        zzgp zzgpVar = (zzgp) this.zzd[i2];
        if (zzgpVar != null) {
            return zzgpVar;
        }
        zzgp<T> zzgpVarZza = zzgk.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zzgpVarZza;
        return zzgpVarZza;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zzb(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long jZzc = zzc(this.zzl[i2]) & 1048575;
            Object objZzf = zzhn.zzf(t, jZzc);
            if (objZzf != null) {
                zzhn.zza(t, jZzc, this.zzs.zzb(objZzf));
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
    /* JADX WARN: Type inference failed for: r1v23, types: [com.google.android.gms.internal.mlkit_language_id.zzgp] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.mlkit_language_id.zzgp] */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final boolean zzc(T t) {
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
                list = (List) zzhn.zzf(t, iZzc & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    Zza = zza(i7);
                    for (i3 = 0; i3 < list.size(); i3++) {
                        if (!Zza.zzc(list.get(i3))) {
                            return false;
                        }
                    }
                }
            } else if (i12 == 60 || i12 == 68) {
                if (zza(t, i8, i7) && !zza(t, iZzc, zza(i7))) {
                    return false;
                }
            } else if (i12 == 49) {
                list = (List) zzhn.zzf(t, iZzc & 1048575);
                if (list.isEmpty()) {
                    Zza = zza(i7);
                    while (i3 < list.size()) {
                        if (!Zza.zzc(list.get(i3))) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            } else if (i12 != 50) {
                continue;
            } else {
                Map<?, ?> mapZza = this.zzs.zza(zzhn.zzf(t, iZzc & 1048575));
                if (mapZza.isEmpty()) {
                    continue;
                } else if (this.zzs.zzc(zzb(i7)).zzb.zza() == zzhy.MESSAGE) {
                    ?? Zza2 = 0;
                    for (Object obj : mapZza.values()) {
                        if (Zza2 == 0) {
                            Zza2 = Zza2;
                            Zza2 = zzgk.zza().zza((Class) obj.getClass());
                        }
                        Zza2 = Zza2;
                        if (!Zza2.zzc(obj)) {
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
    private static boolean zza(Object obj, int i, zzgp zzgpVar) {
        return zzgpVar.zzc(zzhn.zzf(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzib zzibVar) throws IOException {
        if (obj instanceof String) {
            zzibVar.zza(i, (String) obj);
        } else {
            zzibVar.zza(i, (zzdn) obj);
        }
    }

    private final int zzc(int i) {
        return this.zzc[i + 1];
    }

    private final int zzd(int i) {
        return this.zzc[i + 2];
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzhn.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzhn.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzhn.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzhn.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzhn.zzf(t, j)).booleanValue();
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
            return (zzhn.zza(t, j) & (1 << (iZzd >>> 20))) != 0;
        }
        int iZzc = zzc(i);
        long j2 = iZzc & 1048575;
        switch ((iZzc & 267386880) >>> 20) {
            case 0:
                return zzhn.zze(t, j2) != 0.0d;
            case 1:
                return zzhn.zzd(t, j2) != 0.0f;
            case 2:
                return zzhn.zzb(t, j2) != 0;
            case 3:
                return zzhn.zzb(t, j2) != 0;
            case 4:
                return zzhn.zza(t, j2) != 0;
            case 5:
                return zzhn.zzb(t, j2) != 0;
            case 6:
                return zzhn.zza(t, j2) != 0;
            case 7:
                return zzhn.zzc(t, j2);
            case 8:
                Object objZzf = zzhn.zzf(t, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzdn) {
                    return !zzdn.zza.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzhn.zzf(t, j2) != null;
            case 10:
                return !zzdn.zza.equals(zzhn.zzf(t, j2));
            case 11:
                return zzhn.zza(t, j2) != 0;
            case 12:
                return zzhn.zza(t, j2) != 0;
            case 13:
                return zzhn.zza(t, j2) != 0;
            case 14:
                return zzhn.zzb(t, j2) != 0;
            case 15:
                return zzhn.zza(t, j2) != 0;
            case 16:
                return zzhn.zzb(t, j2) != 0;
            case 17:
                return zzhn.zzf(t, j2) != null;
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
        zzhn.zza((Object) t, j, (1 << (iZzd >>> 20)) | zzhn.zza(t, j));
    }

    private final boolean zza(T t, int i, int i2) {
        return zzhn.zza(t, (long) (zzd(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzhn.zza((Object) t, zzd(i2) & 1048575, i);
    }
}
