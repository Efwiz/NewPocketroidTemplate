package com.google.android.gms.internal.mlkit_language_id;

import com.google.android.gms.internal.mlkit_language_id.zzel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzej<T extends zzel<T>> {
    private static final zzej zzd = new zzej(true);
    final zzgq<T, Object> zza;
    private boolean zzb;
    private boolean zzc;

    private zzej() {
        this.zza = zzgq.zza(16);
    }

    private zzej(boolean z) {
        this(zzgq.zza(0));
        zzb();
    }

    private zzej(zzgq<T, Object> zzgqVar) {
        this.zza = zzgqVar;
        zzb();
    }

    public static <T extends zzel<T>> zzej<T> zza() {
        return zzd;
    }

    public final void zzb() {
        if (this.zzb) {
            return;
        }
        this.zza.zza();
        this.zzb = true;
    }

    public final boolean zzc() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzej) {
            return this.zza.equals(((zzej) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> zzd() {
        if (this.zzc) {
            return new zzff(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    final Iterator<Map.Entry<T, Object>> zze() {
        if (this.zzc) {
            return new zzff(this.zza.zze().iterator());
        }
        return this.zza.zze().iterator();
    }

    private final Object zza(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzfa)) {
            return obj;
        }
        return zzfa.zza();
    }

    private final void zzb(T t, Object obj) {
        if (t.zzd()) {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                zza(t.zzb(), obj2);
            }
            obj = arrayList;
        } else {
            zza(t.zzb(), obj);
        }
        if (obj instanceof zzfa) {
            this.zzc = true;
        }
        this.zza.put(t, obj);
    }

    private static void zza(zzhv zzhvVar, Object obj) {
        zzeq.zza(obj);
        boolean z = true;
        switch (zzei.zza[zzhvVar.zza().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if (!(obj instanceof zzdn) && !(obj instanceof byte[])) {
                    z = false;
                }
                break;
            case 8:
                if (!(obj instanceof Integer) && !(obj instanceof zzet)) {
                    z = false;
                }
                break;
            case 9:
                if (!(obj instanceof zzfz) && !(obj instanceof zzfa)) {
                    z = false;
                }
                break;
            default:
                z = false;
                break;
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    public final boolean zzf() {
        for (int i = 0; i < this.zza.zzc(); i++) {
            if (!zza((Map.Entry) this.zza.zzb(i))) {
                return false;
            }
        }
        Iterator it = this.zza.zzd().iterator();
        while (it.hasNext()) {
            if (!zza((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    private static <T extends zzel<T>> boolean zza(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zzc() == zzhy.MESSAGE) {
            if (key.zzd()) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!((zzfz) it.next()).zzi()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzfz) {
                    if (!((zzfz) value).zzi()) {
                        return false;
                    }
                } else {
                    if (value instanceof zzfa) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzej<T> zzejVar) {
        for (int i = 0; i < zzejVar.zza.zzc(); i++) {
            zzb(zzejVar.zza.zzb(i));
        }
        Iterator it = zzejVar.zza.zzd().iterator();
        while (it.hasNext()) {
            zzb((Map.Entry) it.next());
        }
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzgf) {
            return ((zzgf) obj).clone();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final void zzb(Map.Entry<T, Object> entry) {
        zzfz zzfzVarZzg;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzfa) {
            value = zzfa.zza();
        }
        if (key.zzd()) {
            Object objZza = zza((zzel) key);
            if (objZza == null) {
                objZza = new ArrayList();
            }
            Iterator it = ((List) value).iterator();
            while (it.hasNext()) {
                ((List) objZza).add(zza(it.next()));
            }
            this.zza.put(key, objZza);
            return;
        }
        if (key.zzc() == zzhy.MESSAGE) {
            Object objZza2 = zza((zzel) key);
            if (objZza2 == null) {
                this.zza.put(key, zza(value));
                return;
            }
            if (objZza2 instanceof zzgf) {
                zzfzVarZzg = key.zza((zzgf) objZza2, (zzgf) value);
            } else {
                zzfzVarZzg = key.zza(((zzfz) objZza2).zzm(), (zzfz) value).zzg();
            }
            this.zza.put(key, zzfzVarZzg);
            return;
        }
        this.zza.put(key, zza(value));
    }

    static void zza(zzea zzeaVar, zzhv zzhvVar, int i, Object obj) throws IOException {
        if (zzhvVar == zzhv.zzj) {
            zzfz zzfzVar = (zzfz) obj;
            zzeq.zza(zzfzVar);
            zzeaVar.zza(i, 3);
            zzfzVar.zza(zzeaVar);
            zzeaVar.zza(i, 4);
        }
        zzeaVar.zza(i, zzhvVar.zzb());
        switch (zzei.zzb[zzhvVar.ordinal()]) {
            case 1:
                zzeaVar.zza(((Double) obj).doubleValue());
                break;
            case 2:
                zzeaVar.zza(((Float) obj).floatValue());
                break;
            case 3:
                zzeaVar.zza(((Long) obj).longValue());
                break;
            case 4:
                zzeaVar.zza(((Long) obj).longValue());
                break;
            case 5:
                zzeaVar.zza(((Integer) obj).intValue());
                break;
            case 6:
                zzeaVar.zzc(((Long) obj).longValue());
                break;
            case 7:
                zzeaVar.zzd(((Integer) obj).intValue());
                break;
            case 8:
                zzeaVar.zza(((Boolean) obj).booleanValue());
                break;
            case 9:
                ((zzfz) obj).zza(zzeaVar);
                break;
            case 10:
                zzeaVar.zza((zzfz) obj);
                break;
            case 11:
                if (obj instanceof zzdn) {
                    zzeaVar.zza((zzdn) obj);
                } else {
                    zzeaVar.zza((String) obj);
                }
                break;
            case 12:
                if (obj instanceof zzdn) {
                    zzeaVar.zza((zzdn) obj);
                } else {
                    byte[] bArr = (byte[]) obj;
                    zzeaVar.zzb(bArr, 0, bArr.length);
                }
                break;
            case 13:
                zzeaVar.zzb(((Integer) obj).intValue());
                break;
            case 14:
                zzeaVar.zzd(((Integer) obj).intValue());
                break;
            case 15:
                zzeaVar.zzc(((Long) obj).longValue());
                break;
            case 16:
                zzeaVar.zzc(((Integer) obj).intValue());
                break;
            case 17:
                zzeaVar.zzb(((Long) obj).longValue());
                break;
            case 18:
                if (obj instanceof zzet) {
                    zzeaVar.zza(((zzet) obj).zza());
                } else {
                    zzeaVar.zza(((Integer) obj).intValue());
                }
                break;
        }
    }

    public final int zzg() {
        int iZzc = 0;
        for (int i = 0; i < this.zza.zzc(); i++) {
            iZzc += zzc(this.zza.zzb(i));
        }
        Iterator it = this.zza.zzd().iterator();
        while (it.hasNext()) {
            iZzc += zzc((Map.Entry) it.next());
        }
        return iZzc;
    }

    private static int zzc(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (key.zzc() == zzhy.MESSAGE && !key.zzd() && !key.zze()) {
            if (value instanceof zzfa) {
                return zzea.zzb(entry.getKey().zza(), (zzfa) value);
            }
            return zzea.zzb(entry.getKey().zza(), (zzfz) value);
        }
        return zza((zzel<?>) key, value);
    }

    static int zza(zzhv zzhvVar, int i, Object obj) {
        int iZze = zzea.zze(i);
        if (zzhvVar == zzhv.zzj) {
            zzeq.zza((zzfz) obj);
            iZze <<= 1;
        }
        return iZze + zzb(zzhvVar, obj);
    }

    private static int zzb(zzhv zzhvVar, Object obj) {
        switch (zzei.zzb[zzhvVar.ordinal()]) {
            case 1:
                return zzea.zzb(((Double) obj).doubleValue());
            case 2:
                return zzea.zzb(((Float) obj).floatValue());
            case 3:
                return zzea.zzd(((Long) obj).longValue());
            case 4:
                return zzea.zze(((Long) obj).longValue());
            case 5:
                return zzea.zzf(((Integer) obj).intValue());
            case 6:
                return zzea.zzg(((Long) obj).longValue());
            case 7:
                return zzea.zzi(((Integer) obj).intValue());
            case 8:
                return zzea.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzea.zzc((zzfz) obj);
            case 10:
                if (obj instanceof zzfa) {
                    return zzea.zza((zzfa) obj);
                }
                return zzea.zzb((zzfz) obj);
            case 11:
                if (obj instanceof zzdn) {
                    return zzea.zzb((zzdn) obj);
                }
                return zzea.zzb((String) obj);
            case 12:
                if (obj instanceof zzdn) {
                    return zzea.zzb((zzdn) obj);
                }
                return zzea.zzb((byte[]) obj);
            case 13:
                return zzea.zzg(((Integer) obj).intValue());
            case 14:
                return zzea.zzj(((Integer) obj).intValue());
            case 15:
                return zzea.zzh(((Long) obj).longValue());
            case 16:
                return zzea.zzh(((Integer) obj).intValue());
            case 17:
                return zzea.zzf(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzet) {
                    return zzea.zzk(((zzet) obj).zza());
                }
                return zzea.zzk(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zza(zzel<?> zzelVar, Object obj) {
        zzhv zzhvVarZzb = zzelVar.zzb();
        int iZza = zzelVar.zza();
        if (zzelVar.zzd()) {
            int iZza2 = 0;
            if (zzelVar.zze()) {
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    iZza2 += zzb(zzhvVarZzb, it.next());
                }
                return zzea.zze(iZza) + iZza2 + zzea.zzl(iZza2);
            }
            Iterator it2 = ((List) obj).iterator();
            while (it2.hasNext()) {
                iZza2 += zza(zzhvVarZzb, iZza, it2.next());
            }
            return iZza2;
        }
        return zza(zzhvVarZzb, iZza, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzej zzejVar = new zzej();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<K, Object> entryZzb = this.zza.zzb(i);
            zzejVar.zzb((zzel) entryZzb.getKey(), entryZzb.getValue());
        }
        Iterator it = this.zza.zzd().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzejVar.zzb((zzel) entry.getKey(), entry.getValue());
        }
        zzejVar.zzc = this.zzc;
        return zzejVar;
    }
}
