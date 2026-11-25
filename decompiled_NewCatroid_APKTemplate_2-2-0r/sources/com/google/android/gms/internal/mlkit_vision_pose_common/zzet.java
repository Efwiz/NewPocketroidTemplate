package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.android.gms.internal.mlkit_vision_pose_common.zzet;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzeu;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzet<MessageType extends zzeu<MessageType, BuilderType>, BuilderType extends zzet<MessageType, BuilderType>> implements zzho {
    protected abstract BuilderType zza(MessageType messagetype);

    @Override // 
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzho
    public final /* synthetic */ zzho zza(zzhl zzhlVar) {
        if (!zzi().getClass().isInstance(zzhlVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zzeu) zzhlVar);
    }
}
