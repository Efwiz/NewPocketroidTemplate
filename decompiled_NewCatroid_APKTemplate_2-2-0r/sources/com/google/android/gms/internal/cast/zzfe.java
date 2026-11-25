package com.google.android.gms.internal.cast;

import com.huawei.hms.ml.language.common.utils.Constant;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfe implements zzfc {
    private static final zzfc zza = new zzfc() { // from class: com.google.android.gms.internal.cast.zzfd
        @Override // com.google.android.gms.internal.cast.zzfc
        public final Object zza() {
            throw new IllegalStateException();
        }
    };
    private volatile zzfc zzb;

    @CheckForNull
    private Object zzc;

    zzfe(zzfc zzfcVar) {
        this.zzb = zzfcVar;
    }

    public final String toString() {
        Object obj = this.zzb;
        if (obj == zza) {
            obj = "<supplier that returned " + String.valueOf(this.zzc) + ">";
        }
        return "Suppliers.memoize(" + String.valueOf(obj) + Constant.AFTER_QUTO;
    }

    @Override // com.google.android.gms.internal.cast.zzfc
    public final Object zza() {
        zzfc zzfcVar = this.zzb;
        zzfc zzfcVar2 = zza;
        if (zzfcVar != zzfcVar2) {
            synchronized (this) {
                if (this.zzb != zzfcVar2) {
                    Object objZza = this.zzb.zza();
                    this.zzc = objZza;
                    this.zzb = zzfcVar2;
                    return objZza;
                }
            }
        }
        return this.zzc;
    }
}
