package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfy implements ObjectEncoder<zzij> {
    static final zzfy zza = new zzfy();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("durationMs");
        zzcy zzcyVar = new zzcy();
        zzcyVar.zza(1);
        zzb = builder.withProperty(zzcyVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("imageSource");
        zzcy zzcyVar2 = new zzcy();
        zzcyVar2.zza(2);
        zzc = builder2.withProperty(zzcyVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("imageFormat");
        zzcy zzcyVar3 = new zzcy();
        zzcyVar3.zza(3);
        zzd = builder3.withProperty(zzcyVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("imageByteSize");
        zzcy zzcyVar4 = new zzcy();
        zzcyVar4.zza(4);
        zze = builder4.withProperty(zzcyVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("imageWidth");
        zzcy zzcyVar5 = new zzcy();
        zzcyVar5.zza(5);
        zzf = builder5.withProperty(zzcyVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("imageHeight");
        zzcy zzcyVar6 = new zzcy();
        zzcyVar6.zza(6);
        zzg = builder6.withProperty(zzcyVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("rotationDegrees");
        zzcy zzcyVar7 = new zzcy();
        zzcyVar7.zza(7);
        zzh = builder7.withProperty(zzcyVar7.zzb()).build();
    }

    private zzfy() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
