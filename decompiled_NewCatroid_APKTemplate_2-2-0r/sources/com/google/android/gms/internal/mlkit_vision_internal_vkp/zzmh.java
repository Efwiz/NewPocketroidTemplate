package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.ValueEncoder;
import com.google.firebase.encoders.config.EncoderConfig;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzmh implements EncoderConfig<zzmh> {
    public static final /* synthetic */ int zza = 0;
    private static final ObjectEncoder<Object> zzb = new ObjectEncoder() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzmg
        @Override // com.google.firebase.encoders.Encoder
        public final void encode(Object obj, ObjectEncoderContext objectEncoderContext) {
            int i = zzmh.zza;
            String strValueOf = String.valueOf(obj.getClass().getCanonicalName());
            throw new EncodingException(strValueOf.length() != 0 ? "Couldn't find encoder for type ".concat(strValueOf) : new String("Couldn't find encoder for type "));
        }
    };
    private final Map<Class<?>, ObjectEncoder<?>> zzc = new HashMap();
    private final Map<Class<?>, ValueEncoder<?>> zzd = new HashMap();
    private final ObjectEncoder<Object> zze = zzb;

    @Override // com.google.firebase.encoders.config.EncoderConfig
    public final /* bridge */ /* synthetic */ EncoderConfig registerEncoder(Class cls, ObjectEncoder objectEncoder) {
        this.zzc.put(cls, objectEncoder);
        this.zzd.remove(cls);
        return this;
    }

    public final zzmi zza() {
        return new zzmi(new HashMap(this.zzc), new HashMap(this.zzd), this.zze);
    }

    @Override // com.google.firebase.encoders.config.EncoderConfig
    public final /* bridge */ /* synthetic */ EncoderConfig registerEncoder(Class cls, ValueEncoder valueEncoder) {
        this.zzd.put(cls, valueEncoder);
        this.zzc.remove(cls);
        return this;
    }
}
