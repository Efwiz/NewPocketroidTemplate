package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzakq;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanc;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzano;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaoe;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzavn;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaws;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaxw;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzayn;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzazj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbbd;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbcl;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzgi;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzia;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzim;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzjd;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzjm;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzda extends zzano<zzda, zzcz> implements zzapg {
    private static final zzda zze;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzz zzA;
    private zzjm zzC;
    private zzim zzD;
    private zzm zzE;
    private zzavn zzF;
    private zzaws zzG;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzd zzH;
    private int zzf;
    private long zzg;
    private zzgi zzh;
    private zzjm zzi;
    private zzim zzj;
    private zzia zzk;
    private int zzl;
    private zzaxw zzm;
    private zzakq zzn;
    private zzayn zzo;
    private zzjd zzp;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzei zzq;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzct zzr;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzci zzs;
    private zzbcl zzt;
    private zzdu zzu;
    private boolean zzv;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzat zzw;
    private zzbbd zzx;
    private zzazj zzy;
    private zzbo zzz;
    private byte zzJ = 2;
    private zzaob<zzdh> zzB = zzI();
    private String zzI = "";

    static {
        zzda zzdaVar = new zzda();
        zze = zzdaVar;
        zzans.zzM(zzda.class, zzdaVar);
    }

    private zzda() {
    }

    public static zzda zzc() {
        return zze;
    }

    public static zzda zzd(byte[] bArr, zzanc zzancVar) throws zzaoe {
        return (zzda) zzans.zzD(zze, bArr, zzancVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzJ);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u001d\u0000\u0001\u0001\"\u001d\u0000\u0001\u0007\u0001ဂ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဉ\u0003\u0005ဉ\u0004\u0006ဌ\u0005\u0007ᐉ\u0006\bᐉ\u0007\tᐉ\b\nဉ\t\u000bᐉ\n\fဉ\u000e\rဉ\u000b\u000eဉ\r\u0010ဇ\u000f\u0011ဉ\u0010\u0012ဉ\f\u0013ᐉ\u0011\u0014ဉ\u0012\u0015ᐉ\u0013\u0016ဉ\u0014\u0018\u001b\u0019ဉ\u0017\u001aဉ\u0018\u001bဉ\u0015\u001eဉ\u0016\u001fᐉ\u0019!ဉ\u001a\"ဈ\u001b", new Object[]{"zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", com.google.android.gms.internal.mlkit_vision_internal_vkp.zzdv.zza(), "zzm", "zzn", "zzo", "zzp", "zzq", "zzu", "zzr", "zzt", "zzv", "zzw", "zzs", "zzx", "zzy", "zzz", "zzA", "zzB", zzdh.class, "zzE", "zzF", "zzC", "zzD", "zzG", "zzH", "zzI"});
        }
        if (i2 == 3) {
            return new zzda();
        }
        zzcy zzcyVar = null;
        if (i2 == 4) {
            return new zzcz(zzcyVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzJ = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final zzim zze() {
        zzim zzimVar = this.zzj;
        return zzimVar == null ? zzim.zzc() : zzimVar;
    }

    public final zzjm zzf() {
        zzjm zzjmVar = this.zzi;
        return zzjmVar == null ? zzjm.zze() : zzjmVar;
    }
}
