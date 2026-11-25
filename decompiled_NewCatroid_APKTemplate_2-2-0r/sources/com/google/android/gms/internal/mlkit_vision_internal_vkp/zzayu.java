package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzayu extends zzano<zzayu, zzayt> implements zzapg {
    private static final zzayu zze;
    private int zzf;
    private Object zzh;
    private Object zzj;
    private zzaro zzm;
    private int zzn;
    private int zzg = 0;
    private int zzi = 0;
    private byte zzq = 2;
    private String zzk = "FaceAttributesClientBrainEmbedder";
    private String zzl = "";
    private boolean zzo = true;
    private int zzp = 1;

    static {
        zzayu zzayuVar = new zzayu();
        zze = zzayuVar;
        zzans.zzM(zzayu.class, zzayuVar);
    }

    private zzayu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzq);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\n\u0002\u0001\u0002\r\n\u0000\u0000\u0001\u0002ြ\u0000\u0003ျ\u0000\u0004ဇ\b\u0005ဌ\t\u0007ᐼ\u0001\bျ\u0001\tဈ\u0000\nဈ\u0001\fင\u0003\rဉ\u0002", new Object[]{"zzh", "zzg", "zzj", "zzi", "zzf", zzawz.class, "zzo", "zzp", zzays.zza, zzayx.class, "zzk", "zzl", "zzn", "zzm"});
        }
        if (i2 == 3) {
            return new zzayu();
        }
        zzayr zzayrVar = null;
        if (i2 == 4) {
            return new zzayt(zzayrVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzq = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
