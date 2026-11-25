package com.google.android.gms.internal.mlkit_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzeh implements ObjectEncoder<zzhn> {
    static final zzeh zza = new zzeh();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("isChargingRequired");
        zzaw zzawVar = new zzaw();
        zzawVar.zza(1);
        zzb = builder.withProperty(zzawVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("isWifiRequired");
        zzaw zzawVar2 = new zzaw();
        zzawVar2.zza(2);
        zzc = builder2.withProperty(zzawVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("isDeviceIdleRequired");
        zzaw zzawVar3 = new zzaw();
        zzawVar3.zza(3);
        zzd = builder3.withProperty(zzawVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("canDownloadInBackground");
        zzaw zzawVar4 = new zzaw();
        zzawVar4.zza(4);
        zze = builder4.withProperty(zzawVar4.zzb()).build();
    }

    private zzeh() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
