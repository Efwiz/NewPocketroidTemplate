package com.google.android.gms.internal.mlkit_language_id;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgt<FieldDescriptorType> extends zzgq<FieldDescriptorType, Object> {
    zzgt(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgq
    public final void zza() {
        if (!zzb()) {
            for (int i = 0; i < zzc(); i++) {
                Map.Entry<FieldDescriptorType, Object> entryZzb = zzb(i);
                if (((zzel) entryZzb.getKey()).zzd()) {
                    entryZzb.setValue(Collections.unmodifiableList((List) entryZzb.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzd()) {
                if (((zzel) entry.getKey()).zzd()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zza();
    }
}
