package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdl;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdn;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzdn<MessageType extends zzdl<MessageType, BuilderType>, BuilderType extends zzdn<MessageType, BuilderType>> implements zzgh {
    @Override // 
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    protected abstract BuilderType zza(MessageType messagetype);

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgh
    public final /* synthetic */ zzgh zza(zzgi zzgiVar) {
        if (!zzh().getClass().isInstance(zzgiVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zzdl) zzgiVar);
    }
}
