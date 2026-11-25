package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzvd extends zzva {
    private final zzvu<String, zzva> zza = new zzvu<>();

    public final boolean equals(Object obj) {
        if (obj != this) {
            return (obj instanceof zzvd) && ((zzvd) obj).zza.equals(this.zza);
        }
        return true;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final zzvd zza(String str) {
        return (zzvd) this.zza.get("authToken");
    }

    public final zzvg zzc(String str) {
        return (zzvg) this.zza.get(str);
    }

    public final Set<Map.Entry<String, zzva>> zzd() {
        return this.zza.entrySet();
    }

    public final void zze(String str, zzva zzvaVar) {
        this.zza.put(str, zzvaVar);
    }
}
