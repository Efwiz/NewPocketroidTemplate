package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzd;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzc<LOGGER extends zzd<API>, API> extends zzk<LOGGER, API> implements zzv<API> {
    protected zzc(Level level, boolean z) {
        super(level, z);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzk
    protected final zzcf zza() {
        return zzca.zza();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzk
    protected final boolean zza(@NullableDecl zzp zzpVar) {
        zzan zzanVarZzk = zzk();
        int iZza = zzanVarZzk.zza();
        for (int i = 0; i < iZza; i++) {
            if (zzanVarZzk.zza(i).zza() == "eye3tag") {
                if (zzanVarZzk.zza(zzk.zza.zza) != null || zzanVarZzk.zza(zzk.zza.zzg) != null) {
                    break;
                    break;
                }
                zza((zzx<zzw>) zzk.zza.zzg, zzw.SMALL);
                break;
            }
        }
        return super.zza(zzpVar);
    }
}
