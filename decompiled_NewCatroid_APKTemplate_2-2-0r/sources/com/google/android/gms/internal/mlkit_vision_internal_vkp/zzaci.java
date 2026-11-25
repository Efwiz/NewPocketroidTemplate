package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.reandroid.arsc.value.ResConfigBase;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaci implements ObjectEncoder<zzaez> {
    static final zzaci zza = new zzaci();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;
    private static final FieldDescriptor zzd;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("languageOption");
        zzlz zzlzVar = new zzlz();
        zzlzVar.zza(3);
        zzb = builder.withProperty(zzlzVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("isUsingLegacyApi");
        zzlz zzlzVar2 = new zzlz();
        zzlzVar2.zza(4);
        zzc = builder2.withProperty(zzlzVar2.zzb()).build();
        FieldDescriptor.Builder builder3 = FieldDescriptor.builder(ResConfigBase.NAME_sdkVersion);
        zzlz zzlzVar3 = new zzlz();
        zzlzVar3.zza(5);
        zzd = builder3.withProperty(zzlzVar3.zzb()).build();
    }

    private zzaci() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
