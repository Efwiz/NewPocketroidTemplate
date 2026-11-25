package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzay extends zzal {
    private final String zza;

    protected zzay(String str) {
        this.zza = str;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public String zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public void zza(RuntimeException runtimeException, zzai zzaiVar) {
        zza(new zza(runtimeException, zzaiVar));
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    private static final class zza implements zzai {
        private final String zza;
        private final zzai zzb;

        private zza(RuntimeException runtimeException, zzai zzaiVar) {
            StringBuilder sbAppend = new StringBuilder("LOGGING ERROR: ").append(runtimeException.getMessage()).append("\n  original message: ");
            if (zzaiVar.zzg() == null) {
                sbAppend.append(zzaiVar.zzi());
            } else {
                sbAppend.append(zzaiVar.zzg().zzb());
                sbAppend.append("\n  original arguments:");
                for (Object obj : zzaiVar.zzh()) {
                    sbAppend.append("\n    ").append(zzat.zza(obj));
                }
            }
            zzan zzanVarZzk = zzaiVar.zzk();
            if (zzanVarZzk.zza() > 0) {
                sbAppend.append("\n  metadata:");
                for (int i = 0; i < zzanVarZzk.zza(); i++) {
                    sbAppend.append("\n    ").append(zzanVarZzk.zza(i)).append(": ").append(zzanVarZzk.zzb(i));
                }
            }
            sbAppend.append("\n  level: ").append(zzaiVar.zzd());
            sbAppend.append("\n  timestamp (nanos): ").append(zzaiVar.zze());
            sbAppend.append("\n  class: ").append(zzaiVar.zzf().zza());
            sbAppend.append("\n  method: ").append(zzaiVar.zzf().zzb());
            sbAppend.append("\n  line number: ").append(zzaiVar.zzf().zzc());
            this.zza = sbAppend.toString();
            this.zzb = zzaiVar;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final zzaz zzg() {
            return null;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final boolean zzj() {
            return false;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final Level zzd() {
            return this.zzb.zzd().intValue() > Level.WARNING.intValue() ? this.zzb.zzd() : Level.WARNING;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final long zze() {
            return this.zzb.zze();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final zzo zzf() {
            return this.zzb.zzf();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final zzan zzk() {
            return zzan.zzb();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final Object[] zzh() {
            throw new IllegalStateException();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
        public final Object zzi() {
            return this.zza;
        }
    }
}
