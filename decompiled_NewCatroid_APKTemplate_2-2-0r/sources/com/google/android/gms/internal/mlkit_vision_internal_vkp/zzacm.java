package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzacm implements ObjectEncoder<zzafi> {
    static final zzacm zza = new zzacm();
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
        zzlz zzlzVar = new zzlz();
        zzlzVar.zza(1);
        zzb = builder.withProperty(zzlzVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("appVersion");
        zzlz zzlzVar2 = new zzlz();
        zzlzVar2.zza(2);
        zzc = builder2.withProperty(zzlzVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("firebaseProjectId");
        zzlz zzlzVar3 = new zzlz();
        zzlzVar3.zza(3);
        zzd = builder3.withProperty(zzlzVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("mlSdkVersion");
        zzlz zzlzVar4 = new zzlz();
        zzlzVar4.zza(4);
        zze = builder4.withProperty(zzlzVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("tfliteSchemaVersion");
        zzlz zzlzVar5 = new zzlz();
        zzlzVar5.zza(5);
        zzf = builder5.withProperty(zzlzVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("gcmSenderId");
        zzlz zzlzVar6 = new zzlz();
        zzlzVar6.zza(6);
        zzg = builder6.withProperty(zzlzVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("apiKey");
        zzlz zzlzVar7 = new zzlz();
        zzlzVar7.zza(7);
        zzh = builder7.withProperty(zzlzVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("languages");
        zzlz zzlzVar8 = new zzlz();
        zzlzVar8.zza(8);
        zzi = builder8.withProperty(zzlzVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("mlSdkInstanceId");
        zzlz zzlzVar9 = new zzlz();
        zzlzVar9.zza(9);
        zzj = builder9.withProperty(zzlzVar9.zzb()).build();
        FieldDescriptor.Builder builder10 = FieldDescriptor.builder("isClearcutClient");
        zzlz zzlzVar10 = new zzlz();
        zzlzVar10.zza(10);
        zzk = builder10.withProperty(zzlzVar10.zzb()).build();
        FieldDescriptor.Builder builder11 = FieldDescriptor.builder("isStandaloneMlkit");
        zzlz zzlzVar11 = new zzlz();
        zzlzVar11.zza(11);
        zzl = builder11.withProperty(zzlzVar11.zzb()).build();
        FieldDescriptor.Builder builder12 = FieldDescriptor.builder("isJsonLogging");
        zzlz zzlzVar12 = new zzlz();
        zzlzVar12.zza(12);
        zzm = builder12.withProperty(zzlzVar12.zzb()).build();
        FieldDescriptor.Builder builder13 = FieldDescriptor.builder("buildLevel");
        zzlz zzlzVar13 = new zzlz();
        zzlzVar13.zza(13);
        zzn = builder13.withProperty(zzlzVar13.zzb()).build();
    }

    private zzacm() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzafi zzafiVar = (zzafi) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zzafiVar.zzf());
        objectEncoderContext2.add(zzc, zzafiVar.zzg());
        objectEncoderContext2.add(zzd, (Object) null);
        objectEncoderContext2.add(zze, zzafiVar.zzi());
        objectEncoderContext2.add(zzf, zzafiVar.zzj());
        objectEncoderContext2.add(zzg, (Object) null);
        objectEncoderContext2.add(zzh, (Object) null);
        objectEncoderContext2.add(zzi, zzafiVar.zza());
        objectEncoderContext2.add(zzj, zzafiVar.zzh());
        objectEncoderContext2.add(zzk, zzafiVar.zzb());
        objectEncoderContext2.add(zzl, zzafiVar.zzd());
        objectEncoderContext2.add(zzm, zzafiVar.zzc());
        objectEncoderContext2.add(zzn, zzafiVar.zze());
    }
}
