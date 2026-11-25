package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzx implements zzaq {
    final /* synthetic */ Container zza;

    @Override // com.google.android.gms.tagmanager.zzaq
    public final Object zza(String str, Map<String, Object> map) {
        Container.FunctionCallTagCallback functionCallTagCallbackZzb = this.zza.zzb(str);
        if (functionCallTagCallbackZzb != null) {
            functionCallTagCallbackZzb.execute(str, map);
        }
        return zzfv.zzm();
    }
}
