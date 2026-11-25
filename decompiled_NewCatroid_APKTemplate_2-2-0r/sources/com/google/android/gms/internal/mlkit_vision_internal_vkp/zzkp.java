package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkp {
    private final zzkc zza;
    private final zzkn zzb;

    private zzkp(zzkn zzknVar, byte[] bArr) {
        zzkb zzkbVar = zzkb.zza;
        this.zzb = zzknVar;
        this.zza = zzkbVar;
    }

    public static zzkp zzb(String str) {
        return new zzkp(new zzkn("#vk "), null);
    }

    public final List<String> zzc(CharSequence charSequence) {
        charSequence.getClass();
        zzkm zzkmVar = new zzkm(this.zzb, this, charSequence);
        ArrayList arrayList = new ArrayList();
        while (zzkmVar.hasNext()) {
            arrayList.add(zzkmVar.next());
        }
        return Collections.unmodifiableList(arrayList);
    }
}
