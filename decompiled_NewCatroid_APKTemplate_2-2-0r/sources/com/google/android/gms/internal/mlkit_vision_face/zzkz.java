package com.google.android.gms.internal.mlkit_vision_face;

import android.content.Context;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transport;
import com.google.android.datatransport.TransportFactory;
import com.google.android.datatransport.cct.CCTDestination;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.firebase.components.Lazy;
import com.google.firebase.inject.Provider;
import com.reandroid.apkeditor.Options;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkz implements zzkp {
    private Provider<Transport<byte[]>> zza;
    private final Provider<Transport<byte[]>> zzb;
    private final zzki zzc;

    public zzkz(Context context, zzki zzkiVar) {
        this.zzc = zzkiVar;
        CCTDestination cCTDestination = CCTDestination.INSTANCE;
        TransportRuntime.initialize(context);
        final TransportFactory transportFactoryNewFactory = TransportRuntime.getInstance().newFactory(cCTDestination);
        if (cCTDestination.getSupportedEncodings().contains(Encoding.of(Options.TYPE_JSON))) {
            this.zza = new Lazy(new Provider(transportFactoryNewFactory) { // from class: com.google.android.gms.internal.mlkit_vision_face.zzku
                private final TransportFactory zza;

                {
                    this.zza = transportFactoryNewFactory;
                }

                @Override // com.google.firebase.inject.Provider
                public final Object get() {
                    return this.zza.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of(Options.TYPE_JSON), zzkx.zza);
                }
            });
        }
        this.zzb = new Lazy(new Provider(transportFactoryNewFactory) { // from class: com.google.android.gms.internal.mlkit_vision_face.zzkv
            private final TransportFactory zza;

            {
                this.zza = transportFactoryNewFactory;
            }

            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return this.zza.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of("proto"), zzkw.zza);
            }
        });
    }

    static Event<byte[]> zzb(zzki zzkiVar, zzks zzksVar) {
        int iZze = zzkiVar.zze();
        int i = zzky.zza[zzkiVar.zzd().ordinal()];
        if (i != 1) {
            return i != 2 ? Event.ofData(zzksVar.zza(iZze, false)) : Event.ofTelemetry(zzksVar.zza(iZze, false));
        }
        return Event.ofUrgent(zzksVar.zza(iZze, false));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkp
    public final void zza(zzks zzksVar) {
        if (this.zzc.zze() != 0) {
            this.zzb.get().send(zzb(this.zzc, zzksVar));
            return;
        }
        Provider<Transport<byte[]>> provider = this.zza;
        if (provider != null) {
            provider.get().send(zzb(this.zzc, zzksVar));
        }
    }
}
