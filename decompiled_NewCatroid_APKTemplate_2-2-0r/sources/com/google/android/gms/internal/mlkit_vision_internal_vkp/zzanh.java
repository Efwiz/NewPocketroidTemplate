package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzang;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzanh<T extends zzang<T>> {
    private static final zzanh zzb = new zzanh(true);
    final zzaqm<T, Object> zza = new zzaqc(16);
    private boolean zzc;
    private boolean zzd;

    private zzanh() {
    }

    static int zza(zzarj zzarjVar, int i, Object obj) {
        int iZzL = zzamx.zzL(i);
        if (zzarjVar == zzarj.GROUP) {
            zzaoc.zzi((zzapf) obj);
            iZzL += iZzL;
        }
        return iZzL + zzb(zzarjVar, obj);
    }

    static int zzb(zzarj zzarjVar, Object obj) {
        zzarj zzarjVar2 = zzarj.DOUBLE;
        zzark zzarkVar = zzark.INT;
        switch (zzarjVar) {
            case DOUBLE:
                ((Double) obj).doubleValue();
                int i = zzamx.zzf;
                return 8;
            case FLOAT:
                ((Float) obj).floatValue();
                int i2 = zzamx.zzf;
                return 4;
            case INT64:
                return zzamx.zzN(((Long) obj).longValue());
            case UINT64:
                return zzamx.zzN(((Long) obj).longValue());
            case INT32:
                return zzamx.zzF(((Integer) obj).intValue());
            case FIXED64:
                ((Long) obj).longValue();
                int i3 = zzamx.zzf;
                return 8;
            case FIXED32:
                ((Integer) obj).intValue();
                int i4 = zzamx.zzf;
                return 4;
            case BOOL:
                ((Boolean) obj).booleanValue();
                int i5 = zzamx.zzf;
                return 1;
            case STRING:
                return obj instanceof zzamk ? zzamx.zzC((zzamk) obj) : zzamx.zzK((String) obj);
            case GROUP:
                return zzamx.zzE((zzapf) obj);
            case MESSAGE:
                return obj instanceof zzaoj ? zzamx.zzG((zzaoj) obj) : zzamx.zzH((zzapf) obj);
            case BYTES:
                return obj instanceof zzamk ? zzamx.zzC((zzamk) obj) : zzamx.zzB((byte[]) obj);
            case UINT32:
                return zzamx.zzM(((Integer) obj).intValue());
            case ENUM:
                return obj instanceof zzanu ? zzamx.zzF(((zzanu) obj).zza()) : zzamx.zzF(((Integer) obj).intValue());
            case SFIXED32:
                ((Integer) obj).intValue();
                int i6 = zzamx.zzf;
                return 4;
            case SFIXED64:
                ((Long) obj).longValue();
                int i7 = zzamx.zzf;
                return 8;
            case SINT32:
                int iIntValue = ((Integer) obj).intValue();
                return zzamx.zzM((iIntValue >> 31) ^ (iIntValue + iIntValue));
            case SINT64:
                long jLongValue = ((Long) obj).longValue();
                return zzamx.zzN((jLongValue >> 63) ^ (jLongValue + jLongValue));
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzc(zzang<?> zzangVar, Object obj) {
        zzarj zzarjVarZzd = zzangVar.zzd();
        int iZza = zzangVar.zza();
        if (!zzangVar.zzg()) {
            return zza(zzarjVarZzd, iZza, obj);
        }
        int iZza2 = 0;
        if (zzangVar.zzf()) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                iZza2 += zzb(zzarjVarZzd, it.next());
            }
            return zzamx.zzL(iZza) + iZza2 + zzamx.zzM(iZza2);
        }
        Iterator it2 = ((List) obj).iterator();
        while (it2.hasNext()) {
            iZza2 += zza(zzarjVarZzd, iZza, it2.next());
        }
        return iZza2;
    }

    public static <T extends zzang<T>> zzanh<T> zzf() {
        return zzb;
    }

    static void zzl(zzamx zzamxVar, zzarj zzarjVar, int i, Object obj) throws IOException {
        if (zzarjVar == zzarj.GROUP) {
            zzapf zzapfVar = (zzapf) obj;
            zzaoc.zzi(zzapfVar);
            zzamxVar.zzv(i, 3);
            zzapfVar.zzQ(zzamxVar);
            zzamxVar.zzv(i, 4);
            return;
        }
        zzamxVar.zzv(i, zzarjVar.zza());
        zzark zzarkVar = zzark.INT;
        switch (zzarjVar) {
            case DOUBLE:
                zzamxVar.zzm(Double.doubleToRawLongBits(((Double) obj).doubleValue()));
                break;
            case FLOAT:
                zzamxVar.zzk(Float.floatToRawIntBits(((Float) obj).floatValue()));
                break;
            case INT64:
                zzamxVar.zzz(((Long) obj).longValue());
                break;
            case UINT64:
                zzamxVar.zzz(((Long) obj).longValue());
                break;
            case INT32:
                zzamxVar.zzo(((Integer) obj).intValue());
                break;
            case FIXED64:
                zzamxVar.zzm(((Long) obj).longValue());
                break;
            case FIXED32:
                zzamxVar.zzk(((Integer) obj).intValue());
                break;
            case BOOL:
                zzamxVar.zzW(((Boolean) obj).booleanValue() ? (byte) 1 : (byte) 0);
                break;
            case STRING:
                if (!(obj instanceof zzamk)) {
                    zzamxVar.zzu((String) obj);
                } else {
                    zzamxVar.zzi((zzamk) obj);
                }
                break;
            case GROUP:
                ((zzapf) obj).zzQ(zzamxVar);
                break;
            case MESSAGE:
                zzamxVar.zzq((zzapf) obj);
                break;
            case BYTES:
                if (!(obj instanceof zzamk)) {
                    byte[] bArr = (byte[]) obj;
                    zzamxVar.zzY(bArr, 0, bArr.length);
                } else {
                    zzamxVar.zzi((zzamk) obj);
                }
                break;
            case UINT32:
                zzamxVar.zzx(((Integer) obj).intValue());
                break;
            case ENUM:
                if (!(obj instanceof zzanu)) {
                    zzamxVar.zzo(((Integer) obj).intValue());
                } else {
                    zzamxVar.zzo(((zzanu) obj).zza());
                }
                break;
            case SFIXED32:
                zzamxVar.zzk(((Integer) obj).intValue());
                break;
            case SFIXED64:
                zzamxVar.zzm(((Long) obj).longValue());
                break;
            case SINT32:
                int iIntValue = ((Integer) obj).intValue();
                zzamxVar.zzx((iIntValue >> 31) ^ (iIntValue + iIntValue));
                break;
            case SINT64:
                long jLongValue = ((Long) obj).longValue();
                zzamxVar.zzz((jLongValue >> 63) ^ (jLongValue + jLongValue));
                break;
        }
    }

    private static Object zzo(Object obj) {
        if (obj instanceof zzapk) {
            return ((zzapk) obj).zzc();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    private final void zzp(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzaoj) {
            throw null;
        }
        if (key.zzg()) {
            Object objZzg = zzg(key);
            if (objZzg == null) {
                objZzg = new ArrayList();
            }
            Iterator it = ((List) value).iterator();
            while (it.hasNext()) {
                ((List) objZzg).add(zzo(it.next()));
            }
            this.zza.put(key, objZzg);
            return;
        }
        if (key.zze() != zzark.MESSAGE) {
            this.zza.put(key, zzo(value));
            return;
        }
        Object objZzg2 = zzg(key);
        if (objZzg2 == null) {
            this.zza.put(key, zzo(value));
        } else {
            this.zza.put(key, objZzg2 instanceof zzapk ? key.zzc((zzapk) objZzg2, (zzapk) value) : key.zzb(((zzapf) objZzg2).zzO(), (zzapf) value).zzt());
        }
    }

    private static <T extends zzang<T>> boolean zzq(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zze() == zzark.MESSAGE) {
            if (key.zzg()) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!((zzapf) it.next()).zzx()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (!(value instanceof zzapf)) {
                    if (value instanceof zzaoj) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
                if (!((zzapf) value).zzx()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final int zzr(Map.Entry<T, Object> entry) {
        int iZzM;
        int iZzM2;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (key.zze() != zzark.MESSAGE || key.zzg() || key.zzf()) {
            return zzc(key, value);
        }
        if (value instanceof zzaoj) {
            int iZza = entry.getKey().zza();
            int iZzM3 = zzamx.zzM(8);
            int iZza2 = ((zzaoj) value).zza();
            iZzM = iZzM3 + iZzM3 + zzamx.zzM(16) + zzamx.zzM(iZza);
            iZzM2 = zzamx.zzM(24) + zzamx.zzM(iZza2) + iZza2;
        } else {
            int iZza3 = entry.getKey().zza();
            int iZzM4 = zzamx.zzM(8);
            iZzM = iZzM4 + iZzM4 + zzamx.zzM(16) + zzamx.zzM(iZza3);
            iZzM2 = zzamx.zzM(24) + zzamx.zzH((zzapf) value);
        }
        return iZzM + iZzM2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:32:? A[RETURN, SYNTHETIC] */
    private static final void zzs(T t, Object obj) {
        boolean z;
        zzarj zzarjVarZzd = t.zzd();
        zzaoc.zze(obj);
        zzarj zzarjVar = zzarj.DOUBLE;
        zzark zzarkVar = zzark.INT;
        switch (zzarjVarZzd.zzb().ordinal()) {
            case 0:
                z = obj instanceof Integer;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 1:
                z = obj instanceof Long;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 2:
                z = obj instanceof Float;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 3:
                z = obj instanceof Double;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 4:
                z = obj instanceof Boolean;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 5:
                z = obj instanceof String;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 6:
                if ((obj instanceof zzamk) || (obj instanceof byte[])) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 7:
                if ((obj instanceof Integer) || (obj instanceof zzanu)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            case 8:
                if ((obj instanceof zzapf) || (obj instanceof zzaoj)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
            default:
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zzb(), obj.getClass().getName()));
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzanh) {
            return this.zza.equals(((zzanh) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final int zzd() {
        int iZzr = 0;
        for (int i = 0; i < this.zza.zzb(); i++) {
            iZzr += zzr(this.zza.zzg(i));
        }
        Iterator it = this.zza.zzc().iterator();
        while (it.hasNext()) {
            iZzr += zzr((Map.Entry) it.next());
        }
        return iZzr;
    }

    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final zzanh<T> clone() {
        zzanh<T> zzanhVar = new zzanh<>();
        for (int i = 0; i < this.zza.zzb(); i++) {
            Map.Entry<K, Object> entryZzg = this.zza.zzg(i);
            zzanhVar.zzk((zzang) entryZzg.getKey(), entryZzg.getValue());
        }
        Iterator it = this.zza.zzc().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzanhVar.zzk((zzang) entry.getKey(), entry.getValue());
        }
        zzanhVar.zzd = this.zzd;
        return zzanhVar;
    }

    public final Object zzg(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzaoj)) {
            return obj;
        }
        throw null;
    }

    public final Iterator<Map.Entry<T, Object>> zzh() {
        return this.zzd ? new zzaoi(this.zza.entrySet().iterator()) : this.zza.entrySet().iterator();
    }

    public final void zzi() {
        if (this.zzc) {
            return;
        }
        this.zza.zza();
        this.zzc = true;
    }

    public final void zzj(zzanh<T> zzanhVar) {
        for (int i = 0; i < zzanhVar.zza.zzb(); i++) {
            zzp(zzanhVar.zza.zzg(i));
        }
        Iterator it = zzanhVar.zza.zzc().iterator();
        while (it.hasNext()) {
            zzp((Map.Entry) it.next());
        }
    }

    public final void zzk(T t, Object obj) {
        if (!t.zzg()) {
            zzs(t, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                zzs(t, arrayList.get(i));
            }
            obj = arrayList;
        }
        if (obj instanceof zzaoj) {
            this.zzd = true;
        }
        this.zza.put(t, obj);
    }

    public final boolean zzm() {
        return this.zzc;
    }

    public final boolean zzn() {
        for (int i = 0; i < this.zza.zzb(); i++) {
            if (!zzq(this.zza.zzg(i))) {
                return false;
            }
        }
        Iterator it = this.zza.zzc().iterator();
        while (it.hasNext()) {
            if (!zzq((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    private zzanh(boolean z) {
        zzi();
        zzi();
    }
}
