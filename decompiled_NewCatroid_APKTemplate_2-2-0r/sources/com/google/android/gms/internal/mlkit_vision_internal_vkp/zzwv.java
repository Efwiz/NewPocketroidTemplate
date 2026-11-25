package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwv implements zzvk {
    final /* synthetic */ Class zza;
    final /* synthetic */ zzvj zzb;

    zzwv(Class cls, zzvj zzvjVar) {
        this.zza = cls;
        this.zzb = zzvjVar;
    }

    public final String toString() {
        String name2 = this.zza.getName();
        String strValueOf = String.valueOf(this.zzb);
        StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 32 + String.valueOf(strValueOf).length());
        sb.append("Factory[typeHierarchy=");
        sb.append(name2);
        sb.append(",adapter=");
        sb.append(strValueOf);
        sb.append("]");
        return sb.toString();
    }
}
