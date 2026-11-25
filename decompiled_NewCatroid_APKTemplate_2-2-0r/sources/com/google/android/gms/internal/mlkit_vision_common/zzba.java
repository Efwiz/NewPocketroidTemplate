package com.google.android.gms.internal.mlkit_vision_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzba implements ObjectEncoder<zzae> {
    static final zzba zza = new zzba();
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
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("handledErrors");
        zzt zztVar2 = new zzt();
        zztVar2.zza(2);
        zzc = builder2.withProperty(zztVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("partiallyHandledErrors");
        zzt zztVar3 = new zzt();
        zztVar3.zza(3);
        zzd = builder3.withProperty(zztVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("unhandledErrors");
        zzt zztVar4 = new zzt();
        zztVar4.zza(4);
        zze = builder4.withProperty(zztVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("modelNamespace");
        zzt zztVar5 = new zzt();
        zztVar5.zza(5);
        zzf = builder5.withProperty(zztVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("delegateFilter");
        zzt zztVar6 = new zzt();
        zztVar6.zza(6);
        zzg = builder6.withProperty(zztVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("httpResponseCode");
        zzt zztVar7 = new zzt();
        zztVar7.zza(7);
        zzh = builder7.withProperty(zztVar7.zzb()).build();
    }

    private zzba() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
