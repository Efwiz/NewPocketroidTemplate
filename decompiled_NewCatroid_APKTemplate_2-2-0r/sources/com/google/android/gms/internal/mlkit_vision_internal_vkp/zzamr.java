package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzamr implements zzapr {
    private final zzamq zza;
    private int zzb;
    private int zzc;
    private int zzd = 0;

    private zzamr(zzamq zzamqVar) {
        zzaoc.zzf(zzamqVar, "input");
        this.zza = zzamqVar;
        zzamqVar.zzc = this;
    }

    private final Object zzQ(zzarj zzarjVar, Class<?> cls, zzanc zzancVar) throws IOException {
        zzarj zzarjVar2 = zzarj.DOUBLE;
        switch (zzarjVar) {
            case DOUBLE:
                return Double.valueOf(zza());
            case FLOAT:
                return Float.valueOf(zzb());
            case INT64:
                return Long.valueOf(zzl());
            case UINT64:
                return Long.valueOf(zzo());
            case INT32:
                return Integer.valueOf(zzg());
            case FIXED64:
                return Long.valueOf(zzk());
            case FIXED32:
                return Integer.valueOf(zzf());
            case BOOL:
                return Boolean.valueOf(zzO());
            case STRING:
                return zzu();
            case GROUP:
            default:
                throw new RuntimeException("unsupported field type.");
            case MESSAGE:
                zzU(2);
                return zzS(zzapo.zza().zzb(cls), zzancVar);
            case BYTES:
                return zzp();
            case UINT32:
                return Integer.valueOf(zzj());
            case ENUM:
                return Integer.valueOf(zze());
            case SFIXED32:
                return Integer.valueOf(zzh());
            case SFIXED64:
                return Long.valueOf(zzm());
            case SINT32:
                return Integer.valueOf(zzi());
            case SINT64:
                return Long.valueOf(zzn());
        }
    }

    private final <T> T zzR(zzapz<T> zzapzVar, zzanc zzancVar) throws IOException {
        int i = this.zzc;
        this.zzc = ((this.zzb >>> 3) << 3) | 4;
        try {
            T tZze = zzapzVar.zze();
            zzapzVar.zzh(tZze, this, zzancVar);
            zzapzVar.zzf(tZze);
            if (this.zzb != this.zzc) {
                throw zzaoe.zzg();
            }
            this.zzc = i;
            return tZze;
        } catch (Throwable th) {
            this.zzc = i;
            throw th;
        }
    }

    private final <T> T zzS(zzapz<T> zzapzVar, zzanc zzancVar) throws IOException {
        int iZzn = this.zza.zzn();
        zzamq zzamqVar = this.zza;
        if (zzamqVar.zza >= zzamqVar.zzb) {
            throw new zzaoe("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        int iZze = zzamqVar.zze(iZzn);
        T tZze = zzapzVar.zze();
        this.zza.zza++;
        zzapzVar.zzh(tZze, this, zzancVar);
        zzapzVar.zzf(tZze);
        this.zza.zzz(0);
        zzamq zzamqVar2 = this.zza;
        zzamqVar2.zza--;
        zzamqVar2.zzA(iZze);
        return tZze;
    }

    private final void zzT(int i) throws IOException {
        if (this.zza.zzd() != i) {
            throw zzaoe.zzj();
        }
    }

    private final void zzU(int i) throws IOException {
        if ((this.zzb & 7) != i) {
            throw zzaoe.zza();
        }
    }

    private static final void zzV(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzaoe.zzg();
        }
    }

    private static final void zzW(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzaoe.zzg();
        }
    }

    public static zzamr zzq(zzamq zzamqVar) {
        zzamr zzamrVar = zzamqVar.zzc;
        return zzamrVar != null ? zzamrVar : new zzamr(zzamqVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzA(List<Long> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzaot)) {
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    list.add(Long.valueOf(this.zza.zzo()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzn = this.zza.zzn();
            zzW(iZzn);
            int iZzd = this.zza.zzd() + iZzn;
            do {
                list.add(Long.valueOf(this.zza.zzo()));
            } while (this.zza.zzd() < iZzd);
            return;
        }
        zzaot zzaotVar = (zzaot) list;
        int i2 = this.zzb & 7;
        if (i2 == 1) {
            do {
                zzaotVar.zzg(this.zza.zzo());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzn2 = this.zza.zzn();
        zzW(iZzn2);
        int iZzd2 = this.zza.zzd() + iZzn2;
        do {
            zzaotVar.zzg(this.zza.zzo());
        } while (this.zza.zzd() < iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzB(List<Float> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzanj)) {
            int i = this.zzb & 7;
            if (i == 2) {
                int iZzn = this.zza.zzn();
                zzV(iZzn);
                int iZzd = this.zza.zzd() + iZzn;
                do {
                    list.add(Float.valueOf(this.zza.zzc()));
                } while (this.zza.zzd() < iZzd);
                return;
            }
            if (i != 5) {
                throw zzaoe.zza();
            }
            do {
                list.add(Float.valueOf(this.zza.zzc()));
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm = this.zza.zzm();
                }
            } while (iZzm == this.zzb);
            this.zzd = iZzm;
            return;
        }
        zzanj zzanjVar = (zzanj) list;
        int i2 = this.zzb & 7;
        if (i2 == 2) {
            int iZzn2 = this.zza.zzn();
            zzV(iZzn2);
            int iZzd2 = this.zza.zzd() + iZzn2;
            do {
                zzanjVar.zzf(this.zza.zzc());
            } while (this.zza.zzd() < iZzd2);
            return;
        }
        if (i2 != 5) {
            throw zzaoe.zza();
        }
        do {
            zzanjVar.zzf(this.zza.zzc());
            if (this.zza.zzC()) {
                return;
            } else {
                iZzm2 = this.zza.zzm();
            }
        } while (iZzm2 == this.zzb);
        this.zzd = iZzm2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final <T> void zzC(List<T> list, zzapz<T> zzapzVar, zzanc zzancVar) throws IOException {
        int iZzm;
        int i = this.zzb;
        if ((i & 7) != 3) {
            throw zzaoe.zza();
        }
        do {
            list.add(zzR(zzapzVar, zzancVar));
            if (this.zza.zzC() || this.zzd != 0) {
                return;
            } else {
                iZzm = this.zza.zzm();
            }
        } while (iZzm == i);
        this.zzd = iZzm;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzD(List<Integer> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzant)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzh()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Integer.valueOf(this.zza.zzh()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzant zzantVar = (zzant) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzantVar.zzg(this.zza.zzh());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzantVar.zzg(this.zza.zzh());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzE(List<Long> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzaot)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(this.zza.zzp()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Long.valueOf(this.zza.zzp()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzaot zzaotVar = (zzaot) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzaotVar.zzg(this.zza.zzp());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzaotVar.zzg(this.zza.zzp());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final <K, V> void zzF(Map<K, V> map, zzaox<K, V> zzaoxVar, zzanc zzancVar) throws IOException {
        zzU(2);
        int iZze = this.zza.zze(this.zza.zzn());
        Object objZzQ = zzaoxVar.zzb;
        Object objZzQ2 = zzaoxVar.zzd;
        while (true) {
            try {
                int iZzc = zzc();
                if (iZzc == Integer.MAX_VALUE || this.zza.zzC()) {
                    break;
                }
                if (iZzc == 1) {
                    objZzQ = zzQ(zzaoxVar.zza, null, null);
                } else if (iZzc != 2) {
                    try {
                        if (!zzP()) {
                            throw new zzaoe("Unable to parse map entry.");
                        }
                    } catch (zzaod unused) {
                        if (!zzP()) {
                            throw new zzaoe("Unable to parse map entry.");
                        }
                    }
                } else {
                    objZzQ2 = zzQ(zzaoxVar.zzc, zzaoxVar.zzd.getClass(), zzancVar);
                }
            } catch (Throwable th) {
                this.zza.zzA(iZze);
                throw th;
            }
        }
        map.put(objZzQ, objZzQ2);
        this.zza.zzA(iZze);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final <T> void zzG(List<T> list, zzapz<T> zzapzVar, zzanc zzancVar) throws IOException {
        int iZzm;
        int i = this.zzb;
        if ((i & 7) != 2) {
            throw zzaoe.zza();
        }
        do {
            list.add(zzS(zzapzVar, zzancVar));
            if (this.zza.zzC() || this.zzd != 0) {
                return;
            } else {
                iZzm = this.zza.zzm();
            }
        } while (iZzm == i);
        this.zzd = iZzm;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzH(List<Integer> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzant)) {
            int i = this.zzb & 7;
            if (i == 2) {
                int iZzn = this.zza.zzn();
                zzV(iZzn);
                int iZzd = this.zza.zzd() + iZzn;
                do {
                    list.add(Integer.valueOf(this.zza.zzk()));
                } while (this.zza.zzd() < iZzd);
                return;
            }
            if (i != 5) {
                throw zzaoe.zza();
            }
            do {
                list.add(Integer.valueOf(this.zza.zzk()));
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm = this.zza.zzm();
                }
            } while (iZzm == this.zzb);
            this.zzd = iZzm;
            return;
        }
        zzant zzantVar = (zzant) list;
        int i2 = this.zzb & 7;
        if (i2 == 2) {
            int iZzn2 = this.zza.zzn();
            zzV(iZzn2);
            int iZzd2 = this.zza.zzd() + iZzn2;
            do {
                zzantVar.zzg(this.zza.zzk());
            } while (this.zza.zzd() < iZzd2);
            return;
        }
        if (i2 != 5) {
            throw zzaoe.zza();
        }
        do {
            zzantVar.zzg(this.zza.zzk());
            if (this.zza.zzC()) {
                return;
            } else {
                iZzm2 = this.zza.zzm();
            }
        } while (iZzm2 == this.zzb);
        this.zzd = iZzm2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzI(List<Long> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzaot)) {
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    list.add(Long.valueOf(this.zza.zzt()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzn = this.zza.zzn();
            zzW(iZzn);
            int iZzd = this.zza.zzd() + iZzn;
            do {
                list.add(Long.valueOf(this.zza.zzt()));
            } while (this.zza.zzd() < iZzd);
            return;
        }
        zzaot zzaotVar = (zzaot) list;
        int i2 = this.zzb & 7;
        if (i2 == 1) {
            do {
                zzaotVar.zzg(this.zza.zzt());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzn2 = this.zza.zzn();
        zzW(iZzn2);
        int iZzd2 = this.zza.zzd() + iZzn2;
        do {
            zzaotVar.zzg(this.zza.zzt());
        } while (this.zza.zzd() < iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzJ(List<Integer> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzant)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzl()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Integer.valueOf(this.zza.zzl()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzant zzantVar = (zzant) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzantVar.zzg(this.zza.zzl());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzantVar.zzg(this.zza.zzl());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzK(List<Long> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzaot)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(this.zza.zzu()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Long.valueOf(this.zza.zzu()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzaot zzaotVar = (zzaot) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzaotVar.zzg(this.zza.zzu());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzaotVar.zzg(this.zza.zzu());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzM(List<Integer> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzant)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzn()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Integer.valueOf(this.zza.zzn()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzant zzantVar = (zzant) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzantVar.zzg(this.zza.zzn());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzantVar.zzg(this.zza.zzn());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzN(List<Long> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzaot)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(this.zza.zzv()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Long.valueOf(this.zza.zzv()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzaot zzaotVar = (zzaot) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzaotVar.zzg(this.zza.zzv());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzaotVar.zzg(this.zza.zzv());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final boolean zzO() throws IOException {
        zzU(0);
        return this.zza.zzD();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final boolean zzP() throws IOException {
        int i;
        if (this.zza.zzC() || (i = this.zzb) == this.zzc) {
            return false;
        }
        return this.zza.zzE(i);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final double zza() throws IOException {
        zzU(1);
        return this.zza.zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final float zzb() throws IOException {
        zzU(5);
        return this.zza.zzc();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zzc() throws IOException {
        int iZzm = this.zzd;
        if (iZzm != 0) {
            this.zzb = iZzm;
            this.zzd = 0;
        } else {
            iZzm = this.zza.zzm();
            this.zzb = iZzm;
        }
        if (iZzm == 0 || iZzm == this.zzc) {
            return Integer.MAX_VALUE;
        }
        return iZzm >>> 3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zzd() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zze() throws IOException {
        zzU(0);
        return this.zza.zzf();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zzf() throws IOException {
        zzU(5);
        return this.zza.zzg();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zzg() throws IOException {
        zzU(0);
        return this.zza.zzh();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zzh() throws IOException {
        zzU(5);
        return this.zza.zzk();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zzi() throws IOException {
        zzU(0);
        return this.zza.zzl();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final int zzj() throws IOException {
        zzU(0);
        return this.zza.zzn();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final long zzk() throws IOException {
        zzU(1);
        return this.zza.zzo();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final long zzl() throws IOException {
        zzU(0);
        return this.zza.zzp();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final long zzm() throws IOException {
        zzU(1);
        return this.zza.zzt();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final long zzn() throws IOException {
        zzU(0);
        return this.zza.zzu();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final long zzo() throws IOException {
        zzU(0);
        return this.zza.zzv();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final zzamk zzp() throws IOException {
        zzU(2);
        return this.zza.zzw();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final <T> T zzr(zzapz<T> zzapzVar, zzanc zzancVar) throws IOException {
        zzU(3);
        return (T) zzR(zzapzVar, zzancVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final <T> T zzs(zzapz<T> zzapzVar, zzanc zzancVar) throws IOException {
        zzU(2);
        return (T) zzS(zzapzVar, zzancVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final String zzt() throws IOException {
        zzU(2);
        return this.zza.zzx();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final String zzu() throws IOException {
        zzU(2);
        return this.zza.zzy();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzv(List<Boolean> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzalx)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Boolean.valueOf(this.zza.zzD()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Boolean.valueOf(this.zza.zzD()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzalx zzalxVar = (zzalx) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzalxVar.zze(this.zza.zzD());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzalxVar.zze(this.zza.zzD());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzw(List<zzamk> list) throws IOException {
        int iZzm;
        if ((this.zzb & 7) != 2) {
            throw zzaoe.zza();
        }
        do {
            list.add(zzp());
            if (this.zza.zzC()) {
                return;
            } else {
                iZzm = this.zza.zzm();
            }
        } while (iZzm == this.zzb);
        this.zzd = iZzm;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzx(List<Double> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzamz)) {
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    list.add(Double.valueOf(this.zza.zzb()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzn = this.zza.zzn();
            zzW(iZzn);
            int iZzd = this.zza.zzd() + iZzn;
            do {
                list.add(Double.valueOf(this.zza.zzb()));
            } while (this.zza.zzd() < iZzd);
            return;
        }
        zzamz zzamzVar = (zzamz) list;
        int i2 = this.zzb & 7;
        if (i2 == 1) {
            do {
                zzamzVar.zze(this.zza.zzb());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzn2 = this.zza.zzn();
        zzW(iZzn2);
        int iZzd2 = this.zza.zzd() + iZzn2;
        do {
            zzamzVar.zze(this.zza.zzb());
        } while (this.zza.zzd() < iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzy(List<Integer> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzant)) {
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzf()));
                    if (this.zza.zzC()) {
                        return;
                    } else {
                        iZzm = this.zza.zzm();
                    }
                } while (iZzm == this.zzb);
                this.zzd = iZzm;
                return;
            }
            if (i != 2) {
                throw zzaoe.zza();
            }
            int iZzd = this.zza.zzd() + this.zza.zzn();
            do {
                list.add(Integer.valueOf(this.zza.zzf()));
            } while (this.zza.zzd() < iZzd);
            zzT(iZzd);
            return;
        }
        zzant zzantVar = (zzant) list;
        int i2 = this.zzb & 7;
        if (i2 == 0) {
            do {
                zzantVar.zzg(this.zza.zzf());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm2 = this.zza.zzm();
                }
            } while (iZzm2 == this.zzb);
            this.zzd = iZzm2;
            return;
        }
        if (i2 != 2) {
            throw zzaoe.zza();
        }
        int iZzd2 = this.zza.zzd() + this.zza.zzn();
        do {
            zzantVar.zzg(this.zza.zzf());
        } while (this.zza.zzd() < iZzd2);
        zzT(iZzd2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapr
    public final void zzz(List<Integer> list) throws IOException {
        int iZzm;
        int iZzm2;
        if (!(list instanceof zzant)) {
            int i = this.zzb & 7;
            if (i == 2) {
                int iZzn = this.zza.zzn();
                zzV(iZzn);
                int iZzd = this.zza.zzd() + iZzn;
                do {
                    list.add(Integer.valueOf(this.zza.zzg()));
                } while (this.zza.zzd() < iZzd);
                return;
            }
            if (i != 5) {
                throw zzaoe.zza();
            }
            do {
                list.add(Integer.valueOf(this.zza.zzg()));
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm = this.zza.zzm();
                }
            } while (iZzm == this.zzb);
            this.zzd = iZzm;
            return;
        }
        zzant zzantVar = (zzant) list;
        int i2 = this.zzb & 7;
        if (i2 == 2) {
            int iZzn2 = this.zza.zzn();
            zzV(iZzn2);
            int iZzd2 = this.zza.zzd() + iZzn2;
            do {
                zzantVar.zzg(this.zza.zzg());
            } while (this.zza.zzd() < iZzd2);
            return;
        }
        if (i2 != 5) {
            throw zzaoe.zza();
        }
        do {
            zzantVar.zzg(this.zza.zzg());
            if (this.zza.zzC()) {
                return;
            } else {
                iZzm2 = this.zza.zzm();
            }
        } while (iZzm2 == this.zzb);
        this.zzd = iZzm2;
    }

    public final void zzL(List<String> list, boolean z) throws IOException {
        int iZzm;
        int iZzm2;
        if ((this.zzb & 7) != 2) {
            throw zzaoe.zza();
        }
        if (!(list instanceof zzaom) || z) {
            do {
                list.add(z ? zzu() : zzt());
                if (this.zza.zzC()) {
                    return;
                } else {
                    iZzm = this.zza.zzm();
                }
            } while (iZzm == this.zzb);
            this.zzd = iZzm;
            return;
        }
        zzaom zzaomVar = (zzaom) list;
        do {
            zzaomVar.zzi(zzp());
            if (this.zza.zzC()) {
                return;
            } else {
                iZzm2 = this.zza.zzm();
            }
        } while (iZzm2 == this.zzb);
        this.zzd = iZzm2;
    }
}
