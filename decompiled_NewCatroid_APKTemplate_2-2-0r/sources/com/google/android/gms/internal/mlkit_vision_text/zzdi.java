package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.ValueEncoderContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdi implements ValueEncoderContext {
    private boolean zza = false;
    private final FieldDescriptor zzb;
    private final ObjectEncoderContext zzc;

    zzdi(FieldDescriptor fieldDescriptor, ObjectEncoderContext objectEncoderContext) {
        this.zzb = fieldDescriptor;
        this.zzc = objectEncoderContext;
    }

    private final void zza() {
        if (this.zza) {
            throw new EncodingException("Cannot encode a second value in the ValueEncoderContext");
        }
        this.zza = true;
    }

    @Override // com.google.firebase.encoders.ValueEncoderContext
    public final ValueEncoderContext add(double d) throws IOException {
        zza();
        this.zzc.add(this.zzb, d);
        return this;
    }

    @Override // com.google.firebase.encoders.ValueEncoderContext
    public final ValueEncoderContext add(float f) throws IOException {
        zza();
        this.zzc.add(this.zzb, f);
        return this;
    }

    @Override // com.google.firebase.encoders.ValueEncoderContext
    public final ValueEncoderContext add(int i) throws IOException {
        zza();
        ((zzde) this.zzc).zza(this.zzb, i);
        return this;
    }

    @Override // com.google.firebase.encoders.ValueEncoderContext
    public final ValueEncoderContext add(long j) throws IOException {
        zza();
        ((zzde) this.zzc).zzb(this.zzb, j);
        return this;
    }

    @Override // com.google.firebase.encoders.ValueEncoderContext
    public final ValueEncoderContext add(String str) throws IOException {
        zza();
        this.zzc.add(this.zzb, str);
        return this;
    }

    @Override // com.google.firebase.encoders.ValueEncoderContext
    public final ValueEncoderContext add(boolean z) throws IOException {
        zza();
        ((zzde) this.zzc).zzc(this.zzb, z);
        return this;
    }

    @Override // com.google.firebase.encoders.ValueEncoderContext
    public final ValueEncoderContext add(byte[] bArr) throws IOException {
        zza();
        this.zzc.add(this.zzb, bArr);
        return this;
    }
}
