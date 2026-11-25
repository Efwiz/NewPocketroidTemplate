package com.google.android.gms.internal.mlkit_language_id;

import com.google.android.gms.internal.mlkit_language_id.zzde;
import com.google.android.gms.internal.mlkit_language_id.zzdh;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzdh<MessageType extends zzde<MessageType, BuilderType>, BuilderType extends zzdh<MessageType, BuilderType>> implements zzfy {
    protected abstract BuilderType zza(MessageType messagetype);

    @Override // 
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfy
    public final /* synthetic */ zzfy zza(zzfz zzfzVar) {
        if (!zzn().getClass().isInstance(zzfzVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zzde) zzfzVar);
    }
}
