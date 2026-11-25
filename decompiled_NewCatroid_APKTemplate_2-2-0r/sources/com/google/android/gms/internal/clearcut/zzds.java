package com.google.android.gms.internal.clearcut;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes9.dex */
final class zzds<T> implements zzef<T> {
    private static final Unsafe zzmh = zzfd.zzef();
    private final int[] zzmi;
    private final Object[] zzmj;
    private final int zzmk;
    private final int zzml;
    private final int zzmm;
    private final zzdo zzmn;
    private final boolean zzmo;
    private final boolean zzmp;
    private final boolean zzmq;
    private final boolean zzmr;
    private final int[] zzms;
    private final int[] zzmt;
    private final int[] zzmu;
    private final zzdw zzmv;
    private final zzcy zzmw;
    private final zzex<?, ?> zzmx;
    private final zzbu<?> zzmy;
    private final zzdj zzmz;

    private zzds(int[] iArr, Object[] objArr, int i, int i2, int i3, zzdo zzdoVar, boolean z, boolean z2, int[] iArr2, int[] iArr3, int[] iArr4, zzdw zzdwVar, zzcy zzcyVar, zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdj zzdjVar) {
        this.zzmi = iArr;
        this.zzmj = objArr;
        this.zzmk = i;
        this.zzml = i2;
        this.zzmm = i3;
        this.zzmp = zzdoVar instanceof zzcg;
        this.zzmq = z;
        this.zzmo = zzbuVar != null && zzbuVar.zze(zzdoVar);
        this.zzmr = false;
        this.zzms = iArr2;
        this.zzmt = iArr3;
        this.zzmu = iArr4;
        this.zzmv = zzdwVar;
        this.zzmw = zzcyVar;
        this.zzmx = zzexVar;
        this.zzmy = zzbuVar;
        this.zzmn = zzdoVar;
        this.zzmz = zzdjVar;
    }

    private static int zza(int i, byte[] bArr, int i2, int i3, Object obj, zzay zzayVar) throws IOException {
        return zzax.zza(i, bArr, i2, i3, zzn(obj), zzayVar);
    }

