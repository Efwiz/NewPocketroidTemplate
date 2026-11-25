package com.google.android.gms.internal.mlkit_vision_internal_vkp;

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

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzahk implements zzahc {
    private Provider<Transport<byte[]>> zza;
    private final Provider<Transport<byte[]>> zzb;
    private final zzagx zzc;

    public zzahk(Context context, zzagx zzagxVar) {
        this.zzc = zzagxVar;
        CCTDestination cCTDestination = CCTDestination.INSTANCE;
        TransportRuntime.initialize(context);
        final TransportFactory transportFactoryNewFactory = TransportRuntime.getInstance().newFactory(cCTDestination);
        if (cCTDestination.getSupportedEncodings().contains(Encoding.of(Options.TYPE_JSON))) {
            this.zza = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahi
                @Override // com.google.firebase.inject.Provider
                public final Object get() {
                    return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of(Options.TYPE_JSON), new Transformer() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahg
                        @Override // com.google.android.datatransport.Transformer
                        public final Object apply(Object obj) {
                            return (byte[]) obj;
                        }
                    });
                }
            });
        }
        this.zzb = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahj
            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of("proto"), new Transformer() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahh
                    @Override // com.google.android.datatransport.Transformer
                    public final Object apply(Object obj) {
                        return (byte[]) obj;
                    }
                });
            }
        });
    }

    static Event<byte[]> zzb(zzagx zzagxVar, zzagv zzagvVar) {
        int iZza = zzagxVar.zza();
        zzagvVar.zza();
        return Event.ofTelemetry(zzagvVar.zze(iZza, false));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahc
    public final void zza(zzagv zzagvVar) {
        if (this.zzc.zza() != 0) {
            this.zzb.get().send(zzb(this.zzc, zzagvVar));
            return;
        }
        Provider<Transport<byte[]>> provider = this.zza;
        if (provider != null) {
            provider.get().send(zzb(this.zzc, zzagvVar));
        }
    }
}
