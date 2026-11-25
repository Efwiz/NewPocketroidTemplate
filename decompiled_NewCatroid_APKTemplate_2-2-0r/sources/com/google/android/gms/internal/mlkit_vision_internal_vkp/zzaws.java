package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaws extends zzano<zzaws, zzawr> implements zzapg {
    private static final zzaws zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private zzawc zzi;
    private int zzk;
    private int zzl;
    private zzawg zzn;
    private long zzq;
    private byte zzr = 2;
    private String zzj = "";
    private zzaob<zzawq> zzm = zzI();
    private String zzo = "";
    private zzaob<String> zzp = zzans.zzI();

    static {
        zzaws zzawsVar = new zzaws();
        zze = zzawsVar;
        zzans.zzM(zzaws.class, zzawsVar);
    }

    private zzaws() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzr);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0002\u0003\u0001င\u0000\u0002င\u0001\u0003ᐉ\u0002\u0004ဈ\u0003\u0005င\u0004\u0006င\u0005\u0007Л\bᐉ\u0006\tဈ\u0007\n\u001a\u000bဂ\b", new Object[]{"zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", zzawq.class, "zzn", "zzo", "zzp", "zzq"});
        }
        if (i2 == 3) {
            return new zzaws();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzawr(zzavrVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzr = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
