package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzak<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Object zzd = new Object();

    @CheckForNull
    transient int[] zza;

    @CheckForNull
    transient Object[] zzb;

    @CheckForNull
    transient Object[] zzc;

    @CheckForNull
    private transient Object zze;
    private transient int zzf;
    private transient int zzg;

    @CheckForNull
    private transient Set<K> zzh;

    @CheckForNull
    private transient Set<Map.Entry<K, V>> zzi;

    @CheckForNull
    private transient Collection<V> zzj;

    zzak() {
        zzp(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int[] zzA() {
        int[] iArr = this.zza;
        iArr.getClass();
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object[] zzB() {
        Object[] objArr = this.zzb;
        objArr.getClass();
        return objArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object[] zzC() {
        Object[] objArr = this.zzc;
        objArr.getClass();
        return objArr;
    }

    static /* bridge */ /* synthetic */ Object zzg(zzak zzakVar, int i) {
        return zzakVar.zzB()[i];
    }

    static /* bridge */ /* synthetic */ Object zzi(zzak zzakVar) {
        Object obj = zzakVar.zze;
        obj.getClass();
        return obj;
    }

    static /* bridge */ /* synthetic */ Object zzj(zzak zzakVar, int i) {
        return zzakVar.zzC()[i];
    }

    static /* bridge */ /* synthetic */ void zzn(zzak zzakVar, int i, Object obj) {
        zzakVar.zzC()[i] = obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zzv() {
        return (1 << (this.zzf & 31)) - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zzw(@CheckForNull Object obj) {
        if (zzr()) {
            return -1;
        }
        int iZzb = zzam.zzb(obj);
        int iZzv = zzv();
        Object obj2 = this.zze;
        obj2.getClass();
        int iZzc = zzal.zzc(obj2, iZzb & iZzv);
        if (iZzc != 0) {
            int i = ~iZzv;
            int i2 = iZzb & i;
            do {
                int i3 = iZzc - 1;
                int i4 = zzA()[i3];
                if ((i4 & i) == i2 && zzd.zza(obj, zzB()[i3])) {
                    return i3;
                }
                iZzc = i4 & iZzv;
            } while (iZzc != 0);
        }
        return -1;
    }

    private final int zzx(int i, int i2, int i3, int i4) {
        Object objZzd = zzal.zzd(i2);
        int i5 = i2 - 1;
        if (i4 != 0) {
            zzal.zze(objZzd, i3 & i5, i4 + 1);
        }
        Object obj = this.zze;
        obj.getClass();
        int[] iArrZzA = zzA();
        for (int i6 = 0; i6 <= i; i6++) {
            int iZzc = zzal.zzc(obj, i6);
            while (iZzc != 0) {
                int i7 = iZzc - 1;
                int i8 = iArrZzA[i7];
                int i9 = ((~i) & i8) | i6;
                int i10 = i9 & i5;
                int iZzc2 = zzal.zzc(objZzd, i10);
                zzal.zze(objZzd, i10, iZzc);
                iArrZzA[i7] = ((~i5) & i9) | (iZzc2 & i5);
                iZzc = i8 & i;
            }
        }
        this.zze = objZzd;
        zzz(i5);
        return i5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object zzy(@CheckForNull Object obj) {
        if (zzr()) {
            return zzd;
        }
        int iZzv = zzv();
        Object obj2 = this.zze;
        obj2.getClass();
        int iZzb = zzal.zzb(obj, null, iZzv, obj2, zzA(), zzB(), null);
        if (iZzb == -1) {
            return zzd;
        }
        Object obj3 = zzC()[iZzb];
        zzq(iZzb, iZzv);
        this.zzg--;
        zzo();
        return obj3;
    }

    private final void zzz(int i) {
        this.zzf = ((32 - Integer.numberOfLeadingZeros(i)) & 31) | (this.zzf & (-32));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        if (zzr()) {
            return;
        }
        zzo();
        Map<K, V> mapZzl = zzl();
        if (mapZzl != null) {
            this.zzf = zzbu.zza(size(), 3, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
            mapZzl.clear();
            this.zze = null;
            this.zzg = 0;
            return;
        }
        Arrays.fill(zzB(), 0, this.zzg, (Object) null);
        Arrays.fill(zzC(), 0, this.zzg, (Object) null);
        Object obj = this.zze;
        obj.getClass();
        if (obj instanceof byte[]) {
            Arrays.fill((byte[]) obj, (byte) 0);
        } else if (obj instanceof short[]) {
            Arrays.fill((short[]) obj, (short) 0);
        } else {
            Arrays.fill((int[]) obj, 0);
        }
        Arrays.fill(zzA(), 0, this.zzg, 0);
        this.zzg = 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(@CheckForNull Object obj) {
        Map<K, V> mapZzl = zzl();
        if (mapZzl != null) {
            return mapZzl.containsKey(obj);
        }
        return zzw(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(@CheckForNull Object obj) {
        Map<K, V> mapZzl = zzl();
        if (mapZzl != null) {
            return mapZzl.containsValue(obj);
        }
        for (int i = 0; i < this.zzg; i++) {
            if (zzd.zza(obj, zzC()[i])) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.zzi;
        if (set != null) {
            return set;
        }
        zzae zzaeVar = new zzae(this);
        this.zzi = zzaeVar;
        return zzaeVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public final V get(@CheckForNull Object obj) {
        Map<K, V> mapZzl = zzl();
        if (mapZzl != null) {
            return mapZzl.get(obj);
        }
        int iZzw = zzw(obj);
        if (iZzw == -1) {
            return null;
        }
        return (V) zzC()[iZzw];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<K> keySet() {
        Set<K> set = this.zzh;
        if (set != null) {
            return set;
        }
        zzah zzahVar = new zzah(this);
        this.zzh = zzahVar;
        return zzahVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public final V put(K k, V v) {
        int iMin;
        if (zzr()) {
            zzh.zze(zzr(), "Arrays already allocated");
            int i = this.zzf;
            int iMax = Math.max(i + 1, 2);
            int iHighestOneBit = Integer.highestOneBit(iMax);
            if (iMax > iHighestOneBit && (iHighestOneBit = iHighestOneBit + iHighestOneBit) <= 0) {
                iHighestOneBit = 1073741824;
            }
            int iMax2 = Math.max(4, iHighestOneBit);
            this.zze = zzal.zzd(iMax2);
            zzz(iMax2 - 1);
            this.zza = new int[i];
            this.zzb = new Object[i];
            this.zzc = new Object[i];
        }
        Map<K, V> mapZzl = zzl();
        if (mapZzl != null) {
            return mapZzl.put(k, v);
        }
        int[] iArrZzA = zzA();
        Object[] objArrZzB = zzB();
        Object[] objArrZzC = zzC();
        int i2 = this.zzg;
        int i3 = i2 + 1;
        int iZzb = zzam.zzb(k);
        int iZzv = zzv();
        int i4 = iZzb & iZzv;
        Object obj = this.zze;
        obj.getClass();
        int iZzc = zzal.zzc(obj, i4);
        if (iZzc != 0) {
            int i5 = ~iZzv;
            int i6 = iZzb & i5;
            int i7 = 0;
            while (true) {
                int i8 = iZzc - 1;
                int i9 = iArrZzA[i8];
                int i10 = i9 & i5;
                if (i10 == i6 && zzd.zza(k, objArrZzB[i8])) {
                    V v2 = (V) objArrZzC[i8];
                    objArrZzC[i8] = v;
                    return v2;
                }
                int i11 = i9 & iZzv;
                i7++;
                if (i11 == 0) {
                    if (i7 < 9) {
                        if (i3 <= iZzv) {
                            iArrZzA[i8] = (i3 & iZzv) | i10;
                            break;
                        }
                        iZzv = zzx(iZzv, zzal.zza(iZzv), iZzb, i2);
                        break;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap(zzv() + 1, 1.0f);
                    int iZze = zze();
                    while (iZze >= 0) {
                        linkedHashMap.put(zzB()[iZze], zzC()[iZze]);
                        iZze = zzf(iZze);
                    }
                    this.zze = linkedHashMap;
                    this.zza = null;
                    this.zzb = null;
                    this.zzc = null;
                    zzo();
                    return (V) linkedHashMap.put(k, v);
                }
                iZzc = i11;
            }
        } else if (i3 > iZzv) {
            iZzv = zzx(iZzv, zzal.zza(iZzv), iZzb, i2);
        } else {
            Object obj2 = this.zze;
            obj2.getClass();
            zzal.zze(obj2, i4, i3);
        }
        int length = zzA().length;
        if (i3 > length && (iMin = Math.min(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, (Math.max(1, length >>> 1) + length) | 1)) != length) {
            this.zza = Arrays.copyOf(zzA(), iMin);
            this.zzb = Arrays.copyOf(zzB(), iMin);
            this.zzc = Arrays.copyOf(zzC(), iMin);
        }
        zzA()[i2] = (~iZzv) & iZzb;
        zzB()[i2] = k;
        zzC()[i2] = v;
        this.zzg = i3;
        zzo();
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public final V remove(@CheckForNull Object obj) {
        Map<K, V> mapZzl = zzl();
        if (mapZzl != null) {
            return mapZzl.remove(obj);
        }
        V v = (V) zzy(obj);
        if (v == zzd) {
            return null;
        }
        return v;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        Map<K, V> mapZzl = zzl();
        return mapZzl != null ? mapZzl.size() : this.zzg;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection<V> values() {
        Collection<V> collection = this.zzj;
        if (collection != null) {
            return collection;
        }
        zzaj zzajVar = new zzaj(this);
        this.zzj = zzajVar;
        return zzajVar;
    }

    final int zze() {
        return isEmpty() ? -1 : 0;
    }

    final int zzf(int i) {
        int i2 = i + 1;
        if (i2 < this.zzg) {
            return i2;
        }
        return -1;
    }

    @CheckForNull
    final Map<K, V> zzl() {
        Object obj = this.zze;
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    final void zzo() {
        this.zzf += 32;
    }

    final void zzp(int i) {
        this.zzf = zzbu.zza(12, 1, LockFreeTaskQueueCore.MAX_CAPACITY_MASK);
    }

    final void zzq(int i, int i2) {
        Object obj = this.zze;
        obj.getClass();
        int[] iArrZzA = zzA();
        Object[] objArrZzB = zzB();
        Object[] objArrZzC = zzC();
        int size = size();
        int i3 = size - 1;
        if (i >= i3) {
            objArrZzB[i] = null;
            objArrZzC[i] = null;
            iArrZzA[i] = 0;
            return;
        }
        Object obj2 = objArrZzB[i3];
        objArrZzB[i] = obj2;
        objArrZzC[i] = objArrZzC[i3];
        objArrZzB[i3] = null;
        objArrZzC[i3] = null;
        iArrZzA[i] = iArrZzA[i3];
        iArrZzA[i3] = 0;
        int iZzb = zzam.zzb(obj2) & i2;
        int iZzc = zzal.zzc(obj, iZzb);
        if (iZzc == size) {
            zzal.zze(obj, iZzb, i + 1);
            return;
        }
        while (true) {
            int i4 = iZzc - 1;
            int i5 = iArrZzA[i4];
            int i6 = i5 & i2;
            if (i6 == size) {
                iArrZzA[i4] = ((i + 1) & i2) | (i5 & (~i2));
                return;
            }
            iZzc = i6;
        }
    }

    final boolean zzr() {
        return this.zze == null;
    }

    zzak(int i) {
        zzp(12);
    }
}
