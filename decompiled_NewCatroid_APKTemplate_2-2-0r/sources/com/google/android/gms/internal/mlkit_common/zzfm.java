package com.google.android.gms.internal.mlkit_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfm implements ObjectEncoder<zziw> {
    static final zzfm zza = new zzfm();
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
        zzaw zzawVar = new zzaw();
        zzawVar.zza(1);
        zzb = builder.withProperty(zzawVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("appVersion");
        zzaw zzawVar2 = new zzaw();
        zzawVar2.zza(2);
        zzc = builder2.withProperty(zzawVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("firebaseProjectId");
        zzaw zzawVar3 = new zzaw();
        zzawVar3.zza(3);
        zzd = builder3.withProperty(zzawVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("mlSdkVersion");
        zzaw zzawVar4 = new zzaw();
        zzawVar4.zza(4);
        zze = builder4.withProperty(zzawVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("tfliteSchemaVersion");
        zzaw zzawVar5 = new zzaw();
        zzawVar5.zza(5);
        zzf = builder5.withProperty(zzawVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("gcmSenderId");
        zzaw zzawVar6 = new zzaw();
        zzawVar6.zza(6);
        zzg = builder6.withProperty(zzawVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("apiKey");
        zzaw zzawVar7 = new zzaw();
        zzawVar7.zza(7);
        zzh = builder7.withProperty(zzawVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("languages");
        zzaw zzawVar8 = new zzaw();
        zzawVar8.zza(8);
        zzi = builder8.withProperty(zzawVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("mlSdkInstanceId");
        zzaw zzawVar9 = new zzaw();
        zzawVar9.zza(9);
        zzj = builder9.withProperty(zzawVar9.zzb()).build();
        FieldDescriptor.Builder builder10 = FieldDescriptor.builder("isClearcutClient");
        zzaw zzawVar10 = new zzaw();
        zzawVar10.zza(10);
        zzk = builder10.withProperty(zzawVar10.zzb()).build();
        FieldDescriptor.Builder builder11 = FieldDescriptor.builder("isStandaloneMlkit");
        zzaw zzawVar11 = new zzaw();
        zzawVar11.zza(11);
        zzl = builder11.withProperty(zzawVar11.zzb()).build();
        FieldDescriptor.Builder builder12 = FieldDescriptor.builder("isJsonLogging");
        zzaw zzawVar12 = new zzaw();
        zzawVar12.zza(12);
        zzm = builder12.withProperty(zzawVar12.zzb()).build();
        FieldDescriptor.Builder builder13 = FieldDescriptor.builder("buildLevel");
        zzaw zzawVar13 = new zzaw();
        zzawVar13.zza(13);
        zzn = builder13.withProperty(zzawVar13.zzb()).build();
    }

    private zzfm() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zziw zziwVar = (zziw) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zziwVar.zzf());
        objectEncoderContext2.add(zzc, zziwVar.zzg());
        objectEncoderContext2.add(zzd, (Object) null);
        objectEncoderContext2.add(zze, zziwVar.zzi());
        objectEncoderContext2.add(zzf, zziwVar.zzj());
        objectEncoderContext2.add(zzg, (Object) null);
        objectEncoderContext2.add(zzh, (Object) null);
        objectEncoderContext2.add(zzi, zziwVar.zza());
        objectEncoderContext2.add(zzj, zziwVar.zzh());
        objectEncoderContext2.add(zzk, zziwVar.zzb());
        objectEncoderContext2.add(zzl, zziwVar.zzd());
        objectEncoderContext2.add(zzm, zziwVar.zzc());
        objectEncoderContext2.add(zzn, zziwVar.zze());
    }
}
