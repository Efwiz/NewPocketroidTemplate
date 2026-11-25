package com.google.android.gms.internal.mlkit_common;

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

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjz implements zzjq {
    private Provider<Transport<byte[]>> zza;
    private final Provider<Transport<byte[]>> zzb;
    private final zzjk zzc;

    public zzjz(Context context, zzjk zzjkVar) {
        this.zzc = zzjkVar;
        CCTDestination cCTDestination = CCTDestination.INSTANCE;
        TransportRuntime.initialize(context);
        final TransportFactory transportFactoryNewFactory = TransportRuntime.getInstance().newFactory(cCTDestination);
        if (cCTDestination.getSupportedEncodings().contains(Encoding.of(Options.TYPE_JSON))) {
            this.zza = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_common.zzjx
                @Override // com.google.firebase.inject.Provider
                public final Object get() {
                    return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of(Options.TYPE_JSON), new Transformer() { // from class: com.google.android.gms.internal.mlkit_common.zzjv
                        @Override // com.google.android.datatransport.Transformer
                        public final Object apply(Object obj) {
                            return (byte[]) obj;
                        }
                    });
                }
            });
        }
        this.zzb = new Lazy(new Provider() { // from class: com.google.android.gms.internal.mlkit_common.zzjy
            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return transportFactoryNewFactory.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of("proto"), new Transformer() { // from class: com.google.android.gms.internal.mlkit_common.zzjw
                    @Override // com.google.android.datatransport.Transformer
                    public final Object apply(Object obj) {
                        return (byte[]) obj;
                    }
                });
            }
        });
    }

    static Event<byte[]> zzb(zzjk zzjkVar, zzji zzjiVar) {
        return Event.ofTelemetry(zzjiVar.zze(zzjkVar.zza(), false));
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjq
    public final void zza(zzji zzjiVar) {
        if (this.zzc.zza() != 0) {
            this.zzb.get().send(zzb(this.zzc, zzjiVar));
            return;
        }
        Provider<Transport<byte[]>> provider = this.zza;
        if (provider != null) {
            provider.get().send(zzb(this.zzc, zzjiVar));
        }
    }
}
