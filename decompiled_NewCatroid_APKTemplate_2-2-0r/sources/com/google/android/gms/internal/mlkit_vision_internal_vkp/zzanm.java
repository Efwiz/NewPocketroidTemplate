package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzanm<MessageType extends zzans<MessageType, BuilderType>, BuilderType extends zzanm<MessageType, BuilderType>> extends zzaln<MessageType, BuilderType> {
    protected MessageType zza;
    protected boolean zzb = false;
    private final MessageType zzc;

    protected zzanm(MessageType messagetype) {
        this.zzc = messagetype;
        this.zza = (MessageType) messagetype.zzb(4, null, null);
    }

    private static final void zza(MessageType messagetype, MessageType messagetype2) {
        zzapo.zza().zzb(messagetype.getClass()).zzg(messagetype, messagetype2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaln
    protected final /* bridge */ /* synthetic */ zzaln zzn(zzalo zzaloVar) {
        zzq((zzans) zzaloVar);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaln
    /* JADX INFO: renamed from: zzp, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final BuilderType zzm() {
        BuilderType buildertype = (BuilderType) this.zzc.zzb(5, null, null);
        buildertype.zzq(zzu());
        return buildertype;
    }

    public final BuilderType zzq(MessageType messagetype) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zza(this.zza, messagetype);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzape
    /* JADX INFO: renamed from: zzr, reason: merged with bridge method [inline-methods] */
    public final MessageType zzt() {
        MessageType messagetype = (MessageType) zzu();
        if (messagetype.zzx()) {
            return messagetype;
        }
        throw new zzaqs(messagetype);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzape
    /* JADX INFO: renamed from: zzs, reason: merged with bridge method [inline-methods] */
    public MessageType zzu() {
        if (this.zzb) {
            return this.zza;
        }
        MessageType messagetype = this.zza;
        zzapo.zza().zzb(messagetype.getClass()).zzf(messagetype);
        this.zzb = true;
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg
    public final /* bridge */ /* synthetic */ zzapf zzv() {
        return this.zzc;
    }

    protected void zzw() {
        MessageType messagetype = (MessageType) this.zza.zzb(4, null, null);
        zza(messagetype, this.zza);
        this.zza = messagetype;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg
    public final boolean zzx() {
        throw null;
    }
}
