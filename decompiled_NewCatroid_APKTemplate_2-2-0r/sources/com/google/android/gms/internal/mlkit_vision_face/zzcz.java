package com.google.android.gms.internal.mlkit_vision_face;

import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzcz implements ObjectEncoder {
    static final ObjectEncoder zza = new zzcz();

    private zzcz() {
    }

    @Override // com.google.firebase.encoders.Encoder
    public final void encode(Object obj, ObjectEncoderContext objectEncoderContext) {
        int i = zzda.zza;
        String strValueOf = String.valueOf(obj.getClass().getCanonicalName());
        throw new EncodingException(strValueOf.length() != 0 ? "Couldn't find encoder for type ".concat(strValueOf) : new String("Couldn't find encoder for type "));
    }
}
