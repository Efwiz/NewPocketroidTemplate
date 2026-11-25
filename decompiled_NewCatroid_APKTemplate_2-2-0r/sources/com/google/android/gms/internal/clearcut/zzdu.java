package com.google.android.gms.internal.clearcut;

import java.io.IOException;

/* JADX INFO: loaded from: classes9.dex */
final class zzdu<T> implements zzef<T> {
    private final zzdo zzmn;
    private final boolean zzmo;
    private final zzex<?, ?> zzmx;
    private final zzbu<?> zzmy;

    private zzdu(zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdo zzdoVar) {
        this.zzmx = zzexVar;
        this.zzmo = zzbuVar.zze(zzdoVar);
        this.zzmy = zzbuVar;
        this.zzmn = zzdoVar;
    }

    static <T> zzdu<T> zza(zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdo zzdoVar) {
        return new zzdu<>(zzexVar, zzbuVar, zzdoVar);
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean equals(T t, T t2) {
        if (!this.zzmx.zzq(t).equals(this.zzmx.zzq(t2))) {
            return false;
        }
        if (this.zzmo) {
            return this.zzmy.zza(t).equals(this.zzmy.zza(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final int hashCode(T t) {
        int iHashCode = this.zzmx.zzq(t).hashCode();
        return this.zzmo ? (iHashCode * 53) + this.zzmy.zza(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final T newInstance() {
        return (T) this.zzmn.zzbd().zzbi();
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zza(T t, zzfr zzfrVar) throws IOException {
        for (T t2 : this.zzmy.zza(t)) {
            zzca zzcaVar = (zzca) t2.getKey();
            if (zzcaVar.zzav() != zzfq.MESSAGE || zzcaVar.zzaw() || zzcaVar.zzax()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            zzfrVar.zza(zzcaVar.zzc(), t2 instanceof zzct ? ((zzct) t2).zzbs().zzr() : t2.getValue());
        }
        zzex<?, ?> zzexVar = this.zzmx;
        zzexVar.zzc(zzexVar.zzq(t), zzfrVar);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x005c  */
    /* JADX WARN: Code duplicated, block: B:49:0x0061 A[EDGE_INSN: B:49:0x0061->B:27:0x0061 BREAK  A[LOOP:1: B:14:0x0032->B:52:0x0032], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zza(T t, byte[] bArr, int i, int i2, zzay zzayVar) throws IOException {
        zzcg zzcgVar = (zzcg) t;
        zzey zzeyVarZzeb = zzcgVar.zzjp;
        if (zzeyVarZzeb == zzey.zzea()) {
            zzeyVarZzeb = zzey.zzeb();
            zzcgVar.zzjp = zzeyVarZzeb;
        }
        zzey zzeyVar = zzeyVarZzeb;
        while (i < i2) {
            int iZza = zzax.zza(bArr, i, zzayVar);
            int i3 = zzayVar.zzfd;
            if (i3 != 11) {
                i = (i3 & 7) == 2 ? zzax.zza(i3, bArr, iZza, i2, zzeyVar, zzayVar) : zzax.zza(i3, bArr, iZza, i2, zzayVar);
            } else {
                int i4 = 0;
                zzbb zzbbVar = null;
                while (iZza < i2) {
                    iZza = zzax.zza(bArr, iZza, zzayVar);
                    int i5 = zzayVar.zzfd;
                    int i6 = i5 >>> 3;
                    int i7 = i5 & 7;
                    if (i6 == 2) {
                        if (i7 != 0) {
                            if (i5 != 12) {
                                break;
                                break;
                            }
                            iZza = zzax.zza(i5, bArr, iZza, i2, zzayVar);
                        } else {
                            iZza = zzax.zza(bArr, iZza, zzayVar);
                            i4 = zzayVar.zzfd;
                        }
                    } else if (i6 != 3 || i7 != 2) {
                        if (i5 != 12) {
                            break;
                        } else {
                            iZza = zzax.zza(i5, bArr, iZza, i2, zzayVar);
                        }
                    } else {
                        iZza = zzax.zze(bArr, iZza, zzayVar);
                        zzbbVar = (zzbb) zzayVar.zzff;
                    }
                }
                if (zzbbVar != null) {
                    zzeyVar.zzb((i4 << 3) | 2, zzbbVar);
                }
                i = iZza;
            }
        }
        if (i != i2) {
            throw zzco.zzbo();
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t) {
        this.zzmx.zzc(t);
        this.zzmy.zzc(t);
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t, T t2) {
        zzeh.zza(this.zzmx, t, t2);
        if (this.zzmo) {
            zzeh.zza(this.zzmy, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final int zzm(T t) {
        zzex<?, ?> zzexVar = this.zzmx;
        int iZzr = zzexVar.zzr(zzexVar.zzq(t));
        return this.zzmo ? iZzr + this.zzmy.zza(t).zzat() : iZzr;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean zzo(T t) {
        return this.zzmy.zza(t).isInitialized();
    }
}
