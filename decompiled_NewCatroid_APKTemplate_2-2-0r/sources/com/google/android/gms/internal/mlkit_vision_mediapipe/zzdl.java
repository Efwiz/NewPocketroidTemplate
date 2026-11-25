package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdl;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdn;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzdl<MessageType extends zzdl<MessageType, BuilderType>, BuilderType extends zzdn<MessageType, BuilderType>> implements zzgi {
    protected int zza = 0;

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgi
    public final zzdu zze() {
        try {
            zzec zzecVarZzc = zzdu.zzc(zzg());
            zza(zzecVarZzc.zzb());
            return zzecVarZzc.zza();
        } catch (IOException e) {
            String name2 = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name2).length() + 62 + String.valueOf("ByteString").length()).append("Serializing ").append(name2).append(" to a ByteString threw an IOException (should never happen).").toString(), e);
        }
    }

    int zzf() {
        throw new UnsupportedOperationException();
    }

    void zza(int i) {
        throw new UnsupportedOperationException();
    }
}
