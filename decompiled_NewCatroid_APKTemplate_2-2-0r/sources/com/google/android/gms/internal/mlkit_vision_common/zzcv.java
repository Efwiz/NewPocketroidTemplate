package com.google.android.gms.internal.mlkit_vision_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcv implements ObjectEncoder<zzfn> {
    static final zzcv zza = new zzcv();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("durationMs");
        zzt zztVar = new zzt();
        zztVar.zza(1);
        zzb = builder.withProperty(zztVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("imageSource");
        zzt zztVar2 = new zzt();
        zztVar2.zza(2);
        zzc = builder2.withProperty(zztVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("imageFormat");
        zzt zztVar3 = new zzt();
        zztVar3.zza(3);
        zzd = builder3.withProperty(zztVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("imageByteSize");
        zzt zztVar4 = new zzt();
        zztVar4.zza(4);
        zze = builder4.withProperty(zztVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("imageWidth");
        zzt zztVar5 = new zzt();
        zztVar5.zza(5);
        zzf = builder5.withProperty(zztVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("imageHeight");
        zzt zztVar6 = new zzt();
        zztVar6.zza(6);
        zzg = builder6.withProperty(zztVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("rotationDegrees");
        zzt zztVar7 = new zzt();
        zztVar7.zza(7);
        zzh = builder7.withProperty(zztVar7.zzb()).build();
    }

    private zzcv() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzfn zzfnVar = (zzfn) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zzfnVar.zzg());
        objectEncoderContext2.add(zzc, zzfnVar.zzb());
        objectEncoderContext2.add(zzd, zzfnVar.zza());
        objectEncoderContext2.add(zze, zzfnVar.zzc());
        objectEncoderContext2.add(zzf, zzfnVar.zze());
        objectEncoderContext2.add(zzg, zzfnVar.zzd());
        objectEncoderContext2.add(zzh, zzfnVar.zzf());
    }
}
