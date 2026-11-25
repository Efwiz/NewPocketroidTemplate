package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzsg;
import com.google.android.gms.internal.gtm.zzsh;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzsg<MessageType extends zzsh<MessageType, BuilderType>, BuilderType extends zzsg<MessageType, BuilderType>> implements zzwj {
    @Override // 
    public abstract BuilderType zzv();

    protected abstract BuilderType zzw(MessageType messagetype);

    @Override // com.google.android.gms.internal.gtm.zzwj
    public final /* bridge */ /* synthetic */ zzwj zzx(zzwk zzwkVar) {
        if (zzar().getClass().isInstance(zzwkVar)) {
            return zzw((zzsh) zzwkVar);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