    private static int zza(zzef<?> zzefVar, int i, byte[] bArr, int i2, int i3, zzcn<?> zzcnVar, zzay zzayVar) throws IOException {
        int iZza = zza((zzef) zzefVar, bArr, i2, i3, zzayVar);
        while (true) {
            zzcnVar.add(zzayVar.zzff);
            if (iZza >= i3) {
                break;
            }
            int iZza2 = zzax.zza(bArr, iZza, zzayVar);
            if (i != zzayVar.zzfd) {
                break;
            }
            iZza = zza((zzef) zzefVar, bArr, iZza2, i3, zzayVar);
        }
        return iZza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, int i3, zzay zzayVar) throws IOException {
        zzds zzdsVar = (zzds) zzefVar;
        Object objNewInstance = zzdsVar.newInstance();
        int iZza = zzdsVar.zza(objNewInstance, bArr, i, i2, i3, zzayVar);
        zzdsVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return iZza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, zzay zzayVar) throws IOException {
        int iZza = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZza = zzax.zza(i3, bArr, iZza, zzayVar);
            i3 = zzayVar.zzfd;
        }
        int i4 = iZza;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzco.zzbl();
        }
        Object objNewInstance = zzefVar.newInstance();
        int i5 = i3 + i4;
        zzefVar.zza(objNewInstance, bArr, i4, i5, zzayVar);
        zzefVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return i5;
    }

    private static <UT, UB> int zza(zzex<UT, UB> zzexVar, T t) {
        return zzexVar.zzm(zzexVar.zzq(t));
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzay zzayVar) throws IOException {
        Object objValueOf;
        Object objValueOf2;
        int iZzb;
        long jZza;
        int iZzm;
        Object objValueOf3;
        int i9;
        Unsafe unsafe = zzmh;
        long j2 = this.zzmi[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 != 1) {
                    return i;
                }
                objValueOf = Double.valueOf(zzax.zze(bArr, i));
                unsafe.putObject(t, j, objValueOf);
                iZzb = i + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 52:
                if (i5 != 5) {
                    return i;
                }
                objValueOf2 = Float.valueOf(zzax.zzf(bArr, i));
                unsafe.putObject(t, j, objValueOf2);
                iZzb = i + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzayVar.zzfe;
                objValueOf3 = Long.valueOf(jZza);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzayVar.zzfd;
                objValueOf3 = Integer.valueOf(iZzm);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 56:
            case 65:
                if (i5 != 1) {
                    return i;
                }
                objValueOf = Long.valueOf(zzax.zzd(bArr, i));
                unsafe.putObject(t, j, objValueOf);
                iZzb = i + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 57:
            case 64:
                if (i5 != 5) {
                    return i;
                }
                objValueOf2 = Integer.valueOf(zzax.zzc(bArr, i));
                unsafe.putObject(t, j, objValueOf2);
                iZzb = i + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                objValueOf3 = Boolean.valueOf(zzayVar.zzfe != 0);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                i9 = zzayVar.zzfd;
                if (i9 == 0) {
                    objValueOf3 = "";
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                if ((i6 & 536870912) != 0 && !zzff.zze(bArr, iZzb, iZzb + i9)) {
                    throw zzco.zzbp();
                }
                unsafe.putObject(t, j, new String(bArr, iZzb, i9, zzci.UTF_8));
                iZzb += i9;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                iZzb = zza(zzad(i8), bArr, i, i2, zzayVar);
                Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                objValueOf3 = zzayVar.zzff;
                if (object != null) {
                    objValueOf3 = zzci.zza(object, objValueOf3);
                }
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                i9 = zzayVar.zzfd;
                if (i9 == 0) {
                    objValueOf3 = zzbb.zzfi;
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                unsafe.putObject(t, j, zzbb.zzb(bArr, iZzb, i9));
                iZzb += i9;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZza = zzax.zza(bArr, i, zzayVar);
                int i10 = zzayVar.zzfd;
                zzck<?> zzckVarZzaf = zzaf(i8);
                if (zzckVarZzaf != null && zzckVarZzaf.zzb(i10) == null) {
                    zzn(t).zzb(i3, Long.valueOf(i10));
                    return iZza;
                }
                unsafe.putObject(t, j, Integer.valueOf(i10));
                iZzb = iZza;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 66:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzbk.zzm(zzayVar.zzfd);
                objValueOf3 = Integer.valueOf(iZzm);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 67:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzbk.zza(zzayVar.zzfe);
                objValueOf3 = Long.valueOf(jZza);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 68:
                if (i5 != 3) {
                    return i;
                }
                iZzb = zza(zzad(i8), bArr, i, i2, (i3 & (-8)) | 4, zzayVar);
                Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                objValueOf3 = zzayVar.zzff;
                if (object2 != null) {
                    objValueOf3 = zzci.zza(object2, objValueOf3);
                }
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            default:
                return i;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:126:0x0237 A[LOOP:9: B:127:0x0238->B:126:0x0237, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:129:0x023d  */
    /* JADX WARN: Code duplicated, block: B:131:0x0245  */
    /* JADX WARN: Code duplicated, block: B:247:0x0235 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:100:0x01e2 -> B:91:0x01bb). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:132:0x024d -> B:125:0x0235). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:70:0x0157 -> B:64:0x0139). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:86:0x01a8 -> B:79:0x0189). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    private final int zza(T r16, byte[] r17, int r18, int r19, int r20, int r21, int r22, int r23, long r24, int r26, long r27, com.google.android.gms.internal.clearcut.zzay r29) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 994
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, int, int, int, int, long, int, long, com.google.android.gms.internal.clearcut.zzay):int");
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, long j, zzay zzayVar) throws IOException {
        Unsafe unsafe = zzmh;
        Object objZzae = zzae(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzmz.zzi(object)) {
            Object objZzk = this.zzmz.zzk(objZzae);
            this.zzmz.zzb(objZzk, object);
            unsafe.putObject(t, j, objZzk);
            object = objZzk;
        }
        zzdh<?, ?> zzdhVarZzl = this.zzmz.zzl(objZzae);
        Map<?, ?> mapZzg = this.zzmz.zzg(object);
        int iZza = zzax.zza(bArr, i, zzayVar);
        int i5 = zzayVar.zzfd;
        if (i5 < 0 || i5 > i2 - iZza) {
            throw zzco.zzbl();
        }
        int i6 = i5 + iZza;
        K k = zzdhVarZzl.zzmc;
        V v = zzdhVarZzl.zzdu;
        while (iZza < i6) {
            int iZza2 = iZza + 1;
            int i7 = bArr[iZza];
            if (i7 < 0) {
                iZza2 = zzax.zza(i7, bArr, iZza2, zzayVar);
                i7 = zzayVar.zzfd;
            }
            int i8 = iZza2;
            int i9 = i7 >>> 3;
            int i10 = i7 & 7;
            if (i9 != 1) {
                if (i9 == 2 && i10 == zzdhVarZzl.zzmd.zzel()) {
                    iZza = zza(bArr, i8, i2, zzdhVarZzl.zzmd, zzdhVarZzl.zzdu.getClass(), zzayVar);
                    v = zzayVar.zzff;
                } else {
                    iZza = zzax.zza(i7, bArr, i8, i2, zzayVar);
                }
            } else if (i10 == zzdhVarZzl.zzmb.zzel()) {
                iZza = zza(bArr, i8, i2, zzdhVarZzl.zzmb, (Class<?>) null, zzayVar);
                k = (K) zzayVar.zzff;
            } else {
                iZza = zzax.zza(i7, bArr, i8, i2, zzayVar);
            }
        }
        if (iZza != i6) {
            throw zzco.zzbo();
        }
        mapZzg.put(k, v);
        return i6;
    }

    /* JADX WARN: Code duplicated, block: B:133:0x0372 A[ADDED_TO_REGION] */
    /* JADX WARN: Failed to find 'out' block for switch in B:19:0x0069. Please report as an issue. */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, zzay zzayVar) throws IOException {
        Unsafe unsafe;
        int i4;
        int i5;
        int i6;
        int i7;
        T t2;
        zzck<?> zzckVarZzaf;
        int i8;
        int iZza;
        int i9;
        int i10;
        int i11;
        int i12;
        zzay zzayVar2;
        int i13;
        int i14;
        int i15;
        long j;
        Object objZza;
        zzay zzayVar3;
        int iZze;
        zzds<T> zzdsVar = this;
        T t3 = t;
        bArr = bArr;
        i2 = i2;
        int i16 = i3;
        zzay zzayVar4 = zzayVar;
        Unsafe unsafe2 = zzmh;
        int i17 = -1;
        int iZza2 = i;
        int i18 = -1;
        int i19 = 0;
        int i20 = 0;
        while (true) {
            if (iZza2 < i2) {
                int i21 = iZza2 + 1;
                byte b = bArr[iZza2];
                if (b < 0) {
                    iZza = zzax.zza(b, bArr, i21, zzayVar4);
                    i8 = zzayVar4.zzfd;
                } else {
                    i8 = b;
                    iZza = i21;
                }
                int i22 = i8 >>> 3;
                int i23 = i8 & 7;
                int iZzai = zzdsVar.zzai(i22);
                if (iZzai != i17) {
                    int[] iArr = zzdsVar.zzmi;
                    int i24 = iArr[iZzai + 1];
                    int i25 = (i24 & 267386880) >>> 20;
                    int i26 = i8;
                    long j2 = i24 & 1048575;
                    if (i25 <= 17) {
                        int i27 = iArr[iZzai + 2];
                        int i28 = 1 << (i27 >>> 20);
                        int i29 = i27 & 1048575;
                        i17 = -1;
                        if (i29 != i18) {
                            if (i18 != -1) {
                                unsafe2.putInt(t3, i18, i20);
                            }
                            i20 = unsafe2.getInt(t3, i29);
                            i18 = i29;
                        }
                        switch (i25) {
                            case 0:
                                i5 = i26;
                                zzayVar2 = zzayVar;
                                i13 = iZza;
                                i14 = i18;
                                bArr = bArr;
                                if (i23 == 1) {
                                    zzfd.zza(t3, j2, zzax.zze(bArr, i13));
                                    iZza2 = i13 + 8;
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i2;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3 || i3 == 0) {
                                        iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                        zzdsVar = this;
                                        t3 = t;
                                        i16 = i3;
                                        i19 = i5;
                                        unsafe2 = unsafe;
                                        i17 = -1;
                                        zzayVar4 = zzayVar;
                                    } else {
                                        i6 = i18;
                                        i7 = -1;
                                        i4 = i12;
                                    }
                                }
                                break;
                            case 1:
                                i5 = i26;
                                zzayVar2 = zzayVar;
                                i13 = iZza;
                                i14 = i18;
                                bArr = bArr;
                                if (i23 == 5) {
                                    zzfd.zza((Object) t3, j2, zzax.zzf(bArr, i13));
                                    iZza2 = i13 + 4;
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i2;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 2:
                            case 3:
                                i5 = i26;
                                i13 = iZza;
                                i14 = i18;
                                bArr = bArr;
                                if (i23 == 0) {
                                    int iZzb = zzax.zzb(bArr, i13, zzayVar);
                                    unsafe2.putLong(t, j2, zzayVar.zzfe);
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i2;
                                    i19 = i5;
                                    zzayVar4 = zzayVar;
                                    iZza2 = iZzb;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 4:
                            case 11:
                                i5 = i26;
                                zzayVar2 = zzayVar;
                                i13 = iZza;
                                i14 = i18;
                                bArr = bArr;
                                if (i23 == 0) {
                                    iZza2 = zzax.zza(bArr, i13, zzayVar2);
                                    unsafe2.putInt(t3, j2, zzayVar2.zzfd);
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i2;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 5:
                            case 14:
                                i5 = i26;
                                zzayVar2 = zzayVar;
                                i14 = i18;
                                bArr = bArr;
                                if (i23 == 1) {
                                    unsafe2.putLong(t, j2, zzax.zzd(bArr, iZza));
                                    iZza2 = iZza + 8;
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i2;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 6:
                            case 13:
                                i5 = i26;
                                i15 = i2;
                                zzayVar2 = zzayVar;
                                i14 = i18;
                                bArr = bArr;
                                if (i23 == 5) {
                                    unsafe2.putInt(t3, j2, zzax.zzc(bArr, iZza));
                                    iZza2 = iZza + 4;
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i15;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 7:
                                i5 = i26;
                                i15 = i2;
                                zzayVar2 = zzayVar;
                                i14 = i18;
                                bArr = bArr;
                                if (i23 == 0) {
                                    iZza2 = zzax.zzb(bArr, iZza, zzayVar2);
                                    zzfd.zza(t3, j2, zzayVar2.zzfe != 0);
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i15;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 8:
                                i5 = i26;
                                i15 = i2;
                                zzayVar2 = zzayVar;
                                i14 = i18;
                                j = j2;
                                bArr = bArr;
                                if (i23 == 2) {
                                    iZza2 = (i24 & 536870912) == 0 ? zzax.zzc(bArr, iZza, zzayVar2) : zzax.zzd(bArr, iZza, zzayVar2);
                                    objZza = zzayVar2.zzff;
                                    unsafe2.putObject(t3, j, objZza);
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i15;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 9:
                                i5 = i26;
                                zzayVar2 = zzayVar;
                                i14 = i18;
                                j = j2;
                                bArr = bArr;
                                if (i23 == 2) {
                                    i15 = i2;
                                    iZza2 = zza(zzdsVar.zzad(iZzai), bArr, iZza, i15, zzayVar2);
                                    objZza = (i20 & i28) == 0 ? zzayVar2.zzff : zzci.zza(unsafe2.getObject(t3, j), zzayVar2.zzff);
                                    unsafe2.putObject(t3, j, objZza);
                                    i20 |= i28;
                                    i18 = i14;
                                    i2 = i15;
                                    i19 = i5;
                                    zzayVar4 = zzayVar2;
                                    i17 = -1;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 10:
                                i5 = i26;
                                zzayVar3 = zzayVar;
                                bArr = bArr;
                                if (i23 == 2) {
                                    iZze = zzax.zze(bArr, iZza, zzayVar3);
                                    unsafe2.putObject(t3, j2, zzayVar3.zzff);
                                    i20 |= i28;
                                    iZza2 = iZze;
                                    i19 = i5;
                                    zzayVar4 = zzayVar3;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i14 = i18;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 12:
                                i5 = i26;
                                zzayVar3 = zzayVar;
                                i17 = -1;
                                bArr = bArr;
                                if (i23 == 0) {
                                    iZza2 = zzax.zza(bArr, iZza, zzayVar3);
                                    int i30 = zzayVar3.zzfd;
                                    zzck<?> zzckVarZzaf2 = zzdsVar.zzaf(iZzai);
                                    if (zzckVarZzaf2 == null || zzckVarZzaf2.zzb(i30) != null) {
                                        unsafe2.putInt(t3, j2, i30);
                                        i20 |= i28;
                                    } else {
                                        zzn(t).zzb(i5, Long.valueOf(i30));
                                    }
                                    i19 = i5;
                                    zzayVar4 = zzayVar3;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i14 = i18;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 15:
                                i5 = i26;
                                zzayVar3 = zzayVar;
                                bArr = bArr;
                                if (i23 == 0) {
                                    iZze = zzax.zza(bArr, iZza, zzayVar3);
                                    unsafe2.putInt(t3, j2, zzbk.zzm(zzayVar3.zzfd));
                                    i20 |= i28;
                                    iZza2 = iZze;
                                    i19 = i5;
                                    zzayVar4 = zzayVar3;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i14 = i18;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 16:
                                i5 = i26;
                                i17 = -1;
                                if (i23 == 0) {
                                    bArr = bArr;
                                    int iZzb2 = zzax.zzb(bArr, iZza, zzayVar);
                                    unsafe2.putLong(t, j2, zzbk.zza(zzayVar.zzfe));
                                    i20 |= i28;
                                    i19 = i5;
                                    zzayVar4 = zzayVar;
                                    iZza2 = iZzb2;
                                    i2 = i2;
                                    i16 = i3;
                                } else {
                                    i13 = iZza;
                                    i14 = i18;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            case 17:
                                if (i23 == 3) {
                                    i5 = i26;
                                    i17 = -1;
                                    iZza2 = zza(zzdsVar.zzad(iZzai), bArr, iZza, i2, (i22 << 3) | 4, zzayVar);
                                    zzayVar3 = zzayVar;
                                    unsafe2.putObject(t3, j2, (i20 & i28) == 0 ? zzayVar3.zzff : zzci.zza(unsafe2.getObject(t3, j2), zzayVar3.zzff));
                                    i20 |= i28;
                                    bArr = bArr;
                                    i19 = i5;
                                    zzayVar4 = zzayVar3;
                                    i16 = i3;
                                } else {
                                    i5 = i26;
                                    i13 = iZza;
                                    i14 = i18;
                                    i18 = i14;
                                    i3 = i3;
                                    i12 = i13;
                                    unsafe = unsafe2;
                                    if (i5 == i3) {
                                    }
                                    iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                    zzdsVar = this;
                                    t3 = t;
                                    i16 = i3;
                                    i19 = i5;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                    zzayVar4 = zzayVar;
                                }
                                break;
                            default:
                                i5 = i26;
                                i13 = iZza;
                                i14 = i18;
                                i18 = i14;
                                i3 = i3;
                                i12 = i13;
                                unsafe = unsafe2;
                                if (i5 == i3) {
                                }
                                iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                                zzdsVar = this;
                                t3 = t;
                                i16 = i3;
                                i19 = i5;
                                unsafe2 = unsafe;
                                i17 = -1;
                                zzayVar4 = zzayVar;
                                break;
                        }
                    } else {
                        int i31 = iZza;
                        i11 = i18;
                        if (i25 == 27) {
                            if (i23 == 2) {
                                zzcn zzcnVarZzi = (zzcn) unsafe2.getObject(t3, j2);
                                if (!zzcnVarZzi.zzu()) {
                                    int size = zzcnVarZzi.size();
                                    zzcnVarZzi = zzcnVarZzi.zzi(size == 0 ? 10 : size << 1);
                                    unsafe2.putObject(t3, j2, zzcnVarZzi);
                                }
                                zzcn zzcnVar = zzcnVarZzi;
                                zzef zzefVarZzad = zzdsVar.zzad(iZzai);
                                i19 = i26;
                                iZza2 = zza((zzef<?>) zzefVarZzad, i19, bArr, i31, i2, (zzcn<?>) zzcnVar, zzayVar);
                                i16 = i3;
                                i18 = i11;
                                i20 = i20;
                            } else {
                                i9 = i31;
                                i10 = i26;
                            }
                            i17 = -1;
                            zzayVar4 = zzayVar;
                        } else {
                            i20 = i20;
                            if (i25 <= 49) {
                                unsafe = unsafe2;
                                iZza2 = zza(t, bArr, i31, i2, i26, i22, i23, iZzai, i24, i25, j2, zzayVar);
                                if (iZza2 == i31) {
                                    i10 = i26;
                                    i5 = i10;
                                    i12 = iZza2;
                                    i18 = i11;
                                    i20 = i20;
                                } else {
                                    i10 = i26;
                                    zzdsVar = this;
                                    t3 = t;
                                    bArr = bArr;
                                    i19 = i10;
                                    i2 = i2;
                                    i16 = i3;
                                    zzayVar4 = zzayVar;
                                    i18 = i11;
                                    i20 = i20;
                                    unsafe2 = unsafe;
                                    i17 = -1;
                                }
                            } else {
                                i9 = i31;
                                unsafe = unsafe2;
                                if (i25 != 50) {
                                    iZza2 = zza(t, bArr, i9, i2, i10, i22, i23, i24, i25, j2, iZzai, zzayVar);
                                    if (iZza2 == i9) {
                                        i10 = i26;
                                        i5 = i10;
                                        i12 = iZza2;
                                        i18 = i11;
                                        i20 = i20;
                                    } else {
                                        i10 = i26;
                                        zzdsVar = this;
                                        t3 = t;
                                        bArr = bArr;
                                        i19 = i10;
                                        i2 = i2;
                                        i16 = i3;
                                        zzayVar4 = zzayVar;
                                        i18 = i11;
                                        i20 = i20;
                                        unsafe2 = unsafe;
                                        i17 = -1;
                                    }
                                } else if (i23 == 2) {
                                    iZza2 = zza(t, bArr, i9, i2, iZzai, i22, j2, zzayVar);
                                    if (iZza2 == i9) {
                                        i10 = i26;
                                        i5 = i10;
                                        i12 = iZza2;
                                        i18 = i11;
                                        i20 = i20;
                                    } else {
                                        i10 = i26;
                                        zzdsVar = this;
                                        t3 = t;
                                        bArr = bArr;
                                        i19 = i10;
                                        i2 = i2;
                                        i16 = i3;
                                        zzayVar4 = zzayVar;
                                        i18 = i11;
                                        i20 = i20;
                                        unsafe2 = unsafe;
                                        i17 = -1;
                                    }
                                } else {
                                    i10 = i26;
                                    i5 = i10;
                                    i12 = i9;
                                    i18 = i11;
                                    i20 = i20;
                                }
                            }
                            if (i5 == i3) {
                            }
                            iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                            zzdsVar = this;
                            t3 = t;
                            i16 = i3;
                            i19 = i5;
                            unsafe2 = unsafe;
                            i17 = -1;
                            zzayVar4 = zzayVar;
                        }
                    }
                } else {
                    i9 = iZza;
                    i10 = i8;
                    i11 = i18;
                }
                unsafe = unsafe2;
                i5 = i10;
                i12 = i9;
                i18 = i11;
                i20 = i20;
                if (i5 == i3) {
                }
                iZza2 = zza(i5, bArr, i12, i2, t, zzayVar);
                zzdsVar = this;
                t3 = t;
                i16 = i3;
                i19 = i5;
                unsafe2 = unsafe;
                i17 = -1;
                zzayVar4 = zzayVar;
            } else {
                int i32 = i18;
                unsafe = unsafe2;
                i3 = i16;
                i4 = iZza2;
                i5 = i19;
                i6 = i32;
                i7 = -1;
            }
        }
        if (i6 != i7) {
            t2 = t;
            unsafe.putInt(t2, i6, i20);
        } else {
            t2 = t;
        }
        int[] iArr2 = this.zzmt;
        if (iArr2 != null) {
            Object objZza2 = null;
            for (int i33 : iArr2) {
                zzex zzexVar = this.zzmx;
                int i34 = this.zzmi[i33];
                Object objZzo = zzfd.zzo(t2, zzag(i33) & 1048575);
                if (objZzo != null && (zzckVarZzaf = zzaf(i33)) != null) {
                    objZza2 = zza(i33, i34, this.zzmz.zzg(objZzo), zzckVarZzaf, objZza2, (zzex<UT, Object>) zzexVar);
                }
                objZza2 = (zzey) objZza2;
            }
            if (objZza2 != null) {
                this.zzmx.zzf(t2, objZza2);
            }
        }
        if (i3 == 0) {
            if (i4 != i2) {
                throw zzco.zzbo();
            }
        } else if (i4 > i2 || i5 != i3) {
            throw zzco.zzbo();
        }
        return i4;
    }

    private static int zza(byte[] bArr, int i, int i2, zzfl zzflVar, Class<?> cls, zzay zzayVar) throws IOException {
        int iZzb;
        Object objValueOf;
        Object objValueOf2;
        Object objValueOf3;
        int iZzm;
        long jZza;
        switch (zzdt.zzgq[zzflVar.ordinal()]) {
            case 1:
                iZzb = zzax.zzb(bArr, i, zzayVar);
                objValueOf = Boolean.valueOf(zzayVar.zzfe != 0);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 2:
                return zzax.zze(bArr, i, zzayVar);
            case 3:
                objValueOf2 = Double.valueOf(zzax.zze(bArr, i));
                zzayVar.zzff = objValueOf2;
                return i + 8;
            case 4:
            case 5:
                objValueOf3 = Integer.valueOf(zzax.zzc(bArr, i));
                zzayVar.zzff = objValueOf3;
                return i + 4;
            case 6:
            case 7:
                objValueOf2 = Long.valueOf(zzax.zzd(bArr, i));
                zzayVar.zzff = objValueOf2;
                return i + 8;
            case 8:
                objValueOf3 = Float.valueOf(zzax.zzf(bArr, i));
                zzayVar.zzff = objValueOf3;
                return i + 4;
            case 9:
            case 10:
            case 11:
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzayVar.zzfd;
                objValueOf = Integer.valueOf(iZzm);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 12:
            case 13:
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzayVar.zzfe;
                objValueOf = Long.valueOf(jZza);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 14:
                return zza((zzef) zzea.zzcm().zze(cls), bArr, i, i2, zzayVar);
            case 15:
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzbk.zzm(zzayVar.zzfd);
                objValueOf = Integer.valueOf(iZzm);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 16:
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzbk.zza(zzayVar.zzfe);
                objValueOf = Long.valueOf(jZza);
                zzayVar.zzff = objValueOf;
                return iZzb;
            case 17:
                return zzax.zzd(bArr, i, zzayVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    static <T> zzds<T> zza(Class<T> cls, zzdm zzdmVar, zzdw zzdwVar, zzcy zzcyVar, zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdj zzdjVar) {
        int iZzcu;
        int i;
        int i2;
        int iZza;
        int iZza2;
        int iZzdg;
        if (!(zzdmVar instanceof zzec)) {
            ((zzes) zzdmVar).zzcf();
            throw new NoSuchMethodError();
        }
        zzec zzecVar = (zzec) zzdmVar;
        boolean z = zzecVar.zzcf() == zzcg.zzg.zzkm;
        if (zzecVar.getFieldCount() == 0) {
            iZzcu = 0;
            i = 0;
            i2 = 0;
        } else {
            int iZzcp = zzecVar.zzcp();
            int iZzcq = zzecVar.zzcq();
            iZzcu = zzecVar.zzcu();
            i = iZzcp;
            i2 = iZzcq;
        }
        int[] iArr = new int[iZzcu << 2];
        Object[] objArr = new Object[iZzcu << 1];
        int[] iArr2 = zzecVar.zzcr() > 0 ? new int[zzecVar.zzcr()] : null;
        int[] iArr3 = zzecVar.zzcs() > 0 ? new int[zzecVar.zzcs()] : null;
        zzed zzedVarZzco = zzecVar.zzco();
        if (zzedVarZzco.next()) {
            int iZzcx = zzedVarZzco.zzcx();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                if (iZzcx >= zzecVar.zzcv() || i3 >= ((iZzcx - i) << 2)) {
                    if (zzedVarZzco.zzda()) {
                        iZza = (int) zzfd.zza(zzedVarZzco.zzdb());
                        iZza2 = (int) zzfd.zza(zzedVarZzco.zzdc());
                        iZzdg = 0;
                    } else {
                        iZza = (int) zzfd.zza(zzedVarZzco.zzdd());
                        if (zzedVarZzco.zzde()) {
                            iZza2 = (int) zzfd.zza(zzedVarZzco.zzdf());
                            iZzdg = zzedVarZzco.zzdg();
                        } else {
                            iZza2 = 0;
                            iZzdg = 0;
                        }
                    }
                    iArr[i3] = zzedVarZzco.zzcx();
                    int i6 = i3 + 1;
                    iArr[i6] = (zzedVarZzco.zzdi() ? 536870912 : 0) | (zzedVarZzco.zzdh() ? 268435456 : 0) | (zzedVarZzco.zzcy() << 20) | iZza;
                    iArr[i3 + 2] = iZza2 | (iZzdg << 20);
                    if (zzedVarZzco.zzdl() != null) {
                        int i7 = (i3 / 4) << 1;
                        objArr[i7] = zzedVarZzco.zzdl();
                        if (zzedVarZzco.zzdj() != null) {
                            objArr[i7 + 1] = zzedVarZzco.zzdj();
                        } else if (zzedVarZzco.zzdk() != null) {
                            objArr[i7 + 1] = zzedVarZzco.zzdk();
                        }
                    } else if (zzedVarZzco.zzdj() != null) {
                        objArr[((i3 / 4) << 1) + 1] = zzedVarZzco.zzdj();
                    } else if (zzedVarZzco.zzdk() != null) {
                        objArr[((i3 / 4) << 1) + 1] = zzedVarZzco.zzdk();
                    }
                    int iZzcy = zzedVarZzco.zzcy();
                    if (iZzcy == zzcb.MAP.ordinal()) {
                        iArr2[i4] = i3;
                        i4++;
                    } else if (iZzcy >= 18 && iZzcy <= 49) {
                        iArr3[i5] = iArr[i6] & 1048575;
                        i5++;
                    }
                    if (!zzedVarZzco.next()) {
                        break;
                    }
                    iZzcx = zzedVarZzco.zzcx();
                } else {
                    for (int i8 = 0; i8 < 4; i8++) {
                        iArr[i3 + i8] = -1;
                    }
                }
                i3 += 4;
            }
        }
        return new zzds<>(iArr, objArr, i, i2, zzecVar.zzcv(), zzecVar.zzch(), z, false, zzecVar.zzct(), iArr2, iArr3, zzdwVar, zzcyVar, zzexVar, zzbuVar, zzdjVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzck<?> zzckVar, UB ub, zzex<UT, UB> zzexVar) {
        zzdh<?, ?> zzdhVarZzl = this.zzmz.zzl(zzae(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (zzckVar.zzb(((Integer) next.getValue()).intValue()) == null) {
                if (ub == null) {
                    ub = zzexVar.zzdz();
                }
                zzbg zzbgVarZzk = zzbb.zzk(zzdg.zza(zzdhVarZzl, next.getKey(), next.getValue()));
                try {
                    zzdg.zza(zzbgVarZzk.zzae(), zzdhVarZzl, next.getKey(), next.getValue());
                    zzexVar.zza(ub, i2, zzbgVarZzk.zzad());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    private static void zza(int i, Object obj, zzfr zzfrVar) throws IOException {
        if (obj instanceof String) {
            zzfrVar.zza(i, (String) obj);
        } else {
            zzfrVar.zza(i, (zzbb) obj);
        }
    }

    private static <UT, UB> void zza(zzex<UT, UB> zzexVar, T t, zzfr zzfrVar) throws IOException {
        zzexVar.zza(zzexVar.zzq(t), zzfrVar);
    }

    private final <K, V> void zza(zzfr zzfrVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzfrVar.zza(i, this.zzmz.zzl(zzae(i2)), this.zzmz.zzh(obj));
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzag = zzag(i) & 1048575;
        if (zza(t2, i)) {
            Object objZzo = zzfd.zzo(t, jZzag);
            Object objZzo2 = zzfd.zzo(t2, jZzag);
            if (objZzo != null && objZzo2 != null) {
                zzfd.zza(t, jZzag, zzci.zza(objZzo, objZzo2));
                zzb(t, i);
            } else if (objZzo2 != null) {
                zzfd.zza(t, jZzag, objZzo2);
                zzb(t, i);
            }
        }
    }

    private final boolean zza(T t, int i) {
        if (!this.zzmq) {
            int iZzah = zzah(i);
            return (zzfd.zzj(t, (long) (iZzah & 1048575)) & (1 << (iZzah >>> 20))) != 0;
        }
        int iZzag = zzag(i);
        long j = iZzag & 1048575;
        switch ((iZzag & 267386880) >>> 20) {
            case 0:
                return zzfd.zzn(t, j) != 0.0d;
            case 1:
                return zzfd.zzm(t, j) != 0.0f;
            case 2:
                return zzfd.zzk(t, j) != 0;
            case 3:
                return zzfd.zzk(t, j) != 0;
            case 4:
                return zzfd.zzj(t, j) != 0;
            case 5:
                return zzfd.zzk(t, j) != 0;
            case 6:
                return zzfd.zzj(t, j) != 0;
            case 7:
                return zzfd.zzl(t, j);
            case 8:
                Object objZzo = zzfd.zzo(t, j);
                if (objZzo instanceof String) {
                    return !((String) objZzo).isEmpty();
                }
                if (objZzo instanceof zzbb) {
                    return !zzbb.zzfi.equals(objZzo);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzfd.zzo(t, j) != null;
            case 10:
                return !zzbb.zzfi.equals(zzfd.zzo(t, j));
            case 11:
                return zzfd.zzj(t, j) != 0;
            case 12:
                return zzfd.zzj(t, j) != 0;
            case 13:
                return zzfd.zzj(t, j) != 0;
            case 14:
                return zzfd.zzk(t, j) != 0;
            case 15:
                return zzfd.zzj(t, j) != 0;
            case 16:
                return zzfd.zzk(t, j) != 0;
            case 17:
                return zzfd.zzo(t, j) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzfd.zzj(t, (long) (zzah(i2) & 1048575)) == i;
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzmq) {
            return zza(t, i);
        }
        return (i2 & i3) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzef zzefVar) {
        return zzefVar.zzo(zzfd.zzo(obj, i & 1048575));
    }

    private final zzef zzad(int i) {
        int i2 = (i / 4) << 1;
        zzef zzefVar = (zzef) this.zzmj[i2];
        if (zzefVar != null) {
            return zzefVar;
        }
        zzef<T> zzefVarZze = zzea.zzcm().zze((Class) this.zzmj[i2 + 1]);
        this.zzmj[i2] = zzefVarZze;
        return zzefVarZze;
    }

    private final Object zzae(int i) {
        return this.zzmj[(i / 4) << 1];
    }

    private final zzck<?> zzaf(int i) {
        return (zzck) this.zzmj[((i / 4) << 1) + 1];
    }

    private final int zzag(int i) {
        return this.zzmi[i + 1];
    }

    private final int zzah(int i) {
        return this.zzmi[i + 2];
    }

    private final int zzai(int i) {
        int i2 = this.zzmk;
        if (i >= i2) {
            int i3 = this.zzmm;
            if (i < i3) {
                int i4 = (i - i2) << 2;
                if (this.zzmi[i4] == i) {
                    return i4;
                }
                return -1;
            }
            if (i <= this.zzml) {
                int i5 = i3 - i2;
                int length = (this.zzmi.length / 4) - 1;
                while (i5 <= length) {
                    int i6 = (length + i5) >>> 1;
                    int i7 = i6 << 2;
                    int i8 = this.zzmi[i7];
                    if (i == i8) {
                        return i7;
                    }
                    if (i < i8) {
                        length = i6 - 1;
                    } else {
                        i5 = i6 + 1;
                    }
                }
            }
        }
        return -1;
    }

    private final void zzb(T t, int i) {
        if (this.zzmq) {
            return;
        }
        int iZzah = zzah(i);
        long j = iZzah & 1048575;
        zzfd.zza((Object) t, j, zzfd.zzj(t, j) | (1 << (iZzah >>> 20)));
    }

    private final void zzb(T t, int i, int i2) {
        zzfd.zza((Object) t, zzah(i2) & 1048575, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:7:0x0021  */
    private final void zzb(T t, zzfr zzfrVar) throws IOException {
        Iterator it;
        Map.Entry<?, ?> entry;
        int i;
        boolean z;
        if (this.zzmo) {
            zzby<T> zzbyVarZza = this.zzmy.zza(t);
            if (zzbyVarZza.isEmpty()) {
                it = null;
                entry = null;
            } else {
                it = zzbyVarZza.iterator();
                entry = (Map.Entry) it.next();
            }
        } else {
            it = null;
            entry = null;
        }
        int length = this.zzmi.length;
        Unsafe unsafe = zzmh;
        int i2 = -1;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 4) {
            int iZzag = zzag(i4);
            int[] iArr = this.zzmi;
            int i5 = iArr[i4];
            int i6 = (267386880 & iZzag) >>> 20;
            if (this.zzmq || i6 > 17) {
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
            while (entry != null && this.zzmy.zza(entry) <= i5) {
                this.zzmy.zza(zzfrVar, entry);
                entry = it.hasNext() ? (Map.Entry) it.next() : null;
            }
            long j = iZzag & 1048575;
            switch (i6) {
                case 0:
                    if ((i & i3) != 0) {
                        zzfrVar.zza(i5, zzfd.zzn(t, j));
                    }
                    break;
                case 1:
                    if ((i & i3) != 0) {
                        zzfrVar.zza(i5, zzfd.zzm(t, j));
                    }
                    break;
                case 2:
                    if ((i & i3) != 0) {
                        zzfrVar.zzi(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 3:
                    if ((i & i3) != 0) {
                        zzfrVar.zza(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 4:
                    if ((i & i3) != 0) {
                        zzfrVar.zzc(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 5:
                    if ((i & i3) != 0) {
                        zzfrVar.zzc(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 6:
                    if ((i & i3) != 0) {
                        zzfrVar.zzf(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 7:
                    if ((i & i3) != 0) {
                        zzfrVar.zzb(i5, zzfd.zzl(t, j));
                    }
                    break;
                case 8:
                    if ((i & i3) != 0) {
                        zza(i5, unsafe.getObject(t, j), zzfrVar);
                    }
                    break;
                case 9:
                    if ((i & i3) != 0) {
                        zzfrVar.zza(i5, unsafe.getObject(t, j), zzad(i4));
                    }
                    break;
                case 10:
                    if ((i & i3) != 0) {
                        zzfrVar.zza(i5, (zzbb) unsafe.getObject(t, j));
                    }
                    break;
                case 11:
                    if ((i & i3) != 0) {
                        zzfrVar.zzd(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 12:
                    if ((i & i3) != 0) {
                        zzfrVar.zzn(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 13:
                    if ((i & i3) != 0) {
                        zzfrVar.zzm(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 14:
                    if ((i & i3) != 0) {
                        zzfrVar.zzj(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 15:
                    if ((i & i3) != 0) {
                        zzfrVar.zze(i5, unsafe.getInt(t, j));
                    }
                    break;
                case 16:
                    if ((i & i3) != 0) {
                        zzfrVar.zzb(i5, unsafe.getLong(t, j));
                    }
                    break;
                case 17:
                    if ((i & i3) != 0) {
                        zzfrVar.zzb(i5, unsafe.getObject(t, j), zzad(i4));
                    }
                    break;
                case 18:
                    zzeh.zza(this.zzmi[i4], (List<Double>) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 19:
                    zzeh.zzb(this.zzmi[i4], (List<Float>) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 20:
                    zzeh.zzc(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 21:
                    zzeh.zzd(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 22:
                    zzeh.zzh(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 23:
                    zzeh.zzf(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 24:
                    zzeh.zzk(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 25:
                    zzeh.zzn(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 26:
                    zzeh.zza(this.zzmi[i4], (List<String>) unsafe.getObject(t, j), zzfrVar);
                    break;
                case 27:
                    zzeh.zza(this.zzmi[i4], (List<?>) unsafe.getObject(t, j), zzfrVar, zzad(i4));
                    break;
                case 28:
                    zzeh.zzb(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar);
                    break;
                case 29:
                    z = false;
                    zzeh.zzi(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 30:
                    z = false;
                    zzeh.zzm(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 31:
                    z = false;
                    zzeh.zzl(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 32:
                    z = false;
                    zzeh.zzg(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 33:
                    z = false;
                    zzeh.zzj(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 34:
                    z = false;
                    zzeh.zze(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, false);
                    break;
                case 35:
                    zzeh.zza(this.zzmi[i4], (List<Double>) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 36:
                    zzeh.zzb(this.zzmi[i4], (List<Float>) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 37:
                    zzeh.zzc(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 38:
                    zzeh.zzd(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 39:
                    zzeh.zzh(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 40:
                    zzeh.zzf(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 41:
                    zzeh.zzk(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 42:
                    zzeh.zzn(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 43:
                    zzeh.zzi(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 44:
                    zzeh.zzm(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 45:
                    zzeh.zzl(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 46:
                    zzeh.zzg(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 47:
                    zzeh.zzj(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 48:
                    zzeh.zze(this.zzmi[i4], (List) unsafe.getObject(t, j), zzfrVar, true);
                    break;
                case 49:
                    zzeh.zzb(this.zzmi[i4], (List<?>) unsafe.getObject(t, j), zzfrVar, zzad(i4));
                    break;
                case 50:
                    zza(zzfrVar, i5, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zza(i5, zze(t, j));
                    }
                    break;
                case 52:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zza(i5, zzf(t, j));
                    }
                    break;
                case 53:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzi(i5, zzh(t, j));
                    }
                    break;
                case 54:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zza(i5, zzh(t, j));
                    }
                    break;
                case 55:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzc(i5, zzg(t, j));
                    }
                    break;
                case 56:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzc(i5, zzh(t, j));
                    }
                    break;
                case 57:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzf(i5, zzg(t, j));
                    }
                    break;
                case 58:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzb(i5, zzi(t, j));
                    }
                    break;
                case 59:
                    if (zza(t, i5, i4)) {
                        zza(i5, unsafe.getObject(t, j), zzfrVar);
                    }
                    break;
                case 60:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zza(i5, unsafe.getObject(t, j), zzad(i4));
                    }
                    break;
                case 61:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zza(i5, (zzbb) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzd(i5, zzg(t, j));
                    }
                    break;
                case 63:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzn(i5, zzg(t, j));
                    }
                    break;
                case 64:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzm(i5, zzg(t, j));
                    }
                    break;
                case 65:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzj(i5, zzh(t, j));
                    }
                    break;
                case 66:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zze(i5, zzg(t, j));
                    }
                    break;
                case 67:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzb(i5, zzh(t, j));
                    }
                    break;
                case 68:
                    if (zza(t, i5, i4)) {
                        zzfrVar.zzb(i5, unsafe.getObject(t, j), zzad(i4));
                    }
                    break;
                default:
                    break;
            }
        }
        while (entry != null) {
            this.zzmy.zza(zzfrVar, entry);
            entry = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        zza(this.zzmx, t, zzfrVar);
    }

    private final void zzb(T t, T t2, int i) {
        int iZzag = zzag(i);
        int i2 = this.zzmi[i];
        long j = iZzag & 1048575;
        if (zza(t2, i2, i)) {
            Object objZzo = zzfd.zzo(t, j);
            Object objZzo2 = zzfd.zzo(t2, j);
            if (objZzo != null && objZzo2 != null) {
                zzfd.zza(t, j, zzci.zza(objZzo, objZzo2));
                zzb(t, i2, i);
            } else if (objZzo2 != null) {
                zzfd.zza(t, j, objZzo2);
                zzb(t, i2, i);
            }
        }
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zzfd.zzo(obj, j);
    }

    private static <T> double zze(T t, long j) {
        return ((Double) zzfd.zzo(t, j)).doubleValue();
    }

    private static <T> float zzf(T t, long j) {
        return ((Float) zzfd.zzo(t, j)).floatValue();
    }

    private static <T> int zzg(T t, long j) {
        return ((Integer) zzfd.zzo(t, j)).intValue();
    }

    private static <T> long zzh(T t, long j) {
        return ((Long) zzfd.zzo(t, j)).longValue();
    }

    private static <T> boolean zzi(T t, long j) {
        return ((Boolean) zzfd.zzo(t, j)).booleanValue();
    }

    private static zzey zzn(Object obj) {
        zzcg zzcgVar = (zzcg) obj;
        zzey zzeyVar = zzcgVar.zzjp;
        if (zzeyVar != zzey.zzea()) {
            return zzeyVar;
        }
        zzey zzeyVarZzeb = zzey.zzeb();
        zzcgVar.zzjp = zzeyVarZzeb;
        return zzeyVarZzeb;
    }

    /* JADX WARN: Code duplicated, block: B:103:0x01a2  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean equals(T t, T t2) {
        int length = this.zzmi.length;
        int i = 0;
        while (true) {
            boolean zZzd = true;
            if (i >= length) {
                if (!this.zzmx.zzq(t).equals(this.zzmx.zzq(t2))) {
                    return false;
                }
                if (this.zzmo) {
                    return this.zzmy.zza(t).equals(this.zzmy.zza(t2));
                }
                return true;
            }
            int iZzag = zzag(i);
            long j = iZzag & 1048575;
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    if (!zzc(t, t2, i) || zzfd.zzk(t, j) != zzfd.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 1:
                    if (!zzc(t, t2, i) || zzfd.zzj(t, j) != zzfd.zzj(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 2:
                    if (!zzc(t, t2, i) || zzfd.zzk(t, j) != zzfd.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 3:
                    if (!zzc(t, t2, i) || zzfd.zzk(t, j) != zzfd.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 4:
                    if (!zzc(t, t2, i) || zzfd.zzj(t, j) != zzfd.zzj(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 5:
                    if (!zzc(t, t2, i) || zzfd.zzk(t, j) != zzfd.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 6:
                    if (!zzc(t, t2, i) || zzfd.zzj(t, j) != zzfd.zzj(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 7:
                    if (!zzc(t, t2, i) || zzfd.zzl(t, j) != zzfd.zzl(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 8:
                    if (!zzc(t, t2, i) || !zzeh.zzd(zzfd.zzo(t, j), zzfd.zzo(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 9:
                    if (!zzc(t, t2, i) || !zzeh.zzd(zzfd.zzo(t, j), zzfd.zzo(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 10:
                    if (!zzc(t, t2, i) || !zzeh.zzd(zzfd.zzo(t, j), zzfd.zzo(t2, j))) {
                        zZzd = false;
                    }
                    break;
                case 11:
                    if (!zzc(t, t2, i) || zzfd.zzj(t, j) != zzfd.zzj(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 12:
                    if (!zzc(t, t2, i) || zzfd.zzj(t, j) != zzfd.zzj(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 13:
                    if (!zzc(t, t2, i) || zzfd.zzj(t, j) != zzfd.zzj(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 14:
                    if (!zzc(t, t2, i) || zzfd.zzk(t, j) != zzfd.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 15:
                    if (!zzc(t, t2, i) || zzfd.zzj(t, j) != zzfd.zzj(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 16:
                    if (!zzc(t, t2, i) || zzfd.zzk(t, j) != zzfd.zzk(t2, j)) {
                        zZzd = false;
                    }
                    break;
                case 17:
                    if (!zzc(t, t2, i) || !zzeh.zzd(zzfd.zzo(t, j), zzfd.zzo(t2, j))) {
                        zZzd = false;
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
                case 50:
                    zZzd = zzeh.zzd(zzfd.zzo(t, j), zzfd.zzo(t2, j));
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
                    long jZzah = zzah(i) & 1048575;
                    if (zzfd.zzj(t, jZzah) != zzfd.zzj(t2, jZzah) || !zzeh.zzd(zzfd.zzo(t, j), zzfd.zzo(t2, j))) {
                        zZzd = false;
                    }
                    break;
            }
            if (!zZzd) {
                return false;
            }
            i += 4;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:33:0x0061  */
    /* JADX WARN: Code duplicated, block: B:48:0x0093  */
    /* JADX WARN: Code duplicated, block: B:54:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:64:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:68:0x00e2 A[PHI: r3
      0x00e2: PHI (r3v13 java.lang.Object) = (r3v11 java.lang.Object), (r3v14 java.lang.Object) binds: [B:67:0x00e0, B:62:0x00ce] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:70:0x00ea  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    public final int hashCode(T t) {
        int i;
        double dZzn;
        float fZzm;
        long jZzk;
        int iZzj;
        boolean zZzl;
        Object objZzo;
        Object objZzo2;
        int length = this.zzmi.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 4) {
            int iZzag = zzag(i3);
            int i4 = this.zzmi[i3];
            long j = 1048575 & iZzag;
            int iHashCode = 37;
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    dZzn = zzfd.zzn(t, j);
                    jZzk = Double.doubleToLongBits(dZzn);
                    iZzj = zzci.zzl(jZzk);
                    i2 = i + iZzj;
                    break;
                case 1:
                    i = i2 * 53;
                    fZzm = zzfd.zzm(t, j);
                    iZzj = Float.floatToIntBits(fZzm);
                    i2 = i + iZzj;
                    break;
                case 2:
                case 3:
                case 5:
                case 14:
                case 16:
                    i = i2 * 53;
                    jZzk = zzfd.zzk(t, j);
                    iZzj = zzci.zzl(jZzk);
                    i2 = i + iZzj;
                    break;
                case 4:
                case 6:
                case 11:
                case 12:
                case 13:
                case 15:
                    i = i2 * 53;
                    iZzj = zzfd.zzj(t, j);
                    i2 = i + iZzj;
                    break;
                case 7:
                    i = i2 * 53;
                    zZzl = zzfd.zzl(t, j);
                    iZzj = zzci.zzc(zZzl);
                    i2 = i + iZzj;
                    break;
                case 8:
                    i = i2 * 53;
                    iZzj = ((String) zzfd.zzo(t, j)).hashCode();
                    i2 = i + iZzj;
                    break;
                case 9:
                    objZzo = zzfd.zzo(t, j);
                    if (objZzo != null) {
                        iHashCode = objZzo.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
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
                case 50:
                    i = i2 * 53;
                    objZzo2 = zzfd.zzo(t, j);
                    iZzj = objZzo2.hashCode();
                    i2 = i + iZzj;
                    break;
                case 17:
                    objZzo = zzfd.zzo(t, j);
                    if (objZzo != null) {
                        iHashCode = objZzo.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 51:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        dZzn = zze(t, j);
                        jZzk = Double.doubleToLongBits(dZzn);
                        iZzj = zzci.zzl(jZzk);
                        i2 = i + iZzj;
                    }
                    break;
                case 52:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        fZzm = zzf(t, j);
                        iZzj = Float.floatToIntBits(fZzm);
                        i2 = i + iZzj;
                    }
                    break;
                case 53:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        jZzk = zzh(t, j);
                        iZzj = zzci.zzl(jZzk);
                        i2 = i + iZzj;
                    }
                    break;
                case 54:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        jZzk = zzh(t, j);
                        iZzj = zzci.zzl(jZzk);
                        i2 = i + iZzj;
                    }
                    break;
                case 55:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzj = zzg(t, j);
                        i2 = i + iZzj;
                    }
                    break;
                case 56:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        jZzk = zzh(t, j);
                        iZzj = zzci.zzl(jZzk);
                        i2 = i + iZzj;
                    }
                    break;
                case 57:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzj = zzg(t, j);
                        i2 = i + iZzj;
                    }
                    break;
                case 58:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        zZzl = zzi(t, j);
                        iZzj = zzci.zzc(zZzl);
                        i2 = i + iZzj;
                    }
                    break;
                case 59:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzj = ((String) zzfd.zzo(t, j)).hashCode();
                        i2 = i + iZzj;
                    }
                    break;
                case 60:
                    if (zza(t, i4, i3)) {
                        objZzo2 = zzfd.zzo(t, j);
                        i = i2 * 53;
                        iZzj = objZzo2.hashCode();
                        i2 = i + iZzj;
                    }
                    break;
                case 61:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        objZzo2 = zzfd.zzo(t, j);
                        iZzj = objZzo2.hashCode();
                        i2 = i + iZzj;
                    }
                    break;
                case 62:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzj = zzg(t, j);
                        i2 = i + iZzj;
                    }
                    break;
                case 63:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzj = zzg(t, j);
                        i2 = i + iZzj;
                    }
                    break;
                case 64:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzj = zzg(t, j);
                        i2 = i + iZzj;
                    }
                    break;
                case 65:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        jZzk = zzh(t, j);
                        iZzj = zzci.zzl(jZzk);
                        i2 = i + iZzj;
                    }
                    break;
                case 66:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZzj = zzg(t, j);
                        i2 = i + iZzj;
                    }
                    break;
                case 67:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        jZzk = zzh(t, j);
                        iZzj = zzci.zzl(jZzk);
                        i2 = i + iZzj;
                    }
                    break;
                case 68:
                    if (zza(t, i4, i3)) {
                        objZzo2 = zzfd.zzo(t, j);
                        i = i2 * 53;
                        iZzj = objZzo2.hashCode();
                        i2 = i + iZzj;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzmx.zzq(t).hashCode();
        return this.zzmo ? (iHashCode2 * 53) + this.zzmy.zza(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final T newInstance() {
        return (T) this.zzmv.newInstance(this.zzmn);
    }

    /* JADX WARN: Code duplicated, block: B:112:0x0385  */
    /* JADX WARN: Code duplicated, block: B:139:0x0400  */
    /* JADX WARN: Code duplicated, block: B:142:0x0413  */
    /* JADX WARN: Code duplicated, block: B:145:0x0428  */
    /* JADX WARN: Code duplicated, block: B:192:0x04ee  */
    /* JADX WARN: Code duplicated, block: B:295:0x0842  */
    /* JADX WARN: Code duplicated, block: B:322:0x08bd  */
    /* JADX WARN: Code duplicated, block: B:325:0x08d0  */
    /* JADX WARN: Code duplicated, block: B:328:0x08e5  */
    /* JADX WARN: Code duplicated, block: B:9:0x0030  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zza(T t, zzfr zzfrVar) throws IOException {
        Iterator it;
        Map.Entry<?, ?> entry;
        double dZzn;
        float fZzm;
        long jZzk;
        long jZzk2;
        int iZzj;
        long jZzk3;
        int iZzj2;
        boolean zZzl;
        int iZzj3;
        int iZzj4;
        int iZzj5;
        long jZzk4;
        int iZzj6;
        long jZzk5;
        Iterator itDescendingIterator;
        Map.Entry<?, ?> entry2;
        double dZzn2;
        float fZzm2;
        long jZzk6;
        long jZzk7;
        int iZzj7;
        long jZzk8;
        int iZzj8;
        boolean zZzl2;
        int iZzj9;
        int iZzj10;
        int iZzj11;
        long jZzk9;
        int iZzj12;
        long jZzk10;
        if (zzfrVar.zzaj() == zzcg.zzg.zzkp) {
            zza(this.zzmx, t, zzfrVar);
            if (this.zzmo) {
                zzby<T> zzbyVarZza = this.zzmy.zza(t);
                if (zzbyVarZza.isEmpty()) {
                    itDescendingIterator = null;
                    entry2 = null;
                } else {
                    itDescendingIterator = zzbyVarZza.descendingIterator();
                    entry2 = (Map.Entry) itDescendingIterator.next();
                }
            } else {
                itDescendingIterator = null;
                entry2 = null;
            }
            for (int length = this.zzmi.length - 4; length >= 0; length -= 4) {
                int iZzag = zzag(length);
                int i = this.zzmi[length];
                while (entry2 != null && this.zzmy.zza(entry2) > i) {
                    this.zzmy.zza(zzfrVar, entry2);
                    entry2 = itDescendingIterator.hasNext() ? (Map.Entry) itDescendingIterator.next() : null;
                }
                switch ((iZzag & 267386880) >>> 20) {
                    case 0:
                        if (zza(t, length)) {
                            dZzn2 = zzfd.zzn(t, iZzag & 1048575);
                            zzfrVar.zza(i, dZzn2);
                        }
                        break;
                    case 1:
                        if (zza(t, length)) {
                            fZzm2 = zzfd.zzm(t, iZzag & 1048575);
                            zzfrVar.zza(i, fZzm2);
                        }
                        break;
                    case 2:
                        if (zza(t, length)) {
                            jZzk6 = zzfd.zzk(t, iZzag & 1048575);
                            zzfrVar.zzi(i, jZzk6);
                        }
                        break;
                    case 3:
                        if (zza(t, length)) {
                            jZzk7 = zzfd.zzk(t, iZzag & 1048575);
                            zzfrVar.zza(i, jZzk7);
                        }
                        break;
                    case 4:
                        if (zza(t, length)) {
                            iZzj7 = zzfd.zzj(t, iZzag & 1048575);
                            zzfrVar.zzc(i, iZzj7);
                        }
                        break;
                    case 5:
                        if (zza(t, length)) {
                            jZzk8 = zzfd.zzk(t, iZzag & 1048575);
                            zzfrVar.zzc(i, jZzk8);
                        }
                        break;
                    case 6:
                        if (zza(t, length)) {
                            iZzj8 = zzfd.zzj(t, iZzag & 1048575);
                            zzfrVar.zzf(i, iZzj8);
                        }
                        break;
                    case 7:
                        if (zza(t, length)) {
                            zZzl2 = zzfd.zzl(t, iZzag & 1048575);
                            zzfrVar.zzb(i, zZzl2);
                        }
                        break;
                    case 8:
                        if (zza(t, length)) {
                            zza(i, zzfd.zzo(t, iZzag & 1048575), zzfrVar);
                        }
                        break;
                    case 9:
                        if (zza(t, length)) {
                            zzfrVar.zza(i, zzfd.zzo(t, iZzag & 1048575), zzad(length));
                        }
                        break;
                    case 10:
                        if (zza(t, length)) {
                            zzfrVar.zza(i, (zzbb) zzfd.zzo(t, iZzag & 1048575));
                        }
                        break;
                    case 11:
                        if (zza(t, length)) {
                            iZzj9 = zzfd.zzj(t, iZzag & 1048575);
                            zzfrVar.zzd(i, iZzj9);
                        }
                        break;
                    case 12:
                        if (zza(t, length)) {
                            iZzj10 = zzfd.zzj(t, iZzag & 1048575);
                            zzfrVar.zzn(i, iZzj10);
                        }
                        break;
                    case 13:
                        if (zza(t, length)) {
                            iZzj11 = zzfd.zzj(t, iZzag & 1048575);
                            zzfrVar.zzm(i, iZzj11);
                        }
                        break;
                    case 14:
                        if (zza(t, length)) {
                            jZzk9 = zzfd.zzk(t, iZzag & 1048575);
                            zzfrVar.zzj(i, jZzk9);
                        }
                        break;
                    case 15:
                        if (zza(t, length)) {
                            iZzj12 = zzfd.zzj(t, iZzag & 1048575);
                            zzfrVar.zze(i, iZzj12);
                        }
                        break;
                    case 16:
                        if (zza(t, length)) {
                            jZzk10 = zzfd.zzk(t, iZzag & 1048575);
                            zzfrVar.zzb(i, jZzk10);
                        }
                        break;
                    case 17:
                        if (zza(t, length)) {
                            zzfrVar.zzb(i, zzfd.zzo(t, iZzag & 1048575), zzad(length));
                        }
                        break;
                    case 18:
                        zzeh.zza(this.zzmi[length], (List<Double>) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 19:
                        zzeh.zzb(this.zzmi[length], (List<Float>) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 20:
                        zzeh.zzc(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 21:
                        zzeh.zzd(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 22:
                        zzeh.zzh(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 23:
                        zzeh.zzf(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 24:
                        zzeh.zzk(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 25:
                        zzeh.zzn(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 26:
                        zzeh.zza(this.zzmi[length], (List<String>) zzfd.zzo(t, iZzag & 1048575), zzfrVar);
                        break;
                    case 27:
                        zzeh.zza(this.zzmi[length], (List<?>) zzfd.zzo(t, iZzag & 1048575), zzfrVar, zzad(length));
                        break;
                    case 28:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar);
                        break;
                    case 29:
                        zzeh.zzi(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 30:
                        zzeh.zzm(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 31:
                        zzeh.zzl(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 32:
                        zzeh.zzg(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 33:
                        zzeh.zzj(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 34:
                        zzeh.zze(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, false);
                        break;
                    case 35:
                        zzeh.zza(this.zzmi[length], (List<Double>) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 36:
                        zzeh.zzb(this.zzmi[length], (List<Float>) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 37:
                        zzeh.zzc(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 38:
                        zzeh.zzd(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 39:
                        zzeh.zzh(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 40:
                        zzeh.zzf(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 41:
                        zzeh.zzk(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 42:
                        zzeh.zzn(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 43:
                        zzeh.zzi(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 44:
                        zzeh.zzm(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 45:
                        zzeh.zzl(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 46:
                        zzeh.zzg(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 47:
                        zzeh.zzj(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 48:
                        zzeh.zze(this.zzmi[length], (List) zzfd.zzo(t, iZzag & 1048575), zzfrVar, true);
                        break;
                    case 49:
                        zzeh.zzb(this.zzmi[length], (List<?>) zzfd.zzo(t, iZzag & 1048575), zzfrVar, zzad(length));
                        break;
                    case 50:
                        zza(zzfrVar, i, zzfd.zzo(t, iZzag & 1048575), length);
                        break;
                    case 51:
                        if (zza(t, i, length)) {
                            dZzn2 = zze(t, iZzag & 1048575);
                            zzfrVar.zza(i, dZzn2);
                        }
                        break;
                    case 52:
                        if (zza(t, i, length)) {
                            fZzm2 = zzf(t, iZzag & 1048575);
                            zzfrVar.zza(i, fZzm2);
                        }
                        break;
                    case 53:
                        if (zza(t, i, length)) {
                            jZzk6 = zzh(t, iZzag & 1048575);
                            zzfrVar.zzi(i, jZzk6);
                        }
                        break;
                    case 54:
                        if (zza(t, i, length)) {
                            jZzk7 = zzh(t, iZzag & 1048575);
                            zzfrVar.zza(i, jZzk7);
                        }
                        break;
                    case 55:
                        if (zza(t, i, length)) {
                            iZzj7 = zzg(t, iZzag & 1048575);
                            zzfrVar.zzc(i, iZzj7);
                        }
                        break;
                    case 56:
                        if (zza(t, i, length)) {
                            jZzk8 = zzh(t, iZzag & 1048575);
                            zzfrVar.zzc(i, jZzk8);
                        }
                        break;
                    case 57:
                        if (zza(t, i, length)) {
                            iZzj8 = zzg(t, iZzag & 1048575);
                            zzfrVar.zzf(i, iZzj8);
                        }
                        break;
                    case 58:
                        if (zza(t, i, length)) {
                            zZzl2 = zzi(t, iZzag & 1048575);
                            zzfrVar.zzb(i, zZzl2);
                        }
                        break;
                    case 59:
                        if (zza(t, i, length)) {
                            zza(i, zzfd.zzo(t, iZzag & 1048575), zzfrVar);
                        }
                        break;
                    case 60:
                        if (zza(t, i, length)) {
                            zzfrVar.zza(i, zzfd.zzo(t, iZzag & 1048575), zzad(length));
                        }
                        break;
                    case 61:
                        if (zza(t, i, length)) {
                            zzfrVar.zza(i, (zzbb) zzfd.zzo(t, iZzag & 1048575));
                        }
                        break;
                    case 62:
                        if (zza(t, i, length)) {
                            iZzj9 = zzg(t, iZzag & 1048575);
                            zzfrVar.zzd(i, iZzj9);
                        }
                        break;
                    case 63:
                        if (zza(t, i, length)) {
                            iZzj10 = zzg(t, iZzag & 1048575);
                            zzfrVar.zzn(i, iZzj10);
                        }
                        break;
                    case 64:
                        if (zza(t, i, length)) {
                            iZzj11 = zzg(t, iZzag & 1048575);
                            zzfrVar.zzm(i, iZzj11);
                        }
                        break;
                    case 65:
                        if (zza(t, i, length)) {
                            jZzk9 = zzh(t, iZzag & 1048575);
                            zzfrVar.zzj(i, jZzk9);
                        }
                        break;
                    case 66:
                        if (zza(t, i, length)) {
                            iZzj12 = zzg(t, iZzag & 1048575);
                            zzfrVar.zze(i, iZzj12);
                        }
                        break;
                    case 67:
                        if (zza(t, i, length)) {
                            jZzk10 = zzh(t, iZzag & 1048575);
                            zzfrVar.zzb(i, jZzk10);
                        }
                        break;
                    case 68:
                        if (zza(t, i, length)) {
                            zzfrVar.zzb(i, zzfd.zzo(t, iZzag & 1048575), zzad(length));
                        }
                        break;
                }
            }
            while (entry2 != null) {
                this.zzmy.zza(zzfrVar, entry2);
                entry2 = itDescendingIterator.hasNext() ? (Map.Entry) itDescendingIterator.next() : null;
            }
            return;
        }
        if (!this.zzmq) {
            zzb(t, zzfrVar);
            return;
        }
        if (this.zzmo) {
            zzby<T> zzbyVarZza2 = this.zzmy.zza(t);
            if (zzbyVarZza2.isEmpty()) {
                it = null;
                entry = null;
            } else {
                it = zzbyVarZza2.iterator();
                entry = (Map.Entry) it.next();
            }
        } else {
            it = null;
            entry = null;
        }
        int length2 = this.zzmi.length;
        for (int i2 = 0; i2 < length2; i2 += 4) {
            int iZzag2 = zzag(i2);
            int i3 = this.zzmi[i2];
            while (entry != null && this.zzmy.zza(entry) <= i3) {
                this.zzmy.zza(zzfrVar, entry);
                entry = it.hasNext() ? (Map.Entry) it.next() : null;
            }
            switch ((iZzag2 & 267386880) >>> 20) {
                case 0:
                    if (zza(t, i2)) {
                        dZzn = zzfd.zzn(t, iZzag2 & 1048575);
                        zzfrVar.zza(i3, dZzn);
                    }
                    break;
                case 1:
                    if (zza(t, i2)) {
                        fZzm = zzfd.zzm(t, iZzag2 & 1048575);
                        zzfrVar.zza(i3, fZzm);
                    }
                    break;
                case 2:
                    if (zza(t, i2)) {
                        jZzk = zzfd.zzk(t, iZzag2 & 1048575);
                        zzfrVar.zzi(i3, jZzk);
                    }
                    break;
                case 3:
                    if (zza(t, i2)) {
                        jZzk2 = zzfd.zzk(t, iZzag2 & 1048575);
                        zzfrVar.zza(i3, jZzk2);
                    }
                    break;
                case 4:
                    if (zza(t, i2)) {
                        iZzj = zzfd.zzj(t, iZzag2 & 1048575);
                        zzfrVar.zzc(i3, iZzj);
                    }
                    break;
                case 5:
                    if (zza(t, i2)) {
                        jZzk3 = zzfd.zzk(t, iZzag2 & 1048575);
                        zzfrVar.zzc(i3, jZzk3);
                    }
                    break;
                case 6:
                    if (zza(t, i2)) {
                        iZzj2 = zzfd.zzj(t, iZzag2 & 1048575);
                        zzfrVar.zzf(i3, iZzj2);
                    }
                    break;
                case 7:
                    if (zza(t, i2)) {
                        zZzl = zzfd.zzl(t, iZzag2 & 1048575);
                        zzfrVar.zzb(i3, zZzl);
                    }
                    break;
                case 8:
                    if (zza(t, i2)) {
                        zza(i3, zzfd.zzo(t, iZzag2 & 1048575), zzfrVar);
                    }
                    break;
                case 9:
                    if (zza(t, i2)) {
                        zzfrVar.zza(i3, zzfd.zzo(t, iZzag2 & 1048575), zzad(i2));
                    }
                    break;
                case 10:
                    if (zza(t, i2)) {
                        zzfrVar.zza(i3, (zzbb) zzfd.zzo(t, iZzag2 & 1048575));
                    }
                    break;
                case 11:
                    if (zza(t, i2)) {
                        iZzj3 = zzfd.zzj(t, iZzag2 & 1048575);
                        zzfrVar.zzd(i3, iZzj3);
                    }
                    break;
                case 12:
                    if (zza(t, i2)) {
                        iZzj4 = zzfd.zzj(t, iZzag2 & 1048575);
                        zzfrVar.zzn(i3, iZzj4);
                    }
                    break;
                case 13:
                    if (zza(t, i2)) {
                        iZzj5 = zzfd.zzj(t, iZzag2 & 1048575);
                        zzfrVar.zzm(i3, iZzj5);
                    }
                    break;
                case 14:
                    if (zza(t, i2)) {
                        jZzk4 = zzfd.zzk(t, iZzag2 & 1048575);
                        zzfrVar.zzj(i3, jZzk4);
                    }
                    break;
                case 15:
                    if (zza(t, i2)) {
                        iZzj6 = zzfd.zzj(t, iZzag2 & 1048575);
                        zzfrVar.zze(i3, iZzj6);
                    }
                    break;
                case 16:
                    if (zza(t, i2)) {
                        jZzk5 = zzfd.zzk(t, iZzag2 & 1048575);
                        zzfrVar.zzb(i3, jZzk5);
                    }
                    break;
                case 17:
                    if (zza(t, i2)) {
                        zzfrVar.zzb(i3, zzfd.zzo(t, iZzag2 & 1048575), zzad(i2));
                    }
                    break;
                case 18:
                    zzeh.zza(this.zzmi[i2], (List<Double>) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 19:
                    zzeh.zzb(this.zzmi[i2], (List<Float>) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 20:
                    zzeh.zzc(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 21:
                    zzeh.zzd(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 22:
                    zzeh.zzh(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 23:
                    zzeh.zzf(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 24:
                    zzeh.zzk(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 25:
                    zzeh.zzn(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 26:
                    zzeh.zza(this.zzmi[i2], (List<String>) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar);
                    break;
                case 27:
                    zzeh.zza(this.zzmi[i2], (List<?>) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, zzad(i2));
                    break;
                case 28:
                    zzeh.zzb(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar);
                    break;
                case 29:
                    zzeh.zzi(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 30:
                    zzeh.zzm(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 31:
                    zzeh.zzl(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 32:
                    zzeh.zzg(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 33:
                    zzeh.zzj(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 34:
                    zzeh.zze(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, false);
                    break;
                case 35:
                    zzeh.zza(this.zzmi[i2], (List<Double>) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 36:
                    zzeh.zzb(this.zzmi[i2], (List<Float>) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 37:
                    zzeh.zzc(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 38:
                    zzeh.zzd(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 39:
                    zzeh.zzh(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 40:
                    zzeh.zzf(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 41:
                    zzeh.zzk(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 42:
                    zzeh.zzn(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 43:
                    zzeh.zzi(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 44:
                    zzeh.zzm(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 45:
                    zzeh.zzl(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 46:
                    zzeh.zzg(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 47:
                    zzeh.zzj(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 48:
                    zzeh.zze(this.zzmi[i2], (List) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, true);
                    break;
                case 49:
                    zzeh.zzb(this.zzmi[i2], (List<?>) zzfd.zzo(t, iZzag2 & 1048575), zzfrVar, zzad(i2));
                    break;
                case 50:
                    zza(zzfrVar, i3, zzfd.zzo(t, iZzag2 & 1048575), i2);
                    break;
                case 51:
                    if (zza(t, i3, i2)) {
                        dZzn = zze(t, iZzag2 & 1048575);
                        zzfrVar.zza(i3, dZzn);
                    }
                    break;
                case 52:
                    if (zza(t, i3, i2)) {
                        fZzm = zzf(t, iZzag2 & 1048575);
                        zzfrVar.zza(i3, fZzm);
                    }
                    break;
                case 53:
                    if (zza(t, i3, i2)) {
                        jZzk = zzh(t, iZzag2 & 1048575);
                        zzfrVar.zzi(i3, jZzk);
                    }
                    break;
                case 54:
                    if (zza(t, i3, i2)) {
                        jZzk2 = zzh(t, iZzag2 & 1048575);
                        zzfrVar.zza(i3, jZzk2);
                    }
                    break;
                case 55:
                    if (zza(t, i3, i2)) {
                        iZzj = zzg(t, iZzag2 & 1048575);
                        zzfrVar.zzc(i3, iZzj);
                    }
                    break;
                case 56:
                    if (zza(t, i3, i2)) {
                        jZzk3 = zzh(t, iZzag2 & 1048575);
                        zzfrVar.zzc(i3, jZzk3);
                    }
                    break;
                case 57:
                    if (zza(t, i3, i2)) {
                        iZzj2 = zzg(t, iZzag2 & 1048575);
                        zzfrVar.zzf(i3, iZzj2);
                    }
                    break;
                case 58:
                    if (zza(t, i3, i2)) {
                        zZzl = zzi(t, iZzag2 & 1048575);
                        zzfrVar.zzb(i3, zZzl);
                    }
                    break;
                case 59:
                    if (zza(t, i3, i2)) {
                        zza(i3, zzfd.zzo(t, iZzag2 & 1048575), zzfrVar);
                    }
                    break;
                case 60:
                    if (zza(t, i3, i2)) {
                        zzfrVar.zza(i3, zzfd.zzo(t, iZzag2 & 1048575), zzad(i2));
                    }
                    break;
                case 61:
                    if (zza(t, i3, i2)) {
                        zzfrVar.zza(i3, (zzbb) zzfd.zzo(t, iZzag2 & 1048575));
                    }
                    break;
                case 62:
                    if (zza(t, i3, i2)) {
                        iZzj3 = zzg(t, iZzag2 & 1048575);
                        zzfrVar.zzd(i3, iZzj3);
                    }
                    break;
                case 63:
                    if (zza(t, i3, i2)) {
                        iZzj4 = zzg(t, iZzag2 & 1048575);
                        zzfrVar.zzn(i3, iZzj4);
                    }
                    break;
                case 64:
                    if (zza(t, i3, i2)) {
                        iZzj5 = zzg(t, iZzag2 & 1048575);
                        zzfrVar.zzm(i3, iZzj5);
                    }
                    break;
                case 65:
                    if (zza(t, i3, i2)) {
                        jZzk4 = zzh(t, iZzag2 & 1048575);
                        zzfrVar.zzj(i3, jZzk4);
                    }
                    break;
                case 66:
                    if (zza(t, i3, i2)) {
                        iZzj6 = zzg(t, iZzag2 & 1048575);
                        zzfrVar.zze(i3, iZzj6);
                    }
                    break;
                case 67:
                    if (zza(t, i3, i2)) {
                        jZzk5 = zzh(t, iZzag2 & 1048575);
                        zzfrVar.zzb(i3, jZzk5);
                    }
                    break;
                case 68:
                    if (zza(t, i3, i2)) {
                        zzfrVar.zzb(i3, zzfd.zzo(t, iZzag2 & 1048575), zzad(i2));
                    }
                    break;
            }
        }
        while (entry != null) {
            this.zzmy.zza(zzfrVar, entry);
            entry = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        zza(this.zzmx, t, zzfrVar);
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x0164, code lost:
    
        if (r0 == r15) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0188, code lost:
    
        if (r0 == r15) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01a1, code lost:
    
        if (r0 == r15) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01a3, code lost:
    
        r2 = r0;
     */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r23, byte[] r24, int r25, int r26, com.google.android.gms.internal.clearcut.zzay r27) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 518
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.clearcut.zzay):void");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t) {
        int[] iArr = this.zzmt;
        if (iArr != null) {
            for (int i : iArr) {
                long jZzag = zzag(i) & 1048575;
                Object objZzo = zzfd.zzo(t, jZzag);
                if (objZzo != null) {
                    zzfd.zza(t, jZzag, this.zzmz.zzj(objZzo));
                }
            }
        }
        int[] iArr2 = this.zzmu;
        if (iArr2 != null) {
            for (int i2 : iArr2) {
                this.zzmw.zza(t, i2);
            }
        }
        this.zzmx.zzc(t);
        if (this.zzmo) {
            this.zzmy.zzc(t);
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0032  */
    /* JADX WARN: Code duplicated, block: B:41:0x008a  */
    /* JADX WARN: Code duplicated, block: B:53:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:59:0x00c9  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzmi.length; i += 4) {
            int iZzag = zzag(i);
            long j = 1048575 & iZzag;
            int i2 = this.zzmi[i];
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    if (zza(t2, i)) {
                        zzfd.zza(t, j, zzfd.zzn(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 1:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzm(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 2:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 3:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 4:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 5:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 6:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 7:
                    if (zza(t2, i)) {
                        zzfd.zza(t, j, zzfd.zzl(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 8:
                    if (zza(t2, i)) {
                        zzfd.zza(t, j, zzfd.zzo(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 9:
                case 17:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza(t2, i)) {
                        zzfd.zza(t, j, zzfd.zzo(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 11:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 12:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 13:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 14:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 15:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                        zzb(t, i);
                    }
                    break;
                case 16:
                    if (zza(t2, i)) {
                        zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                        zzb(t, i);
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
                    this.zzmw.zza(t, t2, j);
                    break;
                case 50:
                    zzeh.zza(this.zzmz, t, t2, j);
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
                        zzfd.zza(t, j, zzfd.zzo(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 60:
                case 68:
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
                        zzfd.zza(t, j, zzfd.zzo(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
            }
        }
        if (this.zzmq) {
            return;
        }
        zzeh.zza(this.zzmx, t, t2);
        if (this.zzmo) {
            zzeh.zza(this.zzmy, t, t2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:142:0x020d A[PHI: r5
      0x020d: PHI (r5v71 int) = 
      (r5v34 int)
      (r5v37 int)
      (r5v40 int)
      (r5v43 int)
      (r5v46 int)
      (r5v49 int)
      (r5v52 int)
      (r5v55 int)
      (r5v58 int)
      (r5v61 int)
      (r5v64 int)
      (r5v67 int)
      (r5v70 int)
      (r5v75 int)
     binds: [B:141:0x020b, B:136:0x01fa, B:131:0x01e9, B:126:0x01d8, B:121:0x01c7, B:116:0x01b6, B:111:0x01a5, B:106:0x0193, B:101:0x0181, B:96:0x016f, B:91:0x015d, B:86:0x014b, B:81:0x0139, B:76:0x0127] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:160:0x029f  */
    /* JADX WARN: Code duplicated, block: B:171:0x02d2  */
    /* JADX WARN: Code duplicated, block: B:174:0x02dd  */
    /* JADX WARN: Code duplicated, block: B:185:0x0306  */
    /* JADX WARN: Code duplicated, block: B:186:0x030a A[PHI: r5
      0x030a: PHI (r5v94 java.lang.Object) = (r5v12 java.lang.Object), (r5v92 java.lang.Object), (r5v96 java.lang.Object) binds: [B:193:0x0331, B:45:0x00ab, B:185:0x0306] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:189:0x0317  */
    /* JADX WARN: Code duplicated, block: B:195:0x0334 A[PHI: r5
      0x0334: PHI (r5v90 java.lang.Object) = (r5v12 java.lang.Object), (r5v92 java.lang.Object) binds: [B:193:0x0331, B:45:0x00ab] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:198:0x0342  */
    /* JADX WARN: Code duplicated, block: B:201:0x034e  */
    /* JADX WARN: Code duplicated, block: B:204:0x035a  */
    /* JADX WARN: Code duplicated, block: B:219:0x0396  */
    /* JADX WARN: Code duplicated, block: B:222:0x03a2  */
    /* JADX WARN: Code duplicated, block: B:374:0x05fa A[PHI: r4
      0x05fa: PHI (r4v115 int) = 
      (r4v78 int)
      (r4v81 int)
      (r4v84 int)
      (r4v87 int)
      (r4v90 int)
      (r4v93 int)
      (r4v96 int)
      (r4v99 int)
      (r4v102 int)
      (r4v105 int)
      (r4v108 int)
      (r4v111 int)
      (r4v114 int)
      (r4v119 int)
     binds: [B:373:0x05f8, B:368:0x05e7, B:363:0x05d6, B:358:0x05c5, B:353:0x05b4, B:348:0x05a3, B:343:0x0592, B:338:0x0580, B:333:0x056e, B:328:0x055c, B:323:0x054a, B:318:0x0538, B:313:0x0526, B:308:0x0514] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:392:0x06ba A[PHI: r6
      0x06ba: PHI (r6v4 int) = 
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v14 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v15 int)
      (r6v1 int)
     binds: [B:245:0x040b, B:437:0x0761, B:434:0x0757, B:428:0x0742, B:425:0x0730, B:421:0x0721, B:417:0x0714, B:413:0x0707, B:409:0x06fc, B:406:0x06f1, B:402:0x06e4, B:398:0x06d7, B:395:0x06c4, B:371:0x05f4, B:366:0x05e3, B:361:0x05d2, B:356:0x05c1, B:351:0x05b0, B:346:0x059f, B:341:0x058e, B:336:0x057c, B:331:0x056a, B:326:0x0558, B:321:0x0546, B:316:0x0534, B:311:0x0522, B:306:0x0510, B:301:0x04dc, B:298:0x04cf, B:295:0x04bf, B:292:0x04af, B:289:0x049f, B:286:0x0491, B:283:0x0484, B:280:0x047c, B:275:0x046c, B:272:0x0464, B:269:0x045c, B:266:0x0450, B:263:0x0444, B:411:0x0703, B:260:0x043c, B:257:0x0434, B:254:0x0428, B:251:0x041c, B:391:0x06b9, B:248:0x0414] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:423:0x0727 A[PHI: r4
      0x0727: PHI (r4v150 java.lang.Object) = (r4v16 java.lang.Object), (r4v146 java.lang.Object), (r4v153 java.lang.Object) binds: [B:430:0x074a, B:277:0x0474, B:422:0x0723] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:432:0x074d A[PHI: r4
      0x074d: PHI (r4v142 java.lang.Object) = (r4v16 java.lang.Object), (r4v146 java.lang.Object) binds: [B:430:0x074a, B:277:0x0474] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x0414, code lost:
    
        if (zza(r20, r15, r5) != false) goto L396;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0434, code lost:
    
        if (zza(r20, r15, r5) != false) goto L407;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x043c, code lost:
    
        if (zza(r20, r15, r5) != false) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x045c, code lost:
    
        if (zza(r20, r15, r5) != false) goto L422;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x0464, code lost:
    
        if (zza(r20, r15, r5) != false) goto L426;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x047c, code lost:
    
        if (zza(r20, r15, r5) != false) goto L435;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x06c4, code lost:
    
        if ((r12 & r18) != 0) goto L396;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x06c6, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, (com.google.android.gms.internal.clearcut.zzdo) r2.getObject(r20, r10), zzad(r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x06f1, code lost:
    
        if ((r12 & r18) != 0) goto L407;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x06f3, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzbn.zzh(r15, 0L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x06fc, code lost:
    
        if ((r12 & r18) != 0) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x06fe, code lost:
    
        r9 = com.google.android.gms.internal.clearcut.zzbn.zzk(r15, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0721, code lost:
    
        if ((r12 & r18) != 0) goto L422;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0723, code lost:
    
        r4 = r2.getObject(r20, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x0730, code lost:
    
        if ((r12 & r18) != 0) goto L426;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x0732, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzeh.zzc(r15, r2.getObject(r20, r10), zzad(r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x0757, code lost:
    
        if ((r12 & r18) != 0) goto L435;
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x0759, code lost:
    
        r4 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, true);
     */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zzm(T r20) {
        /*
            Method dump skipped, instruction units count: 2306
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzm(java.lang.Object):int");
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:56:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:59:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:62:0x00f6 A[LOOP:2: B:57:0x00e5->B:62:0x00f6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:82:0x00f5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x010a A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22, types: [com.google.android.gms.internal.clearcut.zzef] */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v8, types: [com.google.android.gms.internal.clearcut.zzef] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean zzo(T t) {
        int i;
        List list;
        ?? Zzad;
        int i2;
        int[] iArr = this.zzms;
        int i3 = 1;
        if (iArr == null || iArr.length == 0) {
            return true;
        }
        int length = iArr.length;
        int i4 = -1;
        int i5 = 0;
        int i6 = 0;
        while (i5 < length) {
            int i7 = iArr[i5];
            int iZzai = zzai(i7);
            int iZzag = zzag(iZzai);
            if (this.zzmq) {
                i = 0;
            } else {
                int i8 = this.zzmi[iZzai + 2];
                int i9 = i8 & 1048575;
                i = i3 << (i8 >>> 20);
                if (i9 != i4) {
                    i6 = zzmh.getInt(t, i9);
                    i4 = i9;
                }
            }
            if ((268435456 & iZzag) != 0 && !zza(t, iZzai, i6, i)) {
                return false;
            }
            int i10 = (267386880 & iZzag) >>> 20;
            if (i10 == 9 || i10 == 17) {
                if (zza(t, iZzai, i6, i) && !zza(t, iZzag, zzad(iZzai))) {
                    return false;
                }
            } else if (i10 == 27) {
                list = (List) zzfd.zzo(t, iZzag & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    Zzad = zzad(iZzai);
                    for (i2 = 0; i2 < list.size(); i2++) {
                        if (!Zzad.zzo(list.get(i2))) {
                            return false;
                        }
                    }
                }
            } else if (i10 == 60 || i10 == 68) {
                if (zza(t, i7, iZzai) && !zza(t, iZzag, zzad(iZzai))) {
                    return false;
                }
            } else if (i10 == 49) {
                list = (List) zzfd.zzo(t, iZzag & 1048575);
                if (list.isEmpty()) {
                    Zzad = zzad(iZzai);
                    while (i2 < list.size()) {
                        if (!Zzad.zzo(list.get(i2))) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            } else if (i10 != 50) {
                continue;
            } else {
                Map<?, ?> mapZzh = this.zzmz.zzh(zzfd.zzo(t, iZzag & 1048575));
                if (mapZzh.isEmpty()) {
                    continue;
                } else if (this.zzmz.zzl(zzae(iZzai)).zzmd.zzek() == zzfq.MESSAGE) {
                    ?? Zze = 0;
                    for (Object obj : mapZzh.values()) {
                        if (Zze == 0) {
                            Zze = Zze;
                            Zze = zzea.zzcm().zze(obj.getClass());
                        }
                        Zze = Zze;
                        if (!Zze.zzo(obj)) {
                            return false;
                        }
                    }
                } else {
                    continue;
                }
            }
            i5++;
            length = length;
            i3 = 1;
        }
        return !this.zzmo || this.zzmy.zza(t).isInitialized();
    }
}
