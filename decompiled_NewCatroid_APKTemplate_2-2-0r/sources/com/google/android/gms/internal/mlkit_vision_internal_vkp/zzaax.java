package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaax implements ObjectEncoder<zzadl> {
    static final zzaax zza = new zzaax();
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
        zzlz zzlzVar = new zzlz();
        zzlzVar.zza(1);
        zzb = builder.withProperty(zzlzVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("errorCode");
        zzlz zzlzVar2 = new zzlz();
        zzlzVar2.zza(2);
        zzc = builder2.withProperty(zzlzVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("isColdCall");
        zzlz zzlzVar3 = new zzlz();
        zzlzVar3.zza(3);
        zzd = builder3.withProperty(zzlzVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("autoManageModelOnBackground");
        zzlz zzlzVar4 = new zzlz();
        zzlzVar4.zza(4);
        zze = builder4.withProperty(zzlzVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("autoManageModelOnLowMemory");
        zzlz zzlzVar5 = new zzlz();
        zzlzVar5.zza(5);
        zzf = builder5.withProperty(zzlzVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("isNnApiEnabled");
        zzlz zzlzVar6 = new zzlz();
        zzlzVar6.zza(6);
        zzg = builder6.withProperty(zzlzVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("eventsCount");
        zzlz zzlzVar7 = new zzlz();
        zzlzVar7.zza(7);
        zzh = builder7.withProperty(zzlzVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("otherErrors");
        zzlz zzlzVar8 = new zzlz();
        zzlzVar8.zza(8);
        zzi = builder8.withProperty(zzlzVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("remoteConfigValueForAcceleration");
        zzlz zzlzVar9 = new zzlz();
        zzlzVar9.zza(9);
        zzj = builder9.withProperty(zzlzVar9.zzb()).build();
        FieldDescriptor.Builder builder10 = FieldDescriptor.builder("isAccelerated");
        zzlz zzlzVar10 = new zzlz();
        zzlzVar10.zza(10);
        zzk = builder10.withProperty(zzlzVar10.zzb()).build();
    }

    private zzaax() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
