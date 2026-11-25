package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapj<T> implements zzapz<T> {
    private final zzapf zza;
    private final zzaqt<?, ?> zzb;
    private final boolean zzc;
    private final zzand<?> zzd;

    private zzapj(zzaqt<?, ?> zzaqtVar, zzand<?> zzandVar, zzapf zzapfVar) {
        this.zzb = zzaqtVar;
        this.zzc = zzandVar.zzj(zzapfVar);
        this.zzd = zzandVar;
        this.zza = zzapfVar;
    }

    static <T> zzapj<T> zzc(zzaqt<?, ?> zzaqtVar, zzand<?> zzandVar, zzapf zzapfVar) {
        return new zzapj<>(zzaqtVar, zzandVar, zzapfVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final int zza(T t) {
        zzaqt<?, ?> zzaqtVar = this.zzb;
        int iZzb = zzaqtVar.zzb(zzaqtVar.zzd(t));
        return this.zzc ? iZzb + this.zzd.zzb(t).zzd() : iZzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final int zzb(T t) {
        int iHashCode = this.zzb.zzd(t).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zzb(t).zza.hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final T zze() {
        return (T) this.zza.zzN().zzu();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzf(T t) {
        this.zzb.zzm(t);
        this.zzd.zzf(t);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzg(T t, T t2) {
        zzaqb.zzF(this.zzb, t, t2);
        if (this.zzc) {
            zzaqb.zzE(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzh(T t, zzapr zzaprVar, zzanc zzancVar) throws IOException {
        boolean zZzP;
        zzaqt<?, ?> zzaqtVar = this.zzb;
        zzand<?> zzandVar = this.zzd;
        Object objZzc = zzaqtVar.zzc(t);
        zzanh<T> zzanhVarZzc = zzandVar.zzc(t);
        while (zzaprVar.zzc() != Integer.MAX_VALUE) {
            try {
                int iZzd = zzaprVar.zzd();
                if (iZzd != 11) {
                    if ((iZzd & 7) == 2) {
                        Object objZzd = zzandVar.zzd(zzancVar, this.zza, iZzd >>> 3);
                        if (objZzd != null) {
                            zzandVar.zzg(zzaprVar, objZzd, zzancVar, zzanhVarZzc);
                        } else {
                            zZzP = zzaqtVar.zzr(objZzc, zzaprVar);
                        }
                    } else {
                        zZzP = zzaprVar.zzP();
                    }
                    if (!zZzP) {
                        zzaqtVar.zzn(t, objZzc);
                        return;
                    }
                } else {
                    Object objZzd2 = null;
                    int iZzj = 0;
                    zzamk zzamkVarZzp = null;
                    while (zzaprVar.zzc() != Integer.MAX_VALUE) {
                        int iZzd2 = zzaprVar.zzd();
                        if (iZzd2 == 16) {
                            iZzj = zzaprVar.zzj();
                            objZzd2 = zzandVar.zzd(zzancVar, this.zza, iZzj);
                        } else if (iZzd2 == 26) {
                            if (objZzd2 != null) {
                                zzandVar.zzg(zzaprVar, objZzd2, zzancVar, zzanhVarZzc);
                            } else {
                                zzamkVarZzp = zzaprVar.zzp();
                            }
                        } else if (!zzaprVar.zzP()) {
                            break;
                        }
                    }
                    if (zzaprVar.zzd() != 12) {
                        throw zzaoe.zzb();
                    }
                    if (zzamkVarZzp != null) {
                        if (objZzd2 != null) {
                            zzandVar.zzh(zzamkVarZzp, objZzd2, zzancVar, zzanhVarZzc);
                        } else {
                            zzaqtVar.zzk(objZzc, iZzj, zzamkVarZzp);
                        }
                    }
                }
            } catch (Throwable th) {
                zzaqtVar.zzn(t, objZzc);
                throw th;
            }
        }
        zzaqtVar.zzn(t, objZzc);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0083  */
    /* JADX WARN: Code duplicated, block: B:56:0x0088 A[EDGE_INSN: B:56:0x0088->B:34:0x0088 BREAK  A[LOOP:1: B:18:0x004a->B:61:0x004a], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzi(T t, byte[] bArr, int i, int i2, zzalv zzalvVar) throws IOException {
        zzans zzansVar = (zzans) t;
        zzaqu zzaquVarZze = zzansVar.zzc;
        if (zzaquVarZze == zzaqu.zzc()) {
            zzaquVarZze = zzaqu.zze();
            zzansVar.zzc = zzaquVarZze;
        }
        ((zzano) t).zzh();
        Object objZzd = null;
        while (i < i2) {
            int iZzj = zzalw.zzj(bArr, i, zzalvVar);
            int i3 = zzalvVar.zza;
            if (i3 == 11) {
                int i4 = 0;
                zzamk zzamkVar = null;
                while (iZzj < i2) {
                    iZzj = zzalw.zzj(bArr, iZzj, zzalvVar);
                    int i5 = zzalvVar.zza;
                    int i6 = i5 & 7;
                    int i7 = i5 >>> 3;
                    if (i7 == 2) {
                        if (i6 != 0) {
                            if (i5 != 12) {
                                break;
                                break;
                            }
                            iZzj = zzalw.zzn(i5, bArr, iZzj, i2, zzalvVar);
                        } else {
                            iZzj = zzalw.zzj(bArr, iZzj, zzalvVar);
                            i4 = zzalvVar.zza;
                            objZzd = this.zzd.zzd(zzalvVar.zzd, this.zza, i4);
                        }
                    } else {
                        if (i7 == 3) {
                            if (objZzd != null) {
                                int i8 = zzapo.zza;
                                throw null;
                            }
                            if (i6 == 2) {
                                iZzj = zzalw.zza(bArr, iZzj, zzalvVar);
                                zzamkVar = (zzamk) zzalvVar.zzc;
                            }
                        }
                        if (i5 != 12) {
                            break;
                        } else {
                            iZzj = zzalw.zzn(i5, bArr, iZzj, i2, zzalvVar);
                        }
                    }
                }
                if (zzamkVar != null) {
                    zzaquVarZze.zzh((i4 << 3) | 2, zzamkVar);
                }
                i = iZzj;
            } else if ((i3 & 7) == 2) {
                objZzd = this.zzd.zzd(zzalvVar.zzd, this.zza, i3 >>> 3);
                if (objZzd != null) {
                    int i9 = zzapo.zza;
                    throw null;
                }
                i = zzalw.zzi(i3, bArr, iZzj, i2, zzaquVarZze, zzalvVar);
            } else {
                i = zzalw.zzn(i3, bArr, iZzj, i2, zzalvVar);
            }
        }
        if (i != i2) {
            throw zzaoe.zzg();
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final void zzj(T t, zzarl zzarlVar) throws IOException {
        Iterator itZzh = this.zzd.zzb(t).zzh();
        while (itZzh.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzh.next();
            zzang zzangVar = (zzang) entry.getKey();
            if (zzangVar.zze() != zzark.MESSAGE || zzangVar.zzg() || zzangVar.zzf()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzaoh) {
                zzarlVar.zzx(zzangVar.zza(), ((zzaoh) entry).zza().zzb());
            } else {
                zzarlVar.zzx(zzangVar.zza(), entry.getValue());
            }
        }
        zzaqt<?, ?> zzaqtVar = this.zzb;
        zzaqtVar.zzp(zzaqtVar.zzd(t), zzarlVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final boolean zzk(T t, T t2) {
        if (!this.zzb.zzd(t).equals(this.zzb.zzd(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zzb(t).equals(this.zzd.zzb(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapz
    public final boolean zzl(T t) {
        return this.zzd.zzb(t).zzn();
    }
}
