package com.google.android.gms.internal.mlkit_common;

import androidx.core.app.NotificationCompat;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfj implements ObjectEncoder<zzir> {
    static final zzfj zza = new zzfj();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;
    private static final FieldDescriptor zzi;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("inferenceCommonLogEvent");
        zzaw zzawVar = new zzaw();
        zzawVar.zza(1);
        zzb = builder.withProperty(zzawVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("options");
        zzaw zzawVar2 = new zzaw();
        zzawVar2.zza(2);
        zzc = builder2.withProperty(zzawVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("inputLength");
        zzaw zzawVar3 = new zzaw();
        zzawVar3.zza(3);
        zzd = builder3.withProperty(zzawVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("outputLength");
        zzaw zzawVar4 = new zzaw();
        zzawVar4.zza(4);
        zze = builder4.withProperty(zzawVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("loadDictionaryErrorCode");
        zzaw zzawVar5 = new zzaw();
        zzawVar5.zza(5);
        zzf = builder5.withProperty(zzawVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("translateResultStatusCode");
        zzaw zzawVar6 = new zzaw();
        zzawVar6.zza(6);
        zzg = builder6.withProperty(zzawVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder(NotificationCompat.CATEGORY_STATUS);
        zzaw zzawVar7 = new zzaw();
        zzawVar7.zza(7);
        zzh = builder7.withProperty(zzawVar7.zzb()).build();
        FieldDescriptor.Builder builder8 = FieldDescriptor.builder("downloadHttpResponseCode");
        zzaw zzawVar8 = new zzaw();
        zzawVar8.zza(8);
        zzi = builder8.withProperty(zzawVar8.zzb()).build();
    }

    private zzfj() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
