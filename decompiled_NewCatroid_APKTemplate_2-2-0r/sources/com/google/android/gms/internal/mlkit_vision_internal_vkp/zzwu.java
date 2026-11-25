package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import org.slf4j.Marker;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwu implements zzvk {
    final /* synthetic */ Class zza;
    final /* synthetic */ Class zzb;
    final /* synthetic */ zzvj zzc;

    zzwu(Class cls, Class cls2, zzvj zzvjVar) {
        this.zza = cls;
        this.zzb = cls2;
        this.zzc = zzvjVar;
    }

    public final String toString() {
        String name2 = this.zza.getName();
        String name3 = this.zzb.getName();
        String strValueOf = String.valueOf(this.zzc);
        int length = String.valueOf(name2).length();
        StringBuilder sb = new StringBuilder(length + 24 + String.valueOf(name3).length() + String.valueOf(strValueOf).length());
        sb.append("Factory[type=");
        sb.append(name2);
        sb.append(Marker.ANY_NON_NULL_MARKER);
        sb.append(name3);
        sb.append(",adapter=");
        sb.append(strValueOf);
        sb.append("]");
        return sb.toString();
    }
}
