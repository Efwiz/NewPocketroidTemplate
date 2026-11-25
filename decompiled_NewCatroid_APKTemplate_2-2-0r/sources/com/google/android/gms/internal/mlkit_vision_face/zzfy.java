package com.google.android.gms.internal.mlkit_vision_face;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfy implements ObjectEncoder<zziu> {
    static final zzfy zza = new zzfy();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("options");
        zzcs zzcsVar = new zzcs();
        zzcsVar.zza(1);
        zzb = builder.withProperty(zzcsVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("roughDownloadDurationMs");
        zzcs zzcsVar2 = new zzcs();
        zzcsVar2.zza(2);
        zzc = builder2.withProperty(zzcsVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("errorCode");
        zzcs zzcsVar3 = new zzcs();
        zzcsVar3.zza(3);
        zzd = builder3.withProperty(zzcsVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("exactDownloadDurationMs");
        zzcs zzcsVar4 = new zzcs();
        zzcsVar4.zza(4);
        zze = builder4.withProperty(zzcsVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("downloadStatus");
        zzcs zzcsVar5 = new zzcs();
        zzcsVar5.zza(5);
        zzf = builder5.withProperty(zzcsVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("downloadFailureStatus");
        zzcs zzcsVar6 = new zzcs();
        zzcsVar6.zza(6);
        zzg = builder6.withProperty(zzcsVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("mddDownloadErrorCodes");
        zzcs zzcsVar7 = new zzcs();
        zzcsVar7.zza(7);
        zzh = builder7.withProperty(zzcsVar7.zzb()).build();
    }

    private zzfy() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
