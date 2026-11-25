package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzew;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzeu<T extends zzew<T>> {
    private static final zzeu zzd = new zzeu(true);
    final zzhc<T, Object> zza;
    private boolean zzb;
    private boolean zzc;

    private zzeu() {
        this.zza = zzhc.zza(16);
    }

    private zzeu(boolean z) {
        this(zzhc.zza(0));
        zzb();
    }

    private zzeu(zzhc<T, Object> zzhcVar) {
        this.zza = zzhcVar;
        zzb();
    }

    public static <T extends zzew<T>> zzeu<T> zza() {
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
        if (obj instanceof zzeu) {
            return this.zza.equals(((zzeu) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> zzd() {
        if (this.zzc) {
            return new zzfo(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    final Iterator<Map.Entry<T, Object>> zze() {
        if (this.zzc) {
            return new zzfo(this.zza.zze().iterator());
        }
        return this.zza.zze().iterator();
    }

    private final Object zza(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzfj)) {
            return obj;
        }
        return zzfj.zza();
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
        if (obj instanceof zzfj) {
            this.zzc = true;
        }
        this.zza.put(t, obj);
    }

    private static void zza(zzig zzigVar, Object obj) {
        zzfe.zza(obj);
        boolean z = true;
        switch (zzet.zza[zzigVar.zza().ordinal()]) {
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
                if (!(obj instanceof zzdu) && !(obj instanceof byte[])) {
                    z = false;
                }
                break;
            case 8:
                if (!(obj instanceof Integer) && !(obj instanceof zzfd)) {
                    z = false;
                }
                break;
            case 9:
                if (!(obj instanceof zzgi) && !(obj instanceof zzfj)) {
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

    private static <T extends zzew<T>> boolean zza(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zzc() == zzij.MESSAGE) {
            if (key.zzd()) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!((zzgi) it.next()).a_()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzgi) {
                    if (!((zzgi) value).a_()) {
                        return false;
                    }
                } else {
                    if (value instanceof zzfj) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzeu<T> zzeuVar) {
        for (int i = 0; i < zzeuVar.zza.zzc(); i++) {
            zzb(zzeuVar.zza.zzb(i));
        }
        Iterator it = zzeuVar.zza.zzd().iterator();
        while (it.hasNext()) {
            zzb((Map.Entry) it.next());
        }
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzgn) {
            return ((zzgn) obj).clone();
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
        zzgi zzgiVarZzg;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzfj) {
            value = zzfj.zza();
        }
        if (key.zzd()) {
            Object objZza = zza((zzew) key);
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
        if (key.zzc() == zzij.MESSAGE) {
            Object objZza2 = zza((zzew) key);
            if (objZza2 == null) {
                this.zza.put(key, zza(value));
                return;
            }
            if (objZza2 instanceof zzgn) {
                zzgiVarZzg = key.zza((zzgn) objZza2, (zzgn) value);
            } else {
                zzgiVarZzg = key.zza(((zzgi) objZza2).zzj(), (zzgi) value).zzg();
            }
            this.zza.put(key, zzgiVarZzg);
            return;
        }
        this.zza.put(key, zza(value));
    }

    static void zza(zzel zzelVar, zzig zzigVar, int i, Object obj) throws IOException {
        if (zzigVar == zzig.zzj) {
            zzgi zzgiVar = (zzgi) obj;
            zzfe.zza(zzgiVar);
            zzelVar.zza(i, 3);
            zzgiVar.zza(zzelVar);
            zzelVar.zza(i, 4);
        }
        zzelVar.zza(i, zzigVar.zzb());
        switch (zzet.zzb[zzigVar.ordinal()]) {
            case 1:
                zzelVar.zza(((Double) obj).doubleValue());
                break;
            case 2:
                zzelVar.zza(((Float) obj).floatValue());
                break;
            case 3:
                zzelVar.zza(((Long) obj).longValue());
                break;
            case 4:
                zzelVar.zza(((Long) obj).longValue());
                break;
            case 5:
                zzelVar.zza(((Integer) obj).intValue());
                break;
            case 6:
                zzelVar.zzc(((Long) obj).longValue());
                break;
            case 7:
                zzelVar.zzd(((Integer) obj).intValue());
                break;
            case 8:
                zzelVar.zza(((Boolean) obj).booleanValue());
                break;
            case 9:
                ((zzgi) obj).zza(zzelVar);
                break;
            case 10:
                zzelVar.zza((zzgi) obj);
                break;
            case 11:
                if (obj instanceof zzdu) {
                    zzelVar.zza((zzdu) obj);
                } else {
                    zzelVar.zza((String) obj);
                }
                break;
            case 12:
                if (obj instanceof zzdu) {
                    zzelVar.zza((zzdu) obj);
                } else {
                    byte[] bArr = (byte[]) obj;
                    zzelVar.zzb(bArr, 0, bArr.length);
                }
                break;
            case 13:
                zzelVar.zzb(((Integer) obj).intValue());
                break;
            case 14:
                zzelVar.zzd(((Integer) obj).intValue());
                break;
            case 15:
                zzelVar.zzc(((Long) obj).longValue());
                break;
            case 16:
                zzelVar.zzc(((Integer) obj).intValue());
                break;
            case 17:
                zzelVar.zzb(((Long) obj).longValue());
                break;
            case 18:
                if (obj instanceof zzfd) {
                    zzelVar.zza(((zzfd) obj).zza());
                } else {
                    zzelVar.zza(((Integer) obj).intValue());
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
        if (key.zzc() == zzij.MESSAGE && !key.zzd() && !key.zze()) {
            if (value instanceof zzfj) {
                return zzel.zzb(entry.getKey().zza(), (zzfj) value);
            }
            return zzel.zzb(entry.getKey().zza(), (zzgi) value);
        }
        return zza((zzew<?>) key, value);
    }

    static int zza(zzig zzigVar, int i, Object obj) {
        int iZze = zzel.zze(i);
        if (zzigVar == zzig.zzj) {
            zzfe.zza((zzgi) obj);
            iZze <<= 1;
        }
        return iZze + zzb(zzigVar, obj);
    }

    private static int zzb(zzig zzigVar, Object obj) {
        switch (zzet.zzb[zzigVar.ordinal()]) {
            case 1:
                return zzel.zzb(((Double) obj).doubleValue());
            case 2:
                return zzel.zzb(((Float) obj).floatValue());
            case 3:
                return zzel.zzd(((Long) obj).longValue());
            case 4:
                return zzel.zze(((Long) obj).longValue());
            case 5:
                return zzel.zzf(((Integer) obj).intValue());
            case 6:
                return zzel.zzg(((Long) obj).longValue());
            case 7:
                return zzel.zzi(((Integer) obj).intValue());
            case 8:
                return zzel.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzel.zzc((zzgi) obj);
            case 10:
                if (obj instanceof zzfj) {
                    return zzel.zza((zzfj) obj);
                }
                return zzel.zzb((zzgi) obj);
            case 11:
                if (obj instanceof zzdu) {
                    return zzel.zzb((zzdu) obj);
                }
                return zzel.zzb((String) obj);
            case 12:
                if (obj instanceof zzdu) {
                    return zzel.zzb((zzdu) obj);
                }
                return zzel.zzb((byte[]) obj);
            case 13:
                return zzel.zzg(((Integer) obj).intValue());
            case 14:
                return zzel.zzj(((Integer) obj).intValue());
            case 15:
                return zzel.zzh(((Long) obj).longValue());
            case 16:
                return zzel.zzh(((Integer) obj).intValue());
            case 17:
                return zzel.zzf(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzfd) {
                    return zzel.zzk(((zzfd) obj).zza());
                }
                return zzel.zzk(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zza(zzew<?> zzewVar, Object obj) {
        zzig zzigVarZzb = zzewVar.zzb();
        int iZza = zzewVar.zza();
        if (zzewVar.zzd()) {
            int iZza2 = 0;
            if (zzewVar.zze()) {
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    iZza2 += zzb(zzigVarZzb, it.next());
                }
                return zzel.zze(iZza) + iZza2 + zzel.zzl(iZza2);
            }
            Iterator it2 = ((List) obj).iterator();
            while (it2.hasNext()) {
                iZza2 += zza(zzigVarZzb, iZza, it2.next());
            }
            return iZza2;
        }
        return zza(zzigVarZzb, iZza, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzeu zzeuVar = new zzeu();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<K, Object> entryZzb = this.zza.zzb(i);
            zzeuVar.zzb((zzew) entryZzb.getKey(), entryZzb.getValue());
        }
        Iterator it = this.zza.zzd().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzeuVar.zzb((zzew) entry.getKey(), entry.getValue());
        }
        zzeuVar.zzc = this.zzc;
        return zzeuVar;
    }
}
