package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzatm extends zzans<zzatm, zzatl> implements zzapg {
    private static final zzatm zzb;
    private int zze;
    private int zzg;
    private boolean zzi;
    private int zzn;
    private String zzf = "";
    private String zzh = "";
    private String zzj = "";
    private zzaob<String> zzk = zzans.zzI();
    private zzaob<zzati> zzl = zzI();
    private zzaob<String> zzm = zzans.zzI();

    static {
        zzatm zzatmVar = new zzatm();
        zzb = zzatmVar;
        zzans.zzM(zzatm.class, zzatmVar);
    }

    private zzatm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\t\u0000\u0001\u0001\n\t\u0000\u0003\u0000\u0001ဈ\u0000\u0002င\u0001\u0003\u001a\u0004\u001b\u0006ဈ\u0002\u0007ဇ\u0003\bဈ\u0004\t\u001a\nင\u0005", new Object[]{"zze", "zzf", "zzg", "zzk", "zzl", zzati.class, "zzh", "zzi", "zzj", "zzm", "zzn"});
        }
        if (i2 == 3) {
            return new zzatm();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzatl(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
