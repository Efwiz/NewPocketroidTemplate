package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzacn implements ObjectEncoder<zzafj> {
    static final zzacn zza = new zzacn();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("sourceLanguage");
        zzlz zzlzVar = new zzlz();
        zzlzVar.zza(1);
        zzb = builder.withProperty(zzlzVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("targetLanguage");
        zzlz zzlzVar2 = new zzlz();
        zzlzVar2.zza(2);
        zzc = builder2.withProperty(zzlzVar2.zzb()).build();
    }

    private zzacn() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
