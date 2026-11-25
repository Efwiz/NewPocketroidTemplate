package com.google.android.gms.internal.mlkit_vision_face;

import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzcx implements ObjectEncoder {
    static final ObjectEncoder zza = new zzcx();

    private zzcx() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final void encode(Object obj, ObjectEncoderContext objectEncoderContext) throws IOException {
        zzcy.zzg((Map.Entry) obj, objectEncoderContext);
    }
}
