package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarq;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzavq;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbaj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzfj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzfs;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzgf;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzho;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzig;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zziu;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzix;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzjv;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdp extends zzans<zzdp, zzdm> implements zzapg {
    private static final zzdp zzb;
    private zzcx zzA;
    private zzdx zzB;
    private boolean zzF;
    private int zzI;
    private int zzJ;
    private zzarq zzK;
    private boolean zzL;
    private boolean zzO;
    private zzdk zzP;
    private boolean zzQ;
    private zzavq zzR;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaf zzS;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbb zzT;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbe zzU;
    private zzfs zzV;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzex zzW;
    private zzfj zzX;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzer zzY;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzcc zzZ;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzfd zzaa;
    private int zzab;
    private boolean zzac;
    private int zzad;
    private int zze;
    private int zzf;
    private zzad zzi;
    private zzgf zzl;
    private zzbc zzo;
    private zzho zzq;
    private zzem zzr;
    private zzbs zzs;
    private zzu zzt;
    private zzea zzu;
    private zzed zzv;
    private boolean zzw;
    private zzjv zzx;
    private zzby zzy;
    private zzbl zzz;
    private byte zzae = 2;
    private zzaob<zziu> zzg = zzI();
    private zzaob<zziu> zzh = zzI();
    private zzaob<zzq> zzj = zzI();
    private zzaob<zzbaj> zzk = zzI();
    private zzaob<zzig> zzm = zzI();
    private zzaob<zzap> zzn = zzI();
    private zzaob<zzix> zzp = zzI();
    private zzaob<String> zzC = zzans.zzI();
    private zzaob<zzev> zzD = zzI();
    private zzaob<String> zzE = zzans.zzI();
    private String zzG = "";
    private int zzH = 1;
    private String zzM = "";
    private String zzN = "";

    static {
        zzdp zzdpVar = new zzdp();
        zzb = zzdpVar;
        zzans.zzM(zzdp.class, zzdpVar);
    }

    private zzdp() {
    }

    public static zzdm zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzdp zzdpVar, zzarq zzarqVar) {
        zzdpVar.zzK = zzarqVar;
        zzdpVar.zze |= 1048576;
    }

    static /* synthetic */ void zze(zzdp zzdpVar, String str) {
        zzdpVar.zze |= 4194304;
        zzdpVar.zzM = str;
    }

    static /* synthetic */ void zzf(zzdp zzdpVar, String str) {
        zzdpVar.zze |= 8388608;
        zzdpVar.zzN = str;
    }

    static /* synthetic */ void zzg(zzdp zzdpVar, zziu zziuVar) {
        zziuVar.getClass();
        zzaob<zziu> zzaobVar = zzdpVar.zzg;
        if (!zzaobVar.zzc()) {
            zzdpVar.zzg = zzans.zzJ(zzaobVar);
        }
        zzdpVar.zzg.add(zziuVar);
    }

    static /* synthetic */ void zzh(zzdp zzdpVar, zzap zzapVar) {
        zzapVar.getClass();
        zzaob<zzap> zzaobVar = zzdpVar.zzn;
        if (!zzaobVar.zzc()) {
            zzdpVar.zzn = zzans.zzJ(zzaobVar);
        }
        zzdpVar.zzn.add(zzapVar);
    }

    static /* synthetic */ void zzi(zzdp zzdpVar, boolean z) {
        zzdpVar.zze |= 512;
        zzdpVar.zzw = true;
    }

    static /* synthetic */ void zzj(zzdp zzdpVar, zzjv zzjvVar) {
        zzjvVar.getClass();
        zzdpVar.zzx = zzjvVar;
        zzdpVar.zze |= 1024;
    }

    static /* synthetic */ void zzk(zzdp zzdpVar, zzby zzbyVar) {
        zzbyVar.getClass();
        zzdpVar.zzy = zzbyVar;
        zzdpVar.zze |= 2048;
    }

    static /* synthetic */ void zzl(zzdp zzdpVar, zzdx zzdxVar) {
        zzdxVar.getClass();
        zzdpVar.zzB = zzdxVar;
        zzdpVar.zze |= 16384;
    }

    static /* synthetic */ void zzm(zzdp zzdpVar, int i) {
        zzdpVar.zzJ = i - 1;
        zzdpVar.zze |= 524288;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzae);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u00012\u0000\u0002\u0001>2\u0000\n\b\u0001\u001b\u0002Л\u0003ဉ\u0001\u0005\u001b\u0006Л\u0007ဇ\u000f\bဉ\u000e\nဉ\n\u000bဉ\u000b\fဉ\f\u000fဉ\u001d\u0010ᐉ\u0002\u0013\u001b\u0014ᐉ\u0003\u0015\u001a\u0016ဉ\u001f\u0017ဉ\u0019\u0018ဉ\r\u001aᐉ\u0004\u001b\u001b\u001cဉ\u0005\u001dဉ\u0006\u001eဉ\u001e\u001f\u001b ဈ\u0010!\u001a\"ဉ $ဇ\u001a%ᐉ\u0007&ᐉ\b'ဉ\u0014)ဉ\u001c*\u001b+ဉ!,ဉ\"-ဌ\u0013.ဇ\t/ဉ$1ဉ#3ဌ%4ဇ\u00155ᐉ\u001b6င\u00117င\u00128ဈ\u00169ဈ\u0017:ဌ'<ဇ&=ဇ\u0018>ဉ\u0000", new Object[]{"zze", "zzf", "zzg", zziu.class, "zzk", zzbaj.class, "zzl", "zzm", zzig.class, "zzn", zzap.class, "zzF", "zzB", "zzx", "zzy", "zzz", "zzT", "zzo", "zzp", zzix.class, "zzq", "zzC", "zzV", "zzP", "zzA", "zzr", "zzj", zzq.class, "zzs", "zzt", "zzU", "zzD", zzev.class, "zzG", "zzE", "zzW", "zzQ", "zzu", "zzv", "zzK", "zzS", "zzh", zziu.class, "zzX", "zzY", "zzJ", zza.zza, "zzw", "zzaa", "zzZ", "zzab", zzdo.zza, "zzL", "zzR", "zzH", "zzI", "zzM", "zzN", "zzad", zzdn.zza, "zzac", "zzO", "zzi"});
        }
        if (i2 == 3) {
            return new zzdp();
        }
        zzdl zzdlVar = null;
        if (i2 == 4) {
            return new zzdm(zzdlVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzae = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
