package com.google.android.gms.internal.mlkit_vision_text;

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

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkw implements zzkn {
    private final Provider<Transport<byte[]>> zza;
    private final Provider<Transport<byte[]>> zzb;
    private final zzkg zzc;

    public zzkw(Context context, zzkg zzkgVar) {
        this.zzc = zzkgVar;
        TransportRuntime.initialize(context);
        final TransportFactory transportFactoryNewFactory = TransportRuntime.getInstance().newFactory(CCTDestination.INSTANCE);
        this.zza = new Lazy(new Provider(transportFactoryNewFactory) { // from class: com.google.android.gms.internal.mlkit_vision_text.zzkr
            private final TransportFactory zza;

            {
                this.zza = transportFactoryNewFactory;
            }

            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return this.zza.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of(Options.TYPE_JSON), zzku.zza);
            }
        });
        this.zzb = new Lazy(new Provider(transportFactoryNewFactory) { // from class: com.google.android.gms.internal.mlkit_vision_text.zzks
            private final TransportFactory zza;

            {
                this.zza = transportFactoryNewFactory;
            }

            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return this.zza.getTransport("FIREBASE_ML_SDK", byte[].class, Encoding.of("proto"), zzkt.zza);
            }
        });
    }

    static Event<byte[]> zzb(zzkg zzkgVar, zzkp zzkpVar) {
        int iZze = zzkgVar.zze();
        int i = zzkv.zza[zzkgVar.zzd().ordinal()];
        if (i != 1) {
            return i != 2 ? Event.ofData(zzkpVar.zza(iZze, false)) : Event.ofTelemetry(zzkpVar.zza(iZze, false));
        }
        return Event.ofUrgent(zzkpVar.zza(iZze, false));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkn
    public final void zza(zzkp zzkpVar) {
        if (this.zzc.zze() == 0) {
            this.zza.get().send(zzb(this.zzc, zzkpVar));
        } else {
            this.zzb.get().send(zzb(this.zzc, zzkpVar));
        }
    }
}
