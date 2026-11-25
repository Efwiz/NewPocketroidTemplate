package com.google.android.gms.internal.mlkit_vision_text;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkh implements zzkn {
    final List<zzkn> zza;

    public zzkh(Context context, zzkg zzkgVar) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        if (zzkgVar.zzc()) {
            arrayList.add(new zzkw(context, zzkgVar));
        }
        if (zzkgVar.zzb()) {
            arrayList.add(new zzkq(context));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkn
    public final void zza(zzkp zzkpVar) {
        Iterator<zzkn> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().zza(zzkpVar);
        }
    }
}
