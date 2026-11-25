package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzut;
import com.google.android.gms.internal.gtm.zzuz;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public class zzut<MessageType extends zzuz<MessageType, BuilderType>, BuilderType extends zzut<MessageType, BuilderType>> extends zzsg<MessageType, BuilderType> {
    protected MessageType zza;
    protected boolean zzb = false;
    private final MessageType zzc;

    protected zzut(MessageType messagetype) {
        this.zzc = messagetype;
        this.zza = (MessageType) messagetype.zzb(4, null, null);
    }

    private static final void zza(MessageType messagetype, MessageType messagetype2) {
        zzwt.zza().zzb(messagetype.getClass()).zzg(messagetype, messagetype2);
    }

    @Override // com.google.android.gms.internal.gtm.zzwj
    /* JADX INFO: renamed from: zzA, reason: merged with bridge method [inline-methods] */
    public final MessageType zzC() {
        MessageType messagetype = (MessageType) zzD();
        if (messagetype.zzas()) {
            return messagetype;
        }
        throw new zzxn(messagetype);
    }

    @Override // com.google.android.gms.internal.gtm.zzwj
    /* JADX INFO: renamed from: zzB, reason: merged with bridge method [inline-methods] */
    public MessageType zzD() {
        if (this.zzb) {
            return this.zza;
        }
        MessageType messagetype = this.zza;
        zzwt.zza().zzb(messagetype.getClass()).zzf(messagetype);
        this.zzb = true;
        return this.zza;
    }

    protected void zzF() {
        MessageType messagetype = (MessageType) this.zza.zzb(4, null, null);
        zza(messagetype, this.zza);
        this.zza = messagetype;
    }

    @Override // com.google.android.gms.internal.gtm.zzwl
    public final /* bridge */ /* synthetic */ zzwk zzar() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.gtm.zzwl
    public final boolean zzas() {
        throw null;
    }

    @Override // com.google.android.gms.internal.gtm.zzsg
    protected final /* bridge */ /* synthetic */ zzsg zzw(zzsh zzshVar) {
        zzz((zzuz) zzshVar);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzsg
    /* JADX INFO: renamed from: zzy, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final BuilderType zzv() {
        BuilderType buildertype = (BuilderType) this.zzc.zzb(5, null, null);
        buildertype.zzz(zzD());
        return buildertype;
    }

    public final BuilderType zzz(MessageType messagetype) {
        if (this.zzb) {
            zzF();
            this.zzb = false;
        }
        zza(this.zza, messagetype);
        return this;
    }
}
