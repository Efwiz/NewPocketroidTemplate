package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhf implements ObjectEncoder<zzjy> {
    static final zzhf zza = new zzhf();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;
    private static final FieldDescriptor zzi;
    private static final FieldDescriptor zzj;
    private static final FieldDescriptor zzk;
    private static final FieldDescriptor zzl;
    private static final FieldDescriptor zzm;
    private static final FieldDescriptor zzn;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("appId");
        zzcy zzcyVar = new zzcy();
        zzcyVar.zza(1);
        zzb = builder.withProperty(zzcyVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("appVersion");
        zzcy zzcyVar2 = new zzcy();
        zzcyVar2.zza(2);
        zzc = builder2.withProperty(zzcyVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("firebaseProjectId");
        zzcy zzcyVar3 = new zzcy();
        zzcyVar3.zza(3);
        zzd = builder3.withProperty(zzcyVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("mlSdkVersion");
        zzcy zzcyVar4 = new zzcy();
        zzcyVar4.zza(4);
        zze = builder4.withProperty(zzcyVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("tfliteSchemaVersion");
        zzcy zzcyVar5 = new zzcy();
        zzcyVar5.zza(5);
        zzf = builder5.withProperty(zzcyVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("gcmSenderId");
        zzcy zzcyVar6 = new zzcy();
        zzcyVar6.zza(6);
        zzg = builder6.withProperty(zzcyVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("apiKey");
        zzcy zzcyVar7 = new zzcy();
        zzcyVar7.zza(7);
        zzh = builder7.withProperty(zzcyVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("languages");
        zzcy zzcyVar8 = new zzcy();
        zzcyVar8.zza(8);
        zzi = builder8.withProperty(zzcyVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("mlSdkInstanceId");
        zzcy zzcyVar9 = new zzcy();
        zzcyVar9.zza(9);
        zzj = builder9.withProperty(zzcyVar9.zzb()).build();
        FieldDescriptor.Builder builder10 = FieldDescriptor.builder("isClearcutClient");
        zzcy zzcyVar10 = new zzcy();
        zzcyVar10.zza(10);
        zzk = builder10.withProperty(zzcyVar10.zzb()).build();
        FieldDescriptor.Builder builder11 = FieldDescriptor.builder("isStandaloneMlkit");
        zzcy zzcyVar11 = new zzcy();
        zzcyVar11.zza(11);
        zzl = builder11.withProperty(zzcyVar11.zzb()).build();
        FieldDescriptor.Builder builder12 = FieldDescriptor.builder("isJsonLogging");
        zzcy zzcyVar12 = new zzcy();
        zzcyVar12.zza(12);
        zzm = builder12.withProperty(zzcyVar12.zzb()).build();
        FieldDescriptor.Builder builder13 = FieldDescriptor.builder("buildLevel");
        zzcy zzcyVar13 = new zzcy();
        zzcyVar13.zza(13);
        zzn = builder13.withProperty(zzcyVar13.zzb()).build();
    }

    private zzhf() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzjy zzjyVar = (zzjy) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zzjyVar.zza());
        objectEncoderContext2.add(zzc, zzjyVar.zzb());
        objectEncoderContext2.add(zzd, (Object) null);
        objectEncoderContext2.add(zze, zzjyVar.zzc());
        objectEncoderContext2.add(zzf, zzjyVar.zzd());
        objectEncoderContext2.add(zzg, (Object) null);
        objectEncoderContext2.add(zzh, (Object) null);
        objectEncoderContext2.add(zzi, zzjyVar.zze());
        objectEncoderContext2.add(zzj, zzjyVar.zzf());
        objectEncoderContext2.add(zzk, zzjyVar.zzg());
        objectEncoderContext2.add(zzl, zzjyVar.zzh());
        objectEncoderContext2.add(zzm, zzjyVar.zzi());
        objectEncoderContext2.add(zzn, zzjyVar.zzj());
    }
}
