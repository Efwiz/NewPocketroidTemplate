package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfx implements ObjectEncoder<zzii> {
    static final zzfx zza = new zzfx();
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

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("durationMs");
        zzcy zzcyVar = new zzcy();
        zzcyVar.zza(1);
        zzb = builder.withProperty(zzcyVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("errorCode");
        zzcy zzcyVar2 = new zzcy();
        zzcyVar2.zza(2);
        zzc = builder2.withProperty(zzcyVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("isColdCall");
        zzcy zzcyVar3 = new zzcy();
        zzcyVar3.zza(3);
        zzd = builder3.withProperty(zzcyVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("autoManageModelOnBackground");
        zzcy zzcyVar4 = new zzcy();
        zzcyVar4.zza(4);
        zze = builder4.withProperty(zzcyVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("autoManageModelOnLowMemory");
        zzcy zzcyVar5 = new zzcy();
        zzcyVar5.zza(5);
        zzf = builder5.withProperty(zzcyVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("isNnApiEnabled");
        zzcy zzcyVar6 = new zzcy();
        zzcyVar6.zza(6);
        zzg = builder6.withProperty(zzcyVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("eventsCount");
        zzcy zzcyVar7 = new zzcy();
        zzcyVar7.zza(7);
        zzh = builder7.withProperty(zzcyVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("otherErrors");
        zzcy zzcyVar8 = new zzcy();
        zzcyVar8.zza(8);
        zzi = builder8.withProperty(zzcyVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("remoteConfigValueForAcceleration");
        zzcy zzcyVar9 = new zzcy();
        zzcyVar9.zza(9);
        zzj = builder9.withProperty(zzcyVar9.zzb()).build();
        FieldDescriptor.Builder builder10 = FieldDescriptor.builder("isAccelerated");
        zzcy zzcyVar10 = new zzcy();
        zzcyVar10.zza(10);
        zzk = builder10.withProperty(zzcyVar10.zzb()).build();
    }

    private zzfx() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzii zziiVar = (zzii) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zziiVar.zza());
        objectEncoderContext2.add(zzc, zziiVar.zzb());
        objectEncoderContext2.add(zzd, zziiVar.zzc());
        objectEncoderContext2.add(zze, zziiVar.zzd());
        objectEncoderContext2.add(zzf, zziiVar.zze());
        objectEncoderContext2.add(zzg, (Object) null);
        objectEncoderContext2.add(zzh, (Object) null);
        objectEncoderContext2.add(zzi, (Object) null);
        objectEncoderContext2.add(zzj, (Object) null);
        objectEncoderContext2.add(zzk, (Object) null);
    }
}
