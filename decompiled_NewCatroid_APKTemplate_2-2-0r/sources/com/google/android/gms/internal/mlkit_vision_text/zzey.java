package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzey implements ObjectEncoder<zzec> {
    static final zzey zza = new zzey();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("errorCode");
        zzcy zzcyVar = new zzcy();
        zzcyVar.zza(1);
        zzb = builder.withProperty(zzcyVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("hasResult");
        zzcy zzcyVar2 = new zzcy();
        zzcyVar2.zza(2);
        zzc = builder2.withProperty(zzcyVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("isColdCall");
        zzcy zzcyVar3 = new zzcy();
        zzcyVar3.zza(3);
        zzd = builder3.withProperty(zzcyVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("imageInfo");
        zzcy zzcyVar4 = new zzcy();
        zzcyVar4.zza(4);
        zze = builder4.withProperty(zzcyVar4.zzb()).build();
    }

    private zzey() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzec zzecVar = (zzec) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zzecVar.zza());
        objectEncoderContext2.add(zzc, (Object) null);
        objectEncoderContext2.add(zzd, zzecVar.zzb());
        objectEncoderContext2.add(zze, zzecVar.zzc());
    }
}
