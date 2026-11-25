package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzacf implements ObjectEncoder<zzaex> {
    static final zzacf zza = new zzacf();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("inferenceCommonLogEvent");
        zzlz zzlzVar = new zzlz();
        zzlzVar.zza(1);
        zzb = builder.withProperty(zzlzVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("smartReplies");
        zzlz zzlzVar2 = new zzlz();
        zzlzVar2.zza(2);
        zzc = builder2.withProperty(zzlzVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("resultStatus");
        zzlz zzlzVar3 = new zzlz();
        zzlzVar3.zza(3);
        zzd = builder3.withProperty(zzlzVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("suggestionsCount");
        zzlz zzlzVar4 = new zzlz();
        zzlzVar4.zza(4);
        zze = builder4.withProperty(zzlzVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("blacklistErrorCode");
        zzlz zzlzVar5 = new zzlz();
        zzlzVar5.zza(5);
        zzf = builder5.withProperty(zzlzVar5.zzb()).build();
    }

    private zzacf() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
