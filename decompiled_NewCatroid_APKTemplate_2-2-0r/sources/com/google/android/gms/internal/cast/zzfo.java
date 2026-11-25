package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfo extends zzfg {
    private final zzfq zza;

    zzfo(zzfq zzfqVar, int i) {
        super(zzfqVar.size(), i);
        this.zza = zzfqVar;
    }

    @Override // com.google.android.gms.internal.cast.zzfg
    protected final Object zza(int i) {
        return this.zza.get(i);
    }
}
