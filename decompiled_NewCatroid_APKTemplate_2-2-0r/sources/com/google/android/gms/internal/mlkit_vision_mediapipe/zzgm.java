package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgm<T> implements zzgx<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzhv.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzgi zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzgq zzo;
    private final zzfs zzp;
    private final zzhp<?, ?> zzq;
    private final zzeq<?> zzr;
    private final zzgb zzs;

    private zzgm(int[] iArr, Object[] objArr, int i, int i2, zzgi zzgiVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzgq zzgqVar, zzfs zzfsVar, zzhp<?, ?> zzhpVar, zzeq<?> zzeqVar, zzgb zzgbVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzgiVar instanceof zzfb;
        this.zzj = z;
        this.zzh = zzeqVar != null && zzeqVar.zza(zzgiVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzgqVar;
        this.zzp = zzfsVar;
        this.zzq = zzhpVar;
        this.zzr = zzeqVar;
        this.zzg = zzgiVar;
        this.zzs = zzgbVar;
    }

    /* JADX WARN: Code duplicated, block: B:163:0x033a  */
    /* JADX WARN: Code duplicated, block: B:178:0x0388  */
    /* JADX WARN: Code duplicated, block: B:181:0x0395  */
    static <T> zzgm<T> zza(Class<T> cls, zzgg zzggVar, zzgq zzgqVar, zzfs zzfsVar, zzhp<?, ?> zzhpVar, zzeq<?> zzeqVar, zzgb zzgbVar) {
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
        zzgy zzgyVar;
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
        if (zzggVar instanceof zzgy) {
            zzgy zzgyVar2 = (zzgy) zzggVar;
            int i24 = 0;
            boolean z = zzgyVar2.zza() == zzgt.zzb;
            String strZzd = zzgyVar2.zzd();
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
            Object[] objArrZze = zzgyVar2.zze();
            Class<?> cls2 = zzgyVar2.zzc().getClass();
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
                    zzgyVar = zzgyVar2;
                    strZzd = strZzd;
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza3);
                    i17 = 0;
                } else {
                    int i80 = i56 + 1;
                    Field fieldZza4 = zza(cls2, (String) objArrZze[i56]);
                    zzgyVar = zzgyVar2;
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
                zzgyVar2 = zzgyVar;
            }
            return new zzgm<>(iArr2, objArr, iCharAt, iCharAt2, zzgyVar2.zzc(), z, false, iArr, iCharAt5, i55, zzgqVar, zzfsVar, zzhpVar, zzeqVar, zzgbVar);
        }
        ((zzhm) zzggVar).zza();
        int i90 = zzgt.zzb;
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final T zza() {
        return (T) this.zzo.zza(this.zzg);
    }

    /* JADX WARN: Code duplicated, block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final boolean zza(T t, T t2) {
        int length = this.zzc.length;
        int i = 0;
        while (true) {
            boolean zZza = true;
            if (i < length) {
                int iZzd = zzd(i);
                long j = iZzd & 1048575;
                switch ((iZzd & 267386880) >>> 20) {
                    case 0:
                        if (!zzc(t, t2, i) || Double.doubleToLongBits(zzhv.zze(t, j)) != Double.doubleToLongBits(zzhv.zze(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 1:
                        if (!zzc(t, t2, i) || Float.floatToIntBits(zzhv.zzd(t, j)) != Float.floatToIntBits(zzhv.zzd(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 2:
                        if (!zzc(t, t2, i) || zzhv.zzb(t, j) != zzhv.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 3:
                        if (!zzc(t, t2, i) || zzhv.zzb(t, j) != zzhv.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 4:
                        if (!zzc(t, t2, i) || zzhv.zza(t, j) != zzhv.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 5:
                        if (!zzc(t, t2, i) || zzhv.zzb(t, j) != zzhv.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 6:
                        if (!zzc(t, t2, i) || zzhv.zza(t, j) != zzhv.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 7:
                        if (!zzc(t, t2, i) || zzhv.zzc(t, j) != zzhv.zzc(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 8:
                        if (!zzc(t, t2, i) || !zzgz.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 9:
                        if (!zzc(t, t2, i) || !zzgz.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 10:
                        if (!zzc(t, t2, i) || !zzgz.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j))) {
                            zZza = false;
                        }
                        break;
                    case 11:
                        if (!zzc(t, t2, i) || zzhv.zza(t, j) != zzhv.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 12:
                        if (!zzc(t, t2, i) || zzhv.zza(t, j) != zzhv.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 13:
                        if (!zzc(t, t2, i) || zzhv.zza(t, j) != zzhv.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 14:
                        if (!zzc(t, t2, i) || zzhv.zzb(t, j) != zzhv.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 15:
                        if (!zzc(t, t2, i) || zzhv.zza(t, j) != zzhv.zza(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 16:
                        if (!zzc(t, t2, i) || zzhv.zzb(t, j) != zzhv.zzb(t2, j)) {
                            zZza = false;
                        }
                        break;
                    case 17:
                        if (!zzc(t, t2, i) || !zzgz.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j))) {
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
                        zZza = zzgz.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j));
                        break;
                    case 50:
                        zZza = zzgz.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j));
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
                        long jZze = zze(i) & 1048575;
                        if (zzhv.zza(t, jZze) != zzhv.zza(t2, jZze) || !zzgz.zza(zzhv.zzf(t, j), zzhv.zzf(t2, j))) {
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final int zza(T t) {
        int i;
        int iZza;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzd = zzd(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzd;
            int iHashCode = 37;
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZza = zzfe.zza(Double.doubleToLongBits(zzhv.zze(t, j)));
                    i2 = i + iZza;
                    break;
                case 1:
                    i = i2 * 53;
                    iZza = Float.floatToIntBits(zzhv.zzd(t, j));
                    i2 = i + iZza;
                    break;
                case 2:
                    i = i2 * 53;
                    iZza = zzfe.zza(zzhv.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 3:
                    i = i2 * 53;
                    iZza = zzfe.zza(zzhv.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 4:
                    i = i2 * 53;
                    iZza = zzhv.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 5:
                    i = i2 * 53;
                    iZza = zzfe.zza(zzhv.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 6:
                    i = i2 * 53;
                    iZza = zzhv.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 7:
                    i = i2 * 53;
                    iZza = zzfe.zza(zzhv.zzc(t, j));
                    i2 = i + iZza;
                    break;
                case 8:
                    i = i2 * 53;
                    iZza = ((String) zzhv.zzf(t, j)).hashCode();
                    i2 = i + iZza;
                    break;
                case 9:
                    Object objZzf = zzhv.zzf(t, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZza = zzhv.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 11:
                    i = i2 * 53;
                    iZza = zzhv.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 12:
                    i = i2 * 53;
                    iZza = zzhv.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 13:
                    i = i2 * 53;
                    iZza = zzhv.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 14:
                    i = i2 * 53;
                    iZza = zzfe.zza(zzhv.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 15:
                    i = i2 * 53;
                    iZza = zzhv.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 16:
                    i = i2 * 53;
                    iZza = zzfe.zza(zzhv.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 17:
                    Object objZzf2 = zzhv.zzf(t, j);
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
                    iZza = zzhv.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 50:
                    i = i2 * 53;
                    iZza = zzhv.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 51:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzfe.zza(Double.doubleToLongBits(zzb(t, j)));
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
                        iZza = zzfe.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 54:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzfe.zza(zze(t, j));
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
                        iZza = zzfe.zza(zze(t, j));
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
                        iZza = zzfe.zza(zzf(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 59:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = ((String) zzhv.zzf(t, j)).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 60:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzhv.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 61:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzhv.zzf(t, j).hashCode();
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
                        iZza = zzfe.zza(zze(t, j));
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
                        iZza = zzfe.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 68:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzhv.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzq.zza(t).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzr.zza(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zzb(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzd = zzd(i);
            long j = 1048575 & iZzd;
            int i2 = this.zzc[i];
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    if (zza((Object) t2, i)) {
                        zzhv.zza(t, j, zzhv.zze(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 1:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzd(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 2:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 3:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 4:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 5:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 6:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 7:
                    if (zza((Object) t2, i)) {
                        zzhv.zza(t, j, zzhv.zzc(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 8:
                    if (zza((Object) t2, i)) {
                        zzhv.zza(t, j, zzhv.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((Object) t2, i)) {
                        zzhv.zza(t, j, zzhv.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 11:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 12:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 13:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 14:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 15:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 16:
                    if (zza((Object) t2, i)) {
                        zzhv.zza((Object) t, j, zzhv.zzb(t2, j));
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
                    zzgz.zza(this.zzs, t, t2, j);
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
                        zzhv.zza(t, j, zzhv.zzf(t2, j));
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
                        zzhv.zza(t, j, zzhv.zzf(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzgz.zza(this.zzq, t, t2);
        if (this.zzh) {
            zzgz.zza(this.zzr, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzd = zzd(i) & 1048575;
        if (zza((Object) t2, i)) {
            Object objZzf = zzhv.zzf(t, jZzd);
            Object objZzf2 = zzhv.zzf(t2, jZzd);
            if (objZzf != null && objZzf2 != null) {
                zzhv.zza(t, jZzd, zzfe.zza(objZzf, objZzf2));
                zzb((Object) t, i);
            } else if (objZzf2 != null) {
                zzhv.zza(t, jZzd, objZzf2);
                zzb((Object) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzd = zzd(i);
        int i2 = this.zzc[i];
        long j = iZzd & 1048575;
        if (zza(t2, i2, i)) {
            Object objZzf = zzhv.zzf(t, j);
            Object objZzf2 = zzhv.zzf(t2, j);
            if (objZzf != null && objZzf2 != null) {
                zzhv.zza(t, j, zzfe.zza(objZzf, objZzf2));
                zzb(t, i2, i);
            } else if (objZzf2 != null) {
                zzhv.zza(t, j, objZzf2);
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
    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
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
                int iZzd2 = zzd(i8);
                int i10 = (iZzd2 & i4) >>> 20;
                int i11 = this.zzc[i8];
                long j2 = iZzd2 & 1048575;
                if (i10 >= zzev.DOUBLE_LIST_PACKED.zza() && i10 <= zzev.SINT64_LIST_PACKED.zza()) {
                    int i12 = this.zzc[i8 + 2];
                }
                switch (i10) {
                    case 0:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzb(i11, 0.0d);
                            i9 += iZzb2;
                        }
                        break;
                    case 1:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzb(i11, 0.0f);
                            i9 += iZzb2;
                        }
                        break;
                    case 2:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzd(i11, zzhv.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 3:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zze(i11, zzhv.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 4:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzf(i11, zzhv.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 5:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzg(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 6:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzi(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 7:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzb(i11, true);
                            i9 += iZzb2;
                        }
                        break;
                    case 8:
                        if (zza((Object) t, i8)) {
                            Object objZzf = zzhv.zzf(t, j2);
                            if (objZzf instanceof zzdu) {
                                iZzb2 = zzel.zzc(i11, (zzdu) objZzf);
                            } else {
                                iZzb2 = zzel.zzb(i11, (String) objZzf);
                            }
                            i9 += iZzb2;
                        }
                        break;
                    case 9:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzgz.zza(i11, zzhv.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 10:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzc(i11, (zzdu) zzhv.zzf(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 11:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzg(i11, zzhv.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 12:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzk(i11, zzhv.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 13:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzj(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 14:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzh(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 15:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzh(i11, zzhv.zza(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 16:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzf(i11, zzhv.zzb(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 17:
                        if (zza((Object) t, i8)) {
                            iZzb2 = zzel.zzc(i11, (zzgi) zzhv.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 18:
                        iZzb2 = zzgz.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 19:
                        iZzb2 = zzgz.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 20:
                        iZzb2 = zzgz.zza(i11, (List<Long>) zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 21:
                        iZzb2 = zzgz.zzb(i11, (List<Long>) zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 22:
                        iZzb2 = zzgz.zze(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 23:
                        iZzb2 = zzgz.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 24:
                        iZzb2 = zzgz.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 25:
                        iZzb2 = zzgz.zzj(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 26:
                        iZzb2 = zzgz.zza(i11, zza(t, j2));
                        i9 += iZzb2;
                        break;
                    case 27:
                        iZzb2 = zzgz.zza(i11, zza(t, j2), zza(i8));
                        i9 += iZzb2;
                        break;
                    case 28:
                        iZzb2 = zzgz.zzb(i11, zza(t, j2));
                        i9 += iZzb2;
                        break;
                    case 29:
                        iZzb2 = zzgz.zzf(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 30:
                        iZzb2 = zzgz.zzd(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 31:
                        iZzb2 = zzgz.zzh(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 32:
                        iZzb2 = zzgz.zzi(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 33:
                        iZzb2 = zzgz.zzg(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 34:
                        iZzb2 = zzgz.zzc(i11, zza(t, j2), false);
                        i9 += iZzb2;
                        break;
                    case 35:
                        iZzi2 = zzgz.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 36:
                        iZzi2 = zzgz.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 37:
                        iZzi2 = zzgz.zza((List<Long>) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 38:
                        iZzi2 = zzgz.zzb((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 39:
                        iZzi2 = zzgz.zze((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 40:
                        iZzi2 = zzgz.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 41:
                        iZzi2 = zzgz.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 42:
                        iZzi2 = zzgz.zzj((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 43:
                        iZzi2 = zzgz.zzf((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 44:
                        iZzi2 = zzgz.zzd((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 45:
                        iZzi2 = zzgz.zzh((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 46:
                        iZzi2 = zzgz.zzi((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 47:
                        iZzi2 = zzgz.zzg((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 48:
                        iZzi2 = zzgz.zzc((List) unsafe.getObject(t, j2));
                        if (iZzi2 > 0) {
                            iZze2 = zzel.zze(i11);
                            iZzg2 = zzel.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i9 += iZzb2;
                        }
                        break;
                    case 49:
                        iZzb2 = zzgz.zzb(i11, (List<zzgi>) zza(t, j2), zza(i8));
                        i9 += iZzb2;
                        break;
                    case 50:
                        iZzb2 = this.zzs.zza(i11, zzhv.zzf(t, j2), zzb(i8));
                        i9 += iZzb2;
                        break;
                    case 51:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzb(i11, 0.0d);
                            i9 += iZzb2;
                        }
                        break;
                    case 52:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzb(i11, 0.0f);
                            i9 += iZzb2;
                        }
                        break;
                    case 53:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzd(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 54:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zze(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 55:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzf(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 56:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzg(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 57:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzi(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 58:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzb(i11, true);
                            i9 += iZzb2;
                        }
                        break;
                    case 59:
                        if (zza(t, i11, i8)) {
                            Object objZzf2 = zzhv.zzf(t, j2);
                            if (objZzf2 instanceof zzdu) {
                                iZzb2 = zzel.zzc(i11, (zzdu) objZzf2);
                            } else {
                                iZzb2 = zzel.zzb(i11, (String) objZzf2);
                            }
                            i9 += iZzb2;
                        }
                        break;
                    case 60:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzgz.zza(i11, zzhv.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                    case 61:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzc(i11, (zzdu) zzhv.zzf(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 62:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzg(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 63:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzk(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 64:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzj(i11, 0);
                            i9 += iZzb2;
                        }
                        break;
                    case 65:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzh(i11, 0L);
                            i9 += iZzb2;
                        }
                        break;
                    case 66:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzh(i11, zzd(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 67:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzf(i11, zze(t, j2));
                            i9 += iZzb2;
                        }
                        break;
                    case 68:
                        if (zza(t, i11, i8)) {
                            iZzb2 = zzel.zzc(i11, (zzgi) zzhv.zzf(t, j2), zza(i8));
                            i9 += iZzb2;
                        }
                        break;
                }
                i8 += 3;
                i4 = 267386880;
            }
            return i9 + zza((zzhp) this.zzq, (Object) t);
        }
        Unsafe unsafe2 = zzb;
        int i13 = 1048575;
        int i14 = 0;
        int iZzb3 = 0;
        int i15 = 0;
        while (i14 < this.zzc.length) {
            int iZzd3 = zzd(i14);
            int[] iArr = this.zzc;
            int i16 = iArr[i14];
            int i17 = (iZzd3 & 267386880) >>> 20;
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
            long j3 = iZzd3 & i5;
            switch (i17) {
                case 0:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzel.zzb(i16, 0.0d);
                    }
                    break;
                case 1:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzel.zzb(i16, 0.0f);
                    }
                    break;
                case 2:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzel.zzd(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 3:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzel.zze(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 4:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i & i15) != 0) {
                        iZzd = zzel.zzf(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzd;
                    }
                    break;
                case 5:
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    if ((i15 & i) != 0) {
                        iZzd = zzel.zzg(i16, 0L);
                        iZzb3 += iZzd;
                    }
                    break;
                case 6:
                    i2 = 1;
                    i3 = 0;
                    if ((i15 & i) != 0) {
                        iZzb3 += zzel.zzi(i16, 0);
                    }
                    j = 0;
                    break;
                case 7:
                    if ((i15 & i) != 0) {
                        i2 = 1;
                        iZzb3 += zzel.zzb(i16, true);
                    } else {
                        i2 = 1;
                    }
                    i3 = 0;
                    j = 0;
                    break;
                case 8:
                    if ((i15 & i) != 0) {
                        Object object = unsafe2.getObject(t, j3);
                        if (object instanceof zzdu) {
                            iZzb = zzel.zzc(i16, (zzdu) object);
                        } else {
                            iZzb = zzel.zzb(i16, (String) object);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 9:
                    if ((i15 & i) != 0) {
                        iZzb = zzgz.zza(i16, unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 10:
                    if ((i15 & i) != 0) {
                        iZzb = zzel.zzc(i16, (zzdu) unsafe2.getObject(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 11:
                    if ((i15 & i) != 0) {
                        iZzb = zzel.zzg(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 12:
                    if ((i15 & i) != 0) {
                        iZzb = zzel.zzk(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 13:
                    if ((i15 & i) != 0) {
                        iZzj = zzel.zzj(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 14:
                    if ((i15 & i) != 0) {
                        iZzb = zzel.zzh(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 15:
                    if ((i15 & i) != 0) {
                        iZzb = zzel.zzh(i16, unsafe2.getInt(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 16:
                    if ((i15 & i) != 0) {
                        iZzb = zzel.zzf(i16, unsafe2.getLong(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 17:
                    if ((i15 & i) != 0) {
                        iZzb = zzel.zzc(i16, (zzgi) unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 18:
                    iZzb = zzgz.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 19:
                    i3 = 0;
                    iZzh = zzgz.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 20:
                    i3 = 0;
                    iZzh = zzgz.zza(i16, (List<Long>) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 21:
                    i3 = 0;
                    iZzh = zzgz.zzb(i16, (List<Long>) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 22:
                    i3 = 0;
                    iZzh = zzgz.zze(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 23:
                    i3 = 0;
                    iZzh = zzgz.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 24:
                    i3 = 0;
                    iZzh = zzgz.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 25:
                    i3 = 0;
                    iZzh = zzgz.zzj(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 26:
                    iZzb = zzgz.zza(i16, (List<?>) unsafe2.getObject(t, j3));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 27:
                    iZzb = zzgz.zza(i16, (List<?>) unsafe2.getObject(t, j3), zza(i14));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 28:
                    iZzb = zzgz.zzb(i16, (List) unsafe2.getObject(t, j3));
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 29:
                    iZzb = zzgz.zzf(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzb;
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 30:
                    i3 = 0;
                    iZzh = zzgz.zzd(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 31:
                    i3 = 0;
                    iZzh = zzgz.zzh(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 32:
                    i3 = 0;
                    iZzh = zzgz.zzi(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 33:
                    i3 = 0;
                    iZzh = zzgz.zzg(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 34:
                    i3 = 0;
                    iZzh = zzgz.zzc(i16, (List) unsafe2.getObject(t, j3), false);
                    iZzb3 += iZzh;
                    i2 = 1;
                    j = 0;
                    break;
                case 35:
                    iZzi = zzgz.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 36:
                    iZzi = zzgz.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 37:
                    iZzi = zzgz.zza((List<Long>) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 38:
                    iZzi = zzgz.zzb((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 39:
                    iZzi = zzgz.zze((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 40:
                    iZzi = zzgz.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 41:
                    iZzi = zzgz.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 42:
                    iZzi = zzgz.zzj((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 43:
                    iZzi = zzgz.zzf((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 44:
                    iZzi = zzgz.zzd((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 45:
                    iZzi = zzgz.zzh((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 46:
                    iZzi = zzgz.zzi((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 47:
                    iZzi = zzgz.zzg((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 48:
                    iZzi = zzgz.zzc((List) unsafe2.getObject(t, j3));
                    if (iZzi > 0) {
                        iZze = zzel.zze(i16);
                        iZzg = zzel.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 49:
                    iZzb = zzgz.zzb(i16, (List<zzgi>) unsafe2.getObject(t, j3), zza(i14));
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
                        iZzb = zzel.zzb(i16, 0.0d);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 52:
                    if (zza(t, i16, i14)) {
                        iZzj = zzel.zzb(i16, 0.0f);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 53:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzd(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 54:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zze(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 55:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzf(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 56:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzg(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 57:
                    if (zza(t, i16, i14)) {
                        iZzj = zzel.zzi(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 58:
                    if (zza(t, i16, i14)) {
                        iZzj = zzel.zzb(i16, true);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 59:
                    if (zza(t, i16, i14)) {
                        Object object2 = unsafe2.getObject(t, j3);
                        if (object2 instanceof zzdu) {
                            iZzb = zzel.zzc(i16, (zzdu) object2);
                        } else {
                            iZzb = zzel.zzb(i16, (String) object2);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 60:
                    if (zza(t, i16, i14)) {
                        iZzb = zzgz.zza(i16, unsafe2.getObject(t, j3), zza(i14));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 61:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzc(i16, (zzdu) unsafe2.getObject(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 62:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzg(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 63:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzk(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 64:
                    if (zza(t, i16, i14)) {
                        iZzj = zzel.zzj(i16, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 65:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzh(i16, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 66:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzh(i16, zzd(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 67:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzf(i16, zze(t, j3));
                        iZzb3 += iZzb;
                    }
                    i2 = 1;
                    i3 = 0;
                    j = 0;
                    break;
                case 68:
                    if (zza(t, i16, i14)) {
                        iZzb = zzel.zzc(i16, (zzgi) unsafe2.getObject(t, j3), zza(i14));
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
        int iZza2 = iZzb3 + zza((zzhp) this.zzq, (Object) t);
        if (!this.zzh) {
            return iZza2;
        }
        zzeu<T> zzeuVarZza = this.zzr.zza(t);
        for (int i20 = iZza; i20 < zzeuVarZza.zza.zzc(); i20++) {
            Map.Entry entryZzb = zzeuVarZza.zza.zzb(i20);
            iZza += zzeu.zza((zzew<?>) entryZzb.getKey(), entryZzb.getValue());
        }
        for (Map.Entry entry : zzeuVarZza.zza.zzd()) {
            iZza += zzeu.zza((zzew<?>) entry.getKey(), entry.getValue());
        }
        return iZza2 + iZza;
    }

    private static <UT, UB> int zza(zzhp<UT, UB> zzhpVar, T t) {
        return zzhpVar.zzd(zzhpVar.zza(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzhv.zzf(obj, j);
    }

    /* JADX WARN: Code duplicated, block: B:178:0x054a  */
    /* JADX WARN: Code duplicated, block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zza(T t, zzim zzimVar) throws IOException {
        Iterator itZzd;
        Map.Entry<?, ?> entry;
        Iterator itZze;
        Map.Entry<?, ?> entry2;
        if (zzimVar.zza() == zzil.zzb) {
            zza(this.zzq, t, zzimVar);
            if (this.zzh) {
                zzeu<T> zzeuVarZza = this.zzr.zza(t);
                if (zzeuVarZza.zza.isEmpty()) {
                    itZze = null;
                    entry2 = null;
                } else {
                    itZze = zzeuVarZza.zze();
                    entry2 = (Map.Entry) itZze.next();
                }
            } else {
                itZze = null;
                entry2 = null;
            }
            for (int length = this.zzc.length - 3; length >= 0; length -= 3) {
                int iZzd = zzd(length);
                int i = this.zzc[length];
                while (entry2 != null && this.zzr.zza(entry2) > i) {
                    this.zzr.zza(zzimVar, entry2);
                    entry2 = itZze.hasNext() ? (Map.Entry) itZze.next() : null;
                }
                switch ((iZzd & 267386880) >>> 20) {
                    case 0:
                        if (zza((Object) t, length)) {
                            zzimVar.zza(i, zzhv.zze(t, iZzd & 1048575));
                        }
                        break;
                    case 1:
                        if (zza((Object) t, length)) {
                            zzimVar.zza(i, zzhv.zzd(t, iZzd & 1048575));
                        }
                        break;
                    case 2:
                        if (zza((Object) t, length)) {
                            zzimVar.zza(i, zzhv.zzb(t, iZzd & 1048575));
                        }
                        break;
                    case 3:
                        if (zza((Object) t, length)) {
                            zzimVar.zzc(i, zzhv.zzb(t, iZzd & 1048575));
                        }
                        break;
                    case 4:
                        if (zza((Object) t, length)) {
                            zzimVar.zzc(i, zzhv.zza(t, iZzd & 1048575));
                        }
                        break;
                    case 5:
                        if (zza((Object) t, length)) {
                            zzimVar.zzd(i, zzhv.zzb(t, iZzd & 1048575));
                        }
                        break;
                    case 6:
                        if (zza((Object) t, length)) {
                            zzimVar.zzd(i, zzhv.zza(t, iZzd & 1048575));
                        }
                        break;
                    case 7:
                        if (zza((Object) t, length)) {
                            zzimVar.zza(i, zzhv.zzc(t, iZzd & 1048575));
                        }
                        break;
                    case 8:
                        if (zza((Object) t, length)) {
                            zza(i, zzhv.zzf(t, iZzd & 1048575), zzimVar);
                        }
                        break;
                    case 9:
                        if (zza((Object) t, length)) {
                            zzimVar.zza(i, zzhv.zzf(t, iZzd & 1048575), zza(length));
                        }
                        break;
                    case 10:
                        if (zza((Object) t, length)) {
                            zzimVar.zza(i, (zzdu) zzhv.zzf(t, iZzd & 1048575));
                        }
                        break;
                    case 11:
                        if (zza((Object) t, length)) {
                            zzimVar.zze(i, zzhv.zza(t, iZzd & 1048575));
                        }
                        break;
                    case 12:
                        if (zza((Object) t, length)) {
                            zzimVar.zzb(i, zzhv.zza(t, iZzd & 1048575));
                        }
                        break;
                    case 13:
                        if (zza((Object) t, length)) {
                            zzimVar.zza(i, zzhv.zza(t, iZzd & 1048575));
                        }
                        break;
                    case 14:
                        if (zza((Object) t, length)) {
                            zzimVar.zzb(i, zzhv.zzb(t, iZzd & 1048575));
                        }
                        break;
                    case 15:
                        if (zza((Object) t, length)) {
                            zzimVar.zzf(i, zzhv.zza(t, iZzd & 1048575));
                        }
                        break;
                    case 16:
                        if (zza((Object) t, length)) {
                            zzimVar.zze(i, zzhv.zzb(t, iZzd & 1048575));
                        }
                        break;
                    case 17:
                        if (zza((Object) t, length)) {
                            zzimVar.zzb(i, zzhv.zzf(t, iZzd & 1048575), zza(length));
                        }
                        break;
                    case 18:
                        zzgz.zza(this.zzc[length], (List<Double>) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 19:
                        zzgz.zzb(this.zzc[length], (List<Float>) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 20:
                        zzgz.zzc(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 21:
                        zzgz.zzd(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 22:
                        zzgz.zzh(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 23:
                        zzgz.zzf(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 24:
                        zzgz.zzk(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 25:
                        zzgz.zzn(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 26:
                        zzgz.zza(this.zzc[length], (List<String>) zzhv.zzf(t, iZzd & 1048575), zzimVar);
                        break;
                    case 27:
                        zzgz.zza(this.zzc[length], (List<?>) zzhv.zzf(t, iZzd & 1048575), zzimVar, zza(length));
                        break;
                    case 28:
                        zzgz.zzb(this.zzc[length], (List<zzdu>) zzhv.zzf(t, iZzd & 1048575), zzimVar);
                        break;
                    case 29:
                        zzgz.zzi(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 30:
                        zzgz.zzm(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 31:
                        zzgz.zzl(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 32:
                        zzgz.zzg(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 33:
                        zzgz.zzj(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 34:
                        zzgz.zze(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, false);
                        break;
                    case 35:
                        zzgz.zza(this.zzc[length], (List<Double>) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 36:
                        zzgz.zzb(this.zzc[length], (List<Float>) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 37:
                        zzgz.zzc(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 38:
                        zzgz.zzd(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 39:
                        zzgz.zzh(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 40:
                        zzgz.zzf(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 41:
                        zzgz.zzk(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 42:
                        zzgz.zzn(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 43:
                        zzgz.zzi(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 44:
                        zzgz.zzm(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 45:
                        zzgz.zzl(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 46:
                        zzgz.zzg(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 47:
                        zzgz.zzj(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 48:
                        zzgz.zze(this.zzc[length], (List) zzhv.zzf(t, iZzd & 1048575), zzimVar, true);
                        break;
                    case 49:
                        zzgz.zzb(this.zzc[length], (List<?>) zzhv.zzf(t, iZzd & 1048575), zzimVar, zza(length));
                        break;
                    case 50:
                        zza(zzimVar, i, zzhv.zzf(t, iZzd & 1048575), length);
                        break;
                    case 51:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzb(t, iZzd & 1048575));
                        }
                        break;
                    case 52:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzc(t, iZzd & 1048575));
                        }
                        break;
                    case 53:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zze(t, iZzd & 1048575));
                        }
                        break;
                    case 54:
                        if (zza(t, i, length)) {
                            zzimVar.zzc(i, zze(t, iZzd & 1048575));
                        }
                        break;
                    case 55:
                        if (zza(t, i, length)) {
                            zzimVar.zzc(i, zzd(t, iZzd & 1048575));
                        }
                        break;
                    case 56:
                        if (zza(t, i, length)) {
                            zzimVar.zzd(i, zze(t, iZzd & 1048575));
                        }
                        break;
                    case 57:
                        if (zza(t, i, length)) {
                            zzimVar.zzd(i, zzd(t, iZzd & 1048575));
                        }
                        break;
                    case 58:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzf(t, iZzd & 1048575));
                        }
                        break;
                    case 59:
                        if (zza(t, i, length)) {
                            zza(i, zzhv.zzf(t, iZzd & 1048575), zzimVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzhv.zzf(t, iZzd & 1048575), zza(length));
                        }
                        break;
                    case 61:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, (zzdu) zzhv.zzf(t, iZzd & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i, length)) {
                            zzimVar.zze(i, zzd(t, iZzd & 1048575));
                        }
                        break;
                    case 63:
                        if (zza(t, i, length)) {
                            zzimVar.zzb(i, zzd(t, iZzd & 1048575));
                        }
                        break;
                    case 64:
                        if (zza(t, i, length)) {
                            zzimVar.zza(i, zzd(t, iZzd & 1048575));
                        }
                        break;
                    case 65:
                        if (zza(t, i, length)) {
                            zzimVar.zzb(i, zze(t, iZzd & 1048575));
                        }
                        break;
                    case 66:
                        if (zza(t, i, length)) {
                            zzimVar.zzf(i, zzd(t, iZzd & 1048575));
                        }
                        break;
                    case 67:
                        if (zza(t, i, length)) {
                            zzimVar.zze(i, zze(t, iZzd & 1048575));
                        }
                        break;
                    case 68:
                        if (zza(t, i, length)) {
                            zzimVar.zzb(i, zzhv.zzf(t, iZzd & 1048575), zza(length));
                        }
                        break;
                }
            }
            while (entry2 != null) {
                this.zzr.zza(zzimVar, entry2);
                entry2 = itZze.hasNext() ? (Map.Entry) itZze.next() : null;
            }
            return;
        }
        if (this.zzj) {
            if (this.zzh) {
                zzeu<T> zzeuVarZza2 = this.zzr.zza(t);
                if (zzeuVarZza2.zza.isEmpty()) {
                    itZzd = null;
                    entry = null;
                } else {
                    itZzd = zzeuVarZza2.zzd();
                    entry = (Map.Entry) itZzd.next();
                }
            } else {
                itZzd = null;
                entry = null;
            }
            int length2 = this.zzc.length;
            for (int i2 = 0; i2 < length2; i2 += 3) {
                int iZzd2 = zzd(i2);
                int i3 = this.zzc[i2];
                while (entry != null && this.zzr.zza(entry) <= i3) {
                    this.zzr.zza(zzimVar, entry);
                    entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
                }
                switch ((iZzd2 & 267386880) >>> 20) {
                    case 0:
                        if (zza((Object) t, i2)) {
                            zzimVar.zza(i3, zzhv.zze(t, iZzd2 & 1048575));
                        }
                        break;
                    case 1:
                        if (zza((Object) t, i2)) {
                            zzimVar.zza(i3, zzhv.zzd(t, iZzd2 & 1048575));
                        }
                        break;
                    case 2:
                        if (zza((Object) t, i2)) {
                            zzimVar.zza(i3, zzhv.zzb(t, iZzd2 & 1048575));
                        }
                        break;
                    case 3:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzc(i3, zzhv.zzb(t, iZzd2 & 1048575));
                        }
                        break;
                    case 4:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzc(i3, zzhv.zza(t, iZzd2 & 1048575));
                        }
                        break;
                    case 5:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzd(i3, zzhv.zzb(t, iZzd2 & 1048575));
                        }
                        break;
                    case 6:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzd(i3, zzhv.zza(t, iZzd2 & 1048575));
                        }
                        break;
                    case 7:
                        if (zza((Object) t, i2)) {
                            zzimVar.zza(i3, zzhv.zzc(t, iZzd2 & 1048575));
                        }
                        break;
                    case 8:
                        if (zza((Object) t, i2)) {
                            zza(i3, zzhv.zzf(t, iZzd2 & 1048575), zzimVar);
                        }
                        break;
                    case 9:
                        if (zza((Object) t, i2)) {
                            zzimVar.zza(i3, zzhv.zzf(t, iZzd2 & 1048575), zza(i2));
                        }
                        break;
                    case 10:
                        if (zza((Object) t, i2)) {
                            zzimVar.zza(i3, (zzdu) zzhv.zzf(t, iZzd2 & 1048575));
                        }
                        break;
                    case 11:
                        if (zza((Object) t, i2)) {
                            zzimVar.zze(i3, zzhv.zza(t, iZzd2 & 1048575));
                        }
                        break;
                    case 12:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzb(i3, zzhv.zza(t, iZzd2 & 1048575));
                        }
                        break;
                    case 13:
                        if (zza((Object) t, i2)) {
                            zzimVar.zza(i3, zzhv.zza(t, iZzd2 & 1048575));
                        }
                        break;
                    case 14:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzb(i3, zzhv.zzb(t, iZzd2 & 1048575));
                        }
                        break;
                    case 15:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzf(i3, zzhv.zza(t, iZzd2 & 1048575));
                        }
                        break;
                    case 16:
                        if (zza((Object) t, i2)) {
                            zzimVar.zze(i3, zzhv.zzb(t, iZzd2 & 1048575));
                        }
                        break;
                    case 17:
                        if (zza((Object) t, i2)) {
                            zzimVar.zzb(i3, zzhv.zzf(t, iZzd2 & 1048575), zza(i2));
                        }
                        break;
                    case 18:
                        zzgz.zza(this.zzc[i2], (List<Double>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 19:
                        zzgz.zzb(this.zzc[i2], (List<Float>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 20:
                        zzgz.zzc(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 21:
                        zzgz.zzd(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 22:
                        zzgz.zzh(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 23:
                        zzgz.zzf(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 24:
                        zzgz.zzk(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 25:
                        zzgz.zzn(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 26:
                        zzgz.zza(this.zzc[i2], (List<String>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar);
                        break;
                    case 27:
                        zzgz.zza(this.zzc[i2], (List<?>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, zza(i2));
                        break;
                    case 28:
                        zzgz.zzb(this.zzc[i2], (List<zzdu>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar);
                        break;
                    case 29:
                        zzgz.zzi(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 30:
                        zzgz.zzm(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 31:
                        zzgz.zzl(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 32:
                        zzgz.zzg(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 33:
                        zzgz.zzj(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 34:
                        zzgz.zze(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, false);
                        break;
                    case 35:
                        zzgz.zza(this.zzc[i2], (List<Double>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 36:
                        zzgz.zzb(this.zzc[i2], (List<Float>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 37:
                        zzgz.zzc(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 38:
                        zzgz.zzd(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 39:
                        zzgz.zzh(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 40:
                        zzgz.zzf(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 41:
                        zzgz.zzk(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 42:
                        zzgz.zzn(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 43:
                        zzgz.zzi(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 44:
                        zzgz.zzm(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 45:
                        zzgz.zzl(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 46:
                        zzgz.zzg(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 47:
                        zzgz.zzj(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 48:
                        zzgz.zze(this.zzc[i2], (List) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, true);
                        break;
                    case 49:
                        zzgz.zzb(this.zzc[i2], (List<?>) zzhv.zzf(t, iZzd2 & 1048575), zzimVar, zza(i2));
                        break;
                    case 50:
                        zza(zzimVar, i3, zzhv.zzf(t, iZzd2 & 1048575), i2);
                        break;
                    case 51:
                        if (zza(t, i3, i2)) {
                            zzimVar.zza(i3, zzb(t, iZzd2 & 1048575));
                        }
                        break;
                    case 52:
                        if (zza(t, i3, i2)) {
                            zzimVar.zza(i3, zzc(t, iZzd2 & 1048575));
                        }
                        break;
                    case 53:
                        if (zza(t, i3, i2)) {
                            zzimVar.zza(i3, zze(t, iZzd2 & 1048575));
                        }
                        break;
                    case 54:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzc(i3, zze(t, iZzd2 & 1048575));
                        }
                        break;
                    case 55:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzc(i3, zzd(t, iZzd2 & 1048575));
                        }
                        break;
                    case 56:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzd(i3, zze(t, iZzd2 & 1048575));
                        }
                        break;
                    case 57:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzd(i3, zzd(t, iZzd2 & 1048575));
                        }
                        break;
                    case 58:
                        if (zza(t, i3, i2)) {
                            zzimVar.zza(i3, zzf(t, iZzd2 & 1048575));
                        }
                        break;
                    case 59:
                        if (zza(t, i3, i2)) {
                            zza(i3, zzhv.zzf(t, iZzd2 & 1048575), zzimVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i3, i2)) {
                            zzimVar.zza(i3, zzhv.zzf(t, iZzd2 & 1048575), zza(i2));
                        }
                        break;
                    case 61:
                        if (zza(t, i3, i2)) {
                            zzimVar.zza(i3, (zzdu) zzhv.zzf(t, iZzd2 & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i3, i2)) {
                            zzimVar.zze(i3, zzd(t, iZzd2 & 1048575));
                        }
                        break;
                    case 63:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzb(i3, zzd(t, iZzd2 & 1048575));
                        }
                        break;
                    case 64:
                        if (zza(t, i3, i2)) {
                            zzimVar.zza(i3, zzd(t, iZzd2 & 1048575));
                        }
                        break;
                    case 65:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzb(i3, zze(t, iZzd2 & 1048575));
                        }
                        break;
                    case 66:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzf(i3, zzd(t, iZzd2 & 1048575));
                        }
                        break;
                    case 67:
                        if (zza(t, i3, i2)) {
                            zzimVar.zze(i3, zze(t, iZzd2 & 1048575));
                        }
                        break;
                    case 68:
                        if (zza(t, i3, i2)) {
                            zzimVar.zzb(i3, zzhv.zzf(t, iZzd2 & 1048575), zza(i2));
                        }
                        break;
                }
            }
            while (entry != null) {
                this.zzr.zza(zzimVar, entry);
                entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
            }
            zza(this.zzq, t, zzimVar);
            return;
        }
        zzb((Object) t, zzimVar);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0023  */
    private final void zzb(T t, zzim zzimVar) throws IOException {
        Iterator itZzd;
        Map.Entry<?, ?> entry;
        int i;
        boolean z;
        if (this.zzh) {
            zzeu<T> zzeuVarZza = this.zzr.zza(t);
            if (zzeuVarZza.zza.isEmpty()) {
                itZzd = null;
                entry = null;
            } else {
                itZzd = zzeuVarZza.zzd();
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
            int iZzd = zzd(i4);
            int[] iArr = this.zzc;
            int i5 = iArr[i4];
            int i6 = (iZzd & 267386880) >>> 20;
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
                this.zzr.zza(zzimVar, entry);
                entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
            }
            long j = iZzd & 1048575;
            switch (i6) {
                case 0:
                    if ((i & i3) != 0) {
                        zzimVar.zza(i5, zzhv.zze(t, j));
                    }
                    break;
                case 1:
                    if ((i & i3) != 0) {
                        zzimVar.zza(i5, zzhv.zzd(t, j));
                    }
                    break;
                case 2:
                    if ((i & i3) != 0) {
                        zzimVar.zza(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 3:
                    if ((i & i3) != 0) {
                        zzimVar.zzc(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 4:
                    if ((i & i3) != 0) {
                        zzimVar.zzc(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 5:
                    if ((i & i3) != 0) {
                        zzimVar.zzd(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 6:
                    if ((i & i3) != 0) {
                        zzimVar.zzd(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 7:
                    if ((i & i3) != 0) {
                        zzimVar.zza(i5, zzhv.zzc(t, j));
                    }
                    break;
                case 8:
                    if ((i & i3) != 0) {
                        zza(i5, unsafe.getObject(t, j), zzimVar);
                    }
                    break;
                case 9:
                    if ((i & i3) != 0) {
                        zzimVar.zza(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 10:
                    if ((i & i3) != 0) {
                        zzimVar.zza(i5, (zzdu) unsafe.getObject(t, j));
                    }
                    break;
                case 11:
                    if ((i & i3) != 0) {
                        zzimVar.zze(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 12:
                    if ((i & i3) != 0) {
                        zzimVar.zzb(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 13:
                    if ((i & i3) != 0) {
                        zzimVar.zza(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 14:
                    if ((i & i3) != 0) {
                        zzimVar.zzb(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 15:
                    if ((i & i3) != 0) {
                        zzimVar.zzf(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 16:
                    if ((i & i3) != 0) {
                        zzimVar.zze(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 17:
                    if ((i & i3) != 0) {
                        zzimVar.zzb(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 18:
                    z = false;
                    zzgz.zza(this.zzc[i4], (List<Double>) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 19:
                    z = false;
                    zzgz.zzb(this.zzc[i4], (List<Float>) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 20:
                    z = false;
                    zzgz.zzc(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 21:
                    z = false;
                    zzgz.zzd(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 22:
                    z = false;
                    zzgz.zzh(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 23:
                    z = false;
                    zzgz.zzf(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 24:
                    z = false;
                    zzgz.zzk(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 25:
                    z = false;
                    zzgz.zzn(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 26:
                    zzgz.zza(this.zzc[i4], (List<String>) unsafe.getObject(t, j), zzimVar);
                    break;
                case 27:
                    zzgz.zza(this.zzc[i4], (List<?>) unsafe.getObject(t, j), zzimVar, zza(i4));
                    break;
                case 28:
                    zzgz.zzb(this.zzc[i4], (List<zzdu>) unsafe.getObject(t, j), zzimVar);
                    break;
                case 29:
                    z = false;
                    zzgz.zzi(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 30:
                    z = false;
                    zzgz.zzm(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 31:
                    z = false;
                    zzgz.zzl(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 32:
                    z = false;
                    zzgz.zzg(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 33:
                    z = false;
                    zzgz.zzj(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 34:
                    z = false;
                    zzgz.zze(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, false);
                    break;
                case 35:
                    zzgz.zza(this.zzc[i4], (List<Double>) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 36:
                    zzgz.zzb(this.zzc[i4], (List<Float>) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 37:
                    zzgz.zzc(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 38:
                    zzgz.zzd(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 39:
                    zzgz.zzh(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 40:
                    zzgz.zzf(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 41:
                    zzgz.zzk(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 42:
                    zzgz.zzn(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 43:
                    zzgz.zzi(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 44:
                    zzgz.zzm(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 45:
                    zzgz.zzl(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 46:
                    zzgz.zzg(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 47:
                    zzgz.zzj(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 48:
                    zzgz.zze(this.zzc[i4], (List) unsafe.getObject(t, j), zzimVar, true);
                    break;
                case 49:
                    zzgz.zzb(this.zzc[i4], (List<?>) unsafe.getObject(t, j), zzimVar, zza(i4));
                    break;
                case 50:
                    zza(zzimVar, i5, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zzb(t, j));
                    }
                    break;
                case 52:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zzc(t, j));
                    }
                    break;
                case 53:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zze(t, j));
                    }
                    break;
                case 54:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzc(i5, zze(t, j));
                    }
                    break;
                case 55:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzc(i5, zzd(t, j));
                    }
                    break;
                case 56:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzd(i5, zze(t, j));
                    }
                    break;
                case 57:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzd(i5, zzd(t, j));
                    }
                    break;
                case 58:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zzf(t, j));
                    }
                    break;
                case 59:
                    if (zza(t, i5, i4)) {
                        zza(i5, unsafe.getObject(t, j), zzimVar);
                    }
                    break;
                case 60:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                case 61:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, (zzdu) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zza(t, i5, i4)) {
                        zzimVar.zze(i5, zzd(t, j));
                    }
                    break;
                case 63:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzb(i5, zzd(t, j));
                    }
                    break;
                case 64:
                    if (zza(t, i5, i4)) {
                        zzimVar.zza(i5, zzd(t, j));
                    }
                    break;
                case 65:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzb(i5, zze(t, j));
                    }
                    break;
                case 66:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzf(i5, zzd(t, j));
                    }
                    break;
                case 67:
                    if (zza(t, i5, i4)) {
                        zzimVar.zze(i5, zze(t, j));
                    }
                    break;
                case 68:
                    if (zza(t, i5, i4)) {
                        zzimVar.zzb(i5, unsafe.getObject(t, j), zza(i4));
                    }
                    break;
                default:
                    break;
            }
        }
        while (entry != null) {
            this.zzr.zza(zzimVar, entry);
            entry = itZzd.hasNext() ? (Map.Entry) itZzd.next() : null;
        }
        zza(this.zzq, t, zzimVar);
    }

    private final <K, V> void zza(zzim zzimVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzimVar.zza(i, this.zzs.zzf(zzb(i2)), this.zzs.zzb(obj));
        }
    }

    private static <UT, UB> void zza(zzhp<UT, UB> zzhpVar, T t, zzim zzimVar) throws IOException {
        zzhpVar.zza(zzhpVar.zza(t), zzimVar);
    }

    private static zzhs zze(Object obj) {
        zzfb zzfbVar = (zzfb) obj;
        zzhs zzhsVar = zzfbVar.zzb;
        if (zzhsVar != zzhs.zza()) {
            return zzhsVar;
        }
        zzhs zzhsVarZzb = zzhs.zzb();
        zzfbVar.zzb = zzhsVarZzb;
        return zzhsVarZzb;
    }

    private static int zza(byte[] bArr, int i, int i2, zzig zzigVar, Class<?> cls, zzdt zzdtVar) throws IOException {
        switch (zzgl.zza[zzigVar.ordinal()]) {
            case 1:
                int iZzb = zzdq.zzb(bArr, i, zzdtVar);
                zzdtVar.zzc = Boolean.valueOf(zzdtVar.zzb != 0);
                return iZzb;
            case 2:
                return zzdq.zze(bArr, i, zzdtVar);
            case 3:
                zzdtVar.zzc = Double.valueOf(zzdq.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzdtVar.zzc = Integer.valueOf(zzdq.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzdtVar.zzc = Long.valueOf(zzdq.zzb(bArr, i));
                return i + 8;
            case 8:
                zzdtVar.zzc = Float.valueOf(zzdq.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int iZza = zzdq.zza(bArr, i, zzdtVar);
                zzdtVar.zzc = Integer.valueOf(zzdtVar.zza);
                return iZza;
            case 12:
            case 13:
                int iZzb2 = zzdq.zzb(bArr, i, zzdtVar);
                zzdtVar.zzc = Long.valueOf(zzdtVar.zzb);
                return iZzb2;
            case 14:
                return zzdq.zza(zzgw.zza().zza((Class) cls), bArr, i, i2, zzdtVar);
            case 15:
                int iZza2 = zzdq.zza(bArr, i, zzdtVar);
                zzdtVar.zzc = Integer.valueOf(zzeg.zzb(zzdtVar.zza));
                return iZza2;
            case 16:
                int iZzb3 = zzdq.zzb(bArr, i, zzdtVar);
                zzdtVar.zzc = Long.valueOf(zzeg.zza(zzdtVar.zzb));
                return iZzb3;
            case 17:
                return zzdq.zzd(bArr, i, zzdtVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzdt zzdtVar) throws IOException {
        int iZza;
        int iZza2 = i;
        Unsafe unsafe = zzb;
        zzff zzffVarZza = (zzff) unsafe.getObject(t, j2);
        if (!zzffVarZza.zza()) {
            int size = zzffVarZza.size();
            zzffVarZza = zzffVarZza.zza(size == 0 ? 10 : size << 1);
            unsafe.putObject(t, j2, zzffVarZza);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzem zzemVar = (zzem) zzffVarZza;
                    int iZza3 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i8 = zzdtVar.zza + iZza3;
                    while (iZza3 < i8) {
                        zzemVar.zza(zzdq.zzc(bArr, iZza3));
                        iZza3 += 8;
                    }
                    if (iZza3 == i8) {
                        return iZza3;
                    }
                    throw zzfi.zza();
                }
                if (i5 == 1) {
                    zzem zzemVar2 = (zzem) zzffVarZza;
                    zzemVar2.zza(zzdq.zzc(bArr, i));
                    while (true) {
                        int i9 = iZza2 + 8;
                        if (i9 >= i2) {
                            return i9;
                        }
                        iZza2 = zzdq.zza(bArr, i9, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return i9;
                        }
                        zzemVar2.zza(zzdq.zzc(bArr, iZza2));
                    }
                }
                return iZza2;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzfa zzfaVar = (zzfa) zzffVarZza;
                    int iZza4 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i10 = zzdtVar.zza + iZza4;
                    while (iZza4 < i10) {
                        zzfaVar.zza(zzdq.zzd(bArr, iZza4));
                        iZza4 += 4;
                    }
                    if (iZza4 == i10) {
                        return iZza4;
                    }
                    throw zzfi.zza();
                }
                if (i5 == 5) {
                    zzfa zzfaVar2 = (zzfa) zzffVarZza;
                    zzfaVar2.zza(zzdq.zzd(bArr, i));
                    while (true) {
                        int i11 = iZza2 + 4;
                        if (i11 >= i2) {
                            return i11;
                        }
                        iZza2 = zzdq.zza(bArr, i11, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return i11;
                        }
                        zzfaVar2.zza(zzdq.zzd(bArr, iZza2));
                    }
                }
                return iZza2;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzfw zzfwVar = (zzfw) zzffVarZza;
                    int iZza5 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i12 = zzdtVar.zza + iZza5;
                    while (iZza5 < i12) {
                        iZza5 = zzdq.zzb(bArr, iZza5, zzdtVar);
                        zzfwVar.zza(zzdtVar.zzb);
                    }
                    if (iZza5 == i12) {
                        return iZza5;
                    }
                    throw zzfi.zza();
                }
                if (i5 == 0) {
                    zzfw zzfwVar2 = (zzfw) zzffVarZza;
                    int iZzb = zzdq.zzb(bArr, iZza2, zzdtVar);
                    zzfwVar2.zza(zzdtVar.zzb);
                    while (iZzb < i2) {
                        int iZza6 = zzdq.zza(bArr, iZzb, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return iZzb;
                        }
                        iZzb = zzdq.zzb(bArr, iZza6, zzdtVar);
                        zzfwVar2.zza(zzdtVar.zzb);
                    }
                    return iZzb;
                }
                return iZza2;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzdq.zza(bArr, iZza2, (zzff<?>) zzffVarZza, zzdtVar);
                }
                if (i5 == 0) {
                    return zzdq.zza(i3, bArr, i, i2, (zzff<?>) zzffVarZza, zzdtVar);
                }
                return iZza2;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzfw zzfwVar3 = (zzfw) zzffVarZza;
                    int iZza7 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i13 = zzdtVar.zza + iZza7;
                    while (iZza7 < i13) {
                        zzfwVar3.zza(zzdq.zzb(bArr, iZza7));
                        iZza7 += 8;
                    }
                    if (iZza7 == i13) {
                        return iZza7;
                    }
                    throw zzfi.zza();
                }
                if (i5 == 1) {
                    zzfw zzfwVar4 = (zzfw) zzffVarZza;
                    zzfwVar4.zza(zzdq.zzb(bArr, i));
                    while (true) {
                        int i14 = iZza2 + 8;
                        if (i14 >= i2) {
                            return i14;
                        }
                        iZza2 = zzdq.zza(bArr, i14, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return i14;
                        }
                        zzfwVar4.zza(zzdq.zzb(bArr, iZza2));
                    }
                }
                return iZza2;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzfc zzfcVar = (zzfc) zzffVarZza;
                    int iZza8 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i15 = zzdtVar.zza + iZza8;
                    while (iZza8 < i15) {
                        zzfcVar.zzc(zzdq.zza(bArr, iZza8));
                        iZza8 += 4;
                    }
                    if (iZza8 == i15) {
                        return iZza8;
                    }
                    throw zzfi.zza();
                }
                if (i5 == 5) {
                    zzfc zzfcVar2 = (zzfc) zzffVarZza;
                    zzfcVar2.zzc(zzdq.zza(bArr, i));
                    while (true) {
                        int i16 = iZza2 + 4;
                        if (i16 >= i2) {
                            return i16;
                        }
                        iZza2 = zzdq.zza(bArr, i16, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return i16;
                        }
                        zzfcVar2.zzc(zzdq.zza(bArr, iZza2));
                    }
                }
                return iZza2;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzds zzdsVar = (zzds) zzffVarZza;
                    iZza = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i17 = zzdtVar.zza + iZza;
                    while (iZza < i17) {
                        iZza = zzdq.zzb(bArr, iZza, zzdtVar);
                        zzdsVar.zza(zzdtVar.zzb != 0);
                    }
                    if (iZza != i17) {
                        throw zzfi.zza();
                    }
                    return iZza;
                }
                if (i5 == 0) {
                    zzds zzdsVar2 = (zzds) zzffVarZza;
                    iZza2 = zzdq.zzb(bArr, iZza2, zzdtVar);
                    zzdsVar2.zza(zzdtVar.zzb != 0);
                    while (iZza2 < i2) {
                        int iZza9 = zzdq.zza(bArr, iZza2, zzdtVar);
                        if (i3 == zzdtVar.zza) {
                            iZza2 = zzdq.zzb(bArr, iZza9, zzdtVar);
                            zzdsVar2.zza(zzdtVar.zzb != 0);
                        }
                    }
                }
                return iZza2;
            case 26:
                if (i5 == 2) {
                    if ((j & TagBits.HasTypeVariable) == 0) {
                        int iZza10 = zzdq.zza(bArr, iZza2, zzdtVar);
                        int i18 = zzdtVar.zza;
                        if (i18 < 0) {
                            throw zzfi.zzb();
                        }
                        if (i18 == 0) {
                            zzffVarZza.add("");
                        } else {
                            zzffVarZza.add(new String(bArr, iZza10, i18, zzfe.zza));
                            iZza10 += i18;
                        }
                        while (iZza10 < i2) {
                            int iZza11 = zzdq.zza(bArr, iZza10, zzdtVar);
                            if (i3 != zzdtVar.zza) {
                                return iZza10;
                            }
                            iZza10 = zzdq.zza(bArr, iZza11, zzdtVar);
                            int i19 = zzdtVar.zza;
                            if (i19 < 0) {
                                throw zzfi.zzb();
                            }
                            if (i19 == 0) {
                                zzffVarZza.add("");
                            } else {
                                zzffVarZza.add(new String(bArr, iZza10, i19, zzfe.zza));
                                iZza10 += i19;
                            }
                        }
                        return iZza10;
                    }
                    int iZza12 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i20 = zzdtVar.zza;
                    if (i20 < 0) {
                        throw zzfi.zzb();
                    }
                    if (i20 == 0) {
                        zzffVarZza.add("");
                    } else {
                        int i21 = iZza12 + i20;
                        if (!zzhy.zza(bArr, iZza12, i21)) {
                            throw zzfi.zzf();
                        }
                        zzffVarZza.add(new String(bArr, iZza12, i20, zzfe.zza));
                        iZza12 = i21;
                    }
                    while (iZza12 < i2) {
                        int iZza13 = zzdq.zza(bArr, iZza12, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return iZza12;
                        }
                        iZza12 = zzdq.zza(bArr, iZza13, zzdtVar);
                        int i22 = zzdtVar.zza;
                        if (i22 < 0) {
                            throw zzfi.zzb();
                        }
                        if (i22 == 0) {
                            zzffVarZza.add("");
                        } else {
                            int i23 = iZza12 + i22;
                            if (!zzhy.zza(bArr, iZza12, i23)) {
                                throw zzfi.zzf();
                            }
                            zzffVarZza.add(new String(bArr, iZza12, i22, zzfe.zza));
                            iZza12 = i23;
                        }
                    }
                    return iZza12;
                }
                return iZza2;
            case 27:
                if (i5 == 2) {
                    return zzdq.zza(zza(i6), i3, bArr, i, i2, zzffVarZza, zzdtVar);
                }
                return iZza2;
            case 28:
                if (i5 == 2) {
                    int iZza14 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i24 = zzdtVar.zza;
                    if (i24 < 0) {
                        throw zzfi.zzb();
                    }
                    if (i24 > bArr.length - iZza14) {
                        throw zzfi.zza();
                    }
                    if (i24 == 0) {
                        zzffVarZza.add(zzdu.zza);
                    } else {
                        zzffVarZza.add(zzdu.zza(bArr, iZza14, i24));
                        iZza14 += i24;
                    }
                    while (iZza14 < i2) {
                        int iZza15 = zzdq.zza(bArr, iZza14, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return iZza14;
                        }
                        iZza14 = zzdq.zza(bArr, iZza15, zzdtVar);
                        int i25 = zzdtVar.zza;
                        if (i25 < 0) {
                            throw zzfi.zzb();
                        }
                        if (i25 > bArr.length - iZza14) {
                            throw zzfi.zza();
                        }
                        if (i25 == 0) {
                            zzffVarZza.add(zzdu.zza);
                        } else {
                            zzffVarZza.add(zzdu.zza(bArr, iZza14, i25));
                            iZza14 += i25;
                        }
                    }
                    return iZza14;
                }
                return iZza2;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZza = zzdq.zza(i3, bArr, i, i2, (zzff<?>) zzffVarZza, zzdtVar);
                    }
                    return iZza2;
                }
                iZza = zzdq.zza(bArr, iZza2, (zzff<?>) zzffVarZza, zzdtVar);
                zzfb zzfbVar = (zzfb) t;
                zzhs zzhsVar = zzfbVar.zzb;
                if (zzhsVar == zzhs.zza()) {
                    zzhsVar = null;
                }
                zzhs zzhsVar2 = (zzhs) zzgz.zza(i4, zzffVarZza, zzc(i6), zzhsVar, this.zzq);
                if (zzhsVar2 != null) {
                    zzfbVar.zzb = zzhsVar2;
                }
                return iZza;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzfc zzfcVar3 = (zzfc) zzffVarZza;
                    int iZza16 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i26 = zzdtVar.zza + iZza16;
                    while (iZza16 < i26) {
                        iZza16 = zzdq.zza(bArr, iZza16, zzdtVar);
                        zzfcVar3.zzc(zzeg.zzb(zzdtVar.zza));
                    }
                    if (iZza16 == i26) {
                        return iZza16;
                    }
                    throw zzfi.zza();
                }
                if (i5 == 0) {
                    zzfc zzfcVar4 = (zzfc) zzffVarZza;
                    int iZza17 = zzdq.zza(bArr, iZza2, zzdtVar);
                    zzfcVar4.zzc(zzeg.zzb(zzdtVar.zza));
                    while (iZza17 < i2) {
                        int iZza18 = zzdq.zza(bArr, iZza17, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return iZza17;
                        }
                        iZza17 = zzdq.zza(bArr, iZza18, zzdtVar);
                        zzfcVar4.zzc(zzeg.zzb(zzdtVar.zza));
                    }
                    return iZza17;
                }
                return iZza2;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzfw zzfwVar5 = (zzfw) zzffVarZza;
                    int iZza19 = zzdq.zza(bArr, iZza2, zzdtVar);
                    int i27 = zzdtVar.zza + iZza19;
                    while (iZza19 < i27) {
                        iZza19 = zzdq.zzb(bArr, iZza19, zzdtVar);
                        zzfwVar5.zza(zzeg.zza(zzdtVar.zzb));
                    }
                    if (iZza19 == i27) {
                        return iZza19;
                    }
                    throw zzfi.zza();
                }
                if (i5 == 0) {
                    zzfw zzfwVar6 = (zzfw) zzffVarZza;
                    int iZzb2 = zzdq.zzb(bArr, iZza2, zzdtVar);
                    zzfwVar6.zza(zzeg.zza(zzdtVar.zzb));
                    while (iZzb2 < i2) {
                        int iZza20 = zzdq.zza(bArr, iZzb2, zzdtVar);
                        if (i3 != zzdtVar.zza) {
                            return iZzb2;
                        }
                        iZzb2 = zzdq.zzb(bArr, iZza20, zzdtVar);
                        zzfwVar6.zza(zzeg.zza(zzdtVar.zzb));
                    }
                    return iZzb2;
                }
                return iZza2;
            case 49:
                if (i5 == 3) {
                    zzgx zzgxVarZza = zza(i6);
                    int i28 = (i3 & (-8)) | 4;
                    iZza2 = zzdq.zza(zzgxVarZza, bArr, i, i2, i28, zzdtVar);
                    zzffVarZza.add(zzdtVar.zzc);
                    while (iZza2 < i2) {
                        int iZza21 = zzdq.zza(bArr, iZza2, zzdtVar);
                        if (i3 == zzdtVar.zza) {
                            iZza2 = zzdq.zza(zzgxVarZza, bArr, iZza21, i2, i28, zzdtVar);
                            zzffVarZza.add(zzdtVar.zzc);
                        }
                    }
                }
                return iZza2;
            default:
                return iZza2;
        }
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, long j, zzdt zzdtVar) throws IOException {
        Unsafe unsafe = zzb;
        Object objZzb = zzb(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzs.zzc(object)) {
            Object objZze = this.zzs.zze(objZzb);
            this.zzs.zza(objZze, object);
            unsafe.putObject(t, j, objZze);
            object = objZze;
        }
        zzfz<?, ?> zzfzVarZzf = this.zzs.zzf(objZzb);
        Map<?, ?> mapZza = this.zzs.zza(object);
        int iZza = zzdq.zza(bArr, i, zzdtVar);
        int i4 = zzdtVar.zza;
        if (i4 < 0 || i4 > i2 - iZza) {
            throw zzfi.zza();
        }
        int i5 = i4 + iZza;
        K k = zzfzVarZzf.zzb;
        V v = zzfzVarZzf.zzd;
        while (iZza < i5) {
            int iZza2 = iZza + 1;
            int i6 = bArr[iZza];
            if (i6 < 0) {
                iZza2 = zzdq.zza(i6, bArr, iZza2, zzdtVar);
                i6 = zzdtVar.zza;
            }
            int i7 = iZza2;
            int i8 = i6 >>> 3;
            int i9 = i6 & 7;
            if (i8 == 1) {
                if (i9 == zzfzVarZzf.zza.zzb()) {
                    iZza = zza(bArr, i7, i2, zzfzVarZzf.zza, (Class<?>) null, zzdtVar);
                    k = (K) zzdtVar.zzc;
                } else {
                    iZza = zzdq.zza(i6, bArr, i7, i2, zzdtVar);
                }
            } else if (i8 == 2 && i9 == zzfzVarZzf.zzc.zzb()) {
                iZza = zza(bArr, i7, i2, zzfzVarZzf.zzc, zzfzVarZzf.zzd.getClass(), zzdtVar);
                v = zzdtVar.zzc;
            } else {
                iZza = zzdq.zza(i6, bArr, i7, i2, zzdtVar);
            }
        }
        if (iZza != i5) {
            throw zzfi.zze();
        }
        mapZza.put(k, v);
        return i5;
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzdt zzdtVar) throws IOException {
        int iZzb;
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Double.valueOf(zzdq.zzc(bArr, i)));
                iZzb = i + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 52:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Float.valueOf(zzdq.zzd(bArr, i)));
                iZzb = i + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdq.zzb(bArr, i, zzdtVar);
                unsafe.putObject(t, j, Long.valueOf(zzdtVar.zzb));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdq.zza(bArr, i, zzdtVar);
                unsafe.putObject(t, j, Integer.valueOf(zzdtVar.zza));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 56:
            case 65:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(t, j, Long.valueOf(zzdq.zzb(bArr, i)));
                iZzb = i + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 57:
            case 64:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(t, j, Integer.valueOf(zzdq.zza(bArr, i)));
                iZzb = i + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdq.zzb(bArr, i, zzdtVar);
                unsafe.putObject(t, j, Boolean.valueOf(zzdtVar.zzb != 0));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzdq.zza(bArr, i, zzdtVar);
                int i9 = zzdtVar.zza;
                if (i9 == 0) {
                    unsafe.putObject(t, j, "");
                } else {
                    if ((i6 & 536870912) != 0 && !zzhy.zza(bArr, iZza, iZza + i9)) {
                        throw zzfi.zzf();
                    }
                    unsafe.putObject(t, j, new String(bArr, iZza, i9, zzfe.zza));
                    iZza += i9;
                }
                unsafe.putInt(t, j2, i4);
                return iZza;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZza2 = zzdq.zza(zza(i8), bArr, i, i2, zzdtVar);
                Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object == null) {
                    unsafe.putObject(t, j, zzdtVar.zzc);
                } else {
                    unsafe.putObject(t, j, zzfe.zza(object, zzdtVar.zzc));
                }
                unsafe.putInt(t, j2, i4);
                return iZza2;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                iZzb = zzdq.zze(bArr, i, zzdtVar);
                unsafe.putObject(t, j, zzdtVar.zzc);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZza3 = zzdq.zza(bArr, i, zzdtVar);
                int i10 = zzdtVar.zza;
                zzfg zzfgVarZzc = zzc(i8);
                if (zzfgVarZzc == null || zzfgVarZzc.zza(i10)) {
                    unsafe.putObject(t, j, Integer.valueOf(i10));
                    iZzb = iZza3;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                zze(t).zza(i3, Long.valueOf(i10));
                return iZza3;
            case 66:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdq.zza(bArr, i, zzdtVar);
                unsafe.putObject(t, j, Integer.valueOf(zzeg.zzb(zzdtVar.zza)));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 67:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzdq.zzb(bArr, i, zzdtVar);
                unsafe.putObject(t, j, Long.valueOf(zzeg.zza(zzdtVar.zzb)));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 68:
                if (i5 != 3) {
                    return i;
                }
                iZzb = zzdq.zza(zza(i8), bArr, i, i2, (i3 & (-8)) | 4, zzdtVar);
                Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object2 == null) {
                    unsafe.putObject(t, j, zzdtVar.zzc);
                } else {
                    unsafe.putObject(t, j, zzfe.zza(object2, zzdtVar.zzc));
                }
                unsafe.putInt(t, j2, i4);
                return iZzb;
            default:
                return i;
        }
    }

    private final zzgx zza(int i) {
        int i2 = (i / 3) << 1;
        zzgx zzgxVar = (zzgx) this.zzd[i2];
        if (zzgxVar != null) {
            return zzgxVar;
        }
        zzgx<T> zzgxVarZza = zzgw.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zzgxVarZza;
        return zzgxVarZza;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    private final zzfg zzc(int i) {
        return (zzfg) this.zzd[((i / 3) << 1) + 1];
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 13601. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    final int zza(T r29, byte[] r30, int r31, int r32, int r33, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdt r34) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1360
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_vision_mediapipe.zzgm.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdt):int");
    }

    /* JADX WARN: Code duplicated, block: B:103:0x02d5 A[PHI: r0 r20 r27 r28 r29
      0x02d5: PHI (r0v22 int) = (r0v17 int), (r0v20 int), (r0v24 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x02d5: PHI (r20v5 int) = (r20v3 int), (r20v3 int), (r20v7 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x02d5: PHI (r27v3 int) = (r27v1 int), (r27v1 int), (r27v4 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x02d5: PHI (r28v2 int) = (r28v0 int), (r28v0 int), (r28v3 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x02d5: PHI (r29v6 sun.misc.Unsafe) = (r29v4 sun.misc.Unsafe), (r29v4 sun.misc.Unsafe), (r29v7 sun.misc.Unsafe) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:115:0x033f A[PHI: r0 r20 r27 r28 r29
      0x033f: PHI (r0v21 int) = (r0v17 int), (r0v20 int), (r0v24 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x033f: PHI (r20v4 int) = (r20v3 int), (r20v3 int), (r20v7 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x033f: PHI (r27v2 int) = (r27v1 int), (r27v1 int), (r27v4 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x033f: PHI (r28v1 int) = (r28v0 int), (r28v0 int), (r28v3 int) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]
      0x033f: PHI (r29v5 sun.misc.Unsafe) = (r29v4 sun.misc.Unsafe), (r29v4 sun.misc.Unsafe), (r29v7 sun.misc.Unsafe) binds: [B:114:0x033d, B:109:0x031a, B:101:0x02d1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Failed to find 'out' block for switch in B:29:0x0094. Please report as an issue. */
    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zza(T t, byte[] bArr, int i, int i2, zzdt zzdtVar) throws IOException {
        int i3;
        int iZza;
        int iZzf;
        int i4;
        int i5;
        Unsafe unsafe;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        Unsafe unsafe2;
        int i13;
        int i14;
        Unsafe unsafe3;
        Unsafe unsafe4;
        this = this;
        t = t;
        bArr = bArr;
        i2 = i2;
        zzdtVar = zzdtVar;
        if (this.zzj) {
            Unsafe unsafe5 = zzb;
            int i15 = -1;
            int i16 = 1048575;
            int iZza2 = i;
            int i17 = 1048575;
            int i18 = -1;
            int i19 = 0;
            int i20 = 0;
            while (iZza2 < i2) {
                int i21 = iZza2 + 1;
                byte b = bArr[iZza2];
                if (b < 0) {
                    iZza = zzdq.zza(b, bArr, i21, zzdtVar);
                    i3 = zzdtVar.zza;
                } else {
                    i3 = b;
                    iZza = i21;
                }
                int i22 = i3 >>> 3;
                int i23 = i3 & 7;
                if (i22 > i18) {
                    iZzf = this.zza(i22, i19 / 3);
                } else {
                    iZzf = this.zzf(i22);
                }
                int i24 = iZzf;
                if (i24 == i15) {
                    i4 = iZza;
                    i5 = i22;
                    unsafe = unsafe5;
                    i6 = i15;
                    i7 = 0;
                } else {
                    int[] iArr = this.zzc;
                    int i25 = iArr[i24 + 1];
                    int i26 = (i25 & 267386880) >>> 20;
                    Unsafe unsafe6 = unsafe5;
                    long j = i25 & i16;
                    if (i26 <= 17) {
                        int i27 = iArr[i24 + 2];
                        int i28 = 1 << (i27 >>> 20);
                        int i29 = i27 & 1048575;
                        if (i29 != i17) {
                            if (i17 != 1048575) {
                                long j2 = i17;
                                unsafe4 = unsafe6;
                                unsafe4.putInt(t, j2, i20);
                            } else {
                                unsafe4 = unsafe6;
                            }
                            if (i29 != 1048575) {
                                i20 = unsafe4.getInt(t, i29);
                            }
                            unsafe2 = unsafe4;
                            i17 = i29;
                        } else {
                            unsafe2 = unsafe6;
                        }
                        switch (i26) {
                            case 0:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i14 = iZza;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 1) {
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    zzhv.zza(t, j, zzdq.zzc(bArr, i14));
                                    iZza2 = i14 + 8;
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 1:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i14 = iZza;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 5) {
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    zzhv.zza((Object) t, j, zzdq.zzd(bArr, i14));
                                    iZza2 = i14 + 4;
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 2:
                            case 3:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i14 = iZza;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 0) {
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    int iZzb = zzdq.zzb(bArr, i14, zzdtVar);
                                    unsafe3.putLong(t, j, zzdtVar.zzb);
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    iZza2 = iZzb;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 4:
                            case 11:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i14 = iZza;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 0) {
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    iZza2 = zzdq.zza(bArr, i14, zzdtVar);
                                    unsafe3.putInt(t, j, zzdtVar.zza);
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 5:
                            case 14:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 1) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    unsafe3.putLong(t, j, zzdq.zzb(bArr, iZza));
                                    iZza2 = iZza + 8;
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 6:
                            case 13:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 5) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    unsafe3.putInt(t, j, zzdq.zza(bArr, iZza));
                                    iZza2 = iZza + 4;
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 7:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 0) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    iZza2 = zzdq.zzb(bArr, iZza, zzdtVar);
                                    zzhv.zza(t, j, zzdtVar.zzb != 0);
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 8:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 2) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    if ((i25 & 536870912) == 0) {
                                        iZza2 = zzdq.zzc(bArr, iZza, zzdtVar);
                                    } else {
                                        iZza2 = zzdq.zzd(bArr, iZza, zzdtVar);
                                    }
                                    unsafe3.putObject(t, j, zzdtVar.zzc);
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 9:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 2) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    iZza2 = zzdq.zza(this.zza(i13), bArr, iZza, i2, zzdtVar);
                                    Object object = unsafe3.getObject(t, j);
                                    if (object == null) {
                                        unsafe3.putObject(t, j, zzdtVar.zzc);
                                    } else {
                                        unsafe3.putObject(t, j, zzfe.zza(object, zzdtVar.zzc));
                                    }
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 10:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 2) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    iZza2 = zzdq.zze(bArr, iZza, zzdtVar);
                                    unsafe3.putObject(t, j, zzdtVar.zzc);
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 12:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 0) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    iZza2 = zzdq.zza(bArr, iZza, zzdtVar);
                                    unsafe3.putInt(t, j, zzdtVar.zza);
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 15:
                                i5 = i22;
                                i9 = 1048575;
                                i13 = i24;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                if (i23 != 0) {
                                    i14 = iZza;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    iZza2 = zzdq.zza(bArr, iZza, zzdtVar);
                                    unsafe3.putInt(t, j, zzeg.zzb(zzdtVar.zza));
                                    i20 |= i28;
                                    unsafe5 = unsafe3;
                                    i19 = i13;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            case 16:
                                if (i23 != 0) {
                                    i5 = i22;
                                    i8 = i17;
                                    unsafe3 = unsafe2;
                                    i14 = iZza;
                                    i13 = i24;
                                    i4 = i14;
                                    unsafe = unsafe3;
                                    i7 = i13;
                                    i17 = i8;
                                    i6 = -1;
                                } else {
                                    int iZzb2 = zzdq.zzb(bArr, iZza, zzdtVar);
                                    i8 = i17;
                                    i5 = i22;
                                    i9 = 1048575;
                                    unsafe2.putLong(t, j, zzeg.zza(zzdtVar.zzb));
                                    i20 |= i28;
                                    unsafe5 = unsafe2;
                                    i19 = i24;
                                    iZza2 = iZzb2;
                                    i17 = i8;
                                    i18 = i5;
                                    i16 = i9;
                                    i15 = -1;
                                }
                                break;
                            default:
                                i5 = i22;
                                i13 = i24;
                                i14 = iZza;
                                i8 = i17;
                                unsafe3 = unsafe2;
                                i4 = i14;
                                unsafe = unsafe3;
                                i7 = i13;
                                i17 = i8;
                                i6 = -1;
                                break;
                        }
                    } else {
                        i5 = i22;
                        int i30 = iZza;
                        i8 = i17;
                        i9 = 1048575;
                        if (i26 != 27) {
                            i7 = i24;
                            if (i26 <= 49) {
                                i11 = i20;
                                i12 = i8;
                                unsafe = unsafe6;
                                i6 = -1;
                                iZza2 = zza(t, bArr, i30, i2, i3, i5, i23, i7, i25, i26, j, zzdtVar);
                                if (iZza2 == i30) {
                                    i4 = iZza2;
                                    i20 = i11;
                                    i17 = i12;
                                } else {
                                    i19 = i7;
                                    i15 = i6;
                                    i18 = i5;
                                    i20 = i11;
                                    i17 = i12;
                                }
                            } else {
                                i10 = i30;
                                i11 = i20;
                                unsafe = unsafe6;
                                i12 = i8;
                                i6 = -1;
                                if (i26 == 50) {
                                    if (i23 == 2) {
                                        iZza2 = zza(t, bArr, i10, i2, i7, j, zzdtVar);
                                        if (iZza2 == i10) {
                                            i4 = iZza2;
                                        } else {
                                            i19 = i7;
                                            i15 = i6;
                                            i18 = i5;
                                            i20 = i11;
                                            i17 = i12;
                                        }
                                    }
                                    i20 = i11;
                                    i17 = i12;
                                } else {
                                    iZza2 = zza(t, bArr, i10, i2, i3, i5, i23, i25, i26, j, i7, zzdtVar);
                                    if (iZza2 == i10) {
                                        i4 = iZza2;
                                        i20 = i11;
                                        i17 = i12;
                                    } else {
                                        i19 = i7;
                                        i15 = i6;
                                        i18 = i5;
                                        i20 = i11;
                                        i17 = i12;
                                    }
                                }
                            }
                            unsafe5 = unsafe;
                            i16 = 1048575;
                        } else if (i23 == 2) {
                            zzff zzffVarZza = (zzff) unsafe6.getObject(t, j);
                            if (!zzffVarZza.zza()) {
                                int size = zzffVarZza.size();
                                zzffVarZza = zzffVarZza.zza(size == 0 ? 10 : size << 1);
                                unsafe6.putObject(t, j, zzffVarZza);
                            }
                            iZza2 = zzdq.zza(this.zza(i24), i3, bArr, i30, i2, zzffVarZza, zzdtVar);
                            unsafe5 = unsafe6;
                            i20 = i20;
                            i19 = i24;
                            i17 = i8;
                            i18 = i5;
                            i16 = i9;
                            i15 = -1;
                        } else {
                            i7 = i24;
                            i10 = i30;
                            i11 = i20;
                            unsafe = unsafe6;
                            i12 = i8;
                            i6 = -1;
                        }
                        i4 = i10;
                        i20 = i11;
                        i17 = i12;
                    }
                }
                iZza2 = zzdq.zza(i3, bArr, i4, i2, zze(t), zzdtVar);
                i19 = i7;
                i15 = i6;
                i18 = i5;
                unsafe5 = unsafe;
                i16 = 1048575;
            }
            int i31 = i20;
            Unsafe unsafe7 = unsafe5;
            if (i17 != i16) {
                unsafe7.putInt(t, i17, i31);
            }
            if (iZza2 != i2) {
                throw zzfi.zze();
            }
            return;
        }
        zza(t, bArr, i, i2, 0, zzdtVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zzc(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long jZzd = zzd(this.zzl[i2]) & 1048575;
            Object objZzf = zzhv.zzf(t, jZzd);
            if (objZzf != null) {
                zzhv.zza(t, jZzd, this.zzs.zzd(objZzf));
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

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzfg zzfgVar, UB ub, zzhp<UT, UB> zzhpVar) {
        zzfz<?, ?> zzfzVarZzf = this.zzs.zzf(zzb(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzfgVar.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzhpVar.zza();
                }
                zzec zzecVarZzc = zzdu.zzc(zzga.zza(zzfzVarZzf, next.getKey(), next.getValue()));
                try {
                    zzga.zza(zzecVarZzc.zzb(), zzfzVarZzf, next.getKey(), next.getValue());
                    zzhpVar.zza(ub, i2, zzecVarZzc.zza());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
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
    /* JADX WARN: Type inference failed for: r1v23, types: [com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx] */
    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
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
            int iZzd = zzd(i7);
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
            if ((268435456 & iZzd) != 0 && !zza(t, i7, i, i2, i11)) {
                return false;
            }
            int i12 = (267386880 & iZzd) >>> 20;
            if (i12 == 9 || i12 == 17) {
                if (zza(t, i7, i, i2, i11) && !zza(t, iZzd, zza(i7))) {
                    return false;
                }
            } else if (i12 == 27) {
                list = (List) zzhv.zzf(t, iZzd & 1048575);
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
                if (zza(t, i8, i7) && !zza(t, iZzd, zza(i7))) {
                    return false;
                }
            } else if (i12 == 49) {
                list = (List) zzhv.zzf(t, iZzd & 1048575);
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
                Map<?, ?> mapZzb = this.zzs.zzb(zzhv.zzf(t, iZzd & 1048575));
                if (mapZzb.isEmpty()) {
                    continue;
                } else if (this.zzs.zzf(zzb(i7)).zzc.zza() == zzij.MESSAGE) {
                    ?? Zza2 = 0;
                    for (Object obj : mapZzb.values()) {
                        if (Zza2 == 0) {
                            Zza2 = Zza2;
                            Zza2 = zzgw.zza().zza((Class) obj.getClass());
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
    private static boolean zza(Object obj, int i, zzgx zzgxVar) {
        return zzgxVar.zzd(zzhv.zzf(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzim zzimVar) throws IOException {
        if (obj instanceof String) {
            zzimVar.zza(i, (String) obj);
        } else {
            zzimVar.zza(i, (zzdu) obj);
        }
    }

    private final int zzd(int i) {
        return this.zzc[i + 1];
    }

    private final int zze(int i) {
        return this.zzc[i + 2];
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzhv.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzhv.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzhv.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzhv.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzhv.zzf(t, j)).booleanValue();
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
        int iZze = zze(i);
        long j = iZze & 1048575;
        if (j != 1048575) {
            return (zzhv.zza(t, j) & (1 << (iZze >>> 20))) != 0;
        }
        int iZzd = zzd(i);
        long j2 = iZzd & 1048575;
        switch ((iZzd & 267386880) >>> 20) {
            case 0:
                return zzhv.zze(t, j2) != 0.0d;
            case 1:
                return zzhv.zzd(t, j2) != 0.0f;
            case 2:
                return zzhv.zzb(t, j2) != 0;
            case 3:
                return zzhv.zzb(t, j2) != 0;
            case 4:
                return zzhv.zza(t, j2) != 0;
            case 5:
                return zzhv.zzb(t, j2) != 0;
            case 6:
                return zzhv.zza(t, j2) != 0;
            case 7:
                return zzhv.zzc(t, j2);
            case 8:
                Object objZzf = zzhv.zzf(t, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzdu) {
                    return !zzdu.zza.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzhv.zzf(t, j2) != null;
            case 10:
                return !zzdu.zza.equals(zzhv.zzf(t, j2));
            case 11:
                return zzhv.zza(t, j2) != 0;
            case 12:
                return zzhv.zza(t, j2) != 0;
            case 13:
                return zzhv.zza(t, j2) != 0;
            case 14:
                return zzhv.zzb(t, j2) != 0;
            case 15:
                return zzhv.zza(t, j2) != 0;
            case 16:
                return zzhv.zzb(t, j2) != 0;
            case 17:
                return zzhv.zzf(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzb(T t, int i) {
        int iZze = zze(i);
        long j = 1048575 & iZze;
        if (j == 1048575) {
            return;
        }
        zzhv.zza((Object) t, j, (1 << (iZze >>> 20)) | zzhv.zza(t, j));
    }

    private final boolean zza(T t, int i, int i2) {
        return zzhv.zza(t, (long) (zze(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzhv.zza((Object) t, zze(i2) & 1048575, i);
    }

    private final int zzf(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, 0);
    }

    private final int zza(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, i2);
    }

    private final int zzb(int i, int i2) {
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
}
