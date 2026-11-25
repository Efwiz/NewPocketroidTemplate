package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwo<T> implements zzwx<T> {
    private final zzwk zza;
    private final zzxo<?, ?> zzb;
    private final boolean zzc;
    private final zzuk<?> zzd;

    private zzwo(zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwk zzwkVar) {
        this.zzb = zzxoVar;
        this.zzc = zzukVar.zzi(zzwkVar);
        this.zzd = zzukVar;
        this.zza = zzwkVar;
    }

    static <T> zzwo<T> zzc(zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwk zzwkVar) {
        return new zzwo<>(zzxoVar, zzukVar, zzwkVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zza(T t) {
        zzxo<?, ?> zzxoVar = this.zzb;
        int iZzb = zzxoVar.zzb(zzxoVar.zzd(t));
        return this.zzc ? iZzb + this.zzd.zzb(t).zzb() : iZzb;
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zzb(T t) {
        int iHashCode = this.zzb.zzd(t).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zzb(t).zza.hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final T zze() {
        return (T) this.zza.zzao().zzD();
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzf(T t) {
        this.zzb.zzm(t);
        this.zzd.zzf(t);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzg(T t, T t2) {
        zzwz.zzF(this.zzb, t, t2);
        if (this.zzc) {
            zzwz.zzE(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzh(T t, zzww zzwwVar, zzuj zzujVar) throws IOException {
        boolean zZzT;
        zzxo<?, ?> zzxoVar = this.zzb;
        zzuk<?> zzukVar = this.zzd;
        Object objZzc = zzxoVar.zzc(t);
        zzuo<T> zzuoVarZzc = zzukVar.zzc(t);
        while (zzwwVar.zzc() != Integer.MAX_VALUE) {
            try {
                int iZzd = zzwwVar.zzd();
                if (iZzd != 11) {
                    if ((iZzd & 7) == 2) {
                        Object objZzd = zzukVar.zzd(zzujVar, this.zza, iZzd >>> 3);
                        if (objZzd != null) {
                            zzukVar.zzg(zzwwVar, objZzd, zzujVar, zzuoVarZzc);
                        } else {
                            zZzT = zzxoVar.zzp(objZzc, zzwwVar);
                        }
                    } else {
                        zZzT = zzwwVar.zzT();
                    }
                    if (!zZzT) {
                        zzxoVar.zzn(t, objZzc);
                        return;
                    }
                } else {
                    Object objZzd2 = null;
                    int iZzj = 0;
                    zztd zztdVarZzq = null;
                    while (zzwwVar.zzc() != Integer.MAX_VALUE) {
                        int iZzd2 = zzwwVar.zzd();
                        if (iZzd2 == 16) {
                            iZzj = zzwwVar.zzj();
                            objZzd2 = zzukVar.zzd(zzujVar, this.zza, iZzj);
                        } else if (iZzd2 == 26) {
                            if (objZzd2 != null) {
                                zzukVar.zzg(zzwwVar, objZzd2, zzujVar, zzuoVarZzc);
                            } else {
                                zztdVarZzq = zzwwVar.zzq();
                            }
                        } else if (!zzwwVar.zzT()) {
                            break;
                        }
                    }
                    if (zzwwVar.zzd() != 12) {
                        throw zzvk.zzb();
                    }
                    if (zztdVarZzq != null) {
                        if (objZzd2 != null) {
                            zzukVar.zzh(zztdVarZzq, objZzd2, zzujVar, zzuoVarZzc);
                        } else {
                            zzxoVar.zzk(objZzc, iZzj, zztdVarZzq);
                        }
                    }
                }
            } catch (Throwable th) {
                zzxoVar.zzn(t, objZzc);
                throw th;
            }
        }
        zzxoVar.zzn(t, objZzc);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:57:0x00bf A[EDGE_INSN: B:57:0x00bf->B:33:0x00bf BREAK  A[LOOP:1: B:18:0x0067->B:60:0x0067], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzi(T t, byte[] bArr, int i, int i2, zzsl zzslVar) throws IOException {
        zzuz zzuzVar = (zzuz) t;
        zzxp zzxpVarZze = zzuzVar.zzc;
        if (zzxpVarZze == zzxp.zzc()) {
            zzxpVarZze = zzxp.zze();
            zzuzVar.zzc = zzxpVarZze;
        }
        zzuo<zzuw> zzuoVarZzU = ((zzuv) t).zzU();
        Object objZzd = null;
        while (i < i2) {
            int iZzj = zzsm.zzj(bArr, i, zzslVar);
            int i3 = zzslVar.zza;
            if (i3 == 11) {
                int i4 = 0;
                zztd zztdVar = null;
                while (iZzj < i2) {
                    iZzj = zzsm.zzj(bArr, iZzj, zzslVar);
                    int i5 = zzslVar.zza;
                    int i6 = i5 & 7;
                    int i7 = i5 >>> 3;
                    if (i7 == 2) {
                        if (i6 != 0) {
                            if (i5 != 12) {
                                break;
                                break;
                            }
                            iZzj = zzsm.zzn(i5, bArr, iZzj, i2, zzslVar);
                        } else {
                            iZzj = zzsm.zzj(bArr, iZzj, zzslVar);
                            i4 = zzslVar.zza;
                            objZzd = this.zzd.zzd(zzslVar.zzd, this.zza, i4);
                        }
                    } else {
                        if (i7 == 3) {
                            if (objZzd != null) {
                                zzux zzuxVar = (zzux) objZzd;
                                iZzj = zzsm.zzd(zzwt.zza().zzb(zzuxVar.zzc.getClass()), bArr, iZzj, i2, zzslVar);
                                zzuoVarZzU.zzi(zzuxVar.zzd, zzslVar.zzc);
                            } else if (i6 == 2) {
                                iZzj = zzsm.zza(bArr, iZzj, zzslVar);
                                zztdVar = (zztd) zzslVar.zzc;
                            }
                        }
                        if (i5 != 12) {
                            break;
                        } else {
                            iZzj = zzsm.zzn(i5, bArr, iZzj, i2, zzslVar);
                        }
                    }
                }
                if (zztdVar != null) {
                    zzxpVarZze.zzh((i4 << 3) | 2, zztdVar);
                }
                i = iZzj;
            } else if ((i3 & 7) == 2) {
                Object objZzd2 = this.zzd.zzd(zzslVar.zzd, this.zza, i3 >>> 3);
                if (objZzd2 != null) {
                    zzux zzuxVar2 = (zzux) objZzd2;
                    i = zzsm.zzd(zzwt.zza().zzb(zzuxVar2.zzc.getClass()), bArr, iZzj, i2, zzslVar);
                    zzuoVarZzU.zzi(zzuxVar2.zzd, zzslVar.zzc);
                } else {
                    i = zzsm.zzi(i3, bArr, iZzj, i2, zzxpVarZze, zzslVar);
                }
                objZzd = objZzd2;
            } else {
                i = zzsm.zzn(i3, bArr, iZzj, i2, zzslVar);
            }
        }
        if (i != i2) {
            throw zzvk.zzg();
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final boolean zzj(T t, T t2) {
        if (!this.zzb.zzd(t).equals(this.zzb.zzd(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zzb(t).equals(this.zzd.zzb(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final boolean zzk(T t) {
        return this.zzd.zzb(t).zzk();
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzn(T t, zztp zztpVar) throws IOException {
        Iterator itZzf = this.zzd.zzb(t).zzf();
        while (itZzf.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzf.next();
            zzun zzunVar = (zzun) entry.getKey();
            if (zzunVar.zze() != zzyf.MESSAGE) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            zzunVar.zzg();
            zzunVar.zzf();
            if (entry instanceof zzvn) {
                zztpVar.zzw(zzunVar.zza(), ((zzvn) entry).zza().zzb());
            } else {
                zztpVar.zzw(zzunVar.zza(), entry.getValue());
            }
        }
        zzxo<?, ?> zzxoVar = this.zzb;
        zzxoVar.zzr(zzxoVar.zzd(t), zztpVar);
    }
}
