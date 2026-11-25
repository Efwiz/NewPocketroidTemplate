package com.google.android.gms.internal.mlkit_vision_common;

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

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzid implements zzhw {
    private Provider<Transport<byte[]>> zza;
    private final Provider<Transport<byte[]>> zzb;
    private final zzhr zzc;

    public zzid(Context context, zzhr zzhrVar) {
        this.zzc = zzhrVar;
        CCTDestination cCTDestination = CCTDestination.INSTANCE;
        TransportRuntime.initialize(context);
        final TransportFactory transportFactoryNewFactory = TransportRuntime.getInstance().newFactory(cCTDestination);
        if (cCTDestination.getSupportedEncodings().contains(Encoding.of(Options.TYPE_JSON))) {
            this.zza = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_vision_common.zzib
                @Override // com.google.firebase.inject.Provider
                public final Object get() {
                    return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of(Options.TYPE_JSON), new Transformer() { // from class: com.google.android.gms.internal.mlkit_vision_common.zzhz
                        @Override // com.google.android.datatransport.Transformer
                        public final Object apply(Object obj) {
                            return (byte[]) obj;
                        }
                    });
                }
            });
        }
        this.zzb = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_vision_common.zzic
            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of("proto"), new Transformer() { // from class: com.google.android.gms.internal.mlkit_vision_common.zzia
                    @Override // com.google.android.datatransport.Transformer
                    public final Object apply(Object obj) {
                        return (byte[]) obj;
                    }
                });
            }
        });
    }

    static Event<byte[]> zzb(zzhr zzhrVar, zzhy zzhyVar) {
        return Event.ofTelemetry(zzhyVar.zzb(zzhrVar.zza(), false));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzhw
    public final void zza(zzhy zzhyVar) {
        if (this.zzc.zza() != 0) {
            this.zzb.get().send(zzb(this.zzc, zzhyVar));
            return;
        }
        Provider<Transport<byte[]>> provider = this.zza;
        if (provider != null) {
            provider.get().send(zzb(this.zzc, zzhyVar));
        }
    }
}
