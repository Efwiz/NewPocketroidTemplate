package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzne {
    private static zzne zza;
    private final zznc zzb;
    private final zznj zzc;
    private final zznh zzd;
    private zznk zze;

    public zzne(Context context, zznd zzndVar) {
        zznh zznhVar = new zznh();
        this.zzd = zznhVar;
        this.zzc = new zznj(context);
        this.zzb = new zznc(zzndVar, zznhVar);
    }

    public static synchronized zzne zzb() {
        if (zza == null) {
            zza = new zzne((Context) MlKitContext.getInstance().get(Context.class), zznl.zza);
        }
        return zza;
    }

    public final zzmz zza() {
        Preconditions.checkState(this.zze != null);
        return this.zze.zzb();
    }

    public final String zzc() throws InterruptedException {
        Preconditions.checkState(this.zze != null);
        Preconditions.checkState(this.zze != null);
        if (this.zze.zze()) {
            zzng zzngVar = new zzng();
            zzngVar.zzg();
            try {
                if (this.zzb.zzc(zzngVar)) {
                    this.zze = this.zzb.zza();
                }
                zzngVar.zze();
                this.zzd.zza(zzkp.INSTALLATION_ID_REFRESH_TEMPORARY_TOKEN, zzngVar);
            } catch (Throwable th) {
                zzngVar.zze();
                this.zzd.zza(zzkp.INSTALLATION_ID_REFRESH_TEMPORARY_TOKEN, zzngVar);
                throw th;
            }
        }
        Preconditions.checkState(this.zze != null);
        return this.zze.zzd();
    }

    public final void zzd() throws InterruptedException, IOException {
        zznh zznhVar;
        zzkp zzkpVar;
        zzng zzngVar = new zzng();
        zzngVar.zzg();
        try {
            zznk zznkVarZza = this.zzc.zza(zzngVar);
            if (zznkVarZza != null) {
                this.zze = zznkVarZza;
            } else {
                final zzng zzngVar2 = new zzng();
                zzngVar2.zzg();
                try {
                    final zzmz zzmzVar = new zzmz(zznf.zza());
                    final zznc zzncVar = this.zzb;
                    if (zzpc.zza(new zzpb() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzna
                        @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzpb
                        public final boolean zza() {
                            return zzncVar.zzb(zzmzVar, zzngVar2);
                        }
                    })) {
                        zznk zznkVarZza2 = this.zzb.zza();
                        this.zze = zznkVarZza2;
                        if (zznkVarZza2 != null) {
                            this.zzc.zzc(zznkVarZza2, zzngVar2);
                        }
                        zzngVar2.zze();
                        zznhVar = this.zzd;
                        zzkpVar = zzkp.INSTALLATION_ID_REGISTER_NEW_ID;
                    } else {
                        zzngVar2.zzd(zzmq.RPC_EXPONENTIAL_BACKOFF_FAILED);
                        zzngVar2.zzd(zzmq.RPC_ERROR);
                        zzngVar2.zze();
                        zznhVar = this.zzd;
                        zzkpVar = zzkp.INSTALLATION_ID_REGISTER_NEW_ID;
                    }
                    zznhVar.zza(zzkpVar, zzngVar2);
                } catch (Throwable th) {
                    zzngVar2.zze();
                    this.zzd.zza(zzkp.INSTALLATION_ID_REGISTER_NEW_ID, zzngVar2);
                    throw th;
                }
            }
            zzngVar.zze();
            this.zzd.zza(zzkp.INSTALLATION_ID_INIT, zzngVar);
        } catch (Throwable th2) {
            zzngVar.zze();
            this.zzd.zza(zzkp.INSTALLATION_ID_INIT, zzngVar);
            throw th2;
        }
    }
}
