package com.google.android.gms.internal.mlkit_vision_mediapipe;

import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbw extends zzbz {
    private final zzbx zza;
    private final String zzb;

    public static zzbz zza(zzbx zzbxVar, zzab zzabVar, int i) {
        return new zzbw(zzabVar, i, zzbxVar);
    }

    private zzbw(zzab zzabVar, int i, zzbx zzbxVar) {
        super(zzabVar, i);
        this.zza = zzbxVar;
        this.zzb = zzabVar.zza(new StringBuilder("%")).append(zzabVar.zzf() ? Util.C_TYPE_VARIABLE : Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL).append(zzbxVar.zza()).toString();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzbz
    protected final void zza(zzby zzbyVar, Object obj) {
        zzbyVar.zza(obj, this.zza, zzb());
    }
}
