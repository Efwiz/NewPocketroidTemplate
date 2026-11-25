package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbbd extends zzans<zzbbd, zzbba> implements zzapg {
    private static final zzbbd zzb;
    private int zze;
    private long zzf;
    private int zzi;
    private int zzj;
    private zzbav zzm;
    private zzbbp zzo;
    private byte zzu = 2;
    private String zzg = "";
    private String zzh = "";
    private zzaob<String> zzk = zzans.zzI();
    private String zzl = "";
    private zzamk zzn = zzamk.zzb;
    private String zzp = "";
    private zzaob<zzbbc> zzq = zzI();
    private zzaob<zzbay> zzr = zzI();
    private zzamk zzs = zzamk.zzb;
    private String zzt = "";

    static {
        zzbbd zzbbdVar = new zzbbd();
        zzb = zzbbdVar;
        zzans.zzM(zzbbd.class, zzbbdVar);
    }

    private zzbbd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzu);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u000f\u0000\u0001\u0001\u001c\u000f\u0000\u0003\u0004\u0001ᔂ\u0000\u0002б\u0010ဈ\u0001\u0011ဈ\u0002\u0012င\u0003\u0013င\u0004\u0014\u001a\u0015ဈ\u0005\u0016ည\u0007\u0017ᐉ\b\u0018ᐉ\u0006\u0019ည\n\u001aဈ\t\u001bဈ\u000b\u001c\u001b", new Object[]{"zze", "zzf", "zzq", zzbbc.class, "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzn", "zzo", "zzm", "zzs", "zzp", "zzt", "zzr", zzbay.class});
        }
        if (i2 == 3) {
            return new zzbbd();
        }
        zzbaz zzbazVar = null;
        if (i2 == 4) {
            return new zzbba(zzbazVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzu = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
