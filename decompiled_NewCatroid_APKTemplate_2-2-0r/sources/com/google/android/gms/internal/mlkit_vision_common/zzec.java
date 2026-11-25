package com.google.android.gms.internal.mlkit_vision_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzec implements ObjectEncoder<zzgy> {
    static final zzec zza = new zzec();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("inferenceCommonLogEvent");
        zzt zztVar = new zzt();
        zztVar.zza(1);
        zzb = builder.withProperty(zztVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("smartReplies");
        zzt zztVar2 = new zzt();
        zztVar2.zza(2);
        zzc = builder2.withProperty(zztVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("resultStatus");
        zzt zztVar3 = new zzt();
        zztVar3.zza(3);
        zzd = builder3.withProperty(zztVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("suggestionsCount");
        zzt zztVar4 = new zzt();
        zztVar4.zza(4);
        zze = builder4.withProperty(zztVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("blacklistErrorCode");
        zzt zztVar5 = new zzt();
        zztVar5.zza(5);
        zzf = builder5.withProperty(zztVar5.zzb()).build();
    }

    private zzec() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
