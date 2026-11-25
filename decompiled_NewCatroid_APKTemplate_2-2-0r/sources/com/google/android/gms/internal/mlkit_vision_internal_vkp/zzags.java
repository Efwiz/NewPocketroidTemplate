package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzags extends zzagw {
    private String zza;
    private Boolean zzb;
    private Integer zzc;

    zzags() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagw
    public final zzagw zza(boolean z) {
        this.zzb = true;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagw
    public final zzagw zzb(int i) {
        this.zzc = 1;
        return this;
    }

    public final zzagw zzc(String str) {
        this.zza = str;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagw
    public final zzagx zzd() {
        Boolean bool;
        String str = this.zza;
        if (str != null && (bool = this.zzb) != null && this.zzc != null) {
            return new zzagu(str, bool.booleanValue(), this.zzc.intValue(), null);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" libraryName");
        }
        if (this.zzb == null) {
            sb.append(" enableFirelog");
        }
        if (this.zzc == null) {
            sb.append(" firelogEventType");
        }
        String strValueOf = String.valueOf(sb);
        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 28);
        sb2.append("Missing required properties:");
        sb2.append(strValueOf);
        throw new IllegalStateException(sb2.toString());
    }
}
