package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzasm extends zzans<zzasm, zzasl> implements zzapg {
    private static final zzasm zzb;
    private int zze;
    private boolean zzf;
    private boolean zzg;

    static {
        zzasm zzasmVar = new zzasm();
        zzb = zzasmVar;
        zzans.zzM(zzasm.class, zzasmVar);
    }

    private zzasm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0005\u0006\u0002\u0000\u0000\u0000\u0005ဇ\u0000\u0006ဇ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzasm();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzasl(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
