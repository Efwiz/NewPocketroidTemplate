package com.google.android.gms.internal.mlkit_vision_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdj implements ObjectEncoder<zzge> {
    static final zzdj zza = new zzdj();
    private static final FieldDescriptor zzb;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("errorCode");
        zzt zztVar = new zzt();
        zztVar.zza(1);
        zzb = builder.withProperty(zztVar.zzb()).build();
    }

    private zzdj() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
