package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzdd implements ObjectEncoder {
    static final ObjectEncoder zza = new zzdd();

    private zzdd() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzde.zze((Map.Entry) obj, objectEncoderContext);
    }
}
