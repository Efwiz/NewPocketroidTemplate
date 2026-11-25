package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgo<T> implements zzgx<T> {
    private final zzgi zza;
    private final zzhp<?, ?> zzb;
    private final boolean zzc;
    private final zzeq<?> zzd;

    private zzgo(zzhp<?, ?> zzhpVar, zzeq<?> zzeqVar, zzgi zzgiVar) {
        this.zzb = zzhpVar;
        this.zzc = zzeqVar.zza(zzgiVar);
        this.zzd = zzeqVar;
        this.zza = zzgiVar;
    }

    static <T> zzgo<T> zza(zzhp<?, ?> zzhpVar, zzeq<?> zzeqVar, zzgi zzgiVar) {
        return new zzgo<>(zzhpVar, zzeqVar, zzgiVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final T zza() {
        return (T) this.zza.zzk().zzf();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final boolean zza(T t, T t2) {
        if (!this.zzb.zza(t).equals(this.zzb.zza(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza(t).equals(this.zzd.zza(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final int zza(T t) {
        int iHashCode = this.zzb.zza(t).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zza(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zzb(T t, T t2) {
        zzgz.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzgz.zza(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zza(T t, zzim zzimVar) throws IOException {
        Iterator itZzd = this.zzd.zza(t).zzd();
        while (itZzd.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzd.next();
            zzew zzewVar = (zzew) entry.getKey();
            if (zzewVar.zzc() != zzij.MESSAGE || zzewVar.zzd() || zzewVar.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzfl) {
                zzimVar.zza(zzewVar.zza(), (Object) ((zzfl) entry).zza().zzc());
            } else {
                zzimVar.zza(zzewVar.zza(), entry.getValue());
            }
        }
        zzhp<?, ?> zzhpVar = this.zzb;
        zzhpVar.zzb(zzhpVar.zza(t), zzimVar);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0094  */
    /* JADX WARN: Code duplicated, block: B:56:0x0099 A[EDGE_INSN: B:56:0x0099->B:34:0x0099 BREAK  A[LOOP:1: B:18:0x0053->B:61:0x0053], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zza(T t, byte[] bArr, int i, int i2, zzdt zzdtVar) throws IOException {
        zzfb zzfbVar = (zzfb) t;
        zzhs zzhsVarZzb = zzfbVar.zzb;
        if (zzhsVarZzb == zzhs.zza()) {
            zzhsVarZzb = zzhs.zzb();
            zzfbVar.zzb = zzhsVarZzb;
        }
        ((zzfb.zzb) t).zza();
        zzfb.zzd zzdVar = null;
        while (i < i2) {
            int iZza = zzdq.zza(bArr, i, zzdtVar);
            int i3 = zzdtVar.zza;
            if (i3 == 11) {
                int i4 = 0;
                zzdu zzduVar = null;
                while (iZza < i2) {
                    iZza = zzdq.zza(bArr, iZza, zzdtVar);
                    int i5 = zzdtVar.zza;
                    int i6 = i5 >>> 3;
                    int i7 = i5 & 7;
                    if (i6 == 2) {
                        if (i7 != 0) {
                            if (i5 != 12) {
                                break;
                                break;
                            }
                            iZza = zzdq.zza(i5, bArr, iZza, i2, zzdtVar);
                        } else {
                            iZza = zzdq.zza(bArr, iZza, zzdtVar);
                            i4 = zzdtVar.zza;
                            zzdVar = (zzfb.zzd) this.zzd.zza(zzdtVar.zzd, this.zza, i4);
                        }
                    } else {
                        if (i6 == 3) {
                            if (zzdVar != null) {
                                zzgw.zza();
                                throw new NoSuchMethodError();
                            }
                            if (i7 == 2) {
                                iZza = zzdq.zze(bArr, iZza, zzdtVar);
                                zzduVar = (zzdu) zzdtVar.zzc;
                            }
                        }
                        if (i5 != 12) {
                            break;
                        } else {
                            iZza = zzdq.zza(i5, bArr, iZza, i2, zzdtVar);
                        }
                    }
                }
                if (zzduVar != null) {
                    zzhsVarZzb.zza((i4 << 3) | 2, zzduVar);
                }
                i = iZza;
            } else if ((i3 & 7) == 2) {
                zzdVar = (zzfb.zzd) this.zzd.zza(zzdtVar.zzd, this.zza, i3 >>> 3);
                if (zzdVar != null) {
                    zzgw.zza();
                    throw new NoSuchMethodError();
                }
                i = zzdq.zza(i3, bArr, iZza, i2, zzhsVarZzb, zzdtVar);
            } else {
                i = zzdq.zza(i3, bArr, iZza, i2, zzdtVar);
            }
        }
        if (i != i2) {
            throw zzfi.zze();
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final void zzc(T t) {
        this.zzb.zzb(t);
        this.zzd.zzc(t);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final boolean zzd(T t) {
        return this.zzd.zza(t).zzf();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgx
    public final int zzb(T t) {
        zzhp<?, ?> zzhpVar = this.zzb;
        int iZzc = zzhpVar.zzc(zzhpVar.zza(t));
        return this.zzc ? iZzc + this.zzd.zza(t).zzg() : iZzc;
    }
}
