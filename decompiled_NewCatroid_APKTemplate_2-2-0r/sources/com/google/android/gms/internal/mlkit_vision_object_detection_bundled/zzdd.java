package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdd<K, V> extends AbstractMap<K, V> implements Serializable {
    private static final Comparator<Comparable> zzf = new zzcw();
    final Comparator<? super K> zza;
    zzdc<K, V> zzb;
    int zzc;
    int zzd;
    final zzdc<K, V> zze;
    private zzcy zzg;
    private zzda zzh;

    public zzdd() {
        Comparator<Comparable> comparator = zzf;
        this.zzc = 0;
        this.zzd = 0;
        this.zze = new zzdc<>();
        this.zza = comparator;
    }

    private final void zzf(zzdc<K, V> zzdcVar, boolean z) {
        while (zzdcVar != null) {
            zzdc<K, V> zzdcVar2 = zzdcVar.zzb;
            zzdc<K, V> zzdcVar3 = zzdcVar.zzc;
            int i = zzdcVar2 != null ? zzdcVar2.zzh : 0;
            int i2 = zzdcVar3 != null ? zzdcVar3.zzh : 0;
            int i3 = i - i2;
            if (i3 == -2) {
                zzdc<K, V> zzdcVar4 = zzdcVar3.zzb;
                zzdc<K, V> zzdcVar5 = zzdcVar3.zzc;
                int i4 = (zzdcVar4 != null ? zzdcVar4.zzh : 0) - (zzdcVar5 != null ? zzdcVar5.zzh : 0);
                if (i4 == -1 || (i4 == 0 && !z)) {
                    zzh(zzdcVar);
                } else {
                    zzi(zzdcVar3);
                    zzh(zzdcVar);
                }
                if (z) {
                    return;
                }
            } else if (i3 == 2) {
                zzdc<K, V> zzdcVar6 = zzdcVar2.zzb;
                zzdc<K, V> zzdcVar7 = zzdcVar2.zzc;
                int i5 = (zzdcVar6 != null ? zzdcVar6.zzh : 0) - (zzdcVar7 != null ? zzdcVar7.zzh : 0);
                if (i5 == 1 || (i5 == 0 && !z)) {
                    zzi(zzdcVar);
                } else {
                    zzh(zzdcVar2);
                    zzi(zzdcVar);
                }
                if (z) {
                    return;
                }
            } else if (i3 == 0) {
                zzdcVar.zzh = i + 1;
                if (z) {
                    return;
                }
            } else {
                zzdcVar.zzh = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            }
            zzdcVar = zzdcVar.zza;
        }
    }

    private final void zzg(zzdc<K, V> zzdcVar, zzdc<K, V> zzdcVar2) {
        zzdc<K, V> zzdcVar3 = zzdcVar.zza;
        zzdcVar.zza = null;
        if (zzdcVar2 != null) {
            zzdcVar2.zza = zzdcVar3;
        }
        if (zzdcVar3 == null) {
            this.zzb = zzdcVar2;
        } else if (zzdcVar3.zzb == zzdcVar) {
            zzdcVar3.zzb = zzdcVar2;
        } else {
            zzdcVar3.zzc = zzdcVar2;
        }
    }

    private final void zzh(zzdc<K, V> zzdcVar) {
        zzdc<K, V> zzdcVar2 = zzdcVar.zzb;
        zzdc<K, V> zzdcVar3 = zzdcVar.zzc;
        zzdc<K, V> zzdcVar4 = zzdcVar3.zzb;
        zzdc<K, V> zzdcVar5 = zzdcVar3.zzc;
        zzdcVar.zzc = zzdcVar4;
        if (zzdcVar4 != null) {
            zzdcVar4.zza = zzdcVar;
        }
        zzg(zzdcVar, zzdcVar3);
        zzdcVar3.zzb = zzdcVar;
        zzdcVar.zza = zzdcVar3;
        int iMax = Math.max(zzdcVar2 != null ? zzdcVar2.zzh : 0, zzdcVar4 != null ? zzdcVar4.zzh : 0) + 1;
        zzdcVar.zzh = iMax;
        zzdcVar3.zzh = Math.max(iMax, zzdcVar5 != null ? zzdcVar5.zzh : 0) + 1;
    }

    private final void zzi(zzdc<K, V> zzdcVar) {
        zzdc<K, V> zzdcVar2 = zzdcVar.zzb;
        zzdc<K, V> zzdcVar3 = zzdcVar.zzc;
        zzdc<K, V> zzdcVar4 = zzdcVar2.zzb;
        zzdc<K, V> zzdcVar5 = zzdcVar2.zzc;
        zzdcVar.zzb = zzdcVar5;
        if (zzdcVar5 != null) {
            zzdcVar5.zza = zzdcVar;
        }
        zzg(zzdcVar, zzdcVar2);
        zzdcVar2.zzc = zzdcVar;
        zzdcVar.zza = zzdcVar2;
        int iMax = Math.max(zzdcVar3 != null ? zzdcVar3.zzh : 0, zzdcVar5 != null ? zzdcVar5.zzh : 0) + 1;
        zzdcVar.zzh = iMax;
        zzdcVar2.zzh = Math.max(iMax, zzdcVar4 != null ? zzdcVar4.zzh : 0) + 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.zzb = null;
        this.zzc = 0;
        this.zzd++;
        zzdc<K, V> zzdcVar = this.zze;
        zzdcVar.zze = zzdcVar;
        zzdcVar.zzd = zzdcVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return zzc(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        zzcy zzcyVar = this.zzg;
        if (zzcyVar != null) {
            return zzcyVar;
        }
        zzcy zzcyVar2 = new zzcy(this);
        this.zzg = zzcyVar2;
        return zzcyVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V get(Object obj) {
        zzdc<K, V> zzdcVarZzc = zzc(obj);
        if (zzdcVarZzc != null) {
            return zzdcVarZzc.zzg;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<K> keySet() {
        zzda zzdaVar = this.zzh;
        if (zzdaVar != null) {
            return zzdaVar;
        }
        zzda zzdaVar2 = new zzda(this);
        this.zzh = zzdaVar2;
        return zzdaVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V remove(Object obj) {
        zzdc<K, V> zzdcVarZzd = zzd(obj);
        if (zzdcVarZzd != null) {
            return zzdcVarZzd.zzg;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.zzc;
    }

    final zzdc<K, V> zza(K k, boolean z) {
        int iCompareTo;
        zzdc<K, V> zzdcVar;
        Comparator<? super K> comparator = this.zza;
        zzdc<K, V> zzdcVar2 = this.zzb;
        if (zzdcVar2 != null) {
            Comparable comparable = comparator == zzf ? (Comparable) k : null;
            while (true) {
                iCompareTo = comparable != null ? comparable.compareTo(zzdcVar2.zzf) : comparator.compare(k, zzdcVar2.zzf);
                if (iCompareTo == 0) {
                    return zzdcVar2;
                }
                zzdc<K, V> zzdcVar3 = iCompareTo < 0 ? zzdcVar2.zzb : zzdcVar2.zzc;
                if (zzdcVar3 == null) {
                    break;
                }
                zzdcVar2 = zzdcVar3;
            }
        } else {
            iCompareTo = 0;
        }
        if (!z) {
            return null;
        }
        zzdc<K, V> zzdcVar4 = this.zze;
        if (zzdcVar2 != null) {
            zzdcVar = new zzdc<>(zzdcVar2, k, zzdcVar4, zzdcVar4.zze);
            if (iCompareTo < 0) {
                zzdcVar2.zzb = zzdcVar;
            } else {
                zzdcVar2.zzc = zzdcVar;
            }
            zzf(zzdcVar2, true);
        } else {
            if (comparator == zzf && !(k instanceof Comparable)) {
                throw new ClassCastException(String.valueOf(k.getClass().getName()).concat(" is not Comparable"));
            }
            zzdcVar = new zzdc<>(null, k, zzdcVar4, zzdcVar4.zze);
            this.zzb = zzdcVar;
        }
        this.zzc++;
        this.zzd++;
        return zzdcVar;
    }

    final zzdc<K, V> zzb(Map.Entry<?, ?> entry) {
        zzdc<K, V> zzdcVarZzc = zzc(entry.getKey());
        if (zzdcVarZzc == null) {
            return null;
        }
        V v = zzdcVarZzc.zzg;
        Object value = entry.getValue();
        if (v == value || (v != null && v.equals(value))) {
            return zzdcVarZzc;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    final zzdc<K, V> zzc(Object obj) {
        if (obj == 0) {
            return null;
        }
        try {
            return zza(obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    final zzdc<K, V> zzd(Object obj) {
        zzdc<K, V> zzdcVarZzc = zzc(obj);
        if (zzdcVarZzc != null) {
            zze(zzdcVarZzc, true);
        }
        return zzdcVarZzc;
    }

    final void zze(zzdc<K, V> zzdcVar, boolean z) {
        zzdc<K, V> zzdcVar2;
        zzdc<K, V> zzdcVar3;
        int i;
        if (z) {
            zzdc<K, V> zzdcVar4 = zzdcVar.zze;
            zzdcVar4.zzd = zzdcVar.zzd;
            zzdcVar.zzd.zze = zzdcVar4;
        }
        zzdc<K, V> zzdcVar5 = zzdcVar.zzb;
        zzdc<K, V> zzdcVar6 = zzdcVar.zzc;
        zzdc<K, V> zzdcVar7 = zzdcVar.zza;
        int i2 = 0;
        if (zzdcVar5 == null || zzdcVar6 == null) {
            if (zzdcVar5 != null) {
                zzg(zzdcVar, zzdcVar5);
                zzdcVar.zzb = null;
            } else if (zzdcVar6 != null) {
                zzg(zzdcVar, zzdcVar6);
                zzdcVar.zzc = null;
            } else {
                zzg(zzdcVar, null);
            }
            zzf(zzdcVar7, false);
            this.zzc--;
            this.zzd++;
            return;
        }
        if (zzdcVar5.zzh > zzdcVar6.zzh) {
            do {
                zzdcVar3 = zzdcVar5;
                zzdcVar5 = zzdcVar5.zzc;
            } while (zzdcVar5 != null);
        } else {
            do {
                zzdcVar2 = zzdcVar6;
                zzdcVar6 = zzdcVar6.zzb;
            } while (zzdcVar6 != null);
            zzdcVar3 = zzdcVar2;
        }
        zze(zzdcVar3, false);
        zzdc<K, V> zzdcVar8 = zzdcVar.zzb;
        if (zzdcVar8 != null) {
            i = zzdcVar8.zzh;
            zzdcVar3.zzb = zzdcVar8;
            zzdcVar8.zza = zzdcVar3;
            zzdcVar.zzb = null;
        } else {
            i = 0;
        }
        zzdc<K, V> zzdcVar9 = zzdcVar.zzc;
        if (zzdcVar9 != null) {
            i2 = zzdcVar9.zzh;
            zzdcVar3.zzc = zzdcVar9;
            zzdcVar9.zza = zzdcVar3;
            zzdcVar.zzc = null;
        }
        zzdcVar3.zzh = Math.max(i, i2) + 1;
        zzg(zzdcVar, zzdcVar3);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        zzdc<K, V> zzdcVarZza = zza(k, true);
        V v2 = zzdcVarZza.zzg;
        zzdcVarZza.zzg = v;
        return v2;
    }
}
