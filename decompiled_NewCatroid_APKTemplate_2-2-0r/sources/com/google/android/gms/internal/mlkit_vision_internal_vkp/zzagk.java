package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzagk {
    private static zzagk zza;
    private final zzagi zzb;
    private final zzagp zzc;
    private final zzagn zzd;
    private zzagq zze;

    public zzagk(Context context, zzagj zzagjVar) {
        zzagn zzagnVar = new zzagn();
        this.zzd = zzagnVar;
        this.zzc = new zzagp(context);
        this.zzb = new zzagi(zzagjVar, zzagnVar);
    }

    public static synchronized zzagk zzb() {
        if (zza == null) {
            zza = new zzagk((Context) MlKitContext.getInstance().get(Context.class), zzagr.zza);
        }
        return zza;
    }

    public final zzagg zza() {
        Preconditions.checkState(this.zze != null);
        return this.zze.zzb();
    }

    public final void zzc() throws InterruptedException, IOException {
        zzagn zzagnVar;
        zzadr zzadrVar;
        zzagm zzagmVar = new zzagm();
        zzagmVar.zzg();
        try {
            zzagq zzagqVarZza = this.zzc.zza(zzagmVar);
            if (zzagqVarZza != null) {
                this.zze = zzagqVarZza;
            } else {
                final zzagm zzagmVar2 = new zzagm();
                zzagmVar2.zzg();
                try {
                    final zzagg zzaggVar = new zzagg(zzagl.zza());
                    final zzagi zzagiVar = this.zzb;
                    if (zzahr.zza(new zzahq() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagh
                        @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahq
                        public final boolean zzb() {
                            return zzagiVar.zzb(zzaggVar, zzagmVar2);
                        }
                    })) {
                        zzagq zzagqVarZza2 = this.zzb.zza();
                        this.zze = zzagqVarZza2;
                        if (zzagqVarZza2 != null) {
                            this.zzc.zzc(zzagqVarZza2, zzagmVar2);
                        }
                        zzagmVar2.zze();
                        zzagnVar = this.zzd;
                        zzadrVar = zzadr.INSTALLATION_ID_REGISTER_NEW_ID;
                    } else {
                        zzagmVar2.zzd(zzafd.RPC_EXPONENTIAL_BACKOFF_FAILED);
                        zzagmVar2.zzd(zzafd.RPC_ERROR);
                        zzagmVar2.zze();
                        zzagnVar = this.zzd;
                        zzadrVar = zzadr.INSTALLATION_ID_REGISTER_NEW_ID;
                    }
                    zzagnVar.zza(zzadrVar, zzagmVar2);
                } catch (Throwable th) {
                    zzagmVar2.zze();
                    this.zzd.zza(zzadr.INSTALLATION_ID_REGISTER_NEW_ID, zzagmVar2);
                    throw th;
                }
            }
            zzagmVar.zze();
            this.zzd.zza(zzadr.INSTALLATION_ID_INIT, zzagmVar);
        } catch (Throwable th2) {
            zzagmVar.zze();
            this.zzd.zza(zzadr.INSTALLATION_ID_INIT, zzagmVar);
            throw th2;
        }
    }
}
