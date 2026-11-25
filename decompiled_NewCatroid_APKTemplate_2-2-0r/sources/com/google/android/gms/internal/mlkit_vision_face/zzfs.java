package com.google.android.gms.internal.mlkit_vision_face;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfs implements ObjectEncoder<zzik> {
    static final zzfs zza = new zzfs();
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
        zzcs zzcsVar = new zzcs();
        zzcsVar.zza(1);
        zzb = builder.withProperty(zzcsVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("errorCode");
        zzcs zzcsVar2 = new zzcs();
        zzcsVar2.zza(2);
        zzc = builder2.withProperty(zzcsVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("isColdCall");
        zzcs zzcsVar3 = new zzcs();
        zzcsVar3.zza(3);
        zzd = builder3.withProperty(zzcsVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("autoManageModelOnBackground");
        zzcs zzcsVar4 = new zzcs();
        zzcsVar4.zza(4);
        zze = builder4.withProperty(zzcsVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("autoManageModelOnLowMemory");
        zzcs zzcsVar5 = new zzcs();
        zzcsVar5.zza(5);
        zzf = builder5.withProperty(zzcsVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("isNnApiEnabled");
        zzcs zzcsVar6 = new zzcs();
        zzcsVar6.zza(6);
        zzg = builder6.withProperty(zzcsVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("eventsCount");
        zzcs zzcsVar7 = new zzcs();
        zzcsVar7.zza(7);
        zzh = builder7.withProperty(zzcsVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("otherErrors");
        zzcs zzcsVar8 = new zzcs();
        zzcsVar8.zza(8);
        zzi = builder8.withProperty(zzcsVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("remoteConfigValueForAcceleration");
        zzcs zzcsVar9 = new zzcs();
        zzcsVar9.zza(9);
        zzj = builder9.withProperty(zzcsVar9.zzb()).build();
        FieldDescriptor.Builder builder10 = FieldDescriptor.builder("isAccelerated");
        zzcs zzcsVar10 = new zzcs();
        zzcsVar10.zza(10);
        zzk = builder10.withProperty(zzcsVar10.zzb()).build();
    }

    private zzfs() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzik zzikVar = (zzik) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zzikVar.zza());
        objectEncoderContext2.add(zzc, zzikVar.zzb());
        objectEncoderContext2.add(zzd, zzikVar.zzc());
        objectEncoderContext2.add(zze, zzikVar.zzd());
        objectEncoderContext2.add(zzf, zzikVar.zze());
        objectEncoderContext2.add(zzg, (Object) null);
        objectEncoderContext2.add(zzh, (Object) null);
        objectEncoderContext2.add(zzi, (Object) null);
        objectEncoderContext2.add(zzj, (Object) null);
        objectEncoderContext2.add(zzk, (Object) null);
    }
}
