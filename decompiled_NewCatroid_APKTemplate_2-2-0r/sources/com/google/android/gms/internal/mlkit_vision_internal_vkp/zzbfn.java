package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbfn extends zzanm<zzbfo, zzbfn> implements zzapg {
    private zzbfn() {
        super(zzbfo.zzb);
    }

    public final zzbfn zza(String str) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzbfo.zzi((zzbfo) this.zza, str);
        return this;
    }

    public final zzbfn zzb(int i) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        ((zzbfo) this.zza).zzg = i;
        return this;
    }

    public final zzbfn zzc(String str) {
        if (this.zzb) {
            zzw();
            this.zzb = false;
        }
        zzbfo.zzj((zzbfo) this.zza, str);
        return this;
    }

    /* synthetic */ zzbfn(zzbfm zzbfmVar) {
        super(zzbfo.zzb);
    }
}
