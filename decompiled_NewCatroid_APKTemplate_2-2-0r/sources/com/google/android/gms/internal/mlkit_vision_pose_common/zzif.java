package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzif<FieldDescriptorType> extends zzig<FieldDescriptorType, Object> {
    zzif(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzig
    public final void zza() {
        if (!zzb()) {
            for (int i = 0; i < zzc(); i++) {
                Map.Entry<FieldDescriptorType, Object> entryZzb = zzb(i);
                if (((zzfx) entryZzb.getKey()).zzd()) {
                    entryZzb.setValue(Collections.unmodifiableList((List) entryZzb.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzd()) {
                if (((zzfx) entry.getKey()).zzd()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zza();
    }
}
