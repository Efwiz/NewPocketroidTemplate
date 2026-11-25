package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zztk implements zzww {
    private final zztj zza;
    private int zzb;
    private int zzc;
    private int zzd = 0;

    private zztk(zztj zztjVar) {
        zzvi.zzf(zztjVar, "input");
        this.zza = zztjVar;
        zztjVar.zzc = this;
    }

    private final <T> T zzO(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int i = this.zzc;
        this.zzc = ((this.zzb >>> 3) << 3) | 4;
        try {
            T tZze = zzwxVar.zze();
            zzwxVar.zzh(tZze, this, zzujVar);
            zzwxVar.zzf(tZze);
            if (this.zzb != this.zzc) {
                throw zzvk.zzg();
            }
            this.zzc = i;
            return tZze;
        } catch (Throwable th) {
            this.zzc = i;
            throw th;
        }
    }

    private final <T> T zzU(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int iZzn = ((zzth) this.zza).zzn();
        zztj zztjVar = this.zza;
        if (zztjVar.zza >= zztjVar.zzb) {
            throw new zzvk("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        int iZzb = zztjVar.zzb(iZzn);
        T tZze = zzwxVar.zze();
        this.zza.zza++;
        zzwxVar.zzh(tZze, this, zzujVar);
        zzwxVar.zzf(tZze);
        this.zza.zzg(0);
        zztj zztjVar2 = this.zza;
        zztjVar2.zza--;
        zztjVar2.zzh(iZzb);
        return tZze;
    }

    private final void zzV(int i) throws IOException {
        if (this.zza.zza() != i) {
            throw zzvk.zzj();
        }
    }

    private final void zzW(int i) throws IOException {
        if ((this.zzb & 7) != i) {
            throw zzvk.zza();
        }
    }

    private static final void zzX(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzvk.zzg();
        }
    }

    private static final void zzY(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzvk.zzg();
        }
    }

    public static zztk zzp(zztj zztjVar) {
        zztk zztkVar = zztjVar.zzc;
        return zztkVar != null ? zztkVar : new zztk(zztjVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzA(List<Double> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzug)) {
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    list.add(Double.valueOf(Double.longBitsToDouble(((zzth) this.zza).zzo())));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZzn = ((zzth) this.zza).zzn();
            zzY(iZzn);
            int iZza = this.zza.zza() + iZzn;
            do {
                list.add(Double.valueOf(Double.longBitsToDouble(((zzth) this.zza).zzo())));
            } while (this.zza.zza() < iZza);
            return;
        }
        zzug zzugVar = (zzug) list;
        int i2 = this.zzb & 7;
        if (i2 == 1) {
            do {
                zzugVar.zze(Double.longBitsToDouble(((zzth) this.zza).zzo()));
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZzn2 = ((zzth) this.zza).zzn();
        zzY(iZzn2);
        int iZza2 = this.zza.zza() + iZzn2;
        do {
            zzugVar.zze(Double.longBitsToDouble(((zzth) this.zza).zzo()));
        } while (this.zza.zza() < iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzB(List<Integer> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzva)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(((zzth) this.zza).zzn()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Integer.valueOf(((zzth) this.zza).zzn()));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzvaVar.zzh(((zzth) this.zza).zzn());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzvaVar.zzh(((zzth) this.zza).zzn());
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzC(List<Integer> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzva)) {
            int i = this.zzb & 7;
            if (i == 2) {
                int iZzn = ((zzth) this.zza).zzn();
                zzX(iZzn);
                int iZza = this.zza.zza() + iZzn;
                do {
                    list.add(Integer.valueOf(((zzth) this.zza).zzm()));
                } while (this.zza.zza() < iZza);
                return;
            }
            if (i != 5) {
                throw zzvk.zza();
            }
            do {
                list.add(Integer.valueOf(((zzth) this.zza).zzm()));
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc = this.zza.zzc();
                }
            } while (iZzc == this.zzb);
            this.zzd = iZzc;
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i2 = this.zzb & 7;
        if (i2 == 2) {
            int iZzn2 = ((zzth) this.zza).zzn();
            zzX(iZzn2);
            int iZza2 = this.zza.zza() + iZzn2;
            do {
                zzvaVar.zzh(((zzth) this.zza).zzm());
            } while (this.zza.zza() < iZza2);
            return;
        }
        if (i2 != 5) {
            throw zzvk.zza();
        }
        do {
            zzvaVar.zzh(((zzth) this.zza).zzm());
            if (this.zza.zzi()) {
                return;
            } else {
                iZzc2 = this.zza.zzc();
            }
        } while (iZzc2 == this.zzb);
        this.zzd = iZzc2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzD(List<Long> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzvz)) {
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    list.add(Long.valueOf(((zzth) this.zza).zzo()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZzn = ((zzth) this.zza).zzn();
            zzY(iZzn);
            int iZza = this.zza.zza() + iZzn;
            do {
                list.add(Long.valueOf(((zzth) this.zza).zzo()));
            } while (this.zza.zza() < iZza);
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i2 = this.zzb & 7;
        if (i2 == 1) {
            do {
                zzvzVar.zzf(((zzth) this.zza).zzo());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZzn2 = ((zzth) this.zza).zzn();
        zzY(iZzn2);
        int iZza2 = this.zza.zza() + iZzn2;
        do {
            zzvzVar.zzf(((zzth) this.zza).zzo());
        } while (this.zza.zza() < iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzE(List<Float> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzuq)) {
            int i = this.zzb & 7;
            if (i == 2) {
                int iZzn = ((zzth) this.zza).zzn();
                zzX(iZzn);
                int iZza = this.zza.zza() + iZzn;
                do {
                    list.add(Float.valueOf(Float.intBitsToFloat(((zzth) this.zza).zzm())));
                } while (this.zza.zza() < iZza);
                return;
            }
            if (i != 5) {
                throw zzvk.zza();
            }
            do {
                list.add(Float.valueOf(Float.intBitsToFloat(((zzth) this.zza).zzm())));
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc = this.zza.zzc();
                }
            } while (iZzc == this.zzb);
            this.zzd = iZzc;
            return;
        }
        zzuq zzuqVar = (zzuq) list;
        int i2 = this.zzb & 7;
        if (i2 == 2) {
            int iZzn2 = ((zzth) this.zza).zzn();
            zzX(iZzn2);
            int iZza2 = this.zza.zza() + iZzn2;
            do {
                zzuqVar.zze(Float.intBitsToFloat(((zzth) this.zza).zzm()));
            } while (this.zza.zza() < iZza2);
            return;
        }
        if (i2 != 5) {
            throw zzvk.zza();
        }
        do {
            zzuqVar.zze(Float.intBitsToFloat(((zzth) this.zza).zzm()));
            if (this.zza.zzi()) {
                return;
            } else {
                iZzc2 = this.zza.zzc();
            }
        } while (iZzc2 == this.zzb);
        this.zzd = iZzc2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> void zzF(List<T> list, zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int iZzc;
        int i = this.zzb;
        if ((i & 7) != 3) {
            throw zzvk.zza();
        }
        do {
            list.add(zzO(zzwxVar, zzujVar));
            if (this.zza.zzi() || this.zzd != 0) {
                return;
            } else {
                iZzc = this.zza.zzc();
            }
        } while (iZzc == i);
        this.zzd = iZzc;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzG(List<Integer> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzva)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(((zzth) this.zza).zzn()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Integer.valueOf(((zzth) this.zza).zzn()));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzvaVar.zzh(((zzth) this.zza).zzn());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzvaVar.zzh(((zzth) this.zza).zzn());
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzH(List<Long> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzvz)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(((zzth) this.zza).zzp()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Long.valueOf(((zzth) this.zza).zzp()));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzvzVar.zzf(((zzth) this.zza).zzp());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzvzVar.zzf(((zzth) this.zza).zzp());
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> void zzI(List<T> list, zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int iZzc;
        int i = this.zzb;
        if ((i & 7) != 2) {
            throw zzvk.zza();
        }
        do {
            list.add(zzU(zzwxVar, zzujVar));
            if (this.zza.zzi() || this.zzd != 0) {
                return;
            } else {
                iZzc = this.zza.zzc();
            }
        } while (iZzc == i);
        this.zzd = iZzc;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzJ(List<Integer> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzva)) {
            int i = this.zzb & 7;
            if (i == 2) {
                int iZzn = ((zzth) this.zza).zzn();
                zzX(iZzn);
                int iZza = this.zza.zza() + iZzn;
                do {
                    list.add(Integer.valueOf(((zzth) this.zza).zzm()));
                } while (this.zza.zza() < iZza);
                return;
            }
            if (i != 5) {
                throw zzvk.zza();
            }
            do {
                list.add(Integer.valueOf(((zzth) this.zza).zzm()));
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc = this.zza.zzc();
                }
            } while (iZzc == this.zzb);
            this.zzd = iZzc;
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i2 = this.zzb & 7;
        if (i2 == 2) {
            int iZzn2 = ((zzth) this.zza).zzn();
            zzX(iZzn2);
            int iZza2 = this.zza.zza() + iZzn2;
            do {
                zzvaVar.zzh(((zzth) this.zza).zzm());
            } while (this.zza.zza() < iZza2);
            return;
        }
        if (i2 != 5) {
            throw zzvk.zza();
        }
        do {
            zzvaVar.zzh(((zzth) this.zza).zzm());
            if (this.zza.zzi()) {
                return;
            } else {
                iZzc2 = this.zza.zzc();
            }
        } while (iZzc2 == this.zzb);
        this.zzd = iZzc2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzK(List<Long> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzvz)) {
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    list.add(Long.valueOf(((zzth) this.zza).zzo()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZzn = ((zzth) this.zza).zzn();
            zzY(iZzn);
            int iZza = this.zza.zza() + iZzn;
            do {
                list.add(Long.valueOf(((zzth) this.zza).zzo()));
            } while (this.zza.zza() < iZza);
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i2 = this.zzb & 7;
        if (i2 == 1) {
            do {
                zzvzVar.zzf(((zzth) this.zza).zzo());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZzn2 = ((zzth) this.zza).zzn();
        zzY(iZzn2);
        int iZza2 = this.zza.zza() + iZzn2;
        do {
            zzvzVar.zzf(((zzth) this.zza).zzo());
        } while (this.zza.zza() < iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzL(List<Integer> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzva)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(zzth.zzs(((zzth) this.zza).zzn())));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Integer.valueOf(zzth.zzs(((zzth) this.zza).zzn())));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzvaVar.zzh(zzth.zzs(((zzth) this.zza).zzn()));
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzvaVar.zzh(zzth.zzs(((zzth) this.zza).zzn()));
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzM(List<Long> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzvz)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(zzth.zzt(((zzth) this.zza).zzp())));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Long.valueOf(zzth.zzt(((zzth) this.zza).zzp())));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzvzVar.zzf(zzth.zzt(((zzth) this.zza).zzp()));
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzvzVar.zzf(zzth.zzt(((zzth) this.zza).zzp()));
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzN(List<String> list) throws IOException {
        zzw(list, false);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzP(List<String> list) throws IOException {
        zzw(list, true);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzQ(List<Integer> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzva)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(((zzth) this.zza).zzn()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Integer.valueOf(((zzth) this.zza).zzn()));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzvaVar.zzh(((zzth) this.zza).zzn());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzvaVar.zzh(((zzth) this.zza).zzn());
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzR(List<Long> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzvz)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(((zzth) this.zza).zzp()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Long.valueOf(((zzth) this.zza).zzp()));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzvzVar.zzf(((zzth) this.zza).zzp());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzvzVar.zzf(((zzth) this.zza).zzp());
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final boolean zzS() throws IOException {
        zzW(0);
        return this.zza.zzj();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final boolean zzT() throws IOException {
        int i;
        if (this.zza.zzi() || (i = this.zzb) == this.zzc) {
            return false;
        }
        return this.zza.zzk(i);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final double zza() throws IOException {
        zzW(1);
        return Double.longBitsToDouble(((zzth) this.zza).zzo());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final float zzb() throws IOException {
        zzW(5);
        return Float.intBitsToFloat(((zzth) this.zza).zzm());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzc() throws IOException {
        int iZzc = this.zzd;
        if (iZzc != 0) {
            this.zzb = iZzc;
            this.zzd = 0;
        } else {
            iZzc = this.zza.zzc();
            this.zzb = iZzc;
        }
        if (iZzc == 0 || iZzc == this.zzc) {
            return Integer.MAX_VALUE;
        }
        return iZzc >>> 3;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzd() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zze() throws IOException {
        zzW(0);
        return ((zzth) this.zza).zzn();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzf() throws IOException {
        zzW(5);
        return ((zzth) this.zza).zzm();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzg() throws IOException {
        zzW(0);
        return ((zzth) this.zza).zzn();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzh() throws IOException {
        zzW(5);
        return ((zzth) this.zza).zzm();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzi() throws IOException {
        zzW(0);
        return zzth.zzs(((zzth) this.zza).zzn());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzj() throws IOException {
        zzW(0);
        return ((zzth) this.zza).zzn();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzk() throws IOException {
        zzW(1);
        return ((zzth) this.zza).zzo();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzl() throws IOException {
        zzW(0);
        return ((zzth) this.zza).zzp();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzm() throws IOException {
        zzW(1);
        return ((zzth) this.zza).zzo();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzn() throws IOException {
        zzW(0);
        return zzth.zzt(((zzth) this.zza).zzp());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzo() throws IOException {
        zzW(0);
        return ((zzth) this.zza).zzp();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final zztd zzq() throws IOException {
        zzW(2);
        return this.zza.zzd();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzr(Class<T> cls, zzuj zzujVar) throws IOException {
        zzW(3);
        return (T) zzO(zzwt.zza().zzb(cls), zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzs(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        zzW(3);
        return (T) zzO(zzwxVar, zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzt(Class<T> cls, zzuj zzujVar) throws IOException {
        zzW(2);
        return (T) zzU(zzwt.zza().zzb(cls), zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzu(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        zzW(2);
        return (T) zzU(zzwxVar, zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final String zzv() throws IOException {
        zzW(2);
        return this.zza.zze();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final String zzx() throws IOException {
        zzW(2);
        return this.zza.zzf();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzy(List<Boolean> list) throws IOException {
        int iZzc;
        int iZzc2;
        if (!(list instanceof zzsr)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Boolean.valueOf(this.zza.zzj()));
                    if (this.zza.zzi()) {
                        return;
                    } else {
                        iZzc = this.zza.zzc();
                    }
                } while (iZzc == this.zzb);
                this.zzd = iZzc;
                return;
            }
            if (i != 2) {
                throw zzvk.zza();
            }
            int iZza = this.zza.zza() + ((zzth) this.zza).zzn();
            do {
                list.add(Boolean.valueOf(this.zza.zzj()));
            } while (this.zza.zza() < iZza);
            zzV(iZza);
            return;
        }
        zzsr zzsrVar = (zzsr) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzsrVar.zze(this.zza.zzj());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc2 = this.zza.zzc();
                }
            } while (iZzc2 == this.zzb);
            this.zzd = iZzc2;
            return;
        }
        if (i2 != 2) {
            throw zzvk.zza();
        }
        int iZza2 = this.zza.zza() + ((zzth) this.zza).zzn();
        do {
            zzsrVar.zze(this.zza.zzj());
        } while (this.zza.zza() < iZza2);
        zzV(iZza2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzz(List<zztd> list) throws IOException {
        int iZzc;
        if ((this.zzb & 7) != 2) {
            throw zzvk.zza();
        }
        do {
            list.add(zzq());
            if (this.zza.zzi()) {
                return;
            } else {
                iZzc = this.zza.zzc();
            }
        } while (iZzc == this.zzb);
        this.zzd = iZzc;
    }

    public final void zzw(List<String> list, boolean z) throws IOException {
        int iZzc;
        int iZzc2;
        if ((this.zzb & 7) != 2) {
            throw zzvk.zza();
        }
        if (!(list instanceof zzvs) || z) {
            do {
                list.add(z ? zzx() : zzv());
                if (this.zza.zzi()) {
                    return;
                } else {
                    iZzc = this.zza.zzc();
                }
            } while (iZzc == this.zzb);
            this.zzd = iZzc;
            return;
        }
        zzvs zzvsVar = (zzvs) list;
        do {
            zzvsVar.zzi(zzq());
            if (this.zza.zzi()) {
                return;
            } else {
                iZzc2 = this.zza.zzc();
            }
        } while (iZzc2 == this.zzb);
        this.zzd = iZzc2;
    }
}
