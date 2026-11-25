package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.android.gms.internal.mlkit_vision_pose_common.zzfx;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfv<T extends zzfx<T>> {
    private static final zzfv zzd = new zzfv(true);
    final zzig<T, Object> zza;
    private boolean zzb;
    private boolean zzc;

    private zzfv() {
        this.zza = zzig.zza(16);
    }

    private zzfv(boolean z) {
        this(zzig.zza(0));
        zzb();
    }

    private zzfv(zzig<T, Object> zzigVar) {
        this.zza = zzigVar;
        zzb();
    }

    public static <T extends zzfx<T>> zzfv<T> zza() {
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
        if (obj instanceof zzfv) {
            return this.zza.equals(((zzfv) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> zzd() {
        if (this.zzc) {
            return new zzgr(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    final Iterator<Map.Entry<T, Object>> zze() {
        if (this.zzc) {
            return new zzgr(this.zza.zze().iterator());
        }
        return this.zza.zze().iterator();
    }

    private final Object zza(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzgq)) {
            return obj;
        }
        return zzgq.zza();
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
        if (obj instanceof zzgq) {
            this.zzc = true;
        }
        this.zza.put(t, obj);
    }

    private static void zza(zzjh zzjhVar, Object obj) {
        zzgg.zza(obj);
        boolean z = true;
        switch (zzfy.zza[zzjhVar.zza().ordinal()]) {
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
                if (!(obj instanceof zzez) && !(obj instanceof byte[])) {
                    z = false;
                }
                break;
            case 8:
                if (!(obj instanceof Integer) && !(obj instanceof zzgf)) {
                    z = false;
                }
                break;
            case 9:
                if (!(obj instanceof zzhl) && !(obj instanceof zzgq)) {
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

    private static <T extends zzfx<T>> boolean zza(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zzc() == zzjo.MESSAGE) {
            if (key.zzd()) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!((zzhl) it.next()).b_()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzhl) {
                    if (!((zzhl) value).b_()) {
                        return false;
                    }
                } else {
                    if (value instanceof zzgq) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzfv<T> zzfvVar) {
        for (int i = 0; i < zzfvVar.zza.zzc(); i++) {
            zzb(zzfvVar.zza.zzb(i));
        }
        Iterator it = zzfvVar.zza.zzd().iterator();
        while (it.hasNext()) {
            zzb((Map.Entry) it.next());
        }
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzhr) {
            return ((zzhr) obj).clone();
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
        zzhl zzhlVarZzh;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzgq) {
            value = zzgq.zza();
        }
        if (key.zzd()) {
            Object objZza = zza((zzfx) key);
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
        if (key.zzc() == zzjo.MESSAGE) {
            Object objZza2 = zza((zzfx) key);
            if (objZza2 == null) {
                this.zza.put(key, zza(value));
                return;
            }
            if (objZza2 instanceof zzhr) {
                zzhlVarZzh = key.zza((zzhr) objZza2, (zzhr) value);
            } else {
                zzhlVarZzh = key.zza(((zzhl) objZza2).zzm(), (zzhl) value).zzh();
            }
            this.zza.put(key, zzhlVarZzh);
            return;
        }
        this.zza.put(key, zza(value));
    }

    static void zza(zzfq zzfqVar, zzjh zzjhVar, int i, Object obj) throws IOException {
        if (zzjhVar == zzjh.zzj) {
            zzhl zzhlVar = (zzhl) obj;
            zzgg.zza(zzhlVar);
            zzfqVar.zza(i, 3);
            zzhlVar.zza(zzfqVar);
            zzfqVar.zza(i, 4);
        }
        zzfqVar.zza(i, zzjhVar.zzb());
        switch (zzfy.zzb[zzjhVar.ordinal()]) {
            case 1:
                zzfqVar.zza(((Double) obj).doubleValue());
                break;
            case 2:
                zzfqVar.zza(((Float) obj).floatValue());
                break;
            case 3:
                zzfqVar.zza(((Long) obj).longValue());
                break;
            case 4:
                zzfqVar.zza(((Long) obj).longValue());
                break;
            case 5:
                zzfqVar.zza(((Integer) obj).intValue());
                break;
            case 6:
                zzfqVar.zzc(((Long) obj).longValue());
                break;
            case 7:
                zzfqVar.zzd(((Integer) obj).intValue());
                break;
            case 8:
                zzfqVar.zza(((Boolean) obj).booleanValue());
                break;
            case 9:
                ((zzhl) obj).zza(zzfqVar);
                break;
            case 10:
                zzfqVar.zza((zzhl) obj);
                break;
            case 11:
                if (obj instanceof zzez) {
                    zzfqVar.zza((zzez) obj);
                } else {
                    zzfqVar.zza((String) obj);
                }
                break;
            case 12:
                if (obj instanceof zzez) {
                    zzfqVar.zza((zzez) obj);
                } else {
                    byte[] bArr = (byte[]) obj;
                    zzfqVar.zzb(bArr, 0, bArr.length);
                }
                break;
            case 13:
                zzfqVar.zzb(((Integer) obj).intValue());
                break;
            case 14:
                zzfqVar.zzd(((Integer) obj).intValue());
                break;
            case 15:
                zzfqVar.zzc(((Long) obj).longValue());
                break;
            case 16:
                zzfqVar.zzc(((Integer) obj).intValue());
                break;
            case 17:
                zzfqVar.zzb(((Long) obj).longValue());
                break;
            case 18:
                if (obj instanceof zzgf) {
                    zzfqVar.zza(((zzgf) obj).zza());
                } else {
                    zzfqVar.zza(((Integer) obj).intValue());
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
        if (key.zzc() == zzjo.MESSAGE && !key.zzd() && !key.zze()) {
            if (value instanceof zzgq) {
                return zzfq.zzb(entry.getKey().zza(), (zzgq) value);
            }
            return zzfq.zzb(entry.getKey().zza(), (zzhl) value);
        }
        return zza((zzfx<?>) key, value);
    }

    static int zza(zzjh zzjhVar, int i, Object obj) {
        int iZze = zzfq.zze(i);
        if (zzjhVar == zzjh.zzj) {
            zzgg.zza((zzhl) obj);
            iZze <<= 1;
        }
        return iZze + zzb(zzjhVar, obj);
    }

    private static int zzb(zzjh zzjhVar, Object obj) {
        switch (zzfy.zzb[zzjhVar.ordinal()]) {
            case 1:
                return zzfq.zzb(((Double) obj).doubleValue());
            case 2:
                return zzfq.zzb(((Float) obj).floatValue());
            case 3:
                return zzfq.zzd(((Long) obj).longValue());
            case 4:
                return zzfq.zze(((Long) obj).longValue());
            case 5:
                return zzfq.zzf(((Integer) obj).intValue());
            case 6:
                return zzfq.zzg(((Long) obj).longValue());
            case 7:
                return zzfq.zzi(((Integer) obj).intValue());
            case 8:
                return zzfq.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzfq.zzc((zzhl) obj);
            case 10:
                if (obj instanceof zzgq) {
                    return zzfq.zza((zzgq) obj);
                }
                return zzfq.zzb((zzhl) obj);
            case 11:
                if (obj instanceof zzez) {
                    return zzfq.zzb((zzez) obj);
                }
                return zzfq.zzb((String) obj);
            case 12:
                if (obj instanceof zzez) {
                    return zzfq.zzb((zzez) obj);
                }
                return zzfq.zzb((byte[]) obj);
            case 13:
                return zzfq.zzg(((Integer) obj).intValue());
            case 14:
                return zzfq.zzj(((Integer) obj).intValue());
            case 15:
                return zzfq.zzh(((Long) obj).longValue());
            case 16:
                return zzfq.zzh(((Integer) obj).intValue());
            case 17:
                return zzfq.zzf(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzgf) {
                    return zzfq.zzk(((zzgf) obj).zza());
                }
                return zzfq.zzk(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zza(zzfx<?> zzfxVar, Object obj) {
        zzjh zzjhVarZzb = zzfxVar.zzb();
        int iZza = zzfxVar.zza();
        if (zzfxVar.zzd()) {
            int iZza2 = 0;
            if (zzfxVar.zze()) {
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    iZza2 += zzb(zzjhVarZzb, it.next());
                }
                return zzfq.zze(iZza) + iZza2 + zzfq.zzl(iZza2);
            }
            Iterator it2 = ((List) obj).iterator();
            while (it2.hasNext()) {
                iZza2 += zza(zzjhVarZzb, iZza, it2.next());
            }
            return iZza2;
        }
        return zza(zzjhVarZzb, iZza, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzfv zzfvVar = new zzfv();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<K, Object> entryZzb = this.zza.zzb(i);
            zzfvVar.zzb((zzfx) entryZzb.getKey(), entryZzb.getValue());
        }
        Iterator it = this.zza.zzd().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzfvVar.zzb((zzfx) entry.getKey(), entry.getValue());
        }
        zzfvVar.zzc = this.zzc;
        return zzfvVar;
    }
}
