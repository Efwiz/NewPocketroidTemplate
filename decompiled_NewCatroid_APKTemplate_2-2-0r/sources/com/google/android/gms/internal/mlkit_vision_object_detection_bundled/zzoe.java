package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.content.Context;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transformer;
import com.google.android.datatransport.Transport;
import com.google.android.datatransport.TransportFactory;
import com.google.android.datatransport.cct.CCTDestination;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.firebase.components.Lazy;
import com.google.firebase.inject.Provider;
import com.reandroid.apkeditor.Options;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzoe implements zznv {
    private Provider<Transport<byte[]>> zza;
    private final Provider<Transport<byte[]>> zzb;
    private final zznq zzc;

    public zzoe(Context context, zznq zznqVar) {
        this.zzc = zznqVar;
        CCTDestination cCTDestination = CCTDestination.INSTANCE;
        TransportRuntime.initialize(context);
        final TransportFactory transportFactoryNewFactory = TransportRuntime.getInstance().newFactory(cCTDestination);
        if (cCTDestination.getSupportedEncodings().contains(Encoding.of(Options.TYPE_JSON))) {
            this.zza = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzoc
                @Override // com.google.firebase.inject.Provider
                public final Object get() {
                    return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of(Options.TYPE_JSON), new Transformer() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzoa
                        @Override // com.google.android.datatransport.Transformer
                        public final Object apply(Object obj) {
                            return (byte[]) obj;
                        }
                    });
                }
            });
        }
        this.zzb = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzod
            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of("proto"), new Transformer() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzob
                    @Override // com.google.android.datatransport.Transformer
                    public final Object apply(Object obj) {
                        return (byte[]) obj;
                    }
                });
            }
        });
    }

    static Event<byte[]> zzb(zznq zznqVar, zznz zznzVar) {
        int iZza = zznqVar.zza();
        return zznzVar.zza() != 0 ? Event.ofData(zznzVar.zzc(iZza, false)) : Event.ofTelemetry(zznzVar.zzc(iZza, false));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznv
    public final void zza(zznz zznzVar) {
        if (this.zzc.zza() != 0) {
            this.zzb.get().send(zzb(this.zzc, zznzVar));
            return;
        }
        Provider<Transport<byte[]>> provider = this.zza;
        if (provider != null) {
            provider.get().send(zzb(this.zzc, zznzVar));
        }
    }
}
