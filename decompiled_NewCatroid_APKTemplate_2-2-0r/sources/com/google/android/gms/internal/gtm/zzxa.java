package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzxa extends zzxk {
    zzxa(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.gtm.zzxk
    public final void zza() {
        if (!zzj()) {
            for (int i = 0; i < zzb(); i++) {
                ((zzun) zzg(i).getKey()).zzg();
            }
            Iterator it = zzc().iterator();
            while (it.hasNext()) {
                ((zzun) ((Map.Entry) it.next()).getKey()).zzg();
            }
        }
        super.zza();
    }
}
