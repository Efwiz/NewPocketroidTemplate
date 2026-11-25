package com.google.android.gms.internal.mlkit_common;

import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdn implements ObjectEncoder<zzgc> {
    static final zzdn zza = new zzdn();
    private static final FieldDescriptor zzb;
    private static final FieldDescriptor zzc;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder(ModuleXmlParser.TYPE);
        zzaw zzawVar = new zzaw();
        zzawVar.zza(1);
        zzb = builder.withProperty(zzawVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("dims");
        zzaw zzawVar2 = new zzaw();
        zzawVar2.zza(2);
        zzc = builder2.withProperty(zzawVar2.zzb()).build();
    }

    private zzdn() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final /* bridge */ /* synthetic */ void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        throw null;
    }
}
