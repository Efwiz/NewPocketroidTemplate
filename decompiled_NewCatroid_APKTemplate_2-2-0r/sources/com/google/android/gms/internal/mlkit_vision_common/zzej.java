package com.google.android.gms.internal.mlkit_vision_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzej implements ObjectEncoder<zzhg> {
    static final zzej zza = new zzej();
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
        zzt zztVar = new zzt();
        zztVar.zza(1);
        zzb = builder.withProperty(zztVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("appVersion");
        zzt zztVar2 = new zzt();
        zztVar2.zza(2);
        zzc = builder2.withProperty(zztVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("firebaseProjectId");
        zzt zztVar3 = new zzt();
        zztVar3.zza(3);
        zzd = builder3.withProperty(zztVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("mlSdkVersion");
        zzt zztVar4 = new zzt();
        zztVar4.zza(4);
        zze = builder4.withProperty(zztVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("tfliteSchemaVersion");
        zzt zztVar5 = new zzt();
        zztVar5.zza(5);
        zzf = builder5.withProperty(zztVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("gcmSenderId");
        zzt zztVar6 = new zzt();
        zztVar6.zza(6);
        zzg = builder6.withProperty(zztVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("apiKey");
        zzt zztVar7 = new zzt();
        zztVar7.zza(7);
        zzh = builder7.withProperty(zztVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("languages");
        zzt zztVar8 = new zzt();
        zztVar8.zza(8);
        zzi = builder8.withProperty(zztVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("mlSdkInstanceId");
        zzt zztVar9 = new zzt();
        zztVar9.zza(9);
        zzj = builder9.withProperty(zztVar9.zzb()).build();
        FieldDescriptor.Builder builder10 = FieldDescriptor.builder("isClearcutClient");
        zzt zztVar10 = new zzt();
        zztVar10.zza(10);
        zzk = builder10.withProperty(zztVar10.zzb()).build();
        FieldDescriptor.Builder builder11 = FieldDescriptor.builder("isStandaloneMlkit");
        zzt zztVar11 = new zzt();
        zztVar11.zza(11);
        zzl = builder11.withProperty(zztVar11.zzb()).build();
        FieldDescriptor.Builder builder12 = FieldDescriptor.builder("isJsonLogging");
        zzt zztVar12 = new zzt();
        zztVar12.zza(12);
        zzm = builder12.withProperty(zztVar12.zzb()).build();
        FieldDescriptor.Builder builder13 = FieldDescriptor.builder("buildLevel");
        zzt zztVar13 = new zzt();
        zztVar13.zza(13);
        zzn = builder13.withProperty(zztVar13.zzb()).build();
    }

    private zzej() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzhg zzhgVar = (zzhg) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zzhgVar.zzf());
        objectEncoderContext2.add(zzc, zzhgVar.zzg());
        objectEncoderContext2.add(zzd, (Object) null);
        objectEncoderContext2.add(zze, zzhgVar.zzi());
        objectEncoderContext2.add(zzf, zzhgVar.zzj());
        objectEncoderContext2.add(zzg, (Object) null);
        objectEncoderContext2.add(zzh, (Object) null);
        objectEncoderContext2.add(zzi, zzhgVar.zza());
        objectEncoderContext2.add(zzj, zzhgVar.zzh());
        objectEncoderContext2.add(zzk, zzhgVar.zzb());
        objectEncoderContext2.add(zzl, zzhgVar.zzd());
        objectEncoderContext2.add(zzm, zzhgVar.zzc());
        objectEncoderContext2.add(zzn, zzhgVar.zze());
    }
}
