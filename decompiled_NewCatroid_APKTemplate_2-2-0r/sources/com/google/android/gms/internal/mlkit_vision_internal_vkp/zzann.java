package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzann;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzano;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzann<MessageType extends zzano<MessageType, BuilderType>, BuilderType extends zzann<MessageType, BuilderType>> extends zzanm<MessageType, BuilderType> implements zzapg {
    protected zzann(MessageType messagetype) {
        super(messagetype);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzape
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final MessageType zzu() {
        if (this.zzb) {
            return (MessageType) this.zza;
        }
        ((zzano) this.zza).zzb.zzi();
        return (MessageType) super.zzu();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm
    protected final void zzw() {
        super.zzw();
        zzano zzanoVar = (zzano) this.zza;
        zzanoVar.zzb = zzanoVar.zzb.clone();
    }
}
