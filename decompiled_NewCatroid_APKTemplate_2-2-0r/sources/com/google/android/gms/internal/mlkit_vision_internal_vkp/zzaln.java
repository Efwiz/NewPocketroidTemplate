package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaln;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalo;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzaln<MessageType extends zzalo<MessageType, BuilderType>, BuilderType extends zzaln<MessageType, BuilderType>> implements zzape {
    @Override // 
    public abstract BuilderType zzm();

    protected abstract BuilderType zzn(MessageType messagetype);

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzape
    public final /* bridge */ /* synthetic */ zzape zzo(zzapf zzapfVar) {
        if (zzv().getClass().isInstance(zzapfVar)) {
            return zzn((zzalo) zzapfVar);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
