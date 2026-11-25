package com.google.android.gms.internal.mlkit_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzda implements ObjectEncoder<zzfp> {
    static final zzda zza = new zzda();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("cameraSource");
        zzaw zzawVar = new zzaw();
        zzawVar.zza(1);
        zzb = builder.withProperty(zzawVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("eventType");
        zzaw zzawVar2 = new zzaw();
        zzawVar2.zza(2);
        zzc = builder2.withProperty(zzawVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("requestedPreviewHeight");
        zzaw zzawVar3 = new zzaw();
        zzawVar3.zza(3);
        zzd = builder3.withProperty(zzawVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("requestedPreviewWidth");
        zzaw zzawVar4 = new zzaw();
        zzawVar4.zza(4);
        zze = builder4.withProperty(zzawVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("actualPreviewHeight");
        zzaw zzawVar5 = new zzaw();
        zzawVar5.zza(5);
        zzf = builder5.withProperty(zzawVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("actualPreviewWidth");
        zzaw zzawVar6 = new zzaw();
        zzawVar6.zza(6);
        zzg = builder6.withProperty(zzawVar6.zzb()).build();
    }

    private zzda() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
