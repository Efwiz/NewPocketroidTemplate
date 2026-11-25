package com.google.android.gms.internal.mlkit_vision_common;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhs implements zzhw {
    final List<zzhw> zza;

    public zzhs(Context context, zzhr zzhrVar) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        if (zzhrVar.zzc()) {
            arrayList.add(new zzid(context, zzhrVar));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzhw
    public final void zza(zzhy zzhyVar) {
        Iterator<zzhw> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().zza(zzhyVar);
        }
    }
}
