package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzvu<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Comparator<Comparable> zzf = new zzvn();
    final Comparator<? super K> zza;
    zzvt<K, V> zzb;
    int zzc;
    int zzd;
    final zzvt<K, V> zze;
    private zzvp zzg;
    private zzvr zzh;

    public zzvu() {
        Comparator<Comparable> comparator = zzf;
        this.zzc = 0;
        this.zzd = 0;
        this.zze = new zzvt<>();
        this.zza = comparator;
    }

    private final void zzf(zzvt<K, V> zzvtVar, boolean z) {
        while (zzvtVar != null) {
            zzvt<K, V> zzvtVar2 = zzvtVar.zzb;
            zzvt<K, V> zzvtVar3 = zzvtVar.zzc;
            int i = zzvtVar2 != null ? zzvtVar2.zzh : 0;
            int i2 = zzvtVar3 != null ? zzvtVar3.zzh : 0;
            int i3 = i - i2;
            if (i3 == -2) {
                zzvt<K, V> zzvtVar4 = zzvtVar3.zzb;
                zzvt<K, V> zzvtVar5 = zzvtVar3.zzc;
                int i4 = (zzvtVar4 != null ? zzvtVar4.zzh : 0) - (zzvtVar5 != null ? zzvtVar5.zzh : 0);
                if (i4 == -1 || (i4 == 0 && !z)) {
                    zzh(zzvtVar);
                } else {
                    zzi(zzvtVar3);
                    zzh(zzvtVar);
                }
                if (z) {
                    return;
                }
            } else if (i3 == 2) {
                zzvt<K, V> zzvtVar6 = zzvtVar2.zzb;
                zzvt<K, V> zzvtVar7 = zzvtVar2.zzc;
                int i5 = (zzvtVar6 != null ? zzvtVar6.zzh : 0) - (zzvtVar7 != null ? zzvtVar7.zzh : 0);
                if (i5 == 1 || (i5 == 0 && !z)) {
                    zzi(zzvtVar);
                } else {
                    zzh(zzvtVar2);
                    zzi(zzvtVar);
                }
                if (z) {
                    return;
                }
            } else if (i3 == 0) {
                zzvtVar.zzh = i + 1;
                if (z) {
                    return;
                }
            } else {
                zzvtVar.zzh = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            }
            zzvtVar = zzvtVar.zza;
        }
    }

    private final void zzg(zzvt<K, V> zzvtVar, zzvt<K, V> zzvtVar2) {
        zzvt<K, V> zzvtVar3 = zzvtVar.zza;
        zzvtVar.zza = null;
        if (zzvtVar2 != null) {
            zzvtVar2.zza = zzvtVar3;
        }
        if (zzvtVar3 == null) {
            this.zzb = zzvtVar2;
        } else if (zzvtVar3.zzb == zzvtVar) {
            zzvtVar3.zzb = zzvtVar2;
        } else {
            zzvtVar3.zzc = zzvtVar2;
        }
    }

    private final void zzh(zzvt<K, V> zzvtVar) {
        zzvt<K, V> zzvtVar2 = zzvtVar.zzb;
        zzvt<K, V> zzvtVar3 = zzvtVar.zzc;
        zzvt<K, V> zzvtVar4 = zzvtVar3.zzb;
        zzvt<K, V> zzvtVar5 = zzvtVar3.zzc;
        zzvtVar.zzc = zzvtVar4;
        if (zzvtVar4 != null) {
            zzvtVar4.zza = zzvtVar;
        }
        zzg(zzvtVar, zzvtVar3);
        zzvtVar3.zzb = zzvtVar;
        zzvtVar.zza = zzvtVar3;
        int iMax = Math.max(zzvtVar2 != null ? zzvtVar2.zzh : 0, zzvtVar4 != null ? zzvtVar4.zzh : 0) + 1;
        zzvtVar.zzh = iMax;
        zzvtVar3.zzh = Math.max(iMax, zzvtVar5 != null ? zzvtVar5.zzh : 0) + 1;
    }

    private final void zzi(zzvt<K, V> zzvtVar) {
        zzvt<K, V> zzvtVar2 = zzvtVar.zzb;
        zzvt<K, V> zzvtVar3 = zzvtVar.zzc;
        zzvt<K, V> zzvtVar4 = zzvtVar2.zzb;
        zzvt<K, V> zzvtVar5 = zzvtVar2.zzc;
        zzvtVar.zzb = zzvtVar5;
        if (zzvtVar5 != null) {
            zzvtVar5.zza = zzvtVar;
        }
        zzg(zzvtVar, zzvtVar2);
        zzvtVar2.zzc = zzvtVar;
        zzvtVar.zza = zzvtVar2;
        int iMax = Math.max(zzvtVar3 != null ? zzvtVar3.zzh : 0, zzvtVar5 != null ? zzvtVar5.zzh : 0) + 1;
        zzvtVar.zzh = iMax;
        zzvtVar2.zzh = Math.max(iMax, zzvtVar4 != null ? zzvtVar4.zzh : 0) + 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.zzb = null;
        this.zzc = 0;
        this.zzd++;
        zzvt<K, V> zzvtVar = this.zze;
        zzvtVar.zze = zzvtVar;
        zzvtVar.zzd = zzvtVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return zzc(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        zzvp zzvpVar = this.zzg;
        if (zzvpVar != null) {
            return zzvpVar;
        }
        zzvp zzvpVar2 = new zzvp(this);
        this.zzg = zzvpVar2;
        return zzvpVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V get(Object obj) {
        zzvt<K, V> zzvtVarZzc = zzc(obj);
        if (zzvtVarZzc != null) {
            return zzvtVarZzc.zzg;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<K> keySet() {
        zzvr zzvrVar = this.zzh;
        if (zzvrVar != null) {
            return zzvrVar;
        }
        zzvr zzvrVar2 = new zzvr(this);
        this.zzh = zzvrVar2;
        return zzvrVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V remove(Object obj) {
        zzvt<K, V> zzvtVarZzd = zzd(obj);
        if (zzvtVarZzd != null) {
            return zzvtVarZzd.zzg;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.zzc;
    }

    final zzvt<K, V> zza(K k, boolean z) {
        int iCompareTo;
        zzvt<K, V> zzvtVar;
        Comparator<? super K> comparator = this.zza;
        zzvt<K, V> zzvtVar2 = this.zzb;
        if (zzvtVar2 != null) {
            Comparable comparable = comparator == zzf ? (Comparable) k : null;
            while (true) {
                iCompareTo = comparable != null ? comparable.compareTo(zzvtVar2.zzf) : comparator.compare(k, zzvtVar2.zzf);
                if (iCompareTo == 0) {
                    return zzvtVar2;
                }
                zzvt<K, V> zzvtVar3 = iCompareTo < 0 ? zzvtVar2.zzb : zzvtVar2.zzc;
                if (zzvtVar3 == null) {
                    break;
                }
                zzvtVar2 = zzvtVar3;
            }
        } else {
            iCompareTo = 0;
        }
        if (!z) {
            return null;
        }
        zzvt<K, V> zzvtVar4 = this.zze;
        if (zzvtVar2 != null) {
            zzvtVar = new zzvt<>(zzvtVar2, k, zzvtVar4, zzvtVar4.zze);
            if (iCompareTo < 0) {
                zzvtVar2.zzb = zzvtVar;
            } else {
                zzvtVar2.zzc = zzvtVar;
            }
            zzf(zzvtVar2, true);
        } else {
            if (comparator == zzf && !(k instanceof Comparable)) {
                throw new ClassCastException(String.valueOf(k.getClass().getName()).concat(" is not Comparable"));
            }
            zzvtVar = new zzvt<>(null, k, zzvtVar4, zzvtVar4.zze);
            this.zzb = zzvtVar;
        }
        this.zzc++;
        this.zzd++;
        return zzvtVar;
    }

    final zzvt<K, V> zzb(Map.Entry<?, ?> entry) {
        zzvt<K, V> zzvtVarZzc = zzc(entry.getKey());
        if (zzvtVarZzc == null) {
            return null;
        }
        V v = zzvtVarZzc.zzg;
        Object value = entry.getValue();
        if (v == value || (v != null && v.equals(value))) {
            return zzvtVarZzc;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    final zzvt<K, V> zzc(Object obj) {
        if (obj == 0) {
            return null;
        }
        try {
            return zza(obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    final zzvt<K, V> zzd(Object obj) {
        zzvt<K, V> zzvtVarZzc = zzc(obj);
        if (zzvtVarZzc != null) {
            zze(zzvtVarZzc, true);
        }
        return zzvtVarZzc;
    }

    final void zze(zzvt<K, V> zzvtVar, boolean z) {
        zzvt<K, V> zzvtVar2;
        zzvt<K, V> zzvtVar3;
        int i;
        if (z) {
            zzvt<K, V> zzvtVar4 = zzvtVar.zze;
            zzvtVar4.zzd = zzvtVar.zzd;
            zzvtVar.zzd.zze = zzvtVar4;
        }
        zzvt<K, V> zzvtVar5 = zzvtVar.zzb;
        zzvt<K, V> zzvtVar6 = zzvtVar.zzc;
        zzvt<K, V> zzvtVar7 = zzvtVar.zza;
        int i2 = 0;
        if (zzvtVar5 == null || zzvtVar6 == null) {
            if (zzvtVar5 != null) {
                zzg(zzvtVar, zzvtVar5);
                zzvtVar.zzb = null;
            } else if (zzvtVar6 != null) {
                zzg(zzvtVar, zzvtVar6);
                zzvtVar.zzc = null;
            } else {
                zzg(zzvtVar, null);
            }
            zzf(zzvtVar7, false);
            this.zzc--;
            this.zzd++;
            return;
        }
        if (zzvtVar5.zzh > zzvtVar6.zzh) {
            do {
                zzvtVar3 = zzvtVar5;
                zzvtVar5 = zzvtVar5.zzc;
            } while (zzvtVar5 != null);
        } else {
            do {
                zzvtVar2 = zzvtVar6;
                zzvtVar6 = zzvtVar6.zzb;
            } while (zzvtVar6 != null);
            zzvtVar3 = zzvtVar2;
        }
        zze(zzvtVar3, false);
        zzvt<K, V> zzvtVar8 = zzvtVar.zzb;
        if (zzvtVar8 != null) {
            i = zzvtVar8.zzh;
            zzvtVar3.zzb = zzvtVar8;
            zzvtVar8.zza = zzvtVar3;
            zzvtVar.zzb = null;
        } else {
            i = 0;
        }
        zzvt<K, V> zzvtVar9 = zzvtVar.zzc;
        if (zzvtVar9 != null) {
            i2 = zzvtVar9.zzh;
            zzvtVar3.zzc = zzvtVar9;
            zzvtVar9.zza = zzvtVar3;
            zzvtVar.zzc = null;
        }
        zzvtVar3.zzh = Math.max(i, i2) + 1;
        zzg(zzvtVar, zzvtVar3);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        zzvt<K, V> zzvtVarZza = zza(k, true);
        V v2 = zzvtVarZza.zzg;
        zzvtVarZza.zzg = v;
        return v2;
    }
}
