package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzann;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzano;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzano<MessageType extends zzano<MessageType, BuilderType>, BuilderType extends zzann<MessageType, BuilderType>> extends zzans<MessageType, BuilderType> implements zzapg {
    protected zzanh<zzanp> zzb = zzanh.zzf();

    final zzanh<zzanp> zzh() {
        if (this.zzb.zzm()) {
            this.zzb = this.zzb.clone();
        }
        return this.zzb;
    }
}
