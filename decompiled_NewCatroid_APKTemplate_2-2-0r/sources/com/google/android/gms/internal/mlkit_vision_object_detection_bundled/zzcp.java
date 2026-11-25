package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.math.BigInteger;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcp extends zzcj {
    private final Object zza;

    public zzcp(Boolean bool) {
        this.zza = bool;
    }

    public zzcp(Number number) {
        this.zza = number;
    }

    public zzcp(String str) {
        str.getClass();
        this.zza = str;
    }

    private static boolean zzh(zzcp zzcpVar) {
        Object obj = zzcpVar.zza;
        if (!(obj instanceof Number)) {
            return false;
        }
        Number number = (Number) obj;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzcp zzcpVar = (zzcp) obj;
        if (zzh(this) && zzh(zzcpVar)) {
            return zzc().longValue() == zzcpVar.zzc().longValue();
        }
        Object obj2 = this.zza;
        if (!(obj2 instanceof Number) || !(zzcpVar.zza instanceof Number)) {
            return obj2.equals(zzcpVar.zza);
        }
        double dDoubleValue = zzc().doubleValue();
        double dDoubleValue2 = zzcpVar.zzc().doubleValue();
        if (dDoubleValue != dDoubleValue2) {
            return Double.isNaN(dDoubleValue) && Double.isNaN(dDoubleValue2);
        }
        return true;
    }

    public final int hashCode() {
        long jDoubleToLongBits;
        if (zzh(this)) {
            jDoubleToLongBits = zzc().longValue();
        } else {
            Object obj = this.zza;
            if (!(obj instanceof Number)) {
                return obj.hashCode();
            }
            jDoubleToLongBits = Double.doubleToLongBits(zzc().doubleValue());
        }
        return (int) ((jDoubleToLongBits >>> 32) ^ jDoubleToLongBits);
    }

    public final long zza() {
        return this.zza instanceof Number ? zzc().longValue() : Long.parseLong(zzd());
    }

    public final Number zzc() {
        Object obj = this.zza;
        return obj instanceof String ? new zzcv((String) obj) : (Number) obj;
    }

    public final String zzd() {
        Object obj = this.zza;
        if (obj instanceof Number) {
            return zzc().toString();
        }
        return obj instanceof Boolean ? ((Boolean) obj).toString() : (String) obj;
    }

    public final boolean zze() {
        Object obj = this.zza;
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : Boolean.parseBoolean(zzd());
    }

    public final boolean zzf() {
        return this.zza instanceof Boolean;
    }

    public final boolean zzg() {
        return this.zza instanceof Number;
    }
}
