package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzdf implements ObjectEncoder {
    static final ObjectEncoder zza = new zzdf();

    private zzdf() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final void encode(Object obj, ObjectEncoderContext objectEncoderContext) {
        int i = zzdg.zza;
        String strValueOf = String.valueOf(obj.getClass().getCanonicalName());
        throw new EncodingException(strValueOf.length() != 0 ? "Couldn't find encoder for type ".concat(strValueOf) : new String("Couldn't find encoder for type "));
    }
}
