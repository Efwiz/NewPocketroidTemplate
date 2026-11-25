package com.google.android.gms.internal.mlkit_vision_common;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdc implements ObjectEncoder<zzfx> {
    static final zzdc zza = new zzdc();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;
    private static final FieldDescriptor zzi;
    private static final FieldDescriptor zzj;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("name");
        zzt zztVar = new zzt();
        zztVar.zza(1);
        zzb = builder.withProperty(zztVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("version");
        zzt zztVar2 = new zzt();
        zztVar2.zza(2);
        zzc = builder2.withProperty(zztVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder(FirebaseAnalytics.Param.SOURCE);
        zzt zztVar3 = new zzt();
        zztVar3.zza(3);
        zzd = builder3.withProperty(zztVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("uri");
        zzt zztVar4 = new zzt();
        zztVar4.zza(4);
        zze = builder4.withProperty(zztVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("hash");
        zzt zztVar5 = new zzt();
        zztVar5.zza(5);
        zzf = builder5.withProperty(zztVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("modelType");
        zzt zztVar6 = new zzt();
        zztVar6.zza(6);
        zzg = builder6.withProperty(zztVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("size");
        zzt zztVar7 = new zzt();
        zztVar7.zza(7);
        zzh = builder7.withProperty(zztVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("hasLabelMap");
        zzt zztVar8 = new zzt();
        zztVar8.zza(8);
        zzi = builder8.withProperty(zztVar8.zzb()).build();
        FieldDescriptor.Builder builder9 = FieldDescriptor.builder("isManifestModel");
        zzt zztVar9 = new zzt();
        zztVar9.zza(9);
        zzj = builder9.withProperty(zztVar9.zzb()).build();
    }

    private zzdc() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
