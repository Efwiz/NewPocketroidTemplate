package com.google.android.gms.internal.mlkit_language_id;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
class zzgq<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private final int zza;
    private List<zzgz> zzb;
    private Map<K, V> zzc;
    private boolean zzd;
    private volatile zzhb zze;
    private Map<K, V> zzf;
    private volatile zzgv zzg;

    static <FieldDescriptorType extends zzel<FieldDescriptorType>> zzgq<FieldDescriptorType, Object> zza(int i) {
        return new zzgt(i);
    }

    private zzgq(int i) {
        this.zza = i;
        this.zzb = Collections.emptyList();
        this.zzc = Collections.emptyMap();
        this.zzf = Collections.emptyMap();
    }

    public void zza() {
        Map<K, V> mapUnmodifiableMap;
        Map<K, V> mapUnmodifiableMap2;
        if (this.zzd) {
            return;
        }
        if (this.zzc.isEmpty()) {
            mapUnmodifiableMap = Collections.emptyMap();
        } else {
            mapUnmodifiableMap = Collections.unmodifiableMap(this.zzc);
        }
        this.zzc = mapUnmodifiableMap;
        if (this.zzf.isEmpty()) {
            mapUnmodifiableMap2 = Collections.emptyMap();
        } else {
            mapUnmodifiableMap2 = Collections.unmodifiableMap(this.zzf);
        }
        this.zzf = mapUnmodifiableMap2;
        this.zzd = true;
    }

    public final boolean zzb() {
        return this.zzd;
    }

    public final int zzc() {
        return this.zzb.size();
    }

    public final Map.Entry<K, V> zzb(int i) {
        return this.zzb.get(i);
    }

    public final Iterable<Map.Entry<K, V>> zzd() {
        if (this.zzc.isEmpty()) {
            return zzgu.zza();
        }
        return this.zzc.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzb.size() + this.zzc.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzc.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        if (iZza >= 0) {
            return (V) this.zzb.get(iZza).getValue();
        }
        return this.zzc.get(comparable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final V put(K k, V v) {
        zzf();
        int iZza = zza(k);
        if (iZza >= 0) {
            return (V) this.zzb.get(iZza).setValue(v);
        }
        zzf();
        if (this.zzb.isEmpty() && !(this.zzb instanceof ArrayList)) {
            this.zzb = new ArrayList(this.zza);
        }
        int i = -(iZza + 1);
        if (i >= this.zza) {
            return zzg().put(k, v);
        }
        int size = this.zzb.size();
        int i2 = this.zza;
        if (size == i2) {
            zzgz zzgzVarRemove = this.zzb.remove(i2 - 1);
            zzg().put((Comparable) zzgzVarRemove.getKey(), zzgzVarRemove.getValue());
        }
        this.zzb.add(i, new zzgz(this, k, v));
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzf();
        if (!this.zzb.isEmpty()) {
            this.zzb.clear();
        }
        if (this.zzc.isEmpty()) {
            return;
        }
        this.zzc.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzf();
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        if (iZza >= 0) {
            return zzc(iZza);
        }
        if (this.zzc.isEmpty()) {
            return null;
        }
        return this.zzc.remove(comparable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzc(int i) {
        zzf();
        V v = (V) this.zzb.remove(i).getValue();
        if (!this.zzc.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzg().entrySet().iterator();
            this.zzb.add(new zzgz(this, it.next()));
            it.remove();
        }
        return v;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0028  */
    /* JADX WARN: Code duplicated, block: B:17:0x0045  */
    /* JADX WARN: Code duplicated, block: B:21:0x0043 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:22:0x0048 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:23:0x0040 A[SYNTHETIC] */
    private final int zza(K k) {
        int i;
        int i2;
        int i3;
        int iCompareTo;
        int size = this.zzb.size();
        int i4 = size - 1;
        if (i4 < 0) {
            i = 0;
            while (i <= i4) {
                i3 = (i + i4) / 2;
                iCompareTo = k.compareTo((Comparable) this.zzb.get(i3).getKey());
                if (iCompareTo < 0) {
                    i4 = i3 - 1;
                } else {
                    if (iCompareTo > 0) {
                        return i3;
                    }
                    i = i3 + 1;
                }
            }
            i2 = i + 1;
        } else {
            int iCompareTo2 = k.compareTo((Comparable) this.zzb.get(i4).getKey());
            if (iCompareTo2 > 0) {
                i2 = size + 1;
            } else {
                if (iCompareTo2 == 0) {
                    return i4;
                }
                i = 0;
                while (i <= i4) {
                    i3 = (i + i4) / 2;
                    iCompareTo = k.compareTo((Comparable) this.zzb.get(i3).getKey());
                    if (iCompareTo < 0) {
                        i4 = i3 - 1;
                    } else {
                        if (iCompareTo > 0) {
                            return i3;
                        }
                        i = i3 + 1;
                    }
                }
                i2 = i + 1;
            }
        }
        return -i2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zze == null) {
            this.zze = new zzhb(this, null);
        }
        return this.zze;
    }

    final Set<Map.Entry<K, V>> zze() {
        if (this.zzg == null) {
            this.zzg = new zzgv(this, null);
        }
        return this.zzg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzf() {
        if (this.zzd) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzg() {
        zzf();
        if (this.zzc.isEmpty() && !(this.zzc instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zzc = treeMap;
            this.zzf = treeMap.descendingMap();
        }
        return (SortedMap) this.zzc;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgq)) {
            return super.equals(obj);
        }
        zzgq zzgqVar = (zzgq) obj;
        int size = size();
        if (size != zzgqVar.size()) {
            return false;
        }
        int iZzc = zzc();
        if (iZzc != zzgqVar.zzc()) {
            return entrySet().equals(zzgqVar.entrySet());
        }
        for (int i = 0; i < iZzc; i++) {
            if (!zzb(i).equals(zzgqVar.zzb(i))) {
                return false;
            }
        }
        if (iZzc != size) {
            return this.zzc.equals(zzgqVar.zzc);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzc = zzc();
        int iHashCode = 0;
        for (int i = 0; i < iZzc; i++) {
            iHashCode += this.zzb.get(i).hashCode();
        }
        return this.zzc.size() > 0 ? iHashCode + this.zzc.hashCode() : iHashCode;
    }

    /* synthetic */ zzgq(int i, zzgt zzgtVar) {
        this(i);
    }
}
