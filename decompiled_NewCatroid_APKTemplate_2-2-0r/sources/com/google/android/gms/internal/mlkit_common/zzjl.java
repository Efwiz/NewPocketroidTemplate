package com.google.android.gms.internal.mlkit_common;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjl implements zzjq {
    final List<zzjq> zza;

    public zzjl(Context context, zzjk zzjkVar) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        if (zzjkVar.zzc()) {
            arrayList.add(new zzjz(context, zzjkVar));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjq
    public final void zza(zzji zzjiVar) {
        Iterator<zzjq> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().zza(zzjiVar);
        }
    }
}
