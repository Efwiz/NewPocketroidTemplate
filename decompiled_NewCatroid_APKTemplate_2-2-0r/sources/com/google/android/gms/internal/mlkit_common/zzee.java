package com.google.android.gms.internal.mlkit_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzee implements ObjectEncoder<zzhg> {
    static final zzee zza = new zzee();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;
    private static final FieldDescriptor zze;
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("options");
        zzaw zzawVar = new zzaw();
        zzawVar.zza(1);
        zzb = builder.withProperty(zzawVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("roughDownloadDurationMs");
        zzaw zzawVar2 = new zzaw();
        zzawVar2.zza(2);
        zzc = builder2.withProperty(zzawVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder("errorCode");
        zzaw zzawVar3 = new zzaw();
        zzawVar3.zza(3);
        zzd = builder3.withProperty(zzawVar3.zzb()).build();
        FieldDescriptor.Builder builder4 = FieldDescriptor.builder("exactDownloadDurationMs");
        zzaw zzawVar4 = new zzaw();
        zzawVar4.zza(4);
        zze = builder4.withProperty(zzawVar4.zzb()).build();
        FieldDescriptor.Builder builder5 = FieldDescriptor.builder("downloadStatus");
        zzaw zzawVar5 = new zzaw();
        zzawVar5.zza(5);
        zzf = builder5.withProperty(zzawVar5.zzb()).build();
        FieldDescriptor.Builder builder6 = FieldDescriptor.builder("downloadFailureStatus");
        zzaw zzawVar6 = new zzaw();
        zzawVar6.zza(6);
        zzg = builder6.withProperty(zzawVar6.zzb()).build();
        FieldDescriptor.Builder builder7 = FieldDescriptor.builder("mddDownloadErrorCodes");
        zzaw zzawVar7 = new zzaw();
        zzawVar7.zza(7);
        zzh = builder7.withProperty(zzawVar7.zzb()).build();
    }

    private zzee() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzhg zzhgVar = (zzhg) obj;
        ObjectEncoderContext objectEncoderContext2 = objectEncoderContext;
        objectEncoderContext2.add(zzb, zzhgVar.zzc());
        objectEncoderContext2.add(zzc, zzhgVar.zzf());
        objectEncoderContext2.add(zzd, zzhgVar.zza());
        objectEncoderContext2.add(zze, zzhgVar.zze());
        objectEncoderContext2.add(zzf, zzhgVar.zzb());
        objectEncoderContext2.add(zzg, zzhgVar.zzd());
        objectEncoderContext2.add(zzh, (Object) null);
    }
}
