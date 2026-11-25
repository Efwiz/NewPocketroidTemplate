package com.google.mlkit.common.model;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.mlkit_common.zzx;
import com.google.android.gms.internal.mlkit_common.zzy;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class RemoteModelSource {
    private final String zza;

    protected RemoteModelSource(String str) {
        this.zza = str;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        return Objects.equal(this.zza, ((RemoteModelSource) obj).zza);
    }

    public int hashCode() {
        return Objects.hashCode(this.zza);
    }

    public String toString() {
        zzx zzxVarZzb = zzy.zzb("RemoteModelSource");
        zzxVarZzb.zza("firebaseModelName", this.zza);
        return zzxVarZzb.toString();
    }

    public final String zza() {
        return this.zza;
    }
}
