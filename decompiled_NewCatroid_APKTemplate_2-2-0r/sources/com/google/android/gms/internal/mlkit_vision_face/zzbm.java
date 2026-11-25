package com.google.android.gms.internal.mlkit_vision_face;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzbm<E> extends zzbg<E> implements Set<E> {

    @NullableDecl
    private transient zzbl<E> zza;

    zzbm() {
    }

    @SafeVarargs
    public static <E> zzbm<E> zzf(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        Object[] objArr = new Object[8];
        objArr[0] = NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR;
        objArr[1] = "vision-common";
        objArr[2] = "play-services-mlkit-barcode-scanning";
        objArr[3] = "barcode-scanning";
        objArr[4] = "play-services-mlkit-face-detection";
        objArr[5] = "face-detection";
        System.arraycopy(eArr, 0, objArr, 6, 2);
        return zzk(8, objArr);
    }

    static int zzg(int i) {
        int iMax = Math.max(i, 2);
        if (iMax >= 751619276) {
            if (iMax < 1073741824) {
                return 1073741824;
            }
            throw new IllegalArgumentException("collection too large");
        }
        int iHighestOneBit = Integer.highestOneBit(iMax - 1);
        do {
            iHighestOneBit += iHighestOneBit;
        } while (((double) iHighestOneBit) * 0.7d < iMax);
        return iHighestOneBit;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof zzbm) && zzh() && ((zzbm) obj).zzh() && hashCode() != obj.hashCode()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            try {
                if (size() == set.size() && containsAll(set)) {
                    return true;
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        Iterator<E> it = iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            E next = it.next();
            iHashCode += next != null ? next.hashCode() : 0;
        }
        return iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbg, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* JADX INFO: renamed from: zza */
    public abstract zzcg<E> iterator();

    boolean zzh() {
        throw null;
    }

    public final zzbl<E> zzi() {
        zzbl<E> zzblVar = this.zza;
        if (zzblVar != null) {
            return zzblVar;
        }
        zzbl<E> zzblVarZzj = zzj();
        this.zza = zzblVarZzj;
        return zzblVarZzj;
    }

    zzbl<E> zzj() {
        throw null;
    }

    private static <E> zzbm<E> zzk(int i, Object... objArr) {
        if (i == 0) {
            return zzcb.zza;
        }
        if (i == 1) {
            return new zzce(objArr[0]);
        }
        int iZzg = zzg(i);
        Object[] objArr2 = new Object[iZzg];
        int i2 = iZzg - 1;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            Object obj = objArr[i5];
            zzbz.zzb(obj, i5);
            int iHashCode = obj.hashCode();
            int iZza = zzbd.zza(iHashCode);
            while (true) {
                int i6 = iZza & i2;
                Object obj2 = objArr2[i6];
                if (obj2 == null) {
                    objArr[i4] = obj;
                    objArr2[i6] = obj;
                    i3 += iHashCode;
                    i4++;
                    break;
                }
                if (obj2.equals(obj)) {
                    break;
                }
                iZza++;
            }
        }
        Arrays.fill(objArr, i4, i, (Object) null);
        if (i4 == 1) {
            return new zzce(objArr[0], i3);
        }
        if (zzg(i4) < iZzg / 2) {
            return zzk(i4, objArr);
        }
        if (i4 < 6) {
            objArr = Arrays.copyOf(objArr, i4);
        }
        return new zzcb(objArr, i3, objArr2, i2, i4);
    }
}
