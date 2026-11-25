package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzla<E> extends zzkt<E> {
    public zzla() {
        super(4);
    }

    public final zzla<E> zzb(E e) {
        super.zza(e);
        return this;
    }

    public final zzlb<E> zzc() {
        int i = this.zzb;
        if (i == 0) {
            return zzlf.zza;
        }
        if (i == 1) {
            Object obj = this.zza[0];
            obj.getClass();
            return new zzlg(obj);
        }
        zzlb<E> zzlbVarZzl = zzlb.zzl(i, this.zza);
        this.zzb = zzlbVarZzl.size();
        this.zzc = true;
        return zzlbVarZzl;
    }
}
