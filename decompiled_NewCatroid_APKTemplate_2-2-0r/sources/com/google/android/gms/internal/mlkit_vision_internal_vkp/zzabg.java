package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzabg implements ObjectEncoder<zzady> {
    static final zzabg zza = new zzabg();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("modelInfo");
        zzlz zzlzVar = new zzlz();
        zzlzVar.zza(1);
        zzb = builder.withProperty(zzlzVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("initialDownloadConditions");
        zzlz zzlzVar2 = new zzlz();
        zzlzVar2.zza(2);
        zzc = builder2.withProperty(zzlzVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("updateDownloadConditions");
        zzlz zzlzVar3 = new zzlz();
        zzlzVar3.zza(3);
        zzd = builder3.withProperty(zzlzVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("isModelUpdateEnabled");
        zzlz zzlzVar4 = new zzlz();
        zzlzVar4.zza(4);
        zze = builder4.withProperty(zzlzVar4.zzb()).build();
    }

    private zzabg() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
